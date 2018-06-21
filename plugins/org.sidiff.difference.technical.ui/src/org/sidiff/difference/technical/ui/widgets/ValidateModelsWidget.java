package org.sidiff.difference.technical.ui.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.sidiff.common.settings.BaseSettings;
import org.sidiff.common.settings.BaseSettingsItem;
import org.sidiff.common.settings.ISettingsChangedListener;
import org.sidiff.common.ui.widgets.AbstractWidget;
import org.sidiff.common.ui.widgets.IWidgetDisposable;
import org.sidiff.common.ui.widgets.IWidgetSelection;

public class ValidateModelsWidget extends AbstractWidget implements IWidgetSelection, IWidgetDisposable, ISettingsChangedListener {

	private BaseSettings settings;

	private Composite container;
	private Button buttonValidateModels;

	public ValidateModelsWidget() {

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

		Group modelsGroup = new Group(container, SWT.NONE);
		{
			GridLayout grid = new GridLayout(1, false);
			grid.marginWidth = 10;
			grid.marginHeight = 10;
			modelsGroup.setLayout(grid);
			modelsGroup.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
			modelsGroup.setText("Input Model Validation:");
		}

		/*
		 *  Validate models
		 */

		buttonValidateModels = new Button(modelsGroup, SWT.CHECK);
		buttonValidateModels.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		buttonValidateModels.setText("Validate Models");
		buttonValidateModels.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				settings.setValidate(buttonValidateModels.getSelection());
			}
		});
		updateButtonState();

		return container;
	}

	@Override
	public Composite getWidget() {
		return container;
	}

	public boolean isValidateModels() {
		return buttonValidateModels != null && buttonValidateModels.getSelection();
	}

	@Override
	public void addSelectionListener(SelectionListener listener) {
		if(buttonValidateModels == null) {
			throw new IllegalStateException("createControl must be called first");
		}
		buttonValidateModels.addSelectionListener(listener);
	}

	@Override
	public void removeSelectionListener(SelectionListener listener) {
		if(buttonValidateModels == null) {
			throw new IllegalStateException("createControl must be called first");
		}
		buttonValidateModels.removeSelectionListener(listener);
	}

	@Override
	public void settingsChanged(Enum<?> item) {
		if(item == BaseSettingsItem.VALIDATE) {
			updateButtonState();
			getWidgetCallback().requestValidation();
		}
	}

	protected void updateButtonState() {
		if(buttonValidateModels != null) buttonValidateModels.setSelection(settings.isValidate());
	}

	public BaseSettings getSettings() {
		return settings;
	}

	public void setSettings(BaseSettings settings) {
		if(this.settings != null) {
			this.settings.removeSettingsChangedListener(this);
		}
		this.settings = settings;
		this.settings.addSettingsChangedListener(this);
		updateButtonState();
	}

	@Override
	public void dispose() {
		if(settings != null) {
			settings.removeSettingsChangedListener(this);
			settings = null;
		}
	}
}
