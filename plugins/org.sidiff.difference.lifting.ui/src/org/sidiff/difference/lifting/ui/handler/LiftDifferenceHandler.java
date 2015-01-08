package org.sidiff.difference.lifting.ui.handler;

import java.util.Set;

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
import org.sidiff.difference.lifting.facade.LiftingFacade;
import org.sidiff.difference.lifting.ui.wizard.CreateDifferenceWizard;
import org.sidiff.difference.lifting.ui.wizard.CreateLiftingWizard;
import org.silift.common.util.access.EMFModelAccessEx;
import org.silift.common.util.emf.Scope;

public class LiftDifferenceHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection currentSelection = HandlerUtil.getCurrentSelection(event);

		if (currentSelection instanceof IStructuredSelection) {
			final IStructuredSelection selection = (IStructuredSelection) currentSelection;

			if (selection.size() == 1) {
				// Show a busy indicator while the runnable is executed
				BusyIndicator.showWhile(Display.getDefault(), new Runnable() {

					@Override
					public void run() {
						// Lift a technical difference:
						IFile file = (IFile) selection.getFirstElement();

						WizardDialog wizardDialog = new WizardDialog(PlatformUI
								.getWorkbench().getActiveWorkbenchWindow().getShell(),
								new CreateLiftingWizard(file));

						wizardDialog.open();
					}
				});
			} else if (selection.size() == 2) {
				// Show a busy indicator while the runnable is executed
				BusyIndicator.showWhile(Display.getDefault(), new Runnable() {

					@Override
					public void run() {
						IFile fileA = (IFile) selection.toArray()[0];
						IFile fileB = (IFile) selection.toArray()[1];
						Resource resourceA = LiftingFacade.loadModel(fileA.getLocation().toOSString());
						Resource resourceB = LiftingFacade.loadModel(fileB.getLocation().toOSString());
						Set<String> docTypeA = EMFModelAccessEx.getDocumentTypes(resourceA, Scope.RESOURCE_SET);
						Set<String> docTypeB = EMFModelAccessEx.getDocumentTypes(resourceB, Scope.RESOURCE_SET);
						if (docTypeA.equals(docTypeB)){

							// Create a new difference:
							WizardDialog wizardDialog = new WizardDialog(PlatformUI
								.getWorkbench().getActiveWorkbenchWindow().getShell(),
								new CreateDifferenceWizard(fileA, fileB));

							wizardDialog.open();
						} else {
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
