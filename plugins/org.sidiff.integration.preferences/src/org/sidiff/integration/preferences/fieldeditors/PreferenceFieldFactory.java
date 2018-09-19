package org.sidiff.integration.preferences.fieldeditors;

import java.util.Arrays;
import java.util.Collection;

import org.sidiff.integration.preferences.fieldeditors.internal.CheckBoxPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.internal.CheckListSelectField;
import org.sidiff.integration.preferences.fieldeditors.internal.CompositeField;
import org.sidiff.integration.preferences.fieldeditors.internal.NumberPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.internal.OrderedListSelectField;
import org.sidiff.integration.preferences.fieldeditors.internal.RadioBoxPreferenceField;
import org.sidiff.integration.preferences.valueconverters.EnumPreferenceValueConverter;
import org.sidiff.integration.preferences.valueconverters.IPreferenceValueConverter;

/**
 * The {@link PreferenceFieldFactory} contains methods to create various different {@link IPreferenceField}s and
 * {@link ICompositePreferenceField}s.
 * @author Robert Müller
 *
 */
public class PreferenceFieldFactory {

	private PreferenceFieldFactory() {
		throw new AssertionError();
	}

	/**
	 * Creates a preference field with a checkbox.
	 * @param preferenceName the name of the preference
	 * @param title the title of the field
	 * @return checkbox preference field
	 */
	public static IPreferenceField createCheckBox(String preferenceName, String title) {
		return createCheckBox(preferenceName, title, null);
	}
	
	/**
	 * Creates a preference field with a checkbox.
	 * @param preferenceName the name of the preference
	 * @param title the title of the field
	 * @param tooltip the tooltip of the field
	 * @return checkbox preference field
	 */
	public static IPreferenceField createCheckBox(String preferenceName, String title, String tooltip) {
		return new CheckBoxPreferenceField(preferenceName, title, tooltip);
	}

	/**
	 * <p>Creates a preference field with a group of radio buttons.
	 * Only one radio button can be selected at a time.</p>
	 * <p>If <code>allowUnset</code> is <code>true</code>, an additional button
	 * representing an unset preference will be provided. If this is the case,
	 * the provided value converter must provide a value and label for the value <code>null</code>.</p>
	 * @param preferenceName the name of the preference
	 * @param title the title of the field
	 * @param inputs the items to create radio buttons for
	 * @param valueConverter value converter to provider labels and values for the items
	 * @param allowUnset whether this radio box can allows an unset value
	 * @return radio group preference field
	 */
	public static <T> IMultiPreferenceField<T> createRadioBox(String preferenceName, String title,
			Collection<T> inputs, IPreferenceValueConverter<? super T> valueConverter, boolean allowUnset) {
		return new RadioBoxPreferenceField<T>(preferenceName, title, inputs, valueConverter, allowUnset);
	}

	/**
	 * Creates a preference field with a group of radio buttons.
	 * Only one radio button can be selected at a time.
	 * @param preferenceName the name of the preference
	 * @param title the title of the field
	 * @param inputs the items to create radio buttons for
	 * @param valueConverter value converter to provider labels and values for the items
	 * @return radio group preference field
	 */
	public static <T> IMultiPreferenceField<T> createRadioBox(String preferenceName, String title,
			Collection<T> inputs, IPreferenceValueConverter<? super T> valueConverter) {
		return createRadioBox(preferenceName, title, inputs, valueConverter, false);
	}

	/**
	 * Creates a preference field with a group of radio buttons for the constants of an <b>enum class</b>.
	 * Only one radio button can be selected at a time.
	 * @param <E> enum type
	 * @param preferenceName the name of the preference
	 * @param title the title of the field
	 * @param enumClass class of enum whose constants will be used as radio items
	 * @return radio group preference field for enum class
	 */
	public static <E extends Enum<E>> IMultiPreferenceField<E> createRadioBox(String preferenceName, String title, Class<E> enumClass) {
		return createRadioBox(preferenceName, title,
				Arrays.asList(enumClass.getEnumConstants()),
				new EnumPreferenceValueConverter<E>());
	}

	/**
	 * Creates a preference field with a group of checkboxes.
	 * Multiple checkboxes can be selected at the same time.
	 * @param preferenceName the name of the preference
	 * @param title the title of the field
	 * @param inputs the items to create checkboxes for
	 * @param valueConverter value converter to provide labels and values for the items
	 * @return checkbox list preference field
	 */
	public static <T> IMultiPreferenceField<T> createCheckBoxList(String preferenceName, String title,
			Collection<T> inputs, IPreferenceValueConverter<? super T> valueConverter) {
		return new CheckListSelectField<T>(preferenceName, title, inputs, valueConverter);
	}

	/**
	 * Creates a preference field with list of input items.
	 * Any number of items can be selected and the order of the items can be adjusted.
	 * @param preferenceName the name of the preference
	 * @param title the title of the field
	 * @param inputs the items to show in the list
	 * @param valueConverter value converter to provide labels and values for the items
	 * @return ordered list preference field
	 */
	public static <T> IMultiPreferenceField<T> createOrderedList(String preferenceName, String title,
			Collection<T> inputs, IPreferenceValueConverter<? super T> valueConverter) {
		return new OrderedListSelectField<T>(preferenceName, title, inputs, valueConverter);
	}

	/**
	 * Creates a preference field with a number spinner.
	 * The minimum value is 1, the maximum value is {@link Integer#MAX_VALUE}.
	 * @param preferenceName the name of the preference
	 * @param title the title of the field
	 * @return number preference field
	 */
	public static IPreferenceField createNumber(String preferenceName, String title) {
		return createNumber(preferenceName, title, 1, Integer.MAX_VALUE);
	}

	/**
	 * Creates a preference field with a number spinner with the given minimum and maximum values.
	 * @param preferenceName the name of the preference
	 * @param title the title of the field
	 * @param minimum the minimum value
	 * @param maximum the maximum value
	 * @return
	 */
	public static IPreferenceField createNumber(String preferenceName, String title, int minimum, int maximum) {
		return new NumberPreferenceField(preferenceName, title, minimum, maximum);
	}

	/**
	 * Creates an expandable composite preference field.
	 * The field can be expanded, revealing the items added via {@link ICompositePreferenceField#addField(IPreferenceField)}.
	 * @param title the title of the expandable composite
	 * @return expandable composite preference field
	 */
	public static ICompositePreferenceField<IPreferenceField> createExpandableComposite(String title) {
		return new CompositeField<IPreferenceField>(CompositeField.WrapperSupplier.EXPANDABLE, title);
	}
}
