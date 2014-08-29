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
import org.sidiff.editrule.generator.settings.EditRuleGeneratorSettings;
import org.silift.common.util.ui.EcoreSelectionDialogUtil;
import org.silift.common.util.ui.widgets.IWidget;
import org.silift.common.util.ui.widgets.IWidgetInformation;
import org.silift.common.util.ui.widgets.IWidgetValidation;


public class EditRuleGeneratorSettingsWidget implements IWidget, IWidgetValidation, IWidgetInformation{
	private Text txtRefinedConfig;
	private Text txtDefaultConfig;

	private Composite parent;
	private Group group;
	private Composite composite;
	private Button rBtnRefinedConfig;
	private Button rBtnDefaultConfig;
	private Button btnBrowse;
	private Button btnChooseDocumenttype;
	private EditRuleGeneratorSettings settings;
	private final FileDialog eConfigChooser = new FileDialog(Display.getCurrent().getActiveShell());
	
	private final static int VALID = 0;
	private final static int MISSING_DOCUMENT_TYPE = 1;
	private final static int MISSING_CONFIG = 2;
	
	private final static String[] MESSAGE = {"Correct.", "Documenttype is not set.", "Configuration Path is missing"};
		
	
	private int state=0; // 0 = ERROR_MISSING_ALL; 1 = ERROR_MISSING_OUTPUT; 2 = ERROR_MISSING_CONFIG; 3 = VALID;
	
	/**
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
		composite.setLayout(new GridLayout(5, false));
		
		rBtnRefinedConfig = new Button(composite, SWT.RADIO);
		rBtnRefinedConfig.setText("Refined Config");
		rBtnRefinedConfig.setSelection(true);
		new Label(composite, SWT.NONE);
		
		btnBrowse = new Button(composite, SWT.NONE);
		btnBrowse.setText("Browse");
		new Label(composite, SWT.NONE);
		
		txtRefinedConfig = new Text(composite, SWT.BORDER);
		txtRefinedConfig.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		rBtnDefaultConfig = new Button(composite, SWT.RADIO);
		rBtnDefaultConfig.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		rBtnDefaultConfig.setText("Default Config");
		new Label(composite, SWT.NONE);
		
		btnChooseDocumenttype = new Button(composite, SWT.NONE);
		btnChooseDocumenttype.setText("Choose Documenttype");
		new Label(composite, SWT.NONE);
		
		txtDefaultConfig = new Text(composite, SWT.BORDER);
		txtDefaultConfig.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button cbtnSubfolder = new Button(composite, SWT.CHECK);
		cbtnSubfolder.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		cbtnSubfolder.setText("Create sub-folders for transformation kinds (create, delete, ...)");
		cbtnSubfolder.setSelection(settings.isUseSubfolders());
		new Label(composite, SWT.NONE);
		
		btnBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				settings.setConfigPath(eConfigChooser.open());
				txtRefinedConfig.setText(settings.getConfigPath());
			}
		});
		
		btnChooseDocumenttype.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//@SuppressWarnings("unused")
				txtDefaultConfig.setText(EcoreSelectionDialogUtil.selectRegisteredPackage(Display.getCurrent().getActiveShell(), new ResourceSetImpl()).getNsURI());
			}
		});
		return null;
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

	@Override
	public String getValidationMessage() {
		return MESSAGE[state];
	}

	public void setSettings(EditRuleGeneratorSettings settings) {
		this.settings = settings;
	}
	
	


}
