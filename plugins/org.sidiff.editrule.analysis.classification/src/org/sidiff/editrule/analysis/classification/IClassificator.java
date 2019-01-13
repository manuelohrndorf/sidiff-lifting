package org.sidiff.editrule.analysis.classification;

import org.sidiff.common.extension.IExtension;
import org.sidiff.editrule.rulebase.EditRule;

public interface IClassificator extends IExtension {

	Description<IClassificator> DESCRIPTION = Description.of(IClassificator.class,
			"org.sidiff.editrule.analysis.classification.classification_extension", "classification", "classification");

	ClassificatorManager MANAGER = new ClassificatorManager(DESCRIPTION);


	int getClassificatorId();

	String createClassification(EditRule rule);

	boolean canHandle(EditRule rule);
}
