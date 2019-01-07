package org.sidiff.editrule.rulebase.project.runtime.storage;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.resource.HenshinResource;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.emf.modelstorage.SiDiffResourceSet;
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
	public static final String EXTENSION_EDITRULES = HenshinResource.FILE_EXTENSION;
	
	private static final SiDiffResourceSet RESOURCE_SET = initResourceSet();
	
	private static SiDiffResourceSet initResourceSet() {
		
		SiDiffResourceSet resourceSet = SiDiffResourceSet.create();
		resourceSet.registerXmiIdResourceExtensions(EXTENSION_EDITRULES, EXTENSION_RULEBASE);

		// Do not report unknown rulebase attachments:
		resourceSet.getLoadOptions().put(XMIResource.OPTION_RECORD_UNKNOWN_FEATURE, true);

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
		return RESOURCE_SET.loadEObject(rulebasePluginURI, RuleBase.class);
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
		
		RESOURCE_SET.getURIConverter().getURIMap().put(rulebaseResourceIDURI, rulebasePluginIDURI);

		// Load Rulebase:
		URI rulebasePluginURI = rulebasePluginIDURI.trimSegments(1).appendSegment(rulebasePath);
		return RESOURCE_SET.loadEObject(rulebasePluginURI, RuleBase.class);
	}

	/**
	 * Saves a rule-base which is already contained in a resource.
	 * 
	 * @param rulebase
	 *            the rulebase that will be saved.
	 */
	public static void saveRuleBase(RuleBase rulebase) {
		RESOURCE_SET.saveEObject(rulebase);
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
		RESOURCE_SET.saveEObjectAs(rulebase, uri);
	}
	
	/**
	 * Helper method to load an Henshin module from given (workspace) resource.
	 * 
	 * @param resource
	 *            The Henshin module to load.
	 * @return The Henshin module (with platform resource URI) of the given resource.
	 */
	public static Module loadHenshinModule(IResource resource) {
		HenshinResourceSet resourceSet = new HenshinResourceSet();
		Module editModule = resourceSet.getModule(EMFStorage.toPlatformURI(resource), false);
		return editModule;
	}
}
