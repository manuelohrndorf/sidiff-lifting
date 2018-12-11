package org.sidiff.difference.asymmetric.util;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import org.sidiff.common.collections.TopologicalSorter;
import org.sidiff.difference.asymmetric.DependencyContainer;
import org.sidiff.difference.asymmetric.OperationInvocation;

public class OperationInvocationTopologicalSorter extends TopologicalSorter<OperationInvocation> {

	private static final Function<OperationInvocation,Stream<OperationInvocation>> PREDECESSORS =
			(v) -> v.getOutgoing().stream().map(DependencyContainer::getTarget);

	protected OperationInvocationTopologicalSorter(Collection<OperationInvocation> vertices) {
		super(vertices, PREDECESSORS, true);
	}

	public static List<OperationInvocation> sort(Collection<OperationInvocation> vertices) {
		return new OperationInvocationTopologicalSorter(vertices).sort();
	}
}