package org.sidiff.integration.editor.xtext;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.integration.editor.extension.IEditorIntegration;

public class XtextEditorIntegration implements IEditorIntegration {

	public XtextEditorIntegration() {
		
	}

	@Override
	public Collection<EObject> getHighlightableElements(EObject element) {
		return Collections.emptyList();
	}

	@Override
	public boolean supportsModel(URI modelFile) {		
		return getEditorID(modelFile) != null;
	}

	@Override
	public boolean supportsModel(Resource model) {
		return false;
	}

	@Override
	public boolean supportsDiagram(URI diagramFile) {
		return false;
	}

	@Override
	public boolean supportsDiagramming(Resource model) {
		return false;
	}

	@Override
	public URI copyDiagram(URI modelURI, String savePath) throws FileNotFoundException {
		return null;
	}

	@Override
	public IEditorPart openModelInDefaultEditor(URI modelURI) {
		try {
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			IPath location = Path.fromOSString(EMFStorage.uriToFile(modelURI).getAbsolutePath());
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(location);
			IEditorInput input;
			if (file != null && file.exists()) {
				input = new FileEditorInput(file);
			} else {
				input = new URIEditorInput(modelURI);
			}
			return page.openEditor(input, getEditorID(modelURI));
		} catch (PartInitException ex) {
			return null;
		}
	}

	@Override
	public IEditorPart openDiagram(URI diagramFile) {
		return null;
	}

	@Override
	public IEditorPart openDiagramForModel(URI modelFile) {
		return null;
	}

	@Override
	public boolean supportsGMFAnimation(URI diagramFile) {
		return false;
	}

	@Override
	public String getDefaultEditorID() {
		return "*";
	}

	@Override
	public String getDiagramEditorID() {
		return null;
	}

	@Override
	public boolean isDefaultEditorPresent() {
		return true;
	}

	@Override
	public boolean isDiagramEditorPresent() {
		return false;
	}

	@Override
	public EditingDomain getEditingDomain(IEditorPart editorPart) {
		return null;
	}

	@Override
	public Resource getResource(IEditorPart editorPart) {
		return null;
	}

	@Override
	public Map<String, String> getFileExtensions() {
		return Collections.emptyMap();
	}

	private String getEditorID(URI modelURI){
		IConfigurationElement[] configs = Platform.getExtensionRegistry().getConfigurationElementsFor("org.eclipse.ui.editors");
		for (int i = 0; i < configs.length; i++) {
			IConfigurationElement config = configs[i];
			if (config.getAttribute("extensions") != null
					&& config.getAttribute("extensions").equals(modelURI.fileExtension())) {
				// We found an editor registered for the given file extension
				if (config.getAttribute("class") != null && config.getAttribute("class").contains("XtextEditor")) {
					// The editor is indeed an XtextEditor
					return config.getAttribute("id");
				}
			}
		}
		return null;
	}
}
