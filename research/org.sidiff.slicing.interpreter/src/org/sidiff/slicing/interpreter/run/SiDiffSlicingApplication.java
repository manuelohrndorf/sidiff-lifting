package org.sidiff.slicing.interpreter.run;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.slicing.configuration.SlicingConfiguration;
import org.sidiff.slicing.interpreter.SiDiffSlicingInterpreter;
import org.sidiff.slicing.slicingmodel.Slicing;

import simpleWebModel.HypertextLayer;
import simpleWebModel.Page;
import simpleWebModel.WebModel;

public class SiDiffSlicingApplication implements IApplication{

	private static final String MODEL_PATH = "C:\\Users\\piets\\Git\\sidiff-lifting\\examples\\org.sidiff.coevolution.example.swml.multiviews.webmodel.sample\\version0\\PoetryContest.swml";
	private static final String CONFIG_PATH = "C:\\Users\\piets\\Git\\sidiff-lifting\\examples\\org.sidiff.slicing.configuration.webmodel\\configs\\HypertextLayer.scfg";
	@Override
	public Object start(IApplicationContext context) throws Exception {
		System.out.println("start");
		EObject model = EMFStorage.eLoad(EMFStorage.pathToUri(MODEL_PATH));
		EObject config = EMFStorage.eLoad(EMFStorage.pathToUri(CONFIG_PATH));
		
		Set<EObject> contextes = new HashSet<EObject>();
		for (Iterator<EObject> iterator = model.eResource().getAllContents(); iterator.hasNext();) {
			EObject eObject = (EObject) iterator.next();
			if (eObject instanceof WebModel || eObject instanceof HypertextLayer || eObject instanceof Page) {
				contextes.add(eObject);
				
			}			
		}
		
		SiDiffSlicingInterpreter siDiffSlicingInterpreter = new SiDiffSlicingInterpreter();
		siDiffSlicingInterpreter.init((SlicingConfiguration)config);
		siDiffSlicingInterpreter.slice(contextes);
		Slicing slicedModel = siDiffSlicingInterpreter.getSlicedModel();
		System.out.println(slicedModel.getSlicedContextElements() + ", " + slicedModel.getSlicedBoundaryElements());
		return null;
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
