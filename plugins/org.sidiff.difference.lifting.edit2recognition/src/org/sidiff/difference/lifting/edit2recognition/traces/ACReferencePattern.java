
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
	private Edge acReference;
	
	/**
	 * The Edit-Rule AC edge.
	 */
	private Edge acTrace;
	
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
	
	/**
	 * @return The {@link ACReferencePattern#acReference}
	 */
	public Edge getACReference() {
		return acReference;
	}
	
	/**
	 * @return The {@link ACReferencePattern#acTrace}
	 */
	public Edge getACTrace() {
		return acTrace;
	}
}
