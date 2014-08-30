package org.sidiff.ecore.recognitionrulesorter;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.henshin.model.Node;
import org.sidiff.difference.lifting.recognitionrulesorter.AbstractRecognitionRuleSorter;

public class EcoreRecognitionRuleSorter extends AbstractRecognitionRuleSorter {

	private static final String KEY = "EcoreRRSorter";
	
	private static final String NAME = "Ecore Recognition Rule Sorter";
	
	private static final String DOC_TYPE = EcorePackage.eNS_URI;

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getDocumentType(){
		return DOC_TYPE;
	}
	
	@Override
	protected int compareModelNodes(Node n1, Node n2) {
		// (1) EStringToStringMapEntry ganz nach unten
		if (is_EStringToStringMapEntry(n1) && is_EStringToStringMapEntry(n2)) {
			return 0;
		} else if (is_EStringToStringMapEntry(n1)) {
			return -1;
		} else if (is_EStringToStringMapEntry(n2)) {
			return 1;
		}

		return 0;
	}

}
