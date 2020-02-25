package org.sidiff.integration.compare.structureview.actions;

import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.PlatformUI;
import org.sidiff.common.ui.util.Exceptions;
import org.sidiff.integration.compare.internal.CompareIntegrationPlugin;

/**
 * @author rmueller
 */
public class ShowPropertiesViewAction extends Action {

	public ShowPropertiesViewAction() {
		super("Show Properties View", CompareIntegrationPlugin.getImageDescriptor(CompareIntegrationPlugin.IMAGE_PROPERTIES));
	}

	@Override
	public void run() {
		Exceptions.log(() -> {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(IPageLayout.ID_PROP_SHEET);
			return Status.OK_STATUS;
		});
	}
}
