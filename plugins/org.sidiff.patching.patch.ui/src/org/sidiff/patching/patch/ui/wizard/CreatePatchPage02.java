package org.sidiff.patching.patch.ui.wizard;

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
import org.sidiff.difference.lifting.ui.widgets.DifferenceBuilderWidget;
import org.sidiff.difference.technical.ITechnicalDifferenceBuilder;
import org.sidiff.difference.technical.ui.widgets.MatchingEngineWidget;
import org.sidiff.matcher.IMatcher;
import org.sidiff.matching.input.InputModels;
import org.sidiff.patching.api.settings.PatchingSettings;
import org.sidiff.patching.patch.ui.widgets.SymbolicLinkHandlerWidget;

public class CreatePatchPage02 extends WizardPage implements IPageChangedListener{

	private String default_message;
	
	private Composite container;

	private MatchingEngineWidget matcherWidget;
	private SymbolicLinkHandlerWidget symbolicLinkHandlerWidget;
	private DifferenceBuilderWidget builderWidget;

	private SelectionAdapter validationListener;

	private InputModels inputModels;
	
	private PatchingSettings settings;
	
	private String mode;

	public CreatePatchPage02(InputModels inputModels,
			String pageName, String title, ImageDescriptor titleImage, PatchingSettings settings, Mode mode) {
		super(pageName, title, titleImage);

		this.inputModels = inputModels;
		
		this.settings = settings;

		if(mode.equals(Mode.PATCH)){
			this.mode = "Patch";
		}else{
			this.mode = "Asymmetric Difference";
		}
		
		default_message =  "Create a " + this.mode + " from the changes between the models: origin -> changed";
		
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
		matcherWidget = new MatchingEngineWidget(inputModels.getResources(), true);
		matcherWidget.setSettings(this.settings);
		matcherWidget.setPageChangedListener(this);
		addWidget(algorithmsGroup, matcherWidget);
		
		// Symbolic Link Resolver:
		symbolicLinkHandlerWidget = new SymbolicLinkHandlerWidget();
		symbolicLinkHandlerWidget.setSettings(this.settings);
		if(symbolicLinkHandlerWidget.isSymbolicLinkHandlerAvailable()){
			addWidget(algorithmsGroup, symbolicLinkHandlerWidget);
		}
		// Technical Difference Builder:
		builderWidget = new DifferenceBuilderWidget(inputModels);
		builderWidget.setSettings(this.settings);

//FIXME
//		if (builderWidget.getDifferenceBuilders().size() > 1) {
//			addWidget(algorithmsGroup, builderWidget);
//		}
		addWidget(algorithmsGroup, builderWidget);
		
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
			return builderWidget.getDifferenceBuilders().values()
					.toArray(new ITechnicalDifferenceBuilder[0])[0];
		}
	}

	public IMatcher getSelectedMatchingEngine() {
		return matcherWidget.getSelection();
	}

	public MatchingEngineWidget getMatcherWidget() {
		return matcherWidget;
	}

	@Override
	public void pageChanged(PageChangedEvent event) {
		validate();
	}
}
