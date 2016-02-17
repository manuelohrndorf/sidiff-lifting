package org.sidiff.difference.asymmetric.api;

import static org.sidiff.difference.asymmetric.util.AsymmetricDifferenceUtil.deriveAsymmetricDifference;

import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.util.StatisticsUtil;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.AsymmetricFactory;
import org.sidiff.difference.asymmetric.api.util.Difference;
import org.sidiff.difference.asymmetric.dependencies.real.DependencyAnalyzer;
import org.sidiff.difference.asymmetric.paramretrieval.ParameterMapper;
import org.sidiff.difference.asymmetric.paramretrieval.ParameterRetriever;
import org.sidiff.difference.lifting.api.LiftingFacade;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.technical.MergeImports;

/**
 * Convenient access to asymmetric difference calculation functions.
 */
public class AsymmetricDiffFacade extends LiftingFacade {
	
	/**
	 * Asymmetric difference file extension.
	 */
	public static final String ASYMMETRIC_DIFF_EXT = "asymmetric";

	/**
	 * The patch file extension.
	 */
	public static final String PATCH_EXTENSION = "slp";

	
	public static Difference deriveAsymDiff(SymmetricDifference symmetricDifference, LiftingSettings settings){
		
		if(importMerger == null){
			importMerger = new MergeImports(symmetricDifference, settings.getScope(), true);
		}
		
		if(settings.isEnabled_MergeImports()){
			// Merge Imports
			importMerger.merge();
		}
		
		// Create empty asymmetric difference
		AsymmetricDifference asymmetricDifference = AsymmetricFactory.eINSTANCE.createAsymmetricDifference();
		asymmetricDifference.setSymmetricDifference(symmetricDifference);
		
		// Derive Asymmetric-Difference from Symmetric-Difference
		deriveAsymmetricDifference(symmetricDifference, asymmetricDifference);
		
		// Retrieve dependencies of operation invocations
		StatisticsUtil.getInstance().start("DependencyAnalysis");
		DependencyAnalyzer dependencyAnalyzer = new DependencyAnalyzer(recognitionEngine, asymmetricDifference);
		dependencyAnalyzer.analyze();
		StatisticsUtil.getInstance().stop("DependencyAnalysis");
		
		// Retrieve actual parameter values of operation invocations
		StatisticsUtil.getInstance().start("ParameterRetriever");
		ParameterRetriever paramRetriever = new ParameterRetriever(recognitionEngine, asymmetricDifference);
		paramRetriever.retrieveParameters();
		StatisticsUtil.getInstance().stop("ParameterRetriever");

		// Map OUT-Parameters to respective IN-Parameters
		StatisticsUtil.getInstance().start("ParameterMapping");
		ParameterMapper paramMapper = new ParameterMapper(asymmetricDifference);
		paramMapper.mapParameters();
		StatisticsUtil.getInstance().stop("ParameterMapping");
		
		// Create new difference container
		Difference fullDiff = new Difference(symmetricDifference, asymmetricDifference);
		
		if(settings.isEnabled_MergeImports()){
			// Unmerge Imports
			importMerger.unmerge();
		}
		
		return fullDiff;
	}
	
	public static Difference deriveAsymDiff(Resource modelA, Resource modelB, LiftingSettings settings) throws InvalidModelException{
		SymmetricDifference symmetricDifference = liftMeUp(modelA, modelB, settings);
		return deriveAsymDiff(symmetricDifference, settings);
	}
//	/**
//	 * Creates a lifted symmetric difference and an asymmetric difference between model A and model B.
//	 *
//	 * @param modelA
//	 *            The earlier version of the model.
//	 * @param modelB
//	 *            The later version of the model.
//	 * @param settings
//	 *            Specifies the settings of the algorithm.
//	 * @return A difference container with a lifted symmetric difference and an asymmetric difference.
//	 * @see AsymmetricDiffFacade#load(String)
//	 * @see AsymmetricDiffFacade#liftMeUp(SymmetricDifference)
//	 * @see AsymmetricDiffFacade#liftMeUp(SymmetricDifference, AsymmetricDiffSettings)
//	 * @see AsymmetricDiffFacade#liftMeUp(Resource, Resource, IMatcher)
//	 * @throws InvalidModelException
//	 * @throws NoCorrespondencesException 
//	 */
//	public static Difference liftMeUp(Resource modelA, Resource modelB, LiftingSettings settings) throws InvalidModelException, NoCorrespondencesException{
//		LogUtil.log(LogEvent.NOTICE, settings.toString());
//		
//		// Be sure proxy resolution is done
//		EcoreUtil.resolveAll(modelA.getResourceSet());
//		EcoreUtil.resolveAll(modelB.getResourceSet());
//
//		// Validate models
//		if (settings.isValidate()){
//			EMFValidate.validateObject(modelA.getContents().get(0), modelB.getContents().get(0));
//		}
//
//		//Disabled by default
//		StatisticsUtil.disable();
//
//		// Create matching
//		IMatcher matcher = settings.getMatcher();		
//		StatisticsUtil.getInstance().start("Matching");
//		matcher.startMatching(Arrays.asList(modelA, modelB), settings.getScope());	
//		StatisticsUtil.getInstance().stop("Matching");		
//
//		// Get Matching
//		// In SiLift we assume support of @see{MatchingModelCorrespondences}
//		ICorrespondences correspondences = matcher.getCorrespondencesService();
//		Matching matching = ((MatchingModelCorrespondences)correspondences).getMatching();	
//
//		if (!(matching.getCorrespondences().size() != 0)) {
//			throw new NoCorrespondencesException();
//		}
//
//		//Contain Matching in Difference
//		SymmetricDifference symmetricDiff = SymmetricFactory.eINSTANCE.createSymmetricDifference();
//		symmetricDiff.setMatching(matching);
//
//		// Create empty asymmetric difference
//		AsymmetricDifference asymmetricDiff = AsymmetricFactory.eINSTANCE.createAsymmetricDifference();
//		asymmetricDiff.setSymmetricDifference(symmetricDiff);
//
//		// Merge Imports
//		StatisticsUtil.getInstance().start("MergeImports");
//		MergeImports importMerger = new MergeImports(symmetricDiff, asymmetricDiff, settings.getScope(), true);
//		importMerger.merge();
//		StatisticsUtil.getInstance().stop("MergeImports");
//
//		// Derive technical difference
//		ITechnicalDifferenceBuilder diffBuilder = settings.getTechBuilder();
//		StatisticsUtil.getInstance().start("TechnicalDifference");
//		diffBuilder.deriveTechDiff(symmetricDiff, settings.getScope());
//		StatisticsUtil.getInstance().stop("TechnicalDifference");
//
//		// Lift difference
//		assert (matching.getCorrespondences().size() > 0) : "Empty difference!";
//
//		// Start lifting
//		long lifting = System.currentTimeMillis();
//
//		// Filter out all rules with derived references
//		StatisticsUtil.getInstance().start("FilterRRs");
//		RuleBaseFilter filter = new RuleBaseFilter(settings.getRuleBases());
//		filter.filterDerivedReferences();
//		StatisticsUtil.getInstance().stop("FilterRRs");
//
//
//		// Start recognition engine
//		StatisticsUtil.getInstance().start("RecognitionEngine");
//		RecognitionEngine engine = new RecognitionEngine(symmetricDiff, importMerger.getImports(), settings);
//		engine.execute();
//		StatisticsUtil.getInstance().stop("RecognitionEngine");
//
//		// Revert all filters
//		filter.rollback();
//
//		// Postprocess
//		StatisticsUtil.getInstance().start("PostProcessing");
//		PostProcessor postProcessor = new PostProcessor(engine);
//		postProcessor.postProcess();
//		StatisticsUtil.getInstance().stop("PostProcessing");
//
//		// Print report
//		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
//		LogUtil.log(LogEvent.NOTICE, "---------------------- FINISHED LIFTING --------------------");
//		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
//
//		int scsCount = symmetricDiff.getChangeSets().size();
//		int totalAtomicCount = symmetricDiff.getChanges().size();
//		int uncoveredCount = DifferenceAnalysisUtil.getRemainingChanges(symmetricDiff).size();
//		int coveredCount = totalAtomicCount - uncoveredCount;
//		int correspondenceCount = matching.getCorrespondences().size();
//
//		DecimalFormat f = new DecimalFormat("#0.0");
//
//		LogUtil.log(LogEvent.NOTICE, "Total semantic changes sets (|SCS|): " + scsCount);
//		LogUtil.log(LogEvent.NOTICE, "Total atomic changes (|D|): " + totalAtomicCount);
//		LogUtil.log(LogEvent.NOTICE, "Finally uncovered atomic changes: " + uncoveredCount);
//		LogUtil.log(LogEvent.NOTICE, "Finally covered atomic changes: " + coveredCount);
//		LogUtil.log(LogEvent.NOTICE, "Compression (|D|/|SCS|): " + f.format(((double) coveredCount / (double) scsCount)));
//		LogUtil.log(LogEvent.NOTICE, "Correspondences: " + correspondenceCount);
//		LogUtil.log(LogEvent.NOTICE, "Duration of Difference lifting in ms: " + (System.currentTimeMillis() - lifting));
//
//		// Derive Asymmetric-Difference from Symmetric-Difference
//		deriveAsymmetricDifference(symmetricDiff, asymmetricDiff);
//
//		// Retrieve dependencies of operation invocations
//		StatisticsUtil.getInstance().start("DependencyAnalysis");
//		DependencyAnalyzer dependencyAnalyzer = new DependencyAnalyzer(engine, asymmetricDiff);
//		dependencyAnalyzer.analyze();
//		StatisticsUtil.getInstance().stop("DependencyAnalysis");
//
//		// Retrieve actual parameter values of operation invocations
//		StatisticsUtil.getInstance().start("ParameterRetriever");
//		ParameterRetriever paramRetriever = new ParameterRetriever(engine, asymmetricDiff);
//		paramRetriever.retrieveParameters();
//		StatisticsUtil.getInstance().stop("ParameterRetriever");
//
//
//		// Map OUT-Parameters to respective IN-Parameters
//		StatisticsUtil.getInstance().start("ParameterMapping");
//		ParameterMapper paramMapper = new ParameterMapper(asymmetricDiff);
//		paramMapper.mapParameters();
//		StatisticsUtil.getInstance().stop("ParameterMapping");
//
//		/*
//		 * FINISHED
//		 */
//
//		// Unmerge Imports
//		StatisticsUtil.getInstance().start("UnmergeImports");
//		importMerger.unmerge();
//		StatisticsUtil.getInstance().stop("UnmergeImports");
//
//		// Create new difference container
//		Difference fullDiff = new Difference(symmetricDiff, asymmetricDiff);
//
//		return fullDiff;
//	}
//
//	/**
//	 * Creates a lifted symmetric difference and an asymmetric difference between model A and model B.
//	 *
//	 * @param modelA
//	 *            The earlier version of the model.
//	 * @param modelB
//	 *            The later version of the model.
//	 * @param matcher
//	 *            The matcher which calculates the correspondences between model A and modelB.
//	 * @return A difference container with a lifted symmetric difference and an asymmetric difference.
//	 * @see AsymmetricDiffFacade#load(String)
//	 * @see AsymmetricDiffFacade#liftMeUp(SymmetricDifference)
//	 * @see AsymmetricDiffFacade#liftMeUp(SymmetricDifference, AsymmetricDiffSettings)
//	 * @see AsymmetricDiffFacade#liftMeUp(Resource, Resource, AsymmetricDiffSettings)
//	 * @throws InvalidModelException
//	 * @throws NoCorrespondencesException 
//	 */
//	public static Difference liftMeUp(Resource modelA, Resource modelB, IMatcher matcher) throws InvalidModelException, NoCorrespondencesException{
//		// Get default settings
//		String documentType = EMFModelAccess.getCharacteristicDocumentType(modelA);
//		LiftingSettings defaultSettings = new LiftingSettings(documentType);
//		defaultSettings.setCalculateEditRuleMatch(true);
//		defaultSettings.setMatcher(matcher);
//
//		// Calculate lifted difference
//		Difference fullDiff = liftMeUp(modelA, modelB, defaultSettings);
//
//		return fullDiff;
//	}

	/**
	 * Serializes a difference.
	 *
	 * @param diff
	 *            The difference to serialize.
	 * @param path
	 *            The serialization path.
	 * @param fileName The file name of the difference (without file extension).
	 */
	public static void serializeDifference(Difference diff, String path, String fileName) {
		if (!(path.endsWith("/") || path.endsWith("\\"))) {
			path = path + "/";
		}

		EMFStorage.eSaveAs(EMFStorage.pathToUri(path + fileName + "." + LiftingFacade.SYMMETRIC_DIFF_EXT), diff.getSymmetric());
		EMFStorage.eSaveAs(EMFStorage.pathToUri(path + fileName + "." + AsymmetricDiffFacade.ASYMMETRIC_DIFF_EXT), diff.getAsymmetric());
	}


	/**
	 * Load symmetric difference.
	 *
	 * @param path
	 *            The path to the asymmetric difference.
	 * @return The loaded symmetric difference.
	 */
	public static AsymmetricDifference loadAsymmetricDiff(String path) {
		return (AsymmetricDifference) EMFStorage.eLoad(EMFStorage.pathToUri(path));
	}

	/**
	 * Load difference.
	 *
	 * @param path
	 *            The path to the difference. (without file extension).
	 * @return The loaded difference.
	 */
	public static Difference loadDifference(String path) {
		SymmetricDifference symmetricDiff = (SymmetricDifference)loadSymmetricDifference(path + "." + LiftingFacade.SYMMETRIC_DIFF_EXT);
		AsymmetricDifference asymmetricDiff = (AsymmetricDifference) loadAsymmetricDiff(path + "." + AsymmetricDiffFacade.ASYMMETRIC_DIFF_EXT);

		Difference fullDiff = new Difference(symmetricDiff, asymmetricDiff);
		return fullDiff;
	}
}
