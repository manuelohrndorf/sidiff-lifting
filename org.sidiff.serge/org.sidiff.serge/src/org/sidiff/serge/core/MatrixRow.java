package org.sidiff.serge.core;

import java.util.ArrayList;
import java.util.List;


public class MatrixRow {

	private static List<Object> row = new ArrayList<Object>();
	
	public MatrixRow(List<Object> objects) {
		for(Object object: objects) {
			row.add(object);
		}
	}
	
	public void setEntryAtPosition(Integer index, Object object) {
		row.set(index, object);
	}
	
	public List<Object> getEntries() {
		return row;
	}
	
	public Boolean isDirty() {
		return (Boolean) row.get(0);
	}
}
