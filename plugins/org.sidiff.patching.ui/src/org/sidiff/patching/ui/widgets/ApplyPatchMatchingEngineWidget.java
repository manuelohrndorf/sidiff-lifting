package org.sidiff.patching.ui.widgets;


import java.util.Arrays;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.ui.PlatformUI;
import org.sidiff.common.ui.widgets.IWidgetSelection;
import org.sidiff.common.ui.widgets.IWidgetValidation;
import org.sidiff.difference.technical.ui.widgets.MatchingEngineWidget;
import org.sidiff.matcher.IMatcher;
import org.sidiff.patching.patch.patch.Patch;

public class ApplyPatchMatchingEngineWidget extends MatchingEngineWidget implements IWidgetSelection, IWidgetValidation {

	private Patch patch;
	private Button use_manifest;
	private boolean initialSelection = true;

	public ApplyPatchMatchingEngineWidget(Patch patch) {
		super(Arrays.asList(patch.getAsymmetricDifference().getOriginModel(),
				patch.getAsymmetricDifference().getChangedModel()), true);
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
					ApplyPatchMatchingEngineWidget.super.updateSelection();
				} else {
					list_matchers.setEnabled(false);
					list_matchers.setSelection(getItemFromManifest());
				}
				settings.setMatcher(getSelection());
			}
		});

		list_matchers = new List(container, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL);
		{
			GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
			data.heightHint = 70;
			list_matchers.setLayoutData(data);
		}
		list_matchers.setItems(matchers.keySet().toArray(new String[0]));
		list_matchers.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IMatcher selection = getSelection();
				if(selection != null) {
					settings.setMatcher(selection);
				}
			}
		});

		// Set selection:
		if(list_matchers.getItems().length != 0) {
			updateSelection();
			if(settings.getMatcher() == null) {
				settings.setMatcher(getSelection());
			}
		} else {
			MessageDialog.openError(
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
					"Missing Matcher", "No matchers are found!");
		}

		return container;
	}

	@Override
	protected void updateSelection() {
		if(list_matchers == null) {
			return;
		}

		// get the matcher from the manifest
		if(patch.getSettings().get("matcher") != null) {
			if(initialSelection || use_manifest.getSelection()) {
				int item = getItemFromManifest();
				if(item != -1) {
					use_manifest.setSelection(true);
					use_manifest.setEnabled(true);
					list_matchers.setSelection(item);
					list_matchers.setEnabled(false);
				} else {
					use_manifest.setSelection(false);
					use_manifest.setEnabled(false);
					list_matchers.deselectAll();
					list_matchers.setEnabled(true);
					if(initialSelection) {
						MessageDialog.openInformation(
								PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
								"Missing Matcher", "Corresponding matcher is not found! Please choose another one.");
					}
				}
				initialSelection = false;
			}
		} else {
			use_manifest.setEnabled(false);
			list_matchers.setEnabled(true);
		}

		if(list_matchers.getSelectionCount() == 0) {
			super.updateSelection();
		}
	}

	private int getItemFromManifest() {
		return list_matchers.indexOf(patch.getSettings().get("matcher"));
	}
}