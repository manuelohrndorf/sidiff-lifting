package org.sidiff.integration.remote;

import org.eclipse.compare.ITypedElement;
import org.eclipse.emf.common.util.URI;

/**
 * A <code>IURIResolver</code> resolves the {@link URI} of an input.
 * @author Robert Müller
 *
 */
public interface IURIResolver extends ILoader {

	/**
	 * Returns the {@link URI} for the given input {@link ITypedElement}.
	 * @param input the typed element
	 * @return the resolved URI, <code>null</code> if the resolution failed
	 */
	URI getURI(ITypedElement input);
}
