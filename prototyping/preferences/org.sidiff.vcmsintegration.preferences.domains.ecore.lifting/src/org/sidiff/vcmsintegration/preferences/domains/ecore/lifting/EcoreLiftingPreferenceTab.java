package org.sidiff.vcmsintegration.preferences.domains.ecore.lifting;

import org.sidiff.vcmsintegration.preferences.lifting.interfaces.AbstractSiDiffDomainLiftingPreferenceTab;

/**
 * 
 * Class for the Ecore Lifting settings.
 * @author Daniel Roedder
 */
public class EcoreLiftingPreferenceTab extends AbstractSiDiffDomainLiftingPreferenceTab {

	/**
	 * @see org.sidiff.vcmsintegration.preferences.lifting.interfaces.AbstractSiDiffDomainLiftingPreferenceTab#getDocumentType()
	 */
	@Override
	public String getDocumentType() {
		return "http://www.eclipse.org/emf/2002/Ecore";
	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.interfaces.ISiDiffOrderableTab#getStepInPipeline()
	 */
	@Override
	public int getStepInPipeline() {
		return 2;
	}

}
