package org.sidiff.vcmsintegration.preferences.fieldeditors;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;

/**
 * PreferenceField for one CheckBox with a label
 * @author Felix Breitweiser
 */
public class CheckBoxPreferenceField extends PreferenceField {
	
	Button checkBox;
	
	/**
	 * @param preferenceName the name of the preference in the store 
	 * @param title The title shown beside the checkbox
	 */
	public CheckBoxPreferenceField(String preferenceName, String title) {
		super(preferenceName, title);
	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField#doLoad(org.eclipse.jface.preference.IPreferenceStore, java.lang.String)
	 */
	@Override
	public void doLoad(IPreferenceStore store, String preferenceName) {
		checkBox.setSelection(store.getBoolean(preferenceName));
	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField#doLoadDefault(org.eclipse.jface.preference.IPreferenceStore, java.lang.String)
	 */
	@Override
	public void doLoadDefault(IPreferenceStore store, String preferenceName) {
		checkBox.setSelection(store.getDefaultBoolean(preferenceName));
	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField#doSave(org.eclipse.jface.preference.IPreferenceStore, java.lang.String)
	 */
	@Override
	public void doSave(IPreferenceStore store, String preferenceName) {
		store.setValue(preferenceName, checkBox.getSelection());
	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField#doCreateControls(org.eclipse.swt.widgets.Group, java.lang.String)
	 */
	@Override
	public void doCreateControls(Group parent, String title) {
		checkBox = new Button(parent, SWT.CHECK);
		checkBox.setText(title);
		checkBox.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				firePropertyChanged(!checkBox.getSelection(), checkBox.getSelection());
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}
	
	/**
	 * @see org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled) {
		checkBox.setEnabled(enabled);
	}
}
