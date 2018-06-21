package org.sidiff.editrule.generator.serge.configuration;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.sidiff.common.emf.metamodel.analysis.EClassifierInfoManagement;
import org.sidiff.editrule.generator.types.OperationType;

@SuppressWarnings("unused")
public class Configuration {
	
	/**
	 * The types of Modules which can be generated
	 */
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

	
	// configurations not on config or settings	
	public boolean enable_duplicate_filter = false;
	public boolean enable_execution_check_filter = true;
	public boolean enable_name_mapper = false;
	public boolean enable_inner_containment_cycle_detection = false;
	public boolean enable_annotations = false;
	public boolean enable_consolidatedvariants = true;
	
	// consistency-level configurations
	public final Set<OperationType> create = new HashSet<>();
	public boolean create_multiplicity_preconditions;
	public boolean create_mandatory_children;
	public boolean create_mandatory_neighbours;
	public boolean create_not_required_and_not_id_attributes;
	
	// set reduction configurations
	public final Set<OperationTypeGroup> opTypes_reduced_by_abstract_preference_on_danglings = new HashSet<>();
	public final Set<OperationTypeGroup> opTypes_reduced_by_supertype_preference_on_danglings = new HashSet<>();
	public final Set<OperationTypeGroup> opTypes_reduced_by_supertype_preference_on_contexts = new HashSet<>();
	
	// special operation type usage configuration
	public boolean consider_literal_switching_for_change_operations;
	public boolean consider_reference_switching_for_move_operations;	
	public boolean consider_reference_switching_for_moveUp_operations;
	public boolean consider_reference_switching_for_moveDown_operations;	
	public boolean use_simple_names_for_attach_and_detach_operations;
	
	// the meta model for which rules should be generated
	public EPackage metaModel;
	public boolean metaModel_is_profile;
	
	// all relevant EPackages (either of the main meta model or dependencies)
	public Stack<EPackage> EPACKAGESSTACK = null;
	
	// the expected root element in an instance of this meta-model
	public String rootElementName;
	public EClassifier root;
	public boolean rootEClassCanBeNested; //TODO Check if can be removed

	// the global EClassifierInfoManagement
	public static EClassifierInfoManagement ECM  = null;
	
	// instance
	private static Configuration instance = null;

	/**
	 * singleton instance
	 * @return
	 * 		instance
	 */
	public static Configuration getInstance() {
		if(instance==null) {
			instance = new Configuration();
		}
		return instance;
	}	
	
	/**
	 * Checks whether or not this operation type should be generated.
	 * 
	 * @param type
	 * @return
	 * 		true | false
	 */
	public boolean isRuleCreationEnabled(OperationType type){
		return create.contains(type);
	}
	
	
	/**
	 * Checks whether the mass of rules of the given OperationTypeGroup can be reduced
	 * by preferring super types over its sub types on a dangling node.
	 * 
	 * @param type
	 * @return
	 * 		true | false
	 */
	public boolean preferSuperTypesOnDanglings(OperationTypeGroup type) {
		return opTypes_reduced_by_supertype_preference_on_danglings.contains(type);
	}

	/**
	 * Checks whether the mass of rules of the given OperationTypeGroup can be reduced
	 * by preferring super types over its sub types on a context node.
	 * 
	 * @param type
	 * @return
	 * 		true | false
	 */
	public boolean preferSuperTypesOnContexts(OperationTypeGroup type) {
		return opTypes_reduced_by_supertype_preference_on_danglings.contains(type);
	}
	
	/**
	 * Checks whether the mass of rules of the given OperationTypeGroup can be reduced
	 * by preferring abstract types over concrete sub types on a dangling node.
	 * 
	 * @param type
	 * @return
	 * 		true | false
	 */
	public boolean preferAbstractTypesOnDanglings(OperationTypeGroup opTypeGroup) {
		return opTypes_reduced_by_abstract_preference_on_danglings.contains(opTypeGroup);
	}

	
	/**
	 * Checks whether an eClassifier is defined as root and if it
	 * may not appear somewhere lower in containment hierarchy.
	 * @param eClassifier
	 * 
	 * @return
	 * 		true | false
	 */	
	public Boolean isAnUnnestableRoot(EClassifier eClassifier) {
		if(root==eClassifier && !rootEClassCanBeNested ) {
			return true;
		}
		return false;
	}

	
	/**
	 * Checks whether an eClassifier is the user specified root element
	 * @param eClassifier
	 * @return
	 * 		true | false
	 */	
	public boolean isRoot(EClassifier eClassifier) {
		return root==eClassifier;
	}

}
