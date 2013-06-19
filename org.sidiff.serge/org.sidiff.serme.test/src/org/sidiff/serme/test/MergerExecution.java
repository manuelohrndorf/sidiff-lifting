package org.sidiff.serme.test;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.impl.MatchImpl;
import org.eclipse.emf.henshin.interpreter.impl.UnitApplicationImpl;
import org.eclipse.emf.henshin.interpreter.util.InterpreterUtil;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.impl.HenshinFactoryImpl;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.papyrus.sysml.SysmlPackage;
import org.eclipse.uml2.uml.UMLPackage;

public class MergerExecution implements IApplication {

	private boolean editRule = true;
	private String folder = "testrules/";
	private String stmodule = "CREATE_STEREOTYPE_IN_EDITRULE.henshin";
	//private String stmodule = "ADD_STEREOTYPE_TO_EDITRULE.henshin";
	//private String stmodule = "RENAME_TO_STEREOTYPE_IN_EDITRULE.henshin";	
	//private String target = "SET_Class_Name_execute.henshin";
	private String target = "CREATE_ClassInPackage_execute.henshin";

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
		
		System.out.println("Generating stereotyped editrule...");

		String baseTypeS = "Class";
		String baseReferenceS = "base_" + baseTypeS;
		String stereoTypeS = "Block";
		String stereoPackageS = "http://www.eclipse.org/papyrus/0.7.0/SysML";		
		
		// Create a resource set:
		HenshinResourceSet resourceSet = new HenshinResourceSet(folder);

		// Load the module:
		Module module = resourceSet.getModule(stmodule, true);

		EGraph graph = new EGraphImpl(resourceSet.getResource(target));
		graph.addTree(UMLPackage.eINSTANCE);
		graph.addTree(SysmlPackage.eINSTANCE);

		// Create an engine and a rule application:
		Engine engine = new EngineImpl();

		UnitApplication unitapp = new UnitApplicationImpl(engine);
		unitapp.setEGraph(graph);
		unitapp.setUnit((Unit) module.getUnit("mainUnit"));

		unitapp.setParameterValue("baseType", baseTypeS);
		unitapp.setParameterValue("stereoType", stereoTypeS);
		unitapp.setParameterValue("stereoPackage", stereoPackageS);
		unitapp.setParameterValue("baseReference", baseReferenceS);
		
	//	Parameter baseType = HenshinFactoryImpl.eINSTANCE.createParameter();
	//	Parameter stereoType = HenshinFactoryImpl.eINSTANCE.createParameter("stereoType");
	//	Parameter stereoPackage = HenshinFactoryImpl.eINSTANCE.createParameter("stereoPackage");
	//	Parameter baseReference = HenshinFactoryImpl.eINSTANCE.createParameter("baseReference");

		
	//	Match partialMatch = new MatchImpl((Rule) module.getUnit("AddStereoTypeToEditRule"));
	//	partialMatch.setParameterValue(baseType, "X");


		// Iterate over all matches and print them on the console:
		//for (Match match : engine.findMatches((Rule) module.getUnit("AddStereoTypeToEditRule"), graph, partialMatch)) {
		//  System.out.println(match);
		//}
		

		// Execute the transformation unit:
		try {
			
			for(int i=1; i < 11; i++){
								
				System.out.println(unitapp.execute(null));
			
				//InterpreterUtil.executeOrDie(unitapp);
			}

			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		resourceSet.saveEObject(graph.getRoots().get(0),
				target.replace(baseTypeS, stereoTypeS));
		

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