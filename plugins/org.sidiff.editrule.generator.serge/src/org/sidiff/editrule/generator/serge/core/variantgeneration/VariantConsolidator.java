package org.sidiff.editrule.generator.serge.core.variantgeneration;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Annotation;
import org.eclipse.emf.henshin.model.AttributeCondition;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.emf.metamodel.analysis.EClassifierInfoManagement;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;
import org.sidiff.editrule.generator.exceptions.OperationTypeNotImplementedException;
import org.sidiff.editrule.generator.serge.configuration.Configuration;
import org.sidiff.editrule.generator.serge.configuration.Configuration.OperationTypeGroup;
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
 * - Neighbors are not considered yet
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
	 * The operaiton type of the originalModule
	 */
	private OperationType opType;

	private static String VARIANT_ANNOTATION_KEY = "presenceCondition";
	
	private Integer variantCounter;
	
	/**
	 * The EClassifierInfoManagement.
	 */
	private EClassifierInfoManagement ECM = EClassifierInfoManagement.getInstance();
	/**
	 * 
	 */
	private Configuration c = Configuration.getInstance();

	private OperationTypeGroup opTypeGroup;

	/**
	 * Constructor
	 */
	public VariantConsolidator(Module originalModule, OperationType opType, OperationTypeGroup opTypeGroup, Integer variantCounter)
			throws OperationTypeNotImplementedException {
		this.originalModule = originalModule;
		this.opType = opType;
		this.opTypeGroup = opTypeGroup;
		this.variantCounter = variantCounter;
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
		
		// clear sequences(-sets) up (just for performance aspects)
		sequenceSet.clearIdentityMap();
		sequenceSet.clear();
		
		return originalModule;
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
				if(entry.getEntryFlag() == EntryFlag.uninspected) {
					
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
						if(occurrence.getValue().getEntryFlag()==EntryFlag.uninspected) {
							replicateAndReplace(sequenceSet, occurrence.getKey(), occurrence.getValue());
						}
					}
				}	
			}
			
			// adjust sequence inspection flag to complete
			currentSequence.setInspectionFlag(InspectionFlag.complete);
			
			sequenceSet.getEntryIdentityMap();
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

				// - create new Sequence
				Sequence replicatedSequence = new Sequence();

				// - create a copies of nodes and substitute the corresponding node type which has sub types
				for(SequenceEntry originalEntry: originalSequence) {

					Node originalNode = originalEntry.getKey();
					Node replicatedNode = HenshinRuleAnalysisUtilEx.copyNode(originalNode.getGraph(), originalNode, true);
					
					// copy all incoming references
					for(Edge incEdge: originalNode.getIncoming()) {
						HenshinRuleAnalysisUtilEx.copyEdge(incEdge, incEdge.getSource(), replicatedNode);
					}					

					// copy the rest of the sequence in order
					SequenceEntry entryInReplicatedSequence = replicatedSequence.addEntry(replicatedNode);

					// in case it was the node with sub types...
					if(originalNode.equals(currentNodeInOriginalSequence)) {

						// replace type with sub type
						replicatedNode.setType((EClass)subtype);

						// create new mandatory child nodes and place them at this
						// position of the newSequence
						Sequence subSequence = createSubSequenceForMandatoryChildren(replicatedNode,subtype);
						replicatedSequence.addAll(subSequence);
					}
					else {
						// add entry to identity map (map original entry to replicated entry and its containing sequence)
						// (We don't need identity mappings for entries which have replaced node types, so skip these)
						sequenceSet.addToIdentity(originalEntry, replicatedSequence, entryInReplicatedSequence);
					}
				}			
				
				createNodeAnnotations(replicatedSequence);
				sequenceSet.add(replicatedSequence);
				sequenceSet.getEntryIdentityMap();
			}
		}

	}
	
	private void createNodeAnnotations(Sequence replicatedSequence) {
		
		for(SequenceEntry entry: replicatedSequence) {
						
			Annotation anno = HenshinFactory.eINSTANCE.createAnnotation();
			anno.setKey(VARIANT_ANNOTATION_KEY);
			anno.setValue(replicatedSequence.toString());
			entry.getKey().getAnnotations().add(anno);
						
		}
		
	}

	private Integer getVariantAnnotationValue(Node n) {
		
		for (Annotation a : n.getAnnotations()) {
			if (a.getKey().equals(VARIANT_ANNOTATION_KEY)) {
				return  Integer.valueOf(a.getValue());
			}
		}

		return null;
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
		
		HashMap<EReference,List<EClassifier>> mandatoryChildren = ECM.getEClassifierInfo(type).getAllDirectLocalChildren(type);
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
}
