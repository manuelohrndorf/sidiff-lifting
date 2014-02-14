package org.sidiff.serge.impl;

import java.util.Set;
import java.util.Stack;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Module;
import org.sidiff.common.emf.ecore.ECoreTraversal;
import org.sidiff.common.emf.extensions.impl.EClassifierInfoManagement;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.serge.configuration.Configuration;
import org.sidiff.serge.configuration.ConfigurationParser;
import org.sidiff.serge.core.ConstraintApplicator;
import org.sidiff.serge.core.MainUnitApplicator;
import org.sidiff.serge.core.MetaModelElementVisitor;
import org.sidiff.serge.core.ModuleSerializer;
import org.sidiff.serge.core.NameMapper;
import org.sidiff.serge.core.RuleParameterApplicator;
import org.sidiff.serge.exceptions.EPackageNotFoundException;
import org.sidiff.serge.filter.DuplicateFilter;
import org.sidiff.serge.filter.ElementFilter;
import org.sidiff.serge.filter.ExecutableFilter;

public class Serge {

	private static Stack<EPackage> ePackagesStack = null;
	
	/**
	 * Initial setup for SERGe.
	 * 
	 * @param pathToConfig
	 */
	public void init(String pathToConfig) {
		
		try {
			// create empty instances
			Configuration c = Configuration.getInstance();
			EClassifierInfoManagement ECM = EClassifierInfoManagement.getInstance();
			ElementFilter.getInstance();
			
			// parse and gather infos
			ConfigurationParser parser = new ConfigurationParser();
			parser.parse(pathToConfig);
			ECM.gatherInformation(c.PROFILEAPPLICATIONINUSE, c.EPACKAGESSTACK);
			
			// get ePackageStack for usage in generate()
			ePackagesStack = c.EPACKAGESSTACK;
		
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
	}
	
	/**
	 * Method to start the generation process.
	 */
	public void generate() throws EPackageNotFoundException {

		if(!ePackagesStack.isEmpty()){
			MetaModelElementVisitor eClassVisitor = new MetaModelElementVisitor();
			ECoreTraversal.traverse(eClassVisitor, ePackagesStack.toArray(new EPackage[ePackagesStack.size()]));				
		
			// Postprocessing...
			
			Set<Set<Module>> allModules = eClassVisitor.getAllModules();
			
//			LogUtil.log(LogEvent.NOTICE, "-- Duplicate Filter --");
//			DuplicateFilter duplicateFilter = new DuplicateFilter();
//			duplicateFilter.filterAddSet(addModules, setReferenceModules);
//			duplicateFilter.filterRemoveUnset(removeModules, unsetReferenceModules);
			
			LogUtil.log(LogEvent.NOTICE, "-- Constraint Applicator --");
			ConstraintApplicator constraintApplicator = new ConstraintApplicator();
			constraintApplicator.applyOn(allModules);
			
			LogUtil.log(LogEvent.NOTICE, "-- Execution Filter --");
			ExecutableFilter executionFilter = new ExecutableFilter();
			executionFilter.applyOn(allModules);
			
			LogUtil.log(LogEvent.NOTICE, "-- Execution Filter --");
			DuplicateFilter duplicateFilter = new DuplicateFilter();
			duplicateFilter.filterIdentical(allModules);
			
			LogUtil.log(LogEvent.NOTICE, "-- Rule Parameter Applicator --");
			RuleParameterApplicator ruleParameterApplicator = new RuleParameterApplicator();
			ruleParameterApplicator.applyOn(allModules);
			
			LogUtil.log(LogEvent.NOTICE, "-- Main Unit Applicator --");
			MainUnitApplicator mainUnitApplicator = new MainUnitApplicator();
			mainUnitApplicator.applyOn(allModules);
							
			Set<Module> moduleSet = eClassVisitor.getAllModulesAsSet();
			
			LogUtil.log(LogEvent.NOTICE, "-- Name Mapper --");
			NameMapper nameMapper = new NameMapper(Configuration.getInstance().METAMODEL, moduleSet);
			nameMapper.replaceNames();
			
			LogUtil.log(LogEvent.NOTICE, "-- Module Serializer --");
			ModuleSerializer serializer = new ModuleSerializer();
			serializer.serialize(moduleSet);
		
			LogUtil.log(LogEvent.NOTICE, "SERGe DONE..");
			
		}else{
			throw new EPackageNotFoundException();
		}

	}

}
