package org.sidiff.vcmsintegration.remote;

import java.io.IOException;

import org.eclipse.compare.ITypedElement;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;

/**
 * 
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

	CompareResource(ITypedElement typedElement) {
		this.typedElement = typedElement;
		this.resource = null;
		this.platformResource = null;
		this.editable = false;
	}

	public ITypedElement getTypedElement() {
		return typedElement;
	}

	void setTypedElement(ITypedElement typedElement) {
		this.typedElement = typedElement;
	}

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

	public IResource getPlatformResource() {
		return platformResource;
	}

	void setPlatformResource(IResource platformResource) {
		this.platformResource = platformResource;
	}

	public boolean isEditable() {
		return editable;
	}

	void setEditable(boolean editable) {
		this.editable = editable;
	}

	public static CompareResource load(ITypedElement typedElement) throws IOException, CoreException {
		CompareResource compRes = new CompareResource(typedElement);
		CompareResourceLoader.getInstance().load(compRes);
		return compRes;
	}

	public CompareResource mutateEcoreResource() {
		CompareResource replacement = new CompareResource(this.getTypedElement());
		replacement.setPlatformResource(this.getPlatformResource());
		replacement.setEditable(this.isEditable());

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
		b.append("editable=").append(editable);
		b.append("]");
		return b.toString();
	}
}
