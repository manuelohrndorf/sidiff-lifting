package org.sidiff.difference.lifting.edit2recognition.traces;

import org.eclipse.emf.henshin.model.Edge;

/**
 * Stores a transformation trace between the Edit-Rule and the corresponding Recognition-Rule.
 * 
 * @see {@link ACReferencePattern#ACReferencePattern(Edge, Edge)}
 * 
 * @author Manuel Ohrndorf
 */
public class ACReferencePattern {

	/**
	 * The Recognition-Rule AC edge.
	 */
	public Edge acReference;
	
	/**
	 * The Edit-Rule AC edge.
	 */
	public Edge acTrace;

	/**
	 * Creates a new (Edit- to Recognition-Rule) trace for an application condition (AC) node.
	 * 
	 * @param acReference
	 *            The Recognition-Rule AC edge.
	 * @param acTrace
	 *            The Edit-Rule AC edge.
	 */
	public ACReferencePattern(Edge acReference, Edge acTrace) {
		super();
		this.acReference = acReference;
		this.acTrace = acTrace;
	}
}
