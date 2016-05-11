package org.sidiff.editrule.generator.serge;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Module;
import org.sidiff.common.emf.ecore.ECoreTraversal;
import org.sidiff.common.emf.exceptions.EAttributeNotFoundException;
import org.sidiff.common.emf.exceptions.EClassifierUnresolvableException;
import org.sidiff.common.emf.exceptions.EPackageNotFoundException;
import org.sidiff.common.io.IOUtil;
import org.sidiff.common.io.ResourceUtil;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.common.xml.XMLResolver;
import org.sidiff.editrule.generator.IEditRuleGenerator;
import org.sidiff.editrule.generator.exceptions.EditRuleGenerationException;
import org.sidiff.editrule.generator.exceptions.OperationTypeNotImplementedException;
import org.sidiff.editrule.generator.exceptions.WrongSettingsInstanceException;
import org.sidiff.editrule.generator.serge.configuration.Configuration;
import org.sidiff.editrule.generator.serge.configuration.ConfigurationParser;
import org.sidiff.editrule.generator.serge.core.AnnotationApplicator;
import org.sidiff.editrule.generator.serge.core.ConfigSerializer;
import org.sidiff.editrule.generator.serge.core.InternalAnnotationsRemover;
import org.sidiff.editrule.generator.serge.core.InverseModuleMapSerializer;
import org.sidiff.editrule.generator.serge.core.InverseModuleMapper;
import org.sidiff.editrule.generator.serge.core.MainUnitApplicator;
import org.sidiff.editrule.generator.serge.core.MetaModelElementVisitor;
import org.sidiff.editrule.generator.serge.core.ModuleSerializer;
import org.sidiff.editrule.generator.serge.core.NameMapper;
import org.sidiff.editrule.generator.serge.core.RuleParameterApplicator;
import org.sidiff.editrule.generator.serge.exceptions.NoEncapsulatedTypeInformationException;
import org.sidiff.editrule.generator.serge.exceptions.SERGeConfigParserException;
import org.sidiff.editrule.generator.serge.filter.ClassifierInclusionConfiguration;
import org.sidiff.editrule.generator.serge.filter.DuplicateFilter;
import org.sidiff.editrule.generator.serge.filter.ElementFilter;
import org.sidiff.editrule.generator.serge.filter.ExecutableFilter;
import org.sidiff.editrule.generator.serge.metamodelanalysis.EClassifierInfoManagement;
import org.sidiff.editrule.generator.serge.settings.SergeSettings;
import org.sidiff.editrule.generator.settings.EditRuleGenerationSettings;
import org.sidiff.editrule.generator.types.OperationType;

public class Serge implements IEditRuleGenerator {

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
	private Stack<EPackage> ePackagesStack = null;

	/**
	 * The SERGe configuration (CPEO and meta-model specific configuration).
	 */
	private Configuration config = null;

	/**
	 * The settings object (i/o settings).
	 */
	private SergeSettings settings = null;

	@Override
	public void init(EditRuleGenerationSettings settings, IProgressMonitor monitor) throws EditRuleGenerationException, WrongSettingsInstanceException {

		// Start monitor
		monitor.beginTask("Initializing SERGe", 100);
		
		// Check for valid settings
		validateSettings(settings, monitor);
		
		// Parse configuration
		parseConfiguration(monitor);
		
		// Create Instance of global EClassifierInfoManagement
		EClassifierInfoManagement ECM = EClassifierInfoManagement.getInstance();
		
		// Create Instance of global ElementFilter
		ElementFilter.getInstance();
		
		// Analyze meta model
		monitor.subTask("Analyzing MetaModel");
		ECM.gatherInformation(	config.metaModel_is_profile,
								config.EPACKAGESSTACK,
								config.enable_inner_containment_cycle_detection);		
		monitor.worked(80);

		// Effective/Sliced-Metamodel Derivation Phase:
		// Decide by user config and meta-model analysis..
		// (1) ..which elements may occur as dangling child or neighbor in a rule and
		ClassifierInclusionConfiguration.getInstance().collectConfiguredAndRequiredDanglingClassifiers();
		// (2) ..which elements may occur as parent context in a rule and
		ClassifierInclusionConfiguration.getInstance().collectConfiguredAndRequiredDanglingParentContextClassifiers();
		// (3) ..which elements must occur as focal elements in a rule; i.e., must recieve dedicated rules.
		ClassifierInclusionConfiguration.getInstance().collectConfiguredAndRequiredFocalClassifiers();
		
		// Finish monitor
		monitor.done();
	}

	/**
	 * Method to start the generation process.
	 * 
	 * @param monitor
	 * @throws OperationTypeNotImplementedException
	 * @throws IOException
	 * @throws EPackageNotFoundException
	 */
	@Override
	public void generateEditRules(IProgressMonitor monitor) throws EditRuleGenerationException {

		// Visit all model elements and trigger rule generation for each
		monitor.beginTask("Generating CPEOs", 100);
		monitor.subTask("Visit MetaModel elements");
		MetaModelElementVisitor eClassVisitor = new MetaModelElementVisitor();
		ECoreTraversal.traverse(eClassVisitor, ePackagesStack.toArray(new EPackage[ePackagesStack.size()]));
		
		// Store all generated modules in this map by OperationType
		Map<OperationType, Set<Module>> allModules = eClassVisitor.getAllModules();
		monitor.worked(50);
		

		// Filter out not executable or consistency violating rules
		if (config.enable_execution_check_filter) {
			monitor.subTask("Filtering Executions");
			LogUtil.log(LogEvent.NOTICE, "-- Execution Filter --");
			ExecutableFilter executionFilter = new ExecutableFilter();
			executionFilter.applyOn(allModules);
			monitor.worked(5);
		}

		// Filter out duplicate rules
		if (config.enable_duplicate_filter) {
			monitor.subTask("Filtering Duplicates");
			LogUtil.log(LogEvent.NOTICE, "-- Duplicate Filter --");
			DuplicateFilter duplicateFilter = new DuplicateFilter();
			duplicateFilter.filterIdenticalByName(allModules);
			duplicateFilter.filterAddSet(allModules.get(OperationType.ADD),
					allModules.get(OperationType.SET_REFERENCE));
			duplicateFilter.filterRemoveUnset(allModules.get(OperationType.REMOVE),
					allModules.get(OperationType.UNSET_REFERENCE));
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

		// Inverse Module Mapping and Serialization of logs/configs
		monitor.subTask("Logging Inverse Modules and saving Logs");
		if (settings.isSaveLogs()) {			
			LogUtil.log(LogEvent.NOTICE, "-- Inverse Module Log Serializer --");
			InverseModuleMapper inverseModuleMapper = new InverseModuleMapper();
			inverseModuleMapper.findAndMapInversePairs(allModules);
			InverseModuleMapSerializer inverseModuleSerializer = new InverseModuleMapSerializer(settings);
			inverseModuleSerializer.serialize(inverseModuleMapper);
			
			LogUtil.log(LogEvent.NOTICE, "-- Serge Config Serializer --");
			ConfigSerializer configSerializer = new ConfigSerializer(settings);
			try {
				configSerializer.serialize();
			} catch (IOException e) {
				monitor.done();
				throw new EditRuleGenerationException("When saving config/log.\n" + e.getMessage());
			}
		}
		monitor.worked(5);

		// Name Mapping and Serialization...
		monitor.subTask("Change module names according to name map");
		Set<Module> moduleSet = new HashSet<Module>();
		for (OperationType opType : allModules.keySet()) {
			Set<Module> opSet = allModules.get(opType);
			if (!opSet.isEmpty()) {
				moduleSet.addAll(opSet);
			}
		}	
		if (config.enable_name_mapper) {
			LogUtil.log(LogEvent.NOTICE, "-- Name Mapper --");
			NameMapper nameMapper = new NameMapper(Configuration.getInstance().metaModel, moduleSet);
			nameMapper.replaceNames();
		}
		monitor.worked(5);

		// Generate Annotations
		monitor.subTask("Generate Annotations");
		AnnotationApplicator annotationApplicator = new AnnotationApplicator();
		annotationApplicator.applyOn(allModules);		
		// Remove internal Annotations
		for (Set<Module> modules : allModules.values()) {
			for (Module m : modules) {
				InternalAnnotationsRemover.removeInternalAnnotations(m);
			}
		}
		monitor.worked(5);

		// Serialize modules
		monitor.subTask("Saving Edit Rules");
		LogUtil.log(LogEvent.NOTICE, "-- Module Serializer --");
		ModuleSerializer serializer = new ModuleSerializer(settings);
		try {
			serializer.serialize(moduleSet);
		} catch (OperationTypeNotImplementedException e) {
			monitor.done();
			throw new EditRuleGenerationException(
					"Error when serializing modules." + "There is an unsupported operation type.\n" + e.getMessage());
		}
		// finish
		monitor.worked(10);
		LogUtil.log(LogEvent.NOTICE, "SERGe DONE..");
		monitor.done();
	}
	
	/**
	 * Checks if the settings object is valid
	 * 
	 * @param settings
	 * 
	 * @param monitor
	 * 
	 * @throws WrongSettingsInstanceException
	 * @throws EditRuleGenerationException
	 */
	private void validateSettings(EditRuleGenerationSettings settings, IProgressMonitor monitor) throws WrongSettingsInstanceException, EditRuleGenerationException {
	
		// First check if the given settings object
		if(!(settings instanceof EditRuleGenerationSettings)) {
			throw new WrongSettingsInstanceException();
		}

		// Create more specific SergeSettings out of the general
		// EditRuleGenerationSettings
		this.settings = new SergeSettings(settings);

	}
	
	/**
	 * Validate and parse Configuration
	 * 
	 * @param monitor
	 * 
	 * @throws EditRuleGenerationException
	 */
	private void parseConfiguration(IProgressMonitor monitor) throws EditRuleGenerationException {

		// Load config's DTD
		ResourceUtil.registerClassLoader(this.getClass().getClassLoader());
		XMLResolver.getInstance().includeMapping(IOUtil
				.getInputStream("platform:/plugin/" + PLUGIN_NAME + "/config/Editrulesgeneratorconfig.dtdmap.xml"));

		// Create empty instance of the SergeConfiguration
		this.config = Configuration.getInstance();

		// Use default config (if config path not set in settings).
		if (settings.getConfigPath() == null) {
			monitor.subTask("Setting up default configuration..");
			this.settings.setConfigPath("platform:/plugin/" + PLUGIN_NAME + "/config/DefaultConfigTemplate.xml");
			ConfigurationParser parser = new ConfigurationParser();
			try {
				parser.setupDefaultConfig(settings.getMetaModelNsUri(), this.settings.getConfigPath());
				monitor.worked(20);
			} catch (Exception e) {
				monitor.done();
				throw new EditRuleGenerationException(
						"Error when loading selected meta model NsUri\n" + e.getMessage());
			}
		}
		// .. or use refined config and parse it.
		else {
			monitor.subTask("Loading configuration..");
			ConfigurationParser parser = new ConfigurationParser();
			try {
				parser.parse(this.settings.getConfigPath());
				monitor.worked(20);
			} catch (SERGeConfigParserException | EPackageNotFoundException | NoEncapsulatedTypeInformationException
					| EAttributeNotFoundException | EClassifierUnresolvableException | ParserConfigurationException
					| IOException e) {
				monitor.done();
				throw new EditRuleGenerationException("Error when parsing config file."
						+ "Check for typos, validity and wellformedness.\n" + e.getMessage());
			}
		}
		
		// set the EPackage Stack
		this.ePackagesStack = config.EPACKAGESSTACK;		
		if ((ePackagesStack == null) || (ePackagesStack.isEmpty())) {
			monitor.done();
			throw new EditRuleGenerationException("EPackage could not be resolved.\n");
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
