package org.sidiff.patching.ui.wizard;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.sidiff.common.ui.pages.AbstractWizardPage;
import org.sidiff.common.ui.widgets.IWidget;
import org.sidiff.common.ui.widgets.IWidgetSelection;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.technical.ui.widgets.MatchingEngineWidget;
import org.sidiff.matcher.IMatcher;
import org.sidiff.matching.input.InputModels;
import org.sidiff.patching.settings.PatchingSettings;
import org.sidiff.patching.ui.widgets.ReliabilityWidget;

public class ApplyAsymmetricDifferencePage02 extends AbstractWizardPage {

	private String DEFAULT_MESSAGE = "Apply a patch to a model";
	

	private MatchingEngineWidget matcherWidget;
	private ReliabilityWidget reliabilityWidget;
	
	private SelectionAdapter informationListener;

	private AsymmetricDifference difference;
	private InputModels inputModels;
	
	private PatchingSettings settings;

	public ApplyAsymmetricDifferencePage02(	String pageName, String title, ImageDescriptor titleImage, PatchingSettings settings, AsymmetricDifference difference) {
		super(pageName, title, titleImage);
		this.difference = difference;
		this.inputModels = new InputModels(new Resource[]{this.difference.getOriginModel(), this.difference.getChangedModel()});
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
		super.createControl(parent);
		
		// Initialize information message:
		readInformationMessages();
		
	}

	protected void createWidgets() {

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
		
		//Reliability
		reliabilityWidget = new ReliabilityWidget(50);
		reliabilityWidget.setSettings(this.settings);
		addWidget(container, reliabilityWidget);

	}

	protected void addWidget(Composite parent, IWidget widget) {
		// Create controls:
		super.addWidget(parent, widget);

		// Add selection listener:
		if (widget instanceof IWidgetSelection) {
			((IWidgetSelection) widget).addSelectionListener(informationListener);
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


	@Override
	public void pageChanged(PageChangedEvent event) {
		validate();
	}


	@Override
	protected String getDefaultMessage() {
		return DEFAULT_MESSAGE;
	}

}
