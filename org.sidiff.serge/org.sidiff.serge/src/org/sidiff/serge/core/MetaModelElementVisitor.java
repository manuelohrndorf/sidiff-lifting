package org.sidiff.serge.core;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.henshin.model.Module;
import org.sidiff.common.emf.ecore.EClassVisitor;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.serge.core.Configuration.OperationType;

/**
 * Todo-List for Reintegration:
 * 
 * - VariantProcesser (AbstractTypeReplacer and SubTypeReplacer) needs to be completed
 * - variantModules >> createModules. Some createModules must be removed due to lack of consistency.
 * - isAllowedForModuleBase: complete support for other OperationTypes
 * - isAllowedForDangling: complete support for other OperationTypes
 * - What about implicitlyRequired stuff?
 * - ConstraintApplicatior needs implementation
 * - XML/DTD must be adjusted to new OperationType differenciation
 * - Configuration and ConfigurationParser also
 * - ProfileModelIntegration
 * - Implementation of MoveUp and MoveDown needs implementation
 *
 * - Generally: The refactoring was done on expense of runtime performance:
 * Often same lists/sets have to be iterated several times.
 * To increase speed we should think about using threads in the future.
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
			try{
				
				ConstraintApplicator constraintApplicator = new ConstraintApplicator();
				
				createModules 	= GAD.generate_CREATE(eClassifier);
				variantModules 	= GAD.process_Replacables(createModules, OperationType.CREATE, Configuration.getInstance().REDUCETOSUPERTYPE_CREATEDELETE);
				
				//TODO createModles must be extended by variants but also
				//in some cases, they need to be replaced because only their variants are valid.
				// --> new algorithm to do that required.
				
				constraintApplicator.applyOn(createModules);
				
				deleteModules = GAD.generate_DELETE(createModules);	
				constraintApplicator.applyOn(deleteModules);
				
				moveModules = GAD.generate_MOVE(eClassifier);
				constraintApplicator.applyOn(moveModules);
				
				moveCombinationModules = GAD.generate_MOVE_REFERENCE_COMBINATION(eClassifier);
				constraintApplicator.applyOn(moveCombinationModules);
							
				moveDownModules = GAD.generate_MOVE_DOWN(eClassifier);
				constraintApplicator.applyOn(moveDownModules);
				
				moveUpModules = GAD.generate_MOVE_UP(eClassifier);
				constraintApplicator.applyOn(moveUpModules);
	
				addModules = GAD.generate_ADD(eClassifier);
				constraintApplicator.applyOn(addModules);
				
				removeModules = GAD.generate_REMOVE(addModules);
				constraintApplicator.applyOn(removeModules);
	
				setAttributeModules = GAD.generate_SET_ATTRIBUTE(eClassifier);
				constraintApplicator.applyOn(setAttributeModules);
				
				setReferenceModules = GAD.generate_UNSET_ATTRIBUTE(setAttributeModules);
				constraintApplicator.applyOn(setReferenceModules);
	
				setReferenceModules = GAD.generate_SET_REFERENCE(eClassifier);
				constraintApplicator.applyOn(setReferenceModules);
				
				unsetReferenceModules = GAD.generate_UNSET_REFERENCE(setReferenceModules);
				constraintApplicator.applyOn(unsetReferenceModules);
	
				changeLiteralModules = GAD.generate_CHANGE_Literals(eClassifier);
				constraintApplicator.applyOn(changeLiteralModules);
				
				changeReferenceModules = GAD.generate_CHANGE_Reference(eClassifier);
				constraintApplicator.applyOn(changeReferenceModules);
				
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
				
	
				ModuleSerializer serializer = new ModuleSerializer();
				serializer.serialize(allModules);
				
				
			}
			catch(Exception e) {
				System.err.println(e);
			}

	}

	@Override
	public void finish() {
		LogUtil.log(LogEvent.NOTICE, "finished");		
	}

}
