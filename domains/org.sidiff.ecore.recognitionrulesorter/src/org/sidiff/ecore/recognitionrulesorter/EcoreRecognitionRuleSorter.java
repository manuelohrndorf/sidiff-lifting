package org.sidiff.ecore.recognitionrulesorter;

import java.util.Collections;
import java.util.Set;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.model.Node;
import org.sidiff.difference.lifting.recognitionrulesorter.AbstractRecognitionRuleSorter;
import org.sidiff.difference.lifting.recognitionrulesorter.RecognitionNodeComparator;
import org.sidiff.difference.symmetric.util.DifferenceAnalysis;

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
	public RecognitionNodeComparator createComparator(EGraph eGraph, DifferenceAnalysis analysis) {
		return new RecognitionNodeComparator(eGraph, analysis) {

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
				return super.compareModelNodes(n1, n2);
			}

			protected boolean is_EStringToStringMapEntry(Node n) {
				return n.getType().getName().equals("EStringToStringMapEntry");
			}
		};
	}
}
