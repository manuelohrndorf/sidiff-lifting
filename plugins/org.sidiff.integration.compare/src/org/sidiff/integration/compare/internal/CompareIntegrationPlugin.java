package org.sidiff.integration.compare.internal;

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
public class CompareIntegrationPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String ID = "org.sidiff.integration.compare"; //$NON-NLS-1$

	public static final String IMAGE_MERGE_TO_RIGHT = "merge_to_right.image";
	public static final String IMAGE_MERGE_TO_LEFT = "merge_to_left.image";
	public static final String IMAGE_REFRESH = "refresh.image";
	public static final String IMAGE_SHOW_DIAGRAM = "show_diagram.image";
	public static final String IMAGE_APPLICABLE = "applicable.image";
	public static final String IMAGE_APPLIED = "applied.image";
	public static final String IMAGE_CONFLICT = "conflict.image";
	public static final String IMAGE_IGNORED = "ignored.image";
	public static final String IMAGE_WARNING = "warning.image";
	public static final String IMAGE_APPLY_PATCH = "apply_patch.image";
	public static final String IMAGE_PROPERTIES = "properties.image";
	public static final String IMAGE_APPLY = "apply.image";
	public static final String IMAGE_UNIGNORE = "unignore.image";
	public static final String IMAGE_REVERT = "revert.image";
	public static final String IMAGE_FILTER = "filter.image";

	// The shared instance
	private static CompareIntegrationPlugin plugin;

	/**
	 * The constructor
	 */
	public CompareIntegrationPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.
	 * BundleContext)
	 */
	@Override
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
	public static CompareIntegrationPlugin getDefault() {
		return plugin;
	}

	public static ImageDescriptor getImageDescriptor(String key) {
		return getDefault().getImageRegistry().getDescriptor(key);
	}

	@Override
	protected void initializeImageRegistry(ImageRegistry reg) {
		super.initializeImageRegistry(reg);

		Bundle bundle = getBundle();

		reg.put(IMAGE_MERGE_TO_RIGHT, ImageDescriptor.createFromURL(
				FileLocator.find(bundle, new Path("icons/icon_merge_to_right.gif"), null))); //$NON-NLS-1$
		reg.put(IMAGE_MERGE_TO_LEFT, ImageDescriptor.createFromURL(
				FileLocator.find(bundle, new Path("icons/icon_merge_to_left.gif"), null))); //$NON-NLS-1$
		reg.put(IMAGE_REFRESH, ImageDescriptor.createFromURL(
				FileLocator.find(bundle, new Path("icons/icon_refresh.gif"), null))); //$NON-NLS-1$
		reg.put(IMAGE_SHOW_DIAGRAM, ImageDescriptor.createFromURL(
				FileLocator.find(bundle, new Path("icons/icon_show_diagram.png"), null))); //$NON-NLS-1$
		reg.put(IMAGE_APPLICABLE, ImageDescriptor.createFromURL(
				FileLocator.find(bundle, new Path("icons/applicable.gif"), null))); //$NON-NLS-1$
		reg.put(IMAGE_APPLIED, ImageDescriptor.createFromURL(
				FileLocator.find(bundle, new Path("icons/applied.gif"), null))); //$NON-NLS-1$
		reg.put(IMAGE_CONFLICT, ImageDescriptor.createFromURL(
				FileLocator.find(bundle, new Path("icons/conflict.png"), null))); //$NON-NLS-1$
		reg.put(IMAGE_IGNORED, ImageDescriptor.createFromURL(
				FileLocator.find(bundle, new Path("icons/ignored.gif"), null))); //$NON-NLS-1$
		reg.put(IMAGE_WARNING, ImageDescriptor.createFromURL(
				FileLocator.find(bundle, new Path("icons/warning.gif"), null))); //$NON-NLS-1$
		reg.put(IMAGE_APPLY_PATCH, ImageDescriptor.createFromURL(
				FileLocator.find(bundle, new Path("icons/apply_patch.gif"), null))); //$NON-NLS-1$
		reg.put(IMAGE_PROPERTIES, ImageDescriptor.createFromURL(
				FileLocator.find(bundle, new Path("icons/properties.gif"), null))); //$NON-NLS-1$
		reg.put(IMAGE_APPLY, ImageDescriptor.createFromURL(
				FileLocator.find(bundle, new Path("icons/apply.gif"), null))); //$NON-NLS-1$
		reg.put(IMAGE_UNIGNORE, ImageDescriptor.createFromURL(
				FileLocator.find(bundle, new Path("icons/unignore.gif"), null))); //$NON-NLS-1$
		reg.put(IMAGE_FILTER, ImageDescriptor.createFromURL(
				FileLocator.find(bundle, new Path("icons/filter_applied.gif"), null))); //$NON-NLS-1$
	}
}
