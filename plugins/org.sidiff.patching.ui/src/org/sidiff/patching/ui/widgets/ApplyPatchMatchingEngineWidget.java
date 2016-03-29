package org.sidiff.patching.ui.widgets;


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
import org.sidiff.common.settings.ISettingsChangedListener;
import org.sidiff.common.ui.widgets.IWidget;
import org.sidiff.common.ui.widgets.IWidgetSelection;
import org.sidiff.common.ui.widgets.IWidgetValidation;
import org.sidiff.difference.ui.widgets.MatchingEngineWidget;
import org.sidiff.matching.input.InputModels;
import org.sidiff.patching.patch.patch.Patch;

public class ApplyPatchMatchingEngineWidget extends MatchingEngineWidget implements IWidget, IWidgetSelection, IWidgetValidation, ISettingsChangedListener {

	private Patch patch;
	private Button use_manifest;
	


	public ApplyPatchMatchingEngineWidget(InputModels inputModels, Patch patch) {
		super(inputModels.getResources(), true);
		this.patch = patch;
		
		//Connect scope widget:
		
		getMatchers();
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
		use_manifest.setText("use manifest");
		use_manifest.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e){
				Button button = (Button) e.widget;
				if(!button.getSelection()){
					list_matchers.setEnabled(true);
					list_matchers.setSelection(0);
				}else{
					int item = getItemFromManifest();
					if(item != -1){
						list_matchers.setEnabled(false);
						list_matchers.setSelection(getItemFromManifest());
					}else{
						button.setSelection(false);
					}
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

		// Set selection:
		if(patch.getSettings().get("matcher") != null && list_matchers.getItems().length != 0){
			int item = getItemFromManifest();
			if(item != -1){
				use_manifest.setSelection(true);
				list_matchers.setSelection(item);
				list_matchers.setEnabled(false);
			}else{
				use_manifest.setSelection(false);
				list_matchers.setSelection(0);
				list_matchers.setEnabled(true);
				MessageDialog.openInformation(
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
						"Missing Matcher", "Corresponding matcher is not found! Please choose another one.");
			}
		}else if (list_matchers.getItems().length != 0) {
			//Prefer Unique identifier matcher
			int index = list_matchers.indexOf("UUID Matcher");
		
			if(index != -1)
				list_matchers.setSelection(index);
			else
				list_matchers.setSelection(0);
				
		} else {
			MessageDialog.openError(
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
					"Missing Matcher", "No matchers are found!");
		}
		list_matchers.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				settings.setMatcher(getSelection());
			}
		});
		
		settings.setMatcher(this.getSelection());
		
		return container;
	}

	private int getItemFromManifest(){
		return list_matchers.indexOf(patch.getSettings().get("matcher"));
	}
}