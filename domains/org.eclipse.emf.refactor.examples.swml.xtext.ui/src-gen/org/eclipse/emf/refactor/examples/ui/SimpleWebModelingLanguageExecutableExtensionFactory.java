/*
 * generated by Xtext 2.14.0-SNAPSHOT
 */
package org.eclipse.emf.refactor.examples.ui;

import com.google.inject.Injector;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.refactor.examples.swml.xtext.ui.internal.XtextActivator;
import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.osgi.framework.Bundle;

/**
 * This class was generated. Customizations should only happen in a newly
 * introduced subclass. 
 */
public class SimpleWebModelingLanguageExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {

	@Override
	protected Bundle getBundle() {
		return Platform.getBundle(XtextActivator.PLUGIN_ID);
	}
	
	@Override
	protected Injector getInjector() {
		XtextActivator activator = XtextActivator.getInstance();
		return activator != null ? activator.getInjector(XtextActivator.ORG_ECLIPSE_EMF_REFACTOR_EXAMPLES_SIMPLEWEBMODELINGLANGUAGE) : null;
	}

}
