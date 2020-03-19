package org.sidiff.patching.validation;

public enum ValidationMode {

	NO_VALIDATION {
		@Override
		public String toString() {
			return "No Validation";
		}
	},
	MODEL_VALIDATION {
		@Override
		public String toString() {
			return "Model Validation";
		}
	},
	ITERATIVE_VALIDATION {
		@Override
		public String toString() {
			return "Iterative Validation";
		}
	}
}