package org.sidiff.difference.lifting.edit2recognition.util;

public class TransformationConstants {
	
	/**
	 * The standard Multi-Rule semantic change set priority.
	 */
	public static final int MULTI_RULE_PRIORITY = 0;
	
	/**
	 * The standard priority for atomic edit operations.
	 */
	public static final int ATOMIC_PRIORITY = 0;
	
	/**
	 * The standard priority for complex edit operations (prefered to atomic edit operations).
	 */
	public static final int COMPLEX_PRIORITY = 1;
	
	/**
	 * The first version number of the edit rule.
	 */
	public final static String START_VERSION = "0.0.1";

	public final static String FILE_PREFIX = "rr_";
	
	/**
	 * The recognition rule prefix will be added to a new recognition rule.
	 */
	public final static String RECOGNITION_RULE_PREFIX = "rr:";
	
	public final static String RECOGNITION_RULE_DESCRIPTION_PREFIX = "Recognize: ";

	/**
	 * The recognition unit prefix will be added to a new recognition unit.
	 */
	public final static String RECOGNITION_UNIT_PREFIX = "RR: ";
	
	public final static String RECOGNITION_UNIT_DESCRIPTION_PREFIX = "Recognize: ";
	
	public final static String RECOGNITION_MODULE_PREFIX = "RR: ";
	
	public final static String RECOGNITION_TS_DESCRIPTION_PREFIX = "Recognize: ";
	
	/**
	 * Naming conventions for edit rules
	 */
	public static final String PATH_SEGMENT_ATOMIC_EDITRULES = "editrules.atomic";
	public static final String PATH_SEGMENT_COMPLEX_EDITRULES = "editrules.complex";
}
