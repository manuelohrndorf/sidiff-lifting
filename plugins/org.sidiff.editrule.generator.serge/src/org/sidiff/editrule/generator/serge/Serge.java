package org.sidiff.editrule.generator.serge;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Module;
import org.sidiff.common.emf.ecore.ECoreTraversal;
import org.sidiff.common.emf.exceptions.EAttributeNotFoundException;
import org.sidiff.common.emf.exceptions.EClassifierUnresolvableException;
import org.sidiff.common.emf.exceptions.EPackageNotFoundException;
import org.sidiff.common.emf.extensions.impl.EClassifierInfoManagement;
import org.sidiff.common.io.IOUtil;
import org.sidiff.common.io.ResourceUtil;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.common.xml.XMLResolver;
import org.sidiff.editrule.generator.IEditRuleGenerator;
import org.sidiff.editrule.generator.exceptions.EditRuleGenerationException;
import org.sidiff.editrule.generator.exceptions.OperationTypeNotImplementedException;
import org.sidiff.editrule.generator.serge.configuration.Configuration;
import org.sidiff.editrule.generator.serge.configuration.ConfigurationParser;
import org.sidiff.editrule.generator.serge.core.AnnotationApplicator;
import org.sidiff.editrule.generator.serge.core.AnnotationGenerator;
import org.sidiff.editrule.generator.serge.core.ConfigSerializer;
import org.sidiff.editrule.generator.serge.core.ConstraintApplicator;
import org.sidiff.editrule.generator.serge.core.InverseModuleMapSerializer;
import org.sidiff.editrule.generator.serge.core.InverseModuleMapper;
import org.sidiff.editrule.generator.serge.core.InverseTracker;
import org.sidiff.editrule.generator.serge.core.MainUnitApplicator;
import org.sidiff.editrule.generator.serge.core.MetaModelElementVisitor;
import org.sidiff.editrule.generator.serge.core.ModuleSerializer;
import org.sidiff.editrule.generator.serge.core.NameMapper;
import org.sidiff.editrule.generator.serge.core.RuleParameterApplicator;
import org.sidiff.editrule.generator.serge.exceptions.NoEncapsulatedTypeInformationException;
import org.sidiff.editrule.generator.serge.exceptions.SERGeConfigParserException;
import org.sidiff.editrule.generator.serge.filter.DuplicateFilter;
import org.sidiff.editrule.generator.serge.filter.ElementFilter;
import org.sidiff.editrule.generator.serge.filter.ExecutableFilter;
import org.sidiff.editrule.generator.serge.settings.SergeSettings;
import org.sidiff.editrule.generator.settings.EditRuleGenerationSettings;
import org.sidiff.editrule.generator.types.OperationType;


/** Todo-List :
* 
* - make monitors cancel-able
* - InverseMapping can't be saved anymore due to path setting changes, fix this.
* - XML-Config should be cleaned up (so does the Configuration Parser)
* - more detailed comment in filter identical / filter duplicates
* - more detailed comments to when rule might not be executable in ExecutionChecker
* - remove all the deprecated marks, old classes, old todos/fixmes
* - ProfileModelIntegration: generate APPLY_STEREOTYPE, DETACH_STEREOTYPE modules:
* This requires:
* --> new OperationTypes in the org.sidiff.editrule.generator.types-plugin
* --> new actions classes in the "..generator.actions"-package
* --> respective action calls in GenerationActionDelegator ("..serge.core"-package)
* --> respective delegator calls in MetaModelElementVisitor ("..serge.core"-package) 
*        and mappings in getAllModules() method
* --> further case-distinctions in ElementFilter ("..serge.filter"-package)
*        inside the methods isAllowedAsModuleBasis() and isAllowedAsDangling() to let them
*        allow the generation of APPlY_STEREOTYPE / DETACH_STEREOTYPE modules in case of profile
*        usage and stereotype consideration.
*/
public class Serge implements IEditRuleGenerator{

	/**
	 * Plugin name. Necessary to access dtdmap and dtd files.
	 */
	public final static String PLUGIN_NAME = "org.sidiff.editrule.generator.serge";
	
	/**
	 * The Generator Key defining which type of EditruleGenerator is used.
	 */
	public final static String GENERATOR_KEY = "serge";
	
	/**
	 * The involved meta-models.
	 */
	private static Stack<EPackage> ePackagesStack = null;
	
	/**
	 * The SERGe configuration (CPEO and meta-model specific configuration).
	 */
	private static Configuration config = null;
	
	/**
	 * The settings object (i/o settings).
	 */
	private SergeSettings settings = null;
	
	@Override
	public void init(EditRuleGenerationSettings settings, IProgressMonitor monitor) throws EditRuleGenerationException {
		
		// Start monitor
		monitor.beginTask("Initializing SERGe", 100);
		
		// First check if the given settings object really is a EditRuleGenerationSettings object
		assert(settings instanceof EditRuleGenerationSettings): "Given Settings are not EditRuleGenerationSettings";

		// Create more specific SergeSettings out of the general EditRuleGenerationSettings
		this.settings = new SergeSettings(settings);		
		
		// Load config's DTD
		ResourceUtil.registerClassLoader(this.getClass().getClassLoader());
		XMLResolver.getInstance().includeMapping(IOUtil.getInputStream(
				"platform:/plugin/"+PLUGIN_NAME+"/config/Editrulesgeneratorconfig.dtdmap.xml")); 
		
		// Create empty instance of the SergeConfiguration
		config = Configuration.getInstance();
		EClassifierInfoManagement ECM = EClassifierInfoManagement.getInstance();
		ElementFilter.getInstance();
		
		// Case default config (if config path not set in settings).
		if(settings.getConfigPath() == null) {	
			monitor.subTask("Setting up default configuration..");
			this.settings.setConfigPath("platform:/plugin/"+PLUGIN_NAME+"/config/DefaultConfigTemplate.xml");
			ConfigurationParser parser = new ConfigurationParser();
			try {
				parser.setupDefaultConfig(
						settings.getMetaModelNsUri(),
						this.settings.getConfigPath());
				monitor.worked(20);
			} catch (Exception e) {
				e.printStackTrace();
				monitor.done();
				throw new EditRuleGenerationException(e);
			}
		}
		// Case refined config.
		else{
			monitor.subTask("Loading configuration..");	
			ConfigurationParser parser = new ConfigurationParser();
			try {
				parser.parse(this.settings.getConfigPath());
				monitor.worked(20);
			}
			catch (SERGeConfigParserException 
					| EPackageNotFoundException
					| NoEncapsulatedTypeInformationException
					| EAttributeNotFoundException
					| EClassifierUnresolvableException e)
			{
				e.printStackTrace();
				monitor.done();
				throw new EditRuleGenerationException(e);
			}		
		}
		
		// Analyse meta model
		monitor.subTask("Analyzing MetaModel");
		ECM.gatherInformation(
				config.PROFILE_APPLICATION_IN_USE,
				config.EPACKAGESSTACK,
				config.ENABLE_INNER_CONTAINMENT_CYCLE_DETECTION);
		ePackagesStack = config.EPACKAGESSTACK;
		monitor.worked(80);

		// Finish monitor
		monitor.done();	
	}

	
	/**
	 * Method to start the generation process.
	 * @param monitor 
	 * @throws OperationTypeNotImplementedException 
	 * @throws IOException 
	 * @throws EPackageNotFoundException 
	 */
	@Override
	public void generateEditRules(IProgressMonitor monitor) throws IOException, EPackageNotFoundException, OperationTypeNotImplementedException{

		monitor.beginTask("Generating CPEOs", 100);	
		//TODO make use of the monitor in a more fine grained way
		
		//TODO pass the monitor to make the generation canceable!
		// This has to be an new SubProgressMonitor if passed for
		// right scaling
		
		if(ePackagesStack != null && !ePackagesStack.isEmpty()){			
			
			monitor.subTask("Visit MetaModel elements");
			MetaModelElementVisitor eClassVisitor = new MetaModelElementVisitor();
			ECoreTraversal.traverse(eClassVisitor, ePackagesStack.toArray(new EPackage[ePackagesStack.size()]));				
			monitor.worked(50);
			// Postprocessing...
			
			Map<OperationType, Set<Module>> allModules = eClassVisitor.getAllModules();
			
			if (config.MULTIPLICITY_PRECONDITIONS_INTEGRATED){
				monitor.subTask("Applying Constraints");
				LogUtil.log(LogEvent.NOTICE, "-- Constraint Applicator --");
				ConstraintApplicator constraintApplicator = new ConstraintApplicator();
				constraintApplicator.applyOn(allModules);
				monitor.worked(5);
			}
			
			if (config.ENABLE_EXECUTION_CHECK_FILTER){
				monitor.subTask("Filtering Executions");
				LogUtil.log(LogEvent.NOTICE, "-- Execution Filter --");
				ExecutableFilter executionFilter = new ExecutableFilter();
				executionFilter.applyOn(allModules);
				monitor.worked(5);
			}
			
			if(config.ENABLE_DUPLICATE_FILTER) {
				monitor.subTask("Filtering Duplicates");
				LogUtil.log(LogEvent.NOTICE, "-- Duplicate Filter --");
				DuplicateFilter duplicateFilter = new DuplicateFilter();
				duplicateFilter.filterIdenticalByName(allModules);
				duplicateFilter.filterAddSet(allModules.get(OperationType.ADD), allModules.get(OperationType.SET_REFERENCE));
				duplicateFilter.filterRemoveUnset(allModules.get(OperationType.REMOVE), allModules.get(OperationType.UNSET_REFERENCE));
				monitor.worked(5);

			}
			
			// Rule Parameter Application
			monitor.subTask("Applying Rule Parameter");
			LogUtil.log(LogEvent.NOTICE, "-- Rule Parameter Applicator --");
			RuleParameterApplicator ruleParameterApplicator = new RuleParameterApplicator();
			ruleParameterApplicator.applyOn(allModules);
			monitor.worked(5);

			// MainUnit Application
			monitor.subTask("Applying Main Unit");
			LogUtil.log(LogEvent.NOTICE, "-- Main Unit Applicator --");
			MainUnitApplicator mainUnitApplicator = new MainUnitApplicator();
			mainUnitApplicator.applyOn(allModules);
			monitor.worked(5);

			// Serialization of logs/configs		
			if(settings.isSaveLogs()) {
				monitor.subTask("Logging Inverse Modules");
				LogUtil.log(LogEvent.NOTICE, "-- Inverse Module Log Serializer --");
				InverseModuleMapper inverseModuleMapper = new InverseModuleMapper();
				inverseModuleMapper.findAndMapInversePairs(allModules);
				InverseModuleMapSerializer inverseModuleSerializer = new InverseModuleMapSerializer(settings);
				inverseModuleSerializer.serialize(inverseModuleMapper);				
				monitor.worked(3);
				
				monitor.subTask("Saving Serge Configuration");
				LogUtil.log(LogEvent.NOTICE, "-- Serge Config Serializer --");
				ConfigSerializer configSerializer = new ConfigSerializer(settings);
				configSerializer.serialize();
				monitor.worked(2);				
			}
			
			
			// delete contents of manual folder if wished so
			if(settings.isDeleteManualTransformations()) {
				monitor.subTask("Deleting Manual Transformations");
				File manualFolder = new File(settings.getOutputFolderPath() + "manual");
				if(manualFolder.exists()) {
					for(File f: manualFolder.listFiles()) {
						f.delete();
						
					}		
				}
				monitor.worked(5);
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
				monitor.subTask("Mapping Names");
				LogUtil.log(LogEvent.NOTICE, "-- Name Mapper --");
				NameMapper nameMapper = new NameMapper(Configuration.getInstance().METAMODEL, moduleSet);
				nameMapper.replaceNames();
				monitor.worked(5);
			}
			
			//Generate Annotations
			AnnotationApplicator annotationApplicator = new AnnotationApplicator();
			annotationApplicator.applyOn(allModules);
			InverseTracker.INSTANCE.printInverses();
			
			// Serialize modules
			monitor.subTask("Saving Edit Rules");
			LogUtil.log(LogEvent.NOTICE, "-- Module Serializer --");
			ModuleSerializer serializer = new ModuleSerializer(settings);
			serializer.serialize(moduleSet);
			monitor.worked(10);
			
			LogUtil.log(LogEvent.NOTICE, "SERGe DONE..");
			
			monitor.done();
			
		}else{
			monitor.done();
			throw new EPackageNotFoundException();			
		}
	}


	@Override
	public String getName() {
		return "SERGe (SiDiff EditRule Generator)";
	}


	@Override
	public String getKey() {
		return GENERATOR_KEY;
	}

}
