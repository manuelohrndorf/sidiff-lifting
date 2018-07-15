package org.sidiff.integration.remote;

import org.eclipse.compare.ITypedElement;

/**
 * The <code>ILoader</code> interface is ancestor for all extension
 * classes to the compare resource loader extension and provides common functions.
 * @author Robert Müller
 *
 */
public interface ILoader {

	/**
	 * Returns whether the given {@link ITypedElement} can be handled by this loader.
	 * Note that if this method returns <code>false</code> for an input element,
	 * other methods of subinterfaces are not called.
	 * @param input the typed element
	 * @return <code>true</code> if the given typed element can be handled, <code>false</code> otherwise
	 */
	boolean canHandle(ITypedElement input);
}
