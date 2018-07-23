package org.sidiff.integration.preferences.ui.pages;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

/**
 * Preference MainPage that refers to Subpages
 * @author Daniel Roedder, Robert Müller
 */
public class MainPage extends PropertyAndPreferencePage {

	/**
	 * Creates the Content of the MainPage
	 * @param parent Parent-Composite
	 * @return the control
	 */
	@Override
	protected Control doCreateContents(Composite parent) {
		super.noDefaultAndApplyButton();
		Label label = new Label(parent, SWT.NONE);
		label.setText("Expand the tree to edit preferences for a specific feature.");
		return label;
	}

	@Override
	protected void reloadPreferences() {
	}

	@Override
	protected void defaultPreferences() {
	}

	@Override
	protected void savePreferences() {
	}

	@Override
	protected void validatePreferences() {
	}

	@Override
	protected String getHelpContextId() {
		return "org.sidiff.integration.preferences.index";
	}
}
