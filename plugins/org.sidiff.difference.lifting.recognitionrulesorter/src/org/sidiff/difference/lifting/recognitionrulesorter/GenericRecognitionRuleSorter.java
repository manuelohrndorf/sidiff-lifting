package org.sidiff.difference.lifting.recognitionrulesorter;

import java.util.Collections;
import java.util.Set;

import org.eclipse.emf.henshin.interpreter.EGraph;
import org.sidiff.difference.symmetric.util.DifferenceAnalysis;

public class GenericRecognitionRuleSorter extends AbstractRecognitionRuleSorter {

	@Override
	public String getName() {
		return "Generic Recognition Rule Sorter";
	}

	@Override
	public String getKey() {
		return "GenericRRSorter";
	}

	@Override
	public Set<String> getDocumentTypes() {
		return Collections.singleton(GENERIC_TYPE);
	}

	@Override
	public RecognitionNodeComparator createComparator(EGraph eGraph, DifferenceAnalysis analysis) {
		return new RecognitionNodeComparator(eGraph, analysis);
	}
}
