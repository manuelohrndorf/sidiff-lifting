package org.sidiff.difference.technical.api;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.emf.exceptions.NoCorrespondencesException;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.SymmetricFactory;
import org.sidiff.difference.technical.ITechnicalDifferenceBuilder;
import org.sidiff.difference.technical.MergeImports;
import org.sidiff.difference.technical.api.settings.DifferenceSettings;
import org.sidiff.matching.api.MatchingFacade;
import org.sidiff.matching.input.InputModels;
import org.sidiff.matching.model.Matching;

public class TechnicalDifferenceFacade extends MatchingFacade {

	public static String SYMMETRIC_DIFF_EXT = "symmetric";
	
	protected static MergeImports importMerger = null;
	
	public static SymmetricDifference deriveDifference(Matching matching, DifferenceSettings settings){
		SymmetricDifference symmetricDiff = SymmetricFactory.eINSTANCE.createSymmetricDifference();

		symmetricDiff.setMatching(matching);
		
		importMerger = new MergeImports(symmetricDiff, settings.getScope(), true);
		
		
		if(settings.isEnabled_MergeImports()){
			// Merge Imports
			importMerger.merge();
		}
		// Derive technical difference
		ITechnicalDifferenceBuilder tdBuilder = settings.getTechBuilder();
		tdBuilder.deriveTechDiff(symmetricDiff, settings.getScope());

		if(settings.isEnabled_MergeImports()){
			// Unmerge Imports
			importMerger.unmerge();
		}

		return symmetricDiff;
	}
	
	public static SymmetricDifference deriveDifference(Resource modelA, Resource modelB, DifferenceSettings settings) throws InvalidModelException{
		
		try {
			return deriveDifference(match(Arrays.asList(modelA, modelB), settings), settings);
		} catch (NoCorrespondencesException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
	}
	
	public static SymmetricDifference deriveDifference(InputModels models, DifferenceSettings settings) throws InvalidModelException{
		ArrayList<Resource> resources = new ArrayList<Resource>(models.getResources());
		return deriveDifference(resources.get(0), resources.get(1), settings);
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

		if (!(fileName.endsWith("." + SYMMETRIC_DIFF_EXT))) {
			fileName = fileName + "." + SYMMETRIC_DIFF_EXT;
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
	public static SymmetricDifference loadTechnicalDifference(String path) {
		return (SymmetricDifference) EMFStorage.eLoad(EMFStorage.pathToUri(path));
	}
}
