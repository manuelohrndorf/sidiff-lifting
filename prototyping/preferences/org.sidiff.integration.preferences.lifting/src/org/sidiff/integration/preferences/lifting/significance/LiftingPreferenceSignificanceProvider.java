package org.sidiff.integration.preferences.lifting.significance;

import org.sidiff.difference.lifting.api.util.PipelineUtils;
import org.sidiff.integration.preferences.interfaces.IPreferenceDomainSignificanceProvider;

/**
 * 
 * @author Robert Müller
 *
 */
public class LiftingPreferenceSignificanceProvider implements IPreferenceDomainSignificanceProvider {

	@Override
	public boolean isSignificant(String documentType) {
		return PipelineUtils.getAvailableRulebases(documentType).size() > 0;
	}
}
