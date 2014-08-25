package org.sidiff.difference.rulebase.project.templates;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
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
import org.osgi.framework.FrameworkUtil;
import org.sidiff.difference.rulebase.extension.AbstractProjectRuleBase;
import org.sidiff.difference.rulebase.extension.IRuleBase;
import org.sidiff.difference.rulebase.nature.RuleBaseProjectNature;

public class RuleBaseTemplateSection extends OptionTemplateSection {

	public RuleBaseTemplateSection() {
		addOption(KEY_PACKAGE_NAME, RuleBaseTemplateSection.KEY_PACKAGE_NAME, (String) null, 0);
	}

	@Override
	public String getUsedExtensionPoint() {
		return IRuleBase.extensionPointID;
	}

	@Override
	public String getSectionId() {
		return "rbproject";
	}

	@Override
	protected URL getInstallURL() {		
		return FrameworkUtil.getBundle(getClass()).getEntry("/");
	}

	@Override
	protected ResourceBundle getPluginResourceBundle() {
		return null;
	}

	@Override
	protected void updateModel(IProgressMonitor monitor) throws CoreException {
		addRuleBaseExtension();		
		addRuleBaseNature();
	}


	@Override
	public void addPages(Wizard wizard) {
		//TODO later on: SERGe Generation PAGE
	}

	public boolean isDependentOnParentWizard() {
		return true;
	}


	@Override
	public String[] getNewFiles() {
		return new String[] { AbstractProjectRuleBase.SOURCE_FOLDER + "/"}; 
	}	

	public IPluginReference[] getDependencies(String schemaVersion) {
		IPluginReference[] result = new IPluginReference[2];
		result[0] = new PluginReference("org.sidiff.difference.rulebase", null, 0); 
		result[1] = new PluginReference("org.eclipse.emf.henshin.model", null, 0);
		return result;
	}

	protected void initializeFields(IFieldData data) {
		// In a new project wizard, we don't know this yet - the
		// model has not been created
		String id = data.getId();
		initializeOption(KEY_PACKAGE_NAME, getFormattedPackageName(id));
	}

	public void initializeFields(IPluginModelBase model) {
		// In the new extension wizard, the model exists so 
		// we can initialize directly from it
		String pluginId = model.getPluginBase().getId();
		initializeOption(KEY_PACKAGE_NAME, getFormattedPackageName(pluginId));
	}

	protected String getFormattedPackageName(String id) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < id.length(); i++) {
			char ch = id.charAt(i);
			if (buffer.length() == 0) {
				if (Character.isJavaIdentifierStart(ch))
					buffer.append(Character.toLowerCase(ch));
			} else {
				if (Character.isJavaIdentifierPart(ch) || ch == '.')
					buffer.append(ch);
			}
		}
		return buffer.toString().toLowerCase(Locale.ENGLISH);
	}


	private void addRuleBaseExtension() throws CoreException {
		try{
			IPluginBase plugin = model.getPluginBase();
			IPluginModelFactory factory = model.getPluginFactory();
			IPluginExtension extension = createExtension(IRuleBase.extensionPointID, true);
			IPluginElement element = factory.createElement(extension);
			element.setName("rulebase");
			// We only assume one attribute at this time ("rulebase")
			element.setAttribute("rulebase", getStringOption(KEY_PACKAGE_NAME) + "." + "ProjectRuleBase");
			extension.add(element);
			plugin.add(extension);
		}
		catch (CoreException e){
			//Nothing to do
		}
	}


	private void addRuleBaseNature(){
		try {
			IProjectDescription description = project.getDescription();
			String[] natures = description.getNatureIds();
			String[] newNatures = new String[natures.length + 1];
			System.arraycopy(natures, 0, newNatures, 1, natures.length);
			//Copy to first so the icon is shown
			newNatures[0] = RuleBaseProjectNature.NATURE_ID;
			description.setNatureIds(newNatures);
			project.setDescription(description, null);
		} catch (CoreException e) {
			// Something went wrong
		}
	}
}
