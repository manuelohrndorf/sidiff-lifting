package org.sidiff.ecore.editrules.atomic;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.sidiff.editrule.rulebase.project.runtime.library.AbstractRuleBaseProject;

public class RuleBaseProject extends AbstractRuleBaseProject {

	@Override
	public String getRuleBaseName() {
		return "Ecore Atomic";
	}

	@Override
	public Set<String> getRuleBaseTypes() {
		Set<String> types = new HashSet<String>();
		types.add("org.sidiff.editrule.rulebase.type.basic.IBasicRuleBase");
		types.add("org.sidiff.editrule.rulebase.type.editrule.IEditRuleBase");
		types.add("org.sidiff.difference.rulebase.type.ILiftingRuleBase");
		return types;
	}

	@Override
	public Set<String> getDocumentTypes() {
		return Collections.singleton("http://www.eclipse.org/emf/2002/Ecore");
	}

}
