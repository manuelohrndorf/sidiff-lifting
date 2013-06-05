package org.sidiff.common.henshin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.EGraph;

public class EGraphUtil {
	
	/**
	 * Returns all eObjects contained in an Henshin graph
	 * 
	 * @param graph
	 * @return
	 */
	public static Collection<EObject> getEObjects(EGraph graph){
		Collection<EObject> eObjects = new ArrayList<EObject>();
		for (EObject root : graph.getRoots()) {
			for (Iterator<EObject> iterator = root.eAllContents(); iterator.hasNext();) {
				eObjects.add(iterator.next());	
			}
		}
		
		return eObjects;
	}
}
