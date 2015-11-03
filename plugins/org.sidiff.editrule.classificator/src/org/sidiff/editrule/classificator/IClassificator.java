package org.sidiff.editrule.classificator;

import org.sidiff.difference.rulebase.EditRule;

public interface IClassificator {
	
	/**
	 * The shared extension point id.
	 */
	public static final String extensionPointID = "org.sidiff.editrule.classificator.classificator_extension";
	
	int getClassificatorId();
	
	String createClassification(EditRule rule);

	String getName();

	String getKey();

	boolean canHandle(EditRule rule);

}
