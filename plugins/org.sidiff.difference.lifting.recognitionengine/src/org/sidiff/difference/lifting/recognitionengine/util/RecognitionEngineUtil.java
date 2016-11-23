package org.sidiff.difference.lifting.recognitionengine.util;

import org.sidiff.difference.lifting.recognitionengine.IRecognitionEngine;
import org.sidiff.difference.lifting.recognitionengine.RecognitionEngineSetup;
import org.sidiff.difference.lifting.recognitionengine.impl.RecognitionEngine;

public class RecognitionEngineUtil {

	public static IRecognitionEngine getDefaultRecognitionEngine(RecognitionEngineSetup setup) {
		return new RecognitionEngine(setup);
	}
}
