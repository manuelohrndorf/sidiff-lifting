package org.sidiff.slicer.rulebased.run;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.matcher.MatcherUtil;
import org.sidiff.slicer.ISlicer;
import org.sidiff.slicer.rulebased.RuleBasedSlicer;
import org.sidiff.slicer.rulebased.configuration.SlicingConfiguration;
import org.sidiff.slicer.rulebased.configuration.SlicingMode;
import org.sidiff.slicer.util.SlicerUtil;

public class SiDiffSlicingApplication implements IApplication{
	
	@Override
	public Object start(IApplicationContext context) throws Exception {
		System.out.println("start");
		String[] argument = (String[]) context.getArguments().get(IApplicationContext.APPLICATION_ARGS);
		String input_model_path = argument[0];
		String input_context_ids = argument[1];
		
		String[] context_ids = input_context_ids.replaceAll(" ", "").split(";");
		
		
		URI loadModelURI = EMFStorage.pathToUri(input_model_path);


		
		EObject model = EMFStorage.eLoad(loadModelURI);

		LiftingSettings settings = new LiftingSettings(EMFModelAccess.getDocumentTypes(model.eResource(), Scope.RESOURCE));
		settings.setMatcher(MatcherUtil.getMatcher("org.sidiff.matcher.signature.name.NamedElementMatcher"));
		SlicingConfiguration slicing_config = new SlicingConfiguration(SlicingMode.MINIMAL_BATCH, settings);
		
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
		
	
		ISlicer slicer = new RuleBasedSlicer();
		slicer.init(slicing_config);
		slicer.slice(contexts);
		Collection<EObject> modelSlice = slicer.getModelSlice().export();
		System.out.println(modelSlice);

		SlicerUtil.serializeSlicedModel(modelSlice, URI.createURI(loadModelURI.toString().replace(loadModelURI.lastSegment(), "sliced_" + loadModelURI.lastSegment())), false);
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
