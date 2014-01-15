package org.sidiff.serge.core;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.henshin.model.Node;

public class MatrixColumns {

	
	/**
	 * List of Objects representing the original module values
	 * plus additional information like the dirty bit of the original module.
	 * The dirty bit states if the original module contains replacables.
	 */
	private List<Object> columns = new ArrayList<Object>();
	
	/**
	 * Adds the given Nodes to the list of column entries.
	 * It also sets default value for dirty bit.
	 * @param nodes
	 */
	public void addNodes(List<Node> nodes) {
		
		columns.add(true);	
		columns.addAll(nodes);
		
	}
	
	/**
	 * Method that delivers the number of columns
	 * @return
	 */
	public Integer getColumnCount() {
		return columns.size();
	}
	
	/**
	 * Method that delivers the index of a Node.
	 * @param node
	 * @return
	 */
	public int getColumnIndexOfNode(Node node) {
		return columns.indexOf(node);
	}
	
	/**
	 * Method that returns an Object by given Index
	 * @param columnIndex
	 * @return
	 */
	public Object getObjectByColumn(Integer columnIndex) {
		
		if(columnIndex >= columns.size()) {
			return null;
		}
		return columns.get(columnIndex);
	}
	
	/**
	 * Method that delivers all column entries.
	 * @return
	 */
	public List<Object> getAllEntries() {
		return columns;
	}
	
}
