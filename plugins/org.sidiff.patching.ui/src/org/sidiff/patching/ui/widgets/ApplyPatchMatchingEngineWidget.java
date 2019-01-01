package org.sidiff.patching.ui.widgets;


import java.util.Collections;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.sidiff.common.ui.util.MessageDialogUtil;
import org.sidiff.common.ui.widgets.IWidgetValidation;
import org.sidiff.difference.technical.ui.widgets.MatchingEngineWidget;
import org.sidiff.matcher.IMatcher;
import org.sidiff.matching.api.settings.MatchingSettings;
import org.sidiff.matching.input.InputModels;
import org.sidiff.patching.patch.patch.Patch;

public class ApplyPatchMatchingEngineWidget extends MatchingEngineWidget implements IWidgetValidation {

	private Patch patch;
	private Button use_manifest;

	public ApplyPatchMatchingEngineWidget(Patch patch, MatchingSettings settings) {
		super(new InputModels(
				patch.getAsymmetricDifference().getOriginModel(),
				patch.getAsymmetricDifference().getChangedModel()), settings);
		this.patch = patch;
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

		// Matcher controls:
		Label matchingLabel = new Label(container, SWT.NONE);
		matchingLabel.setText("Matching Engine:");

		use_manifest = new Button(container, SWT.CHECK);
		use_manifest.setText("Use manifest");
		use_manifest.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e){
				if(!use_manifest.getSelection()){
					list_matchers.setEnabled(true);
					// reset selection using the super class implementation
					setSelection(getSettingsMatchers());
				} else {
					list_matchers.setEnabled(false);
					setSelection(Collections.singletonList(matchers.get(patch.getSettings().get("matcher"))));
				}
				settings.setMatcher(getSelectedMatcher());
			}
		});

		list_matchers = new List(container, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL);
		{
			GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
			data.minimumHeight = 70;
			list_matchers.setLayoutData(data);
		}
		list_matchers.setItems(matchers.keySet().toArray(new String[0]));
		list_matchers.addSelectionListener(SelectionListener.widgetSelectedAdapter(e -> setSelection(getSelectedMatchers())));

		if(list_matchers.getItems().length != 0) {
			initSelection();
		}

		return container;
	}

	@Override
	protected void initSelection() {
		// get the matcher from the manifest
		if(patch.getSettings().get("matcher") != null) {
			if(use_manifest.getSelection()) {
				IMatcher item = matchers.get(patch.getSettings().get("matcher"));
				if(item != null) {
					use_manifest.setSelection(true);
					use_manifest.setEnabled(true);
					setSelection(Collections.singletonList(item));
					list_matchers.setEnabled(false);
				} else {
					use_manifest.setSelection(false);
					use_manifest.setEnabled(false);
					setSelection(Collections.emptyList());
					list_matchers.setEnabled(true);
					MessageDialogUtil.showMessageDialog("Missing Matcher", "Corresponding matcher is not found! Please choose another one.");
				}
			}
		} else {
			use_manifest.setEnabled(false);
			list_matchers.setEnabled(true);
		}

		super.initSelection();
	}
}