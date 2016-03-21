package org.sidiff.editrule.generator.serge.configuration;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.sidiff.common.emf.extensions.impl.EClassifierInfoManagement;
import org.sidiff.editrule.generator.types.OperationType;

@SuppressWarnings("unused")
public class Configuration {
	
	public static enum OperationTypeGroup{
		SET_UNSET_ATTRIBUTE,
		SET_UNSET_REFERENCE,
		ADD_REMOVE,
		CHANGE_REFERENCE,
		CHANGE_LITERAL,
		MOVE,
		MOVE_REFERENCE_COMBINATION,
		MOVE_UP,
		MOVE_DOWN,
		CREATE_DELETE,
		ATTACH_DETACH
	}

	
	// Tool specific configurations
	
	public boolean ENABLE_DUPLICATE_FILTER = false;
	public boolean ENABLE_EXECUTION_CHECK_FILTER = true;
	public boolean ENABLE_NAME_MAPPER = false;
	public boolean ENABLE_INNER_CONTAINMENT_CYCLE_DETECTION = false;
	
	// Meta-model dependend configurations
	public final Set<OperationType> create = new HashSet<>();
	
	public boolean MULTIPLICITY_PRECONDITIONS;
	public boolean CREATE_MANDATORY_CHILDREN;
	public boolean CREATE_MANDATORY_NEIGHBOURS;
	public boolean CREATE_NOT_REQUIRED_AND_NOT_ID_ATTRIBUTES;
	
	public final Set<OperationTypeGroup> reduceAbstractDanglings = new HashSet<>();
	public final Set<OperationTypeGroup> reduceSupertypeDanglings = new HashSet<>();
	public final Set<OperationTypeGroup> reduceSupertypeContext = new HashSet<>();
	
	public boolean LITERALSWITCHING_CHANGE;
	public boolean REFERENCESWITCHING_MOVE;	
	public boolean REFERENCESWITCHING_MOVE_UP;
	public boolean REFERENCESWITCHING_MOVE_DOWN;	
	public boolean USE_SIMPLE_NAMES_ATTACHDETACH;
		
	public EPackage METAMODEL;
	
	public String ROOTNAME;
	public EClassifier ROOT;
	public boolean ROOTECLASSCANBENESTED; //TODO Check if can be removed
	
	public boolean MAINMODEL_IS_PROFILE;
	
	public Stack<EPackage> EPACKAGESSTACK = null;	
	public static EClassifierInfoManagement ECM  = null;
	
	private static Configuration instance = null;

	
	public static Configuration getInstance() {
		if(instance==null) {
			instance = new Configuration();
		}
		return instance;
	}	
	
	public boolean isRuleCreationEnabled(OperationType type){
		return create.contains(type);
	}
	
	
	public boolean reduceToSupertypeDanglings(OperationTypeGroup type) {
		return reduceSupertypeDanglings.contains(type);
	}

	public boolean reduceToSupertypeContext(OperationTypeGroup type) {
		return reduceSupertypeDanglings.contains(type);
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

	public boolean reduceToAbstractTypeDanglings(OperationTypeGroup opTypeGroup) {
		return reduceAbstractDanglings.contains(opTypeGroup);
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
