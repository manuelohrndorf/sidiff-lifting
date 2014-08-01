package org.sidiff.serge.ocl.constraintapplicator.store;

import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.eclipse.ocl.OCL;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.expressions.OCLExpression;
import org.eclipse.ocl.helper.OCLHelper;
import org.sidiff.serge.ocl.constraintapplicator.modulepatterns.ModuleMatchPattern;
import org.sidiff.serge.ocl.constraintapplicator.oclpatterns.OCLExpressionPattern;
import org.sidiff.serge.ocl.constraintapplicator.settings.OCLCASettings;

public class Constraint2ModuleStore {
	
	private HenshinResourceSet moduleSet;
	private Set<OCLExpressionPattern> oclXPPatternSet;
	
	private Set<ConstraintModulePair> constraintModulePair;
	
	
	public Constraint2ModuleStore (OCLCASettings settings){
		
		// load modules
		String inputPath = settings.getInputPath();		
		moduleSet = new HenshinResourceSet(inputPath);
		
	}
	
	public void addOCLExpressionPattern(OCLExpressionPattern oclxpp) {
		oclXPPatternSet.add(oclxpp);
	}

	public Set<OCLExpressionPattern> getAllConstraints() {
		return oclXPPatternSet;
	}

	public void addAffectedModules(OCLExpressionPattern oclxpp, Set<Module> affectedModules) {
		for(Module m: affectedModules) {
			ConstraintModulePair cmPair = new ConstraintModulePair(m, oclxpp);
			constraintModulePair.add(cmPair);
		}
	}
	
	public void linkMultipleMultipleModuleAffections() {
		// for each ConstraintModulePair add additonalModuleAffections
		//...
	}


}
