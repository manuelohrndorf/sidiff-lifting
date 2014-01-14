package org.sidiff.serge.core;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.henshin.model.Node;

public class CombinationMatrix {

	private static List<MatrixRow> combinationMatrix = null;
	private static List<Node> nodeListAsColumnIdentifier = null;
	
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
		return nodeListAsColumnIdentifier.indexOf(node)+1;
	}

	public Node getNodeByColumn(Integer columnIndex) {
		
		if(columnIndex+1 > nodeListAsColumnIdentifier.size()) {
			return null;
		}
		return nodeListAsColumnIdentifier.get(columnIndex+1);
	}
	
	public List<MatrixRow> getRows() {
		return combinationMatrix;
	}
 }
