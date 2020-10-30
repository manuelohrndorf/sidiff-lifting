package org.sidiff.patching.ui.widgets;

import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.PlatformUI;
import org.sidiff.common.ui.widgets.AbstractWidget;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;
import org.sidiff.patching.api.settings.PatchingSettings;
import org.sidiff.patching.patch.patch.Patch;
import org.silift.difference.symboliclink.handler.ISymbolicLinkHandler;

public class ApplyPatchSymbolicLinkHandlerWidget extends AbstractWidget {

	private Map<String, ISymbolicLinkHandler> symbolicLinkHandlers;
	private PatchingSettings settings;
	private Patch patch;

	protected Composite container;

	public ApplyPatchSymbolicLinkHandlerWidget(PatchingSettings settings, Patch patch) {
		this.settings = settings;
		this.patch = patch;

		// Search registered symbolic link resolver extension
		symbolicLinkHandlers = ISymbolicLinkHandler.MANAGER.getExtensions().stream()
				.collect(Collectors.toMap(h -> h.getName(), h -> h));
	}

	@Override
	public Composite createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		{
			GridLayout grid = new GridLayout(1, false);
			grid.marginWidth = 0;
			grid.marginHeight = 0;
			container.setLayout(grid);
		}

		// Symbolic link handler controls:
		Label slhLabel = new Label(container, SWT.NONE);
		slhLabel.setText("Symbolic Link Handler:");

		Label symbolicLinkHandler_from_manifest = new Label(container, SWT.NONE);
		ISymbolicLinkHandler symbolicLinkHandler = getManifestSymbolicLinkHandler();
		if(symbolicLinkHandler != null) {
			symbolicLinkHandler_from_manifest.setText(symbolicLinkHandler.getName());
			settings.setSymbolicLinkHandler(symbolicLinkHandler);
		} else {
			MessageDialog.openInformation(
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
					"Missing Symbolic Link Handler", "Corresponding symbolic link handler is not found!");
			symbolicLinkHandler_from_manifest.setText("Not found");
		}

		return container;
	}

	private ISymbolicLinkHandler getManifestSymbolicLinkHandler() {
		return symbolicLinkHandlers.get(patch.getSettings().get("symbolicLinkHandler"));
	}

	@Override
	protected ValidationMessage doValidate() {
		if (getManifestSymbolicLinkHandler() == null) {
			return new ValidationMessage(ValidationType.ERROR, "Missing Symbolic Link Handler!");
		}
		return ValidationMessage.OK;
	}

	@Override
	public Composite getWidget() {
		return container;
	}
}