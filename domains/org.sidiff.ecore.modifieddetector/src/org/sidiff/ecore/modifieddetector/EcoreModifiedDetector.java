package org.sidiff.ecore.modifieddetector;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.sidiff.annotation.IAnnotation;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.common.xml.XMLParser;
import org.sidiff.modifieddetector.annotation.AnnotationModifiedDetector;

public class EcoreModifiedDetector extends AnnotationModifiedDetector {

	@Override
	public void initAnnotator(IAnnotation annotator) throws FileNotFoundException {
		
		String ANN_CFG_NAME = "platform:/plugin/org.sidiff.ecore.modifieddetector/config/"
				+ "org.sidiff.ecore.core.annotations.xml";
		
		LogUtil.log(LogEvent.DEBUG, "Config: " + ANN_CFG_NAME);	
		

		// Configure AnnotationService
		annotator.init(XMLParser.parseStream(new FileInputStream(ANN_CFG_NAME)));
		
	}



}
