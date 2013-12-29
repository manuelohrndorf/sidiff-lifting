package org.silift.common.util.emf;

/**
 * Just like EMF Compare, we distinguish two comparison modes. The usual (and
 * default) case is {@link ComparisonMode#COMPARE_RESOURCE}.
 * 
 * @author kehrer
 */
public enum ComparisonMode {

	COMPARE_RESOURCE, COMPARE_RESOURCE_SET
}
