package org.sidiff.integration.editor.extension;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.emf.modelstorage.SiDiffResourceSet;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.common.ui.util.UIUtil;

public class BasicEditorIntegration extends AbstractEditorIntegration {

	protected String docType, modelFileExt, diagramFileExt;

	public BasicEditorIntegration(String defaultEditorId, String diagramEditorId,
			String docType, String modelFileExt, String diagramFileExt) {
		super(defaultEditorId, diagramEditorId);
		this.docType = docType;
		this.modelFileExt = modelFileExt;
		this.diagramFileExt = diagramFileExt;
	}

	protected URI getMainDiagramFile(URI modelFile) {
		URI[] files = getDiagramFiles(modelFile);
		return files.length > 0 ? files[0] : null;
	}

	protected URI[] getDiagramFiles(URI modelFile) {
		String ext = modelFile.fileExtension();
		if (modelFileExt != null && diagramFileExt != null && modelFileExt.equals(ext)) {
			URI diagramUri = modelFile.trimFileExtension().appendFileExtension(diagramFileExt);
			return new URI[] { diagramUri };
		}
		return new URI[0];
	}

	@Override
	public boolean supportsModel(URI modelFile) {
		return modelFileExt != null && modelFile.fileExtension().toLowerCase().endsWith(modelFileExt);
	}

	@Override
	public boolean supportsDiagram(URI modelFile) {
		if(diagramFileExt == null) {
			return false;
		} else if(modelFile.fileExtension().toLowerCase().endsWith(diagramFileExt)) {
			return true;
		}
		URI diagram = getMainDiagramFile(modelFile);
		if(diagram == null) {
			return false;
		}
		File diagramFile = EMFStorage.toFile(diagram);
		return diagramFile != null && diagramFile.exists();
	}

	@Override
	public IEditorPart openModelInDefaultEditor(URI modelURI) {
		if (modelURI == null || !isDefaultEditorPresent()) {
			return null;
		}
		try {
			IFile file = EMFStorage.toIFile(modelURI);
			IEditorInput input;
			if ((file != null) && file.exists()) {
				input = new FileEditorInput(file);
			} else {
				input = new URIEditorInput(modelURI);
			}
			return UIUtil.getActivePage().openEditor(input, defaultEditorId);
		} catch (PartInitException ex) {
			return null;
		}
	}

	@Override
	public IEditorPart openDiagram(URI diagramURI) {
		if (diagramURI == null || !isDiagramEditorPresent()) {
			return null;
		}
		try {
			IFile file = EMFStorage.toIFile(diagramURI);
			IEditorInput input;
			if ((file != null) && file.exists()) {
				input = new FileEditorInput(file);
			} else {
				input = new URIEditorInput(diagramURI);
			}
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			return page.openEditor(input, diagramEditorId);
		} catch (PartInitException ex) {
			return null;
		}
	}

	@Override
	public IEditorPart openDiagramForModel(URI modelFile) {
		return openDiagram(getMainDiagramFile(modelFile));
	}

	@Override
	public boolean supportsGMFAnimation(URI diagramFile) {
		return false;
	}

	@Override
	public boolean supportsDiagramming(Resource model) {
		if(docType == null || !EMFModelAccess.getDocumentTypes(model, Scope.RESOURCE).contains(docType)) {
			return false;			
		}
		URI diagram = getMainDiagramFile(model.getURI());
		if(diagram == null) {
			return false;
		}
		File diagramFile = EMFStorage.toFile(diagram);
		return diagramFile != null && diagramFile.exists();
	}

	@Override
	public boolean supportsModel(Resource model) {
		return docType != null && docType.equals(EMFModelAccess.getCharacteristicDocumentType(model))
				&& model.getURI().fileExtension().equals(this.modelFileExt);
	}

	/**
	 * Determines if a file can be copied using a standard file copy instead of EMF
	 * @param diagramFile File to check
	 * @return <i>true</i> if file can be copied using standard file copy
	 */
	protected boolean allowFileCopy(URI diagramFile) {
		return false;
	}

	@Override
	public URI copyDiagram(URI modelFile, URI savePath) throws FileNotFoundException {
		try {
			String modelFileName = modelFile.lastSegment();
			
			// This code assumes that mainDiagramUri is in diagramFiles:
			URI[] diagramFiles = getDiagramFiles(modelFile);
			URI mainDiagramUri = getMainDiagramFile(modelFile);

			if (diagramFiles == null || diagramFiles.length == 0 || mainDiagramUri == null) {
				throw new RuntimeException("Model not supported");
			}

			SiDiffResourceSet resourceSet = SiDiffResourceSet.create();
			resourceSet.getSaveOptions().put(XMIResource.OPTION_URI_HANDLER, new SiDiffResourceSet.DeresolveLastSegment());
			
			// Load the diagram file:
			List<Resource> diagramResources = new ArrayList<Resource>();
			List<File> diagramNonResources = new ArrayList<File>();
			for (URI diagramFile : diagramFiles) {
				File file = EMFStorage.toFile(diagramFile);
				if (file == null || !file.exists() || !file.isFile()) {
					throw new FileNotFoundException("A diagram file was not found");
				}
				Resource resource = tryLoad(resourceSet, diagramFile);
				if (resource != null) {
					diagramResources.add(resource);
				} else if (allowFileCopy(diagramFile)) {
					diagramNonResources.add(file);
				} else {
					throw new RuntimeException("Diagram cannot be loaded as resource");
				}
			}
			// Save the diagram files:
			for (Resource diagramResource : diagramResources) {
				EcoreUtil.resolveAll(diagramResource);
				for(Resource r : diagramResource.getResourceSet().getResources()){
					if(r.getURI().lastSegment().equals(modelFileName)){
						r.setURI(savePath.appendSegment(modelFileName));
					}
				}
				resourceSet.saveResourceAs(diagramResource, savePath.appendSegment(diagramResource.getURI().lastSegment()));
			}
			for (File file : diagramNonResources) {
				File dest = EMFStorage.toFile(savePath.appendSegment(file.getName()));
				Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
				// Refresh views like project manager, so that the copied file
				// is displayed
				try {
					EMFStorage.toIFile(dest).refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
				} catch (Exception e) {
					LogUtil.log(LogEvent.NOTICE, "Could not refresh file after file copy");
				}
			}
			return savePath.appendSegment(mainDiagramUri.lastSegment());
		} catch (Exception e) {
			if (e instanceof FileNotFoundException) {
				throw (FileNotFoundException) e;
			}
			LogUtil.log(LogEvent.NOTICE, e.getMessage());
			throw new RuntimeException("Error copying diagram", e);
		}
	}

	private static Resource tryLoad(SiDiffResourceSet resourceSet, URI uri) {
		try {
			return resourceSet.getResource(uri, true);
		} catch (Exception e) {
			LogUtil.log(LogEvent.NOTICE, "Could not load diagram as resource", e);
			return null;
		}
	}

	@Override
	public Collection<EObject> getHighlightableElements(EObject element) {
		return Collections.singleton(element);
	}

	@Override
	public Map<String, String> getFileExtensions() {
		Map<String, String>  extensions = new HashMap<String, String> ();
		extensions.put("model", this.modelFileExt);
		extensions.put("diagram", this.diagramFileExt);
		return extensions;
	}
}
