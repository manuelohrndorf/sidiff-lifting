package org.sidiff.slicer.structural.configuration.preferences;

/**
 * Constant definitions for plug-in preferences
 */
public class PreferenceConstants
{
	/**
	 * The default text color of elements in the imported models.
	 */
	public static final String TEXT_COLOR_DEFAULT = "textColorDefault"; //$NON-NLS-1$

	/**
	 * The text color of referenced elements in the imported models.
	 */
	public static final String TEXT_COLOR_REFERENCED = "textColorReferenced"; //$NON-NLS-1$

	/**
	 * The text color of the decoration for overriding references in the imported models.
	 */
	public static final String TEXT_COLOR_OVERRIDE = "textColorOverride"; //$NON-NLS-1$

	/**
	 * The background color of dangling references in the imported models.
	 */
	public static final String BACKGROUND_COLOR_DANGLING_REFERENCE = "backgroundDanglingReference"; //$NON-NLS-1$

	/**
	 * The default text style of elements in the imported models.
	 */
	public static final String TEXT_STYLE_DEFAULT = "textStyleDefault"; //$NON-NLS-1$

	/**
	 * The text style of referenced elements in the imported models.
	 */
	public static final String TEXT_STYLE_REFERENCED = "textStyleReferenced"; //$NON-NLS-1$

	/**
	 * The text style of referenced elements with multiplicity &gt;= 1 in the imported models.
	 */
	public static final String TEXT_STYLE_REFERENCED_MULTI = "textStyleReferencedMulti"; //$NON-NLS-1$

	/**
	 * Users choice whether the confirmation dialog should be shown if elements with constraints are removed.
	 */
	public static final String DONT_ASK_AGAIN_CONSTRAINT_REMOVAL = "dontAskAgain_constraintRemoval"; //$NON-NLS-1$
}
