package org.sidiff.difference.mutation;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.impl.RuleApplicationImpl;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.common.util.StatisticsUtil;
import org.sidiff.difference.mutation.config.MutationConfig;
import org.sidiff.difference.mutation.util.MutationUtil;
import org.sidiff.difference.rulebase.EditRule;
import org.silift.common.util.emf.EMFStorage;

/**
 * Mutator class
 * 
 * @author dreuling
 */
public class Mutator {
	
	/**
	 * Mutation configuration to be used
	 */
	private MutationConfig mc;
	
	/**
	 * Mutation management for
	 * saving result and writing logs
	 */
	private MutationManagement mm;
	
	
	public Mutator(MutationConfig mc) {
		super();
		this.mc = mc;	
		this.mm = new MutationManagement();
		
		LogUtil.log(LogEvent.NOTICE, this.mc);

	}

	/**
	 * Peform the mutation.
	 * @throws Exception 
	 */
	public void mutate(IProgressMonitor monitor){
		
		
		//TODO Make use of IProgressMonitor
		// Create subprogressmonitors and stuff
		
		LogUtil.log(LogEvent.NOTICE, "Running Mutator...");		
		
		StatisticsUtil.getInstance().start("Mutation");
		
		int currentOrder = 1;

		while(currentOrder <= this.mc.getMutationOrder()){
			
			SubProgressMonitor spm = new SubProgressMonitor(monitor, 1/this.mc.getMutationOrder());
			spm.beginTask("Mutating Order " + currentOrder, 100);
		
			LogUtil.log(LogEvent.NOTICE, "-------------------------");			
			LogUtil.log(LogEvent.NOTICE, "MutationOrder: " + currentOrder);			
			LogUtil.log(LogEvent.NOTICE, "-------------------------");			
					
			// Use input model in first iteration
			if(currentOrder==1){
				this.mm.setInputModels(currentOrder,Collections.singleton(mc.getTargetModel()));
			}
			
			// Use result mutants from last iteration
			else{
				this.mm.initializeOrder(currentOrder);
			}
			
		
			for(Resource inputModel : this.mm.getInputModels(currentOrder)){
				
				SubProgressMonitor spmm = new SubProgressMonitor(spm, 1/this.mm.getInputModels(currentOrder).size());

				monitor.beginTask("Mutating Model " + inputModel.getURI(), 100);
				
				clearPreviousResults();
				
				LogUtil.log(LogEvent.DEBUG, "Selected Input Model: " + inputModel.getURI());			

				while(!this.mc.getAos().selectionCoverageReached()){
					
					// select next operator
					EditRule operator = this.mc.getAos().selectNextCandidate();

					//Compute all possible contexts
					LinkedList<Match> contexts = computeContexts(inputModel,operator);
					this.mc.getAcs().initializeCandidates(contexts);
					
					int mutantNumber = 1;

					while(!this.mc.getAcs().selectionCoverageReached()){
						
						// check for user cancel request
						if(monitor.isCanceled()){
							//Mark finished and exit
							monitor.done();
							return;
						}

						// select next context
						Match context = this.mc.getAcs().selectNextCandidate();		

						// check if this mutation has already been
						// performed before
						if(!this.mm.mutationHasBeenUsedBefore(operator, context)){

							// Instantiate Copier
							Copier copier = new Copier();

							// Copy input model
							Resource targetModel = initTargetModel(inputModel, currentOrder, mutantNumber, copier);

							// Create a graph
							EGraph graph = new EGraphImpl(targetModel);

							// Get Rule
							Rule rule = (Rule) operator.getExecuteModule().getUnits().get(0);

							// Create an engine and a rule application:
							Engine engine = new EngineImpl();		
							RuleApplication ra = new RuleApplicationImpl(engine);
							ra.setEGraph(graph);
							ra.setRule(rule);						

							// find corresponding context in copied model
							// and adapt references
							Match adaptedContext = adaptContext(copier, context, rule);				

							// Use adapted context for execution
							ra.setCompleteMatch(adaptedContext);

							// Apply transformation
							boolean result = ra.execute(null);

							//TODO Validation
							// Validate the mutated model					

							if (result) {
								// Remember mutation
								Mutation mutation = new Mutation(inputModel, targetModel,
										copier, operator, adaptedContext);
								this.mm.addMutation(currentOrder, mutation);

								// Save mutant
								try {
									targetModel.save(Collections.EMPTY_MAP);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								LogUtil.log(LogEvent.NOTICE, "- Created Mutant " + MutationUtil.getResourceName(targetModel) + " -");
								LogUtil.log(LogEvent.DEBUG, mutation);
								LogUtil.log(LogEvent.DEBUG, "Mutation Sequence: " + this.mm.printMutationSequence(targetModel));

								mutantNumber++;
							}
						}
					}
				}		
				
				spmm.done();
				
			}

			currentOrder++;
			spm.done();
		}
		StatisticsUtil.getInstance().stop("Mutation");
		
		LogUtil.log(LogEvent.NOTICE, "Finished Mutation. Generated " + this.mm.getAllMutations().size() + " Mutant(s)(" +
				StatisticsUtil.getInstance().getTime("Mutation") + "ms)");
		monitor.done();
	}

	/**
	 * Computes the possible contexts of given edit rule.
	 */
	private LinkedList<Match> computeContexts(Resource inputModel, EditRule editRule){

		LinkedList<Match> contexts = new LinkedList<Match>();

		// Create a graph
		EGraph graph = new EGraphImpl(inputModel);

		// Create an engine and a rule application:
		Engine engine = new EngineImpl();		
		RuleApplication ra = new RuleApplicationImpl(engine);
		ra.setEGraph(graph);
		ra.setRule((Rule) editRule.getExecuteModule().getUnits().get(0));		

		Iterable<Match> matches = engine.findMatches((Rule)editRule.getExecuteModule().getUnits().get(0), graph, null);
		for(Match context : matches){
			contexts.add(context);			
		}

		return contexts;

	}

	/**
	 * Creates a copy of the input model. We refer to this copy as target model.
	 * @return 
	 */
	private Resource initTargetModel(Resource inputModel, int currenOrder, int mutantNumber, Copier copier) {
		// Calculate file names
		String targetFile = inputModel.getURI().toFileString();
		if(targetFile == null)
			targetFile = EMFStorage.uriToPath(inputModel.getURI());
		String folder = targetFile.substring(0, targetFile.lastIndexOf(File.separator));
		String mutantFolder = "Mutants_" + mc.getName();
		String orderFolder = "Order_" + currenOrder;
		String file = targetFile.substring(targetFile.lastIndexOf(File.separator) + 1, targetFile.length());
		String baseName = file.substring(0, file.lastIndexOf("."));
		
		//Use only one flat mutant folder
		if(folder.contains(mutantFolder)){
			mutantFolder = "";
		}
		
		//Use order folder just once
		if(folder.contains(orderFolder)){
			orderFolder = "";
		}
		
		String targetFileCopy = folder + File.separator + mutantFolder + File.separator +  orderFolder +
				File.separator+ baseName + "_M" +  mutantNumber +".featuremodel";		

		// do copy
		ResourceSet resourceSet = new ResourceSetImpl();
		URI uri = URI.createFileURI(targetFileCopy);
		Resource targetModel = resourceSet.createResource(uri);		
		copier.copyAll(inputModel.getContents());
		copier.copyReferences();
		for (EObject orig : inputModel.getContents()) {
			targetModel.getContents().add(copier.get(orig));
		}
		
		LogUtil.log(LogEvent.DEBUG, "Created copy of input model: " + targetFileCopy);
		
		return targetModel;
	}
	
	/**
	 * Adapts the given context references according to
	 * the information given by the copier. This is used to redirect
	 * references of an copied model.
	 * @param copier
	 * @param context
	 * @param rule
	 * @return
	 */
	private Match adaptContext(Copier copier, Match context, Rule rule){
		
		for(Node node : rule.getLhs().getNodes()){
			if(copier.containsKey(context.getNodeTarget(node))){				
				context.setNodeTarget(node, copier.get(context.getNodeTarget(node)));				
			}			
		}		
		return context;		
	}
	
	private void clearPreviousResults(){

		// Cleanup selections
		this.mc.getAcs().clearCandidates();
		this.mc.getAos().clearCandidates();
		this.mc.getAcs().clearResults();
		this.mc.getAos().clearResults();
		
		// Copy operator catalog to selection				
		Copier c = new Copier();
		c.copyAll(this.mc.getMutationOperators());
		c.copyReferences();
		
		LinkedList<EditRule> copiedOPs = new LinkedList<EditRule>(); 			
		for(EditRule operator : this.mc.getMutationOperators()){
			copiedOPs.add((EditRule) c.get(operator));				
		}
		
		// Re-Initialize operators
		this.mc.getAos().initializeCandidates(copiedOPs);
		
	}

	protected MutationConfig getMutationConfig() {
		return mc;
	}

}
