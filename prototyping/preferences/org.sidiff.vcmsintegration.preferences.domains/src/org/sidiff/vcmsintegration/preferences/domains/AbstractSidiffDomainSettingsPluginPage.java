package org.sidiff.vcmsintegration.preferences.domains;

import java.util.ArrayList;
import java.util.Comparator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.sidiff.vcmsintegration.preferences.TabbedPreferenceFieldPage;
import org.sidiff.vcmsintegration.preferences.domains.interfaces.ISiDiffDomainPreferenceTab;
import org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField;
import org.sidiff.vcmsintegration.preferences.interfaces.ISiDiffOrderableTab;

/**
 * 
 * Abstract class to create the {@link org.sidiff.vcmsintegration.preferences.TabbedPreferenceFieldPage} for a specific domain.
 * @author Daniel Roedder
 */
public abstract class AbstractSidiffDomainSettingsPluginPage extends TabbedPreferenceFieldPage 
implements IWorkbenchPreferencePage{

	/**
	 * Array to hold all registered extensions for an extension point
	 */
	private IConfigurationElement[] extensionList;
	
	/**
	 * List to hold all executable classes of the registered extensions
	 */
	private ArrayList<ISiDiffDomainPreferenceTab> extensionClassList;
	
	
	/**
	 * @see org.sidiff.vcmsintegration.preferences.TabbedPreferenceFieldPage#createPreferenceFields()
	 */
	@Override
	protected void createPreferenceFields() {
		extensionClassList = new ArrayList<ISiDiffDomainPreferenceTab>();
		getAllExtensions();
		
		
		for (IConfigurationElement element : extensionList) {
			ISiDiffDomainPreferenceTab temp;
			try {
				temp = ((ISiDiffDomainPreferenceTab) element.createExecutableExtension("class"));
				extensionClassList.add(temp);
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
		
		for (ISiDiffDomainPreferenceTab tab : extensionClassList) {
			
			if (tab.getDocumentType().equals(getDocumentType())) {
				int tabNumber = this.addTab(tab.getTitle());
				for (PreferenceField field : tab.getTabContent()) {
					this.addField(field, tabNumber);
				} 
			}
		}
	}
	
	/**
	 * Gets all extensions for a given extension point
	 */
	private void getAllExtensions() {
		extensionList = Platform.getExtensionRegistry().getConfigurationElementsFor("org.sidiff.vcmsintegration.preferences.domains.pipelineStep"); 
	}
	
	/**
	 * Used to return the document type string of the concrete domain settings page.
	 * Subclasses must implement.
	 * @return The document type as String
	 */
	protected abstract String getDocumentType();
}
