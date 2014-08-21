package org.sidiff.serge.generators.actions;

import java.util.HashMap;
import java.util.Stack;

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
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.serge.configuration.Configuration;
import org.sidiff.serge.configuration.GlobalConstants;
import org.sidiff.serge.configuration.Configuration.OperationType;
import org.sidiff.serge.core.ModuleInternalsApplicator;
import org.sidiff.serge.exceptions.OperationTypeNotImplementedException;

public class MoveDownGenerator {

	/**
	 * The eClassifier to be moved.
	 */
	private EClassifier eClassifier;
	
	/**
	 * The path
	 */
	private Stack<HashMap<EReference,EClassifier>> path;
	
	/**
	 * The configuration.
	 */
	private static Configuration config = Configuration.getInstance();
	
	/**
	 * Constructor
	 * @param eClassifier
	 * @param eReference
	 * @param oldContext
	 * @param newContext
	 */
	public MoveDownGenerator(EClassifier eClassifier,
			Stack<HashMap<EReference,EClassifier>> path) {

		this.eClassifier = eClassifier;
		this.path = path;
	}
	
	public Module generate() throws OperationTypeNotImplementedException {
		
		HashMap<EReference,EClassifier> lastStep = path.peek();
		EReference eRef = (EReference) lastStep.keySet().iterator().next();
		EClassifier parent = (EClassifier) eRef.eContainer();		
		
		String name = GlobalConstants.MOVE_DOWN_prefix + eClassifier.getName()
				+ GlobalConstants.FROM + parent.getName()
				+"_("+eRef.getName()+")"
				+ GlobalConstants.TO+parent.getName()
				+"_("+eRef.getName()+")"; 

		LogUtil.log(LogEvent.NOTICE, "Generating Move Down: " + name);
		
		Module MOVE_DOWN_Module = HenshinFactory.eINSTANCE.createModule();
		MOVE_DOWN_Module.setName(name);
		
		MOVE_DOWN_Module.setDescription("MOVE DOWNWARDs "+eClassifier.getName()
									+ " from " + parent.getName()
									+"(Reference:"+eRef.getName()+")"
									+ " to "+parent.getName()
									+"(Reference:"+eRef.getName());
		
		// add imports
		MOVE_DOWN_Module.getImports().addAll(config.EPACKAGESSTACK);
		
		// create rule (we can use OperationType.MOVE for this
		Rule r = ModuleInternalsApplicator.createBasicRule(MOVE_DOWN_Module, eRef, eClassifier, parent, eRef, parent, OperationType.MOVE_DOWN);
		
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
		Node nodeToConnectNewEdgeWith = oldSourceNodeInNCGraph;
		int numbOfSteps = path.size();
		
		// now create nodes and draw edges after the following principle
		for(int i=1; i<=numbOfSteps-2; i++) {		
			HashMap<EReference,EClassifier> step = path.get(i);
			EClass eClass = (EClass) step.values().iterator().next();
			EReference stepRef = step.keySet().iterator().next();
	
			// if it is not the last step before the last entry in path create new Node...
			if((numbOfSteps==3 && i<=numbOfSteps-2)|| (numbOfSteps>3 && i<=numbOfSteps-3)) {
				Node newNode = HenshinFactory.eINSTANCE.createNode(graph, eClass, "");
				// ...and draw edge between  the current nodeToConnectNewEdgeWith and the following new node.
				Edge newEdge = HenshinFactory.eINSTANCE.createEdge(nodeToConnectNewEdgeWith, newNode, stepRef);
				graph.getEdges().add(newEdge);
				// ...and  update the node to connect the next edge with
				nodeToConnectNewEdgeWith = newNode;
				// ...And additionally, check if step is one before the last.
				// If so, draw also an edge between the new Node and the oldSourceNodeInNCGraph.
				if((numbOfSteps==3 && i==numbOfSteps-2) || (numbOfSteps>3 && i==numbOfSteps-3)) {
					Edge newEdgeBackToOld = HenshinFactory.eINSTANCE.createEdge(newNode, newSourceNodeInNCGraph, stepRef);
					graph.getEdges().add(newEdgeBackToOld);
				}
			}
		}
		
		
		
		return MOVE_DOWN_Module;
	}
}
