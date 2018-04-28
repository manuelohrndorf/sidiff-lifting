package org.sidiff.integration.preferences.domains.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EPackage;
import org.sidiff.integration.preferences.domains.Activator;
import org.sidiff.integration.preferences.domains.interfaces.IPreferenceDomainSignificanceProvider;

/**
 * Contains functions for finding whether a given domain (document type) is significant (e.g. has custom rulebases)
 * and for returning all registered document types that are significant.
 * @author Robert Müller
 *
 */
public class DomainSignificanceUtil {

	private static List<IPreferenceDomainSignificanceProvider> providers;

	public static List<IPreferenceDomainSignificanceProvider> getAllAvailableDomainSignificanceProviders() {
		if(providers == null) {
			providers = new ArrayList<IPreferenceDomainSignificanceProvider>();
			for(IConfigurationElement element :
					Platform.getExtensionRegistry().getConfigurationElementsFor(IPreferenceDomainSignificanceProvider.EXTENSION_POINT_ID)) {
				try {
					providers.add((IPreferenceDomainSignificanceProvider)element.createExecutableExtension(IPreferenceDomainSignificanceProvider.EXTENSION_POINT_ATTRIBUTE));
				} catch(CoreException e) {
					Activator.getDefault().getLog().log(new Status(Status.WARNING, Activator.PLUGIN_ID,
							"Executable extension for domain significance provider could not be created", e));
				}
			}
		}
		return providers;
	}

	public static boolean isDomainSignificant(String documentType) {
		for(IPreferenceDomainSignificanceProvider provider : getAllAvailableDomainSignificanceProviders()) {
			if(provider.isSignificant(documentType)) {
				return true;
			}
		}
		return false;
	}

	public static List<String> getSignificantDocumentTypes() {
		List<String> significantDocTypes = new ArrayList<String>();
		for(String docType : EPackage.Registry.INSTANCE.keySet()) {
			if(isDomainSignificant(docType)) {
				significantDocTypes.add(docType);
			}
		}
		return significantDocTypes;
	}
}
