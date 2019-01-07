package org.sidiff.difference.technical.ui.validation;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.sidiff.common.ui.util.UIUtil;

public class ValidateDialog {

	public static boolean openErrorDialog(final String pluginId, final Exception e) {
		boolean skipValidation[] = new boolean[] { false };
		Display.getDefault().syncExec(() -> {
			List<Status> singleStats = new ArrayList<Status>();
			
			for (String s : e.getMessage().split(";")) {
				// build & add status
				singleStats.add(new Status(IStatus.ERROR, pluginId, s));
			}
			
			MultiStatus multiStats = new MultiStatus(pluginId, IStatus.ERROR,
					singleStats.toArray(new Status[] {}),
					"The models are not valid (open 'Details')", null);
			
			ErrorDialog.openError(UIUtil.getActiveShell(), "Invalid Models",
					"Problems encountered during model validation...", multiStats);
			
			skipValidation[0] = MessageDialog.openQuestion(
					UIUtil.getActiveShell(),
					"Problems encountered during model validation...", 
					"Continue and skip validation?");
		});
		return skipValidation[0];
	}
}
