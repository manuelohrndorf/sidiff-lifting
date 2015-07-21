package org.sidiff.integration.editor.xtext;

import java.io.FileNotFoundException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.ui.IEditorPart;
import org.sidiff.integration.editor.extension.AbstractEditorIntegration;


public class XtextEditorIntegration extends AbstractEditorIntegration {
	
	
	public XtextEditorIntegration() {
		//TODO 
		super(null,	"xtext editor id ? ...");

	}

	@Override
	public EObject getHighlightableElement(EObject element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supportsModel(URI modelFile) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supportsModel(Resource model) {
		// TODO 
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
	public URI copyDiagram(URI modelURI, String savePath)
			throws FileNotFoundException {		
		return null;
	}

	@Override
	public IEditorPart openModelInDefaultEditor(URI modelURI) {
		// TODO Auto-generated method stub
		return null;
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

}
