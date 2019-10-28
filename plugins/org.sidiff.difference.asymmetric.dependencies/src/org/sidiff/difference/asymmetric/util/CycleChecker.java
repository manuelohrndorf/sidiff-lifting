package org.sidiff.difference.asymmetric.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.sidiff.difference.asymmetric.DependencyContainer;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.editrule.rulebase.EditRule;


/**
 * Util class to check a collection of OperationInvocations
 * respectively Dependencies for a cycle
 * 
 * @author cpietsch
 *
 */
public class CycleChecker {

	private Collection<OperationInvocation> vertices;
	private Collection<OperationInvocation> finished = new ArrayList<>();
	private List<OperationInvocation> visited = new ArrayList<>();
	private List<OperationInvocation> path = new ArrayList<>();

	private CycleChecker(Collection<OperationInvocation> vertices) {
		this.vertices = new ArrayList<>(vertices);
		visited = new ArrayList<>();
	}

	/**
	 * checks if there is an cycle between the OperationInvocation
	 * respectively the Dependencies
	 * 
	 * @return EditRules involved in a cycle
	 */
	public List<EditRule> check() {
		List<EditRule> cycle = new ArrayList<>();
		for(OperationInvocation o : vertices) {
			if(checkNode(o)) {
				break;
			}
		}
		for(OperationInvocation o : path) {
			EditRule editRule = o.resolveEditRule();
			if(!cycle.contains(editRule)) {
				cycle.add(editRule);
			}
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
		for(DependencyContainer d : node.getOutgoing()) {
			if(checkNode(d.getTarget())) {
				path.add(d.getTarget());
				return true;
			}
		}
		finished.add(node);	
		return false;
	}

	public static List<EditRule> check(Collection<OperationInvocation> vertices) {
		return new CycleChecker(vertices).check();
	}
}
