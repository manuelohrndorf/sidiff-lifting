package org.sidiff.difference.lifting.edit2recognition;

import java.util.Map;

import org.eclipse.emf.henshin.model.Unit;
import org.sidiff.difference.lifting.edit2recognition.traces.TransformationPatterns;

/**
 * This interface has to be implemented by all edit- to recognition-rule transformers.
 * 
 * @author Manuel Ohrndorf
 */
public interface EditPattern2RecognitionPattern {

	/**
	 * @return All transformation (trace) patterns of the unit transformation (edit-rule -> traces).
	 */
	public Map<Unit, TransformationPatterns> getPatterns();
}
