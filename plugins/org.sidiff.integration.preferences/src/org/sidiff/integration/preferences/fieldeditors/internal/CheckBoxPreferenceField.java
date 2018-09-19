package org.sidiff.integration.preferences.fieldeditors.internal;

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
	private String tooltip;

	/**
	 * @param preferenceName the name of the preference in the store 
	 * @param title The title shown beside the checkbox
	 * @param tooltip The tooltip of the field, may be <code>null</code>
	 */
	public CheckBoxPreferenceField(String preferenceName, String title, String tooltip) {
		super(preferenceName, title);
		this.tooltip = tooltip;
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
	public Control doCreateControls(Composite parent) {
		checkBox = new Button(parent, SWT.CHECK);
		checkBox.setText(getTitle());
		checkBox.setToolTipText(tooltip);
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

	public void setSelection(boolean selected) {
		checkBox.setSelection(selected);
	}

	public boolean getSelection() {
		return checkBox.getSelection();
	}
}
