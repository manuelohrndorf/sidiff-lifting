package org.sidiff.patching.ui.wsupdate.wizard;

import org.sidiff.common.extension.ui.widgets.ConfigurableExtensionWidget;
import org.sidiff.common.ui.pages.AbstractWizardPage;
import org.sidiff.difference.technical.ui.widgets.TechnicalDifferenceBuilderWidget;
import org.sidiff.difference.technical.ui.widgets.MatchingEngineWidget;
import org.sidiff.patching.api.settings.PatchingSettings;
import org.sidiff.patching.ui.widgets.ReliabilityWidget;
import org.sidiff.patching.ui.wsupdate.internal.PatchingWsUpdateUiPlugin;
import org.sidiff.patching.ui.wsupdate.util.WSUModels;

public class WorkspaceUpdatePage02 extends AbstractWizardPage {

	private MatchingEngineWidget matcherWidget;
	private ReliabilityWidget reliabilityWidget;
	private TechnicalDifferenceBuilderWidget builderWidget;

	private PatchingSettings settings;
	private WSUModels mergeModels;
	private WorkspaceUpdatePage01 workbenchUpdatePage01;

	public WorkspaceUpdatePage02(WSUModels mergeModels,
			PatchingSettings settings, WorkspaceUpdatePage01 workbenchUpdatePage01) {
		super("WorkspaceUpdatePage02", "Workspace update", PatchingWsUpdateUiPlugin.getImageDescriptor("icon.png"));
		this.settings = settings;
		this.mergeModels = mergeModels;
		this.workbenchUpdatePage01 = workbenchUpdatePage01;
	}

	@Override
	protected void createWidgets() {

		// Matcher:
		matcherWidget = new MatchingEngineWidget(mergeModels.getBaseTheirsModels(), settings);
		matcherWidget.setDependency(workbenchUpdatePage01.getSettingsSourceWidget());
		addWidget(container, matcherWidget);
		ConfigurableExtensionWidget.addAllForWidget(container, matcherWidget, this::addWidget);

		// Reliability
		reliabilityWidget = new ReliabilityWidget(settings);
		reliabilityWidget.setDependency(workbenchUpdatePage01.getSettingsSourceWidget());
		addWidget(container, reliabilityWidget);

		// Technical Difference Builder:
		builderWidget = new TechnicalDifferenceBuilderWidget(mergeModels.getBaseTheirsModels(), settings);
		builderWidget.setDependency(workbenchUpdatePage01.getSettingsSourceWidget());
		addWidget(container, builderWidget);
	}

	@Override
	protected String getDefaultMessage() {
		return "Propagates parallel changes by other developers, which were checked-in into a common repository, to the local workspace.";
	}
}
