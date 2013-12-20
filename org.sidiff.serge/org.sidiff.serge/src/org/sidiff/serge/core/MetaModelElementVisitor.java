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
	private Set<Module> addModules 				= new HashSet<Module>();
	private Set<Module> set_attribute_Modules 	= new HashSet<Module>();
	private Set<Module> set_reference_Modules	= new HashSet<Module>();
	
	@Override
	public void eClassifier(EClassifier eClassifier, String fullyQualifiedPath) {
		
			LogUtil.log(LogEvent.NOTICE, "***** " + eClassifier.getName() + " ***********************************************");

			try{
				createModules 	= GAD.generate_CREATE(eClassifier);
				variantModules 	= GAD.VariantPostprocessor(eClassifier);
	
				GAD.generate_DELETE(variantModules);
	
				GAD.generate_MOVE(eClassifier);
				GAD.generate_MOVE_COMBINATION(eClassifier);
				GAD.generate_MOVE_DOWN(eClassifier);
				GAD.generate_MOVE_UP(eClassifier);
	
				addModules = GAD.generate_ADD(eClassifier);
				GAD.generate_REMOVE(addModules);
	
				set_attribute_Modules = GAD.generate_SET_ATTRIBUTE(eClassifier);
				GAD.generate_UNSET_ATTRIBUTE(set_attribute_Modules);
	
				set_reference_Modules = GAD.generate_SET_REFERENCE(eClassifier);
				GAD.generate_UNSET_REFERENCE(set_reference_Modules);
	
				GAD.generate_CHANGE(eClassifier);
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
