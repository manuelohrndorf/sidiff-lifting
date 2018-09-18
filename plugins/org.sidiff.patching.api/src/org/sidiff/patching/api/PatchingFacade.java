package org.sidiff.patching.api;

import java.io.IOException;

import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.emf.exceptions.NoCorrespondencesException;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.api.AsymmetricDiffFacade;
import org.sidiff.difference.asymmetric.api.util.Difference;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.matching.input.InputModels;
import org.sidiff.patching.api.settings.PatchingSettings;
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
	 * @throws IOException  
	 */
	public static void createPatch(PatchCreator patchCreator, String path, String filename) throws IOException {
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
	 * @throws IOException 
	 */
	public static void createPatch(AsymmetricDifference asymmetricDifference, PatchingSettings settings, String path, String filename) throws IOException{
		patchCreator = new PatchCreator(asymmetricDifference, settings.getMatcher(), settings.useSymbolicLinks(), settings.getSymbolicLinkHandler());
		createPatch(patchCreator, path, filename);
	}
	
	/**
	 * @param symmetricDifference
	 * @param settings
	 * @param path
	 * @param filename
	 * @throws IOException 
	 */
	public static void createPatch(SymmetricDifference symmetricDifference, PatchingSettings settings, String path, String filename) throws IOException{
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
	 * @throws NoCorrespondencesException
	 * @throws IOException 
	 */
	public static void createPatch(Resource modelA, Resource modelB, PatchingSettings settings, String path, String filename) throws InvalidModelException, NoCorrespondencesException, IOException{
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
	 * @throws IOException 
	 */
	public static void createPatch(InputModels models, PatchingSettings settings, String path, String filename) throws InvalidModelException, NoCorrespondencesException, IOException{
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
