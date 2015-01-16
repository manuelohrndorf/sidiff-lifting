package org.sidiff.difference.lifting.ui.wizard;

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
import org.eclipse.swt.widgets.Group;
import org.sidiff.difference.lifting.settings.LiftingSettings;
import org.sidiff.difference.lifting.settings.LiftingSettings.RecognitionEngineMode;
import org.sidiff.difference.lifting.ui.Activator;
import org.sidiff.difference.lifting.ui.util.InputModels;
import org.sidiff.difference.lifting.ui.widgets.DifferenceBuilderWidget;
import org.sidiff.difference.lifting.ui.widgets.MatchingEngineWidget;
import org.sidiff.difference.lifting.ui.widgets.RecognitionEngineWidget;
import org.sidiff.difference.lifting.ui.widgets.RecognitionRuleSorterWidget;
import org.sidiff.difference.matcher.IMatcher;
import org.sidiff.difference.technical.ITechnicalDifferenceBuilder;
import org.silift.common.util.ui.widgets.IWidget;
import org.silift.common.util.ui.widgets.IWidgetSelection;
import org.silift.common.util.ui.widgets.IWidgetValidation;
import org.silift.common.util.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;

public class CreateDifferencePage02 extends WizardPage implements IPageChangedListener {

	private String DEFAULT_MESSAGE = "Compare two versions of a model: origin -> changed";
	
	private Composite container;

	private RecognitionEngineWidget recognitionWidget;
	private MatchingEngineWidget matcherWidget;
	private DifferenceBuilderWidget builderWidget;
	private RecognitionRuleSorterWidget rrSorterWidget;

	private SelectionAdapter validationListener;

	private InputModels inputModels;
	private LiftingSettings settings;
	public CreateDifferencePage02(InputModels inputModels, LiftingSettings settings) {
		super("CreateDifferencePage02");

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

		// Matcher:
		matcherWidget = new MatchingEngineWidget(inputModels);
		matcherWidget.setSettings(this.settings);
		matcherWidget.setPageChangedListener(this);
		addWidget(algorithmsGroup, matcherWidget);
		
		// Technical Difference Builder:
		builderWidget = new DifferenceBuilderWidget(inputModels);
		builderWidget.setSettings(this.settings);
// FIXME
//		if (builderWidget.getDifferenceBuilders().size() > 1) {
//			addWidget(algorithmsGroup, builderWidget);
//		}
		addWidget(algorithmsGroup, builderWidget);
		
		// Recognition Rule Sorter:
		rrSorterWidget = new RecognitionRuleSorterWidget(inputModels);
		rrSorterWidget.setSettings(this.settings);
		addWidget(algorithmsGroup, rrSorterWidget);
		
		// Recognition engine:
		recognitionWidget = new RecognitionEngineWidget();
		recognitionWidget.setSettings(this.settings);
		addWidget(algorithmsGroup, recognitionWidget);
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
		setMessage(null);
		setPageComplete(true);
		validateWidget(recognitionWidget);
		validateWidget(builderWidget);
		validateWidget(matcherWidget);
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

	public ITechnicalDifferenceBuilder getSelectedTechnicalDifferenceBuilder() {
		if (builderWidget.getDifferenceBuilders().size() > 1) {
			return builderWidget.getSelection();
		} else {
			return builderWidget.getDifferenceBuilders().values()
					.toArray(new ITechnicalDifferenceBuilder[0])[0];
		}
	}

	public IMatcher getSelectedMatchingEngine() {
		return matcherWidget.getSelection();
	}

	public RecognitionEngineMode getSelectedRecognitionEngine() {
		return recognitionWidget.getSelection();
	}

	public RecognitionEngineWidget getRecognitionWidget() {
		return recognitionWidget;
	}

	public MatchingEngineWidget getMatcherWidget() {
		return matcherWidget;
	}

	@Override
	public void pageChanged(PageChangedEvent event) {
		validate();
	}
	
}
