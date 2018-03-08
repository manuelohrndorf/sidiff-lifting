package org.sidiff.slicing.util.visualization;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

public class GraphUtil {

	private static Map<Object,GraphUtil> instances;

	public static GraphUtil get(Object context)
	{
		if(instances == null) {
			instances = new HashMap<>();
		}
		if(!instances.containsKey(context)) {
			instances.put(context, new GraphUtil());
		}
		return instances.get(context);
	}

	private StringBuffer sb;
	private Map<EObject, String> obj2ID;
	private Set<Node> nodes;
	private Set<Edge> edges;

	private GraphUtil() {
		this.sb = new StringBuffer();
		this.obj2ID = new HashMap<>();
		this.nodes = new HashSet<>();
		this.edges = new HashSet<>();
	}

	public void addNode(EObject eObject) {
		nodes.add(new Node(eObject));
		obj2ID.put(eObject, "ID" + Integer.toHexString(eObject.hashCode()));
	}

	public void addEdge(EReference type, EObject src, EObject tgt) {
		edges.add(new Edge(type, src, tgt));
	}

	public String getOutput() {
		sb.append("digraph{\n");
		sb.append("{style = filled\n");
		sb.append("shape = box\n");
		sb.append("}\n");
		for(Node node : nodes){
			sb.append(node);
		}
		for(Edge edge : edges){
			sb.append(edge);
		}
		sb.append("}");
		return sb.toString();
	}

	private static String getLabel(EObject eObject) {
		String label = "";
		if(eObject.eClass().getEStructuralFeature("name") != null) {
			label += eObject.eGet(eObject.eClass().getEStructuralFeature("name"));
		}
		label += ": " + eObject.eClass().getName();
		return label;
	}

	// ############# inner classes ##############
	private class Node {
		private EObject eObject;
		private Color color = Color.white;
		private NodeStyle style = NodeStyle.solid;

		public Node(EObject eObject) {
			this.eObject = eObject;
		}

		private String getLabel() {
			return GraphUtil.getLabel(eObject);
		}

		@Override
		public String toString() {
			return "\"" + obj2ID.get(eObject) + "\"" + " [label= \"" + getLabel() + "\" style=" + style + " fillcolor = " + color+ "]\n";
		}
	}

	private class Edge {
		private EObject src;
		private EObject tgt;
		private EReference type;

		private Color color = Color.black;
		private EdgeStyle style = EdgeStyle.solid;

		public Edge(EReference type, EObject src, EObject tgt) {
			this.type = type;
			 this.src = src;
			 this.tgt = tgt;
		}

		public String getLabel() {
			return type.getName();
		}

		public String toString() {
			return obj2ID.get(src) + " -> " + obj2ID.get(tgt) + " [label = " + getLabel() + " style = " + style + " color = " + color + " ]\n";
		}
	}

	// ############# inner enums ##############
	private enum NodeStyle {
		solid, dashed, dotted, bold, rounded, 
		diagonals, filled, striped, wedged
	}

	private enum EdgeStyle {
		solid, dashed, dotted, bold
	}

	private enum Color {
		white, black, red, green, blue,
		gray, gray10, gray20, gray30, gray40, 
		gray50, gray60, gray70, gray80, gray90,
		magenta, cyan, yellow
	}
}
