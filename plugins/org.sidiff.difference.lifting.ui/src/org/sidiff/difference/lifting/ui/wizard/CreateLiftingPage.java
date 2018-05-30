package org.sidiff.difference.lifting.ui.wizard;

import org.eclipse.core.resources.IFile;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.sidiff.common.ui.pages.AbstractWizardPage;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.lifting.api.settings.LiftingSettingsItem;
import org.sidiff.difference.lifting.ui.Activator;
import org.sidiff.difference.lifting.ui.widgets.RecognitionEngineWidget;
import org.sidiff.difference.lifting.ui.widgets.RulebaseWidget;
import org.sidiff.integration.preferences.ui.widgets.SettingsSourceWidget;
import org.sidiff.matching.input.InputModels;

public class CreateLiftingPage extends AbstractWizardPage {

	private String DEFAULT_MESSAGE = "Edit operation detection on a technical difference.";

	private SettingsSourceWidget settingsSourceWidget;
	private RecognitionEngineWidget recognitionWidget;
	private RulebaseWidget rulebaseWidget;

	private IFile differenceFile;
	private InputModels inputModels;
	private LiftingSettings settings;

	public CreateLiftingPage(IFile differenceFile, InputModels inputModels, LiftingSettings settings) {
		super("CreateLiftingPage", "Lift up the difference", Activator.getImageDescriptor("icon.png"));
		this.differenceFile = differenceFile;
		this.inputModels = inputModels;
		this.settings = settings;
	}

	@Override
	protected void createWidgets() {

		// Show technical file:
		Group technicalDifferenceGroup = new Group(container, SWT.NONE);
		{
			GridLayout grid = new GridLayout(1, false);
			grid.marginWidth = 10;
			grid.marginHeight = 10;
			technicalDifferenceGroup.setLayout(grid);

			GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
			technicalDifferenceGroup.setLayoutData(data);
			technicalDifferenceGroup.setText("Selected technical difference model");
		}

		Label model = new Label(technicalDifferenceGroup, SWT.NONE);
		model.setText(differenceFile.getName());

		// Settings Source:
		settingsSourceWidget = new SettingsSourceWidget(settings, inputModels);
		settingsSourceWidget.addConsideredSettings(LiftingSettingsItem.values());
		addWidget(container, settingsSourceWidget);

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

		// Recognition engine:
		recognitionWidget = new RecognitionEngineWidget();
		recognitionWidget.setSettings(this.settings);
		recognitionWidget.setDependency(settingsSourceWidget);
		recognitionWidget.showNoSemanticLifting = false;
		addWidget(algorithmsGroup, recognitionWidget);

		// Rulebases:
		rulebaseWidget = new RulebaseWidget(inputModels);
		rulebaseWidget.setSettings(this.settings);
		rulebaseWidget.setDependency(settingsSourceWidget);
		addWidget(container, rulebaseWidget);
	}

	@Override
	protected String getDefaultMessage() {
		return DEFAULT_MESSAGE;
	}
}
