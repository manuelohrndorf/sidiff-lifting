package org.sidiff.difference.rulebase.project.templates;

import java.net.URL;
import java.util.ResourceBundle;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.pde.ui.templates.OptionTemplateSection;
import org.sidiff.difference.rulebase.extension.IRuleBase;

public class RuleBaseTemplateSection extends OptionTemplateSection {
	
	//TODO later on: SERGe Generation PAGE
	
	@Override
	public String getUsedExtensionPoint() {
		return IRuleBase.extensionPointID;
	}

	@Override
	public String[] getNewFiles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSectionId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected URL getInstallURL() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ResourceBundle getPluginResourceBundle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void updateModel(IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub		
	}

	@Override
	public void addPages(Wizard wizard) {
		//TODO
	}	

}
