package org.sidiff.integration.preferences.domains;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EPackage;
import org.sidiff.integration.preferences.AbstractNestedPreferencePage;
import org.sidiff.integration.preferences.PreferenceFieldPage;
import org.sidiff.integration.preferences.domains.util.DomainSignificanceUtil;

/**
 * 
 * @author Robert Müller
 *
 */
public class DomainSettingsIndexPage extends AbstractNestedPreferencePage {

	private List<String> siginficantDocumentTypes;

	@Override
	protected List<PreferenceFieldPage> createSubPages() {
		List<PreferenceFieldPage> pages = new LinkedList<PreferenceFieldPage>();
		siginficantDocumentTypes = DomainSignificanceUtil.getSignificantDocumentTypes();
		for(String documentType : siginficantDocumentTypes) {
			pages.add(new DomainSpecificSettingsPage(
					EPackage.Registry.INSTANCE.getEPackage(documentType).getName(), documentType));
		}
		return pages;
	}

	@Override
	protected void onPageChanged(int newIndex) {
		String documentType = siginficantDocumentTypes.get(newIndex);
		setMessage("Domain: " + EPackage.Registry.INSTANCE.getEPackage(documentType).getName() + " [" + documentType + "]");
	}
}
