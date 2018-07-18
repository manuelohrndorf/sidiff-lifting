package org.sidiff.integration.remote.git;

import java.io.IOException;

import org.eclipse.compare.ITypedElement;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.egit.core.synchronize.GitRemoteResource;
import org.eclipse.team.core.variants.IResourceVariant;
import org.eclipse.team.internal.ui.history.FileRevisionTypedElement;
import org.sidiff.integration.remote.IPlatformResourceLoader;

/**
 * 
 * @author Robert Müller
 *
 */
@SuppressWarnings("restriction")
public class GitPlatformResourceLoader implements IPlatformResourceLoader {

	@Override
	public boolean canHandle(ITypedElement input) {
		return getVariant(input) != null;
	}

	@Override
	public IResource loadPlatformResource(ITypedElement input) throws IOException, CoreException {
		return ResourcesPlugin.getWorkspace().getRoot().getFile(
				Path.fromOSString(getVariant(input).getPath()));
	}

	private GitRemoteResource getVariant(ITypedElement input) {
		if(!(input instanceof FileRevisionTypedElement)) {
			return null;
		}
		FileRevisionTypedElement element = (FileRevisionTypedElement)input;
		IResourceVariant variant = Adapters.adapt(element.getRevision(), IResourceVariant.class);
		if(!(variant instanceof GitRemoteResource)) {
			return null;
		}
		return (GitRemoteResource)variant;
	}
}
