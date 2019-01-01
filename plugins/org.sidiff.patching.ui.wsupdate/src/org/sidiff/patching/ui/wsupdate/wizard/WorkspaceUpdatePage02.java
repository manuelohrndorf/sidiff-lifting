package org.sidiff.patching.ui.wsupdate.wizard;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.sidiff.common.extension.ui.widgets.ConfigurableExtensionWidget;
import org.sidiff.common.ui.pages.AbstractWizardPage;
import org.sidiff.difference.technical.ui.widgets.DifferenceBuilderWidget;
import org.sidiff.difference.technical.ui.widgets.MatchingEngineWidget;
import org.sidiff.matching.input.InputModels;
import org.sidiff.patching.api.settings.PatchingSettings;
import org.sidiff.patching.ui.widgets.ReliabilityWidget;
import org.sidiff.patching.ui.wsupdate.Activator;
import org.sidiff.patching.ui.wsupdate.util.WSUModels;

public class WorkspaceUpdatePage02 extends AbstractWizardPage {

	private MatchingEngineWidget matcherWidget;
	private ReliabilityWidget reliabilityWidget;
	private DifferenceBuilderWidget builderWidget;

	private PatchingSettings settings;
	private InputModels inputModels;
	private WorkspaceUpdatePage01 workbenchUpdatePage01;

	public WorkspaceUpdatePage02(WSUModels mergeModels, String pageName, String title,
			PatchingSettings settings, WorkspaceUpdatePage01 workbenchUpdatePage01) {
		super(pageName, title, Activator.getImageDescriptor("icon.png"));
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
		matcherWidget = new MatchingEngineWidget(inputModels, settings);
		matcherWidget.setDependency(workbenchUpdatePage01.getSettingsSourceWidget());
		addWidget(algorithmsGroup, matcherWidget);
		ConfigurableExtensionWidget.addAllForWidget(algorithmsGroup, matcherWidget, this::addWidget);

		// Reliability
		reliabilityWidget = new ReliabilityWidget();
		reliabilityWidget.setSettings(this.settings);
		reliabilityWidget.setDependency(workbenchUpdatePage01.getSettingsSourceWidget());
		addWidget(algorithmsGroup, reliabilityWidget);

		// Technical Difference Builder:
		builderWidget = new DifferenceBuilderWidget(inputModels);
		builderWidget.setSettings(this.settings);
		builderWidget.setDependency(workbenchUpdatePage01.getSettingsSourceWidget());
		addWidget(algorithmsGroup, builderWidget);
	}

	@Override
	protected String getDefaultMessage() {
		return "Propagates parallel changes by other developers, which were checked-in into a common repository, to the local workspace.";
	}
}
