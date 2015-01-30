package org.sidiff.difference.mutation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import org.sidiff.difference.rulebase.EditRule;

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
	 * Input models to use
	 */
	private List<Resource> inputModels;
	
	/**
	 * The result mutants
	 */
	private List<Resource> mutants;	
	
	/**
	 * Mapping between mutation order and generated mutants
	 */
	private Map<Integer,List<Resource>> orderToMutants;
	

	public Mutator(MutationConfig mc) {
		super();
		this.mc = mc;	
		this.mutants = new ArrayList<Resource>();
		this.inputModels = new ArrayList<Resource>();
		this.orderToMutants = new HashMap<Integer, List<Resource>>();
	}

	/**
	 * Peform the mutation.
	 * @throws Exception 
	 */
	public void mutate() throws Exception {
		LogUtil.log(LogEvent.NOTICE, "Running Mutator...");
		
		StatisticsUtil.getInstance().start("Mutation");
		
		int currentOrder = 1;

		while(currentOrder <= this.mc.getMutationOrder()){
			
			clearPreviousOrderResults();
			
			// Copy operator catalogue to selection
			Copier c = new Copier();
			c.copyAll(this.mc.getMutationOperators());
			c.copyReferences();
			
			LinkedList<EditRule> copiedOPs = new LinkedList<EditRule>(); 			
			for(EditRule operator : this.mc.getMutationOperators()){
				copiedOPs.add((EditRule) c.get(operator));				
			}
			
			this.mc.getAos().initializeCandidates(copiedOPs);
			
			LogUtil.log(LogEvent.NOTICE, "MutationOrder: " + currentOrder);			
					
			// Use input model in first iteration
			if(currentOrder==1){
				this.inputModels.add(this.mc.getTargetModel());
			}
			
			// Use result mutants from last iteration
			else{
				this.inputModels = this.orderToMutants.get(currentOrder-1);
			}
			
		
			for(Resource inputModel : this.inputModels){	

				LogUtil.log(LogEvent.NOTICE, "Selected Input Model: " + inputModel);			

				while(!this.mc.getAos().selectionCoverageReached()){
					EditRule editRule = this.mc.getAos().selectNextCandidate();

					//Compute all possible contexts
					LinkedList<Match> contexts = computeContexts(inputModel,editRule);
					this.mc.getAcs().initializeCandidates(contexts);
					int mutantNumber = 1;

					while(!this.mc.getAcs().selectionCoverageReached()){

						// Instantiate Copier
						Copier copier = new Copier();

						// Copy input model
						Resource targetModel = initTargetModel(inputModel, currentOrder, mutantNumber, copier);

						// Create a graph
						EGraph graph = new EGraphImpl(targetModel);

						// Get Rule
						Rule rule = (Rule) editRule.getExecuteModule().getUnits().get(0);

						// Create an engine and a rule application:
						Engine engine = new EngineImpl();		
						RuleApplication ra = new RuleApplicationImpl(engine);
						ra.setEGraph(graph);
						ra.setRule(rule);						

						// select next context
						Match context = this.mc.getAcs().selectNextCandidate();		

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
							try {
								//Save mutant
								targetModel.save(Collections.EMPTY_MAP);
								LogUtil.log(LogEvent.NOTICE, "Created Mutant " + targetModel.toString());

								//Remember mutant
								this.mutants.add(targetModel);

								mutantNumber++;

							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}		
			}

			this.orderToMutants.put(currentOrder, new ArrayList<Resource>(mutants));
			currentOrder++;
		}
		StatisticsUtil.getInstance().stop("Mutation");
		
		int numberOfGeneratedMutants = 0;
		for(int order :this.orderToMutants.keySet()){
			numberOfGeneratedMutants += this.orderToMutants.get(order).size();
		}
		LogUtil.log(LogEvent.NOTICE, "Finished Mutation. Generated " + numberOfGeneratedMutants + " Mutants(" +
				StatisticsUtil.getInstance().getTime("Mutation") + "ms)");


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
		String folder = targetFile.substring(0, targetFile.lastIndexOf(File.separator));
		String file = targetFile.substring(targetFile.lastIndexOf(File.separator) + 1, targetFile.length());
		String baseName = file.substring(0, file.lastIndexOf("."));
		String targetFileCopy = folder + File.separator + "Mutants" + File.separator + baseName + mutantNumber + "_" + mc.getName()
				+ ".featuremodel";		

		// do copy
		ResourceSet resourceSet = new ResourceSetImpl();
		URI uri = URI.createFileURI(targetFileCopy);
		Resource targetModel = resourceSet.createResource(uri);		
		copier.copyAll(inputModel.getContents());
		copier.copyReferences();
		for (EObject orig : inputModel.getContents()) {
			targetModel.getContents().add(copier.get(orig));
		}
		
		LogUtil.log(LogEvent.NOTICE, "Created copy of input model: " + targetFileCopy);
		
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
	
	/**
	 * Convenient method for cleaning up results of
	 * a previous mutation order.
	 */
	private void clearPreviousOrderResults(){
		
		// Cleanup old lists
		this.mutants.clear();
		this.inputModels.clear();
		
		// Cleanup selections
		this.mc.getAcs().clearCandidates();
		this.mc.getAos().clearCandidates();
		this.mc.getAcs().clearResults();
		this.mc.getAos().clearResults();
		
		// Re-Initialize operators
		this.mc.getAos().initializeCandidates(this.mc.getMutationOperators());
	}

	protected MutationConfig getMutationConfig() {
		return mc;
	}

}
