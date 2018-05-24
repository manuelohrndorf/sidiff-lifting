package org.sidiff.integration.preferences.fieldeditors;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.swt.widgets.Composite;

/**
 * An {@link IPreferenceField} represents a field on a preference page.
 * @author Robert Müller
 *
 */
public interface IPreferenceField {

	/**
	 * Loads the value of this preference field from the preference store.
	 * @param store the preference store
	 */
	void load(IPreferenceStore store);

	/**
	 * Loads the default value of this preference field from the preference store.
	 * @param store the preference store
	 */
	void loadDefault(IPreferenceStore store);

	/**
	 * Saves the value of this preference field to the preference store.
	 * @param store the preference store
	 */
	void save(IPreferenceStore store);

	/**
	 * Returns whether the value/selection of this preference field is valid.
	 * @return <code>true</code> if the field is valid, <code>false</code> otherwise
	 */
	boolean isValid();

	/**
	 * Creates the controls for this preference fields for the given parent control.
	 * @param parent the parent control
	 */
	void createControls(Composite parent);

	/**
	 * Enables/disables this preference field. Disables fields are read-only.
	 * @param enabled whether to enable or disable this field
	 */
	void setEnabled(boolean enabled);

	/**
	 * Shows/hides this preference field. Invisible fields take up no space.
	 * @param visible whether to show/hide this field
	 */
	void setVisible(boolean visible);

	/**
	 * Adds the listener to the list of listeners that will be notified when this field's value changes.
	 * @param listener the listener
	 */
	void addPropertyChangeListener(IPropertyChangeListener listener);

	/**
	 * Removes this listener from the list of listeners that will be notified when this field's value changes.
	 * @param listener the listener
	 */
	void removePropertyChangeListener(IPropertyChangeListener listener);

	/**
	 * Prefixes this field's preference name with the given prefix.
	 * @param prefix the prefix
	 */
	void setPrefix(String prefix);
}
