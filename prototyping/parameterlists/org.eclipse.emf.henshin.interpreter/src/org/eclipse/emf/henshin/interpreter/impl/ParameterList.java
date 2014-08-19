/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.interpreter.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.ecore.ENamedElement;

/**
 * <p>
 * The parameter-list is uses to assign a list of input values or 
 * input objects to a rule or a multi-rule (sub-rule) parameter.
 * </p>
 * <h1>Please note: All lists of a rule have to be of the same size!</h1>
 * <p>
 * For example: A set of assigned parameter-lists:
 * </p>
 * <ul>
 * 	<li><code>P1 => [x1, x2, ..., xN]</code></li>
 * 	<li><code>P2 => [y1, y2,... yN]</code></li>
 * </ul>
 * <p>
 * The result would be <code>N</code> matching steps with the following parameter value assignments:
 * </p>
 * <ul>
 * 	<li><code>{P1 => x1, P2 => y1}</code></li>
 * 	<li><code>{P1 => x2, P2 => y2}</code></li>
 * 	<li><code>...</code></li>
 * 	<li><code>{P1 => xN, P2 => yN}</code></li>
 * </ul>
 * <h1>The parameter values can be structured as a tree:</h1>
 * <p>
 * <code>
 * <table border="1" style="table-layout:fixed">
 *  <tr>
 * 		<th style="text-align:left; width:220px; height: 30px">rule01(list01, list02, list03)</th>
 * 		<td style="text-align:right; width:80px">list01&nbsp;=&gt;&nbsp;</td>
 * 		<td colspan="3" style="text-align:center">package01</td>
 * 		<td colspan="3" style="text-align:center">package02</td>
 * 	</tr>
 * 	<tr>
 * 		<th style="text-align:left; height: 30px">&nbsp;&nbsp;rule02(list02, list03)</th>
 * 		<td style="text-align:right">list02&nbsp;=&gt;&nbsp;</td>
 * 		<td colspan="3" style="text-align:center">class01</td>
 * 		<td style="text-align:center">class02</td>
 * 		<td colspan="2" style="text-align:center">class03</td>
 * 	</tr>
 * 	<tr>
 * 		<th style="text-align:left; height: 30px">&nbsp;&nbsp;&nbsp;&nbsp;rule03(list03)</th>
 * 		<td style="text-align:right">list03&nbsp;=&gt;&nbsp;</td>
 * 		<td style="text-align:center">attribute01</td>
 * 		<td style="text-align:center">attribute02</td>
 * 		<td style="text-align:center">attribute03</td>
 * 		<td style="text-align:center">&nbsp;</td>
 * 		<td style="text-align:center">attribute04</td>
 *  	<td style="text-align:center">attribute05</td>
 *  </tr>
 * </table>
 * </code>
 * </p>
 * <p>
 * The result would be 6 matching steps with the following parameter value assignments:
 * </p>
 * <ul>
 * 	<li><code>{rule01(list01 => package01), rule02(list02 => class01), rule03(list03 => attribute01)}</code></li>
 * 	<li><code>{rule01(list01 => package01), rule02(list02 => class01), rule03(list03 => attribute02)}</code></li>
 * 	<li><code>{rule01(list01 => package01), rule02(list02 => class01), rule03(list03 => attribute03)}</code></li>
 * </ul>
 * <ul>
 * 	<li><code>{rule01(list01 => package02), rule02(list02 => class02), <em>no match on rule03</em>}</code></li>
 * </ul>
 * <ul>
 * 	<li><code>{rule01(list01 => package02), rule02(list02 => class03), rule03(list03 => attribute04)}</code></li>
 * 	<li><code>{rule01(list01 => package02), rule02(list02 => class03), rule03(list03 => attribute05)}</code></li>
 * </ul>
 * <p>
 * <em>Please have look at the code examples for further information about the concept.</em>
 * </p>
 * 
 * @author Manuel Ohrndorf <code>{mohrndorf@informatik.uni-siegen.de}</code>
 */
public class ParameterList<T> extends ArrayList<ParameterList<T>.Value> {
	private static final long serialVersionUID = 1L;

	/**
	 * The parent parameter-list of this list.
	 */
	private ParameterList<?> parent;

	/**
	 * Creates a new <strong>root</strong> parameter-list.
	 */
	public ParameterList() {
		super();
	}

	/**
	 * Creates a new <strong>root</strong> parameter-list.
	 * 
	 * @param initialCapacity
	 *            The initial capacity of the list.
	 */
	public ParameterList(int initialCapacity) {
		super(initialCapacity);
	}

	/**
	 * Creates a new parameter-list as child of the given parent list.
	 * 
	 * @param parent
	 *            The parent parameter-list of this list.
	 */
	public ParameterList(ParameterList<?> parent) {
		super();
		this.parent = parent;
	}

	/**
	 * Creates a new parameter-list as child of the given parent list.
	 * 
	 * @param parent
	 *            The parent parameter-list of this list.
	 * @param initialCapacity
	 *            The initial capacity of the list.
	 */
	public ParameterList(ParameterList<?> parent, int initialCapacity) {
		super(initialCapacity);
		this.parent = parent;
	}

	/**
	 * @return The parent parameter-list of this list.
	 */
	public ParameterList<?> getParent() {
		return parent;
	}

	/**
	 * Set the parent parameter-list of this list.
	 * 
	 * @param parent
	 *            The parent parameter-list.
	 */
	public void setParent(ParameterList<?> parent) {
		this.parent = parent;
	}

	/**
	 * @return <code>true</code> if this list has a parent parameter-list;
	 *         <code>false</code> otherwise.
	 */
	public boolean hasParent() {
		return (this.parent != null);
	}

	/**
	 * Check the ancestors of this value against the given list.
	 * 
	 * @param potentialAncestors
	 *            A list of potential ancestors to check.
	 * @return <code>true</code> if this value has at least one of the ancestors in the given list;
	 *         <code>false</code> otherwise.
	 */
	boolean checkAncestors(final Collection<ParameterList<?>> potentialAncestors) {

		if (potentialAncestors.contains(parent)) {
			return true;
		} else {
			if (parent != null) {
				return parent.checkAncestors(potentialAncestors);
			} else {
				return false;
			}
		}
	}

	/**
	 * Utility function to calculate the depth/level of the parameter-list.
	 * 
	 * @return The depth of this list in in the parameter-list tree.
	 */
	public int getDepth() {
		return getDepth(0);
	}

	/**
	 * Counting all ancestors of this list.
	 * 
	 * @param depth
	 *            Recursion parameter.
	 * @return The number of ancestors.
	 */
	private int getDepth(int depth) {
		if (parent == null) {
			return depth;
		} else {
			depth++;
			return parent.getDepth(depth);
		}
	}

	@Override
	public String toString() {
		return super.toString() + " (list level: " + getDepth() + ")";
	}

	/**
	 * Parameter value wrapper to form a tree of parameter values.
	 * 
	 * @see {@link ParameterList#addValue(Object, Value)}
	 */
	public class Value {

		/**
		 * The parameter value.
		 */
		private T parameterValue;

		/**
		 * The parent value of this value or <code>null</code> if it is the root.
		 */
		private ParameterList<?>.Value parent;

		/**
		 * Constructs a new parameter value.
		 * 
		 * @param parameterValue
		 *            The parameter value.
		 * @param parent
		 *            The parent value of this value or <code>null</code> if it is the root.
		 * 
		 * @see {@link ParameterList#addValue(Object, Value)}
		 */
		public Value(T parameterValue, ParameterList<?>.Value parent) {
			super();
			this.parameterValue = parameterValue;
			this.parent = parent;
		}

		/**
		 * @return The parameter value.
		 */
		public T getParameterValue() {
			return parameterValue;
		}

		/**
		 * @param parameterValue
		 *            The parameter value.
		 * 
		 * @see {@link ParameterList#addValue(Object, Value)}
		 */
		public void setParameterValue(T parameterValue) {
			this.parameterValue = parameterValue;
		}

		/**
		 * @return The parent value of this value.
		 */
		public ParameterList<?>.Value getParent() {
			return parent;
		}

		/**
		 * @param parent
		 *            The parent value of this value or <code>null</code> if it is the root.
		 * 
		 * @see {@link ParameterList#addValue(Object, Value)}
		 */
		public void setParent(ParameterList<?>.Value parent) {
			this.parent = parent;
		}

		/**
		 * Check the ancestors of this value against the given list.
		 * 
		 * @param potentialAncestors
		 *            a list of potential ancestors to check.
		 * @return <code>true<code> if this value has at least one of the ancestors
		 * 	       in the given list; <code>false</code> otherwise.
		 */
		boolean checkAncestors(
				final Collection<ParameterList<?>.Value> potentialAncestors) {

			if (potentialAncestors.contains(parent)) {
				return true;
			} else {
				if (parent != null) {
					return parent.checkAncestors(potentialAncestors);
				} else {
					return false;
				}
			}
		}

		@Override
		public String toString() {
			String parameterValuePrint = parameterValue.toString();

			if (parameterValue instanceof ENamedElement) {
				parameterValuePrint = ((ENamedElement) parameterValue).getName();
			}

			if (parent != null) {
				String parentPrint = parent.parameterValue.toString();

				if (parent.parameterValue instanceof ENamedElement) {
					parentPrint = ((ENamedElement) parent.parameterValue).getName();
				}

				return "{Value: " + parameterValuePrint + "; Parent: "
						+ parentPrint + "}";
			} else {
				return "{Value: " + parameterValuePrint + "; Parent: N/A}";
			}
		}
	}

	/**
	 * Adds a new parameter value with a given parent value to the list.
	 * 
	 * @param parameterValue
	 *            The unwrapped parameter value to add.
	 * @param parentNode
	 *            The wrapped parent parameter value.
	 * @return The {@linkplain ParameterList.Value} wrapper of given parameter value.
	 * 
	 * @see {@link ParameterList#addValue(Object)}
	 * @see {@link ParameterList.Value}
	 */
	public Value addValue(T parameterValue, ParameterList<?>.Value parentNode) {
		Value pvn = new Value(parameterValue, parentNode);
		add(pvn);
		return pvn;
	}

	/**
	 * Adds a new parameter value to the list.
	 * 
	 * @param parameterValue
	 *            The unwrapped parameter value to add.
	 * @return The {@linkplain ParameterList.Value} wrapper of given parameter value.
	 * 
	 * @see {@link ParameterList#addValue(Object, Value)}
	 * @see {@link ParameterList.Value}
	 */
	public Value addValue(T parameterValue) {
		return addValue(parameterValue, null);
	}

	/**
	 * Utility function to check if all parameter values have the correct parent
	 * value. The parent value had to be contained in the parent parameter-list.
	 * 
	 * @return <code>true</code> if everything is fine; <code>false</code> otherwise.
	 * 
	 * @see {@link ParameterList#getParent()}
	 * @see {@link ParameterList.Value#getParent()}
	 */
	public boolean checkTreeStructure() {
		for (Value value : this) {
			if (!checkParent(value)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Check parent (value <-> list) structure.
	 * 
	 * @param value
	 *            The parameter value to check.
	 * @return <code>true</code> if everything is fine; <code>false</code> otherwise.
	 */
	private boolean checkParent(ParameterList<?>.Value value) {
		// This list is a root list:
		if ((value == null) && !hasParent()) {
			return true;
		}

		// Check parent (value <-> list) structure:
		if ((value != null) && (this.parent.contains(value))) {
			return true;
		}

		return false;
	}
}
