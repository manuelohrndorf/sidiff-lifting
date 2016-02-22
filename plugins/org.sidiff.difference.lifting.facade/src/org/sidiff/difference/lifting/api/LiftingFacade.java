package org.sidiff.difference.lifting.api;

import java.text.DecimalFormat;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
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

/**
 * Convenient access to lifting functions.
 */
public class LiftingFacade extends TechnicalDifferenceFacade {

	/**
	 * Symmetric difference file extension.
	 */
	public static final String SYMMETRIC_DIFF_EXT = "symmetric";
	
	protected static RecognitionEngine recognitionEngine;


	
	/**
	 * Lifts a technical difference.
	 * 
	 * @param symmetricDifference
	 *            The technical difference to lift.
	 * @param settings
	 *            Specifies the settings of the lifting algorithm.
	 *            
	 * @return A lifted symmetric difference.
	 * 
	 * @see LiftingFacade#liftMeUp(Resource, Resource, IMatcher)
	 */
	public static SymmetricDifference liftMeUp(SymmetricDifference symmetricDifference, LiftingSettings settings) {
		
		LogUtil.log(LogEvent.NOTICE, settings.toString());
		
		// get console
		MessageConsoleStream mcStream = console.newMessageStream();
		mcStream.println(settings.toString());
		
		if(importMerger == null){
			importMerger = new MergeImports(symmetricDifference, settings.getScope(), true);
			
		}
		
		if(settings.isEnabled_MergeImports()){
			// Merge Imports
			importMerger.merge();
		}

		// Start recognition engine
		recognitionEngine = new RecognitionEngine(symmetricDifference, importMerger.getImports(), settings.getScope(), settings.getRuleBases(), settings.isRuleSetReduction(), settings.isBuildGraphPerRule(), settings.getRrSorter(), settings.isUseThreadPool(), settings.getNumberOfThreads(), settings.getRulesPerThread(), settings.isCalculateEditRuleMatch(), settings.isSerializeEditRuleMatch());
		recognitionEngine.execute();

		// Postprocess
		if (settings.getRecognitionEngineMode() == RecognitionEngineMode.LIFTING_AND_POST_PROCESSING) {
			PostProcessor postProcessor = new PostProcessor(recognitionEngine);
			postProcessor.postProcess();
		}
		// Aggregate deleted subtrees
		// TODO: How to deal with deleted subtrees..?
		// SubtreeAggregator aggregator = new SubtreeAggregator();
		// aggregator.aggregate(symmetricDiff, SubtreeAggregator.CREATE,
		// SubtreeAggregator.DELETE);
		if(settings.isEnabled_MergeImports()){
			// Unmerge Imports
			importMerger.unmerge();
		}

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

		return symmetricDifference;
	}
	
	/**
	 * Creates a lifted symmetric difference between model A and model B.
	 * 
	 * @param modelA
	 *            The earlier version of the model.
	 * @param modelB
	 *            The later version of the model.
	 * @param settings
	 *            Specifies the settings of the semantic lifting algorithm.
	 *            
	 * @return A lifted symmetric difference.
	 * 
	 * @see LiftingFacade#load(String)
	 * @see LiftingFacade#liftMeUp(Resource, Resource, IMatcher)
	 * 
	 * @throws InvalidModelException
	 */
	public static SymmetricDifference liftMeUp(Resource modelA, Resource modelB, LiftingSettings settings) throws InvalidModelException{
		return liftMeUp(deriveDifference(modelA, modelB, settings), settings);
	}
	
	/**
	 * Creates a lifted symmetric difference between model A and model B using
	 * default settings (see LiftingSettings).
	 * 
	 * @param modelA
	 *            The earlier version of the model.
	 * @param modelB
	 *            The later version of the model.
	 * @param matcher
	 *            The matcher which calculates the correspondences between model
	 *            A and modelB.
	 * @return A lifted symmetric difference.
	 * @see LiftingFacade#load(String)
	 * @see LiftingFacade#getAvailableMatchers(String)
	 * @see LiftingFacade#liftMeUp(Resource, Resource, LiftingSettings)
	 * @throws InvalidModelException
	 * @throws NoCorrespondencesException
	 */
	public static SymmetricDifference liftMeUp(Resource modelA, Resource modelB, IMatcher matcher)
			throws InvalidModelException, NoCorrespondencesException {
		// Create default settings
		String documentType = EMFModelAccess.getCharacteristicDocumentType(modelA);
		LiftingSettings defaultSettings = new LiftingSettings(documentType);
		defaultSettings.setMatcher(matcher);
		LogUtil.log(LogEvent.NOTICE, defaultSettings.toString());
		// Lift difference
		SymmetricDifference symmetricDiff = liftMeUp(modelA, modelB, defaultSettings);

		return symmetricDiff;
	}

	private static void report(SymmetricDifference symmetricDiff) {
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
		LogUtil.log(LogEvent.NOTICE,
				"Compression (|D|/|SCS|): " + f.format(((double) coveredCount / (double) scsCount)));
		LogUtil.log(LogEvent.NOTICE, "Correspondences: " + correspondenceCount);
	}

	/**
	 * Serializes a difference.
	 * 
	 * @param symDiff
	 *            The difference to serialize.
	 * @param path
	 *            The serialization path.
	 * @param fileName
	 *            The file name of the difference.
	 */
	public static void serializeDifference(SymmetricDifference symDiff, String path, String fileName) {
		if (!(path.endsWith("/") || path.endsWith("\\"))) {
			path = path + "/";
		}

		if (!(fileName.endsWith("." + LiftingFacade.SYMMETRIC_DIFF_EXT))) {
			fileName = fileName + "." + LiftingFacade.SYMMETRIC_DIFF_EXT;
		}

		EMFStorage.eSaveAs(EMFStorage.pathToUri(path + fileName), symDiff);
	}

	/**
	 * Load symmetric difference.
	 * 
	 * @param path
	 *            The path to the symmetric difference.
	 * @return The loaded symmetric difference.
	 */
	public static SymmetricDifference loadSymmetricDifference(String path) {
		return (SymmetricDifference) EMFStorage.eLoad(EMFStorage.pathToUri(path));
	}

	/**
	 * Load EMF resource.
	 * 
	 * @param path
	 *            The EMF-file path.
	 * @return The loaded EMF-object.
	 */
	public static Resource loadModel(String path) {
		return EMFStorage.eLoad(EMFStorage.pathToUri(path)).eResource();
	}
}
