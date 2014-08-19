package org.sidiff.difference.lifting.postprocessing;

import java.util.LinkedHashSet;
import java.util.List;

/**
 * Calculates the power set of the given input set. Remember that
 * <code>Long.MAX_VALUE</code> is the limit of generated subsets in this
 * implementation.
 * 
 * @param <T>
 *            the set type.
 *            
 * @author Manuel Ohrndorf
 */
public class PowerSet<T> {

	/**
	 * The limit of generated subsets.
	 */
	private long limit = Long.MAX_VALUE;

	/**
	 * Construct power set calculator
	 * 
	 * @param limit the limit of generated subsets.
	 */
	public PowerSet(long limit) {
		super();
		this.limit = limit;
	}
	
	/**
	 * Construct power set calculator
	 */
	public PowerSet() {
		super();
	}

	/**
	 * Calculates the power set of the given input set (list)
	 * 
	 * @param inputSet
	 *            the input set
	 * @return the power set
	 */
	public LinkedHashSet<LinkedHashSet<T>> powerSet(List<T> inputSet) {

		// Create the empty power set
		LinkedHashSet<LinkedHashSet<T>> powerSet = new LinkedHashSet<LinkedHashSet<T>>();

		// The number of members of a power set is 2^n
		int n = inputSet.size();
		double powerElements = Math.pow(2, n);

		// Check limit
		if (powerElements > limit) {
			// Set subset count to limit
			powerElements = limit;
		}

		// Run a binary counter for the number of power elements
		for (long i = 0; i < powerElements; i++) {

			// Convert the binary number to a string containing n digits
			StringBuffer binaryCounter = intToBinary(i, n);

			// Create new subset
			LinkedHashSet<T> subset = new LinkedHashSet<T>();

			// Interpret 0 as "not part of the subset" and 1 as
			// "part of the subset"
			for (int j = 0; j < binaryCounter.length(); j++) {
				if (binaryCounter.charAt(j) == '1')
					subset.add(inputSet.get(j));
			}

			// Add the new subset to the power set
			powerSet.add(subset);

			// Check limit
			if ((limit != -1) && (i == limit)) {
				break;
			}
		}

		return powerSet;
	}

	/**
	 * Converts the given integer to a String representing a binary number with
	 * the specified number of digits. (Add leading zeros.)
	 * 
	 * @param number
	 *            the number to convert.
	 * @param digits
	 *            the digit count.
	 * @return the number as a binary String.
	 */
	private static StringBuffer intToBinary(long number, int digits) {

		String returner = Long.toBinaryString(number);
		StringBuffer binary = new StringBuffer(digits);
		long foundDigits = returner.length();
		long leadings = digits - foundDigits;

		for (long i = 0; i < leadings; i++) {
			binary.append("0");
		}
		
		binary.append(returner);

		return binary;
	}

	/**
	 * @return the limit of generated subsets. Remember that
	 *         <code>Long.MAX_VALUE</code> is the maximum in this
	 *         implementation.
	 */
	public long getLimit() {
		return limit;
	}

	/**
	 * Set the limit of generated subsets.
	 * 
	 * @param limit
	 *            the limit count.
	 */
	public void setLimit(long limit) {
		this.limit = limit;
	}

}
