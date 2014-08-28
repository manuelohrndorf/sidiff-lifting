package org.sidiff.editrule.generator.util;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.sidiff.editrule.generator.IEditRuleGenerator;

/**
 * Utility class to find appropriate editrule generators.
 * 
 * @author dreuling
 */
public class EditRuleGeneratorUtil {

	/**
	 * Find all available edit rule generators
	 * 	
	 * @return All available edit rule generators
	 */
	public static Set<IEditRuleGenerator> getAvailableEditRuleGenerators() {
		Set<IEditRuleGenerator> generators = new HashSet<IEditRuleGenerator>();

		for (IConfigurationElement configurationElement : Platform.getExtensionRegistry().getConfigurationElementsFor(
				IEditRuleGenerator.extensionPointID)) {
			try {
				IEditRuleGenerator generatorExtension = (IEditRuleGenerator) configurationElement.createExecutableExtension("name");
				generators.add(generatorExtension);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return generators;
	}

	/**
	 * Get Generator by its key name.
	 * 
	 * @param key
	 *            The key name of the generator.
	 *            	
	 * @return The generator with the key name; null otherwise.
	 */
	public static IEditRuleGenerator getEditRuleGeneratorByKey(String key) {
		for (IEditRuleGenerator generator : getAvailableEditRuleGenerators()) {
			if (generator.getKey().equalsIgnoreCase(key)) {
				return generator;
			}
		}
		return null;
	}
}
