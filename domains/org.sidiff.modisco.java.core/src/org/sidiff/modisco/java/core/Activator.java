package org.sidiff.modisco.java.core;

import org.eclipse.gmt.modisco.java.emf.JavaPackage;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.sidiff.common.io.ResourceUtil;
import org.sidiff.common.services.ServiceHelper;
import org.sidiff.core.annotation.AnnotationService;
import org.sidiff.core.matching.IterativeMatchingService;
import org.sidiff.core.similaritiescalculator.DefaultSimilaritiesCalculationService;

public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		
		ResourceUtil.registerClassLoader(Activator.class.getClassLoader());
		
		ServiceHelper.configureInstance(context, DefaultSimilaritiesCalculationService.class, JavaPackage.eNS_URI, ServiceHelper.DEFAULT, "org.sidiff.modisco.java.core.compareconfig.xml");
		ServiceHelper.configureInstance(context, AnnotationService.class, JavaPackage.eNS_URI, ServiceHelper.DEFAULT, "org.sidiff.modisco.java.core.annotation.xml");
		ServiceHelper.configureInstance(context, IterativeMatchingService.class, JavaPackage.eNS_URI, ServiceHelper.DEFAULT, "org.sidiff.modisco.java.core.matchingconfig.xml");
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		
		ServiceHelper.unregisterVariantInstance(context, DefaultSimilaritiesCalculationService.class, JavaPackage.eNS_URI, "org.sidiff.modisco.java.core.compareconfig.xml");
		ServiceHelper.unregisterVariantInstance(context, AnnotationService.class, JavaPackage.eNS_URI, "org.sidiff.modisco.java.core.annotation.xml");
		ServiceHelper.unregisterVariantInstance(context, IterativeMatchingService.class, JavaPackage.eNS_URI, "org.sidiff.modisco.java.core.matchingconfig.xml");
		
		ResourceUtil.unregisterClassLoader(Activator.class.getClassLoader());
	}

}
