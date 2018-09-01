package org.sidiff.uml2.stringresolver;
import java.util.Collections;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.UMLPackage;
import org.sidiff.common.stringresolver.AbstractStringResolver;
import org.sidiff.common.stringresolver.IStringResolver;

/**
 * An {@link IStringResolver} for UML 2
 * 
 * @author cpietsch
 *
 */
public class UML2StringResolver extends AbstractStringResolver {

	private final static String KEY = "UML2StringResolver";
	private final static String NAME = "UML2 String Resolver";

	@Override
	public String resolve(EObject eObject) {
		if(eObject instanceof NamedElement){
			NamedElement namedElement = (NamedElement) eObject;
			return String.format("%s [%s]", namedElement.getName(),  namedElement.getClass().getSimpleName());
		}
		return eObject.toString();
	}

	@Override
	public Set<String> getDocumentTypes() {
		return Collections.singleton(UMLPackage.eNS_URI);
	}
	
	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getName() {
		return NAME;
	}
}
