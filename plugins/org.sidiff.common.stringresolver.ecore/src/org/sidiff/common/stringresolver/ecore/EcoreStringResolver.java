package org.sidiff.common.stringresolver.ecore;


import org.eclipse.emf.ecore.EObject;
import org.sidiff.common.stringresolver.IStringResolver;

/**
 * An {@link IStringResolver} for Ecore
 * 
 * @author cpietsch
 *
 */
public class EcoreStringResolver implements IStringResolver {

	private final static String DOC_TYPE = "http://www.eclipse.org/emf/2002/Ecore";
	
	private final static String KEY = "EcoreStringResolver";
	
	private final static String NAME = "Ecore String Resolver";
	
	@Override
	public boolean canHandleDocType(String docType) {
		if(docType.equals(DOC_TYPE)){
			return true;
		}else{
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
		String res = "";
		if(eObject.eClass().getEStructuralFeature("name") != null){
			res += eObject.eGet(eObject.eClass().getEStructuralFeature("name"));
		}else{
			res += eObject;
		}
		
		return res;
	}

}
