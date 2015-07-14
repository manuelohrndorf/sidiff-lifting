package org.sidiff.domain.editor.siriusecore;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.eclipse.sirius.diagram.ui.tools.api.editor.DDiagramEditor;
import org.eclipse.sirius.ui.business.api.dialect.DialectUIManager;
import org.eclipse.sirius.viewpoint.DRepresentation;
import org.eclipse.sirius.viewpoint.DRepresentationElement;
import org.eclipse.sirius.viewpoint.DView;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.domain.editor.extension.AbstractDomainEditor;
import org.silift.common.util.access.EMFModelAccessEx;
import org.silift.common.util.emf.EMFStorage;

public class EcoreSiriusDomainEditor extends AbstractDomainEditor {

	public EcoreSiriusDomainEditor() {
		// The fake editor is sufficient for this purpose
		super("org.eclipse.emf.ecore.presentation.EcoreEditorID",
				"org.eclipse.sirius.ui.fakeeditoronlyforicon");

	}

	private static final String MODEL_FILE_EXT = "ecore";
	private static final String DIAGRAM_FILE_EXT = "aird";
	private static final String DOC_TYPE = EcorePackage.eINSTANCE.getNsURI();

	@Override
	public boolean supportsModel(Resource model) {
		return DOC_TYPE.equals(EMFModelAccessEx
				.getCharacteristicDocumentType(model));
	}

	@Override
	public boolean supportsDiagramming(Resource model) {
		return DOC_TYPE.equals(EMFModelAccessEx
				.getCharacteristicDocumentType(model));
	}

	@Override
	public boolean supportsModel(URI modelFile) {
		return modelFile.fileExtension().toLowerCase().endsWith(MODEL_FILE_EXT);
	}

	@Override
	public boolean supportsDiagram(URI diagramFile) {
		return diagramFile.fileExtension().toLowerCase()
				.endsWith(DIAGRAM_FILE_EXT);
	}

	@Override
	public URI copyDiagram(URI modelFile, String savePath)
			throws FileNotFoundException {
		final String separator = System.getProperty("file.separator");
		try {
			final URI mainDiagramUri = getDiagramForModel(modelFile);
			if (mainDiagramUri == null)
				throw new RuntimeException("Model not supported");
			ResourceSet set = new ResourceSetImpl();
			final File testFile = new File(EMFStorage.uriToPath(mainDiagramUri));
			if (!testFile.exists() || !testFile.isFile())
				throw new FileNotFoundException("A diagram file was not found");
			Resource resource = EMFStorage.eLoad(mainDiagramUri).eResource();
			set.getResources().add(resource);
			final URI diagramSaveFile = EMFStorage.pathToUri(savePath
					+ separator + resource.getURI().lastSegment());
			EMFStorage.eSaveAs(diagramSaveFile, resource.getContents().get(0),
					false);
			return EMFStorage.pathToUri(savePath + separator
					+ mainDiagramUri.lastSegment());
		} catch (Exception e) {
			if (e instanceof FileNotFoundException)
				throw (FileNotFoundException) e;
			LogUtil.log(LogEvent.NOTICE, e.getMessage());
			// TODO Exception-handling?
			throw new RuntimeException("Error copying diagram", e);
		}
	}

	@Override
	public IEditorPart openModelInTreeEditor(URI modelURI) {
		if (modelURI == null || !isTreeEditorPresent())
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
			return page.openEditor(input, treeEditorId);
		} catch (PartInitException ex) {
			return null;
		}
	}

	private URI getDiagramForModel(URI modelFile) {
		if (MODEL_FILE_EXT.equals(modelFile.fileExtension().toLowerCase())) {
			return URI.createURI(modelFile.toString().replaceAll(
					MODEL_FILE_EXT + "$", DIAGRAM_FILE_EXT));
		}
		return null;
	}
	

	@Override
	public IEditorPart openDiagramForModel(URI modelFile) {
		URI diagramUri = getDiagramForModel(modelFile);
		if (diagramUri != null)
			return openDiagram(diagramUri);
		return null;
	}

	@Override
	public boolean supportsGMFAnimation(URI diagramFile) {
		return true;
	}

	@Override
	public IEditorPart openDiagram(URI diagramFile) {
		if (diagramFile == null || !isDiagramEditorPresent())
			return null;

		// Start Sirius Session
		Session session = SessionManager.INSTANCE.getSession(diagramFile,
				new NullProgressMonitor());
		if (session == null){
			System.out.println("Session was not opened"); //TODO Remove
			return null;
		}
		if (!session.isOpen()) {
			session.open(new NullProgressMonitor());
		}

		for (Session otherSession : SessionManager.INSTANCE.getSessions()) {
			otherSession.getOwnedViews();
		}
		// Get representation
		Collection<DView> views = session.getOwnedViews();
		EList<DRepresentation> representations = new BasicEList<DRepresentation>();
		for (DView view : views) {
			representations.addAll(view.getOwnedRepresentations());
		}

		// FIXME: Generic way of choosing the right representation
		DRepresentation representation = null;
		if (representations.size() < 1)
			return null;
		representation = representations.get(0);
		if (representation != null) {
			return DialectUIManager.INSTANCE.openEditor(session,
					representation, new NullProgressMonitor());
		} else {
			return null;
		}
	}

	@Override
	public EObject getHighlightableElement(EObject element) {
		if (element instanceof DRepresentationElement) {
			return ((DRepresentationElement) element).getTarget();
		} else {
			return null;
		}
	}

	@Override
	public Resource getResource(IEditorPart editorPart) {
		if (editorPart instanceof DDiagramEditor) {
			DDiagramEditor editor = (DDiagramEditor) editorPart;
			Iterator<Resource> iter=editor.getSession().getSemanticResources().iterator();
			if(iter.hasNext()){
				//TODO Determine the right resource if there are multiple
				return iter.next();
			} else {
				return null;
			}
		} else {
			// TODO Exception?
			return null;
		}
	}

	
	
}
