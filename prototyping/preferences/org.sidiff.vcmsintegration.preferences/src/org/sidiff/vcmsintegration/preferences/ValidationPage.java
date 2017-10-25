/**
 * @author Daniel Roedder
 */
package org.sidiff.vcmsintegration.preferences;

import java.util.ArrayList;
import java.util.Comparator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField;
import org.sidiff.vcmsintegration.preferences.interfaces.ISiDiffOrderableTab;
import org.sidiff.vcmsintegration.preferences.interfaces.ISiDiffValidationPreferenceTab;
import org.sidiff.vcmsintegration.preferences.util.PreferenceUtil;

/**
 * Preference subpage for validation settings.
 * @author Daniel Roedder
 *
 */
public class ValidationPage extends TabbedPreferenceFieldPage implements IWorkbenchPreferencePage {

	/**
	 * Array to hold all registered extensions for an extension point
	 */
	private IConfigurationElement[] extensionList;
	
	/**
	 * List to hold all executable extensions for an extension point
	 */
	private ArrayList<ISiDiffValidationPreferenceTab> extensionClassList;

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
		getAllExtensions();
		
		for (IConfigurationElement element : extensionList) {
			ISiDiffValidationPreferenceTab temp;
			try {
				temp = ((ISiDiffValidationPreferenceTab) element.createExecutableExtension("class"));
				extensionClassList.add(temp);
				temp.setPreferenceStore(PreferenceUtil.getInstance().getPluginPreferenceStore());
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
		
		for (ISiDiffValidationPreferenceTab tab : extensionClassList) {
			int tabNumber = this.addTab(tab.getTitle());
			for (PreferenceField field : tab.getTabContent()) {
				this.addField(field, tabNumber);
			}
		}
		
	}
	

	/**
	 * Gets all extensions for an given extension point
	 */
	private void getAllExtensions() {
		extensionList = Platform.getExtensionRegistry().getConfigurationElementsFor("org.sidiff.vcmsintegration.preferences.validationPipelineStep"); 
	}

}