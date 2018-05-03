package org.sidiff.integration.preferences;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.sidiff.integration.preferences.fieldeditors.PreferenceField;
import org.sidiff.integration.preferences.interfaces.IOrderableStep;
import org.sidiff.integration.preferences.interfaces.IPreferenceTab;

/**
 * Abstract superclass for preference/property pages that show registered preference tabs.
 * @author Robert Müller
 *
 */
public class AbstractPreferenceTabPage extends TabbedPreferenceFieldPage {

	private IPreferenceTab.TabPage page;
	private String documentType;

	/**
	 * Creates a preference page that shows all registered preference tabs that specify the given page.
	 * @param page the type of preference tabs that are shown
	 */
	public AbstractPreferenceTabPage(IPreferenceTab.TabPage page) {
		this.page = page;
	}

	/**
	 * Creates a preference page that shows all registered domain specific preference tabs for the given document type.
	 * @param documentType the domain document type
	 */
	public AbstractPreferenceTabPage(String documentType) {
		this(IPreferenceTab.TabPage.DOMAINS);
		this.documentType = documentType;
	}

	@Override
	protected void createPreferenceFields() {
		List<IPreferenceTab> tabs = new ArrayList<IPreferenceTab>();

		for (IConfigurationElement element :
				Platform.getExtensionRegistry().getConfigurationElementsFor(IPreferenceTab.EXTENSION_POINT_ID)) {
			try {
				IPreferenceTab tab = (IPreferenceTab)element.createExecutableExtension(
						IPreferenceTab.EXTENSION_POINT_ATTRIBUTE);
				if(tab.getPage() == page) {
					if(tab instanceof IPreferenceTab.DomainSpecific) {
						((IPreferenceTab.DomainSpecific)tab).setDocumentType(documentType);
					}
					tabs.add(tab);
				}
			} catch (CoreException e) {
				PreferencesPlugin.logWarning("Failed to create IPreferenceTab contributed by "
								+ element.getDeclaringExtension().getContributor().getName(), e);
			}
		}

		tabs.sort(IOrderableStep.COMPARATOR);

		for (IPreferenceTab tab : tabs) {
			List<PreferenceField> fields = new ArrayList<PreferenceField>();
			tab.createPreferenceFields(fields);
			if(fields.isEmpty())
				continue; // empty tabs are skipped
			addTab(tab.getTitle(), fields);
		}
	}

}
