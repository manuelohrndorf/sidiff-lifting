package org.sidiff.integration.preferences;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.sidiff.integration.preferences.fieldeditors.PreferenceField;
import org.sidiff.integration.preferences.interfaces.ISiDiffEnginesPreferenceTab;
import org.sidiff.integration.preferences.interfaces.ISiDiffOrderableTab;

/**
 * Preference Subpage for Engine Settings
 * @author Daniel Roedder, Robert Müller
 */
public class EnginesPage extends TabbedPreferenceFieldPage implements IWorkbenchPreferencePage {

	/**
	 * List to hold all executable classes of the registered extensions
	 */
	private List<ISiDiffEnginesPreferenceTab> extensionClassList;

	/**
	 * Property Change Event for the engine Page
	 * Delegates the event handling to all preference tabs
	 * @param event The event to react to
	 */
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		for (ISiDiffEnginesPreferenceTab tab : extensionClassList) {
			tab.propertyChange(event);
		}
	}

	/**
	 * Creates the executable classes of the extensions and adds them to the collection and sorts them.
	 * Then creates all needed Preference Fields by polling the fields from the extensions.
	 */
	@Override
	protected void createPreferenceFields() {
		extensionClassList = new ArrayList<ISiDiffEnginesPreferenceTab>();

		for (IConfigurationElement element :
				Platform.getExtensionRegistry().getConfigurationElementsFor(ISiDiffEnginesPreferenceTab.EXTENSION_POINT_ID)) {
			try {
				ISiDiffEnginesPreferenceTab tab = ((ISiDiffEnginesPreferenceTab)element.createExecutableExtension("class"));
				tab.setPreferenceStore(Activator.getDefault().getPreferenceStore());
				extensionClassList.add(tab);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}

		extensionClassList.sort(ISiDiffOrderableTab.COMPARATOR);

		// TODO: hide empty tabs
		for (ISiDiffEnginesPreferenceTab tab : extensionClassList) {
			int tabNumber = this.addTab(tab.getTitle());
			for (PreferenceField field : tab.getTabContent()) {
				this.addField(field, tabNumber);
			}
		}
	}
}
