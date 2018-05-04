package org.sidiff.integration.preferences.fieldeditors;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * PreferenceField for one CheckBox with a label
 * @author Felix Breitweiser, Robert Müller
 */
public class CheckBoxPreferenceField extends PreferenceField {

	private Button checkBox;

	/**
	 * @param preferenceName the name of the preference in the store 
	 * @param title The title shown beside the checkbox
	 */
	public CheckBoxPreferenceField(String preferenceName, String title) {
		super(preferenceName, title);
	}

	@Override
	public void load(IPreferenceStore store) {
		checkBox.setSelection(store.getBoolean(getPreferenceName()));
	}

	@Override
	public void loadDefault(IPreferenceStore store) {
		checkBox.setSelection(store.getDefaultBoolean(getPreferenceName()));
	}

	@Override
	public void save(IPreferenceStore store) {
		store.setValue(getPreferenceName(), checkBox.getSelection());
	}

	@Override
	public Control doCreateControls(Composite parent, String title) {
		checkBox = new Button(parent, SWT.CHECK);
		checkBox.setText(title);
		checkBox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				firePropertyChanged(!checkBox.getSelection(), checkBox.getSelection());
			}
		});
		return checkBox;
	}

	@Override
	public void setEnabled(boolean enabled) {
		checkBox.setEnabled(enabled);
	}
}
