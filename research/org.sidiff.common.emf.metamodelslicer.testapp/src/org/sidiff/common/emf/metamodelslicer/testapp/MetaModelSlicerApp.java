package org.sidiff.common.emf.metamodelslicer.testapp;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.sidiff.common.emf.metamodelslicer.impl.MetaModelSlicer;


public class MetaModelSlicerApp implements IApplication {

	@Override
	public Object start(IApplicationContext context) throws Exception {

		System.out.println("*****************************");
		System.out.println("*****MetaModelSlicerTest*****");
		System.out.println("*****************************");
		
		String testingOutputPath = "/media/mrindt/data/Workspaces/Linux/MetaModelSlicer/TMP/sliced.ecore";
//		String testingOutputPath = "D://Eclipse_SiDiffOpt/configGenerator/org.sidiff.common.emf.metamodelslicer.testapp/output/sliced.ecore";
		
		MetaModelSlicer	slicer = new MetaModelSlicer();
	
		// UML Example		
//		EPackage epg = EPackage.Registry.INSTANCE.getEPackage("http://www.eclipse.org/uml2/4.0.0/UML");
//		List<String> listOfclassifiers = new ArrayList<String>();
//		listOfclassifiers.add("Class");
//		listOfclassifiers.add("Package");
//		listOfclassifiers.add("Operation");
//		listOfclassifiers.add("Element");
//		slicer.slice(epg, null, listOfclassifiers, null, "http://www.eclipse.org/uml2/4.0.0/UML/Sliced",testingOutputPath);			

		// Skeleton Example
//		EPackage epg = EPackage.Registry.INSTANCE.getEPackage("http://www.sidiff.org/org.sidiff.skeleton.model");
//		listOfclassifiers.add("Skeleton");
//		listOfclassifiers.add("ContourEdge");
//		slicer.slice(epg, null, listOfclassifiers, null, "http://www.sidiff.org/org.sidiff.skeleton.model/Sliced", testingOutputPath);	

		//Example for ModelInstance
		EPackage epg = EPackage.Registry.INSTANCE.getEPackage("http://www.sidiff.org/org.sidiff.skeleton.model");
		XMIResourceImpl resource = new XMIResourceImpl();
		File source = new File("D://Eclipse_SiDiffOpt/configGenerator/org.sidiff.common.emf.metamodelslicer.testapp/output/bird02.gml.smf.xmi");
		resource.load( new FileInputStream(source), new HashMap<Object,Object>());
		slicer.slice(epg, null, resource, null, "http://www.sidiff.org/org.sidiff.skeleton.model/Sliced", testingOutputPath);
				
	//	printMetaModels();
		
		
		return null;
	}

	@Override
	public void stop() {
		//do nothing		
	}
	
	//http://www.eclipse.org/uml2/4.0.0/UML
	public void printMetaModels() {
		
		for (String s : EPackage.Registry.INSTANCE.keySet())
			System.out.println(s);
		
	}

}
