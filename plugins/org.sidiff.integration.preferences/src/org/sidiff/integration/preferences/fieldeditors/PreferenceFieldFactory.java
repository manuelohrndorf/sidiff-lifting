package org.sidiff.integration.preferences.fieldeditors;


import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Shell;
import org.sidiff.common.collections.CollectionUtil;
import org.sidiff.common.extension.IExtension;
import org.sidiff.common.extension.configuration.ConfigurationOption;
import org.sidiff.common.extension.configuration.IConfigurableExtension;
import org.sidiff.common.extension.configuration.IExtensionConfiguration;
import org.sidiff.integration.preferences.fieldeditors.internal.CheckBoxPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.internal.CheckListSelectField;
import org.sidiff.integration.preferences.fieldeditors.internal.CompositeField;
import org.sidiff.integration.preferences.fieldeditors.internal.EditableListField;
import org.sidiff.integration.preferences.fieldeditors.internal.FilteredAddElementSelectionDialog;
import org.sidiff.integration.preferences.fieldeditors.internal.NumberPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.internal.OrderedListSelectField;
import org.sidiff.integration.preferences.fieldeditors.internal.RadioBoxPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.internal.TextPreferenceField;
import org.sidiff.integration.preferences.valueconverters.EnumPreferenceValueConverter;
import org.sidiff.integration.preferences.valueconverters.IPreferenceValueConverter;

/**
 * The {@link PreferenceFieldFactory} contains methods to create various different {@link IPreferenceField}s and
 * {@link ICompositePreferenceField}s.
 * @author rmueller
 */
public class PreferenceFieldFactory {

	private PreferenceFieldFactory() {
		throw new AssertionError();
	}
	
	/**
	 * Creates a preference field with a text input.
	 * @param preferenceName the name of the preference
	 * @param title the title of the field
	 * @return text preference field
	 */
	public static IPreferenceField createText(String preferenceName, String title) {
		return createText(preferenceName, title, null);
	}
	
	/**
	 * Creates a preference field with a text input.
	 * @param preferenceName the name of the preference
	 * @param title the title of the field
	 * @param tooltip the tooltip of the field
	 * @return text preference field
	 */
	public static IPreferenceField createText(String preferenceName, String title, String tooltip) {
		return new TextPreferenceField(preferenceName, title, tooltip);
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

	public static <T> IMultiPreferenceField<T> createEditableList(String preferenceName, String title,
			IPreferenceValueConverter<? super T> valueConverter, Function<Shell,? extends T> addedValueSelector,
			Function<String,? extends T> keyToValueConverter) {
		return new EditableListField<T>(preferenceName, title, valueConverter, addedValueSelector, keyToValueConverter);
	}
	
	public static <T> IMultiPreferenceField<T> createEditableList(String preferenceName, String title,
			Class<T> elementType, Collection<T> inputs, IPreferenceValueConverter<? super T> valueConverter) {
		
		Map<String, T> keyToValueMap = inputs.stream().collect(Collectors.toMap(valueConverter::getValue, Function.identity()));
		Function<Shell, ? extends T> addedValueSupplier = shell -> {
			FilteredAddElementSelectionDialog<T> dialog = 
					new FilteredAddElementSelectionDialog<>(shell, elementType, inputs, valueConverter);
			dialog.setBlockOnOpen(true);
			if(dialog.open() != Dialog.OK) {
				return null;
			}
			return elementType.cast(dialog.getFirstResult());
		};
		return new EditableListField<T>(preferenceName, title, valueConverter, addedValueSupplier, keyToValueMap::get);
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

	public static ICompositePreferenceField<IPreferenceField> createConfigurableExtensionsFields(
			Collection<? extends IExtension> extensions, IPreferenceField primaryField, BiFunction<String,String,String> keyFunction) {

		ICompositePreferenceField<IPreferenceField> composite =
				new CompositeField<IPreferenceField>(CompositeField.WrapperSupplier.COMPOSITE, null);
		Map<String,IPreferenceField> fields = new HashMap<>();

		for(IExtension extension : extensions) {
			if(extension instanceof IConfigurableExtension) {
				ICompositePreferenceField<IPreferenceField> field =
						createConfigurableExtensionFields((IConfigurableExtension)extension,
								optionKey -> keyFunction.apply(extension.getKey(), optionKey));
				if(!field.isEmpty()) {
					composite.addField(field);
					fields.put(extension.getKey(), field);
				}
			}
		}

		if(primaryField != null) {
			primaryField.addPropertyChangeListener(event -> {
				List<String> newValue = CollectionUtil.getValues(event.getNewValue(), String.class);
				for(Entry<String, IPreferenceField> field : fields.entrySet()) {
					field.getValue().setVisible(newValue.contains(field.getKey()));
				}
			});			
		}

		return composite;
	}

	public static ICompositePreferenceField<IPreferenceField> createConfigurableExtensionFields(
			IConfigurableExtension extension, Function<String,String> keyFunction) {
		ICompositePreferenceField<IPreferenceField> optionsComposite =
				PreferenceFieldFactory.createExpandableComposite(extension.getName() + " Options");
		IExtensionConfiguration configuration = extension.getConfiguration();
		for(ConfigurationOption<?> option : configuration.getConfigurationOptions()) {
			String key = keyFunction.apply(option.getKey());
			if(option.getType() == Boolean.class) {
				optionsComposite.addField(createCheckBox(key, option.getName()));
			}  else if(option.getType() == Byte.class) {
				optionsComposite.addField(createNumber(key, option.getName(), Byte.MIN_VALUE, Byte.MAX_VALUE));
			} else if(option.getType() == Short.class) {
				optionsComposite.addField(createNumber(key, option.getName(), Short.MIN_VALUE, Short.MAX_VALUE));
			} else if(option.getType() == Integer.class) {
				optionsComposite.addField(createNumber(key, option.getName(), Integer.MIN_VALUE, Integer.MAX_VALUE));
			} else if(option.getType() == String.class
					|| option.getType() == Float.class
					|| option.getType() == Double.class
					|| option.getType() == Long.class) {
				// text field as fallback for some types
				optionsComposite.addField(createText(key, option.getName()));
			}
		}
		return optionsComposite;
	}
}
