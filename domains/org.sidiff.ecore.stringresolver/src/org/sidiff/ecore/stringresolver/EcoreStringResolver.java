package org.sidiff.ecore.stringresolver;


import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sidiff.common.stringresolver.AbstractStringResolver;

/**
 * An {@link IStringResolver} for Ecore
 * 
 * @author cpietsch
 *
 */
public class EcoreStringResolver extends AbstractStringResolver {

	@Override
	public String resolve(EObject eObject) {		
		if (eObject instanceof EAnnotation) {
			EAnnotation eAnnotaion = (EAnnotation) eObject;
			return String.format("%s [%s]", eAnnotaion.getSource(), eAnnotaion.eClass().getName());
		} else if (eObject instanceof ENamedElement) {
			ENamedElement eNamedElement = (ENamedElement) eObject;
			String res = String.format("%s [%s]", eNamedElement.getName(), eNamedElement.eClass().getName());
			if (eObject instanceof EAttribute) {
				EAttribute eAttribute = (EAttribute) eObject;
				if (eAttribute.isID()) {
					res += ": ID";
				}
			}
			return res;
		} else {
			String[] fragments = EcoreUtil.getURI(eObject).toString().split("\\.");
			String indexFragment = fragments[fragments.length - 1];

			if (indexFragment.matches("\\d+")) {
				if (eObject.eContainingFeature() != null) {
					return String.format("%s.%s [%s]", eObject.eContainingFeature().getName(),
							fragments[fragments.length - 1], eObject.eClass().getName());
				}
				return String.format("%s [%s]",
					fragments[fragments.length - 1], eObject.eClass().getName());
			}
			return eObject.eClass().getName();
		}
	}
}
