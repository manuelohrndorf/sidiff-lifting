package org.sidiff.vcmsintegration.remote.svn;

import org.eclipse.compare.ITypedElement;
import org.eclipse.team.svn.core.operation.IResourcePropertyProvider;
import org.sidiff.vcmsintegration.remote.IEditabilityResolver;

/**
 * 
 * @author Robert Müller
 *
 */
public class SVNEditabilityResolver implements IEditabilityResolver {

	@Override
	public boolean canHandle(ITypedElement typedElement) {
		return typedElement instanceof IResourcePropertyProvider;
	}

	@Override
	public boolean isEditable(ITypedElement input) {
		return ((IResourcePropertyProvider)input).isEditAllowed();
	}
}
