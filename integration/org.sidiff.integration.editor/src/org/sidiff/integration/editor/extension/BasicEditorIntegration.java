package org.sidiff.integration.editor.extension;

import java.io.File;
import java.io.FileNotFoundException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.silift.common.util.access.EMFModelAccessEx;
import org.silift.common.util.emf.EMFStorage;

public class BasicEditorIntegration extends AbstractEditorIntegration {

	protected final String docType, modelFileExt, diagramFileExt;

	public BasicEditorIntegration(String defaultEditorId,
			String diagramEditorId, String docType, String modelFileExt,
			String diagramFileExt) {
		super(defaultEditorId, diagramEditorId);
		this.docType = docType;
		this.modelFileExt = modelFileExt;
		this.diagramFileExt = diagramFileExt;
	}

	protected URI getMainDiagramFile(URI modelFile) {
		URI[] files = getDiagramFiles(modelFile);
		return (files.length >= 0 ? files[0] : null);
	}

	protected URI[] getDiagramFiles(URI modelFile) {
		String ext = modelFile.fileExtension();
		if (modelFileExt != null && diagramFileExt != null
				&& modelFileExt.equals(ext)) {
			int index = modelFile.toString().lastIndexOf(ext);
			String modelName = modelFile.toString().substring(0, index);
			return new URI[] { URI.createURI(modelName + this.diagramFileExt) };
		}
		return new URI[0];
	}

	@Override
	public boolean supportsModel(URI modelFile) {
		return (modelFileExt != null && modelFile.fileExtension().toLowerCase()
				.endsWith(modelFileExt));
	}

	@Override
	public boolean supportsDiagram(URI diagramFile) {
		return (diagramFile != null && diagramFileExt != null && diagramFile
				.fileExtension().toLowerCase().endsWith(diagramFileExt));
	}

	@Override
	public IEditorPart openModelInDefaultEditor(URI modelURI) {
		if (modelURI == null || !isDefaultEditorPresent())
			return null;
		try {
			IWorkbenchPage page = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage();
			IPath location = Path.fromOSString(EMFStorage.uriToFile(modelURI)
					.getAbsolutePath());
			IFile file = ResourcesPlugin.getWorkspace().getRoot()
					.getFileForLocation(location);
			IEditorInput input;
			if (file != null && file.exists()) {
				input = new FileEditorInput(file);
			} else {
				input = new URIEditorInput(modelURI);
			}
			return page.openEditor(input, defaultEditorId);
		} catch (PartInitException ex) {
			return null;
		}
	}

	@Override
	public IEditorPart openDiagram(URI diagramURI) {
		if (diagramURI == null || !isDiagramEditorPresent())
			return null;
		try {
			IWorkbenchPage page = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage();
			IPath location = Path.fromOSString(EMFStorage.uriToFile(diagramURI)
					.getAbsolutePath());
			IFile file = ResourcesPlugin.getWorkspace().getRoot()
					.getFileForLocation(location);
			IEditorInput input;
			if (file != null && file.exists()) {
				input = new FileEditorInput(file);
			} else {
				input = new URIEditorInput(diagramURI);
			}
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
		return (docType != null
				&& docType.equals(EMFModelAccessEx
						.getCharacteristicDocumentType(model)) && getMainDiagramFile(model
					.getURI()) != null);
	}

	@Override
	public boolean supportsModel(Resource model) {
		return (docType != null
				&& docType.equals(EMFModelAccessEx
						.getCharacteristicDocumentType(model)) && model
				.getURI().fileExtension().equals(this.modelFileExt));
	}

	@Override
	public URI copyDiagram(URI modelFile, String savePath)
			throws FileNotFoundException {
		final String separator = System.getProperty("file.separator");
		try {
			final URI[] diagramFiles = getDiagramFiles(modelFile);
			final URI mainDiagramUri = getMainDiagramFile(modelFile);
			if (diagramFiles == null || diagramFiles.length == 0
					|| mainDiagramUri == null)
				throw new RuntimeException("Model not supported");
			//This code assumes that mainDiagramUri is in diagramFiles
			ResourceSet set = new ResourceSetImpl();
			for (URI diagramFile : diagramFiles) {
				final File testFile = new File(
						EMFStorage.uriToPath(diagramFile));
				if (!testFile.exists() || !testFile.isFile())
					throw new FileNotFoundException(
							"A diagram file was not found");
				Resource resource = EMFStorage.eLoad(diagramFile).eResource();
				set.getResources().add(resource);
			}
			EcoreUtil.resolveAll(set);
			for(Resource resource : set.getResources()) {
				if(resource.getURI().fileExtension().equals(modelFile.fileExtension())){
					final URI modelFileURI = EMFStorage.pathToUri(savePath + separator + resource.getURI().lastSegment()); 
					EMFStorage.eSaveAs(modelFileURI, resource.getContents().get(0), true);
				}
			}
			for (Resource resource : set.getResources()) {
				if(!resource.getURI().fileExtension().equals(modelFile.fileExtension())){
					final URI diagramSaveFile = EMFStorage.pathToUri(savePath
							+ separator + resource.getURI().lastSegment());
					EMFStorage.eSaveAs(diagramSaveFile,
							resource.getContents().get(0), true);
				}
			}
			return EMFStorage.pathToUri(savePath + separator
					+ mainDiagramUri.lastSegment());
		} catch (Exception e) {
			if (e instanceof FileNotFoundException)
				throw (FileNotFoundException) e;
			LogUtil.log(LogEvent.NOTICE, e.getMessage());
			throw new RuntimeException("Error copying diagram", e);
		}
	}

	@Override
	public EObject getHighlightableElement(EObject element) {
		return element;
	}

}
