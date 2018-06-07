package org.sidiff.vcmsintegration.util;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.emf.exceptions.NoCorrespondencesException;

/**
 * A class that provides static methods to create {@link MessageDialog}s for
 * certain types of exception that have to be handled multiple times.
 * 
 * @author Adrian Bingener
 *
 */
public class MessageDialogUtil {

	/**
	 * Creates a dialog for {@link InvalidModelException}s
	 */
	public static void showInvalidModelDialog() {
		MessageDialog.open(MessageDialog.ERROR, null, "Invalid Model", "One of the given models seems to be invalid.", SWT.NONE);
	}

	/**
	 * Creates a dialog for {@link NoCorrespondencesException}s
	 */
	public static void showNoCorrespondencesDialog() {
		MessageDialog.open(MessageDialog.ERROR, null, "No Correspondences", "No correspondences could be found between the models.", SWT.NONE);
	}
}