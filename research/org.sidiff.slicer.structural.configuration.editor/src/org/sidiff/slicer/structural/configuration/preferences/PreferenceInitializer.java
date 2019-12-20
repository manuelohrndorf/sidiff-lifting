package org.sidiff.slicer.structural.configuration.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.sidiff.slicer.structural.configuration.presentation.ConfigurationEditorPlugin;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer
{
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
	 * initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences()
	{
		final IPreferenceStore store = ConfigurationEditorPlugin.getPlugin().getPreferenceStore();
		store.setDefault(PreferenceConstants.TEXT_COLOR_DEFAULT, "0,0,0"); // black //$NON-NLS-1$
		store.setDefault(PreferenceConstants.TEXT_COLOR_REFERENCED, "128,0,0"); // dark red //$NON-NLS-1$
		store.setDefault(PreferenceConstants.TEXT_COLOR_OVERRIDE, "128,128,128"); // dark gray //$NON-NLS-1$
		store.setDefault(PreferenceConstants.BACKGROUND_COLOR_DANGLING_REFERENCE, "255,0,0"); // red //$NON-NLS-1$
		store.setDefault(PreferenceConstants.TEXT_STYLE_DEFAULT, SWT.NORMAL);
		store.setDefault(PreferenceConstants.TEXT_STYLE_REFERENCED, SWT.ITALIC);
		store.setDefault(PreferenceConstants.TEXT_STYLE_REFERENCED_MULTI, SWT.BOLD);
		store.setDefault(PreferenceConstants.DONT_ASK_AGAIN_CONSTRAINT_REMOVAL, false);
	}
}
