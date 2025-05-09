package org.sidiff.difference.lifting.edit2recognition.internal;

import static org.sidiff.common.henshin.HenshinMultiRuleUtil.createMultiMapping;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.findMappingByOrigin;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.getAttributeByType;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.getLHSMappings;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.getLHSMinusRHSEdges;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.getLHStoRHSChangingAttributes;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.getRHSChangingAttributes;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.getRHSMappings;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.getRHSMinusLHSEdges;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isKernelRule;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isNodeMapped;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.sidiff.common.henshin.view.AttributePair;
import org.sidiff.common.henshin.view.NodePair;
import org.sidiff.difference.lifting.edit2recognition.EditPattern2RecognitionPattern;
import org.sidiff.difference.lifting.edit2recognition.exceptions.EditToRecognitionException;
import org.sidiff.difference.lifting.edit2recognition.traces.AddObjectPattern;
import org.sidiff.difference.lifting.edit2recognition.traces.AddReferencePattern;
import org.sidiff.difference.lifting.edit2recognition.traces.AttributeValueChangePattern;
import org.sidiff.difference.lifting.edit2recognition.traces.CorrespondencePattern;
import org.sidiff.difference.lifting.edit2recognition.traces.RemoveObjectPattern;
import org.sidiff.difference.lifting.edit2recognition.traces.RemoveReferencePattern;
import org.sidiff.difference.lifting.edit2recognition.traces.TransformationPatterns;
import org.sidiff.difference.lifting.edit2recognition.util.TransformationConstants;
import org.sidiff.difference.symmetric.SymmetricPackage;

/**
 * <p>
 * Transforms an multi rule into a recognition multi rule.
 * </p>
 * 
 * @author Manuel Ohrndorf
 */
public class EditMulti2RecognitionMulti implements EditPattern2RecognitionPattern {
	
	/**
	 * The unit containing the multi-rule.
	 */
	private Unit editContainingUnit;
	
	/**
	 * The input edit rule.
	 */
	private Rule editRootKernel;

	/**
	 * The generated recognition rule.
	 */
	private Rule recognitionRootKernel;
	
	/**
	 * The semantic change set of the root recognition rule.
	 */
	private Node rootSemanticChangeSet;
	
	/**
	 * Number of ACs of all Multi-Rules (for synchronization).
	 */
	private int numberOfACs = 0;
	
	/**
	 * Whether this is an <b>atomic</b> or a <b>complex</b> edit operation.
	 */
	private boolean atomic;
	
	/**
	 * All transformed units
	 */
	private Set<EditPattern2RecognitionPattern> transformations;

	/**
	 * Transforms an multi-rule into a recognition multi-rule.
	 * 
	 * @param editContainingUnit
	 *            The unit containing the multi-rule.
	 * @param atomic
	 *            Whether this is an <b>atomic</b> or a <b>complex</b> edit operation.
	 */
	public EditMulti2RecognitionMulti(Unit editContainingUnit, boolean atomic) {
		
		this.editContainingUnit = editContainingUnit;
		this.atomic = atomic;
		
		// Get kernel rule:
		this.editRootKernel = (Rule) editContainingUnit.getSubUnits(false).get(0);
		assert isKernelRule(editRootKernel) : "No multi rules found!"; 
		
		// Save all transformed units:
		transformations = new HashSet<EditPattern2RecognitionPattern>();
	}
	
	/**
	 * Transforms an multi rule into a recognition multi rule.
	 * 
	 * @return the recognition multi rule.
	 * @throws EditToRecognitionException 
	 */
	public Rule transform() throws EditToRecognitionException {

		// Transform kernel rule into recognition rule:
		EditRule2RecognitionRule kernelTF = new EditRule2RecognitionRule(editRootKernel, editContainingUnit, atomic);
		recognitionRootKernel = kernelTF.transform();
		rootSemanticChangeSet = kernelTF.getSemanticChangeSet();

		// Save transformed kernel rule:
		transformations.add(kernelTF);

		// Set semantic change set priority:
		Attribute kernelPriority = getAttributeByType(rootSemanticChangeSet.getAttributes(),
				SymmetricPackage.eINSTANCE.getSemanticChangeSet_Priority());
		kernelPriority.setValue("" + TransformationConstants.MULTI_RULE_PRIORITY);
		
		// Sum semantic change set number of ACs: (synchronized later)
		Attribute kernelNumberOfACs = getAttributeByType(kernelTF.getSemanticChangeSet().getAttributes(),
				SymmetricPackage.eINSTANCE.getSemanticChangeSet_NumberOfACs());

		numberOfACs += Integer.valueOf(kernelNumberOfACs.getValue());

		// Transform multi rules into recognition multi rules:
		transformMultiRules(kernelTF);
		
		// Synchronize number of ACs:
		for (EditPattern2RecognitionPattern tf : transformations) {
			if (tf instanceof EditRule2RecognitionRule) {
				Attribute attr_numberOfACs = getAttributeByType(
						((EditRule2RecognitionRule) tf).getSemanticChangeSet().getAttributes(),
						SymmetricPackage.eINSTANCE.getSemanticChangeSet_NumberOfACs());
				attr_numberOfACs.setValue("" + numberOfACs);
			}
		}

		return recognitionRootKernel;
	}
	
	/**
	 * Transforms the multi rules of the given kernel rule into recognition multi rules. All deeper
	 * multi rules will be transformed recursively.
	 * 
	 * @param kernelTF
	 *            The kernel rule transformation engine.
	 * @throws EditToRecognitionException 
	 */
	private void transformMultiRules(EditRule2RecognitionRule kernelTF) throws EditToRecognitionException {
		
		// Transform multi rules into recognition multi rules:
		Rule editKernel = kernelTF.getEditRule();
		Rule recognitionKernel = kernelTF.getRecognitionRule();
		
		for (Rule editMulti : editKernel.getMultiRules()) {
			EditRule2RecognitionRule multiTF = new EditRule2RecognitionRule(editMulti, editContainingUnit, atomic);
			Rule recognitionMulti = multiTF.transform();
			recognitionKernel.getMultiRules().add(recognitionMulti);

			// Embed the multi rule into the kernel rule:
			embedIntoKernel(kernelTF, multiTF);

			// Save transformed multi rule:
			transformations.add(multiTF);
			
			// Transform deeper multi rules:
			transformMultiRules(multiTF);
		}
	}

	/**
	 * Embeds the kernel recognition rule into the multi recognition rule. Thats means to create LHS
	 * and RHS mappings from each node in the kernel rule to the corresponding element in the multi rule.
	 * 
	 * @param kernelTF
	 *            the kernel rule transformation engine.
	 * @param multiTF
	 *            the multi rule transformation engine.
	 * @throws CreateAmalgamationMappingException
	 */
	private void embedIntoKernel(EditRule2RecognitionRule kernelTF, EditRule2RecognitionRule multiTF) {
		
		// Create pattern mappings
		createCorrespondenceMappings(kernelTF, multiTF);
		createRemoveObjectMappings(kernelTF, multiTF);
		createAddObjectMappings(kernelTF, multiTF);
		createRemoveReferenceMappings(kernelTF, multiTF);
		createAddReferenceMappings(kernelTF, multiTF);
		createAttributeValueChangeMappings(kernelTF, multiTF);
		
		// Recognition multi rule
		Rule recognitionMulti = multiTF.getRecognitionRule();

		// Map semantic change set
		createMultiMapping(recognitionMulti, kernelTF.getSemanticChangeSet(),
				multiTF.getSemanticChangeSet());

		// Synchronize semantic change set priority:
		Attribute kernelPriority = getAttributeByType(rootSemanticChangeSet.getAttributes(),
				SymmetricPackage.eINSTANCE.getSemanticChangeSet_Priority());
		Attribute multiPriority = getAttributeByType(multiTF.getSemanticChangeSet().getAttributes(),
				SymmetricPackage.eINSTANCE.getSemanticChangeSet_Priority());
		multiPriority.setValue(kernelPriority.getValue());

		// Synchronize semantic change set name:
		Attribute kernelName = getAttributeByType(rootSemanticChangeSet.getAttributes(),
				SymmetricPackage.eINSTANCE.getSemanticChangeSet_Name());
		Attribute multiName = getAttributeByType(multiTF.getSemanticChangeSet().getAttributes(),
				SymmetricPackage.eINSTANCE.getSemanticChangeSet_Name());
		multiName.setValue(kernelName.getValue());

		// Synchronize semantic change set refinement level:
		// NOTE: All Multi-Rules are optional => Use only Kernel-Rule refinement.
		Attribute kernelRefinement = getAttributeByType(rootSemanticChangeSet.getAttributes(),
				SymmetricPackage.eINSTANCE.getSemanticChangeSet_RefinementLevel());
		Attribute multiRefinement = getAttributeByType(multiTF.getSemanticChangeSet().getAttributes(),
				SymmetricPackage.eINSTANCE.getSemanticChangeSet_RefinementLevel());
		multiRefinement.setValue(kernelRefinement.getValue());
		
		// Sum semantic change set number of ACs: (synchronized later)
		Attribute multiNumberOfACs = getAttributeByType(multiTF.getSemanticChangeSet().getAttributes(),
				SymmetricPackage.eINSTANCE.getSemanticChangeSet_NumberOfACs());

		numberOfACs += Integer.valueOf(multiNumberOfACs.getValue());

		// Map difference
		createMultiMapping(recognitionMulti, kernelTF.getDifference(), multiTF.getDifference());
	}

	/**
	 * Creates multi mappings for each correspondence pattern.
	 * 
	 * @param kernelTF
	 *            the kernel rule transformation engine.
	 * @param multiTF
	 *            the multi rule transformation engine.
	 */
	private void createCorrespondenceMappings(
			EditRule2RecognitionRule kernelTF, EditRule2RecognitionRule multiTF) {

		Rule recognitionMulti = multiTF.getRecognitionRule();
		Rule editMulti = multiTF.getEditRule();
		Rule editKernel = kernelTF.getEditRule();
		
		for (Mapping mapping : getPreservationNodeMappings(editKernel, editMulti)) {

			// Get mapped correspondence patterns
			CorrespondencePattern kernelPattern = kernelTF.getTransformationPatterns()
					.getCorrespondecePattern(mapping.getOrigin());

			CorrespondencePattern multiPattern = multiTF.getTransformationPatterns()
					.getCorrespondecePattern(mapping.getImage());

			if (kernelPattern.getNodeA() != null) {
				// Map Model A node
				createMultiMapping(recognitionMulti, kernelPattern.getNodeA(), multiPattern.getNodeA());
			}

			if (kernelPattern.getNodeB() != null) {
				// Map Model B node
				createMultiMapping(recognitionMulti, kernelPattern.getNodeB(), multiPattern.getNodeB());
			}

			if (kernelPattern.getCorrespondence() != null) {
				// Map Correspondence node
				createMultiMapping(recognitionMulti,
						kernelPattern.getCorrespondence(), multiPattern.getCorrespondence());
			}
		}
	}

	/**
	 * Creates multi mappings for each remove object pattern.
	 * 
	 * @param kernelTF
	 *            the kernel rule transformation engine.
	 * @param multiTF
	 *            the multi rule transformation engine.
	 */
	private void createRemoveObjectMappings(
			EditRule2RecognitionRule kernelTF, EditRule2RecognitionRule multiTF) {

		Rule recognitionMulti = multiTF.getRecognitionRule();
		Rule editMulti = multiTF.getEditRule();
		Rule editKernel = kernelTF.getEditRule();
		
		for (Mapping mapping : getDeletionNodeMappings(editKernel, editMulti)) {

			// Get mapped remove object patterns
			RemoveObjectPattern kernelPattern = kernelTF.getTransformationPatterns()
					.getRemoveObjectPattern(mapping.getOrigin());

			RemoveObjectPattern multiPattern = multiTF.getTransformationPatterns()
					.getRemoveObjectPattern(mapping.getImage());

			// Map RemoveObject node
			createMultiMapping(recognitionMulti,
					kernelPattern.getRemoveObject(), multiPattern.getRemoveObject());

			// Map Model A node
			createMultiMapping(recognitionMulti, kernelPattern.getNodeA(), multiPattern.getNodeA());
		}
	}

	/**
	 * Creates multi mappings for each add object pattern.
	 * 
	 * @param kernelTF
	 *            the kernel rule transformation engine.
	 * @param multiTF
	 *            the multi rule transformation engine.
	 */
	private void createAddObjectMappings(
			EditRule2RecognitionRule kernelTF, EditRule2RecognitionRule multiTF) {

		Rule recognitionMulti = multiTF.getRecognitionRule();
		Rule editMulti = multiTF.getEditRule();
		Rule editKernel = kernelTF.getEditRule();
		
		for (Mapping mapping : getCreationNodeMappings(editKernel, editMulti)) {

			// Get mapped remove object patterns
			AddObjectPattern kernelPattern = kernelTF.getTransformationPatterns()
					.getAddObjectPattern(mapping.getOrigin());

			AddObjectPattern multiPattern = multiTF.getTransformationPatterns()
					.getAddObjectPattern(mapping.getImage());

			// Map RemoveObject node
			createMultiMapping(recognitionMulti,
					kernelPattern.getAddObject(), multiPattern.getAddObject());

			// Map Model A node
			createMultiMapping(recognitionMulti,
					kernelPattern.getNodeB(), multiPattern.getNodeB());
		}

	}

	/**
	 * Creates multi mappings for each remove reference pattern.
	 * 
	 * @param kernelTF
	 *            the kernel rule transformation engine.
	 * @param multiTF
	 *            the multi rule transformation engine.
	 */
	private void createRemoveReferenceMappings(
			EditRule2RecognitionRule kernelTF, EditRule2RecognitionRule multiTF) {

		Rule recognitionMulti = multiTF.getRecognitionRule();
		Rule editMulti = multiTF.getEditRule();
		Rule editKernel = kernelTF.getEditRule();
		
		/*
		 * Map EReference type node
		 */

		for (EReference eRefence : kernelTF.geteReferenceTypeNodes().keySet()) {
			// Find kernel and multi rule node by the EReference type. Remember
			// there is only 1 type node for each EReference type in each rule.
			NodePair kernelTypeNode = kernelTF.geteReferenceTypeNodes().get(eRefence);
			NodePair multiTypeNode = multiTF.geteReferenceTypeNodes().get(eRefence);

			// Create mapping for EReference type node
//			if (multiTypeNode != null)
			createMultiMapping(recognitionMulti, kernelTypeNode, multiTypeNode);
		}

		/*
		 * Map RemoveReference node
		 */

		for (Entry<Edge, Edge> mapping : getDeletionEdgeMappings(editKernel, editMulti).entrySet()) {

			// Get mapped remove reference patterns
			RemoveReferencePattern kernelPattern = kernelTF.getTransformationPatterns()
					.getRemoveReferencePattern(mapping.getKey());

			RemoveReferencePattern multiPattern = multiTF.getTransformationPatterns()
					.getRemoveReferencePattern(mapping.getValue());

			// Map RemoveReference node
			createMultiMapping(recognitionMulti,
					kernelPattern.getRemoveReference(), multiPattern.getRemoveReference());
		}
	}

	/**
	 * Creates multi mappings for each create reference pattern.
	 * 
	 * @param kernelTF
	 *            the kernel rule transformation engine.
	 * @param multiTF
	 *            the multi rule transformation engine.
	 */
	private void createAddReferenceMappings(
			EditRule2RecognitionRule kernelTF, EditRule2RecognitionRule multiTF) {

		Rule recognitionMulti = multiTF.getRecognitionRule();
		Rule editMulti = multiTF.getEditRule();
		Rule editKernel = kernelTF.getEditRule();
		
		// Map AddReference nodes
		for (Entry<Edge, Edge> mapping : getCreationEdgeMappings(editKernel, editMulti).entrySet()) {

			// Get mapped remove reference patterns
			AddReferencePattern kernelPattern = kernelTF.getTransformationPatterns()
					.getAddReferencePattern(mapping.getKey());

			AddReferencePattern multiPattern = multiTF.getTransformationPatterns()
					.getAddReferencePattern(mapping.getValue());

			// Map RemoveReference node
			createMultiMapping(recognitionMulti,
					kernelPattern.getAddReference(), multiPattern.getAddReference());
		}
	}

	/**
	 * Creates multi mappings for each attribute value change pattern.
	 * 
	 * @param kernelTF
	 *            the kernel rule transformation engine.
	 * @param multiTF
	 *            the multi rule transformation engine.
	 */
	private void createAttributeValueChangeMappings(
			EditRule2RecognitionRule kernelTF, EditRule2RecognitionRule multiTF) {

		Rule recognitionMulti = multiTF.getRecognitionRule();
		Rule editMulti = multiTF.getEditRule();
		Rule editKernel = kernelTF.getEditRule();
		
		/*
		 * Map EAttribute type nodes
		 */

		for (EAttribute eAttribute : kernelTF.geteAttributeTypeNodes().keySet()) {
			// Find kernel and multi rule node by the EAttribute type. Remember
			// there is only 1 type node for each EAttribute type in each rule.
			NodePair kernelTypeNode = kernelTF.geteAttributeTypeNodes().get(eAttribute);
			NodePair multiTypeNode = multiTF.geteAttributeTypeNodes().get(eAttribute);

			// Create mapping for EAttribute type node
			createMultiMapping(recognitionMulti, kernelTypeNode, multiTypeNode);
		}

		/*
		 * Map AttributeValueChange node
		 */

		// Map AttributeValueChange nodes
		for (Entry<Attribute, Attribute> mapping : getChangingAttributeMappings(editKernel, editMulti).entrySet()) {

			// Get mapped attribute value change patterns
			AttributeValueChangePattern kernelPattern = kernelTF.getTransformationPatterns()
					.getAttributeValueChangePattern(mapping.getKey());

			AttributeValueChangePattern multiPattern = multiTF.getTransformationPatterns()
					.getAttributeValueChangePattern(mapping.getValue());

			// Filter optional Attribute-Value-Changes:
			if ((kernelPattern != null) && (multiPattern != null)) {
				// Map RemoveReference node
				createMultiMapping(recognitionMulti,
						kernelPattern.getAttributeValueChange(), multiPattern.getAttributeValueChange());	
			}
		}
	}

	/**
	 * Returns all multi mappings of << preserve >> nodes.
	 * 
	 * @param kernelRule
	 *            the kernel rule.
	 * @param multiRule
	 *            the multi rule of the target image node.
	 * @return the multi mappings.
	 */
	private List<Mapping> getPreservationNodeMappings(Rule kernelRule, Rule multiRule) {

		List<Mapping> preserved = new LinkedList<Mapping>();

		for (Mapping mapping : getLHSMappings(multiRule.getMultiMappings())) {
			if (isNodeMapped(kernelRule.getMappings(), mapping.getOrigin())
					&& (mapping.getImage().getGraph().getRule() == multiRule)) {

				// Mapping of <<preserve>> node.
				preserved.add(mapping);
			}
		}

		return preserved;
	}

	/**
	 * Returns all multi mappings of << delete >> nodes.
	 * 
	 * @param kernelRule
	 *            the kernel rule.
	 * @param multiRule
	 *            the multi rule of the target image node.
	 * @return the multi mappings.
	 */
	private List<Mapping> getDeletionNodeMappings(Rule kernelRule, Rule multiRule) {

		List<Mapping> deleted = new LinkedList<Mapping>();

		for (Mapping mapping : getLHSMappings(multiRule.getMultiMappings())) {
			if (!(isNodeMapped(kernelRule.getMappings(), mapping.getOrigin()))
					&& (mapping.getImage().getGraph().getRule() == multiRule)) {

				// Mapping of <<delete>> node.
				deleted.add(mapping);
			}
		}

		return deleted;
	}

	/**
	 * Returns all multi mappings of << create >> nodes.
	 * 
	 * @param kernelRule
	 *            the kernel rule.
	 * @param multiRule
	 *            the multi rule of the target image node.
	 * @return the multi mappings.
	 */
	private List<Mapping> getCreationNodeMappings(Rule kernelRule, Rule multiRule) {

		List<Mapping> created = new LinkedList<Mapping>();

		for (Mapping mapping : getRHSMappings(multiRule.getMultiMappings())) {
			if (!(isNodeMapped(kernelRule.getMappings(), mapping.getOrigin()))
					&& (mapping.getImage().getGraph().getRule() == multiRule)) {

				// Mapping of <<create>> node.
				created.add(mapping);
			}
		}

		return created;
	}

	/**
	 * Returns all multi mappings of << delete >> edges.
	 * 
	 * @param kernelRule
	 *            the kernel rule.
	 * @param multiRule
	 *            the multi rule of the target image node.
	 * @return the multi mappings (Map<origin, image>).
	 */
	private Map<Edge, Edge> getDeletionEdgeMappings(Rule kernelRule, Rule multiRule) {

		// Map<origin, image>
		Map<Edge, Edge> deleteEdges = new HashMap<Edge, Edge>();

		// Secure correct mappings if there are some edges
		// with the same type an the same source and target nodes.
		// (Improbable but possibly)
		Set<Edge> usedMultiEdges = new HashSet<Edge>();

		for (Edge kernelEdge : getLHSMinusRHSEdges(kernelRule)) {
			Node kernelSource = kernelEdge.getSource();
			Mapping sourceMapping = findMappingByOrigin(getLHSMappings(multiRule.getMultiMappings()),
					kernelSource);

			Node kernelTarget = kernelEdge.getTarget();
			Mapping targetMapping = findMappingByOrigin(getLHSMappings(multiRule.getMultiMappings()),
					kernelTarget);

			Node multiSource = sourceMapping.getImage();
			Node multiTarget = targetMapping.getImage();

			for (Edge multiEdge : multiSource.getOutgoing()) {
				if ((multiEdge.getType() == kernelEdge.getType())
						&& (multiEdge.getTarget() == multiTarget)
						&& !usedMultiEdges.contains(multiEdge)) {

					// Create new mapping
					deleteEdges.put(kernelEdge, multiEdge);
					usedMultiEdges.add(multiEdge);
				}
			}
		}
		return deleteEdges;
	}

	/**
	 * Returns all multi mappings of << create >> edges.
	 * 
	 * @param kernelRule
	 *            the kernel rule.
	 * @param multiRule
	 *            the multi rule of the target image node.
	 * @return the multi mappings (Map<origin, image>).
	 */
	private Map<Edge, Edge> getCreationEdgeMappings(Rule kernelRule, Rule multiRule) {

		// Map<origin, image>
		Map<Edge, Edge> createEdges = new HashMap<Edge, Edge>();

		// Secure correct mappings if there are some edges
		// with the same type an the same source and target nodes.
		// (Improbable but possibly)
		Set<Edge> usedMultiEdges = new HashSet<Edge>();

		for (Edge kernelEdge : getRHSMinusLHSEdges(kernelRule)) {
			Node kernelSource = kernelEdge.getSource();
			Mapping sourceMapping = findMappingByOrigin(getRHSMappings(multiRule.getMultiMappings()),
					kernelSource);

			Node kernelTarget = kernelEdge.getTarget();
			Mapping targetMapping = findMappingByOrigin(getRHSMappings(multiRule.getMultiMappings()),
					kernelTarget);

			Node multiSource = sourceMapping.getImage();
			Node multiTarget = targetMapping.getImage();

			for (Edge multiEdge : multiSource.getOutgoing()) {
				if ((multiEdge.getType() == kernelEdge.getType())
						&& (multiEdge.getTarget() == multiTarget)
						&& !usedMultiEdges.contains(multiEdge)) {

					// Create new mapping
					createEdges.put(kernelEdge, multiEdge);
					usedMultiEdges.add(multiEdge);
				}
			}
		}
		return createEdges;
	}

	/**
	 * Returns all multi mappings of attributes of the form value1->value2 
	 * and of << create >> attributes in a << preserve >> node.
	 * 
	 * @param kernelRule
	 *            the kernel rule.
	 * @param multiRule
	 *            the multi rule of the target image node.
	 * @return the multi mappings (Map<origin, image>).
	 */
	private Map<Attribute, Attribute> getChangingAttributeMappings(Rule kernelRule, Rule multiRule) {

		// Map<origin, image>
		Map<Attribute, Attribute> changingAttributeMappings = new HashMap<Attribute, Attribute>();

		for (Attribute kernelAttribute : getChangingAttributes(kernelRule)) {
			
			Node kernelNode = kernelAttribute.getNode();
			Mapping mapping = findMappingByOrigin(getRHSMappings(
					multiRule.getMultiMappings()), kernelNode);
			Node multiNode = mapping.getImage();

			for (Attribute multiAttribute : multiNode.getAttributes()) {
				if ((multiAttribute.getType() == kernelAttribute.getType())
						&& (multiAttribute.getValue().equals(kernelAttribute.getValue()))) {

					changingAttributeMappings.put(kernelAttribute, multiAttribute);
				}
			}
		}
		return changingAttributeMappings;
	}

	/**
	 * Returns all attributes of the form value1->value2 and all << create >> attributes 
	 * in a << preserve >> node.
	 * 
	 * @param rule
	 *            the Henshin rule.
	 * @return the changing attributes (RHS attributes).
	 */
	private List<Attribute> getChangingAttributes(Rule rule) {

		// Unpacking value1->value2 attributes
		List<Attribute> changingAttributes = new LinkedList<Attribute>();

		for (AttributePair attribute : getLHStoRHSChangingAttributes(rule)) {
			Attribute rhsAttribute = attribute.getRhsAttribute();
			changingAttributes.add(rhsAttribute);
		}

		// All <<create>> attributes in a <<create>> node
		changingAttributes.addAll(getRHSChangingAttributes(rule));

		return changingAttributes;
	}

	@Override
	public Map<Unit, TransformationPatterns> getPatterns() {

		// Collect the traces for kernel rule and all multi rules:
		Map<Unit, TransformationPatterns> patterns = new HashMap<Unit, TransformationPatterns>();
		
		for (EditPattern2RecognitionPattern transformation : transformations) {
			patterns.putAll(transformation.getPatterns());
		}
		
		return patterns;
	}
}
