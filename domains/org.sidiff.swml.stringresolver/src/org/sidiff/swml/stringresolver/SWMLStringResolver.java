package org.sidiff.swml.stringresolver;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.refactor.examples.simpleWebModelingLanguage.DataLayer;
import org.eclipse.emf.refactor.examples.simpleWebModelingLanguage.HypertextLayer;
import org.eclipse.emf.refactor.examples.simpleWebModelingLanguage.Link;
import org.eclipse.emf.refactor.examples.simpleWebModelingLanguage.Page;
import org.eclipse.emf.refactor.examples.simpleWebModelingLanguage.WebModel;
import org.sidiff.common.stringresolver.IStringResolver;

/**
 * An {@link IStringResolver} for SWML
 * 
 * @author kehrer
 *
 */
public class SWMLStringResolver implements IStringResolver {

	private final static String DOC_TYPE = "http://www.eclipse.org/emf/refactor/examples/SimpleWebModelingLanguage";

	private final static String KEY = "SWMLStringResolver";

	private final static String NAME = "SWML String Resolver";

	@Override
	public boolean canHandleDocType(String docType) {
		if (docType.equals(DOC_TYPE)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String getDocType() {
		return DOC_TYPE;
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String resolve(EObject eObject) {
		// "Singleton" elements
		if (eObject instanceof HypertextLayer || eObject instanceof DataLayer || eObject instanceof WebModel) {
			return "<" + eObject.eClass().getName() + ">";
		}

		// Links
		if (eObject instanceof Link) {
			Page src = (Page) ((Link) eObject).eContainer();
			Page tgt = ((Link) eObject).getTarget();

			return getName(src) + "->" + getName(tgt);
		}

		// Named Elements
		return getName(eObject);
	}

	@Override
	public String resolveQualified(EObject eObject) {
		String res = resolve(eObject);
		EObject eContainer = eObject.eContainer();
		while (eContainer != null) {
			res = resolve(eContainer) + "." + res;
			eContainer = eContainer.eContainer();
		}
		return res;
	}

	private String getName(EObject namedElement) {
		EStructuralFeature attrName = namedElement.eClass().getEStructuralFeature("name");
		if (attrName != null && attrName instanceof EAttribute) {
			return (String) namedElement.eGet(attrName);
		} else {
			return "<anonymous>";
		}
	}

}
