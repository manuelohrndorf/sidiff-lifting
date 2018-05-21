package org.sidiff.remote.application.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.sidiff.remote.application.adapters.IRepositoryAdapter;

/**
 * 
 * @author cpietsch
 *
 */
public class ExtensionUtil {

	/**
	 * Get all repository adapters from the extension registry.
	 * 
	 * @return
	 */
	public static List<IRepositoryAdapter> getAllAvailableRepositoryAdapters() {
		List<IRepositoryAdapter> availableRepositoryAdapters = new ArrayList<IRepositoryAdapter>();
		
		for (IConfigurationElement configurationElement : Platform.getExtensionRegistry().getConfigurationElementsFor(
				IRepositoryAdapter.EXTENSION_POINT_ID)) {
			try {
				IRepositoryAdapter repositoryAdapter = (IRepositoryAdapter) configurationElement.createExecutableExtension(IRepositoryAdapter.ATTRIBUTE_ID);
				
				if (!availableRepositoryAdapters.contains(repositoryAdapter)) {
					availableRepositoryAdapters.add(repositoryAdapter);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		availableRepositoryAdapters.sort(new Comparator<IRepositoryAdapter>() {

			@Override
			public int compare(IRepositoryAdapter t1, IRepositoryAdapter t2) {
				return t1.getName().compareTo(t2.getName());
			}
		});
		
		return availableRepositoryAdapters;
	}
	
	public static IRepositoryAdapter getRepositoryAdapter(String key) {
		for(IRepositoryAdapter repositoryAdapter : getAllAvailableRepositoryAdapters()){
			if(key.equals(repositoryAdapter.getKey())) {
				return repositoryAdapter;
			}
		}
		return null;
	}
}
