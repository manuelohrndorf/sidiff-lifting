/**
 */
package org.sidiff.slicer.structural.configuration.wizard;

import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.ui.EclipseUIPlugin;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.provider.EcoreEditPlugin;
import org.eclipse.jface.resource.ImageDescriptor;

/**
 * This is the central singleton for the Configuration wizard plugin.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 */
public final class ConfigurationWizardPlugin extends EMFPlugin {
	/**
	 * The plugin bundle's ID.
	 */
	public static final String ID = "org.sidiff.slicer.structural.configuration.wizard"; //$NON-NLS-1$

	/**
	 * Keep track of the singleton.
	 */
	public static final ConfigurationWizardPlugin INSTANCE = new ConfigurationWizardPlugin();
	
	/**
	 * Keep track of the singleton.
	 */
	private static Implementation plugin;

	/**
	 * Create the instance.
	 */
	public ConfigurationWizardPlugin() {
		super
			(new ResourceLocator [] {
				EcoreEditPlugin.INSTANCE,
			});
	}

	/**
	 * Returns the singleton instance of the Eclipse plugin.
	 * @return the singleton instance.
	 */
	@Override
	public ResourceLocator getPluginResourceLocator() {
		return plugin;
	}
	
	/**
	 * Returns the singleton instance of the Eclipse plugin.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the singleton instance.
	 * @generated
	 */
	public static Implementation getPlugin() {
		return plugin;
	}

	/**
	 * Returns the descriptor associated with the given key in this registry, or <code>null</code> if none.
	 * Calling this method is equivalent to <code>getPlugin().getImageRegistry().getDescriptor(key)</code>.
	 * @param key the key
	 * @return the descriptor, or <code>null</code> if none
	 */
	public static ImageDescriptor getImageDescriptor(String key)
	{
		return plugin.getImageRegistry().getDescriptor(key);
	}

	/**
	 * Returns a string resource associated with the key, and performs substitutions.
	 * Calling this method is equivalent to <code>INSTANCE.getString(key, substitutions)</code>
	 * (using an object array instead of varargs).
	 * @param key the key of the string.
	 * @param substitutions the key of the string (varargs).
	 * @return a string resource associated with the key.
	 */
	public static String getSubstitutedString(String key, Object... substitutions)
	{
		return INSTANCE.getString(key, substitutions);
	}

	/**
	 * The actual implementation of the Eclipse <b>Plugin</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static class Implementation extends EclipseUIPlugin {
		/**
		 * Creates an instance.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public Implementation() {
			super();
	
			// Remember the static instance.
			//
			plugin = this;
		}
	}
}
