package org.sidiff.patching.api.input;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.input.InputModels;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.emf.modelstorage.SiDiffResourceSet;
import org.sidiff.difference.asymmetric.AsymmetricDifference;

public class PatchingInputModels extends InputModels {

	private AsymmetricDifference asymmetricDifference;

	protected PatchingInputModels(SiDiffResourceSet resourceSet, List<Resource> resources, AsymmetricDifference asymmetricDifference) {
		super(resourceSet, resources);
		this.asymmetricDifference = asymmetricDifference;
		if(asymmetricDifference.eResource() != null) {
			getResourceSet().getResources().add(asymmetricDifference.eResource());			
		}
	}
	
	public AsymmetricDifference getAsymmetricDifference() {
		return asymmetricDifference;
	}

	public static PatchingInputModels forDifference(AsymmetricDifference asymmetricDifference) {
		return InputModels.builder((resSet, resources) -> new PatchingInputModels(resSet, resources, asymmetricDifference))
				.addModel(asymmetricDifference.getOriginModel())
				.addModel(asymmetricDifference.getChangedModel())
				.build();
	}
	
	public static PatchingInputModels forDifference(IFile differenceFile) {
		return forDifference(SiDiffResourceSet.create().loadEObject(EMFStorage.toPlatformURI(differenceFile), AsymmetricDifference.class));
	}
}
