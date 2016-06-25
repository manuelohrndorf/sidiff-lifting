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
		
				Collection<EObject> modelA = generator.generateOriginModel(false, false, false);
				StorageUtil.serializeModel(url.getPath() + File.separator + "testcases" + File.separator + module.getName() + File.separator + "modelA" + file_extension, modelA);
				if(generator.hasPACs(false)){
					Collection<EObject> modelA_PACs = generator.generateOriginModel(true, false, false);
					StorageUtil.serializeModel(url.getPath() + File.separator + "testcases" + File.separator + module.getName() + File.separator + "modelA_pacs" + file_extension, modelA_PACs);
				}
				if(generator.hasNACs(false)){
					Collection<EObject> modelA_NACs = generator.generateOriginModel(false, true, false);
					StorageUtil.serializeModel(url.getPath() + File.separator + "testcases" + File.separator + module.getName() + File.separator + "modelA_nacs" + file_extension, modelA_NACs);
				}
				if(generator.hasPACs(false) && generator.hasNACs(false)){
					Collection<EObject> modelA_PACs_NACs = generator.generateOriginModel(true, true, false);
					StorageUtil.serializeModel(url.getPath() + File.separator + "testcases" + File.separator + module.getName() + File.separator + "modelA_pacs_nacs" + file_extension, modelA_PACs_NACs);
				}
				
				if(generator.hasMultiRules()){
					Collection<EObject> modelA_Multi = generator.generateOriginModel(false, false, true);
					StorageUtil.serializeModel(url.getPath() + File.separator + "testcases" + File.separator + module.getName() + File.separator + "modelA_multi" + file_extension, modelA_Multi);
					if(generator.hasPACs(true)){
						Collection<EObject> modelA_Multi_PACs = generator.generateOriginModel(true, false, true);
						StorageUtil.serializeModel(url.getPath() + File.separator + "testcases" + File.separator + module.getName() + File.separator + "modelA_multi_pacs" + file_extension, modelA_Multi_PACs);
					}
					if(generator.hasNACs(true)){
						Collection<EObject> modelA_Multi_NACs = generator.generateOriginModel(false, true, true);
						StorageUtil.serializeModel(url.getPath() + File.separator + "testcases" + File.separator + module.getName() + File.separator + "modelA_multi_nacs" + file_extension, modelA_Multi_NACs);
					}
					if(generator.hasPACs(true)&&generator.hasNACs(true)){
						Collection<EObject> modelA_Multi_PACs_NACs = generator.generateOriginModel(true, true, true);
						StorageUtil.serializeModel(url.getPath() + File.separator + "testcases" + File.separator + module.getName() + File.separator + "modelA_multi_pacs_nacs" + file_extension, modelA_Multi_PACs_NACs);
					}
				}
				
				Collection<EObject> modelB = generator.generateModifiedModel(false, false, false);
				StorageUtil.serializeModel(url.getPath() + File.separator + "testcases" + File.separator + module.getName() + File.separator + "modelB" + file_extension, modelB);
				if(generator.hasMultiRules()){
					Collection<EObject> modelB_Multi = generator.generateModifiedModel(false, false, true);
					StorageUtil.serializeModel(url.getPath() + File.separator + "testcases" + File.separator + module.getName() + File.separator + "modelB_multi" + file_extension, modelB_Multi);
				}
		
				System.out.println("-------------------------------------");
			}
		}
		
		return null;
	}

	@Override
	public void stop() {
	}

}
