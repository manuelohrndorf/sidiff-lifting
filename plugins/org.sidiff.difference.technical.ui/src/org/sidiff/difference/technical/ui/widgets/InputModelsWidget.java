package org.sidiff.difference.technical.ui.widgets;

import org.eclipse.core.resources.IContainer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.sidiff.common.settings.BaseSettings;
import org.sidiff.common.settings.BaseSettingsItem;
import org.sidiff.common.settings.ISettingsChangedListener;
import org.sidiff.common.ui.widgets.IWidget;
import org.sidiff.common.ui.widgets.IWidgetSelection;
import org.sidiff.common.ui.widgets.IWidgetValidation;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;
import org.sidiff.difference.technical.ui.Activator;
import org.sidiff.matching.input.InputModels;

public class InputModelsWidget implements IWidget, IWidgetSelection, IWidgetValidation, ISettingsChangedListener {

	private BaseSettings settings;
	private InputModels inputModels;

	private Composite container;
	private Button modelARadio;
	private Button modelBRadio;
	private Button buttonValidateModels;

	private String arrowLabel;

	private boolean inverseDirection = false;
	private Composite composite_arrow;
	private Label label_arrow;

	public InputModelsWidget(InputModels inputModels, String arrowLabel) {
		this.inputModels = inputModels;
		this.arrowLabel = arrowLabel;
	}

	private String[] getFileNames() {
		String resourceA_name = inputModels.getFiles().get(0).getName();
		String resourceB_name = inputModels.getFiles().get(1).getName();

		IContainer parentA = inputModels.getFiles().get(0).getParent();
		IContainer parentB = inputModels.getFiles().get(1).getParent();

		while (resourceA_name.equals(resourceB_name)) {
			resourceA_name = parentA.getName() + "/" + resourceA_name;
			resourceB_name = parentB.getName() + "/" + resourceB_name;
			parentA = parentA.getParent();
			parentB = parentB.getParent();
		}

		String[] result = { resourceA_name, resourceB_name };
		return result;
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

		// Generate model file names:
		String[] names = getFileNames();
		String resourceA_name = names[0];
		String resourceB_name = names[1];

		/*
		 *  Swap models:
		 */

		Group modelsGroup = new Group(container, SWT.NONE);
		{
			GridLayout grid = new GridLayout(2, false);
			grid.marginWidth = 10;
			grid.marginHeight = 10;
			modelsGroup.setLayout(grid);
			modelsGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

			modelsGroup.setText("Select origin model:");
		}

		// Model A:
		modelARadio = new Button(modelsGroup, SWT.RADIO);
		modelARadio.setText("Model: " + resourceA_name);
		modelARadio.setSelection(true);

		// Arrow:
		final Image arrowUp = Activator.getImageDescriptor("arrow_up.png").createImage();
		final Image arrowDown = Activator.getImageDescriptor("arrow_down.png").createImage();
		new Label(modelsGroup, SWT.NONE);
		
		composite_arrow = new Composite(modelsGroup, SWT.NONE);
		composite_arrow.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		composite_arrow.setLayout(new GridLayout(2, false));
		
		final Label arrow = new Label(composite_arrow, SWT.NONE);
		{
			arrow.setImage(arrowDown);
		}

		label_arrow = new Label(composite_arrow, SWT.NONE);
		label_arrow.setSize(55, 15);
		label_arrow.setText(arrowLabel);
		new Label(modelsGroup, SWT.NONE);

		// Model B:
		modelBRadio = new Button(modelsGroup, SWT.RADIO);
		modelBRadio.setText("Model: " + resourceB_name);
		new Label(modelsGroup, SWT.NONE);

		Label label = new Label(modelsGroup, SWT.SEPARATOR | SWT.HORIZONTAL | SWT.SHADOW_IN
				| SWT.CENTER);
		{
			GridData data = new GridData(GridData.FILL_HORIZONTAL);
			data.heightHint = 10;
			data.horizontalSpan = 2;
			label.setLayoutData(data);
		}

		/*
		 * Swap action
		 */

		modelARadio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (inverseDirection) {
					arrow.setImage(arrowDown);
					inverseDirection = false;
					inputModels.swap();
				}
			}
		});

		modelBRadio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!inverseDirection) {
					arrow.setImage(arrowUp);
					inverseDirection = true;
					inputModels.swap();
				}
			}
		});

		/*
		 *  Validate models
		 */

		buttonValidateModels = new Button(modelsGroup, SWT.CHECK);
		{
			buttonValidateModels.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
			buttonValidateModels.setText("Validate Models");
		}
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

	@Override
	public void setLayoutData(Object layoutData) {
		container.setLayoutData(layoutData);
	}

	/**
	 * @return <code>true</code> if the direction is B->A; false if the
	 *         direction is A->B.
	 */
	public boolean isInverseDirection() {
		return inverseDirection;
	}

	public boolean isValidateModels() {
		return buttonValidateModels != null && buttonValidateModels.getSelection();
	}

	@Override
	public boolean validate() {
		return (modelARadio.getSelection() || modelBRadio.getSelection());
	}

	@Override
	public ValidationMessage getValidationMessage() {
		if (validate()) {
			return new ValidationMessage(ValidationType.OK, "");
		} else {
			return new ValidationMessage(ValidationType.ERROR, "Please select a source model!");
		}
	}

	@Override
	public void addSelectionListener(SelectionListener listener) {
		if ((modelARadio == null) || (modelBRadio == null)) {
			throw new RuntimeException("Create controls first!");
		}
		modelARadio.addSelectionListener(listener);
		modelBRadio.addSelectionListener(listener);
	}

	@Override
	public void removeSelectionListener(SelectionListener listener) {
		if ((modelARadio != null) && (modelBRadio != null)) {
			modelARadio.removeSelectionListener(listener);
			modelBRadio.removeSelectionListener(listener);
		}
	}

	@Override
	public void settingsChanged(Enum<?> item) {
		if(item == BaseSettingsItem.VALIDATE) {
			updateButtonState();
		}
	}

	private void updateButtonState() {
		if(buttonValidateModels != null) buttonValidateModels.setSelection(settings.isValidate());
	}

	public BaseSettings getSettings() {
		return settings;
	}

	public void setSettings(BaseSettings settings) {
		this.settings = settings;
		this.settings.addSettingsChangedListener(this);
		updateButtonState();
	}
}
