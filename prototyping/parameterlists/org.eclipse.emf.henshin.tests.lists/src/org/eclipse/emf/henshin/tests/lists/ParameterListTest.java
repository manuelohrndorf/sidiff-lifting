package org.eclipse.emf.henshin.tests.lists;

import java.util.Iterator;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.interpreter.ApplicationMonitor;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.impl.AbstractApplicationImpl;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.impl.LoggingApplicationMonitor;
import org.eclipse.emf.henshin.interpreter.impl.ParameterList;
import org.eclipse.emf.henshin.interpreter.impl.RuleApplicationImpl;
import org.eclipse.emf.henshin.interpreter.impl.UnitApplicationImpl;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;

public class ParameterListTest {

	private String baseDir = "parameterListTests/scenario";
	private String unitPath = "rule.henshin";
	private String mainUnit = "kernel";
	private String modelPath = "model.ecore";
	private String modelTransformationPath = "model_out.ecore";
	
	private HenshinResourceSet resourceSet;
	private EPackage model;
	private Unit unit;
	private Engine engine;
	private EGraph graph;
	private AbstractApplicationImpl app;
	

	public static void main(String[] args) {
		new ParameterListTest(9);
	}

	public ParameterListTest(int scenario) {

		switch (scenario) {
		case 1:
			scenario01();
			break;
		case 2:
			scenario02();
			break;
		case 3:
			scenario03();
			break;
		case 4:
			scenario04();
			break;
		case 5:
			scenario05();
			break;
		case 6:
			scenario06();
			break;
		case 7:
			scenario07();
			break;
		case 8:
			scenario08();
			break;
		case 9:
			scenario09();
			break;

		default:
			System.out.println("!!! scenario " + scenario + " not found !!!");
			break;
		}
	}

	/**
	 * Normal parameter value (multi-rule).
	 */
	private void scenario01() {
		baseDir += "01";
		init();
		
		// Create unit parameter
		app.setParameterValue("inputValue", "EAttribute0");
		
		// Start test
		execute();
//		findAllMatches();
	}
	
	/**
	 * Single parameter value list (multi-rule).
	 */
	private void scenario02() {
		baseDir += "02";
		init();
		
		// Create multi-rule parameter values
		ParameterList<EAttribute> selectedAttributes = new ParameterList<EAttribute>();
		app.setParameterValue("attrInput", selectedAttributes);
		
		selectedAttributes.addValue((EAttribute) getModelElementByName("EAttribute0"));
		selectedAttributes.addValue((EAttribute) getModelElementByName("EAttribute1"));
		
		// Start test
		execute();
	}

	
	/**
	 * <ul>
	 * <li>Normal parameter value (multi-rule).</li>
	 * <li>Single parameter value list (multi-rule).</li>
	 * </ul>
	 */
	private void scenario03() {
		baseDir += "03";
		init();
		
		// Create unit parameters
		app.setParameterValue("inputValue", false);
		
		// -> EAttribute0 and EAttribute3 are derived = true
		// -> EAttribute1 and EAttribute2 are derived = false
		
		// Create multi-rule parameter values
		ParameterList<EAttribute> selectedAttributes = new ParameterList<EAttribute>();
		app.setParameterValue("attrInput", selectedAttributes);
		
		selectedAttributes.addValue((EAttribute) getModelElementByName("EAttribute0"));
		selectedAttributes.addValue((EAttribute) getModelElementByName("EAttribute1"));
		selectedAttributes.addValue((EAttribute) getModelElementByName("EAttribute2"));
		selectedAttributes.addValue((EAttribute) getModelElementByName("EAttribute3"));
		
		// Start test
		execute();
	}
	
	/**
	 * <ul>
	 * <li>Multiple normal rule parameter values (kernel + multi-rule).</li>
	 * <li>Multiple parameter value lists. (multi-rule)</li>
	 * </ul>
	 */
	private void scenario04() {
		baseDir += "04";
		init();
		
		// Create unit parameters
		
		// Kernel:
		app.setParameterValue("eClassInput", getModelElementByName("EClass0"));
		app.setParameterValue("kernelInput", false);
//		app.setParameterValue("kernelInput", true); // -> Rule not applicable!
		
		// -> EClass0 is abstract = false
		
		// Multi:
		app.setParameterValue("inputValue01", false);
		app.setParameterValue("inputValue02", false);
		
		// -> EAttribute0 and EAttribute3 are derived = true
		// -> EAttribute1 and EAttribute2 are derived = false
		
		// Create multi-rule parameter values
		ParameterList<EAttribute> selectedAttributes = new ParameterList<EAttribute>();
		app.setParameterValue("attrInput", selectedAttributes);
		
		selectedAttributes.addValue((EAttribute) getModelElementByName("EAttribute0"));
		selectedAttributes.addValue((EAttribute) getModelElementByName("EAttribute1"));
		
		ParameterList<EOperation> selectedOperations = new ParameterList<EOperation>();
		app.setParameterValue("opInput", selectedOperations);
		
		selectedOperations.addValue((EOperation) getModelElementByName("EOperation0"));
		selectedOperations.addValue((EOperation) getModelElementByName("EOperation1"));
		
		// -> Prematch01: {EClass0(abstract = false), EAttribute0(derived = false), EOperation0}
		//    -> Fails because EAttribute0 is derived = true
		// -> Prematch02: {EClass0(abstract = false), EAttribute1(derived = false), EOperation1}
		
		// Start test
		execute();
	}
	
	/**
	 * Multiple multi-rules
	 */
	private void scenario05() {
		baseDir += "05";
		init();
		
		// Create multi-rule parameter values
		app.setParameterValue("value", false);
		
		// -> Not in conflict with EAttribute parameter list values
		
		ParameterList<EAttribute> selectedAttributes = new ParameterList<EAttribute>();
		app.setParameterValue("attrInput", selectedAttributes);
		
		selectedAttributes.addValue((EAttribute) getModelElementByName("EAttribute1"));
		selectedAttributes.addValue((EAttribute) getModelElementByName("EAttribute2"));
		selectedAttributes.addValue((EAttribute) getModelElementByName("EAttribute3"));
		
		ParameterList<EOperation> selectedOperations = new ParameterList<EOperation>();
		app.setParameterValue("opInput", selectedOperations);
		
		selectedOperations.addValue((EOperation) getModelElementByName("EOperation1"));
		
		// Start test
		execute();
	}
	
	/**
	 * Multiple multi-rules. -> No Match in multiRule01.
	 */
	private void scenario06() {
		baseDir += "06";
		init();
		
		// Create multi-rule parameter values
		app.setParameterValue("value", true);
		
		// -> In conflict with EAttribute parameter list values
		
		ParameterList<EAttribute> selectedAttributes = new ParameterList<EAttribute>();
		app.setParameterValue("attrInput", selectedAttributes);
		
		selectedAttributes.addValue((EAttribute) getModelElementByName("EAttribute0"));
		selectedAttributes.addValue((EAttribute) getModelElementByName("EAttribute1"));
		selectedAttributes.addValue((EAttribute) getModelElementByName("EAttribute2"));
		
		ParameterList<EOperation> selectedOperations = new ParameterList<EOperation>();
		app.setParameterValue("opInput", selectedOperations);
		
		selectedOperations.addValue((EOperation) getModelElementByName("EOperation0"));
		
		// Start test
		execute();
	}
	
	/**
	 * Parameter list for single rule.
	 */
	private void scenario07() {
		baseDir += "07";
		init();
		
		// Create multi-rule parameter values
		ParameterList<EClass> classes = new ParameterList<EClass>();
		app.setParameterValue("classInput", classes);
		
		classes.addValue((EClass) getModelElementByName("EClass0"));
		classes.addValue((EClass) getModelElementByName("EClass3"));
		classes.addValue((EClass) getModelElementByName("EClass4"));
		
		// Start test
		findAllMatches();
	}
	
	/**
	 * Multi-level multi-rules
	 */
	private void scenario08() {
		baseDir += "08";
		mainUnit = "level01_package";
		init();
		
		// Create multi-rule parameter values
		
		/* 
		 * EPackage Level
		 */
		ParameterList<EPackage> selectedPackages = new ParameterList<EPackage>();
		app.setParameterValue("packInput", selectedPackages);
		
		ParameterList<EPackage>.Value ePackage0 = selectedPackages.addValue((EPackage) getModelElementByName("EPackage0"));
		ParameterList<EPackage>.Value ePackage1 = selectedPackages.addValue((EPackage) getModelElementByName("EPackage1"));
		
		/*
		 *  EClass Level
		 */
		ParameterList<EClass> selectedEClasses = new ParameterList<EClass>(selectedPackages);
		app.setParameterValue("classInput", selectedEClasses);
		
		// -> EPackage0
		ParameterList<EClass>.Value eClass0 = selectedEClasses.addValue((EClass) getModelElementByName("EClass0"), ePackage0);
		
		// -> EPackage1
		@SuppressWarnings("unused")
		ParameterList<EClass>.Value eClass3 = selectedEClasses.addValue((EClass) getModelElementByName("EClass3"), ePackage1);
		ParameterList<EClass>.Value eClass4 = selectedEClasses.addValue((EClass) getModelElementByName("EClass4"), ePackage1);
		
		/*
		 * EAttribute Level
		 */
		ParameterList<EAttribute> selectedAttributes = new ParameterList<EAttribute>(selectedEClasses);
		app.setParameterValue("attrInput", selectedAttributes);
		
		// -> EClass0
		selectedAttributes.addValue((EAttribute) getModelElementByName("EAttribute0"), eClass0);
		selectedAttributes.addValue((EAttribute) getModelElementByName("EAttribute1"), eClass0);
		selectedAttributes.addValue((EAttribute) getModelElementByName("EAttribute3"), eClass0);
		
		// -> EClass3
		
		// -> EClass4
		selectedAttributes.addValue((EAttribute) getModelElementByName("EAttribute11"), eClass4);
		selectedAttributes.addValue((EAttribute) getModelElementByName("EAttribute12"), eClass4);
		
		/*
		 * EOperation Level
		 */
		ParameterList<EOperation> selectedOperations = new ParameterList<EOperation>(selectedEClasses);
		app.setParameterValue("opInput", selectedOperations);
		
		// -> EClass0
		ParameterList<EOperation>.Value eOperation0 = selectedOperations.addValue((EOperation) getModelElementByName("EOperation0"), eClass0);
		ParameterList<EOperation>.Value eOperation1 = selectedOperations.addValue((EOperation) getModelElementByName("EOperation1"), eClass0);
		
		// -> EClass3
		
		// -> EClass4
		@SuppressWarnings("unused")
		ParameterList<EOperation>.Value eOperation2 = selectedOperations.addValue((EOperation) getModelElementByName("EOperation2"), eClass4);
		ParameterList<EOperation>.Value eOperation3 = selectedOperations.addValue((EOperation) getModelElementByName("EOperation3"), eClass4);

		/*
		 *  EParameter Level
		 */
		ParameterList<EParameter> selectedParameters = new ParameterList<EParameter>(selectedOperations);
		app.setParameterValue("paramInput", selectedParameters);
		
		// -> EOperation0
		selectedParameters.addValue((EParameter) getModelElementByName("p1"), eOperation0);
		
		// -> EOperation1
		selectedParameters.addValue((EParameter) getModelElementByName("p2"), eOperation1);
		selectedParameters.addValue((EParameter) getModelElementByName("p3"), eOperation1);
		
		// -> EOperation2
		
		// -> EOperation3
		selectedParameters.addValue((EParameter) getModelElementByName("p5"), eOperation3);
		selectedParameters.addValue((EParameter) getModelElementByName("p6"), eOperation3);
		
		// Start test
		findAllMatches();
	}
	
	/**
	 * Multi-level multi-rules
	 */
	private void scenario09() {
		baseDir += "09";
		mainUnit = "level01_package";
		init();
		
		/* 
		 * EPackage Level
		 */
		ParameterList<EPackage> selectedPackages = new ParameterList<EPackage>();
		app.setParameterValue("packInput", selectedPackages);
		
		ParameterList<EPackage>.Value ePackage0 = selectedPackages.addValue((EPackage) getModelElementByName("EPackage0"));
		ParameterList<EPackage>.Value ePackage1 = selectedPackages.addValue((EPackage) getModelElementByName("EPackage1"));
		
		/*
		 *  EClass Level
		 */
		ParameterList<EClass> selectedEClasses = new ParameterList<EClass>(selectedPackages);
		app.setParameterValue("classInput", selectedEClasses);
		
		// -> EPackage0
		ParameterList<EClass>.Value eClass0 = selectedEClasses.addValue((EClass) getModelElementByName("EClass0"), ePackage0);
		
		// -> EPackage1
		ParameterList<EClass>.Value eClass2 = selectedEClasses.addValue((EClass) getModelElementByName("EClass2"), ePackage1);
		@SuppressWarnings("unused")
		ParameterList<EClass>.Value eClass3 = selectedEClasses.addValue((EClass) getModelElementByName("EClass3"), ePackage1);
		
		/*
		 * EAttribute Level
		 */
		ParameterList<EAttribute> selectedAttributes = new ParameterList<EAttribute>(selectedEClasses);
		app.setParameterValue("attrInput", selectedAttributes);
		
		// -> EClass0
		selectedAttributes.addValue((EAttribute) getModelElementByName("EAttribute0"), eClass0);
		selectedAttributes.addValue((EAttribute) getModelElementByName("EAttribute1"), eClass0);
		
		// -> EClass0
		selectedAttributes.addValue((EAttribute) getModelElementByName("EAttribute6"), eClass2);
		
		// -> EClass3
		
		/*
		 * EOperation Level
		 */
		ParameterList<EOperation> selectedOperations = new ParameterList<EOperation>(selectedEClasses);
		app.setParameterValue("opInput", selectedOperations);
		
		// -> Empty List -> No EOperation matching!
		
		// Start test
		findAllMatches();
	}

	private void init() {
		
		// Create a resource set with a base directory
		resourceSet = new HenshinResourceSet(baseDir);
		
		// Load the module and find the rule
		Module module = resourceSet.getModule(unitPath, false);
		unit = module.getUnit(mainUnit);

		// Load the model into a graph
		Resource resource = resourceSet.getResource(modelPath);
		model = (EPackage) resource.getContents().get(0);
		graph = new EGraphImpl(model);

		// Create an engine and a unit application
		engine = new EngineImpl();
		
		if(unit instanceof Rule) {
			app = new RuleApplicationImpl(engine);
			app.setUnit(unit);
			app.setEGraph(graph);
		} else {
			app = new UnitApplicationImpl(engine);
			app.setUnit(unit);
			app.setEGraph(graph);
		}
	}
	
	private void execute() {
		// Apply the (amalgamation) unit
		ApplicationMonitor monitor = new LoggingApplicationMonitor();
		boolean success = app.execute(monitor);
		System.out.println("Success: " + success);
		System.out.println("Transformation saved: " + modelTransformationPath);
		
		// Save transformation
		resourceSet.saveEObject(model, modelTransformationPath);
	}
	
	private void findAllMatches() {
		if(unit instanceof Rule) {
			Iterator<Match> matches = engine.findMatches((Rule) unit, graph, (Match) app.getAssignment()).iterator();
			
			while(matches.hasNext()) {
				Match match = matches.next();

				System.out.println(match + "\n");
			}
		} else {
			System.err.println("Main unit is not a rule!");
		}
	}
	
	private EObject getModelElementByName(String name) {
		Iterator<EObject> it = model.eAllContents();

		while (it.hasNext()) {
			EObject element = it.next();

			if (element instanceof ENamedElement) {
				if (((ENamedElement) element).getName().equals(name)) {
					return element;
				}
			}
		}

		System.out.println("!!! " + name + " not found !!!");
		return null;
	}
}
