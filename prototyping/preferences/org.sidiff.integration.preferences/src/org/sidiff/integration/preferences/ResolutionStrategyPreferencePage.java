package org.sidiff.integration.preferences;

import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.integration.preferences.fieldeditors.RadioBoxPreferenceField;
import org.sidiff.integration.preferences.valueconverters.EnumPreferenceValueConverter;

/**
 * 
 * Preference page for resolution strategy
 * @author Daniel Roedder, Robert Müller
 */
public class ResolutionStrategyPreferencePage extends TabbedPreferenceFieldPage implements IWorkbenchPreferencePage {
	
	/**
	 * The {@link org.sidiff.integration.preferences.fieldeditors.RadioBoxPreferenceField} for the scope setting
	 */
	private RadioBoxPreferenceField<?> scopeField;

	/**
	 * @see org.sidiff.integration.preferences.PreferenceFieldPage#init(org.eclipse.ui.IWorkbench)
	 */
	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}

	/**
	 * @see org.eclipse.jface.util.IPropertyChangeListener#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
	 * Not needed here
	 */
	@Override
	public void propertyChange(PropertyChangeEvent event) {
	}

	/**
	 * Creates the preference fields for the page.
	 */
	@Override
	protected void createPreferenceFields() {
		scopeField = RadioBoxPreferenceField.create("scope", "Scope", Scope.values(), new EnumPreferenceValueConverter());
		addField(scopeField, this.addTab("Scope Settings"));
	}
}
