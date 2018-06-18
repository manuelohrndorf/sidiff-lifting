package org.sidiff.vcmsintegration.remote;

import org.eclipse.compare.ITypedElement;

/**
 * 
 * @author Robert Müller
 *
 */
public interface ILoader {

	boolean canHandle(ITypedElement typedElement);

}
