package org.sidiff.integration.remote.git;

import java.io.IOException;

import org.eclipse.compare.IResourceProvider;
import org.eclipse.compare.ITypedElement;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.egit.core.internal.storage.OpenWorkspaceVersionEnabled;
import org.eclipse.egit.core.synchronize.GitRemoteResource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.team.core.variants.IResourceVariant;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.integration.remote.IPlatformResourceLoader;

/**
 * @author rmueller
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
				Path.fromOSString(getVariant(input)));
	}

	private static String getVariant(ITypedElement input) {
		Object fileRevision = null;
		if(input instanceof org.eclipse.team.internal.ui.history.FileRevisionTypedElement) {
			fileRevision = ((org.eclipse.team.internal.ui.history.FileRevisionTypedElement)input).getRevision();
		} else if(input instanceof org.eclipse.egit.ui.internal.revision.FileRevisionTypedElement) {
			fileRevision = ((org.eclipse.egit.ui.internal.revision.FileRevisionTypedElement)input).getFileRevision();
		}
		if(fileRevision == null) {
			return null;
		}

		if(fileRevision instanceof IResourceProvider) {
			return ((IResourceProvider)fileRevision).getResource().getFullPath().toOSString();
		}

		if(fileRevision instanceof OpenWorkspaceVersionEnabled) {
			OpenWorkspaceVersionEnabled workspaceData = (OpenWorkspaceVersionEnabled)fileRevision;
			URI fileURI = EMFStorage.toFileURI(workspaceData.getRepository().getDirectory())
					.trimSegments(1) // trim .git folder
					.appendSegments(workspaceData.getGitPath().split("/"));
			return EMFStorage.toPlatformURI(fileURI).toPlatformString(false);
		}

		IResourceVariant variant = Adapters.adapt(fileRevision, IResourceVariant.class);
		if(!(variant instanceof GitRemoteResource)) {
			return null;
		}
		return ((GitRemoteResource)variant).getPath();
	}
}
