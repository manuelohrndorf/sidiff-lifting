package org.silift.common.util.ui.widgets;


import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.viewers.ISelection;
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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Text;
import org.sidiff.editrule.generator.settings.SergeSettings;
import org.silift.common.util.ui.EcoreSelectionDialogUtil;

public abstract class Widget implements IWidget {
	
	private ISelection selection;
	private Text txtSelectOutputFolder;
	private Text txtImportPackage;
	private Text txtConfig;
	
	private Boolean bOutputFolder;
	private Boolean bConfig;
	
	private SergeSettings settings;
	private Composite container;

	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public Composite createControl(Composite parent, final WizardPage page) {
		
		container = new Composite(parent, SWT.NULL);
		final FileDialog eConfigChooser = new FileDialog(parent.getShell());
		bConfig = false;
		bOutputFolder = false;
		container.setLayout(new GridLayout(1, false));
		
		Group grpMetamodelSpecificConfiguration = new Group(container, SWT.SHADOW_ETCHED_IN);
		grpMetamodelSpecificConfiguration.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpMetamodelSpecificConfiguration.setText("Metamodel Specific Configuration");
		grpMetamodelSpecificConfiguration.setLayout(new GridLayout(1, false));
		
		Composite comMetaModelSpecificConfiguration = new Composite(grpMetamodelSpecificConfiguration, SWT.NONE);
		comMetaModelSpecificConfiguration.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		comMetaModelSpecificConfiguration.setLayout(new GridLayout(5, false));
		
		final Button radioBtnRefinedConfig = new Button(comMetaModelSpecificConfiguration, SWT.RADIO);
		radioBtnRefinedConfig.setText("Refined Config");
		radioBtnRefinedConfig.setSelection((settings.getConfigPath()!=null));
		new Label(comMetaModelSpecificConfiguration, SWT.NONE);		

		Button btnBrowseConfig = new Button(comMetaModelSpecificConfiguration, SWT.NONE);
		btnBrowseConfig.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				settings.setConfigPath(eConfigChooser.open());
				txtConfig.setText(settings.getConfigPath());
			}
		});
		btnBrowseConfig.setText("Browse");		
		
		txtConfig = new Text(comMetaModelSpecificConfiguration, SWT.BORDER);
		txtConfig.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		if(this.settings.getConfigPath()!=null){
			txtConfig.setText(this.settings.getConfigPath());
			bConfig = true;
		}
		
		final Button radioBtnDefaultConfig = new Button(comMetaModelSpecificConfiguration, SWT.RADIO);
		radioBtnDefaultConfig.setText("Default Config");
		new Label(comMetaModelSpecificConfiguration, SWT.NONE);
		
		Button btnImportPackage = new Button(comMetaModelSpecificConfiguration, SWT.NONE);
		btnImportPackage.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnImportPackage.setText("Choose Documenttype");		
		btnImportPackage.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				@SuppressWarnings("unused")
				EPackage documentTypePackage = EcoreSelectionDialogUtil.selectRegisteredPackage(Display.getCurrent().getActiveShell(), new ResourceSetImpl());
				txtImportPackage.setText(documentTypePackage.getNsURI());				
			}
		});
		
		txtImportPackage = new Text(comMetaModelSpecificConfiguration, SWT.BORDER);
		txtImportPackage.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		if (page instanceof WizardPage){
		
		txtConfig.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				if (txtConfig.getText().length() == 0 && txtImportPackage.getText().length() == 0) {bConfig = false;} else {bConfig = true;}
				if (bConfig && bOutputFolder) {page.setPageComplete(true); page.setErrorMessage(null);} else {page.setPageComplete(false);}
				if (!bConfig && !bOutputFolder) {page.setErrorMessage("Output Folder Path and Configuration Path are missing!");}
				if (!bConfig && bOutputFolder) {page.setErrorMessage("Configuration Path is missing!");}
				if (bConfig && !bOutputFolder) {page.setErrorMessage("Output Folder Path is missing!");}
				radioBtnRefinedConfig.setSelection(true);
				radioBtnDefaultConfig.setSelection(false);
			}
		});
		
		txtImportPackage.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				if (txtConfig.getText().length() == 0 && txtImportPackage.getText().length() == 0) {bConfig = false;} else {bConfig = true;}
				if (bConfig && bOutputFolder) {page.setPageComplete(true); page.setErrorMessage(null);} else {page.setPageComplete(false);}
				if (!bConfig && !bOutputFolder) {page.setErrorMessage("Output Folder Path and Configuration Path are missing!");}
				if (!bConfig && bOutputFolder) {page.setErrorMessage("Configuration Path is missing!");}
				if (bConfig && !bOutputFolder) {page.setErrorMessage("Output Folder Path is missing!");}
				radioBtnDefaultConfig.setSelection(true);
				radioBtnRefinedConfig.setSelection(false);
			}
		});
		
		} else {
			JOptionPane.showMessageDialog(null, "keine WizardPage");
		}
		
		return container;
	}

	@Override
	public Composite getWidget() {
		return container;
	}

	@Override
	public void setLayoutData(Object layoutData) {
		container.setLayout((Layout) layoutData);
	}
}
