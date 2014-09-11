package org.sidiff.difference.rulebase.project.wizard;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.pde.internal.ui.elements.ElementList;
import org.eclipse.pde.internal.ui.wizards.plugin.ContentPage;
import org.eclipse.pde.internal.ui.wizards.plugin.NewPluginProjectWizard;
import org.eclipse.pde.internal.ui.wizards.plugin.TemplateListSelectionPage;
import org.eclipse.pde.ui.IPluginContentWizard;

/**
 * This is a wizard for creating a new rulebase plug-ins.
 */
@SuppressWarnings("restriction")
public class DirectRulebaseProjectWizard extends NewPluginProjectWizard {
	
	private RulebaseProjectWizard rulebaseWizard = new RulebaseProjectWizard();
	
	
	public DirectRulebaseProjectWizard() {
		super();
		this.setWindowTitle("Rule-Base Plug-In Project");
	}
	
	@Override
	public void addPage(IWizardPage page) {

		if (page instanceof TemplateListSelectionPage) {
			try {
				TemplateListSelectionPage templates = (TemplateListSelectionPage) page;
				
		        // Access private member: fWizardListPage
		        final Class<NewPluginProjectWizard> pluginWizardClass = NewPluginProjectWizard.class;
		        final java.lang.reflect.Field field = pluginWizardClass.getDeclaredField("fWizardListPage");
		        field.setAccessible(true);

				// Replace template selection page:
		        field.set(this, new RulebaseTemplate(templates.getWizardElements(), fContentPage, templates.getMessage()));		        
			} catch (Exception e) {
				super.addPage(page);
			}
		} else {
			super.addPage(page);
		}
	}
	
	@Override
	public void addPages() {
		super.addPages();
		
		// Add rulebase wizard pages:
		rulebaseWizard.addPages();
		
		for (IWizardPage page : rulebaseWizard.getPages()) {
			addPage(page);
		}
	}
	
	private class RulebaseTemplate extends TemplateListSelectionPage {

		public RulebaseTemplate(ElementList wizardElements, ContentPage page, String message) {
			super(wizardElements, page, message);
		}
		
		@Override
		public IPluginContentWizard getSelectedWizard() {
			return rulebaseWizard;
		}
	}
}
