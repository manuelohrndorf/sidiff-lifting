package org.sidiff.profileapplicator.ui;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.sidiff.profileapplicator.settings.ProfileApplicatorSettings;

public class ApplyProfilesWizardPage0 extends WizardPage {

	protected ApplyProfilesWizardPage0(ProfileApplicatorSettings settings) {
		super("Main Page");
		this.settings=settings;
		setTitle("ProfileApplicator configuration");
		setDescription("");
		
	}
	
	/* Settings */
	private final ProfileApplicatorSettings settings;
	
	/* Components */
	private Composite container;
	private FolderSelectionWidget inputFolder;
	private Button includeSubfolders;
	private FolderSelectionWidget outputFolder;
	private FileSelectionWidget configFile;
	
	@Override
	public void createControl(Composite parent) {
		/* Container and layout */
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		container = new Composite(parent, SWT.NONE);
		setControl(container);
		setPageComplete(false);
		/* Layout */
		GridLayout layout = new GridLayout(2, false);
		container.setLayout(layout);
		/* Rule input folder */
		Label label1 = new Label(container, SWT.NONE);
		label1.setText("Rule input folder");
		inputFolder = new FolderSelectionWidget("Rule input folder",settings.getInputFolderPath(), true);
		inputFolder.createControl(container);
		inputFolder.setLayoutData(gd);
		inputFolder.addModifiedListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				updatePageState();
			}
		});
		/* Subfolders */
		// Empty Label for intend
		Label label2 = new Label(container, SWT.NONE);
		label2.setText("");
		includeSubfolders = new Button(container, SWT.CHECK);
		includeSubfolders.setText("Include subfolders");
		includeSubfolders.setSelection(true);
		/* Output folder */
		Label label3 = new Label(container, SWT.NONE);
		label3.setText("Output folder");
		String suggestedPath = "";
		String inPath=settings.getInputFolderPath();
		if (inPath != null){
			java.io.File f = new java.io.File(inPath);
			if (f.isDirectory()){
				suggestedPath=inPath;
				if (!inPath.endsWith(java.io.File.separator)) suggestedPath+=java.io.File.separator;
				suggestedPath+="profiled"+java.io.File.separator;
			}
		}
		outputFolder = new FolderSelectionWidget("Output folder",suggestedPath, false);
		outputFolder.createControl(container);
		outputFolder.setLayoutData(gd);
		outputFolder.addModifiedListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				updatePageState();
			}
		});
		/* Config file */
		Label label4 = new Label(container, SWT.NONE);
		label4.setText("Configuration file");
		configFile = new FileSelectionWidget("Configuration file", "", true, new String[]{"*.xml"});
		configFile.createControl(container);
		configFile.setLayoutData(gd);
		configFile.addModifiedListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				updatePageState();
			}
		});
		/* Finish */
		updatePageState();
	}
	
	private void updatePageState(){
		boolean inputFolderValid = inputFolder.validate();
		boolean outputFolderValid=outputFolder.validate();
		boolean configFileValid=configFile.validate();
		if (!inputFolderValid){
			setErrorMessage(inputFolder.getValidationMessage());
		} else if (!outputFolderValid){
			setErrorMessage(outputFolder.getValidationMessage());
		} else if (!configFileValid){
			setErrorMessage(configFile.getValidationMessage());
		} else {
			setErrorMessage(null);
		}
		setPageComplete(inputFolderValid && outputFolderValid && configFileValid);
	}
}
