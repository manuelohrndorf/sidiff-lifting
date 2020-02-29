package org.sidiff.difference.lifting.ui.handler;

import java.util.Set;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.emf.modelstorage.SiDiffResourceSet;
import org.sidiff.difference.technical.ITechnicalDifferenceBuilder;


public class LiftingPropertyTester extends PropertyTester {

	private static final String MODEL_FILE = "modelFile";

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if(property.equals(MODEL_FILE)){
			if(receiver instanceof IFile) {
				Resource resource = SiDiffResourceSet.create().getResource(EMFStorage.toPlatformURI((IFile)receiver), true);
				Set<String> documentType = EMFModelAccess.getDocumentTypes(resource, Scope.RESOURCE_SET);
				return !ITechnicalDifferenceBuilder.MANAGER.getExtensions(documentType, true).isEmpty();				
			}
		}
		return false;
	}
}
