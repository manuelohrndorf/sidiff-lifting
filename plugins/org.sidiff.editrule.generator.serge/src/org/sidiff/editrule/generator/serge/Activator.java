package org.sidiff.editrule.generator.serge;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.sidiff.common.xml.IResourceLoader;
import org.sidiff.common.xml.XMLResolver;

public class Activator implements BundleActivator, IResourceLoader {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		XMLResolver.getInstance().registerLoader(this);
		//XMLResolver.getInstance().includeMapping(loadResourceAsStream("org.sidiff.editrule.generator.serge.config.dtd"));
	}
	
    /**
     * Loads a resource from the current bundle using its relative path.
     *
     * @param path The path to the resource inside the bundle (e.g., "resources/config.properties")
     * @return InputStream of the resource, or null if not found.
     * @throws IOException If an I/O error occurs.
     */
	@Override
    public InputStream loadResourceAsStream(String path) throws IOException {
        if (context == null) {
            throw new IllegalStateException("BundleContext is not initialized yet.");
        }

        Bundle bundle = context.getBundle();
        URL entry = bundle.getEntry("resources/" + path);

        if (entry != null) {
            return entry.openStream();
        }

        return null;
    }

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
