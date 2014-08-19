package org.sidiff.difference.lifting.edit2recognition.traces;

import org.eclipse.emf.henshin.model.Edge;

public class ACReferencePattern {

	private Edge forbidReference;
	
	private Edge trace;

	public ACReferencePattern(Edge forbidReference, Edge trace) {
		super();
		this.forbidReference = forbidReference;
		this.trace = trace;
	}

	public Edge getForbidReference() {
		return forbidReference;
	}

	public void setForbidReference(Edge forbidReference) {
		this.forbidReference = forbidReference;
	}

	public Edge getTrace() {
		return trace;
	}

	public void setTrace(Edge trace) {
		this.trace = trace;
	}
}
