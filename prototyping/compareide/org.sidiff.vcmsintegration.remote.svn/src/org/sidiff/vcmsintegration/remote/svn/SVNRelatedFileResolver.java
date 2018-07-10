package org.sidiff.vcmsintegration.remote.svn;

import org.eclipse.compare.ITypedElement;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.team.svn.core.connector.SVNRevision;
import org.eclipse.team.svn.core.operation.AbstractGetFileContentOperation;
import org.eclipse.team.svn.core.operation.local.GetLocalFileContentOperation;
import org.eclipse.team.svn.core.operation.remote.GetFileContentOperation;
import org.eclipse.team.svn.core.svnstorage.SVNRepositoryFile;
import org.eclipse.team.svn.ui.compare.ResourceCompareInput;
import org.sidiff.vcmsintegration.remote.IRelatedFileResolver;

/**
 * 
 * @author Robert Müller
 *
 */
public class SVNRelatedFileResolver implements IRelatedFileResolver {

	@Override
	public boolean canHandle(ITypedElement typedElement) {
		return typedElement instanceof ResourceCompareInput.ResourceElement;
	}

	@Override
	public URI resolveRelatedFile(ITypedElement input, String diagramExt) {
		ResourceCompareInput.ResourceElement elem = (ResourceCompareInput.ResourceElement)input;
		String origUrl = elem.getRepositoryResource().getUrl();
		int lastDotIndex = origUrl.lastIndexOf('.');
		if(lastDotIndex == -1) {
			return null;
		}
		SVNRevision revision = elem.getRepositoryResource().getSelectedRevision();
		AbstractGetFileContentOperation fetcher;
		if(revision.getKind() == SVNRevision.Kind.BASE || revision.getKind() == SVNRevision.Kind.WORKING) {
			int lastSlashIndex = origUrl.lastIndexOf('/', lastDotIndex);
			if(lastSlashIndex == -1) {
				return null;
			}
			String name = origUrl.substring(lastSlashIndex, lastDotIndex);
			IResource localResource = elem.getLocalResource().getResource().getParent().findMember(name + "." + diagramExt);
			fetcher = new GetLocalFileContentOperation(localResource, revision.getKind());
		} else {
			String diagramUrl = origUrl.substring(0, lastDotIndex) + "." + diagramExt;
			SVNRepositoryFile svnFile = new SVNRepositoryFile(elem.getRepositoryResource().getRepositoryLocation(), diagramUrl, revision);
			fetcher = new GetFileContentOperation(svnFile);
		}
		fetcher.run(new NullProgressMonitor());
		return URI.createFileURI(fetcher.getTemporaryPath());
	}
}
