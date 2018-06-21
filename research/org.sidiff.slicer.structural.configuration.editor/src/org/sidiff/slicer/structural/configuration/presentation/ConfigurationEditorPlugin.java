/**
 */
package org.sidiff.slicer.structural.configuration.presentation;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.ui.EclipseUIPlugin;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.provider.EcoreEditPlugin;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.osgi.framework.Bundle;

/**
 * This is the central singleton for the Configuration editor plugin.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public final class ConfigurationEditorPlugin extends EMFPlugin {
	/**
	 * The plugin bundle's ID.
	 */
	public static final String ID = "org.sidiff.slicer.structural.configuration.editor"; //$NON-NLS-1$
	
	public static final String IMAGE_REFRESH = "refresh.image"; //$NON-NLS-1$
	public static final String IMAGE_ALTERNATIVE_ELEMENTS = "alternative_elements.image"; //$NON-NLS-1$
	public static final String IMAGE_PROPERTIES = "properties.image"; //$NON-NLS-1$
	public static final String IMAGE_ADD = "add.image"; //$NON-NLS-1$
	public static final String IMAGE_CONSTRAINT = "constraint.image"; //$NON-NLS-1$
	public static final String IMAGE_MULTIPLICITY = "multiplicity.image"; //$NON-NLS-1$
	public static final String IMAGE_DANGLING = "dangling.image"; //$NON-NLS-1$
	
	/**
	 * Keep track of the singleton.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final ConfigurationEditorPlugin INSTANCE = new ConfigurationEditorPlugin();
	
	/**
	 * Keep track of the singleton.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static Implementation plugin;

	/**
	 * Create the instance.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConfigurationEditorPlugin() {
		super
			(new ResourceLocator [] {
				EcoreEditPlugin.INSTANCE,
			});
	}

	/**
	 * Returns the singleton instance of the Eclipse plugin.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the singleton instance.
	 * @generated
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
		
		@Override
		protected void initializeImageRegistry(ImageRegistry reg)
		{
			Bundle bundle = Platform.getBundle(ConfigurationEditorPlugin.ID);
			
			reg.put(IMAGE_REFRESH, ImageDescriptor.createFromURL(
					FileLocator.find(bundle, new Path("/icons/full/obj16/refresh_tab.gif"), null))); //$NON-NLS-1$
			
			reg.put(IMAGE_ALTERNATIVE_ELEMENTS, ImageDescriptor.createFromURL(
					FileLocator.find(bundle, new Path("/icons/full/obj16/brkp_grp.gif"), null))); //$NON-NLS-1$
			
			reg.put(IMAGE_PROPERTIES, ImageDescriptor.createFromURL(
					FileLocator.find(bundle, new Path("/icons/full/obj16/properties.gif"), null))); //$NON-NLS-1$
			
			reg.put(IMAGE_ADD, ImageDescriptor.createFromURL(
					FileLocator.find(bundle, new Path("/icons/full/obj16/add_obj.gif"), null))); //$NON-NLS-1$
			
			reg.put(IMAGE_CONSTRAINT, ImageDescriptor.createFromURL(
					FileLocator.find(bundle, new Path("/icons/full/obj8/constraint.gif"), null))); //$NON-NLS-1$
			
			reg.put(IMAGE_MULTIPLICITY, ImageDescriptor.createFromURL(
					FileLocator.find(bundle, new Path("/icons/full/obj8/multiplicity.gif"), null))); //$NON-NLS-1$
			
			reg.put(IMAGE_DANGLING, ImageDescriptor.createFromURL(
					FileLocator.find(bundle, new Path("/icons/full/obj8/dangling.gif"), null))); //$NON-NLS-1$
		}
	}
}
