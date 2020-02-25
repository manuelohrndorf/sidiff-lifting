package org.sidiff.integration.preferences.fieldeditors.internal;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Spinner;

/**
 * PreferenceField for a number spinner
 * @author Felix Breitweiser
 * @author rmueller
 */
public class NumberPreferenceField extends PreferenceField {

	private Spinner spinner;
	private int minimum;
	private int maximum;

	/**
	 * Creates a new NumberPreferenceField with the given preference name, title, minimum value, and maximum value.
	 * @param preferenceName the name of the preference in the store 
	 * @param title The title shown beside the spinner
	 * @param minimum the minimum value
	 * @param maximum the maximum value
	 */
	public NumberPreferenceField(String preferenceName, String title, int minimum, int maximum) {
		super(preferenceName, title);
		Assert.isLegal(minimum <= maximum);
		this.minimum = minimum;
		this.maximum = maximum;
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
	protected Control doCreateControls(Composite parent) {
		Group group = new Group(parent, SWT.NONE);
		group.setText(getTitle());
		group.setLayout(new GridLayout(1, true));

		spinner = new Spinner(group, SWT.NULL);
		spinner.setMinimum(minimum);
		spinner.setMaximum(maximum);

		return group;
	}

	@Override
	public void setEnabled(boolean enabled) {
		spinner.setEnabled(enabled);	
	}
}
