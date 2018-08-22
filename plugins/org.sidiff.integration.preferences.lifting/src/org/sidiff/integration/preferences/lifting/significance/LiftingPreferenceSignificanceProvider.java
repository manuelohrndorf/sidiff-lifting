package org.sidiff.integration.preferences.lifting.significance;

import java.util.HashSet;
import java.util.Set;

import org.sidiff.difference.lifting.api.util.PipelineUtils;
import org.sidiff.integration.preferences.significance.IPreferenceDomainSignificanceProvider;

/**
 * 
 * @author Robert Müller
 *
 */
public class LiftingPreferenceSignificanceProvider implements IPreferenceDomainSignificanceProvider {

	@Override
	public boolean isSignificant(String documentType) {
		Set<String> documentTypes = new HashSet<String>();
		return PipelineUtils.getAvailableRulebases(documentTypes).size() > 0;
	}
}
