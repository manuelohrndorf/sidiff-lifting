package org.sidiff.fm.recognitionrulesorter;

import org.eclipse.emf.henshin.model.Node;
import org.sidiff.difference.lifting.recognitionrulesorter.AbstractRecognitionRuleSorter;

import de.imotep.featuremodel.variability.metamodel.FeatureModel.FeatureModelPackage;

public class FMRecognitionRuleSorter extends AbstractRecognitionRuleSorter {

	private static final String KEY = "FM_RRSorter";
	
	private static final String NAME = "FM Recognition Rule Sorter";
	
	private static final String DOC_TYPE = FeatureModelPackage.eNS_URI;
	
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
		// (1) FeatureModel
		if (is_FeatureModel(n1) && is_FeatureModel(n2)) {
			return 0;
		} else if (is_FeatureModel(n1)) {
			return -1;
		} else if (is_FeatureModel(n2)) {
			return 1;
		}

		// (2) Group
		if (is_Group(n1) && is_Group(n2)) {
			return compareNodesOfSameType(n1, n2);
		} else if (is_Group(n1)) {
			return -1;
		} else if (is_Group(n2)) {
			return 1;
		}

		// (3) Feature
		if (is_Feature(n1) && is_Feature(n2)) {
			return compareNodesOfSameType(n1, n2);
		} else if (is_Feature(n1)) {
			return -1;
		} else if (is_Feature(n2)) {
			return 1;
		}

		// (4) ExcludeConstraint
		if (is_ExcludeConstraint(n1) && is_ExcludeConstraint(n2)) {
			return compareNodesOfSameType(n1, n2);
		} else if (is_ExcludeConstraint(n1)) {
			return -1;
		} else if (is_ExcludeConstraint(n2)) {
			return 1;
		}

		// (5) RequireConstraint
		if (is_RequireConstraint(n1) && is_RequireConstraint(n2)) {
			return compareNodesOfSameType(n1, n2);
		} else if (is_RequireConstraint(n1)) {
			return -1;
		} else if (is_RequireConstraint(n2)) {
			return 1;
		}

		assert (false) : "We should never come here";
		return 0;
	}
	
	private boolean is_FeatureModel(Node n){
		return n.getType().getName().equals("FeatureModel");
	}
	
	private boolean is_Group(Node n){
		return n.getType().getName().equals("Group");
	}
	
	private boolean is_Feature(Node n){
		return n.getType().getName().equals("Feature");
	}
	
	private boolean is_ExcludeConstraint(Node n){
		return n.getType().getName().equals("ExcludeConstraint");
	}
	
	private boolean is_RequireConstraint(Node n){
		return n.getType().getName().equals("RequireConstraint");
	}

}
