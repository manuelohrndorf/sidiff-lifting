package org.sidiff.integration.structureview.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.sidiff.common.ui.util.MessageDialogUtil;
import org.sidiff.integration.Activator;

/**
 * 
 * @author Robert Müller
 *
 */
public class ShowPropertiesViewAction extends Action {

	public ShowPropertiesViewAction() {
		super("Show Properties View", Activator.getImageDescriptor(Activator.IMAGE_PROPERTIES));
	}

	@Override
	public void run() {
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(IPageLayout.ID_PROP_SHEET);
		} catch (PartInitException e) {
			MessageDialogUtil.showExceptionDialog(e, "Property view could not be initialized");
		}
	}
}
