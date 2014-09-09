package org.sidiff.difference.lifting.edit2recognition.traces;

import org.eclipse.emf.henshin.model.Edge;

public class ACReferencePattern {

	public Edge forbidReference;
	
	public Edge trace;

	/**
	 * @param forbidReference
	 * @param trace
	 */
	public ACReferencePattern(Edge forbidReference, Edge trace) {
		super();
		this.forbidReference = forbidReference;
		this.trace = trace;
	}
}
