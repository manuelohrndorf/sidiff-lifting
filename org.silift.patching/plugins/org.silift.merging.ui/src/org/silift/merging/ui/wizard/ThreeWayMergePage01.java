package org.silift.merging.ui.wizard;

import java.util.Set;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.sidiff.difference.lifting.ui.util.InputModels;
import org.sidiff.difference.lifting.ui.widgets.ComparisonModeWidget;
import org.sidiff.difference.lifting.ui.widgets.InputModelsWidget;
import org.sidiff.difference.lifting.ui.widgets.RulebaseWidget;
import org.sidiff.difference.rulebase.extension.IRuleBase;
import org.silift.common.util.ui.widgets.IWidget;
import org.silift.common.util.ui.widgets.IWidgetSelection;
import org.silift.common.util.ui.widgets.IWidgetValidation;

public class ThreeWayMergePage01 extends WizardPage {

	private Composite container;

	private InputModelsWidget sourceWidget;
	private ComparisonModeWidget comparisonWidget;
	private RulebaseWidget rulebaseWidget;

	private SelectionAdapter validationListener;

	private InputModels inputModels;

	public ThreeWayMergePage01(InputModels inputModels,
			String pageName, String title, ImageDescriptor titleImage) {
		super(pageName, title, titleImage);

		this.inputModels = inputModels;

		// Listen for validation failures:
		validationListener =
				new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						validate();
					}
				};
	}

	@Override
	public void createControl(Composite parent) {

		// Add scrolling to this page
		final Composite wrapper = new Composite(parent, SWT.NONE);
		{
			GridLayout layout = new GridLayout(1, false);
			layout.marginWidth = 0;
			layout.marginHeight = 0;
			wrapper.setLayout(layout);
		}

		final ScrolledComposite sc = new ScrolledComposite(wrapper, SWT.V_SCROLL);
		GridData sc_data = new GridData(SWT.FILL, SWT.FILL, true, true);
		{
			sc.setLayoutData(sc_data);

			sc.setExpandHorizontal(true);
			sc.setExpandVertical(true);
		}

		container = new Composite(sc, SWT.NULL);
		{
			GridLayout layout = new GridLayout(1, false);
			layout.marginWidth = 10;
			layout.marginHeight = 10;
			container.setLayout(layout);
		}

		sc.setContent(container);

		// Create widgets for this page:
		createWidgets();

		// Compute height:
		sc.setMinSize(container.computeSize(SWT.DEFAULT, SWT.DEFAULT, true));
		Point containerSize = container.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
		sc_data.heightHint = containerSize.y;

		// Required to avoid an error in the system:
		setControl(wrapper);

		// Initial validation:
		validate();

		// Set dialog message:
		/* Note: Needed to force correct layout for scrollbar!? *
		 *       Set at least to setMessage(" ")!               */
		setMessage("Create a patch from the changes between the models: origin -> changed");
	}

	private void createWidgets() {

		// Models:
		sourceWidget = new InputModelsWidget(inputModels, "Patch Direction");
		addWidget(container, sourceWidget);
		
		// Comparison mode:
		comparisonWidget = new ComparisonModeWidget();
		addWidget(container, comparisonWidget);

		// Algorithms:
		Group algorithmsGroup = new Group(container, SWT.NONE);
		{
			GridLayout grid = new GridLayout(1, false);
			grid.marginWidth = 10;
			grid.marginHeight = 10;
			algorithmsGroup.setLayout(grid);

			GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
			algorithmsGroup.setLayoutData(data);

			algorithmsGroup.setText("Algorithms:");
		}

		// Rulebases:
		rulebaseWidget = new RulebaseWidget(inputModels);
		addWidget(container, rulebaseWidget);
	}

	private void addWidget(Composite parent, IWidget widget) {
		// Create controls:
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		widget.createControl(parent);
		widget.setLayoutData(data);

		// Add validation:
		if ((widget instanceof IWidgetSelection) && (widget instanceof IWidgetValidation)) {
			((IWidgetSelection) widget).addSelectionListener(validationListener);
		}
	}

	private void validate() {
		setErrorMessage(null);
		setPageComplete(true);

		validateWidget(sourceWidget);
		validateWidget(comparisonWidget);
		validateWidget(rulebaseWidget);
	}

	private void validateWidget(IWidgetValidation widget) {
		if (!widget.validate()) {
			setErrorMessage(widget.getValidationMessage());
			setPageComplete(false);
		}
	}

	public boolean isValidateModels() {
		return sourceWidget.isValidateModels();
	}

	public boolean isInverseDirection() {
		return sourceWidget.isInverseDirection();
	}
	
	public int getComparisonMode() {
		return comparisonWidget.getSelection();
	}
	
	public ComparisonModeWidget getComparisonWidget() {
		return comparisonWidget;
	}

	public Set<IRuleBase> getSelectedRulebases() {
		return rulebaseWidget.getSelection();
	}
}
