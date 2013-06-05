package org.sidiff.common.henshin;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationUnit;

/**
 * Utility functions for Henshin parameters.
 * 
 */
//TODO: Comment this class
public class ParameterInfo {

	public enum ParameterDirection {
		IN, OUT
	};

	public enum ParameterKind {
		OBJECT, VALUE
	};

	/**
	 * Prüft, ob Parameter ein IN, OUT oder INOUT Parameter ist.
	 * Ausschlaggebend dafür sind die ParameterMappings der Unit.
	 * 
	 * @param parameter
	 * @return
	 */
	public static ParameterDirection getParameterDirection(Parameter parameter) {
		EList<ParameterMapping> mappings = parameter.getUnit().getParameterMappings();

		boolean in = false;
		boolean out = false;
		
		// Go through the parameter mappings
		for (ParameterMapping mapping : mappings) {
			if (mapping.getTarget().equals(parameter)) {
				out = true;
			}	
			else if (mapping.getSource().equals(parameter)) {
				in = true;
			}	
		}
		
		if (in && out) {
			return ParameterDirection.IN;
		}	
		else if (out) {
			return ParameterDirection.OUT;
		}
		else if (in) {
			return ParameterDirection.IN;
		}
		else {
			assert (false) : "Unmapped parameter?: " + parameter.getName();
		}
		
		return null;
	}

	/**
	 * Checks whether the given parameter is a value parameter or a object parameter.
	 * Parameters of type EDataType or one of its subclasses are considered as object parameter.
	 * 
	 * @param parameter
	 * @return
	 */
	public static ParameterKind getParameterKind(Parameter parameter) {
		EClassifier type = getRealType(parameter);
		if (EDataType.class.isAssignableFrom(type.getClass())) {
			return ParameterKind.VALUE;
		} else {
			return ParameterKind.OBJECT;
		}
	}

	/**
	 * Get the node that is identified by this parameter.
	 * Remark: There might be a node which is identified by the given parameter.name both in LHS and in RHS. 
	 * So the second parameter <code>lhs</code> determines whether to search in LHS (true) or in RHS (false).  
	 * 
	 * Please note that this
	 * method only supports OBJECT-parameter.
	 * 
	 * @param parameter
	 * @param lhs
	 * 			true: search in LHS
	 * 			false: search in RHS
	 * 
	 * @return the Node which is finally identified by the given
	 *         object-parameter
	 */
	public static Node getIdentifiedNode(Parameter parameter, boolean lhs) {
		assert (getParameterKind(parameter).equals(ParameterKind.OBJECT)) : "Nodes are only identified by object parameters. However, "
				+ parameter.getName() + " is a value parameter!";
	
		Parameter oppositeParameter = getParameterMappingTail(parameter);
		Rule rule = (Rule) oppositeParameter.eContainer();
		
		if (lhs){
			// Check LHS
			Node node = rule.getNodeByName(oppositeParameter.getName(), true);
			if (node != null) {
				return node;
			}
		} else {
			// Check RHS
			// the next step is necessary since rule.getNodeByName searches only in LHS
			for (Node n : rule.getRhs().getNodes()) {
				if (oppositeParameter.getName().equals(n.getName())) {
					return n;
				}
			}
		}
			
		return null;
	}

	/**
	 * Gets the type of the given parameter.<br/>
	 * Works both for OBJECT and VALUE parameters.
	 * 
	 * @param parameter
	 * @return
	 */
	public static EClassifier getRealType(Parameter parameter) {
		Parameter oppositeParameter = getParameterMappingTail(parameter);
		Rule rule = (Rule) oppositeParameter.eContainer();
		Node node = null;

		// Check LHS
		node = rule.getNodeByName(oppositeParameter.getName(), true);
		if (node != null) {
			return node.getType();
		}
		
		// Check RHS
		// the next step is necessary since rule.getNodeByName searches only in LHS
		for (Node n : rule.getRhs().getNodes()) {
			if ((n.getName() != null) && (n.getName().equals(oppositeParameter.getName()))) {
				node = n;
				return node.getType();
			}
		}
		
		// node not found in RHS/LHS : oppositeParameter points to an attribute
		return findTypeOfAnAttribute(oppositeParameter);
	}

	private static EDataType findTypeOfAnAttribute(Parameter oppositeParameter) {

		// container must be Rule here
		assert (oppositeParameter.eContainer() instanceof Rule) : "Can only find attributes in Rules!";

		Rule rule = (Rule) oppositeParameter.eContainer();

		// get all attributes under all nodes of RHS
		EList<Node> rightNodes = rule.getRhs().getNodes();
		List<Attribute> rightAttributes = new ArrayList<Attribute>();
		for (Node rN : rightNodes) {
			rightAttributes.addAll(rN.getAttributes());
		}

		// get the type (if its the correct attribute)
		for (Attribute rA : rightAttributes) {
			// TODO: handle functions such as +, -, etc.
			if (rA.getValue().equals(oppositeParameter.getName())) {
				return rA.getType().getEAttributeType();
			}
		}

		// try to find attribute under LHS, if it is not under RHS

		// get all attributes under all nodes of LHS
		EList<Node> leftNodes = rule.getLhs().getNodes();
		List<Attribute> leftAttributes = new ArrayList<Attribute>();
		for (Node lN : leftNodes) {
			leftAttributes.addAll(lN.getAttributes());
		}

		// get the type (if its the correct attribute)
		for (Attribute lA : leftAttributes) {
			if (lA.getValue().equals(oppositeParameter.getName())) {
				return lA.getType().getEAttributeType();
			}
		}

		return null;
	}

	public static Parameter getParameterMappingTail(Parameter parameter){
		return getParameterMappingTail(parameter.getUnit().getParameterMappings(), parameter);
	}
	
	private static Parameter getParameterMappingTail(EList<ParameterMapping> mappings, Parameter parameter){
		Parameter oppositeParameter = null;
		
		// Go through the mappings and find the type of the opposite ending
		for (ParameterMapping mapping : mappings) {

			/** if its an outgoing-parameter (outPort) **/
			if (mapping.getTarget().equals(parameter)) {
				oppositeParameter = mapping.getSource();
				TransformationUnit unit = (TransformationUnit) oppositeParameter.eContainer();
				
				return getParameterMappingTail(unit.getParameterMappings(), oppositeParameter);
			}

			/** if its an incoming-parameter **/
			else if (mapping.getSource().equals(parameter)) {
				oppositeParameter = mapping.getTarget();
				TransformationUnit unit = (TransformationUnit) oppositeParameter.eContainer();
				
				return getParameterMappingTail(unit.getParameterMappings(), oppositeParameter);
			}
		}

		// No more mappings: We must have reached tail
		assert (parameter.eContainer() instanceof Rule) : "Unmapped Parameter " + parameter.getName();
		
		return parameter;
	}
}
