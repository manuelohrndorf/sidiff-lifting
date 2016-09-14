package org.sidiff.ecore.difference.technical;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.sidiff.difference.technical.AbstractTechnicalDifferenceBuilder;

/**
 * Filters technical stuff but does not filter types related to Ecore Generics.
 * 
 * @author kehrer
 */
public class TechnicalDifferenceBuilderEcoreGenerics extends AbstractTechnicalDifferenceBuilder {

	@Override
	protected boolean hasCorrespondingObjectInA(EObject objectInB, Set<EObject> objectsInA) {
		
		if (super.hasCorrespondingObjectInA(objectInB, objectsInA)) {
			return true;
		}
		
		// Compare Ecore-Types:
		if (objectInB instanceof EGenericType) {
			EClassifier rawTypeB = ((EGenericType) objectInB).getERawType();
			
			for (EObject objectInA : objectsInA) {
				if (objectInA instanceof EGenericType) {
					EClassifier rawTypeA = ((EGenericType) objectInA).getERawType();
					
					if (rawTypeB.equals(rawTypeA) || super.hasCorrespondingObjectInA(rawTypeB, Collections.singleton((EObject) rawTypeA))) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	@Override
	protected boolean hasCorrespondingObjectInB(EObject objectInA, Set<EObject> objectsInB) {
		
		if (super.hasCorrespondingObjectInB(objectInA, objectsInB)) {
			return true;
		}
		
		// Compare Ecore-Types:
		if (objectInA instanceof EGenericType) {
			EClassifier rawTypeA = ((EGenericType) objectInA).getERawType();
			
			for (EObject objectInB : objectsInB) {
				if (objectInB instanceof EGenericType) {
					EClassifier rawTypeB = ((EGenericType) objectInB).getERawType();
					
					if (rawTypeA.equals(rawTypeB) || super.hasCorrespondingObjectInB(rawTypeA, Collections.singleton((EObject) rawTypeB))) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	@Override
	protected Set<EClass> getUnconsideredNodeTypes() {
		Set<EClass> unconsideredNodeTypes = new HashSet<EClass>();

		// technical
		unconsideredNodeTypes.add(EcorePackage.eINSTANCE.getEFactory());

		return unconsideredNodeTypes;
	}

	@Override
	protected Set<EReference> getUnconsideredEdgeTypes() {
		Set<EReference> unconsideredEdgeTypes = new HashSet<EReference>();

		// technical
		unconsideredEdgeTypes.add(EcorePackage.eINSTANCE.getEFactory_EPackage());
		unconsideredEdgeTypes.add(EcorePackage.eINSTANCE.getEPackage_EFactoryInstance());

		return unconsideredEdgeTypes;
	}

	@Override
	protected Set<EAttribute> getUnconsideredAttributeTypes() {
		Set<EAttribute> unconsideredAttributeTypes = new HashSet<EAttribute>();

		// technical
		unconsideredAttributeTypes.add(EcorePackage.eINSTANCE.getEClassifier_InstanceClassName());
		unconsideredAttributeTypes.add(EcorePackage.eINSTANCE.getEClassifier_InstanceTypeName());

		return unconsideredAttributeTypes;
	}

	@Override
	protected String getObjectName(EObject obj) {		
		return obj.toString();
	}

	@Override
	public String getDocumentType() {
		return EcorePackage.eNS_URI;
	}

	@Override
	public String getName() {
		return "Ecore Technical Difference Builder (with Generics)";
	}
}
