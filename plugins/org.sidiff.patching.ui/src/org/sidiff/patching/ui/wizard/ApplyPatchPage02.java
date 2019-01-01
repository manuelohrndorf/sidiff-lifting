package org.sidiff.patching.ui.wizard;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.sidiff.common.extension.ui.widgets.ConfigurableExtensionWidget;
import org.sidiff.common.ui.pages.AbstractWizardPage;
import org.sidiff.patching.api.settings.PatchingSettings;
import org.sidiff.patching.patch.patch.Patch;
import org.sidiff.patching.ui.Activator;
import org.sidiff.patching.ui.widgets.ApplyPatchMatchingEngineWidget;
import org.sidiff.patching.ui.widgets.ApplyPatchSymbolicLinkHandlerWidget;
import org.sidiff.patching.ui.widgets.ReliabilityWidget;

public class ApplyPatchPage02 extends AbstractWizardPage {

	private ApplyPatchPage01 applyPatchPage01;

	private ApplyPatchMatchingEngineWidget matcherWidget;
	private ApplyPatchSymbolicLinkHandlerWidget symbolicLinkHandlerWidget;
	private ReliabilityWidget reliabilityWidget;

	private Patch patch;
	private PatchingSettings settings;
	private boolean use_SymbolicLinks;

	public ApplyPatchPage02(Patch patch, String pageName, String title, PatchingSettings settings, ApplyPatchPage01 applyPatchPage01) {
		super(pageName, title, Activator.getImageDescriptor("icon.png"));
		this.patch = patch;
		this.settings = settings;
		this.use_SymbolicLinks = patch.getSettings().get("symbolicLinkHandler")!=null;
		this.applyPatchPage01 = applyPatchPage01;
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

		if(use_SymbolicLinks) {
			// Symbolic Link Resolver:
			symbolicLinkHandlerWidget = new ApplyPatchSymbolicLinkHandlerWidget(settings, patch);
			symbolicLinkHandlerWidget.setDependency(applyPatchPage01.getSettingsSourceWidget());
			addWidget(algorithmsGroup, symbolicLinkHandlerWidget);
		} else {
			// Matcher:
			matcherWidget = new ApplyPatchMatchingEngineWidget(patch, settings);
			matcherWidget.setDependency(applyPatchPage01.getSettingsSourceWidget());
			addWidget(algorithmsGroup, matcherWidget);
			ConfigurableExtensionWidget.addAllForWidget(algorithmsGroup, matcherWidget, this::addWidget);
		}

		// Reliability:
		reliabilityWidget = new ReliabilityWidget();
		reliabilityWidget.setSettings(this.settings);
		reliabilityWidget.setDependency(applyPatchPage01.getSettingsSourceWidget());
		addWidget(container, reliabilityWidget);
	}

	@Override
	protected String getDefaultMessage() {
		return "Apply a patch to a model";
	}
}
