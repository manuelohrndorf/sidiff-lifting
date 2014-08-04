package org.sidiff.serge.ocl.constraintapplicator;

import java.util.Set;

import org.eclipse.emf.henshin.model.Module;
import org.eclipse.ocl.ParserException;
import org.sidiff.serge.ocl.constraintapplicator.analyzers.ModuleSetAnalyzer;
import org.sidiff.serge.ocl.constraintapplicator.analyzers.OCLConstraintAnalyzer;
import org.sidiff.serge.ocl.constraintapplicator.exceptions.OCLExpressionPatternNotImplementedExeption;
import org.sidiff.serge.ocl.constraintapplicator.oclpatterns.OCLExpressionPattern;
import org.sidiff.serge.ocl.constraintapplicator.settings.OCLCASettings;
import org.sidiff.serge.ocl.constraintapplicator.store.Constraint2ModuleStore;
import org.sidiff.serge.ocl.constraintapplicator.store.ModuleSetModifier;

public class OCLConstraintApplicator {

	/**
	 * The general OCLConstraintApplicator settings object.
	 */
	private OCLCASettings settings;
	
	
	/**
	 * Constructor.
	 * @param settings
	 */
	public OCLConstraintApplicator(OCLCASettings settings) {
		this.settings = settings;
	}
	
	/**
	 * The entry point for OCLConstraintApplicator processing.
	 * From here the analysis of Constraints and Modules and also
	 * the corresponding modification on module sets is initiated.
	 */
	public void applyConstraints() {
		
		try {
			
			// Analyze OCL-constraints in the meta-model and
			// identify supported OCLExpressionPatterns.
			// Found patterns will be kept in Constraint2ModuleStore
			OCLConstraintAnalyzer oclAnalyzer = new OCLConstraintAnalyzer(settings);
			Constraint2ModuleStore c2mstore = oclAnalyzer.indentifyConstraintPatterns();
		
			// Load existing ModulesSet and
			// find modules affected by found OCLExpressionPatterns
			ModuleSetAnalyzer msAnalyzer = new ModuleSetAnalyzer();
			for(OCLExpressionPattern oclxpp: c2mstore.getAllConstraints()) {
				Set<Module> affectedModules = msAnalyzer.findAffectedModules(oclxpp);
				if(affectedModules!=null) {
					c2mstore.addAffectedModules(oclxpp,affectedModules);
				}
			}
			c2mstore.linkMultipleModuleAffections();
			
			//Modify ModuleSet accordingly
			ModuleSetModifier msModifier = new ModuleSetModifier();
			msModifier.modify(c2mstore);
			
		
		} catch (ParserException | OCLExpressionPatternNotImplementedExeption e) {
			e.printStackTrace();
		}	
		
	}
	
}
