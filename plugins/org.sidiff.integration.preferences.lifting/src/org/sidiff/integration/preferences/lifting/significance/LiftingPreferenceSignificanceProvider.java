package org.sidiff.integration.preferences.lifting.significance;

import java.util.Collections;

import org.sidiff.difference.lifting.api.util.PipelineUtils;
import org.sidiff.integration.preferences.significance.IDomainSignificance;

/**
 * @author rmueller
 */
public class LiftingPreferenceSignificanceProvider implements IDomainSignificance {

	@Override
	public boolean isSignificant(String documentType) {
		return PipelineUtils.getAvailableRulebases(Collections.singleton(documentType)).size() > 0;
	}
}
