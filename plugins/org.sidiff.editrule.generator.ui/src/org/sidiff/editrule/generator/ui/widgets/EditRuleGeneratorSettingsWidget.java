package org.sidiff.editrule.generator.ui.widgets;

import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.sidiff.common.emf.ui.util.EcoreSelectionDialogUtil;
import org.sidiff.common.ui.widgets.AbstractWidget;
import org.sidiff.common.ui.widgets.IWidgetValidation;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;
import org.sidiff.editrule.generator.settings.EditRuleGenerationSettings;

/**
 * Widget containing UI-Tools for generating CPEO Configurations.
 * @author Simon Heimes
 *
 */
public class EditRuleGeneratorSettingsWidget extends AbstractWidget implements IWidgetValidation {

	/**
	 * Path to Configuration File
	 */
	private Text txtRefinedConfig;

	/**
	 * Path to Default Configuration File
	 */
	private Text txtDefaultConfig;

	private Group group;
	private Button rBtnRefinedConfig;
	private Button rBtnDefaultConfig;

	private ValidationMessage message;

	/**
	 * Settings-Object with Generator Settings
	 */
	private EditRuleGenerationSettings settings;

	@Override
	public Composite createControl(Composite parent) {
		group = new Group(parent, SWT.SHADOW_ETCHED_IN);
		group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		group.setText("EditRule Generator Settings");
		group.setLayout(new GridLayout(1, false));
		
		Composite composite = new Composite(group, SWT.NONE);
		GridData gd_composite = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_composite.widthHint = 428;
		composite.setLayoutData(gd_composite);
		composite.setLayout(new GridLayout(3, false));
		
		rBtnRefinedConfig = new Button(composite, SWT.RADIO);
		rBtnRefinedConfig.setText("Refined Config");
		rBtnRefinedConfig.setSelection(true);
		
		Button btnBrowse = new Button(composite, SWT.NONE);
		btnBrowse.setText("Browse");
		
		btnBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog eConfigChooser = new FileDialog(Display.getCurrent().getActiveShell());
				String configPath = eConfigChooser.open();
				if(configPath != null) {
					settings.setConfigPath(configPath);
					settings.setUseDefaultConfig(false);
					txtRefinedConfig.setText(settings.getConfigPath());				
					getWidgetCallback().requestValidation();
				}
			}
		});
		
		txtRefinedConfig = new Text(composite, SWT.BORDER);
		txtRefinedConfig.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtRefinedConfig.addModifyListener(e -> getWidgetCallback().requestValidation());
		
		rBtnDefaultConfig = new Button(composite, SWT.RADIO);
		rBtnDefaultConfig.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		rBtnDefaultConfig.setText("Default Config");
		rBtnDefaultConfig.setSelection(false);
		rBtnDefaultConfig.addSelectionListener(SelectionListener.widgetSelectedAdapter(e -> {
			settings.setUseDefaultConfig(rBtnDefaultConfig.getSelection());
			getWidgetCallback().requestValidation();
		}));

		Button btnChooseDocumenttype = new Button(composite, SWT.NONE);
		GridData gd_btnChooseDocumenttype = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnChooseDocumenttype.widthHint = 164;
		btnChooseDocumenttype.setLayoutData(gd_btnChooseDocumenttype);
		btnChooseDocumenttype.setText("Choose Document Type");
		btnChooseDocumenttype.addSelectionListener(SelectionListener.widgetSelectedAdapter(e -> {
			String ePackageNsUri = EcoreSelectionDialogUtil.selectRegisteredPackage(
					Display.getCurrent().getActiveShell(), new ResourceSetImpl()).getNsURI();
			txtDefaultConfig.setText(ePackageNsUri);
			settings.setUseDefaultConfig(true);
			settings.setMetaModelNsUri(ePackageNsUri);
			getWidgetCallback().requestValidation();
		}));

		txtDefaultConfig = new Text(composite, SWT.BORDER);
		txtDefaultConfig.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtDefaultConfig.addModifyListener(e -> getWidgetCallback().requestValidation());
		if(this.settings.getConfigPath() != null){
			txtRefinedConfig.setText(this.settings.getConfigPath());
		}
		
		final Button cbtnSubfolder = new Button(composite, SWT.CHECK);
		GridData gd_cbtnSubfolder = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 3, 1);
		gd_cbtnSubfolder.widthHint = 449;
		cbtnSubfolder.setLayoutData(gd_cbtnSubfolder);
		cbtnSubfolder.setText("Create sub-folders for transformation kinds (create, delete, ...)");
		cbtnSubfolder.setSelection(settings.isUseSubfolders());
		cbtnSubfolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {			
				settings.setUseSubfolders(cbtnSubfolder.getSelection());
			}});
		return group;
	}

	@Override
	public boolean validate() {
		if (rBtnDefaultConfig.getSelection() && txtDefaultConfig.getText().isEmpty()) {
			message = new ValidationMessage(ValidationType.ERROR, "Documenttype is not set.");
			return false;
		} else if (rBtnRefinedConfig.getSelection() && txtRefinedConfig.getText().isEmpty()) {
			message = new ValidationMessage(ValidationType.ERROR, "Configuration Path is missing");
			return false;
		}
		message = ValidationMessage.OK;
		return true;
	}

	@Override
	public ValidationMessage getValidationMessage() {		
		return message;			
	}

	@Override
	public Composite getWidget() {
		return group;
	}

	public void setSettings(EditRuleGenerationSettings settings) {
		this.settings = settings;
	}

	public EditRuleGenerationSettings getSettings() {
		return settings;
	}
}
