package org.sidiff.fm.recognitionrulesorter;

import java.util.Collections;
import java.util.Set;

import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.model.Node;
import org.sidiff.difference.lifting.recognitionrulesorter.AbstractRecognitionRuleSorter;
import org.sidiff.difference.lifting.recognitionrulesorter.RecognitionNodeComparator;
import org.sidiff.difference.symmetric.util.DifferenceAnalysis;

import de.imotep.featuremodel.variability.metamodel.FeatureModel.FeatureModelPackage;

public class FMRecognitionRuleSorter extends AbstractRecognitionRuleSorter {

	@Override
	public String getName() {
		return "FM Recognition Rule Sorter";
	}

	@Override
	public String getKey() {
		return "FM_RRSorter";
	}
	
	@Override
	public Set<String> getDocumentTypes(){
		return Collections.singleton(FeatureModelPackage.eNS_URI);
	}

	@Override
	public RecognitionNodeComparator createComparator(EGraph eGraph, DifferenceAnalysis analysis) {
		return new FMRecognitionNodeComparator(eGraph, analysis);
	}
}
