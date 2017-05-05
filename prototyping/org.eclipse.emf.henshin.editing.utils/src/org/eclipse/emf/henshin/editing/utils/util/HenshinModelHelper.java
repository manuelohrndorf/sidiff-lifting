package org.eclipse.emf.henshin.editing.utils.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Action.Type;

/**
 * Utility methods for analyzing Henshin Modules, Units and Rules.
 * 
 * @author Timo Kehrer, Manuel Ohrndorf
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

	public static Set<EClass> getRequiredTypes(Node node) {
		Set<EClass> types = new HashSet<>();

		for (Edge ougoing : node.getOutgoing()) {
			types.add(ougoing.getType().getEContainingClass());
		}

		for (Edge incoming : node.getIncoming()) {
			types.add(incoming.getType().getEReferenceType());
		}

		for (Attribute attribute : node.getAttributes()) {
			types.add(attribute.getType().getEContainingClass());
		}

		return types;
	}

	public static EClass selectMostSpecificType(Collection<EClass> types) {

		if (!types.isEmpty()) {
			EClass mostSpecific = types.iterator().next();

			for (EClass type : types) {
				if (type.getEAllSuperTypes().contains(mostSpecific)) {
					mostSpecific = type;
				}
			}

			return mostSpecific;
		}

		return EcorePackage.eINSTANCE.getEObject();
	}
	
	/**
	 * @param node
	 *            The node which will be deleted from its graph.
	 */
	public static void deleteNode(Node node) {
		// Clean-up mappings:
		node.getGraph().getRule().getMappings().stream()
				.filter(m -> ((m.getOrigin() == node) || (m.getImage() == node))).findFirst()
				.ifPresent(m -> EcoreUtil.remove(m));

		// Remove node from graph:
		EcoreUtil.remove(node);
	}

	/**
	 * @param edge
	 *            The edge which will be deleted from its graph.
	 */
	public static void deleteEdge(Edge edge) {
		EcoreUtil.remove(edge);
		edge.getSource().getOutgoing().remove(edge);
		edge.getTarget().getIncoming().remove(edge);
	}

	/**
	 * Searches for the first incoming or outgoing edge of the given node having
	 * the given actionType. Returns <code> true </code> if there is such an
	 * edge; <code> false </code> otherwise.
	 * 
	 * @param node
	 *            the node to test.
	 * @return <code> true </code> if there is an edge with the given
	 *         actionType; <code> false </code> otherwise.
	 */
	public static boolean isNodeWithActionEdges(Node node, Type actionType) {

		for (Edge edge : node.getIncoming()) {
			if (edge.getActionEdge().getAction().getType().equals(actionType)) {
				return true;
			}
		}

		for (Edge edge : node.getOutgoing()) {
			if (edge.getActionEdge().getAction().getType().equals(actionType)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Searches for (X -> Y) attributes in a << preserve >> node
	 * (lhsNode/rhsNode pair).
	 * 
	 * Returns <code> true </code> if there is a (X -> Y) attribute, false
	 * otherwise
	 * 
	 */
	public static boolean isNodeWithChangingAttributes(Node lhsNode, Node rhsNode) {

		for (Attribute lhsAttribute : lhsNode.getAttributes()) {
			Attribute rhsAttribute = rhsNode.getAttribute(lhsAttribute.getType());
			if (rhsAttribute == null || !rhsAttribute.getValue().equals(lhsAttribute.getValue())) {
				return true;
			}
		}

		for (Attribute rhsAttribute : rhsNode.getAttributes()) {
			Attribute lhsAttribute = lhsNode.getAttribute(rhsAttribute.getType());
			if (lhsAttribute == null || !lhsAttribute.getValue().equals(rhsAttribute.getValue())) {
				return true;
			}
		}

		return false;
	}
}