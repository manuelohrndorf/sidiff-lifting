package org.sidiff.patching.api;

import java.io.FileNotFoundException;

import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.emf.exceptions.NoCorrespondencesException;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.api.AsymmetricDiffFacade;
import org.sidiff.difference.asymmetric.api.util.Difference;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.matching.input.InputModels;
import org.sidiff.patching.patch.patch.PatchCreator;

public class PatchingFacade extends AsymmetricDiffFacade{
	
	/**
	 * 
	 */
	private static PatchCreator patchCreator;
	/**
	 * 
	 */
	private static String patchPath;
	
	/**
	 * @param patchCreator
	 * @param path
	 * @param filename
	 * @throws FileNotFoundException
	 */
	public static void createPatch(PatchCreator patchCreator, String path, String filename) throws FileNotFoundException{
		PatchingFacade.patchCreator = patchCreator;
		if(filename == null || filename.isEmpty()){
			patchPath = patchCreator.serializePatch(path);
		}else{
			patchPath = patchCreator.serializePatch(path, filename);
		}
	}
	
	/**
	 * @param asymmetricDifference
	 * @param settings
	 * @param path
	 * @param filename
	 * @throws FileNotFoundException
	 */
	public static void createPatch(AsymmetricDifference asymmetricDifference, LiftingSettings settings, String path, String filename) throws FileNotFoundException{
		patchCreator = new PatchCreator(asymmetricDifference, settings);
		createPatch(patchCreator, path, filename);
	}
	
	/**
	 * @param symmetricDifference
	 * @param settings
	 * @param path
	 * @param filename
	 * @throws FileNotFoundException
	 */
	public static void createPatch(SymmetricDifference symmetricDifference, LiftingSettings settings, String path, String filename) throws FileNotFoundException{
		Difference difference = deriveLiftedAsymmetricDifference(symmetricDifference, settings);
		createPatch(difference.getAsymmetric(), settings, path, filename);
	}
	
	/**
	 * @param modelA
	 * @param modelB
	 * @param settings
	 * @param path
	 * @param filename
	 * @throws InvalidModelException
	 * @throws FileNotFoundException
	 * @throws NoCorrespondencesException
	 */
	public static void createPatch(Resource modelA, Resource modelB, LiftingSettings settings, String path, String filename) throws InvalidModelException, FileNotFoundException, NoCorrespondencesException{
		Difference difference = deriveLiftedAsymmetricDifference(modelA, modelB, settings);
		createPatch(difference.getAsymmetric(), settings, path, filename);
	}
	
	/**
	 * @param models
	 * @param settings
	 * @param path
	 * @param filename
	 * @throws InvalidModelException
	 * @throws NoCorrespondencesException
	 * @throws FileNotFoundException
	 */
	public static void createPatch(InputModels models, LiftingSettings settings, String path, String filename) throws InvalidModelException, NoCorrespondencesException, FileNotFoundException{
		Difference difference = deriveLiftedAsymmetricDifference(models, settings);
		createPatch(difference.getAsymmetric(), settings, path, filename);
	}
	

	/**
	 * 
	 * @return
	 */
	public static String getPatchPath() {
		return patchPath;
	}
}
