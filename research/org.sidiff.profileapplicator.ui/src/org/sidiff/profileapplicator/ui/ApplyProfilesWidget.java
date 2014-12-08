package org.sidiff.profileapplicator.ui;

import org.eclipse.jface.layout.GridDataFactory;
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
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.sidiff.profileapplicator.settings.ProfileApplicatorSettings;
import org.silift.common.util.ui.widgets.IWidget;
import org.silift.common.util.ui.widgets.IWidgetInformation;
import org.silift.common.util.ui.widgets.IWidgetValidation;

public class ApplyProfilesWidget implements IWidget, IWidgetValidation,
		IWidgetInformation {

	/* Settings */
	private final ProfileApplicatorSettings settings;

	/* Fields */
	private String errorMessage = null;

	/* Components */
	private Composite container;
	private FolderSelectionWidget inputFolder;
	private Button includeSubfolders;
	private FolderSelectionWidget outputFolder;
	private Button keepGenerated;
	private Button deleteGenerated;
	private Button overwriteGenerated;
	private Button overwriteConfig;
	private FileSelectionWidget configFile;

	public ApplyProfilesWidget(ProfileApplicatorSettings settings) {
		super();
		this.settings = settings;
	}

	@Override
	public String getInformationMessage() {
		return null;
	}

	public ProfileApplicatorSettings getSettings() {
		return settings;
	}

	@Override
	public boolean validate() {
		boolean inputFolderValid = inputFolder.validate();
		boolean outputFolderValid = outputFolder.validate();
		boolean configFileValid = configFile.validate();
		if (!inputFolderValid) {
			this.errorMessage = inputFolder.getValidationMessage();
		} else if (!configFileValid) {
			this.errorMessage = configFile.getValidationMessage();
		} else if (!outputFolderValid) {
			this.errorMessage = outputFolder.getValidationMessage();
		} else {
			this.errorMessage = null;
		}
		return (inputFolderValid && outputFolderValid && configFileValid);
	}

	@Override
	public String getValidationMessage() {
		return errorMessage;
	}

	@Override
	public Composite createControl(Composite parent) {
		/* Container and layout */
		// parent.setLayout(new FillLayout());
		GridData gdFillHorizontal = new GridData(GridData.FILL_HORIZONTAL);
		container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		Composite compGrid = new Composite(container, SWT.NONE);
		compGrid.setLayout(new GridLayout(2, false));
		compGrid.setLayoutData(gdFillHorizontal);
		/* Rule input folder */
		Label label1 = new Label(compGrid, SWT.NONE);
		label1.setText("Rule input folder");
		inputFolder = new FolderSelectionWidget("Rule input folder",
				settings.getInputFolderPath(), true);
		inputFolder.createControl(compGrid);
		inputFolder.setLayoutData(gdFillHorizontal);
		inputFolder.addModifiedListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				settings.setInputFolderPath(inputFolder.getText());
			}
		});
		(new Label(compGrid, SWT.NONE)).setText(""); // for intend
		includeSubfolders = new Button(compGrid, SWT.CHECK);
		includeSubfolders.setText("Include subfolders");
		includeSubfolders.setSelection(settings.isUseSubfolders());
		includeSubfolders.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				settings.setUseSubfolders(includeSubfolders.getSelection());
			}
			
		});
		/* Config file */
		Label label4 = new Label(compGrid, SWT.NONE);
		label4.setText("Configuration file");
		// TODO Load from settings
		configFile = new FileSelectionWidget("Configuration file", "", true,
				new String[] { "*.xml" });
		configFile.createControl(compGrid);
		configFile.setLayoutData(gdFillHorizontal);
		configFile.addModifiedListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				settings.setConfigPath(configFile.getText());
			}
		});
		/* Output folder */
		Label label3 = new Label(compGrid, SWT.NONE);
		label3.setText("Output folder");
		String suggestedPath = settings.getOutputFolderPath();
		if (suggestedPath == null || suggestedPath.isEmpty()) {
			String inPath = settings.getInputFolderPath();
			if (inPath != null) {
				java.io.File f = new java.io.File(inPath);
				if (f.isDirectory()) {
					suggestedPath = inPath;
					if (!inPath.endsWith(java.io.File.separator))
						suggestedPath += java.io.File.separator;
					suggestedPath += "profiled" + java.io.File.separator;
				}
			}
		}
		outputFolder = new FolderSelectionWidget("Output folder",
				suggestedPath, false);
		outputFolder.createControl(compGrid);
		outputFolder.setLayoutData(gdFillHorizontal);
		outputFolder.addModifiedListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				settings.setOutputFolderPath(outputFolder.getText());
			}
		});
		/* Delete and override genrated transforms */
		Group groupOutputMode = new Group(container, SWT.SHADOW_IN);
		groupOutputMode
				.setText("Transformations already present in output folder");
		groupOutputMode.setLayout(new GridLayout(1, false));
		GridDataFactory.fillDefaults().hint(SWT.DEFAULT, 85)
				.applyTo(groupOutputMode);
		boolean keepTransforms;
		boolean overwriteTransforms;
		boolean deleteTransforms;
		if (settings.isDeleteGeneratedTransformations()) {
			keepTransforms = false;
			overwriteTransforms = false;
			deleteTransforms = true;
		} else if (settings.isOverwriteGeneratedTransformations()) {
			keepTransforms = false;
			overwriteTransforms = true;
			deleteTransforms = false;
		} else {
			keepTransforms = true;
			overwriteTransforms = false;
			deleteTransforms = false;
		}
		final SelectionListener groupOutputSelectionListener = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (overwriteGenerated.getSelection()){
					settings.setDeleteGeneratedTransformations(false);
					settings.setOverwriteGeneratedTransformations(true);
				} else if (deleteGenerated.getSelection()){
					settings.setDeleteGeneratedTransformations(true);
					settings.setOverwriteGeneratedTransformations(true);
				} else {
					settings.setDeleteGeneratedTransformations(false);
					settings.setOverwriteGeneratedTransformations(false);
				}
			}
		};
		keepGenerated = new Button(groupOutputMode, SWT.RADIO);
		keepGenerated.setText("Keep");
		keepGenerated.setSelection(keepTransforms);
		keepGenerated.addSelectionListener(groupOutputSelectionListener);
		overwriteGenerated = new Button(groupOutputMode,
				SWT.RADIO);
		overwriteGenerated.setText("Overwrite");
		overwriteGenerated.setSelection(overwriteTransforms);
		overwriteGenerated.addSelectionListener(groupOutputSelectionListener);
		deleteGenerated = new Button(groupOutputMode, SWT.RADIO);
		deleteGenerated.setText("Delete");
		deleteGenerated.setSelection(deleteTransforms);
		deleteGenerated.addSelectionListener(groupOutputSelectionListener);
		/* Overwrite config file */
		overwriteConfig = new Button(container, SWT.CHECK);
		overwriteConfig
				.setText("Overwrite configuration file in output folder");
		overwriteConfig
				.setSelection(settings.isOverwriteConfigInTargetFolder());
		overwriteConfig.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				settings.setOverwriteConfigInTargetFolder(overwriteConfig.getSelection());
			}
			
		});
		/* Finish */
		container.pack();
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

	public void addModifyListener(ModifyListener listener) {
		inputFolder.addModifiedListener(listener);
		configFile.addModifiedListener(listener);
		outputFolder.addModifiedListener(listener);
	}

}
