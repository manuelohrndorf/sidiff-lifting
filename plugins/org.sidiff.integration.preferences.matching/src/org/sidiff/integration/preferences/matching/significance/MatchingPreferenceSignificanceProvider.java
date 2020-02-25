package org.sidiff.integration.preferences.matching.significance;

import java.util.Collections;

import org.sidiff.integration.preferences.significance.IDomainSignificance;
import org.sidiff.matcher.IMatcher;

/**
 * @author rmueller
 */
public class MatchingPreferenceSignificanceProvider implements IDomainSignificance {

	@Override
	public boolean isSignificant(String documentType) {
		// check if any of the matchers supporting this document type are not generic ones
		return !IMatcher.MANAGER.getExtensions(Collections.singleton(documentType), false).isEmpty();
	}
}
