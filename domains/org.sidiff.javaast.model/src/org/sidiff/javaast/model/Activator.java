package org.sidiff.javaast.model;

import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.sidiff.common.emf.modelstorage.*;
import org.sidiff.common.io.ResourceUtil;
import org.sidiff.javaast.model.adapter.JavaModelAdapterFactory;

/**
 * Activator used for initialization purposes.
 * 
 * @author kehrer
 */
public class Activator implements BundleActivator {

	private static BundleContext context=null;
	private JavaModelAdapterFactory adapterFactory;
	
	private Loader javaModelLoader = new AbstractEMFImporter() {
		
		@Override
		public String getSuffix() {
			return "jam";
		}
		
		@Override
		public Map<String, String> getSchemaLocationMappings() {
			Map<String, String> map = new HashMap<String, String>();
			map.put("http://www.sidiff.org/org.sidiff.javaast.model","platform:/resource/org.sidiff.javaast.model/model/JavaModel.ecore");
			return map;
		}
		
		@Override
		public String getLoaderDescription() {
			return "Loader for JavaModels parsed with the JavaAST-Parser";
		}
	};
		
	@Override
	public void start(BundleContext context) throws Exception {
		assert(Activator.context==null) : "Singelton already Initialized??!";
		Activator.context = context;
		
		// register class loader
		ResourceUtil.registerClassLoader(this.getClass().getClassLoader());

		// adapter factory
		adapterFactory = new JavaModelAdapterFactory();
		ModelStorage.getInstance().registerAdapterFactory(adapterFactory);
		
		ModelStorage.getInstance().registerLoader(javaModelLoader);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		Activator.context = null;
		
		//unregister class loader
		ResourceUtil.unregisterClassLoader(this.getClass().getClassLoader());
		ModelStorage.getInstance().releaseLoader(javaModelLoader);
		
		// unregister adapter factory
		ModelStorage.getInstance().unregisterAdapterFactory(adapterFactory);
	}
	
	public static BundleContext getContext(){
		return Activator.context;
	}
}
