package org.sidiff.difference.testcase.util;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;

/**
 * 
 * @author cpietsch
 *
 */
public class MetaModelAccess {

	public static EClass getFirstConcreteSubClass(EClass superClass){
		if(superClass.isAbstract()){
			EPackage ePackage = superClass.getEPackage();
			for(EClassifier eClassifier : ePackage.getEClassifiers()){
				if(eClassifier instanceof EClass){
					EClass eClass = (EClass) eClassifier;
					if(!eClass.isAbstract() && eClass.getESuperTypes().contains(superClass)){
						return eClass;
					}
				}
			}
		}
		return superClass;
	}
	
}
