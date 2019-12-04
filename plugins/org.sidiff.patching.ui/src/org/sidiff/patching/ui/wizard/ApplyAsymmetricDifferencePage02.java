package org.sidiff.patching.ui.wizard;

import org.sidiff.common.emf.input.InputModels;
import org.sidiff.common.extension.ui.widgets.ConfigurableExtensionWidget;
import org.sidiff.common.ui.pages.AbstractWizardPage;
import org.sidiff.difference.technical.ui.widgets.MatchingEngineWidget;
import org.sidiff.patching.api.settings.PatchingSettings;
import org.sidiff.patching.ui.internal.PatchingUiPlugin;
import org.sidiff.patching.ui.widgets.ReliabilityWidget;

public class ApplyAsymmetricDifferencePage02 extends AbstractWizardPage {

	private ApplyAsymmetricDifferencePage01 applyDiffPage01;

	private MatchingEngineWidget matcherWidget;
	private ReliabilityWidget reliabilityWidget;

	private InputModels inputModels;
	private PatchingSettings settings;

	public ApplyAsymmetricDifferencePage02(InputModels inputModels, String title,
			PatchingSettings settings, ApplyAsymmetricDifferencePage01 applyDiffPage01) {
		super("ApplyAsymmetricDifferencePage02", title, PatchingUiPlugin.getImageDescriptor("icon.png"));
		this.inputModels = inputModels;
		this.settings = settings;
		this.applyDiffPage01 = applyDiffPage01;
	}

	@Override
	protected void createWidgets() {

		// Matcher:
		matcherWidget = new MatchingEngineWidget(inputModels, settings);
		matcherWidget.setDependency(applyDiffPage01.getSettingsSourceWidget());
		addWidget(container, matcherWidget);
		ConfigurableExtensionWidget.addAllForWidget(container, matcherWidget, this::addWidget);

		// Reliability:
		reliabilityWidget = new ReliabilityWidget();
		reliabilityWidget.setSettings(this.settings);
		reliabilityWidget.setDependency(applyDiffPage01.getSettingsSourceWidget());
		addWidget(container, reliabilityWidget);
	}

	@Override
	protected String getDefaultMessage() {
		return "Apply a patch to a model";
	}
}
