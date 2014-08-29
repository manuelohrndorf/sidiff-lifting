package org.sidiff.editrule.generator.serge.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.henshin.model.Module;
import org.sidiff.common.emf.EMFUtil;
import org.sidiff.common.emf.ecore.EClassVisitor;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.editrule.generator.serge.configuration.Configuration;
import org.sidiff.editrule.generator.types.OperationType;

/**
 * Todo-List for Reintegration:
 * 
 * - why use getAllModulesAsSet() ?
 * - ProfileModelIntegration
 * - implement MoveDown
 * - implement MoveUp
 * - contraint generation
 * - more detailed comment in filter identical / filter duplicates
 * - more detailed comments to when rule might not be executable in ExecutionChecker
 * - remove all the deprecated marks, old classes, old todos/fixmes
 * 
 * @author mrindt
 *
 */
public class MetaModelElementVisitor implements EClassVisitor{

	private GenerationActionDelegator GAD 		= GenerationActionDelegator.getInstance();
	
	// Sets for module variants and inverse creations
	private Set<Module> allCreateModules 						= new HashSet<Module>();
	private Set<Module> allVariantModules					= new HashSet<Module>();
	private Set<Module> allDeleteModules 						= new HashSet<Module>();
	private Set<Module> allMoveModules 							= new HashSet<Module>();
	private Set<Module> allMoveCombinationModules 	= new HashSet<Module>();
	private Set<Module> allMoveDownModules 					= new HashSet<Module>();
	private Set<Module> allMoveUpModules 						= new HashSet<Module>();
	private Set<Module> allAddModules 							= new HashSet<Module>();
	private Set<Module> allRemoveModules 						= new HashSet<Module>();
	private Set<Module> allSetAttributeModules 			= new HashSet<Module>();
	private Set<Module> allSetReferenceModules			= new HashSet<Module>();
	private Set<Module> allUnsetAttributeModules 		= new HashSet<Module>();
	private Set<Module> allUnsetReferenceModules		= new HashSet<Module>();
	private Set<Module> allChangeLiteralModules		= new HashSet<Module>();
	private Set<Module> allChangeReferenceModules	= new HashSet<Module>();
	
	@Override
	public void eClassifier(EClassifier eClassifier, String fullyQualifiedPath) {
		
			LogUtil.log(LogEvent.NOTICE, "***** " + eClassifier.getName() + " ***********************************************");
			assert(eClassifier instanceof EClass);
			
			EClass contextClass = (EClass) eClassifier;
			
			// exclude generation for Ecore Elements in case the main meta model is not Ecore
			if(!Configuration.getInstance().METAMODEL.getNsURI().equals(EcorePackage.eNS_URI)) {
				return;
			}

			try{
				
				Set<Module> createModules 	= GAD.generate_CREATE(contextClass);
				if (!createModules.isEmpty()){
					allCreateModules.addAll(createModules);
				}
				
				Set<Module> variantModules 	= GAD.process_Replacables(createModules, OperationType.CREATE, Configuration.getInstance().REDUCETOSUPERTYPE_CREATEDELETE);
				if (!variantModules.isEmpty()){
					allVariantModules.addAll(variantModules);
				}
				
				Set<Module> deleteModules = GAD.generate_DELETE(createModules);	
				if (!deleteModules.isEmpty()){
					allDeleteModules.addAll(deleteModules);
				}
				
				Set<Module> moveModules = GAD.generate_MOVE(contextClass);
				if (!moveModules.isEmpty()){
					allMoveModules.addAll(moveModules);
				}
				
				Set<Module> moveCombinationModules = GAD.generate_MOVE_REFERENCE_COMBINATION(contextClass);			
				if (!moveCombinationModules.isEmpty()){
					allMoveCombinationModules.addAll(moveCombinationModules);
				}
				
				Set<Module> moveDownModules = GAD.generate_MOVE_DOWN(contextClass);
				if (!moveDownModules.isEmpty()){
					allMoveDownModules.addAll(moveDownModules);
				}
				
				Set<Module> moveUpModules = GAD.generate_MOVE_UP(contextClass);
				if (!moveUpModules.isEmpty()){
					allMoveUpModules.addAll(moveUpModules);
				}
				
				Set<Module> addModules = GAD.generate_ADD(contextClass);
				if (!addModules.isEmpty()){
					allAddModules.addAll(addModules);
				}
				
				Set<Module> removeModules = GAD.generate_REMOVE(addModules);
				if (!removeModules.isEmpty()){
					allRemoveModules.addAll(removeModules);
				}
				
				Set<Module> setAttributeModules = GAD.generate_SET_ATTRIBUTE(contextClass);	
				if (!setAttributeModules.isEmpty()){
					allSetAttributeModules.addAll(setAttributeModules);
				}
				
				Set<Module> unsetAttributeModules = GAD.generate_UNSET_ATTRIBUTE(setAttributeModules);	
				if (!unsetAttributeModules.isEmpty()){ //unset attributes revert attribute values back to default.
					allUnsetAttributeModules.addAll(unsetAttributeModules);
				}
				
				Set<Module> setReferenceModules = GAD.generate_SET_REFERENCE(contextClass);
				if (!setReferenceModules.isEmpty()){
					allSetReferenceModules.addAll(setReferenceModules);
				}
				
				Set<Module> unsetReferenceModules = GAD.generate_UNSET_REFERENCE(setReferenceModules);	
				if (!unsetReferenceModules.isEmpty()){
					allUnsetReferenceModules.addAll(unsetReferenceModules);
				}
				
				Set<Module> changeLiteralModules = GAD.generate_CHANGE_Literals(contextClass);
				if (!changeLiteralModules.isEmpty()){
					allChangeLiteralModules.addAll(changeLiteralModules);
				}
				
				Set<Module> changeReferenceModules = GAD.generate_CHANGE_Reference(contextClass);
				if (!changeReferenceModules.isEmpty()){
					allChangeReferenceModules.addAll(changeReferenceModules);
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}

	}

	@Override
	public void finish() {
		LogUtil.log(LogEvent.NOTICE, "finished");		
	}
	
	public Map<OperationType, Set<Module>> getAllModules(){
		Map<OperationType, Set<Module>> allModules	= new HashMap<OperationType, Set<Module>>();
		
		allModules.put(OperationType.CREATE, allCreateModules);
		allModules.get(OperationType.CREATE).addAll(allVariantModules);
		allModules.put(OperationType.DELETE, allDeleteModules);
		allModules.put(OperationType.MOVE, allMoveModules);
		allModules.put(OperationType.MOVE_REFERENCE_COMBINATION, allMoveCombinationModules);
		allModules.put(OperationType.MOVE_UP, allMoveUpModules);
		allModules.put(OperationType.MOVE_DOWN, allMoveDownModules);
		allModules.put(OperationType.ADD, allAddModules);
		allModules.put(OperationType.REMOVE, allRemoveModules);
		allModules.put(OperationType.SET_ATTRIBUTE, allSetAttributeModules);
		allModules.put(OperationType.UNSET_ATTRIBUTE, allUnsetAttributeModules);
		allModules.put(OperationType.SET_REFERENCE, allSetReferenceModules);
		allModules.put(OperationType.UNSET_REFERENCE, allUnsetReferenceModules);
		allModules.put(OperationType.CHANGE_LITERAL, allChangeLiteralModules);
		allModules.put(OperationType.CHANGE_REFERENCE, allChangeReferenceModules);
		
		return allModules;
	}

}
