package org.sidiff.vcmsintegration.preferences.domains.ecore.matching;

import org.sidiff.vcmsintegration.preferences.matching.interfaces.AbstractSiDiffDomainMatchingPreferenceTab;

/**
 * 
 * Class for the Ecore Matching settings.
 * @author Daniel Roedder
 */
public class EcoreMatchingPreferenceTab extends AbstractSiDiffDomainMatchingPreferenceTab {

	/**
	 * @see org.sidiff.vcmintegration.preferences.matching.interfaces.AbstractSiDiffDomainMatchingPreferenceTab#getDocumentType()
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
		return 0;
	}

}
