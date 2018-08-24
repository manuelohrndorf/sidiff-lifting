package org.sidiff.integration.preferences.fieldeditors.internal;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.jface.preference.IPreferenceStore;
import org.sidiff.common.util.StringListSerializer;
import org.sidiff.integration.preferences.fieldeditors.IMultiPreferenceField;
import org.sidiff.integration.preferences.valueconverters.IPreferenceValueConverter;

/**
 * Preference field for a List of Checkboxes which can individually be selected
 * @author Felix Breitweiser, Robert Müller
 */
public class CheckListSelectField<T> extends CompositeField<CheckBoxPreferenceField> implements IMultiPreferenceField<T> {

	private Collection<T> inputs = Collections.emptySet();
	private Map<String,CheckBoxPreferenceField> checkBoxFields;
	private IPreferenceValueConverter<? super T> valueConverter;

	/**
	 * @param preferenceName name of the preference in the store 
	 * @param title The title, shown above all Checkboxes
	 * @param inputs the input elements
	 * @param valueConverter the value converter for the inputs
	 */
	public CheckListSelectField(String preferenceName, String title,
			Collection<T> inputs, IPreferenceValueConverter<? super T> valueConverter) {
		super(WrapperSupplier.GROUP, preferenceName, title);
		this.valueConverter = valueConverter;
		setInputs(inputs);
	}

	@Override
	public void setInputs(Collection<T> inputs) {
		clearFields();
		this.inputs = inputs;
		checkBoxFields = new HashMap<>();
		for(T input : inputs) {
			String value = valueConverter.getValue(input);
			CheckBoxPreferenceField field = new CheckBoxPreferenceField(value, valueConverter.getLabel(input));
			addField(field);
			checkBoxFields.put(value, field);
		}
	}

	@Override
	public Collection<T> getInputs() {
		return inputs;
	}

	@Override
	public void setSelection(T item, boolean selected) {
		checkBoxFields.get(valueConverter.getValue(item)).setSelection(selected);
	}

	@Override
	public boolean isSelected(T item) {
		return checkBoxFields.get(valueConverter.getValue(item)).getSelection();
	}

	@Override
	public Collection<T> getSelection() {
		return inputs.stream().filter(this::isSelected).collect(Collectors.toList());
	}

	@Override
	public void save(IPreferenceStore store) {
		List<String> values = checkBoxFields.values().stream()
				.filter(CheckBoxPreferenceField::getSelection)
				.map(CheckBoxPreferenceField::getPreferenceName)
				.collect(Collectors.toList());
		store.setValue(getPreferenceName(), StringListSerializer.DEFAULT.serialize(values));
	}

	@Override
	public void load(IPreferenceStore store) {
		doLoad(store.getString(getPreferenceName()));
	}

	@Override
	public void loadDefault(IPreferenceStore store) {
		doLoad(store.getDefaultString(getPreferenceName()));
	}

	private void doLoad(String preferenceValue) {
		checkBoxFields.values().stream().forEach(field -> field.setSelection(false));
		StringListSerializer.DEFAULT.deserialize(preferenceValue).stream()
			.map(checkBoxFields::get)
			.filter(Objects::nonNull)
			.forEach(field -> field.setSelection(true));
	}
}
