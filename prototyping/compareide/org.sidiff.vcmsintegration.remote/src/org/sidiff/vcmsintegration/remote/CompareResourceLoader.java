package org.sidiff.vcmsintegration.remote;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.compare.ITypedElement;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
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
	private static final String ELEMENT_RELATED_FILE_RESOLVER = "relatedFileResolver";

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
	private List<IRelatedFileResolver> relatedFileResolvers;

	private CompareResourceLoader() {
		initializeRemoteLoaders();
	}

	private void initializeRemoteLoaders() {
		platformResourceLoaders = new ArrayList<>();
		ecoreResourceLoaders = new ArrayList<>();
		editabilityResolvers = new ArrayList<>();
		uriResolvers = new ArrayList<>();
		relatedFileResolvers = new ArrayList<>();

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

					case ELEMENT_RELATED_FILE_RESOLVER:
						relatedFileResolvers.add((IRelatedFileResolver)executableExtension);
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

		loadPlatformResource(compRes);
		URI uri = resolveURI(compRes);
		loadEcoreResource(compRes, uri);
		resolveEditability(compRes);
		loadRelatedFileResolver(compRes);
	}

	protected void loadPlatformResource(CompareResource compRes)
			throws IOException, CoreException {
		final ITypedElement typedElement = compRes.getTypedElement();
		IResource platformResource = null;
		for(IPlatformResourceLoader l : platformResourceLoaders) {
			if(l.canHandle(typedElement)) {
				platformResource = l.loadPlatformResource(typedElement);
				if(platformResource != null) break;
			}
		}
		if(platformResource == null) {
			platformResource = Adapters.adapt(typedElement, IFile.class);
		}
		if(platformResource == null) {
			platformResource = Adapters.adapt(typedElement, IResource.class);
		}
		compRes.setPlatformResource(platformResource);
	}

	protected URI resolveURI(CompareResource compRes) {
		final ITypedElement typedElement = compRes.getTypedElement();
		URI uri = null;
		if(compRes.getPlatformResource() != null) {
			// use the platform resource to resolve the uri
			uri = EMFStorage.iResourceToURI(compRes.getPlatformResource());
		} else {
			// resolve using the registered extensions
			for(IURIResolver r : uriResolvers) {
				if(r.canHandle(typedElement)) {
					uri = r.getURI(typedElement);
					if(uri != null) break;
				}
			}
			// fall back to the typed element name, this is not a fully qualified URI
			if(uri == null) {
				uri = URI.createURI(typedElement.getName());
			}
		}
		return uri;
	}

	protected void loadEcoreResource(CompareResource compRes, URI uri)
			throws IOException, CoreException {
		final ITypedElement typedElement = compRes.getTypedElement();
		Resource resource = null;
		for(IEcoreResourceLoader l : ecoreResourceLoaders) {
			if(l.canHandle(typedElement)) {
				resource = l.loadEcoreResource(typedElement, uri);
				if(resource != null) break;
			}
		}
		compRes.setResource(resource);
	}

	protected void resolveEditability(CompareResource compRes) {
		final ITypedElement typedElement = compRes.getTypedElement();
		for(IEditabilityResolver r : editabilityResolvers) {
			if(r.canHandle(typedElement)) {
				compRes.setEditable(r.isEditable(typedElement));
				break;
			}
		}
	}

	protected void loadRelatedFileResolver(CompareResource compRes) {
		final ITypedElement typedElement = compRes.getTypedElement();
		for(IRelatedFileResolver r : relatedFileResolvers) {
			if(r.canHandle(typedElement)) {
				compRes.setRelatedFileResolver(r);
				break;
			}
		}
	}
}
