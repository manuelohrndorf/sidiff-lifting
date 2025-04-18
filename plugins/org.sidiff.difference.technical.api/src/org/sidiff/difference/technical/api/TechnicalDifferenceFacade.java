package org.sidiff.difference.technical.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.emf.exceptions.NoCorrespondencesException;
import org.sidiff.common.emf.input.InputModels;
import org.sidiff.common.emf.modelstorage.SiDiffResourceSet;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.symmetric.Change;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.SymmetricFactory;
import org.sidiff.difference.symmetric.impl.AddObjectImpl;
import org.sidiff.difference.symmetric.impl.AddReferenceImpl;
import org.sidiff.difference.symmetric.impl.AttributeValueChangeImpl;
import org.sidiff.difference.symmetric.impl.RemoveObjectImpl;
import org.sidiff.difference.symmetric.impl.RemoveReferenceImpl;
import org.sidiff.difference.symmetric.mergeimports.MergeImports;
import org.sidiff.difference.technical.ITechnicalDifferenceBuilder;
import org.sidiff.difference.technical.api.settings.DifferenceSettings;
import org.sidiff.matching.api.MatchingFacade;
import org.sidiff.matching.model.Matching;

/**
 * Convenient access to differencing functions.
 */
public class TechnicalDifferenceFacade extends MatchingFacade {

	public static final String SYMMETRIC_DIFF_EXT = "symmetric";
	
	/**
	 * Derives a technical {@link SymmetricDifference} based on a given
	 * {@link Matching} between two models.
	 * 
	 * @param matching
	 *            The matching between the input models.
	 * @param settings
	 *            Specifies the settings of the difference algorithm.
	 * @return A technical {@link SymmetricDifference} between the input models.
	 * 
	 * @see MatchingFacade#match(java.util.Collection, org.sidiff.matching.api.settings.MatchingSettings)
	 */
	public static SymmetricDifference deriveTechnicalDifference(Matching matching, DifferenceSettings settings) {
		
		if(!settings.getClass().equals(DifferenceSettings.class)){
			LogUtil.log(LogEvent.NOTICE, "Settings:\n" + settings);
		}
		
		SymmetricDifference symmetricDifference = SymmetricFactory.eINSTANCE.createSymmetricDifference();
		symmetricDifference.setMatching(matching);
		
		// Merge external references:
		mergeImports(symmetricDifference, settings);
		
		// Derive technical difference
		LogUtil.log(LogEvent.NOTICE, "Derive technical difference ...");

		ITechnicalDifferenceBuilder tdBuilder = settings.getTechBuilder();
		tdBuilder.deriveTechDiff(symmetricDifference, settings.getScope());
		
		// Unmerge Imports
		unmergeImports(settings);	

		// Report
		Map<Class<? extends Change>, Long> counts = symmetricDifference.getChanges().stream()
				.collect(Collectors.groupingBy(Change::getClass, Collectors.counting()));

		LogUtil.log(LogEvent.NOTICE, "Add Object: " + counts.getOrDefault(AddObjectImpl.class, 0L));
		LogUtil.log(LogEvent.NOTICE, "Add Reference: " + counts.getOrDefault(AddReferenceImpl.class, 0L));
		LogUtil.log(LogEvent.NOTICE, "Remove Object: " + counts.getOrDefault(RemoveObjectImpl.class, 0L));
		LogUtil.log(LogEvent.NOTICE, "Remove Reference: " + counts.getOrDefault(RemoveReferenceImpl.class, 0L));
		LogUtil.log(LogEvent.NOTICE, "Attribute Value Change: " + counts.getOrDefault(AttributeValueChangeImpl.class, 0L));
		
		return symmetricDifference;
	}
	
	/**
	 * Derives a technical {@link SymmetricDifference} between two models.
	 * Although, a symmetric difference is usually undirected the underlying
	 * difference model of SiLift implies a direction.
	 * 
	 * @param modelA
	 *            The origin model.
	 * @param modelB
	 *            The modified model.
	 * @param settings
	 *            Specifies the settings of the difference algorithm.
	 * @return A technical {@link SymmetricDifference} between the input models.
	 * @throws InvalidModelException
	 * @throws NoCorrespondencesException 
	 */
	public static SymmetricDifference deriveTechnicalDifference(Resource modelA, Resource modelB, DifferenceSettings settings) throws InvalidModelException, NoCorrespondencesException{
		return deriveTechnicalDifference(match(Arrays.asList(modelA, modelB), settings), settings);
	}
	
	/**
	 * Derives a technical {@link SymmetricDifference} between two models.
	 * Although, a symmetric difference is usually undirected the underlying
	 * difference model of SiLift implies a direction.
	 * 
	 * @param models
	 * 			The origin and modified model.
	 * @param settings
	 *            Specifies the settings of the difference algorithm.
	 * @return A technical {@link SymmetricDifference} between the input models.
	 * @throws InvalidModelException
	 * @throws NoCorrespondencesException 
	 */
	public static SymmetricDifference deriveTechnicalDifference(InputModels models, DifferenceSettings settings) throws InvalidModelException, NoCorrespondencesException{
		ArrayList<Resource> resources = new ArrayList<Resource>(models.getResources());
		return deriveTechnicalDifference(resources.get(0), resources.get(1), settings);
	}
	
	/**
	 * Serializes a technical {@link SymmetricDifference}.
	 * 
	 * @param symDiff
	 *            The difference to be serialized.
	 * @param outputDir
	 *            The serialization output directory.
	 * @param fileName
	 *            The file name of the difference.
	 */
	public static void serializeTechnicalDifference(SymmetricDifference symDiff, URI outputDir, String fileName) {
		if (!(fileName.endsWith("." + SYMMETRIC_DIFF_EXT))) {
			fileName = fileName + "." + SYMMETRIC_DIFF_EXT;
		}
		SiDiffResourceSet.create().saveEObjectAs(symDiff, outputDir.appendSegment(fileName));
	}
	
	/**
	 * Load a technical {@link SymmetricDifference}.
	 * 
	 * @param path
	 *            The path to the symmetric difference.
	 * @return The loaded technical {@link SymmetricDifference}.
	 */
	public static SymmetricDifference loadTechnicalDifference(URI uri) {
		return SiDiffResourceSet.create().loadEObject(uri, SymmetricDifference.class);
	}
	
	protected static void mergeImports(SymmetricDifference symmetricDifference, DifferenceSettings settings) {
		
		if (settings.isEnabled_MergeImports()){
			if(settings.getImports() == null) {
				MergeImports mergeImports = new MergeImports(settings.getScope(), true);
				settings.setImports(mergeImports);
			}
			settings.getImports().setSymmetricDifference(symmetricDifference);
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
}
