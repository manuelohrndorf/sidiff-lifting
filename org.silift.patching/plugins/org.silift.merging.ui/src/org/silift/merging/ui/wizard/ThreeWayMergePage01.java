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
import org.sidiff.difference.lifting.ui.util.InputModels;
import org.sidiff.difference.lifting.ui.widgets.RulebaseWidget;
import org.sidiff.difference.lifting.ui.widgets.ScopeWidget;
import org.sidiff.difference.rulebase.extension.IRuleBase;
import org.sidiff.patching.ui.widgets.ValidationModeWidget;
import org.silift.common.util.emf.Scope;
import org.silift.common.util.ui.widgets.IWidget;
import org.silift.common.util.ui.widgets.IWidgetSelection;
import org.silift.common.util.ui.widgets.IWidgetValidation;
import org.silift.merging.ui.util.MergeModels;
import org.silift.merging.ui.widgets.MergeModelsWidget;
import org.silift.patching.settings.PatchingSettings;
import org.silift.patching.settings.PatchingSettings.ValidationMode;

public class ThreeWayMergePage01 extends WizardPage {

	private Composite container;

	private MergeModelsWidget mergeModelsWidget;
	private ValidationModeWidget validationWidget;
	private ScopeWidget scopeWidget;
	private RulebaseWidget rulebaseWidget;

	private SelectionAdapter validationListener;
	private MergeModels mergeModels;
	private PatchingSettings settings;


	public ThreeWayMergePage01(
			MergeModels mergeModels, String pageName, String title, ImageDescriptor titleImage, PatchingSettings settings) {
		super(pageName, title, titleImage);

		this.mergeModels = mergeModels;
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

		// Initial validation:
		validate();

		// Set dialog message:
		/* Note: Needed to force correct layout for scrollbar!? *
		 *       Set at least to setMessage(" ")!               */
		setMessage("Merge three models");
	}

	private void createWidgets() {

		// Models:
		mergeModelsWidget = new MergeModelsWidget(mergeModels);;
		addWidget(container, mergeModelsWidget);
		
		// Comparison mode:
		scopeWidget = new ScopeWidget();
		addWidget(container, scopeWidget);
		
		// Validation mode:
		validationWidget = new ValidationModeWidget();
		addWidget(container, validationWidget);
		

		// Rulebases:
		rulebaseWidget = new RulebaseWidget(new InputModels(mergeModels.getFileBase(), mergeModels.getFileTheirs()));
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

		validateWidget(mergeModelsWidget);
		validateWidget(scopeWidget);
		validateWidget(validationWidget);
		validateWidget(rulebaseWidget);
	}

	private void validateWidget(IWidgetValidation widget) {
		if (!widget.validate()) {
			setErrorMessage(widget.getValidationMessage());
			setPageComplete(false);
		}
	}
	
	public MergeModelsWidget getMergeModelsWidget(){
		return mergeModelsWidget;
	}
	
	public MergeModels getMergeModels(){
		return mergeModelsWidget.getMergeModels();
	}
	
	public ValidationModeWidget getValidationModeWidget(){
		return validationWidget;
	}
	
	public boolean isValidateModels(){
		if(validationWidget.getSelection() == ValidationMode.NO_VALIDATION){
			return false;
		}
		return true;
	}
	
	public Scope getScope() {
		return scopeWidget.getSelection();
	}
	
	public ScopeWidget getScopeWidget() {
		return scopeWidget;
	}

	public Set<IRuleBase> getSelectedRulebases() {
		return rulebaseWidget.getSelection();
	}
	
	public void updateSettings(){
		settings.setValidationMode(validationWidget.getSelection());
		settings.setScope(scopeWidget.getSelection());
		settings.setRuleBases(rulebaseWidget.getSelection());
	}
}
