package org.sidiff.domain.editor.simplewebmodel;

import org.eclipse.emf.refactor.examples.simpleWebModelingLanguage.SimpleWebModelingLanguagePackage;
import org.sidiff.domain.editor.extension.BasicDomainEditor;


public class SimpleWebModelDomainEditor extends BasicDomainEditor {

	
	
	public SimpleWebModelDomainEditor() {
		//TODO Set NsURI
		super("org.eclipse.emf.refactor.examples.SimpleWebModelingLanguage", 
				null, 
				SimpleWebModelingLanguagePackage.eNS_URI, "swml", null);
	}

}
