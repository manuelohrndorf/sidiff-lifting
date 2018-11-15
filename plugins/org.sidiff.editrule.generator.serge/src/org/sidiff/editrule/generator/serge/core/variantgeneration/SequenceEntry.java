package org.sidiff.editrule.generator.serge.core.variantgeneration;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Annotation;
import org.eclipse.emf.henshin.model.Node;

public class SequenceEntry implements Map.Entry<Node, Enum>{

	private Node k;
	private EntryFlag v;
	private Sequence containingSequence;
	
	public enum EntryFlag {
		uninspected,
		isAbstractAndWasReplaced,
		isConcreteAndWasReplaced;
	}
	
	public SequenceEntry(Node n, Sequence containingSequence) {
		k = n;
		v = EntryFlag.uninspected;
		this.containingSequence = containingSequence;
	}
	

	@Override
	public Node getKey() {
		return k;
	}

	@Override
	public Enum getValue() {
		return v;
	}
	
	public Sequence getContainingSequence() {
		return containingSequence;
	}

	/**
	 * Convenience method for returning the EntryFlag
	 * @return
	 * 			the entry flag
	 */
	public EntryFlag getEntryFlag() {
		return v;
	}

	@Override
	public Enum setValue(Enum value) {
		this.v = (EntryFlag) value;
		return v;
	}

	

	/**
	 * Convenience method for setting the EntryFlag
	 * @return
	 * 			the entry flag
	 */
	public EntryFlag setEntryFlag(EntryFlag value) {
		this.v = value;
		return v;
	}

	@Override
	public String toString() {

		return k.getType().getName() + " (" + v.name() + ")" + " [" + k.hashCode() + "] ";

	}
	

}
