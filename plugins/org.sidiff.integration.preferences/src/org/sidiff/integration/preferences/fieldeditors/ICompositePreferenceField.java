package org.sidiff.integration.preferences.fieldeditors;

/**
 * An {@link ICompositePreferenceField} is an {@link IPreferenceField} that contains further preference fields.
 * @author Robert Müller
 */
public interface ICompositePreferenceField<T extends IPreferenceField> extends IPreferenceField {

	/**
	 * Adds a child preference field to this preference field.
	 * @param field the child preference field
	 */
	void addField(T field);

	/**
	 * Removes all child preference fields from this preference field.
	 */
	void clearFields();
	
	/**
	 * Returns whether the composite field is empty, i.e. has no children.
	 * @return <code>true</code> if empty, <code>false</code> otherwise
	 */
	boolean isEmpty();
}
