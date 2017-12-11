package org.sidiff.difference.asymmetric.api;

import static org.sidiff.difference.asymmetric.util.AsymmetricDifferenceUtil.deriveAsymmetricDifference;

import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.emf.exceptions.NoCorrespondencesException;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.common.util.StatisticsUtil;
import org.sidiff.correspondences.CorrespondencesUtil;
import org.sidiff.correspondences.matchingmodel.MatchingModelCorrespondences;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.AsymmetricFactory;
import org.sidiff.difference.asymmetric.api.util.Difference;
import org.sidiff.difference.asymmetric.dependencies.real.DependencyAnalyzer;
import org.sidiff.difference.asymmetric.dependencies.real.EngineBasedDependencyAnalyzer;
import org.sidiff.difference.asymmetric.mergeimports.AsymmetricMergeImports;
import org.sidiff.difference.asymmetric.paramretrieval.ParameterMapper;
import org.sidiff.difference.asymmetric.paramretrieval.ParameterRetriever;
import org.sidiff.difference.lifting.api.LiftingFacade;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.technical.api.settings.DifferenceSettings;
import org.sidiff.matching.input.InputModels;

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

	/**
	 * Lifts a technical {@link SymmetricDifference} and derives an executable
	 * {@link AsymmetricDifference}.
	 * 
	 * @param symmetricDifference
	 *            The technical difference to be lifted.
	 * @param settings
	 *            Specifies the settings of the lifting algorithm.
	 * 
	 * @return A {@link Difference} containing both the lifted
	 *         {@link SymmetricDifference} and executable
	 *         {@link AsymmetricDifference}.
	 */
	public static Difference deriveLiftedAsymmetricDifference(SymmetricDifference symmetricDifference, LiftingSettings settings) {
		
		// Create empty asymmetric difference
		AsymmetricDifference asymmetricDifference = AsymmetricFactory.eINSTANCE.createAsymmetricDifference();
		asymmetricDifference.setSymmetricDifference(symmetricDifference);
		
		// Merge imports:
		mergeImports(symmetricDifference, asymmetricDifference, settings);
		
		// Lifting:
		liftMeUp(symmetricDifference, settings);
		
		// Derive Asymmetric-Difference from Symmetric-Difference
		LogUtil.log(LogEvent.NOTICE, "Derive asymmetric difference");
		deriveAsymmetricDifference(symmetricDifference, asymmetricDifference);
		
		// Retrieve dependencies of operation invocations
		StatisticsUtil.getInstance().start("DependencyAnalysis");
		LogUtil.log(LogEvent.NOTICE, "Analyze dependencies");
		DependencyAnalyzer dependencyAnalyzer = new EngineBasedDependencyAnalyzer(asymmetricDifference, settings.getRecognitionEngine());
		dependencyAnalyzer.analyze();
		StatisticsUtil.getInstance().stop("DependencyAnalysis");
		
		// Retrieve actual parameter values of operation invocations
		StatisticsUtil.getInstance().start("ParameterRetriever");
		LogUtil.log(LogEvent.NOTICE, "Retrieve parameter values");
		ParameterRetriever paramRetriever = new ParameterRetriever(settings.getRecognitionEngine(), asymmetricDifference);
		paramRetriever.retrieveParameters();
		StatisticsUtil.getInstance().stop("ParameterRetriever");

		// Map OUT-Parameters to respective IN-Parameters
		StatisticsUtil.getInstance().start("ParameterMapping");
		LogUtil.log(LogEvent.NOTICE, "Map parameter values");
		ParameterMapper paramMapper = new ParameterMapper(asymmetricDifference);
		paramMapper.mapParameters();
		StatisticsUtil.getInstance().stop("ParameterMapping");
		
		// Create new difference container
		Difference fullDiff = new Difference(symmetricDifference, asymmetricDifference);
		
		// Unmerge imports:
		unmergeImports(settings);
		
		return fullDiff;
	}
	
	/**
	 * Computes an lifted executable {@link AsymmetricDifference} between two models.
	 * 
	 * @param modelA
	 *            The origin model.
	 * @param modelB
	 *            The modified model.
	 * @param settings
	 *            Specifies the settings of the semantic lifting algorithm.
	 * 
	 * @return A {@link Difference} containing both the lifted
	 *         {@link SymmetricDifference} and executable
	 *         {@link AsymmetricDifference}.
	 * 
	 * @throws InvalidModelException
	 * @throws NoCorrespondencesException
	 */
	public static Difference deriveLiftedAsymmetricDifference(Resource modelA, Resource modelB, LiftingSettings settings) throws InvalidModelException, NoCorrespondencesException{
		
		// Set SiLift default Correspondence-Service:
		settings.setCorrespondencesService(
				CorrespondencesUtil.getAvailableCorrespondencesService(
						MatchingModelCorrespondences.SERVICE_ID));
		
		settings.setImports(new AsymmetricMergeImports(settings.getScope(), true));
		
		// Calculate model difference:
		settings.setUnmergeImports(false); // Do not unmerge imports until lifting is done...
		SymmetricDifference symmetricDifference = deriveTechnicalDifference(modelA, modelB, settings);
		settings.setUnmergeImports(true);
		
		// Lift model difference:
		return deriveLiftedAsymmetricDifference(symmetricDifference, settings);
	}
	
	/**
	 * Computes an lifted executable {@link AsymmetricDifference} between two models.
	 * 
	 * @param models
	 *            The origin and modified model.
	 * @param settings
	 *            Specifies the settings of the semantic lifting algorithm.
	 * 
	 * @return A {@link Difference} containing both the lifted
	 *         {@link SymmetricDifference} and executable
	 *         {@link AsymmetricDifference}.
	 * 
	 * @throws InvalidModelException
	 * @throws NoCorrespondencesException
	 */
	public static Difference deriveLiftedAsymmetricDifference(InputModels models, LiftingSettings settings) throws InvalidModelException, NoCorrespondencesException{
		
		if (models.getResources().size() == 2) {
			return deriveLiftedAsymmetricDifference(models.getResources().get(0), models.getResources().get(1), settings);
		} else {
			throw new RuntimeException("Exactly two models are required.");
		}
	}
	
	protected static void mergeImports(SymmetricDifference symmetricDifference,
			AsymmetricDifference asymmetricDifference, DifferenceSettings settings) {

		if (settings.isEnabled_MergeImports()) {
			if (settings.getImports() == null) {
				LogUtil.log(LogEvent.NOTICE, "Merge imports");
				AsymmetricMergeImports importMerger = new AsymmetricMergeImports(settings.getScope(), true);
				settings.setImports(importMerger);
			}
			((AsymmetricMergeImports) settings.getImports()).setSymmetricDifference(symmetricDifference);
			((AsymmetricMergeImports) settings.getImports()).setAsymmetricDifference(asymmetricDifference);
			settings.getImports().merge();
		}

	}
	
	protected static void unmergeImports(DifferenceSettings settings) {
		
		if ((settings.getImports() != null) && (settings.isEnabled_UnmergeImports())) {
			LogUtil.log(LogEvent.NOTICE, "Umerge imports");
			settings.getImports().unmerge();
			settings.setImports(null);
		}
	}

	/**
	 * Serializes a difference.
	 *
	 * @param diff
	 *            The difference to serialize.
	 * @param path
	 *            The serialization path.
	 * @param fileName The file name of the difference (without file extension).
	 */
	public static void serializeLiftedDifference(Difference diff, String path, String fileName) {
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
	public static AsymmetricDifference loadLiftedAsymmetricDifference(String path) {
		return (AsymmetricDifference) EMFStorage.eLoad(EMFStorage.pathToUri(path));
	}
}
