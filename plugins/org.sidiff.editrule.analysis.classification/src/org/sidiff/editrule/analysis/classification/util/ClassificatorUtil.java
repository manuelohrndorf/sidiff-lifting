package org.sidiff.editrule.analysis.classification.util;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.sidiff.editrule.analysis.classification.IClassificator;
import org.sidiff.editrule.rulebase.EditRule;

/**
 * Utility class to find appropriate classifiers for a edit rules.
 * 
 * @author mwoermann
 */
public class ClassificatorUtil {

	/**
	 * Find all available classifiers matching the given document type.
	 * 
	 * @param rule
	 *            edit rule to be classified
	 *            
	 * @return All available classifiers.
	 */
	public static Set<IClassificator> getAvailableClassificators(EditRule rule) {
		Set<IClassificator> classificators = new HashSet<IClassificator>();

		for (IConfigurationElement configurationElement : Platform.getExtensionRegistry().getConfigurationElementsFor(
				IClassificator.extensionPointID)) {
			try {
				IClassificator classificatorExtension = (IClassificator)configurationElement.createExecutableExtension("classification");
				if (classificatorExtension.canHandle(rule)) {
					classificators.add(classificatorExtension);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return classificators;
	}

	/**
	 * Get classifier by its key name.
	 * 
	 * @param key
	 *            The key name of the classifier.
	 * @param rule
	 * 				The rule to classify
	 * 
	 * @return The classifier with the key name; null otherwise.
	 */
	public static IClassificator getClassificatorByKey(String key, EditRule rule) {
		for (IClassificator classificator : getAvailableClassificators(rule)) {
			if (classificator.getKey().equalsIgnoreCase(key)) {
				return classificator;
			}
		}
		return null;
	}
}
