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
	 * It also sets default value "false" for the dirty bit.
	 * @param nodes
	 */
	public void addNodes(List<Node> nodes) {
		
		columns.add(false);	
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
	
	/**
	 * Method that sets the dirty bit of a column containing
	 * the original nodes of a module to true.
	 * A column representing the original nodes of a module can
	 * be considered dirty, if their types can not be left like that
	 * in the original module because they are not well formed.
	 * Example: a < < create > > child node may not be abstract.
	 */
	public void setDirty() {
		columns.add(0, true);
	}
	
	public Boolean isDirty() {
		return (Boolean)columns.get(0);
	}
}
