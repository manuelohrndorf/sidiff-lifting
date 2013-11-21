package org.silift.common.util.emf;

import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;

public class EMFResourceUtil {

	public static final int RESOURCE_INTERNAL = 0;
	public static final int RESOURCE_SET_INTERNAL = 1;
	public static final int PACKAGE_REGISTRY = 2;

	public static final int COMPARE_RESOURCE = 10;
	public static final int COMPARE_RESOURCE_SET = 11;

	//TODO: this must be a dynamic comparison option
	public static int COMPARISON_MODE = COMPARE_RESOURCE;

	public static int locate(Resource model, EObject eObject) {
		// RESOURCE_INTERNAL..?
		if (contains(model, eObject)) {
			return RESOURCE_INTERNAL;
		}

		// RESOURCE_SET_INTERNAL..?
		for (Resource r : model.getResourceSet().getResources()) {
			if (r == model) {
				continue;
			}
			if (contains(r, eObject)) {
				return RESOURCE_SET_INTERNAL;
			}
		}

		// Must be found in PACKAGE_REGISTRY
		assert (EPackage.Registry.INSTANCE.containsValue(eObject.eClass().getEPackage())) : "" + eObject;
		
		return PACKAGE_REGISTRY;
	}

	private static boolean contains(Resource resource, EObject eObject) {
		for (Iterator<EObject> iterator = resource.getAllContents(); iterator.hasNext();) {
			if (eObject == iterator.next()) {
				return true;
			}
		}

		return false;
	}
}
