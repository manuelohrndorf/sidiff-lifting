/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.difference.rulebase.wrapper;

import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.ResourceLocator;


/**
 * This is the central singleton for the RuleBase editor plugin.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public final class RuleBaseWrapperPlugin extends EMFPlugin {

	public static final String PLUGIN_ID = "org.sidiff.difference.rulebase.wrapper";
	
	/**
	 * Static plug-in instance.
	 */
	public static final RuleBaseWrapperPlugin INSTANCE = new RuleBaseWrapperPlugin();
	
	private static Activator plugin;

	public static Activator getPlugin() {
		return plugin;
	}
	
	public ResourceLocator getPluginResourceLocator() {
		return plugin;
	}
	
	public static class Activator extends EMFPlugin {
		public Activator() {
			super(new ResourceLocator[]{});
			plugin = this;
		}

		@Override
		public ResourceLocator getPluginResourceLocator() {
			// TODO Auto-generated method stub
			return plugin;
		}
	}
	
	/**
	 * Default constructor.
	 */
	public RuleBaseWrapperPlugin() {
		super(new ResourceLocator [] {
			});
	}
}
