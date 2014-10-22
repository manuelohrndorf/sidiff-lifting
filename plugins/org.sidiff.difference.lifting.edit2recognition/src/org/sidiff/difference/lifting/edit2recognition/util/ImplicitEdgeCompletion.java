package org.sidiff.difference.lifting.edit2recognition.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.henshin.HenshinModuleAnalysis;

/**
 * Creates all missing opposite edges in the module.
 * 
 * @author Manuel Ohrndorf
 */
public class ImplicitEdgeCompletion {
	
	/**
	 * Remembers all added implicit edges
	 */
	private List<Edge> implicitEdges;
	
	/**
	 * Creates all missing opposite edges in the module.
	 * 
	 * @param editModule
	 *            The edit rule module to process.
	 */
	public void createImplicitEdges(Module editModule) {
		implicitEdges = new ArrayList<Edge>();
		
		for (Rule editRule : HenshinModuleAnalysis.getAllRules(editModule)) {
			createImplicitEdges(editRule.getLhs().getEdges());
			createImplicitEdges(editRule.getRhs().getEdges());
		}
	}
	
	/**
	 * Create the implicit opposite edges in the edit rule which were not defined before.
	 * 
	 * @param edges
	 *            the list of edges which will be searched for undefined opposite edges.
	 */
	private void createImplicitEdges(List<Edge> edges) {
		List<Edge> oppositeEdges = new LinkedList<Edge>();
		
		for (Edge edge : edges) {
			// Check if edge (EReference type) has opposite
			if (edge.getType().getEOpposite() != null) {
				// Look for existing opposite edge
				boolean oppositeExists = false;
				
				for (Edge opposite : edge.getTarget().getOutgoing()) {
					if ((opposite.getType() == edge.getType().getEOpposite()) && (opposite.getTarget() == edge.getSource())) {
						oppositeExists = true;
					}
				}
				
				// If opposite do not exist
				if (!oppositeExists) {
					// Create new edge (unlinked to graph)
					Edge oppositeEdge = HenshinFactory.eINSTANCE.createEdge();
					oppositeEdge.setSource(edge.getTarget());
					oppositeEdge.setTarget(edge.getSource());
					oppositeEdge.setType(edge.getType().getEOpposite());
					oppositeEdges.add(oppositeEdge);
				}
			}
		}
		
		// Remember edges
		implicitEdges.addAll(oppositeEdges);
		
		// Link new edges to graph
		edges.addAll(oppositeEdges);
	}
	
	/**
	 * Delete all added implicit edges
	 */
	public void deleteImplicitEdges() {
		for (Edge edge : implicitEdges) {
			EcoreUtil.remove(edge);
			edge.getSource().getOutgoing().remove(edge);
			edge.getTarget().getIncoming().remove(edge);
		}
		
		implicitEdges = null;
	}
}
