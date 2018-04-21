package org.sidiff.integration.preferences.fieldeditors;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.JFaceResources;
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
		notSelected.addAll(selected);
		selected.clear();
		if (left != null) {
            String[] array = parseString(store.getString(preferenceName));
            for (int i = 0; i < array.length; i++) {
                addString(array[i]);
            }
        }
		reloadLists();
		firePropertyChanged(new String[0], getSelectedKeys());
	}

	/**
	 * @see org.sidiff.integration.preferences.fieldeditors.PreferenceField#doLoadDefault(org.eclipse.jface.preference.IPreferenceStore, java.lang.String)
	 */
	@Override
	public void doLoadDefault(IPreferenceStore store, String preferenceName) {
		if (left != null) {
            left.removeAll();
            String[] array = parseString(store.getDefaultString(preferenceName));
            for (int i = 0; i < array.length; i++) {
            	addString(array[i]);
            }
        }
		reloadLists();
		firePropertyChanged(new String[0], getSelectedKeys());
	}

	/**
	 * @see org.sidiff.integration.preferences.fieldeditors.PreferenceField#doSave(org.eclipse.jface.preference.IPreferenceStore, java.lang.String)
	 */
	@Override
	public void doSave(IPreferenceStore store, String preferenceName) {
		String s = createList();
        if (s != null) {
        	store.setValue(preferenceName, s);
		}
	}

	/**
	 * @see org.sidiff.integration.preferences.fieldeditors.PreferenceField#doCreateControls(org.eclipse.swt.widgets.Group, java.lang.String)
	 */
	@Override
	public void doCreateControls(Group parent, String title) {
		parent.setText(title);
		parent.setLayout(new GridLayout(3, false));
		
		left = getListControl(parent, true);
		left.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
		
        buttonBox = getButtonBox(parent);
        buttonBox.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
        
        right = getListControl(parent, false);
        right.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
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
	 * @param key the key from which the buttons label will be loaded
	 * @return the button
	 */
	private Button createButton(Composite parent, String key) {
        Button button = new Button(parent, SWT.PUSH);
        button.setText(JFaceResources.getString(key));
        button.addSelectionListener(getSelectionListener());
        return button;
    }


	/**
	 * helper method to create a selection listener for the buttons
	 * @return a SelectionListener for the buttons
	 */
	private SelectionListener getSelectionListener() {
		return new SelectionAdapter() {
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
                } else if (widget == left) {
                    selectionChanged(true);
                } else if (widget == right) {
                    selectionChanged(false);
                }
            }
		};
	}

	/**
	 * enables/disables the buttons according to the current selection
	 * (e.g. disable 'up'-button if the first element is selected)
	 * @param left true if the selection on the left side has changed
	 */
	protected void selectionChanged(boolean left) {
		if (left) {
			int index = this.left.getSelectionIndex();
	        int size = this.left.getItemCount();
	
	        remove.setEnabled(size >= 0);
	        up.setEnabled(size > 1 && index > 0);
	        down.setEnabled(size > 1 && index >= 0 && index < size - 1);
		} else {
			add.setEnabled(right.getItemCount() > 0);
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
        int target = down ? index + 1 : index - 1;

        if (index >= 0) {
        	T current = selected.get(index);
        	selected.remove(current);
        	selected.add(target, current);
            reloadLists(target);
        }
        selectionChanged(true);
	}

	/**
	 * called when 'remove' is pressed
	 */
	protected void removePressed() {
		String[] previous = getSelectedKeys();
		int index = left.getSelectionIndex();
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
		String[] previous = getSelectedKeys();
		int index = right.getSelectionIndex();
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
		up.setEnabled(enabled);
		down.setEnabled(enabled);
		add.setEnabled(enabled);
		remove.setEnabled(enabled);
		right.setEnabled(enabled);
	}

	/**
	 * helper method to create a list control
	 * @param parent the composite into which the list will be created
	 * @param left true, if the left list should be created
	 * @return a new List
	 */
	private org.eclipse.swt.widgets.List getListControl(Composite parent, boolean left) {
		if(left && this.left == null) {
			this.left = new org.eclipse.swt.widgets.List(parent, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL
	                | SWT.H_SCROLL);
			this.left.setFont(parent.getFont());
			this.left.addSelectionListener(getSelectionListener());
		} else if(right == null) {
			right = new org.eclipse.swt.widgets.List(parent, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL
	                | SWT.H_SCROLL);
			right.setFont(parent.getFont());
			right.addSelectionListener(getSelectionListener());
		}
        return left? this.left : right;
	}

	/**
	 * removes an element from the not selected list and adds it to the selected list
	 * @param key the key of the element
	 */
	private void addString(String key) {
		String[] previous = getSelectedKeys();
		for(int i = 0; i < notSelected.size(); i++) {
			T kv = notSelected.get(i);
			if(valueConverter.getValue(kv).equals(key)) {
				notSelected.remove(i);
				selected.add(kv);
				firePropertyChanged(previous, getSelectedKeys());
				return;
			}
		}
	}
	
	/**
	 * creates a string, that can be saved into the preference store
	 * @return semicolon separated list of currently selected values, in order
	 */
	private String createList() {
		StringBuilder list = new StringBuilder();
		boolean first = true;
		for(T s : selected) {
			if(first) first = false;
			else list.append(";");
			list.append(valueConverter.getValue(s));
		}
		return list.toString();
	}

	/**
	 * parses a list of semicolon separated list of currently selected values 
	 * @param stringList the currently selected settings
	 * @return a list of selected keys
	 */
	private String[] parseString(String stringList) {
		return stringList.split(";");
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
