package org.sidiff.editrule.generator.serge.core.variantgeneration;

import java.util.Map;

import org.eclipse.emf.henshin.model.Node;
import org.sidiff.editrule.generator.serge.core.variantgeneration.SequenceEntry.EntryFlag;

public class SequenceEntry implements Map.Entry<Node, EntryFlag>{

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
	public EntryFlag getValue() {
		return v;
	}
	
	public Sequence getContainingSequence() {
		return containingSequence;
	}

	@Override
	public EntryFlag setValue(EntryFlag value) {
		EntryFlag old = v;
		this.v = value;
		return old;
	}

	@Override
	public String toString() {
		return k.getType().getName() + " (" + v.name() + ")" + " [" + k.hashCode() + "] ";
	}
}
