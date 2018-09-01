package org.sidiff.slicer;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.extension.ITypedExtension;
import org.sidiff.slicer.slice.ModelSlice;

public interface ISlicer extends ITypedExtension {

	Description<ISlicer> DESCRIPTION = Description.of(ISlicer.class, "org.sidiff.slicer.instantiation", "instantiation", "class");
	SlicerManager MANAGER = new SlicerManager();

	boolean canHandleModels(Collection<Resource> models);

	boolean canHandleConfiguration(ISlicingConfiguration config);

	void init(ISlicingConfiguration config) throws Exception;

	ModelSlice slice(Collection<EObject> input) throws Exception;
}
