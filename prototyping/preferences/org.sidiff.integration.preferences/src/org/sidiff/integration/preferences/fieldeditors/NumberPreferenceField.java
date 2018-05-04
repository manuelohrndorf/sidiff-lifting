package org.sidiff.integration.preferences.fieldeditors;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Spinner;

/**
 * PreferenceField for a number spinner
 * @author Felix Breitweiser, Robert Müller
 */
public class NumberPreferenceField extends PreferenceField {

	private Spinner spinner;

	/**
	 * @param preferenceName the name of the preference in the store 
	 * @param title The title shown beside the spinner
	 */
	public NumberPreferenceField(String preferenceName, String title) {
		super(preferenceName, title);
	}

	@Override
	public void load(IPreferenceStore store) {
		spinner.setSelection(store.getInt(getPreferenceName()));
	}

	@Override
	public void loadDefault(IPreferenceStore store) {
		spinner.setSelection(store.getDefaultInt(getPreferenceName()));
	}

	@Override
	public void save(IPreferenceStore store) {
		store.setValue(getPreferenceName(), spinner.getSelection());
	}

	@Override
	protected Control doCreateControls(Composite parent, String title) {
		Group group = new Group(parent, SWT.NONE);
		group.setText(title);
		group.setLayout(new GridLayout(1, true));

		spinner = new Spinner(group, SWT.NULL);

		return group;
	}

	@Override
	public void setEnabled(boolean enabled) {
		spinner.setEnabled(enabled);	
	}
}
