package org.sidiff.uml2v4.editrules.atomic;

import java.util.HashSet;
import java.util.Set;

import org.sidiff.editrule.rulebase.project.runtime.library.AbstractRuleBaseProject;

public class UML2v4RuleBaseProject extends AbstractRuleBaseProject{

	@Override
	public String getName() {
		return "Edit Rules - UML2v4 - Atomic";
	}

	@Override
	public Set<String> getAttachmentTypes() {
		Set<String> types = new HashSet<String>();
		types.add("org.sidiff.difference.rulebase.RecognitionRule");

		return types;
	}

	@Override
	public Set<String> getDocumentTypes() {
		Set<String> types = new HashSet<String>();
		types.add("http://www.eclipse.org/uml2/5.0.0/UML");
		types.add("http://www.eclipse.org/uml2/5.0.0/Types");

		return types;
	}

}
