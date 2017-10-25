package org.sidiff.vcmsintegration.preferences.domains.ecore.patching;

import org.sidiff.vcmsintegration.preferences.patching.interfaces.AbstractSiDiffDomainPatchingPreferenceTab;
/**
 * 
 * Class for the Ecore Patching settings.
 * @author Daniel Roedder
 */
public class EcorePatchingPreferenceTab extends AbstractSiDiffDomainPatchingPreferenceTab {

	/**
	 * @see org.sidiff.vcmsintegration.preferences.patching.interfaces.AbstractSiDiffDomainPatchingPreferenceTab#getDocumentType()
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
		return 3;
	}

}
