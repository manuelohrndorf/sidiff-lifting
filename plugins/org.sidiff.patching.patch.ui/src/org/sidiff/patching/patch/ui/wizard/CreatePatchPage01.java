package org.sidiff.patching.patch.ui.wizard;

import java.util.Set;

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
import org.eclipse.swt.widgets.Group;
import org.sidiff.common.ui.widgets.IWidget;
import org.sidiff.common.ui.widgets.IWidgetSelection;
import org.sidiff.common.ui.widgets.IWidgetValidation;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;
import org.sidiff.difference.lifting.settings.LiftingSettings;
import org.sidiff.difference.lifting.ui.util.InputModels;
import org.sidiff.difference.lifting.ui.widgets.InputModelsWidget;
import org.sidiff.difference.lifting.ui.widgets.RulebaseWidget;
import org.sidiff.difference.lifting.ui.widgets.ScopeWidget;
import org.sidiff.difference.rulebase.extension.IRuleBase;
import org.silift.patching.patch.ui.widgets.EditRuleMatchWidget;

public class CreatePatchPage01 extends WizardPage implements IPageChangedListener {

	private String default_message;
	
	private Composite container;

	private InputModelsWidget sourceWidget;
	private ScopeWidget scopeWidget;
	private EditRuleMatchWidget erMatchWidget;
	private RulebaseWidget rulebaseWidget;

	private SelectionAdapter validationListener;

	private InputModels inputModels;
	private LiftingSettings settings;
	
	private String mode;

	public CreatePatchPage01(InputModels inputModels,
			String pageName, String title, ImageDescriptor titleImage, LiftingSettings settings, Mode mode) {
		super(pageName, title, titleImage);

		this.inputModels = inputModels;
		this.settings = settings;
		
		if(mode.equals(Mode.PATCH)){
			this.mode = "Patch";
		}else{
			this.mode = "Asymmetric Difference";
		}
		
		default_message = "Create a " + this.mode + " from the changes between the models: origin -> changed";
		
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
		setMessage(default_message);
		
		// Initial validation:
		validate();
	}

	private void createWidgets() {

		// Models:
		sourceWidget = new InputModelsWidget(inputModels, mode +" Direction");
		sourceWidget.setSettings(this.settings);
		addWidget(container, sourceWidget);
		
		// Comparison mode:
		scopeWidget = new ScopeWidget();
		scopeWidget.setSettings(this.settings);
		scopeWidget.setPageChangedListener(this);
		addWidget(container, scopeWidget);

		// Edit-Rule Matches:
		Group erMatchesGroup = new Group(container, SWT.NONE);
		{
			GridLayout grid = new GridLayout(1, false);
			grid.marginWidth = 10;
			grid.marginHeight = 10;
			erMatchesGroup.setLayout(grid);

			GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
			erMatchesGroup.setLayoutData(data);

			erMatchesGroup.setText("Edit Rule Matches:");
		}
		
		erMatchWidget = new EditRuleMatchWidget();
		erMatchWidget.setSettings(settings);
		addWidget(erMatchesGroup, erMatchWidget);
		
		// Rulebases:
		rulebaseWidget = new RulebaseWidget(inputModels);
		rulebaseWidget.setSettings(this.settings);
		addWidget(container, rulebaseWidget);
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
		validateWidget(sourceWidget);
		validateWidget(scopeWidget);
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

	public boolean isValidateModels() {
		return sourceWidget.isValidateModels();
	}

	public boolean isInverseDirection() {
		return sourceWidget.isInverseDirection();
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
