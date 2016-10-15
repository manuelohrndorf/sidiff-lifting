package org.sidiff.editrule.recorder.handlers;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.createCreateEdge;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.createCreateNode;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.createDeleteEdge;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.createDeleteNode;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.createPreservedEdge;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.createPreservedNode;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.getLHS;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.SequentialUnit;
import org.sidiff.common.henshin.INamingConventions;
import org.sidiff.common.henshin.view.NodePair;
import org.sidiff.difference.symmetric.AddObject;
import org.sidiff.difference.symmetric.AddReference;
import org.sidiff.difference.symmetric.AttributeValueChange;
import org.sidiff.difference.symmetric.Change;
import org.sidiff.difference.symmetric.RemoveObject;
import org.sidiff.difference.symmetric.RemoveReference;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.editrule.recorder.handlers.util.EMFHandlerUtil;
import org.sidiff.editrule.recorder.handlers.util.EditRuleUtil;
import org.sidiff.editrule.recorder.handlers.util.UIUtil;
import org.sidiff.matching.model.Correspondence;

/**
 * Transforms a symmetric difference into an edit-rule.
 * 
 * @author Manuel Ohrndorf
 */
public class CreateEditRuleHandler extends AbstractHandler implements IHandler {

	private static final String UNKNOWN_NAMES = "#";
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		SymmetricDifference difference = EMFHandlerUtil.getSelection(event, SymmetricDifference.class);
		
		if (difference != null) {
			Module module = createEditRule(difference);

			if (module != null) {
				module.getImports().addAll(EditRuleUtil.getImports(module));
				
				URI eoURI = EcoreUtil.getURI(difference).trimSegments(1)
						.appendSegment(module.getName() + "_execute")
						.appendFileExtension("henshin");
				Resource eoRes = difference.eResource().getResourceSet().createResource(eoURI);
				eoRes.getContents().add(module);

				try {
					eoRes.save(Collections.emptyMap());
				} catch (IOException e) {
					e.printStackTrace();
				}

				UIUtil.showMessage("Edit-Rule saved:\n\n" + eoURI.toPlatformString(true));
				return null;
			} else {
				UIUtil.showError("Could not transform this difference to an edit-rule.");
				return null;
			}
		} else {
			UIUtil.showError("The selected resource does not contain a model difference.");
			return null;
		}
	}
	
	@SuppressWarnings({ "unchecked" })
	private Module createEditRule(SymmetricDifference diff) {
		String eoName = diff.eResource().getURI().segments()[diff.eResource().getURI().segmentCount() - 2];
		
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
		Map<EObject, Node> traceA2LHS = new HashMap<>();
		Map<EObject, Node> traceB2RHS = new HashMap<>();
		
		Set<String> names = new HashSet<>();
		names.add(UNKNOWN_NAMES);
		
		// Preserve nodes:
		for (Correspondence correspondence : diff.getMatching().getCorrespondences()) {
			NodePair preserveNode = createPreservedNode(
					editrule, getName(correspondence, names), correspondence.getMatchedA().eClass());
			traceA2LHS.put(correspondence.getMatchedA(), preserveNode.getLhsNode());
			traceB2RHS.put(correspondence.getMatchedB(), preserveNode.getRhsNode());
		}

		// Change nodes:
		for (Change change : diff.getChanges()) {

			// Delete nodes:
			if (change instanceof RemoveObject) {
				RemoveObject removeObject = (RemoveObject) change;
				Node removeNode = createDeleteNode(
						getName(removeObject.getObj(), names), 
						removeObject.getObj().eClass(), 
						editrule);
				traceA2LHS.put(removeObject.getObj(), removeNode);
			}
			
			// Create nodes:
			else if (change instanceof AddObject) {
				AddObject addObject = (AddObject) change;
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
			}
		}
		
		// Change edges:
		for (Change change : diff.getChanges()) {
			
			// Delete edges:
			if (change instanceof RemoveReference) {
				RemoveReference removeReference = (RemoveReference) change;
				Node srcNode = traceA2LHS.get(removeReference.getSrc());
				Node tgtNode = traceA2LHS.get(removeReference.getTgt());
				
				// Create edges only once:
				if (!removeReference.getType().isDerived()) {
					if (!isEdgeContained(srcNode, tgtNode, removeReference.getType())) {
						createDeleteEdge(srcNode, tgtNode, removeReference.getType(), editrule);
					}
				}
			}
			
			// Create edges:
			if (change instanceof AddReference) {
				AddReference addReference = (AddReference) change;
				Node srcNode = traceB2RHS.get(addReference.getSrc());
				Node tgtNode = traceB2RHS.get(addReference.getTgt());
				
				// Create edges only once:
				if (!addReference.getType().isDerived()) {
					if (!isEdgeContained(srcNode, tgtNode, addReference.getType())) {
						createCreateEdge(srcNode, tgtNode, addReference.getType());
					}
				}
			}
			
			// Attribute value changes:
			if (change instanceof AttributeValueChange) {
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
		
		// Preserve edges:
		for (Correspondence correspondence : diff.getMatching().getCorrespondences()) {
			EObject modelB = correspondence.getMatchedB();
			
			Node srcNodeB = traceB2RHS.get(modelB);
			Node srcNodeA = traceA2LHS.get(correspondence.getMatchedA());
			assert (srcNodeA != null) && (srcNodeB != null);
			
			NodePair srcNode = new NodePair(srcNodeA, srcNodeB);
			
			for (EStructuralFeature feature : modelB.eClass().getEAllStructuralFeatures()) {
				if (feature instanceof EReference) {
					Collection<EObject> targets = Collections.emptyList();
					Object value = modelB.eGet(feature);

					if (value != null) {
						if (feature.isMany()) {
							targets = (Collection<EObject>) value;
						} else {
							targets = (Collection<EObject>) (Object) Collections.singletonList(modelB.eGet(feature));
						}
					}
					
					// Create edge:
					for (EObject target : targets) {
						Node tgtNodeB = traceB2RHS.get(target);
						Node tgtNodeA = (tgtNodeB != null) ? getLHS(tgtNodeB) : null;
						
						// A: Is target a preserve node:
						// NOTE: Preserve edges exists only between preserve nodes.
						
						// B: Is the target node covered in the difference?
						// NOTE: This might depend on the technical difference builder used.
						if ((tgtNodeB != null) && (tgtNodeA != null)) {
							NodePair tgtNode = new NodePair(tgtNodeA, tgtNodeB);
							
							if (!feature.isDerived()) {
								
								// New edge?
								// NOTE: Already existing preserve.
								// NOTE: Prefer create/delete over preserve edges.
								if (!isEdgeContained(srcNodeA, tgtNodeA, (EReference) feature)
										&& !isEdgeContained(srcNodeB, tgtNodeB, (EReference) feature)) {
									createPreservedEdge(editrule, srcNode, tgtNode, (EReference) feature);
								}
							}
							}
					}
				}
			}
		}
		
		return module;
	}
	
	private String getName(EObject obj, Set<String> names) {
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
	
	private String getName(EObject obj) {
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
	
	private String getName(Correspondence correspondence, Set<String> names) {
		String name = getName(correspondence.getMatchedA(), names);
		
		if (name == null) {
			name = getName(correspondence.getMatchedB(), names);
		}
		
		return name;
	}
	
	private boolean isEdgeContained(Node src, Node tgt, EReference type) {
		
		for (Edge outgoing : src.getOutgoing()) {
			if ((outgoing.getType() == type) && (outgoing.getTarget() == tgt)) {
				return true;
			}
		}
		
		return false;
	}
}
