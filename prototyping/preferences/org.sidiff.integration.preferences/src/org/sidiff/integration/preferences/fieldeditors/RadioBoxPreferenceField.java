package org.sidiff.integration.preferences.fieldeditors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.sidiff.integration.preferences.valueconverters.EnumPreferenceValueConverter;
import org.sidiff.integration.preferences.valueconverters.IPreferenceValueConverter;

/**
 * A PreferenceField for multiple values, one of which can be selected
 * @author Felix Breitweiser, Robert Müller
 */
public class RadioBoxPreferenceField<T> extends PreferenceField {

	private final List<T> inputs;
	private Map<String, Button> buttons;
	private String current;
	private IPreferenceValueConverter<T> valueConverter;

	/**
	 * @param preferenceName the name of the preference in the store 
	 * @param title The title, shown above all Radioboxes
	 * @param inputs the input elements (map of value to label)
	 * @param valueConverter a converter, that returns a value and a label for an element in the input
	 */
	public RadioBoxPreferenceField(String preferenceName, String title,
			Collection<? extends T> inputs, IPreferenceValueConverter<T> valueConverter) {
		super(preferenceName, title);
		this.inputs = Collections.unmodifiableList(new ArrayList<T>(inputs));
		this.valueConverter = valueConverter;
	}

	/**
	 * @see org.sidiff.integration.preferences.fieldeditors.PreferenceField#doLoad(org.eclipse.jface.preference.IPreferenceStore, java.lang.String)
	 */
	@Override
	public void doLoad(IPreferenceStore store, String preferenceName) {
		current = store.getString(preferenceName);
		updateButton();
	}

	/** 
	 * @see org.sidiff.integration.preferences.fieldeditors.PreferenceField#doLoadDefault(org.eclipse.jface.preference.IPreferenceStore, java.lang.String)
	 */
	@Override
	public void doLoadDefault(IPreferenceStore store, String preferenceName) {
		current = store.getDefaultString(preferenceName);
		updateButton();
	}

	/**
	 * updates the state of the Radioboxes, selecting the correct box if necessary
	 */
	public void updateButton() {
		for(Button button : buttons.values()) {
			button.setSelection(false);
		}
		
		if(buttons.containsKey(current))
			buttons.get(current).setSelection(true);
	}

	/**
	 * @see org.sidiff.integration.preferences.fieldeditors.PreferenceField#doSave(org.eclipse.jface.preference.IPreferenceStore, java.lang.String)
	 */
	@Override
	public void doSave(IPreferenceStore store, String preferenceName) {
		store.setValue(preferenceName, current);
	}

	/**
	 * @see org.sidiff.integration.preferences.fieldeditors.PreferenceField#doCreateControls(org.eclipse.swt.widgets.Group, java.lang.String)
	 */
	@Override
	public void doCreateControls(Group parent, String title) {
		parent.setLayout(new RowLayout(SWT.VERTICAL));
		parent.setText(title);

		buttons = new HashMap<String, Button>();
		for(T input : inputs) {
			Button b = new Button(parent, SWT.RADIO);
			b.addSelectionListener(createSelectionListener(valueConverter.getValue(input)));
			b.setText(valueConverter.getLabel(input));
			buttons.put(valueConverter.getValue(input), b);
		}

		if(inputs.isEmpty()) {
			Label label = new Label(parent, SWT.NONE);
			label.setText("None available");
		}
	}

	/**
	 * helper method to create a SelectionListener for the radio boxes
	 * @param value the new value, that has been selected
	 * @return the newly created SelectionListener
	 */
	private SelectionListener createSelectionListener(final String value) {
		return new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String previous = current;
				current = value;
				firePropertyChanged(previous, current);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		};
	}

	/**
	 * @see org.sidiff.integration.preferences.fieldeditors.PreferenceField#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled) {
		for(Entry<String, Button> entry : buttons.entrySet()) {
			entry.getValue().setEnabled(enabled);
		}
	}

	/**
	 * a helper method that accepts an collection and a mapping function to create values and labels for all the elements in the collection
	 * @param preferenceName the name of the preference, for the resulting RadioBoxPreferenceField
	 * @param title the title of the resulting RadioBoxPreferenceField
	 * @param inputs the input collection
	 * @param valueConverter a converter, that returns a value and a label for an element in the input
	 * @return the newly created RadioBoxPreferenceField
	 */
	public static <T> RadioBoxPreferenceField<T> create(String preferenceName, String title,
			Collection<? extends T> inputs, IPreferenceValueConverter<T> valueConverter) {
		return new RadioBoxPreferenceField<T>(preferenceName, title, inputs, valueConverter);
	}

	/**
	 * a helper method that accepts an array and a mapping function to create values and labels for all the elements in the array
	 * @param preferenceName the name of the preference, for the resulting RadioBoxPreferenceField
	 * @param title the title of the resulting RadioBoxPreferenceField
	 * @param inputs the input array
	 * @param valueConverter a converter, that returns a value and a label for an element in the input
	 * @return the newly created RadioBoxPreferenceField
	 */
	public static <T> RadioBoxPreferenceField<T> create(String preferenceName, String title,
			T[] inputs, IPreferenceValueConverter<T> valueConverter) {
		return new RadioBoxPreferenceField<T>(preferenceName, title, Arrays.asList(inputs), valueConverter);
	}

	/**
	 * Creates a new RadioBoxPreferenceField for an Enum-type. Uses the {@link EnumPreferenceValueConverter}.
	 * @param preferenceName the name of the preference
	 * @param title the title of the preference field
	 * @param enumClass the class of the Enum
	 * @return the newly created RadioBoxPreferenceField
	 */
	@SuppressWarnings("unchecked")
	public static <E extends Enum<E>> RadioBoxPreferenceField<E> create(String preferenceName, String title, Class<E> enumClass) {
		return (RadioBoxPreferenceField<E>)create(preferenceName, title,
				enumClass.getEnumConstants(), new EnumPreferenceValueConverter());
	}
}
