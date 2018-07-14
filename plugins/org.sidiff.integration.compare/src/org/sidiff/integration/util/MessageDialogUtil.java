package org.sidiff.integration.util;

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
import org.sidiff.integration.Activator;
import org.sidiff.patching.exceptions.OperationNotExecutableException;

/**
 * A class that provides static methods to create {@link MessageDialog}s for
 * certain types of exception that have to be handled multiple times.
 * 
 * @author Adrian Bingener, Robert Müller
 *
 */
public class MessageDialogUtil {

	/**
	 * Shows an error dialog for the given exception and logs the exception.
	 * The title is determined by the type of the exception.
	 * @param e the exception
	 * @param message message to show, <code>null</code> to show the exception's message
	 */
	public static void showExceptionDialog(Throwable e, String message) {
		String title;
		if(e instanceof InvalidModelException) {
			title = "Invalid Model";
		} else if(e instanceof NoCorrespondencesException) {
			title = "No Correspondences";
		} else if(e instanceof OperationNotExecutableException) {
			title = "Operation cannot be executed";
		} else {
			title = "An internal error occured";
		}

		if(message == null) {
			message = e.getMessage();
		}

		Activator.logError(title + "/" + message, e);
		IStatus status;
		if(e instanceof CoreException) {
			status = ((CoreException)e).getStatus();
		} else {
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e);
		}
		ErrorDialog.openError(getShell(), title, message, status);
	}

	public static void showExceptionDialog(Throwable e) {
		showExceptionDialog(e, null);
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