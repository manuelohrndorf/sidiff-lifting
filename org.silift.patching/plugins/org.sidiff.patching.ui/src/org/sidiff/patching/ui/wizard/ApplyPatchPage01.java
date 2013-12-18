package org.sidiff.patching.ui.wizard;

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
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.lifting.ui.util.InputModels;
import org.sidiff.difference.lifting.ui.widgets.ComparisonModeWidget;
import org.sidiff.patching.ui.widgets.ReliabilityWidget;
import org.sidiff.patching.ui.widgets.TargetModelWidget;
import org.sidiff.patching.ui.widgets.ValidationModeWidget;
import org.silift.common.util.ui.widgets.IWidget;
import org.silift.common.util.ui.widgets.IWidgetSelection;
import org.silift.common.util.ui.widgets.IWidgetValidation;
import org.silift.patching.patch.Patch;

public class ApplyPatchPage01 extends WizardPage {

	private Composite container;

	private TargetModelWidget targetWidget;
	private ComparisonModeWidget comparisonWidget;
	private ReliabilityWidget reliabilityWidget;
	private ValidationModeWidget validationWidget;

	private SelectionAdapter validationListener;

	private Patch patch;
	private String filterPath;
	
	//TODO 
	private AsymmetricDifference difference;
	private InputModels inputModels;

	public ApplyPatchPage01(Patch patch,
			String pageName, String title, ImageDescriptor titleImage) {
		super(pageName, title, titleImage);

		this.patch = patch;
		this.difference = this.patch.getDifference();
		this.inputModels = new InputModels(this.difference.getChangedModel(), this.difference.getOriginModel());
		
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
		setMessage("Apply a patch to a model");
	}

	private void createWidgets() {
		
		//Target model:
		targetWidget = new TargetModelWidget(filterPath);
		addWidget(container, targetWidget);

		// Comparison mode:
		comparisonWidget = new ComparisonModeWidget();
		addWidget(container, comparisonWidget);
		
		//Reliability
		reliabilityWidget = new ReliabilityWidget(80);
		addWidget(container, reliabilityWidget);
		
		//Validation
		validationWidget = new ValidationModeWidget();
		addWidget(container, validationWidget);

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

		validateWidget(targetWidget);
		validateWidget(comparisonWidget);
		validateWidget(reliabilityWidget);
		validateWidget(validationWidget);
	}

	private void validateWidget(IWidgetValidation widget) {
		if (!widget.validate()) {
			setErrorMessage(widget.getValidationMessage());
			setPageComplete(false);
		}
	}

	
	public int getComparisonMode() {
		return comparisonWidget.getSelection();
	}
	
	public TargetModelWidget getTargetWidget(){
		return targetWidget;
	}
	
	public ComparisonModeWidget getComparisonWidget() {
		return comparisonWidget;
	}
	
	public ReliabilityWidget getReliabilityWidget(){
		return reliabilityWidget;
	}
	
	public ValidationModeWidget getValidationWidget(){
		return validationWidget;
	}

}
