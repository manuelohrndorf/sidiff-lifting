package org.silift.common.util.ui.widgets;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.ProgressBar;

public class ProgressBarWidget implements IWidget{

	
	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public Composite createControl(Composite parent){
		
		ProgressBar monitor = new ProgressBar(parent, 0);
		
		
		return parent;
		}

	
	public Composite createControl(Composite parent, WizardPage page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Composite getWidget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLayoutData(Object layoutData) {
		// TODO Auto-generated method stub
		
	}


}
