package org.sidiff.difference.lifting.edit2recognition;

import java.util.Collection;

import org.sidiff.difference.lifting.edit2recognition.traces.TransformationPatterns;

/**
 * This interface has to be implemented by all Edit- to Recognition-Rule transformers.
 * 
 * @author Manuel Ohrndorf
 */
public interface EditUnit2RecognitionUnit {

	/**
	 * @return All transformation patterns of the unit transformation.
	 */
	public Collection<TransformationPatterns> getPatterns();
}
