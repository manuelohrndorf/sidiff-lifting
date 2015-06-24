package org.sidiff.difference.rulebase.ui.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;
import org.sidiff.difference.rulebase.wrapper.RuleBaseWrapper;

public class RulebasePreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	private BooleanFieldEditor extendedDependencyCheckFieldEditor;

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(PlatformUI.getPreferenceStore());
		getPreferenceStore().setDefault(RuleBaseWrapper.PREF_ENABLE_EXTENDED_DEPENDENCY_CHECK, false);
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout grid = new GridLayout(1, false);
		container.setLayout(grid);

		extendedDependencyCheckFieldEditor = new BooleanFieldEditor(
				RuleBaseWrapper.PREF_ENABLE_EXTENDED_DEPENDENCY_CHECK,
				"Enable extended complete and correct dependency analysis. [WARNING: may take hours]",
				container);
		extendedDependencyCheckFieldEditor.setPreferenceStore(getPreferenceStore());
		extendedDependencyCheckFieldEditor.load();

		return container;
	}

	@Override
	public boolean performOk() {
		extendedDependencyCheckFieldEditor.store();
		
		return super.performOk();
	}

	@Override
	public void performApply() {
		extendedDependencyCheckFieldEditor.store();
		
		super.performApply();
	}
	
	@Override
	protected void performDefaults() {
		extendedDependencyCheckFieldEditor.loadDefault();
		extendedDependencyCheckFieldEditor.store();
		
		super.performDefaults();
	}
}
