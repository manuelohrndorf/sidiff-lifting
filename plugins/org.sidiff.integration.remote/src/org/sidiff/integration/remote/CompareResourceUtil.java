package org.sidiff.integration.remote;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.modelstorage.SiDiffResourceSet;

/**
 * Contains utility functions for loading and processing {@link CompareResource}s.
 * @author rmueller
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

			SiDiffResourceSet resourceSet = SiDiffResourceSet.create(Resource.Factory.Registry.DEFAULT_EXTENSION);
			Resource resource = resourceSet.createResource(uri);
			resource.load(in, null);
			return resource;
		}
	}
}
