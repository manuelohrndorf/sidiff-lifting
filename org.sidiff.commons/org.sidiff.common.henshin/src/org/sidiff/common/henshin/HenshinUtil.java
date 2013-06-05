package org.sidiff.common.henshin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.henshin.matching.EmfGraph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;

/**
 * Some common Henshin Utils, mostly used for DEBUGGING purposes.
 * 
 * @author kehrer
 */
public class HenshinUtil {

	/**
	 * Only for DEBUGGING purposes. Prints the contents of a Henshin graph.
	 * 
	 * @param graph
	 */
	public static void printGraph(EmfGraph graph) {
		System.out.println("-----------------");
		for (EObject eObject : graph.geteObjects()) {
			System.out.println(eObject);
		}
		System.out.println("-----------------");
	}

	/**
	 * Only for DEBUGGING purposes. Serializes a temporary Henshin rule to the given path.
	 * 
	 * @param rule
	 * @param path
	 */
	public static void serializeTempRule(Rule rule, String path) {
		TransformationSystem newTS = HenshinFactory.eINSTANCE.createTransformationSystem();
		newTS.getRules().add(rule);

		URI uri = URI.createFileURI(new File(path).getAbsolutePath() + "/temp.henshin");
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = resourceSet.createResource(uri);
		resource.getContents().add((EObject) newTS);

		Map<String, Boolean> options = new HashMap<String, Boolean>();
		options.put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);

		try {
			resource.save(options);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
