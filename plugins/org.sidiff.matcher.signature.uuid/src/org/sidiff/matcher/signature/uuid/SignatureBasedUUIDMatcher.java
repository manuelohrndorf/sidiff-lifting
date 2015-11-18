package org.sidiff.matcher.signature.uuid;

import java.util.Map;

import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EObject;
import org.sidiff.common.emf.EMFUtil;
import org.sidiff.matcher.SignatureBasedMatcher;
import org.silift.common.util.access.EMFModelAccessEx;

public class SignatureBasedUUIDMatcher extends SignatureBasedMatcher<String> {

	@Override
	public String getName() {
		return "Signature-Based UUID Matcher";
	}

	@Override
	public String getKey() {
		return "SignatureBasedUUID";
	}

	@Override
	public String getDocumentType() {
		return EMFModelAccessEx.GENERIC_DOCUMENT_TYPE;
	}

	@Override
	public boolean canComputeReliability() {
		return false;
	}

	@Override
	public Map<String, Object> getConfigurationOptions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String calculateSignature(EObject eObject) {
		
		String uuid = EMFUtil.getXmiId(eObject);
		
		assert (eObject instanceof EGenericType || uuid != null): eObject + "has no uuid";
		
		return uuid;
	}

}
