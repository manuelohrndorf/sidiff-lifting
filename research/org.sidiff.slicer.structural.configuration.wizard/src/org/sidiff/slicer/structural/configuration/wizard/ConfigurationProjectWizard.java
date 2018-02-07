package org.sidiff.slicer.structural.configuration.wizard;

import org.eclipse.pde.ui.IFieldData;
import org.eclipse.pde.ui.templates.ITemplateSection;
import org.eclipse.pde.ui.templates.NewPluginTemplateWizard;
import org.sidiff.slicer.structural.configuration.wizard.templates.ConfigurationProjectTemplateSection;

public class ConfigurationProjectWizard extends NewPluginTemplateWizard
{
	@Override
	public void init(IFieldData data)
	{
		super.init(data);
		setWindowTitle(ConfigurationWizardPlugin.INSTANCE.getString("_UI_ProjectWizard_title")); //$NON-NLS-1$
	}

	@Override
	public ITemplateSection[] createTemplateSections()
	{
		return new ITemplateSection[] { new ConfigurationProjectTemplateSection() };
	}
}
