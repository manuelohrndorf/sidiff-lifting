package org.sidiff.vcmsintegration.preferences;

import java.util.ArrayList;
import java.util.Comparator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField;
import org.sidiff.vcmsintegration.preferences.interfaces.ISiDiffEnginesPreferenceTab;
import org.sidiff.vcmsintegration.preferences.interfaces.ISiDiffOrderableTab;

/**
 * Preference Subpage for Engine Settings
 * @author Daniel Roedder
 */
public class EnginesPage extends TabbedPreferenceFieldPage implements IWorkbenchPreferencePage {

	/**
	 * Array to hold all registered extensions for an extension point
	 */
	private IConfigurationElement[] extensionList;
	
	/**
	 * List to hold all executable classes of the registered extensions
	 */
	private ArrayList<ISiDiffEnginesPreferenceTab> extensionClassList;

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
		getAllExtensions();
		
		for (IConfigurationElement element : extensionList) {
			ISiDiffEnginesPreferenceTab temp;
			try {
				temp = ((ISiDiffEnginesPreferenceTab) element.createExecutableExtension("class"));
				extensionClassList.add(temp);
				temp.setPreferenceStore(Activator.getDefault().getPreferenceStore());
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		
		extensionClassList.sort(new Comparator<ISiDiffOrderableTab>() {

			@Override
			public int compare(ISiDiffOrderableTab o1, ISiDiffOrderableTab o2) {
				return o1.getStepInPipeline()-o2.getStepInPipeline();
			}
			
		});
		
		for (ISiDiffEnginesPreferenceTab tab : extensionClassList) {
			int tabNumber = this.addTab(tab.getTitle());
			for (PreferenceField field : tab.getTabContent()) {
				this.addField(field, tabNumber);
			}
		}
		
	}
	

	/**
	 * Gets all registered extensions for a given extension point
	 */
	private void getAllExtensions() {
		extensionList = Platform.getExtensionRegistry().getConfigurationElementsFor("org.sidiff.vcmsintegration.preferences.enginePipelineStep"); 
	}

}
