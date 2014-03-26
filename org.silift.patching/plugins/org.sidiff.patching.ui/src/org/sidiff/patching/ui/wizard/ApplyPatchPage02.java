package org.sidiff.patching.ui.wizard;

import org.eclipse.jface.dialogs.IMessageProvider;
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
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.lifting.ui.util.InputModels;
import org.sidiff.difference.lifting.ui.widgets.MatchingEngineWidget;
import org.sidiff.difference.matcher.IMatcher;
import org.sidiff.patching.ui.widgets.ReliabilityWidget;
import org.silift.common.util.ui.widgets.IWidget;
import org.silift.common.util.ui.widgets.IWidgetSelection;
import org.silift.common.util.ui.widgets.IWidgetValidation;
import org.silift.patching.patch.Patch;
import org.silift.settings.patching.PatchingSettings;

public class ApplyPatchPage02 extends WizardPage {

	private String DEFAULT_MESSAGE = "Apply a patch to a model";
	
	private Composite container;

	private MatchingEngineWidget matcherWidget;
	private ReliabilityWidget reliabilityWidget;
	
	private SelectionAdapter validationListener;
	private SelectionAdapter informationListener;

	private AsymmetricDifference difference;
	private InputModels inputModels;
	
	private PatchingSettings settings;

	public ApplyPatchPage02(Patch patch,
			String pageName, String title, ImageDescriptor titleImage, PatchingSettings settings) {
		super(pageName, title, titleImage);

		this.difference = patch.getDifference();
		this.inputModels = new InputModels(this.difference.getOriginModel(), this.difference.getChangedModel());

		this.settings = settings;
		// Listen for validation failures:
		validationListener = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				validate();
			}
		};

		// Listen for widget information messages:
		informationListener = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				readInformationMessages();
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
		
		// Initialize information message:
		readInformationMessages();
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
		matcherWidget = new MatchingEngineWidget(inputModels, settings.getScope());
		addWidget(algorithmsGroup, matcherWidget);
		
		//Reliability
		reliabilityWidget = new ReliabilityWidget(50, matcherWidget);
		addWidget(container, reliabilityWidget);

	}

	private void addWidget(Composite parent, IWidget widget) {
		// Create controls:
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		widget.createControl(parent);
		widget.setLayoutData(data);

		// Add selection listener:
		if (widget instanceof IWidgetSelection) {
			((IWidgetSelection) widget).addSelectionListener(validationListener);
			((IWidgetSelection) widget).addSelectionListener(informationListener);
		}
	}

	private void validate() {
		setErrorMessage(null);
		setPageComplete(true);

		validateWidget(matcherWidget);
	}

	private void validateWidget(IWidgetValidation widget) {
		if (!widget.validate()) {
			setErrorMessage(widget.getValidationMessage());
			setPageComplete(false);
		}
	}

	private void readInformationMessages() {
		if ((getErrorMessage() == null) || getErrorMessage().equals("")) {
			setMessage(reliabilityWidget.getInformationMessage(), IMessageProvider.INFORMATION);
		}
		if ((getMessage() == null) || getMessage().equals("")) {
			setMessage(DEFAULT_MESSAGE);
		}
	}

	public IMatcher getSelectedMatchingEngine() {
		return matcherWidget.getSelection();
	}
	
	public ReliabilityWidget getReliabilityWidget(){
		return reliabilityWidget;
	}

	public MatchingEngineWidget getMatcherWidget() {
		return matcherWidget;
	}
	
	public void updateSettings(){
		settings.setMatcher(matcherWidget.getSelection());
		settings.setMinReliability(reliabilityWidget.getReliability());
	}
}
