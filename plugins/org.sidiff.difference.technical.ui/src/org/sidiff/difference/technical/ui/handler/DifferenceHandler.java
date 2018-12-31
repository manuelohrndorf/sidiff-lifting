package org.sidiff.difference.technical.ui.handler;

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
import org.sidiff.difference.technical.api.util.TechnicalDifferenceUtils;
import org.sidiff.difference.technical.ui.wizard.CreateDifferenceWizard;

public class DifferenceHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Object selectedObjects[] = HandlerUtil.getCurrentStructuredSelection(event).toArray();
		if (selectedObjects.length == 2) {				
			Display.getDefault().asyncExec(() -> {
				IFile fileA = (IFile)selectedObjects[0];
				IFile fileB = (IFile)selectedObjects[1];
				Resource resourceA = TechnicalDifferenceUtils.loadModel(fileA.getLocation().toOSString());
				Resource resourceB = TechnicalDifferenceUtils.loadModel(fileB.getLocation().toOSString());

				boolean canHandle = false;
				if (EMFModelAccess.isProfiled(resourceA) || EMFModelAccess.isProfiled(resourceB)){
					canHandle = EMFModelAccess.getCharacteristicDocumentType(resourceA).equals(EMFModelAccess.getCharacteristicDocumentType(resourceB));
				} else {
					Set<String> docTypeA = EMFModelAccess.getDocumentTypes(resourceA, Scope.RESOURCE_SET);
					Set<String> docTypeB = EMFModelAccess.getDocumentTypes(resourceB, Scope.RESOURCE_SET);
					docTypeA.retainAll(docTypeB);
					canHandle = docTypeA.size() > 0;
				}

				// Create a new difference:
				if (canHandle) {
					WizardDialog wizardDialog = new WizardDialog(UIUtil.getActiveShell(), new CreateDifferenceWizard(fileA, fileB));
					wizardDialog.open();
				} else {
					MessageDialog.openError(UIUtil.getActiveShell(), "File Input Error",
							"The input files must have the same document type!");
				}
			});
		}
		return null;
	}
}
