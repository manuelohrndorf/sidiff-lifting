package org.silift.common.util.emf;

import java.util.List;

/**
 * Simple container class for external links.
 * 
 * @author kehrer
 */
public class ExternalReferenceContainer {

	/**
	 * external links: resource -> package registry
	 */
	private List<ExternalReference> registryReferences;

	/**
	 * external links: resource -> another resource (within the same resource
	 * set)
	 */
	private List<ExternalReference> resourceSetReferences;

	public ExternalReferenceContainer(List<ExternalReference> registryReferences,
			List<ExternalReference> resourceSetReferences) {
		super();

		this.registryReferences = registryReferences;
		this.resourceSetReferences = resourceSetReferences;
	}

	public List<ExternalReference> getRegistryReferences() {
		return registryReferences;
	}

	public List<ExternalReference> getResourceSetReferences() {
		return resourceSetReferences;
	}

}
