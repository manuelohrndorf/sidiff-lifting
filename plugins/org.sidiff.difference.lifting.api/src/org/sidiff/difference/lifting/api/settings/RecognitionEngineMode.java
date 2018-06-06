package org.sidiff.difference.lifting.api.settings;

/**
 * <p>
 * Triggering of the Recognition-Engine pipeline.
 * </p>
 * <ul>
 * <li><code>NO_LIFTING</code>: Disable the operation detection (Lifting).</li>
 * <li><code>LIFTING</code>: Enable the operation detection (Lifting).</li>
 * <li><code>LIFTING_AND_POST_PROCESSING:</code> Post processed (remove
 * overlapping Semantic-Change-Sets) operation detection (Lifting).</li>
 * </ul>
 */
public enum RecognitionEngineMode {

	/**
	 * Disable the operation detection (Lifting).
	 */
	NO_LIFTING,

	/**
	 * Enable the operation detection (Lifting).
	 */
	LIFTING,

	/**
	 * Post processed (remove overlapping Semantic-Change-Sets) operation
	 * detection (Lifting).
	 */
	LIFTING_AND_POST_PROCESSING
}