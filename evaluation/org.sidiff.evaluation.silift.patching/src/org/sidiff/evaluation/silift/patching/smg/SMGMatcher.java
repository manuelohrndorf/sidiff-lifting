package org.sidiff.evaluation.silift.patching.smg;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.matcher.BaseMatcher;
import org.sidiff.matching.model.Correspondence;

public class SMGMatcher extends BaseMatcher {

	private List<Correspondence> correspondences;

	public SMGMatcher(List<Correspondence> correspondences) {
		this.correspondences = correspondences;
	}

	public static final String KEY = "SMGMatcher";

	@Override
	public String getName() {
		return "SMG Matcher";
	}

	@Override
	public String getKey() {
		return KEY;
	}

	/**
	 * We overwrite default canHandle behavior which only checks for documentType.
	 */
	@Override
	public boolean canHandle(Resource modelA, Resource modelB) {
		if (correspondences != null)
			return true;
		else
			return false;
	}
	
	@Override
	public String getDocumentType() {
		return EMFModelAccess.GENERIC_DOCUMENT_TYPE;
	}

	@Override
	protected boolean isCorresponding(EObject elementA, EObject elementB) {
		for (Correspondence correspondence : correspondences) {
			if (correspondence.getObjA().equals(elementA) && correspondence.getObjB().equals(elementB)) {
				return true;
			}
		}
		return false;
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

}
