package org.sidiff.vcmsintegration.preferences;

import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.vcmsintegration.preferences.fieldeditors.RadioBoxPreferenceField;
import org.sidiff.vcmsintegration.preferences.fieldeditors.ValueAndLabelProvider;

/**
 * 
 * Preference page for resolution strategy
 * @author Daniel Roedder
 */
public class ResStratPage extends TabbedPreferenceFieldPage implements IWorkbenchPreferencePage {
	
	/**
	 * The {@link org.sidiff.vcmsintegration.preferences.fieldeditors.RadioBoxPreferenceField} for the scope setting
	 */
	private RadioBoxPreferenceField scopeField;

	
	/**
	 * @see org.sidiff.vcmsintegration.preferences.PreferenceFieldPage#init(org.eclipse.ui.IWorkbench)
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
		scopeField = RadioBoxPreferenceField.createFromArray("scope", "Scope", Scope.values(), new ValueAndLabelProvider<Scope>() {
			@Override public String[] get(Scope input) { return new String[]{ input.toString(), input.toString() }; }
		});
		addField(scopeField, this.addTab("Scope Settings"));
		
	}

}
