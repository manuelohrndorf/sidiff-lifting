package org.sidiff.patching.patch.ui.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.difference.lifting.api.LiftingFacade;
import org.sidiff.patching.patch.ui.wizard.CreateAsymDiffWizard;

public class CreateAsymDiffHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection currentSelection = HandlerUtil.getCurrentSelection(event);

		if (currentSelection instanceof IStructuredSelection) {
			final IStructuredSelection selection = (IStructuredSelection) currentSelection;

			if (selection.size() == 2) {
				// Show a busy indicator while the runnable is executed
				BusyIndicator.showWhile(Display.getDefault(), new Runnable() {
					@Override
					public void run() {
						// Create a new difference
						IFile fileA = (IFile) selection.toArray()[0];
						IFile fileB = (IFile) selection.toArray()[1];
						Resource resourceA = LiftingFacade.loadModel(fileA.getLocation().toOSString());
						Resource resourceB = LiftingFacade.loadModel(fileB.getLocation().toOSString());
						String docTypeA = EMFModelAccess.getCharacteristicDocumentType(resourceA);
						String docTypeB = EMFModelAccess.getCharacteristicDocumentType(resourceB);
						if(docTypeA.equals(docTypeB)){

							WizardDialog wizardDialog = new WizardDialog(PlatformUI
								.getWorkbench().getActiveWorkbenchWindow().getShell(),
								new CreateAsymDiffWizard(fileA, fileB));

							wizardDialog.open();
						}else {
							MessageDialog.openError(
									PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
									"File Input Error","The input files must have the same document type!");
						}
					}
					
				});
			}
		}
		return null;
	}

}
