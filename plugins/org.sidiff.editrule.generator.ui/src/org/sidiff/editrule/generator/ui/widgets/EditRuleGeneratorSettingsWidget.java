package org.sidiff.editrule.generator.ui.widgets;

import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.sidiff.editrule.generator.settings.EditRuleGenerationSettings;
import org.silift.common.util.ui.EcoreSelectionDialogUtil;
import org.silift.common.util.ui.widgets.IWidget;
import org.silift.common.util.ui.widgets.IWidgetInformation;
import org.silift.common.util.ui.widgets.IWidgetValidation;

/**
 * Widget containing UI-Tools for generating CPEO Configurations.
 * @author Simon Heimes
 *
 */
public class EditRuleGeneratorSettingsWidget implements IWidget, IWidgetValidation, IWidgetInformation{
	/**
	 * Path to Configuration File
	 */
	private Text txtRefinedConfig;
	/**
	 * Path to Default Configuration File
	 */
	private Text txtDefaultConfig;

	private Composite parent;
	private Group group;
	private Composite composite;
	private Button rBtnRefinedConfig;	

	private Button rBtnDefaultConfig;
	private Button btnBrowse;
	private Button btnChooseDocumenttype;	
	
	/**
	 * Settings-Object with Generator Settings
	 */
	private EditRuleGenerationSettings settings;
	private final FileDialog eConfigChooser = new FileDialog(Display.getCurrent().getActiveShell());
	
	/**
	 * Static Access-Variable to Message-Array
	 */
	private final static int VALID = 0;
	/**
	 * Static Access-Variable to Message-Array
	 */
	private final static int MISSING_DOCUMENT_TYPE = 1;
	/**
	 * Static Access-Variable to Message-Array
	 */
	private final static int MISSING_CONFIG = 2;
	
	/**
	 * Array of Error Messages
	 */
	private final static String[] MESSAGE = {null, "Documenttype is not set.", "Configuration Path is missing"};
		
	/**
	 * 0 = VALID; 1 = MISSING_DOCUMENT_TYPE; 2 = MISSING_CONFIG, refers to MESSAGE
	 */
	private int state=0; //  	
	
	
	public EditRuleGeneratorSettingsWidget(EditRuleGenerationSettings settings) {
		super();
		this.settings = settings;
	}

	/**
	 * Adds UI-Tools to Composite parent. Specifies Settings for Configuration
	 * of CPEO Generation.
	 * 
	 * @author Simon Heimes
	 * @param parent
	 *            Composite, which will be filled with UI-Tools.
	 * @return Composite, filled with UI-Tools
	 * @see createControl
	 */
	@Override
	public Composite createControl(Composite parent) {

		this.parent = parent;
				
		parent.setLayout(new GridLayout(1, false));
		group = new Group(parent, SWT.SHADOW_ETCHED_IN);
		group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		group.setText("EditRule Generator Settings");
		group.setLayout(new GridLayout(1, false));
		
		composite = new Composite(group, SWT.NONE);
		GridData gd_composite = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_composite.widthHint = 428;
		composite.setLayoutData(gd_composite);
		composite.setLayout(new GridLayout(5, false));
		
		rBtnRefinedConfig = new Button(composite, SWT.RADIO);
		rBtnRefinedConfig.setText("Refined Config");
		rBtnRefinedConfig.setSelection(true);
		
		btnBrowse = new Button(composite, SWT.NONE);
		btnBrowse.setText("Browse");
		
		btnBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				settings.setConfigPath(eConfigChooser.open());
				txtRefinedConfig.setText(settings.getConfigPath());
			}
		});
		
		txtRefinedConfig = new Text(composite, SWT.BORDER);
		txtRefinedConfig.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		rBtnDefaultConfig = new Button(composite, SWT.RADIO);
		rBtnDefaultConfig.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		rBtnDefaultConfig.setText("Default Config");
		
		btnChooseDocumenttype = new Button(composite, SWT.NONE);
		GridData gd_btnChooseDocumenttype = new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1);
		gd_btnChooseDocumenttype.widthHint = 164;
		btnChooseDocumenttype.setLayoutData(gd_btnChooseDocumenttype);
		btnChooseDocumenttype.setText("Choose Documenttype");
		
		btnChooseDocumenttype.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//@SuppressWarnings("unused")
				try{
					txtDefaultConfig.setText(EcoreSelectionDialogUtil.selectRegisteredPackage(Display.getCurrent().getActiveShell(), new ResourceSetImpl()).getNsURI());
				} catch (NullPointerException NPe) {
					System.out.println("Selection canceled");
				}
			}
		});
		
		txtDefaultConfig = new Text(composite, SWT.BORDER);
		txtDefaultConfig.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		if(this.settings.getConfigPath()!=null){
			txtRefinedConfig.setText(this.settings.getConfigPath());
		}
		
		final Button cbtnSubfolder = new Button(composite, SWT.CHECK);
		cbtnSubfolder.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 2, 1));
		cbtnSubfolder
				.setText("Create sub-folders for transformation kinds (create, delete, ...)");
		cbtnSubfolder.setSelection(settings.isUseSubfolders());
		new Label(composite, SWT.NONE);
		
		cbtnSubfolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {			
				settings.setUseSubfolders(cbtnSubfolder.getSelection());
			}});
		return parent;
	}


	/**
	 * Checks, if entered Information is correct.
	 * @author Simon Heimes
	 * @return 	true - Entered Information are correct
	 * 			false - Entered Information are incorrect
	 */
	@Override
	public boolean validate() {
		if ((rBtnDefaultConfig.getSelection() && !txtDefaultConfig.getText().isEmpty()) || 
				(rBtnRefinedConfig.getSelection() && !txtRefinedConfig.getText().isEmpty())) 
			state = VALID;
		if (rBtnDefaultConfig.getSelection() && txtDefaultConfig.getText().isEmpty())
			state = MISSING_DOCUMENT_TYPE;
		if (rBtnRefinedConfig.getSelection() && txtRefinedConfig.getText().isEmpty())
			state = MISSING_CONFIG;
		
		if (state == 0) return true;
		else return false;
	}
	
	/**
	 * @author Simon Heimes
	 * @return String - Errormessage to be displayed in Wizard.
	 *  	If Content is valid - null <>
	 *  	If Documenttype is not set while selected - "Documenttype is not set." <>
	 * 	If Configuration Path is not set while selected - "Configuration Path is missing" <>
	 */
	@Override
	public String getValidationMessage() {
		return MESSAGE[state];
	}
	
	@Override
	public Composite getWidget() {
		return parent;
	}

	@Override
	public void setLayoutData(Object layoutData) {
		parent.setLayoutData(layoutData);
		
	}

	@Override
	public String getInformationMessage() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setSettings(EditRuleGenerationSettings settings) {
		this.settings = settings;
	}

	public EditRuleGenerationSettings getSettings() {
		return settings;
	}

	public Text getTxtRefinedConfig() {
		return txtRefinedConfig;
	}

	public void setTxtRefinedConfig(Text txtRefinedConfig) {
		this.txtRefinedConfig = txtRefinedConfig;
	}

	public Text getTxtDefaultConfig() {
		return txtDefaultConfig;
	}

	public void setTxtDefaultConfig(Text txtDefaultConfig) {
		this.txtDefaultConfig = txtDefaultConfig;
	}
	
	public Button getrBtnRefinedConfig() {
		return rBtnRefinedConfig;
	}

	public void setrBtnRefinedConfig(Button rBtnRefinedConfig) {
		this.rBtnRefinedConfig = rBtnRefinedConfig;
	}

	public Button getrBtnDefaultConfig() {
		return rBtnDefaultConfig;
	}

	public void setrBtnDefaultConfig(Button rBtnDefaultConfig) {
		this.rBtnDefaultConfig = rBtnDefaultConfig;
	}
}
