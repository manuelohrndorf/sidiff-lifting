package org.sidiff.patching.ui.wsupdate.wizard;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
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
import org.sidiff.common.ui.widgets.IWidget;
import org.sidiff.common.ui.widgets.IWidgetSelection;
import org.sidiff.common.ui.widgets.IWidgetValidation;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.lifting.ui.widgets.RulebaseWidget;
import org.sidiff.difference.rulebase.extension.IRuleBase;
import org.sidiff.difference.ui.widgets.ScopeWidget;
import org.sidiff.matching.input.InputModels;
import org.sidiff.patching.settings.PatchingSettings;
import org.sidiff.patching.settings.PatchingSettings.ValidationMode;
import org.sidiff.patching.ui.widgets.ValidationModeWidget;
import org.sidiff.patching.ui.wsupdate.util.WSUModels;
import org.sidiff.patching.ui.wsupdate.widgets.WSUModelsWidget;

public class WorkspaceUpdatePage01 extends WizardPage implements IPageChangedListener {

	private Composite container;

	private WSUModelsWidget mergeModelsWidget;
	private ValidationModeWidget validationWidget;
	private ScopeWidget scopeWidget;
	private RulebaseWidget rulebaseWidget;

	private SelectionAdapter validationListener;
	private WSUModels mergeModels;
	private LiftingSettings liftingSettings;
	private PatchingSettings patchingSettings;


	public WorkspaceUpdatePage01(
			WSUModels mergeModels, String pageName, String title, ImageDescriptor titleImage, LiftingSettings liftingSettings, PatchingSettings patchingSettings) {
		super(pageName, title, titleImage);

		this.mergeModels = mergeModels;
		this.liftingSettings = liftingSettings;
		this.patchingSettings = patchingSettings;
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
		setMessage("Propagates parallel changes by other developers, which were checked-in into a common repository, to the local workspace.");
		
	}

	private void createWidgets() {

		// Models:
		mergeModelsWidget = new WSUModelsWidget(mergeModels);
		mergeModelsWidget.setSettings(this.liftingSettings);
		addWidget(container, mergeModelsWidget);
		
		// Comparison mode:
		scopeWidget = new ScopeWidget();
		scopeWidget.setSettings(this.liftingSettings);
		scopeWidget.setPageChangedListener(this);
		addWidget(container, scopeWidget);
		
		// Validation mode:
		validationWidget = new ValidationModeWidget();
		validationWidget.setSettings(this.patchingSettings);
		addWidget(container, validationWidget);
		

		// Rulebases:
		rulebaseWidget = new RulebaseWidget(new InputModels(new IFile[]{mergeModels.getFileBase(), mergeModels.getFileTheirs()}));
		rulebaseWidget.setSettings(this.liftingSettings);
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
			if(widget.getValidationMessage().getType().equals(ValidationType.ERROR)){
				setErrorMessage(widget.getValidationMessage().getMessage());
				setPageComplete(false);
			}else{
				setMessage(widget.getValidationMessage().getMessage(), IMessageProvider.WARNING);
			}
		}
	}
	
	public WSUModelsWidget getMergeModelsWidget(){
		return mergeModelsWidget;
	}
	
	public WSUModels getMergeModels(){
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
	
	public ScopeWidget getScopeWidget() {
		return scopeWidget;
	}

	public Set<IRuleBase> getSelectedRulebases() {
		return rulebaseWidget.getSelection();
	}

	@Override
	public void pageChanged(PageChangedEvent event) {
		validate();
	}
}
