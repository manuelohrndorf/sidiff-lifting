package org.sidiff.serge.core;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.henshin.model.Module;
import org.sidiff.common.emf.EMFUtil;
import org.sidiff.common.emf.ecore.EClassVisitor;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.serge.configuration.Configuration;
import org.sidiff.serge.configuration.Configuration.OperationType;
import org.sidiff.serge.filter.DuplicateFilter;
import org.sidiff.serge.filter.ExecutableFilter;

/**
 * Todo-List for Reintegration:
 * 
 * - VariantProcesser (AbstractTypeReplacer and SubTypeReplacer) needs to be completed
 * - variantModules >> createModules. Some createModules must be removed due to lack of consistency.
 * - isAllowedForModuleBase: complete support for other OperationTypes
 * - isAllowedForDangling: complete support for other OperationTypes
 * - What about implicitlyRequired stuff?
 * - XML/DTD must be adjusted to new OperationType differenciation
 * - Configuration and ConfigurationParser also
 * - ProfileModelIntegration
 * - Implementation of MoveUp and MoveDown
 *
 * 
 * @author mrindt
 *
 */
public class MetaModelElementVisitor implements EClassVisitor{

	private GenerationActionDelegator GAD 		= GenerationActionDelegator.getInstance();
	
	// Sets for module variants and inverse creations
	private Set<Module> createModules 			= new HashSet<Module>();
	private Set<Module> variantModules			= new HashSet<Module>();
	private Set<Module> deleteModules 			= new HashSet<Module>();
	private Set<Module> moveModules 			= new HashSet<Module>();
	private Set<Module> moveCombinationModules 	= new HashSet<Module>();
	private Set<Module> moveDownModules 		= new HashSet<Module>();
	private Set<Module> moveUpModules 			= new HashSet<Module>();
	private Set<Module> addModules 				= new HashSet<Module>();
	private Set<Module> removeModules 			= new HashSet<Module>();
	private Set<Module> setAttributeModules 	= new HashSet<Module>();
	private Set<Module> setReferenceModules		= new HashSet<Module>();
	private Set<Module> unsetAttributeModules 	= new HashSet<Module>();
	private Set<Module> unsetReferenceModules	= new HashSet<Module>();
	private Set<Module> changeLiteralModules	= new HashSet<Module>();
	private Set<Module> changeReferenceModules	= new HashSet<Module>();
	
	// Ultimate Set for all modules
	private Set<Set<Module>> allModules			= new HashSet<Set<Module>>();
	
	@Override
	public void eClassifier(EClassifier eClassifier, String fullyQualifiedPath) {
		
			LogUtil.log(LogEvent.NOTICE, "***** " + eClassifier.getName() + " ***********************************************");
			assert(eClassifier instanceof EClass);
			
			allModules.clear();
			
			EClass contextClass = (EClass) eClassifier;
			
			// FIXME: Workaround to exclude Generation of Operations on Ecore
			if (EMFUtil.createListFromEAllContents(EcorePackage.eINSTANCE).contains(contextClass)){
				return;
			}
			
			try{
				
				createModules 	= GAD.generate_CREATE(contextClass);
				variantModules 	= GAD.process_Replacables(createModules, OperationType.CREATE, Configuration.getInstance().REDUCETOSUPERTYPE_CREATEDELETE);
				
//TODO createModules must be extended by variants but also
				//in some cases, they need to be replaced because only their variants are valid.
				// --> new algorithm to do that required.
				
				deleteModules = GAD.generate_DELETE(createModules);	
				moveModules = GAD.generate_MOVE(contextClass);
				moveCombinationModules = GAD.generate_MOVE_REFERENCE_COMBINATION(contextClass);			
				moveDownModules = GAD.generate_MOVE_DOWN(contextClass);
				moveUpModules = GAD.generate_MOVE_UP(contextClass);
				addModules = GAD.generate_ADD(contextClass);
				removeModules = GAD.generate_REMOVE(addModules);
				setAttributeModules = GAD.generate_SET_ATTRIBUTE(contextClass);				
// FIXME: What do we mean with unset attribute?
//				unsetAttributeModules = GAD.generate_UNSET_ATTRIBUTE(setAttributeModules);
				setReferenceModules = GAD.generate_SET_REFERENCE(contextClass);
				unsetReferenceModules = GAD.generate_UNSET_REFERENCE(setReferenceModules);	
				changeLiteralModules = GAD.generate_CHANGE_Literals(contextClass);
				changeReferenceModules = GAD.generate_CHANGE_Reference(contextClass);
				
				allModules.add(createModules);
				allModules.add(variantModules);
				allModules.add(deleteModules);
				allModules.add(moveModules);
				allModules.add(moveCombinationModules);
				allModules.add(moveUpModules);
				allModules.add(moveDownModules);
				allModules.add(addModules);
				allModules.add(removeModules);
				allModules.add(setAttributeModules);
				allModules.add(unsetAttributeModules);
				allModules.add(setReferenceModules);
				allModules.add(unsetReferenceModules);
				allModules.add(changeLiteralModules);
				allModules.add(changeReferenceModules);
				
				LogUtil.log(LogEvent.NOTICE, "-- Duplicate Filter --");
				DuplicateFilter duplicateFilter = new DuplicateFilter();
				duplicateFilter.filterAddSet(addModules, setReferenceModules);
				duplicateFilter.filterRemoveUnset(removeModules, unsetReferenceModules);
				
				LogUtil.log(LogEvent.NOTICE, "-- Constraint Applicator --");
				ConstraintApplicator constraintApplicator = new ConstraintApplicator();
				constraintApplicator.applyOn(allModules);
				
				LogUtil.log(LogEvent.NOTICE, "-- Execution Filter --");
				ExecutableFilter executionFilter = new ExecutableFilter();
				executionFilter.applyOn(allModules);
				
				LogUtil.log(LogEvent.NOTICE, "-- Rule Parameter Applicator --");
				RuleParameterApplicator ruleParameterApplicator = new RuleParameterApplicator();
				ruleParameterApplicator.applyOn(allModules);
				
				LogUtil.log(LogEvent.NOTICE, "-- Main Unit Applicator --");
				MainUnitApplicator mainUnitApplicator = new MainUnitApplicator();
				mainUnitApplicator.applyOn(allModules);
								
				LogUtil.log(LogEvent.NOTICE, "-- Module Serializer --");
				ModuleSerializer serializer = new ModuleSerializer();
				serializer.serialize(allModules);
			}
			catch(Exception e) {
				e.printStackTrace();
			}

	}

	@Override
	public void finish() {
		LogUtil.log(LogEvent.NOTICE, "finished");		
	}

}
