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
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.sidiff.editrule.generator.serge.settings.SergeSettings;
import org.sidiff.editrule.generator.ui.widgets.EditRuleGeneratorSettingsWidget;

/**
 * The "New" wizard page allows setting the container for the new file as well
 * as the file name. The page will only accept file name without the extension
 * OR with the extension that matches the expected one (mpe).
 */
public class SergeWizardPage1 extends WizardPage {

	private ISelection selection;
	
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
		setMessage(configWidget.getValidationMessage().getMessage(), ERROR);
		if (configWidget.getValidationMessage().getMessage().equals("")){
			/** TODO
			 * Enter Message, that should be displayed.
			 */
			setMessage("", NONE);
		}
		setPageComplete(valid);
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
//		layout.verticalSpacing = 9;
		initialize();
		//dialogChanged();
		setControl(container);
		setPageComplete(false);
		
		container.setLayout(new GridLayout(1, false));
		
		Composite config = new Composite(container, SWT.SMOOTH);
		config.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		config.setLayout(new GridLayout(1, false));
		
		configWidget = new EditRuleGeneratorSettingsWidget(settings);
		configWidget.createControl(config);
		
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
		/* TODO 
		 * Change from setErrorMessage() to setMessage with if-clause on type
		 */
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