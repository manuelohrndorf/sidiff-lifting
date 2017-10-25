package org.sidiff.vcmsintegration.preferences.domains.uml.patching;

import org.sidiff.vcmsintegration.preferences.patching.interfaces.AbstractSiDiffDomainPatchingPreferenceTab;

/**
 * Class for the UML Patching Settings
 * @author Daniel Roedder
 *
 */
public class UmlPatchingPreferenceTab extends AbstractSiDiffDomainPatchingPreferenceTab {

	/**
	 * @see org.sidiff.vcmsintegration.preferences.interfaces.ISiDiffOrderableTab#getStepInPipeline()
	 */
	@Override
	public int getStepInPipeline() {
		return 3;
	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.patching.interfaces.AbstractSiDiffDomainPatchingPreferenceTab#getDocumentType()
	 */
	@Override
	public String getDocumentType() {
		return "http://www.eclipse.org/uml2/5.0.0/UML";
	}

}
