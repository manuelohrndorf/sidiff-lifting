package org.sidiff.difference.lifting.api;

import java.text.DecimalFormat;

import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.emf.exceptions.NoCorrespondencesException;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.lifting.api.settings.LiftingSettings.RecognitionEngineMode;
import org.sidiff.difference.lifting.postprocessing.PostProcessor;
import org.sidiff.difference.lifting.recognitionengine.ruleapplication.RecognitionEngine;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.util.DifferenceAnalysisUtil;
import org.sidiff.difference.technical.MergeImports;
import org.sidiff.difference.technical.api.TechnicalDifferenceFacade;
import org.sidiff.matcher.IMatcher;
import org.sidiff.matching.input.InputModels;

/**
 * Convenient access to lifting functions.
 */
public class LiftingFacade extends TechnicalDifferenceFacade {

	/**
	 * Symmetric difference file extension.
	 */
	public static final String SYMMETRIC_DIFF_EXT = "symmetric";
	
	/**
	 * The {@link RecognitionEngine}
	 */
	protected static RecognitionEngine recognitionEngine;

	private static DecimalFormat f = new DecimalFormat("#0.0");
	private static int scsCount = 0;
	private static int totalAtomicCount = 0;
	private static int uncoveredCount = 0;
	private static int coveredCount = 0;
	private static int correspondenceCount = 0;
	
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
		
		if(importMerger == null && settings.isEnabled_MergeImports()){
			importMerger = new MergeImports(symmetricDifference, settings.getScope(), true);
		}
		
		if(settings.isEnabled_MergeImports()){
			// Merge Imports
			LogUtil.log(LogEvent.NOTICE, "Merge imports");
			importMerger.merge();	
		}

		liftMeUp(symmetricDifference, settings);
		
		if(settings.isEnabled_MergeImports()){
			// Unmerge Imports
			LogUtil.log(LogEvent.NOTICE, "Umerge imports");
			importMerger.unmerge();
		}
		
		return symmetricDifference;
	}
	
	protected static void liftMeUp(SymmetricDifference symmetricDifference, LiftingSettings settings){
		// Start recognition engine
		LogUtil.log(LogEvent.NOTICE, "Recognize operations");
		recognitionEngine = new RecognitionEngine(symmetricDifference,
				importMerger.getImports(), settings.getScope(),
				settings.getRuleBases(), settings.isRuleSetReduction(),
				settings.isBuildGraphPerRule(), settings.getRrSorter(),
				settings.isUseThreadPool(), settings.getNumberOfThreads(),
				settings.getRulesPerThread(),
				settings.isCalculateEditRuleMatch(),
				settings.isSerializeEditRuleMatch());
		recognitionEngine.execute();

		// Postprocess
		if (settings.getRecognitionEngineMode() == RecognitionEngineMode.LIFTING_AND_POST_PROCESSING) {
			LogUtil.log(LogEvent.NOTICE, "Post processing");
			PostProcessor postProcessor = new PostProcessor(recognitionEngine);
			postProcessor.postProcess();
		}
		// Aggregate deleted subtrees
		// TODO: How to deal with deleted subtrees..?
		// SubtreeAggregator aggregator = new SubtreeAggregator();
		// aggregator.aggregate(symmetricDiff, SubtreeAggregator.CREATE,
		// SubtreeAggregator.DELETE);
		
		report(symmetricDifference);
		// // Reduce to relevant parts of the difference
		// TODO(TK 8.4.2014): Shall we introduce a global DEBUG flag?
		// boolean debug = true;
		// if (debug){
		// ModelReducer modelReducer = new ModelReducer(symmetricDiff);
		// modelReducer.reduce();
		// try {
		// modelA.save(null);
		// modelB.save(null);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }
		LogUtil.log(LogEvent.NOTICE, "Total semantic changes sets (|SCS|): " + scsCount);
		LogUtil.log(LogEvent.NOTICE, "Total atomic changes (|D|): " + totalAtomicCount);
		LogUtil.log(LogEvent.NOTICE, "Finally uncovered atomic changes: " + uncoveredCount);
		LogUtil.log(LogEvent.NOTICE, "Finally covered atomic changes: " + coveredCount);
		LogUtil.log(LogEvent.NOTICE, "Compression (|D|/|SCS|): " + f.format(((double) coveredCount / (double) scsCount)));
		LogUtil.log(LogEvent.NOTICE, "Correspondences: " + correspondenceCount);
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
		return liftTechnicalDifference(deriveTechnicalDifference(modelA, modelB, settings), settings);
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
		return liftTechnicalDifference(deriveTechnicalDifference(models, settings), settings);
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
		String documentType = EMFModelAccess.getCharacteristicDocumentType(modelA);
		LiftingSettings defaultSettings = new LiftingSettings(documentType);
		defaultSettings.setMatcher(matcher);
		// Lift difference
		SymmetricDifference symmetricDiff = liftTechnicalDifference(modelA, modelB, defaultSettings);

		return symmetricDiff;
	}

	private static void report(SymmetricDifference symmetricDiff) {
		// Print report
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
		LogUtil.log(LogEvent.NOTICE, "------------------------- FINISHED -------------------------");
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
		
		scsCount = symmetricDiff.getChangeSets().size();
		totalAtomicCount = symmetricDiff.getChanges().size();
		uncoveredCount = DifferenceAnalysisUtil.getRemainingChanges(symmetricDiff).size();
		coveredCount = totalAtomicCount - uncoveredCount;
		correspondenceCount = symmetricDiff.getMatching().getCorrespondences().size();

		f = new DecimalFormat("#0.0");

		LogUtil.log(LogEvent.NOTICE, "Total semantic changes sets (|SCS|): " + scsCount);
		LogUtil.log(LogEvent.NOTICE, "Total atomic changes (|D|): " + totalAtomicCount);
		LogUtil.log(LogEvent.NOTICE, "Finally uncovered atomic changes: " + uncoveredCount);
		LogUtil.log(LogEvent.NOTICE, "Finally covered atomic changes: " + coveredCount);
		LogUtil.log(LogEvent.NOTICE,
				"Compression (|D|/|SCS|): " + f.format(((double) coveredCount / (double) scsCount)));
		LogUtil.log(LogEvent.NOTICE, "Correspondences: " + correspondenceCount);
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
	public static void serializeLiftedDifference(SymmetricDifference symDiff, String path, String fileName) {
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
	public static SymmetricDifference loadLiftedDifference(String path) {
		return loadTechnicalDifference(path);
	}

	/**
	 * Loads an EMF resource.
	 * 
	 * @param path
	 *            The EMF-file path.
	 * @return The loaded EMF-object.
	 */
	public static Resource loadModel(String path) {
		return EMFStorage.eLoad(EMFStorage.pathToUri(path)).eResource();
	}
}
