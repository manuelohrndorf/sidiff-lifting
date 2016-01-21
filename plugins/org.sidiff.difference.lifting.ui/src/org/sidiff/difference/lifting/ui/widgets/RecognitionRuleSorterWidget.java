package org.sidiff.difference.lifting.ui.widgets;

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
import org.sidiff.common.settings.settings.ISettingsChangedListener;
import org.sidiff.common.ui.widgets.IWidget;
import org.sidiff.common.ui.widgets.IWidgetSelection;
import org.sidiff.common.ui.widgets.IWidgetValidation;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;
import org.sidiff.difference.lifting.facade.LiftingFacade;
import org.sidiff.difference.lifting.recognitionrulesorter.IRecognitionRuleSorter;
import org.sidiff.difference.lifting.settings.LiftingSettings;
import org.sidiff.difference.lifting.settings.Settings;
import org.sidiff.difference.lifting.ui.util.InputModels;

public class RecognitionRuleSorterWidget implements IWidget, IWidgetSelection, IWidgetValidation, ISettingsChangedListener {

	private LiftingSettings settings;
	
	private InputModels inputModels;

	private SortedMap<String, IRecognitionRuleSorter> sorters;
	private Composite container;
	private List list_sorters;

	public RecognitionRuleSorterWidget(InputModels inputModels) {
		this.inputModels = inputModels;
		getSorters();
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

		// Recognition Rule Sorter controls:
		Label tdbLabel = new Label(container, SWT.NONE);
		tdbLabel.setText("Recognition Rule Sorter:");

		list_sorters = new List(container, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL);
		{
			GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
			data.heightHint = 70;
			list_sorters.setLayoutData(data);
		}
		list_sorters.setItems(sorters.keySet().toArray(new String[0]));

		if(list_sorters.getItems().length != 0){
			list_sorters.select(0);
		}else{
			MessageDialog.openError(
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
					"Missing Recognition Rule Sorter", "No rule sorters are found!");
		}
		
		list_sorters.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				((LiftingSettings)settings).setRrSorter(getSelection());
			}		
		});
		this.settings.setRrSorter(this.getSelection());
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

	private void getSorters() {
		sorters = new TreeMap<String, IRecognitionRuleSorter>();

		// Search registered sorter extension points
		Set<IRecognitionRuleSorter> sorterSet = LiftingFacade.getAvailableRecognitionRuleSorters(inputModels.getCharacteristicDocumentType());

		for (Iterator<IRecognitionRuleSorter> iterator = sorterSet.iterator(); iterator.hasNext();) {
			IRecognitionRuleSorter sorter = iterator.next();
			sorters.put(sorter.getName(), sorter);
		}
	}

	public IRecognitionRuleSorter getSelection() {
		if (validate()) {
			return sorters.get(list_sorters.getSelection()[0]);
		} else {
			return null;
		}
	}

	public SortedMap<String, IRecognitionRuleSorter> getRecognitionRuleSorters() {
		return sorters;
	}

	@Override
	public boolean validate() {
		if (list_sorters.getSelectionIndex() != -1) {
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
			message = new ValidationMessage(ValidationType.ERROR, "Please select a recognition rule sorter!");
		}
		return message;
	}

	@Override
	public void addSelectionListener(SelectionListener listener) {
		if (list_sorters == null) {
			throw new RuntimeException("Create controls first!");
		}
		list_sorters.addSelectionListener(listener);
	}

	@Override
	public void removeSelectionListener(SelectionListener listener) {
		if (list_sorters != null) {
			list_sorters.removeSelectionListener(listener);
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