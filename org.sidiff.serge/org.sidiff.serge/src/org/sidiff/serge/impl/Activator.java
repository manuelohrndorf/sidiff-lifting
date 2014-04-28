package org.sidiff.serge.impl;

import org.eclipse.core.runtime.internal.adaptor.EclipseAppLauncher;
import org.eclipse.equinox.internal.app.EclipseAppContainer;
import org.eclipse.equinox.internal.app.EclipseAppHandle;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.sidiff.common.io.IOUtil;
import org.sidiff.common.io.ResourceUtil;
import org.sidiff.common.services.ServiceHelper;
import org.sidiff.common.xml.XMLResolver;
import org.sidiff.serge.app.SergeApp;

public class Activator extends AbstractUIPlugin implements BundleActivator {


	// The plug-in ID
	public static final String PLUGIN_ID = "org.sidiff.serge.popup.actions"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	private static BundleContext context;
	
	public static BundleContext getContext() {
		return context;
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
						
		ResourceUtil.registerClassLoader(this.getClass().getClassLoader());
		XMLResolver.getInstance().includeMapping(IOUtil.getInputStream("Editrulesgeneratorconfig.dtdmap.xml"));
		
		//TODO differenciation between EclipseApplication or OSGI-Application/Service run
		// current workaround in SergeService argument input == null check.
		
		ServiceHelper.registerService(context, SergeApp.class, new SergeApp(), null, ServiceHelper.DEFAULT);
		super.start(context);
		plugin = this;

	}
	
	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}
	
	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}


	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		plugin = null;
		super.stop(context);
		Activator.context = null;
	}

}