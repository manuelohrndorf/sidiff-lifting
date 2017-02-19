package org.eclipse.emf.henshin.editing.utils.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;


/**
 * Utility methods for analyzing Henshin Modules, Units and Rules.
 */
public class HenshinModelHelper {

	public static Set<EPackage> calculateImports(Module module) {
		Set<EPackage> imports = new HashSet<>();

		module.eAllContents().forEachRemaining(element -> {
			if (element instanceof Node) {
				imports.add(((Node) element).getType().getEPackage());
			}
		});

		return imports;
	}
	
	/**
	 * Returns whether the node is << delete >> or not.
	 * 
	 * @param node
	 *            the node to test.
	 * @return true if node is << delete >>; false otherwise.
	 */
	public static boolean isDeletionNode(Node node) {

		// Load node container
		Object container = node.getGraph().eContainer();

		// Container must be a Rule
		if (!(container instanceof Rule)) {
			return false;
		}

		// Rule that contains the node
		Rule rule = (Rule) container;

		for (Mapping mapping : rule.getMappings()) {

			// Node is preserve node
			if (mapping.getOrigin() == node) {
				return false;
			}
		}

		// Node is delete node
		if (rule.getLhs() == node.getGraph()) {
			return true;
		}

		// Node is create node
		return false;
	}
	
	/**
	 * Returns whether the node is << preserve >> or not.
	 * 
	 * @param node
	 *            the node to test.
	 * @return true if node is << preserve >>; false otherwise.
	 */
	public static boolean isPreservedNode(Node node) {

		// Load node container
		Object container = node.getGraph().eContainer();

		// Container must be a Rule
		if (!(container instanceof Rule)) {
			return false;
		}

		// Rule that contains the node
		Rule rule = (Rule) container;

		for (Mapping mapping : rule.getMappings()) {

			// Node is preserve node
			if ((mapping.getOrigin() == node) || (mapping.getImage() == node)) {
				return true;
			}
		}

		// Node is not a preserve node
		return false;
	}
	
	/**
	 * Returns the image or origin of the specified node. If the node is not
	 * part of a mapping, null will be returned. If the node is part of multiple
	 * mappings, only the first remote node is returned.
	 * 
	 * @param mappings
	 * @param node
	 * @return
	 */
	public static Node getRemoteNode(Collection<Mapping> mappings, Node node) {
		for (Mapping mapping : mappings) {
			if (mapping.getOrigin() == node) {
				return mapping.getImage();
			}
			if (mapping.getImage() == node) {
				return mapping.getOrigin();
			}
		}
		
		return null;
	}
	
	/**
	 * Searches for the first incoming or outgoing << delete >> edge of the given node. Returns
	 * <code> true </code> if there is a << delete >> edge; <code> false </code> otherwise.
	 * 
	 * @param node
	 *            the node to test.
	 * @return <code> true </code> if there is a << delete >> edge; <code> false </code> otherwise.
	 */
	public static boolean isNodeWithDeletionEdges(Node node) {

		for (Edge edge : node.getIncoming()) {
			if (isDeletionEdge(edge)) {
				return true;
			}
		}
		
		for (Edge edge : node.getOutgoing()) {
			if (isDeletionEdge(edge)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Checks if the given edge represents a 'deletion' edge. This is the case,
	 * if it is contained in a LHS and if there is no corresponding image edge
	 * in the RHS.<br>
	 * 
	 * @param edge
	 * @return true if the edge could be identified to be a 'deletion' edge. In
	 *         every other case this method returns false.
	 */
	public static boolean isDeletionEdge(Edge edge) {
		if (edge.getSource() != null && edge.getTarget() != null && edge.getGraph() != null
				&& edge.getGraph().getRule() != null) {
			Rule rule = edge.getGraph().getRule();
			return edge.getGraph().isLhs()
					&& (getEdgeImage(edge, rule.getRhs(), rule.getMappings()) == null);
		} else {
			return false;
		}
	}
	
	/**
	 * Find the image of an edge.
	 * @param edge Origin edge.
	 * @param targetGraph Graph the sought image is contained in
	 * @param mappings Mappings.
	 * @return Edge image.
	 */
	public static Edge getEdgeImage(Edge edge, Graph targetGraph,
			List<Mapping> mappings) {
		if (edge.getSource() == null || edge.getTarget() == null) {
			return null;
		}
		Node source = getNodeImage(edge.getSource(), targetGraph, mappings);
		Node target = getNodeImage(edge.getTarget(), targetGraph, mappings);
		if (source == null || target == null) {
			return null;
		}
		return source.getOutgoing(edge.getType(), target);
	}
	
	/**
	 * Find the image of a node with respect to a target graph and a list of
	 * mappings.
	 * @param origin Origin node.
	 * @param targetGraph Target graph.
	 * @param mappings Mappings.
	 * @return The image of the node.
	 */
	public static Node getNodeImage(Node origin, Graph targetGraph,
			List<Mapping> mappings) {
		Mapping mapping = getNodeImageMapping(origin, targetGraph, mappings);
		return (mapping != null) ? mapping.getImage() : null;
	}
	
	/**
	 * Find a corresponding mapping for a given origin nodes and target graph.
	 * @param origin Origin node.
	 * @param targetGraph Target graph.
	 * @param mappings Mappings.
	 * @return Mapping if found, <code>null</code> otherwise.
	 */
	public static Mapping getNodeImageMapping(Node origin, Graph targetGraph,
			List<Mapping> mappings) {
		for (Mapping mapping : mappings) {
			if (mapping.getOrigin() == origin
					&& mapping.getImage().getGraph() == targetGraph) {
				return mapping;
			}
		}
		return null;
	}
	
	/**
	 * Searches for the first incoming or outgoing << create >> edge of the given node. Returns
	 * <code> true </code> if there is a << create >> edge; <code> false </code> otherwise.
	 * 
	 * @param node
	 *            the node to test.
	 * @return <code> true </code> if there is a << create >> edge; <code> false </code> otherwise.
	 */
	public static boolean isNodeWithCreationEdges(Node node) {

		for (Edge edge : node.getIncoming()) {
			if (isCreationEdge(edge)) {
				return true;
			}
		}
		
		for (Edge edge : node.getOutgoing()) {
			if (isCreationEdge(edge)) {
				return true;
			}
		}

		return false;
	}
	
	/**
	 * Checks if the given edge represents a 'creation' edge. This is the case,
	 * if it is contained in a RHS and if there is no corresponding origin edge
	 * in the LHS.
	 * 
	 * @param edge
	 * @return true if the edge could be identified to be a 'creation' edge. In
	 *         every other case this method returns false.
	 */
	public static boolean isCreationEdge(Edge edge) {
		if (edge.getSource() != null && edge.getTarget() != null && edge.getGraph() != null
				&& edge.getGraph().getRule() != null) {
			Rule rule = edge.getGraph().getRule();
			return edge.getGraph().isRhs()
					&& (getEdgeOrigin(edge, rule.getMappings()) == null);
		} else {
			return false;
		}
	}
	
	/**
	 * Find the origin of an edge.
	 * @param edge Image edge.
	 * @param mappings Mappings.
	 * @return Edge image.
	 */
	public static Edge getEdgeOrigin(Edge edge, List<Mapping> mappings) {
		if (edge.getSource() == null || edge.getTarget() == null) {
			return null;
		}
		Node source = getNodeOrigin(edge.getSource(), mappings);
		Node target = getNodeOrigin(edge.getTarget(), mappings);
		if (source == null || target == null) {
			return null;
		}
		return source.getOutgoing(edge.getType(), target);
	}
	
	/**
	 * Find the origin of a node with respect to a list of mappings.
	 * @param image Image node.
	 * @param target Target graph.
	 * @param mappings Mappings.
	 * @return The image of the node.
	 */
	public static Node getNodeOrigin(Node image, List<Mapping> mappings) {
		Mapping mapping = getNodeOriginMapping(image, mappings);
		return (mapping != null) ? mapping.getOrigin() : null;
	}
	
	/**
	 * Find the corresponding mapping for a given image node.
	 * @param image Image node.
	 * @param mappings Mappings.
	 * @return Mapping if found, <code>null</code> otherwise.
	 */
	public static Mapping getNodeOriginMapping(Node image,
			List<Mapping> mappings) {
		for (Mapping mapping : mappings) {
			if (mapping.getImage() == image) {
				return mapping;
			}
		}
		return null;
	}
	
	/**
	 * Searches for (X -> Y) attributes in a << preserve >> node.
	 * 
	 * @param node
	 *            the node to test.
	 * @return <code> true </code> if there is a (X -> Y) attribute;
	 *         <code> false </code> otherwise.
	 */
	public static boolean isNodeWithChangingAttributes(Node node) {
		
		for (Attribute attribute : node.getAttributes()) {
			if (isChangingAttribute(attribute)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Is the given attribute a << preserve >> attribute and LHS/RHS differs in value.
	 * 
	 * @param attribute
	 *            the attribute to test.
	 * @return <code>true</code> if the attribute is a << preserve >> attribute
	 *         and LHS/RHS differs in value; <code>false</code> otherwise.
	 */
	public static boolean isChangingAttribute(Attribute attribute){
		if(isPreservedNode(attribute.getNode())){
			Attribute remoteAttribute = getRemoteAttribute(attribute);
			if(remoteAttribute != null){
				if(!remoteAttribute.getValue().equals(attribute.getValue())){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Returns the LHS/RHS attribute corresponding to the RHS/LHS attribute.
	 * 
	 * @param attribute
	 *            the known attribute.
	 * @return the corresponding remote attribute or null if it not exists.
	 */
	public static Attribute getRemoteAttribute(Attribute attribute) {
		
		if (attribute.getNode().getGraph().getRule() == null) {
			return null;
		}
		
		Node node = attribute.getNode();
		Node remoteNode = getRemoteNode(node.getGraph().getRule().getMappings(), node);

		if ((node != null) && (remoteNode != null)) {
			for (Attribute remoteAttribute : remoteNode.getAttributes()) {
				if (remoteAttribute.getType() == attribute.getType()) {
					return remoteAttribute;
				}
			}
		}

		return null;
	}
}