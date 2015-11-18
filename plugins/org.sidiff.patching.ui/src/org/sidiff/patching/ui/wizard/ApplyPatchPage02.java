package org.sidiff.patching.ui.wizard;

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
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.lifting.ui.util.InputModels;
import org.sidiff.matcher.IMatcher;
import org.sidiff.patching.patch.patch.Patch;
import org.sidiff.patching.ui.widgets.ApplyPatchMatchingEngineWidget;
import org.sidiff.patching.ui.widgets.ApplyPatchSymbolicLinkHandlerWidget;
import org.sidiff.patching.ui.widgets.ReliabilityWidget;
import org.silift.patching.settings.PatchingSettings;

public class ApplyPatchPage02 extends WizardPage implements IPageChangedListener {

	private String DEFAULT_MESSAGE = "Apply a patch to a model";
	
	private Composite container;

	private ApplyPatchMatchingEngineWidget matcherWidget;
	private ApplyPatchSymbolicLinkHandlerWidget symbolicLinkHandlerWidget;
	private ReliabilityWidget reliabilityWidget;
	
	private SelectionAdapter validationListener;
	private SelectionAdapter informationListener;

	private Patch patch;
	private AsymmetricDifference difference;
	private InputModels inputModels;
	
	private PatchingSettings settings;
	boolean use_SymbolicLinks=false;

	public ApplyPatchPage02(Patch patch,
			String pageName, String title, ImageDescriptor titleImage, PatchingSettings settings) {
		super(pageName, title, titleImage);
		this.patch = patch;
		this.difference = patch.getAsymmetricDifference();
		this.inputModels = new InputModels(this.difference.getOriginModel(), this.difference.getChangedModel());
		this.settings = settings;
		
		if(patch.getSettings().get("symbolicLinkHandler")!=null){
			use_SymbolicLinks=true;
		}
		
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

		if(use_SymbolicLinks){
			// Symbolic Link Resolver:
			symbolicLinkHandlerWidget = new ApplyPatchSymbolicLinkHandlerWidget(settings, patch);
			addWidget(algorithmsGroup, symbolicLinkHandlerWidget);
		}else{
			// Matcher:
			matcherWidget = new ApplyPatchMatchingEngineWidget(inputModels, patch);
			matcherWidget.setSettings(this.settings);
			matcherWidget.setPageChangedListener(this);
			addWidget(algorithmsGroup, matcherWidget);
		}
		//Reliability
		reliabilityWidget = new ReliabilityWidget(50);
		reliabilityWidget.setSettings(this.settings);
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
		if(use_SymbolicLinks){
			validateWidget(symbolicLinkHandlerWidget);
		}else{
			validateWidget(matcherWidget);
		}
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

	public ApplyPatchMatchingEngineWidget getMatcherWidget() {
		return matcherWidget;
	}

	@Override
	public void pageChanged(PageChangedEvent event) {
		validate();
	}

}
