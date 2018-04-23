package org.sidiff.integration.preferences.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceStore;
import org.sidiff.integration.preferences.exceptions.UnsupportedFeatureLevelException;
import org.sidiff.integration.preferences.interfaces.ISiDiffSettingsFactory;

/**
 * 
 * Main settings factory class. Determines the installed plugins and handles delegation of
 * settings creation to the appropriate factory classes.
 * @author Daniel Roedder
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
		List<ISiDiffSettingsFactory> extensionClassList = new ArrayList<ISiDiffSettingsFactory>();

		//Create executable class for each registered SettingsFactory
		IConfigurationElement[] extensionList =
				Platform.getExtensionRegistry().getConfigurationElementsFor(ISiDiffSettingsFactory.EXTENSION_POINT_ID);
		for (IConfigurationElement element : extensionList) {
			try {
				extensionClassList.add((ISiDiffSettingsFactory)element.createExecutableExtension("class"));
			} 
			catch (CoreException e) {
				e.printStackTrace();
			}
		}
		
		// Search for all factories and save them
		for (ISiDiffSettingsFactory factory : extensionClassList) {
			
			//Find "Matching" Factory and save it
			if (factory.getFeatureLevel().equals("Matching")) {
				this.matchingFactory = factory;
			}
			
			//Find "Difference" Factory and save it
			if (factory.getFeatureLevel().equals("Difference")) {
				this.differenceFactory = factory;
			}
			
			//Find "Lifting" Factory and save it
			if (factory.getFeatureLevel().equals("Lifting")) {
				this.liftingFactory = factory;
			}
			
			//Find "Patching" Factory and save it
			if (factory.getFeatureLevel().equals("Patching")) {
				this.patchingFactory = factory;
			}
		}
	}
	

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
			throw new UnsupportedFeatureLevelException("Matching");
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
			throw new UnsupportedFeatureLevelException("Difference");
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
			throw new UnsupportedFeatureLevelException("Lifting");
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
			throw new UnsupportedFeatureLevelException("Patching");
		}
	}
}
