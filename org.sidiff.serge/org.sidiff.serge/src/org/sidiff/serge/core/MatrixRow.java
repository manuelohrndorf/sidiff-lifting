package org.sidiff.serge.core;

import java.util.ArrayList;
import java.util.List;


public class MatrixRow {

	private List<Object> row = new ArrayList<Object>();
	
	public MatrixRow(List<Object> objects) {
		for(Object object: objects) {
			row.add(object);
		}
	}
	
	public void setEntryAtPosition(Integer index, Object object) {
		try{
			row.set(index,object);
		}
		catch(IndexOutOfBoundsException e) {
			row.add(index, object);
		}
	}
	
	public List<Object> getEntries() {
		return row;
	}
	
	public Boolean isDirty() {
		return (Boolean) row.get(0);
	}
}
