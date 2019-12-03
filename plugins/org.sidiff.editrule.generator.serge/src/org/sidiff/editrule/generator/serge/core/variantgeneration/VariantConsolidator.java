package org.sidiff.editrule.generator.serge.core.variantgeneration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Annotation;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.emf.metamodel.analysis.EClassifierInfoManagement;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;
import org.sidiff.editrule.generator.exceptions.OperationTypeNotImplementedException;
import org.sidiff.editrule.generator.serge.core.ModuleInternalsApplicator;
import org.sidiff.editrule.generator.serge.core.variantgeneration.Sequence.InspectionFlag;
import org.sidiff.editrule.generator.serge.core.variantgeneration.SequenceEntry.EntryFlag;
import org.sidiff.editrule.generator.types.OperationType;

/**
 * This class provides methods to create semantically coherent variants inside the same module.
 * 
 * Notes:
 * - Only <<create>> modules are supported so far
 * - No type exclusions as defined on the config file are considered
 * - Sequences with abstract entries are not dismissed yet
 * 
 * 
 * @author mrindt
 *
 */
public class VariantConsolidator {

	/**
	 * The original module that needs to be examined for replacables.
	 */
	private Module originalModule;

	/**
	 * The operation type of the originalModule
	 */
	private OperationType opType;

	/**
	 * The String for the annotation on a sequence entry / node which tells
	 * to which presence condition (=variant) it belongs
	 */
	private static String ANNOTATION_PRESENCECONDITION = "presenceCondition";
	
	/**
	 * The String for the annotation which lists all found variants
	 */
	private static String ANNOTATION_ALL_VARIANTS = "allVariants";
	
	
	/**
	 * The EClassifierInfoManagement.
	 */
	private EClassifierInfoManagement ECM = EClassifierInfoManagement.getInstance();


	/**
	 * Constructor
	 */
	public VariantConsolidator(Module originalModule, OperationType opType) {
		
		this.originalModule = originalModule;
		this.opType = opType;
		ECM = EClassifierInfoManagement.getInstance();
		
	}

	public Module replace() throws OperationTypeNotImplementedException {

		if (opType != OperationType.CREATE){
			throw new OperationTypeNotImplementedException(opType);
		}
		
		// sequence set
		SequenceSet sequenceSet = new SequenceSet();
		
		// find initial sequence
		Sequence sequence = findInitialSequence();
		sequenceSet.add(sequence);
		
		// fill entry identity map
		for(SequenceEntry entry: sequence) {
			sequenceSet.addToIdentity(entry, sequence, entry);
		}
		
		// find all sequences by considering sub types
		sequenceSet = findAllSequences(sequenceSet);

		// print results
		System.out.println("\nDerivates: \n");
		for(Sequence s: sequenceSet) {
			System.out.println(s.toString());
		}
		
		// annotate the module with all found variants as a summary
		annotateModuleWithAllFoundVariants(sequenceSet);		
		
		// clear sequences(-sets) up (just for performance aspects)
		sequenceSet.clearIdentityMap();
		sequenceSet.clear();
		
		return originalModule;
	}
	
	private void annotateModuleWithAllFoundVariants(SequenceSet sequenceSet) {
		
		// create variant annotation
		Annotation anno = HenshinFactory.eINSTANCE.createAnnotation();
		anno.setKey(ANNOTATION_ALL_VARIANTS);
		
		for(Sequence sequence: sequenceSet) {
			if(anno.getValue()!=null) {
				anno.setValue(anno.getValue() + "," + sequence.getSequenceWithoutInspectionFlag());
			}else {
				anno.setValue(sequence.getSequenceWithoutInspectionFlag());
			}
		}
		
		originalModule.getAnnotations().add(anno);
	}

	private SequenceSet findAllSequences(SequenceSet sequenceSet) {
		
		for(Sequence currentSequence: sequenceSet) {
			
			// skip this sequence if it was already inspected completely
			if(currentSequence.getInspectionFlag() == InspectionFlag.complete) {				
				continue;
			}

			// find first occurence of a super type in an entry
			for(SequenceEntry entry: currentSequence) {

				// only proceed if the entry is still uninspected
				if(entry.getValue() == EntryFlag.uninspected) {
					
					// replicate and replace (just in case the entry node type has sub types)
					replicateAndReplace(sequenceSet, currentSequence, entry);

					// adjust flags
					if(entry.getKey().getType().isAbstract()) {
						currentSequence.setEntryFlag(entry.getKey(), EntryFlag.isAbstractAndWasReplaced);
						currentSequence.setSequenceContainsAbstracts();
					}else {
						currentSequence.setEntryFlag(entry.getKey(), EntryFlag.isConcreteAndWasReplaced);
					}
				}

				// check other sequences for the same entry occurence and replace/replicate it:
				if(sequenceSet.getEntryIdentityMap().get(entry) != null) {
					for(Map.Entry<Sequence,SequenceEntry> occurrence :sequenceSet.getEntryIdentityMap().get(entry).entrySet()) {
						if(occurrence.getValue().getValue()==EntryFlag.uninspected) {
							replicateAndReplace(sequenceSet, occurrence.getKey(), occurrence.getValue());
						}
					}
				}	
			}
			
			// adjust sequence inspection flag to complete
			currentSequence.setInspectionFlag(InspectionFlag.complete);
			
			// recursively: find further resulting sequences
			sequenceSet = findAllSequences(sequenceSet);
			
			return sequenceSet;
		}


		return sequenceSet;
	}
	
	
	private void replicateAndReplace(SequenceSet sequenceSet, Sequence originalSequence, SequenceEntry entryInOriginalSequence) {
		
		Node currentNodeInOriginalSequence = entryInOriginalSequence.getKey();

		// replicate and replace (just in case the entry node type has sub types)
		Set<EClassifier> subTypes = ECM.getAllSubTypes(currentNodeInOriginalSequence.getType());
		if(!subTypes.isEmpty()) {
			for(EClassifier subtype: subTypes) {

				// create new Sequence
				Sequence replicatedSequence = new Sequence();
				
				// history log of node replication (Map<old,new>)
				Map<SequenceEntry, SequenceEntry> historyMap = new HashMap<SequenceEntry, SequenceEntry>();

				// replicate nodes / sequence entries and..
				for(SequenceEntry originalEntry: originalSequence) {

					Node originalNode = originalEntry.getKey();
					Node replicatedNode = HenshinRuleAnalysisUtilEx.copyNode(originalNode.getGraph(), originalNode, true);
					SequenceEntry entryInReplicatedSequence = replicatedSequence.addEntry(replicatedNode);

					// log replication (Map<old,new>)
					historyMap.put(originalEntry, entryInReplicatedSequence);
					
					// copy all incoming references (and recurve edges to replicated sources)
					for(Edge incEdge: originalNode.getIncoming()) {
						
						Node replicatedEdgeSource = getReplicatedNodeInHistory(incEdge.getSource(), historyMap);
						
						//TODO need more documentation / explanation here
						if(incEdge.getSource().equals(currentNodeInOriginalSequence) && originalNode != currentNodeInOriginalSequence) {
							SequenceEntry replicatedAndSubstitutedEntry = historyMap.get(entryInOriginalSequence);
							HenshinRuleAnalysisUtilEx.copyEdge(incEdge, replicatedAndSubstitutedEntry.getKey(), replicatedNode);
							
						}
						else if(replicatedEdgeSource !=null && replicatedEdgeSource != currentNodeInOriginalSequence) {
							HenshinRuleAnalysisUtilEx.copyEdge(incEdge, replicatedEdgeSource, replicatedNode);
						}				
						else {
							HenshinRuleAnalysisUtilEx.copyEdge(incEdge, incEdge.getSource(), replicatedNode);
						}
					}					


					
					// in case it was the node with sub types...
					if(originalNode.equals(currentNodeInOriginalSequence)) {

						// replace type with sub type
						replicatedNode.setType((EClass)subtype);

						// create new mandatory child nodes and place them at this
						// position of the newSequence
						Sequence subSequence = createSubSequenceForMandatoryChildren(replicatedNode,subtype);
						subSequence.concatSequence(createSubSequenceForMandatoryNeighbors(replicatedNode, subtype));
						replicatedSequence.addAll(subSequence);
					}
					else {
						// add entry to identity map (map original entry to replicated entry and its containing sequence)
						// (We don't need identity mappings for entries which have replaced node types, so skip these)
						sequenceSet.addToIdentity(originalEntry, replicatedSequence, entryInReplicatedSequence);
					}
					
					// create variant annotation on node
					createVariantAnnotationOnNode(entryInReplicatedSequence, replicatedSequence);
				}			
				
				// update variant annotations on nodes with full sequence information
				updateVariantAnnotationOnNodes(replicatedSequence);
				
				sequenceSet.add(replicatedSequence);
				sequenceSet.getEntryIdentityMap();
			}
		}

	}
	
	private Node getReplicatedNodeInHistory(Node node, Map<SequenceEntry, SequenceEntry> historyMap) {
		
		for(Entry<SequenceEntry, SequenceEntry> historyEntry: historyMap.entrySet()) {
			if(historyEntry.getKey().getKey().equals(node)) {
				return historyEntry.getValue().getKey();
			}
		}
		return null;
	}
	
	private void createVariantAnnotationOnNode(SequenceEntry entryInReplicatedSequence, Sequence replicatedSequence) {

		// create variant annotation
		Annotation anno = HenshinFactory.eINSTANCE.createAnnotation();
		anno.setKey(ANNOTATION_PRESENCECONDITION);
		anno.setValue(replicatedSequence.toString());
		
		// add annotation to the replicated node
		entryInReplicatedSequence.getKey().getAnnotations().add(anno);
	}
	
	private void updateVariantAnnotationOnNodes(Sequence sequence) {
			
		for(SequenceEntry entry: sequence) {
			
			for(Annotation anno: entry.getKey().getAnnotations()) {
				
				if(anno.getKey().equals(ANNOTATION_PRESENCECONDITION)) {				
					String valueWithoutFlagInfo = sequence.toString().split(Pattern.quote(" ("))[0];
					anno.setValue(valueWithoutFlagInfo);
				}
			}	
		}
		
	}
		
	private Sequence addDirectDescendantsToSequence(Sequence sequence, Node node) {
	
		// add all direct decendant children of specified node
		// (direct = no subtypes considered)
		for(Edge e: node.getOutgoing()) {
					
			if(e.getType().isContainment()) {
				
				// add the direct children of the specified node
				sequence.addEntry(e.getTarget());

				// recursively add all the direct descendants of this child
				sequence = addDirectDescendantsToSequence(sequence, e.getTarget());
			}

		}
		
		return sequence;
	}
	
	private Sequence findInitialSequence() {
		
		// get focal node
		Rule rule = (Rule)originalModule.getUnits().get(0);
		Node focalNode = rule.getRhs().getNode("New");
		
		// add it and it's available, direct descendants to the sequence
		Sequence sequence = new Sequence();
		//sequence.addEntry(focalNode);		
		addDirectDescendantsToSequence(sequence, focalNode);		
	
		// print sequence
		System.out.println("Initial Sequence for: " + originalModule.getName() +"\n");
		sequence.print();
		
		return sequence;
		
	}

	private Sequence createSubSequenceForMandatoryChildren(Node node, EClassifier type) {
		
		Sequence subSequenceForChildren = new Sequence();
		
		HashMap<EReference,List<EClassifier>> mandatoryChildren = ECM.getEClassifierInfo(type).getAllDirectLocalMandatoryChildren(type);
		if(!mandatoryChildren.isEmpty()) {
			
			
			for(Map.Entry<EReference, List<EClassifier>> childEntry: mandatoryChildren.entrySet()) {
				
				EReference containmentRef = childEntry.getKey();
				
				for(EClassifier childType: childEntry.getValue()) {
					
					String childNodeName = ModuleInternalsApplicator.getFreeAttributeName("New", node.getGraph().getRule());
					Node childNode = HenshinRuleAnalysisUtilEx.createCreateNode(node.getGraph(), childNodeName, (EClass) childType);
					HenshinRuleAnalysisUtilEx.createCreateEdge(node, childNode, containmentRef);
					subSequenceForChildren.addEntry(childNode);
					
				}

			}
			
		}
		
		return subSequenceForChildren;
	}
	
	private Sequence createSubSequenceForMandatoryNeighbors(Node node, EClassifier type) {
		
		Sequence subSequenceForNeighbors = new Sequence();
		
		// local, direct, mandatory neighbors & inherited, direct, mandatory neighbors
		HashMap<EReference,List<EClassifier>> mandatoryNeighbors = ECM.getEClassifierInfo(type).getAllDirectLocalMandatoryNeighbors(type);
		mandatoryNeighbors.putAll(ECM.getEClassifierInfo(type).getAllDirectInheritedMandatoryNeighbors(type));
	
		if(!mandatoryNeighbors.isEmpty()) {
						
			for(Map.Entry<EReference, List<EClassifier>> neighborEntry: mandatoryNeighbors.entrySet()) {
				
				EReference eRef = neighborEntry.getKey();
				
				for(EClassifier neighborType: neighborEntry.getValue()) {
					
					String neighborNodeName = ModuleInternalsApplicator.getFreeAttributeName("Existing", node.getGraph().getRule());
					Node neighborNode = HenshinRuleAnalysisUtilEx.createPreservedNode(((Rule)originalModule.getUnits().get(0)), neighborNodeName, (EClass) neighborType).getRhsNode();
					HenshinRuleAnalysisUtilEx.createCreateEdge(node, neighborNode, eRef);
					subSequenceForNeighbors.addEntry(neighborNode);
					
				}

			}			
		}
		
		return subSequenceForNeighbors;
	}
}
