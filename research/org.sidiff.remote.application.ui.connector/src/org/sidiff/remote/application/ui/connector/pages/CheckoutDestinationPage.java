package org.sidiff.remote.application.ui.connector.pages;

import org.eclipse.jface.resource.ImageDescriptor;
import org.sidiff.common.ui.pages.AbstractWizardPage;
import org.sidiff.remote.application.connector.settings.CheckoutSettings;
import org.sidiff.remote.application.ui.connector.widgets.SelectTargetFolderWidget;

public class CheckoutDestinationPage extends AbstractWizardPage {

	private CheckoutSettings settings;
		
	private SelectTargetFolderWidget selectTargetFolderWidget;

	public CheckoutDestinationPage(String pageName, String title, CheckoutSettings settings) {
		super(pageName, title);
		this.settings = settings;
	}
	
	public CheckoutDestinationPage(String pageName, String title, ImageDescriptor titleImage, CheckoutSettings settings) {
		super(pageName, title, titleImage);
		this.settings = settings;
	}

	@Override
	protected void createWidgets() {
		this.selectTargetFolderWidget = new SelectTargetFolderWidget(settings);
		addWidget(container, this.selectTargetFolderWidget);
	}

	@Override
	protected String getDefaultMessage() {
		// TODO Auto-generated method stub
		return null;
	}

}
