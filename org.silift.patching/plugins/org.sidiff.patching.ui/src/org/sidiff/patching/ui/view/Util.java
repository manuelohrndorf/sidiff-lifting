package org.sidiff.patching.ui.view;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.gmf.runtime.notation.View;
import org.sidiff.patching.util.PatchUtil;

public class Util {
	
	public static String getName(EObject eObject) {
		if (eObject instanceof ENamedElement) {
			return ((ENamedElement) eObject).getName();
		}
		if (eObject instanceof EAnnotation) {
			EAnnotation annotation = (EAnnotation) eObject;
			return "Annotation of " + getName(annotation.getEModelElement());
		}
		if (eObject instanceof EStringToStringMapEntryImpl) {
			EStringToStringMapEntryImpl entryImpl = (EStringToStringMapEntryImpl) eObject;
			return "MapEntry in \"" + getName(entryImpl.eContainer()) + "\"";
		}
		if (eObject != null) {
			return eObject.toString() + "(Unknown Object)";
		}
		return "null";
	}

	public static ResourceSet copyResourceSet(ResourceSet resourceSet) {
		Map<EObject, EObject>  map = new HashMap<EObject, EObject>();
		
		ResourceSet rs = new ResourceSetImpl();
		for (Resource resource : resourceSet.getResources()) {
			URI copyUri = PatchUtil.createURI(resource.getURI(), "patched");
			Copier copier = new Copier();
			Resource copyResource = PatchUtil.copyWithId(resource, copyUri, true, copier);
			rs.getResources().add(copyResource);
			for (EObject eObject : copier.keySet()) {
				map.put(eObject, copier.get(eObject));
			}
		}

		for (Resource resource : rs.getResources()) {
			for (TreeIterator<EObject> iterator = resource.getAllContents(); iterator.hasNext();) {
				EObject type = (EObject) iterator.next();
				if (type instanceof View) {
					View view = (View) type;
					EObject element = view.getElement();
					EObject eObject = map.get(element);
					if (element != null && eObject != null) {
						view.setElement(eObject);
					}
				}
			}
		}
		
//		for (Resource resource : rs.getResources()) {
//			resource.save(Collections.EMPTY_MAP);
//		}

		return rs;
	}

}
