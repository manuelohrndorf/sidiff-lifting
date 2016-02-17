package org.sidiff.patching.ui.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.sidiff.common.settings.settings.ISettingsChangedListener;
import org.sidiff.common.ui.widgets.IWidget;
import org.sidiff.common.ui.widgets.IWidgetSelection;
import org.sidiff.common.ui.widgets.IWidgetValidation;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;
import org.sidiff.patching.settings.PatchingSettings;
import org.sidiff.patching.settings.PatchingSettings.ValidationMode;

public class ValidationModeWidget implements IWidget, IWidgetSelection, IWidgetValidation, ISettingsChangedListener {

	private PatchingSettings settings;
	private ValidationMode validationMode = ValidationMode.NO_VALIDATION;

	private Composite container;
	private Button noValidationButton;
	private Button iterativeValidationButton;
	private Button modelValidationButton;

	public ValidationModeWidget() {
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public Composite createControl(Composite parent) {

		container = new Composite(parent, SWT.NONE);
		{
			GridLayout grid = new GridLayout(1, false);
			grid.marginWidth = 0;
			grid.marginHeight = 0;
			container.setLayout(grid);
		}

		Group comparisonGroup = new Group(container, SWT.NONE);
		{
			GridLayout grid = new GridLayout(1, false);
			grid.marginWidth = 10;
			grid.marginHeight = 10;
			comparisonGroup.setLayout(grid);
			comparisonGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		}
		comparisonGroup.setText("Validation mode:");

		noValidationButton = new Button(comparisonGroup, SWT.RADIO);
		noValidationButton.setText("No Validation");	

		modelValidationButton = new Button(comparisonGroup, SWT.RADIO);
		modelValidationButton.setText("Model Validation");
		
		iterativeValidationButton = new Button(comparisonGroup, SWT.RADIO);
		iterativeValidationButton.setText("Iterative Validation");


		noValidationButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				validationMode = ValidationMode.NO_VALIDATION;
				((PatchingSettings)settings).setValidationMode(validationMode);
			}
		});

		modelValidationButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				validationMode = ValidationMode.MODEL_VALIDATION;
				((PatchingSettings)settings).setValidationMode(validationMode);
			}
		});
		
		iterativeValidationButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				validationMode = ValidationMode.ITERATIVE_VALIDATION;
				((PatchingSettings)settings).setValidationMode(validationMode);
			}
		});
		
		noValidationButton.setSelection(true);
		((PatchingSettings)settings).setValidationMode(this.validationMode);
		return container;
	}

	@Override
	public Composite getWidget() {
		return container;
	}

	@Override
	public void setLayoutData(Object layoutData) {
		container.setLayoutData(layoutData);
	}

	public ValidationMode getSelection() {
		if (validate()) {
			return validationMode;
		} else {
			return null;
		}
	}

	@Override
	public boolean validate() {
		if (noValidationButton.getSelection() || modelValidationButton.getSelection()|| iterativeValidationButton.getSelection() ){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public ValidationMessage getValidationMessage() {
		ValidationMessage message;
		if (validate()) {
			message = new ValidationMessage(ValidationType.OK, "");
		} else {
			message = new ValidationMessage(ValidationType.ERROR, "Please select a validation mode!");
		}
		return message;
	}

	@Override
	public void addSelectionListener(SelectionListener listener) {
		if ((noValidationButton == null) || (iterativeValidationButton == null) || (modelValidationButton == null)) {
			throw new RuntimeException("Create controls first!");
		}
		noValidationButton.addSelectionListener(listener);
		modelValidationButton.addSelectionListener(listener);
		iterativeValidationButton.addSelectionListener(listener);
	}

	@Override
	public void removeSelectionListener(SelectionListener listener) {
		if (noValidationButton != null)
			noValidationButton.removeSelectionListener(listener);
		if (modelValidationButton != null)
			modelValidationButton.removeSelectionListener(listener);
		if (iterativeValidationButton != null)
			iterativeValidationButton.removeSelectionListener(listener);
	}

	@Override
	public void settingsChanged(Enum<?> item) {
	}

	public PatchingSettings getSettings() {
		return settings;
	}

	public void setSettings(PatchingSettings settings) {
		this.settings = settings;
	}
}
