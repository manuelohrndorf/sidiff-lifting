package org.sidiff.patching.patch.ui.wizard;

import org.eclipse.jface.resource.ImageDescriptor;
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
		matcherWidget = new MatchingEngineWidget(inputModels);
		matcherWidget.setSettings(this.settings);
		matcherWidget.setDependency(createPatchPage01.getSettingsSourceWidget());
		addWidget(algorithmsGroup, matcherWidget);
		ConfigurableExtensionWidget.addAllForWidget(algorithmsGroup, matcherWidget, this::addWidget);

		// Symbolic Link Resolver:
		symbolicLinkHandlerWidget = new SymbolicLinkHandlerWidget();
		symbolicLinkHandlerWidget.setSettings(this.settings);
		if(symbolicLinkHandlerWidget.isSymbolicLinkHandlerAvailable()) {
			symbolicLinkHandlerWidget.setDependency(createPatchPage01.getSettingsSourceWidget());
			addWidget(algorithmsGroup, symbolicLinkHandlerWidget);
		}

		// Technical Difference Builder:
		builderWidget = new DifferenceBuilderWidget(inputModels);
		builderWidget.setSettings(this.settings);
		builderWidget.setDependency(createPatchPage01.getSettingsSourceWidget());
		addWidget(algorithmsGroup, builderWidget);
	}

	@Override
	protected String getDefaultMessage() {
		return defaultMessage;
	}
}
