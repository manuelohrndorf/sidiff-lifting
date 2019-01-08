package org.sidiff.common.henshin.internal;

import org.eclipse.emf.henshin.interpreter.impl.ChangeImpl;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		// This disables the warnings that henshin outputs on System.out
		ChangeImpl.PRINT_WARNINGS = false;
	}

	@Override
	public void stop(BundleContext context) throws Exception {		
	}
}
