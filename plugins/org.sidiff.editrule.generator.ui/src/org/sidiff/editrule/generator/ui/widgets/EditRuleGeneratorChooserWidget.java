package org.sidiff.editrule.generator.ui.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.silift.common.util.ui.widgets.IWidget;
import org.silift.common.util.ui.widgets.IWidgetInformation;
import org.silift.common.util.ui.widgets.IWidgetValidation;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;

public class EditRuleGeneratorChooserWidget implements IWidget, IWidgetValidation, IWidgetInformation{

	Group gChooser;
	Composite parent;
	Button rBtnManually;
	Button rBtnGenerate;
	
	@Override
	public String getInformationMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean validate() {
		return (rBtnGenerate.getSelection() || rBtnManually.getSelection());
	}

	@Override
	public String getValidationMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public Composite createControl(Composite parent) {
		
		this.parent = parent;
		
		gChooser = new Group(parent, SWT.SMOOTH);
		gChooser.setText("Generate Editrules?");
		gChooser.setLayout(new GridLayout(1, false));
		
		rBtnManually = new Button(gChooser, SWT.RADIO);
		rBtnManually.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		rBtnManually.setText("Define Edit Rule manually");
		
		rBtnGenerate = new Button(gChooser, SWT.RADIO);
		rBtnGenerate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		rBtnGenerate.setText("Generate Edit Rules");
		
		rBtnGenerate.setSelection(true);
		return null;
	}

	@Override
	public Composite getWidget() {
		return parent;
	}

	@Override
	public void setLayoutData(Object layoutData) {
		parent.setLayoutData(layoutData);		
	}

	public Button getrBtnManually() {
		return rBtnManually;
	}

	public void setrBtnManually(Button rBtnManually) {
		this.rBtnManually = rBtnManually;
	}

	public Button getrBtnGenerate() {
		return rBtnGenerate;
	}

	public void setrBtnGenerate(Button rBtnGenerate) {
		this.rBtnGenerate = rBtnGenerate;
	}
	
	
	
}
