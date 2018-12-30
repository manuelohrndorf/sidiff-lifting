package org.sidiff.editrule.generator.ui.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.sidiff.common.ui.widgets.AbstractWidget;
import org.sidiff.common.ui.widgets.IWidgetValidation;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;

public class EditRuleGeneratorChooserWidget extends AbstractWidget implements IWidgetValidation {

	private Group gChooser;
	private Button rBtnManually;
	private Button rBtnGenerate;

	@Override
	public Composite createControl(Composite parent) {
		gChooser = new Group(parent, SWT.SMOOTH);
		gChooser.setText("Generate Edit Rules?");
		gChooser.setLayout(new GridLayout(1, false));

		rBtnManually = new Button(gChooser, SWT.RADIO);
		rBtnManually.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		rBtnManually.setText("Define Edit Rules manually");
		rBtnManually.setSelection(true);
		rBtnManually.addSelectionListener(SelectionListener.widgetSelectedAdapter(e -> {
			getWidgetCallback().requestValidation();
			propagateEnabledState();
		}));

		rBtnGenerate = new Button(gChooser, SWT.RADIO);
		rBtnGenerate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		rBtnGenerate.setText("Generate Edit Rules");
		rBtnGenerate.setSelection(false);
		rBtnGenerate.addSelectionListener(SelectionListener.widgetSelectedAdapter(e -> {
			getWidgetCallback().requestValidation();
			propagateEnabledState();
		}));
		
		return gChooser;
	}

	@Override
	public Composite getWidget() {
		return gChooser;
	}

	public boolean isGenerate() {
		return rBtnGenerate.getSelection();
	}

	@Override
	public boolean areDependentsEnabled() {
		return super.areDependentsEnabled() && isGenerate();
	}

	@Override
	public boolean validate() {
		return true;
	}

	@Override
	public ValidationMessage getValidationMessage() {
		if(rBtnManually.getSelection()) {
			return new ValidationMessage(ValidationType.INFORMATION, "Press Finish to define Edit Rules manually");
		}
		return ValidationMessage.OK;
	}
}
