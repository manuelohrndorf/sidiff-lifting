package org.sidiff.patching.ui.dialog;

import java.io.File;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

public class ChooseModelDialog extends Dialog {

	private static final int MODELA_ID = 100;
	private final int defaultReliability = 80;

	private Button okButton;
	private Text modelAtext;
	private String file;
	private Scale scale;
	private Spinner spinner;
	private float reliability;

	public ChooseModelDialog(Shell parentShell) {
		super(parentShell);
		setBlockOnOpen(true);
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		okButton = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
		updateButtons();
	}

	protected Control createDialogArea(Composite parent) {
		// Run super.
		Composite composite = (Composite) super.createDialogArea(parent);
		composite.setFont(parent.getFont());

		createModelInputBox(composite);

		// Restore the last state
		// restoreWidgetValues();

		applyDialogFont(composite);

		// Return results.
		return composite;
	}

	private void createModelInputBox(Composite composite) {
		GridData spec = new GridData(GridData.FILL_BOTH);
		spec.widthHint = 400;
		spec.heightHint = 80;
		composite.setLayoutData(spec);

		GridLayout layout = new GridLayout(3, false);
		composite.setLayout(layout);

		Label label = new Label(composite, SWT.NONE);
		label.setText("Min Reliability:");

		scale = new Scale(composite, SWT.BORDER);
		scale.setMaximum(100);
		scale.setIncrement(5);
		scale.setSelection(defaultReliability);
		spec = new GridData(GridData.FILL_HORIZONTAL);
		scale.setLayoutData(spec);

		spinner = new Spinner(composite, SWT.NONE);
		spinner.setMaximum(100);
		spinner.setSelection(defaultReliability);

		scale.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				int perspectiveValue = scale.getSelection() + scale.getMinimum();
				spinner.setSelection(perspectiveValue);
			}
		});

		label = new Label(composite, SWT.NONE);
		label.setText("Model A':");
		modelAtext = new Text(composite, SWT.NONE);
		spec = new GridData(GridData.FILL_HORIZONTAL);
		modelAtext.setLayoutData(spec);
		Button button = new Button(composite, SWT.PUSH);
		button.setText("Choose Model");
		button.setFont(JFaceResources.getDialogFont());
		button.setData(new Integer(MODELA_ID));
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				buttonPressed(((Integer) event.widget.getData()).intValue());
			}
		});
	}

	private void updateButtons() {
		if (okButton != null) {
			boolean aExists = new File(modelAtext.getText()).exists();
			okButton.setEnabled(aExists);
		}
	}

	protected void buttonPressed(int buttonId) {
		switch (buttonId) {
		case MODELA_ID: {
			FileDialog dialog = new FileDialog(getShell(), SWT.OPEN);
			String file;
			if ((file = dialog.open()) != null) {
				modelAtext.setText(file);
			}
			break;
		}
		case IDialogConstants.OK_ID: {
			file = modelAtext.getText();
			reliability = spinner.getSelection() / 100f;
			break;
		}
		default:
			break;
		}
		updateButtons();
		super.buttonPressed(buttonId);
	}

	public String getFile() {
		return file;
	}

	public float getReliability() {
		return reliability;
	}

}
