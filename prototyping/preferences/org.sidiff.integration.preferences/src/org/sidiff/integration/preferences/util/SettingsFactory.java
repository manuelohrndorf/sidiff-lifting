package org.sidiff.integration.preferences.util;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceStore;
import org.sidiff.integration.preferences.FeatureLevel;
import org.sidiff.integration.preferences.exceptions.UnsupportedFeatureLevelException;
import org.sidiff.integration.preferences.interfaces.ISiDiffSettingsFactory;

// TODO: rename this class
// TODO: test this class
/**
 * 
 * Main settings factory class. Determines the installed plugins and handles delegation of
 * settings creation to the appropriate factory classes.
 * @author Daniel Roedder, Robert Müller
 */
public class SettingsFactory {

	/**
	 * The registered matching settings factory
	 */
	private ISiDiffSettingsFactory matchingFactory;
	
	/**
	 * The registered difference settings factory
	 */
	private ISiDiffSettingsFactory differenceFactory;
	
	/**
	 * The registered lifting settings factory
	 */
	private ISiDiffSettingsFactory liftingFactory;
	
	/**
	 * The registered patching settings factory
	 */
	private ISiDiffSettingsFactory patchingFactory;
	
	/**
	 * The singleton instance
	 */
	private static SettingsFactory instance;
	
	/**
	 * Singleton method.
	 * @return The actual instance. If none is set, a new is generated.
	 */
	public static SettingsFactory getInstance() {
		if (SettingsFactory.instance == null) {
			SettingsFactory.instance = new SettingsFactory();
		}
		return SettingsFactory.instance;
	}
	
	/**
	 * The private singleton constructor. 
	 * Creates executable extensions for all registered extensions and searches the appropriate factories and saves 
	 * them to the corresponding fields.
	 */
	private SettingsFactory() {
		//Create executable class for each registered SettingsFactory
		IConfigurationElement[] extensionList =
				Platform.getExtensionRegistry().getConfigurationElementsFor(ISiDiffSettingsFactory.EXTENSION_POINT_ID);
		for (IConfigurationElement element : extensionList) {
			try {
				ISiDiffSettingsFactory factory = ((ISiDiffSettingsFactory)element.createExecutableExtension("class"));
				switch(factory.getFeatureLevel()) {
					case DIFFERENCE:
						this.differenceFactory = factory;
						break;
	
					case LIFTING:
						this.liftingFactory = factory;
						break;
	
					case MATCHING:
						this.matchingFactory = factory;
						break;
	
					case PATCHING:
						this.patchingFactory = factory;
						break;
				}
			}
			catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}
	
	// TODO: the preference store should probably not be passed to these functions as an argument, as all are stored in the main plugin's preference store anyway
	
	/**
	 * Checks if the required plugins are installed and returns a {@link org.sidiff.integration.preferences.matching.factory.MatchingSettingsFactory}.
	 * @param documentType Document Type {@link String} of the model to be processed.
	 * @param store The {@link IPreferenceStore} to load the preference values from.
	 * @return An instance of a {@link org.sidiff.integration.preferences.matching.factory.MatchingSettingsFactory}
	 * @throws UnsupportedFeatureLevelException Thrown if the required plugins are not installed.
	 */
	public ISiDiffSettingsFactory getMatchingSettingsFactory(String documentType, IPreferenceStore store) throws UnsupportedFeatureLevelException {
		return getMatchingSettingsFactory(documentType, store, null);
	}
	
	
	/**
	 * Checks if the required plugins are installed and returns a {@link org.sidiff.integration.preferences.difference.factory.DifferenceSettingsFactory}.
	 * Capable of using local settings.
	 * @param documentType Document Type {@link String} of the model to be processed.
	 * @param store The {@link IPreferenceStore} to load the preference values from.
	 * @param resource The {@link IResource} of the model to be processed
	 * @return An instance of a {@link org.sidiff.integration.preferences.difference.factory.DifferenceSettingsFactory}
	 * @throws UnsupportedFeatureLevelException Thrown if the required plugins are not installed.
	 */
	public ISiDiffSettingsFactory getMatchingSettingsFactory(String documentType, IPreferenceStore store, IResource resource) throws UnsupportedFeatureLevelException {
		if (matchingFactory != null) {
			if (resource != null) matchingFactory.doSetFields(documentType, new PropertyStore(resource, store, PreferenceUtil.getInstance().getPropertyQualifier()));
			else matchingFactory.doSetFields(documentType, store);
			return matchingFactory;
		}
		else {
			throw new UnsupportedFeatureLevelException(FeatureLevel.MATCHING);
		}
	}
	
	/**
	 * Checks if the required plugins are installed and returns a {@link org.sidiff.integration.preferences.difference.factory.DifferenceSettingsFactory}.
	 * @param documentType Document Type {@link String} of the model to be processed.
	 * @param store The {@link IPreferenceStore} to load the preference values from.
	 * @return An instance of a {@link org.sidiff.integration.preferences.difference.factory.DifferenceSettingsFactory}
	 * @throws UnsupportedFeatureLevelException Thrown if the required plugins are not installed.
	 */
	public ISiDiffSettingsFactory getDifferenceSettingsFactory(String documentType, IPreferenceStore store) throws UnsupportedFeatureLevelException {
		return getDifferenceSettingsFactory(documentType, store, null);
	}
	
	/**
	 * Checks if the required plugins are installed and returns a {@link org.sidiff.integration.preferences.difference.factory.DifferenceSettingsFactory}.
	 * Capable of using local settings.
	 * @param documentType Document Type {@link String} of the model to be processed.
	 * @param store The {@link IPreferenceStore} to load the preference values from.
	 * @param resource The {@link IResource} of the model to be processed
	 * @return An instance of a {@link org.sidiff.integration.preferences.difference.factory.DifferenceSettingsFactory}
	 * @throws UnsupportedFeatureLevelException Thrown if the required plugins are not installed.
	 */
	public ISiDiffSettingsFactory getDifferenceSettingsFactory(String documentType, IPreferenceStore store, IResource resource) throws UnsupportedFeatureLevelException {
		if (differenceFactory != null) {
			if (resource != null) differenceFactory.doSetFields(documentType, new PropertyStore(resource, store, PreferenceUtil.getInstance().getPropertyQualifier()));
			else differenceFactory.doSetFields(documentType, store);
			return differenceFactory;
		}
		else {
			throw new UnsupportedFeatureLevelException(FeatureLevel.DIFFERENCE);
		}
	}
	
	/**
	 * Checks if the required plugins are installed and returns a {@link org.sidiff.integration.preferences.lifting.factory.LiftingSettingsFactory}.
	 * @param documentType Document Type {@link String} of the model to be processed.
	 * @param store The {@link IPreferenceStore} to load the preference values from.
	 * @return An instance of a {@link org.sidiff.integration.preferences.lifting.factory.LiftingSettingsFactory}
	 * @throws UnsupportedFeatureLevelException Thrown if the required plugins are not installed.
	 */
	public ISiDiffSettingsFactory getLiftingSettingsFactory(String documentType, IPreferenceStore store) throws UnsupportedFeatureLevelException {
		return getLiftingSettingsFactory(documentType, store, null);
	}
	
	/**
	 * Checks if the required plugins are installed and returns a {@link org.sidiff.integration.preferences.lifting.factory.LiftingSettingsFactory}.
	 * Capable of using local settings.
	 * @param documentType Document Type {@link String} of the model to be processed.
	 * @param store The {@link IPreferenceStore} to load the preference values from.
	 * @param resource The {@link IResource} of the model to be processed
	 * @return An instance of a {@link org.sidiff.integration.preferences.lifting.factory.LiftingSettingsFactory}
	 * @throws UnsupportedFeatureLevelException Thrown if the required plugins are not installed.
	 */
	public ISiDiffSettingsFactory getLiftingSettingsFactory(String documentType, IPreferenceStore store, IResource resource) throws UnsupportedFeatureLevelException {
		if (liftingFactory != null) {
			if (resource != null) liftingFactory.doSetFields(documentType, new PropertyStore(resource, store, PreferenceUtil.getInstance().getPropertyQualifier()));
			else liftingFactory.doSetFields(documentType, store);
			return liftingFactory;
		}
		else {
			throw new UnsupportedFeatureLevelException(FeatureLevel.LIFTING);
		}
	}
	
	/**
	 * Checks if the required plugins are installed and returns a {@link org.sidiff.integration.preferences.patching.factory.PatchingSettingsFactory}.
	 * @param documentType Document Type {@link String} of the model to be processed.
	 * @param store The {@link IPreferenceStore} to load the preference values from.
	 * @return An instance of a {@link org.sidiff.integration.preferences.patching.factory.PatchingSettingsFactory}
	 * @throws UnsupportedFeatureLevelException Thrown if the required plugins are not installed.
	 */
	public ISiDiffSettingsFactory getPatchingSettingsFactory(String documentType, IPreferenceStore store) throws UnsupportedFeatureLevelException {
		return getPatchingSettingsFactory(documentType, store, null);
	}
	
	/**
	 * Checks if the required plugins are installed and returns a {@link org.sidiff.integration.preferences.patching.factory.PatchingSettingsFactory}.
	 * Capable of using local settings.
	 * @param documentType Document Type {@link String} of the model to be processed.
	 * @param store The {@link IPreferenceStore} to load the preference values from.
	 * @param resource The {@link IResource} of the model to be processed
	 * @return An instance of a {@link org.sidiff.integration.preferences.patching.factory.PatchingSettingsFactory}
	 * @throws UnsupportedFeatureLevelException Thrown if the required plugins are not installed.
	 */
	public ISiDiffSettingsFactory getPatchingSettingsFactory(String documentType, IPreferenceStore store, IResource resource) throws UnsupportedFeatureLevelException {
		if (patchingFactory != null) {
			if (resource != null) patchingFactory.doSetFields(documentType, new PropertyStore(resource, store, PreferenceUtil.getInstance().getPropertyQualifier()));
			else patchingFactory.doSetFields(documentType, store);
			return patchingFactory;
		}
		else {
			throw new UnsupportedFeatureLevelException(FeatureLevel.PATCHING);
		}
	}
}
