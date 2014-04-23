package org.sidiff.serge.core;

import java.util.Iterator;
import java.util.Vector;

import org.eclipse.emf.ecore.EClassifier;

public class ReplacementTableRow implements Iterable<EClassifier> {

	private Vector<EClassifier> row;
	
	public ReplacementTableRow() {
		row = new Vector<EClassifier>();
	}

	@Override
	public Iterator<EClassifier> iterator() {
		return row.iterator();
	}
	
	public Integer size() {
		return row.size();
	}
	
	public EClassifier get(int index) {
		return row.get(index);
	}

	public int getIndexOf(EClassifier eClassifier) {
		return row.indexOf(eClassifier);
	}
	
	/**
	 * Inserts the EClassifier at the given index. If the position
	 * is already occupied, the node will be appended.
	 * (use set(..) if overwriting is needed)
	 * @param eClassifier
	 * @param index
	 */
	public void insertElementAt(EClassifier eClassifier, int index) {
		row.insertElementAt(eClassifier, index);
	}
	/**
	 *  Overwrites the EClassifier at the given index.
	 * @param eClassifier
	 * @param index
	 */
	public void set(EClassifier eClassifier, int index) {
		row.set(index,eClassifier);
	}

	public void ensureCapacity(int minCapacity) {
		row.ensureCapacity(minCapacity);
	}
}
