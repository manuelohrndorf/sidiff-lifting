package org.sidiff.patching.ui.wsupdate.wizard;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.sidiff.common.ui.pages.AbstractWizardPage;
import org.sidiff.difference.technical.ITechnicalDifferenceBuilder;
import org.sidiff.difference.technical.ui.widgets.DifferenceBuilderWidget;
import org.sidiff.difference.technical.ui.widgets.MatchingEngineWidget;
import org.sidiff.matcher.IMatcher;
import org.sidiff.matching.input.InputModels;
import org.sidiff.patching.api.settings.PatchingSettings;
import org.sidiff.patching.ui.widgets.ReliabilityWidget;
import org.sidiff.patching.ui.wsupdate.util.WSUModels;

public class WorkspaceUpdatePage02 extends AbstractWizardPage {

	private String DEFAULT_MESSAGE = "Workspace update";

	private MatchingEngineWidget matcherWidget;
	private ReliabilityWidget reliabilityWidget;
	private DifferenceBuilderWidget builderWidget;

	private PatchingSettings settings;
	private InputModels inputModels;
	private WorkspaceUpdatePage01 workbenchUpdatePage01;

	public WorkspaceUpdatePage02(WSUModels mergeModels, String pageName, String title, ImageDescriptor titleImage,
			PatchingSettings settings, WorkspaceUpdatePage01 workbenchUpdatePage01) {
		super(pageName, title, titleImage);

		this.settings = settings;
		this.inputModels = new InputModels(mergeModels.getFileBase(), mergeModels.getFileTheirs());
		this.workbenchUpdatePage01 = workbenchUpdatePage01;
	}

	@Override
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
		matcherWidget.setDependency(workbenchUpdatePage01.getSettingsSourceWidget());
		addWidget(algorithmsGroup, matcherWidget);

		// Reliability
		reliabilityWidget = new ReliabilityWidget();
		reliabilityWidget.setSettings(this.settings);
		reliabilityWidget.setDependency(workbenchUpdatePage01.getSettingsSourceWidget());
		addWidget(algorithmsGroup, reliabilityWidget);

		// Technical Difference Builder:
		builderWidget = new DifferenceBuilderWidget(inputModels);
		builderWidget.setSettings(this.settings);
		builderWidget.setDependency(workbenchUpdatePage01.getSettingsSourceWidget());
		// FIXME
		// if (builderWidget.getDifferenceBuilders().size() > 1) {
		// addWidget(algorithmsGroup, builderWidget);
		// }
		addWidget(algorithmsGroup, builderWidget);
	}

	public ITechnicalDifferenceBuilder getSelectedTechnicalDifferenceBuilder() {
		return builderWidget.getSelection();
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
	protected String getDefaultMessage() {
		return DEFAULT_MESSAGE;
	}
}
