package org.sidiff.editrule.rulebase.project.runtime.storage;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
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

	/**
	 * This resource set contains all loaded rulebases.
	 */
	private static ResourceSetImpl resourceSet;
	
	private static ResourceSetImpl getResourceSet() {
		
		if (resourceSet == null) {
			resourceSet = new ResourceSetImpl();
			
			// Do not report unknown rulebase attachments:
			resourceSet.getLoadOptions().put(XMIResource.OPTION_RECORD_UNKNOWN_FEATURE, true);
			
			// Performance improvements:
			resourceSet.setURIResourceMap(new HashMap<URI, Resource>());
			
			Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		    Map<String, Object> extensionToFactoryMap = reg.getExtensionToFactoryMap();
		    
		    extensionToFactoryMap.put(EXTENSION_EDITRULES, new XMIResourceFactoryImpl());
		    extensionToFactoryMap.put(EXTENSION_RULEBASE, new XMIResourceFactoryImpl());
		}
		
		return resourceSet;
	}
	
	/**
	 * Clears the resource set.
	 */
	public static void unloadRuleBases() {
		resourceSet = null;
	}
	
	/**
	 * Loads an EMF XMI persistent saved rulebase (without URI mapping).
	 * 
	 * @param uri
	 *            the URI of the rulbase XMI.
	 * @return the rulebase instance.
	 */
	public static RuleBase loadRuleBaseResource(URI rulebasePluginURI) {
		
		// Load Rulebase:
		Resource rulebaseResource = getResourceSet().getResource(rulebasePluginURI, true);

		if ((rulebaseResource == null) || (rulebaseResource.getContents().isEmpty())) {
			LogUtil.log(LogEvent.ERROR, "No Rule Base found for URI: " + rulebasePluginURI);
			throw new RuntimeException("No Rule Base found for URI: " + rulebasePluginURI);
		}

		RuleBase rulebase = (RuleBase) rulebaseResource.getContents().get(0);
		return rulebase;
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
		getResourceSet().getURIConverter().getURIMap().put(rulebaseResourceIDURI, rulebasePluginIDURI);

		// Load Rulebase:
		URI rulebasePluginURI = URI.createPlatformPluginURI(rulebasePluginID + "/" + rulebasePath, true);
		return loadRuleBaseResource(rulebasePluginURI);
	}
}
