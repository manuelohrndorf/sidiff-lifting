package org.sidiff.remote.common.settings;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

/**
 * A remote preference supplier supplies {@link RemotePreferences}
 * for sending to the remote application.
 * @author Robert Müller
 *
 */
public interface IRemotePreferencesSupplier {

	String EXTENSION_POINT_ID = "org.sidiff.remote.common.remotePreferencesSuppliers";
	String EXTENSION_POINT_ATTRIBUTE = "class";

	/**
	 * Returns a human-readable name for this remote preference supplier.
	 * @return human-readable name
	 */
	String getName();

	/**
	 * Returns a unique key for this remote preference supplier.
	 * @return unique key
	 */
	String getKey();

	/**
	 * Returns the {@link RemotePreferences}.
	 * @return the remote preferences
	 */
	RemotePreferences getRemotePreference();

	/**
	 * Returns all registered {@link IRemotePreferencesSupplier}s
	 * as a map of <code>key -> supplier</code>.
	 * @return map of all registered remote preference suppliers
	 */
	static Map<String, IRemotePreferencesSupplier> getAllSuppliers() {
		Map<String, IRemotePreferencesSupplier> suppliers = new HashMap<>();
		for(IConfigurationElement element :
			Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_POINT_ID)) {
			try {
				IRemotePreferencesSupplier supplier =
						(IRemotePreferencesSupplier)element.createExecutableExtension(EXTENSION_POINT_ATTRIBUTE);
				suppliers.put(supplier.getKey(), supplier);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		return suppliers;
	}

	/**
	 * Returns the {@link RemotePreferences} supplied by the default supplier,
	 * i.e. the user-specified preferences from the preference store.
	 * 
	 * @return the remote preferences, <code>null</code> if the default supplier is not available
	 */
	static RemotePreferences getDefaultRemotePreferences() {
		IRemotePreferencesSupplier supplier = getAllSuppliers().get("UiRemotePreferenceSupplier");
		return supplier == null ? null : supplier.getRemotePreference();
	}
}
