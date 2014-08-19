package org.sidiff.orderedsets.test;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.sidiff.orderedsets.MMOrig2MMRefined;
import org.sidiff.orderedsets.TrafoFactory;

public class TestMMOriginal2MMRefined {
	
	public static void main(String[] args) {
		EPackage original = load();
		MMOrig2MMRefined trafo = TrafoFactory.createMMOrig2MMRefined();
		EPackage refined = trafo.mmOrig2mmRefined(original);
		save(refined);
	}
	
	private static EPackage load() {
	    // Initialize the model
	    EcorePackage.eINSTANCE.eClass();
	    
	    // Register the XMI resource factory for the .website extension

	    Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
	    Map<String, Object> m = reg.getExtensionToFactoryMap();
	    m.put("ecore", new EcoreResourceFactoryImpl());

	    // Obtain a new resource set
	    ResourceSet resSet = new ResourceSetImpl();

	    // Get the resource
	    Resource resource = resSet.getResource(URI.createFileURI(ConstantTestSetup.WORKSPACE_PATH + System.getProperty("file.separator") + "sample.metamodel/model/SampleMetamodel.ecore"), true);
	    // Get the first model element and cast it to the right type, in my
	    // example everything is hierarchical included in this first node
	    EPackage p = (EPackage) resource.getContents().get(0);
	    return p;
	  }
	
	private static void save(EPackage p){
		  // Obtain a new resource set
	    ResourceSet resSet = new ResourceSetImpl();

	    // Create a resource
	    Resource resource = resSet.createResource(URI.createFileURI(ConstantTestSetup.WORKSPACE_PATH + System.getProperty("file.separator") + "sample.metamodel.refined/model/SampleMetamodel.ecore"));
	    // Get the first model element and cast it to the right type, in my
	    // example everything is hierarchical included in this first node
	    resource.getContents().add(p);

	    // Now save the content.
	    try {
	      resource.save(Collections.EMPTY_MAP);
	    } catch (IOException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    }
	}

}
