package org.sidiff.vcmsintegration.remote.impl;

import org.eclipse.compare.IEditableContent;
import org.eclipse.compare.ITypedElement;
import org.sidiff.vcmsintegration.remote.IEditabilityResolver;

/**
 * 
 * @author Robert Müller
 *
 */
public class DefaultEditabilityResolver implements IEditabilityResolver {

	@Override
	public boolean canHandle(ITypedElement typedElement) {
		return typedElement instanceof IEditableContent;
	}

	@Override
	public boolean isEditable(ITypedElement input) {
		return ((IEditableContent)input).isEditable();
	}
}
