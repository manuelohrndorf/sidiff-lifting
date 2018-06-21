package org.sidiff.vcmsintegration.util;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.emf.exceptions.NoCorrespondencesException;
import org.sidiff.vcmsintegration.Activator;

/**
 * A class that provides static methods to create {@link MessageDialog}s for
 * certain types of exception that have to be handled multiple times.
 * 
 * @author Adrian Bingener, Robert Müller
 *
 */
public class MessageDialogUtil {

	public static void showExceptionDialog(Throwable e) {
		if(e instanceof InvalidModelException) {
			showErrorDialog("Invalid Model", "One of the given models seems to be invalid.");
		} else if(e instanceof NoCorrespondencesException) {
			showErrorDialog("No Correspondences", "No correspondences could be found between the models.");
		} else if(e instanceof CoreException) {
			ErrorDialog.openError(getShell(), "An internal error occured", e.getMessage(), ((CoreException)e).getStatus());
		} else {
			showExceptionDialog(new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e)));
		}
	}

	public static void showErrorDialog(String title, String message) {
		MessageDialog.open(MessageDialog.ERROR, getShell(), title, message, SWT.NONE);
	}

	public static void showMessageDialog(String title, String message) {
		MessageDialog.open(MessageDialog.INFORMATION, getShell(), title, message, SWT.NONE);
	}

	public static void showProgressDialog(IRunnableWithProgress runnable) {
		try {
			ProgressMonitorDialog dialog = new ProgressMonitorDialog(getShell());
			dialog.run(false, false, runnable);
		} catch (InvocationTargetException e) {
			showExceptionDialog(e.getTargetException());
		} catch (InterruptedException e) {
			showExceptionDialog(e);
		}
	}

	protected static Shell getShell() {
		IWorkbenchWindow win = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		return win != null ? win.getShell() : null;
	}
}