package org.sidiff.difference.lifting.facade;

import java.text.DecimalFormat;
import java.util.Arrays;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sidiff.common.emf.EMFValidate;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.emf.exceptions.NoCorrespondencesException;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.correspondences.ICorrespondences;
import org.sidiff.correspondences.matchingmodel.MatchingModelCorrespondences;
import org.sidiff.difference.lifting.facade.util.PipelineUtils;
import org.sidiff.difference.lifting.postprocessing.PostProcessor;
import org.sidiff.difference.lifting.recognitionengine.ruleapplication.RecognitionEngine;
import org.sidiff.difference.lifting.settings.LiftingSettings;
import org.sidiff.difference.lifting.settings.LiftingSettings.RecognitionEngineMode;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.SymmetricFactory;
import org.sidiff.difference.symmetric.util.DifferenceAnalysisUtil;
import org.sidiff.difference.technical.ITechnicalDifferenceBuilder;
import org.sidiff.difference.technical.MergeImports;
import org.sidiff.matcher.IMatcher;
import org.sidiff.matching.model.Matching;

/**
 * Convenient access to lifting functions.
 */
public class LiftingFacade extends PipelineUtils {

	/**
	 * Symmetric difference file extension.
	 */
	public static final String SYMMETRIC_DIFF_EXT = "symmetric";

	/**
	 * Lifts a technical difference.
	 * 
	 * @param symmetricDifference
	 *            The technical difference to lift.
	 * @param settings
	 *            Specifies the settings of the lifting algorithm.
	 * @return A lifted symmetric difference.
	 * @see LiftingFacade#liftMeUp(Resource, Resource, IMatcher)
	 */
	public static SymmetricDifference liftMeUp(SymmetricDifference symmetricDifference, LiftingSettings settings) {
		LogUtil.log(LogEvent.NOTICE, settings.toString());
		

		// Merge Imports
		MergeImports importMerger = new MergeImports(symmetricDifference, settings.getScope(), true);
		importMerger.merge();

		// Start recognition engine
		RecognitionEngine engine = new RecognitionEngine(symmetricDifference, importMerger.getImports(), settings);
		engine.execute();

		// Postprocess
		if (settings.getRecognitionEngineMode() == RecognitionEngineMode.LIFTING_AND_POST_PROCESSING) {
			PostProcessor postProcessor = new PostProcessor(engine);
			postProcessor.postProcess();
		}

		// Unmerge Imports
		importMerger.unmerge();

		report(symmetricDifference);

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
	 * @return A lifted symmetric difference.
	 * @see LiftingFacade#load(String)
	 * @see LiftingFacade#liftMeUp(Resource, Resource, IMatcher)
	 * @throws InvalidModelException
	 * @throws NoCorrespondencesException
	 */
	public static SymmetricDifference liftMeUp(Resource modelA, Resource modelB, LiftingSettings settings)
			throws InvalidModelException, NoCorrespondencesException {
		LogUtil.log(LogEvent.NOTICE, settings.toString());
		// Be sure proxy resolution is done
		EcoreUtil.resolveAll(modelA.getResourceSet());
		EcoreUtil.resolveAll(modelB.getResourceSet());

		// Validate models
		if (settings.isValidate()) {
			EMFValidate.validateObject(modelA.getContents().get(0), modelB.getContents().get(0));
		}

		// Create matching
		IMatcher matcher = settings.getMatcher();		
		matcher.startMatching(Arrays.asList(modelA, modelB), settings.getScope());	

		// Get Matching
		// In SiLift we assume support of @see{MatchingModelCorrespondences}
		ICorrespondences correspondences = matcher.getCorrespondencesService();
		Matching matching = ((MatchingModelCorrespondences)correspondences).getMatching();	
	
		if (!(matching.getCorrespondences().size() != 0)) {
			throw new NoCorrespondencesException();
		}
		
		//Contain Matching in Difference
		SymmetricDifference symmetricDiff = SymmetricFactory.eINSTANCE.createSymmetricDifference();
		symmetricDiff.setMatching(matching);


		// Merge Imports
		MergeImports importMerger = new MergeImports(symmetricDiff, settings.getScope(), false);
		importMerger.merge();

		// Derive technical difference
		ITechnicalDifferenceBuilder diffBuilder = settings.getTechBuilder();
		diffBuilder.deriveTechDiff(symmetricDiff, settings.getScope());

		if (settings.getRecognitionEngineMode() != RecognitionEngineMode.NO_LIFTING) {
			// Start recognition engine
			RecognitionEngine engine = new RecognitionEngine(symmetricDiff, importMerger.getImports(), settings);
			engine.execute();

			// Postprocess
			if (settings.getRecognitionEngineMode() == RecognitionEngineMode.LIFTING_AND_POST_PROCESSING) {
				PostProcessor postProcessor = new PostProcessor(engine);
				postProcessor.postProcess();
			}

			// Aggregate deleted subtrees
			// TODO: How to deal with deleted subtrees..?
//			 SubtreeAggregator aggregator = new SubtreeAggregator();
//			 aggregator.aggregate(symmetricDiff, SubtreeAggregator.CREATE, SubtreeAggregator.DELETE);
		}

		// Unmerge Imports
		importMerger.unmerge();

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

		report(symmetricDiff);

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

	/**
	 * Derives the technical differences from a given matching.
	 * 
	 * @param difference
	 *            the difference (consisting of correspondences only)
	 * @param tdBuilder
	 *            the technical difference builder that shall be used
	 * 
	 * @return the (technical) symmetric difference derived from comparing
	 *         modelA and modelB with matcher.
	 * @throws NoCorrespondencesException 
	 */
	public static SymmetricDifference deriveTechnicalDifferences(Resource modelA, Resource modelB, Scope scope,
			IMatcher matcher, ITechnicalDifferenceBuilder tdBuilder) throws NoCorrespondencesException {

		// Be sure proxy resolution is done
		EcoreUtil.resolveAll(modelA.getResourceSet());
		EcoreUtil.resolveAll(modelB.getResourceSet());

		// Create matching
		matcher.startMatching(Arrays.asList(modelA, modelB), scope);	

		// Get Matching
		// In SiLift we assume support of @see{MatchingModelCorrespondences}
		MatchingModelCorrespondences mmc = (MatchingModelCorrespondences) matcher.getCorrespondencesService();
		Matching matching = mmc.getMatching();	

		if (!(matching.getCorrespondences().size() != 0)) {
			throw new NoCorrespondencesException();
		}

		SymmetricDifference symmetricDiff = SymmetricFactory.eINSTANCE.createSymmetricDifference();
		symmetricDiff.setMatching(matching);
		

		// Merge Imports
		MergeImports importMerger = new MergeImports(symmetricDiff, scope, false);
		importMerger.merge();

		// Derive technical difference
		tdBuilder.deriveTechDiff(symmetricDiff, scope);

		// Unmerge Imports
		importMerger.unmerge();

		return symmetricDiff;
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
	public static SymmetricDifference loadDifference(String path) {
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
