package org.sidiff.patching.ui.handler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.StringReader;
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
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.sidiff.common.xml.XMLParser;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.facade.AsymmetricDiffFacade;
import org.sidiff.patching.ui.wizard.ApplyPatchWizard;
import org.silift.common.util.file.ZipUtil;
import org.silift.patching.patch.Manifest;
import org.silift.patching.patch.Patch;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

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
						uri_manifest = URI.createURI(patchPath); //URI.createURI(ARCHIVE_URI_PREFIX + patchPath + ARCHIVE_SEPERATOR + entry);
					}
				}
				// Load AsymmetricDifference
				ResourceSet resourceSet = new ResourceSetImpl();
				Resource patchResource = resourceSet.getResource(uri_asymDiff, true);

				// Load Manifest
				Manifest manifest = null;
				try {
					manifest = loadManifest(uri_manifest);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
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
	 * @throws FileNotFoundException 
	 */
	public Manifest loadManifest(URI uri) throws FileNotFoundException{
		// Load Resource
		String txt = ZipUtil.readFileFromZip(uri.toString(), "MANIFEST.xml");
		Document manifestResource = XMLParser.parseXML(new InputSource(new StringReader(txt)));
		Element setting = (Element)manifestResource.getElementsByTagName("setting").item(0);
		Attr matcher = setting.getAttributeNode("matcher");
		Attr symblH = setting.getAttributeNode("symboliclinkhandler");
		Manifest manifest = new Manifest();
		if(matcher != null){
			manifest.setMatcherName(matcher.getValue());
		}else if(symblH != null){
			manifest.setSymbolicLinkHandlerName(symblH.getValue());
		}
		
		assert !(matcher==null && symblH==null): "There went something wrong during reading the manifest";
		return manifest;
	}
}
