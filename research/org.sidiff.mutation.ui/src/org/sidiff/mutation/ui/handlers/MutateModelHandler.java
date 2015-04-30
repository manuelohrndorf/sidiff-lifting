package org.sidiff.mutation.ui.handlers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
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
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.sidiff.mutation.ui.wizards.MutationWizard;
import org.silift.common.util.access.EMFModelAccessEx;
import org.silift.common.util.emf.EMFStorage;
import org.silift.common.util.emf.Scope;

public class MutateModelHandler extends AbstractHandler {

	private static final String MESSAGE = "Error loading selected model";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection currentSelection = HandlerUtil.getCurrentSelection(event);

		if (currentSelection instanceof IStructuredSelection) {
			final IStructuredSelection selection = (IStructuredSelection) currentSelection;

			if (selection.size() == 0)
				return null;
			String targetModelFile = null;
			if (selection.size() == 1) {
				if (selection.getFirstElement() instanceof IFile) {
					IFile file = (IFile) selection.getFirstElement();
					targetModelFile = file.getLocation().makeAbsolute()
							.toOSString();
				}
			}
			if (targetModelFile == null)
				return null;
			Resource targetModel;
			Set<String> doctypes;
			try {
				targetModel = EMFStorage.eLoad(
						EMFStorage.pathToUri(targetModelFile)).eResource();
			} catch (Exception e) {
				targetModel = null;
			}

			final Resource targetModelF = targetModel;
			
			// Show a busy indicator while the runnable is executed
			BusyIndicator.showWhile(Display.getDefault(), new Runnable() {

				@Override
				public void run() {
					WizardDialog wizardDialog = new WizardDialog(PlatformUI
							.getWorkbench().getActiveWorkbenchWindow()
							.getShell(), new MutationWizard(targetModelF));

					wizardDialog.open();
				}
			});
		}

		return null;
	}

}
