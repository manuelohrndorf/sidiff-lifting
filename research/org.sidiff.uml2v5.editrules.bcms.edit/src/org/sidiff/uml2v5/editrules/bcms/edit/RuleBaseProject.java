package org.sidiff.uml2v5.editrules.bcms.edit;

import java.util.HashSet;
import java.util.Set;

import org.sidiff.editrule.rulebase.project.runtime.library.AbstractRuleBaseProject;

public class RuleBaseProject extends AbstractRuleBaseProject {

	@Override
	public String getName() {
		return "Edit (2017-10-24 21:48:00)";
	}
	
	@Override
	public Set<String> getDocumentTypes() {
		Set<String> types = new HashSet<String>();
		types.add("http://www.eclipse.org/uml2/5.0.0/UML");

		return types;
	}
	
	@Override
	public Set<String> getAttachmentTypes() {
		Set<String> types = new HashSet<String>();
		types.add("org.sidiff.difference.rulebase.RecognitionRule");

		return types;
	}
}
