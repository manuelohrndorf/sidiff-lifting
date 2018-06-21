package org.sidiff.editrule.generator.ui.widgets;

import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.sidiff.common.ui.util.EcoreSelectionDialogUtil;
import org.sidiff.common.ui.widgets.IWidget;
import org.sidiff.common.ui.widgets.IWidgetValidation;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;
import org.sidiff.editrule.generator.settings.EditRuleGenerationSettings;

/**
 * Widget containing UI-Tools for generating CPEO Configurations.
 * @author Simon Heimes
 *
 */
public class EditRuleGeneratorSettingsWidget implements IWidget, IWidgetValidation {
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
	 * @wbp.parser.entryPoint
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
		composite.setLayout(new GridLayout(3, false));
		
		rBtnRefinedConfig = new Button(composite, SWT.RADIO);
		rBtnRefinedConfig.setText("Refined Config");
		rBtnRefinedConfig.setSelection(true);
		
		btnBrowse = new Button(composite, SWT.NONE);
		btnBrowse.setText("Browse");
		
		btnBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try{
					settings.setConfigPath(eConfigChooser.open());
					settings.setUseDefaultConfig(false);
					txtRefinedConfig.setText(settings.getConfigPath());
				} catch (NullPointerException NPe){
					System.out.println("Selection canceled.");
				} catch (IllegalArgumentException ILe){
					System.out.println("Selection canceled.");
				}
			}
		});
		
		txtRefinedConfig = new Text(composite, SWT.BORDER);
		txtRefinedConfig.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		rBtnDefaultConfig = new Button(composite, SWT.RADIO);
		rBtnDefaultConfig.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		rBtnDefaultConfig.setText("Default Config");
		rBtnDefaultConfig.setSelection(false);
	
		
		rBtnDefaultConfig.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean useDefault = rBtnDefaultConfig.getSelection();
				settings.setUseDefaultConfig(useDefault);
			}		
		});
		
		
		btnChooseDocumenttype = new Button(composite, SWT.NONE);
		GridData gd_btnChooseDocumenttype = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnChooseDocumenttype.widthHint = 164;
		btnChooseDocumenttype.setLayoutData(gd_btnChooseDocumenttype);
		btnChooseDocumenttype.setText("Choose Documenttype");
		
		btnChooseDocumenttype.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//@SuppressWarnings("unused")
				try{
					String ePackageNsUri = EcoreSelectionDialogUtil.selectRegisteredPackage(Display.getCurrent().getActiveShell(), new ResourceSetImpl()).getNsURI();
					txtDefaultConfig.setText(ePackageNsUri);
					settings.setUseDefaultConfig(true);
					settings.setMetaModelNsUri(ePackageNsUri);
				} catch (NullPointerException NPe) {
					System.out.println("Selection canceled.");
				}
			}
		});
		
		txtDefaultConfig = new Text(composite, SWT.BORDER);
		txtDefaultConfig.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		if(this.settings.getConfigPath()!=null){
			txtRefinedConfig.setText(this.settings.getConfigPath());
		}
		
		final Button cbtnSubfolder = new Button(composite, SWT.CHECK);
		GridData gd_cbtnSubfolder = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 3, 1);
		gd_cbtnSubfolder.widthHint = 449;
		cbtnSubfolder.setLayoutData(gd_cbtnSubfolder);
		cbtnSubfolder
				.setText("Create sub-folders for transformation kinds (create, delete, ...)");
		cbtnSubfolder.setSelection(settings.isUseSubfolders());
		
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
	public ValidationMessage getValidationMessage() {		
		ValidationMessage message;
		if(validate()){
			message = new ValidationMessage(ValidationType.OK, "");
		}
		else{
			message = new ValidationMessage(ValidationType.ERROR,  MESSAGE[state]);
		}	
		return message;			
	}
	
	@Override
	public Composite getWidget() {
		return parent;
	}

	@Override
	public void setLayoutData(Object layoutData) {
		parent.setLayoutData(layoutData);
	}
	
	public void setEnabled(Boolean enabled) {
		for (Control c : composite.getChildren()){
			c.setEnabled(enabled);
		}
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

	public void addSelectionListener(SelectionAdapter validationListener) {
		rBtnDefaultConfig.addSelectionListener(validationListener);
		rBtnRefinedConfig.addSelectionListener(validationListener);
		btnBrowse.addSelectionListener(validationListener);
		btnChooseDocumenttype.addSelectionListener(validationListener);
		
	}
}
