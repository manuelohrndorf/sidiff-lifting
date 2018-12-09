package org.sidiff.difference.asymmetric.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.sidiff.difference.asymmetric.DependencyContainer;
import org.sidiff.difference.asymmetric.OperationInvocation;

public class TopologicalSorter {

	private List<OperationInvocation> vertices; // the remaining vertices to visit
	private List<OperationInvocation> sorted; // the sorted list
	private Set<OperationInvocation> visited; // vertices visited, to detect cycles

	private TopologicalSorter(Collection<OperationInvocation> vertices) {
		this.vertices = new ArrayList<>(vertices);
	}

	private List<OperationInvocation> sort() {
		this.sorted = new ArrayList<OperationInvocation>();
		this.visited = new HashSet<>();
		while(!vertices.isEmpty()) {
			visit(vertices.get(0));
		}
		return sorted;
	}

	private void visit(OperationInvocation vertex) {
		if(visited.contains(vertex) || sorted.contains(vertex)) {
			return;
		}
		visited.add(vertex);
		for(DependencyContainer edge : vertex.getOutgoing()) {
			visit(edge.getTarget());
		}
		vertices.remove(vertex);
		visited.remove(vertex);
		sorted.add(vertex);
	}

	public static List<OperationInvocation> sort(Collection<OperationInvocation> vertices) {
		return new TopologicalSorter(vertices).sort();
	}
}