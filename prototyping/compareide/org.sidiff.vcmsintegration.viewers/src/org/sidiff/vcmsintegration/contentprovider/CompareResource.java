/**
 * 
 */
package org.sidiff.vcmsintegration.contentprovider;

import java.io.IOException;

import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.ResourceNode;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.egit.ui.internal.revision.FileRevisionTypedElement;
import org.eclipse.egit.ui.internal.revision.ResourceEditableRevision;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.team.svn.ui.compare.ResourceCompareInput.ResourceElement;
import org.sidiff.vcmsintegration.util.Util;

/**
 * 
 * This class is used to wrap the different inputs the StructureMergeViewer
 * gets. It contains the original input as ITypedElement, the EMF Resource which
 * is shown and some methodes to determine where the resource came from.
 * 
 * @author Louis Christ
 *
 */
@SuppressWarnings("restriction")
public class CompareResource {

	/**
	 * resource, which can be used by sidiff
	 */
	private Resource resource;

	// resource type
	/**
	 * type of resource:LEFT,RIGHT,ANCESTOR
	 */
	private ResourceType resourceType;
	/**
	 * where does the resources come from
	 */
	private boolean local;
	private boolean git;
	private boolean svn;

	// the input that the viewers get
	private ITypedElement typedElement;

	/**
	 *  Wrapper for ITypedELements. Provides a EMF resource and information about the 
	 *  source(git,svn,local)
	 * @param input ITypedElement, should also implement IStreamContentAccessor
	 * @throws CoreException
	 * @throws IOException
	 */
	public CompareResource(ITypedElement input, ResourceType resourceType) throws IOException, CoreException {
		this.typedElement = input;
		this.resourceType = resourceType;

		// default for resources is not editable
		local = false;
		git = false;
		svn = false;

		// local files
		if (input instanceof ResourceNode) {
			// ResourceNodes are local
			local = true;
			// create resource with correct path
			this.resource = Util.getResourceByTypedElement(input, ((ResourceNode) input).getResource().getFullPath().toString());
			
		// local files from git
		} else if (input instanceof ResourceEditableRevision) {
			git = true;
			local = true;
			this.resource = Util.getResourceByTypedElement(input);
			
		// remote files from git
		} else if (input instanceof FileRevisionTypedElement) {
			git = true;
			this.resource = Util.getResourceByTypedElement(input);
			
		// files from svn
		} else if (input instanceof ResourceElement) {
			// if the file is editable we assume that it is local
			String path = ((ResourceElement) input).getLocalResource().getResource().getFullPath().toString();
			
			// local svn files
			if (((ResourceElement) input).isEditable()) {
				// if the file is editable we assume that it is local
				local = true;
				// create resource with correct path
				this.resource = Util.getResourceByTypedElement(input, path);
				
			//remote svn files
			} else {
				// if it is not editable it is remote
				svn = true;
				this.resource = Util.getResourceByTypedElement(input, path);
			}
			
		// TODO test when input is FileRevisionTypedElement and if the path
		// is correct
		// remote svn file? maybe?
		} else if (input instanceof org.eclipse.team.internal.ui.history.FileRevisionTypedElement) {
			String path = ((org.eclipse.team.internal.ui.history.FileRevisionTypedElement) input).getPath();
			svn = true;
			this.resource = Util.getResourceByTypedElement(input, path);
		}
	}

	/**
	 * Return the The internal EMF {@link Resource} instance.
	 * 
	 * @return The internal {@link Resource} instance.
	 */
	public Resource getResource() {
		return resource;
	}

	/**
	 * Type of the Ressource: LEFT,RIGHT or ANCESTOR
	 */
	public ResourceType getResourceType() {
		return resourceType;
	}

	/**
	 * Reload resource from ITypedElement.
	 * 
	 * @throws IOException
	 * @throws CoreException
	 */
	public void reloadRessource() throws IOException, CoreException {
		if (local) {
			this.resource = Util.getResourceByTypedElement(this.typedElement, ((ResourceNode) this.typedElement).getResource().getFullPath().toString());
		} else {
			this.resource = Util.getResourceByTypedElement(this.typedElement);
		}
	}

	/**
	 * Is the resource editable or not. Local resources are editable. Remote
	 * resources(git,svn) are not editable.
	 * 
	 */
	public boolean isEditable() {
		return this.local;
	}

	/**
	 * Input of the constructor, which is used to create the EMF resource
	 */
	public ITypedElement getTypedElement() {
		return this.typedElement;
	}

	/**
	 * Whether the file is local or not.
	 */
	public boolean isLocal() {
		return this.local;
	}
	
	/**
	 * Whether the file is remote or not.
	 */
	public boolean isRemote() {
		return !this.local;
	}

	/**
	 * True if file is a remote git file.
	 */
	public boolean isGit() {
		return this.git;
	}
	
	/**
	 * True if file is a remote svn file.
	 */
	public boolean isSvn() {
		return this.svn;
	}

}
