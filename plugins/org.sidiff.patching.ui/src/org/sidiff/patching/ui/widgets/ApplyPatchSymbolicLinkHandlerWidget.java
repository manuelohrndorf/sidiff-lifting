package org.sidiff.patching.ui.widgets;

import java.util.Iterator;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.PlatformUI;
import org.sidiff.common.ui.widgets.IWidget;
import org.sidiff.common.ui.widgets.IWidgetValidation;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;
import org.sidiff.patching.patch.patch.Patch;
import org.silift.difference.symboliclink.handler.ISymbolicLinkHandler;
import org.silift.difference.symboliclink.handler.util.SymbolicLinkHandlerUtil;
import org.silift.patching.settings.PatchingSettings;

public class ApplyPatchSymbolicLinkHandlerWidget implements IWidget, IWidgetValidation{


	private SortedMap<String, ISymbolicLinkHandler> symbolicLinkHandlers;
	
	private PatchingSettings settings;
	private Patch patch;
	protected Composite container;
	private Label symbolicLinkHandler_from_manifest;
	
	public ApplyPatchSymbolicLinkHandlerWidget(PatchingSettings settings, Patch patch) {
		super();
		this.settings = settings;
		this.patch = patch;
		symbolicLinkHandlers = new TreeMap<String, ISymbolicLinkHandler>();

		// Search registered symbolic link resolver extension
		Set<ISymbolicLinkHandler> symbolicLinkHandlerSet = SymbolicLinkHandlerUtil.getAvailableSymbolicLinkHandlers();

		for (Iterator<ISymbolicLinkHandler> iterator = symbolicLinkHandlerSet.iterator(); iterator.hasNext();) {
			ISymbolicLinkHandler symbolicLinkHandler = iterator.next();
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
		
		symbolicLinkHandler_from_manifest = new Label(container, SWT.NONE);
		
		if(symbolicLinkHandlers.get(patch.getSettings().get("symbolicLinkHandler")) != null){
			symbolicLinkHandler_from_manifest.setText(patch.getSettings().get("symbolicLinkHandler"));
			settings.setSymbolicLinkHandler(symbolicLinkHandlers.get(patch.getSettings().get("symbolicLinkHandler")));
		}else{
			MessageDialog.openInformation(
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
					"Missing Symbolic Link Handler", "Corresponding symbolic link handler is not found!");
			symbolicLinkHandler_from_manifest.setText("");
		}
		
		return container;
	}
	
	@Override
	public boolean validate() {
		if (symbolicLinkHandlers.get(patch.getSettings().get("symbolicLinkHandler")) != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public ValidationMessage getValidationMessage() {
		ValidationMessage message;
		if (validate()) {
			message = new ValidationMessage(ValidationType.OK, "");
		} else {
			message = new ValidationMessage(ValidationType.ERROR, "Missing Symbolic Link Handler!");
		}
		return message;
	}

	@Override
	public Composite getWidget() {
		return container;
	}

	@Override
	public void setLayoutData(Object layoutData) {
		container.setLayoutData(layoutData);
	}

}