package org.sidiff.integration.remote;

import java.io.IOException;

import org.eclipse.compare.ITypedElement;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;

/**
 * A <code>IRelatedFileResolver</code> resolves files related to a
 * given input, e.g. diagram files for a model file.
 * @author rmueller
 */
public interface IRelatedFileResolver extends ILoader {

	/**
	 * Resolves a related file for the given input {@link ITypedElement},
	 * with the given file extension, and returns a {@link URI} for the
	 * resolved file. If the result is a URI outside of the workspace,
	 * it is assumed that it is a temporary file.
	 * @param input the typed element
	 * @param extension the file extension
	 * @return URI for related file, <code>null</code> if resolution failed
	 * @throws IOException if an I/O error occurred
	 * @throws CoreException if some other error occurred
	 */
	URI resolveRelatedFile(ITypedElement input, String extension) throws IOException, CoreException;
}
