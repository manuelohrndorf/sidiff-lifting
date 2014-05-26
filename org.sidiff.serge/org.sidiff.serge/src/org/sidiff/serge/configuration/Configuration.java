package org.sidiff.serge.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.sidiff.common.emf.extensions.impl.EClassifierInfoManagement;

@SuppressWarnings("unused")
public class Configuration {
	
	// Tool specific configurations
	
	public String BASEMODELRULEFOLDERPATH	= null;

	public boolean ENABLE_DUPLICATE_FILTER = true;
	public boolean ENABLE_EXECUTION_CHECK_FILTER = true;
	public boolean ENABLE_NAME_MAPPER = true;
	
	// Meta-model dependend configurations
	
	public boolean CREATE_CREATES;
	public boolean CREATE_DELETES;
	public boolean CREATE_MOVES;
	public boolean CREATE_MOVE_UPS;
	public boolean CREATE_MOVE_DOWNS;
	public boolean CREATE_MOVE_REFERENCE_COMBINATIONS;
	public boolean CREATE_ADDS;
	public boolean CREATE_REMOVES;
	public boolean CREATE_SET_ATTRIBUTES;
	public boolean CREATE_SET_REFERENCES;
	public boolean CREATE_UNSET_ATTRIBUTES;
	public boolean CREATE_UNSET_REFERENCES;
	public boolean CREATE_CHANGE_REFERENCES;
	public boolean CREATE_CHANGE_LITERALS;
	
	public boolean MULTIPLICITY_PRECONDITIONS_INTEGRATED;
	public boolean MULTIPLICITY_PRECONDITIONS_SEPARATELY;
	public boolean CREATE_NOT_REQUIRED_AND_NOT_ID_ATTRIBUTES;
	public boolean PREVENT_INCONSISTENCY_THROUGHSKIPPING;
	
	public boolean REDUCETOSUPERTYPE_SETUNSET_ATTRIBUTES;
	public boolean REDUCETOSUPERTYPE_SETUNSET_REFERENCES;
	public boolean REDUCETOSUPERTYPE_ADDREMOVE;
	public boolean REDUCETOSUPERTYPE_CHANGE_REFERENCE;
	public boolean REDUCETOSUPERTYPE_CHANGE_LITERALS;
	public boolean REDUCETOSUPERTYPE_MOVE;
	public boolean REDUCETOSUPERTYPE_MOVE_REFERENCE_COMBINATION;
	public boolean REDUCETOSUPERTYPE_MOVE_UP;
	public boolean REDUCETOSUPERTYPE_MOVE_DOWN;
	public boolean REDUCETOSUPERTYPE_CREATEDELETE;

	public boolean LITERALSWITCHING_CHANGE;
	public boolean REFERENCESWITCHING_MOVE;	
	public boolean REFERENCESWITCHING_MOVE_UP;
	public boolean REFERENCESWITCHING_MOVE_DOWN;	
	
	public EPackage METAMODEL;
	
	public String ROOTNAME;
	public static EClassifier ROOT;
	public static Boolean ROOTECLASSCANBENESTED;
	
	public Boolean PROFILE_APPLICATION_IN_USE;
	public Boolean DISABLE_VARIANTS_BY_SUPERTYPE_REPLACEMENT;
	
	public Stack<EPackage> EPACKAGESSTACK = null;	
	public static EClassifierInfoManagement ECM  = null;
	
	private static Configuration instance = null;
	
	public static enum OperationType { CREATE,DELETE,SET_ATTRIBUTE,
										SET_REFERENCE,UNSET_ATTRIBUTE,
										UNSET_REFERENCE,
										ADD,REMOVE,CHANGE_LITERAL, CHANGE_REFERENCE,
										MOVE,MOVE_REFERENCE_COMBINATION,MOVE_UP,MOVE_DOWN; }
	

	
	public static Configuration getInstance() {
		if(instance==null) {
			instance = new Configuration();
		}
		return instance;
	}	
	
	
	/**
	 * Checks whether an eClassifier is defined as root and if it
	 * may not appear somewhere lower in containment hierarchy.
	 * @param eClassifier
	 * @return
	 */	
	public Boolean isAnUnnestableRoot(EClassifier eClassifier) {
		if(ROOT==eClassifier && !ROOTECLASSCANBENESTED ) {
			return true;
		}
		return false;
	}

	
	/**
	 * Checks whether an eClassifier is the user specified root element
	 * @param eClassifier
	 * @return
	 */	
	public boolean isRoot(EClassifier eClassifier) {
		return ROOT==eClassifier;
	}
	
	/**
	 * Checks whether a configured root element is configured to be nested
	 * (Example: The children of the root StateMachine can contain further sub-StateMachines)
	 * @param eClassifier
	 * @return
	 */
	@Deprecated
	public static boolean rootCanBeNested(EClassifier eClassifier) {
		return ROOTECLASSCANBENESTED;
	}
	
	
	/**
	 * Checks if a given EReference is inherited
	 * @param the EReference
	 * @param concerningEClassifier is the class to check on
	 * @return true if inherited
	 */
	public static boolean isInheritedReference(EReference eRef, EClassifier concerningEClassifier) {
		
		if(concerningEClassifier instanceof EClass) {
			EClass eClass = (EClass) concerningEClassifier;
			return !eClass.getEReferences().contains(eRef);
		}
		
		return true;
	}
	
//	/**
//	 * Checks whether an EClassifier is implicitly required because it inherits its features
//	 * to sub types which are white listed. Implicitly required EClassifiers are not required in CREATE/DELETES.
//	 * Only in SET/ADD/CHANGE transformations.
//	 * @param eClassifier
//	 * @return
//	 */
//	protected static boolean isImplicitlyRequiredForFeatureInheritance(EClassifier eClassifier) {
//		if(implicitRequirements.get(ImplicitRequirementType.INHERITING_SUPERTYPES).contains(eClassifier)) {
//			return true;
//		}
//		return false;
//	}

}
