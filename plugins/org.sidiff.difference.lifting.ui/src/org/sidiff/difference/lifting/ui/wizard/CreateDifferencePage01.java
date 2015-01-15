package org.sidiff.difference.lifting.ui.wizard;

import java.util.Set;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.sidiff.difference.lifting.settings.LiftingSettings;
import org.sidiff.difference.lifting.ui.Activator;
import org.sidiff.difference.lifting.ui.util.InputModels;
import org.sidiff.difference.lifting.ui.widgets.ScopeWidget;
import org.sidiff.difference.lifting.ui.widgets.InputModelsWidget;
import org.sidiff.difference.lifting.ui.widgets.RulebaseWidget;
import org.sidiff.difference.rulebase.extension.IRuleBase;
import org.silift.common.util.ui.widgets.IWidget;
import org.silift.common.util.ui.widgets.IWidgetSelection;
import org.silift.common.util.ui.widgets.IWidgetValidation;
import org.silift.common.util.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;

public class CreateDifferencePage01 extends WizardPage implements IPageChangedListener{

	private String DEFAULT_MESSAGE = "Compare two versions of a model: origin -> changed";
	
	private Composite container;

	private InputModelsWidget sourceWidget;
	private ScopeWidget scopeWidget;
	private RulebaseWidget rulebaseWidget;

	private SelectionAdapter validationListener;

	private InputModels inputModels;
	private LiftingSettings settings;
	
	public CreateDifferencePage01(InputModels inputModels, LiftingSettings settings) {
		super("CreateDifferencePage01");

		this.setTitle("Compare models with each other");
		this.setImageDescriptor(Activator.getImageDescriptor("icon.png"));

		this.inputModels = inputModels;
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
	public void createControl(final Composite parent) {

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
		// Models:
		sourceWidget = new InputModelsWidget(inputModels, "Comparison Direction");
		sourceWidget.setSettings(this.settings);
		addWidget(container, sourceWidget);

		// Comparison mode:
		scopeWidget = new ScopeWidget();
		scopeWidget.setSettings(this.settings);
		scopeWidget.setPageChangedListener(this);
		addWidget(container, scopeWidget);

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
		validateWidget(rulebaseWidget);
		validateWidget(scopeWidget);
		validateWidget(sourceWidget);
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


	public Set<IRuleBase> getSelectedRulebases() {
		return rulebaseWidget.getSelection();
	}

	public InputModelsWidget getSourceWidget() {
		return sourceWidget;
	}

	public ScopeWidget getScopeWidget() {
		return scopeWidget;
	}

	public RulebaseWidget getRulebaseWidget() {
		return rulebaseWidget;
	}

	@Override
	public void pageChanged(PageChangedEvent event) {
		validate();
	}
}
