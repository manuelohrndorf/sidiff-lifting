package org.sidiff.difference.technical.ui.widgets;

import java.util.Objects;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.sidiff.common.ui.widgets.AbstractContainerWidget;
import org.sidiff.difference.technical.ui.Activator;
import org.sidiff.matching.input.InputModels;

public class InputModelsWidget extends AbstractContainerWidget {

	private String arrowLabel;
	private InputModels inputModels;
	private boolean inverseDirection = false;

	/**
	 * Creates a new InputModelsWidget for the given InputModels, containing exactly two models.
	 * Default values:
	 * <ul>
	 * <li>Title: "Select origin model"</li>
	 * <li>Arrow label: "Direction"</li>
	 * </ul>
	 * @param inputModels the input models, must be exactly two
	 */
	public InputModelsWidget(InputModels inputModels) {
		Assert.isLegal(inputModels.getFiles().size() == 2, "Exactly two input models must be specified");
		this.inputModels = inputModels;
		setTitle("Select origin model");
		setArrowLabel("Direction");
	}

	/**
	 * @deprecated Use {@link #InputModelsWidget(InputModels)} instead and
	 * set the label using {@link #setArrowLabel(String)}.
	 */
	public InputModelsWidget(InputModels inputModels, String arrowLabel) {
		this(inputModels);
		setArrowLabel(arrowLabel);
	}

	@Override
	protected Composite createContents(Composite container) {
		Composite gridContainer = new Composite(container, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(gridContainer);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(gridContainer);

		// Model A:
		Button radioA = new Button(gridContainer, SWT.RADIO);
		radioA.setText("Model: " + inputModels.getLabels().get(0));
		radioA.setSelection(true);
		GridDataFactory.fillDefaults().span(2, 1).grab(true, true).applyTo(radioA);

		// Arrow:
		final Image arrowUp = Activator.getImageDescriptor("arrow_up.png").createImage();
		final Image arrowDown = Activator.getImageDescriptor("arrow_down.png").createImage();

		final Label arrowImage = new Label(gridContainer, SWT.NONE);
		arrowImage.setImage(inverseDirection ? arrowUp : arrowDown);
		arrowImage.addDisposeListener(e -> {
			arrowUp.dispose();
			arrowDown.dispose();
		});
		GridDataFactory.fillDefaults().grab(false, true).applyTo(arrowImage);

		Label arrowText = new Label(gridContainer, SWT.NONE);
		arrowText.setSize(55, 15);
		arrowText.setText(arrowLabel);
		GridDataFactory.swtDefaults().grab(false, true).applyTo(arrowText);

		// Model B:
		Button radioB = new Button(gridContainer, SWT.RADIO);
		radioB.setText("Model: " + inputModels.getLabels().get(1));
		GridDataFactory.fillDefaults().span(2, 1).grab(true, true).applyTo(radioB);

		/*
		 * Swap action
		 */

		SelectionListener selectionListener = SelectionListener.widgetSelectedAdapter(e -> {
			if((inverseDirection && radioA.getSelection()) || (!inverseDirection && radioB.getSelection())) {
				inverseDirection = !inverseDirection;
				arrowImage.setImage(inverseDirection ? arrowUp : arrowDown);
				inputModels.swap();				
			}
		});

		radioA.addSelectionListener(selectionListener);
		radioB.addSelectionListener(selectionListener);

		return gridContainer;
	}

	public void setArrowLabel(String arrowLabel) {
		this.arrowLabel = Objects.requireNonNull(arrowLabel);
	}

	public String getArrowLabel() {
		return arrowLabel;
	}
}
