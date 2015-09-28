package org.sidiff.uml2.stringresolver;
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

	private final static String DOC_TYPE = UMLPackage.eNS_URI;
	
	private final static String KEY = "UML2StringResolver";
	
	private final static String NAME = "UML2 String Resolver";

	@Override
	public String resolve(EObject eObject) {
		String res = eObject.toString();
		if(eObject instanceof NamedElement){
			NamedElement namedElement = (NamedElement) eObject;
			res = String.format("%s [%s]", namedElement.getName(),  namedElement.getClass().getSimpleName());
		}else{
			//...
		}
		return res;
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
}
