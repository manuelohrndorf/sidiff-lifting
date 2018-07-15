package org.sidiff.integration.remote.impl;

import org.eclipse.compare.IEditableContent;
import org.eclipse.compare.ITypedElement;
import org.sidiff.integration.remote.IEditabilityResolver;

/**
 * 
 * @author Robert Müller
 *
 */
public class DefaultEditabilityResolver implements IEditabilityResolver {

	@Override
	public boolean canHandle(ITypedElement input) {
		return input instanceof IEditableContent;
	}

	@Override
	public boolean isEditable(ITypedElement input) {
		return ((IEditableContent)input).isEditable();
	}
}
