package org.sidiff.difference.lifting.api;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.emf.exceptions.NoCorrespondencesException;
import org.sidiff.common.emf.input.InputModels;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.correspondences.ICorrespondences;
import org.sidiff.correspondences.matchingmodel.MatchingModelCorrespondences;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.lifting.api.settings.RecognitionEngineMode;
import org.sidiff.difference.lifting.api.util.PipelineUtils;
import org.sidiff.difference.lifting.postprocessing.PostProcessor;
import org.sidiff.difference.lifting.recognitionengine.util.RecognitionEngineUtil;
import org.sidiff.difference.lifting.recognitionengine.util.SubtreeAggregator;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.util.ChangeSetPriorityComparator;
import org.sidiff.difference.symmetric.util.DifferenceAnalysisUtil;
import org.sidiff.difference.symmetric.util.debug.ModelReducer;
import org.sidiff.difference.technical.api.TechnicalDifferenceFacade;
import org.sidiff.matcher.IMatcher;

/**
 * Convenient access to lifting functions.
 */
public class LiftingFacade extends TechnicalDifferenceFacade {

	/**
	 * Symmetric difference file extension.
	 */
	public static final String SYMMETRIC_DIFF_EXT = "symmetric";
	
	/**
	 * Experimental flag for the lifting process.
	 */
	private static final boolean TEST_MODEL_REDUCER = false;
	
	/**
	 * Experimental flag for the lifting process.
	 */
	private static final boolean TEST_SUB_TREE_AGGREGATION = false;
	
	protected static void liftMeUp(SymmetricDifference symmetricDifference, LiftingSettings settings) {
		
		// Start recognition engine
		if (settings.getRecognitionEngineMode() != RecognitionEngineMode.NO_LIFTING) {
			LogUtil.log(LogEvent.NOTICE, "Recognize operations");

			if (settings.getRecognitionEngine() == null) {
				settings.setRecognitionEngine(RecognitionEngineUtil.getDefaultRecognitionEngine(
						PipelineUtils.createRecognitionEngineSetup(settings)));
			}
			
			settings.getRecognitionEngine().getSetup().setDifference(symmetricDifference);
			settings.getRecognitionEngine().getSetup().setImports(
					(settings.getImports() != null) ? settings.getImports().getImports() : null);

			settings.getRecognitionEngine().execute();
		}

		// Postprocess
		if (settings.getRecognitionEngineMode() == RecognitionEngineMode.LIFTING_AND_POST_PROCESSING) {
			LogUtil.log(LogEvent.NOTICE, "Post processing");
			
			if (settings.getComparator() == null){
				settings.setComparator(new ChangeSetPriorityComparator());
			}
			
			PostProcessor postProcessor = new PostProcessor(settings.getRecognitionEngine());
			postProcessor.setCsPrioComparator(settings.getComparator());
			postProcessor.postProcess();
		}
		
		// Aggregate deleted subtrees
		if (TEST_SUB_TREE_AGGREGATION) {
			// TODO: How to deal with deleted subtrees..?
			SubtreeAggregator aggregator = new SubtreeAggregator();
			aggregator.aggregate(symmetricDifference, SubtreeAggregator.CREATE, SubtreeAggregator.DELETE);
		}
		
		report(symmetricDifference);
		
		// Reduce to relevant parts of the difference
		if (TEST_MODEL_REDUCER){
			ModelReducer modelReducer = new ModelReducer(symmetricDifference);
			modelReducer.reduce();
			
			try {
				symmetricDifference.getModelA().save(null);
				symmetricDifference.getModelB().save(null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Lifts a technical {@link SymmetricDifference}.
	 * 
	 * @param symmetricDifference
	 *            The technical difference to be lifted.
	 * @param settings
	 *            Specifies the settings of the lifting algorithm.
	 *            
	 * @return A lifted {@link SymmetricDifference}.
	 * 
	 * @see LiftingFacade#liftTechnicalDifference(Resource, Resource, IMatcher)
	 */
	public static SymmetricDifference liftTechnicalDifference(SymmetricDifference symmetricDifference, LiftingSettings settings) {
		LogUtil.log(LogEvent.NOTICE, settings.toString());
		
		// Merge Imports
		mergeImports(symmetricDifference, settings);
		
		// Lifting
		liftMeUp(symmetricDifference, settings);
		
		// Unmerge imports
		unmergeImports(settings);
		
		return symmetricDifference;
	}
	
	/**
	 * Computes a lifted {@link SymmetricDifference} between two models.
	 * Although, a symmetric difference is usually undirected the underlying
	 * difference model of SiLift implies a direction.
	 * 
	 * @param modelA
	 *            The origin model.
	 * @param modelB
	 *            The modified model.
	 * @param settings
	 *            Specifies the settings of the semantic lifting algorithm.
	 * 
	 * @return A lifted {@link SymmetricDifference}.
	 * 
	 * @see TechnicalDifferenceFacade#deriveTechnicalDifference(Resource, Resource, org.sidiff.difference.technical.api.settings.DifferenceSettings)
	 * @see LiftingFacade#liftTechnicalDifference(SymmetricDifference, LiftingSettings)
	 * 
	 * @throws InvalidModelException
	 * @throws NoCorrespondencesException 
	 */
	public static SymmetricDifference liftTechnicalDifference(Resource modelA, Resource modelB, LiftingSettings settings) throws InvalidModelException, NoCorrespondencesException{
		
		// Set SiLift default Correspondence-Service:
		settings.setCorrespondencesService(ICorrespondences.MANAGER.getExtension(MatchingModelCorrespondences.class).get());
		
		// Calculate model difference:
		settings.setUnmergeImports(false); // Do not unmerge imports until lifting is done...
		SymmetricDifference symmetricDifference = deriveTechnicalDifference(modelA, modelB, settings);
		settings.setUnmergeImports(true);
		
		// Lift model difference:
		return liftTechnicalDifference(symmetricDifference, settings);
	}
	
	/**
	 * Computes a lifted {@link SymmetricDifference} between two models.
	 * Although, a symmetric difference is usually undirected the underlying
	 * difference model of SiLift implies a direction.
	 * 
	 * @param models
	 *            The origin and modified model.
	 * @param settings
	 *            Specifies the settings of the semantic lifting algorithm.
	 *            
	 * @return A lifted {@link SymmetricDifference}.
	 * 
	 * @see TechnicalDifferenceFacade#deriveTechnicalDifference(InputModels, org.sidiff.difference.technical.api.settings.DifferenceSettings)
	 * @see LiftingFacade#liftTechnicalDifference(SymmetricDifference, LiftingSettings)
	 * 
	 * @throws InvalidModelException
	 * @throws NoCorrespondencesException
	 */
	public static SymmetricDifference liftTechnicDifference(InputModels models, LiftingSettings settings) throws InvalidModelException, NoCorrespondencesException{
		ArrayList<Resource> resources = new ArrayList<Resource>(models.getResources());
		return liftTechnicalDifference(resources.get(0), resources.get(1), settings);
	}
	
	/**
	 * Computes a lifted {@link SymmetricDifference} between two models using
	 * default settings (see LiftingSettings).Although, a symmetric difference
	 * is usually undirected the underlying difference model of SiLift implies a
	 * direction.
	 * 
	 * @param modelA
	 *            The origin model.
	 * @param modelB
	 *            The modified model.
	 * @param matcher
	 *            The {@link IMatcher} which calculates the correspondences
	 *            between model A and modelB.
	 *            
	 * @return A lifted {@link SymmetricDifference}.
	 * 
	 * @see LiftingFacade#liftTechnicalDifference(Resource, Resource,
	 *      LiftingSettings)
	 *      
	 * @throws InvalidModelException
	 * @throws NoCorrespondencesException
	 */
	public static SymmetricDifference liftTechnicalDifference(Resource modelA, Resource modelB, IMatcher matcher)
			throws InvalidModelException, NoCorrespondencesException {
		
		// Create default settings
		Set<String> documentTypes = EMFModelAccess.getDocumentTypes(Arrays.asList(modelA,modelB));
		LiftingSettings defaultSettings = new LiftingSettings();
		defaultSettings.setMatcher(matcher);
		defaultSettings.initDefaults(documentTypes);
		
		// Lift difference
		SymmetricDifference symmetricDiff = liftTechnicalDifference(modelA, modelB, defaultSettings);

		return symmetricDiff;
	}

	/**
	 * Serializes a lifted {@link SymmetricDifference}.
	 * 
	 * @param symDiff
	 *            The difference to serialize.
	 * @param path
	 *            The serialization path.
	 * @param fileName
	 *            The file name of the difference.
	 *            
	 * @see TechnicalDifferenceFacade#serializeTechnicalDifference(SymmetricDifference, String, String)
	 */
	public static void serializeLiftedDifference(SymmetricDifference symDiff, URI path, String fileName) {
		TechnicalDifferenceFacade.serializeTechnicalDifference(symDiff, path, fileName);
	}

	/**
	 * Loads a lifted {@link SymmetricDifference}.
	 * 
	 * @param path
	 *            The path to the symmetric difference.
	 * @return The loaded lifted {@link SymmetricDifference}.
	 * 
	 * @see TechnicalDifferenceFacade#loadTechnicalDifference(String)
	 */
	public static SymmetricDifference loadLiftedDifference(URI path) {
		return loadTechnicalDifference(path);
	}
	
	protected static void report(SymmetricDifference symmetricDiff) {
		// Print report
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
		LogUtil.log(LogEvent.NOTICE, "------------------------- FINISHED -------------------------");
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
		
		int scsCount = symmetricDiff.getChangeSets().size();
		int totalAtomicCount = symmetricDiff.getChanges().size();
		int uncoveredCount = DifferenceAnalysisUtil.getRemainingChanges(symmetricDiff).size();
		int coveredCount = totalAtomicCount - uncoveredCount;
		int correspondenceCount = symmetricDiff.getMatching().getCorrespondences().size();

		DecimalFormat f = new DecimalFormat("#0.0");

		LogUtil.log(LogEvent.NOTICE, "Total semantic changes sets (|SCS|): " + scsCount);
		LogUtil.log(LogEvent.NOTICE, "Total atomic changes (|D|): " + totalAtomicCount);
		LogUtil.log(LogEvent.NOTICE, "Finally uncovered atomic changes: " + uncoveredCount);
		LogUtil.log(LogEvent.NOTICE, "Finally covered atomic changes: " + coveredCount);
		LogUtil.log(LogEvent.NOTICE, "Compression (|D|/|SCS|): " + f.format(((double) coveredCount / (double) scsCount)));
		LogUtil.log(LogEvent.NOTICE, "Correspondences: " + correspondenceCount);
	}
}
