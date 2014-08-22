package org.sidiff.serge.batch;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.sidiff.common.services.ServiceHelper;

public class Activator implements BundleActivator {

	
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
		
		ServiceHelper.registerService(context, Runner.class, new Runner(), null, ServiceHelper.DEFAULT);

	}
	


	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}