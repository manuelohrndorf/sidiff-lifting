package org.sidiff.integration.remote;

import org.eclipse.compare.ITypedElement;

/**
 * 
 * @author Robert Müller
 *
 */
public interface ILoader {

	boolean canHandle(ITypedElement typedElement);

}
