package org.sidiff.vcmsintegration.preferences.domains.uml.difference;

import org.sidiff.vcmsintegration.preferences.difference.interfaces.AbstractSiDiffDomainDifferencePreferenceTab;

/**
 * 
 * Class for the UML Difference Settings
 * @author Daniel Roedder
 */
public class UmlDifferencePreferenceTab extends AbstractSiDiffDomainDifferencePreferenceTab {

	/**
	 * @see org.sidiff.vcmsintegration.preferences.interfaces.ISiDiffOrderableTab#getStepInPipeline()
	 */
	@Override
	public int getStepInPipeline() {
		return 1;
	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.difference.interfaces.AbstractSiDiffDomainDifferencePreferenceTab#getDocumentType()
	 */
	@Override
	public String getDocumentType() {
		return "http://www.eclipse.org/uml2/5.0.0/UML";
	}

}
