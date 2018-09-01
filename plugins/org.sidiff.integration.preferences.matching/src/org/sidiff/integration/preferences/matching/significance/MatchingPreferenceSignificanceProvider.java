package org.sidiff.integration.preferences.matching.significance;

import java.util.Collections;

import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.integration.preferences.significance.IDomainSignificance;
import org.sidiff.matcher.IMatcher;
import org.sidiff.matcher.MatcherUtil;

/**
 * 
 * @author Robert Müller
 *
 */
public class MatchingPreferenceSignificanceProvider implements IDomainSignificance {

	@Override
	public boolean isSignificant(String documentType) {
		// check if any of the matchers supporting this document type are not generic ones
		for(IMatcher matcher : MatcherUtil.getAvailableMatchers(Collections.singleton(documentType))) {
			if(!matcher.getDocumentTypes().contains(EMFModelAccess.GENERIC_DOCUMENT_TYPE)) {
				return true;
			}
		}
		return false;
	}
}
