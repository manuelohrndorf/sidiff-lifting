package org.sidiff.common.symboliclinks.stringresolver;

import org.eclipse.emf.ecore.EObject;
import org.sidiff.common.emf.stringresolver.AbstractStringResolver;
import org.silift.difference.symboliclink.SymbolicLinkObject;

/**
 * An {@link IStringResolver} for SymbolicLinks
 * 
 * @author cpietsch
 *
 */
public class SymbolicLinksStringResolver extends AbstractStringResolver {

	@Override
	public String resolve(EObject eObject) {
		String res = "";
		if(eObject instanceof SymbolicLinkObject){
			SymbolicLinkObject symbolicLinkObject = (SymbolicLinkObject)eObject;
			res += "(" + symbolicLinkObject.getType().eGet(symbolicLinkObject.getType().eClass().getEStructuralFeature("name")) + ") ";
			if(symbolicLinkObject.eClass().getEStructuralFeature("name") != null){
				res += symbolicLinkObject.eGet(symbolicLinkObject.eClass().getEStructuralFeature("name"));
			}
		}
		return res;
	}
}
