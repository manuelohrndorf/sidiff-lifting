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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.sidiff.integration.preferences.valueconverters.IPreferenceValueConverter;

/**
 * A PreferenceField for multiple values, one of which can be selected
 * @author Felix Breitweiser, Robert Müller
 */
public class RadioBoxPreferenceField<T> extends PreferenceField {

	private final List<T> inputs;
	private final IPreferenceValueConverter<? super T> valueConverter;
	private final boolean allowUnset;

	private String current;
	private Map<String, Button> buttons;
	private SelectionListener selectionListener;

	/**
	 * @param preferenceName the name of the preference in the store 
	 * @param title The title, shown above all Radioboxes
	 * @param inputs the input elements (map of value to label)
	 * @param valueConverter a converter, that returns a value and a label for an element in the input
	 */
	public RadioBoxPreferenceField(String preferenceName, String title, Collection<? extends T> inputs,
			IPreferenceValueConverter<? super T> valueConverter, boolean allowUnset) {
		super(preferenceName, title);
		this.inputs = Collections.unmodifiableList(new ArrayList<T>(inputs));
		this.valueConverter = valueConverter;
		this.allowUnset = allowUnset;
	}

	@Override
	public void load(IPreferenceStore store) {
		current = store.getString(getPreferenceName());
		updateButton();
	}

	@Override
	public void loadDefault(IPreferenceStore store) {
		current = store.getDefaultString(getPreferenceName());
		updateButton();
	}

	/**
	 * updates the state of the Radioboxes, selecting the correct box if necessary
	 */
	protected void updateButton() {
		for(Button button : buttons.values()) {
			button.setSelection(false);
		}

		if(buttons.containsKey(current)) {
			buttons.get(current).setSelection(true);
		}
	}

	@Override
	public void save(IPreferenceStore store) {
		store.setValue(getPreferenceName(), current);
	}

	@Override
	public Control doCreateControls(Composite parent, String title) {
		Group group = new Group(parent, SWT.NONE);
		group.setText(title);
		group.setLayout(new GridLayout(1, true));

		buttons = new HashMap<String, Button>();
		for(T input : inputs) {
			createRadioButton(group, valueConverter.getValue(input), valueConverter.getLabel(input));
		}

		if(allowUnset) {
			// create a separator if at least one other button exists
			if(!inputs.isEmpty()) {
				Label separator = new Label(group, SWT.HORIZONTAL | SWT.SEPARATOR);
				separator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			}

			createRadioButton(group, valueConverter.getValue(null), valueConverter.getLabel(null));
		} else if(inputs.isEmpty()) {
			// unset value is not allowed, so no button is shown at all
			Label label = new Label(group, SWT.NONE);
			label.setText("None available");
		}

		return group;
	}

	protected Button createRadioButton(Group group, String value, String label) {
		Button b = new Button(group, SWT.RADIO);
		b.addSelectionListener(getButtonSelectionListener());
		b.setText(label);
		buttons.put(value, b);
		return b;
	}

	private SelectionListener getButtonSelectionListener() {
		if(selectionListener == null) {
			selectionListener = new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					final Button button = (Button)e.getSource();
					if(button.getSelection()) {
						for(Map.Entry<String, Button> entry : buttons.entrySet()) {
							if(entry.getValue() == button) {
								final String previous = current;
								current = entry.getKey();
								firePropertyChanged(previous, current);
								return;
							}
						}
						
					}
				}
			};
		}
		return selectionListener;
	}

	@Override
	public void setEnabled(boolean enabled) {
		for(Entry<String, Button> entry : buttons.entrySet()) {
			entry.getValue().setEnabled(enabled);
		}
	}
}
