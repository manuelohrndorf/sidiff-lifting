package org.sidiff.domain.editor.extension;

import java.io.File;
import java.io.FileNotFoundException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPathEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.silift.common.util.access.EMFModelAccessEx;
import org.silift.common.util.emf.EMFStorage;

public class BasicDomainEditor extends AbstractDomainEditor {

	protected final String docType, modelFileExt, diagramFileExt;

	public BasicDomainEditor(String treeEditorId, String diagramEditorId,
			String docType, String modelFileExt, String diagramFileExt) {
		super(treeEditorId, diagramEditorId);
		this.docType = docType;
		this.modelFileExt = modelFileExt;
		this.diagramFileExt = diagramFileExt;
	}

	protected URI getMainDiagramFile(URI modelFile) {
		URI[] files = getDiagramFiles(modelFile);
		return (files.length >= 0 ? files[0] : null);
	}

	protected URI[] getDiagramFiles(URI modelFile) {
		if (modelFileExt != null && modelFileExt.equals(modelFile.fileExtension().toLowerCase())) {
			return new URI[] { URI.createURI(modelFile.toString().replaceAll(
					modelFileExt + "$", diagramFileExt)) };
		}
		return new URI[0];
	}

	@Override
	public boolean supportsModel(URI modelFile) {
		return (modelFileExt != null && modelFile.fileExtension().toLowerCase().endsWith(modelFileExt));
	}

	@Override
	public boolean supportsDiagram(URI diagramFile) {
		return (diagramFile != null && diagramFile.fileExtension().toLowerCase()
				.endsWith(diagramFileExt));
	}

	@Override
	public IEditorPart openModelInTreeEditor(URI modelURI) {
		if (modelURI == null || !isTreeEditorPresent())
			return null;
		try {
			IWorkbenchPage page = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage();
			IPath location= Path.fromOSString(EMFStorage.uriToFile(modelURI).getAbsolutePath());
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(location);
			return page.openEditor(new FileEditorInput(file), treeEditorId);
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
			IPath location= Path.fromOSString(EMFStorage.uriToFile(diagramURI).getAbsolutePath());
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(location);
			return page.openEditor(new FileEditorInput(file),
					diagramEditorId);
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
		return (docType != null && docType.equals(EMFModelAccessEx
				.getCharacteristicDocumentType(model)));
		// TODO Auch Dateiendung überprüfen
	}

	@Override
	public boolean supportsModel(Resource model) {
		return (docType != null && docType.equals(EMFModelAccessEx
				.getCharacteristicDocumentType(model)));
	}

	@Override
	public URI copyDiagram(URI modelFile, String savePath)
			throws FileNotFoundException {
		final String separator = System.getProperty("file.separator");
		try {
			final URI[] diagramFiles = getDiagramFiles(modelFile);
			final URI mainDiagramUri = getMainDiagramFile(modelFile);
			if (diagramFiles == null || diagramFiles.length == 0 || mainDiagramUri == null)
				throw new RuntimeException("Model not supported");
			//TODO Check if main diagram file is in array
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
			for (Resource resource : set.getResources()){
				final URI diagramSaveFile = EMFStorage.pathToUri(savePath
						+ separator + resource.getURI().lastSegment());
				EMFStorage.eSaveAs(diagramSaveFile,
						resource.getContents().get(0), false);
			}
			return EMFStorage.pathToUri(savePath
					+ separator + mainDiagramUri.lastSegment());
		} catch (Exception e) {
			LogUtil.log(LogEvent.NOTICE, e.getMessage());
			// TODO Exception-handling?
			throw new RuntimeException("Error copying diagram", e);
		}
	}

}
