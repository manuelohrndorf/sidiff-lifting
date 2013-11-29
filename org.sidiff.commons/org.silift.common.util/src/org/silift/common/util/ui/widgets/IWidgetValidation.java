package org.silift.common.util.ui.widgets;

public interface IWidgetValidation {

	/**
	 * Validate the actual state of the widget.
	 *
	 * @return <code>true</code> if everything is fine; <code>false</code> otherwise.
	 */
	public boolean validate();

	/**
	 * @return An information/error message about the actual state of the widget.
	 */
	public String getValidationMessage();
}
