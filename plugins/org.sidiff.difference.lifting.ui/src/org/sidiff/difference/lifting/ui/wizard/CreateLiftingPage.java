package org.sidiff.difference.lifting.ui.wizard;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.menus.IWidget;
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
import org.eclipse.swt.widgets.Label;
import org.sidiff.common.ui.widgets.IWidgetSelection;
import org.sidiff.common.ui.widgets.IWidgetValidation;
import org.sidiff.difference.lifting.settings.LiftingSettings;
import org.sidiff.difference.lifting.ui.Activator;
import org.sidiff.difference.lifting.ui.util.InputModels;
import org.sidiff.difference.lifting.ui.widgets.RecognitionEngineWidget;
import org.sidiff.difference.lifting.ui.widgets.RulebaseWidget;

public class CreateLiftingPage extends WizardPage {

	private String DEFAULT_MESSAGE = "Edit operation detection on a technical difference.";
	
	private Composite container;

	private RecognitionEngineWidget recognitionWidget;
	private RulebaseWidget rulebaseWidget;

	private SelectionAdapter validationListener;

	private IFile differenceFile;
	private InputModels inputModels;
	private LiftingSettings settings;

	public CreateLiftingPage(IFile differenceFile, InputModels inputModels, LiftingSettings settings) {
		super("CreateLiftingPage");

		this.setTitle("Lift up the difference");
		this.setImageDescriptor(Activator.getImageDescriptor("icon.png"));

		this.differenceFile = differenceFile;
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

		// Show technical file:
		Group technicalDifferenceGroup = new Group(container, SWT.NONE);
		{
			GridLayout grid = new GridLayout(1, false);
			grid.marginWidth = 10;
			grid.marginHeight = 10;
			technicalDifferenceGroup.setLayout(grid);

			GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
			technicalDifferenceGroup.setLayoutData(data);

			technicalDifferenceGroup.setText("Selected technical difference model");
		}

		Label model = new Label(technicalDifferenceGroup, SWT.NONE);
		model.setText(differenceFile.getName());

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

		// Recognition engine:
		recognitionWidget = new RecognitionEngineWidget();
		recognitionWidget.setSettings(this.settings);
		recognitionWidget.showNoSemanticLifting = false;
		addWidget(algorithmsGroup, recognitionWidget);

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

		validateWidget(recognitionWidget);
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
	
	public void updateSettings(){
		this.settings.setRecognitionEngineMode(recognitionWidget.getSelection());
		this.settings.setRuleBases(this.rulebaseWidget.getSelection());
	}
}
