package org.sidiff.patching.ui.widgets;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.ui.dialogs.WorkspaceResourceDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.sidiff.common.ui.widgets.AbstractWidget;
import org.sidiff.common.ui.widgets.IWidgetSelection;
import org.sidiff.common.ui.widgets.IWidgetValidation;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;

public class TargetModelWidget extends AbstractWidget implements IWidgetSelection, IWidgetValidation {

	private Composite container;
	private Button modelChooseButton;
	private Text targetModelText;

	private String file;

	public TargetModelWidget() {
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
		}

		Group modelChooseGroup = new Group(container, SWT.NONE);
		{
			GridLayout grid = new GridLayout(2, false);
			grid.marginWidth = 10;
			grid.marginHeight = 10;
			modelChooseGroup.setLayout(grid);
			modelChooseGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		}
		modelChooseGroup.setText("Target model:");

		targetModelText = new Text(modelChooseGroup, SWT.BORDER);
		targetModelText.setEditable(false);
		targetModelText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		modelChooseButton = new Button(modelChooseGroup, SWT.PUSH);
		modelChooseButton.setText("Choose Model");
		modelChooseButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				IFile files[] = WorkspaceResourceDialog.openFileSelection(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
						"Target model selection", "Select the target model for patch application", false, null, null);

				if(files.length > 0) {
					file = files[0].getRawLocation().toOSString();
					targetModelText.setText(file);
					getWidgetCallback().requestValidation();
				}
			}
		});

		return container;
	}

	@Override
	public Composite getWidget() {
		return container;
	}

	public String getFilename() {
		return file;
	}

	@Override
	public boolean validate() {
		return file != null;
	}

	@Override
	public ValidationMessage getValidationMessage() {
		if (validate()) {
			return ValidationMessage.OK;
		} else {
			return new ValidationMessage(ValidationType.ERROR, "Please select a target model!");
		}
	}

	@Override
	public void addSelectionListener(SelectionListener listener) {
		if(modelChooseButton == null) {
			throw new IllegalStateException("createControl must be called first");
		}
		modelChooseButton.addSelectionListener(listener);
	}

	@Override
	public void removeSelectionListener(SelectionListener listener) {
		if(modelChooseButton == null) {
			throw new IllegalStateException("createControl must be called first");
		}
		modelChooseButton.removeSelectionListener(listener);
	}
}