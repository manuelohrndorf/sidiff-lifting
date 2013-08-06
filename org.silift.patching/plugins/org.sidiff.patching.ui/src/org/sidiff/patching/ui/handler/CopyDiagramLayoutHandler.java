package org.sidiff.patching.ui.handler;

import java.util.List;
import java.util.logging.Logger;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.RelativeBendpoints;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.sidiff.patching.ui.dialog.CopyLayoutDialog;

public class CopyDiagramLayoutHandler extends AbstractHandler {
	private Logger LOGGER = Logger.getLogger(CopyDiagramLayoutHandler.class.getName());

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getActiveMenuSelection(event);
		if (selection.size() == 2) {
			@SuppressWarnings("unchecked")
			List<IFile> files = selection.toList();
			CopyLayoutDialog dialog = new CopyLayoutDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), files);
			if (dialog.open() == Window.OK) {
				List<IFile> sortedFiles = dialog.getFiles();
				ResourceSet resourceSet = new ResourceSetImpl();
				URI uri = URI.createFileURI(sortedFiles.get(0).getLocation().toOSString());
				Resource from = resourceSet.getResource(uri, true);
				uri = URI.createFileURI(sortedFiles.get(1).getLocation().toOSString());
				Resource to = resourceSet.getResource(uri, true);
				
				copyLayoutData(from, to);
			}
		}
		return null;
	}

	private void copyLayoutData(Resource from, Resource to) {
		for (TreeIterator<EObject> iterator = from.getAllContents(); iterator.hasNext();) {
			EObject fromObject = (EObject) iterator.next();
			if (fromObject instanceof Bounds) {
				Bounds fromBounds = (Bounds) fromObject;
				LOGGER.info(fromBounds.toString());
			} else if (fromObject instanceof Location) {
				Location fromLocation = (Location) fromObject;
				LOGGER.info(fromLocation.toString());
			} else if (fromObject instanceof RelativeBendpoints) {
				RelativeBendpoints fromBendpoints = (RelativeBendpoints) fromObject;
				LOGGER.info(fromBendpoints.toString());
			}
		}
		
	}

}
