package org.sidiff.patching.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

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

		//TODO if needed this should be implemented
	}

	private static List<EObject> getAllContentsAsList(Resource model) {
		List<EObject> result = new ArrayList<EObject>();
		for (EObject root : model.getContents()) {
			for (Iterator<EObject> iterator = root.eAllContents(); iterator
					.hasNext();) {
				result.add(iterator.next());
			}
		}

		return result;
	}

}
