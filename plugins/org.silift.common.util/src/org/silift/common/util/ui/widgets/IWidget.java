package org.silift.common.util.ui.widgets;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public interface IWidget {
	/**
	 * Create/Initialize the widget controls.
	 *
	 * @param parent
	 *            The widget parent container.
	 * @return The top level container.
	 */
	public Composite createControl(Composite parent, WizardPage page);
	
	public Composite createControl(Composite parent);

	/**
	 * @return The top level container or <code>null</code> if has not yet been created.
	 */
	public Composite getWidget();

	/**
	 * Set the widget layout data.
	 *
	 * @param layoutData
	 *            The layout of the top level container.
	 */
	public void setLayoutData(Object layoutData);


}
