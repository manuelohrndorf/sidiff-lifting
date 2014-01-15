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
import org.sidiff.common.emf.extensions.impl.EClassifierInfo;
import org.sidiff.common.emf.extensions.impl.EClassifierInfoManagement;
import org.sidiff.common.emf.extensions.impl.EcoreHelper;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;

public class TypeReplacer {
	
	/**
	 * The original module that needs to be examined for replacables.
	 */
	private Module originalModule = null;
	
	/**
	 * The EClassifierInfoManagement.
	 */
	private EClassifierInfoManagement ECM = EClassifierInfoManagement.getInstance();
	
	/**
	 * Map of possible replacement types for a each node
	 */
	private Map<Node, Set<EClassifier>> nodeReplacementMap = null;	
	
	/**
	 * Matrix containing the possible node type replacements
	 */
	private CombinationMatrix matrix = null;
	
	/**
	 * Constructor
	 * @param originalModule
	 */
	public TypeReplacer(Module originalModule) {
		this.originalModule = originalModule;
		nodeReplacementMap = new HashMap<Node, Set<EClassifier>>();
		
		initializeMatrixForVariants();

	}
	
	private void initializeMatrixForVariants() {
		
		
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
		
		// Create a Replacement Matrix for the nodesRequiringReplaces
		matrix = new CombinationMatrix(childNodes);		
		
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
				matrix.addRow(row);
				
				// continue to fill row horizontally and also matrix vertically
				// with additional new rows resulting of recursive replaces. 
				int currentColumn = matrix.getColumnIndexOfNode(initialNode);
				if(matrix.getNodeByColumn(currentColumn+1)!=null) {
					continueFillMatrix(row,currentColumn+1);				
				}
			}
		}
	
	}
	
	private void continueFillMatrix(MatrixRow row, Integer column) {

		Node nextNodeToReplace = matrix.getNodeByColumn(column);

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
				if(matrix.getNodeByColumn(column+1)!=null) {
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
					matrix.addRow(newRowByCopy);

					// continue adjusting this row with replacements in next columns
					if(matrix.getNodeByColumn(column+1)!=null) {
						continueFillMatrix(newRowByCopy,column+1);				
					}		
				}
			}
			}

	}
	

	public Set<Module> replace() {
		
		Set<Module> modules = new HashSet<Module>();		

		for(MatrixRow row: matrix.getRows()) {
			
			for(Object entry: row.getEntries()) {
				if(entry instanceof Boolean) {
					System.out.print(((Boolean) entry).booleanValue() + " ");
				}
				else{
					System.out.print(((EClassifier)entry).getName()+ " ");	
				}
				
			}	
			System.out.print("\n");
		}
		
		
		return modules;
		
	}
}
