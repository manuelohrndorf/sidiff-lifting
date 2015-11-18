package org.sidiff.difference.symmetric.profiled.ui.handler;

import java.io.FileNotFoundException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.ui.util.UIUtil;
import org.sidiff.difference.lifting.facade.LiftingFacade;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetricprofiled.ProfiledSymmetricDifference;
import org.sidiff.difference.symmetricprofiled.impl.SymmetricProfiledFactoryImpl;

public class DeriveHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection currentSelection = HandlerUtil.getCurrentSelection(event);

		if (currentSelection instanceof IStructuredSelection) {
			final IStructuredSelection selection = (IStructuredSelection) currentSelection;
			Job job = new Job("Deriving Profiled Symmetric Difference") {
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					IFile file=(IFile) selection.getFirstElement();
					String inputFile = file.getLocation().toOSString();
					final String outputFile=inputFile+"profiled";
					SymmetricDifference sd = LiftingFacade.loadDifference(inputFile);
					ProfiledSymmetricDifference psd = SymmetricProfiledFactoryImpl.init()
							.createProfiledSymmetricDifference();
					psd.derive(sd);
					if (!monitor.isCanceled()) {
						EMFStorage.eSaveAs(EMFStorage.pathToUri(outputFile), psd);
						try {
							file.getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
							UIUtil.openEditor(outputFile);	
						} catch (CoreException e) {
							e.printStackTrace();
						} catch (OperationCanceledException e) {
						} catch (NullPointerException e){
							e.printStackTrace();
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
						
						return Status.OK_STATUS;
					} else {
						return Status.CANCEL_STATUS;
					}
				}
			};
			// Start the Job
			job.schedule();
		}
		return null;
	}

}
