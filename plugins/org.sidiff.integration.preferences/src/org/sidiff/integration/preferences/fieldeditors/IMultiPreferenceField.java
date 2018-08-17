package org.sidiff.integration.preferences.fieldeditors;

import java.util.Collection;

/**
 * An {@link IMultiPreferenceField} is an {@link IPreferenceField} that displays
 * a collection of inputs and allows selection thereof.
 * @author Robert Müller
 * @param <T> the type of the preference field input
 */
public interface IMultiPreferenceField<T> extends IPreferenceField {

	/**
	 * Sets the possible inputs for this preference field.
	 * @param inputs collection of possible inputs
	 */
	void setInputs(Collection<T> inputs);

	/**
	 * Returns the possible inputs for this preference field.
	 * @return collection of possible inputs
	 */
	Collection<T> getInputs();

	/**
	 * Sets the selection state for the given item.
	 * @param item the item
	 * @param selected <code>true</code> to select, <code>false</code> to deselect
	 */
	void setSelection(T item, boolean selected);

	/**
	 * Returns whether the given item is currently selected.
	 * @param item the item
	 * @return <code>true</code> if it is selected, <code>false</code> otherwise
	 */
	boolean isSelected(T item);

	/**
	 * Returns all selected items.
	 * @return collection of all selected items
	 */
	Collection<T> getSelection();
}
