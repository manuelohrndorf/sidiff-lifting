package org.sidiff.integration.remote;

import org.eclipse.compare.ITypedElement;

/**
 * A <code>IEditabilityResolver</code> resolves whether an input is editable.
 * @author Robert Müller
 *
 */
public interface IEditabilityResolver extends ILoader {

	/**
	 * Returns whether the given input {@link ITypedElement} is editable.
	 * @param input the the typed element
	 * @return <code>true</code> if the input is editable, <code>false</code> otherwise
	 */
	boolean isEditable(ITypedElement input);
}
