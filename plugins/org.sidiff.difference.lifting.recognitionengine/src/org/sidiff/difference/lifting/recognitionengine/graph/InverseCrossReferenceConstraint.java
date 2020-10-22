package org.sidiff.difference.lifting.recognitionengine.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.matching.constraints.DomainChange;
import org.eclipse.emf.henshin.interpreter.matching.constraints.DomainSlot;
import org.eclipse.emf.henshin.interpreter.matching.constraints.ReferenceConstraint;
import org.eclipse.emf.henshin.interpreter.matching.constraints.Variable;
import org.eclipse.emf.henshin.model.Edge;

public abstract class InverseCrossReferenceConstraint extends ReferenceConstraint {

	/**
	 * The edge that have to be check in reverse direction.
	 */
	private Edge incoming;
	
	/**
	 * Convenience constructor.
	 * 
	 * @param referenceSource
	 *            The the source variable w.r.t. the given outgoing edge.
	 * @param incoming
	 *            An edge with no EOpposite.
	 */
	public InverseCrossReferenceConstraint(Variable referenceSource, Edge outgoing) {
		super(referenceSource, outgoing.getType());
		this.incoming = outgoing;
	}
	
	@Override
	public boolean check(DomainSlot referenceTarget, DomainSlot referenceSource) {
		
		// Inverse matching:
		// [referenceTarget] <- [referenceSource]
		
		// Source variable must be locked:
		if (!referenceTarget.isLocked()) {
			return false;
		}
		
		// Target domain slot not locked yet?
		if (!referenceSource.isLocked()) {
			
			// Get the target objects:
			Collection<? extends EObject> targetObjects = getInverseCrossReferenced(referenceTarget.getValue(), incoming);
			
			if (targetObjects != null) {
				
				// Create a domain change to restrict the target domain:
				DomainChange change = new DomainChange(referenceSource, referenceSource.getTemporaryDomain());
				referenceTarget.getRemoteChangeMap().put(this, change);
				
				// Calculated temporary domain:
				referenceSource.setTemporaryDomain(new ArrayList<EObject>(targetObjects));
				
				if (change.getOriginalValues() != null) {
					referenceSource.getTemporaryDomain().retainAll(change.getOriginalValues());
				}
				
				// Temporary domain must not be empty:
				return !referenceSource.getTemporaryDomain().isEmpty();
			}
		} else {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Calculates all cross-references: [incoming.getTarget() / target] <-
	 * incoming - [incoming.getSource() / ?]
	 * 
	 * @param target
	 *            The target of an edge with no EOpposite.
	 * @param incoming
	 *            The edge that have to be check in reverse direction.
	 * @return A fresh and modifiable list of the objects that references the
	 *         given target object.
	 */
	public abstract List<EObject> getInverseCrossReferenced(EObject target, Edge incoming);
}
