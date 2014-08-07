package org.sidiff.serge;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Module;
import org.sidiff.common.emf.ecore.ECoreTraversal;
import org.sidiff.common.emf.exceptions.EPackageNotFoundException;
import org.sidiff.common.emf.extensions.impl.EClassifierInfoManagement;
import org.sidiff.common.io.IOUtil;
import org.sidiff.common.io.ResourceUtil;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.common.xml.XMLResolver;
import org.sidiff.serge.configuration.Configuration;
import org.sidiff.serge.configuration.Configuration.OperationType;
import org.sidiff.serge.configuration.ConfigurationParser;
import org.sidiff.serge.core.ConstraintApplicator;
import org.sidiff.serge.core.InverseModuleMapper;
import org.sidiff.serge.core.InverseModuleMapSerializer;
import org.sidiff.serge.core.MainUnitApplicator;
import org.sidiff.serge.core.MetaModelElementVisitor;
import org.sidiff.serge.core.ModuleSerializer;
import org.sidiff.serge.core.NameMapper;
import org.sidiff.serge.core.RuleParameterApplicator;
import org.sidiff.serge.exceptions.OperationTypeNotImplementedException;
import org.sidiff.serge.filter.DuplicateFilter;
import org.sidiff.serge.filter.ElementFilter;
import org.sidiff.serge.filter.ExecutableFilter;
import org.sidiff.serge.settings.SergeSettings;

public class Serge {

	/**
	 * The involved meta-models.
	 */
	private static Stack<EPackage> ePackagesStack = null;
	
	/**
	 * The SERGe configuration.
	 */
	private static Configuration config = null;
	
	private SergeSettings settings = null;
	
	public Serge(SergeSettings settings) {
		
		ResourceUtil.registerClassLoader(this.getClass().getClassLoader());
		XMLResolver.getInstance().includeMapping(IOUtil.getInputStream("Editrulesgeneratorconfig.dtdmap.xml"));
		
		this.settings = settings;
				
		try {
			// create empty instances
			config = Configuration.getInstance();
			EClassifierInfoManagement ECM = EClassifierInfoManagement.getInstance();
			ElementFilter.getInstance();
			
			// parse and gather infos
			ConfigurationParser parser = new ConfigurationParser();
			parser.parse(settings.getConfigPath());
			ECM.gatherInformation(config.PROFILE_APPLICATION_IN_USE, config.EPACKAGESSTACK);
			
			// get ePackageStack for usage in generate()
			ePackagesStack = config.EPACKAGESSTACK;
		
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
	}

	
	/**
	 * Method to start the generation process.
	 * @throws OperationTypeNotImplementedException 
	 */
	public void generate() throws EPackageNotFoundException, OperationTypeNotImplementedException {

		if(ePackagesStack != null && !ePackagesStack.isEmpty()){
			MetaModelElementVisitor eClassVisitor = new MetaModelElementVisitor();
			ECoreTraversal.traverse(eClassVisitor, ePackagesStack.toArray(new EPackage[ePackagesStack.size()]));				
		
			// Postprocessing...
			
			Map<OperationType, Set<Module>> allModules = eClassVisitor.getAllModules();
			
			if (config.MULTIPLICITY_PRECONDITIONS_INTEGRATED){
				LogUtil.log(LogEvent.NOTICE, "-- Constraint Applicator --");
				ConstraintApplicator constraintApplicator = new ConstraintApplicator();
				constraintApplicator.applyOn(allModules);
			}
			
			if (config.ENABLE_EXECUTION_CHECK_FILTER){
				LogUtil.log(LogEvent.NOTICE, "-- Execution Filter --");
				ExecutableFilter executionFilter = new ExecutableFilter();
				executionFilter.applyOn(allModules);
			}
			
			if(config.ENABLE_DUPLICATE_FILTER) {
				LogUtil.log(LogEvent.NOTICE, "-- Duplicate Filter --");
				DuplicateFilter duplicateFilter = new DuplicateFilter();
				duplicateFilter.filterIdenticalByName(allModules);
				duplicateFilter.filterAddSet(allModules.get(OperationType.ADD), allModules.get(OperationType.SET_REFERENCE));
				duplicateFilter.filterRemoveUnset(allModules.get(OperationType.REMOVE), allModules.get(OperationType.UNSET_REFERENCE));
			}
			
			// Rule Parameter Application
			
			LogUtil.log(LogEvent.NOTICE, "-- Rule Parameter Applicator --");
			RuleParameterApplicator ruleParameterApplicator = new RuleParameterApplicator();
			ruleParameterApplicator.applyOn(allModules);
			
			// MainUnit Application
			
			LogUtil.log(LogEvent.NOTICE, "-- Main Unit Applicator --");
			MainUnitApplicator mainUnitApplicator = new MainUnitApplicator();
			mainUnitApplicator.applyOn(allModules);
			
			// InverseModulePair collection and log serialization
			
			if(config.ENABLE_INVERSE_MAPPING) {
				LogUtil.log(LogEvent.NOTICE, "-- Inverse Module Log Serializer --");
				InverseModuleMapper inverseModuleMapper = new InverseModuleMapper();
				inverseModuleMapper.findAndMapInversePairs(allModules);
				InverseModuleMapSerializer inverseModuleSerializer = new InverseModuleMapSerializer(settings);
				inverseModuleSerializer.serialize(inverseModuleMapper);
			}
			
			// Name Mapping and Serialization...
			
			Set<Module> moduleSet = new HashSet<Module>();
			for (OperationType opType : allModules.keySet()) {
				Set<Module> opSet = allModules.get(opType);
				if(!opSet.isEmpty()) {
					moduleSet.addAll(opSet);
				}
			}
			
			if(config.ENABLE_NAME_MAPPER) {
				LogUtil.log(LogEvent.NOTICE, "-- Name Mapper --");
				NameMapper nameMapper = new NameMapper(Configuration.getInstance().METAMODEL, moduleSet);
				nameMapper.replaceNames();
			}
			
			// Serialize modules
			
			LogUtil.log(LogEvent.NOTICE, "-- Module Serializer --");
			ModuleSerializer serializer = new ModuleSerializer(settings);
			serializer.serialize(moduleSet);
			
			LogUtil.log(LogEvent.NOTICE, "SERGe DONE..");
			
		}else{
			throw new EPackageNotFoundException();
		}
	}

}
