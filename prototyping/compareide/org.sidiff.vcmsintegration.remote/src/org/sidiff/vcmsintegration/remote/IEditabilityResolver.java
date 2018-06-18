package org.sidiff.vcmsintegration.remote;

import org.eclipse.compare.ITypedElement;

/**
 * 
 * @author Robert Müller
 *
 */
public interface IEditabilityResolver extends ILoader {

	boolean isEditable(ITypedElement input);

}
