package org.sidiff.common.emf.metamodelslicer.testapp;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.sidiff.common.emf.metamodelslicer.impl.MetaModelSlicer;


public class MetaModelSlicerApp implements IApplication {

	@Override
	public Object start(IApplicationContext context) throws Exception {

		System.out.println("*****************************");
		System.out.println("*****MetaModelSlicerTest*****");
		System.out.println("*****************************");
		
		
		MetaModelSlicer	slicer = new MetaModelSlicer();
		List<EClassifier> listOfclassifiers = new ArrayList<EClassifier>();
		
		EPackage epg = EPackage.Registry.INSTANCE.getEPackage("http://www.eclipse.org/uml2/4.0.0/UML");
		listOfclassifiers.add(epg.getEClassifier("Class"));
		listOfclassifiers.add(epg.getEClassifier("Package"));
		listOfclassifiers.add(epg.getEClassifier("Operation"));
		slicer.slice(epg, null, listOfclassifiers, null, "http://www.eclipse.org/uml2/4.0.0/UML/Sliced");			

//		EPackage epg = EPackage.Registry.INSTANCE.getEPackage("http://www.sidiff.org/org.sidiff.skeleton.model");
//		listOfclassifiers.add(epg.getEClassifier("SkeletonEdge"));
//		listOfclassifiers.add(epg.getEClassifier("ContourEdge"));
//		slicer.slice(epg, null, listOfclassifiers, null, "http://www.sidiff.org/org.sidiff.skeleton.model/Sliced");	

		


		
		printMetaModels();
		
		
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
