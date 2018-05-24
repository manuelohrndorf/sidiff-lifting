package org.sidiff.integration.preferences.fieldeditors.internal;

import java.util.Collection;

import org.sidiff.integration.preferences.valueconverters.IPreferenceValueConverter;

/**
 * Preference field for a List of Checkboxes which can individually be selected
 * @author Felix Breitweiser, Robert Müller
 */
public class CheckListSelectField extends GroupCompositeField {

	/**
	 * @param preferencePrefix the prefix for preference names of the child preferences
	 * @param title The title, shown above all Checkboxes
	 * @param inputs the input elements (map of value to label)
	 */
	public <T> CheckListSelectField(String preferencePrefix, String title,
			Collection<T> inputs, IPreferenceValueConverter<? super T> valueConverter) {
		super(preferencePrefix, title);
		for(T input : inputs) {
			addField(new CheckBoxPreferenceField(valueConverter.getValue(input), valueConverter.getLabel(input)));
		}
	}
}
