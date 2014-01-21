package org.sidiff.patching.test.sa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.EMFUtil;
import org.sidiff.patching.test.INormalizer;

public class SaNormalizer implements INormalizer {

	@Override
	public void normalize(Resource model) {
		// we have to call sort() twice because of dependencies
		sort(model);
		sort(model);
	}
	
	private void sort(Resource model) {
		List<EObject> allContents = EMFUtil.createListFromEAllContents(model);
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

			List referencedObjects = (List) object.eGet(eReference);
			// Workaround: prevent the following exception during sorting:
			// java.lang.IllegalArgumentException The 'no duplicates'
			// constraint is violated
			// ==> Thus we sort on the copied list
			List referencedObjects_copy = new ArrayList(referencedObjects.size());
			copyList(referencedObjects, referencedObjects_copy);
			Collections.sort(referencedObjects_copy, new SaComparator());
			copyList(referencedObjects_copy, referencedObjects);
		}
	}

	private class SaComparator implements Comparator<EObject> {

		@Override
		public int compare(EObject o1, EObject o2) {
			String name1 = "";
			String name2 = "";
			
			EStructuralFeature attrName = o1.eClass().getEStructuralFeature("name");
			if (attrName != null && attrName instanceof EAttribute) {
				name1 = (String) o1.eGet(attrName);
			}
			attrName = o2.eClass().getEStructuralFeature("name");
			if (attrName != null && attrName instanceof EAttribute) {
				name2 = (String) o2.eGet(attrName);
			}
				
			return name1.compareTo(name2);
		}
	}

	private static void copyList(List from, List to) {
		to.clear();
		for (Iterator iterator = from.iterator(); iterator.hasNext();) {
			to.add(iterator.next());
		}
	}
}
