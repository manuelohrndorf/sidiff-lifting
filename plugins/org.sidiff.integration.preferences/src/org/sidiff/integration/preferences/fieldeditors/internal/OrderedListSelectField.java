package org.sidiff.integration.preferences.fieldeditors.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

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
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Widget;
import org.sidiff.common.util.StringListSerializer;
import org.sidiff.integration.preferences.fieldeditors.IMultiPreferenceField;
import org.sidiff.integration.preferences.valueconverters.IPreferenceValueConverter;

/**
 * PreferenceField for selecting a subset of a given set with a configurable order
 * @author Felix Breitweiser, Robert Müller
 */
public class OrderedListSelectField<T> extends PreferenceField implements IMultiPreferenceField<T> {

	/**
	 * All currently selected values
	 */
	private List<T> selected;

	/**
	 * All currently not selected values
	 */
	private List<T> notSelected;

	private SortedMap<String,T> value2item;

	private IPreferenceValueConverter<? super T> valueConverter;

	private Group group;
	private org.eclipse.swt.widgets.List left, right;
	private Composite buttonBox;
	private Button up, down, add, remove;
	private Label descriptionLabel;
	private GridData descriptionData;
	private SelectionListener selectionListener;

	/**
	 * @param preferenceName the name of the preference in the store 
	 * @param title The title shown above the control
	 * @param inputs the input collection
	 * @param valueConverter a converter, that returns a value and a label for an element in the input
	 */
	public OrderedListSelectField(String preferenceName, String title,
			Collection<T> inputs, IPreferenceValueConverter<? super T> valueConverter) {
		super(preferenceName, title);
		this.valueConverter = valueConverter;
		this.selected = new ArrayList<>();
		this.notSelected = new ArrayList<>(inputs);
		setInputs(inputs);
	}

	/**
	 * @return a list of all values that are currently selected, in order
	 */
	private List<String> getSelectedKeys() {
		return selected.stream().map(valueConverter::getValue).collect(Collectors.toList());
	}

	@Override
	public void load(IPreferenceStore store) {
		addInitialElements(store.getString(getPreferenceName()));
	}

	@Override
	public void loadDefault(IPreferenceStore store) {
		addInitialElements(store.getDefaultString(getPreferenceName()));
	}

	/**
	 * Adds all elements in the given array from the right side to the left side.
	 * Elements not found on the right side are ignored.
	 * @param keys the keys of the elements
	 */
	private void addInitialElements(String value) {
		List<String> keys = StringListSerializer.DEFAULT.deserialize(value);
		List<String> previous = getSelectedKeys();

		notSelected.addAll(selected);
		selected.clear();

		for(String key : keys) {
			T actualItem = value2item.get(key);
			if(actualItem != null) {
				setSelection(actualItem, true);
			}
		}

		reloadLists();
		firePropertyChanged(previous, getSelectedKeys());
	}

	@Override
	public void save(IPreferenceStore store) {
		store.setValue(getPreferenceName(), StringListSerializer.DEFAULT.serialize(getSelectedKeys()));
	}

	@Override
	public Control doCreateControls(Composite parent) {
		group = new Group(parent, SWT.NONE);
		group.setText(getTitle());
		group.setLayout(new GridLayout());

		Composite listContainer = new Composite(group, SWT.NONE);
		listContainer.setLayout(new GridLayout(3, false));
		listContainer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		left = new org.eclipse.swt.widgets.List(listContainer, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL | SWT.H_SCROLL);
		left.addSelectionListener(getSelectionListener());
		left.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		buttonBox = createButtonBox(listContainer);
		buttonBox.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));

		right = new org.eclipse.swt.widgets.List(listContainer, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL | SWT.H_SCROLL);
		right.addSelectionListener(getSelectionListener());
		right.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		descriptionLabel = new Label(group, SWT.NONE);
		descriptionData = new GridData(SWT.FILL, SWT.FILL, true, true);
		descriptionData.exclude = true;
		descriptionLabel.setLayoutData(descriptionData);
		descriptionLabel.setVisible(false);

		return group;
	}

	/**
	 * creates the buttons for adding/removing/reordering items
	 * @param parent the parent into which the buttons will be placed
	 * @return the parent
	 */
	private Composite createButtonBox(Composite parent) {
		buttonBox = new Composite(parent, SWT.NULL);
		buttonBox.setLayout(new RowLayout(SWT.VERTICAL));
		add = createButton(buttonBox, "Add");
		remove = createButton(buttonBox, "Remove");
		up = createButton(buttonBox, "Move Up");
		down = createButton(buttonBox, "Move Down");
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
						setSelection(notSelected.get(right.getSelectionIndex()), true);
					} else if (widget == remove) {
						setSelection(selected.get(left.getSelectionIndex()), false);
					} else if (widget == up) {
						swap(false);
					} else if (widget == down) {
						swap(true);
					} else if(widget == left && left.getSelectionIndex() != -1) {
						updateDescription(selected.get(left.getSelectionIndex()));
					} else if(widget == right && right.getSelectionIndex() != -1) {
						updateDescription(notSelected.get(right.getSelectionIndex()));
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
			final int index = left.getSelectionIndex();
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
	 * swap currently selected element upward/downward
	 * @param down true if element is swapped downward
	 */
	private void swap(boolean down)  {
		List<String> previous = getSelectedKeys();
		int index = left.getSelectionIndex();
		int target = index + (down ? 1 : -1);
		T current = selected.get(index);
		selected.remove(current);
		selected.add(target, current);
		reloadLists();
		left.setSelection(target);
		firePropertyChanged(previous, getSelectedKeys());
	}

	/**
	 * reloads both lists, retaining their currently selected elements
	 */
	private void reloadLists() {
		int selectionLeft = left.getSelectionIndex();
		left.removeAll();
		for(T item : selected) {
			left.add(valueConverter.getLabel(item));
		}
		left.setSelection(selectionLeft);

		int selectionRight = right.getSelectionIndex();
		right.removeAll();
		for(T item : notSelected) {
			right.add(valueConverter.getLabel(item));
		}
		right.setSelection(selectionRight);

		// update the size of the lists
		left.pack();
		left.requestLayout();
		right.pack();
		right.requestLayout();
	}

	private void updateDescription(T selection) {
		String descriptionText = valueConverter.getDescription(selection);
		if(descriptionText == null) {
			descriptionData.exclude = true;
			descriptionLabel.setVisible(false);
		} else {
			descriptionLabel.setText(valueConverter.getLabel(selection) + ": " + descriptionText);
			descriptionData.exclude = false;
			descriptionLabel.setVisible(true);
		}
		descriptionLabel.requestLayout();
	}

	@Override
	public void setEnabled(boolean enabled) {
		left.setEnabled(enabled);
		buttonBox.setEnabled(enabled);
		right.setEnabled(enabled);
		updateButtonStates();
	}

	@Override
	public void setInputs(Collection<T> inputs) {
		List<String> previous = getSelectedKeys();
		value2item = new TreeMap<>();
		for(T item : inputs) {
			value2item.put(valueConverter.getValue(item), item);
		}

		selected.removeIf(e -> !value2item.containsKey(valueConverter.getValue(e)));
		notSelected = new ArrayList<>(inputs);
		notSelected.removeAll(selected);

		if(left != null && right != null && group != null) {
			reloadLists();
		}
		firePropertyChanged(previous, getSelectedKeys());
	}

	@Override
	public Collection<T> getInputs() {
		return Collections.unmodifiableCollection(value2item.values());
	}

	@Override
	public void setSelection(T item, boolean selected) {
		T actualItem = getActualItem(item);
		if(actualItem == null) {
			return;
		}

		List<String> previous = getSelectedKeys();
		if(selected) {
			// select the item
			if(notSelected.contains(actualItem) && !this.selected.contains(actualItem)) {
				notSelected.remove(actualItem);
				this.selected.add(actualItem);
			}
		} else {
			// unselect the item
			if(this.selected.contains(actualItem) && !this.notSelected.contains(actualItem)) {
				this.selected.remove(actualItem);
				notSelected.add(actualItem);
			}
		}

		reloadLists();
		firePropertyChanged(previous, getSelectedKeys());
	}

	@Override
	public boolean isSelected(T item) {
		return selected.contains(item);
	}

	@Override
	public Collection<T> getSelection() {
		return new ArrayList<>(selected);
	}

	private T getActualItem(T item) {
		if(item == null) return null;
		return value2item.get(valueConverter.getValue(item));
	}
}
