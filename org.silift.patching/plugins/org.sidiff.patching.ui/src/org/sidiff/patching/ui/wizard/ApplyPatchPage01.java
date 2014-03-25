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
import org.sidiff.difference.lifting.ui.widgets.ScopeWidget;
import org.sidiff.patching.ui.widgets.TargetModelWidget;
import org.sidiff.patching.ui.widgets.ValidationModeWidget;
import org.silift.common.util.emf.Scope;
import org.silift.common.util.ui.widgets.IWidget;
import org.silift.common.util.ui.widgets.IWidgetSelection;
import org.silift.common.util.ui.widgets.IWidgetValidation;
import org.silift.settings.PatchingSettings;

public class ApplyPatchPage01 extends WizardPage {

	private String DEFAULT_MESSAGE = "Apply a patch to a model";
	
	private Composite container;

	private TargetModelWidget targetWidget;
	private ScopeWidget scopeWidget;
	private ValidationModeWidget validationWidget;

	private SelectionAdapter validationListener;
	private String filterPath;
	private PatchingSettings settings;

	public ApplyPatchPage01(String pageName, String title, ImageDescriptor titleImage, PatchingSettings settings) {
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
		
		// Set dialog message:
		/* Note: Needed to force correct layout for scrollbar!? *
		 *       Set at least to setMessage(" ")!               */
		setMessage(DEFAULT_MESSAGE);
		
		// Initial validation:
		validate();
	}

	private void createWidgets() {
		
		//Target model:
		targetWidget = new TargetModelWidget();
		addWidget(container, targetWidget);

		// Comparison mode:
		scopeWidget = new ScopeWidget();
		addWidget(container, scopeWidget);
		
		
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
		if (widget instanceof IWidgetSelection) {
			((IWidgetSelection) widget).addSelectionListener(validationListener);
		}
	}

	private void validate() {
		setErrorMessage(null);
		setPageComplete(true);

		validateWidget(targetWidget);
		validateWidget(scopeWidget);
		validateWidget(validationWidget);
	}

	private void validateWidget(IWidgetValidation widget) {
		if (!widget.validate()) {
			setErrorMessage(widget.getValidationMessage());
			setPageComplete(false);
		}
	}

	public Scope getScope() {
		return scopeWidget.getSelection();
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
	
	public void setFilterPath(String filterPath){
		this.filterPath = filterPath;
	}

	public void updateSettings(){
		settings.setScope(scopeWidget.getSelection());
		settings.setValidationMode(validationWidget.getSelection());
	}
	
}
