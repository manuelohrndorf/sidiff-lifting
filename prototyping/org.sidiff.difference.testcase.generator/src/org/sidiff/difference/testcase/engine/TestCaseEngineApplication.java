package org.sidiff.difference.testcase.engine;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.sidiff.difference.testcase.generator.TestCaseGenerator;
import org.sidiff.difference.testcase.util.StorageUtil;

/**
 * 
 * @author cpietsch
 *
 */
public class TestCaseEngineApplication implements IApplication{

	@Override
	public Object start(IApplicationContext context) throws Exception {
		
		System.out.println("--------------- START ---------------");
		
		URL url = getClass().getProtectionDomain().getCodeSource().getLocation();
		
		String[] args = (String[]) context.getArguments().get(IApplicationContext.APPLICATION_ARGS);
		
		String path = args[0];
		
		List<String> files = StorageUtil.getFiles(path);
		
		String file_extension = ".xmi";
	
		if(args[1] != null){
			if(args[1].trim().length() > 1){
				file_extension = "." + args[1];
			}
		}
		
		TestCaseGenerator generator = new TestCaseGenerator();
		
		for(String filePath: files){
		
			if(filePath.endsWith(".henshin")){
				Module module = StorageUtil.loadHenshinModule(filePath);
				System.out.println("-------------------------------------");
				System.out.println("Module: " + module.getName());
				generator.init(module);
		
				Collection<EObject> modelA = generator.generateOriginModel(false, true);
				Collection<EObject> modelA_ACs = generator.generateOriginModel(true, true);
				Collection<EObject> modelB = generator.generateModifiedModel(false, true);	
		
				StorageUtil.serializeModel(url.getPath() + File.separator + "testcases" + File.separator + module.getName() + File.separator + "modelA" + file_extension, modelA);
				StorageUtil.serializeModel(url.getPath() + File.separator + "testcases" + File.separator + module.getName() + File.separator + "modelA_ACs" + file_extension, modelA_ACs);
				StorageUtil.serializeModel(url.getPath() + File.separator + "testcases" + File.separator + module.getName() + File.separator + "modelB" + file_extension, modelB);
		
				System.out.println("-------------------------------------");
			}
		}
		
		return null;
	}

	@Override
	public void stop() {
	}

}
