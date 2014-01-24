package org.sidiff.serge.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.henshin.model.Node;


public class MatrixRow {

	/**
	 * The row containing variant information like the dirty bit value,
	 * which declares if a row still contains replacables and it also
	 * contains the type of replacements in the same order as the original
	 * types are listed in the MatrixColumn.
	 */
	private List<Object> row = new ArrayList<Object>();
	
	/**
	 * Constructor
	 * @param objects
	 */
	public MatrixRow(List<Object> objects) {
		for(Object object: objects) {
			row.add(object);
		}
	}
	
	/**
	 * Sets the entry at the given index in a row.
	 * 
	 * @param index
	 * @param object
	 */
	public void setEntryAtPosition(Integer index, Object object) {
		try{
			row.set(index,object);
		}
		catch(IndexOutOfBoundsException e) {
			row.add(index, object);
		}
	}
	
	/**
	 * Returns the position of an object in a row.
	 * @param object
	 * @return
	 */
	public Integer getPositionOfObject(Object object) {
		return row.indexOf(object);
	}
	
	/**
	 * Gets all entries in a row, no matter if it contains
	 * additional information or the node types.
	 * @return
	 */
	public List<Object> getEntries() {
		return row;
	}

	/**
	 * This method only delivers the replacements EClassifiers in a row
	 * without additional information like the dirty bit.
	 * @return
	 */
	public List<EClassifier> getOnlyReplacementClassifierEntries() {
		List<EClassifier> replacements = new ArrayList<EClassifier>();
		for(Object object: row) {
			if(object instanceof EClassifier) {
				replacements.add((EClassifier)object);
			}
		}
		return replacements;
	}
	
	/**
	 * Convenience method that tells if the row still contains
	 * replacables and in this case can be considered as dirty.
	 * @return
	 */
	public Boolean isDirty() {
		return (Boolean) row.get(0);
	}

}
