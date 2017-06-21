
package org.sidiff.difference.lifting.edit2recognition.internal;

import static org.sidiff.common.henshin.HenshinMultiRuleUtil.createMultiMapping;
import static org.sidiff.common.henshin.HenshinMultiRuleUtil.recreateMultiRuleEmbedding;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.copyPreserveNodes;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.createParameter;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.createPreservedAttribute;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.createPreservedEdge;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.createPreservedNode;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.createPreservedNodeWithAttribute;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.findUnitParamterMapping;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.getCreationAttributes;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.getDeletionAttributes;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.getLHSIntersectRHSNodes;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.getLHSMinusRHSEdges;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.getLHSMinusRHSNodes;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.getLHStoRHSChangingAttributes;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.getPreservedAttributes;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.getPreservedEdges;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.getPreservedNodes;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.getRHSChangingAttributes;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.getRHSMinusLHSEdges;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.getRHSMinusLHSNodes;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.getRemoteNode;
import static org.sidiff.editrule.analysis.conditions.EditRuleConditionsConfiguration.isPreservedAttributePostCondition;
import static org.sidiff.editrule.analysis.conditions.EditRuleConditionsConfiguration.isPreservedAttributePreCondition;
import static org.sidiff.editrule.analysis.conditions.EditRuleConditionsConfiguration.isPreservedEdgePostCondition;
import static org.sidiff.editrule.analysis.conditions.EditRuleConditionsConfiguration.isPreservedEdgePreCondition;
import static org.sidiff.editrule.analysis.transienteffects.EditRuleTransientEffects.isOptionalAttributeValueChange;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.henshin.model.And;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.sidiff.common.emf.access.EMFMetaAccess;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;
import org.sidiff.common.henshin.ParameterInfo;
import org.sidiff.common.henshin.view.AttributePair;
import org.sidiff.common.henshin.view.EdgePair;
import org.sidiff.common.henshin.view.NodePair;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.lifting.edit2recognition.EditPattern2RecognitionPattern;
import org.sidiff.difference.lifting.edit2recognition.exceptions.EditToRecognitionException;
import org.sidiff.difference.lifting.edit2recognition.exceptions.UnsupportedApplicationConditionException;
import org.sidiff.difference.lifting.edit2recognition.traces.AddObjectPattern;
import org.sidiff.difference.lifting.edit2recognition.traces.AddReferencePattern;
import org.sidiff.difference.lifting.edit2recognition.traces.AttributeValueChangePattern;
import org.sidiff.difference.lifting.edit2recognition.traces.CorrespondencePattern;
import org.sidiff.difference.lifting.edit2recognition.traces.RemoveObjectPattern;
import org.sidiff.difference.lifting.edit2recognition.traces.RemoveReferencePattern;
import org.sidiff.difference.lifting.edit2recognition.traces.TransformationPatterns;
import org.sidiff.difference.lifting.edit2recognition.util.TransformationConstants;
import org.sidiff.difference.symmetric.SymmetricPackage;
import org.sidiff.editrule.analysis.annotations.EditRuleAnnotations;
import org.sidiff.editrule.analysis.transienteffects.EditRuleTransientEffects;
import org.sidiff.matching.model.MatchingModelPackage;

// TODO: Forbid/Prevent wrong correspondences if there are nodes with the same type!

/**
 * <p>
 * Transforms an edit rule into a recognition rule. A recognition rule matches the atomic changes in
 * a difference which were produced by the edit rule. The recognition rule groups the atomic changes
 * in a semantic change set.
 * </p>
 * 
 * @author Manuel Ohrndorf
 */
public class EditRule2RecognitionRule implements EditPattern2RecognitionPattern {
	
	/**
	 * The edit rule from which the recognition rule will be created.
	 */
	private Rule editRule;
	
	/**
	 * Atomic or complex edit operation?
	 */
	private boolean atomic;
	
	/**
	 * The recognition rule which will be created by <code>transform()</code>.
	 */
	private Rule recognitionRule;
	
	/**
	 * The unit which contains the input edit rule.
	 */
	private Unit editContainingUnit;
	
	/**
	 * The generated transformation patterns and traces.
	 */
	private TransformationPatterns patterns;
	
	/**
	 * The recognition rule Difference node.
	 */
	private NodePair difference;
	
	/**
	 * The recognition rule semantic change set node (RHS).
	 */
	private Node semanticChangeSet;
	
	/**
	 * Collects unit parameters. Unit parameters are input parameters coming from outside of the
	 * rule.
	 * 
	 * Map <editParameter, recognitionParameter>
	 */
	private Map<Parameter, Parameter> unitParameter;
	
	/**
	 * Collects rule parameters. Rule parameters are set inside the rule (e.g. from another value).
	 * 
	 * Map <editParameter, recognitionParameter>
	 */
	private Map<Parameter, Parameter> ruleParameter;
	
	/**
	 * Collects all changes for semantic change set.
	 */
	private List<NodePair> changes;
	
	/**
	 * Collects all optional changes for semantic change set.
	 */
	private List<NodePair> optionalChanges;
	
	/**
	 * Prevent Ecore-Type-Nodes from duplicating (EReference)
	 */
	private Map<EReference, NodePair> eReferenceTypeNodes;
	
	/**
	 * Prevent Ecore-Type-Nodes from duplicating (EAttribute)
	 */
	private Map<EAttribute, NodePair> eAttributeTypeNodes;
	
	// /**
	// * Content check warning for attributes with unit parameters an operators
	// */
	// private static final String WARNING_OPERATOR_ON_CONTENT_CHECK =
	// "Warning: Content check of attribute with '+,-,*,/' operator "
	// + "and unit mapped parameter(s) is not supported!";
	/*
	 * TODO: TK (22.Sept.2013): Content-Check disables for all parameters (also rule parameters) if
	 * attribute value is a JavaScript expression.
	 */
	
	/**
	 * Transforms an edit rule into a recognition rule. A recognition rule matches the atomic
	 * changes in a difference which were produced by the edit rule. The recognition rule groups the
	 * atomic changes in a semantic change set.
	 * 
	 * @param editRule
	 *            the edit-rule which will be transformed.
	 * @param editContainingUnit
	 *            the unit which contains the given edit-rule.
	 * @param atomic
	 *            whether this is an <b>atomic</b> or a <b>complex</b> edit operation.
	 */
	public EditRule2RecognitionRule(Rule editRule, Unit editContainingUnit, boolean atomic) {
		
		assert (editRule != null);
		assert (editContainingUnit != null);
		
		this.editRule = editRule;
		this.editContainingUnit = editContainingUnit;
		this.atomic = atomic;
		
		// another assertion: chack availability of all Packages imported by the edit rule
		checkImportedEPackagesAvailability();
		
		// Initialize new recognition rule
		recognitionRule = HenshinFactory.eINSTANCE.createRule();
		recognitionRule.setName(TransformationConstants.RECOGNITION_RULE_PREFIX + editRule.getName());
		recognitionRule.setInjectiveMatching(editRule.isInjectiveMatching());
		
		// Copy description
		if (editRule.getDescription() != null) {
			recognitionRule.setDescription(TransformationConstants.RECOGNITION_RULE_DESCRIPTION_PREFIX
					+ editRule.getDescription());
		}
		
		// Initialize transformation information
		patterns = new TransformationPatterns();
		unitParameter = new HashMap<Parameter, Parameter>();
		ruleParameter = new HashMap<Parameter, Parameter>();
		changes = new ArrayList<NodePair>();
		optionalChanges = new ArrayList<NodePair>();
		
		// Initialize Ecore-Type-Nodes lists
		eReferenceTypeNodes = new HashMap<EReference, NodePair>();
		eAttributeTypeNodes = new HashMap<EAttribute, NodePair>();
	}
	
	/**
	 * Transforms an edit rule into a recognition rule. A recognition rule matches the atomic
	 * changes in a difference which were produced by the edit rule. The recognition rule groups the
	 * atomic changes in a semantic change set.
	 * 
	 * @return the recognition-rule.
	 * @throws EditToRecognitionException
	 */
	public Rule transform() throws EditToRecognitionException {
		
		// Create recognition rule parameters
		createParameters();
		
		/*
		 * Create Patterns
		 */
		
		// Preserve:
		createCorrespondencePatterns();
		createPreservedReferencePattterns();
		
		// Remove:
		createRemoveObjectPatterns();
		createRemoveReferencePatterns();
		
		// Add:
		createAddObjectPattterns();
		createAddReferencePattern();
		
		// @formatter:off
		
		// Attribute-Value-Changes / Content-Check:
		createAttributeValueChangePatterns();			// << preserve >> node -> attributeX -> attributeY 
														// << preserve >> node -> << create >>   attribute 

		// Attribute-Value-Content-Check:
		createPreservedAttributeValueCheckPatterns(); 	// << preserve >> node -> << preserve >> attribute
														// << preserve >> node -> << delete >>   attribute 
		createCreationAttributeValueCheckPatterns(); 	// << create >>   node -> << create >>   attribute 
		createDeletionAttributeValueCheckPatterns();	// << delete >>   node -> << delete >>   attribute 
		
		// @formatter:on
		
		// Application Condition:
		createACPattern(editRule.getLhs().getFormula());
		
		// Create Semantic change set and link changes to the change set:
		createChangeSet();
		
		// Embed Kernel-Recognition-Rule into Multi-Rules:
		embeddKernel();
		linkOptionalChangesToChangeSet();
		
		return recognitionRule;
	}
	
	/**
	 * Embeddeds all Multi-Rules (i.e. optional attribute value changes) into the Kernel-Rule.
	 */
	private void embeddKernel() {
		
		for (Rule multiRule : recognitionRule.getAllMultiRules()) {
			recreateMultiRuleEmbedding(multiRule);
		}
	}
	
	/**
	 * Link all optional changes (i.e. optional attribute value changes) to the existing
	 * Semantic-Change-Set
	 */
	private void linkOptionalChangesToChangeSet() {
		
		for (NodePair change : optionalChanges) {
			Rule multiRule = change.getLhsNode().getGraph().getRule();
			
			// Find Semantic-Change-Set:
			for (Mapping multiMapping : multiRule.getMultiMappings()) {
				if (multiMapping.getOrigin() == getSemanticChangeSet()) {
					HenshinFactory.eINSTANCE.createEdge(multiMapping.getImage(), change.getRhsNode(),
							SymmetricPackage.eINSTANCE.getSemanticChangeSet_Changes());
				}
			}
		}
	}
	
	/**
	 * <p>
	 * Recursively creates the application condition pattern(s):
	 * </p>
	 * <p>
	 * Supported structure:
	 * </p>
	 * <ul>
	 * <li>NAC Formula: < NAC > ::= < NOT -> NestedCondition ></li>
	 * <li>PAC Formula: < PAC > ::= < NestedCondition ></li>
	 * <li>NAC/PAC Combination: < AND > ::= < LEFT > < RIGHT ></li>
	 * <ul>
	 * <li>< LEFT > ::= < NAC > | < PAC > | < AND ></li>
	 * <li>< RIGT > ::= < NAC > | < PAC > | < AND ></li>
	 * </ul>
	 * </ul>
	 * 
	 * @param formula
	 *            The formula to transform.
	 * @return The transformed formula.
	 * @throws UnsupportedApplicationConditionException
	 */
	public void createACPattern(Formula formula) throws UnsupportedApplicationConditionException {
		
		if (formula == null) {
			// No formula found!
			return;
		}
		
		if (formula instanceof And) {
			// AND:
			And recognitionAnd = transformAndFormula((And) formula);
			recognitionRule.getLhs().setFormula(recognitionAnd);
		} else if ((formula instanceof NestedCondition) || (formula instanceof Not)) {
			// NAC or PAC:
			Formula recognitionAC = transformNestedCondition(formula);
			recognitionRule.getLhs().setFormula(recognitionAC);
		} else {
			throw new UnsupportedApplicationConditionException(formula);
		}
	}
	
	private Formula transformNestedCondition(Formula formula)
			throws UnsupportedApplicationConditionException {
		
		// NAC or PAC:
		EditCondition2RecognitionCondition conditionTF = new EditCondition2RecognitionCondition(formula, patterns);
		Formula recognitionAC = conditionTF.transform();
		
		return recognitionAC;
	}
	
	/**
	 * Transforms a edit AND into a recognition AND.
	 * 
	 * @param and
	 *            The edit AND formula.
	 * @return The recognition AND formula.
	 * @throws UnsupportedApplicationConditionException
	 */
	private And transformAndFormula(And and)
			throws UnsupportedApplicationConditionException {
		
		// Left:
		Formula er_leftFormula = and.getLeft();
		Formula rr_leftFormula = null;
		
		if (er_leftFormula instanceof And) {
			rr_leftFormula = transformAndFormula((And) er_leftFormula);
		} else {
			rr_leftFormula = transformNestedCondition(er_leftFormula);
		}
		
		// Right:
		Formula er_rightFormula = and.getRight();
		Formula rr_rightFormula = null;
		
		if (er_rightFormula instanceof And) {
			rr_rightFormula = transformAndFormula((And) er_rightFormula);
		} else {
			rr_rightFormula = transformNestedCondition(er_rightFormula);
		}
		
		And recognitionAnd = HenshinFactory.eINSTANCE.createAnd();
		recognitionAnd.setLeft(rr_leftFormula);
		recognitionAnd.setRight(rr_rightFormula);
		
		return recognitionAnd;
	}
	
	/**
	 * Copy's all parameters from the edit rule to the recognition rule. Parameters will be grouped
	 * in "unit parameters" and "rule parameters". Unit parameters are input parameters coming from
	 * outside of the rule. Rule parameters are set inside the rule (e.g. from another value). We
	 * have to know this because we can not make a content check of attributes with unit parameters
	 * and operators like '+,-,*,/' inside of the recognition rule.
	 */
	private void createParameters() {
		
		// Create unit parameter
		for (Parameter parameter : editRule.getParameters()) {
			Parameter newParameter = createParameter(recognitionRule, parameter.getName(), parameter.getType());
			
			if (findUnitParamterMapping(editContainingUnit.getParameterMappings(), parameter) != null) {
				unitParameter.put(parameter, newParameter);
			} else {
				ruleParameter.put(parameter, newParameter);
			}
		}
	}
	
	/**
	 * Creates the correspondence patterns for the recognition rule. Analyze each preserved node in
	 * the edit rule to generate the minimal correspondence pattern.
	 */
	private void createCorrespondencePatterns() {
		
		// Intersection of edit rule LHS and RHS <<preserve>> node
		for (NodePair node : getPreservedNodes(editRule)) {
			
			/*
			 * Analyze node to generate the minimal correspondence pattern:
			 */
			
			Node lhsNode = node.getLhsNode();
			
			NodePair node_a = null;
			NodePair node_b = null;
			NodePair correspondence = null;
			
			// Create model B correspondence pattern.
			if (EditRuleTransientEffects.isPreservedNodeSearchedInModelB(node)) {
				
				// Create model B node
				node_b = createPreservedNode(
						recognitionRule, getSearchedInBName(lhsNode), lhsNode.getType());
			}
			
			// Create model A correspondence pattern.
			if (EditRuleTransientEffects.isPreservedNodeSearchedInModelA(node)) {
				
				// Create model A node
				node_a = createPreservedNode(
						recognitionRule, getSearchedInAName(lhsNode), lhsNode.getType());
			}
			
			// Create correspondence node if model node A and model node B exists.
			if ((node_a != null) && (node_b != null)) {
				correspondence = createCorrespondence(node_a, node_b);
			}
			
			// Save transformation pattern
			CorrespondencePattern correspondencePattern = new CorrespondencePattern(
					node_a, node_b, correspondence, node);
			patterns.addCorrespondencePattern(correspondencePattern);
		}
	}
	
	/**
	 * Creates a correspondence node and links it to the model A and model B node.
	 * 
	 * @param nodeA
	 *            Model A node.
	 * @param nodeB
	 *            Model B node.
	 */
	private NodePair createCorrespondence(NodePair nodeA, NodePair nodeB) {

		// The (multi-)rule of the correspondence pattern:
		Rule recognitionRule = nodeA.getLhsNode().getGraph().getRule();
		
		// Create correspondence node:
		NodePair correspondence = createPreservedNode(recognitionRule, "",
				MatchingModelPackage.eINSTANCE.getCorrespondence());
		
		// Link correspondence node to model A and model B node:
		createPreservedEdge(recognitionRule, correspondence, nodeA,
				MatchingModelPackage.eINSTANCE.getCorrespondence_MatchedA());
		createPreservedEdge(recognitionRule, correspondence, nodeB,
				MatchingModelPackage.eINSTANCE.getCorrespondence_MatchedB());
		
		return correspondence;
	}
	
	/**
	 * Returns the name of the recognition-rule model A node.
	 * 
	 * @param node
	 *            The edit-rule node.
	 * @return The recognition-rule node name.
	 */
	private String getSearchedInAName(Node node) {
		return "A." + node.getName();
	}
	
	/**
	 * Returns the name of the recognition-rule model B node.
	 * 
	 * @param node
	 *            The edit-rule node.
	 * @return The recognition-rule node name.
	 */
	private String getSearchedInBName(Node node) {
		return "B." + node.getName();
	}
	
	/**
	 * Creates a content check into the recognition rule for each << preserved / delete >> attribute
	 * in the edit rule. That means the attribute which is checked in the edit rule will be check
	 * for the corresponding model A/B node* of the recognition rule.
	 * <p>
	 * *Depends on:</br> {@link EditRuleTransientEffects#isPreservedNodeSearchedInModelA(EdgePair)}</br>
	 * {@link EditRuleTransientEffects#isPreservedNodeSearchedInModelB(EdgePair)}
	 * </p>
	 */
	private void createPreservedAttributeValueCheckPatterns() {
		
		// Run through all preserved attributes in the edit rule.
		for (Attribute attribute : getPreservedAttributes(editRule)) {
			if (!isUnconsideredAttribute(attribute)) {
				
				if (isPreservedAttributePreCondition()) {
					// Find model A node for edit rule attribute
					NodePair aNode = patterns.getTraceA(attribute.getNode());
					
					// Generate attribute content check for model B if it exists
					createAttributeValueChangeContentCheck(aNode, attribute);
				}
				
				if (isPreservedAttributePostCondition()) {
					// Find model B node for edit rule attribute
					NodePair bNode = patterns.getTraceB(attribute.getNode());
					
					// Generate attribute content check for model B if it exists
					createAttributeValueChangeContentCheck(bNode, attribute);
				}
			}
		}
	}
	
	/**
	 * Creates the add attribute value patterns. That means the function will create a content check
	 * for model B nodes for each << create >> attribute in a << create >> node in the edit rule.
	 */
	private void createCreationAttributeValueCheckPatterns() {
		
		// Edit rule <<create>> attributes:
		for (Attribute rhsAttribute : getCreationAttributes(editRule)) {
			if (!isUnconsideredAttribute(rhsAttribute)) {
				
				// Get corresponding recognition node
				Node rhsNode = rhsAttribute.getNode();
				NodePair bNode = patterns.getTraceB(rhsNode);
				
				// Create content check
				createAttributeValueChangeContentCheck(bNode, rhsAttribute);
			}
		}
	}
	
	/**
	 * Creates the remove attribute value patterns. That means the function will create a content
	 * check for model A nodes for each << delete >> attribute in a << delete >> node in the edit
	 * rule.
	 */
	private void createDeletionAttributeValueCheckPatterns() {

		// Edit rule LHS \ RHS <<delete>> node attributes
		for (Attribute lhsAttribute : getDeletionAttributes(editRule)) {
			if (!isUnconsideredAttribute(lhsAttribute)) {
				
				// Get corresponding recognition node
				Node lhsNode = lhsAttribute.getNode();
				NodePair aNode = patterns.getTraceA(lhsNode);
				
				// Create content check
				createAttributeValueChangeContentCheck(aNode, lhsAttribute);
			}
		}
	}
	
	/**
	 * Creates the attribute value change patterns and attribute content checks:
	 * 
	 * <p>Optional attribute value changes:</p>
	 * 
	 * <ul>
	 * <li>variableX -> variableY</li>
	 * <li>parameterX -> parameterY</li>
	 * <li><< create >> attribute</li>
	 * </ul>
	 * 
	 * <p>Required attribute value changes:</p>
	 * 
	 * <ul>
	 * <li>literalX -> literalY</li>
	 * </ul>
	 */
	private void createAttributeValueChangePatterns() {
		
		// Create attribute value change pattern:
		// Content check: value1->value2
		for (AttributePair attribute : getLHStoRHSChangingAttributes(editRule)) {
			
			// Filter unconsidered attributes:
			if (!isUnconsideredAttribute(attribute.getLhsAttribute())) {
				
				Attribute rhsAttribute = attribute.getRhsAttribute();
				Attribute lhsAttribute = attribute.getLhsAttribute();
				
				// Find model A and B node for edit rule attribute
				Node rhsNode = rhsAttribute.getNode();
				NodePair aNode = patterns.getTraceA(rhsNode);
				NodePair bNode = patterns.getTraceB(rhsNode);
				
				// Create content check for model A node (attributes for recognition rule)
				createAttributeValueChangeContentCheck(aNode, lhsAttribute);
				
				// Create content check for model B node (attributes for recognition rule)
				createAttributeValueChangeContentCheck(bNode, rhsAttribute);
				
				// Create attribute value change pattern (nodes for recognition rule)
				if (isOptionalAttributeValueChange(attribute) && !changes.isEmpty()) {
					// FIXME: If there are only optional changes => Check if at least one (optional) Multi-Rule has matched...
					// WORKAROUND: !changes.isEmpty()
					
					// Optional attribute value change:
					createOptionalAttributeValueChangeGraph(aNode, bNode, rhsAttribute);
				} else {
					// Required attribute value change:
					createAttributeValueChangeGraph(aNode, bNode, rhsAttribute);
				}	
			}
		}
		
		// Create attribute value change pattern:
		// Content check: <<create>> attribute
		for (Attribute rhsAttribute : getRHSChangingAttributes(editRule)) {
			
			// Filter unconsidered attributes:
			if (!isUnconsideredAttribute(rhsAttribute)) {
			
				// Find model A and B node for edit rule attribute
				Node rhsNode = rhsAttribute.getNode();
				NodePair aNode = patterns.getTraceA(rhsNode);	// May be null!
				NodePair bNode = patterns.getTraceB(rhsNode);
				
				// Create content check for model B node:
				createAttributeValueChangeContentCheck(bNode, rhsAttribute);
				
				// Create attribute value change pattern (nodes for recognition rule)
				if (!changes.isEmpty()) {
					// FIXME: If there are only optional changes => Check if at least one (optional) Multi-Rule has matched...
					// WORKAROUND: !changes.isEmpty()
					
					// Optional attribute value change:
					createOptionalAttributeValueChangeGraph(aNode, bNode, rhsAttribute);
				} else {
					// WORKAROUND: Required attribute value change:
					if (aNode == null) {
						// Create model A node:
						aNode = createPreservedNode(
								recognitionRule, "A." + rhsNode.getName(), rhsNode.getType());
						
						// Create correspondence node:
						NodePair correspondence = createPreservedNode(recognitionRule, "",
								MatchingModelPackage.eINSTANCE.getCorrespondence());
						
						// Link correspondence node to model A and model B node:
						createPreservedEdge(recognitionRule, correspondence, aNode,
								MatchingModelPackage.eINSTANCE.getCorrespondence_MatchedA());
						createPreservedEdge(recognitionRule, correspondence, bNode,
								MatchingModelPackage.eINSTANCE.getCorrespondence_MatchedB());
						
						// Fix the trace:
						CorrespondencePattern oldPattern = patterns.getCorrespondecePattern(rhsNode);
						patterns.getCorrespondencePatterns().remove(oldPattern);

						Node lhsNode = getRemoteNode(rhsNode.getGraph().getRule().getMappings(), rhsNode);
						CorrespondencePattern correspondencePattern = new CorrespondencePattern(
								aNode, bNode, correspondence, new NodePair(lhsNode,rhsNode));
						patterns.addCorrespondencePattern(correspondencePattern);
					}
					
					createAttributeValueChangeGraph(aNode, bNode, rhsAttribute);
				}	
			}
		}
	}
	
	/**
	 * Creates the attribute value change pattern (nodes for recognition rule).
	 * 
	 * @param aNode
	 *            the model A node.
	 * @param bNode
	 *            the model B node.
	 * @param rhsAttribute
	 *            the RHS Attribute.
	 */
	private void createAttributeValueChangeGraph(
			NodePair aNode, NodePair bNode, Attribute rhsAttribute) {
		
		// Create change graph pattern
		NodePair attributeValueChange = createPreservedNode(recognitionRule, "",
				SymmetricPackage.eINSTANCE.getAttributeValueChange());
		createPreservedEdge(recognitionRule, attributeValueChange, aNode,
				SymmetricPackage.eINSTANCE.getAttributeValueChange_ObjA());
		createPreservedEdge(recognitionRule, attributeValueChange, bNode,
				SymmetricPackage.eINSTANCE.getAttributeValueChange_ObjB());
		
		// Look for existing attribute type node
		EAttribute eAttributeType = rhsAttribute.getType();
		NodePair typeNode = getEAttributeTypeNode(eAttributeType);
		
		// Link EAttribute node to pattern
		createPreservedEdge(recognitionRule, attributeValueChange, typeNode,
				SymmetricPackage.eINSTANCE.getAttributeValueChange_Type());
		
		// Save change for semantic change set
		changes.add(attributeValueChange);
		
		// Save transformation pattern
		AttributeValueChangePattern avcPattern = new AttributeValueChangePattern(
				attributeValueChange, typeNode, rhsAttribute);
		patterns.addAttributeValueChangePattern(avcPattern);
	}
	
	/**
	 * Creates an optional attribute value change pattern (nodes for recognition rule).
	 * 
	 * @param aNode
	 *            the model A boundary node (attrX -> attrY OR << create >> attr) 
	 *            OR <code>null</code> (<< create >> attr).
	 * @param bNode
	 *            the model B boundary node.
	 * @param rhsAttribute
	 *            the RHS Attribute.
	 */
	private void createOptionalAttributeValueChangeGraph(
			NodePair aNode, NodePair bNode, Attribute rhsAttribute) {
		
		assert ((aNode != null) || (bNode != null)) : "Need at least one boundary node!";
		
		/*
		 * Create the multi-rule:
		 */
		
		Rule optionalAVC = HenshinFactory.eINSTANCE.createRule();
		Graph lhsOptionalAVC = HenshinFactory.eINSTANCE.createGraph();
		Graph rhsOptionalAVC = HenshinFactory.eINSTANCE.createGraph();
		optionalAVC.setLhs(lhsOptionalAVC);
		optionalAVC.setRhs(rhsOptionalAVC);
		optionalAVC.setName(recognitionRule.getName() + ":OptionalAVC");
		
		recognitionRule.getMultiRules().add(optionalAVC);
		
		/*
		 * Create optional model A node:
		 */
		
		NodePair optionalNodeA;
		
		if (aNode != null) {
			// Create model A boundary node:
			optionalNodeA = copyPreserveNodes(optionalAVC, aNode, true);
			createMultiMapping(optionalAVC, aNode, optionalNodeA);
		} else {
			// Create model A multi-node and correspondence node:
			Node lhsTrace = getRemoteNode(
					rhsAttribute.getNode().getGraph().getRule().getMappings(),
					rhsAttribute.getNode());

			optionalNodeA = createPreservedNode(
					optionalAVC, getSearchedInAName(lhsTrace), lhsTrace.getType());
		}
		
		/*
		 *  Create optional model B node:
		 */
		
		NodePair optionalNodeB;
		
		if (bNode != null) {
			// Create model B boundary node:
			optionalNodeB = copyPreserveNodes(optionalAVC, bNode, true);
			createMultiMapping(optionalAVC, bNode, optionalNodeB);
		} else {
			// Create model A multi-node and correspondence node:
			Node lhsTrace = getRemoteNode(
					rhsAttribute.getNode().getGraph().getRule().getMappings(),
					rhsAttribute.getNode());

			optionalNodeB = createPreservedNode(
					optionalAVC, getSearchedInAName(lhsTrace), lhsTrace.getType());
		}
		
		/*
		 * Create optional correspondence (extension)
		 */
		
		if ((aNode == null) || (bNode == null)) {
			// Create linked correspondence:
			createCorrespondence(optionalNodeA, optionalNodeB);
		}

		/*
		 *  Create change graph pattern:
		 */
		
		NodePair attributeValueChange = createPreservedNode(optionalAVC, "",
				SymmetricPackage.eINSTANCE.getAttributeValueChange());
		createPreservedEdge(optionalAVC, attributeValueChange, optionalNodeA,
				SymmetricPackage.eINSTANCE.getAttributeValueChange_ObjA());
		createPreservedEdge(optionalAVC, attributeValueChange, optionalNodeB,
				SymmetricPackage.eINSTANCE.getAttributeValueChange_ObjB());
		
		// Look for existing attribute type node:
		EAttribute eAttributeType = rhsAttribute.getType();
		NodePair typeNode = getEAttributeTypeNode(eAttributeType);
		
		// Create attribute type node boundary node:
		NodePair typeBoundary = copyPreserveNodes(optionalAVC, typeNode, true);
		
		// Map boundary attribute type node:
		createMultiMapping(optionalAVC, typeNode, typeBoundary);
		
		// Link EAttribute node to pattern:
		createPreservedEdge(optionalAVC, attributeValueChange, typeBoundary,
				SymmetricPackage.eINSTANCE.getAttributeValueChange_Type());
		
		// Save change for semantic change set
		optionalChanges.add(attributeValueChange);
	}
	
	/**
	 * Returns the attribute type node for the given type. Creates a type node if necessary.
	 * 
	 * @param eAttributeType
	 *            The attribute type.
	 * @return The attribute type node.
	 */
	private NodePair getEAttributeTypeNode(EAttribute eAttributeType) {
		
		// Look for existing EAttribute node
		NodePair typeNode = eAttributeTypeNodes.get(eAttributeType);
		
		if (typeNode == null) {
			// Create EAttribute node if it not exists
			typeNode = createPreservedNodeWithAttribute(recognitionRule,
					"", EcorePackage.eINSTANCE.getEAttribute(),
					EcorePackage.eINSTANCE.getENamedElement_Name(), eAttributeType.getName(), true);
			
			eAttributeTypeNodes.put(eAttributeType, typeNode);
		}
		
		return typeNode;
	}
	
	/**
	 * Creates the preserved reference pattern. That means the function will create a preserved edge
	 * between model A/B node* for each << preserve >> edge in the edit rule.
	 * 
	 * <p>
	 * *Depends on:</br> {@link EditRuleTransientEffects#isPreservedNodeSearchedInModelA(EdgePair)}</br>
	 * {@link EditRuleTransientEffects#isPreservedNodeSearchedInModelB(EdgePair)}
	 * </p>
	 */
	private void createPreservedReferencePattterns() {
		
		// Intersection of edit rule LHS and RHS (preserved)
		for (EdgePair edge : getPreservedEdges(editRule)) {
			
			if (isPreservedEdgePreCondition()) {
				// Corresponding model A node:
				createPreservedEdge(
						recognitionRule,
						patterns.getTraceA(edge.getLhsEdge().getSource()),
						patterns.getTraceA(edge.getLhsEdge().getTarget()),
						edge.getType());
			}
			
			if (isPreservedEdgePostCondition()) {
				// Corresponding model B node:
				createPreservedEdge(
						recognitionRule,
						patterns.getTraceB(edge.getLhsEdge().getSource()),
						patterns.getTraceB(edge.getLhsEdge().getTarget()),
						edge.getType());
			}
		}
	}
	
	/**
	 * Creates the remove object patterns. That means the function will create a RemoveObject node
	 * and a model A node for each <<delete>> node in the edit rule.
	 */
	private void createRemoveObjectPatterns() {
		
		// Edit rule LHS \ RHS <<delete>> node
		for (Node node : getLHSMinusRHSNodes(editRule)) {
			
			// RemoveObject node
			NodePair removeObject = createPreservedNode(recognitionRule, "",
					SymmetricPackage.eINSTANCE.getRemoveObject());
			
			// Corresponding model A node
			NodePair node_a = createPreservedNode(recognitionRule, "A." + node.getName(),
					node.getType());
			
			// Link RemoveObject node and model A node
			createPreservedEdge(recognitionRule, removeObject, node_a,
					SymmetricPackage.eINSTANCE.getRemoveObject_Obj());
			
			// Save change for semantic change set
			changes.add(removeObject);
			
			// Save transformation pattern
			RemoveObjectPattern removeObjectPattern = new RemoveObjectPattern(
					removeObject, node_a, node);
			patterns.addRemoveObjectPattern(removeObjectPattern);
		}
	}
	
	/**
	 * Creates the remove reference patterns. That means the function will create a RemoveRefernece
	 * node linked to the corresponding model A source and target nodes. It also create and link a
	 * new EReferenc type node if necessary. Otherwise it will only link the type node if it already
	 * exists.
	 */
	private void createRemoveReferencePatterns() {
		
		// Edit rule LHS \ RHS <<delete>> node
		for (Edge edge : getLHSMinusRHSEdges(editRule)) {
			NodePair removeReference = createPreservedNode(recognitionRule, "",
					SymmetricPackage.eINSTANCE.getRemoveReference());
			
			// Add EReference type node if necessary
			NodePair typeNode = eReferenceTypeNodes.get(edge.getType());
			
			if (typeNode == null) {
				typeNode = createPreservedNodeWithAttribute(recognitionRule, "",
						EcorePackage.eINSTANCE.getEReference(),
						EcorePackage.eINSTANCE.getENamedElement_Name(), edge.getType().getName(), true);
				eReferenceTypeNodes.put(edge.getType(), typeNode);
			}
			
			// Create edges
			createPreservedEdge(recognitionRule, removeReference,
					patterns.getTraceA(edge.getSource()),
					SymmetricPackage.eINSTANCE.getRemoveReference_Src());
			createPreservedEdge(recognitionRule, removeReference,
					patterns.getTraceA(edge.getTarget()),
					SymmetricPackage.eINSTANCE.getRemoveReference_Tgt());
			createPreservedEdge(recognitionRule, removeReference, typeNode,
					SymmetricPackage.eINSTANCE.getRemoveReference_Type());
			
			EdgePair edge_a = createPreservedEdge(recognitionRule, patterns.getTraceA(edge.getSource()),
					patterns.getTraceA(edge.getTarget()), edge.getType());
			
			// Save change for semantic change set
			changes.add(removeReference);
			
			// Save transformation pattern
			RemoveReferencePattern removeReferencePattern = new RemoveReferencePattern(
					removeReference, typeNode, edge_a, edge);
			patterns.addRemoveReferencePattern(removeReferencePattern);
		}
	}
	
	/**
	 * Creates the add object patterns. That means the function will create a AddObject node and a
	 * model B node for each <<create>> node in the edit rule.
	 */
	private void createAddObjectPattterns() {
		
		// Edit rule RHS \ LHS <<create>> node
		for (Node node : getRHSMinusLHSNodes(editRule)) {
			
			// AddObject node
			NodePair addObject = createPreservedNode(recognitionRule, "",
					SymmetricPackage.eINSTANCE.getAddObject());
			
			// Corresponding model A node
			NodePair node_b = createPreservedNode(recognitionRule, "B." + node.getName(),
					node.getType());
			
			// Link AddObject node and model B node
			createPreservedEdge(recognitionRule, addObject, node_b,
					SymmetricPackage.eINSTANCE.getAddObject_Obj());
			
			// Save change for semantic change set
			changes.add(addObject);
			
			// Save transformation pattern
			AddObjectPattern addObjectPattern = new AddObjectPattern(addObject, node_b, node);
			patterns.addAddObjectPattern(addObjectPattern);
		}
	}
	
	/**
	 * Creates the add reference patterns. That means the function will create a AddRefernece node
	 * linked to the corresponding model B source and target nodes. It also create and link a new
	 * EReferenc type node if necessary. Otherwise it will only link the type node if it already
	 * exists.
	 */
	private void createAddReferencePattern() {
		
		// Edit rule RHS \ LHS (added)
		for (Edge edge : getRHSMinusLHSEdges(editRule)) {
			NodePair addReference = createPreservedNode(recognitionRule, "",
					SymmetricPackage.eINSTANCE.getAddReference());
			
			// Add reference type node
			NodePair typeNode = eReferenceTypeNodes.get(edge.getType());
			
			if (typeNode == null) {
				typeNode = createPreservedNodeWithAttribute(recognitionRule, "",
						EcorePackage.eINSTANCE.getEReference(),
						EcorePackage.eINSTANCE.getENamedElement_Name(), edge.getType().getName(), true);
				eReferenceTypeNodes.put(edge.getType(), typeNode);
			}
			
			// Create edges
			createPreservedEdge(recognitionRule, addReference,
					patterns.getTraceB(edge.getSource()),
					SymmetricPackage.eINSTANCE.getAddReference_Src());
			createPreservedEdge(recognitionRule, addReference,
					patterns.getTraceB(edge.getTarget()),
					SymmetricPackage.eINSTANCE.getAddReference_Tgt());
			createPreservedEdge(recognitionRule, addReference, typeNode,
					SymmetricPackage.eINSTANCE.getAddReference_Type());
			
			EdgePair edge_b = createPreservedEdge(recognitionRule, patterns.getTraceB(edge.getSource()),
					patterns.getTraceB(edge.getTarget()), edge.getType());
			
			// Save change for semantic change set
			changes.add(addReference);
			
			// Save transformation pattern
			AddReferencePattern addReferencePattern = new AddReferencePattern(
					addReference, typeNode, edge_b, edge);
			patterns.addAddReferencePattern(addReferencePattern);
		}
	}
	
	/**
	 * Creates a semantic change node and links it to the given atomic change nodes in the given
	 * recognition rule.
	 */
	private void createChangeSet() {
		// Create new semantic change set node
		Node cs = HenshinFactory.eINSTANCE.createNode(recognitionRule.getRhs(),
				SymmetricPackage.eINSTANCE.getSemanticChangeSet(), "ChangeSet");
		
		// Name attribute
		Attribute attrName = HenshinFactory.eINSTANCE.createAttribute();
		attrName.setType(SymmetricPackage.eINSTANCE.getSemanticChangeSet_Name());
		attrName.setValue("\"" + this.editRule.getModule().getName() + "\"");
		attrName.setNode(cs);
		
		// Description attribute from MODULE
		Attribute attrDescription = HenshinFactory.eINSTANCE.createAttribute();
		attrDescription.setType(SymmetricPackage.eINSTANCE.getSemanticChangeSet_Description());
		String description = this.editRule.getModule().getDescription();
		
		if ((description != null) && (!description.equals(""))) {
			attrDescription.setValue("\"" + this.editRule.getModule().getDescription() + "\"");
		}
		else {
			attrDescription.setValue("\"" + "n/a" + "\"");
		}
		attrDescription.setNode(cs);
		
		// editRName attribute
		Attribute editRName = HenshinFactory.eINSTANCE.createAttribute();
		editRName.setType(SymmetricPackage.eINSTANCE.getSemanticChangeSet_EditRName());
		editRName.setValue("\"" + this.editRule.getModule().getName() + "\"");
		editRName.setNode(cs);
		
		// recognitionRName attribute
		Attribute recognitionRName = HenshinFactory.eINSTANCE.createAttribute();
		recognitionRName.setType(SymmetricPackage.eINSTANCE.getSemanticChangeSet_RecognitionRName());
		recognitionRName.setValue("\"" + TransformationConstants.RECOGNITION_UNIT_PREFIX
				+ this.editRule.getModule().getName() + "\"");
		recognitionRName.setNode(cs);
		
		// Priority attribute
		Attribute attrPriority = HenshinFactory.eINSTANCE.createAttribute();
		attrPriority.setType(SymmetricPackage.eINSTANCE.getSemanticChangeSet_Priority());
		attrPriority.setNode(cs);
		
		Integer priority = EditRuleAnnotations.getPriority(editRule.getModule());
		
		if (priority != null) {
			// Set value from the edit-rule:
			attrPriority.setValue("" + priority);
		} else {
			// Set default value:
			if (atomic) {
				attrPriority.setValue("" + TransformationConstants.ATOMIC_PRIORITY);
			} else {
				attrPriority.setValue("" + TransformationConstants.COMPLEX_PRIORITY);
			}
		}
		
		// Number of application conditions
		Attribute attrNumberOfACs = HenshinFactory.eINSTANCE.createAttribute();
		attrNumberOfACs.setType(SymmetricPackage.eINSTANCE.getSemanticChangeSet_NumberOfACs());
		attrNumberOfACs.setValue("" + countNumberOfACs());
		attrNumberOfACs.setNode(cs);
		
		// Number of parameters
		Attribute attrNumberOfParams = HenshinFactory.eINSTANCE.createAttribute();
		attrNumberOfParams.setType(SymmetricPackage.eINSTANCE.getSemanticChangeSet_NumberOfParams());
		attrNumberOfParams.setValue("" + countNumberOfParams());
		attrNumberOfParams.setNode(cs);
		
		// Abstraction level attribute
		Attribute attrAbstraction = HenshinFactory.eINSTANCE.createAttribute();
		attrAbstraction.setType(SymmetricPackage.eINSTANCE.getSemanticChangeSet_RefinementLevel());
		attrAbstraction.setValue("" + calculateAbstractionLevel());
		attrAbstraction.setNode(cs);
		
		// Add low-level changes to semantic change set:
		for (NodePair nodePair : changes) {
			HenshinFactory.eINSTANCE.createEdge(cs, nodePair.getRhsNode(),
					SymmetricPackage.eINSTANCE.getSemanticChangeSet_Changes());
		}
		
		// Add semantic change set to difference
		NodePair diff = createPreservedNode(recognitionRule, "",
				SymmetricPackage.eINSTANCE.getSymmetricDifference());
		HenshinFactory.eINSTANCE.createEdge(diff.getRhsNode(), cs,
				SymmetricPackage.eINSTANCE.getSymmetricDifference_ChangeSets());
		
		// Save nodes
		semanticChangeSet = cs;
		difference = diff;
	}
	
	/**
	 * Counts the number of applications conditions contained in the rule/SCS.
	 * 
	 * NACs and PACs are taken into consideration
	 * 
	 * @return the number of application conditions.
	 */
	private int countNumberOfACs() {
		
		// Count of ACs
		int countOfAcs = 0;
		
		countOfAcs += HenshinRuleAnalysisUtilEx.getRequireNodes(editRule).size();
		countOfAcs += HenshinRuleAnalysisUtilEx.getForbidNodes(editRule).size();
		
		return countOfAcs;
		
	}
	
	/**
	 * Counts the number of parameters contained in the rule/SCS.
	 * 
	 * The less parameters are definable, the more specialized the rule is
	 * 
	 * @return the number of parameters.
	 */
	private int countNumberOfParams() {
		
		// Count of Parameters
		int countOfParams = 0;
		
		for (Parameter parameter : editRule.getParameters()) {
			// Parameter is a Unit-Parameter:
			if (ParameterInfo.getOutermostParameter(parameter) != null) {
				countOfParams++;
			}
		}
		
		return countOfParams;
	}
	
	/**
	 * Calculate the abstraction level by summing all super types of all <<create>>, <<delete>> and
	 * <<preserve>> nodes. Take also the number of elements into account.
	 * 
	 * @return the abstraction level.
	 */
	private int calculateAbstractionLevel() {
		
		// Calculate abstraction level
		int abstractionLevel = 0;
		
		// Edit rule RHS \ LHS <<create>> node
		for (Node node : getRHSMinusLHSNodes(editRule)) {
			abstractionLevel += node.getType().getEAllSuperTypes().size();
			abstractionLevel += 1;
		}
		
		// Edit rule LHS \ RHS <<delete>> node
		for (Node node : getLHSMinusRHSNodes(editRule)) {
			abstractionLevel += node.getType().getEAllSuperTypes().size();
			abstractionLevel += 1;
			
		}
		
		// Intersection of edit rule LHS and RHS <<preserve>> node
		for (Node node : getLHSIntersectRHSNodes(editRule)) {
			abstractionLevel += node.getType().getEAllSuperTypes().size();
			abstractionLevel += 1;
		}
		
		return abstractionLevel;
	}
	
	/**
	 * Utility-function creates an attribute value content check. That means the function copies the
	 * attribute from the edit rule into the recognition rule. It also check the used parameter of
	 * the attribute. Parameters are grouped in "unit parameters" and "rule parameters". Unit
	 * parameters are input parameters coming from outside of the rule. Rule parameters are set
	 * inside the rule (e.g. from another value). We have to know this because we can not make a
	 * content check of attributes with unit parameters and operators like '+,-,*,/' inside of the
	 * recognition rule.
	 * 
	 * @param node
	 *            the <<preserve>> recognition rule node.
	 * @param attribute
	 *            the edit rule attribute.
	 */
	private void createAttributeValueChangeContentCheck(NodePair node, Attribute attribute) {
		
		// Generate attribute content check
		String value = attribute.getValue();
		EAttribute type = attribute.getType();
		
		// Operator '+,-,/,*' is not supported for incoming unit parameters
		if (!Pattern.matches("\".*?\"", value) && Pattern.matches(".*[+-/*].*", value)) {
			// // Extract parameters from attribute
			// Set<Parameter> usedParameter = ParameterInfo.getUsedParameters(editRule, attribute);
			//
			// // Check for unit parameter
			// for (Parameter parameter : usedParameter) {
			// if (unitParameter.containsKey(parameter)) {
			// LogUtil.log(LogEvent.WARNING, WARNING_OPERATOR_ON_CONTENT_CHECK);
			// return;
			// }
			// }
			
			/*
			 * TODO: TK (22.Sept.2013): Content-Check disables for all parameters (also rule
			 * parameters) if attribute value is a JavaScript expression.
			 */
			
			LogUtil.log(LogEvent.WARNING, "Unsupported attribute with a JavaScript expression!");
			return;
		}
		
		// Node attribute
		createPreservedAttribute(node, type, value, true);
	}
	
	/**
	 * (Meta-) attributes which are not changeable, derived or transient are unconsidered while
	 * model comparison.
	 * 
	 * @param attribute
	 *            The attribute to test.
	 * @return <code>true</code> if the attribute is unconsidered while model comparison;
	 *         <code>false</code> otherwise;
	 */
	private boolean isUnconsideredAttribute(Attribute attribute) {
		if (EMFMetaAccess.isUnconsideredStructualFeature(attribute.getType())) {
			LogUtil.log(LogEvent.WARNING, "");
			LogUtil.log(LogEvent.WARNING, "WARNING: Unconsidered (not changeable, transient, derived) attribute in edit rule! Skip that!");
			LogUtil.log(LogEvent.WARNING, "Edit rule: " + editRule.getName());
			LogUtil.log(LogEvent.WARNING, "Node:      " + attribute.getNode());
			LogUtil.log(LogEvent.WARNING, "Attribute: " + attribute + " (type: " + attribute.getType() + ")");
			LogUtil.log(LogEvent.WARNING, "");
			
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * @return the edit rule from which the recognition rule will be created.
	 */
	public Rule getEditRule() {
		return editRule;
	}
	
	/**
	 * @return the recognition rule which will be created by <code>transform()</code>.
	 */
	public Rule getRecognitionRule() {
		return recognitionRule;
	}
	
	/**
	 * @return the generated transformation patterns and traces.
	 */
	public TransformationPatterns getTransformationPatterns() {
		return patterns;
	}
	
	/**
	 * @return the recognition rule Difference node.
	 */
	public NodePair getDifference() {
		return difference;
	}
	
	/**
	 * @return the recognition rule semantic change set node.
	 */
	public Node getSemanticChangeSet() {
		return semanticChangeSet;
	}
	
	/**
	 * Collected unit parameters. Unit parameters are input parameters coming from outside of the
	 * rule. Map <editParameter, recognitionParameter>
	 * 
	 * @return the unit parameters.
	 */
	public Map<Parameter, Parameter> getUnitParameter() {
		return unitParameter;
	}
	
	/**
	 * Collects rule parameters. Rule parameters are set inside the rule (e.g. from another value).
	 * Map <editParameter, recognitionParameter>
	 * 
	 * @return the rule parameters.
	 */
	public Map<Parameter, Parameter> getRuleParameter() {
		return ruleParameter;
	}
	
	/**
	 * @return the atomic changes of the recognition rule semantic change set.
	 */
	public List<NodePair> getChanges() {
		return changes;
	}
	
	/**
	 * @return all recognition rule EReference type nodes.
	 */
	public Map<EReference, NodePair> geteReferenceTypeNodes() {
		return eReferenceTypeNodes;
	}
	
	/**
	 * @return all recognition rule EAttribute type nodes.
	 */
	public Map<EAttribute, NodePair> geteAttributeTypeNodes() {
		return eAttributeTypeNodes;
	}
	
	/**
	 * Checks whether all imported packages are registered in the global EMF Package registry.
	 */
	private void checkImportedEPackagesAvailability() {
		for (EPackage p : this.editRule.getModule().getImports()) {
			String error = "The package "
					+ p
					+ " cannot be found in the global EMF Package registry. Make sure the respective domain bundle (<domain>.model) is properly loaded.";
			
			assert (p.getNsURI() != null) : error;
			assert (EPackage.Registry.INSTANCE.containsKey(p.getNsURI())) : error;
		}
		
	}
	
	@Override
	public Map<Unit, TransformationPatterns> getPatterns() {
		return Collections.singletonMap((Unit) editRule, patterns);
	}
}
