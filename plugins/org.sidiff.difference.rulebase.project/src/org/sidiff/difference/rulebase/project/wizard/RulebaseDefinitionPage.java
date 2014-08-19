package org.sidiff.difference.rulebase.project.wizard;

import java.util.MissingResourceException;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.sidiff.difference.rulebase.project.Activator;
import org.sidiff.difference.rulebase.provider.RuleBaseEditPlugin;

public class RulebaseDefinitionPage extends WizardPage {

	private static final int LABEL_WIDTH = 60;

	private Group rulebaseGroup = null;

	private Text rulebaseNameText = null;

	public RulebaseDefinitionPage(String pageId) {
		super(pageId);
		this.setTitle("Define Rulebase Options");
	}	

	public String getRulebaseName() {
		return rulebaseNameText.getText().trim();
	}	

	public void setRulebaseName(String name) {
		if (rulebaseNameText != null) {
			rulebaseNameText.setText(name);
			rulebaseNameText.setEnabled(false);
			rulebaseGroup.setEnabled(false);
		}
	}

	@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		{
			GridLayout layout = new GridLayout(1, false);
			composite.setLayout(layout);

			GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
			composite.setLayoutData(data);
		}

		rulebaseGroup = new Group(composite, SWT.NONE);
		{
			GridLayout layout = new GridLayout(1, false);
			rulebaseGroup.setLayout(layout);

			GridData data = new GridData(SWT.FILL, SWT.CENTER, true, false);
			rulebaseGroup.setLayoutData(data);

			rulebaseGroup.setText("Rulebase");
		}

		Composite rulebaseNameComposite = new Composite(rulebaseGroup, SWT.NONE);
		{
			GridLayout layout = new GridLayout(2, false);
			rulebaseNameComposite.setLayout(layout);

			GridData data = new GridData(SWT.FILL, SWT.CENTER, true, false);
			rulebaseNameComposite.setLayoutData(data);
		}
		Label rulebaseNameLabel = new Label(rulebaseNameComposite, SWT.LEFT);
		{
			rulebaseNameLabel.setText("Name:");

			GridData data = new GridData(SWT.LEFT, SWT.CENTER, false, false);
			data.widthHint = LABEL_WIDTH;
			rulebaseNameLabel.setLayoutData(data);
		}

		rulebaseNameText = new Text(rulebaseNameComposite, SWT.BORDER);
		{
			GridData data = new GridData(SWT.FILL, SWT.CENTER, true, false);
			rulebaseNameText.setLayoutData(data);

			rulebaseNameText.addModifyListener(validator);
		}


		setPageComplete(validatePage());
		setControl(composite);
	}	

	protected ModifyListener validator = new ModifyListener() {
		@Override
		public void modifyText(ModifyEvent e) {
			setPageComplete(validatePage());
		}
	};

	protected boolean validatePage() {

		// Test name field rulebase
		if (rulebaseNameText.getText().isEmpty() || rulebaseNameText.getText().contains(" ")) {
			setErrorMessage("Please specifiy a valid rulebasename!");
			return false;
		}		

		setErrorMessage(null);
		return true;
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
	}

	/**
	 * Returns the label for the specified type name.
	 */
	protected String getLabel(String typeName) {
		try {
			return RuleBaseEditPlugin.INSTANCE.getString("_UI_" + typeName + "_type");
		} catch (MissingResourceException mre) {
			Activator.INSTANCE.log(mre);
		}
		return typeName;
	}
}
