package org.sidiff.serge.core;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.henshin.model.Node;

public class CombinationMatrix {

	private List<MatrixRow> combinationMatrix = null;
	private List<Node> nodeListAsColumnIdentifier = null;
	
	public CombinationMatrix(List<Node> nodeListAsColumnIdentifier) {

		combinationMatrix = new ArrayList<MatrixRow>();
		this.nodeListAsColumnIdentifier = nodeListAsColumnIdentifier;
	}
	
	public void createNewRow(List<Object> objects) {
		
		assert(objects.size()==nodeListAsColumnIdentifier.size()+1): "Entries in row unequal to defined amount of columns";
		
		MatrixRow newRow = new MatrixRow(objects);
		combinationMatrix.add(newRow);
		
	}
	
	public void addRow(MatrixRow row) {
		combinationMatrix.add(row);
	}
	
	public int getColumnIndexOfNode(Node node) {
		return nodeListAsColumnIdentifier.indexOf(node);
	}

	public Node getNodeByColumn(Integer columnIndex) {
		
		if(columnIndex >= nodeListAsColumnIdentifier.size()) {
			return null;
		}
		return nodeListAsColumnIdentifier.get(columnIndex);
	}
	
	public List<MatrixRow> getRows() {
		return combinationMatrix;
	}
	
	public List<Node> getColumnsAfterDirtyBit() {
		return nodeListAsColumnIdentifier;
	}
	
 }
