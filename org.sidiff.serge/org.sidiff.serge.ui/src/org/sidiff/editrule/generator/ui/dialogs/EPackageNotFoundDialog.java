package org.sidiff.editrule.generator.ui.dialogs;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

public class EPackageNotFoundDialog{

	public static void openErrorDialog(final String pluginId, final Exception e) {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				MessageDialog.openError(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(), "EPackage Not Found", e.getMessage());
			}
		});
	}
}
