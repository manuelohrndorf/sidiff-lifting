package org.sidiff.model.statemachine.editrules.atomic;

import java.util.HashSet;
import java.util.Set;

import org.sidiff.editrule.rulebase.project.runtime.library.AbstractRuleBaseProject;

public class RuleBaseProject extends AbstractRuleBaseProject {

	@Override
	public String getName() {
		return "Atomic";
	}
	
	@Override
	public Set<String> getDocumentTypes() {
		Set<String> types = new HashSet<String>();
		types.add("http://www.sidiff.org/model/statemachine");

		return types;
	}
	
	@Override
	public Set<String> getAttachmentTypes() {
		Set<String> types = new HashSet<String>();
		types.add("org.sidiff.difference.rulebase.RecognitionRule");

		return types;
	}
}
