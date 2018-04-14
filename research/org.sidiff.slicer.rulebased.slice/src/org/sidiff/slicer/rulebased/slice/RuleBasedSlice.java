package org.sidiff.slicer.rulebased.slice;

import java.io.File;
import java.io.FileNotFoundException;

import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.file.FileOperations;
import org.sidiff.common.file.ZipUtil;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.api.AsymmetricDiffFacade;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.patching.patch.patch.Patch;
import org.sidiff.patching.patch.patch.PatchFactory;
import org.sidiff.slicer.slice.ModelSlice;
import org.sidiff.slicer.slice.SlicedElement;

/**
 * 
 * @author cpietsch
 *
 */
public class RuleBasedSlice {

	/**
	 * 
	 */
	private static final String MODELSLICE_NAME = "RuleBasedSlice.slice";
	
	/**
	 * 
	 */
	private static final String SYMMETRICDIFF_NAME = "RuleBasedSlice." + AsymmetricDiffFacade.SYMMETRIC_DIFF_EXT;
	
	/**
	 * 
	 */
	private static final String ASYMMETRICDIFF_NAME = "RuleBasedSlice." + AsymmetricDiffFacade.ASYMMETRIC_DIFF_EXT;
	
	/**
	 * 
	 */
	private static final String FOLDERNAME = "RuleBasedSlice";
	
	/**
	 * 
	 */
	private static final String SEPARATOR = File.separator;
	
	/**
	 * 
	 */
	private AsymmetricDifference asymmetricDifference;
	
	/**
	 * 
	 */
	private SymmetricDifference symmetricDifference;
	
	/**
	 * 
	 */
	private ModelSlice modelSlice;
	
	/**
	 * 
	 */
	private Patch patch;


	/**
	 * 
	 * @param asymmetricDifference
	 * @param modelSlice
	 */
	public RuleBasedSlice(AsymmetricDifference asymmetricDifference,
			ModelSlice modelSlice) {
		this.asymmetricDifference = asymmetricDifference;
		this.symmetricDifference = asymmetricDifference.getSymmetricDifference();
		this.modelSlice = modelSlice;
		this.patch = PatchFactory.eINSTANCE.createPatch();
	}
	
	/**
	 * 
	 * @return
	 */
	public SymmetricDifference getSymmetricDifference() {
		return symmetricDifference;
	}
	
	/**
	 * 
	 * @return
	 */
	public AsymmetricDifference getAsymmetricDifference() {
		return asymmetricDifference;
	}
	
	/**
	 * 
	 * @return
	 */
	public ModelSlice getModelSlice() {
		return modelSlice;
	}
	
	/**
	 * 
	 * @param path
	 * @return
	 * @throws FileNotFoundException
	 */
	public String serialize(String path) throws FileNotFoundException {
		
		for(SlicedElement slicedElement : modelSlice.getSlicedElements()) {
			slicedElement.setObject(null);
		}
		
		if (!path.endsWith(SEPARATOR)) {
			path += SEPARATOR;
		}
		
		path += FOLDERNAME;
		
		String resModelSliceSavePath = path + SEPARATOR + MODELSLICE_NAME;
		
		LogUtil.log(LogEvent.NOTICE, "serialize " + MODELSLICE_NAME + " to " + resModelSliceSavePath);

		EMFStorage.eSaveAs(EMFStorage.pathToUri(resModelSliceSavePath), modelSlice, true);		
		
		symmetricDifference.getMatching().setUriA(MODELSLICE_NAME);
		symmetricDifference.getMatching().setUriB(MODELSLICE_NAME);
		symmetricDifference.setUriModelA(MODELSLICE_NAME);
		symmetricDifference.setUriModelB(MODELSLICE_NAME);
		asymmetricDifference.setUriOriginModel(MODELSLICE_NAME);
		asymmetricDifference.setUriChangedModel(MODELSLICE_NAME);
		
		String resSymDiffSavePath = path + SEPARATOR + SYMMETRICDIFF_NAME;
		
		LogUtil.log(LogEvent.NOTICE, "serialize " + SYMMETRICDIFF_NAME + " to "
				+ resSymDiffSavePath);
		EMFStorage.eSaveAs(EMFStorage.pathToUri(resSymDiffSavePath), symmetricDifference, true);
		
		String resAsymDiffSavePath = path + SEPARATOR + ASYMMETRICDIFF_NAME;
		
		LogUtil.log(LogEvent.NOTICE, "serialize " + ASYMMETRICDIFF_NAME + " to "
				+ resAsymDiffSavePath);
		EMFStorage.eSaveAs(EMFStorage.pathToUri(resAsymDiffSavePath), asymmetricDifference, true);

		patch.setAsymmetricDifference(asymmetricDifference);
		String manifest = path + SEPARATOR + "Manifest.MF";
		EMFStorage.eSaveAs(EMFStorage.pathToUri(manifest), patch, true);
		ZipUtil.zip(path, path, AsymmetricDiffFacade.PATCH_EXTENSION);
		FileOperations.removeFolder(path);

		// Return path of saved zip:
		return path + "." + AsymmetricDiffFacade.PATCH_EXTENSION;
	}
}
