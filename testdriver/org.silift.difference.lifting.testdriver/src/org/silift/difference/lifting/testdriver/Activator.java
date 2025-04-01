package org.silift.difference.lifting.testdriver;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;

public class Activator implements BundleActivator {
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		LiftingTestApp.start(context);
		StatisticLiftingTestApp.start(context);
		LogUtil.log(LogEvent.MESSAGE, "Local Tests Activated");
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		LiftingTestApp.stop(context);
		StatisticLiftingTestApp.stop(context);
		LogUtil.log(LogEvent.MESSAGE,"Local Tests Disposed!");
	}

}
