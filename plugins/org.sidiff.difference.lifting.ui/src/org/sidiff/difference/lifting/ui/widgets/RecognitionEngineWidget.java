package org.sidiff.difference.lifting.ui.widgets;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.sidiff.common.settings.ISettingsChangedListener;
import org.sidiff.common.ui.widgets.AbstractWidget;
import org.sidiff.common.ui.widgets.IWidgetSelection;
import org.sidiff.common.ui.widgets.IWidgetValidation;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.lifting.api.settings.LiftingSettingsItem;
import org.sidiff.difference.lifting.api.settings.RecognitionEngineMode;

public class RecognitionEngineWidget extends AbstractWidget implements IWidgetSelection, IWidgetValidation, ISettingsChangedListener {

	public static final String LABEL_NO_LIFTING = "No Semantic Lifting (Operation Detection)";
	public static final String LABEL_LIFTING = "Semantic Lifting (Operation Detection)";
	public static final String LABEL_LIFTING_AND_POST_PROCESSING = "Semantic Lifting and Post Processing (Default)";
	
	public boolean showNoSemanticLifting = true;
	public boolean showSimpleLifting = true;
	public boolean showPostProcessedLifting = true;

	private LiftingSettings settings;
	private Composite container;
	private List list_recEngines;
	private java.util.List<RecognitionEngineMode> selectableModes;

	public RecognitionEngineWidget() {

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

		// Recognition engine controls:
		Label recEngineLabel = new Label(container, SWT.NONE);
		recEngineLabel.setText("Recognition Engine:");

		list_recEngines = new List(container, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL);
		{
			GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
			data.minimumHeight = 70;
			list_recEngines.setLayoutData(data);
		}

		list_recEngines.setItems(getRecognitionEnginesNames());
		updateSelection();

		list_recEngines.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				settings.setRecognitionEngineMode(getSelection());
			}
		});

		if(settings.getRecognitionEngineMode() == null) {
			settings.setRecognitionEngineMode(this.getSelection());
		}
		return container;
	}

	private String[] getRecognitionEnginesNames() {
		java.util.List<String> items = new ArrayList<String>(3);
		selectableModes = new ArrayList<RecognitionEngineMode>(3);

		if (showNoSemanticLifting) {
			items.add(LABEL_NO_LIFTING);
			selectableModes.add(RecognitionEngineMode.NO_LIFTING);
		}
		if (showSimpleLifting) {
			items.add(LABEL_LIFTING);
			selectableModes.add(RecognitionEngineMode.LIFTING);
		}
		if (showPostProcessedLifting) {
			items.add(LABEL_LIFTING_AND_POST_PROCESSING);
			selectableModes.add(RecognitionEngineMode.LIFTING_AND_POST_PROCESSING);
		}

		return items.toArray(new String[0]);
	}

	@Override
	public Composite getWidget() {
		return container;
	}

	public RecognitionEngineMode getSelection() {
		if (validate()) {
			return selectableModes.get(list_recEngines.getSelectionIndex());
		} else {
			return null;
		}
	}

	@Override
	public boolean validate() {
		return list_recEngines.getSelectionIndex() != -1;
	}

	@Override
	public ValidationMessage getValidationMessage() {
		if (validate()) {
			return ValidationMessage.OK;
		} else {
			return new ValidationMessage(ValidationType.ERROR, "Please select a recognition engine!");
		}
	}

	@Override
	public void addSelectionListener(SelectionListener listener) {
		if (list_recEngines == null) {
			throw new RuntimeException("Create controls first!");
		}
		list_recEngines.addSelectionListener(listener);
	}

	@Override
	public void removeSelectionListener(SelectionListener listener) {
		if (list_recEngines != null) {
			list_recEngines.removeSelectionListener(listener);
		}
	}

	@Override
	public void settingsChanged(Enum<?> item) {
		if(item.equals(LiftingSettingsItem.RULEBASES)) {
			if(settings.getRuleBases().isEmpty()){
				this.list_recEngines.setEnabled(false);
				this.list_recEngines.setSelection(selectableModes.indexOf(RecognitionEngineMode.NO_LIFTING));
			} else {
				this.list_recEngines.setEnabled(true);
				this.list_recEngines.setSelection(selectableModes.indexOf(RecognitionEngineMode.LIFTING_AND_POST_PROCESSING));
			}
		} else if(item.equals(LiftingSettingsItem.RECOGNITION_ENGINE_MODE)) {
			updateSelection();
			getWidgetCallback().requestValidation();
		}
	}

	private void updateSelection() {
		if(list_recEngines != null && selectableModes != null) {
			int selection = selectableModes.indexOf(settings.getRecognitionEngineMode());
			if(selection == -1) {
				selection = selectableModes.size() - 1;
			}
			list_recEngines.setSelection(selection);
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