package org.sidiff.difference.lifting.recognitionrulesorter;

import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.extension.ITypedExtension;
import org.sidiff.common.extension.TypedExtensionManager;
import org.sidiff.common.extension.storage.NoExtensionManagerStorage;
import org.sidiff.difference.symmetric.util.DifferenceAnalysis;

public interface IRecognitionRuleSorter extends ITypedExtension {

	Description<IRecognitionRuleSorter> DESCRIPTION = Description.of(IRecognitionRuleSorter.class,
			"org.sidiff.difference.lifting.recognitionrulesorter.recognition_rule_sorter_extension",
			"recognition_rule_sorter", "recognition_rule_sorter");

	TypedExtensionManager<IRecognitionRuleSorter> MANAGER = new TypedExtensionManager<>(
			new NoExtensionManagerStorage<>(DESCRIPTION));

	/**
	 * Setup the sorting algorithm and perform the sort of the recognition rule
	 * nodes, i.e. the algorithm optimizes the (matching) order of the nodes for the
	 * graph matching engine.
	 * 
	 * @param recognitionRule The rule containing the nodes to be sorted
	 * @param eGraph          The working graph for the matching the given nodes.
	 * @param analysis        The corresponding model difference and statistics.
	 */
	void sort(Rule recognitionRule, EGraph eGraph, DifferenceAnalysis analysis);

}
