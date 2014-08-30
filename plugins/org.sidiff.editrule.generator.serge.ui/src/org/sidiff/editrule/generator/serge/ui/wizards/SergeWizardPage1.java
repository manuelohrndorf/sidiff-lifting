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
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.sidiff.editrule.generator.serge.settings.SergeSettings;
import org.sidiff.editrule.generator.ui.widgets.EditRuleGeneratorSettingsWidget;

/**
 * The "New" wizard page allows setting the container for the new file as well
 * as the file name. The page will only accept file name without the extension
 * OR with the extension that matches the expected one (mpe).
 */
public class SergeWizardPage1 extends WizardPage {

	private ISelection selection;
	private Text txtSelectOutputFolder;
	
	private ModifyListener validationListener;
	private SergeSettings settings;
	private EditRuleGeneratorSettingsWidget configWidget;
	

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public SergeWizardPage1(ISelection selection, SergeSettings settings) {
		super("wizardPage");
		setTitle("SERGe CPEO Generation Wizard");
		setDescription("Please define general settings for the generation.");
		this.selection = selection;
		this.settings = settings;
	}
	
	private void validate(){
		Boolean valid = false;
		configWidget.getSettings();
		valid = configWidget.validate();
		setErrorMessage(configWidget.getValidationMessage());
		if (txtSelectOutputFolder.getText().length() == 0) {
			setErrorMessage("Output Folder Path is missing.");
			valid = false;
		}
		setPageComplete(valid);
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
//		layout.verticalSpacing = 9;
		initialize();
		//dialogChanged();
		setControl(container);
		setPageComplete(false);

		
		final DirectoryDialog eOutputFolderChooser = new DirectoryDialog(this.getShell());
		container.setLayout(new GridLayout(1, false));
		
		Composite cOutput = new Composite(container, SWT.NONE);
		cOutput.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		cOutput.setLayout(new GridLayout(1, false));
		
		
		Group grpOutputPaths = new Group(cOutput, SWT.NONE);
		grpOutputPaths.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpOutputPaths.setLayout(new GridLayout(1, false));
		grpOutputPaths.setText("Output");
		
		Composite composite = new Composite(grpOutputPaths, SWT.NONE);
		composite.setLayout(new GridLayout(3, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));
		
		Composite config = new Composite(container, SWT.SMOOTH);
		config.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		config.setLayout(new GridLayout(1, false));
		
		configWidget = new EditRuleGeneratorSettingsWidget(settings);
		configWidget.createControl(config);

		Label lblSelectOutputFolder = new Label(composite, SWT.NONE);
		lblSelectOutputFolder.setText("Select Output Folder");

		txtSelectOutputFolder = new Text(composite, SWT.BORDER);
		txtSelectOutputFolder.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false, 1, 1));

		Button btnBrowseOutputFolder = new Button(composite, SWT.NONE);
		btnBrowseOutputFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				settings.setOutputFolderPath(eOutputFolderChooser.open());
				txtSelectOutputFolder.setText(settings.getOutputFolderPath());
				configWidget.setSettings(settings);
			}
		});
		btnBrowseOutputFolder.setText("Browse");
		
	
		
		validationListener = new ModifyListener() {			
			@Override
			public void modifyText(ModifyEvent e) {
				validate();
			}
		};
		
		configWidget.getTxtDefaultConfig().addModifyListener(validationListener);
		
		configWidget.getTxtRefinedConfig().addModifyListener(validationListener);
		
		configWidget.getrBtnDefaultConfig().addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				validate();		
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		
		configWidget.getrBtnRefinedConfig().addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				validate();	
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		
		txtSelectOutputFolder.addModifyListener(validationListener);
		
		setErrorMessage("Output Folder Path is missing");
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
		IResource container = ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(getContainerName()));
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