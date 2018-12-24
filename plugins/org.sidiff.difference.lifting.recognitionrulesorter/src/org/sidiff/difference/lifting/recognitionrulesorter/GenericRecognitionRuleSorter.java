package org.sidiff.difference.lifting.recognitionrulesorter;

import java.util.Collections;
import java.util.Set;

import org.eclipse.emf.henshin.model.Node;

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
	protected int compareModelNodes(Node n1, Node n2) {
		return 0;
	}
}
