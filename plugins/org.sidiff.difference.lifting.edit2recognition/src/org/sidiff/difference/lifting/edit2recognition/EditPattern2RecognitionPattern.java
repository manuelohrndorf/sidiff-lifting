package org.sidiff.difference.lifting.edit2recognition;

import java.util.Collection;

import org.sidiff.difference.lifting.edit2recognition.traces.TransformationPatterns;

/**
 * This interface has to be implemented by all edit- to recognition-rule transformers.
 * 
 * @author Manuel Ohrndorf
 */
public interface EditPattern2RecognitionPattern {

	/**
	 * @return All transformation (trace) patterns of the unit transformation.
	 */
	public Collection<TransformationPatterns> getPatterns();
}
