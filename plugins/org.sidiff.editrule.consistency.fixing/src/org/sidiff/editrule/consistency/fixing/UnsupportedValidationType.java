package org.sidiff.editrule.consistency.fixing;

public class UnsupportedValidationType extends Exception {

	private static final long serialVersionUID = -3564440310153830723L;

	public UnsupportedValidationType(String type) {
		super("Fixes for the validation type " + type + " are not supported at the moment.");
	}
}
