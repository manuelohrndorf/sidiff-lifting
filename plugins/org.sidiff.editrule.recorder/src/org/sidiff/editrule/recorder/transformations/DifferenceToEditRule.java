package org.sidiff.editrule.recorder.transformations;

import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.createCreateEdge;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.createCreateNode;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.createDeleteEdge;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.createDeleteNode;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.createPreservedEdge;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.createPreservedNode;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.SequentialUnit;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;
import org.sidiff.common.henshin.INamingConventions;
import org.sidiff.common.henshin.view.NodePair;
import org.sidiff.difference.symmetric.AddObject;
import org.sidiff.difference.symmetric.AddReference;
import org.sidiff.difference.symmetric.AttributeValueChange;
import org.sidiff.difference.symmetric.Change;
import org.sidiff.difference.symmetric.RemoveObject;
import org.sidiff.difference.symmetric.RemoveReference;
import org.sidiff.editrule.recorder.filters.IAttributeFilter;
import org.sidiff.editrule.recorder.filters.IObjectFilter;
import org.sidiff.editrule.recorder.filters.IReferenceFilter;
import org.sidiff.matching.model.Correspondence;

public class DifferenceToEditRule {
	
	protected static final String UNKNOWN_NAMES = "N";
	
	protected String eoName;
	
	protected Module editRule;
	
	protected Collection<Correspondence> correspondences;
	
	protected Collection<Change> changes;
	
	protected IObjectFilter objectFilter = IObjectFilter.DUMMY;
	
	protected IAttributeFilter attributeFilter = IAttributeFilter.DUMMY;
	
	protected IReferenceFilter referenceFilter = IReferenceFilter.DUMMY;
	
	protected Map<EObject, Node> traceA2LHS;
	
	protected Map<EObject, Node> traceB2RHS;
	
	public DifferenceToEditRule(String eoName, Collection<Correspondence> correspondences, Collection<Change> changes) {
		this.eoName = eoName;
		this.correspondences = correspondences;
		this.changes = changes;
	}
	
	public IObjectFilter getObjectFilter() {
		return objectFilter;
	}

	public void setObjectFilter(IObjectFilter objectFilter) {
		this.objectFilter = objectFilter;
	}

	public IAttributeFilter getAttributeFilter() {
		return attributeFilter;
	}

	public void setAttributeFilter(IAttributeFilter attributeFilter) {
		this.attributeFilter = attributeFilter;
	}

	public IReferenceFilter getReferenceFilter() {
		return referenceFilter;
	}

	public void setReferenceFilter(IReferenceFilter referenceFilter) {
		this.referenceFilter = referenceFilter;
	}

	public Module getEditRule() {
		
		if (editRule == null) {
			editRule = createEditRule();
		}
		
		return editRule;
	}
	
	public void addAttribute(EObject object, Object value, EAttribute type) {
		
		// Make sure the edit rule is created: 
		getEditRule();
		
		// Create attribute in corresponding node:
		Node node = traceA2LHS.containsKey(object) ? traceA2LHS.get(object) : traceB2RHS.get(object);
		
		// Overwrite existing attribute!
		if (node != null) {
			
			// Delete existing attribute:
			node.getAttributes().remove(node.getAttribute(type));
			
			// Create new attribute:
			HenshinRuleAnalysisUtilEx.createCreateAttribute(node, type, value.toString());
		}
	}

	private Module createEditRule() {
		
		// Create rule container:
		Module module = HenshinFactory.eINSTANCE.createModule();
		module.setName(eoName);
		
		SequentialUnit mainUnit = HenshinFactory.eINSTANCE.createSequentialUnit();
		mainUnit.setName(INamingConventions.MAIN_UNIT);
		module.getUnits().add(mainUnit);
		
		Rule editrule = HenshinFactory.eINSTANCE.createRule(eoName);
		module.getUnits().add(editrule);
		mainUnit.getSubUnits().add(editrule);
		
		// Transform difference to edit-rule:
		traceA2LHS = new HashMap<>();
		traceB2RHS = new HashMap<>();
		
		Set<String> names = new HashSet<>();
		names.add(UNKNOWN_NAMES);
		
		convertCorrespondences(correspondences, editrule, names);
		convertAttribteValueChange(changes);
		convertObjectChanges(changes, mainUnit, editrule, names);
		convertReferenceChanges(changes, editrule);
		convertContextEdges(correspondences, editrule);
		
		return module;
	}

	private void convertCorrespondences(Collection<Correspondence> correspondences, Rule editrule, Set<String> names) {
		
		// Preserve nodes:
		for (Correspondence correspondence : correspondences) {
			if (validate(correspondence)) {
				NodePair preserveNode = createPreservedNode(
						editrule, getName(correspondence, names), correspondence.getMatchedA().eClass());
				traceA2LHS.put(correspondence.getMatchedA(), preserveNode.getLhsNode());
				traceB2RHS.put(correspondence.getMatchedB(), preserveNode.getRhsNode());
			}
		}
	}
	
	private boolean validate(Correspondence correspondence) {
		
		if ((correspondence.getMatchedA() != null) && (correspondence.getMatchedB() != null)) {
			if (!objectFilter.filter(correspondence.getMatchedA())) {
				if (!objectFilter.filter(correspondence.getMatchedB())) {
					return true;
				}
			}
		}  else {
			System.err.println("Invalid Correspondence: " + correspondence);
		}
		return false;
	}
	
	private void convertAttribteValueChange(Collection<Change> changes) {
		
		// Attribute value changes:
		for (Change change : changes) {
			if (change instanceof AttributeValueChange) {
				if (validate((AttributeValueChange) change)) {
					AttributeValueChange avc = (AttributeValueChange) change;
					Node rhsNode = traceB2RHS.get(avc.getObjB());
					
					// Create attribute with parameter:
					Parameter param = HenshinFactory.eINSTANCE.createParameter(
							"in_" + rhsNode.getName() + "_" + avc.getType().getName());
					Attribute attr = HenshinFactory.eINSTANCE.createAttribute(
							rhsNode, avc.getType(), param.getName());
					rhsNode.getAttributes().add(attr);
				}
			}
		}
	}
	
	private boolean validate(AttributeValueChange avc) {
		
		if ((avc.getObjA() != null) && (avc.getObjB() != null) && (avc.getType() != null)) {
			if (!objectFilter.filter(avc.getObjA()) && !objectFilter.filter(avc.getObjB())) {
				Object valueA =  avc.getObjA().eGet(avc.getType());
				Object valueB =  avc.getObjB().eGet(avc.getType());
						
				if (!attributeFilter.filter(avc.getObjA(), valueA, avc.getType())) {
					if (!attributeFilter.filter(avc.getObjB(), valueB, avc.getType())) {
						return true;
					}
				}
			}
		} else {
			System.err.println("Invalid Attribute-Value-Change: " + avc);
		}
		return false;
	}

	private void convertObjectChanges(
			Collection<Change> changes, SequentialUnit mainUnit, Rule editrule, Set<String> names) {
		
		// Change nodes:
		for (Change change : changes) {

			// Delete nodes:
			if (change instanceof RemoveObject) {
				convertRemoveObject((RemoveObject) change, editrule, names);
			}
			
			// Create nodes:
			else if (change instanceof AddObject) {
				convertCreateObject((AddObject) change, mainUnit, editrule, names);
			}
		}
	}
	
	private void convertRemoveObject(RemoveObject removeObject, Rule editrule, Set<String> names) {
		
		if (filter(removeObject)) {
			Node removeNode = createDeleteNode(
					getName(removeObject.getObj(), names), 
					removeObject.getObj().eClass(), 
					editrule);
			traceA2LHS.put(removeObject.getObj(), removeNode);
		}
	}
	
	protected boolean filter(RemoveObject removeObject) {
		
		if ((removeObject.getObj() != null)) {
			if (!objectFilter.filter(removeObject.getObj())) {
				return true;
			}
		} else {
			System.err.println("Invalid Remove-Object: " + removeObject);
		}
		
		return false;
	}
	
	private void convertCreateObject(AddObject addObject, 
			SequentialUnit mainUnit, Rule editrule, Set<String> names) {
		
		if (filter(addObject)) {
			Node addNode = createCreateNode(
					getName(addObject.getObj(), names), 
					addObject.getObj().eClass(), 
					editrule);
			traceB2RHS.put(addObject.getObj(), addNode);
			
			// Map node to out-parameter:
			Parameter ruleAddNodeParameter = HenshinFactory.eINSTANCE.createParameter(addNode.getName());
			editrule.getParameters().add(ruleAddNodeParameter);
			
			Parameter mainUnitAddOutParameter = HenshinFactory.eINSTANCE.createParameter(addNode.getName());
			mainUnit.getParameters().add(mainUnitAddOutParameter);
			
			ParameterMapping parameterMapping = HenshinFactory.eINSTANCE.createParameterMapping();
			parameterMapping.setSource(ruleAddNodeParameter);
			parameterMapping.setTarget(mainUnitAddOutParameter);
			mainUnit.getParameterMappings().add(parameterMapping);
			
			// Create initial attributes:
			createInitializationAttributes(addObject.getObj(), addNode);
		}
	}
	
	protected boolean filter(AddObject addObject) {
		
		if ((addObject.getObj() != null)) {
			if (!objectFilter.filter(addObject.getObj())) {
				return true;
			}
		} else {
			System.err.println("Invalid Add-Object: " + addObject);
		}
		return false;
	}

	private static void createInitializationAttributes(EObject object, Node node) {
		
		for (EAttribute eAttribute : object.eClass().getEAllAttributes()) {
			if (isUneditableStructualFeature(eAttribute)) {
				continue;
			}

			Object attValue = object.eGet(eAttribute);
			
			if (attValue == null) {
				continue;
			}

			attValue = attValue.toString();

			// Quote String values
			if (eAttribute.getEAttributeType() == EcorePackage.eINSTANCE.getEString()) {
				attValue = "\"" + attValue + "\"";
			}

			HenshinFactory.eINSTANCE.createAttribute(node, eAttribute, attValue.toString());
		}
	}
	
	private void convertReferenceChanges(Collection<Change> changes, Rule editrule) {
		
		// Change edges:
		for (Change change : changes) {
			
			// Delete edges:
			if (change instanceof RemoveReference) {
				convertRemoveReference((RemoveReference) change, editrule);
			}
			
			// Create edges:
			if (change instanceof AddReference) {
				convertAddReference((AddReference) change, editrule);
			}
		}
	}
	
	private void convertRemoveReference(RemoveReference rmvRef, Rule editrule) {
		
		if (filter(rmvRef)) {
			Node srcNode = traceA2LHS.get(rmvRef.getSrc());
			Node tgtNode = traceA2LHS.get(rmvRef.getTgt());
			
			// Create edges only once:
			if (!rmvRef.getType().isDerived()) {
				if (!isEdgeContained(srcNode, tgtNode, rmvRef.getType())) {
					createDeleteEdge(srcNode, tgtNode, rmvRef.getType(), editrule);
				}
			}
		}
	}
	
	protected boolean filter(RemoveReference ref) {
		
		if ((ref.getSrc() != null) && (ref.getTgt() != null) && (ref.getType() != null)) {
			if (!objectFilter.filter(ref.getSrc()) && !objectFilter.filter(ref.getTgt())) {
				if (!referenceFilter.filter(ref.getSrc(), ref.getTgt(), ref.getType())) {
					return true;
				}
			}
		} else {
			System.err.println("Invalid Remove-Reference: " + ref);
		}
		return false;
	}
	
	private void convertAddReference(AddReference addRef, Rule editrule) {
		
		if (filter(addRef)) {
			Node srcNode = traceB2RHS.get(addRef.getSrc());
			Node tgtNode = traceB2RHS.get(addRef.getTgt());
			
			// Create edges only once:
			if (!addRef.getType().isDerived()) {
				if (!isEdgeContained(srcNode, tgtNode, addRef.getType())) {
					createCreateEdge(srcNode, tgtNode, addRef.getType());
				}
			}
		}
	}
	
	protected boolean filter(AddReference ref) {
		
		if ((ref.getSrc() != null) && (ref.getTgt() != null) && (ref.getType() != null)) {
			if (!objectFilter.filter(ref.getSrc()) && !objectFilter.filter(ref.getTgt())) {
				if (!referenceFilter.filter(ref.getSrc(), ref.getTgt(), ref.getType())) {
					return true;
				}
			}
		} else {
			System.err.println("Invalid Add-Reference: " + ref);
		}
		return false;
	}
	
	private void convertContextEdges(Collection<Correspondence> correspondences, Rule editrule) {
		
		// Preserve edges:
		for (Correspondence source : correspondences) {
			EObject objASource = source.getMatchedA();
			EObject objBSource = source.getMatchedB();
			
			for (EStructuralFeature feature : objBSource.eClass().getEAllStructuralFeatures()) {
				if (feature instanceof EReference) {
					EReference refType = (EReference) feature;
					
					// Create <<preserve>> edges:
					for (EObject objATarget : getTargets(objASource, refType)) {
						EObject objBTarget = getMatchedB(correspondences, objATarget);

						// Is reference contained in model B?
						if ((objBTarget != null) && getTargets(objBSource, refType).contains(objBTarget)) {

							if (filter(objASource, objATarget, refType) && filter(objBSource, objBTarget, refType)) {

								Node srcNodeLHS = traceA2LHS.get(objASource);
								Node srcNodeRHS = traceB2RHS.get(objBSource);
								NodePair srcNode = new NodePair(srcNodeLHS, srcNodeRHS);
								assert (srcNodeLHS != null) && (srcNodeRHS != null);

								Node tgtNodeLHS = traceA2LHS.get(objATarget);
								Node tgtNodeRHS = traceB2RHS.get(objBTarget);
								NodePair tgtNode = new NodePair(tgtNodeLHS, tgtNodeRHS);
								assert (tgtNodeLHS != null) && (tgtNodeRHS != null);

								if (!isEdgeContained(srcNodeLHS, tgtNodeLHS, refType)
										&& !isEdgeContained(srcNodeRHS, tgtNodeRHS, refType)) {
									createPreservedEdge(editrule, srcNode, tgtNode, refType);
								}
							}
						}
					}
				}
			}
		}
	}
	
	protected boolean filter(EObject source, EObject target, EReference feature) {
		
		if ((source != null) && (target != null) && (feature != null)) {
			if (!objectFilter.filter(source) && !objectFilter.filter(target)) {
				if (!referenceFilter.filter(source, target, feature)) {
					return true;
				}
			}
		}  else {
			System.err.println("Invalid Context-Reference: " + source + " - " + feature + " -> " + target);
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	private Collection<EObject> getTargets(EObject obj, EReference feature) {
		Collection<EObject> targets = Collections.emptyList();
		Object value = obj.eGet(feature);

		if (value != null) {
			if (feature.isMany()) {
				targets = (Collection<EObject>) value;
			} else {
				targets = (Collection<EObject>) (Object) Collections.singletonList(obj.eGet(feature));
			}
		}
		
		return targets;
	}
	
	private EObject getMatchedB(Collection<Correspondence> correspondences, EObject modelAElement) {

		for (Correspondence correspondence : correspondences) {
			if (correspondence.getMatchedA() == modelAElement) {
				return correspondence.getMatchedB();
			}
		}
		
		return null;
	}

	private static boolean isEdgeContained(Node src, Node tgt, EReference type) {
		
		for (Edge outgoing : src.getOutgoing()) {
			if ((outgoing.getType() == type) && (outgoing.getTarget() == tgt)) {
				return true;
			}
		}
		
		return false;
	}
	
	protected static String getName(EObject obj, Set<String> names) {
		String name = getName(obj);
		
//		// Qualified:
//		EObject container = obj.eContainer();
//		
//		while (container != null) {
//			name = getName(container) + "." + name;
//			
//			if (!(obj.eContainer() instanceof Resource)) {
//				container = container.eContainer();
//			} else {
//				container = null;
//			}
//		}

		// If this isn't unique:
		if (names.contains(name)) {
			int occurence = 1;

			while (names.contains(name + occurence)) {
				++occurence;
			}

			name = name + occurence;
		}

		names.add(name);
		return name;
	}
	
	protected static String getName(EObject obj) {
		EClass eClass = (obj != null) ? obj.eClass() : null;
		EStructuralFeature nameFeature = (eClass != null) ? eClass.getEStructuralFeature("name") : null;
		
		if (nameFeature != null) {
			Object nameFeatureValue = obj.eGet(nameFeature);
			
			if (nameFeatureValue instanceof String) {
				return (String) nameFeatureValue;
			}
		}
		
		return UNKNOWN_NAMES;
	}
	
	protected static String getName(Correspondence correspondence, Set<String> names) {
		String name = getName(correspondence.getMatchedA(), names);
		
		if (name == null) {
			name = getName(correspondence.getMatchedB(), names);
		}
		
		return name;
	}
	
	/**
	 * We do not consider unchangeable, derived and transient features.
	 */
	protected static boolean isUneditableStructualFeature(EStructuralFeature structualFeatureType) {
		return  ((structualFeatureType.isChangeable() == false) 
				|| (structualFeatureType.isDerived() == true)
				|| (structualFeatureType.isTransient() == true));
	}
}
