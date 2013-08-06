package org.sidiff.patching.util;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.sidiff.patching.IPatchCorrespondence;

public class CorrespondenceUtil {

	/**
	 * Find all available patch correspondences matching the given document
	 * type.
	 * 
	 * @param documentType
	 * @return
	 */
	public static Set<IPatchCorrespondence> getAvailablePatchCorrespondence(String documentType) {
		Set<IPatchCorrespondence> correspondences = new HashSet<IPatchCorrespondence>();
		IConfigurationElement[] configurationElements = Platform.getExtensionRegistry().getConfigurationElementsFor(
				IPatchCorrespondence.EXTENSION_POINT_ID);
		for (IConfigurationElement configurationElement : configurationElements) {
			String attribute = configurationElement.getAttribute(IPatchCorrespondence.DOCUMENT_TYPE);
			if (attribute.equals(documentType) || attribute.equals(IPatchCorrespondence.DEFAULT_DOCUMENT_TYPE)) {
				try {
					IPatchCorrespondence correspondence = (IPatchCorrespondence) configurationElement.createExecutableExtension(IPatchCorrespondence.EXECUTEBALE);
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
	public static IPatchCorrespondence getFirstPatchCorrespondence(String documentType) {
		return getAvailablePatchCorrespondence(documentType).iterator().next();
	}
}
