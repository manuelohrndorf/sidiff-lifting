package org.sidiff.patching.api.input;

import org.eclipse.core.resources.IFile;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.emf.modelstorage.SiDiffResourceSet;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.matching.input.InputModels;

public class PatchingInputModels extends InputModels {

	private AsymmetricDifference asymmetricDifference;

	public PatchingInputModels(IFile differenceFile) {
		this(SiDiffResourceSet.create().loadEObject(EMFStorage.toPlatformURI(differenceFile), AsymmetricDifference.class));
	}

	public PatchingInputModels(AsymmetricDifference asymmetricDifference) {
		super(asymmetricDifference.getOriginModel(), asymmetricDifference.getChangedModel());
		this.asymmetricDifference = asymmetricDifference;
		if(asymmetricDifference.eResource() != null) {
			this.resourceSet.getResources().add(asymmetricDifference.eResource());			
		}
	}

	public AsymmetricDifference getAsymmetricDifference() {
		return asymmetricDifference;
	}
}
