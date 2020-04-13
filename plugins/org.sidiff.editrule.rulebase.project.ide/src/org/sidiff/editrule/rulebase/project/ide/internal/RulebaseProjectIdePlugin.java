package org.sidiff.editrule.rulebase.project.ide.internal;

import java.net.URL;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class RulebaseProjectIdePlugin extends AbstractUIPlugin {

	// Shared instance
	private static RulebaseProjectIdePlugin fInstance;

	public URL getInstallURL() {
		return getDefault().getBundle().getEntry("/"); //$NON-NLS-1$
	}

	public static RulebaseProjectIdePlugin getDefault() {
		return fInstance;
	}

	public static String getPluginId() {
		return getDefault().getBundle().getSymbolicName();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		fInstance = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		fInstance = null;
		super.stop(context);
	}
}
