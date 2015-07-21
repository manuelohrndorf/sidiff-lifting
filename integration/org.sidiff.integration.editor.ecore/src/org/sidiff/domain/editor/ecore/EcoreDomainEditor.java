package org.sidiff.domain.editor.ecore;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecoretools.diagram.part.EcoreDiagramEditor;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ui.IEditorPart;
import org.sidiff.domain.editor.extension.BasicDomainEditor;

public class EcoreDomainEditor extends BasicDomainEditor {

	public EcoreDomainEditor() {
		super("org.eclipse.emf.ecore.presentation.EcoreEditorID",
				"org.eclipse.emf.ecoretools.diagram.part.EcoreDiagramEditorID",
				EcorePackage.eINSTANCE.getNsURI(), "ecore", "ecorediag");
	}

	@Override
	public boolean supportsGMFAnimation(URI diagramFile) {
		return diagramFileExt.equals(diagramFile.fileExtension().toLowerCase());
	}


	@Override
	public EditingDomain getEditingDomain(IEditorPart editorPart) {
		if (editorPart instanceof org.eclipse.emf.ecoretools.diagram.part.EcoreDiagramEditor) {
			EcoreDiagramEditor editor = (EcoreDiagramEditor) editorPart;
			return editor.getEditingDomain();
		} else {
			// TODO Exception?
			return null;
		}
	}

	@Override
	public Resource getResource(IEditorPart editorPart) {
		if (editorPart instanceof org.eclipse.emf.ecoretools.diagram.part.EcoreDiagramEditor) {
			EcoreDiagramEditor editor = (EcoreDiagramEditor) editorPart;
			return editor.getDiagram().getElement().eResource();
		} else {
			// TODO Exception?
			return null;
		}
	}

}
