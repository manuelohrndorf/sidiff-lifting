package org.sidiff.difference.asymmetric.api;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.emf.exceptions.NoCorrespondencesException;
import org.sidiff.common.emf.input.InputModels;
import org.sidiff.common.emf.modelstorage.SiDiffResourceSet;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.common.statistics.StatisticsUtil;
import org.sidiff.correspondences.ICorrespondences;
import org.sidiff.correspondences.matchingmodel.MatchingModelCorrespondences;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.AsymmetricFactory;
import org.sidiff.difference.asymmetric.api.util.Difference;
import org.sidiff.difference.asymmetric.dependencies.real.DependencyAnalyzer;
import org.sidiff.difference.asymmetric.dependencies.real.EngineBasedDependencyAnalyzer;
import org.sidiff.difference.asymmetric.mergeimports.AsymmetricMergeImports;
import org.sidiff.difference.asymmetric.paramretrieval.ParameterMapper;
import org.sidiff.difference.asymmetric.paramretrieval.ParameterRetriever;
import org.sidiff.difference.asymmetric.util.AsymmetricDifferenceUtil;
import org.sidiff.difference.lifting.api.LiftingFacade;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.technical.api.settings.DifferenceSettings;

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
		((AsymmetricMergeImports)settings.getImports()).setAsymmetricDifference(asymmetricDifference);
		
		// Merge imports:
		mergeImports(symmetricDifference, asymmetricDifference, settings);
		
		// Lifting:
		liftMeUp(symmetricDifference, settings);
		
		// Derive Asymmetric-Difference from Symmetric-Difference
		LogUtil.log(LogEvent.NOTICE, "Derive asymmetric difference");
		AsymmetricDifferenceUtil.deriveAsymmetricDifference(symmetricDifference, asymmetricDifference);
		
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
		settings.setCorrespondencesService(ICorrespondences.MANAGER.getExtension(MatchingModelCorrespondences.class).get());
		
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
		Assert.isLegal(models.getResources().size() == 2, "Exactly two models are required.");
		return deriveLiftedAsymmetricDifference(models.getResources().get(0), models.getResources().get(1), settings);
	}
	
	protected static void mergeImports(SymmetricDifference symmetricDifference,
			AsymmetricDifference asymmetricDifference, DifferenceSettings settings) {

		if (settings.isEnabled_MergeImports()) {
			if (settings.getImports() == null) {
				AsymmetricMergeImports importMerger = new AsymmetricMergeImports(settings.getScope(), true);
				settings.setImports(importMerger);
			}
			((AsymmetricMergeImports) settings.getImports()).setSymmetricDifference(symmetricDifference);
			((AsymmetricMergeImports) settings.getImports()).setAsymmetricDifference(asymmetricDifference);
			LogUtil.log(LogEvent.NOTICE, "Merge imports");
			settings.getImports().merge();
		}

	}
	
	protected static void unmergeImports(DifferenceSettings settings) {
		
		if ((settings.getImports() != null) && (settings.isEnabled_UnmergeImports())) {
			LogUtil.log(LogEvent.NOTICE, "Unmerge imports");
			settings.getImports().unmerge();
			settings.setImports(null);
		}
	}

	/**
	 * Serializes a difference.
	 *
	 * @param diff
	 *            The difference to serialize.
	 * @param outputDir
	 *            The serialization output directory.
	 * @param fileName The file name of the difference (without file extension).
	 * @deprecated Use {@link Difference#serialize(SiDiffResourceSet, URI, String)} instead.
	 */
	public static void serializeLiftedDifference(Difference diff, URI outputDir, String fileName) {
		SiDiffResourceSet.create().saveEObjectAs(diff.getSymmetric(),
				outputDir.appendSegment(fileName).appendFileExtension(LiftingFacade.SYMMETRIC_DIFF_EXT));
		SiDiffResourceSet.create().saveEObjectAs(diff.getAsymmetric(),
				outputDir.appendSegment(fileName).appendFileExtension(AsymmetricDiffFacade.ASYMMETRIC_DIFF_EXT));
	}


	/**
	 * Load asymmetric difference.
	 *
	 * @param path
	 *            The path to the asymmetric difference.
	 * @return The loaded asymmetric difference.
	 */
	public static AsymmetricDifference loadLiftedAsymmetricDifference(URI uri) {
		return SiDiffResourceSet.create().loadEObject(uri, AsymmetricDifference.class);
	}
}
