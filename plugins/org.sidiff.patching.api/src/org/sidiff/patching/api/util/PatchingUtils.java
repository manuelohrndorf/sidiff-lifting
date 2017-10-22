package org.sidiff.patching.api.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.sidiff.patching.arguments.IArgumentManager;

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
				IArgumentManager manager = (IArgumentManager) configurationElement.createExecutableExtension(IArgumentManager.EXTENSION_POINT_ATTRIBUTE);
				
				if (!availableManagers.contains(manager)) {
					availableManagers.add(manager);
				}
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
}
