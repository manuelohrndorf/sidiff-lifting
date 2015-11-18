package org.sidiff.ecore.modifieddetector;

import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.core.annotation.AnnotationService;
import org.sidiff.modifieddetector.annotation.AnnotationModifiedDetector;

public class EcoreModifiedDetector extends AnnotationModifiedDetector {

	@Override
	public void initAnnotator(AnnotationService annotator) {
		
		String ANN_CFG_NAME = "platform:/plugin/org.sidiff.ecore.modifieddetector/config/"
				+ "org.sidiff.ecore.core.annotations.xml";
		
		LogUtil.log(LogEvent.DEBUG, "Config: " + ANN_CFG_NAME);

		// Configure AnnotationService
		annotator.configure(ANN_CFG_NAME);
		
	}


}
