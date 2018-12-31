package org.sidiff.patching.patch.ui.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.handlers.HandlerUtil;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.ui.util.UIUtil;
import org.sidiff.difference.lifting.api.util.PipelineUtils;
import org.sidiff.patching.patch.ui.wizard.CreateAsymDiffWizard;

public class CreateAsymDiffHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection selection = HandlerUtil.getCurrentStructuredSelection(event);
		if (selection.size() == 2) {
			Display.getDefault().asyncExec(() -> {
				// Create a new difference
				IFile fileA = (IFile)selection.toArray()[0];
				IFile fileB = (IFile)selection.toArray()[1];
				Resource resourceA = PipelineUtils.loadModel(fileA.getLocation().toOSString());
				Resource resourceB = PipelineUtils.loadModel(fileB.getLocation().toOSString());
				String docTypeA = EMFModelAccess.getCharacteristicDocumentType(resourceA);
				String docTypeB = EMFModelAccess.getCharacteristicDocumentType(resourceB);
				if(docTypeA.equals(docTypeB)) {
					WizardDialog wizardDialog = new WizardDialog(UIUtil.getActiveShell(), new CreateAsymDiffWizard(fileA, fileB));
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
