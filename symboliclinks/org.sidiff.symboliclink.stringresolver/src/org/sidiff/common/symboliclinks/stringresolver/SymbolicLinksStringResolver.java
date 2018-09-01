package org.sidiff.common.symboliclinks.stringresolver;

import java.util.Collections;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.sidiff.common.stringresolver.IStringResolver;
import org.silift.difference.symboliclink.SymbolicLinkObject;

/**
 * An {@link IStringResolver} for SymbolicLinks
 * 
 * @author cpietsch
 *
 */
public class SymbolicLinksStringResolver implements IStringResolver {

	private final static String DOC_TYPE = "http://symboliclink/1.0";
	private final static String KEY = "SymbolicLinksStringResolver";
	private final static String NAME = "Symbolic Links String Resolver";

	@Override
	public Set<String> getDocumentTypes() {
		return Collections.singleton(DOC_TYPE);
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

	@Override
	public String resolveQualified(EObject eObject) {
		// TODO Auto-generated method stub
		return null;
	}

}
