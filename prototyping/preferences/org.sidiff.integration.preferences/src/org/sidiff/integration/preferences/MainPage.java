package org.sidiff.integration.preferences;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbenchPropertyPage;

/**
 * Preference MainPage that refers to Subpages
 * @author Daniel Roedder
 */
public class MainPage extends PreferencePage implements IWorkbenchPreferencePage, IWorkbenchPropertyPage {

	/**
	 * The textfield for the text on the page
	 */
	private Text textField;
	
	/**
	 * Creates the Content of the MainPage
	 * @param parent Parent-Composite
	 * @return null, the {@link Control} is not needed elsewhere
	 */
	@Override
	protected Control createContents(Composite parent) {
		super.noDefaultAndApplyButton();
		textField = new Text(parent, SWT.INHERIT_FORCE);
		textField.setBackground(parent.getBackground());
		textField.setText("Expand the tree to view options");
		return null;
	}

	/**
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
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
	public void setElement(IAdaptable element) {}
}
