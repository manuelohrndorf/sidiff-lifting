package org.sidiff.patching.ui.wsupdate.widgets;


import org.eclipse.core.resources.IFile;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.sidiff.common.settings.settings.ISettingsChangedListener;
import org.sidiff.common.ui.widgets.IWidget;
import org.sidiff.common.ui.widgets.IWidgetSelection;
import org.sidiff.common.ui.widgets.IWidgetValidation;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;
import org.sidiff.difference.lifting.settings.DifferenceSettings;
import org.sidiff.patching.ui.wsupdate.util.WSUModels;

public class WSUModelsWidget implements IWidget, IWidgetSelection, IWidgetValidation, ISettingsChangedListener {

	private DifferenceSettings settings;
	private WSUModels mergeModels;
	
	private Composite container;
	
	private Button modelMineButton1;
	private Button modelMineButton2;
	private Button modelMineButton3;
	
	private Button modelTheirsButton1;
	private Button modelTheirsButton2;
	private Button modelTheirsButton3;
	
	private Button modelBaseButton1;
	private Button modelBaseButton2;
	private Button modelBaseButton3;

	public WSUModelsWidget(WSUModels mergeModels) {
		this.mergeModels = mergeModels;
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public Composite createControl(Composite parent) {

		container = new Composite(parent, SWT.NONE);
		{
			GridLayout grid = new GridLayout(1, false);
			grid.marginWidth = 0;
			grid.marginHeight = 0;
			container.setLayout(grid);
		}


		Group modelsGroup = new Group(container, SWT.NONE);
		{
			GridLayout grid = new GridLayout(3, false);
			grid.marginWidth = 10;
			grid.marginHeight = 10;
			modelsGroup.setLayout(grid);
			modelsGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

			modelsGroup.setText("Select roles of used models:");
		}
		
		Group modelBaseGroup = new Group(modelsGroup, SWT.NONE);
		{
			GridLayout grid = new GridLayout(1, false);
			grid.marginWidth = 10;
			grid.marginHeight = 10;
			modelBaseGroup.setLayout(grid);
			modelBaseGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

			modelBaseGroup.setText("Base version");
		}
        
	    modelBaseButton1 = new Button(modelBaseGroup, SWT.RADIO);
	    modelBaseButton1.setData(mergeModels.getFileBase());
	    modelBaseButton1.setText(mergeModels.getFileBase().getName());
	    modelBaseButton1.setSelection(true);
	    mergeModels.setModelBase((IFile) modelBaseButton1.getData());
	    
	    modelBaseButton1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				mergeModels.setModelBase((IFile) modelBaseButton1.getData());
			}
		});
	    
	    modelBaseButton2 = new Button(modelBaseGroup, SWT.RADIO);
	    modelBaseButton2.setData(mergeModels.getFileMine());
	    modelBaseButton2.setText(mergeModels.getFileMine().getName());
	    
	    modelBaseButton2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				mergeModels.setModelBase((IFile) modelBaseButton2.getData());
			}
		});
	    
	    modelBaseButton3 = new Button(modelBaseGroup, SWT.RADIO); 
	    modelBaseButton3.setData(mergeModels.getFileTheirs());
	    modelBaseButton3.setText(mergeModels.getFileTheirs().getName());
	    
	    modelBaseButton3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				mergeModels.setModelBase((IFile) modelBaseButton3.getData());
			}
		});
		
		Group modelMineGroup = new Group(modelsGroup, SWT.NONE);
		{
			GridLayout grid = new GridLayout(1, false);
			grid.marginWidth = 10;
			grid.marginHeight = 10;
			modelMineGroup.setLayout(grid);
			modelMineGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

			modelMineGroup.setText("Workspace version");
		}
		
	    modelMineButton1 = new Button(modelMineGroup, SWT.RADIO);
	    modelMineButton1.setData(mergeModels.getFileBase());
	    modelMineButton1.setText(mergeModels.getFileBase().getName());
	    
	    modelMineButton1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				mergeModels.setModelMine((IFile) modelMineButton1.getData());
			}
		});
	    
	    
	    modelMineButton2 = new Button(modelMineGroup, SWT.RADIO);
	    modelMineButton2.setData(mergeModels.getFileMine());
	    modelMineButton2.setText(mergeModels.getFileMine().getName());
	    modelMineButton2.setSelection(true);
		mergeModels.setModelMine((IFile) modelMineButton2.getData());
	    
	    modelMineButton2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				mergeModels.setModelMine((IFile) modelMineButton2.getData());
			}
		});
	    
	    modelMineButton3 = new Button(modelMineGroup, SWT.RADIO);
	    modelMineButton3.setData(mergeModels.getFileTheirs());
	    modelMineButton3.setText(mergeModels.getFileTheirs().getName());
	    
	    modelMineButton3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				mergeModels.setModelMine((IFile) modelMineButton3.getData());
			}
		});
	    
		Group modelTheirsGroup = new Group(modelsGroup, SWT.NONE);
		{
			GridLayout grid = new GridLayout(1, false);
			grid.marginWidth = 10;
			grid.marginHeight = 10;
			modelTheirsGroup.setLayout(grid);
			modelTheirsGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

			modelTheirsGroup.setText("Repository version");
		}
        
	    modelTheirsButton1 = new Button(modelTheirsGroup, SWT.RADIO);
	    modelTheirsButton1.setData(mergeModels.getFileBase());
	    modelTheirsButton1.setText(mergeModels.getFileBase().getName());
	    
	    modelTheirsButton1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				mergeModels.setModelTheirs((IFile) modelTheirsButton1.getData());
			}
		});
	    
	    modelTheirsButton2 = new Button(modelTheirsGroup, SWT.RADIO);
	    modelTheirsButton2.setData(mergeModels.getFileMine());
	    modelTheirsButton2.setText(mergeModels.getFileMine().getName());
	    
	    modelTheirsButton2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				mergeModels.setModelTheirs((IFile) modelTheirsButton2.getData());
			}
		});
	    
	    modelTheirsButton3 = new Button(modelTheirsGroup, SWT.RADIO);
	    modelTheirsButton3.setData(mergeModels.getFileTheirs());
	    modelTheirsButton3.setText(mergeModels.getFileTheirs().getName());
	    modelTheirsButton3.setSelection(true);
		mergeModels.setModelTheirs((IFile) modelTheirsButton3.getData());
	    
	    modelTheirsButton3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				mergeModels.setModelTheirs((IFile) modelTheirsButton3.getData());
			}
		});
	    
	    
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

	public WSUModels getMergeModels(){
		if(validate()){
			return this.mergeModels;
		}
		else
			return null;
	}


	@Override
	public boolean validate() {
		if((modelBaseButton1.getSelection() ^ modelMineButton1.getSelection() ^ modelTheirsButton1.getSelection()) &&
				(modelBaseButton2.getSelection() ^ modelMineButton2.getSelection() ^ modelTheirsButton2.getSelection()) &&
				(modelBaseButton3.getSelection() ^ modelMineButton3.getSelection() ^ modelTheirsButton3.getSelection())){ 
			return true;
		} else {
			return false;
		}
	}

	@Override
	public ValidationMessage getValidationMessage() {
		ValidationMessage message;
		if (validate()) {
			message = new ValidationMessage(ValidationType.OK, "");
		} else {
			message = new ValidationMessage(ValidationType.ERROR, "Please define only one role for each model!");
		}
		return message;
	}

	@Override
	public void addSelectionListener(SelectionListener listener) {
		if(modelBaseButton1 == null || modelBaseButton2 == null || modelBaseButton3 == null
				|| modelMineButton1 == null || modelMineButton2 == null || modelMineButton3 == null ||
				modelTheirsButton1 == null || modelTheirsButton2 == null || modelTheirsButton3 == null){
			throw new RuntimeException("Create controls first!");
		}
		modelBaseButton1.addSelectionListener(listener);
		modelBaseButton2.addSelectionListener(listener);
		modelBaseButton3.addSelectionListener(listener);
		modelMineButton1.addSelectionListener(listener);
		modelMineButton2.addSelectionListener(listener);
		modelMineButton3.addSelectionListener(listener);
		modelTheirsButton1.addSelectionListener(listener);
		modelTheirsButton2.addSelectionListener(listener);
		modelTheirsButton3.addSelectionListener(listener);
	}

	@Override
	public void removeSelectionListener(SelectionListener listener) {
		if(modelBaseButton1 != null)
			modelBaseButton1.removeSelectionListener(listener);
		if(modelBaseButton2 != null)
			modelBaseButton2.removeSelectionListener(listener);
		if(modelBaseButton3 != null)
			modelBaseButton3.removeSelectionListener(listener);
		if(modelMineButton1 != null)
			modelMineButton1.removeSelectionListener(listener);
		if(modelMineButton2 != null)
			modelMineButton2.removeSelectionListener(listener);
		if(modelMineButton3 != null)
			modelMineButton3.removeSelectionListener(listener);
		if(modelTheirsButton1 != null)
			modelTheirsButton1.removeSelectionListener(listener);
		if(modelTheirsButton2 != null)
			modelTheirsButton2.removeSelectionListener(listener);
		if(modelTheirsButton3 != null)
			modelTheirsButton3.removeSelectionListener(listener);
	}

	@Override
	public void settingsChanged(Enum<?> item) {
	}

	public DifferenceSettings getSettings() {
		return settings;
	}

	public void setSettings(DifferenceSettings settings) {
		this.settings = settings;
		this.settings.addSettingsChangedListener(this);
	}
}
