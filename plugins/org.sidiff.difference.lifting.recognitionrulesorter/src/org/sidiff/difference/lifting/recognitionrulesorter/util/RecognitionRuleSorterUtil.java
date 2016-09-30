package org.sidiff.difference.lifting.recognitionrulesorter.util;

import java.util.Collection;
import java.util.Set;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.difference.lifting.recognitionrulesorter.IRecognitionRuleSorter;
import org.sidiff.difference.lifting.recognitionrulesorter.structural.RecognitionRuleStructureSorting;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.SymmetricPackage;
import org.sidiff.difference.symmetric.util.DifferenceAnalysis;
import org.sidiff.matching.model.MatchingModelPackage;

public class RecognitionRuleSorterUtil {

	/**
	 * Setup the sorting algorithm and perform the sort of the recognition rule
	 * nodes, i.e. the algorithm optimizes the (matching) order of the nodes for
	 * the graph matching engine.
	 * 
	 * @param sorter
	 *            The domain specific or generic sorting algorithm.
	 * @param difference
	 *            The difference on which the rules should be applied.
	 * @param recognitionRules
	 *            The recognition rules to sort.
	 * @param filtered
	 *            Contains all the rules which should not be sorted.
	 * @return A statistic of the given difference.
	 */
	public static DifferenceAnalysis sort(IRecognitionRuleSorter sorter, 
			Collection<Rule> recognitionRules, Set<Rule> filtered, SymmetricDifference difference) {
		
		// Analyze the difference:
		DifferenceAnalysis analysis = new DifferenceAnalysis(difference);
		
		// Domain-Size sorting:
		sorter.setDifferenceAnalysis(analysis);

		for (Rule recognitionRule : recognitionRules) {
			if (!filtered.contains(recognitionRule)) {
				// Sort kernel rule
				ECollections.sort(recognitionRule.getLhs().getNodes(), sorter);

				// Sort all multi-rules (if there are any)
				for (Rule multiRule : recognitionRule.getAllMultiRules()) {
					ECollections.sort(multiRule.getLhs().getNodes(), sorter);
				}
			}
		}
		
		// Structural sorting:
		RecognitionRuleStructureSorting.sort(recognitionRules);
		
		return analysis;
	}
	
	/**
	 * Test node type.
	 * 
	 * @param node
	 *            the Henshin node to test
	 * @return <code>true</code> if the node is a Difference-Node;
	 *         <code>false</code> otherwise.
	 */
	public static boolean isDifference(Node node) {
		if (node.getType() == SymmetricPackage.eINSTANCE.getSymmetricDifference()) {
			return true;
		}
		return false;
	}

	/**
	 * Test node type.
	 * 
	 * @param node
	 *            the Henshin node to test
	 * @return <code>true</code> if the node is a Type-Node; <code>false</code>
	 *         otherwise.
	 */
	public static boolean isTypeNode(Node node) {

		if (node.getAttributes().size() != 1) {
			return false;
		}

		if ((node.getType() == EcorePackage.eINSTANCE.getEReference())
				|| (node.getType() == EcorePackage.eINSTANCE.getEAttribute())) {
			// We also need to find an incoming edge according to symmetric difference model:
			
			// ... try to find it in Kernel Rule
			if (isUsedAsTypeNodeInChange(node)){
				return true;
			}
			
			// ... try to find it in multi-rules
			Rule kernelRule = node.getGraph().getRule();
			for (Rule multiRule : kernelRule.getMultiRules()) {
				Node image = multiRule.getMultiMappings().getImage(node, multiRule.getLhs());
				if (isUsedAsTypeNodeInChange(image)){
					return true;
				}
				image = multiRule.getMultiMappings().getImage(node, multiRule.getRhs());
				if (isUsedAsTypeNodeInChange(image)){
					return true;
				}
			}
		}

		return false;
	}

	private static boolean isUsedAsTypeNodeInChange(Node node){
		if (node == null){
			return false;
		}
		
		for (Edge edge : node.getIncoming()) {
			if ((edge.getType() == SymmetricPackage.eINSTANCE.getAttributeValueChange_Type())
					|| (edge.getType() == SymmetricPackage.eINSTANCE.getAddReference_Type())
					|| (edge.getType() == SymmetricPackage.eINSTANCE.getRemoveReference_Type())) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Test node type.
	 * 
	 * @param node
	 *            the Henshin node to test
	 * @return <code>true</code> if the node is of the (sub-)type Change;
	 *         <code>false</code> otherwise.
	 */
	public static boolean isChangeNode(Node node) {
		return node.getType().getESuperTypes().contains(SymmetricPackage.eINSTANCE.getChange())
				|| (node.getType() == SymmetricPackage.eINSTANCE.getChange());
	}
	
	/**
	 * Test node type.
	 * 
	 * @param node
	 *            the Henshin node to test
	 * @return <code>true</code> if the node is of the type AddObject;
	 *         <code>false</code> otherwise.
	 */
	public static boolean isAddObject(Node node) {
		if (node.getType() == SymmetricPackage.eINSTANCE.getAddObject()) {
			return true;
		}
		return false;
	}

	/**
	 * Test node type.
	 * 
	 * @param node
	 *            the Henshin node to test
	 * @return <code>true</code> if the node is of the type RemoveObject;
	 *         <code>false</code> otherwise.
	 */
	public static boolean isRemoveObject(Node node) {
		if (node.getType() == SymmetricPackage.eINSTANCE.getRemoveObject()) {
			return true;
		}
		return false;
	}

	/**
	 * Test node type.
	 * 
	 * @param node
	 *            the Henshin node to test
	 * @return <code>true</code> if the node is of the type AddReference;
	 *         <code>false</code> otherwise.
	 */
	public static boolean isAddReference(Node node) {
		if (node.getType() == SymmetricPackage.eINSTANCE.getAddReference()) {
			return true;
		}
		return false;
	}

	/**
	 * Test node type.
	 * 
	 * @param node
	 *            the Henshin node to test
	 * @return <code>true</code> if the node is of the type RemoveReference;
	 *         <code>false</code> otherwise.
	 */
	public static boolean isRemoveReference(Node node) {
		if (node.getType() == SymmetricPackage.eINSTANCE.getRemoveReference()) {
			return true;
		}
		return false;
	}

	/**
	 * Test node type.
	 * 
	 * @param node
	 *            the Henshin node to test
	 * @return <code>true</code> if the node is of the type
	 *         AttributeValueChange; <code>false</code> otherwise.
	 */
	public static boolean isAttributeValueChange(Node node) {
		if (node.getType() == SymmetricPackage.eINSTANCE.getAttributeValueChange()) {
			return true;
		}
		return false;
	}

	/**
	 * Test node type.
	 * 
	 * @param node
	 *            the Henshin node to test
	 * @return <code>true</code> if the node is of the type Correspondence;
	 *         <code>false</code> otherwise.
	 */
	public static boolean isCorrespondence(Node node) {
		if (node.getType() == MatchingModelPackage.eINSTANCE.getCorrespondence()) {
			return true;
		}
		return false;
	}
}
