package org.sidiff.patching.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;

public class EcoreNormalizer implements INormalizer {

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

			// params of an EOperation are "really" ordered
			if (eReference == EcorePackage.eINSTANCE.getEOperation_EParameters()) {
				continue;
			}

			// do not consider generics
			if (eReference == EcorePackage.eINSTANCE.getEClassifier_ETypeParameters()
					|| eReference == EcorePackage.eINSTANCE.getEOperation_ETypeParameters()
					|| eReference == EcorePackage.eINSTANCE.getEOperation_EGenericExceptions()
					|| eReference == EcorePackage.eINSTANCE.getEClass_EGenericSuperTypes()
					|| eReference == EcorePackage.eINSTANCE.getETypedElement_EGenericType()) {
				continue;
			}

			List referencedObjects = (List) object.eGet(eReference);
			// Workaround: prevent the following exception during sorting:
			// java.lang.IllegalArgumentException The 'no duplicates'
			// constraint is violated
			// ==> Thus we sort on the copied list
			List referencedObjects_copy = new ArrayList(referencedObjects.size());
			copyList(referencedObjects, referencedObjects_copy);

			if (eReference == EcorePackage.eINSTANCE.getEModelElement_EAnnotations()) {
				Collections.sort(referencedObjects_copy, new EAnnotationComparator());
			} else if (eReference == EcorePackage.eINSTANCE.getEAnnotation_Details()) {
				Collections.sort(referencedObjects_copy, new EMapEntryComparator());
			} else {
				Collections.sort(referencedObjects_copy, new ENamedElementComparator());
			}

			copyList(referencedObjects_copy, referencedObjects);
		}
	}

	private class ENamedElementComparator implements Comparator<ENamedElement> {

		@Override
		public int compare(ENamedElement o1, ENamedElement o2) {
			return o1.getName().compareTo(o2.getName());
		}
	}

	private class EAnnotationComparator implements Comparator<EAnnotation> {

		@Override
		public int compare(EAnnotation o1, EAnnotation o2) {
			String signature1 = o1.getSource();
			for (Entry mapEntry : o1.getDetails()) {
				signature1 += mapEntry.getKey().toString() + mapEntry.getValue().toString();
			}
			String signature2 = o2.getSource();
			for (Entry mapEntry : o2.getDetails()) {
				signature2 += mapEntry.getKey().toString() + mapEntry.getValue().toString();
			}

			return signature1.compareTo(signature2);
		}
	}

	private class EMapEntryComparator implements Comparator<Entry> {

		@Override
		public int compare(Entry o1, Entry o2) {
			String signature1 = o1.getKey().toString() + o1.getValue().toString();
			String signature2 = o2.getKey().toString() + o2.getValue().toString();

			return signature1.compareTo(signature2);
		}

	}

	private static List<EObject> getAllContentsAsList(Resource model) {
		List<EObject> result = new ArrayList<EObject>();
		for (EObject root : model.getContents()) {
			for (Iterator<EObject> iterator = root.eAllContents(); iterator.hasNext();) {
				result.add(iterator.next());
			}
		}

		return result;
	}

	private static void copyList(List from, List to) {
		to.clear();
		for (Iterator iterator = from.iterator(); iterator.hasNext();) {
			to.add(iterator.next());
		}
	}
}
