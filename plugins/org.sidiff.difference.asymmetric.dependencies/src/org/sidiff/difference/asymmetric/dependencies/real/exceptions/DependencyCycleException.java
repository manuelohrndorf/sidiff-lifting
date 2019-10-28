package org.sidiff.difference.asymmetric.dependencies.real.exceptions;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.sidiff.editrule.rulebase.EditRule;

public class DependencyCycleException extends RuntimeException {

	private static final long serialVersionUID = 3661852963028878198L;

	private List<EditRule> cycle;

	public DependencyCycleException(List<EditRule> cycle) {
		this.cycle = Objects.requireNonNull(cycle);
	}

	public List<EditRule> getCycle() {
		return Collections.unmodifiableList(cycle);
	}

	@Override
	public String getMessage() {
		return "Dependency cycle exists between: " + getCycle();
	}
}
