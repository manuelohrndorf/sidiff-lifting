package org.sidiff.integration.editor.papyrus;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.editor.PapyrusMultiDiagramEditor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.uml2.uml.UMLPackage;
import org.sidiff.integration.editor.extension.BasicEditorIntegration;

public class PapyrusEditorIntegration extends BasicEditorIntegration {

	private final String NOTATION_FILE_EXT="notation";
	
	public PapyrusEditorIntegration() {
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
