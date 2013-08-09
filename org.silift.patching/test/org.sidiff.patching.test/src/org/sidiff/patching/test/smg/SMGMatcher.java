package org.sidiff.patching.test.smg;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.difference.matcher.BaseMatcher;
import org.sidiff.difference.symmetric.Correspondence;

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

	@Override
	public boolean canHandle(Resource modelA, Resource modelB) {
		if (correspondences != null)
			return true;
		else
			return false;
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

}
