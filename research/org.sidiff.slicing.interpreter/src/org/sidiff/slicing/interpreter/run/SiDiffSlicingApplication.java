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
import org.sidiff.slicing.configuration.SlicingMode;
import org.sidiff.slicing.interpreter.SiDiffSlicingInterpreter;
import org.sidiff.slicing.interpreter.util.StorageUtil;
import org.sidiff.slicing.slicingmodel.Slicing;

import simpleWebModel.WebModel;

public class SiDiffSlicingApplication implements IApplication{

	private static final String MODEL_PATH = "D:\\Git\\sidiff-lifting\\examples\\org.sidiff.coevolution.example.swml.multiviews.webmodel.sample\\version0\\PoetryContest.swml";
	private static final String CONFIG_HYPERTEXTLAYER_PATH = "D:\\Git\\sidiff-lifting\\examples\\org.sidiff.slicing.configuration.webmodel\\configs\\HypertextLayer.scfg";
	private static final String CONFIG_DATALAYER_PATH = "D:\\Git\\sidiff-lifting\\examples\\org.sidiff.slicing.configuration.webmodel\\configs\\DataLayer.scfg";
	@Override
	public Object start(IApplicationContext context) throws Exception {
		System.out.println("start");
		URI loadModelURI = EMFStorage.pathToUri(MODEL_PATH);
		URI loadSlicingConfigurationURI_hypertext = EMFStorage.pathToUri(CONFIG_HYPERTEXTLAYER_PATH);
		URI loadSlicingConfigurationURI_data = EMFStorage.pathToUri(CONFIG_DATALAYER_PATH);
		
		EObject model = EMFStorage.eLoad(loadModelURI);
		EObject config_hypertext = EMFStorage.eLoad(loadSlicingConfigurationURI_hypertext);
		EObject config_data = EMFStorage.eLoad(loadSlicingConfigurationURI_data);
		
		Set<EObject> contexts = new HashSet<EObject>();
		for (Iterator<EObject> iterator = model.eResource().getAllContents(); iterator.hasNext();) {
			EObject eObject = (EObject) iterator.next();
			if (eObject instanceof WebModel) {
				contexts.add(eObject);
			}			
		}
		
	
		
		SiDiffSlicingInterpreter siDiffSlicingInterpreter = new SiDiffSlicingInterpreter();
		siDiffSlicingInterpreter.init((SlicingConfiguration)config_hypertext);
		siDiffSlicingInterpreter.slice(contexts);
		Slicing slicedModel = siDiffSlicingInterpreter.getSlicedModel();
		System.out.println(slicedModel.getSlicedContextElements() + ", " + slicedModel.getSlicedBoundaryElements());
		StorageUtil.serializeSlicedModel(slicedModel, StorageUtil.generateSaveURI(loadModelURI, (SlicingConfiguration) config_hypertext), false);
		
		siDiffSlicingInterpreter.init((SlicingConfiguration)config_data);
		siDiffSlicingInterpreter.slice(contexts);
		slicedModel = siDiffSlicingInterpreter.getSlicedModel();
		System.out.println(slicedModel.getSlicedContextElements() + ", " + slicedModel.getSlicedBoundaryElements());
		StorageUtil.serializeSlicedModel(slicedModel, StorageUtil.generateSaveURI(loadModelURI, (SlicingConfiguration) config_data), false);
		
		((SlicingConfiguration)config_data).setSlicingMode(SlicingMode.PESSIMISTIC);
		siDiffSlicingInterpreter.init((SlicingConfiguration)config_data);
		siDiffSlicingInterpreter.slice(contexts);
		slicedModel = siDiffSlicingInterpreter.getSlicedModel();
		System.out.println(slicedModel.getSlicedContextElements() + ", " + slicedModel.getSlicedBoundaryElements());
		StorageUtil.serializeSlicedModel(slicedModel, StorageUtil.generateSaveURI(loadModelURI, (SlicingConfiguration) config_data), false);
		return null;
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
