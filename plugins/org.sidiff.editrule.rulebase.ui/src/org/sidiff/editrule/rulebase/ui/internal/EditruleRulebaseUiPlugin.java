package org.sidiff.editrule.rulebase.ui.internal;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class EditruleRulebaseUiPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.sidiff.editrule.rulebase.ui"; //$NON-NLS-1$

	// The shared instance
	private static EditruleRulebaseUiPlugin plugin;
	
	/**
	 * The constructor
	 */
	public EditruleRulebaseUiPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static EditruleRulebaseUiPlugin getDefault() {
		return plugin;
	}

	public static ImageDescriptor getImageDescriptor(String name) {
		return ImageDescriptor.createFromURL(
				FileLocator.find(Platform.getBundle(EditruleRulebaseUiPlugin.PLUGIN_ID),
						new Path(String.format("icons/%s", name)), null));
	}
}
