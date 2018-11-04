package org.sidiff.integration.remote.svn;

import org.eclipse.compare.ITypedElement;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.team.core.variants.IResourceVariant;
import org.eclipse.team.internal.ui.history.FileRevisionTypedElement;
import org.eclipse.team.svn.core.synchronize.variant.ResourceVariant;

/**
 * 
 * @author Robert Müller
 *
 */
@SuppressWarnings("restriction") // FileRevisionTypedElement is not API
class SVNRemoteResourceUtil {

	public static ResourceVariant getVariant(ITypedElement input) {
		if(!(input instanceof FileRevisionTypedElement)) {
			return null;
		}
		FileRevisionTypedElement element = (FileRevisionTypedElement)input;
		IResourceVariant variant = Adapters.adapt(element.getRevision(), IResourceVariant.class);
		if(!(variant instanceof ResourceVariant)) {
			return null;
		}
		return (ResourceVariant)variant;
	}

}
