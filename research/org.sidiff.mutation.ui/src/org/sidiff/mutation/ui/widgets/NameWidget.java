package org.sidiff.mutation.ui.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.silift.common.util.ui.widgets.IWidget;
import org.silift.common.util.ui.widgets.IWidgetModification;
import org.silift.common.util.ui.widgets.IWidgetSelection;
import org.silift.common.util.ui.widgets.IWidgetValidation;
import org.silift.common.util.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;

public class NameWidget implements IWidget, IWidgetModification,
		IWidgetValidation {

	private static final String DEFAULT_TEXT="Name";
	private static final String DEFAULT_ERRORMESSAGE = "Enter a name";
	private static final String DEFAULT_WARNINGMESSAGE = "No name provided";

	private String text;
	private String warningMessage;
	private String errorMessage;
	private boolean required;
	private Composite container;
	private ValidationMessage message;
	private Text nameText;

	public NameWidget(boolean required, String text, String errorMessage,
			String warningMessage) {
		this.required = required;
		this.text=(text != null ? text : DEFAULT_TEXT);
		this.warningMessage = (warningMessage != null ? warningMessage
				: DEFAULT_WARNINGMESSAGE);
		this.errorMessage = (errorMessage != null ? errorMessage
				: DEFAULT_ERRORMESSAGE);
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public Composite createControl(Composite parent) {

		container = new Composite(parent, SWT.NONE);
		{
			GridLayout grid = new GridLayout(2, false);
			grid.marginWidth = 0;
			grid.marginHeight = 0;
			container.setLayout(grid);
			container.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		}

		Label nameLabel = new Label(container, SWT.NONE);
		nameLabel.setText(text);
		nameText = new Text(container, SWT.SINGLE | SWT.BORDER);
		nameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		return container;

	}

	@Override
	public boolean validate() {
		if (nameText == null) {
			message = new ValidationMessage(ValidationType.ERROR,
					"(intenral) Components not created");
			return false;
		}
		if (nameText.getText().trim().isEmpty()) {
			if (required) {
				message = new ValidationMessage(ValidationType.ERROR,
						errorMessage);
				return false;
			}
			message = new ValidationMessage(ValidationType.WARNING,
					warningMessage);
		} else {
			message = new ValidationMessage(ValidationType.OK, "");
		}
		return true;
	}
	
	public void setEnabled(boolean enabled){
		nameText.setEnabled(enabled);
	}

	@Override
	public ValidationMessage getValidationMessage() {
		return message;
	}

	@Override
	public Composite getWidget() {
		return null;
	}

	@Override
	public void setLayoutData(Object layoutData) {
	}

	@Override
	public void addModifyListener(ModifyListener listener) {
		if (nameText == null)
			throw new RuntimeException("Create controls first!");
		nameText.addModifyListener(listener);
	}

	@Override
	public void removeModifyListener(ModifyListener listener) {
		if (nameText == null)
			return;
		nameText.removeModifyListener(listener);
	}

	public String getName() {
		if (nameText == null)
			throw new RuntimeException("Create controls first!");
		return nameText.getText();
	}
}