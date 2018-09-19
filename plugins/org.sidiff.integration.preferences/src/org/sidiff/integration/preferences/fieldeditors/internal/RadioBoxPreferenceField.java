package org.sidiff.integration.preferences.fieldeditors.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.sidiff.integration.preferences.fieldeditors.IMultiPreferenceField;
import org.sidiff.integration.preferences.valueconverters.IPreferenceValueConverter;

/**
 * A PreferenceField for multiple values, one of which can be selected
 * @author Felix Breitweiser, Robert Müller
 */
public class RadioBoxPreferenceField<T> extends PreferenceField implements IMultiPreferenceField<T> {

	private final IPreferenceValueConverter<? super T> valueConverter;
	private final boolean allowUnset;

	private List<T> inputs;
	private T selectedItem;

	private Composite parent;
	private Group group;
	private Map<String, Button> buttons;

	/**
	 * @param preferenceName the name of the preference in the store 
	 * @param title The title, shown above all Radioboxes
	 * @param inputs the input elements (map of value to label)
	 * @param valueConverter a converter, that returns a value and a label for an element in the input
	 */
	public RadioBoxPreferenceField(String preferenceName, String title, Collection<T> inputs,
			IPreferenceValueConverter<? super T> valueConverter, boolean allowUnset) {
		super(preferenceName, title);
		this.valueConverter = valueConverter;
		this.allowUnset = allowUnset;
		setInputs(inputs);
	}

	@Override
	public void load(IPreferenceStore store) {
		setSelection(findItem(store.getString(getPreferenceName())), true);
	}

	@Override
	public void loadDefault(IPreferenceStore store) {
		setSelection(findItem(store.getDefaultString(getPreferenceName())), true);
	}

	@Override
	public void save(IPreferenceStore store) {
		if(selectedItem != null || allowUnset) {
			store.setValue(getPreferenceName(), valueConverter.getValue(selectedItem));
		}
	}

	@Override
	public Control doCreateControls(Composite parent) {
		this.parent = parent;
		group = new Group(parent, SWT.NONE);
		group.setText(getTitle());
		group.setLayout(new GridLayout(1, true));

		buttons = new HashMap<>();
		for(T input : inputs) {
			createRadioButton(group, input);
		}

		if(allowUnset) {
			// create a separator if at least one other button exists
			if(!inputs.isEmpty()) {
				Label separator = new Label(group, SWT.HORIZONTAL | SWT.SEPARATOR);
				separator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			}

			createRadioButton(group, null);
		} else if(inputs.isEmpty()) {
			// unset value is not allowed, so no button is shown at all
			Label label = new Label(group, SWT.NONE);
			label.setText("None available");
		}

		return group;
	}

	protected Button createRadioButton(Group group, T input) {
		Button b = new Button(group, SWT.RADIO);
		b.addSelectionListener(SelectionListener.widgetSelectedAdapter((event) -> setSelection(input, true)));
		b.setText(valueConverter.getLabel(input));
		b.setToolTipText(valueConverter.getDescription(input));
		b.setSelection(isSelected(input));
		buttons.put(valueConverter.getValue(input), b);
		return b;
	}

	@Override
	public void setEnabled(boolean enabled) {
		for(Button button : buttons.values()) {
			button.setEnabled(enabled);
		}
	}

	@Override
	public void setInputs(Collection<T> inputs) {
		this.inputs = new ArrayList<>(inputs);
		// update the selected item to an item from the new inputs
		if(selectedItem != null || allowUnset) {
			selectedItem = findItem(valueConverter.getValue(selectedItem));
		}

		// recreate the controls
		if(parent != null && !parent.isDisposed()) {
			group.dispose();
			doCreateControls(parent);
		}
	}

	@Override
	public Collection<T> getInputs() {
		return Collections.unmodifiableCollection(inputs);
	}

	@Override
	public void setSelection(T item, boolean selected) {
		T oldSelection = selectedItem;
		if(selected) {
			selectedItem = item;
		} else if (isSelected(item)) {
			selectedItem = null;
		}

		if(selectedItem != null || allowUnset) {
			String itemValue = valueConverter.getValue(selectedItem);
			for(Entry<String, Button> entry : buttons.entrySet()) {
				if(entry.getKey().equals(itemValue)) {
					entry.getValue().setSelection(selected);
				} else {
					entry.getValue().setSelection(!selected);
				}
			}

			if(oldSelection != null || allowUnset) {
				firePropertyChanged(valueConverter.getValue(oldSelection), itemValue);
			}
		}
	}

	@Override
	public boolean isSelected(T item) {
		return selectedItem != null && valueConverter.getValue(item).equals(valueConverter.getValue(selectedItem));
	}

	@Override
	public Collection<T> getSelection() {
		return selectedItem == null ? Collections.emptySet() : Collections.singleton(selectedItem);
	}

	private T findItem(String value) {
		for(T input : inputs) {
			if(valueConverter.getValue(input).equals(value)) {
				return input;
			}
		}
		return null;
	}
}
