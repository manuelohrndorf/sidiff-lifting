package org.sidiff.patching.test.sa;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import SA.ComponentInstance;
import SA.PortInstance;
import SA.SAPackage;

public class ModelCorrections {

	public static void main(String[] args) {
		// Initialize the model type
		SAPackage.eINSTANCE.eClass();

		scen_04b();
	}

	/****************************************************************
	 * Corrections to Scenario 04b
	 ****************************************************************/

	private static void scen_04b() {
		File modelFile = new File("../org.sidiff.patching.testmodels/sa/scenario_04b.sa");
		EObject root = load(modelFile.getAbsolutePath());

		convertInPortToOutPort(root, "CraneFBToMaxLeftSTactile_CraneFBPort");
		convertInPortToOutPort(root, "CraneFBToMaxRightSTactile_CraneFBPort");
		convertInPortToOutPort(root, "CraneFBToAtPositionSlideSTactile_CraneFBPort");
		convertInPortToOutPort(root, "CraneFBToAtPositionStackSTactile_CraneFBPort");
		convertInPortToOutPort(root, "CraneFBToAtPositionStampSTactile_CraneFBPort");
		
		
		save(root);
	}

//	private static void convert_CraneFBToMaxLeftSTactile_CraneFBPort_ToOutPort(EObject root) {
//		ComponentInstance craneFB = null;
//		for (Iterator<EObject> iterator = root.eAllContents(); iterator.hasNext();) {
//			EObject node = iterator.next();
//			if (node instanceof ComponentInstance) {
//				ComponentInstance component = (ComponentInstance) node;
//				if (component.getName().equals("craneFB")) {
//					craneFB = component;
//				}
//			}
//		}
//
//		PortInstance CraneFBToMaxLeftSTactile_CraneFBPort = null;
//		for (PortInstance port : craneFB.getInPorts()) {
//			if (port.getName().equals("CraneFBToMaxLeftSTactile_CraneFBPort")) {
//				CraneFBToMaxLeftSTactile_CraneFBPort = port;
//			}
//		}
//
//		craneFB.getInPorts().contains(i)
//		craneFB.getInPorts().remove(CraneFBToMaxLeftSTactile_CraneFBPort);
//		craneFB.getOutPorts().add(CraneFBToMaxLeftSTactile_CraneFBPort);
//	}

	/****************************************************************
	 * Generic correction functions
	 ****************************************************************/

	private static void convertInPortToOutPort(EObject root, String inPortName) {
		PortInstance inPort = null;
		for (Iterator<EObject> iterator = root.eAllContents(); iterator.hasNext();) {
			EObject node = iterator.next();
			if (node instanceof PortInstance) {
				PortInstance port = (PortInstance) node;
				if (port.getName().equals(inPortName)) {
					if (inPort == null) {
						inPort = port;
					} else {
						assert (false) : "Duplicate port " + inPortName;
					}
				}
			}
		}

		ComponentInstance component = (ComponentInstance) inPort.eContainer();
		if (component.getInPorts().contains(inPort)) {
			System.out.println("Convert " + inPortName + " from inPort to outPort");
			component.getInPorts().remove(inPort);
			component.getOutPorts().add(inPort);
		}
	}

	/****************************************************************
	 * Load and save
	 ****************************************************************/

	private static void save(EObject root) {
		// now save the content.
		try {
			root.eResource().save(Collections.EMPTY_MAP);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static EObject load(String pathName) {
		// Register the XMI resource factory for the .sa extension
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("sa", new XMIResourceFactoryImpl());

		// Obtain a new resource set
		ResourceSet resSet = new ResourceSetImpl();

		// Get the resource
		Resource resource = resSet.getResource(URI.createFileURI(pathName), true);
		EObject root = resource.getContents().get(0);
		return root;
	}

}
