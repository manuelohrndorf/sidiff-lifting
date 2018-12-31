package org.sidiff.difference.lifting.ui.handler;

import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.handlers.HandlerUtil;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.common.ui.util.UIUtil;
import org.sidiff.difference.lifting.api.util.PipelineUtils;
import org.sidiff.difference.lifting.ui.wizard.CreateDifferenceWizard;
import org.sidiff.difference.lifting.ui.wizard.CreateLiftingWizard;

public class LiftDifferenceHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final Object selectedObjects[] = HandlerUtil.getCurrentStructuredSelection(event).toArray();
		Display.getDefault().asyncExec(() -> {
			if (selectedObjects.length == 1) {
				// Lift a technical difference:
				WizardDialog wizardDialog = new WizardDialog(UIUtil.getActiveShell(), new CreateLiftingWizard((IFile)selectedObjects[0]));
				wizardDialog.open();
			} else if (selectedObjects.length == 2) {
				IFile fileA = (IFile)selectedObjects[0];
				IFile fileB = (IFile)selectedObjects[1];
				Resource resourceA = PipelineUtils.loadModel(fileA.getLocation().toOSString());
				Resource resourceB = PipelineUtils.loadModel(fileB.getLocation().toOSString());
				//TODO Workaround: bisher keine einheitliche Regelung f�r Ressourcen mit mehreren Dokumenttypen
				// Annahme: Sofern nicht alle Dokumenttypen der Profile gleich sind, sollte zumindest der eigentliche Dokumenttyp gleich sein.
				boolean canHandle = false;
				if(EMFModelAccess.isProfiled(resourceA) || EMFModelAccess.isProfiled(resourceB)){
					canHandle = EMFModelAccess.getCharacteristicDocumentType(resourceA).equals(EMFModelAccess.getCharacteristicDocumentType(resourceB));
				} else {
					Set<String> docTypeA = EMFModelAccess.getDocumentTypes(resourceA, Scope.RESOURCE_SET);
					Set<String> docTypeB = EMFModelAccess.getDocumentTypes(resourceB, Scope.RESOURCE_SET);
					docTypeA.retainAll(docTypeB);
					canHandle = docTypeA.size() > 0;
				}
	
				if (canHandle){
					// Create a new difference:
					WizardDialog wizardDialog = new WizardDialog(UIUtil.getActiveShell(), new CreateDifferenceWizard(fileA, fileB));
					wizardDialog.open();
				} else {
					MessageDialog.openError(UIUtil.getActiveShell(), "File Input Error",
							"The input files must have the same document type!");
				}
			}
		});
		return null;
	}
}
