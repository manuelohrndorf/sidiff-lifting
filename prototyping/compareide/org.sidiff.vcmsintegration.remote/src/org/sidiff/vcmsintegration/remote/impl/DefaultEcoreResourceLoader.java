package org.sidiff.vcmsintegration.remote.impl;

import java.io.IOException;

import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.compare.ITypedElement;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.vcmsintegration.remote.CompareResourceUtil;
import org.sidiff.vcmsintegration.remote.IEcoreResourceLoader;

/**
 * 
 * @author Robert Müller
 *
 */
public class DefaultEcoreResourceLoader implements IEcoreResourceLoader {

	@Override
	public boolean canHandle(ITypedElement typedElement) {
		return typedElement instanceof IStreamContentAccessor;
	}

	@Override
	public Resource loadEcoreResource(ITypedElement input, URI uri) throws IOException, CoreException {
		return CompareResourceUtil.loadResourceFromStream((IStreamContentAccessor)input, uri);
	}
}
