package org.sidiff.patching.ui.handler;

import java.util.Collection;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.patching.interrupt.IPatchInterruptHandler;
import org.sidiff.patching.interrupt.PatchInterruptOption;
import org.sidiff.patching.validation.IValidationError;

public class DialogPatchInterruptHandler implements IPatchInterruptHandler {

	@Override
	public PatchInterruptOption getInterruptOption(Boolean revertedOperation,
			OperationInvocation operationInvocation,
			Collection<IValidationError> validationErrors) {
		
			String title = null;
			String[] options = null;
			
			if(revertedOperation){
				title = "Reverting Operation: Validation Error";
				options = new String[] {"Ignore", "Reapply and continue", "Reapply and abort"};
			}
			else{
				title = "Applying Operation: Validation Error";
				options = new String[] {"Ignore", "Revert and continue", "Revert and abort"};
			}
		
			StringBuffer validationErrorString = new StringBuffer();
			for(IValidationError error : validationErrors){
				validationErrorString.append(error.getMessage());
				validationErrorString.append(System.lineSeparator());
			}
		 MessageDialog dialog = new MessageDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
				 "Operation " + operationInvocation.getChangeSet().getName() + ": Validation Error",
				 null,
				 "The following validation errors have occured:"
				 + System.lineSeparator() + validationErrorString,
				 MessageDialog.WARNING, options, 0);
	    		int result = dialog.open();
	    		switch (result) {
				case 0:
					return PatchInterruptOption.IGNORE;
				case 1:
					return PatchInterruptOption.CONTINUE;
				case 2:
					return PatchInterruptOption.ABORT;
				default:
					return PatchInterruptOption.IGNORE;
				}
	}

}
