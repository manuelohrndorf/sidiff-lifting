package org.sidiff.editrule.generator.serge;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
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
import org.sidiff.editrule.generator.IEditRuleGenerator;
import org.sidiff.editrule.generator.exceptions.OperationTypeNotImplementedException;
import org.sidiff.editrule.generator.serge.configuration.Configuration;
import org.sidiff.editrule.generator.serge.configuration.ConfigurationParser;
import org.sidiff.editrule.generator.serge.core.ConstraintApplicator;
import org.sidiff.editrule.generator.serge.core.InverseModuleMapSerializer;
import org.sidiff.editrule.generator.serge.core.InverseModuleMapper;
import org.sidiff.editrule.generator.serge.core.MainUnitApplicator;
import org.sidiff.editrule.generator.serge.core.MetaModelElementVisitor;
import org.sidiff.editrule.generator.serge.core.ModuleSerializer;
import org.sidiff.editrule.generator.serge.core.NameMapper;
import org.sidiff.editrule.generator.serge.core.RuleParameterApplicator;
import org.sidiff.editrule.generator.serge.filter.DuplicateFilter;
import org.sidiff.editrule.generator.serge.filter.ElementFilter;
import org.sidiff.editrule.generator.serge.filter.ExecutableFilter;
import org.sidiff.editrule.generator.serge.settings.SergeSettings;
import org.sidiff.editrule.generator.settings.EditRuleGeneratorSettings;
import org.sidiff.editrule.generator.types.OperationType;

public class Serge implements IEditRuleGenerator{

	/**
	 * Plugin name. Necessary to access dtdmap and dtd files.
	 */
	private final static String PLUGIN_NAME = "org.sidiff.editrule.generator.serge";
	
	/**
	 * The involved meta-models.
	 */
	private static Stack<EPackage> ePackagesStack = null;

	
	/**
	 * The SERGe configuration.
	 */
	private static Configuration config = null;
	
	private SergeSettings settings = null;
	
	@Override
	public void init(EditRuleGeneratorSettings settings) {
		
		ResourceUtil.registerClassLoader(this.getClass().getClassLoader());
		XMLResolver.getInstance().includeMapping(IOUtil.getInputStream(
				"platform:/plugin/"+PLUGIN_NAME+"/config/Editrulesgeneratorconfig.dtdmap.xml")); 

		if(settings instanceof SergeSettings){
			this.settings = (SergeSettings)settings;
		}
		
		assert(this.settings != null) : "This is no valid SergeSettings Instance:" + settings.toString();
				
		try {
			// create empty instances
			config = Configuration.getInstance();
			EClassifierInfoManagement ECM = EClassifierInfoManagement.getInstance();
			ElementFilter.getInstance();
			
			// parse and gather infos
			ConfigurationParser parser = new ConfigurationParser();
			parser.parse(this.settings.getConfigPath());
			ECM.gatherInformation(config.PROFILE_APPLICATION_IN_USE, config.EPACKAGESSTACK, config.ENABLE_INNER_CONTAINMENT_CYCLE_DETECTION);
			
			// get ePackageStack for usage in generate()
			ePackagesStack = config.EPACKAGESSTACK;
		
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
	}

	
	/**
	 * Method to start the generation process.
	 * @throws OperationTypeNotImplementedException 
	 * @throws IOException 
	 */
	public void generateEditRules() throws EPackageNotFoundException, OperationTypeNotImplementedException, IOException {

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
			
			// InverseModulePair collection and log serialization + copy config file
			
			if(settings.isSaveLogs()) {
				LogUtil.log(LogEvent.NOTICE, "-- Inverse Module Log Serializer --");
				InverseModuleMapper inverseModuleMapper = new InverseModuleMapper();
				inverseModuleMapper.findAndMapInversePairs(allModules);
				InverseModuleMapSerializer inverseModuleSerializer = new InverseModuleMapSerializer(settings);
				inverseModuleSerializer.serialize(inverseModuleMapper);
				
				// copy config file to target transformation folder
				String timestamp = new java.text.SimpleDateFormat("YYYYMMdd").format(new Date());
				Path sourceConfig = Paths.get(settings.getConfigPath());
				Path targetConfig = Paths.get(settings.getOutputFolderPath()
																				+ System.getProperty("file.separator")
																				+ "usedConfig"
																				+ "_"+ timestamp + ".serge");
				if(Files.exists(targetConfig) && !settings.isOverwriteConfigInTargetFolder()) {
					timestamp = new java.text.SimpleDateFormat("YYYYMMdd_hhmmss").format(new Date());
					targetConfig = Paths.get(settings.getOutputFolderPath()
																		+ System.getProperty("file.separator")
																		+ "usedConfig"
																		+ "_"+ timestamp + ".serge");
							
				}
				Files.copy(sourceConfig, targetConfig, StandardCopyOption.REPLACE_EXISTING);
				
			}

			
			
			// delete contents of manual folder if wished so
			if(settings.isDeleteManualTransformations()) {
				File manualFolder = new File(settings.getOutputFolderPath() + "manual");
				if(manualFolder.exists()) {
					for(File f: manualFolder.listFiles()) {
						f.delete();
						
					}		
				}
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


	@Override
	public String getName() {
		return "SERGe (SiDiff EditRule Generator)";
	}


	@Override
	public String getKey() {
		return "serge";
	}

}
