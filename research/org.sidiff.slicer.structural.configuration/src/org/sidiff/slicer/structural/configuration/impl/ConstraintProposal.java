package org.sidiff.slicer.structural.configuration.impl;

import org.sidiff.slicer.structural.configuration.IConstraintProposal;

/**
 * A basic immutable constraint proposal.
 * @author Robert Müller
 */
public class ConstraintProposal implements IConstraintProposal {

	public ConstraintProposal(String content, int cursorPosition, String label, String description) {
		this.content = content;
		this.cursorPosition = cursorPosition;
		this.label = label;
		this.description = description;
	}

	private String content;
	private int cursorPosition;
	private String label;
	private String description;
	
	@Override
	public String getContent() {
		return content;
	}

	@Override
	public int getCursorPosition() {
		return cursorPosition;
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public String getDescription() {
		return description;
	}

}
