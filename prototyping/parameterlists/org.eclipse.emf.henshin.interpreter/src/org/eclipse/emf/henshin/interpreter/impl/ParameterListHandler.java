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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.info.RuleInfo;
import org.eclipse.emf.henshin.interpreter.matching.conditions.ConditionHandler;
import org.eclipse.emf.henshin.interpreter.matching.constraints.DomainSlot;
import org.eclipse.emf.henshin.interpreter.matching.constraints.Variable;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;

/**
 * A parameter-list-handler stores all parameter assignments of the corresponding rule. It handles
 * the list iteration during the solution computation and creates (if necessary) sub-lists for each
 * solution that was found. A sub-list is a parameter-list that will be passed to possible sub-rules
 * on a deeper multi-rule level.
 * 
 * @author Manuel Ohrndorf <code>{mohrndorf@informatik.uni-siegen.de}</code>
 * 
 * @see {@link ParameterList}
 */
public class ParameterListHandler {

	/**
	 * This parameter-list-handler is 'active' if it has at least one active parameter-list.
	 * 
	 * @see {@link ParameterListHandler#isActive()}
	 * @see {@link ParameterListHandler#initialize(Map, ConditionHandler)}
	 */
	private boolean isActive = false;
	
	/**
	 * The actual position of the parameter-list iteration:
	 */
	private int index = -1;

	/**
	 * All active parameter-lists must have the same size. The first assigned
	 * parameter-list will set this value.
	 */
	private int size = -1;
	
	/**
	 * The variable domain map needed for {@link ParameterListHandler#variableAssignments}:
	 */
	private Map<Variable, DomainSlot> domainMap;
	
	/**
	 * The condition handler needed for {@link ParameterListHandler#conditionAssignments}:
	 */
	private ConditionHandler conditionHandler;
	
	/**
	 * Active variable assignments:
	 * 
	 * @see {@link ParameterListHandler#setVariableParameter(RuleInfo, Parameter, ParameterList)}
	 * @see {@link ParameterListHandler#setNextEntries()}
	 * @see {@link ParameterListHandler#setNextVariableEntries()}:
	 */
	private List<Assignment<Variable>> variableAssignments;

	/**
	 * Active attribute value condition assignments:
	 * 
	 * @see {@link ParameterListHandler#setConditionParameter(Parameter, ParameterList)}
	 * @see {@link ParameterListHandler#setNextEntries()}
	 * @see {@link ParameterListHandler#setNextConditionEntries()}
	 */
	private List<Assignment<String>> conditionAssignments;

	/**
	 * Assignments of sub-rules (sub-list) -> inactive on the rule corresponding to this handler:
	 */
	private List<Assignment<Parameter>> inactiveAssignments;
	
	/**
	 * Stores an (internal) assignment of a parameter-list to its target.
	 *
	 * @param <T> Type of the target: Variable/String/Parameter
	 */
	private class Assignment<T> { 
		T target;
		ParameterList<?> list;
		
		public Assignment(T target, ParameterList<?> list) {
			super();
			this.target = target;
			this.list = list;
		}
	}
	
	/**
	 * Create a new parameter-list-handler.
	 */
	public ParameterListHandler() {
		conditionAssignments = Collections.emptyList();
		variableAssignments = Collections.emptyList();
		inactiveAssignments = Collections.emptyList();
	}
	
	/**
	 * Call this function after all parameter-lists were set.
	 * 
	 * @param domainMap
	 *            Variable domain map.
	 * @param conditionHandler
	 *            Attribute condition handler.
	 */
	public void initialize(Map<Variable, DomainSlot> domainMap, ConditionHandler conditionHandler) {
		if (!variableAssignments.isEmpty() || !conditionAssignments.isEmpty()) {
			this.conditionHandler = conditionHandler;
			this.domainMap = domainMap;
			isActive = true;
		} else {
			isActive = false;
		}
	}
	
	/**
	 * Assign a new parameter-list to a parameter.
	 * 
	 * @param ruleInfo
	 *            The rule-info containing the node to variable mappings.
	 * @param parameter
	 *            The parameter.
	 * @param values
	 *            The parameter-list which will be assigned to the parameter.
	 */
	public void setParameter(RuleInfo ruleInfo, Parameter parameter, ParameterList<?> values) {
		
		// Filter all parameters which are (implicitly) mapped to sub rules:
		if (!isMappedMultiRuleParameter(ruleInfo.getRule(), parameter.getName())) {
			
			// Set variable parameter list:
			boolean isVariableParameter = setVariableParameter(ruleInfo, parameter, values);
			
			// Set conditional parameter list (implicit assumption):
			if (!isVariableParameter) {
				setConditionParameter(parameter, values);
			}
		} else {
			setInactiveParameter(parameter, values);
		}
	}
	
	/**
	 * Assign a new active parameter-list to a node/variable.
	 * 
	 * @param ruleInfo
	 *            The rule-info containing the node to variable mappings.
	 * @param parameter
	 *            The parameter.
	 * @param values
	 *            The parameter-list which will be assigned to the parameter.
	 * @return <code>true</code> if the parameter-list could be assigned to
	 *         node/variable with the same name as the parameter;
	 *         <code>false</code> otherwise.
	 */
	private boolean setVariableParameter(RuleInfo ruleInfo, Parameter parameter, ParameterList<?> values) {
		// Check whether there are nodes with the same name as the parameter:
		boolean isSet = false;
		
		for (Entry<Node, Variable> node2var : ruleInfo.getVariableInfo().getNode2variable().entrySet()) {
			Node node = node2var.getKey();
			Variable variable = node2var.getValue();
				
			if (parameter.getName().equals(node.getName())) {
				if (variableAssignments == Collections.EMPTY_LIST) {
					variableAssignments = new ArrayList<Assignment<Variable>>();
				}
				variableAssignments.add(new Assignment<Variable>(variable, values));
				isSet = true;
			}
		}
		
		if (isSet && !checkSize(values)) {
			throw new RuntimeException("Incorrect size of paramter-list (" 
					+ parameter.getName() + "): " + values 
					+ "\n" + "  Size: " + values.size()
					+ "\n" + "  Expected size: " + size);
		}
		
		return isSet;
	}

	/**
	 * Assign a new active parameter-list as attribute condition.
	 * 
	 * @param parameter
	 *            The parameter.
	 * @param values
	 *            The parameter-list which will be assigned to the parameter.
	 */
	private void setConditionParameter(Parameter parameter, ParameterList<?> values) {		
		if (checkSize(values)) {
			if (conditionAssignments == Collections.EMPTY_LIST) {
				conditionAssignments = new ArrayList<Assignment<String>>();
			}
			conditionAssignments.add(new Assignment<String>(parameter.getName(), values));
		} else {
			throw new RuntimeException("Incorrect size of paramter-list (" 
					+ parameter.getName() + "): " + values 
					+ "\n" + "  Size: " + values.size()
					+ "\n" + "  Expected size: " + size);
		}
	}
	
	/**
	 * Assign a new inactive parameter-list.
	 * 
	 * @param parameter
	 *            The parameter.
	 * @param values
	 *            The parameter-list which is assigned to the parameter.
	 */
	private void setInactiveParameter(Parameter parameter, ParameterList<?> values) {
		if (inactiveAssignments == Collections.EMPTY_LIST) {
			inactiveAssignments = new ArrayList<Assignment<Parameter>>();
		}
		inactiveAssignments.add(new Assignment<Parameter>(parameter, values));
	}
	
	/**
	 * Check if the given parameter is implicitly mapped to a sub rule.
	 * 
	 * @param rule
	 *            The rule containing the parameter.
	 * @param parameterName
	 *            The name of the parameter.
	 * @return <code>true</code> if the given parameter is mapped to a sub-rule;
	 *         <code>false</code> otherwise.
	 */
	private static boolean isMappedMultiRuleParameter(Rule rule, String parameterName) {
		for (Rule multiRule : rule.getMultiRules()) {
			for (Parameter multiParameter : multiRule.getParameters()) {
				if (multiParameter.getName().equals(parameterName)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * All active parameter-lists must have the same size.
	 * 
	 * @param values
	 *            The parameter-list to test.
	 * @return <code>true</code> if the given list has the same size as the other
	 *         lists; <code>false</code> otherwise.
	 */
	private boolean checkSize(ParameterList<?> values) {
		if (size == -1) {
			size = values.size();
		} else if (values.size() != size) {
			return false;
		}
		return true;
	}

	/**
	 * <p>
	 * Sets the next variable/condition assignments. The parameter-list-handler
	 * steps through the values of each active parameter-list in parallel. e.g.
	 * if there are the lists: [x1, x2, ..., xN] and [y1, y2,... yN]. The
	 * assignment steps (for each call of this function) are: {x1, y1}, {x2,
	 * y2}, ... , {xN, yN}.
	 * </p>
	 * <p>
	 * A parameter-list is called 'active' if the corresponding parameter is not
	 * mapped to a deeper level multi-rule (sub-rule) and if it could be
	 * assigned to a node/variable (with same name as the parameter). (A
	 * parameter is mapped implicitly by its name to a sub-rule.)
	 * </p>
	 * 
	 * @return <code>true</code> if the next entry was set; <code>false</code>
	 *         if there are no more entry's available.
	 * 
	 * @see {@link DomainSlot#fixInstantiation(EObject)}
	 * @see {@link ConditionHandler#setParameter(String, Object)}
	 */
	public boolean setNextEntries() {
		if (isActive) {
			index++;
			
			if (index >= size) {
				return false;
			}
			
			setNextConditionEntries();
			setNextVariableEntries();
			
			return true;
		} else {
			throw new RuntimeException("This parameter-list-handler wasn't initialized!");
		}
	}

	/**
	 * Set the next condition assignments.
	 * 
	 * @see {@link ConditionHandler#setParameter(String, Object)}
	 */
	private void setNextConditionEntries() {
		for (Assignment<String> assignment : conditionAssignments) {
			String paramName = assignment.target;
			Object value = assignment.list.get(index).getParameterValue();
			
			conditionHandler.setParameter(paramName, value);
		}
	}
	
	/**
	 * Set the next variable assignments.
	 * 
	 * @see {@link DomainSlot#fixInstantiation(EObject)}
	 */
	private void setNextVariableEntries() {
		for (Assignment<Variable> assignment : variableAssignments) {
			DomainSlot slot = domainMap.get(assignment.target);
			Object value = assignment.list.get(index).getParameterValue();

			if (value instanceof EObject) {
				slot.fixInstantiation((EObject) value);
			} else {
				throw new RuntimeException(
						"Parameter node assignments may only be of type EObject!");
			}
		}
	}
	
	/**
	 * <p>
	 * Calculates the parameter value subtrees corresponding to the actual
	 * active values of this parameter-list-handler.
	 * </p>
	 * <p>
	 * A parameter-list itself is called active if the corresponding parameter
	 * is not mapped to a deeper level multi-rule (sub-rule) and if it could be
	 * assigned to a node/variable (with same name as the parameter). (A
	 * parameter is mapped implicitly by its name to a sub-rule.) A value of a
	 * parameter-list is called active if it is actually assigned to a variable
	 * or set to the condition-handler (by this parameter-list-handler).
	 * </p>
	 * <p>
	 * A parameter value subtree includes all values which are descendants of an
	 * active value. Also all (independent) inactive parameter-lists which are
	 * not descendants of an active parameter-list are included unchanged in the
	 * result.
	 * </p>
	 * 
	 * @return A mapping of each parameter (name) to its corresponding (sub)
	 *         parameter-list. A parameter-list assigns the values of the
	 *         calculated subtrees to the its parameters.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String, Object> getParameterLists() {
		
		// Table: parameter name -> calculated (sub) parameter-list
		Map<String, Object> parameterLists = new HashMap<String, Object>();
		
		if (!isUsed()) {
			return parameterLists;
		}
		
		/* Collect the active values/lists of this parameter-list handler. *
		 * Using a hash table to test them quickly by 'checkAncestors()'.  */
		Set<ParameterList<?>> activeLists = new HashSet<ParameterList<?>>();
		Set<ParameterList.Value> activeValues = new HashSet<ParameterList.Value>();

		for (Assignment<?> activeAssignment : variableAssignments) {
			activeLists.add(activeAssignment.list);
			activeValues.add(activeAssignment.list.get(index));
		}
		
		for (Assignment<?> activeAssignment : conditionAssignments) {
			activeLists.add(activeAssignment.list);
			activeValues.add(activeAssignment.list.get(index));
		}
		
		// Table: parameter-list of this handler -> calculated (sub) parameter-list:
		Map<ParameterList, ParameterList> subLists = new HashMap<ParameterList, ParameterList>();

		// Calculate the parameter value subtrees:
		for (Assignment<Parameter> inactiveAssignment : inactiveAssignments) {
			if (inactiveAssignment.list.isEmpty()) {
				/* This list is empty. => Blocks matching on the corresponding rule. *
				 * => Move this list directly to the result.                         */
				parameterLists.put(inactiveAssignment.target.getName(), inactiveAssignment.list);
			} else if (!inactiveAssignment.list.checkAncestors(activeLists)) {
				/* This list is not an descendant of an active list. *
				 * => Move this list unchanged to the result.        */
				parameterLists.put(inactiveAssignment.target.getName(), inactiveAssignment.list);
			} else {
				/* For each value of this list: Check if it is an descendant *
				 * of an active value. => Add it to the subtree.             */
				ParameterList subList = new ParameterList();
					
				for (ParameterList.Value value : inactiveAssignment.list) {
					if (value.checkAncestors(activeValues)) {	
						subList.add(value);
					}
				}
				
				if (!subList.isEmpty()) {
					// Add the new (sub) parameter-list to the result:
					parameterLists.put(inactiveAssignment.target.getName(), subList);
					subLists.put(inactiveAssignment.list, subList);		
				}
			}
		}
		
		// Set parent the parameter-lists of the new (sub) parameter-lists:
		for (Entry<ParameterList, ParameterList> subListsEntry : subLists.entrySet()) {
			ParameterList parent = subLists.get(subListsEntry.getKey().getParent());
			subListsEntry.getValue().setParent(parent);
		}
		
		return parameterLists;
	}
	
	/**
	 * This parameter-list-handler is 'active' if it has at least one active
	 * parameter-list. A parameter-list is called active if the corresponding
	 * parameter is not mapped to a deeper level multi-rule (sub-rule) and if it
	 * could be assigned to a node/variable (with same name as the parameter).
	 * (A parameter is mapped implicitly by its name to a sub-rule.)
	 * 
	 * @return <code>true</code> if this handler has some work to do (active);
	 *         <code>false</code> otherwise (inactive).
	 */
	public boolean isActive() {
		/* Set on initialize():                                              *
		/*   !variableValueLists.isEmpty() || !conditionValueLists.isEmpty() */
		return isActive;
	}
	
	/**
	 * This parameter-list-handler is 'used' if there is at least one assigned
	 * parameter-list ('active' or 'inactive').
	 * 
	 * @return <code>true</code> if there are some parameter-list assignments to
	 *         manage; <code>false</code> otherwise.
	 * 
	 * @see {@link ParameterListHandler#isActive()}
	 */
	public boolean isUsed() {
		return isActive() || !inactiveAssignments.isEmpty();
	}

	@Override
	public String toString() {
		StringBuffer print = new StringBuffer();
		
		print.append("========== ParameterListHandler ==========\n");
		print.append("Actual index: " + index + "\n");
		print.append("Size of lists: " + size + "\n");
		print.append("\n");
		
		print.append("Value parameter list assignment: (inactive parameters)\n");
		
		for (Assignment<Parameter> assignment : inactiveAssignments) {
			print.append("\n");
			print.append("  Parameter name: " + assignment.target + "\n");
			print.append("  Parameter value list: " + assignment.list + "\n");
		}
		print.append("\n");
		
		print.append("Value parameter list assignment: (active conditions)\n");
		
		for (Assignment<String> assignment : conditionAssignments) {
			print.append("\n");
			print.append("  Parameter name: " + assignment.target + "\n");
			print.append("  Parameter value list: " + assignment.list + "\n");
		}
		print.append("\n");
		
		print.append("Value parameter list assignment: (active variables)\n");
		
		for (Assignment<Variable> assignment : variableAssignments) {
			print.append("\n");
			print.append("  Parameter variable: " + assignment.target + "\n");
			print.append("  Parameter value list: " + assignment.list + "\n");
		}
		print.append("\n");

		print.append("==========================================");
		
		return print.toString();
	}
}