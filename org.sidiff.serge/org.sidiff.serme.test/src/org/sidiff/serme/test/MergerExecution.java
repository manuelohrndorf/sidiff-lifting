package org.sidiff.serme.test;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.impl.RuleApplicationImpl;
import org.eclipse.emf.henshin.interpreter.util.InterpreterUtil;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.impl.HenshinPackageImpl;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.papyrus.sysml.internal.impl.SysmlPackageImpl;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.internal.impl.UMLPackageImpl;

public class MergerExecution implements IApplication {

	private boolean editRule = true;
	private String folder = "testrules/";
	private String stmodule = "APPLY_STEREOTYPE.henshin";
	private String target = "SET_Class_Name_execute.henshin";

	// private String henshinFilename = folder +
	// "CREATE_CLASS_IN_PACKAGE_execute.henshin";
	private String henshinFilename = folder + "APPLY_STEREOTYPE_BLOCK.henshin";
	private String savePath = folder + "merged";

	@Override
	public Object start(IApplicationContext context) throws Exception {

		System.out.println("SeqMerger starting...");
		String[] args = (String[]) context.getArguments().get(
				IApplicationContext.APPLICATION_ARGS);
		henshinFilename = args[0];
		savePath = args[1];
		run();

		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	public void run() {

		HenshinPackageImpl.init();
		SysmlPackageImpl.init();
		UMLPackageImpl.init();	
		
		
		System.out.println("Generating stereotyped rule...");

		// Create a resource set:
		HenshinResourceSet resourceSet = new HenshinResourceSet(folder);
		
		// Load the module:
		Module module = resourceSet.getModule(stmodule, false);
		
		// Load the model into an EGraph:
		EGraph graph = new EGraphImpl(resourceSet.getResource(target));
		
		// Create an engine and a rule application:
		Engine engine = new EngineImpl();
		RuleApplication ruleapp = new RuleApplicationImpl(engine);
		ruleapp.setEGraph(graph);
		
		ruleapp.setRule((Rule) module.getUnit("ApplyStereoType"));
		

	//	ruleapp.setParameterValue("stereoPackage", SysmlPackageImpl.eINSTANCE);
		ruleapp.setParameterValue("baseType", (EClass)UMLFactory.eINSTANCE.createClass());
		ruleapp.setParameterValue("stereoType", (EClass) UMLFactory.eINSTANCE.createClass());	
//		ruleapp.setParameterValue("V", "v");
//		ruleapp.setParameterValue("W", "w");
//		ruleapp.setParameterValue("X", "x");
//		ruleapp.setParameterValue("Y", "y");
//		ruleapp.setParameterValue("Z", "z");


		// Execute the transformation unit:
		try {
		InterpreterUtil.executeOrDie(ruleapp);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
			
	
		
		resourceSet.saveEObject(graph.getRoots().get(0), module.getName() + "_merged.henshin");
	

		System.out.println("Successfully added stereotype.");
		


		// MergeEngine mergeEngine = new MergeEngine(henshinFilename);
		// HenshinResourceSet resourceSet = new HenshinResourceSet();
		//
		// try {
		// mergeEngine.startEngine(resourceSet);
		//
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}

	public static void main(String[] args) {
		MergerExecution mergeExec = new MergerExecution();
		mergeExec.run();
	}

}