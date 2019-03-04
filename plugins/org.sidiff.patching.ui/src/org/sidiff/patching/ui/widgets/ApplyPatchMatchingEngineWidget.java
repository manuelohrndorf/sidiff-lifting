package org.sidiff.patching.ui.widgets;

import java.util.Collections;
import java.util.stream.Collectors;

import org.eclipse.jface.dialogs.ControlEnableState;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.sidiff.common.emf.input.InputModels;
import org.sidiff.common.ui.util.MessageDialogUtil;
import org.sidiff.common.ui.widgets.IWidgetValidation;
import org.sidiff.difference.technical.ui.widgets.MatchingEngineWidget;
import org.sidiff.matcher.IMatcher;
import org.sidiff.matching.api.settings.MatchingSettings;
import org.sidiff.patching.patch.patch.Patch;

public class ApplyPatchMatchingEngineWidget extends MatchingEngineWidget implements IWidgetValidation {

	private Patch patch;
	private Button use_manifest;
	
	private ControlEnableState enableState;

	public ApplyPatchMatchingEngineWidget(Patch patch, MatchingSettings settings) {
		super(new InputModels(
				patch.getAsymmetricDifference().getOriginModel(),
				patch.getAsymmetricDifference().getChangedModel()), settings);
		this.patch = patch;
		super.setLowerUpperBounds(1, 1);
	}

	@Override
	protected Composite createContents(Composite container) {
		Composite contents = new Composite(container, SWT.NONE);
		contents.setLayout(new GridLayout(1, false));

		use_manifest = new Button(contents, SWT.CHECK);
		use_manifest.setText("Use manifest");
		use_manifest.addSelectionListener(SelectionListener.widgetSelectedAdapter(event -> {
			if(!use_manifest.getSelection()) {
				updateParentControlEnabled(true);
				// reset selection using the super class implementation
				setSelection(getSettingsMatchers());
			} else {
				updateParentControlEnabled(false);
				String matcherName = patch.getSettings().get("matcher");
				setSelection(matchers.stream()
						.filter(m -> m.getName().equals(matcherName))
						.collect(Collectors.toList()));
			}
		}));

		super.createContents(contents);
		return contents;
	}

	protected void updateParentControlEnabled(boolean enabled) {
		if(enabled) {
			if(enableState != null) {
				enableState.restore();
				enableState = null;
			}
		} else {
			if(enableState == null) {
				enableState = ControlEnableState.disable(contents);
			}
		}
	}

	@Override
	protected void hookInitSelection() {
		// get the matcher from the manifest
		String matcherName = patch.getSettings().get("matcher");
		if(matcherName != null) {
			IMatcher item = matchers.stream().filter(m -> m.getName().equals(matcherName)).findFirst().orElse(null);
			if(item != null) {
				use_manifest.setSelection(true);
				use_manifest.setEnabled(true);
				setSelection(Collections.singletonList(item));
				updateParentControlEnabled(false);
			} else {
				use_manifest.setSelection(false);
				use_manifest.setEnabled(false);
				setSelection(Collections.emptyList());
				updateParentControlEnabled(true);
				MessageDialogUtil.showMessageDialog("Missing Matcher", "Corresponding matcher is not found! Please choose another one.");
			}
		} else {
			use_manifest.setEnabled(false);
			updateParentControlEnabled(true);
		}

		super.hookInitSelection();
	}
}