package org.sidiff.remote.common.settings;

import java.util.Objects;
import java.util.stream.Stream;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;

/**
 * A remote preference supplier supplies {@link RemotePreferences}
 * for the remote application.
 * @author Robert Müller
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
	RemotePreferences getRemotePreferences();


	/**
	 * Returns all registered {@link IRemotePreferencesSupplier}s.
	 * @return stream of all registered remote preference suppliers
	 */
	static Stream<IRemotePreferencesSupplier> getAllSuppliers() {
		return Stream.of(Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_POINT_ID))
			.map(element -> {
				try {
					return (IRemotePreferencesSupplier)element.createExecutableExtension(EXTENSION_POINT_ATTRIBUTE);
				} catch (CoreException e) {
					e.printStackTrace();
					return null;
				}
			})
			.filter(Objects::nonNull);
	}

	/**
	 * Returns {@link RemotePreferences} supplied by the
	 * supplier with the given key.
	 * @param key the key
	 * @return the remote preference, <code>null</code> if the supplier is not available
	 */
	static RemotePreferences getSupplier(String key) {
		return getAllSuppliers()
				.filter(supplier -> key.equals(supplier.getKey()))
				.findAny()
				.map(IRemotePreferencesSupplier::getRemotePreferences).orElse(null);
	}

	/**
	 * Returns default {@link RemotePreferences} supplied by the
	 * local SiDiff environment.
	 * @return the remote preferences, <code>null</code> if the local supplier is not available
	 */
	static RemotePreferences getLocalRemotePreferences() {
		return getSupplier("LocalRemotePreferencesSupplier");
	}

	/**
	 * Returns the {@link RemotePreferences} supplied by the
	 * user-specified preferences in the preference store.
	 * @return the remote preferences, <code>null</code> if the UI supplier is not available
	 */
	static RemotePreferences getUiRemotePreferences() {
		return getSupplier("UiRemotePreferencesSupplier");
	}
}
