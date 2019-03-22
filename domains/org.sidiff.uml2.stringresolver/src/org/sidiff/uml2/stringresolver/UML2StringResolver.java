package org.sidiff.uml2.stringresolver;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.NamedElement;
import org.sidiff.common.emf.stringresolver.AbstractStringResolver;

/**
 * An {@link IStringResolver} for UML 2
 * 
 * @author cpietsch
 *
 */
public class UML2StringResolver extends AbstractStringResolver {

	@Override
	public String resolve(EObject eObject) {
		if(eObject instanceof NamedElement){
			NamedElement namedElement = (NamedElement) eObject;
			return String.format("%s [%s]", namedElement.getName(),  namedElement.getClass().getSimpleName());
		}
		return eObject.toString();
	}
}
