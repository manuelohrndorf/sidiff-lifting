package org.sidiff.integration.preferences.significance;

import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EPackage;
import org.sidiff.common.extension.ExtensionManager;

/**
 * Contains functions for finding whether a given domain (document type) is significant (e.g. has custom rulebases)
 * and for returning all registered document types that are significant.
 * @author Robert Müller
 *
 */
public final class DomainSignificanceManager extends ExtensionManager<IDomainSignificance> {

	DomainSignificanceManager() {
		super(IDomainSignificance.DESCRIPTION);
	}

	/**
	 * Returns whether the given document type is significant.
	 * @param documentType the document type
	 * @return <code>true</code> if custom settings are available for this document type, <code>false</code> otherwise
	 */
	public boolean isDomainSignificant(String documentType) {
		return getExtensions().stream().anyMatch(provider -> provider.isSignificant(documentType));
	}

	/**
	 * Returns all document types from the {@link EPackage.Registry#INSTANCE}
	 * that are {@link #isDomainSignificant(String) significant}.
	 * @return
	 */
	public Set<String> getSignificantDocumentTypes() {
		return EPackage.Registry.INSTANCE.keySet().stream()
				.filter(docType -> isDomainSignificant(docType))
				.collect(Collectors.toSet());
	}
}
