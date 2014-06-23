package org.sidiff.patching.ui.widgets;

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
import org.sidiff.difference.lifting.settings.ISettingsChangedListener;
import org.sidiff.difference.lifting.settings.Settings;
import org.silift.common.util.ui.widgets.IWidget;
import org.silift.common.util.ui.widgets.IWidgetSelection;
import org.silift.common.util.ui.widgets.IWidgetValidation;

public class TargetModelWidget implements IWidget, IWidgetSelection, IWidgetValidation, ISettingsChangedListener {
	
	private Settings settings;
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
		
		targetModelText = new Text(modelChooseGroup, SWT.NONE);
		targetModelText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		modelChooseButton = new Button(modelChooseGroup, SWT.PUSH);
		modelChooseButton.setText("Choose Model");
//		modelChooseButton.setFont(JFaceResources.getDialogFont());
		modelChooseButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				file = WorkspaceResourceDialog.openFileSelection(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
						"Target model selection", "Select the target model for patch application", false, null,null)[0].getRawLocation().toOSString();
				if (file != null && file != "") {
					targetModelText.setText(file);
				}
				else{
					targetModelText.setText("");
				}
			}
		});

		return container;
	}

	@Override
	public Composite getWidget() {
		return container;
	}

	@Override
	public void setLayoutData(Object layoutData) {
		container.setLayoutData(layoutData);
	}

	public String getFilename() {
		if (validate()) {
			return file;
		} else {
			return null;
		}
	}

	@Override
	public boolean validate() {
		if (file != null && file != ""){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String getValidationMessage() {
		if (validate()) {
			return "";
		} else {
			return "Please select a target model!";
		}
	}

	@Override
	public void addSelectionListener(SelectionListener listener) {
		if(modelChooseButton == null){
			throw new RuntimeException("Create controls first!");
		}
		modelChooseButton.addSelectionListener(listener);
	}

	@Override
	public void removeSelectionListener(SelectionListener listener) {
		if(modelChooseButton != null)
			modelChooseButton.removeSelectionListener(listener);
	}

	@Override
	public void settingsChanged(Enum<?> item) {
	}

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
		this.settings.addSettingsChangedListener(this);
	}
}