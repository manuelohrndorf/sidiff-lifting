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
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.difference.lifting.api.LiftingFacade;
import org.sidiff.difference.lifting.ui.wizard.CreateDifferenceWizard;
import org.sidiff.difference.lifting.ui.wizard.CreateLiftingWizard;

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
						//TODO Workaround: bisher keine einheitliche Regelung f�r Ressourcen mit mehreren Dokumenttypen
						// Annahme: Sofern nicht alle Dokumenttypen der Profile gleich sind, sollte zumindest der eigentliche Dokumenttyp gleich sein.
						boolean canHandle = false;
						if(EMFModelAccess.isProfiled(resourceA) || EMFModelAccess.isProfiled(resourceB)){
								canHandle = EMFModelAccess.getCharacteristicDocumentType(resourceA).equals(EMFModelAccess.getCharacteristicDocumentType(resourceB));
						}else {
							Set<String> docTypeA = EMFModelAccess.getDocumentTypes(resourceA, Scope.RESOURCE_SET);
							Set<String> docTypeB = EMFModelAccess.getDocumentTypes(resourceB, Scope.RESOURCE_SET);
							docTypeA.retainAll(docTypeB);
							canHandle = docTypeA.size() > 0;
						}
						if (canHandle){

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
