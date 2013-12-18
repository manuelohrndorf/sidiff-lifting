package org.sidiff.serge.services;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.henshin.model.Module;
import org.sidiff.common.emf.ecore.EClassVisitor;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.serge.core.GenerationActionDelegator;

public class MetaModelElementVisitor implements EClassVisitor{

	private GenerationActionDelegator GAD 		= GenerationActionDelegator.getInstance();
	
	// Sets for module variants and inverse creations
	private Set<Module> createModules 			= new HashSet<Module>();
	private Set<Module> variantModules			= new HashSet<Module>();
	private Set<Module> addModules 				= new HashSet<Module>();
	private Set<Module> set_attribute_Modules 	= new HashSet<Module>();
	private Set<Module> set_reference_Modules	= new HashSet<Module>();
	
	@Override
	public void eClassifier(EClassifier eClassifier, String fullyQualifiedPath) {
		
		if(eClassifier instanceof EClass) {		
			EClass eClass = (EClass) eClassifier;
			LogUtil.log(LogEvent.NOTICE, "***** " + eClass.getName() + " ***********************************************");

//			try {
				
				createModules 	= GAD.generate_CREATE(eClass);
				variantModules 	= GAD.VariantPostprocessor(eClass);
				
				GAD.generate_DELETE(variantModules);
				
				GAD.generate_MOVE(eClass);
				GAD.generate_MOVE_COMBINATION(eClass);
				GAD.generate_MOVE_DOWN(eClass);
				GAD.generate_MOVE_UP(eClass);
				
				addModules = GAD.generate_ADD(eClass);
				GAD.generate_REMOVE(addModules);
				
				set_attribute_Modules = GAD.generate_SET_ATTRIBUTE(eClass);
				GAD.generate_UNSET_ATTRIBUTE(set_attribute_Modules);
				
				set_reference_Modules = GAD.generate_SET_REFERENCE(eClass);
				GAD.generate_UNSET_REFERENCE(set_reference_Modules);
				
				GAD.generate_CHANGE(eClass);

				
//			} catch (ConstraintException e) {
//				e.printStackTrace();
//			}

		}
	}

	@Override
	public void finish() {
		LogUtil.log(LogEvent.NOTICE, "finished");		
	}

}
