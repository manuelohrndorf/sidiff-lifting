package org.sidiff.vcmsintegration.preferences.fieldeditors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Group;

/**
 * Preference field for a List of Checkboxes which can individually be selected
 * @author Felix Breitweiser
 */
public class CheckListSelectField extends PreferenceField {
	
	/**
	 * Internal list of CheckBoxes
	 */
	public List<CheckBoxPreferenceField> fields;
	
	/**
	 * @param preferenceName the name of the preference in the store 
	 * @param title The title, shown above all Checkboxes
	 * @param valuesAndLabels 2-Dimensional Array of strings with each element being an internal value (for the preference store) and a label 
	 */
	public CheckListSelectField(String preferenceName, String title, String[][] valuesAndLabels) {
		super(preferenceName, title);
		fields = new ArrayList<CheckBoxPreferenceField>();
		for(String[] valueAndLabel : valuesAndLabels) {
			fields.add(new CheckBoxPreferenceField(getPreferenceName(valueAndLabel[0]), valueAndLabel[1]));
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
	 * @see org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField#doLoad(org.eclipse.jface.preference.IPreferenceStore, java.lang.String)
	 */
	@Override
	public void doLoad(IPreferenceStore store, String preferenceName) {
		for(CheckBoxPreferenceField f : fields) {
			f.doLoad(store, f.getPreferenceName());
		}
	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField#doLoadDefault(org.eclipse.jface.preference.IPreferenceStore, java.lang.String)
	 */
	@Override
	public void doLoadDefault(IPreferenceStore store, String preferenceName) {
		for(CheckBoxPreferenceField f : fields) {
			f.doLoadDefault(store, f.getPreferenceName());
		}
	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField#doSave(org.eclipse.jface.preference.IPreferenceStore, java.lang.String)
	 */
	@Override
	public void doSave(IPreferenceStore store, String preferenceName) {
		for(CheckBoxPreferenceField f : fields) {
			f.doSave(store, f.getPreferenceName());
		}
	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField#doCreateControls(org.eclipse.swt.widgets.Group, java.lang.String)
	 */
	@Override
	public void doCreateControls(Group parent, String title) {
		parent.setLayout(new RowLayout(SWT.VERTICAL));
		parent.setText(title);
		
		for(CheckBoxPreferenceField f : fields) {
			f.createControls(parent);
			f.addPropertyChangeListener(new IPropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent event) {
					CheckListSelectField.this.firePropertyChanged(event.getOldValue(), event.getNewValue());
				}
			});
		}
	}
	
	/**
	 * @see org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled) {
		for(CheckBoxPreferenceField f : fields) {
			if(enabled) 
				f.enable();
			else
				f.disable();
		}
	}
	
	/**
	 * a helper method that accepts an iterable and a mapping function to create values and labels for all the elements in the iterable
	 * @param preferenceName the name of the preference, for the resulting CheckListSelectField
	 * @param title the title of the resulting CheckListSelectField
	 * @param input the input interable
	 * @param provider a mapper, that returns a value and a label for an element in the input
	 * @return the newly created CheckListSelectField
	 */
	public static <T> CheckListSelectField createFromIterable(String preferenceName, String title, Iterable<T> input, ValueAndLabelProvider<T> provider) {
		ArrayList<String[]> valuesAndLabels = new ArrayList<String[]>();
		for(T t : input) {
			valuesAndLabels.add(provider.get(t));
		}
		return new CheckListSelectField(preferenceName, title, valuesAndLabels.toArray(new String[valuesAndLabels.size()][]));
	}
}
