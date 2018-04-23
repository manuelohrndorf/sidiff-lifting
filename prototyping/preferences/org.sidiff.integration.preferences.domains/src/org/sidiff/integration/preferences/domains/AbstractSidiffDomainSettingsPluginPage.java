package org.sidiff.integration.preferences.domains;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.sidiff.integration.preferences.TabbedPreferenceFieldPage;
import org.sidiff.integration.preferences.domains.interfaces.ISiDiffDomainPreferenceTab;
import org.sidiff.integration.preferences.fieldeditors.PreferenceField;
import org.sidiff.integration.preferences.interfaces.ISiDiffOrderableTab;

/**
 * 
 * Abstract class to create the {@link org.sidiff.integration.preferences.TabbedPreferenceFieldPage} for a specific domain.
 * @author Daniel Roedder, Robert Müller
 */
public abstract class AbstractSidiffDomainSettingsPluginPage extends TabbedPreferenceFieldPage 
															implements IWorkbenchPreferencePage {

	/**
	 * @see org.sidiff.integration.preferences.TabbedPreferenceFieldPage#createPreferenceFields()
	 */
	@Override
	protected void createPreferenceFields() {
		List<ISiDiffDomainPreferenceTab> extensionClassList = new ArrayList<ISiDiffDomainPreferenceTab>();
		for (IConfigurationElement element :
				Platform.getExtensionRegistry().getConfigurationElementsFor(ISiDiffDomainPreferenceTab.EXTENSION_POINT_ID)) {
			try {
				ISiDiffDomainPreferenceTab tab = (ISiDiffDomainPreferenceTab)element.createExecutableExtension("class");
				tab.setDocumentType(getDocumentType());
				extensionClassList.add(tab);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}

		extensionClassList.sort(ISiDiffOrderableTab.COMPARATOR);

		for (ISiDiffDomainPreferenceTab tab : extensionClassList) {
			List<PreferenceField> contents = tab.getTabContent();
			if(contents.isEmpty())
				continue; // empty tabs are skipped
			int tabNumber = this.addTab(tab.getTitle());
			for (PreferenceField field : tab.getTabContent()) {
				this.addField(field, tabNumber);
			}
		}
	}

	/**
	 * Used to return the document type string of the concrete domain settings page.
	 * Subclasses must implement.
	 * @return The document type as String
	 */
	protected abstract String getDocumentType();
}
