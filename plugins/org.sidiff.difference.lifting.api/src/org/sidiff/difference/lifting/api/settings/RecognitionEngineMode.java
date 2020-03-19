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
	NO_LIFTING {
		@Override
		public String toString() {
			return "No Semantic Lifting (Operation Detection)";
		}
	},

	/**
	 * Enable the operation detection (Lifting).
	 */
	LIFTING {
		@Override
		public String toString() {
			return "Semantic Lifting (Operation Detection)";
		}
	},

	/**
	 * Post processed (remove overlapping Semantic-Change-Sets) operation
	 * detection (Lifting).
	 */
	LIFTING_AND_POST_PROCESSING {
		@Override
		public String toString() {
			return "Semantic Lifting and Post Processing (Default)";
		}
	};
}