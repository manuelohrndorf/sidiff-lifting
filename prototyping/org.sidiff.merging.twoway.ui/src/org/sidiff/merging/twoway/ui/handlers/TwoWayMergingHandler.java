package org.sidiff.merging.twoway.ui.handlers;

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
import org.sidiff.difference.lifting.facade.LiftingFacade;
import org.sidiff.merging.twoway.ui.wizards.TwoWayMergeWizard;

public class TwoWayMergingHandler extends AbstractHandler {

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
						IFile fileA = (IFile) selection.toArray()[0];
						IFile fileB = (IFile) selection.toArray()[1];
						Resource resourceA = LiftingFacade.loadModel(fileA.getLocation().toOSString());
						Resource resourceB = LiftingFacade.loadModel(fileB.getLocation().toOSString());
						Set<String> docTypeA = EMFModelAccess.getDocumentTypes(resourceA, Scope.RESOURCE_SET);
						Set<String> docTypeB = EMFModelAccess.getDocumentTypes(resourceB, Scope.RESOURCE_SET);
						if (docTypeA.equals(docTypeB)){

							// Create a new difference:
							WizardDialog wizardDialog = new WizardDialog(PlatformUI
								.getWorkbench().getActiveWorkbenchWindow().getShell(),
								new TwoWayMergeWizard(fileA, fileB));

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
