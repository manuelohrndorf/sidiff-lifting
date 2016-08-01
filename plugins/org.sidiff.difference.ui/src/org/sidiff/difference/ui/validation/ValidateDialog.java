package org.sidiff.difference.ui.validation;

import java.util.ArrayList;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

public class ValidateDialog{
	
	private boolean skipValidation = false;

	public boolean openErrorDialog(final String pluginId, final Exception e) {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				ArrayList<Status> singleStats = new ArrayList<Status>();
				
				for (String s : e.getMessage().split(";")) {
					// build & add status
					singleStats.add(new Status(IStatus.ERROR, pluginId, s));
				}
				
				MultiStatus multiStats = new MultiStatus(pluginId, IStatus.ERROR,
						singleStats.toArray(new Status[] {}),
						"The models are not valid (open 'Details')", null);
				
				ErrorDialog.openError(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(), "Invalid Models",
						"Problems encountered during model validation...", multiStats);
				
				skipValidation = MessageDialog.openQuestion(
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
						"Problems encountered during model validation...", 
						"Continue and skip validation?");
			}
		});
		
		return skipValidation;
	}
}
