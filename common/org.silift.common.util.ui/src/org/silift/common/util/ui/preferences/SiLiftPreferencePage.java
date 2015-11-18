package org.silift.common.util.ui.preferences;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class SiLiftPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	@Override
	public void init(IWorkbench workbench) {
		// currently, we have nothing which needs to be initialized for this
		// general preference page
	}

	@Override
	protected Control createContents(Composite parent) {
		// currently, we have nothing which needs to be initialized for this
		// general preference page. So we return an empty composite
		return null;
	}

}
