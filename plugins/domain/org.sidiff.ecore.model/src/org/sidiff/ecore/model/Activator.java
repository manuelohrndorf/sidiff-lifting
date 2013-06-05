package org.sidiff.ecore.model;

import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.sidiff.common.emf.modelstorage.AbstractEMFImporter;
import org.sidiff.common.emf.modelstorage.Loader;
import org.sidiff.common.emf.modelstorage.ModelStorage;
import org.sidiff.common.io.ResourceUtil;

public class Activator implements BundleActivator {

	private Loader ecoreModelLoader = new AbstractEMFImporter() {
		
		@Override
		public String getSuffix() {
			return "ecore";
		}
		
		@Override
		public Map<String, String> getSchemaLocationMappings() {
			Map<String, String> map = new HashMap<String, String>();
			return map;
		}

		@Override
		public String getLoaderDescription() {
			return "Loader for Ecore models";
		}
	};

	@Override
	public void start(BundleContext context) throws Exception {
		ResourceUtil.registerClassLoader(Activator.class.getClassLoader());
		ModelStorage.getInstance().registerLoader(ecoreModelLoader);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		ResourceUtil.unregisterClassLoader(Activator.class.getClassLoader());
		ModelStorage.getInstance().releaseLoader(ecoreModelLoader);
	}

}
