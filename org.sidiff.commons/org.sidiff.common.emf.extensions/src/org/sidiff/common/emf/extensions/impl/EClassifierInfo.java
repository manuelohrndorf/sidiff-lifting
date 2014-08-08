package org.sidiff.common.emf.extensions.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EReference;

/**
 * An instance of this class will hold additional information for a specific EClassifier
 * that is collected by the analysis of a meta-model with the {@link EClassiferInfoManagement}.
 * These additional information (e.g. mandatory children, optional neighbors, stereotypes, 
 * attached constraints, etc.)
 * are made available by various public methods.
 * 
 * @author mrindt
 *
 */
public class EClassifierInfo {

	public static enum ConstraintType {NAME_UNIQUENESS_LOCAL, NAME_UNIQUENESS_GLOBAL};
	
	private EClassifier theEClassifier = null;
		
	private HashMap<EReference, List<EClassifier>> mandatoryChildren = new HashMap<EReference, List<EClassifier>>();
	private HashMap<EReference, List<EClassifier>> mandatoryNeighbours = new HashMap<EReference, List<EClassifier>>();
	private HashMap<EReference, List<EClassifier>> mandatoryParentContext = new HashMap<EReference, List<EClassifier>>();
	private HashMap<EReference, List<EClassifier>> mandatoryNeighbourContext = new HashMap<EReference, List<EClassifier>>();
	
	private HashMap<EReference, List<EClassifier>> optionalChildren = new HashMap<EReference, List<EClassifier>>();
	private HashMap<EReference, List<EClassifier>> optionalNeighbours = new HashMap<EReference, List<EClassifier>>();
	private HashMap<EReference, List<EClassifier>> optionalParentContext = new HashMap<EReference, List<EClassifier>>();
	private HashMap<EReference, List<EClassifier>> optionalNeighbourContext = new HashMap<EReference, List<EClassifier>>();
	
	private ArrayList<EClassifier> stereotypes = new ArrayList<EClassifier>();
	private ArrayList<EClassifier> extendedMetaClasses = new ArrayList<EClassifier>();
	
	
	private List<Mask> masks = new ArrayList<Mask>(); 
	private HashMap<ConstraintType,List<Object>> appliedConstraints = new HashMap<ConstraintType,List<Object>>();
	
	public enum Map {	MANDATORY_CHILDREN, MANDATORY_NEIGHBOURS, MANDATORY_PARENT_CONTEXT,
						OPTIONAL_CHILDREN,OPTIONAL_NEIGHBOURS, OPTIONAL_PARENT_CONTEXT,OPTIONAL_NEIGHBOUR_CONTEXT;
	}
		
	
	/**	Constructor ************************************************************************/
	
	public EClassifierInfo(EClassifier eClassifier) {
		this.theEClassifier = eClassifier;
	}
	
	/** Getter ****************************************************************************/
	
	public HashMap<EReference, List<EClassifier>> getMandatoryChildren() {
		return mandatoryChildren;
	}
	public HashMap<EReference, List<EClassifier>> getMandatoryNeighbours() {
		return mandatoryNeighbours;
	}
	public HashMap<EReference, List<EClassifier>> getMandatoryParentContext() {
		return mandatoryParentContext;
	}
	public HashMap<EReference, List<EClassifier>> getMandatoryNeighbourContext() {
		return mandatoryNeighbourContext;
	}
	public HashMap<EReference, List<EClassifier>> getOptionalChildren() {
		return optionalChildren;
	}
	public HashMap<EReference, List<EClassifier>> getOptionalNeighbours() {
		return optionalNeighbours;
	}
	public HashMap<EReference, List<EClassifier>> getOptionalParentContext() {
		return optionalParentContext;
	}
	public HashMap<EReference, List<EClassifier>> getOptionalNeighbourContext() {
		return optionalNeighbourContext;
	}
	public EClassifier getTheEClassifier() {
		return theEClassifier;
	}	
	public ArrayList<EClassifier> getStereotypes() {
		return stereotypes;
	}
	public ArrayList<EClassifier> getExtendedMetaClasses() {
		return extendedMetaClasses;
	}
	public HashMap<ConstraintType,List<Object>> getConstraintsAndFlags() {
		return appliedConstraints;
	}
	public List<Mask> getMasks() {
		return masks;
	}

	/** Setter ****************************************************************************/
	
	public void addExtendedMetaClass(EClassifier extendedMetaClass) {
		if(!extendedMetaClasses.contains(extendedMetaClass)) {
			extendedMetaClasses.add(extendedMetaClass);
		}
	}
	
	public void addStereotype(EClassifier stereotype) {
		if(!stereotypes.contains(stereotype)) {
			stereotypes.add(stereotype);
		}
	}
	
	public void addConstraint(ConstraintType ctype, List<Object> flags) {
		appliedConstraints.put(ctype, flags);
	}

	public void addMask(Mask mask) {
		if(!masks.contains(mask)) {
			masks.add(mask);
		}
	}
	
	/** Convenience methods ***************************************************************/
	
	public HashMap<EReference, List<EClassifier>> getAllDirectChildren(EClassifier childEClassifier)  {
		
		HashMap<EReference, List<EClassifier>> allDirectChildren = new HashMap<EReference, List<EClassifier>>();
		
		// get all direct children (mandatory and optional)
		allDirectChildren.putAll(getMandatoryChildren());
		allDirectChildren.putAll(getOptionalChildren());

		return allDirectChildren;
		
	}
	
	
	public HashSet<EClassifier> getClassifiersOfAttributesForEClassifier(EClassifier eClassifier)
	{
		HashSet<EClassifier> attributeClassifiers = new HashSet<EClassifier>();
		
		for (EAttribute eAttribute : eClassifier.eClass().getEAllAttributes())
			attributeClassifiers.add(eAttribute.getEType());
		
		return attributeClassifiers;
	}
	
	public HashSet<EClassifier> getAllMandatoryClassifiers()
	{
		HashSet<EClassifier> mandatoryClassifiers = new HashSet<EClassifier>();
		
		//Identify all mandatory children classifiers
		for (EReference ref : getMandatoryChildren().keySet())
		{
			for (EClassifier classifier : getMandatoryChildren().get(ref))
			if (!mandatoryClassifiers.contains(classifier))
				mandatoryClassifiers.add(classifier);
		}
		
		//Identify all mandatory neighbour classifiers
		for (EReference ref : getMandatoryNeighbours().keySet())
		{
			for (EClassifier classifier : getMandatoryNeighbours().get(ref))
			if (!mandatoryClassifiers.contains(classifier))
				mandatoryClassifiers.add(classifier);
		}
		
		//Identify all mandatory neighbour context classifiers
		for (EReference ref : getMandatoryNeighbourContext().keySet())
		{
			for (EClassifier classifier : getMandatoryNeighbourContext().get(ref))
			if (!mandatoryClassifiers.contains(classifier))
				mandatoryClassifiers.add(classifier);
		}
		
		//Identify all mandatory parent context classifiers
		for (EReference ref : getMandatoryParentContext().keySet())
		{
			for (EClassifier classifier : getMandatoryParentContext().get(ref))
			if (!mandatoryClassifiers.contains(classifier))
				mandatoryClassifiers.add(classifier);
		}
		
		//Identify all attribute classifiers
		mandatoryClassifiers.addAll(getClassifiersOfAttributesForEClassifier(theEClassifier));
			
		return mandatoryClassifiers;
	}
	
	public boolean selfMayHaveTransformations(){
		if(!optionalParentContext.isEmpty() || !optionalNeighbourContext.isEmpty()
				|| (mandatoryParentContext.isEmpty() && mandatoryNeighbourContext.isEmpty())) {
			return true;
		}
		return false;
	}
	
	public boolean hasMandatories() {
		if(!mandatoryChildren.isEmpty() || !mandatoryNeighbours.isEmpty()) {
			return true;
		}
		return false;
	}
	
	public boolean hasMasks() {
		return !masks.isEmpty();
	}
	
	public boolean isStereotype() {
		return stereotypes.isEmpty();
	}
	
	public boolean isExtendedMetaClass(){
		return extendedMetaClasses.isEmpty();
	}
	
	public boolean isConstrainedToLocalNameUniqueness() {
		return !(appliedConstraints.get(ConstraintType.NAME_UNIQUENESS_LOCAL)==null);
	}
	
	public boolean isConstrainedToGlobalNameUniqueness() {
		return !(appliedConstraints.get(ConstraintType.NAME_UNIQUENESS_GLOBAL)==null);
	}
	
	public boolean isOnlyConstrainedToGlobalNameUniqueness() {
		if( (appliedConstraints.get(ConstraintType.NAME_UNIQUENESS_LOCAL)==null) &&
		   !(appliedConstraints.get(ConstraintType.NAME_UNIQUENESS_GLOBAL)==null)) {
				return true;
			}
		return false;
	}
	
	/**
	 * This convenience method returns all mandatory parent contexts and all mandatory neighbour contexts.
	 * @return
	 * 			HashMap of EReferences (EReferences from context to EClassifier) and lists of EClassifier (contexts).
	 */
	public HashMap<EReference, List<EClassifier>> getMandatoryContexts() {
		
		HashMap<EReference, List<EClassifier>> mandatoryContexts = new  HashMap<EReference, List<EClassifier>>();
		
		mandatoryContexts.putAll(getMandatoryParentContext());
		mandatoryContexts.putAll(getMandatoryNeighbourContext());
		
		return mandatoryContexts;
	}
}
