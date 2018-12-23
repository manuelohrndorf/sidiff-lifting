package org.sidiff.integration.preferences.fieldeditors.internal;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

/**
 * Preference field with a text input.
 * @author Robert Müller
 */
public class TextPreferenceField extends PreferenceField {

	private Text textInput;
	private String oldValue;
	private String tooltip;

	/**
	 * @param preferenceName the name of the preference in the store 
	 * @param title The title
	 * @param tooltip The tooltip of the field, may be <code>null</code>
	 */
	public TextPreferenceField(String preferenceName, String title, String tooltip) {
		super(preferenceName, title);
		this.tooltip = tooltip;
	}

	@Override
	public void load(IPreferenceStore store) {
		textInput.setText(store.getString(getPreferenceName()));
	}

	@Override
	public void loadDefault(IPreferenceStore store) {
		textInput.setText(store.getDefaultString(getPreferenceName()));
	}

	@Override
	public void save(IPreferenceStore store) {
		store.setValue(getPreferenceName(), textInput.getText());
	}

	@Override
	public Control doCreateControls(Composite parent) {
		Group group = new Group(parent, SWT.NONE);
		group.setText(getTitle());
		group.setLayout(new GridLayout(1, true));
		group.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		
		textInput = new Text(group, SWT.BORDER|SWT.SINGLE);
		textInput.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		textInput.setToolTipText(tooltip);
		textInput.addModifyListener(e -> {
			firePropertyChanged(oldValue, textInput.getText());
			oldValue = textInput.getText();
		});
		return group;
	}

	@Override
	public void setEnabled(boolean enabled) {
		textInput.setEnabled(enabled);
	}
}
