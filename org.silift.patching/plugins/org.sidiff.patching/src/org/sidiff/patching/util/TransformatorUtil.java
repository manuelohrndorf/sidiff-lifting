package org.sidiff.patching.util;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.sidiff.patching.ITransformationEngine;

public class TransformatorUtil {

	/**
	 * Find all available patch correspondences matching the given document
	 * type.
	 * 
	 * @param documentType
	 * @return
	 */
	public static Set<ITransformationEngine> getAvailableTransformationEngines(String documentType) {
		Set<ITransformationEngine> correspondences = new HashSet<ITransformationEngine>();
		IConfigurationElement[] configurationElements = Platform.getExtensionRegistry().getConfigurationElementsFor(
				ITransformationEngine.EXTENSION_POINT_ID);
		for (IConfigurationElement configurationElement : configurationElements) {
			String attribute = configurationElement.getAttribute(ITransformationEngine.DOCUMENT_TYPE);
			if (attribute.equals(documentType) || attribute.equals(ITransformationEngine.DEFAULT_DOCUMENT_TYPE)) {
				try {
					ITransformationEngine correspondence = (ITransformationEngine) configurationElement.createExecutableExtension(ITransformationEngine.EXECUTEBALE);
					correspondences.add(correspondence);
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		}
		return correspondences;
	}

	/**
	 * Returns first matching PatchCorrespondence
	 * 
	 * @param documentType
	 * @return
	 */
	public static ITransformationEngine getFirstTransformationEngine(String documentType) {
		return getAvailableTransformationEngines(documentType).iterator().next();
	}
}
