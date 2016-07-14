package org.sidiff.editrule.generator.serge.ui.wizards;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.sidiff.editrule.generator.serge.settings.SergeSettings;

/**
 * The "New" wizard page allows setting the container for the new file as well
 * as the file name. The page will only accept file name without the extension
 * OR with the extension that matches the expected one (mpe).
 */

public class SergeWizardPage2 extends WizardPage {

	private ISelection selection;
	private SergeSettings settings;

	/**
	 * Constructor for the second SergeWizardPage.
	 * 
	 * @param pageName
	 */
	public SergeWizardPage2(ISelection selection, SergeSettings settings) {
		super("wizardPage");
		setTitle("SERGe CPEO Generation Wizard");
		setDescription("Please define advanced settings for the generation.");
		this.selection = selection;
		this.settings = settings;

	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		container.setLayout(layout);
		layout.verticalSpacing = 9;
		initialize();
		//dialogChanged();
		setControl(container);	

		// TransformationGroup
		Group group_trafoSettings = new Group(container, SWT.SHADOW_IN);
		group_trafoSettings.setLayout(new GridLayout(2, false));
		GridData gd_grpTransformationSettings = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		gd_grpTransformationSettings.widthHint = 424;
		group_trafoSettings.setLayoutData(gd_grpTransformationSettings);
		group_trafoSettings.setText("Transformation Settings");

		// Checkbox delete manual transformations
		final Button checkbox_deleteManualTrafos = new Button(group_trafoSettings, SWT.CHECK);
		checkbox_deleteManualTrafos.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		checkbox_deleteManualTrafos.setText("Delete existing, manually created transformations ");
		checkbox_deleteManualTrafos.setSelection(settings.isDeleteManualTransformations());
		checkbox_deleteManualTrafos.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				if(checkbox_deleteManualTrafos.getSelection()==false) {
					settings.setOverwriteConfigInTargetFolder(false);
				}else {
					settings.setOverwriteConfigInTargetFolder(true);
				}

			}
		});
		
		// Radiobutton delete existing transformations
		final Button radiobutton_deleteGeneratedTrafos = new Button(group_trafoSettings, SWT.RADIO);
		radiobutton_deleteGeneratedTrafos.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		radiobutton_deleteGeneratedTrafos.setText("Delete existing, generated transformations");
		radiobutton_deleteGeneratedTrafos.setSelection(settings.isDeleteGeneratedTransformations());
		radiobutton_deleteGeneratedTrafos.addSelectionListener(new SelectionAdapter() {	
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				if(radiobutton_deleteGeneratedTrafos.getSelection()) {
					settings.setDeleteGeneratedTransformations(true);
				}else {
					settings.setDeleteGeneratedTransformations(false);
				}
			
			}
		
		});
		
		// Radiobutton keep existing, generated transformations
		final Button radiobutton_keepGeneratedTrafos = new Button(group_trafoSettings, SWT.RADIO);
		radiobutton_keepGeneratedTrafos.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		radiobutton_keepGeneratedTrafos.setText("Keep existing, generated transformations");
		radiobutton_keepGeneratedTrafos.setSelection(!settings.isDeleteGeneratedTransformations());
		radiobutton_keepGeneratedTrafos.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				if(radiobutton_keepGeneratedTrafos.getSelection()) {
					settings.setDeleteGeneratedTransformations(false);
				}else {
					settings.setDeleteGeneratedTransformations(true);
				}
				
			}		
			
		});	
		
		
		// composite layout
		Composite composite = new Composite(group_trafoSettings, SWT.NONE);
		GridData gd_composite = new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1);
		gd_composite.widthHint = 31;
		gd_composite.heightHint = 5;
		composite.setLayoutData(gd_composite);

		
		// Checkbox overwrite existing transformations
		final Button checkbox_overwriteGeneratedTransfos = new Button(group_trafoSettings, SWT.CHECK);
		GridData gd_cbtnOverwriteExisting = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_cbtnOverwriteExisting.widthHint = 506;
		checkbox_overwriteGeneratedTransfos.setLayoutData(gd_cbtnOverwriteExisting);
		checkbox_overwriteGeneratedTransfos.setText("Overwrite existing, generated transformation");
		checkbox_overwriteGeneratedTransfos.setSelection(settings.isOverwriteGeneratedTransformations());
		checkbox_overwriteGeneratedTransfos.addSelectionListener(new SelectionAdapter() {
		
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(checkbox_overwriteGeneratedTransfos.getSelection()) {
					settings.setOverwriteGeneratedTransformations(true);
				}else {
					settings.setOverwriteGeneratedTransformations(false);
				}
			}
		
		});

		// Group Config Serialization
		Group group_configSerialization = new Group(container, SWT.SHADOW_IN);
		group_configSerialization.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		group_configSerialization.setText("Config Serialization");
		group_configSerialization.setLayout(new GridLayout(1, false));

		
		// Checkbox overwrite config
		final Button checkbox_overwriteConfig = new Button(group_configSerialization, SWT.CHECK);
		checkbox_overwriteConfig.setText("Overwrite existing config");
		checkbox_overwriteConfig.setSelection(settings.isOverwriteConfigInTargetFolder());		
		checkbox_overwriteConfig.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				if(checkbox_overwriteConfig.getSelection()) {
					settings.setOverwriteConfigInTargetFolder(true);
				}else {
					settings.setOverwriteConfigInTargetFolder(false);
				}

			}
		});

		// Group Log Serialization
		Group grpLogSerialization = new Group(container, SWT.SHADOW_IN);
		grpLogSerialization.setLayout(new GridLayout(1, false));
		GridData gd_grpLogSerialization = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_grpLogSerialization.widthHint = 184;
		grpLogSerialization.setLayoutData(gd_grpLogSerialization);
		grpLogSerialization.setText("Log Serialization");
		

		// Checkbox save log serialization
		final Button checkbox_saveLogSerialization = new Button(grpLogSerialization, SWT.CHECK);
		checkbox_saveLogSerialization.setText("Save logs");
		checkbox_saveLogSerialization.setSelection(settings.isSaveLogs());
		checkbox_saveLogSerialization.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(checkbox_saveLogSerialization.getSelection()) {
					settings.setSaveLogs(true);
				}else {
					settings.setSaveLogs(false);
				}
			}
			
		});
		
		// Checkbox delete previous logs
		final Button checkbox_deleteLogs = new Button(grpLogSerialization, SWT.CHECK);
		checkbox_deleteLogs.setText("Delete previous logs");
		checkbox_deleteLogs.setSelection(settings.isDeleteLogs());
		checkbox_deleteLogs.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(checkbox_deleteLogs.getSelection()) {
					settings.setDeleteLogs(true);
				}else {
					settings.setDeleteLogs(false);
				}
			}
			
		});
	}

	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */

	private void initialize() {
		if (selection != null && selection.isEmpty() == false
				&& selection instanceof IStructuredSelection) {
			IStructuredSelection ssel = (IStructuredSelection) selection;
			if (ssel.size() > 1)
				return;
			Object obj = ssel.getFirstElement();
			if (obj instanceof IResource) {
				IContainer container;
				if (obj instanceof IContainer)
					container = (IContainer) obj;
				else
					container = ((IResource) obj).getParent();
				//containerText.setText(container.getFullPath().toString());
			}
		}
	}

	/**
	 * Ensures that both text fields are set.
	 */

	private void dialogChanged() {
		IResource container = ResourcesPlugin.getWorkspace().getRoot()
				.findMember(new Path(getContainerName()));
		String fileName = getFileName();

		if (getContainerName().length() == 0) {
			updateStatus("File container must be specified");
			return;
		}
		if (container == null
				|| (container.getType() & (IResource.PROJECT | IResource.FOLDER)) == 0) {
			updateStatus("File container must exist");
			return;
		}
		if (!container.isAccessible()) {
			updateStatus("Project must be writable");
			return;
		}
		if (fileName.length() == 0) {
			updateStatus("File name must be specified");
			return;
		}
		if (fileName.replace('\\', '/').indexOf('/', 1) > 0) {
			updateStatus("File name must be valid");
			return;
		}
		int dotLoc = fileName.lastIndexOf('.');
		if (dotLoc != -1) {
			String ext = fileName.substring(dotLoc + 1);
			if (ext.equalsIgnoreCase("serge") == false) {
				updateStatus("File extension must be \"serge\"");
				return;
			}
		}
		updateStatus(null);
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public String getContainerName() {
		return null;
	}

	public String getFileName() {
		return null;
	}
}