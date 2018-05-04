package org.sidiff.integration.preferences.fieldeditors;

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
			Collection<T> inputs, IPreferenceValueConverter<T> valueConverter) {
		super(preferencePrefix, title);
		for(T input : inputs) {
			addField(new CheckBoxPreferenceField(valueConverter.getValue(input), valueConverter.getLabel(input)));
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
