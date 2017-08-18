package org.sidiff.ecore.stringresolver;


import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sidiff.common.stringresolver.AbstractStringResolver;
import org.sidiff.common.stringresolver.IStringResolver;

/**
 * An {@link IStringResolver} for Ecore
 * 
 * @author cpietsch
 *
 */
public class EcoreStringResolver extends AbstractStringResolver {

	private final static String DOC_TYPE = "http://www.eclipse.org/emf/2002/Ecore";
	
	private final static String KEY = "EcoreStringResolver";
	
	private final static String NAME = "Ecore String Resolver";

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
		String res = eObject.toString();
		
		if (eObject instanceof EAnnotation) {
			EAnnotation eAnnotaion = (EAnnotation) eObject;
			res = String.format("%s [%s]", eAnnotaion.getSource(), eAnnotaion.eClass().getName());
		} else if (eObject instanceof ENamedElement) {
			ENamedElement eNamedElement = (ENamedElement) eObject;
			res = String.format("%s [%s]", eNamedElement.getName(), eNamedElement.eClass().getName());
			if (eObject instanceof EAttribute) {
				EAttribute eAttribute = (EAttribute) eObject;
				if (eAttribute.isID()) {
					res += ": ID";
				}
			}
		} else {
			String[] fragments = EcoreUtil.getURI(eObject).toString().split("\\.");
			String indexFragment = fragments[fragments.length - 1];

			if (indexFragment.matches("\\d+")) {
				if (eObject.eContainingFeature() != null) {
					res = String.format("%s.%s [%s]", eObject.eContainingFeature().getName(),
							fragments[fragments.length - 1], eObject.eClass().getName());
				} else {
					res = String.format("%s [%s]",
							fragments[fragments.length - 1], eObject.eClass().getName());
				}
			} else {
				res = eObject.eClass().getName();
			}
		}
		return res;
	}
}
