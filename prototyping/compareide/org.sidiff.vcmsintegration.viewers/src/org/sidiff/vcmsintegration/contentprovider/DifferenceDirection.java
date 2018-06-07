package org.sidiff.vcmsintegration.contentprovider;

/**
 * This enumerations stores types of directions that a difference can have.
 * 
 * @author Adrian Bingener
 *
 */
public enum DifferenceDirection {
	/**
	 * This direction is to be used when the difference is calculates to
	 * transform the left model into the right model.
	 */
	LEFT_TO_RIGHT,
	/**
	 * This direction is to be used when the difference is calculates to
	 * transform the right model into the left model.
	 */
	RIGHT_TO_LEFT;
}
