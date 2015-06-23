package org.silift.diagram.util;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.ui.IEditorPart;
import org.silift.diagram.util.actions.OpenDiagramAction;

public class DiagramUtil {
	
	private final static String EDITOR_FILES_EXTENSION = "org.silift.diagram.util.editorFiles";
	private final static String OPEN_DIAGRAM_EXTENSION = "org.silift.diagram.util.openDiagramAction";
	
	private static Map<String, String> editorFileCombinations = null;
	private static Map<String, String> modelDiagramCombinations = null;
	private static OpenDiagramAction openDiagramAction = null;

	public static IEditorPart open(URI uri) {
		if (editorFileCombinations == null) {
			readExtensions();
		}

		String fileType = uri.fileExtension();
		String editorId = editorFileCombinations.get(fileType);
		
		return openDiagramAction.open(uri, editorId);
	}
	
	public static URI findDiagramFile(URI uri) {
		if (editorFileCombinations == null) {
			readExtensions();
		}

		IFile diagramFile = findRepresentationsFile(uri);
		
		if (diagramFile == null || !diagramFile.exists()) {
			diagramFile = findLegacyDiagramFile(uri);
		}
		
		if (diagramFile != null && diagramFile.exists()) {
			return URI.createPlatformResourceURI(diagramFile.getFullPath().toOSString(), true);
		}

		return null;
	}
	
	public static IFile findRepresentationsFile(URI uri) {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		
		IFile file = null;
		IFile aird = null;
		if (uri.isPlatform()) {
			IPath location = Path.fromOSString(uri.toPlatformString(true));
			file = workspace.getRoot().getFile(location);
		} else {
			IPath location = Path.fromOSString(uri.toFileString());
			file = workspace.getRoot().getFile(location);
		}
		
		IContainer container = file.getParent();
		try {
			for (IResource resource : container.members()) {
				if (resource instanceof IFile) {
					if (((IFile) resource).getFileExtension().equals("aird")) {
						aird = (IFile) resource;
					}
				}
			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return aird;
	}
	
	public static IFile findLegacyDiagramFile(URI uri) {
		if (editorFileCombinations == null) {
			readExtensions();
		}
		
		String fileType = uri.fileExtension();
		if (modelDiagramCombinations.containsKey(fileType)) {
			URI diagramUri = URI.createURI(uri.toString().replaceAll(fileType + "$", modelDiagramCombinations.get(fileType)));
			
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			
			IFile file = null;
			if (diagramUri.isPlatform()) {
				IPath location = Path.fromOSString(diagramUri.toPlatformString(true));
				file = workspace.getRoot().getFile(location);
			} else {
				IPath location = Path.fromOSString(diagramUri.toFileString());
				file = workspace.getRoot().getFile(location);
			}
			
			if (file != null && file.exists()) {
				return file;
			}
		}
		return null;
	}
	
	private static void readExtensions() {
		editorFileCombinations = new HashMap<String, String>();
		modelDiagramCombinations = new HashMap<String, String>();
		
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] config = registry.getConfigurationElementsFor(EDITOR_FILES_EXTENSION);

		for (IConfigurationElement configElement : config) {
			for (IConfigurationElement childElement : configElement.getChildren()) {
				String modelEditorId = childElement.getAttribute("ModelEditorId");
				String modelFileType = childElement.getAttribute("ModelEditorFileType");
				
				editorFileCombinations.put(modelFileType, modelEditorId);
				
				String diagramEditorId = childElement.getAttribute("DiagramEditorId");
				if (diagramEditorId != null && diagramEditorId.length() > 0) {
					String diagramFileType = childElement.getAttribute("DiagramEditorFileType");
					
					editorFileCombinations.put(diagramFileType, diagramEditorId);
					modelDiagramCombinations.put(modelFileType, diagramFileType);
				}
			}
		}
		
		config = registry.getConfigurationElementsFor(OPEN_DIAGRAM_EXTENSION);
	
		for (IConfigurationElement configElement : config) {
			try {
				openDiagramAction = (OpenDiagramAction) configElement.createExecutableExtension("OpenDiagramAction");
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}
}
