package org.sidiff.difference.symmetric.metrics;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IProgressMonitor;
import org.sidiff.common.emf.metrics.IMetricValueAcceptor;
import org.sidiff.common.emf.metrics.defaults.AbstractModelElementMetric;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.difference.symmetric.SymmetricDifference;

public class SemanticChangeSetCountingMetric extends AbstractModelElementMetric<SymmetricDifference>{

	public SemanticChangeSetCountingMetric() {
		super(SymmetricDifference.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doCalculate(SymmetricDifference context, IMetricValueAcceptor acceptor, IProgressMonitor monitor) {
		Map<String, Integer> operationGroup = new HashMap<String, Integer>();
		for(SemanticChangeSet scs : context.getChangeSets()) {
			if(operationGroup.containsKey(scs.getName())) {
				operationGroup.put(scs.getName(), operationGroup.get(scs.getName()) +1);
			}else {
				operationGroup.put(scs.getName(), 1);
			}
		}
		for(Entry<String, Integer> entry : operationGroup.entrySet()) {
			acceptor.accept(Collections.singleton(entry.getKey()), entry.getValue());
		}
	}

}
