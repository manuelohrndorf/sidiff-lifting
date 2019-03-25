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

	private CreatePatchPage01 createPatchPage01;

	private String defaultMessage;

	private MatchingEngineWidget matcherWidget;
	private SymbolicLinkHandlerWidget symbolicLinkHandlerWidget;
	private DifferenceBuilderWidget builderWidget;

	private InputModels inputModels;
	private PatchingSettings settings;

	private String mode;

	public CreatePatchPage02(InputModels inputModels, String pageName, String title, ImageDescriptor titleImage,
			PatchingSettings settings, Mode mode, CreatePatchPage01 createPatchPage01) {
		super(pageName, title, titleImage);

		this.inputModels = inputModels;
		this.settings = settings;
		this.createPatchPage01 = createPatchPage01;

		if(mode == Mode.PATCH) {
			this.mode = "Patch";
		} else {
			this.mode = "Asymmetric Difference";
		}

		this.defaultMessage =  "Create a " + this.mode + " from the changes between the models: origin -> changed";
	}

	@Override
	protected void createWidgets() {

		// Matcher:
		matcherWidget = new MatchingEngineWidget(inputModels, settings);
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
		return defaultMessage;
	}
}
