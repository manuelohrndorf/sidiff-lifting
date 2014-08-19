package org.sidiff.patching.ui.handler;

import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gmt.modisco.xml.Attribute;
import org.eclipse.gmt.modisco.xml.Element;
import org.eclipse.gmt.modisco.xml.Node;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.facade.AsymmetricDiffFacade;
import org.sidiff.patching.ui.wizard.ApplyPatchWizard;
import org.silift.common.util.file.ZipUtil;
import org.silift.patching.patch.Manifest;
import org.silift.patching.patch.Patch;

public class PatchApplyHandler extends AbstractHandler {
	public static final String ARCHIVE_URI_PREFIX = "archive:file:///";
	public static final String ARCHIVE_SEPERATOR = "!/";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getActiveMenuSelection(event);

		if (selection.size() == 1) {
			Object firstElement = selection.getFirstElement();
			if (firstElement instanceof IFile) {
				IFile iFile = (IFile) firstElement;
				if (!iFile.getFileExtension().equals(AsymmetricDiffFacade.PATCH_EXTENSION))
					return -1;

				String patchPath = iFile.getLocation().toOSString();

				//TODO own util class
				// Search asymmetric difference:
				URI uri_asymDiff = null;
				URI uri_manifest = null;
				for (String entry : ZipUtil.getEntries(patchPath)) {
					if (entry.endsWith(AsymmetricDiffFacade.ASYMMETRIC_DIFF_EXT)) {
						uri_asymDiff = URI.createURI(ARCHIVE_URI_PREFIX + patchPath + ARCHIVE_SEPERATOR + entry);
					}if(entry.endsWith("xml")){
						uri_manifest = URI.createURI(ARCHIVE_URI_PREFIX + patchPath + ARCHIVE_SEPERATOR + entry);
					}
				}
				// Load AsymmetricDifference
				ResourceSet resourceSet = new ResourceSetImpl();
				Resource patchResource = resourceSet.getResource(uri_asymDiff, true);

				// Load Manifest
				Manifest manifest = loadManifest(uri_manifest);
				
				if (patchResource.getContents().size() == 0) {
					MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "Error in patch model", "There is something wrong with this patch!");
					return null;
				}
				EObject root = patchResource.getContents().get(0);
				if (root instanceof AsymmetricDifference) {
					final AsymmetricDifference difference = (AsymmetricDifference) root;
					final Patch patch = new Patch(difference);
					WizardDialog wizardDialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
							new ApplyPatchWizard(patch, manifest, (IFile) firstElement));
					wizardDialog.open();
				}
				else {
					MessageDialog.openError(
							PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
							"Patch Model Error","This is no patch model");
				}
			}
		}
		return null;
	}
	
	/** loads the settings stored in the manifest.xml
	 * @param uri
	 * @return
	 */
	public Manifest loadManifest(URI uri){
		// Load Resource
		ResourceSet resourceSet_Manifest = new ResourceSetImpl();
		Resource manifestResource = resourceSet_Manifest.getResource(uri, true);
		
		Manifest manifest = new Manifest();
		// Search for setting nodes
		for (Iterator<EObject> iterator = manifestResource.getAllContents(); iterator.hasNext();) {
			EObject eObject = (EObject) iterator.next();
			if(eObject instanceof Element){
				Element elem = (Element)eObject;
				if(elem.getName().equals("setting")){
					for(Node node : elem.getChildren()){
						if(node instanceof Attribute){
							Attribute attr = (Attribute)node;
							if(attr.getName().equals("matcher")){
								manifest.setMatcherName(attr.getValue());
							}else if(attr.getName().equals("symboliclinkhandler")){
								manifest.setSymbolicLinkHandlerName(attr.getValue());
							}
						}
					}
				}
			}
		}
		return manifest;
	}
}
