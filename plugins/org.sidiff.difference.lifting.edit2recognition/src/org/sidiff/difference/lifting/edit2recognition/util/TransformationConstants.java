package org.sidiff.difference.lifting.edit2recognition.util;

/**
 * All the constants used while transforming an edit rule into a recognition rule.
 */
public class TransformationConstants {
	
	/**
	 * The standard multi-rule semantic change set priority.
	 */
	public static final int MULTI_RULE_PRIORITY = 1;
	
	/**
	 * The standard priority for atomic edit operations.
	 */
	public static final int ATOMIC_PRIORITY = 0;
	
	/**
	 * The standard priority for complex edit operations (preferred to atomic edit operations).
	 */
	public static final int COMPLEX_PRIORITY = 1;
	
	/**
	 * The first version number of the edit rule.
	 */
	public final static String START_VERSION = "0.0.1";

	/**
	 * The prefix of all generated recognition rule (henshin) filenames (originally copied from the edit rule).
	 */
	public final static String FILE_PREFIX = "rr_";
	
	/**
	 * The prefix will be added to a new recognition rule name (originally copied from the edit rule).
	 */
	public final static String RECOGNITION_RULE_PREFIX = "rr:";
	
	/**
	 * The prefix will be added to recognition rule description (originally copied from the edit rule).
	 */
	public final static String RECOGNITION_RULE_DESCRIPTION_PREFIX = "Recognize: ";

	/**
	 * The prefix will be added to a new recognition unit (originally copied from the edit rule).
	 */
	public final static String RECOGNITION_UNIT_PREFIX = "RR: ";
	
	/**
	 * The prefix will be added to recognition unit description (originally copied from the edit rule).
	 */
	public final static String RECOGNITION_UNIT_DESCRIPTION_PREFIX = "Recognize: ";
	
	/**
	 * The prefix will be added to recognition module name (originally copied from the edit rule).
	 */
	public final static String RECOGNITION_MODULE_PREFIX = "RR: ";
	
	/**
	 * The prefix will be added to recognition module description (originally copied from the edit rule).
	 */
	public final static String RECOGNITION_MODULE_DESCRIPTION_PREFIX = "Recognize: ";
	
	/**
	 * Naming convention (prefix) for (atomic/elementary/mandatory) edit rule projects.
	 */
	public static final String PATH_SEGMENT_ATOMIC_EDITRULES = "editrules.atomic";
	
	/**
	 * Naming convention (prefix) for (complex) edit rule projects (e.g. refactorings).
	 */
	public static final String PATH_SEGMENT_COMPLEX_EDITRULES = "editrules.complex";
}
