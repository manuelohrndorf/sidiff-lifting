package org.sidiff.vcmsintegration.preferences.fieldeditors;

import java.util.ArrayList;

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
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Widget;

/**
 * PreferenceField for selecting a subset of a given set with a configurable order
 * @author Felix Breitweiser 
 */
public class OrderListSelectField extends PreferenceField {
	
	/**
	 * All currently selected values their corresponding labels, in order
	 */
	private java.util.List<String[]> selected;
	
	/**
	 * All currently not selected values their corresponding labels
	 */
	private java.util.List<String[]> notSelected;
	
	private List left, right;
	private Composite buttonBox;
	private Button up, down, add, remove;
	
	/**
	 * @param preferenceName the name of the preference in the store 
	 * @param title The title shown above the control
	 * @param valuesAndLabels 2-Dimensional Array of strings with each element being an internal value (for the preference store) and a label
	 */
	public OrderListSelectField(String preferenceName, String title, String[][] valuesAndLabels) {
		super(preferenceName, title);
		loadValues(valuesAndLabels);
	}

	/**
	 * @param elements all the elements that can be selected
	 */
	private void loadValues(String[][] elements) {
		selected = new ArrayList<String[]>();
		notSelected = new ArrayList<String[]>();
		for(String[] element : elements) {
			notSelected.add(new String[] {element[0], element[1]});
		}
	}
	
	/**
	 * @return an array of all values, that are currently selected, in order
	 */
	private String[] getSelectedKeys() {
		String[] keys = new String[selected.size()];
		for(int i = 0; i < keys.length; i++) {
			keys[i] = selected.get(i)[0];
		}
		return keys;
	}
	
	/**
	 * @see org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField#doLoad(org.eclipse.jface.preference.IPreferenceStore, java.lang.String)
	 */
	@Override
	public void doLoad(IPreferenceStore store, String preferenceName) {
		for(String[] s : selected) {
			notSelected.add(s);
		}
		selected.clear();
		if (left != null) {
            String s = store.getString(preferenceName);
            String[] array = parseString(s);
            for (int i = 0; i < array.length; i++) {
                addString(array[i]);
            }
        }
		reloadLists();
		firePropertyChanged(new String[0], getSelectedKeys());
	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField#doLoadDefault(org.eclipse.jface.preference.IPreferenceStore, java.lang.String)
	 */
	@Override
	public void doLoadDefault(IPreferenceStore store, String preferenceName) {
		if (left != null) {
            left.removeAll();
            String s = store.getDefaultString(preferenceName);
            String[] array = parseString(s);
            for (int i = 0; i < array.length; i++) {
            	addString(array[i]);
            }
        }
		reloadLists();
		firePropertyChanged(new String[0], getSelectedKeys());
	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField#doSave(org.eclipse.jface.preference.IPreferenceStore, java.lang.String)
	 */
	@Override
	public void doSave(IPreferenceStore store, String preferenceName) {
		String s = createList();
        if (s != null) {
        	store.setValue(preferenceName, s);
		}
	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField#doCreateControls(org.eclipse.swt.widgets.Group, java.lang.String)
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
        	String[] current = selected.get(index);
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
		String[] current = selected.get(index);
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
		String[] current = notSelected.get(index);
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
		for(String[] kv : selected) {
			left.add(kv[1]);
		}
		left.setSelection(selectionLeft);
		
		right.removeAll();
		for(String[] kv : notSelected) {
			right.add(kv[1]);
		}
		right.setSelection(selectionRight);
	}
	
	/**
	 * @see org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField#setEnabled(boolean)
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
	private List getListControl(Composite parent, boolean left) {
		if(left && this.left == null) {
			this.left = new List(parent, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL
	                | SWT.H_SCROLL);
			this.left.setFont(parent.getFont());
			this.left.addSelectionListener(getSelectionListener());
		} else if(right == null) {
			right = new List(parent, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL
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
			String[] kv = notSelected.get(i);
			if(kv[0].equals(key)) {
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
		for(String[] s : selected) {
			if(first) first = false;
			else list.append(";");
			list.append(s[0]);
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
	 * a helper method that accepts an iterable and a mapping function to create values and labels for all the elements in the iterable
	 * @param preferenceName the name of the preference, for the resulting OrderListSelectField
	 * @param title the title of the resulting OrderListSelectField
	 * @param input the input interable
	 * @param provider a mapper, that returns a value and a label for an element in the input
	 * @return the newly created OrderListSelectField
	 */
	public static <T> OrderListSelectField createFromIterable(String preferenceName, String title, Iterable<T> input, ValueAndLabelProvider<T> provider) {
		ArrayList<String[]> valuesAndLabels = new ArrayList<String[]>();
		for(T t : input) {
			valuesAndLabels.add(provider.get(t));
		}
		return new OrderListSelectField(preferenceName, title, valuesAndLabels.toArray(new String[valuesAndLabels.size()][]));
	}
}
