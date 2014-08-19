package org.sidiff.difference.lifting.edit2recognition;

import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.copyNode;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.createEdge;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.createNode;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.findMappingByImage;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.getAttributes;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.*;
import static org.sidiff.common.henshin.HenshinConditionAnalysis.*;

import java.util.List;

import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Not;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;
import org.sidiff.common.henshin.NodePair;
import org.sidiff.difference.lifting.edit2recognition.exceptions.UnsupportedApplicationConditionException;
import org.sidiff.difference.lifting.edit2recognition.traces.ACContextNodePattern;
import org.sidiff.difference.lifting.edit2recognition.traces.ACExtensionPattern;
import org.sidiff.difference.lifting.edit2recognition.traces.ACObjectPattern;
import org.sidiff.difference.lifting.edit2recognition.traces.ACReferencePattern;
import org.sidiff.difference.lifting.edit2recognition.traces.TransformationPatterns;
import org.sidiff.difference.symmetric.SymmetricPackage;

/**
 * Transforms a single Negative-Application-Condition (NAC) or Positive-Application-Condition (PAC)
 * formula.
 * 
 * @author Manuel Ohrndorf
 */
public class EditCondition2RecognitionCondition {
	
	/* Key:
	 *  er_				-> Edit-Rule
	 * rr_				-> Recognition-Rule
	 * _ac_ 			-> Application Condition
	 * _ac_context_ 	-> A AC node of a nested graph that is mapped to a parent (LHS) graph node.
	 * _context_		-> The node to which a 'context NAC/PAC node' is mapped.
	 */

	/**
	 * The generated transformation patterns and traces.
	 */
	private TransformationPatterns patterns;
	
	/**
	 * The nested condition of the edit rule.
	 */
	private NestedCondition editCondition;
	
	/**
	 * The edit rule graph which will be transformed.
	 */
	private Graph editGraph;
	
	/**
	 * The nested condition of the recognition rule.
	 */
	private NestedCondition recognitionCondition;
	
	/**
	 * The transformed recognition rule graph.
	 */
	private Graph recognitionGraph;
	
	/**
	 * Transforms a single Negative-Application-Condition (NAC) or Positive-Application-Condition
	 * (PAC) formula.
	 * 
	 * @param editRuleformula
	 *            The nested PAC/NAC formula.
	 * @param patterns
	 *            The transformation patterns of the containing graph.
	 * @return The transformed PAC/NAC recognition rule formula.
	 * @throws UnsupportedApplicationConditionException
	 */
	public Formula transform(Formula editRuleformula, TransformationPatterns patterns) 
			throws UnsupportedApplicationConditionException {
		
		this.patterns = patterns;
		
		if (editRuleformula instanceof Not) {
			return transformNAC(editRuleformula);
		} else  {
			return transformPAC(editRuleformula);
		}
	}
	
	/**
	 * Transforms a single Negative-Application-Condition (NAC) formula.
	 * 
	 * @param er_formula
	 *            The nested NAC formula.
	 * @return The transformed NAC recognition rule formula.
	 * @throws UnsupportedApplicationConditionException
	 */
	private Formula transformNAC(Formula er_formula) 
			throws UnsupportedApplicationConditionException {
		
		Formula er_not_child = ((Not) er_formula).getChild();
		
		if (er_not_child instanceof NestedCondition) {
			editCondition = (NestedCondition) er_not_child;
			editGraph = editCondition.getConclusion();
			
			recognitionGraph = HenshinFactory.eINSTANCE.createGraph(editGraph.getName());
			recognitionCondition = HenshinFactory.eINSTANCE.createNestedCondition();
			recognitionCondition.setConclusion(recognitionGraph);

			Not not = HenshinFactory.eINSTANCE.createNot();
			not.setChild(recognitionCondition);

			createPatterns();
			
			return not;	
		} else {
			throw new UnsupportedApplicationConditionException(er_formula);
		}
	}
	
	/**
	 * Transforms a single Positive-Application-Condition (PAC) formula.
	 * 
	 * @param er_formula
	 *            The nested PAC formula.
	 * @return The transformed PAC recognition rule formula.
	 * @throws UnsupportedApplicationConditionException
	 */
	private Formula transformPAC(Formula er_formula) 
			throws UnsupportedApplicationConditionException {
		
		if (er_formula instanceof NestedCondition) {
			editCondition = (NestedCondition) er_formula;
			editGraph = editCondition.getConclusion();
			
			recognitionGraph = HenshinFactory.eINSTANCE.createGraph(editGraph.getName());
			recognitionCondition = HenshinFactory.eINSTANCE.createNestedCondition();
			recognitionCondition.setConclusion(recognitionGraph);
			
			createPatterns();
			
			return recognitionCondition;	
		}  else {
			throw new UnsupportedApplicationConditionException(er_formula);
		}
	}
	
	/**
	 * Create all NAC/PAC patters.
	 */
	private void createPatterns() {
		
		if (checkForPreservedGlueNodes()) {
			createACObjectPattern();
			createACContextNodePattern();
			createACReferencePattern();
			createACAttributePattern();
		} else {
			return; // => Dummy AC
		}
	}
	
	/**
	 * Checks whether the AC graph is not incident with a << delete >> / << create >> node.
	 * 
	 * @return <code>true</code> if all glue nodes are preserve nodes; <code>false</code> otherwise.
	 */
	private boolean checkForPreservedGlueNodes() {

		// Edit rule AC nodes:
		for (Node er_ac_context_node : editGraph.getNodes()) {
			// Catch context (glue) nodes:
			Node er_context_node = getRemoteNode(editCondition.getMappings(), er_ac_context_node);

			if ((er_context_node != null) && isACGlueNode(er_ac_context_node)) {
				// Is glue node NOT a preserved node?
				if (!isPreservedNode(er_context_node)) {
					return false;
				}
			}
		}

		return true;
	}
	
	/**
	 * Creates the AC object pattern. Which means the function copies the NAC/PAC node from
	 * the edit rule to the recognition rule.
	 */
	private void createACObjectPattern() {

		// Edit rule AC nodes:
		for (Node er_ac_node : editGraph.getNodes()) {
			// Filter context nodes:
			if (!HenshinRuleAnalysisUtilEx.isNodeMapped(editCondition.getMappings(), er_ac_node)) {
				// B-side node
				Node node_b = copyNode(recognitionGraph, er_ac_node, false);

				// Save transformation pattern
				ACObjectPattern ACObjectPattern = new ACObjectPattern(node_b, er_ac_node);
				patterns.addACObjectPattern(ACObjectPattern);
			}
		}
	}
	
	/**
	 * Creates the context of the Recognition-Rule NAC/PAC. Creates a NAC/PAC correspondence
	 * extension if needed.
	 * 
	 * <p> That means: LHS-A-Node -mapping-> AC-A-Node <- Correspondence -> AC-B-Node</p> 
	 */
	private void createACContextNodePattern() {
		
		// Edit rule AC nodes:
		for (Node er_ac_context_node : editGraph.getNodes()) {
			// Catch context (glue) nodes:
			Node er_context_node = getRemoteNode(editCondition.getMappings(), er_ac_context_node);
			
			if ((er_context_node != null) && isACGlueNode(er_ac_context_node)) {
				// Need AC-Extension?
				NodePair rr_context_node_b = patterns.getTraceB(er_context_node);
				Node rr_ac_context_node_b;
				
				if (rr_context_node_b == null) {
					// Create AC-Extension:
					rr_ac_context_node_b = createACExtensionPattern(er_context_node, er_ac_context_node);
				} else {
					// Create AC edge -> source context:
					rr_ac_context_node_b = createContextOfAC(rr_context_node_b.getLhsNode());		
				}
				
				// Save transformation pattern:
				ACContextNodePattern acContextNodePattern = new ACContextNodePattern(
						rr_ac_context_node_b, er_ac_context_node);
				patterns.addACContextNodePattern(acContextNodePattern);
			}
		}
	}
	
	/**
	 * Creates (if it nor already exists) a NAC/PAC correspondence pattern where model A node is the
	 * (mapped) context node.
	 * 
	 * @param er_context_node
	 *            The Edit-Rule LHS context node.
	 * @param er_ac_contect_node
	 *            The Edit-Rule nested condition context node.
	 * @return The model B node of the NAC/PAC correspondence pattern.
	 */
	private Node createACExtensionPattern(Node er_context_node, Node er_ac_contect_node) {

		/*
		 * NAC/PAC correspondence pattern
		 */

		if (patterns.getACExtensionPattern(er_ac_contect_node) != null) {
			// Pattern already exists.
			return patterns.getACExtensionPattern(er_context_node).getAc_node_b();
		} else {
			
			// Find trace of edit rule to recognition rule context node
			NodePair rr_context_node_a = patterns.getTraceA(er_context_node);

			assert (rr_context_node_a != null) : "Missing trace!";
			
			// Create new AC-Extension pattern.

			// NAC/PAC Correspondence
			Node rr_ac_correspondence = createNode(recognitionGraph,
					SymmetricPackage.eINSTANCE.getCorrespondence());

			// NAC/PAC model A context node
			Node rr_ac_context_node_a = createContextOfAC(rr_context_node_a.getLhsNode());

			// NAC/PAC model B node
			Node rr_ac_node_b = copyNode(recognitionGraph, rr_context_node_a.getLhsNode(), false);
			rr_ac_node_b.setName(rr_ac_node_b.getName().replace("A.", "B."));
			
			// NAC/PAC Correspondence -> NAC/PAC Edge -> Model A Node
			createEdge(rr_ac_correspondence, rr_ac_context_node_a,
					SymmetricPackage.eINSTANCE.getCorrespondence_ObjA(), recognitionGraph);

			// NAC/PAC Correspondence -> NAC/PAC Edge -> Model B Node
			createEdge(rr_ac_correspondence, rr_ac_node_b,
					SymmetricPackage.eINSTANCE.getCorrespondence_ObjB(), recognitionGraph);

			// Save transformation pattern
			ACExtensionPattern actExtensionPattern = new ACExtensionPattern(
					rr_context_node_a,
					rr_ac_context_node_a, rr_ac_node_b, rr_ac_correspondence,
					er_context_node, er_ac_contect_node);
			patterns.addACExtension(actExtensionPattern);

			return rr_ac_node_b;
		}
	}
	
	/**
	 * Creates a new node inside the NAC/PAC which is mapped to the given node of the parent graph.
	 * 
	 * @param lhsContext
	 *            The LHS node of the parent graph.
	 * @return The mapped NAC/PAC context node.
	 */
	private Node createContextOfAC(Node lhsContext) {

		// The mapped NAC/PAC context node
		Node ac_context = copyNode(recognitionGraph, lhsContext, false);

		Mapping contextMapping = HenshinFactory.eINSTANCE.createMapping();
		contextMapping.setOrigin(lhsContext);
		contextMapping.setImage(ac_context);
		
		// Add mapping to AC
		((NestedCondition) recognitionGraph.eContainer()).getMappings().add(contextMapping);

		return ac_context;
	}

	/**
	 * Creates the AC reference pattern. That means the function will create a NAC/PAC edge
	 * on the model B side of the recognition rule for each NAC/PAC edge in the edit rule. We
	 * presuppose that application conditions will not be violated after applying an edit
	 * operation. The function creates a AC-Extension if it is necessary.
	 * 
	 * @see EditCondition2RecognitionCondition#getACContextNode(Mapping)
	 * @see EditCondition2RecognitionCondition#createACExtensionPattern(Node)
	 * @see EditCondition2RecognitionCondition#createContextOfAC(Node)
	 */
	private void createACReferencePattern() {

		// Edit rule AC edges
		for (Edge er_ac_edge : editGraph.getEdges()) {

			// Mappings for context AC nodes
			List<Mapping> mappings = ((NestedCondition) er_ac_edge.getGraph().eContainer()).getMappings();
			Mapping er_ac_source_mapping = findMappingByImage(mappings, er_ac_edge.getSource());
			Mapping er_ac_target_mapping = findMappingByImage(mappings, er_ac_edge.getTarget());

			// Is edge mapped?
			if ((er_ac_source_mapping != null) && (er_ac_target_mapping != null)) {
				continue;
			}

			/*
			 * Recognition rule NAC/PAC source
			 */

			Node rr_ac_source = null;

			if (er_ac_source_mapping != null) {
				// Source of the AC edge is a AC context node!
				rr_ac_source = patterns.getContextTrace(er_ac_edge.getSource());
			} else {
				// Use existing AC-Object-Pattern
				rr_ac_source = patterns.getACObjectPattern(er_ac_edge.getSource()).getNode_b();
			}

			/*
			 * Recognition rule NAC/PAC target
			 */

			Node rr_ac_target = null;

			if (er_ac_target_mapping != null) {
				// Target of the AC edge is a AC context node!
				rr_ac_target = patterns.getContextTrace(er_ac_edge.getTarget());
			} else {
				// Use existing AC-Object-Pattern
				rr_ac_target = patterns.getACObjectPattern(er_ac_edge.getTarget()).getNode_b();
			}

			// B-side edge
			Edge rr_ac_edge = createEdge(rr_ac_source, rr_ac_target,
					er_ac_edge.getType(), recognitionGraph);

			// Save transformation pattern
			ACReferencePattern ACReferencePattern = new ACReferencePattern(rr_ac_edge, er_ac_edge);
			patterns.addACRreferencePatter(ACReferencePattern);
		}
	}
	
	/**
	 * Creates the AC attribute pattern. Which means:
	 * <ul>
	 * <li>NAC/PAC attribute in NAC/PAC node. -> NAC/PAC for model B.</li>
	 * <li>NAC/PAC attribute in << preserve >> node. -> NAC/PAC for model B; create AC extension if
	 * needed.</li>
	 * <li>NAC/PAC attribute in << delete >> node. -> NAC/PAC for model A; otherwise we would
	 * recognize a rule with transient effects.</li>
	 * </ul>
	 */
	private void createACAttributePattern() {
		
		// Edit-Rule AC attributes:
		for (Attribute er_ac_attr : getAttributes(editGraph)) {
			
			/*
			 * Get recognition rule AC node of the attribute.
			 */
			
			Node er_ac_node = er_ac_attr.getNode();
			Node rr_ac_node = null;
			
			// Mapping for context AC nodes of the edit rule
			List<Mapping> er_ac_mappings = ((NestedCondition) er_ac_node.getGraph().eContainer()).getMappings();
			Mapping er_ac_node_mapping = findMappingByImage(er_ac_mappings, er_ac_node);

			if (er_ac_node_mapping != null) {
				// Node of the AC attribute is a AC context node!
				Node er_ac_context_node = er_ac_node_mapping.getImage();
				rr_ac_node = patterns.getContextTrace(er_ac_context_node);
			} else {
				// NAC/PAC attribute in NAC/PAC node.
				rr_ac_node = patterns.getACObjectPattern(er_ac_node).getNode_b();
			}
			
			/*
			 * Create recognition rule NAC/PAC attribute
			 */
			
			HenshinFactory.eINSTANCE.createAttribute(
					rr_ac_node, er_ac_attr.getType(), er_ac_attr.getValue());
		}
	}
}
