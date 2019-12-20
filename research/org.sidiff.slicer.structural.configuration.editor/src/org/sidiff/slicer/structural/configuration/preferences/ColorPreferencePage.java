package org.sidiff.slicer.structural.configuration.preferences;

import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.sidiff.slicer.structural.configuration.presentation.ConfigurationEditorPlugin;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing {@link FieldEditorPreferencePage}, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

public class ColorPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage
{
	/**
	 * The ID of the preference page
	 */
	public static final String ID = "org.sidiff.slicer.structural.configuration.preferences.ColorPreferencePage"; //$NON-NLS-1$

	/**
	 * Create the preference page with a grid-layout.
	 */
	public ColorPreferencePage()
	{
		super(GRID);
		setPreferenceStore(ConfigurationEditorPlugin.getPlugin().getPreferenceStore());
	}

	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	@Override
	public void createFieldEditors()
	{
		// the displayed text and values for the text style combo boxes
		final String textStyles[][] = new String[][]
		{
			new String[] { ConfigurationEditorPlugin.INSTANCE.getString("_UI_ColorPreference_Font_Normal"), 	Integer.toString(SWT.NORMAL) }, //$NON-NLS-1$
			new String[] { ConfigurationEditorPlugin.INSTANCE.getString("_UI_ColorPreference_Font_Italic"), 	Integer.toString(SWT.ITALIC) }, //$NON-NLS-1$
			new String[] { ConfigurationEditorPlugin.INSTANCE.getString("_UI_ColorPreference_Font_Bold"), 		Integer.toString(SWT.BOLD) }, //$NON-NLS-1$
			new String[] { ConfigurationEditorPlugin.INSTANCE.getString("_UI_ColorPreference_Font_BoldItalic"), Integer.toString(SWT.ITALIC|SWT.BOLD) }, //$NON-NLS-1$
		};

		addField(new ColorFieldEditor(PreferenceConstants.TEXT_COLOR_DEFAULT,
				ConfigurationEditorPlugin.INSTANCE.getString("_UI_ColorPreference_DefaultColor"), getFieldEditorParent())); //$NON-NLS-1$
		addField(new ComboFieldEditor(PreferenceConstants.TEXT_STYLE_DEFAULT, 
				ConfigurationEditorPlugin.INSTANCE.getString("_UI_ColorPreference_DefaultStyle"), textStyles, getFieldEditorParent())); //$NON-NLS-1$
		addField(new ColorFieldEditor(PreferenceConstants.TEXT_COLOR_REFERENCED,
				ConfigurationEditorPlugin.INSTANCE.getString("_UI_ColorPreference_ReferencedColor"), getFieldEditorParent())); //$NON-NLS-1$
		addField(new ComboFieldEditor(PreferenceConstants.TEXT_STYLE_REFERENCED, 
				ConfigurationEditorPlugin.INSTANCE.getString("_UI_ColorPreference_ReferencedStyle"), textStyles, getFieldEditorParent())); //$NON-NLS-1$
		addField(new ComboFieldEditor(PreferenceConstants.TEXT_STYLE_REFERENCED_MULTI, 
				ConfigurationEditorPlugin.INSTANCE.getString("_UI_ColorPreference_ReferencedMultiStyle"), textStyles, getFieldEditorParent())); //$NON-NLS-1$
		addField(new ColorFieldEditor(PreferenceConstants.TEXT_COLOR_OVERRIDE,
				ConfigurationEditorPlugin.INSTANCE.getString("_UI_ColorPreference_OverrideColor"), getFieldEditorParent())); //$NON-NLS-1$
		addField(new ColorFieldEditor(PreferenceConstants.BACKGROUND_COLOR_DANGLING_REFERENCE,
				ConfigurationEditorPlugin.INSTANCE.getString("_UI_ColorPreference_DanglingReference"), getFieldEditorParent())); //$NON-NLS-1$
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	@Override
	public void init(IWorkbench workbench)
	{
	}
}