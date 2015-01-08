package org.sidiff.difference.lifting.ui.widgets;

import java.util.ArrayList;
import java.util.Iterator;
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
import org.sidiff.difference.technical.ITechnicalDifferenceBuilder;
import org.sidiff.difference.technical.IncrementalTechnicalDifferenceBuilder;
import org.silift.common.util.ui.widgets.IWidget;
import org.silift.common.util.ui.widgets.IWidgetSelection;
import org.silift.common.util.ui.widgets.IWidgetValidation;

public class DifferenceBuilderWidget implements IWidget, IWidgetSelection, IWidgetValidation, ISettingsChangedListener {

	private LiftingSettings settings;
	
	private InputModels inputModels;

	private SortedMap<String, ITechnicalDifferenceBuilder> builders;
	private Composite container;
	private List list_builders;
	
	private boolean multiSelection;

	public DifferenceBuilderWidget(InputModels inputModels) {
		this.inputModels = inputModels;
		multiSelection = inputModels.getDocumentTypes().size()>1? true: false;
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

		list_builders = new List(container, multiSelection? SWT.MULTI : SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL);
		{
			GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
			data.heightHint = 70;
			list_builders.setLayoutData(data);
		}
		list_builders.setItems(builders.keySet().toArray(new String[0]));

		if(list_builders.getItems().length != 0){
			list_builders.select(0);
		}else{
			MessageDialog.openError(
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
					"Missing Difference Builder", "No difference builders are found!");
		}
		
		list_builders.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				((LiftingSettings)settings).setTechBuilder(getSelection());
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
			builders.put(builder.getName(), builder);
		}
	}

	public ITechnicalDifferenceBuilder getSelection() {
		if (validate()) {
			if(list_builders.getSelection().length > 1){
				ArrayList<ITechnicalDifferenceBuilder> tecBuilders = new ArrayList<ITechnicalDifferenceBuilder>();
				for(String key : list_builders.getSelection()){
					tecBuilders.add(builders.get(key));
				}
				IncrementalTechnicalDifferenceBuilder incBuilder = new IncrementalTechnicalDifferenceBuilder(tecBuilders);
				return incBuilder;
			}else{
				return builders.get(list_builders.getSelection()[0]);
			}
		} else {
			return null;
		}
	}

	public SortedMap<String, ITechnicalDifferenceBuilder> getDifferenceBuilders() {
		return builders;
	}

	@Override
	public boolean validate() {
		if (list_builders.getSelectionIndex() != -1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String getValidationMessage() {
		if (validate()) {
			return "";
		} else {
			return "Please select a technical difference builder!";
		}
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