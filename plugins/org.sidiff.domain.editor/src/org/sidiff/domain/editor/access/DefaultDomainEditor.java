package org.sidiff.domain.editor.access;

import java.io.FileNotFoundException;

import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.sidiff.domain.editor.extension.IDomainEditor;
import org.silift.common.util.emf.EMFStorage;


//TODO Use Default editor to open default editors^^
public class DefaultDomainEditor implements IDomainEditor {

	private static DefaultDomainEditor INSTANCE = null;

	public static DefaultDomainEditor getInstance() {
		if (INSTANCE == null)
			INSTANCE = new DefaultDomainEditor();
		return INSTANCE;
	}

	private DefaultDomainEditor() {

	}
	
	@Override
	public boolean supportsModel(Resource model) {
		return true;
	}


	@Override
	public boolean supportsDiagramming(Resource model) {
		return false;
	}

	@Override
	public boolean supportsModel(URI modelFile) {
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
			// TODO Exception?
			return null;
		}
	}

	@Override
	public Resource getResource(IEditorPart editorPart) {
		if (editorPart instanceof IEditingDomainProvider) {
			IEditingDomainProvider editor = (IEditingDomainProvider) editorPart;
			return editor.getEditingDomain().getResourceSet().getResources()
					.get(0);
		} else {
			// TODO Exception?
			return null;
		}
	}

	@Override
	public URI copyDiagram(URI modelURI, String savePath)
			throws FileNotFoundException {
		throw new RuntimeException("Not supported");
	}

	@Override
	public String getTreeEditorID() {
		//TODO Test
		return null;
	}

	@Override
	public String getDiagramEditorID() {
		return null;
	}

	@Override
	public Boolean isTreeEditorPresent() {
		return null;
	}

	@Override
	public boolean isDiagramEditorPresent() {
		return false;
	}

	@Override
	public IEditorPart openModelInTreeEditor(URI modelURI) {
		//TODO Test
		try {
			String path=EMFStorage.uriToPath(modelURI);
			IEditorDescriptor desc = PlatformUI.getWorkbench().
			        getEditorRegistry().getDefaultEditor(path);
			IWorkbenchPage page = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage();
			return page.openEditor(new URIEditorInput(modelURI), desc.getId());
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

	
}
