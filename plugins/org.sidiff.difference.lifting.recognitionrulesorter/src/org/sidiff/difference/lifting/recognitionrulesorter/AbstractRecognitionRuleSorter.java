package org.sidiff.difference.lifting.recognitionrulesorter;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.difference.lifting.recognitionrulesorter.structural.RecognitionRuleStructureSorting;
import org.sidiff.difference.symmetric.util.DifferenceAnalysis;

/**
 * Sorts a Henshin recognition rule to be optimized for matching on a technical
 * difference. The Type-Nodes and Change-Nodes will be moved and ordered to the
 * top of the graph and the correspondences will be moved to the bottom of the
 * graph list. All Model-Nodes will retain in the middle of the graph list. The
 * Change-Nodes are ordered by the count of their appearance in the difference.
 * That means atomic changes with a low count in the difference will appear on
 * the top the Henshin graph list and will be matched first. This is important
 * to get a "good" execution time.
 */
public abstract class AbstractRecognitionRuleSorter implements IRecognitionRuleSorter {
	
	protected RecognitionRuleStructureSorting structureSorting;

	@Override
	public void sort(Rule recognitionRule, EGraph eGraph, DifferenceAnalysis analysis) {
		
		// Domain-Size sorting:
		RecognitionNodeComparator sorter = createComparator(eGraph, analysis);

		// Sort kernel rule:
		ECollections.sort(recognitionRule.getLhs().getNodes(), sorter);
		
		// Sort application conditions:
		for (NestedCondition ac : recognitionRule.getLhs().getNestedConditions()) {
			ECollections.sort(ac.getConclusion().getNodes(), sorter);
		}

		// Sort all multi-rules:
		for (Rule multiRule : recognitionRule.getAllMultiRules()) {
			ECollections.sort(multiRule.getLhs().getNodes(), sorter);
			
			// Sort application conditions:
			for (NestedCondition ac : multiRule.getLhs().getNestedConditions()) {
				ECollections.sort(ac.getConclusion().getNodes(), sorter);
			}
		}

		// Structural sorting:
		RecognitionRuleStructureSorting structureSorting = createStructureSorting(eGraph, analysis);
		structureSorting.sort(recognitionRule);
	}
	
	public RecognitionRuleStructureSorting createStructureSorting(EGraph eGraph, DifferenceAnalysis analysis) {
		if (structureSorting == null) {
			this.structureSorting = new RecognitionRuleStructureSorting();
		}
		return structureSorting;
	}

	/**
	 * Can be implemented by domain-specific plug-ins.
	 * 
	 * @param eGraph The current working graph (for the rule to be sorted).
	 * @param analysis Some statistics of the model difference.
	 * @return A node comparator for sorting the graph to be matched.
	 */
	public abstract RecognitionNodeComparator createComparator(EGraph eGraph, DifferenceAnalysis analysis);

}
