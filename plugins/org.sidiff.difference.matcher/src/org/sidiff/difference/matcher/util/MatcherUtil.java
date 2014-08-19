package org.sidiff.difference.matcher.util;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.difference.matcher.IMatcher;

/**
 * Utility class to find appropriate matchers for a comparison.
 * 
 * @author kehrer, mohrndorf
 */
public class MatcherUtil {

	// TODO: Matcher priorisieren und eine Liste statt Set zurueck liefern?
	/**
	 * Find all available matchers matching the given document type.
	 * 
	 * @param modelA
	 *            Model A of the comparison
	 * @param modelB
	 *            Model B of the comparison
	 *            
	 * @return All available rulebases matching the given document type.
	 */
	public static Set<IMatcher> getAvailableMatchers(Resource modelA, Resource modelB) {
		Set<IMatcher> matchers = new HashSet<IMatcher>();

		for (IConfigurationElement configurationElement : Platform.getExtensionRegistry().getConfigurationElementsFor(
				IMatcher.extensionPointID)) {
			try {
				IMatcher matcherExtension = (IMatcher) configurationElement.createExecutableExtension("name");
				if (matcherExtension.canHandle(modelA, modelB)) {
					matchers.add(matcherExtension);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return matchers;
	}

	/**
	 * Get matcher by its key name.
	 * 
	 * @param key
	 *            The key name of the matcher.
	 * @param modelA
	 *            Model A of the comparison
	 * @param modelB
	 *            Model B of the comparison
	 * 
	 * @return The matcher with the key name; null otherwise.
	 */
	public static IMatcher getMatcherByKey(String key, Resource modelA, Resource modelB) {
		for (IMatcher matcher : getAvailableMatchers(modelA, modelB)) {
			if (matcher.getKey().equalsIgnoreCase(key)) {
				return matcher;
			}
		}
		return null;
	}
}
