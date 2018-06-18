package org.sidiff.vcmsintegration.remote;

import org.eclipse.compare.ITypedElement;

/**
 * 
 * @author Robert M�ller
 *
 */
public interface IEditabilityResolver extends ILoader {

	boolean isEditable(ITypedElement input);

}
