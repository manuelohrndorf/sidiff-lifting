package org.sidiff.difference.lifting.ui.widgets;

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
import org.sidiff.common.settings.ISettingsChangedListener;
import org.sidiff.common.ui.widgets.AbstractWidget;
import org.sidiff.common.ui.widgets.IWidgetSelection;
import org.sidiff.common.ui.widgets.IWidgetValidation;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.lifting.api.settings.LiftingSettingsItem;
import org.sidiff.difference.lifting.api.util.PipelineUtils;
import org.sidiff.difference.lifting.recognitionrulesorter.IRecognitionRuleSorter;
import org.sidiff.matching.input.InputModels;

public class RecognitionRuleSorterWidget extends AbstractWidget implements IWidgetSelection, IWidgetValidation, ISettingsChangedListener {

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
			updateSelection();
		}else{
			MessageDialog.openError(
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
					"Missing Recognition Rule Sorter", "No rule sorters are found!");
		}
		
		list_sorters.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				settings.setRrSorter(getSelection());
			}		
		});
		if(this.settings.getRrSorter() == null) {
			this.settings.setRrSorter(this.getSelection());
		}
		return container;
	}

	@Override
	public Composite getWidget() {
		return container;
	}

	private void getSorters() {
		// Search registered sorter extension points
		Set<IRecognitionRuleSorter> sorterSet = PipelineUtils.getAvailableRecognitionRuleSorters(inputModels.getDocumentTypes());

		sorters = new TreeMap<String, IRecognitionRuleSorter>();
		for (IRecognitionRuleSorter sorter : sorterSet) {
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
		return list_sorters.getSelectionIndex() != -1;
	}

	@Override
	public ValidationMessage getValidationMessage() {
		if (validate()) {
			return ValidationMessage.OK;
		} else {
			return new ValidationMessage(ValidationType.ERROR, "Please select a recognition rule sorter!");
		}
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
		if(item.equals(LiftingSettingsItem.RECOGNITION_RULE_SORTER)) {
			updateSelection();
			getWidgetCallback().requestValidation();
		}
	}

	private void updateSelection() {
		if(list_sorters != null) {
			if(settings.getRrSorter() != null) {
				int index = list_sorters.indexOf(settings.getRrSorter().getName());
				list_sorters.setSelection(index == -1 ? 0 : index);
			} else {
				list_sorters.setSelection(0);
			}
		}
	}

	public LiftingSettings getSettings() {
		return settings;
	}

	public void setSettings(LiftingSettings settings) {
		this.settings = settings;
		this.settings.addSettingsChangedListener(this);
		updateSelection();
	}
}