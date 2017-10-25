package org.sidiff.vcmsintegration.preferences.domains.uml.matching;

import org.sidiff.vcmsintegration.preferences.matching.interfaces.AbstractSiDiffDomainMatchingPreferenceTab;

/**
 * 
 * Class for the UML Matching settings.
 * @author Daniel Roedder
 */
public class UmlMatchingPreferenceTab extends AbstractSiDiffDomainMatchingPreferenceTab {

	/**
	 * @see org.sidiff.vcmsintegration.preferences.interfaces.ISiDiffOrderableTab#getStepInPipeline()
	 */
	@Override
	public int getStepInPipeline() {
		return 0;
	}

	/**
	 * @see org.sidiff.vcmintegration.preferences.matching.interfaces.AbstractSiDiffDomainMatchingPreferenceTab#getDocumentType()
	 */
	@Override
	public String getDocumentType() {
		return "http://www.eclipse.org/uml2/5.0.0/UML";
	}

}
