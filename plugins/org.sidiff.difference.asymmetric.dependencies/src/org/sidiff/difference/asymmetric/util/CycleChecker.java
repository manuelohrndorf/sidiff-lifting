package org.sidiff.difference.asymmetric.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.sidiff.difference.asymmetric.DependencyContainer;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.rulebase.EditRule;


/**
 * Util class to check a collection of OperationInvocations
 * respectively Dependencies for a cycle
 * 
 * @author cpietsch
 *
 */
public class CycleChecker {
	private Collection<OperationInvocation> vertices;
	private Collection<OperationInvocation> finished = new ArrayList<OperationInvocation>();
	private List<OperationInvocation> visited = new ArrayList<OperationInvocation>();
	private List<OperationInvocation> path = new ArrayList<OperationInvocation>();
	private List<EditRule> cycle = new ArrayList<EditRule>();
	
	public CycleChecker(Collection<OperationInvocation> vertices){
		this.vertices = vertices;
		visited = new ArrayList<OperationInvocation>();
	}
	
	
	/**
	 * checks if there is an cycle between the OperationInvocation
	 * respectively the Dependencies
	 * 
	 * @return EditRules involved in a cycle
	 */
	public List<EditRule> check(){
		
		for(OperationInvocation o : vertices){
			if(checkNode(o))
				break;
		}
		
		for(int i=0; i<path.size(); i++){
			if(!cycle.contains(path.get(i).resolveEditRule()))
				cycle.add(path.get(i).resolveEditRule());
		}
		Collections.reverse(cycle);
		return cycle;
	}
	
	
	private boolean checkNode(OperationInvocation node){
		if(finished.contains(node))
			return false;
		if(visited.contains(node))
			return true;
		visited.add(node);
		for(DependencyContainer d : node.getOutgoing()){
			if (checkNode(d.getTarget())){
				path.add(d.getTarget());
				return true;
			}
		}
		finished.add(node);	
		return false;
	}
}
