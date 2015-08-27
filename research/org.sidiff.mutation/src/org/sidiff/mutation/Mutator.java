package org.sidiff.mutation;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.impl.RuleApplicationImpl;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.sidiff.common.emf.EMFValidate;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.common.util.StatisticsUtil;
import org.sidiff.difference.matcher.copier.CopierMatcher;
import org.sidiff.difference.matcher.util.MatcherUtil;
import org.sidiff.difference.rulebase.EditRule;
import org.sidiff.difference.rulebase.Parameter;
import org.sidiff.difference.rulebase.ParameterDirection;
import org.sidiff.difference.rulebase.ParameterKind;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.technical.ITechnicalDifferenceBuilder;
import org.sidiff.difference.technical.MergeImports;
import org.sidiff.difference.technical.util.TechnicalDifferenceBuilderUtil;
import org.sidiff.mutation.config.MutationConfig;
import org.sidiff.mutation.util.MutationUtil;
import org.silift.common.util.access.EMFModelAccessEx;
import org.silift.common.util.emf.EMFStorage;
import org.silift.common.util.emf.Scope;

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
		this.mm = new MutationManagement(this.mc.getTargetModel());
		
		LogUtil.log(LogEvent.NOTICE, this.mc);

	}
	
	/**
	 * Returns all generated mutants, which have been created by this Mutator.
	 * @return resources, which have been generated
	 */
	public Set<Resource> getGeneratedMutants(){
		return this.mm.getAllMutants();
	}

	/**
	 * Peform the mutation.
	 * @throws Exception 
	 */
	public void mutate(IProgressMonitor monitor){
				
		LogUtil.log(LogEvent.NOTICE, "Running Mutator...");		
		
		StatisticsUtil.getInstance().start("Mutation");
		
		int currentOrder = 1;

		monitor.beginTask("Mutating Model(s)" ,this.mc.getMutationOrder());		

		while(currentOrder <= this.mc.getMutationOrder()){				
			
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
		
			SubProgressMonitor spm1 = new SubProgressMonitor(monitor, 1);			
			spm1.beginTask("Mutation Order " + currentOrder, this.mm.getInputModels(currentOrder).size());
			
			for(Resource inputModel : this.mm.getInputModels(currentOrder)){				
				
				clearPreviousResults();
				
				LogUtil.log(LogEvent.NOTICE, "###### Selected Input Model: " + MutationUtil.getResourceName(inputModel) + "######");			
				
				SubProgressMonitor spm2 = new SubProgressMonitor(spm1, 1);				
				
				spm2.beginTask("Mutating Model " + MutationUtil.getResourceName(inputModel), this.mc.getAos().getNumberToBeSelected());
				
				int mutantNumber = 1;
												
				while(!this.mc.getAos().selectionCoverageReached()){					
					
					// select next operator
					EditRule operator = this.mc.getAos().selectNextCandidate();

					//Compute all possible contexts
					LinkedList<Match> contexts = computeContexts(inputModel,operator);
					this.mc.getAcs().initializeCandidates(contexts);					

					SubProgressMonitor spm3 = new SubProgressMonitor(spm2, 1);
					
					spm3.beginTask("Mutation Operator " + operator.getExecuteModule().getName(),this.mc.getAcs().getNumberToBeSelected());

					while(!this.mc.getAcs().selectionCoverageReached()){
						
						// check for user cancel request
						if(monitor.isCanceled()){
							//Mark finished and exit
							monitor.done();
							throw new OperationCanceledException();
						}

						// select next context
						Match context = this.mc.getAcs().selectNextCandidate();						

						// Check beforehand if the resulting mutant has already been
						// created before
						if(!this.mm.mutantHasBeenCreatedBefore(inputModel, operator, context, currentOrder)){

							// Instantiate Copier
							Copier copier = new Copier();

							// Copy input model
							Resource targetModel = initTargetModel(inputModel, currentOrder, mutantNumber, copier);

							// Get Rule
							Rule rule = (Rule) operator.getExecuteModule().getUnits().get(0);
							
							// Create a graph efficiently:
							// only add needed elements (for mutation)
							EGraph graph = new EGraphImpl(context.getNodeTargets());

							// Create an engine and a rule application:
							Engine engine = new EngineImpl();		
							RuleApplication ra = new RuleApplicationImpl(engine);
							ra.setEGraph(graph);
							ra.setRule(rule);						

							// find corresponding context in copied model
							// and adapt references
							MutationUtil.adaptContext(copier, context, rule);								

							// Use adapted context for execution
							ra.setCompleteMatch(context);							
												
							// Apply transformation
							boolean result = ra.execute(null);							

							// Validate the mutated model
							if(this.mc.isValidateMutants()){
								try {
									EMFValidate.validateModel(targetModel);
								} catch (InvalidModelException e1) {								
									System.err.println(e1.getMessage());
									// Do not write invalid model later on
									result = false;
								}
							}

							if (result) {								
								
								Mutation mutation = new Mutation(inputModel, targetModel,
										copier, operator, context);
								
								LogUtil.log(LogEvent.DEBUG, "Derive Difference...");
								SymmetricDifference sd = deriveDifference(mutation);								
								LogUtil.log(LogEvent.DEBUG, "Derived Difference.");
								
								//Only proceed iff mutation has introduced changes at all
								if(sd.getChanges().size() > 0){

									// Remember mutation
									this.mm.addMutation(currentOrder, mutation);

									// Save mutant and difference
									try {
										targetModel.save(Collections.EMPTY_MAP);
										saveDifference(mutation, sd);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();

									}



									monitor.subTask("Created Mutant " + MutationUtil.getResourceName(targetModel));
									LogUtil.log(LogEvent.NOTICE, "- Created Mutant " + MutationUtil.getResourceName(targetModel) + " -");
									LogUtil.log(LogEvent.DEBUG, mutation);
									LogUtil.log(LogEvent.DEBUG, "Mutation Sequence: " + this.mm.printMutationSequence(targetModel));							


									mutantNumber++;
									spm3.worked(1);
								}
								
								else{
									LogUtil.log(LogEvent.DEBUG, "Difference is empty, Mutation did"
											+ "not introduce any changes!");							

								}
								
							}
							
							releaseAdapters(graph);	

						}
					}
					spm3.done();
				}	
				spm2.done();
			}
			spm1.done();
			currentOrder++;
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
		
		Rule rule = null;
		
		for(Unit unit : editRule.getExecuteModule().getUnits()){
			if(unit instanceof Rule)
				rule = (Rule)unit;
			break;

		}	

			
		// Abort if no executable rule could be found in units
		if(rule == null){			
			System.err.println("No Rule for application found!");
			return new LinkedList<Match>();		
		}
		
		
		ra.setRule(rule);		
	
		// Get all creation attributes, they are relevant
		// for initializing value parameters
		List<Attribute> paList = HenshinRuleAnalysisUtilEx.getPreservedAttributes(ra.getRule());
	
		//TODO this has to make use of a "catalogue" somehow, which
		// is valid in the current context(type), e.g. a class name must
		// be well-formed.
		
		
		// Initialize value parameter in RHS with default values
		// as they are irrelevant in this scenario
		for(Parameter para : editRule.getParameters()){
			// If in- and value parameter
			if (para.getDirection() == ParameterDirection.IN &&
					para.getKind() == ParameterKind.VALUE){

				String paramName = para.getName();
				
				// Check whether this parameter is used for matching.
				// We will not set parameters which are meant for 
				// searching (=LHS attributes).
				boolean isLHSParameter = false;
				
				for(Attribute pa : paList ){
					if(pa.getValue().equals(paramName)){
						isLHSParameter = true;
						break;
					}						
				}
				
				if(!isLHSParameter){
					EClassifier paramType = para.getType();									
					Object defaultValue = paramType.getDefaultValue();				

					// If default value is defined use this
					if(defaultValue != null){
						ra.setParameterValue(paramName, defaultValue);
					}

					// Use just a sequential number otherwise
					else{
						ra.setParameterValue(paramName, this.mm.getCurrentInputParameter());
					}
				}
			}
		}

		Iterable<Match> matches = engine.findMatches((Rule)editRule.getExecuteModule().getUnits().get(0), graph, ra.getPartialMatch());
		for(Match context : matches){
			contexts.add(context);			
		}
		
		//Clean up
		releaseAdapters(graph);	
		
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
		String mutantFolder = "Mutants";
		String orderFolder = "";
		if(mc.getMutationOrder() > 1)
			orderFolder = "Order" + currenOrder;
		String file = targetFile.substring(targetFile.lastIndexOf(File.separator) + 1, targetFile.length());
		String baseName = file.substring(0, file.lastIndexOf("."));
		String ending = file.substring(file.lastIndexOf("."), file.length());
		
		//Use only one flat mutant folder
		if(folder.contains(mutantFolder)){
			mutantFolder = "";
		}
		
		//Use order folder just once
		if(folder.contains(orderFolder)){
			orderFolder = "";
		}
		
		String targetFileCopy = folder + File.separator + mutantFolder ;
		
		if(orderFolder != "")
			targetFileCopy +=  File.separator +  orderFolder; 
		
		targetFileCopy += File.separator + baseName + "_M" +  mutantNumber + ending;		

		// do copy
		ResourceSet resourceSet = new ResourceSetImpl();		
		URI uri = EMFStorage.pathToUri(targetFileCopy);
		Resource targetModel = resourceSet.createResource(uri);		
		copier.copyAll(inputModel.getContents());
		copier.copyReferences();
		for (EObject orig : inputModel.getContents()) {
			targetModel.getContents().add(copier.get(orig));
		}
		
		LogUtil.log(LogEvent.DEBUG, "Created copy of input model: " + targetFileCopy);
		
		return targetModel;
	}
	
	private SymmetricDifference deriveDifference(Mutation mutation){		
		
		//Resolve all
		EcoreUtil.resolveAll(mutation.getInputModel().getResourceSet());
		EcoreUtil.resolveAll(mutation.getMutant().getResourceSet());
		
		//Create Matching
		CopierMatcher cmatcher =(CopierMatcher)MatcherUtil.getMatcherByKey("Copier", mutation.getInputModel(), mutation.getMutant());
		cmatcher.setCopier(mutation.getCopier());
		SymmetricDifference sd = cmatcher.createMatching(mutation.getInputModel(),  mutation.getMutant(),
				Scope.RESOURCE_SET, false);
		
		
		// Merge Imports
		MergeImports importMerger = new MergeImports(sd, Scope.RESOURCE_SET, true);
		importMerger.merge();		
		
		//Derive Technical Difference
		ITechnicalDifferenceBuilder tdb = TechnicalDifferenceBuilderUtil.getDefaultTechnicalDifferenceBuilder(
				EMFModelAccessEx.getCharacteristicDocumentType(mutation.getInputModel()));
		tdb.deriveTechDiff(sd, Scope.RESOURCE_SET);
		
		// Unmerge Imports
		importMerger.unmerge();
		
		
		return sd;

	}
	
	
	private void saveDifference(Mutation mutation, SymmetricDifference sd) throws IOException{
		
		//Save Matching Model	
		String mutantFile = mutation.getMutant().getURI().toFileString();
		if(mutantFile == null)
			mutantFile = EMFStorage.uriToPath(mutation.getMutant().getURI());		

		String folder = mutantFile.substring(0, mutantFile.lastIndexOf(File.separator));
		String inputName = MutationUtil.getResourceName(mutation.getInputModel());
		String mutantName = MutationUtil.getResourceName(mutation.getMutant());

		String matchingFile = folder + File.separator + inputName + "-" + mutantName + ".symmetric";

		// do copy
		ResourceSet resourceSet = new ResourceSetImpl();
		URI uri = URI.createFileURI(matchingFile);
		Resource targetModel = resourceSet.createResource(uri);	
		targetModel.getContents().add(sd);
		targetModel.save(Collections.EMPTY_MAP);
		}

	/**
	 * Clean up method
	 */
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
	
	/**
	 * Helper method for clearing adapters and memory leaks in EGraph
	 * 
	 * @param graph
	 *            The EGraph to clear of Adapters
	 */
	private void releaseAdapters(EGraph graph) {

		try {
			// Clear memory from unused EObjects
			// If not done, execution time is exponential
			for (EObject roots : graph.getRoots()) {

				graph.removeGraph(roots);
			}

		} catch (Exception e) {

			// Nothing to do here
			// Just catching exceptions
			// of deleting cross references

		}
	}

}
