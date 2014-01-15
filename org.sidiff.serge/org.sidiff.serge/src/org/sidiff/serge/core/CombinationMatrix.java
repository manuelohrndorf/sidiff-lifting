package org.sidiff.serge.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.sidiff.common.emf.extensions.impl.EClassifierInfoManagement;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;

public class CombinationMatrix {

	/**
	 * The matrix containing MatrixRows
	 */
	private List<MatrixRow> matrixRows = null;
	
	/**
	 * Map of possible replacement types for a each node
	 */
	private Map<Node, Set<EClassifier>> nodeReplacementMap = null;	
		
	/**
	 * The MatrixColumns existing of a dirty bit and nodes to replace.
	 */
	private MatrixColumns matrixColumns = null;
	
	/**
	 * The original module this matrix is based upon.
	 */
	private Module originalModule = null;
	
	/**
	 * The EClassifierInfoManagement
	 */
	private EClassifierInfoManagement ECM = EClassifierInfoManagement.getInstance();
	
	/**
	 * Constructor
	 */
	public CombinationMatrix() {

		matrixRows			= new ArrayList<MatrixRow>();
		matrixColumns 		= new MatrixColumns();
		nodeReplacementMap	= new HashMap<Node, Set<EClassifier>>();
	}
	
	/**
	 * Method that delivers all rows of a Matrix exclusive their column header.
	 * @return
	 * 			matrix rows.
	 */
	public List<MatrixRow> getRows() {
		return matrixRows;
	}
	
	/**
	 * Method that delivers the corresponding original node for a replacement
	 * in a row.
	 * @param replacement
	 * @param row
	 * @return
	 * 		The corresponding original node.
	 */
	public Node getOriginalNode(Object replacement, MatrixRow row) {
		Integer rowPosition = row.getPositionOfObject(replacement);
		
		return (Node)matrixColumns.getObjectByColumn(rowPosition);
		
	}
	
	/**
	 * Method that calculates and builds up the Matrix content
	 * for a given originalModule.
	 * @param originalModule
	 */
	public void calculateFor(Module originalModule) {
		
		this.originalModule = originalModule;
		
		// gather all child nodes within a containment relation
		List<Node> childNodes = HenshinRuleAnalysisUtilEx.getChildNodesWithinAContainmentRelation(
				originalModule, HenshinRuleAnalysisUtilEx.NodeKindSelection.CREATE,false);
		
		// remove node entries if they are neither abstract nor super type
		// because then there is no replacement needed		
		Iterator<Node> childNodeIterator = childNodes.iterator();
		while(childNodeIterator.hasNext()) {
			Node childNode = childNodeIterator.next();
			if(!childNode.getType().isAbstract() 
					&& ECM.getAllSubTypesAsEClassifiersWithoutAbstracts(childNode.getType()).isEmpty()) {
				childNodeIterator.remove();
			}
			
		}
		
		// add childNodes as columns
		matrixColumns.addNodes(childNodes);
		
		// gather possible replacements per node
		for(Node nodeRequiringReplace: childNodes) {

			// the original type
			EClassifier originalTypeOfNode = nodeRequiringReplace.getType();
			
			// get all possible replacements for the original type if it is a super type
			Set<EClassifier> possibleSubtypes = ECM.getAllSubTypesAsEClassifiersWithoutAbstracts(originalTypeOfNode);
			
			// get possible concrete replacements for current originalTypeOfNode if it is abstract
			ArrayList<EClassifier> possibleConcretes = new ArrayList<EClassifier>();
			if(originalTypeOfNode instanceof EClass && ((EClass)originalTypeOfNode).isAbstract()) {
				possibleConcretes = ECM.getAllConcreteEClassifiersForAbstract(originalTypeOfNode);
			}

			// put all in a set, so there will be no duplicates
			Set<EClassifier> replacementsForCurrentNode = new HashSet<EClassifier>();
			replacementsForCurrentNode.addAll(possibleConcretes);
			replacementsForCurrentNode.addAll(possibleSubtypes);
			
			// add node and its possible replacements into nodeReplacementMap
			nodeReplacementMap.put(nodeRequiringReplace, replacementsForCurrentNode);
		}
		
		
		// get the first node to replace if any and start building matrix
		if(!childNodes.isEmpty()) {
			
			// the first node and its original type
			Node initialNode = childNodes.get(0);
			
			// create row for each replacement for the first node
			for(EClassifier replacement: nodeReplacementMap.get(initialNode)) {
				
				// initial rows
				List<Object> initialRowEntries = new ArrayList<Object>();
				initialRowEntries.add(false); //set dirty bit
				initialRowEntries.add(replacement);
				MatrixRow row = new MatrixRow(initialRowEntries);
				addRow(row);
				
				// continue to fill row horizontally and also matrix vertically
				// with additional new rows resulting of recursive replaces. 
				int currentColumn = matrixColumns.getColumnIndexOfNode(initialNode);
				if( matrixColumns.getObjectByColumn(currentColumn+1)!=null) {
					continueFillMatrix(row,currentColumn+1);				
				}
			}
		}
		
		// uncomment this if not dbugging
		debugHelp_MatrixPrintout();
	}
	
	/**
	 * This method is required for filling the matrix recursively.
	 * @param row
	 * @param column
	 */
	private void continueFillMatrix(MatrixRow row, Integer column) {

		Node nextNodeToReplace = (Node)matrixColumns.getObjectByColumn(column);

		if(nextNodeToReplace!=null) {

			//get replacements for nextNodeToReplace
			Set<EClassifier> replacements = nodeReplacementMap.get(nextNodeToReplace);

			if(replacements!=null) {

				// iterator over replacements
				Iterator<EClassifier> repIterator = replacements.iterator();

				// first replacement
				EClassifier firstReplacement = repIterator.next();

				// update row with first replacement		
				row.setEntryAtPosition(column, firstReplacement);		

				// continue adjusting this row with replacements in next columns
				if(matrixColumns.getObjectByColumn(column+1)!=null) {
					continueFillMatrix(row,column+1);				
				}	

				// if there are more possible replacements for this node, create copy of current
				// row and replace node type in it
				while(repIterator.hasNext()) {

					// create new row by copying the existing one
					EClassifier nextReplace = repIterator.next();
					MatrixRow newRowByCopy = new MatrixRow(row.getEntries());

					// update row with nextReplace		
					newRowByCopy.setEntryAtPosition(column, nextReplace);

					// add it to matrix
					addRow(newRowByCopy);

					// continue adjusting this row with replacements in next columns
					if(matrixColumns.getObjectByColumn(column+1)!=null) {
						continueFillMatrix(newRowByCopy,column+1);				
					}		
				}
			}
		}
	}
	
	/**
	 * This method adds a row to the matrix.
	 * @param row
	 */
	private void addRow(MatrixRow row) {
		matrixRows.add(row);
	}
	
	/**
	 * Just a helper method for debugging purpose. It prints the matrix to system.out.
	 */
	private void debugHelp_MatrixPrintout() {
		
		System.out.print("----------------------------------------------------\n"
						+"Replacement Matrix for: "+originalModule.getName() + "\n\n");
		
		// print out column values
		for(Object object: matrixColumns.getAllEntries()) {
			if(object instanceof Boolean) {
				System.out.print(((Boolean) object) + "\t\t");
			}
			else if(object instanceof Node) {
				System.out.print(((Node)object).getType().getName() + "\t\t");	
			}
		}
		System.out.println("\n");
		
		// print out row values
		for(MatrixRow row: getRows()) {
			
			for(Object entry: row.getEntries()) {
				if(entry instanceof Boolean) {
					System.out.print(((Boolean) entry).booleanValue() + "\t\t");
				}
				else{
					System.out.print(((EClassifier)entry).getName()+ "\t\t");	
				}
				
			}	
			System.out.print("\n");
		}
		System.out.print("\n\n");
	}
 }
