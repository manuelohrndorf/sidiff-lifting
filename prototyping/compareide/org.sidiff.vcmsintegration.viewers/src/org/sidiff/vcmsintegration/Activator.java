package org.sidiff.vcmsintegration;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.sidiff.vcmsintegration.viewers"; //$NON-NLS-1$

	public static final String IMAGE_TOGGLE_ANCESTOR = "toggle_ancestor.image";
	public static final String IMAGE_MERGE_TO_RIGHT = "merge_to_right.image";
	public static final String IMAGE_MERGE_TO_LEFT = "merge_to_left.image";
	public static final String IMAGE_REFRESH = "refresh.image";
	public static final String IMAGE_SHOW_DIAGRAM = "show_diagram.image";
	public static final String IMAGE_SWAP_LEFT_RIGHT = "swap_left_right.image";
	public static final String IMAGE_SAVE = "save.image";

	// The shared instance
	private static Activator plugin;

	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.
	 * BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.
	 * BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	public static ImageDescriptor getImageDescriptor(String key) {
		return getDefault().getImageRegistry().getDescriptor(key);
	}

	@Override
	protected void initializeImageRegistry(ImageRegistry reg) {
		super.initializeImageRegistry(reg);

		Bundle bundle = getBundle();

		reg.put(IMAGE_TOGGLE_ANCESTOR, ImageDescriptor.createFromURL(
				FileLocator.find(bundle, new Path("icons/icon_toggle_ancestor.gif"), null))); //$NON-NLS-1$
		reg.put(IMAGE_MERGE_TO_RIGHT, ImageDescriptor.createFromURL(
				FileLocator.find(bundle, new Path("icons/icon_merge_to_right.gif"), null))); //$NON-NLS-1$
		reg.put(IMAGE_MERGE_TO_LEFT, ImageDescriptor.createFromURL(
				FileLocator.find(bundle, new Path("icons/icon_merge_to_left.gif"), null))); //$NON-NLS-1$
		reg.put(IMAGE_REFRESH, ImageDescriptor.createFromURL(
				FileLocator.find(bundle, new Path("icons/icon_refresh.gif"), null))); //$NON-NLS-1$
		reg.put(IMAGE_SHOW_DIAGRAM, ImageDescriptor.createFromURL(
				FileLocator.find(bundle, new Path("icons/icon_show_diagram.png"), null))); //$NON-NLS-1$
		reg.put(IMAGE_SWAP_LEFT_RIGHT, ImageDescriptor.createFromURL(
				FileLocator.find(bundle, new Path("icons/icon_swap_left_right.gif"), null))); //$NON-NLS-1$
		reg.put(IMAGE_SAVE, ImageDescriptor.createFromURL(
				FileLocator.find(bundle, new Path("icons/icon_save.gif"), null))); //$NON-NLS-1$
	}
}
