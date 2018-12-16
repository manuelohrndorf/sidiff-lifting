package org.sidiff.ecore.modifieddetector;

import java.io.IOException;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.annotation.IAnnotation;
import org.sidiff.common.io.IOUtil;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.common.xml.XMLParser;
import org.sidiff.conflicts.annotation.AnnotationModifiedDetector;
import org.sidiff.ecore.modifieddetector.internal.Activator;

public class EcoreModifiedDetector extends AnnotationModifiedDetector {

	private static final String CONFIG_PATH = "/config/org.sidiff.ecore.core.annotations.xml";

	@Override
	public void initAnnotator(IAnnotation annotator, Resource model) throws IOException {
		LogUtil.log(LogEvent.DEBUG, "Config: " + CONFIG_PATH);	
		
		// Configure AnnotationService
		annotator.configure(XMLParser.parseStream(IOUtil.openInputStream(Activator.PLUGIN_ID, CONFIG_PATH)));
	}

	@Override
	public String getName() {
		return "Modified Detector for Ecore";
	}

	@Override
	public String getDocumentType() {
		return EcorePackage.eNS_URI;
	}

	@Override
	public boolean canHandle(String docType) {
		return docType.equals(getDocumentType());
	}
}
