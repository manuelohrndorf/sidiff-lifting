package org.sidiff.integration.remote.svn;

import org.eclipse.compare.ITypedElement;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.team.svn.core.connector.SVNEntryInfo;
import org.eclipse.team.svn.core.connector.SVNEntryRevisionReference;
import org.eclipse.team.svn.core.connector.SVNRevision;
import org.eclipse.team.svn.core.operation.AbstractGetFileContentOperation;
import org.eclipse.team.svn.core.operation.IActionOperation;
import org.eclipse.team.svn.core.operation.local.GetLocalFileContentOperation;
import org.eclipse.team.svn.core.operation.remote.GetFileContentOperation;
import org.eclipse.team.svn.core.resource.ILocalResource;
import org.eclipse.team.svn.core.resource.IRepositoryLocation;
import org.eclipse.team.svn.core.resource.IRepositoryResource;
import org.eclipse.team.svn.core.svnstorage.SVNRemoteStorage;
import org.eclipse.team.svn.core.svnstorage.SVNRepositoryFile;
import org.eclipse.team.svn.core.synchronize.variant.ResourceVariant;
import org.eclipse.team.svn.core.utility.FileUtility;
import org.eclipse.team.svn.core.utility.SVNUtility;
import org.eclipse.team.svn.ui.compare.ResourceCompareInput;
import org.sidiff.integration.remote.IRelatedFileResolver;

/**
 * Related file resolver for SVN.
 * <b>This resolver only resolves files from exactly the same
 * revision as the given input, if the related file is only part
 * of another revision, it won't work!</b>
 * @author Robert Müller
 */
public class SVNRelatedFileResolver implements IRelatedFileResolver {

	@Override
	public boolean canHandle(ITypedElement input) {
		return input instanceof ResourceCompareInput.ResourceElement
				|| SVNRemoteResourceUtil.getVariant(input) != null;
	}

	@Override
	public URI resolveRelatedFile(ITypedElement input, String extension) throws CoreException {
		ILocalResource local = null;
		IRepositoryResource remote = null;

		// determine local/remote resource from input
		if(input instanceof ResourceCompareInput.ResourceElement) {
			ResourceCompareInput.ResourceElement elem = (ResourceCompareInput.ResourceElement)input;
			remote = elem.getRepositoryResource();
			local = elem.getLocalResource();
		} else {
			ResourceVariant variant = SVNRemoteResourceUtil.getVariant(input);
			if(variant == null) {
				throw new IllegalArgumentException("resolver cannot handle this input: " + input);
			}

			// see org.eclipse.team.svn.core.synchronize.variant.RemoteFileVariant
			local = variant.getResource();
			if (local.isCopied()) {
				IRepositoryLocation location = SVNRemoteStorage.instance().getRepositoryLocation(local.getResource());
				SVNEntryInfo []st = SVNUtility.info(new SVNEntryRevisionReference(FileUtility.getWorkingCopyPath(local.getResource())));
				remote = location.asRepositoryFile(st[0].copyFromUrl, false);
				remote.setSelectedRevision(SVNRevision.fromNumber(st[0].copyFromRevision));
				remote.setPegRevision(SVNRevision.fromNumber(st[0].copyFromRevision));
			}
			else {
				remote = SVNRemoteStorage.instance().asRepositoryResource(local.getResource());
				remote.setSelectedRevision(SVNRevision.fromNumber(local.getRevision()));
				remote.setPegRevision(SVNRevision.fromNumber(local.getRevision()));
			}
		}

		// load the local/remote resource via SVN
		String origUrl = remote.getUrl();
		int lastDotIndex = origUrl.lastIndexOf('.');
		if(lastDotIndex == -1) {
			return null;
		}
		SVNRevision revision = remote.getSelectedRevision();
		AbstractGetFileContentOperation fetcher;
		if(revision.getKind() == SVNRevision.Kind.BASE || revision.getKind() == SVNRevision.Kind.WORKING) {
			int lastSlashIndex = origUrl.lastIndexOf('/', lastDotIndex);
			if(lastSlashIndex == -1) {
				return null;
			}
			String name = origUrl.substring(lastSlashIndex, lastDotIndex);
			IResource localResource = local.getResource().getParent().findMember(name + "." + extension);
			fetcher = new GetLocalFileContentOperation(localResource, revision.getKind());
		} else {
			String diagramUrl = origUrl.substring(0, lastDotIndex) + "." + extension;
			SVNRepositoryFile svnFile = new SVNRepositoryFile(remote.getRepositoryLocation(), diagramUrl, revision);
			fetcher = new GetFileContentOperation(svnFile);
		}
		IActionOperation operation = fetcher.run(new NullProgressMonitor());
		if(operation.getStatus().getSeverity() >= IStatus.ERROR) {
			throw new CoreException(operation.getStatus());
		}
		return URI.createFileURI(fetcher.getTemporaryPath());
	}
}
