package org.sidiff.editrule.rulebase.project.ide.wizard;

import org.eclipse.pde.ui.IFieldData;
import org.eclipse.pde.ui.templates.ITemplateSection;
import org.eclipse.pde.ui.templates.NewPluginTemplateWizard;
import org.sidiff.editrule.rulebase.project.ide.templates.RuleBaseTemplateSection;

/**
 * This is a simple wizard for creating a new model file.
 */
public class RulebaseProjectWizard  extends NewPluginTemplateWizard {
	

	@Override
	public void init(IFieldData data) {		
		super.init(data);
		setWindowTitle("RuleBase Plugin-Project Wizard");
	}
	
	@Override
	public ITemplateSection[] createTemplateSections() {
		return new ITemplateSection[] {new RuleBaseTemplateSection()};
		
	}


}
