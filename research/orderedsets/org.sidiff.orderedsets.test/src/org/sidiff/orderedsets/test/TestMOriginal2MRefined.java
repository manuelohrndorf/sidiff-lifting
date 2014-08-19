package org.sidiff.orderedsets.test;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.sidiff.orderedsets.MOrig2MRefined;
import org.sidiff.orderedsets.TrafoFactory;
import org.sidiff.orderedsets.impl.MOrig2MRefinedJava;
import org.sidiff.orderedsets.util.OrderedSetUtil;

public class TestMOriginal2MRefined {

	static {
		// Initialize the model packages 
		// (workaround as we are a pure java app and no eclipse plugin)
		EcorePackage.eINSTANCE.eClass();
		sample.mm.samplemm.SamplemmPackage.eINSTANCE.eClass();
		sample.mm.refined.samplemm.SamplemmPackage.eINSTANCE.eClass();

	}

	public static void main(String[] args) {
		String basePath = ConstantTestSetup.WORKSPACE_PATH + System.getProperty("file.separator")
				+ "sample.metamodel.test/sampleInstances/";
		
//		transform(basePath + "create/Original_A.xmi");
//		transform(basePath + "create/Original_B.xmi");
//		
//		transform(basePath + "createBefore/Original_A.xmi");
//		transform(basePath + "createBefore/Original_B.xmi");
//		
//		transform(basePath + "shift/Original_A.xmi");
//		transform(basePath + "shift/Original_B.xmi");
//		
//		transform(basePath + "shiftBefore/Original_A.xmi");
//		transform(basePath + "shiftBefore/Original_B.xmi");
//		
//		transform(basePath + "shiftSubsequence/Original_A.xmi");
//		transform(basePath + "shiftSubsequence/Original_B.xmi");
//		
//		transform(basePath + "shiftSubsequenceBefore/Original_A.xmi");
//		transform(basePath + "shiftSubsequenceBefore/Original_B.xmi");
//	
//		transform(basePath + "swap/Original_A.xmi");
//		transform(basePath + "swap/Original_B.xmi");
		
		transform(basePath + "Ti1984TCS/Original_A.xmi");
		transform(basePath + "Ti1984TCS/Original_B.xmi");
	}

	private static void transform(String path){
		System.out.println("processing: " + path);
		Resource original = load(path);
		EObject modelOriginal = original.getContents().get(0);
		EPackage pOriginal = modelOriginal.eClass().getEPackage();
		String nsUriRefined = OrderedSetUtil.getRefinedUri(pOriginal.getNsURI());
		EPackage pRefined = EPackage.Registry.INSTANCE.getEPackage(nsUriRefined);

		MOrig2MRefinedJava.pRefined = pRefined;
		MOrig2MRefined trafo = TrafoFactory.createMOrig2MRefined();
		EObject modelRefined = trafo.mOrig2mRefined(original);

		save(modelRefined, path.replace("Original", "Refined"));
	}
	
	private static Resource load(String path) {

		// Register the XMI resource factory for the .website extension

		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("xmi", new XMIResourceFactoryImpl());

		// Obtain a new resource set
		ResourceSet resSet = new ResourceSetImpl();

		// Get the resource
		Resource r = resSet.getResource(
				URI.createFileURI(path), true);
		// Get the first model element and cast it to the right type, in my
		// example everything is hierarchical included in this first node
		return r;
	}

	private static void save(EObject modelRoot, String path) {
		// Obtain a new resource set
		ResourceSet resSet = new ResourceSetImpl();

		// Create a resource
		Resource resource = resSet.createResource(URI.createFileURI(path));
		resource.getContents().add(modelRoot);

		// Now save the content.
		try {
			resource.save(Collections.EMPTY_MAP);
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}

}
