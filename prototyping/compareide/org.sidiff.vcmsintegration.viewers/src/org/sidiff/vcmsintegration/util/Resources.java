package org.sidiff.vcmsintegration.util;

import java.io.File;

/**
 * Defines constants that are used in the VCMS viewers package to access
 * resources.
 * 
 * @author Adrian Bingener
 *
 */
public class Resources {

	/**
	 * Base path to the folder that contains all resources. The path to this
	 * folder is relative to the project directory.
	 */
	private static final String RESOURCE_BASE_FOLDER = "icons";

	public static final String ICON_TOGGLE_ANCESTOR = RESOURCE_BASE_FOLDER + File.separator + "icon_toggle_ancestor.gif";
	public static final String ICON_MERGE_TO_RIGHT = RESOURCE_BASE_FOLDER + File.separator + "icon_merge_to_right.gif";
	public static final String ICON_MERGE_TO_LEFT = RESOURCE_BASE_FOLDER + File.separator + "icon_merge_to_left.gif";
	public static final String ICON_REFRESH = RESOURCE_BASE_FOLDER + File.separator + "icon_refresh.gif";
	public static final String ICON_SHOW_DIAGRAM = RESOURCE_BASE_FOLDER + File.separator + "icon_show_diagram.png";
	public static final String ICON_TOGGLE = RESOURCE_BASE_FOLDER + File.separator + "icon_toggle.gif";
	public static final String ICON_SAVE = RESOURCE_BASE_FOLDER + File.separator + "icon_save.gif";
}
