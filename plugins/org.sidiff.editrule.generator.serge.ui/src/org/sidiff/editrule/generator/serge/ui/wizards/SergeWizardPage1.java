package org.sidiff.editrule.generator.serge.ui.wizards;

import javax.swing.JFileChooser;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.dialogs.IDialogPage;
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
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.sidiff.editrule.generator.serge.settings.SergeSettings;
import org.silift.common.util.ui.EcoreSelectionDialogUtil;
import org.silift.common.util.ui.widgets.IWidget;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.RowData;

/**
 * The "New" wizard page allows setting the container for the new file as well
 * as the file name. The page will only accept file name without the extension
 * OR with the extension that matches the expected one (mpe).
 */

public class SergeWizardPage1 extends WizardPage {

	private ISelection selection;
	private Text txtSelectOutputFolder;
	private Text txtImportPackage;
	private Text txtConfig;
	
	private Boolean bOutputFolder;
	private Boolean bConfig;
	
	private SergeSettings settings;
	

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
		final FileDialog eConfigChooser = new FileDialog(this.getShell());
		bConfig = false;
		bOutputFolder = false;
		container.setLayout(new GridLayout(1, false));
		
		
		Group grpOutputPaths = new Group(container, SWT.NONE);
		grpOutputPaths.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpOutputPaths.setLayout(new GridLayout(1, false));
		grpOutputPaths.setText("Output");
		
		Composite composite = new Composite(grpOutputPaths, SWT.NONE);
		composite.setLayout(new GridLayout(3, false));
//		gd_composite.widthHint = 380;
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblSelectOutputFolder = new Label(composite, SWT.NONE);
		lblSelectOutputFolder.setText("Select Output Folder");
		
		txtSelectOutputFolder = new Text(composite, SWT.BORDER);
		txtSelectOutputFolder.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnBrowseOutputFolder = new Button(composite, SWT.NONE);
		btnBrowseOutputFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				settings.setOutputFolderPath(eOutputFolderChooser.open());				
				txtSelectOutputFolder.setText(settings.getOutputFolderPath());
			}
		});
		btnBrowseOutputFolder.setText("Browse");
		
		Button cbtnSubfolder = new Button(composite, SWT.CHECK);
		cbtnSubfolder.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		cbtnSubfolder.setText("Create sub-folders for transformation kinds (create, delete, ...)");
		cbtnSubfolder.setSelection(settings.isUseSubfolders());
		new Label(composite, SWT.NONE);
		
		Group grpMetamodelSpecificConfiguration = new Group(container, SWT.SHADOW_ETCHED_IN);
		grpMetamodelSpecificConfiguration.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpMetamodelSpecificConfiguration.setText("Metamodel Specific Configuration");
		grpMetamodelSpecificConfiguration.setLayout(new GridLayout(1, false));
		
		Composite composite_1 = new Composite(grpMetamodelSpecificConfiguration, SWT.NONE);
//		gd_composite_1.widthHint = 383;
		composite_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		composite_1.setLayout(new GridLayout(5, false));
		
		final Button btnRefinedConfig = new Button(composite_1, SWT.RADIO);
		btnRefinedConfig.setText("Refined Config");
		btnRefinedConfig.setSelection((settings.getConfigPath()!=null));
		new Label(composite_1, SWT.NONE);		

		Button btnBrowseConfig = new Button(composite_1, SWT.NONE);
		btnBrowseConfig.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				settings.setConfigPath(eConfigChooser.open());
				txtConfig.setText(settings.getConfigPath());
			}
		});
		btnBrowseConfig.setText("Browse");		
		
		txtConfig = new Text(composite_1, SWT.BORDER);
		txtConfig.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		if(this.settings.getConfigPath()!=null){
			txtConfig.setText(this.settings.getConfigPath());
			bConfig = true;
		}
		
		final Button btnDefaultConfig = new Button(composite_1, SWT.RADIO);
		btnDefaultConfig.setText("Default Config");
		new Label(composite_1, SWT.NONE);
		
		Button btnImportPackage = new Button(composite_1, SWT.NONE);
		btnImportPackage.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnImportPackage.setText("Choose Documenttype");	
		
		txtImportPackage = new Text(composite_1, SWT.BORDER);
		txtImportPackage.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		
		btnImportPackage.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				@SuppressWarnings("unused")
				EPackage documentTypePackage = EcoreSelectionDialogUtil.selectRegisteredPackage(Display.getCurrent().getActiveShell(), new ResourceSetImpl());
				txtImportPackage.setText(documentTypePackage.getNsURI());				
			}
		});
		
		Composite widget = new Composite(container, SWT.SMOOTH);
		widget.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		widget.setLayout(new GridLayout(1, false));
		
		MetaModelSpecConfigWidget Config = new MetaModelSpecConfigWidget();
		Config.createControl(widget);
		
		
		txtSelectOutputFolder.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				if (txtSelectOutputFolder.getText().length() == 0) {bOutputFolder = false;} else {bOutputFolder = true;}
				if (bConfig && bOutputFolder) {setPageComplete(true); setErrorMessage(null);} else {setPageComplete(false);}
				if (!bConfig && !bOutputFolder) {setErrorMessage("Output Folder Path and Configuration Path are missing!");}
				if (!bConfig && bOutputFolder) {setErrorMessage("Configuration Path is missing!");}
				if (bConfig && !bOutputFolder) {setErrorMessage("Output Folder Path is missing!");}
			}
		});
		
		txtConfig.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				if (txtConfig.getText().length() == 0 && txtImportPackage.getText().length() == 0) {bConfig = false;} else {bConfig = true;}
				if (bConfig && bOutputFolder) {setPageComplete(true); setErrorMessage(null);} else {setPageComplete(false);}
				if (!bConfig && !bOutputFolder) {setErrorMessage("Output Folder Path and Configuration Path are missing!");}
				if (!bConfig && bOutputFolder) {setErrorMessage("Configuration Path is missing!");}
				if (bConfig && !bOutputFolder) {setErrorMessage("Output Folder Path is missing!");}
				btnRefinedConfig.setSelection(true);
				btnDefaultConfig.setSelection(false);
			}
		});
		
		txtImportPackage.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				if (txtConfig.getText().length() == 0 && txtImportPackage.getText().length() == 0) {bConfig = false;} else {bConfig = true;}
				if (bConfig && bOutputFolder) {setPageComplete(true); setErrorMessage(null);} else {setPageComplete(false);}
				if (!bConfig && !bOutputFolder) {setErrorMessage("Output Folder Path and Configuration Path are missing!");}
				if (!bConfig && bOutputFolder) {setErrorMessage("Configuration Path is missing!");}
				if (bConfig && !bOutputFolder) {setErrorMessage("Output Folder Path is missing!");}
				btnDefaultConfig.setSelection(true);
				btnRefinedConfig.setSelection(false);
			}
		});
		
		btnDefaultConfig.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e){
				if (!bConfig && !bOutputFolder) {setErrorMessage("Output Folder Path and Configuration Path are missing!");}
				if (!bConfig && bOutputFolder) {setErrorMessage("Configuration Path is missing!");}
				if (bConfig && !bOutputFolder) {setErrorMessage("Output Folder Path is missing!");}
				if (bConfig && bOutputFolder) {setPageComplete(true); setErrorMessage(null);} else {setPageComplete(false);}
				if (txtImportPackage.getText().length() == 0) {setPageComplete(false);setErrorMessage("Default Config is empty!");};
				
			}
		});
		
		btnRefinedConfig.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e){
				if (!bConfig && !bOutputFolder) {setErrorMessage("Output Folder Path and Configuration Path are missing!");}
				if (!bConfig && bOutputFolder) {setErrorMessage("Configuration Path is missing!");}
				if (bConfig && !bOutputFolder) {setErrorMessage("Output Folder Path is missing!");}
				if (bConfig && bOutputFolder) {setPageComplete(true); setErrorMessage(null);} else {setPageComplete(false);}
				if (txtConfig.getText().length() == 0) {setPageComplete(false);setErrorMessage("Refined Config is empty!");};
				
			}
		});
		
		setErrorMessage("Output Folder Path ist Missing");
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