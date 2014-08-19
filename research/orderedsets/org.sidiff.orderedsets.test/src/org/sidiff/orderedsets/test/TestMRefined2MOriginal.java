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
import org.sidiff.orderedsets.MRefined2MOrig;
import org.sidiff.orderedsets.TrafoFactory;
import org.sidiff.orderedsets.impl.MRefined2MOrigJava;

import sample.mm.samplemm.SamplemmPackage;

public class TestMRefined2MOriginal {

	static String REFINED = "Sample03_Refined_out.xmi";
	static String ORIGINAL = REFINED.replace("Refined", "Original");

	static {
		// Initialize the model (workaround weil java application und keine
		// eclipse app)
		EcorePackage.eINSTANCE.eClass();
		SamplemmPackage.eINSTANCE.eClass();
		sample.mm.refined.samplemm.SamplemmPackage.eINSTANCE.eClass();
	}

	public static void main(String[] args) {
		Resource refined = load();
		EObject modelRefined = refined.getContents().get(0);
		EPackage pRefined = modelRefined.eClass().getEPackage();
		String nsUriOriginal = pRefined.getNsURI().substring(0, pRefined.getNsURI().length() - 8);
		EPackage pOriginal = EPackage.Registry.INSTANCE.getEPackage(nsUriOriginal);

		MRefined2MOrigJava.pOriginal = pOriginal;
		MRefined2MOrig trafo = TrafoFactory.createMRefined2MOrig();
		EObject modelOriginal = trafo.mRefined2mOrig(refined);

		System.out.println("test: " + modelOriginal);

		save(modelOriginal);
	}

	// private static Resource loadMetamodel(){
	// return null;
	// }

	private static Resource load() {

		// Register the XMI resource factory for the .xmi extension
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("xmi", new XMIResourceFactoryImpl());

		// Obtain a new resource set
		ResourceSet resSet = new ResourceSetImpl();

		// Get the resource
		Resource r = resSet.getResource(
				URI.createFileURI(ConstantTestSetup.WORKSPACE_PATH + System.getProperty("file.separator")
						+ "sample.metamodel.test/sampleInstances/" + REFINED), true);

		return r;
	}

	private static void save(EObject modelRoot) {
		// Obtain a new resource set
		ResourceSet resSet = new ResourceSetImpl();

		// Create a resource
		Resource resource = resSet.createResource(URI.createFileURI(ConstantTestSetup.WORKSPACE_PATH
				+ System.getProperty("file.separator") + "sample.metamodel.test/sampleInstances/" + ORIGINAL));
		resource.getContents().add(modelRoot);

		// Now save the content.
		try {
			resource.save(Collections.EMPTY_MAP);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
