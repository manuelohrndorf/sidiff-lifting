package org.sidiff.vcmsintegration.editor.ecoretools;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramEditorInput;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.sidiff.vcmsintegration.editor.extension.BasicEditorIntegration;

public class EcoreToolsEditorIntegration extends BasicEditorIntegration {

	public EcoreToolsEditorIntegration() {
		super("org.eclipse.emf.ecore.presentation.EcoreEditorID",
				"org.eclipse.emf.ecoretools.diagram.part.EcoreDiagramEditorID", EcorePackage.eINSTANCE.getNsURI(),
				"ecore", "ecorediag");
	}

	@Override
	public boolean supportsGMFAnimation(URI diagramFile) {
		return diagramFileExt.equals(diagramFile.fileExtension().toLowerCase());
	}

	@Override
	public Resource getResource(IEditorPart editorPart) {
		IEditorInput input = editorPart.getEditorInput();
		if (input instanceof IDiagramEditorInput) {
			return ((IDiagramEditorInput) input).getDiagram().getElement().eResource();
		}
		return super.getResource(editorPart);
	}
}
