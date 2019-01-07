package org.sidiff.patching.api;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.emf.exceptions.NoCorrespondencesException;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.api.AsymmetricDiffFacade;
import org.sidiff.difference.asymmetric.api.util.Difference;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.matching.input.InputModels;
import org.sidiff.patching.api.exceptions.PatchCreationException;
import org.sidiff.patching.api.settings.PatchingSettings;
import org.sidiff.patching.patch.patch.PatchCreator;

public class PatchingFacade extends AsymmetricDiffFacade{

	/**
	 * @param patchCreator
	 * @param outputDir
	 * @param filename
	 * @throws PatchCreationException  
	 */
	public static URI createPatch(PatchCreator patchCreator, URI outputDir, String filename) throws PatchCreationException {
		try {
			if(filename == null || filename.isEmpty()){
				return patchCreator.serializePatch(outputDir);			
			}
			return patchCreator.serializePatch(outputDir, filename);		
		} catch (IOException e) {
			throw new PatchCreationException(e);
		}
	}

	/**
	 * @param asymmetricDifference
	 * @param settings
	 * @param outputDir
	 * @param filename
	 * @throws PatchCreationException 
	 */
	public static URI createPatch(AsymmetricDifference asymmetricDifference, PatchingSettings settings, URI outputDir, String filename) throws PatchCreationException{
		PatchCreator patchCreator = new PatchCreator(asymmetricDifference, settings.getMatcher(),
				settings.useSymbolicLinks(), settings.getSymbolicLinkHandler());
		return createPatch(patchCreator, outputDir, filename);
	}
	
	/**
	 * @param symmetricDifference
	 * @param settings
	 * @param outputDir
	 * @param filename
	 * @throws PatchCreationException
	 */
	public static URI createPatch(SymmetricDifference symmetricDifference, PatchingSettings settings, URI outputDir, String filename) throws PatchCreationException{
		Difference difference = deriveLiftedAsymmetricDifference(symmetricDifference, settings);
		return createPatch(difference.getAsymmetric(), settings, outputDir, filename);
	}
	
	/**
	 * @param modelA
	 * @param modelB
	 * @param settings
	 * @param outputDir
	 * @param filename
	 * @throws PatchCreationException
	 */
	public static URI createPatch(Resource modelA, Resource modelB, PatchingSettings settings, URI outputDir, String filename) throws PatchCreationException{
		try {
			Difference difference = deriveLiftedAsymmetricDifference(modelA, modelB, settings);
			return createPatch(difference.getAsymmetric(), settings, outputDir, filename);
		} catch (InvalidModelException | NoCorrespondencesException e) {
			throw new PatchCreationException(e);
		}
	}
	
	/**
	 * @param models
	 * @param settings
	 * @param outputDir
	 * @param filename
	 * @throws PatchCreationException
	 */
	public static URI createPatch(InputModels models, PatchingSettings settings, URI outputDir, String filename) throws PatchCreationException {
		try {
			Difference difference = deriveLiftedAsymmetricDifference(models, settings);
			return createPatch(difference.getAsymmetric(), settings, outputDir, filename);
		} catch (InvalidModelException | NoCorrespondencesException e) {
			throw new PatchCreationException(e);
		}
	}
}
