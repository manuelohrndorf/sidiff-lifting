package org.sidiff.difference.lifting.ui.pages;

import org.eclipse.core.resources.IFile;
import org.sidiff.common.emf.input.InputModels;
import org.sidiff.common.ui.pages.AbstractWizardPage;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.lifting.api.settings.LiftingSettingsItem;
import org.sidiff.difference.lifting.api.settings.RecognitionEngineMode;
import org.sidiff.difference.lifting.ui.internal.DifferenceLiftingUiPlugin;
import org.sidiff.difference.lifting.ui.widgets.RecognitionEngineModeWidget;
import org.sidiff.difference.lifting.ui.widgets.RulebaseWidget;
import org.sidiff.integration.preferences.ui.widgets.SettingsSourceWidget;

public class CreateLiftingPage extends AbstractWizardPage {

	private SettingsSourceWidget settingsSourceWidget;
	private RecognitionEngineModeWidget recognitionWidget;
	private RulebaseWidget rulebaseWidget;

	private IFile differenceFile;
	private InputModels inputModels;
	private LiftingSettings settings;

	public CreateLiftingPage(IFile differenceFile, InputModels inputModels, LiftingSettings settings) {
		super("CreateLiftingPage", "Lift up technical difference", DifferenceLiftingUiPlugin.getImageDescriptor("icon.png"));
		this.differenceFile = differenceFile;
		this.inputModels = inputModels;
		this.settings = settings;
	}

	@Override
	protected void createWidgets() {

		// Settings Source:
		settingsSourceWidget = new SettingsSourceWidget(settings, inputModels);
		settingsSourceWidget.addConsideredSettings(LiftingSettingsItem.values());
		addWidget(container, settingsSourceWidget);

		// Recognition engine:
		recognitionWidget = new RecognitionEngineModeWidget(settings);
		recognitionWidget.setFilter(mode -> mode != RecognitionEngineMode.NO_LIFTING);
		recognitionWidget.setDependency(settingsSourceWidget);
		addWidget(container, recognitionWidget);

		// Rulebases:
		rulebaseWidget = new RulebaseWidget(inputModels, settings);
		rulebaseWidget.setDependency(settingsSourceWidget);
		addWidget(container, rulebaseWidget);
	}

	@Override
	protected String getDefaultMessage() {
		return "Edit operation detection on technical difference: " + differenceFile.getName();
	}
}
