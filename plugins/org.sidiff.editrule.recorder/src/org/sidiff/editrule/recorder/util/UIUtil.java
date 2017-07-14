package org.sidiff.editrule.recorder.util;

import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

public class UIUtil {
	
	public static void showMessage(String message) {
		MessageDialog.openInformation(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart().getTitle(),
				message);
	}
	
	public static void showError(String message) {
		MessageDialog.openError(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart().getTitle(),
				message);
	}
	
	
	/**
	 * This class validates a String.
	 */
	public static class NotEmptyValidator implements IInputValidator {

	  public String isValid(String newText) {
	    int len = newText.length();

	    // Determine if input is empty
	    if (len < 1) return "Empty inputs are not allowed!";

	    // Input must be OK
	    return null;
	  }
	}
}
