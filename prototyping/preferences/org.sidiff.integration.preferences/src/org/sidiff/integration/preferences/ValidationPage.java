/**
 * @author Daniel Roedder
 */
package org.sidiff.integration.preferences;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.sidiff.integration.preferences.fieldeditors.PreferenceField;
import org.sidiff.integration.preferences.interfaces.ISiDiffOrderableTab;
import org.sidiff.integration.preferences.interfaces.ISiDiffValidationPreferenceTab;

/**
 * Preference subpage for validation settings.
 * @author Daniel Roedder, Robert Müller
 *
 */
public class ValidationPage extends TabbedPreferenceFieldPage implements IWorkbenchPreferencePage {

	/**
	 * List to hold all executable extensions for an extension point
	 */
	private List<ISiDiffValidationPreferenceTab> extensionClassList;

	/**
	 * Property Change Event for Engine Page
	 * @param event The event to react to
	 */
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		for (ISiDiffValidationPreferenceTab tab : extensionClassList) {
			tab.propertyChange(event);
		}
	}

	/**
	 * Creates the executable classes of the extensions and adds them to the collection and sorts them.
	 * Then creates all needed Preference Fields by polling the fields from the extensions.
	 */
	@Override
	protected void createPreferenceFields() {
		extensionClassList = new ArrayList<ISiDiffValidationPreferenceTab>();

		for (IConfigurationElement element :
				Platform.getExtensionRegistry().getConfigurationElementsFor(ISiDiffValidationPreferenceTab.EXTENSION_POINT_ID)) {
			try {
				ISiDiffValidationPreferenceTab tab = ((ISiDiffValidationPreferenceTab) element.createExecutableExtension("class"));
				tab.setPreferenceStore(Activator.getDefault().getPreferenceStore());
				extensionClassList.add(tab);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}

		extensionClassList.sort(ISiDiffOrderableTab.COMPARATOR);

		for (ISiDiffValidationPreferenceTab tab : extensionClassList) {
			List<PreferenceField> contents = tab.getTabContent();
			if(contents.isEmpty())
				continue; // empty tabs are skipped
			int tabNumber = this.addTab(tab.getTitle());
			for (PreferenceField field : tab.getTabContent()) {
				this.addField(field, tabNumber);
			}
		}
	}
}
