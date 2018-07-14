package org.sidiff.integration.remote;

import org.eclipse.compare.ITypedElement;
import org.eclipse.emf.common.util.URI;

/**
 * 
 * @author Robert Müller
 *
 */
public interface IRelatedFileResolver extends ILoader {

	URI resolveRelatedFile(ITypedElement input, String diagramExt);

}
