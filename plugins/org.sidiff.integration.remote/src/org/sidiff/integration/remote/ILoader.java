package org.sidiff.integration.remote;

import org.eclipse.compare.ITypedElement;

/**
 * 
 * @author Robert M�ller
 *
 */
public interface ILoader {

	boolean canHandle(ITypedElement typedElement);

}
