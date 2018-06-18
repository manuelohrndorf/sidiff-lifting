package org.sidiff.vcmsintegration.remote.svn;

import java.io.IOException;

import org.eclipse.compare.ITypedElement;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.team.svn.core.operation.IResourcePropertyProvider;
import org.sidiff.vcmsintegration.remote.IPlatformResourceLoader;

/**
 * 
 * @author Robert Müller
 *
 */
public class SVNPlatformResourceLoader implements IPlatformResourceLoader {

	@Override
	public boolean canHandle(ITypedElement typedElement) {
		return typedElement instanceof IResourcePropertyProvider;
	}

	@Override
	public IResource loadPlatformResource(ITypedElement input) throws IOException, CoreException {
		return ((IResourcePropertyProvider)input).getLocal();
	}
}
