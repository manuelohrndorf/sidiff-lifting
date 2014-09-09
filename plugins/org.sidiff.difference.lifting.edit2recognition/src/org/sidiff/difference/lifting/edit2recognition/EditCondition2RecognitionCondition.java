package org.sidiff.difference.lifting.edit2recognition;

import static org.sidiff.common.henshin.HenshinConditionAnalysis.isACGlueNode;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.copyNode;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.createEdge;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.createNode;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.findMappingByImage;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.getAttributes;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.getRemoteNode;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isDeletionNode;

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
	 * er_				-> Edit-Rule
	 * rr_				-> Recognition-Rule
	 * _ac_ 			-> Application Condition
	 * _ac_context_ 	-> A AC node of a nested graph that is mapped to a parent (LHS) graph node.
	 * _context_		-> The node to which a 'context NAC/PAC node' is mapped.
	 * ac_extension_	-> The mapped extension node of an extension pattern:
	 * 							- extension <-- correspondence -> context
	 */

	/**
	 * The generated transformation patterns and traces.
	 */
	private TransformationPatterns patterns;
	
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
		
		// An AC with an incident << delete >> node will be interpreted as a precondition! 
		if (checkForDeletionGlueNodes()) {
			isPrecondition = true;
		}
		
		createACObjectPattern();
		createACContextNodePattern();
		createACReferencePattern();
		createACAttributePattern();
	}
	
	/**
	 * Checks whether the AC graph is not incident with a << delete >> node.
	 * 
	 * @return <code>true</code> if at least on glue nodes is a << delete >> nodes;
	 *         <code>false</code> otherwise.
	 */
	private boolean checkForDeletionGlueNodes() {

		// Edit rule AC nodes:
		for (Node er_ac_context_node : editGraph.getNodes()) {
			// Catch context (glue) nodes:
			Node er_context_node = getRemoteNode(editCondition.getMappings(), er_ac_context_node);

			if ((er_context_node != null) && isACGlueNode(er_ac_context_node)) {
				// Is glue node a << delete >> node?
				if (isDeletionNode(er_context_node)) {
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
			// Filter context nodes:
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
				
				NodePair rr_context_node;
				Node rr_ac_context_node;
				
				// Precondition OR Postcondition?
				if (isPrecondition) {
					// Precondition: Glue AC to model A:
					rr_context_node = patterns.getTraceA(er_context_node);
				} else {
					// Postcondition: Glue AC to model B:
					rr_context_node = patterns.getTraceB(er_context_node);
				}
				
				// Need AC-Extension?
				if (rr_context_node == null) {
					// Create AC-Extension:
					rr_ac_context_node = createACExtensionPattern(er_context_node, er_ac_context_node);
				} else {
					// Create AC edge -> source context:
					rr_ac_context_node = createContextOfAC(rr_context_node.getLhsNode());		
				}
				
				// Save transformation pattern:
				ACContextNodePattern acContextNodePattern = new ACContextNodePattern(
						rr_ac_context_node, er_ac_context_node);
				patterns.addACContextNodePattern(acContextNodePattern);
			}
		}
	}
	
	/**
	 * Creates (if it nor already exists) a NAC/PAC correspondence pattern.
	 * <p>Precondition: Glue AC-Extension to model A</p>
	 * <p>Postcondition: Glue AC-Extension to model B</p>
	 * 
	 * @param er_context_node
	 *            The Edit-Rule LHS context node.
	 * @param er_ac_contect_node
	 *            The Edit-Rule nested condition context node.
	 * @return The extension node of the NAC/PAC correspondence pattern.
	 */
	private Node createACExtensionPattern(Node er_context_node, Node er_ac_contect_node) {

		/*
		 * NAC/PAC correspondence pattern
		 */

		if (patterns.getACExtensionPattern(er_ac_contect_node) != null) {
			// Pattern already exists.
			return patterns.getACExtensionPattern(er_context_node).acExtensionNode;
		} else {
			
			// Find trace of edit rule to recognition rule context node:
			NodePair rr_context_node;
			
			// Precondition OR Postcondition?
			if (isPrecondition) {
				// Precondition: Glue AC-Extension to model B:
				rr_context_node = patterns.getTraceB(er_context_node);
			} else {
				// Postcondition: Glue AC-Extension to model A:
				rr_context_node = patterns.getTraceA(er_context_node);
			}

			assert (rr_context_node != null) : "Missing trace!";
			
			// Create new AC-Extension pattern:

			// NAC/PAC context node:
			Node rr_ac_context_node = createContextOfAC(rr_context_node.getLhsNode());

			// NAC/PAC extension node:
			Node rr_ac_extension_node = copyNode(recognitionGraph, rr_context_node.getLhsNode(), false);
			rr_ac_extension_node.setName("");
			
			// NAC/PAC correspondence node:
			Node rr_ac_correspondence = createNode(recognitionGraph,
					SymmetricPackage.eINSTANCE.getCorrespondence());
			
			// NAC/PAC Correspondence -> NAC/PAC Edge -> Context Node
			createEdge(rr_ac_correspondence, rr_ac_context_node,
					SymmetricPackage.eINSTANCE.getCorrespondence_ObjA(), recognitionGraph);

			// NAC/PAC Correspondence -> NAC/PAC Edge -> Extension Node
			createEdge(rr_ac_correspondence, rr_ac_extension_node,
					SymmetricPackage.eINSTANCE.getCorrespondence_ObjB(), recognitionGraph);

			// Save transformation pattern
			ACExtensionPattern actExtensionPattern = new ACExtensionPattern(
					rr_context_node,
					rr_ac_context_node, rr_ac_extension_node, rr_ac_correspondence,
					er_context_node, er_ac_contect_node);
			patterns.addACExtension(actExtensionPattern);

			return rr_ac_extension_node;
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
	 * Creates the AC reference pattern. That means the function will create a NAC/PAC edge for each
	 * NAC/PAC edge in the edit rule. We presuppose that application conditions will not be violated
	 * after applying an edit operation. The function creates a AC-Extension if it is necessary.
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
				rr_ac_source = patterns.getACObjectPattern(er_ac_edge.getSource()).acNode;
			}

			/*
			 * Recognition rule NAC/PAC target:
			 */

			Node rr_ac_target = null;

			if (er_ac_target_mapping != null) {
				// Target of the AC edge is a AC context node!
				rr_ac_target = patterns.getContextTrace(er_ac_edge.getTarget());
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
			
			// Mapping for context AC nodes of the edit rule
			List<Mapping> er_ac_mappings = ((NestedCondition) er_ac_node.getGraph().eContainer()).getMappings();
			Mapping er_ac_node_mapping = findMappingByImage(er_ac_mappings, er_ac_node);

			if (er_ac_node_mapping != null) {
				// Node of the AC attribute is a AC context node!
				Node er_ac_context_node = er_ac_node_mapping.getImage();
				rr_ac_node = patterns.getContextTrace(er_ac_context_node);
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
