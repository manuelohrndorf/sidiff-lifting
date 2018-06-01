package org.sidiff.difference.technical.ui.widgets;

import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
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
import org.sidiff.common.ui.widgets.AbstractWidget;
import org.sidiff.common.ui.widgets.IWidgetSelection;
import org.sidiff.common.ui.widgets.IWidgetValidation;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;
import org.sidiff.difference.technical.ui.Activator;
import org.sidiff.matching.input.InputModels;

public class InputModelsWidget extends AbstractWidget implements IWidgetSelection, IWidgetValidation {

	private String arrowLabel;
	private InputModels inputModels;
	private boolean inverseDirection = false;

	private Composite container;
	private Button modelARadio;
	private Button modelBRadio;

	public InputModelsWidget(InputModels inputModels, String arrowLabel) {
		Assert.isLegal(inputModels.getFiles().size() == 2, "Exactly two input models must be specified");
		this.inputModels = inputModels;
		this.arrowLabel = arrowLabel;
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

		/*
		 *  Swap models:
		 */

		Group modelsGroup = new Group(container, SWT.NONE);
		{
			GridLayout grid = new GridLayout(1, false);
			grid.marginWidth = 10;
			grid.marginHeight = 10;
			modelsGroup.setLayout(grid);
			modelsGroup.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
			modelsGroup.setText("Select origin model:");
		}

		// Model A:
		modelARadio = new Button(modelsGroup, SWT.RADIO);
		modelARadio.setText("Model: " + inputModels.getLabels().get(0));
		modelARadio.setSelection(true);

		// Arrow:
		final Image arrowUp = Activator.getImageDescriptor("arrow_up.png").createImage();
		final Image arrowDown = Activator.getImageDescriptor("arrow_down.png").createImage();

		Composite composite_arrow = new Composite(modelsGroup, SWT.NONE);
		composite_arrow.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		composite_arrow.setLayout(new GridLayout(2, false));
		
		final Label arrow = new Label(composite_arrow, SWT.NONE);
		arrow.setImage(arrowDown);
		arrow.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				arrowUp.dispose();
				arrowDown.dispose();
			}
		});

		Label label_arrow = new Label(composite_arrow, SWT.NONE);
		label_arrow.setSize(55, 15);
		label_arrow.setText(arrowLabel);

		// Model B:
		modelBRadio = new Button(modelsGroup, SWT.RADIO);
		modelBRadio.setText("Model: " + inputModels.getLabels().get(1));

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

		return container;
	}

	@Override
	public Composite getWidget() {
		return container;
	}

	/**
	 * @return <code>true</code> if the direction is B->A; false if the
	 *         direction is A->B.
	 */
	public boolean isInverseDirection() {
		return inverseDirection;
	}

	@Override
	public boolean validate() {
		return (modelARadio.getSelection() || modelBRadio.getSelection());
	}

	@Override
	public ValidationMessage getValidationMessage() {
		if (validate()) {
			return ValidationMessage.OK;
		} else {
			return new ValidationMessage(ValidationType.ERROR, "Please select a source model!");
		}
	}

	@Override
	public void addSelectionListener(SelectionListener listener) {
		if(modelARadio == null || modelBRadio == null) {
			throw new IllegalStateException("createControl must be called first");
		}
		modelARadio.addSelectionListener(listener);
		modelBRadio.addSelectionListener(listener);
	}

	@Override
	public void removeSelectionListener(SelectionListener listener) {
		if(modelARadio == null || modelBRadio == null) {
			throw new IllegalStateException("createControl must be called first");
		}
		modelARadio.removeSelectionListener(listener);
		modelBRadio.removeSelectionListener(listener);
	}
}
