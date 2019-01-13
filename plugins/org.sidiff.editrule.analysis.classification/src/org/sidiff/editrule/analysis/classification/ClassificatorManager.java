package org.sidiff.editrule.analysis.classification;

import java.util.Set;
import java.util.stream.Collectors;

import org.sidiff.common.extension.ExtensionManager;
import org.sidiff.common.extension.IExtension.Description;
import org.sidiff.editrule.rulebase.EditRule;

public class ClassificatorManager extends ExtensionManager<IClassificator> {

	public ClassificatorManager(final Description<? extends IClassificator> description) {
		super(description);
	}

	public Set<IClassificator> getClassificators(EditRule editRule) {
		return getExtensions().stream()
				.filter(ext -> ext.canHandle(editRule))
				.collect(Collectors.toSet());
	}
}
