package org.sidiff.slicing.util.visualization;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
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

	private Map<EObject, String> obj2ID;
	private Set<Node> nodes;
	private Set<Edge> edges;

	private GraphUtil() {
		this.obj2ID = new HashMap<>();
		this.nodes = new HashSet<>();
		this.edges = new HashSet<>();
	}

	public void addNode(EObject eObject) {
		nodes.add(new Node(eObject));
		obj2ID.put(eObject, "ID" + Integer.toHexString(eObject.hashCode()));
	}

	public void addEdge(EReference type, EObject src, EObject tgt) {
		edges.add(new Edge(type.getName(), src, tgt));
	}

	public void addEdge(String label, EObject src, EObject tgt) {
		edges.add(new Edge(label, src, tgt));
	}

	public String getOutput() {
		StringBuilder sb = new StringBuilder();
		sb.append("digraph{\n");
		sb.append("{style = filled\n");
		sb.append("shape = box\n");
		sb.append("}\n");
		for(Node node : nodes){
			sb.append(node.output(obj2ID));
		}
		for(Edge edge : edges){
			sb.append(edge.output(obj2ID));
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
	private static class Node {

		private EObject eObject;
		private Color color = Color.white;
		private NodeStyle style = NodeStyle.solid;

		public Node(EObject eObject) {
			this.eObject = eObject;
		}

		private String getLabel() {
			return GraphUtil.getLabel(eObject);
		}

		public String output(Map<EObject,String> obj2ID) {
			return "\"" + obj2ID.get(eObject) + "\"" + " [label= \"" + getLabel() + "\" style=" + style + " fillcolor = " + color+ "]\n";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((color == null) ? 0 : color.hashCode());
			result = prime * result + ((eObject == null) ? 0 : eObject.hashCode());
			result = prime * result + ((style == null) ? 0 : style.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null || getClass() != obj.getClass())
				return false;
			Node other = (Node)obj;
			return Objects.equals(eObject, other.eObject)
					&& Objects.equals(color, other.color)
					&& Objects.equals(style, other.style);
		}
	}

	private static class Edge {
		private EObject src;
		private EObject tgt;
		private String label;

		private Color color = Color.black;
		private EdgeStyle style = EdgeStyle.solid;

		public Edge(String label, EObject src, EObject tgt) {
			this.label = label;
			this.src = src;
			this.tgt = tgt;
		}

		public String output(Map<EObject,String> obj2ID) {
			return obj2ID.get(src) + " -> " + obj2ID.get(tgt) + " [label = \"" + label + "\" style = " + style + " color = " + color + " ]\n";
		}

		@Override
		public int hashCode() {
			final int prime = 17;
			int result = 1;
			result = prime * result + ((color == null) ? 0 : color.hashCode());
			result = prime * result + ((label == null) ? 0 : label.hashCode());
			result = prime * result + ((src == null) ? 0 : src.hashCode());
			result = prime * result + ((style == null) ? 0 : style.hashCode());
			result = prime * result + ((tgt == null) ? 0 : tgt.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null || getClass() != obj.getClass())
				return false;
			Edge other = (Edge)obj;
			return Objects.equals(src, other.src)
					&& Objects.equals(tgt, other.tgt)
					&& Objects.equals(label, other.label)
					&& Objects.equals(color, other.color)
					&& Objects.equals(style, other.style);
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
