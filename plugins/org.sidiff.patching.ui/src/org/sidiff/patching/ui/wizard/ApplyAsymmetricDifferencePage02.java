package org.sidiff.patching.ui.wizard;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.sidiff.common.extension.ui.widgets.ConfigurableExtensionWidget;
import org.sidiff.common.ui.pages.AbstractWizardPage;
import org.sidiff.difference.technical.ui.widgets.MatchingEngineWidget;
import org.sidiff.matching.input.InputModels;
import org.sidiff.patching.api.settings.PatchingSettings;
import org.sidiff.patching.ui.Activator;
import org.sidiff.patching.ui.widgets.ReliabilityWidget;

public class ApplyAsymmetricDifferencePage02 extends AbstractWizardPage {

	private ApplyAsymmetricDifferencePage01 applyDiffPage01;

	private MatchingEngineWidget matcherWidget;
	private ReliabilityWidget reliabilityWidget;

	private InputModels inputModels;
	private PatchingSettings settings;

	public ApplyAsymmetricDifferencePage02(InputModels inputModels, String title,
			PatchingSettings settings, ApplyAsymmetricDifferencePage01 applyDiffPage01) {
		super("ApplyAsymmetricDifferencePage02", title, Activator.getImageDescriptor("icon.png"));
		this.inputModels = inputModels;
		this.settings = settings;
		this.applyDiffPage01 = applyDiffPage01;
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
		matcherWidget.setDependency(applyDiffPage01.getSettingsSourceWidget());
		addWidget(algorithmsGroup, matcherWidget);
		ConfigurableExtensionWidget.addAllForWidget(algorithmsGroup, matcherWidget, this::addWidget);

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
