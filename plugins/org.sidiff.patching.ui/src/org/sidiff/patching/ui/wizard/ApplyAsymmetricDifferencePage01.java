package org.sidiff.patching.ui.wizard;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.sidiff.common.ui.pages.AbstractWizardPage;
import org.sidiff.difference.ui.widgets.ScopeWidget;
import org.sidiff.patching.settings.PatchingSettings;
import org.sidiff.patching.ui.widgets.TargetModelWidget;
import org.sidiff.patching.ui.widgets.ValidationModeWidget;

public class ApplyAsymmetricDifferencePage01 extends AbstractWizardPage{

	private String DEFAULT_MESSAGE = "Apply a patch to a model";
	
	private TargetModelWidget targetWidget;
	private ScopeWidget scopeWidget;
	private ValidationModeWidget validationWidget;

	private PatchingSettings settings;

	public ApplyAsymmetricDifferencePage01(String pageName, String title, ImageDescriptor titleImage, PatchingSettings settings) {
		super(pageName, title, titleImage);
		this.settings = settings;
		// Listen for validation failures:
		validationListener =
				new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						validate();
					}
				};
	}

	protected void createWidgets() {
		
		//Target model:
		targetWidget = new TargetModelWidget();
		targetWidget.setSettings(this.settings);
		addWidget(container, targetWidget);

		// Comparison mode:
		scopeWidget = new ScopeWidget();
		scopeWidget.setSettings(this.settings);
		scopeWidget.setPageChangedListener(this);
		addWidget(container, scopeWidget);
		
		
		//Validation
		validationWidget = new ValidationModeWidget();
		validationWidget.setSettings(this.settings);
		addWidget(container, validationWidget);

	}
	
	public TargetModelWidget getTargetWidget(){
		return targetWidget;
	}
	
	public ScopeWidget getScopeWidget() {
		return scopeWidget;
	}

	public ValidationModeWidget getValidationWidget(){
		return validationWidget;
	}

	@Override
	protected String getDefaultMessage() {
		return DEFAULT_MESSAGE;
	}	
}
