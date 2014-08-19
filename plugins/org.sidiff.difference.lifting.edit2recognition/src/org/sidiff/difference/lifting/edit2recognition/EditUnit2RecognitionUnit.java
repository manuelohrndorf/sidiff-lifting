package org.sidiff.difference.lifting.edit2recognition;

import java.util.Collection;

import org.sidiff.difference.lifting.edit2recognition.traces.TransformationPatterns;

public abstract class EditUnit2RecognitionUnit {

	/**
	 * @return All transformation patterns of the unit transformation.
	 */
	public abstract Collection<TransformationPatterns> getPatterns();
}
