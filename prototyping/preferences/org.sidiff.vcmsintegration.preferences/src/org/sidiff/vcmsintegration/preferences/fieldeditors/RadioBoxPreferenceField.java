package org.sidiff.vcmsintegration.preferences.fieldeditors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;

/**
 * A PreferenceField for multiple values, one of which can be selected
 * @author Felix Breitweiser
 */
public class RadioBoxPreferenceField extends PreferenceField {

	String[][] valuesAndLabels;
	Map<String, Button> buttons;
	String current;
	
	/**
	 * @param preferenceName the name of the preference in the store 
	 * @param title The title, shown above all Radioboxes
	 * @param valuesAndLabels 2-Dimensional Array of strings with each element being an internal value (for the preference store) and a label 
	 */
	public RadioBoxPreferenceField(String preferenceName, String title, String[][] valuesAndLabels) {
		super(preferenceName, title);
		
		this.valuesAndLabels = valuesAndLabels;
	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField#doLoad(org.eclipse.jface.preference.IPreferenceStore, java.lang.String)
	 */
	@Override
	public void doLoad(IPreferenceStore store, String preferenceName) {
		current = store.getString(preferenceName);
		updateButton();
	}

	/** 
	 * @see org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField#doLoadDefault(org.eclipse.jface.preference.IPreferenceStore, java.lang.String)
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
	 * @see org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField#doSave(org.eclipse.jface.preference.IPreferenceStore, java.lang.String)
	 */
	@Override
	public void doSave(IPreferenceStore store, String preferenceName) {
		store.setValue(preferenceName, current);
	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField#doCreateControls(org.eclipse.swt.widgets.Group, java.lang.String)
	 */
	@Override
	public void doCreateControls(Group parent, String title) {
		parent.setLayout(new RowLayout(SWT.VERTICAL));
		parent.setText(title);
		
		buttons = new HashMap<String, Button>();
		for(String[] valueAndLabel : valuesAndLabels) {
			Button b = new Button(parent, SWT.RADIO);
			b.addSelectionListener(createSelectionListener(valueAndLabel[0]));
			b.setText(valueAndLabel[1]);
			buttons.put(valueAndLabel[0], b);
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
	 * @see org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled) {
		for(Entry<String, Button> entry : buttons.entrySet()) {
			entry.getValue().setEnabled(enabled);
		}
	}
	
	/**
	 * a helper method that accepts an iterable and a mapping function to create values and labels for all the elements in the iterable
	 * @param preferenceName the name of the preference, for the resulting RadioBoxPreferenceField
	 * @param title the title of the resulting RadioBoxPreferenceField
	 * @param input the input interable
	 * @param provider a mapper, that returns a value and a label for an element in the input
	 * @return the newly created RadioBoxPreferenceField
	 */
	public static <T> RadioBoxPreferenceField createFromIterable(String preferenceName, String title, Iterable<T> input, ValueAndLabelProvider<T> provider) {
		ArrayList<String[]> valuesAndLabels = new ArrayList<String[]>();
		for(T t : input) {
			valuesAndLabels.add(provider.get(t));
		}
		return new RadioBoxPreferenceField(preferenceName, title, valuesAndLabels.toArray(new String[valuesAndLabels.size()][]));
	}
	
	/**
	 * a helper method that accepts an array and a mapping function to create values and labels for all the elements in the array
	 * @param preferenceName the name of the preference, for the resulting RadioBoxPreferenceField
	 * @param title the title of the resulting RadioBoxPreferenceField
	 * @param input the input array
	 * @param provider a mapper, that returns a value and a label for an element in the input
	 * @return the newly created RadioBoxPreferenceField
	 */
	public static <T> RadioBoxPreferenceField createFromArray(String preferenceName, String title, T[] input, ValueAndLabelProvider<T> provider) {
		ArrayList<String[]> valuesAndLabels = new ArrayList<String[]>();
		for(T t : input) {
			valuesAndLabels.add(provider.get(t));
		}
		return new RadioBoxPreferenceField(preferenceName, title, valuesAndLabels.toArray(new String[valuesAndLabels.size()][]));
	}

}
