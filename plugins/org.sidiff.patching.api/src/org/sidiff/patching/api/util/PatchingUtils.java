package org.sidiff.patching.api.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.patching.ExecutionMode;
import org.sidiff.patching.arguments.IArgumentManager;
import org.sidiff.patching.arguments.IArgumentManagerSettings;

public class PatchingUtils {

	/**
	 * Returns the argument manager identified by the given key.
	 * 
	 * @param key
	 * @return
	 */
	public static IArgumentManager getArgumentManager(String key) {
		IArgumentManager manager = null;
		
		for(IArgumentManager m : getAllAvailableArgumentManagers()){
			if(m.getKey().equals(key)){
				manager = m;
				break;
			}
		}
		
		return manager;
	}

	/**
	 * Get all argument managers from the extension registry.
	 * 
	 * @return
	 */
	public static List<IArgumentManager> getAllAvailableArgumentManagers() {
		List<IArgumentManager> availableManagers = new ArrayList<IArgumentManager>();

		for (IConfigurationElement configurationElement : Platform.getExtensionRegistry().getConfigurationElementsFor(
				IArgumentManager.EXTENSION_POINT_ID)) {
			try {
				IArgumentManager manager = (IArgumentManager)configurationElement.createExecutableExtension(
						IArgumentManager.EXTENSION_POINT_ATTRIBUTE);
				availableManagers.add(manager);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		availableManagers.sort(new Comparator<IArgumentManager>() {
			@Override
			public int compare(IArgumentManager t1, IArgumentManager t2) {
				return t1.getName().compareTo(t2.getName());
			}
		});

		return availableManagers;
	}

	/**
	 * <p>Returns an argument manager that supports the given settings,
	 * and is able to resolve arguments for the given models.</p>
	 * <p>If an argument manager with the preferred mode is available, it will be returned.
	 * Else, any supported argument manager is returned.</p>
	 * @param asymmetricDifference the asymmetric difference
	 * @param targetModel the target model
	 * @param settings the argument manager settings
	 * @param preferredMode the preferred mode
	 * @return argument manager that supports the input parameters,
	 * <code>null</code> if no appropriate one was found
	 */
	public static IArgumentManager getArgumentManager(AsymmetricDifference asymmetricDifference, Resource targetModel,
			IArgumentManagerSettings settings, ExecutionMode preferredMode) {
		IArgumentManager fallbackManager = null;
		for(IArgumentManager manager : getAllAvailableArgumentManagers()) {
			if(manager.canResolveArguments(asymmetricDifference, targetModel, settings)) {
				if(preferredMode == manager.getExecutionMode()) {
					// if the manager has the preferred mode, it is returned right away
					return manager;
				} else if(fallbackManager == null) {
					// else the first viable manager is stored as a fallback
					fallbackManager = manager;
				}
			}
		}
		return fallbackManager;
	}
}
