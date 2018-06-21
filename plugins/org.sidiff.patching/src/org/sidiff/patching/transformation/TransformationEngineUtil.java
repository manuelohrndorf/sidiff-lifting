package org.sidiff.patching.transformation;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

public class TransformationEngineUtil {

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
					ITransformationEngine correspondence = (ITransformationEngine) configurationElement.createExecutableExtension(ITransformationEngine.EXECUTABLE);
					correspondences.add(correspondence);
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		}
		return correspondences;
	}

	/**
	 * Returns the registered {@link ITransformationEngine} with the given key.
	 * @param key the key of the transformation engine
	 * @return transformation engine with the specified key, <code>null</code> if none was found
	 * @author Robert Müller
	 */
	public static ITransformationEngine getTransformationEngine(String key) {
		IConfigurationElement[] configurationElements =
				Platform.getExtensionRegistry().getConfigurationElementsFor(ITransformationEngine.EXTENSION_POINT_ID);
		for (IConfigurationElement configurationElement : configurationElements) {
			try {
				ITransformationEngine transformationEngine =
						(ITransformationEngine)configurationElement.createExecutableExtension(ITransformationEngine.EXECUTABLE);
				if(transformationEngine.getKey().equals(key)) {
					return transformationEngine;
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		return null;
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
