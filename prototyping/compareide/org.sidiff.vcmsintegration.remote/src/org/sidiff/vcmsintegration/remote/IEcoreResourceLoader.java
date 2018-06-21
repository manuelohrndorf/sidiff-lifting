package org.sidiff.vcmsintegration.remote;

import java.io.IOException;

import org.eclipse.compare.ITypedElement;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * 
 * @author Robert Müller
 *
 */
public interface IEcoreResourceLoader extends ILoader {

	Resource loadEcoreResource(ITypedElement input, URI uri) throws IOException, CoreException;

}
