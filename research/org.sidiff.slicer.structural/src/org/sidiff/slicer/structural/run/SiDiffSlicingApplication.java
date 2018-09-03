package org.sidiff.slicer.structural.run;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.file.FileOperations;
import org.sidiff.slicer.ISlicer;
import org.sidiff.slicer.ISlicingConfiguration;
import org.sidiff.slicer.slice.ModelSlice;
import org.sidiff.slicer.structural.StructureBasedSlicer;
import org.sidiff.slicer.structural.StructureBasedSlicerUtil;
import org.sidiff.slicer.structural.configuration.SlicingConfiguration;
import org.sidiff.slicer.util.SlicerUtil;
import org.sidiff.slicing.util.visualization.GraphUtil;

public class SiDiffSlicingApplication implements IApplication{
	
	@Override
	public Object start(IApplicationContext context) throws Exception {
		System.out.println("start");
		final long timeStart = System.currentTimeMillis();
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
			for(String context_id : context_ids){
				if(eObject.eClass().getEStructuralFeature("name")!=null){
					String name = (String) eObject.eGet(eObject.eClass().getEStructuralFeature("name"));
					if (context_id.equals(name)) {
						contexts.add(eObject);
					}
				}
				
				if(context_id.equals(eObject.eClass().getName())){
					contexts.add(eObject);
				}
			}
			
		}
		final long timeLoad = System.currentTimeMillis();
		
		System.out.println("Load model: " + (timeLoad - timeStart) + "ms");
	
		ISlicer slicer = new StructureBasedSlicer();
		slicer.init((ISlicingConfiguration)slicing_config);
		final long timeSlicerInit = System.currentTimeMillis();
		System.out.println("Init slicer: " + (timeSlicerInit - timeLoad) + "ms");
		ModelSlice modelSlice = slicer.slice(contexts);
		final long timerSlice = System.currentTimeMillis();
		System.out.println("Slicing: " + (timerSlice - timeSlicerInit) + "ms");
		URI saveURI = StructureBasedSlicerUtil.generateSaveURI(loadModelURI, (SlicingConfiguration)slicing_config);
		SlicerUtil.serializeModelSlice(saveURI, modelSlice.export(object -> model.eResource().equals(object.eResource())));
		String gv_path = loadModelURI.path().replace(loadModelURI.lastSegment(), "graph.dot");
		FileOperations.writeFile(gv_path, GraphUtil.get(slicer).getOutput());

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
