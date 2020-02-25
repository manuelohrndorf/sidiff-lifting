package org.sidiff.integration.remote;

import java.io.IOException;

import org.eclipse.compare.ITypedElement;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

/**
 * A <code>IPlatformResourceLoader</code> loads a platform {@link IResource}.
 * @author rmueller
 */
public interface IPlatformResourceLoader extends ILoader {

	/**
	 * Loads a platform {@link IResource} for the given {@link ITypedElement}.
	 * @param input the typed element
	 * @return the loaded platform resource
	 * @throws IOException if an I/O error occurred
	 * @throws CoreException if some other error occurred
	 */
	IResource loadPlatformResource(ITypedElement input) throws IOException, CoreException;
}
