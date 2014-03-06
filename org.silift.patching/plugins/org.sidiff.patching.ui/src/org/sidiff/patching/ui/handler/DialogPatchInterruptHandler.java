package org.sidiff.patching.ui.handler;

import java.util.Collection;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.patching.interrupt.IPatchInterruptHandler;
import org.sidiff.patching.interrupt.PatchInterruptOption;
import org.sidiff.patching.validation.IValidationError;

public class DialogPatchInterruptHandler implements IPatchInterruptHandler {
	
	private final static String LINE_SEPERATOR = System.getProperty("line.separator");

	@Override
	public PatchInterruptOption getInterruptOption(Boolean revertedOperation,
			OperationInvocation operationInvocation,
			Collection<IValidationError> validationErrors) {
		
			String title = null;
			String[] options = null;
			
			if(revertedOperation){
				title = "Reverting Operation: Validation Error";
				options = new String[] {"Ignore", "Reapply Operation"};
			}
			else{
				title = "Applying Operation: Validation Error";
				options = new String[] {"Ignore", "Revert Operation"};
			}
		
			StringBuffer validationErrorString = new StringBuffer();
			for(IValidationError error : validationErrors){
				validationErrorString.append(error.getMessage());
				validationErrorString.append(LINE_SEPERATOR);
			}
		 MessageDialog dialog = new MessageDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
				 "Validation Error(s) after Operation " + operationInvocation.getChangeSet().getName(),
				 null,
				 "The following validation error(s) has(have) occurred:"
				 + LINE_SEPERATOR + validationErrorString,
				 MessageDialog.WARNING, options, 0);
	    		int result = dialog.open();
	    		switch (result) {
				case 0:
					return PatchInterruptOption.IGNORE;
				case 1:
					return PatchInterruptOption.UNDO;			
				default:
					return PatchInterruptOption.IGNORE;
				}
	}

}
