package org.sidiff.vcmsintegration.remote;

import org.eclipse.compare.ITypedElement;
import org.eclipse.emf.common.util.URI;

/**
 * 
 * @author Robert M�ller
 *
 */
public interface IURIResolver extends ILoader {

	URI getURI(ITypedElement typedElement);

}
