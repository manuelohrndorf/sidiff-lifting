package org.sidiff.difference.lifting.edit2recognition.internal;

import static org.sidiff.common.henshin.HenshinConditionAnalysis.isACBoundaryNode;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.copyNode;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.createEdge;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.createNode;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.findMappingByImage;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.getAttributes;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.getRemoteNode;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isDeletionNode;

import java.util.List;

import org.eclipse.emf.ecore.EReference;
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
import org.sidiff.difference.lifting.edit2recognition.editrule.EditRuleAnnotations;
import org.sidiff.difference.lifting.edit2recognition.exceptions.UnsupportedApplicationConditionException;
import org.sidiff.difference.lifting.edit2recognition.traces.ACBoundaryNodePattern;
import org.sidiff.difference.lifting.edit2recognition.traces.ACExtensionPattern;
import org.sidiff.difference.lifting.edit2recognition.traces.ACObjectPattern;
import org.sidiff.difference.lifting.edit2recognition.traces.ACReferencePattern;
import org.sidiff.difference.lifting.edit2recognition.traces.TransformationPatterns;
import org.sidiff.difference.symmetric.SymmetricPackage;

/**
 * Transforms a single Negative-Application-Condition (NAC) or Positive-Application-Condition (PAC) formula.
 * 
 * @author Manuel Ohrndorf
 */
public class EditCondition2RecognitionCondition {
	
	/* Key:
	 * er_				-> Edit-Rule
	 * rr_				-> Recognition-Rule
	 * _ac_ 			-> Application Condition
	 * _ac_boundary_ 	-> A AC node of a nested graph that is mapped to a parent (LHS) graph node.
	 * _boundary_		-> The node to which a 'boundary NAC/PAC node' is mapped.
	 * ac_extension_	-> The mapped extension node of an extension pattern:
	 * 							- extension <-- correspondence -> boundary
	 */
	
	/**
	 * The generated transformation patterns and traces.
	 */
	private TransformationPatterns patterns;
	
	/**
	 * The AC is either a negative- or a positive-application-condition.
	 */
	private boolean isNAC = false;
	
	/**
	 * The AC is either a precondition or a postcondition.
	 */
	private boolean isPrecondition = false;
	
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
	 * @param editFormula
	 *            The nested PAC/NAC formula.
	 * @param patterns
	 *            The transformation patterns of the containing graph.
	 * @throws UnsupportedApplicationConditionException 
	 */
	public EditCondition2RecognitionCondition(Formula editFormula, TransformationPatterns patterns) 
			throws UnsupportedApplicationConditionException {
		
		// Transformation patterns of the containing graph:
		this.patterns = patterns;
		
		// Check if the formula is a NAC or a PAC:
		if (editFormula instanceof Not) {
			
			// Formula is a NAC:
			isNAC = true;
			Formula er_not_child = ((Not) editFormula).getChild();
			
			if (er_not_child instanceof NestedCondition) {
				editCondition = (NestedCondition) er_not_child;
				editGraph = editCondition.getConclusion();
			} else {
				throw new UnsupportedApplicationConditionException(editFormula);
			}
		} else  {
			
			// Formula is a PAC:
			isNAC = false;
			
			if (editFormula instanceof NestedCondition) {
				editCondition = (NestedCondition) editFormula;
				editGraph = editCondition.getConclusion();
			} else {
				throw new UnsupportedApplicationConditionException(editFormula);
			}
		}
		
		// Check weather the AC is pre- or a postcondition:
		checkConditionType();
	}
	
	/**
	 * Transforms a single Negative-Application-Condition (NAC) or Positive-Application-Condition
	 * (PAC) formula.
	 * 
	 * @return The transformed PAC/NAC recognition rule formula.
	 */
	public Formula transform() {

		recognitionGraph = HenshinFactory.eINSTANCE.createGraph(editGraph.getName());
		recognitionCondition = HenshinFactory.eINSTANCE.createNestedCondition();
		recognitionCondition.setConclusion(recognitionGraph);
	
		// Create all NAC/PAC patters: 
		createPatterns();
		
		// Return NAC or PAC:
		if (isNAC) {
			Not not = HenshinFactory.eINSTANCE.createNot();
			not.setChild(recognitionCondition);

			return not;	
		} else  {
			return recognitionCondition;	
		}
	}
	
	/**
	 * Create all NAC/PAC patters.
	 */
	private void createPatterns() {
		
		// Create patterns:
		createACObjectPattern();
		createACBoundaryNodePattern();
		createACReferencePattern();
		createACAttributePattern();
	}
	
	private void checkConditionType() {
		isPrecondition = false;
		
		// An AC with an incident << delete >> node will be interpreted as a precondition! 
		if (checkForDeletionBoundaryNodes()) {
			isPrecondition = true;
			return;
		}
		
		// Check annotations:
		EditRuleAnnotations.Condition conditionType = EditRuleAnnotations.getCondition(editGraph);
		
		if (conditionType.equals(EditRuleAnnotations.Condition.pre)) {
			isPrecondition = true;
			return;
		}
		
		if (conditionType.equals(EditRuleAnnotations.Condition.post)) {
			isPrecondition = false;
			return;
		}
	}
	
	/**
	 * Checks whether the AC graph is not incident with a << delete >> node.
	 * 
	 * @return <code>true</code> if at least on glue nodes is a << delete >> nodes;
	 *         <code>false</code> otherwise.
	 */
	private boolean checkForDeletionBoundaryNodes() {

		// Edit rule AC nodes:
		for (Node er_ac_boundary_node : editGraph.getNodes()) {
			// Catch boundary (glue) nodes:
			Node er_boundary_node = getRemoteNode(editCondition.getMappings(), er_ac_boundary_node);

			if ((er_boundary_node != null) && isACBoundaryNode(er_ac_boundary_node)) {
				// Is glue node a << delete >> node?
				if (isDeletionNode(er_boundary_node)) {
					return true;
				}
			}
		}

		return false;
	}
	
	/**
	 * Creates the AC object pattern. Which means the function copies the NAC/PAC node from
	 * the edit rule to the recognition rule.
	 */
	private void createACObjectPattern() {

		// Edit rule AC nodes:
		for (Node er_ac_node : editGraph.getNodes()) {
			// Filter boundary nodes:
			if (!HenshinRuleAnalysisUtilEx.isNodeMapped(editCondition.getMappings(), er_ac_node)) {
				// Copy the Edit-Rule node as Recognition-Rule node:
				Node rr_ac_node = copyNode(recognitionGraph, er_ac_node, false);

				// Save transformation pattern
				ACObjectPattern ACObjectPattern = new ACObjectPattern(rr_ac_node, er_ac_node);
				patterns.addACObjectPattern(ACObjectPattern);
			}
		}
	}
	
	/**
	 * Creates the boundary of the Recognition-Rule NAC/PAC. Creates a NAC/PAC correspondence
	 * extension if needed.
	 * 
	 * <p> That means: LHS-A-Node -mapping-> AC-A-Node <- Correspondence -> AC-B-Node</p> 
	 */
	private void createACBoundaryNodePattern() {
		
		// Edit rule AC nodes:
		for (Node er_ac_boundary_node : editGraph.getNodes()) {
			// Catch boundary (glue) nodes:
			Node er_boundary_node = getRemoteNode(editCondition.getMappings(), er_ac_boundary_node);
			
			if ((er_boundary_node != null) && isACBoundaryNode(er_ac_boundary_node)) {
				
				NodePair rr_boundary_node;
				Node rr_ac_boundary_node;
				
				// Precondition OR Postcondition?
				if (isPrecondition) {
					// Precondition: Glue AC to model A:
					rr_boundary_node = patterns.getTraceA(er_boundary_node);
				} else {
					// Postcondition: Glue AC to model B:
					rr_boundary_node = patterns.getTraceB(er_boundary_node);
				}
				
				// Need AC-Extension?
				if (rr_boundary_node == null) {
					// Create AC-Extension:
					rr_ac_boundary_node = createACExtensionPattern(er_boundary_node, er_ac_boundary_node);
				} else {
					// Create AC edge -> source boundary:
					rr_ac_boundary_node = createBoundaryOfAC(rr_boundary_node.getLhsNode());		
				}
				
				// Save transformation pattern:
				ACBoundaryNodePattern acBoundaryNodePattern = new ACBoundaryNodePattern(
						rr_ac_boundary_node, er_ac_boundary_node);
				patterns.addACBoundaryNodePattern(acBoundaryNodePattern);
			}
		}
	}
	
	/**
	 * Creates (if it nor already exists) a NAC/PAC correspondence pattern.
	 * <p>Precondition: Glue AC-Extension to model A</p>
	 * <p>Postcondition: Glue AC-Extension to model B</p>
	 * 
	 * @param er_boundary_node
	 *            The Edit-Rule LHS boundary node.
	 * @param er_ac_boundary_node
	 *            The Edit-Rule nested condition boundary node.
	 * @return The extension node of the NAC/PAC correspondence pattern.
	 */
	private Node createACExtensionPattern(Node er_boundary_node, Node er_ac_boundary_node) {

		/*
		 * NAC/PAC correspondence pattern
		 */

		if (patterns.getACExtensionPattern(er_ac_boundary_node) != null) {
			// Pattern already exists.
			return patterns.getACExtensionPattern(er_boundary_node).acExtensionNode;
		} else {
			
			// Find trace of edit rule to recognition rule boundary node:
			NodePair rr_boundary_node;
			
			// Precondition OR Postcondition?
			EReference CorrespondenceToBoundaryEdgeType = null;
			EReference CorrespondenceToExtensionEdgeType = null;
			
			if (isPrecondition) {
				// Precondition: Glue AC-Extension to model B:
				rr_boundary_node = patterns.getTraceB(er_boundary_node);
				
				// Connect AC-Extension to model side B:
				CorrespondenceToBoundaryEdgeType = SymmetricPackage.eINSTANCE.getCorrespondence_ObjB();
				CorrespondenceToExtensionEdgeType = SymmetricPackage.eINSTANCE.getCorrespondence_ObjA();
			} else {
				// Postcondition: Glue AC-Extension to model A:
				rr_boundary_node = patterns.getTraceA(er_boundary_node);
				
				// Connect AC-Extension to model side A:
				CorrespondenceToBoundaryEdgeType = SymmetricPackage.eINSTANCE.getCorrespondence_ObjA();
				CorrespondenceToExtensionEdgeType = SymmetricPackage.eINSTANCE.getCorrespondence_ObjB();
			}

			assert (rr_boundary_node != null) : "Missing trace!";
			
			// Create new AC-Extension pattern:

			// NAC/PAC boundary node:
			Node rr_ac_boundary_node = createBoundaryOfAC(rr_boundary_node.getLhsNode());

			// NAC/PAC extension node:
			Node rr_ac_extension_node = copyNode(recognitionGraph, rr_boundary_node.getLhsNode(), false);
			rr_ac_extension_node.setName("");
			
			// NAC/PAC correspondence node:
			Node rr_ac_correspondence = createNode(recognitionGraph,
					SymmetricPackage.eINSTANCE.getCorrespondence());
			
			// NAC/PAC Correspondence -> NAC/PAC Edge -> Boundary Node
			createEdge(rr_ac_correspondence, rr_ac_boundary_node,
					CorrespondenceToBoundaryEdgeType, recognitionGraph);

			// NAC/PAC Correspondence -> NAC/PAC Edge -> Extension Node
			createEdge(rr_ac_correspondence, rr_ac_extension_node,
					CorrespondenceToExtensionEdgeType, recognitionGraph);

			// Save transformation pattern
			ACExtensionPattern actExtensionPattern = new ACExtensionPattern(
					rr_boundary_node,
					rr_ac_boundary_node, rr_ac_extension_node, rr_ac_correspondence,
					er_boundary_node, er_ac_boundary_node);
			patterns.addACExtension(actExtensionPattern);

			return rr_ac_extension_node;
		}
	}
	
	/**
	 * Creates a new node inside the NAC/PAC which is mapped to the given node of the parent graph.
	 * 
	 * @param lhsBoundary
	 *            The LHS node of the parent graph.
	 * @return The mapped NAC/PAC boundary node.
	 */
	private Node createBoundaryOfAC(Node lhsBoundary) {

		// The mapped NAC/PAC boundary node
		Node ac_boundary = copyNode(recognitionGraph, lhsBoundary, false);

		Mapping boundaryMapping = HenshinFactory.eINSTANCE.createMapping();
		boundaryMapping.setOrigin(lhsBoundary);
		boundaryMapping.setImage(ac_boundary);
		
		// Add mapping to AC
		((NestedCondition) recognitionGraph.eContainer()).getMappings().add(boundaryMapping);

		return ac_boundary;
	}

	/**
	 * Creates the AC reference pattern. That means the function will create a NAC/PAC edge for each
	 * NAC/PAC edge in the edit rule. We presuppose that application conditions will not be violated
	 * after applying an edit operation. The function creates a AC-Extension if it is necessary.
	 * 
	 * @see EditCondition2RecognitionCondition#getACBoundaryNode(Mapping)
	 * @see EditCondition2RecognitionCondition#createACExtensionPattern(Node)
	 * @see EditCondition2RecognitionCondition#createBoundaryOfAC(Node)
	 */
	private void createACReferencePattern() {

		// Edit rule AC edges
		for (Edge er_ac_edge : editGraph.getEdges()) {

			// Mappings for boundary AC nodes
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
				// Source of the AC edge is a AC boundary node!
				rr_ac_source = patterns.getBoundaryTrace(er_ac_edge.getSource());
			} else {
				// Use existing AC-Object-Pattern
				rr_ac_source = patterns.getACObjectPattern(er_ac_edge.getSource()).acNode;
			}

			/*
			 * Recognition rule NAC/PAC target:
			 */

			Node rr_ac_target = null;

			if (er_ac_target_mapping != null) {
				// Target of the AC edge is a AC boundary node!
				rr_ac_target = patterns.getBoundaryTrace(er_ac_edge.getTarget());
			} else {
				// Use existing AC-Object-Pattern
				rr_ac_target = patterns.getACObjectPattern(er_ac_edge.getTarget()).acNode;
			}

			// Create Recognition-Rule AC edge:
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
			
			// Mapping for boundary AC nodes of the edit rule
			List<Mapping> er_ac_mappings = ((NestedCondition) er_ac_node.getGraph().eContainer()).getMappings();
			Mapping er_ac_node_mapping = findMappingByImage(er_ac_mappings, er_ac_node);

			if (er_ac_node_mapping != null) {
				// Node of the AC attribute is a AC boundary node!
				Node er_ac_boundary_node = er_ac_node_mapping.getImage();
				rr_ac_node = patterns.getBoundaryTrace(er_ac_boundary_node);
			} else {
				// NAC/PAC attribute in NAC/PAC node.
				rr_ac_node = patterns.getACObjectPattern(er_ac_node).acNode;
			}
			
			/*
			 * Create recognition rule NAC/PAC attribute
			 */
			
			HenshinFactory.eINSTANCE.createAttribute(
					rr_ac_node, er_ac_attr.getType(), er_ac_attr.getValue());
		}
	}
}
