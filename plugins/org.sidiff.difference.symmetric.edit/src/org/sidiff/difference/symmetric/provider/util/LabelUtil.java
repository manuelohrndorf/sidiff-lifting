package org.sidiff.difference.symmetric.provider.util;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

public class LabelUtil {

	public static String getLabel(Object object){
		String label = "";
		if(object instanceof EObject){
			EObject eObject = (EObject) object;
			for(EAttribute attribute : eObject.eClass().getEAllAttributes()){
				if(attribute.getName().equalsIgnoreCase("name")){
					label = String.format("%s (%s)", (String) eObject.eGet(attribute),  eObject.eClass().getName());
					return label;
				}
			}

			for(EAttribute attribute : eObject.eClass().getEAllAttributes()){
				if(attribute.getName().equalsIgnoreCase("id")){
					label = String.format("%s.%s", eObject.eClass().getName(), "" + eObject.eGet(attribute));
					return label;
				}
			}

			if(object instanceof EAnnotation){
				
				EAnnotation eAnnotation = (EAnnotation) object;
				label = String.format("%s (%s)", eAnnotation.getSource(), eAnnotation.eClass().getName());
				return label;
			}

			String[] fragments = EcoreUtil.getURI(eObject).toString().split("\\.");
			String indexFragment = fragments[fragments.length-1];

			if(indexFragment.matches("\\d+")){
				label = String.format("%s.%s (%s)", eObject.eContainingFeature().getName(), fragments[fragments.length-1], eObject.eClass().getName());
			} else {
				label = eObject.eClass().getName();
			}

		}
		return label;
	}
	
	public static String getToolTipLabel(Object object){
		String label = "";
	
		if(object instanceof EObject){
			EObject eObject = (EObject) object;
			EObject eContainer = eObject.eContainer();
			for(EAttribute attribute : eObject.eClass().getEAllAttributes()){
				if(attribute.getName().equalsIgnoreCase("name")){
					String path="";
					while(eContainer != null){
						for(EAttribute a : eContainer.eClass().getEAllAttributes()){
							if(a.getName().equalsIgnoreCase("name")){
								path = eContainer.eGet(a) + "." + path;
							}
						}
					
						eContainer = eContainer.eContainer();
					}		
						
					label = String.format("%s%s (%s)", path, (String) eObject.eGet(attribute),  eObject.eClass().getName());
					
					return label;
				}
			}

			for(EAttribute attribute : eObject.eClass().getEAllAttributes()){
				if(attribute.getName().equalsIgnoreCase("id")){
					String path="";
					while(eContainer != null){
						for(EAttribute a : eContainer.eClass().getEAllAttributes()){
							if(a.getName().equalsIgnoreCase("id")){
								path = eContainer.eGet(a) + "." + path;
							}
						}
						eContainer = eContainer.eContainer();
					}
					label = String.format("%s.%s", eObject.eClass().getName(), (String) eObject.eGet(attribute));
					return label;
				}
			}

			if(object instanceof EAnnotation){
				
				EAnnotation eAnnotation = (EAnnotation) object;
				String path="";
				while(eContainer != null){
					for(EAttribute a : eContainer.eClass().getEAllAttributes()){
						if(a.getName().equalsIgnoreCase("name")){
							path = eContainer.eGet(a) + "." + path;
						}
					}
				
					eContainer = eContainer.eContainer();
				}
				label = String.format("%s%s (%s)", path, eAnnotation.getSource(), eAnnotation.eClass().getName());
				return label;
			}

			String[] fragments = EcoreUtil.getURI(eObject).toString().split("\\.");
			String indexFragment = fragments[fragments.length-1];

			if(indexFragment.matches("\\d+")){
				label = String.format("%s.%s (%s)", eObject.eContainingFeature().getName(), fragments[fragments.length-1], eObject.eClass().getName());
			} else {
				label = eObject.eClass().getName();
			}

		}
		return label;
	}
}
