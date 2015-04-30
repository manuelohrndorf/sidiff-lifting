package org.sidiff.mutation.ui.handlers;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.resource.Resource;
import org.silift.common.util.emf.EMFStorage;


public class ModelFilePropertyTester extends PropertyTester {

	private static final String MODEL_FILE = "modelFile";

	public ModelFilePropertyTester() {

	}

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if(property.equals(MODEL_FILE)){

			if(receiver instanceof IFile){
				IFile file = (IFile) receiver;
				Resource resource = EMFStorage.eLoad(EMFStorage.pathToUri(file.getLocation().toOSString())).eResource();
				if(resource != null)
					return true;
			}
		}

		return false;
	}

}
