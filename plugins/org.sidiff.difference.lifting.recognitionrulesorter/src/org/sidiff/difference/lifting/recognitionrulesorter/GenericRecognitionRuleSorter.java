package org.sidiff.difference.lifting.recognitionrulesorter;

import org.eclipse.emf.henshin.model.Node;

public class GenericRecognitionRuleSorter extends AbstractRecognitionRuleSorter {

	private static final String KEY = "GenericRRSorter";
	
	private static final String NAME = "Generic Recognition Rule Sorter";
	
	private static final String DOC_TYPE = "generic";
	
	
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
