package org.sidiff.integration.remote.impl;

import java.io.IOException;

import org.eclipse.compare.IResourceProvider;
import org.eclipse.compare.ITypedElement;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.sidiff.integration.remote.IPlatformResourceLoader;

/**
 * 
 * @author Robert Müller
 *
 */
public class DefaultPlatformResourceLoader implements IPlatformResourceLoader {

	@Override
	public boolean canHandle(ITypedElement input) {
		return input instanceof IResourceProvider;
	}

	@Override
	public IResource loadPlatformResource(ITypedElement input) throws IOException, CoreException {
		return ((IResourceProvider)input).getResource();
	}
}
