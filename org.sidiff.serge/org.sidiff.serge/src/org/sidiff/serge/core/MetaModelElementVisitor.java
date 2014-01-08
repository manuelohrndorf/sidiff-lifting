package org.sidiff.serge.core;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.henshin.model.Module;
import org.sidiff.common.emf.ecore.EClassVisitor;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.serge.exceptions.OperationTypeNotImplementedException;

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
	private Set<Module> changeModules			= new HashSet<Module>();
	
	@Override
	public void eClassifier(EClassifier eClassifier, String fullyQualifiedPath) {
		
			LogUtil.log(LogEvent.NOTICE, "***** " + eClassifier.getName() + " ***********************************************");
			assert(eClassifier instanceof EClass);
			try{
				createModules 	= GAD.generate_CREATE(eClassifier);
				variantModules 	= GAD.VariantPostprocessor(eClassifier);
	
				deleteModules = GAD.generate_DELETE(variantModules);
	
				moveModules = GAD.generate_MOVE(eClassifier);
				moveCombinationModules = GAD.generate_MOVE_COMBINATION(eClassifier);
				moveDownModules = GAD.generate_MOVE_DOWN(eClassifier);
				moveUpModules = GAD.generate_MOVE_UP(eClassifier);
	
				addModules = GAD.generate_ADD(eClassifier);
				removeModules = GAD.generate_REMOVE(addModules);
	
				setAttributeModules = GAD.generate_SET_ATTRIBUTE(eClassifier);
				setReferenceModules = GAD.generate_UNSET_ATTRIBUTE(setAttributeModules);
	
				setReferenceModules = GAD.generate_SET_REFERENCE(eClassifier);
				unsetReferenceModules = GAD.generate_UNSET_REFERENCE(setReferenceModules);
	
				changeModules = GAD.generate_CHANGE(eClassifier);
				
				
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
