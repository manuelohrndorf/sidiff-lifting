package org.sidiff.integration.remote;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

/**
 * Contains utility functions for loading and processing {@link CompareResource}s.
 * @author Robert Müller
 *
 */
public class CompareResourceUtil {

	private CompareResourceUtil() {
		// this class is not supposed to be instantiated
	}

	public static Resource loadResourceFromStream(IStreamContentAccessor input, URI uri) throws IOException, CoreException {
		try(InputStream in = input.getContents()) {
			if(in == null) {
				throw new IOException("object has no streamable contents");
			}

			ResourceSet resourceSet = new ResourceSetImpl();
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
				.put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());
			Resource resource = resourceSet.createResource(uri);
			resource.load(in, null);
			return resource;
		}
	}
}
