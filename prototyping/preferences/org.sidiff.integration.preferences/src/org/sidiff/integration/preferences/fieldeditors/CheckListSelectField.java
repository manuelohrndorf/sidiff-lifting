package org.sidiff.integration.preferences.fieldeditors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.sidiff.integration.preferences.valueconverters.IPreferenceValueConverter;

/**
 * Preference field for a List of Checkboxes which can individually be selected
 * @author Felix Breitweiser, Robert Müller
 */
public class CheckListSelectField extends PreferenceField {

	/**
	 * Internal list of CheckBoxes
	 */
	private List<CheckBoxPreferenceField> fields;

	/**
	 * @param preferenceName the name of the preference in the store 
	 * @param title The title, shown above all Checkboxes
	 * @param inputs the input elements (map of value to label)
	 */
	public <T> CheckListSelectField(String preferenceName, String title,
			Collection<T> inputs, IPreferenceValueConverter<T> valueConverter) {
		super(preferenceName, title);
		fields = new ArrayList<CheckBoxPreferenceField>();
		for(T input : inputs) {
			fields.add(new CheckBoxPreferenceField(
					getPreferenceName(valueConverter.getValue(input)),
					valueConverter.getLabel(input)));
		}
	}

	/**
	 * @param fragment the value of the checkbox
	 * @return a combination of the preferenceName and the fragment
	 */
	private String getPreferenceName(String fragment) {
		return getPreferenceName() + ":" + fragment;
	}

	/**
	 * @see org.sidiff.integration.preferences.fieldeditors.PreferenceField#doLoad(org.eclipse.jface.preference.IPreferenceStore, java.lang.String)
	 */
	@Override
	public void doLoad(IPreferenceStore store, String preferenceName) {
		for(CheckBoxPreferenceField f : fields) {
			f.doLoad(store, f.getPreferenceName());
		}
	}

	/**
	 * @see org.sidiff.integration.preferences.fieldeditors.PreferenceField#doLoadDefault(org.eclipse.jface.preference.IPreferenceStore, java.lang.String)
	 */
	@Override
	public void doLoadDefault(IPreferenceStore store, String preferenceName) {
		for(CheckBoxPreferenceField f : fields) {
			f.doLoadDefault(store, f.getPreferenceName());
		}
	}

	/**
	 * @see org.sidiff.integration.preferences.fieldeditors.PreferenceField#doSave(org.eclipse.jface.preference.IPreferenceStore, java.lang.String)
	 */
	@Override
	public void doSave(IPreferenceStore store, String preferenceName) {
		for(CheckBoxPreferenceField f : fields) {
			f.doSave(store, f.getPreferenceName());
		}
	}

	/**
	 * @see org.sidiff.integration.preferences.fieldeditors.PreferenceField#doCreateControls(org.eclipse.swt.widgets.Group, java.lang.String)
	 */
	@Override
	public void doCreateControls(Composite parent, String title) {
		Group group = new Group(parent, SWT.NONE);
		group.setText(title);
		group.setLayout(new RowLayout(SWT.VERTICAL));

		for(CheckBoxPreferenceField f : fields) {
			f.createControls(group);
			f.addPropertyChangeListener(new IPropertyChangeListener() {
				@Override
				public void propertyChange(PropertyChangeEvent event) {
					CheckListSelectField.this.firePropertyChanged(event.getOldValue(), event.getNewValue());
				}
			});
		}

		if(fields.isEmpty()) {
			Label label = new Label(group, SWT.NONE);
			label.setText("None available");
		}
	}

	/**
	 * @see org.sidiff.integration.preferences.fieldeditors.PreferenceField#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled) {
		for(CheckBoxPreferenceField f : fields) {
			f.setEnabled(enabled);
		}
	}

	/**
	 * a helper method that accepts an iterable and a mapping function to create values and labels for all the elements in the iterable
	 * @param preferenceName the name of the preference, for the resulting CheckListSelectField
	 * @param title the title of the resulting CheckListSelectField
	 * @param input the input collection
	 * @param valueConverter a converter, that returns a value and a label for an element in the input
	 * @return the newly created CheckListSelectField
	 */
	public static <T> CheckListSelectField create(String preferenceName, String title,
			Collection<T> inputs, IPreferenceValueConverter<T> valueConverter) {
		return new CheckListSelectField(preferenceName, title, inputs, valueConverter);
	}
}
