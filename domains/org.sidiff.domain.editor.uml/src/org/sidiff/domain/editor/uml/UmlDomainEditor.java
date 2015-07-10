package org.sidiff.domain.editor.uml;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.papyrus.editor.PapyrusMultiDiagramEditor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.uml2.uml.UMLPackage;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.domain.editor.extension.BasicDomainEditor;
import org.silift.common.util.emf.EMFStorage;

public class UmlDomainEditor extends BasicDomainEditor {

	private final String NOTATION_FILE_EXT="notation";
	
	public UmlDomainEditor() {
		super("org.eclipse.uml2.uml.editor.presentation.UMLEditorID", 
				"org.eclipse.papyrus.infra.core.papyrusEditor", 
				UMLPackage.eINSTANCE.getNsURI(),"uml","di");
	}


	
	@Override
	protected URI[] getDiagramFiles(URI modelFile) {
		// TODO Test
		if (modelFileExt.equals(modelFile.fileExtension().toLowerCase())) {
			return new URI[]{URI.createURI(modelFile.toString().replaceAll(
					modelFileExt + "$", diagramFileExt)),URI.createURI(modelFile.toString().replaceAll(
							modelFileExt + "$", NOTATION_FILE_EXT))};
		}
		return new URI[0];
	}


	@Override
	public boolean supportsGMFAnimation(URI diagramFile) {
		return false;
	}

	@Override
	public EditingDomain getEditingDomain(IEditorPart editorPart) {
		if (editorPart instanceof PapyrusMultiDiagramEditor) {
			PapyrusMultiDiagramEditor editor = (PapyrusMultiDiagramEditor) editorPart;
			return editor.getEditingDomain();
		} else {
			return super.getEditingDomain(editorPart);
		}
	}

	@Override
	public Resource getResource(IEditorPart editorPart) {
		if (editorPart instanceof PapyrusMultiDiagramEditor) {
			PapyrusMultiDiagramEditor editor = (PapyrusMultiDiagramEditor) editorPart;
			return editor.getEditingDomain().getResourceSet().getResources()
					.get(0);
		} else {
			return super.getResource(editorPart);
		}
	}
}
