package org.eclipse.emf.henshin.examples.au;

import java.util.Iterator;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.interpreter.ApplicationMonitor;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.impl.LoggingApplicationMonitor;
import org.eclipse.emf.henshin.interpreter.impl.ParameterList;
import org.eclipse.emf.henshin.interpreter.impl.UnitApplicationImpl;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;

public class Demo {

	private final String baseDir = "model";
	private final String unitPath = "extract_eclass_execute.henshin";
	private final String modelPath = "model.ecore";
	private final String modelTransformationPath = "model_out.ecore";
	
	private HenshinResourceSet resourceSet;
	private UnitApplication app;
	private EPackage model;

	public static void main(String[] args) {
		new Demo();
	}

	public Demo() {
		// Initialize Henshin
		init();

		// Set input parameter values		
		app.setParameterValue("Selected", getModelElementByName("Person"));
		app.setParameterValue("NewClassName", "ContactInfo");
		app.setParameterValue("NewReferenceName", "contactInfo");

		// Set input parameter value list
		ParameterList<EAttribute> extractedAttributes = new ParameterList<EAttribute>();
		app.setParameterValue("SelectedAttributes", extractedAttributes);
		extractedAttributes.addValue((EAttribute) getModelElementByName("address"));
		extractedAttributes.addValue((EAttribute) getModelElementByName("phone"));
		extractedAttributes.addValue((EAttribute) getModelElementByName("email"));
		
//		app.setParameterValue("selectedOperations", getModelElementByName("EOperation0"));
		
		ParameterList<EOperation> extractedOperations = new ParameterList<EOperation>();
		app.setParameterValue("SelectedOperations", extractedOperations);
		extractedOperations.addValue((EOperation) getModelElementByName("printAddressLabel"));
		extractedOperations.addValue((EOperation) getModelElementByName("mailTo"));
	
		// Apply the (amalgamation) unit
		execute();
	}

	private void init() {
		
		// Create a resource set with a base directory
		resourceSet = new HenshinResourceSet(baseDir);
		
		// Load the module and find the rule
		Module module = resourceSet.getModule(unitPath, false);
		Unit unit = module.getUnit("mainUnit");

		// Load the model into a graph
		Resource resource = resourceSet.getResource(modelPath);
		model = (EPackage) resource.getContents().get(0);
		EGraph graph = new EGraphImpl();
		graph.addTree(model);

		// Create an engine and a unit application
		Engine engine = new EngineImpl();
		
		app = new UnitApplicationImpl(engine);
		app.setUnit(unit);
		app.setEGraph(graph);
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
