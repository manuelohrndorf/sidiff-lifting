package org.sidiff.patching.ui.dialog;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class CopyLayoutDialog extends Dialog {

	private static final int SWITCH_DIAGRAMS = 100;
	private List<IFile> files;
	private Label file1label;
	private Label file0label;

	public CopyLayoutDialog(Shell parentShell, List<IFile> files) {
		super(parentShell);
		setBlockOnOpen(true);
		this.files = files;
	}
	
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}
	
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		composite.setFont(parent.getFont());

		createDiagramOrderBox(composite);

		applyDialogFont(composite);

		return composite;
	}

	private void createDiagramOrderBox(Composite composite) {
		GridData spec = new GridData(GridData.FILL_BOTH);
		spec.widthHint = 400;
		spec.heightHint = 80;
		composite.setLayoutData(spec);

		GridLayout layout = new GridLayout(2, false);
		composite.setLayout(layout);

		Label label = new Label(composite, SWT.NONE);
		label.setText("From:");
		
		file0label = new Label(composite, SWT.NONE);
		
		label = new Label(composite, SWT.NONE);
		label.setText("To:");
		
		file1label = new Label(composite, SWT.NONE);
		
		refreshLabels();

		Button button = new Button(composite, SWT.PUSH);
		GridData layoutData = new GridData();
		layoutData.horizontalSpan=2;
		button.setLayoutData(layoutData);
		button.setText("Switch");
		button.setFont(JFaceResources.getDialogFont());
		button.setData(new Integer(SWITCH_DIAGRAMS));
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				buttonPressed(((Integer) event.widget.getData()).intValue());
			}
		});
	}
	
	private void refreshLabels() {
		file0label.setText(files.get(0).getName());
		file1label.setText(files.get(1).getName());
	}
	
	protected void buttonPressed(int buttonId) {
		switch (buttonId) {
		case SWITCH_DIAGRAMS: {
			Collections.reverse(files);
			refreshLabels();
			break;
		}
		default:
			break;
		}
		super.buttonPressed(buttonId);
	}
	
	public List<IFile> getFiles() {
		return files;
	}

}
