package org.sidiff.difference.lifting.recognitionrulesorter;

import org.eclipse.emf.henshin.model.Node;

public class GeneralRecognitionRuleSorter extends AbstractRecognitionRuleSorter {

	private static final String KEY = "GeneralRRSorter";
	
	private static final String NAME = "General Recognition Rule Sorter";
	
	private static final String DOC_TYPE = "general";
	
	
	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getDocumentType() {
		return DOC_TYPE;
	}

	@Override
	protected int compareModelNodes(Node n1, Node n2) {
		return 0;
	}

}
