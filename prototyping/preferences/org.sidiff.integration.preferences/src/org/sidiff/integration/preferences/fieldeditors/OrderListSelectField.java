package org.sidiff.integration.preferences.fieldeditors;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Widget;
import org.sidiff.integration.preferences.valueconverters.IPreferenceValueConverter;

/**
 * PreferenceField for selecting a subset of a given set with a configurable order
 * @author Felix Breitweiser, Robert Müller
 */
public class OrderListSelectField<T> extends PreferenceField {

	/**
	 * All currently selected values
	 */
	private List<T> selected;

	/**
	 * All currently not selected values
	 */
	private List<T> notSelected;

	private IPreferenceValueConverter<T> valueConverter;

	private org.eclipse.swt.widgets.List left, right;
	private Composite buttonBox;
	private Button up, down, add, remove;
	private SelectionListener selectionListener;

	/**
	 * @param preferenceName the name of the preference in the store 
	 * @param title The title shown above the control
	 * @param inputs the input collection
	 * @param valueConverter a converter, that returns a value and a label for an element in the input
	 */
	public OrderListSelectField(String preferenceName, String title,
			Collection<T> inputs, IPreferenceValueConverter<T> valueConverter) {
		super(preferenceName, title);
		this.valueConverter = valueConverter;
		selected = new LinkedList<T>();
		notSelected = new LinkedList<T>(inputs);
	}

	/**
	 * @return an array of all values, that are currently selected, in order
	 */
	private String[] getSelectedKeys() {
		String[] selectedValues = new String[selected.size()];
		for(int i = 0; i < selected.size(); i++) {
			selectedValues[i] = valueConverter.getValue(selected.get(i));
		}
		return selectedValues;
	}

	/**
	 * @see org.sidiff.integration.preferences.fieldeditors.PreferenceField#doLoad(org.eclipse.jface.preference.IPreferenceStore, java.lang.String)
	 */
	@Override
	public void doLoad(IPreferenceStore store, String preferenceName) {
		addInitialElements(store.getString(preferenceName).split(";"));
	}

	/**
	 * @see org.sidiff.integration.preferences.fieldeditors.PreferenceField#doLoadDefault(org.eclipse.jface.preference.IPreferenceStore, java.lang.String)
	 */
	@Override
	public void doLoadDefault(IPreferenceStore store, String preferenceName) {
		addInitialElements(store.getDefaultString(preferenceName).split(";"));
	}

	/**
	 * @see org.sidiff.integration.preferences.fieldeditors.PreferenceField#doSave(org.eclipse.jface.preference.IPreferenceStore, java.lang.String)
	 */
	@Override
	public void doSave(IPreferenceStore store, String preferenceName) {
		store.setValue(preferenceName, createList());
	}

	/**
	 * @see org.sidiff.integration.preferences.fieldeditors.PreferenceField#doCreateControls(org.eclipse.swt.widgets.Group, java.lang.String)
	 */
	@Override
	public void doCreateControls(Group parent, String title) {
		parent.setText(title);
		parent.setLayout(new GridLayout(3, false));

		left = new org.eclipse.swt.widgets.List(parent, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL | SWT.H_SCROLL);
		left.setFont(parent.getFont());
		left.addSelectionListener(getSelectionListener());
		left.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		buttonBox = getButtonBox(parent);
		buttonBox.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));

		right = new org.eclipse.swt.widgets.List(parent, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL | SWT.H_SCROLL);
		right.setFont(parent.getFont());
		right.addSelectionListener(getSelectionListener());
		right.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	}

	/**
	 * creates the buttons for adding/removing/reordering items
	 * @param parent the parent into which the buttons will be placed
	 * @return the parent
	 */
	private Composite getButtonBox(Composite parent) {
		if(buttonBox == null) {
			buttonBox = new Composite(parent, SWT.NULL);
			buttonBox.setLayout(new RowLayout(SWT.VERTICAL));
	        add = createButton(buttonBox, "Add");
	        remove = createButton(buttonBox, "Remove");
	        up = createButton(buttonBox, "Move Up");
	        down = createButton(buttonBox, "Move Down");
		}
        return buttonBox;
	}
	
	/**
	 * helper method to create a button
	 * @param parent the composite into which the button will be created
	 * @param text the button label
	 * @return the button
	 */
	private Button createButton(Composite parent, String text) {
        Button button = new Button(parent, SWT.PUSH);
        button.setText(text);
        button.addSelectionListener(getSelectionListener());
        button.setEnabled(false); // button defaults to disabled state and is enabled when items are selected
        return button;
    }

	/**
	 * helper method to create a selection listener for the buttons
	 * @return a SelectionListener for the buttons
	 */
	private SelectionListener getSelectionListener() {
		if(selectionListener == null) {
			selectionListener = new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent event) {
	                Widget widget = event.widget;
	                if (widget == add) {
	                    addPressed();
	                } else if (widget == remove) {
	                    removePressed();
	                } else if (widget == up) {
	                    upPressed();
	                } else if (widget == down) {
	                    downPressed();
	                }

	                // all actions update the button states
	                updateButtonStates();
	            }
			};
		}
		return selectionListener;
	}

	/**
	 * enables/disables the buttons according to the current selection
	 * (e.g. disable 'up'-button if the first element is selected)
	 */
	protected void updateButtonStates() {
		// left side buttons
		{
			int index = left.getSelectionIndex();
	        remove.setEnabled(index != -1);
	        up.setEnabled(index > 0);
	        down.setEnabled(index >= 0 && index < left.getItemCount() - 1);
		}

		// right side buttons
		{
			add.setEnabled(right.getSelectionIndex() != -1);
		}
	}

	/**
	 * called, when 'down' is pressed
	 */
	protected void downPressed() {
		String[] previous = getSelectedKeys();
		swap(true);
		firePropertyChanged(previous, getSelectedKeys());
	}

	/**
	 * called, when 'up' is pressed
	 */
	protected void upPressed() {
		String[] previous = getSelectedKeys();
		swap(false);
		firePropertyChanged(previous, getSelectedKeys());
	}

	/**
	 * swap currently selected element upward/downward
	 * @param down true if element is swapped downward
	 */
	protected void swap(boolean down)  {
		int index = left.getSelectionIndex();
		if(index == -1)
			return;

        int target = index + (down ? 1 : -1);
    	T current = selected.get(index);
    	selected.remove(current);
    	selected.add(target, current);
        reloadLists(target);
	}

	/**
	 * called when 'remove' is pressed
	 */
	protected void removePressed() {
		int index = left.getSelectionIndex();
		if(index == -1)
			return;

		String[] previous = getSelectedKeys();
		T current = selected.get(index);
		selected.remove(current);
		notSelected.add(current);
		reloadLists();
		firePropertyChanged(previous, getSelectedKeys());
	}

	/**
	 * called when 'add' is pressed
	 */
	protected void addPressed() {
		int index = right.getSelectionIndex();
		if(index == -1)
			return;

		String[] previous = getSelectedKeys();
		T current = notSelected.get(index);
		notSelected.remove(current);
		selected.add(current);
		reloadLists();
		firePropertyChanged(previous, getSelectedKeys());
	}

	/**
	 * reloads both lists, retaining their currently selected elements
	 */
	private void reloadLists() {
		reloadLists(-1, -1);
	}

	/**
	 * reloads both lists, retaining their currently selected elements
	 * @param selectionLeft currently selected element index on the left
	 */
	private void reloadLists(int selectionLeft) {
		reloadLists(selectionLeft, -1);
	}

	/**
	 * reloads both lists, retaining their currently selected elements
	 * @param selectionLeft currently selected element index on the left
	 * @param selectionRight currently selected element index on the right
	 */
	private void reloadLists(int selectionLeft, int selectionRight) {
		if(selectionLeft == -1) selectionLeft = left.getSelectionIndex();
		if(selectionRight == -1) selectionRight = right.getSelectionIndex();

		left.removeAll();
		for(T item : selected) {
			left.add(valueConverter.getLabel(item));
		}
		left.setSelection(selectionLeft);

		right.removeAll();
		for(T item : notSelected) {
			right.add(valueConverter.getLabel(item));
		}
		right.setSelection(selectionRight);
	}

	/**
	 * @see org.sidiff.integration.preferences.fieldeditors.PreferenceField#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled) {
		left.setEnabled(enabled);
		buttonBox.setEnabled(enabled);
		right.setEnabled(enabled);
		updateButtonStates();
	}

	/**
	 * Adds all elements in the given array from the right side to the left side.
	 * Elements not found on the right side are ignored.
	 * @param keys the keys of the elements
	 */
	private void addInitialElements(String keys[]) {
		String[] previous = getSelectedKeys();
		notSelected.addAll(selected);
		selected.clear();

		for(String key : keys) {
			for(int i = 0; i < notSelected.size(); i++) {
				T item = notSelected.get(i);
				if(valueConverter.getValue(item).equals(key)) {
					notSelected.remove(i);
					selected.add(item);
					break; // continue with outer for-loop
				}
			}
		}

		reloadLists();
		firePropertyChanged(previous, getSelectedKeys());
	}

	/**
	 * creates a string, that can be saved into the preference store
	 * @return semicolon separated list of currently selected values, in order
	 */
	private String createList() {
		StringBuilder list = new StringBuilder();
		for(T s : selected) {
			if(list.length() > 0)
				list.append(";");
			list.append(valueConverter.getValue(s));
		}
		return list.toString();
	}

	/**
	 * a helper method that accepts an collection and a mapping function to create values and labels for all the elements in the collection
	 * @param preferenceName the name of the preference, for the resulting OrderListSelectField
	 * @param title the title of the resulting OrderListSelectField
	 * @param inputs the input collection
	 * @param valueConverter a converter, that returns a value and a label for an element in the input
	 * @return the newly created OrderListSelectField
	 */
	public static <T> OrderListSelectField<T> create(String preferenceName, String title,
			Collection<T> inputs, IPreferenceValueConverter<T> valueConverter) {
		return new OrderListSelectField<T>(preferenceName, title, inputs, valueConverter);
	}
}
