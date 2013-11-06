package org.sidiff.patching.ui.view;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.gmf.runtime.notation.View;
import org.sidiff.patching.util.PatchUtil;

public class Util {
	
	public static String getName(EObject eObject) {

		// FIXME[MO@06.11.13]: This isn't generic -> emf instance level
		if (eObject instanceof EAnnotation) {
			EAnnotation annotation = (EAnnotation) eObject;
			return "Annotation of " + getName(annotation.getEModelElement());
		}
		
		else if (eObject instanceof EStringToStringMapEntryImpl) {
			EStringToStringMapEntryImpl entryImpl = (EStringToStringMapEntryImpl) eObject;
			return "MapEntry in \"" + getName(entryImpl.eContainer()) + "\"";
		}
		
		else {
			// Generic name search:
			String name = eObject.toString();
			
			// Check for attribute "name":
			EStructuralFeature attrName = eObject.eClass().getEStructuralFeature("name");
			if (attrName != null && attrName instanceof EAttribute) {
				Object nameAttrValue = eObject.eGet(attrName);
				
				if (nameAttrValue instanceof String) {
					name = (String) nameAttrValue;
				}
			}
			return name;
		}
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
