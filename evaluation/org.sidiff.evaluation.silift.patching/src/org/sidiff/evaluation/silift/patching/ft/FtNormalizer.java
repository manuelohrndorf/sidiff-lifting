package org.sidiff.evaluation.silift.patching.ft;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.EMFUtil;
import org.sidiff.evaluation.silift.patching.INormalizer;

public class FtNormalizer implements INormalizer {

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
			Collections.sort(referencedObjects_copy, new FtComparator());
			copyList(referencedObjects_copy, referencedObjects);
		}
	}

	private class FtComparator implements Comparator<EObject> {

		@Override
		public int compare(EObject o1, EObject o2) {
			String str1 = o1.eClass().getName();
			String str2 = o2.eClass().getName();

			EStructuralFeature attrName1 = o1.eClass().getEStructuralFeature("name");
			EStructuralFeature attrName2 = o2.eClass().getEStructuralFeature("name");
			EStructuralFeature attrId1 = o1.eClass().getEStructuralFeature("id");
			EStructuralFeature attrId2 = o2.eClass().getEStructuralFeature("id");

			if (attrName1 != null && attrName2 != null) {
				str1 = (String) o1.eGet(attrName1) + str1;
				str2 = (String) o2.eGet(attrName2) + str2;
			} else if (attrId1 != null && attrId2 != null) {
				str1 += o1.eGet(attrId1) + str1;
				str2 += o2.eGet(attrId2) + str2;				
			}

			return str1.compareTo(str2);
		}
	}

	private static void copyList(List from, List to) {
		to.clear();
		for (Iterator iterator = from.iterator(); iterator.hasNext();) {
			to.add(iterator.next());
		}
	}
}
