package org.sidiff.integration.remote;

import java.io.IOException;

import org.eclipse.compare.ITypedElement;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

/**
 * 
 * @author Robert Müller
 *
 */
public interface IPlatformResourceLoader extends ILoader {

	IResource loadPlatformResource(ITypedElement input) throws IOException, CoreException;

}
