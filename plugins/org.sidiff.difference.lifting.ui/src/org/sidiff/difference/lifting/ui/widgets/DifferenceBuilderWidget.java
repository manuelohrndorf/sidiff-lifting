package org.sidiff.difference.lifting.ui.widgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.ui.PlatformUI;
import org.sidiff.difference.lifting.facade.LiftingFacade;
import org.sidiff.difference.lifting.settings.ISettingsChangedListener;
import org.sidiff.difference.lifting.settings.LiftingSettings;
import org.sidiff.difference.lifting.settings.Settings;
import org.sidiff.difference.lifting.ui.util.InputModels;
import org.sidiff.difference.technical.GenericTechnicalDifferenceBuilder;
import org.sidiff.difference.technical.ITechnicalDifferenceBuilder;
import org.sidiff.difference.technical.IncrementalTechnicalDifferenceBuilder;
import org.silift.common.util.ui.widgets.IWidget;
import org.silift.common.util.ui.widgets.IWidgetSelection;
import org.silift.common.util.ui.widgets.IWidgetValidation;
import org.silift.common.util.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;

public class DifferenceBuilderWidget implements IWidget, IWidgetSelection, IWidgetValidation, ISettingsChangedListener {

	private LiftingSettings settings;
	
	private InputModels inputModels;

	private SortedMap<String, ITechnicalDifferenceBuilder> builders;
	private Composite container;
	private List list_builders;
	
	private boolean multiSelection;
	private boolean useGeneric;

	public DifferenceBuilderWidget(InputModels inputModels) {
		this.inputModels = inputModels;
		multiSelection = inputModels.getDocumentTypes().size()>1? true: false;
		useGeneric = false;
		getBuilders();
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

		// Technical difference builder controls:
		Label tdbLabel = new Label(container, SWT.NONE);
		tdbLabel.setText("Technical Difference Builder:");

		int swtSelection = multiSelection? SWT.MULTI : SWT.SINGLE;
		list_builders = new List(container, swtSelection | SWT.BORDER | SWT.V_SCROLL);
		{
			GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
			data.heightHint = 70;
			list_builders.setLayoutData(data);
		}
		list_builders.setItems(builders.keySet().toArray(new String[0]));

		if(list_builders.getItems().length != 0){
//			if(multiSelection){
//				for(String docType : inputModels.getDocumentTypes()){
//					for(ITechnicalDifferenceBuilder builder : builders.values()){
//						if(builder.getDocumentType().equals(docType)){
//							for(int i = 0; i < list_builders.getItems().length; i++){
//								if(list_builders.getItem(i).equals(builder.getName())){
//									list_builders.select(i);
//									break;
//								}
//							}
//							break;
//						}
//					}
//				}
//			}else{
			if(useGeneric){
				for(int i = 0; i < list_builders.getItemCount(); i++){
					if(builders.get(list_builders.getItem(i)) instanceof GenericTechnicalDifferenceBuilder){
						list_builders.select(i);
						break;
					}
				}
			}else{
				list_builders.select(0);
			}
		}else{
			MessageDialog.openError(
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
					"Missing Difference Builder", "No difference builders are found!");
		}
		
		list_builders.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ITechnicalDifferenceBuilder techBuilder = getSelection();
				((LiftingSettings)settings).setTechBuilder(techBuilder);
			}		
		});
		this.settings.setTechBuilder(this.getSelection());
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

	private void getBuilders() {
		builders = new TreeMap<String, ITechnicalDifferenceBuilder>();

		// Search registered matcher extension points
		Set<ITechnicalDifferenceBuilder> builderSet = LiftingFacade
				.getAvailableTechnicalDifferenceBuilders(inputModels.getDocumentTypes());

		for (Iterator<ITechnicalDifferenceBuilder> iterator = builderSet.iterator(); iterator.hasNext();) {
			ITechnicalDifferenceBuilder builder = iterator.next();
			if(builder instanceof GenericTechnicalDifferenceBuilder) useGeneric = true;
			builders.put(builder.getName(), builder);
		}
	}

	public ITechnicalDifferenceBuilder getSelection() {
		//if (validate()) {
			if(list_builders.getSelectionCount() > 1){
				ArrayList<ITechnicalDifferenceBuilder> tecBuilders = new ArrayList<ITechnicalDifferenceBuilder>();
				for(String key : list_builders.getSelection()){
					tecBuilders.add(builders.get(key));
				}
				IncrementalTechnicalDifferenceBuilder incBuilder = new IncrementalTechnicalDifferenceBuilder(tecBuilders);
				return incBuilder;
			}else{
				return builders.get(list_builders.getSelection()[0]);
			}
//		} else {
//			return null;
//		}
	}

	public SortedMap<String, ITechnicalDifferenceBuilder> getDifferenceBuilders() {
		return builders;
	}

	@Override
	public boolean validate() {
		if (list_builders.getSelectionIndex() != -1) {
			if(list_builders.getSelectionCount() > 1){
				for(String key : list_builders.getSelection()){
					if(builders.get(key) instanceof GenericTechnicalDifferenceBuilder){
						return false;
					}
				}
			}
			
			
			
			
			
			if(multiSelection){
				Set<String> supportedDocTypes = new HashSet<String>();
				for(String s : list_builders.getSelection()){
					ITechnicalDifferenceBuilder builder = builders.get(s);
					supportedDocTypes.add(builder.getDocumentType());
				}
				
				if(!useGeneric){
					for(String docType : inputModels.getDocumentTypes()){
						if(!supportedDocTypes.contains(docType)){
							return false;
						}
					}
				}
			}
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
		}else{
			if(list_builders.getSelectionIndex() == -1){
				message = new ValidationMessage(ValidationType.ERROR, "Please select a technical difference builder!");
			}else if(list_builders.getSelectionCount() > 1){
				boolean isGeneric = false;
				for(String key : list_builders.getSelection()){
					if(builders.get(key) instanceof GenericTechnicalDifferenceBuilder){
						isGeneric = true;
						break;
					}
				}
				if(isGeneric){
					message = new ValidationMessage(ValidationType.ERROR, "Please select a technical difference builder for every document type!");
				}else{
					message = new ValidationMessage(ValidationType.WARNING, "unexpected");
					//TODO Warning
				}
			}else{
				message = new ValidationMessage(ValidationType.ERROR, "Unexpected error due to the selection of a technical difference builder");
			}
		}
		
		return message;
		
	}

	@Override
	public void addSelectionListener(SelectionListener listener) {
		if (list_builders == null) {
			throw new RuntimeException("Create controls first!");
		}
		list_builders.addSelectionListener(listener);
	}

	@Override
	public void removeSelectionListener(SelectionListener listener) {
		if (list_builders != null) {
			list_builders.removeSelectionListener(listener);
		}
	}

	@Override
	public void settingsChanged(Enum<?> item) {
	}

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(LiftingSettings settings) {
		this.settings = settings;
		this.settings.addSettingsChangedListener(this);
	}
}