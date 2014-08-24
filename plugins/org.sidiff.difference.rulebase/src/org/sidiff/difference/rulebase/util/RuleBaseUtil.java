package org.sidiff.difference.rulebase.util;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.rulebase.EditRule;
import org.sidiff.difference.rulebase.RuleBase;
import org.sidiff.difference.rulebase.RuleBaseItem;
import org.sidiff.difference.rulebase.extension.IRuleBase;

/**
 * Utility to load a rulebase.
 */
public class RuleBaseUtil {

	public static ResourceSet resourceSet = new ResourceSetImpl();

	/**
	 * Loads an EMF XMI persistent saved rulebase.
	 * 
	 * @param pluginU
	 *            the URI of the rulbase XMI.
	 * @return the rulebase instance.
	 */
	public static RuleBase loadRuleBase(String rulebasePath,
			String editRulesPluginID, String recognitionRulesPluginID) {
		
		// Set URI mapping:
		URI er_pluginURI = URI.createPlatformPluginURI(editRulesPluginID + "/", true);
		URI er_resourceURI = URI.createPlatformResourceURI(editRulesPluginID + "/", true);
		resourceSet.getURIConverter().getURIMap().put(er_resourceURI, er_pluginURI);
		
		URI rr_pluginURI = URI.createPlatformPluginURI(recognitionRulesPluginID + "/", true);
		URI rr_resourceURI = URI.createPlatformResourceURI(recognitionRulesPluginID + "/", true);
		resourceSet.getURIConverter().getURIMap().put(rr_resourceURI, rr_pluginURI);

		// Load Rulebase:
		URI rulebasePluginURI = URI.createPlatformPluginURI(rulebasePath, true);
		Resource rulebaseResource = resourceSet.getResource(rulebasePluginURI, true);

		if (rulebaseResource == null) {
			LogUtil.log(LogEvent.WARNING, "No Rule Base found for URI " + rulebasePluginURI);
			return null;
		}

		assert (rulebaseResource.getContents().get(0) instanceof RuleBase) : ": No Rule Base..?";

		RuleBase rulebase = (RuleBase) rulebaseResource.getContents().get(0);
		
		return rulebase;
	}
	
	/**
	 * Clears the resource set.
	 */
	public static void unloadRuleBases() {
		resourceSet = new ResourceSetImpl();
	}
	
	/**
	 * Returns only the active rule base items of a rulebase.
	 * 
	 * @param rulebase
	 *            the rulebase instance.
	 * @return a list of rule base items.
	 */
	public static Set<RuleBaseItem> getActiveRuleBaseItems(RuleBase rulebase) {
		Set<RuleBaseItem> items = new HashSet<RuleBaseItem>();
		
		for (RuleBaseItem item : rulebase.getItems()) {
			if (item.isActive()) {
				items.add(item);
			}
		}
		return items;
	}

	/**
	 * Returns only the active recognition rules of a rulebase.
	 * 
	 * @param rulebase
	 *            the rulebase instance.
	 * @return a list of recognition rules.
	 */
	public static Set<Rule> getActiveRecognitonRules(RuleBase rulebase) {
		Set<Rule> units = new HashSet<Rule>();

		for (RuleBaseItem item : rulebase.getItems()) {
			if (item.isActive()) {
				units.add(item.getRecognitionRule().getRecognitionMainUnit());
			}
		}
		return units;
	}
	
	/**
	 * Returns only the active edit rules of a rulebase.
	 * 
	 * @param rulebase
	 *            the rulebase instance.
	 * @return a list of edit rules.
	 */
	public static Set<Unit> getActiveEditRules(RuleBase rulebase) {
		Set<Unit> units = new HashSet<Unit>();

		for (RuleBaseItem item : rulebase.getItems()) {
			if (item.isActive()) {
				units.add(item.getEditRule().getExecuteMainUnit());
			}
		}
		return units;
	}
	
	/**
	 * Returns the available rulebases for the given (characteristic) documentType
	 * 
	 * @param documentType
	 * @return
	 */
	public static Set<IRuleBase> getAvailableRulebases(String documentType){
		Set<IRuleBase> rulebases = new HashSet<IRuleBase>();
		for (IConfigurationElement configurationElement : Platform.getExtensionRegistry().getConfigurationElementsFor(
				IRuleBase.extensionPointID)) {
			try {
				IRuleBase rulebaseExtension = (IRuleBase) configurationElement.createExecutableExtension("rulebase");
				if (documentType.equals(rulebaseExtension.getCharacteristicDocumentType())) {
					rulebases.add(rulebaseExtension);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return rulebases;
	}
	
	/**
	 * Resolve an EditRule by its name. We lookup in all rulebases which are
	 * suitable for the given document type.
	 * 
	 * @param documentType
	 * @param editRuleName
	 * @return
	 */
	public static EditRule resolveEditRule(String documentType, String editRuleName) {
		for (IRuleBase iRulebase : RuleBaseUtil.getAvailableRulebases(documentType)) {
			EditRule editRule = iRulebase.getEditRule(editRuleName);
			if (editRule != null) {
				return editRule;
			}
		}

		return null;
	}
}
