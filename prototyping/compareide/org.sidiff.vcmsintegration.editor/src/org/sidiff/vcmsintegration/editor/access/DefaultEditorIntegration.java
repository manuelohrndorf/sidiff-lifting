package org.sidiff.vcmsintegration.editor.access;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.vcmsintegration.editor.extension.IEditorIntegration;

public class DefaultEditorIntegration implements IEditorIntegration {

	private static DefaultEditorIntegration INSTANCE = null;

	private String defaultEditorId = null;

	public static DefaultEditorIntegration getInstance(URI modelFile) {
		if (INSTANCE == null)
			INSTANCE = new DefaultEditorIntegration(modelFile);
		return INSTANCE;
	}

	private DefaultEditorIntegration(URI modelFile) {
		// Save editor id for later purposes
		String path = EMFStorage.uriToPath(modelFile);
		IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(path);
		defaultEditorId = desc.getId();
	}

	@Override
	public boolean supportsModel(Resource model) {
		// Save editor id for later purposes
		String path = EMFStorage.uriToPath(model.getURI());
		IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(path);
		defaultEditorId = desc.getId();
		return true;
	}

	@Override
	public boolean supportsDiagramming(Resource model) {
		return false;
	}

	@Override
	public boolean supportsModel(URI modelFile) {
		// Save editor id for later purposes
		String path = EMFStorage.uriToPath(modelFile);
		IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(path);
		defaultEditorId = desc.getId();
		return true;
	}

	@Override
	public boolean supportsDiagram(URI diagramFile) {
		return false;
	}

	@Override
	public EditingDomain getEditingDomain(IEditorPart editorPart) {
		if (editorPart instanceof IEditingDomainProvider) {
			IEditingDomainProvider editor = (IEditingDomainProvider) editorPart;
			return editor.getEditingDomain();
		} else {
			return null;
		}
	}

	@Override
	public Resource getResource(IEditorPart editorPart) {
		if (editorPart instanceof IEditingDomainProvider) {
			IEditingDomainProvider editor = (IEditingDomainProvider) editorPart;
			return editor.getEditingDomain().getResourceSet().getResources().get(0);
		} else {
			return null;
		}
	}

	@Override
	public URI copyDiagram(URI modelURI, String savePath) throws FileNotFoundException {
		throw new RuntimeException("Not supported");
	}

	@Override
	public String getDefaultEditorID() {
		return defaultEditorId;
	}

	@Override
	public String getDiagramEditorID() {
		return null;
	}

	@Override
	public Boolean isDefaultEditorPresent() {
		return true;
	}

	@Override
	public boolean isDiagramEditorPresent() {
		return false;
	}

	@Override
	public IEditorPart openModelInDefaultEditor(URI modelURI) {
		try {
			String path = EMFStorage.uriToPath(modelURI);
			IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(path);
			defaultEditorId = desc.getId();
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			return page.openEditor(new URIEditorInput(modelURI), defaultEditorId);
		} catch (PartInitException ex) {
			return null;
		}
	}

	@Override
	public IEditorPart openDiagram(URI diagramURI) {
		throw new RuntimeException("Not supported");
	}

	@Override
	public boolean supportsGMFAnimation(URI diagramFile) {
		return false;
	}

	@Override
	public IEditorPart openDiagramForModel(URI modelFile) {
		return null;
	}

	@Override
	public EObject getHighlightableElement(EObject element) {
		return element;
	}

	@Override
	public HashMap<String, String> getFileExtensions() {
		return new HashMap<String, String> ();
	}

}
