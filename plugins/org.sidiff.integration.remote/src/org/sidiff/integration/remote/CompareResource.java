package org.sidiff.integration.remote;

import java.io.IOException;
import java.net.URISyntaxException;

import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;

/**
 * The <code>CompareResource</code> class encapsulates a object
 * of type {@link ITypedElement} which is one of the sides of a {@link ICompareInput}.
 * A <code>CompareResource</code> can be loaded using the {@link #load(ITypedElement)} method.
 * @author Robert Müller
 *
 */
public class CompareResource {

	public enum Side {
		LEFT, RIGHT, ANCESTOR
	}

	private ITypedElement typedElement; // can be null for empty resources
	private Resource resource; // can be null for empty resources
	private IResource platformResource; // can be null if resolution not possible
	private boolean editable;
	private IRelatedFileResolver relatedFileResolver;

	CompareResource(ITypedElement typedElement) {
		this.typedElement = typedElement;
		this.resource = null;
		this.platformResource = null;
		this.editable = false;
		this.relatedFileResolver = null;
	}

	/**
	 * Returns the {@link ITypedElement} from which this compare resource was loaded.
	 * @return the typed element, <code>null</code> if this compare resource is empty
	 */
	public ITypedElement getTypedElement() {
		return typedElement;
	}

	void setTypedElement(ITypedElement typedElement) {
		this.typedElement = typedElement;
	}

	/**
	 * Returns the Ecore {@link Resource} of this compare resource.
	 * @return the ecore resource, <code>null</code> if this compare resource is empty, or if the resolution failed
	 */
	public Resource getResource() {
		return resource;
	}

	void setResource(Resource resource) {
		this.resource = resource;
	}

	/**
	 * Returns the URI of the Ecore resource. Equivalent to:
	 * <pre>getResource().getURI()</pre>
	 * @return the URI, <code>null</code> if the resource is <code>null</code>
	 */
	public URI getURI() {
		if(resource == null) {
			return null;
		}
		return resource.getURI();
	}

	/**
	 * Returns the platform {@link IResource} of this compare resource.
	 * @return the platform resource, <code>null</code> if this compare resource is empty, or if the resolution failed
	 */
	public IResource getPlatformResource() {
		return platformResource;
	}

	void setPlatformResource(IResource platformResource) {
		this.platformResource = platformResource;
	}

	/**
	 * Returns whether this compare resource is editable.
	 * @return <code>true</code> if this compare resource can be edited, <code>false</code> otherwise
	 */
	public boolean isEditable() {
		return editable;
	}

	void setEditable(boolean editable) {
		this.editable = editable;
	}

	IRelatedFileResolver getRelatedFileResolver() {
		return relatedFileResolver;
	}

	void setRelatedFileResolver(IRelatedFileResolver relatedFileResolver) {
		this.relatedFileResolver = relatedFileResolver;
	}

	/**
	 * <p>Resolves a file related to this compare resource from the
	 * current revision of this compare resource.</p>
	 * <p>The target file's filename is the same as this compare
	 * resource's one, with its extension replaced with the given one.</p>
	 * <p>When possible, the resulting URI is converted to a platform URI.
	 * If it is a file URI, it should be assumed, that the file is a temporary file.</p>
	 * 
	 * @param extension the new file extension, might also be the original file extension
	 * @return URI of the related file, might be a platform URI or a file URI for a temporary file
	 */
	public URI resolveRelatedFile(String extension) {
		URI resolved = null;
		if(this.getRelatedFileResolver() != null) {
			resolved = this.getRelatedFileResolver().resolveRelatedFile(this.getTypedElement(), extension);
		}
		if(resolved == null && getURI() != null) {
			resolved = getURI().trimFileExtension().appendFileExtension(extension);
		}
		if(resolved == null) {
			return null;
		}

		// try to convert to platform URI
		if(!resolved.isPlatform()) {
			try {
				java.net.URI uri = new java.net.URI(resolved.toString());
				if(uri.isAbsolute()) {
					IFile files[] = ResourcesPlugin.getWorkspace().getRoot()
							.findFilesForLocationURI(uri);
					if(files.length > 0) {
						return URI.createPlatformResourceURI(files[0].getFullPath().toString(), true);
					}
				}
			} catch (URISyntaxException e) {
				// return the original resolved uri
			}
		}
		return resolved;
	}

	/**
	 * Returns a new <code>CompareResource</code> that is a copy of this one.
	 * The Ecore {@link Resource} of the new compare resource is a new
	 * copy of the original one.
	 * @return a copy of this compare resource with a new instance for the ecore resource
	 */
	public CompareResource mutateEcoreResource() {
		CompareResource replacement = new CompareResource(this.getTypedElement());
		replacement.setPlatformResource(this.getPlatformResource());
		replacement.setEditable(this.isEditable());
		replacement.setRelatedFileResolver(this.getRelatedFileResolver());

		// create a complete copy of the resource
		if(this.getURI() != null) {
			Resource res = new ResourceSetImpl().createResource(this.getURI());
			Copier copier = new EcoreUtil.Copier();
			res.getContents().addAll(copier.copyAll(this.getResource().getContents()));
			copier.copyReferences();
			replacement.setResource(res);
		} else {
			replacement.setResource(null);
		}

		return replacement;
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("CompareResource[");
		b.append("typedElement=").append(typedElement).append(", ");
		b.append("platformResource=").append(platformResource).append(", ");
		b.append("ecoreResource=").append(resource).append(", ");
		b.append("editable=").append(editable).append(", ");
		b.append("relatedFileResolver=").append(relatedFileResolver);
		b.append("]");
		return b.toString();
	}

	/**
	 * Loads a {@link ITypedElement} and returns a {@link CompareResource} to wrap it.
	 * @param typedElement the typed element
	 * @return compare resource wrapping the typed element
	 * @throws IOException if loading a resource failed due to an I/O error
	 * @throws CoreException if loading failed for some other reason
	 */
	public static CompareResource load(ITypedElement typedElement) throws IOException, CoreException {
		CompareResource compRes = new CompareResource(typedElement);
		CompareResourceLoader.getInstance().load(compRes);
		return compRes;
	}
}
