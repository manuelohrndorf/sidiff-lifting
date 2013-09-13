package org.sidiff.patching.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;

public class SysMLNormalizer implements INormalizer {

	@Override
	public void normalize(Resource model) {
		// we have to call sort() twice because of dependencies
		sort(model);
		sort(model);
	}

	private void sort(Resource model) {
		List<EObject> allContents = getAllContentsAsList(model);
		for (int i = 0; i < allContents.size(); i++) {
			EObject object = allContents.get(i);
			sort(object);
		}
	}

	private void sort(EObject object) {
		for (EReference eReference : object.eClass().getEAllReferences()) {
			// never interesting
			if (!eReference.isChangeable() || eReference.isContainer() || !eReference.isMany()
					|| eReference.isTransient() || eReference.isVolatile()) {
				continue;
			}

			// these reference types are "really" ordered
			if (eReference == UMLPackage.eINSTANCE.getBehavioralFeature_OwnedParameter()
					|| eReference == UMLPackage.eINSTANCE.getBehavior_OwnedParameter()) {
				continue;
			}

			EList referencedObjects = (EList) object.eGet(eReference);
			bubbleSort(referencedObjects);
		}
	}

	private static List<EObject> getAllContentsAsList(Resource model) {
		List<EObject> result = new ArrayList<EObject>();
		for (EObject root : model.getContents()) {
			result.add(root);
			for (Iterator<EObject> iterator = root.eAllContents(); iterator.hasNext();) {
				result.add(iterator.next());
			}
		}

		return result;
	}

	private static void bubbleSort(EList list) {
		int j;
		boolean flag = true; // set flag to true to begin first pass

		while (flag) {
			flag = false; // set flag to false awaiting a possible swap
			for (j = 0; j < list.size() - 1; j++) {
				EObject obj_j = (EObject) list.get(j);
				EObject obj_jplus1 = (EObject) list.get(j + 1);

				if (compare(obj_j, obj_jplus1) > 0) {
					list.move(j + 1, j);
					flag = true; // shows a swap occurred
				}
			}
		}
	}

	private static int compare(EObject obj1, EObject obj2){
		if (obj1 instanceof Association && obj2 instanceof Association){
			Association aObj1 = (Association) obj1;
			Association aObj2 = (Association) obj2;
			String s1 = "" + aObj1.getName();
			for (Property p : aObj1.getMemberEnds()) {
				String n = "" + p.getName();
				s1 += n;
			}
			String s2 = "" + aObj2.getName();
			for (Property p : aObj2.getMemberEnds()) {
				String n = "" + p.getName();
				s2 += n;
			}
			return s1.compareTo(s2);
		}
		
		if (obj1 instanceof NamedElement && obj2 instanceof NamedElement){
			NamedElement nObj1 = (NamedElement) obj1;
			NamedElement nObj2 = (NamedElement) obj2;
			if(nObj1.getName()!=null && nObj2.getName()!=null)
				return nObj1.getName().compareTo(nObj2.getName());
			else
				return 0;
		}
		
		// default: we don't know how to compare
		return 0;
	}

}
