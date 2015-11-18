/**
 * 
 */
package org.sidiff.matcher.signature.namedelement;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.matcher.SignatureBasedMatcher;

/**
 * @author cpietsch
 *
 */
public class SignatureBasedNamedElementMatcher extends SignatureBasedMatcher<String> {

	
	private Map<String, Object> configuration;

	/**
	 * Initialize named element matcher and start matching.
	 */
	public SignatureBasedNamedElementMatcher() {
		super();
		this.configuration = new HashMap<String, Object>();
		configuration.put("use qualified names", false);
	}
	
	@Override
	public String getName() {
		return "Signature-Based Named Element Matcher";
	}

	@Override
	public String getKey() {
		return "SignatureBasedNamedElement";
	}

	@Override
	public String getDocumentType() {
		return EMFModelAccess.GENERIC_DOCUMENT_TYPE;
	}

	@Override
	public boolean canComputeReliability() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Map<String, Object> getConfigurationOptions() {
		return configuration;
	}

	@Override
	protected String calculateSignature(EObject eObject) {
	
		if(eObject.eClass().getEStructuralFeature("name")!=null){
			boolean useQualifiedNames = (Boolean)configuration.get("use qualified names");
			if(useQualifiedNames){
				return deriveQualifiedName(eObject);
			}
			
			if(eObject.eGet(eObject.eClass().getEStructuralFeature("name")) != null){
				String name = eObject.eGet(eObject.eClass().getEStructuralFeature("name")).toString();
				name += eObject.eClass().getName() + eObject.eResource().getURI().lastSegment();
				return name;
			}
		}
		
		return (eObject.toString()+eObject.eClass().getName());
	}

	private String deriveQualifiedName(EObject eObject){
		
		String featureName = "";
		
		if(eObject instanceof ENamedElement){
			featureName = eObject.eGet(eObject.eClass().getEStructuralFeature("name")).toString();

			assert (featureName != ""): eObject + "has no name";
			
			while (eObject.eContainer() != null){
				eObject = eObject.eContainer();
				featureName = eObject.eGet(eObject.eClass().getEStructuralFeature("name")) + "." + featureName;
			}
		}
		
		return featureName;
	}
	
}
