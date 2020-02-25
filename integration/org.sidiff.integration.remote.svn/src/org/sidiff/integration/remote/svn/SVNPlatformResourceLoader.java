package org.sidiff.integration.remote.svn;

import java.io.IOException;

import org.eclipse.compare.ITypedElement;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.team.svn.core.synchronize.variant.ResourceVariant;
import org.eclipse.team.svn.ui.compare.ResourceCompareInput;
import org.eclipse.team.svn.ui.compare.ResourceCompareInput.ResourceElement;
import org.sidiff.integration.remote.IPlatformResourceLoader;

/**
 * @author rmueller
 */
public class SVNPlatformResourceLoader implements IPlatformResourceLoader {

	@Override
	public boolean canHandle(ITypedElement input) {
		return input instanceof ResourceCompareInput.ResourceElement
				|| SVNRemoteResourceUtil.getVariant(input) != null;
	}

	@Override
	public IResource loadPlatformResource(ITypedElement input) throws IOException, CoreException {
		if(input instanceof ResourceCompareInput.ResourceElement) {
			ResourceElement resourceElement = (ResourceCompareInput.ResourceElement) input;
			if(resourceElement.getLocalResource() != null) {
				return resourceElement.getLocalResource().getResource();
			}
			return null;
		}

		ResourceVariant variant = SVNRemoteResourceUtil.getVariant(input);
		if(variant != null) {
			return variant.getResource().getResource();
		}

		throw new IllegalArgumentException("loader cannot handle this input: " + input);
	}
}
