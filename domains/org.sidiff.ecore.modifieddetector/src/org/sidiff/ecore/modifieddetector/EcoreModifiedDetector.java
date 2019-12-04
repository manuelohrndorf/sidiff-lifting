package org.sidiff.ecore.modifieddetector;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.annotation.IAnnotation;
import org.sidiff.common.io.IOUtil;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.common.xml.XMLParser;
import org.sidiff.conflicts.annotation.AnnotationModifiedDetector;

public class EcoreModifiedDetector extends AnnotationModifiedDetector {

	private static final String CONFIG_PATH = "/config/org.sidiff.ecore.core.annotations.xml";
	private static final String PLUGIN_ID = "org.sidiff.ecore.modifieddetector";

	@Override
	public void initAnnotator(IAnnotation annotator, Resource model) throws IOException {
		LogUtil.log(LogEvent.DEBUG, "Config: " + CONFIG_PATH);	
		
		// Configure AnnotationService
		annotator.getConfiguration().setOption("configDocument", XMLParser.parseStream(IOUtil.openInputStream(PLUGIN_ID, CONFIG_PATH)));
	}

	@Override
	public Set<String> getDocumentTypes() {
		return Collections.singleton(EcorePackage.eNS_URI);
	}
}
