package org.sidiff.patching.ui.widgets;

import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.PlatformUI;
import org.sidiff.common.ui.widgets.AbstractWidget;
import org.sidiff.common.ui.widgets.IWidgetValidation;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;
import org.sidiff.patching.api.settings.PatchingSettings;
import org.sidiff.patching.patch.patch.Patch;
import org.silift.difference.symboliclink.handler.ISymbolicLinkHandler;
import org.silift.difference.symboliclink.handler.util.SymbolicLinkHandlerUtil;

public class ApplyPatchSymbolicLinkHandlerWidget extends AbstractWidget implements IWidgetValidation {

	private SortedMap<String, ISymbolicLinkHandler> symbolicLinkHandlers;
	private PatchingSettings settings;
	private Patch patch;

	protected Composite container;

	public ApplyPatchSymbolicLinkHandlerWidget(PatchingSettings settings, Patch patch) {
		this.settings = settings;
		this.patch = patch;

		// Search registered symbolic link resolver extension
		Set<ISymbolicLinkHandler> symbolicLinkHandlerSet = SymbolicLinkHandlerUtil.getAvailableSymbolicLinkHandlers();
		symbolicLinkHandlers = new TreeMap<String, ISymbolicLinkHandler>();
		for (ISymbolicLinkHandler symbolicLinkHandler : symbolicLinkHandlerSet) {
			symbolicLinkHandlers.put(symbolicLinkHandler.getName(), symbolicLinkHandler);
		}
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
		ISymbolicLinkHandler symbolicLinkHandler = symbolicLinkHandlers.get(patch.getSettings().get("symbolicLinkHandler"));
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

	@Override
	public boolean validate() {
		return symbolicLinkHandlers.get(patch.getSettings().get("symbolicLinkHandler")) != null;
	}

	@Override
	public ValidationMessage getValidationMessage() {
		if (validate()) {
			return ValidationMessage.OK;
		} else {
			return new ValidationMessage(ValidationType.ERROR, "Missing Symbolic Link Handler!");
		}
	}

	@Override
	public Composite getWidget() {
		return container;
	}
}