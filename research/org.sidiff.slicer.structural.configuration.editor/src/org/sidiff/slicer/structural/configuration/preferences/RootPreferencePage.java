package org.sidiff.slicer.structural.configuration.preferences;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.sidiff.slicer.structural.configuration.presentation.ConfigurationEditorPlugin;

public class RootPreferencePage extends PreferencePage implements IWorkbenchPreferencePage
{
	public static final String ID = "org.sidiff.slicer.structural.configuration.preferences.RootPreferencePage"; //$NON-NLS-1$

	@Override
	public void init(IWorkbench workbench)
	{
	}

	@Override
	protected Control createContents(Composite parent)
	{
		Composite container = new Composite(parent, SWT.NULL);
		{
			GridLayout layout = new GridLayout();
			layout.numColumns = 1;
			layout.marginHeight = 0;
			layout.marginWidth = 0;
			container.setLayout(layout);
		}
		container.setFont(parent.getFont());

		Button resetAllDontAskMe = new Button(container, SWT.PUSH);
		resetAllDontAskMe.setText(ConfigurationEditorPlugin.getSubstitutedString("_UI_RootReference_ResetAllDonAskMeDecisions")); //$NON-NLS-1$
		resetAllDontAskMe.addListener(SWT.Selection, new Listener()
		{
			@Override
			public void handleEvent(Event event)
			{
				if(MessageDialog.openConfirm(getShell(),
						ConfigurationEditorPlugin.getSubstitutedString("_UI_RootReference_ResetDecisions"), //$NON-NLS-1$
						ConfigurationEditorPlugin.getSubstitutedString("_UI_RootReference_ResetDecisionsDesc"))) //$NON-NLS-1$
				{
					IPreferenceStore prefStore = ConfigurationEditorPlugin.getPlugin().getPreferenceStore();
					prefStore.setValue(PreferenceConstants.DONT_ASK_AGAIN_CONSTRAINT_REMOVAL, false);
				}
			}
		});

		return container;
	}
}
