package org.sidiff.vcmsintegration.preferences.fieldeditors;

/** 
 * helper class to create a label and a value for a given type 
 * @author Felix Breitweiser
 * @param <T> the type of the input
 */
public interface ValueAndLabelProvider<T> {
	/**
	 * @param input the input element
	 * @return an array whose first element is the value and second element is the label for the corresponding input
	 */
	String[] get(T input);
}
