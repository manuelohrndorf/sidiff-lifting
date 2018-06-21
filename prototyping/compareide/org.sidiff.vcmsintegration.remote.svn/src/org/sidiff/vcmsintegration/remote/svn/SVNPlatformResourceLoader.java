package org.sidiff.vcmsintegration.remote.svn;

import java.io.IOException;

import org.eclipse.compare.ITypedElement;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.team.svn.ui.compare.ResourceCompareInput;
import org.sidiff.vcmsintegration.remote.IPlatformResourceLoader;

/**
 * 
 * @author Robert Müller
 *
 */
public class SVNPlatformResourceLoader implements IPlatformResourceLoader {

	@Override
	public boolean canHandle(ITypedElement typedElement) {
		return typedElement instanceof ResourceCompareInput.ResourceElement;
	}

	@Override
	public IResource loadPlatformResource(ITypedElement input) throws IOException, CoreException {
		return ((ResourceCompareInput.ResourceElement)input).getLocalResource().getResource();
	}
}
