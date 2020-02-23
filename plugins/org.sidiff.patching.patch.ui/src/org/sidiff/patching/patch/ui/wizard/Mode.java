package org.sidiff.patching.patch.ui.wizard;

public enum Mode {
	PATCH {
		@Override
		public String toString() {
			return "Patch";
		}
	},
	ASYMMETRIC_DIFFERENCE {
		@Override
		public String toString() {
			return "Asymmetric Difference";
		}
	}
}
