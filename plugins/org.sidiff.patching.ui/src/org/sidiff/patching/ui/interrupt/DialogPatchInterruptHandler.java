package org.sidiff.patching.ui.interrupt;

import java.util.Collection;
import java.util.stream.Collectors;

import org.eclipse.jface.dialogs.MessageDialog;
import org.sidiff.common.ui.util.UIUtil;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.patching.interrupt.IPatchInterruptHandler;
import org.sidiff.patching.interrupt.PatchInterruptOption;
import org.sidiff.patching.validation.IValidationError;

public class DialogPatchInterruptHandler implements IPatchInterruptHandler {

	private final static String LINE_SEPERATOR = System.getProperty("line.separator");

	@Override
	public PatchInterruptOption getInterruptOption(boolean revertedOperation,
			OperationInvocation operationInvocation,
			Collection<IValidationError> validationErrors) {

		String[] options = new String[] { "Ignore", revertedOperation ? "Reapply Operation" : "Revert Operation" };

		MessageDialog dialog = new MessageDialog(UIUtil.getActiveShell(),
				"Validation Error(s) after Operation " + operationInvocation.getChangeSet().getName(), null,
				"The following validation error(s) has(have) occurred:" + LINE_SEPERATOR
					+ validationErrors.stream().map(IValidationError::getMessage).collect(Collectors.joining(LINE_SEPERATOR)),
				MessageDialog.WARNING, options, 0);
		switch (dialog.open()) {
			case 0:
				return PatchInterruptOption.IGNORE;
			case 1:
				return PatchInterruptOption.UNDO;
			default:
				return PatchInterruptOption.IGNORE;
		}
	}

}
