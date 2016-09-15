package org.sidiff.slicing.interpreter.run;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.uml2.uml.Package;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.file.FileOperations;
import org.sidiff.slicing.configuration.SlicingConfiguration;
import org.sidiff.slicing.configuration.SlicingMode;
import org.sidiff.slicing.interpreter.SiDiffSlicingInterpreter;
import org.sidiff.slicing.interpreter.util.SiDiffSlicingStorage;
import org.sidiff.slicing.slicingmodel.Slicing;
import org.sidiff.slicing.util.visualization.GraphUtil;

import simpleWebModel.WebModel;

public class SiDiffSlicingApplication implements IApplication{

//	private static final String MODEL_PATH = "C:\\Users\\piets\\Git\\sidiff-lifting\\examples\\org.sidiff.coevolution.example.swml.multiviews.webmodel.sample\\version0\\PoetryContest.swml";
	private static final String MODEL_PATH = "C:\\Users\\piets\\Git\\sidiff-lifting\\examples\\org.sidiff.coevolution.example.uml.multiviews.example.bcms\\core_model_v2\\bCMS.uml";
//	private static final String MODEL_PATH = "C:\\Users\\piets\\sidiff-lifting\\examples\\org.sidiff.coevolution.example.uml.multiviews.example.bcms\\reduced\\model.uml";

//	private static final String CONFIG_HYPERTEXTLAYER_PATH = "C:\\Users\\piets\\Git\\sidiff-lifting\\examples\\org.sidiff.slicing.configuration.webmodel\\configs\\HypertextLayer.scfg";
//	private static final String CONFIG_DATALAYER_PATH = "C:\\Users\\piets\\Git\\sidiff-lifting\\examples\\org.sidiff.slicing.configuration.webmodel\\configs\\DataLayer.scfg";
	private static final String CONFIG_CLASS_DIAGRAMS_PATH = "C:\\Users\\piets\\Git\\sidiff-lifting\\examples\\org.sidiff.slicing.configuration.webmodel\\configs\\classdiagram.scfg";
	

	
	@Override
	public Object start(IApplicationContext context) throws Exception {
		System.out.println("start");
		URI loadModelURI = EMFStorage.pathToUri(MODEL_PATH);
//		URI loadSlicingConfigurationURI_hypertext = EMFStorage.pathToUri(CONFIG_HYPERTEXTLAYER_PATH);
//		URI loadSlicingConfigurationURI_data = EMFStorage.pathToUri(CONFIG_DATALAYER_PATH);
		URI loadSlicingConfigurationURI_class_diagrams = EMFStorage.pathToUri(CONFIG_CLASS_DIAGRAMS_PATH);
		
		EObject model = EMFStorage.eLoad(loadModelURI);
//		EObject config_hypertext = EMFStorage.eLoad(loadSlicingConfigurationURI_hypertext);
//		EObject config_data = EMFStorage.eLoad(loadSlicingConfigurationURI_data);
		EObject config_class_diagrams = EMFStorage.eLoad(loadSlicingConfigurationURI_class_diagrams);
		
		Set<EObject> contexts = new HashSet<EObject>();
		for (Iterator<EObject> iterator = model.eResource().getAllContents(); iterator.hasNext();) {
			EObject eObject = (EObject) iterator.next();
//			if (eObject instanceof WebModel) {
//				contexts.add(eObject);
//			}			
			if(eObject instanceof Package){
				contexts.add(eObject);
			}
		}
		
	
		
//		SiDiffSlicingInterpreter siDiffSlicingInterpreter = new SiDiffSlicingInterpreter();
//		siDiffSlicingInterpreter.init((SlicingConfiguration)config_hypertext);
//		siDiffSlicingInterpreter.slice(contexts);
//		Slicing slicedModel = siDiffSlicingInterpreter.getSlicedModel();
//		System.out.println(slicedModel.getSlicedContextElements() + ", " + slicedModel.getSlicedBoundaryElements());
//		URI uri_sliced_hypertext_layer = StorageUtil.generateSaveURI(loadModelURI, (SlicingConfiguration) config_hypertext);
//		StorageUtil.serializeSlicedModel(slicedModel, uri_sliced_hypertext_layer, false);
//
//		
//		siDiffSlicingInterpreter.init((SlicingConfiguration)config_data);
//		siDiffSlicingInterpreter.slice(contexts);
//		slicedModel = siDiffSlicingInterpreter.getSlicedModel();
//		System.out.println(slicedModel.getSlicedContextElements() + ", " + slicedModel.getSlicedBoundaryElements());
//		URI uri_sliced_data_layer = StorageUtil.generateSaveURI(loadModelURI, (SlicingConfiguration) config_data);
//		StorageUtil.serializeSlicedModel(slicedModel, uri_sliced_data_layer, false);
//		
//		((SlicingConfiguration)config_data).setSlicingMode(SlicingMode.PESSIMISTIC);
//		siDiffSlicingInterpreter.init((SlicingConfiguration)config_data);
//		siDiffSlicingInterpreter.slice(contexts);
//		slicedModel = siDiffSlicingInterpreter.getSlicedModel();
//		System.out.println(slicedModel.getSlicedContextElements() + ", " + slicedModel.getSlicedBoundaryElements());
//		StorageUtil.serializeSlicedModel(slicedModel, StorageUtil.generateSaveURI(loadModelURI, (SlicingConfiguration) config_data), false);
		
		SiDiffSlicingInterpreter siDiffSlicingInterpreter = new SiDiffSlicingInterpreter();
		siDiffSlicingInterpreter.init((SlicingConfiguration)config_class_diagrams);
		siDiffSlicingInterpreter.slice(contexts);
		Slicing slicedModel = siDiffSlicingInterpreter.getSlicedModel();
		System.out.println(slicedModel.getSlicedContextElements() + ", " + slicedModel.getSlicedBoundaryElements());
		SiDiffSlicingStorage.serializeSlicedModel(slicedModel, SiDiffSlicingStorage.generateSaveURI(loadModelURI, (SlicingConfiguration) config_class_diagrams), false);
	
		String gv_path = MODEL_PATH.replace(loadModelURI.lastSegment(), "graph.dot");
		FileOperations.writeFile(gv_path, GraphUtil.getOutput());

		String command = "cmd /c start C:\\\"Program Files (x86)\"\\Graphviz2.38\\bin\\gvedit.exe " + gv_path;
		Runtime runtime = Runtime.getRuntime();
		Process process = runtime.exec(command);
		process.waitFor();
		return null;
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
