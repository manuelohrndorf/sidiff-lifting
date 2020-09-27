package org.sidiff.difference.lifting.recognitionrulesorter;

import java.util.Comparator;

import org.eclipse.emf.henshin.model.Node;
import org.sidiff.common.extension.ITypedExtension;
import org.sidiff.common.extension.TypedExtensionManager;
import org.sidiff.common.extension.storage.NoExtensionManagerStorage;
import org.sidiff.difference.symmetric.util.DifferenceAnalysis;

public interface IRecognitionRuleSorter extends ITypedExtension, Comparator<Node>{

	Description<IRecognitionRuleSorter> DESCRIPTION = Description.of(IRecognitionRuleSorter.class,
			"org.sidiff.difference.lifting.recognitionrulesorter.recognition_rule_sorter_extension", "recognition_rule_sorter", "recognition_rule_sorter");

	TypedExtensionManager<IRecognitionRuleSorter> MANAGER =
			new TypedExtensionManager<>(new NoExtensionManagerStorage<>(DESCRIPTION));

	/**
	 * @param analysis
	 * 			the corresponding difference analysis knowing the count of
	 *          changes in the difference.
	 */
	public void setDifferenceAnalysis(DifferenceAnalysis analysis);

	/**
	 * @return the corresponding difference analysis knowing the count of
	 *         changes in the difference.
	 */
	public DifferenceAnalysis getDifferenceAnalysis();
}
