package org.sidiff.editrule.rulebase.project.runtime.storage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.sidiff.common.emf.modelstorage.XMIIDResourceImpl;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.editrule.rulebase.RuleBase;

/**
 * Utility to load a rulebase.
 */
public class RuleBaseStorage {
	
	/**
	 * Rulebase file extension.
	 */
	public static final String EXTENSION_RULEBASE = "rulebase";
	
	/**
	 * Edit rule file extension.
	 */
	public static final String EXTENSION_EDITRULES = "henshin";
	
	private static ResourceSet getResourceSet() {
		
		ResourceSetImpl resourceSet = new ResourceSetImpl();

		// Do not report unknown rulebase attachments:
		resourceSet.getLoadOptions().put(XMIResource.OPTION_RECORD_UNKNOWN_FEATURE, true);

		// Performance improvements:
		resourceSet.setURIResourceMap(new HashMap<URI, Resource>());

		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> extensionToFactoryMap = reg.getExtensionToFactoryMap();

		extensionToFactoryMap.put(EXTENSION_EDITRULES, new XMIResourceFactoryImpl());
		extensionToFactoryMap.put(EXTENSION_RULEBASE, new XMIResourceFactoryImpl());
		
		return resourceSet;
	}
	
	/**
	 * Loads an EMF XMI persistent saved rulebase (without URI mapping).
	 * 
	 * @param uri
	 *            the URI of the rulbase XMI.
	 * @return the rulebase instance.
	 */
	public static RuleBase loadRuleBaseResource(URI rulebasePluginURI) {
		return internalLoadRuleBaseResource(rulebasePluginURI, getResourceSet());
	}

	/**
	 * Loads an EMF XMI persistent saved rulebase from a runtime plug-in (with URI mapping).
	 * 
	 * @param rulebasePath
	 *            the file path of the rulebase.
	 * @param rulebasePluginID
	 *            the plugin of the rulebase.
	 * @return the rulebase instance.
	 */
	public static RuleBase loadRuleBasePlugin(String rulebasePath, String rulebasePluginID) {
		
		// Set URI mapping:
		URI rulebasePluginIDURI = URI.createPlatformPluginURI(rulebasePluginID + "/", true);
		URI rulebaseResourceIDURI = URI.createPlatformResourceURI(rulebasePluginID + "/", true);
		
		ResourceSet resourceSet = getResourceSet();
		resourceSet.getURIConverter().getURIMap().put(rulebaseResourceIDURI, rulebasePluginIDURI);

		// Load Rulebase:
		URI rulebasePluginURI = URI.createPlatformPluginURI(rulebasePluginID + "/" + rulebasePath, true);
		return internalLoadRuleBaseResource(rulebasePluginURI, resourceSet);
	}
	
	/**
	 * Loads an EMF XMI persistent saved rulebase.
	 * 
	 * @param uri
	 *            the URI of the rulbase XMI.
	 * @param resourceSet
	 *            the resource set to use.
	 * @return the rulebase instance.
	 */
	private static RuleBase internalLoadRuleBaseResource(URI rulebasePluginURI, ResourceSet resourceSet) {
		
		// Load Rulebase:
		Resource rulebaseResource = resourceSet.getResource(rulebasePluginURI, true);

		if ((rulebaseResource == null) || (rulebaseResource.getContents().isEmpty())) {
			LogUtil.log(LogEvent.ERROR, "No Rule Base found for URI: " + rulebasePluginURI);
			throw new RuntimeException("No Rule Base found for URI: " + rulebasePluginURI);
		}

		RuleBase rulebase = (RuleBase) rulebaseResource.getContents().get(0);
		return rulebase;
	}
	
	/**
	 * Saves a rule-base which is already contained in a resource.
	 * 
	 * @param rulebase
	 *            the rulebase that will be saved.
	 */
	public static void saveRuleBase(RuleBase rulebase) {

		Resource resource = rulebase.eResource();
		Map<String, Object> options = new HashMap<String, Object>();
		options.put(XMIResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);

		try {
			resource.save(options);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Save the rule-base resource to given URI path.
	 * 
	 * @param path
	 *            the save path.
	 * @param root
	 *            the rulebase that will be saved.
	 */
	public static void saveRuleBaseAs(URI uri, RuleBase rulebase) {

		Resource resource = new XMIIDResourceImpl(uri);
		resource.getContents().add(rulebase);

		Map<String, Object> options = new HashMap<String, Object>();
		options.put(XMIResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);

		try {
			resource.save(options);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Helper method to load an Henshin module from given (workspace) resource.
	 * 
	 * @param resource
	 *            The Henshin module to load.
	 * @return The Henshin module (with platform resource URI) of the given resource.
	 */
	public static Module loadHenshinModule(IResource resource) {
		URI editRuleProjectURI = URI.createPlatformResourceURI(resource.getFullPath().toString(), true);
		
		HenshinResourceSet resourceSet = new HenshinResourceSet();
		Module editModule = resourceSet.getModule(editRuleProjectURI, false);
		return editModule;
	}
}
