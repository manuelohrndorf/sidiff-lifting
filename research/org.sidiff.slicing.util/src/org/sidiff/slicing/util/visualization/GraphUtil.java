package org.sidiff.slicing.util.visualization;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.util.StringUtil;

public class GraphUtil {

	private static StringBuffer sb = new StringBuffer();
	
	private static Map<EObject, Integer> contextNodes = new HashMap<EObject, Integer>();
	private static Map<EObject, Integer> boundaryNodes = new HashMap<EObject, Integer>();
	
	
	public static void addContextNode(EObject eObject, int radius){
		contextNodes.put(eObject, radius);
	}
	
	public static void addBoundaryNode(EObject eObject, int radius){
		boundaryNodes.put(eObject, radius);
	}
	
	public static void deriveEdges(){
		Set<EObject> nodes = new HashSet<EObject>();
		nodes.addAll(contextNodes.keySet());
		nodes.addAll(boundaryNodes.keySet());
		for(EObject eObject: nodes){
			String id_src = "\"" + StringUtil.resolve(eObject) + "\"";
			for(EReference eReference : eObject.eClass().getEAllReferences()){
				List<EObject> targets = EMFModelAccess.getNodeNeighbors(eObject, eReference);
				for (EObject target :targets) {
					String style = "solid";
					String fillcolor = "black";
					if(boundaryNodes.containsKey(eObject) && boundaryNodes.containsKey(target)){
						style = "dashed";
					}else if((contextNodes.containsKey(eObject) && boundaryNodes.containsKey(target))
							|| (contextNodes.containsKey(target) && boundaryNodes.containsKey(eObject))){
						fillcolor = "red";
					}
					String id_tgt = "\""+ StringUtil.resolve(target) + "\"";
					sb.append(id_src + " -> " + id_tgt + " [label = " + eReference.getName() + " style = " + style + " color = " + fillcolor + " fillcolor = " + fillcolor + " ]\n");
				}
				
			}
		}
	}
	

	public static String getOutput(){
		sb.append("digraph{\n");
		sb.append("{style = filled\n");
		sb.append("shape = box\n");
		sb.append("}\n");
		for(EObject eObject : contextNodes.keySet()){
			String id = StringUtil.resolve(eObject);
			sb.append("\"" + id + "\"" + getNodeStyle(eObject, contextNodes.get(eObject)) + "\n");
		}
		for(EObject eObject : boundaryNodes.keySet()){
			String id = StringUtil.resolve(eObject);
			sb.append("\"" + id + "\"" + getNodeStyle(eObject, boundaryNodes.get(eObject)) + "\n");
		}
		deriveEdges();
		sb.append("}");
		return sb.toString();
	}
	
	
	private static String getNodeStyle(EObject eObject, int i){
		String label = "";
		if(eObject.eClass().getEStructuralFeature("name")!=null){
			label += eObject.eGet(eObject.eClass().getEStructuralFeature("name"));
		}
		label += ":" + eObject.eClass().getName();
		
		String style = "filled";
		if(boundaryNodes.keySet().contains(eObject)){
			style = "dashed";
		}
		String fillcolor = "gray" + (i+2)*10;
		return " [label= \"" + label + "\" style=" + style + " fillcolor = " + fillcolor + "]";
	}
}
