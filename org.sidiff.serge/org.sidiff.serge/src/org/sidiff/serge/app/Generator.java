package org.sidiff.serge.app;

import java.io.File;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.sidiff.common.emf.modelstorage.ModelStorage;
import org.sidiff.common.services.ServiceHelper;
import org.sidiff.serge.EditRulesGeneratingService;
import org.sidiff.serge.impl.Activator;
import org.sidiff.serge.services.HenshinTransformationGenerator;

public class Generator implements IApplication {
    
	@Override
	public Object start(IApplicationContext context) throws Exception {
		
		/** File and Folder loading ********************************************************************/
		
    	String FILE_SEPERATOR = System.getProperty("file.separator");
		String[] argument = (String[]) context.getArguments().get(IApplicationContext.APPLICATION_ARGS);
		String INPUT_METAMODEL_PATH = argument[0];
		String OUTPUT_FOLDER_PATH	= argument[1] + FILE_SEPERATOR;
		String INPUT_CONFIG_PATH = argument[2];
		String INPUT_WORKSPACE_LOC = argument[3];
		
		
		String modelKey = ModelStorage.getInstance().loadEMF(new File(INPUT_METAMODEL_PATH).getPath());
		
		Resource metaModel = ModelStorage.getInstance().getModel(modelKey);	
		
		/*** Start generating rules*********************************************************************/
		
		EditRulesGeneratingService generatingService = ServiceHelper.getService(Activator.getContext(), EditRulesGeneratingService.class);
		generatingService.generate(HenshinTransformationGenerator.class, OUTPUT_FOLDER_PATH, metaModel, INPUT_CONFIG_PATH, INPUT_WORKSPACE_LOC);
		
				
		return null;
	}

	@Override
	public void stop() {


	}
	

}
