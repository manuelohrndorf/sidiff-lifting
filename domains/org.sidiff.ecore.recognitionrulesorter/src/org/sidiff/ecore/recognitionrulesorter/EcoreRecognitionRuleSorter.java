package org.sidiff.ecore.recognitionrulesorter;

import java.util.Collections;
import java.util.Set;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.henshin.model.Node;
import org.sidiff.difference.lifting.recognitionrulesorter.AbstractRecognitionRuleSorter;

public class EcoreRecognitionRuleSorter extends AbstractRecognitionRuleSorter {

	@Override
	public String getName() {
		return "Ecore Recognition Rule Sorter";
	}

	@Override
	public String getKey() {
		return "EcoreRRSorter";
	}

	@Override
	public Set<String> getDocumentTypes(){
		return Collections.singleton(EcorePackage.eNS_URI);
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
