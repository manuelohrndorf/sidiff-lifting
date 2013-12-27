package org.sidiff.patching.ui.view;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;

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
			String name = "[" + eObject.eClass().getName() + "]";

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

}
