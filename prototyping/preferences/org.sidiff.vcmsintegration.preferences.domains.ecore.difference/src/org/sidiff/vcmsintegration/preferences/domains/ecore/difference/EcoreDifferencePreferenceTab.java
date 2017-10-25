package org.sidiff.vcmsintegration.preferences.domains.ecore.difference;

import org.sidiff.vcmsintegration.preferences.difference.interfaces.AbstractSiDiffDomainDifferencePreferenceTab;

/**
 * 
 * Class for the Ecore Difference Settings
 * @author Daniel Roedder
 */
public class EcoreDifferencePreferenceTab extends AbstractSiDiffDomainDifferencePreferenceTab {

	/**
	 * @see org.sidiff.vcmsintegration.preferences.difference.interfaces.AbstractSiDiffDomainDifferencePreferenceTab#getDocumentType()
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
		return 1;
	}

}
