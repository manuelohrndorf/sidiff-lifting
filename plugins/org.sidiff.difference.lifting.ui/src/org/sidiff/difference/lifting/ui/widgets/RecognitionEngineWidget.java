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
import org.sidiff.difference.lifting.settings.ISettingsChangedListener;
import org.sidiff.difference.lifting.settings.LiftingSettings;
import org.sidiff.difference.lifting.settings.SettingsItem;
import org.sidiff.difference.lifting.settings.LiftingSettings.RecognitionEngineMode;
import org.sidiff.difference.lifting.settings.Settings;
import org.silift.common.util.ui.widgets.IWidget;
import org.silift.common.util.ui.widgets.IWidgetSelection;
import org.silift.common.util.ui.widgets.IWidgetValidation;
import org.silift.common.util.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;

public class RecognitionEngineWidget implements IWidget, IWidgetSelection, IWidgetValidation, ISettingsChangedListener {

	public static final String NO_LIFTING = "No Semantic Lifting (Operation Detection)";
	public static final String LIFTING = "Semantic Lifting (Operation Detection)";
	public static final String LIFTING_AND_POST_PROCESSING = "Semantic Lifting and Post Processing (Default)";

	public boolean showNoSemanticLifting = true;
	public boolean showSimpleLifting = true;
	public boolean showPostProcessedLifting = true;

	private Settings settings;
	private Composite container;
	private List list_recEngines;

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
			data.heightHint = 70;
			list_recEngines.setLayoutData(data);
		}

		list_recEngines.setItems(getRecognitionEnginesNames());
		setDefaultSelection();

		list_recEngines.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(settings instanceof LiftingSettings){
					((LiftingSettings)settings).setRecognitionEngineMode(getSelection());
				}
			}
		});
		// Enable/Disable rulebase on 'no lifting':
//		addRulebaseDependency();

		((LiftingSettings)settings).setRecognitionEngineMode(this.getSelection());
		return container;
	}

//	private void addRulebaseDependency() {
//		if (rulebaseWidget != null) {
//			addSelectionListener(new SelectionAdapter() {
//				@Override
//				public void widgetSelected(SelectionEvent e) {
//					if (getSelection().equals(RecognitionEngineWidget.NO_LIFTING)) {
//						rulebaseWidget.setEnabled(false);
//					} else {
//						rulebaseWidget.setEnabled(true);
//					}
//				}
//			});
//		}
//	}

	private String[] getRecognitionEnginesNames() {
		java.util.List<String> items = new ArrayList<String>(3);

		if (showNoSemanticLifting) {
			items.add(NO_LIFTING);
		}
		if (showSimpleLifting) {
			items.add(LIFTING);
		}
		if (showPostProcessedLifting) {
			items.add(LIFTING_AND_POST_PROCESSING);
		}

		return items.toArray(new String[0]);
	}

	private void setDefaultSelection() {
		list_recEngines.select(list_recEngines.getItemCount() - 1);
	}

	@Override
	public Composite getWidget() {
		return container;
	}

	@Override
	public void setLayoutData(Object layoutData) {
		container.setLayoutData(layoutData);
	}

	public RecognitionEngineMode getSelection() {
		if (validate()) {
			String selection = list_recEngines.getSelection()[0];
			if(selection.equals(NO_LIFTING)){
				return RecognitionEngineMode.NO_LIFTING;
			}
			if(selection.equals(LIFTING)){
				return RecognitionEngineMode.LIFTING;
			}
			if(selection.equals(LIFTING_AND_POST_PROCESSING)){
				return RecognitionEngineMode.LIFTING_AND_POST_PROCESSING;
			}
			return null;
		} else {
			return null;
		}
	}

	@Override
	public boolean validate() {
		if (list_recEngines.getSelectionIndex() != -1) {
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
			message = new ValidationMessage(ValidationType.ERROR, "Please select a recognition engine!");
		}
		return message;
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
		if(item.equals(SettingsItem.RULEBASES)){
			if(settings.getRuleBases().isEmpty()){
				this.list_recEngines.select(0);
				this.list_recEngines.setEnabled(false);
			}else{
				this.list_recEngines.setEnabled(true);
				this.list_recEngines.select(2);
			}
		}
	}

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
		this.settings.addSettingsChangedListener(this);
	}
}