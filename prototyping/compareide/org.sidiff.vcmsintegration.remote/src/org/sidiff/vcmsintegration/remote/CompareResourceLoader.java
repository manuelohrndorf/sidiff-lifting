package org.sidiff.vcmsintegration.remote;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.compare.ITypedElement;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;

/**
 * 
 * @author Robert Müller
 *
 */
class CompareResourceLoader {

	private static final String EXTENSION_POINT_ID = "org.sidiff.vcmsintegration.remote.loaders";
	private static final String ATTRIBUTE_CLASS = "class";
	private static final String ELEMENT_PLATFORM_RESOURCE_LOADER = "platformResourceLoader";
	private static final String ELEMENT_ECORE_RESOURCE_LOADER = "ecoreResourceLoader";
	private static final String ELEMENT_EDITABILITY_RESOLVER = "editabilityResolver";
	private static final String ELEMENT_URI_RESOLVER = "uriResolver";

	private static CompareResourceLoader instance;

	public static CompareResourceLoader getInstance() {
		if(instance == null) {
			instance = new CompareResourceLoader();
		}
		return instance;
	}


	private List<IPlatformResourceLoader> platformResourceLoaders;
	private List<IEcoreResourceLoader> ecoreResourceLoaders;
	private List<IEditabilityResolver> editabilityResolvers;
	private List<IURIResolver> uriResolvers;

	private CompareResourceLoader() {
		initializeRemoteLoaders();
	}

	private void initializeRemoteLoaders() {
		platformResourceLoaders = new ArrayList<>();
		ecoreResourceLoaders = new ArrayList<>();
		editabilityResolvers = new ArrayList<>();
		uriResolvers = new ArrayList<>();

		for(IConfigurationElement element :
			Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_POINT_ID)) {
			try {
				Object executableExtension = element.createExecutableExtension(ATTRIBUTE_CLASS);
				switch(element.getName()) {
					case ELEMENT_PLATFORM_RESOURCE_LOADER:
						platformResourceLoaders.add((IPlatformResourceLoader)executableExtension);
						break;

					case ELEMENT_ECORE_RESOURCE_LOADER:
						ecoreResourceLoaders.add((IEcoreResourceLoader)executableExtension);
						break;

					case ELEMENT_EDITABILITY_RESOLVER:
						editabilityResolvers.add((IEditabilityResolver)executableExtension);
						break;

					case ELEMENT_URI_RESOLVER:
						uriResolvers.add((IURIResolver)executableExtension);
						break;
				}
			} catch(CoreException e) {
				LogUtil.log(LogEvent.ERROR, "Creating executable extension for element " + element.getName()
					+ " contributed by " + element.getContributor().getName() + " failed", e);
			}
		}
	}

	public void load(CompareResource compRes) throws IOException, CoreException {
		Assert.isNotNull(compRes);

		final ITypedElement typedElement = compRes.getTypedElement();
		if(typedElement == null || typedElement.getType().equals(ITypedElement.UNKNOWN_TYPE)
				|| typedElement.getType().equals(ITypedElement.FOLDER_TYPE)) {
			// this handles the various cases that the compare resource is empty / does not exist
			// this might e.g. be the case when a file does not exist in a particular revision etc.
			return;
		}

		// load the platform resource
		for(IPlatformResourceLoader l : platformResourceLoaders) {
			if(l.canHandle(typedElement)) {
				compRes.setPlatformResource(l.loadPlatformResource(typedElement));
				break;
			}
		}

		// resolve uri for the ecore resource
		URI uri = null;
		if(compRes.getPlatformResource() != null) {
			// use the platform resource to resolve the uri
			uri = EMFStorage.iResourceToURI(compRes.getPlatformResource());
		} else {
			// resolve using the registered extensions
			for(IURIResolver r : uriResolvers) {
				if(r.canHandle(typedElement)) {
					uri = r.getURI(typedElement);
					break;
				}
			}
			// fall back to the typed element name, this is not a fully qualified URI
			if(uri == null) {
				uri = URI.createURI(typedElement.getName());
			}
		}

		// load the ecore resource
		for(IEcoreResourceLoader l : ecoreResourceLoaders) {
			if(l.canHandle(typedElement)) {
				compRes.setResource(l.loadEcoreResource(typedElement, uri));
				break;
			}
		}

		// resolve editability of the compare resource
		for(IEditabilityResolver r : editabilityResolvers) {
			if(r.canHandle(typedElement)) {
				compRes.setEditable(r.isEditable(typedElement));
				break;
			}
		}
	}
}
