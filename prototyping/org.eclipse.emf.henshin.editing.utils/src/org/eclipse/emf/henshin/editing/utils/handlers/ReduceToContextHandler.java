package org.eclipse.emf.henshin.editing.utils.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.editing.utils.util.EMFHandlerUtil;
import org.eclipse.emf.henshin.model.Action.Type;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;

/**
 * Removes all non-context (preserve) nodes from an edit rule.
 * 
 * @author Manuel Ohrndorf, Timo Kehrer
 */
public class ReduceToContextHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Module module = EMFHandlerUtil.getSelection(event, Module.class);

		if (module != null) {

			// We identify all non-context nodes and <<preserve>> edges
			List<Node> nonContextNodes = new ArrayList<>();
			List<Edge> preserveEdges = new ArrayList<>();
			for (Iterator<EObject> iterator = module.eAllContents(); iterator.hasNext();) {
				EObject element = iterator.next();

				// Nodes
				if (element instanceof Node) {
					Node node = (Node) element;

					if (node.getActionNode().getAction().getType().equals(Type.PRESERVE)) {
						if (node.getActionNode() != node) {
							Node lhsNode = node.getActionNode();
							Node rhsNode = node;

							if (!isNodeWithActionEdges(rhsNode, Type.CREATE)
									&& !isNodeWithActionEdges(lhsNode, Type.DELETE)
									&& !isNodeWithChangingAttributes(lhsNode, rhsNode)) {

								nonContextNodes.add(lhsNode);
								nonContextNodes.add(rhsNode);
							}
						}
					}
				}

				// Edges
				if (element instanceof Edge) {
					Edge edge = (Edge) element;

					if (edge.getActionEdge().getAction().getType().equals(Type.PRESERVE)) {
						if (edge.getActionEdge() != edge) {
							Edge lhsEdge = edge.getActionEdge();
							Edge rhsEdge = edge;

							preserveEdges.add(lhsEdge);
							preserveEdges.add(rhsEdge);
						}
					}
				}

			}

			// Then, we delete all the identified elements
			for (Edge edge : preserveEdges) {
				deleteEdge(edge);
			}
			for (Node node : nonContextNodes) {
				deleteNode(node);
			}

			try {
				String fileName = module.eResource().getURI().lastSegment().replace(".henshin", "") + "_reduced";
				URI uri = module.eResource().getURI().trimSegments(1).appendSegment(fileName)
						.appendFileExtension("henshin");
				Resource res = new ResourceSetImpl().createResource(uri);
				res.getContents().add(module);
				res.save(Collections.EMPTY_MAP);

			} catch (IOException e) {
				e.printStackTrace();
			}			
		}

		return null;
	}

	/**
	 * @param node
	 *            The node which will be deleted from its graph.
	 */
	private void deleteNode(Node node) {
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
	private void deleteEdge(Edge edge) {
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
	private boolean isNodeWithActionEdges(Node node, Type actionType) {

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
	private boolean isNodeWithChangingAttributes(Node lhsNode, Node rhsNode) {

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
