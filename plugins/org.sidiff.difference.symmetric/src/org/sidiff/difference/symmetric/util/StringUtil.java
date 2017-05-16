package org.sidiff.difference.symmetric.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

public class StringUtil {

	public static String eObjectToString(EObject obj) {
		EStructuralFeature nameFeature = obj.eClass().getEStructuralFeature("name");
		
		if (nameFeature != null) {
			Object name = obj.eGet(nameFeature);
			
			if (name instanceof String) {
				return (String) name;
			}
		}
		
		return obj.toString();
	}
}
