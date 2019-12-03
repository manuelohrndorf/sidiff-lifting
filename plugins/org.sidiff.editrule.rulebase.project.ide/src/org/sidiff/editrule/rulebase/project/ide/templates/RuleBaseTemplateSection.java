package org.sidiff.editrule.rulebase.project.ide.templates;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.pde.core.plugin.IPluginBase;
import org.eclipse.pde.core.plugin.IPluginElement;
import org.eclipse.pde.core.plugin.IPluginExtension;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.core.plugin.IPluginModelFactory;
import org.eclipse.pde.core.plugin.IPluginReference;
import org.eclipse.pde.ui.IFieldData;
import org.eclipse.pde.ui.templates.OptionTemplateSection;
import org.eclipse.pde.ui.templates.PluginReference;
import org.osgi.framework.Bundle;
import org.sidiff.editrule.generator.exceptions.EditRuleGenerationException;
import org.sidiff.editrule.generator.exceptions.WrongSettingsInstanceException;
import org.sidiff.editrule.generator.settings.EditRuleGenerationSettings;
import org.sidiff.editrule.rulebase.builder.EditRuleBaseBuilder;
import org.sidiff.editrule.rulebase.builder.EditRuleBaseClassBuilder;
import org.sidiff.editrule.rulebase.builder.attachment.IEditRuleAttachmentBuilder;
import org.sidiff.editrule.rulebase.project.ide.internal.Activator;
import org.sidiff.editrule.rulebase.project.ide.nature.RuleBaseProjectNature;
import org.sidiff.editrule.rulebase.project.ide.wizard.RuleBaseProjectPage01;
import org.sidiff.editrule.rulebase.project.runtime.library.IRuleBaseProject;

public class RuleBaseTemplateSection extends OptionTemplateSection {

	private EditRuleGenerationSettings settings;

	public RuleBaseTemplateSection() {
		addOption(KEY_PACKAGE_NAME, RuleBaseTemplateSection.KEY_PACKAGE_NAME, (String) null, 0);
		this.settings = new EditRuleGenerationSettings(null, "", null, false, null, true);
		setPageCount(1);
	}

	@Override
	public String getUsedExtensionPoint() {
		return IRuleBaseProject.DESCRIPTION.getExtensionPointId();
	}

	@Override
	public String getSectionId() {
		return "rbproject";
	}

	@Override
	protected ResourceBundle getPluginResourceBundle() {
		Bundle bundle = Platform.getBundle(Activator.getPluginId());
		return Platform.getResourceBundle(bundle);
	}

	@Override
	protected URL getInstallURL() {
		return Activator.getDefault().getInstallURL();
	}

	@Override
	protected void updateModel(IProgressMonitor monitor) throws CoreException {
		addRuleBaseExtension();
		addRuleBaseNature();
	}

	@Override
	public void addPages(Wizard wizard) {
		RuleBaseProjectPage01 ruleBaseProjectPage01 = new RuleBaseProjectPage01(settings);
		wizard.addPage(ruleBaseProjectPage01);
		markPagesAdded();
	}

	@Override
	public boolean isDependentOnParentWizard() {
		return true;
	}

	@Override
	public String[] getNewFiles() {
		// build.properties:
		Set<String> newFiles = new HashSet<>();
		newFiles.add(IRuleBaseProject.EDIT_RULE_FOLDER + "/");
		newFiles.add(IRuleBaseProject.RULEBASE_FILE);
		newFiles.addAll(IEditRuleAttachmentBuilder.MANAGER.getAttachmentNewFolders());
		return newFiles.toArray(new String[0]);
	}

	@Override
	public IPluginReference[] getDependencies(String schemaVersion) {
		return new IPluginReference[] {
			new PluginReference("org.sidiff.editrule.rulebase.project.runtime", null, 0)
		};
	}

	@Override
	protected void initializeFields(IFieldData data) {
		// In a new project wizard, we don't know this yet - the
		// model has not been created
		String id = data.getId();
		initializeOption(KEY_PACKAGE_NAME, EditRuleBaseClassBuilder.getFormattedPackageName(id));
	}

	@Override
	public void initializeFields(IPluginModelBase model) {
		// In the new extension wizard, the model exists so
		// we can initialize directly from it
		String pluginId = model.getPluginBase().getId();
		initializeOption(KEY_PACKAGE_NAME, EditRuleBaseClassBuilder.getFormattedPackageName(pluginId));
	}

	private void addRuleBaseExtension() throws CoreException {
		IPluginBase plugin = model.getPluginBase();
		IPluginModelFactory factory = model.getPluginFactory();
		IPluginExtension extension = createExtension(IRuleBaseProject.DESCRIPTION.getExtensionPointId(), true);
		IPluginElement element = factory.createElement(extension);
		element.setName(IRuleBaseProject.DESCRIPTION.getElementName());
		
		// We only assume one attribute at this time ("rulebase")
		element.setAttribute(IRuleBaseProject.DESCRIPTION.getClassAttribute(),
				getStringOption(KEY_PACKAGE_NAME) + "." + EditRuleBaseBuilder.RULE_BASE_CLASS);
		extension.add(element);
		plugin.add(extension);
	}

	private void addRuleBaseNature() throws CoreException {
		IProjectDescription description = project.getDescription();
		String[] natures = description.getNatureIds();
		String[] newNatures = new String[natures.length + 1];
		System.arraycopy(natures, 0, newNatures, 1, natures.length);
		// Copy to first so the icon is shown
		newNatures[0] = RuleBaseProjectNature.NATURE_ID;
		description.setNatureIds(newNatures);
		project.setDescription(description, null);
	}

	@Override
	public void execute(IProject project, IPluginModelBase model, IProgressMonitor monitor) throws CoreException {
		super.execute(project, model, monitor);

		// Use new plug-in project id + edit rule folder as output path 
		settings.setOutputFolderPath(project.getName() + "/" + IRuleBaseProject.EDIT_RULE_FOLDER);
	
		// Only generate if valid
		if (settings.getGenerator() != null && (settings.getConfigPath() != null || settings.getMetaModelNsUri() != null)) {

			// Init Generator with settings. Whether default or refined config
			// should be used is simply found out in init()-implementation (when pathToConfig is null).			
			try {
				settings.getGenerator().init(settings, monitor);
				settings.getGenerator().generateEditRules(monitor);
			} catch (WrongSettingsInstanceException | EditRuleGenerationException e) {
				throw new CoreException(new Status(IStatus.ERROR, Activator.getPluginId(), "Generating editrules failed", e));
			}
		}
	}
}
