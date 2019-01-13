package org.sidiff.slicer.structural.configuration;

/**
 * This interface is analog to <code>org.eclipse.jface.fieldassist.IContentProposal</code>, 
 * and is used to remove the dependence on JFace.
 * @see org.sidiff.slicer.structural.configuration.impl.ConstraintProposal
 */
public interface IConstraintProposal {

	/**
	 * Return the content represented by this proposal.
	 * @return the String content represented by this proposal.
	 */
	public String getContent();

	/**
	 * Return the integer position within the contents that the cursor should be
	 * placed after the proposal is accepted.
	 *
	 * @return the zero-based index position within the contents where the
	 *         cursor should be placed after the proposal is accepted. The range
	 *         of the cursor position is from 0..N where N is the number of
	 *         characters in the contents.
	 */
	public int getCursorPosition();

	/**
	 * Return the label used to describe this proposal.
	 *
	 * @return the String label used to display the proposal. If
	 *         <code>null</code>, then the content will be displayed as the
	 *         label.
	 */
	public String getLabel();

	/**
	 * Return a description that describes this proposal.
	 *
	 * @return the String label used to further the proposal. If
	 *         <code>null</code>, then no description will be displayed.
	 */
	public String getDescription();
}