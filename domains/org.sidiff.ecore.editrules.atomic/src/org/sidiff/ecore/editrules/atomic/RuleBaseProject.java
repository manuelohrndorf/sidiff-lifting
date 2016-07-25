package org.sidiff.ecore.editrules.atomic;

import java.util.HashSet;
import java.util.Set;

import org.sidiff.editrule.rulebase.project.runtime.library.AbstractRuleBaseProject;

public class RuleBaseProject extends AbstractRuleBaseProject {

	@Override
	public String getName() {
		return "SiDiff Domain - Atomic Edit Rules for Ecore";
	}
	
	@Override
	public Set<String> getDocumentTypes() {
		Set<String> types = new HashSet<String>();
		types.add("http://www.eclipse.org/emf/2002/Ecore");

		return types;
	}
	
	@Override
	public Set<String> getAttachmentTypes() {
		Set<String> types = new HashSet<String>();
		types.add("org.sidiff.difference.rulebase.RecognitionRule");

		return types;
	}
}
