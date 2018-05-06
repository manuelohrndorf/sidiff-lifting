package org.sidiff.integration.preferences.pages;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbenchPropertyPage;

/**
 * Preference MainPage that refers to Subpages
 * @author Daniel Roedder, Robert Müller
 */
public class MainPage extends PreferencePage implements IWorkbenchPreferencePage, IWorkbenchPropertyPage {

	/**
	 * Creates the Content of the MainPage
	 * @param parent Parent-Composite
	 * @return the control
	 */
	@Override
	protected Control createContents(Composite parent) {
		super.noDefaultAndApplyButton();
		Label label = new Label(parent, SWT.NONE);
		label.setText("Expand the tree to edit preferences for a specific feature.");
		return label;
	}

	/**
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	@Override
	public void init(IWorkbench workbench) {
	}

	/**
	 * Superclass method, not needed here
	 * @see org.eclipse.ui.IWorkbenchPropertyPage#getElement()
	 */
	@Override
	public IAdaptable getElement() {
		return null;
	}

	/**
	 * Superclass method, not needed here
	 * @see org.eclipse.ui.IWorkbenchPropertyPage#setElement(org.eclipse.core.runtime.IAdaptable)
	 */
	@Override
	public void setElement(IAdaptable element) {
	}
}
