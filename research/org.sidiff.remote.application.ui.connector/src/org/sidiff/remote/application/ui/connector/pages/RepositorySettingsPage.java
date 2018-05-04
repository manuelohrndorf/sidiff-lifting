package org.sidiff.remote.application.ui.connector.pages;

import org.eclipse.jface.resource.ImageDescriptor;
import org.sidiff.common.ui.pages.AbstractWizardPage;
import org.sidiff.remote.application.connector.settings.RepositorySettings;
import org.sidiff.remote.application.ui.connector.widgets.AuthenticationWidget;
import org.sidiff.remote.application.ui.connector.widgets.RepositoryUriValidationWidget;

public class RepositorySettingsPage extends AbstractWizardPage {

	private RepositorySettings settings;
	
	private RepositoryUriValidationWidget repositoryUriValidationWidget;
	
	private AuthenticationWidget authenticationWidget;
	
	public RepositorySettingsPage(String pageName, String title, RepositorySettings settings) {
		super(pageName, title);
		this.settings = settings;
	}

	
	public RepositorySettingsPage(String pageName, String title, ImageDescriptor titleImage, RepositorySettings settings) {
		super(pageName, title, titleImage);
		this.settings = settings;
	}


	@Override
	protected void createWidgets() {
		
		this.repositoryUriValidationWidget = new RepositoryUriValidationWidget(settings);
		addWidget(this.container, this.repositoryUriValidationWidget);
		
		this.authenticationWidget = new AuthenticationWidget(settings);
		addWidget(this.container, this.authenticationWidget);

	}

	@Override
	protected String getDefaultMessage() {
		return "";
	}

}
