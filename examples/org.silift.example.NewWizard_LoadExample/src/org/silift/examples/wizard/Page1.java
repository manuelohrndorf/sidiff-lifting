/**
 * 
 */
package org.silift.examples.wizard;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * @author M
 *
 */
public class Page1 extends WizardPage {
	
	Composite parent;
	
	Label label;

	protected Page1(String pageName) {
		super(pageName);
		
        setTitle("Load the PI Example");
        setDescription("Here you can load the PI Example");
        
        setControl(label);
        setPageComplete(true);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		
		this.parent = parent;
		
        Composite container = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout(1, false);
        container.setLayout(layout);
        
        label = new Label(container, SWT.NONE);
        label.setText("Load the PI Example");
        
        // required to avoid an error in the system
        setControl(container);

        setPageComplete(true);
		

	}

}
