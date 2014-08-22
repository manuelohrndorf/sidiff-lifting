package org.sidiff.editrule.generator.ui.wizards;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.eclipse.swt.widgets.Group;
import org.sidiff.editrule.generator.settings.SergeSettings;

/**
 * The "New" wizard page allows setting the container for the new file as well
 * as the file name. The page will only accept file name without the extension
 * OR with the extension that matches the expected one (mpe).
 */

public class SergeWizardPage2 extends WizardPage {

	private ISelection selection;
	private SergeSettings settings;

	/**
	 * Constructor for SampleNewWizardPage.
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
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		container.setLayout(layout);
		layout.verticalSpacing = 9;
		initialize();
		//dialogChanged();
		setControl(container);	

		
		Group grpTransformationSettings = new Group(container, SWT.SHADOW_IN);
		grpTransformationSettings.setLayout(new GridLayout(2, false));
		GridData gd_grpTransformationSettings = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		gd_grpTransformationSettings.widthHint = 424;
		grpTransformationSettings.setLayoutData(gd_grpTransformationSettings);
		grpTransformationSettings.setText("Transformation Settings");
		
		Button cbtnDeleteExisting = new Button(grpTransformationSettings, SWT.CHECK);
		cbtnDeleteExisting.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		cbtnDeleteExisting.setText("Delete existing, manually created transformations ");
		cbtnDeleteExisting.setSelection(settings.isDeleteManualTransformations());
		
		Button btnDeleteExisting = new Button(grpTransformationSettings, SWT.RADIO);
		btnDeleteExisting.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		btnDeleteExisting.setText("Delete existing, generated transformations");
		btnDeleteExisting.setSelection(settings.isDeleteGeneratedTransformations());
		
				Button btnKeepExisting = new Button(grpTransformationSettings, SWT.RADIO);
				btnKeepExisting.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
				btnKeepExisting.setText("Keep existing, generated transformations");
				btnKeepExisting.setSelection(!settings.isDeleteGeneratedTransformations());
						
						Composite composite = new Composite(grpTransformationSettings, SWT.NONE);
						GridData gd_composite = new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1);
						gd_composite.widthHint = 31;
						gd_composite.heightHint = 5;
						composite.setLayoutData(gd_composite);
				
						Button cbtnOverwriteExisting = new Button(grpTransformationSettings, SWT.CHECK);
						GridData gd_cbtnOverwriteExisting = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
						gd_cbtnOverwriteExisting.widthHint = 506;
						cbtnOverwriteExisting.setLayoutData(gd_cbtnOverwriteExisting);
						cbtnOverwriteExisting.setText("Overwrite existing, generated transformation");
						cbtnOverwriteExisting.setSelection(settings.isOverwriteGeneratedTransformations());

		
		Group grpConfigSerialization = new Group(container, SWT.SHADOW_IN);
		grpConfigSerialization.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		grpConfigSerialization.setText("Config Serialization");
		grpConfigSerialization.setLayout(new GridLayout(1, false));
		
		Button cbtnOverwriteExistingConfig = new Button(grpConfigSerialization, SWT.CHECK);
		cbtnOverwriteExistingConfig.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		cbtnOverwriteExistingConfig.setText("Overwrite existing config");
		cbtnOverwriteExistingConfig.setSelection(settings.isOverwriteConfigInTargetFolder());		
		
		Group grpLogSerialization = new Group(container, SWT.SHADOW_IN);
		grpLogSerialization.setLayout(new GridLayout(1, false));
		GridData gd_grpLogSerialization = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_grpLogSerialization.widthHint = 184;
		grpLogSerialization.setLayoutData(gd_grpLogSerialization);
		grpLogSerialization.setText("Log Serialization");
		
		Button cbtnSaveLog = new Button(grpLogSerialization, SWT.CHECK);
		cbtnSaveLog.setText("Save logs");
		cbtnSaveLog.setSelection(settings.isSaveLogs());		
		
				Button cbtnDeletePreviousTags = new Button(grpLogSerialization, SWT.CHECK);
				cbtnDeletePreviousTags.setText("Delete previous logs");
				cbtnDeletePreviousTags.setSelection(settings.isDeleteLogs());

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