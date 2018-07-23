package org.sidiff.integration.remote;

import java.io.IOException;

import org.eclipse.compare.ITypedElement;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * A <code>IEcoreResourceLoader</code> loads an Ecore {@link Resource}.
 * @author Robert Müller
 *
 */
public interface IEcoreResourceLoader extends ILoader {

	/**
	 * Loads an Ecore {@link Resource} for the given input {@link ITypedElement}.
	 * @param input the typed element
	 * @param uri the URI that should be used for the resource
	 * @return the loaded resource
	 * @throws IOException if an I/O error occurred
	 * @throws CoreException if some other error occurred
	 */
	Resource loadEcoreResource(ITypedElement input, URI uri) throws IOException, CoreException;
}
