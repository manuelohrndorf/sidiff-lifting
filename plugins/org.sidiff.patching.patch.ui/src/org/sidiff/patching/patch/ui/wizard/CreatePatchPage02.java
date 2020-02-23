package org.sidiff.patching.patch.ui.wizard;

import org.eclipse.jface.resource.ImageDescriptor;
import org.sidiff.common.emf.input.InputModels;
import org.sidiff.common.extension.ui.widgets.ConfigurableExtensionWidget;
import org.sidiff.common.ui.pages.AbstractWizardPage;
import org.sidiff.difference.technical.ui.widgets.DifferenceBuilderWidget;
import org.sidiff.difference.technical.ui.widgets.MatchingEngineWidget;
import org.sidiff.patching.api.settings.PatchingSettings;
import org.sidiff.patching.patch.ui.widgets.SymbolicLinkHandlerWidget;

public class CreatePatchPage02 extends AbstractWizardPage {

	private MatchingEngineWidget matcherWidget;
	private SymbolicLinkHandlerWidget symbolicLinkHandlerWidget;
	private DifferenceBuilderWidget builderWidget;

	private final InputModels inputModels;
	private final PatchingSettings settings;
	private final Mode mode;
	private final CreatePatchPage01 createPatchPage01;

	public CreatePatchPage02(InputModels inputModels, String pageName, String title, ImageDescriptor titleImage,
			PatchingSettings settings, Mode mode, CreatePatchPage01 createPatchPage01) {
		super(pageName, title, titleImage);

		this.inputModels = inputModels;
		this.settings = settings;
		this.mode = mode;
		this.createPatchPage01 = createPatchPage01;
	}

	@Override
	protected void createWidgets() {
		// Matcher:
		matcherWidget = new MatchingEngineWidget(inputModels, settings);
		if(mode == Mode.PATCH) {
			// There can only be one matcher for patch files because
			// serializing an incremental matcher does not work.
			matcherWidget.setLowerUpperBounds(1, 1);
		}
		matcherWidget.setDependency(createPatchPage01.getSettingsSourceWidget());
		addWidget(container, matcherWidget);
		ConfigurableExtensionWidget.addAllForWidget(container, matcherWidget, this::addWidget);

		// Symbolic Link Resolver:
		symbolicLinkHandlerWidget = new SymbolicLinkHandlerWidget(settings);
		symbolicLinkHandlerWidget.setDependency(createPatchPage01.getSettingsSourceWidget());
		addWidget(container, symbolicLinkHandlerWidget);

		// Technical Difference Builder:
		builderWidget = new DifferenceBuilderWidget(inputModels, settings);
		builderWidget.setDependency(createPatchPage01.getSettingsSourceWidget());
		addWidget(container, builderWidget);
	}

	@Override
	protected String getDefaultMessage() {
		return "Create a " + mode + " from the changes between the models: origin -> changed";
	}
}
