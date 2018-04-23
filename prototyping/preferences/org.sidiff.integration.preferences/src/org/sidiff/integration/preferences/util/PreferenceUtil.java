package org.sidiff.integration.preferences.util;

import org.eclipse.jface.preference.IPreferenceStore;
import org.sidiff.integration.preferences.Activator;

// TODO: this class is not needed
/**
 * 
 * Util class to get the general {@link org.eclipse.jface.preference.IPreferenceStore} used by the main settings plugin.
 * @author Daniel Roedder
 */
public class PreferenceUtil {
	
	/**
	 * Singleton instance field.
	 */
	private static PreferenceUtil util;
	
	/**
	 * Singleton method.
	 * @return The actual instance. If the instance is not set, a new is generated.
	 */
	public static PreferenceUtil getInstance() {
		
		if (util == null) 
		{
			util = new PreferenceUtil();
		}
		return util;
	}
	
	/**
	 * The constructor.
	 */
	protected PreferenceUtil() {
		
	}
	
	/**
	 * 
	 * @return The {@link IPreferenceStore} used to store the preferences.
	 */
	public IPreferenceStore getPluginPreferenceStore() {
		return Activator.getDefault().getPreferenceStore();
	}
	
	/**
	 * Returns the Qulifier for the property store to distinguish between global and local settings
	 * @return The Property Qualifier
	 */
	public String getPropertyQualifier() {
		return "org.sidiff.integration.preferences";
	}

}
