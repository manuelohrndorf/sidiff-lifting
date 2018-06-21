package org.sidiff.vcmsintegration.remote;

import java.io.IOException;

import org.eclipse.compare.ITypedElement;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

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
	private Side side;
	private Resource resource; // can be null for empty resources
	private IResource platformResource; // can be null if resolution not possible
	private boolean editable;

	CompareResource(ITypedElement typedElement, Side side) {
		Assert.isNotNull(side);
		this.typedElement = typedElement;
		this.side = side;
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

	public Side getSide() {
		return side;
	}

	void setSide(Side side) {
		this.side = side;
	}

	public boolean isEditable() {
		return editable;
	}
	
	void setEditable(boolean editable) {
		this.editable = editable;
	}

	public static CompareResource load(ITypedElement typedElement, Side side) throws IOException, CoreException {
		CompareResource compRes = new CompareResource(typedElement, side);
		CompareResourceLoader.getInstance().load(compRes);
		return compRes;
	}

	public static void swap(CompareResource left, CompareResource right) {
		Assert.isLegal(left.getSide() == Side.LEFT);
		Assert.isLegal(right.getSide() == Side.RIGHT);
		left.setSide(Side.RIGHT);
		right.setSide(Side.LEFT);
	}
}
