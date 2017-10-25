package org.sidiff.vcmsintegration.preferences.fieldeditors;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

/**
 * PreferenceField for a number spinner
 * @author Felix Breitweiser 
 */
public class NumberPreferenceField extends PreferenceField {

	/**
	 * the currently selected value
	 */
	int value;
	
	Spinner spinner;
	
	/**
	 * @param preferenceName the name of the preference in the store 
	 * @param title The title shown beside the spinner
	 */
	public NumberPreferenceField(String preferenceName, String title) {
		super(preferenceName, title);
	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField#doLoad(org.eclipse.jface.preference.IPreferenceStore, java.lang.String)
	 */
	@Override
	protected void doLoad(IPreferenceStore store, String preferenceName) {
		spinner.setSelection(store.getInt(preferenceName));
	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField#doLoadDefault(org.eclipse.jface.preference.IPreferenceStore, java.lang.String)
	 */
	@Override
	protected void doLoadDefault(IPreferenceStore store, String preferenceName) {
		spinner.setSelection(store.getDefaultInt(preferenceName));
	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField#doSave(org.eclipse.jface.preference.IPreferenceStore, java.lang.String)
	 */
	@Override
	protected void doSave(IPreferenceStore store, String preferenceName) {
		store.setValue(preferenceName, spinner.getSelection());
	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField#doCreateControls(org.eclipse.swt.widgets.Group, java.lang.String)
	 */
	@Override
	protected void doCreateControls(Group parent, String title) {
		new Text(parent, SWT.NULL).setText(title);
		spinner = new Spinner(parent, SWT.NULL);
	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled) {
		spinner.setEnabled(enabled);	
	}

}
