package org.sidiff.slicing.interpreter.run;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.slicing.configuration.SlicingConfiguration;
import org.sidiff.slicing.interpreter.SiDiffSlicingInterpreter;
import org.sidiff.slicing.interpreter.util.SiDiffSlicingStorage;
import org.sidiff.slicing.slicingmodel.Slicing;

public class SiDiffSlicingApplication implements IApplication{
	
	@Override
	public Object start(IApplicationContext context) throws Exception {
		System.out.println("start");
		String[] argument = (String[]) context.getArguments().get(IApplicationContext.APPLICATION_ARGS);
		String input_config_path = argument[0];
		String input_model_path = argument[1];
		String input_context_ids = argument[2];
		
		String[] context_ids = input_context_ids.replaceAll(" ", "").split(";");
		
		
		URI loadSlicingConfigURI = EMFStorage.pathToUri(input_config_path);
		
		URI loadModelURI = EMFStorage.pathToUri(input_model_path);


		
		EObject model = EMFStorage.eLoad(loadModelURI);

		EObject slicing_config = EMFStorage.eLoad(loadSlicingConfigURI);
		
		Set<EObject> contexts = new HashSet<EObject>();
		for (Iterator<EObject> iterator = model.eResource().getAllContents(); iterator.hasNext();) {
			EObject eObject = (EObject) iterator.next();
			if(eObject.eClass().getEStructuralFeature("name")!=null){
				String name = (String) eObject.eGet(eObject.eClass().getEStructuralFeature("name"));
				for(String context_id: context_ids){
					if(context_id.contentEquals(name)){
						contexts.add(eObject);
					}
				}
			}
		}
		
	
		SiDiffSlicingInterpreter siDiffSlicingInterpreter = new SiDiffSlicingInterpreter();
		siDiffSlicingInterpreter.init((SlicingConfiguration)slicing_config);
		siDiffSlicingInterpreter.slice(contexts);
		Slicing slicedModel = siDiffSlicingInterpreter.getSlicedModel();
		System.out.println(slicedModel.getSlicedContextElements() + ", " + slicedModel.getSlicedBoundaryElements());
		SiDiffSlicingStorage.serializeSlicedModel(slicedModel, SiDiffSlicingStorage.generateSaveURI(loadModelURI, (SlicingConfiguration) slicing_config), false);	
		//String gv_path = MODEL_PATH.replace(loadModelURI.lastSegment(), "graph.dot");
		//FileOperations.writeFile(gv_path, GraphUtil.getOutput());

		// String command = "cmd /c start C:\\\"Program Files (x86)\"\\Graphviz2.38\\bin\\gvedit.exe " + gv_path;
		// Runtime runtime = Runtime.getRuntime();
		// Process process = runtime.exec(command);
		//process.waitFor();
		return null;
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
