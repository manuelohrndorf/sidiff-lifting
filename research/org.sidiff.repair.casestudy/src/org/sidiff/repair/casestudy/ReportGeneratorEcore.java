package org.sidiff.repair.casestudy;

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.repair.casestudy.util.EcoreUtil;

public class ReportGeneratorEcore extends ReportGenerator {

	@Override
	protected String[] getFileFilters() {
		return new String[] { "ecore" };
	}

	@Override
	protected long maxModelFileLength() {
		// return 10000L;
		return Long.MAX_VALUE;
	}

	@Override
	protected List<String> getCharacterizingMessageFragments() {
		return EcoreUtil.getCharacterizingMessageFragments();
	}

	@Override
	protected boolean exists(EObject element, Resource model) {
		if (element instanceof ENamedElement) {
			ENamedElement namedElement = (ENamedElement) element;

			for (Iterator<EObject> iterator = model.getAllContents(); iterator.hasNext();) {
				EObject obj = iterator.next();
				if (obj instanceof ENamedElement) {
					ENamedElement namedObj = (ENamedElement) obj;

					if ((namedElement.eClass() == namedObj.eClass())
							&& (namedElement.getName().equals(namedObj.getName()))) {
						return true;
					}
				}
			}
		}

		return false;
	}

}
