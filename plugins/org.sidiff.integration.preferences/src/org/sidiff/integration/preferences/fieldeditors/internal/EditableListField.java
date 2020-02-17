package org.sidiff.integration.preferences.fieldeditors.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.sidiff.common.util.StringListSerializer;
import org.sidiff.integration.preferences.fieldeditors.IMultiPreferenceField;
import org.sidiff.integration.preferences.valueconverters.IPreferenceValueConverter;

public class EditableListField<T> extends PreferenceField implements IMultiPreferenceField<T> {

	private List<T> selected;

	private IPreferenceValueConverter<? super T> valueConverter;
	private Function<Shell,? extends T> addedValueSelector;
	private Function<String,? extends T> keyToValueConverter;
	
	private Group group;
	private org.eclipse.swt.widgets.List selectedItems;
	private Composite buttonBox;
	private Button up, down, addNew, remove;
	private Label descriptionLabel;
	private GridData descriptionData;
	private SelectionListener selectionListener;

	public EditableListField(String preferenceName, String title, IPreferenceValueConverter<? super T> valueConverter,
			Function<Shell,? extends T> addedValueSelector, Function<String,? extends T> keyToValueConverter) {
		super(preferenceName, title);
		this.valueConverter = valueConverter;
		this.addedValueSelector = addedValueSelector;
		this.keyToValueConverter = keyToValueConverter;
		this.selected = new ArrayList<>();
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

	private void addInitialElements(String value) {
		List<String> keys = StringListSerializer.DEFAULT.deserialize(value);
		List<String> previous = getSelectedKeys();

		selected.clear();

		for(String key : keys) {
			T actualItem = keyToValueConverter.apply(key);
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
		listContainer.setLayout(new GridLayout(2, false));
		listContainer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		selectedItems = new org.eclipse.swt.widgets.List(listContainer, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL | SWT.H_SCROLL);
		selectedItems.addSelectionListener(getSelectionListener());
		selectedItems.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		buttonBox = createButtonBox(listContainer);
		buttonBox.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));

		descriptionLabel = new Label(group, SWT.NONE);
		descriptionData = new GridData(SWT.FILL, SWT.FILL, true, true);
		descriptionData.exclude = true;
		descriptionLabel.setLayoutData(descriptionData);
		descriptionLabel.setVisible(false);

		updateButtonStates();
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
		addNew = createButton(buttonBox, "Add New");
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
					if (widget == addNew) {
						T value = addedValueSelector.apply(widget.getDisplay().getActiveShell());
						if(value != null) {
							setSelection(value, true);
						}
					} else if (widget == remove) {
						setSelection(selected.get(selectedItems.getSelectionIndex()), false);
					} else if (widget == up) {
						swap(false);
					} else if (widget == down) {
						swap(true);
					} else if(widget == selectedItems && selectedItems.getSelectionIndex() != -1) {
						updateDescription(selected.get(selectedItems.getSelectionIndex()));
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
		final int index = selectedItems.getSelectionIndex();
        remove.setEnabled(index != -1);
        up.setEnabled(index > 0);
        down.setEnabled(index >= 0 && index < selectedItems.getItemCount() - 1);
        addNew.setEnabled(true);
	}

	/**
	 * swap currently selected element upward/downward
	 * @param down true if element is swapped downward
	 */
	private void swap(boolean down)  {
		List<String> previous = getSelectedKeys();
		int index = selectedItems.getSelectionIndex();
		int target = index + (down ? 1 : -1);
		T current = selected.get(index);
		selected.remove(current);
		selected.add(target, current);
		reloadLists();
		selectedItems.setSelection(target);
		firePropertyChanged(previous, getSelectedKeys());
	}

	/**
	 * reloads both lists, retaining their currently selected elements
	 */
	private void reloadLists() {
		int selectionLeft = selectedItems.getSelectionIndex();
		selectedItems.removeAll();
		for(T item : selected) {
			selectedItems.add(valueConverter.getLabel(item));
		}
		selectedItems.setSelection(selectionLeft);

		selectedItems.pack();
		selectedItems.requestLayout();
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
		selectedItems.setEnabled(enabled);
		buttonBox.setEnabled(enabled);
		updateButtonStates();
	}

	@Override
	public void setInputs(Collection<T> inputs) {
		throw new UnsupportedOperationException("Possible inputs are determined by the addedValueSupplier");
	}

	@Override
	public Collection<T> getInputs() {
		return Collections.emptySet();
	}

	@Override
	public void setSelection(T item, boolean selected) {
		List<String> previous = getSelectedKeys();
		if(selected) {
			this.selected.add(item);
		} else {
			this.selected.remove(item);
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
}
