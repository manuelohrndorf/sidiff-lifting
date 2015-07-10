package org.sidiff.domain.editor.simplewebmodel;

import org.sidiff.domain.editor.extension.BasicDomainEditor;


public class SimpleWebModelDomainEditor extends BasicDomainEditor {

	
	
	public SimpleWebModelDomainEditor() {
		//TODO Set NsURI
		super("simplewebmodel.presentation.SimplewebmodelEditorID", 
				"simplewebmodel.diagram.part.SimplewebmodelDiagramEditorID", 
				"simplewebmodel", "simplewebmodel", "simplewebmodel_diagram");
	}

}
