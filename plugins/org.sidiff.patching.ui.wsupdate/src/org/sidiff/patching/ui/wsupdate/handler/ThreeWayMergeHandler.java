package org.sidiff.patching.ui.wsupdate.handler;

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
import org.sidiff.difference.lifting.facade.LiftingFacade;
import org.sidiff.patching.ui.wsupdate.wizard.ThreeWayMergeWizard;

public class ThreeWayMergeHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection currentSelection = HandlerUtil.getCurrentSelection(event);

		if (currentSelection instanceof IStructuredSelection) {
			final IStructuredSelection selection = (IStructuredSelection) currentSelection;

			if (selection.size() == 3) {
				// Show a busy indicator while the runnable is executed
				BusyIndicator.showWhile(Display.getDefault(), new Runnable() {
					@Override
					public void run() {
						// Create a new difference
						IFile fileMine = (IFile) selection.toArray()[0];
						IFile fileTheirs = (IFile) selection.toArray()[1];
						IFile fileBase = (IFile) selection.toArray()[2];
						Resource resourceMine = LiftingFacade.loadModel(fileMine.getLocation().toOSString());
						Resource resourceTheirs = LiftingFacade.loadModel(fileTheirs.getLocation().toOSString());
						Resource resourceBase = LiftingFacade.loadModel(fileBase.getLocation().toOSString());
						String docTypeMine = EMFModelAccess.getCharacteristicDocumentType(resourceMine);
						String docTypeTheirs = EMFModelAccess.getCharacteristicDocumentType(resourceTheirs);
						String docTypeBase = EMFModelAccess.getCharacteristicDocumentType(resourceBase);
						if(docTypeMine.equals(docTypeTheirs) && docTypeTheirs.equals(docTypeBase)){
							
							WizardDialog wizardDialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow()
									.getShell(), new ThreeWayMergeWizard(fileMine, fileTheirs, fileBase));
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
