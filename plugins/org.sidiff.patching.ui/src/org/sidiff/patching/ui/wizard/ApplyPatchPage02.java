package org.sidiff.patching.ui.wizard;

import org.sidiff.common.extension.ui.widgets.ConfigurableExtensionWidget;
import org.sidiff.common.ui.pages.AbstractWizardPage;
import org.sidiff.patching.api.settings.PatchingSettings;
import org.sidiff.patching.patch.patch.Patch;
import org.sidiff.patching.ui.internal.PatchingUiPlugin;
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

	public ApplyPatchPage02(Patch patch, String title, PatchingSettings settings, ApplyPatchPage01 applyPatchPage01) {
		super("ApplyPatchPage02", title, PatchingUiPlugin.getImageDescriptor("icon.png"));
		this.patch = patch;
		this.settings = settings;
		this.use_SymbolicLinks = patch.getSettings().get("symbolicLinkHandler")!=null;
		this.applyPatchPage01 = applyPatchPage01;
	}

	@Override
	protected void createWidgets() {

		if(use_SymbolicLinks) {
			// Symbolic Link Resolver:
			symbolicLinkHandlerWidget = new ApplyPatchSymbolicLinkHandlerWidget(settings, patch);
			symbolicLinkHandlerWidget.setDependency(applyPatchPage01.getSettingsSourceWidget());
			addWidget(container, symbolicLinkHandlerWidget);
		} else {
			// Matcher:
			matcherWidget = new ApplyPatchMatchingEngineWidget(patch, settings);
			matcherWidget.setDependency(applyPatchPage01.getSettingsSourceWidget());
			addWidget(container, matcherWidget);
			ConfigurableExtensionWidget.addAllForWidget(container, matcherWidget, this::addWidget);
		}

		// Reliability:
		reliabilityWidget = new ReliabilityWidget(settings);
		reliabilityWidget.setDependency(applyPatchPage01.getSettingsSourceWidget());
		addWidget(container, reliabilityWidget);
	}

	@Override
	protected String getDefaultMessage() {
		return "Apply a patch to a model";
	}
}
