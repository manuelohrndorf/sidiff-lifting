package org.sidiff.difference.asymmetric.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.sidiff.difference.asymmetric.DependencyContainer;
import org.sidiff.difference.asymmetric.OperationInvocation;

public class TopologicalSorter {
	
	private Collection<OperationInvocation> vertices;	
	private List<OperationInvocation> sorted;
	
	public TopologicalSorter(Collection<OperationInvocation> vertices) {
		this.vertices = vertices;
		sorted = new ArrayList<OperationInvocation>(vertices.size());
	}
	
	public List<OperationInvocation> sort(){
		while (!vertices.isEmpty()){				
			for (Iterator<OperationInvocation> iterator = vertices.iterator(); iterator.hasNext();) {
				OperationInvocation vertex = iterator.next();
				if (!hasOutgoingEdges(vertex)){
					sorted.add(vertex);
					iterator.remove();
				}
			}
		}
		
		return sorted;
	}
	
	private boolean hasOutgoingEdges(OperationInvocation vertex) {
		for (DependencyContainer edge : vertex.getOutgoing()) {
			if (vertices.contains(edge.getTarget())){
				return true;
			}
		}
		
		return false;
	}
}