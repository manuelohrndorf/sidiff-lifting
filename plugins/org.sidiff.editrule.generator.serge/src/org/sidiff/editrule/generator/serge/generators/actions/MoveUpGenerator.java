package org.sidiff.editrule.generator.serge.generators.actions;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.emf.metamodel.analysis.ContainmentCycle;
import org.sidiff.common.emf.metamodel.analysis.ContainmentCyclePathStep;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.editrule.generator.exceptions.OperationTypeNotImplementedException;
import org.sidiff.editrule.generator.serge.configuration.Configuration;
import org.sidiff.editrule.generator.serge.configuration.GlobalConstants;
import org.sidiff.editrule.generator.serge.core.ModuleInternalsApplicator;
import org.sidiff.editrule.generator.types.OperationType;

public class MoveUpGenerator {
	
	/**
	 * The eClassifier to be moved.
	 */
	private EClassifier eClassifier;
	
	/**
	 * The ContainmentCycle
	 */
	private ContainmentCycle cc;
	
	/**
	 * The configuration.
	 */
	private static Configuration config = Configuration.getInstance();
	
	/**
	 * Constructor
	 * @param eClassifier
	 * @param path
	 */
	public MoveUpGenerator(EClassifier eClassifier,
			ContainmentCycle cc) {

		this.eClassifier = eClassifier;
		this.cc = cc;
	}
	
	public Module generate() throws OperationTypeNotImplementedException {
		
		ContainmentCyclePathStep lastStep = cc.getBackwardPointingStep();
		EReference eRef = lastStep.getTargetingReference();
		EClassifier parent = (EClassifier) eRef.eContainer();		
		
		String name = GlobalConstants.MOVE_UP_prefix + eClassifier.getName()
				+ GlobalConstants.FROM + parent.getName()
				+"_("+eRef.getName()+")"
				+ GlobalConstants.TO+parent.getName()
				+"_("+eRef.getName()+")"; 

		LogUtil.log(LogEvent.NOTICE, "Generating Move Up: " + name);
		
		Module MOVE_UP_Module = HenshinFactory.eINSTANCE.createModule();
		MOVE_UP_Module.setName(name);
		
		MOVE_UP_Module.setDescription("MOVE UPWARDs "+eClassifier.getName()
									+ " from " + parent.getName()
									+"(Reference:"+eRef.getName()+")"
									+ " to "+parent.getName()
									+"(Reference:"+eRef.getName());
		
		// add imports
		MOVE_UP_Module.getImports().addAll(config.EPACKAGESSTACK);
		
		// create rule (we can use OperationType.MOVE for this
		Rule r = ModuleInternalsApplicator.createBasicRule(MOVE_UP_Module, eRef, eClassifier, parent, eRef, parent, OperationType.MOVE_UP);
		
		//** Now apply the containment cycle path as PACs ****************************************************
		
		// 1. create nested condition
		NestedCondition nc = r.getLhs().createPAC("cyclePath");
		Graph graph = nc.getConclusion();
		
		// 2. copy relevant nodes/edges into nested condition graph
		// and also create mappings from normal LHS graph into the NestedCondition graph
		Node oldSourceNodeInLHS = HenshinRuleAnalysisUtilEx.getNodeByName(r, GlobalConstants.OLDSRC, true);
		Node oldSourceNodeInNCGraph = HenshinFactory.eINSTANCE.createNode();
		oldSourceNodeInNCGraph.setName(GlobalConstants.OLDSRC);
		oldSourceNodeInNCGraph.setType(oldSourceNodeInLHS.getType());
		graph.getNodes().add(oldSourceNodeInNCGraph);
		
		Node newSourceNodeInLHS = HenshinRuleAnalysisUtilEx.getNodeByName(r, GlobalConstants.NEWSRC, true);
		Node newSourceNodeInNCGraph = HenshinFactory.eINSTANCE.createNode();
		newSourceNodeInNCGraph.setName(GlobalConstants.NEWSRC);
		newSourceNodeInNCGraph.setType(newSourceNodeInLHS.getType());
		graph.getNodes().add(newSourceNodeInNCGraph);
		
		Mapping mOldSrc = HenshinFactory.eINSTANCE.createMapping(oldSourceNodeInLHS, oldSourceNodeInNCGraph);
		Mapping mNewSrc = HenshinFactory.eINSTANCE.createMapping(newSourceNodeInLHS, newSourceNodeInNCGraph);
		
		nc.getMappings().add(mNewSrc);
		nc.getMappings().add(mOldSrc);
		
		// 3. add new required nodes/edges iteratively					
		// In the Beginning, the edge should have its source in the following node:
		Node nodeToConnectNewEdgeWith = newSourceNodeInNCGraph;
		int numberOfIntermediateSteps = cc.getNumberOfIntermediateSteps();
		
		// == 0: direct backward pointer to self
		if(numberOfIntermediateSteps==0) {
			
			// ...just draw edges between newSourceNodeInNCGraph and oldSourceNodeInNCGraph.
			// no node creation is needed in this case.
			EReference backwardRef = cc.getBackwardPointingStep().getTargetingReference();
			Edge newEdge = HenshinFactory.eINSTANCE.createEdge(newSourceNodeInNCGraph, oldSourceNodeInNCGraph, backwardRef);
			graph.getEdges().add(newEdge);
	
					
		}// >= 0: contains intermediate steps
		// (Note: an intermediate step includes the container of an object to move)
		else{
			Node lastCreatedNode = null;
			for(ContainmentCyclePathStep step: cc.getIntermediateSteps()) {
			
				EClass eClass = (EClass) step.getTargetedEClassifier();
				EReference stepRef = step.getTargetingReference();
				
				// create node for each intermediate step other than the last intermediate step
				if(!cc.isLastIntermediateStep(step)) {
					Node newNode = HenshinFactory.eINSTANCE.createNode(graph, eClass, "");
					lastCreatedNode = newNode;
					// ...and draw edge between  the current nodeToConnectNewEdgeWith and the following new node.
					Edge newEdge = HenshinFactory.eINSTANCE.createEdge(nodeToConnectNewEdgeWith, newNode, stepRef);
					graph.getEdges().add(newEdge);
					// ...and  update the node to connect the next edge with
					nodeToConnectNewEdgeWith = newNode;
				}else{
					// On last (single) intermediate step; (i.e., which has to be connected to object to move)
					// ...draw an edge between the last created Node and the oldSourceNodeInNCGraph,
					// if there has been an intermediate node before
					if(lastCreatedNode!=null) {
						Edge newEdgeBackToOld = HenshinFactory.eINSTANCE.createEdge(lastCreatedNode, oldSourceNodeInNCGraph, stepRef);
						graph.getEdges().add(newEdgeBackToOld);
					}
					// otherwise (if there was no intermediate node before)
					// ...draw edge between newSourceNodeInNCGraph and oldSourceNodeInNCGraph.
					else{					
						EReference backwardRef = stepRef;
						Edge newEdge = HenshinFactory.eINSTANCE.createEdge(newSourceNodeInNCGraph, oldSourceNodeInNCGraph, backwardRef);
						graph.getEdges().add(newEdge);
						
					}
				}
				
			}
			
		}
		return MOVE_UP_Module;
	}
	
}
