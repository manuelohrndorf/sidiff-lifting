package org.sidiff.editrule.analysis.classification;

import org.sidiff.editrule.rulebase.EditRule;

public interface IClassificator {
	
	/**
	 * The shared extension point id.
	 */
	public static final String extensionPointID = "org.sidiff.editrule.analysis.classification.classification_extension";
	
	int getClassificatorId();
	
	String createClassification(EditRule rule);

	String getName();

	String getKey();

	boolean canHandle(EditRule rule);

}
