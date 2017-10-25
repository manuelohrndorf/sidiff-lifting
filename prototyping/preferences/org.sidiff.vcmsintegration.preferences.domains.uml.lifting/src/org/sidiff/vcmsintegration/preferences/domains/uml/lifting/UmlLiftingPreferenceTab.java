package org.sidiff.vcmsintegration.preferences.domains.uml.lifting;

import org.sidiff.vcmsintegration.preferences.lifting.interfaces.AbstractSiDiffDomainLiftingPreferenceTab;

/**
 * 
 * Class for the UML Lifting settings.
 * @author Daniel Roedder
 */
public class UmlLiftingPreferenceTab extends AbstractSiDiffDomainLiftingPreferenceTab {

	/**
	 * @see org.sidiff.vcmsintegration.preferences.interfaces.ISiDiffOrderableTab#getStepInPipeline()
	 */
	@Override
	public int getStepInPipeline() {
		return 2;
	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.lifting.interfaces.AbstractSiDiffDomainLiftingPreferenceTab#getDocumentType()
	 */
	@Override
	public String getDocumentType() {
		return "http://www.eclipse.org/uml2/5.0.0/UML";
	}

}
