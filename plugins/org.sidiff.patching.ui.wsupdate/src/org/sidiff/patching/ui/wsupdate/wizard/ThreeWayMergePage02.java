package org.sidiff.patching.ui.wsupdate.wizard;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.menus.IWidget;
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
import org.sidiff.patching.ui.wsupdate.util.MergeModels;

public class ThreeWayMergePage02 extends WizardPage implements IPageChangedListener {

	private String DEFAULT_MESSAGE = "Workspace update";

	private Composite container;

	private MatchingEngineWidget matcherWidget;
	private ReliabilityWidget reliabilityWidget;
	private DifferenceBuilderWidget builderWidget;

	private SelectionAdapter validationListener;
	private SelectionAdapter informationListener;

	private MergeModels mergeModels;

	private PatchingSettings patchingSettings;
	private LiftingSettings liftingSettings;

	public ThreeWayMergePage02(MergeModels mergeModels, String pageName, String title, ImageDescriptor titleImage,
			LiftingSettings liftingSettings, PatchingSettings settings) {
		super(pageName, title, titleImage);

		this.mergeModels = mergeModels;

		this.patchingSettings = settings;
		this.liftingSettings = liftingSettings;
		
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

		// Initial validation:
		validate();

		// Set dialog message:
		/*
		 * Note: Needed to force correct layout for scrollbar!? * Set at least
		 * to setMessage(" ")!
		 */
		setMessage(DEFAULT_MESSAGE);

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
		matcherWidget = new MatchingEngineWidget(
				new InputModels(mergeModels.getFileBase(), mergeModels.getFileTheirs()));
		matcherWidget.setSettings(this.patchingSettings);
		matcherWidget.setPageChangedListener(this);
		addWidget(algorithmsGroup, matcherWidget);

		// Reliability
		reliabilityWidget = new ReliabilityWidget(50);
		reliabilityWidget.setSettings(this.patchingSettings);
		addWidget(algorithmsGroup, reliabilityWidget);

		// Technical Difference Builder:
		builderWidget = new DifferenceBuilderWidget(new InputModels(mergeModels.getFileBase(),
				mergeModels.getFileTheirs()));
		builderWidget.setSettings(this.liftingSettings);
		// FIXME
		// if (builderWidget.getDifferenceBuilders().size() > 1) {
		// addWidget(algorithmsGroup, builderWidget);
		// }
		addWidget(algorithmsGroup, builderWidget);
	}

	private void addWidget(Composite parent, IWidget widget) {
		// Create controls:
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		widget.createControl(parent);
		widget.setLayoutData(data);

		// Add validation:
		if ((widget instanceof IWidgetSelection) && (widget instanceof IWidgetValidation)) {
			((IWidgetSelection) widget).addSelectionListener(validationListener);
			((IWidgetSelection) widget).addSelectionListener(informationListener);

		}
	}

	private void validate() {
		setErrorMessage(null);
		setPageComplete(true);
		validateWidget(matcherWidget);
		validateWidget(builderWidget);
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
			return builderWidget.getDifferenceBuilders().values().toArray(new ITechnicalDifferenceBuilder[0])[0];
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

	public MatchingEngineWidget getMatcherWidget() {
		return matcherWidget;
	}

	public int getReliability() {
		return reliabilityWidget.getReliability();
	}

	public ReliabilityWidget getReliabilityWidget() {
		return reliabilityWidget;
	}

	@Override
	public void pageChanged(PageChangedEvent event) {
		validate();
	}
}
