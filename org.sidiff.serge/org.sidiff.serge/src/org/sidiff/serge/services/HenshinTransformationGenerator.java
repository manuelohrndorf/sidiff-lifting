package org.sidiff.serge.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.emf.henshin.model.PriorityUnit;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Unit;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;
import org.sidiff.common.henshin.INamingConventions;
import org.sidiff.common.henshin.NodePair;
import org.sidiff.common.io.IOUtil;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.common.xml.XMLParser;
import org.sidiff.serge.util.Common;
import org.sidiff.serge.util.EClassInfo;
import org.sidiff.serge.util.EClassInfoManagement;
import org.sidiff.serge.util.ModuleFilenamePair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class HenshinTransformationGenerator extends AbstractHenshinTransformationGenerator {
	
	/** Next tasks and ideas:
	 * - initials and finals for stereotype
	 * - stereotype versions x abstract replacements x subtype replacements
	 * - check if update-trafos need special stereotype handling
	 * - check if there is some profile setting prohibiting meta classes as standalones
	 * - Integrate configuration property that prevents SET-Trafos for "base_X"-extensions?
	 * - what about new references between stereotypes?
	 * - what about giving every stereotype a parameter for SMG-Selectivity (and maybe dropping the parameter of the metaclass)
	 * - Should stereotype be mentioned in every rule where its meta class exists (for better selectivity by stereotype or its properties)
	 * - configuration option for "assumeAllOnWhiteList" only for profile model?
	 * - reuse of finalizeStereotypeVersion()-content to avoid redundant code
	 * - externalizing of strings and more refactoring
	 * - variant matrix with bitsets to avoid semantically equal trafos
	 **/ 
	
	/** General settings **************************************************************************/
	
	private final String CREATE_prefix 			= "CREATE_";
	private final String DELETE_prefix			= "DELETE_";
	private final String SET_prefix 			= "SET_";
	private final String UNSET_prefix 			= "UNSET_";
	private final String ADD_prefix 			= "ADD_";
	private final String REMOVE_prefix 			= "REMOVE_";
	private final String CHANGE_prefix 			= "CHANGE_";
	private final String MOVE_prefix 			= "MOVE_";
	private final String EXECUTE_suffix			= "_execute.henshin";
	private final String INITIALCHECK_suffix	= "_initialcheck.henshin";

	private static final String REQ_IS			= "InheritingSupertypes";
	
	private String outputFolderPath  = null;
	private static List<EPackage> ePackages = null;
	
	private static EClassInfoManagement eClassInfoManagement = null;
	
	private static enum OperationType { CREATE,DELETE,SET,UNSET,ADD,REMOVE,CHANGE,MOVE; }
	
	/** Configuration *****************************************************************************/

	private static boolean createCREATES						= true;
	private static boolean createDELETES						= true; // CREATES are required
	private static boolean createMOVES							= true;
	private static boolean createADDS							= true;
	private static boolean createREMOVES						= true; // ADDS are required
	private static boolean createSETS							= true;
	private static boolean createUNSETS							= true; // SETS are required
	private static boolean createCHANGES						= true;
	
	private static boolean createINITIALS						= true;
	private static boolean createNotRequiredAndNotIDAttributes	= true;
	private static boolean preventInconsistencyThroughSkipping  = false;
	private static boolean reduceToSuperType_SETUNSET			= true;
	private static boolean reduceToSuperType_ADDREMOVE			= true;
	private static boolean reduceToSuperType_CHANGE				= true;
	
	private static ArrayList<String> blackList					= new ArrayList<String>();
	private static ArrayList<EClass> unfoldedBlackList			= new ArrayList<EClass>();
	
	private static ArrayList<String> whiteList					= new ArrayList<String>();
	private static ArrayList<EClass> unfoldedWhiteList			= new ArrayList<EClass>();
	
	private static HashMap<String,ArrayList<EClass>> implicitRequirements	= new HashMap<String,ArrayList<EClass>>();
	
	private static String rootName								= null;
	private static EClass root									= null;
	private static Boolean rootEClassCanBeNested				= false;
	
	private static Boolean profileApplicationInUse				= false;
	private static Boolean disableVariants						= true;
	
	/** Henshin access ****************************************************************************/
	
	private static HenshinFactory henshinFactory = HenshinFactory.eINSTANCE;
			

	public HenshinTransformationGenerator(String outputFolderPath, List<EPackage> ePackages, String config) {
		
		this.outputFolderPath 	= outputFolderPath;
		this.ePackages			= ePackages;

		LogUtil.log(LogEvent.NOTICE, "Generation service for henshin edit rules initialized.");
		
		// load configuration from xml
		loadConfiguration(config);
		
		LogUtil.log(LogEvent.NOTICE, "Analysing meta model...");
		
		implicitRequirements.put(REQ_IS, new ArrayList<EClass>());	
		eClassInfoManagement = new EClassInfoManagement(profileApplicationInUse);	
		eClassInfoManagement.mapConcreteEClassesToAbstractSuperTypes(ePackages);
		eClassInfoManagement.gatherAllEClassInfos(ePackages);
		eClassInfoManagement.linkSubTypesToSuperTypes(ePackages);		
		
		// unfold lists
		unfoldBlackList();
		unfoldWhiteList();
		

		
	}



	@Override
	public void generate_CREATE_And_DELETE_Modules(EClass eClass) {
		
		// Get the EClassInfo for eClass and return if no transformations are allowed
		EClassInfo eClassInfo = eClassInfoManagement.getEClassInfo(eClass);
		
		// return on the following conditions
		if (!eClassInfo.selfMayHaveTransformations()) return;
		if ((isRoot(eClass) && !rootEClassCanBeNested)) return;
		if (!isAllowed(eClass,true)) return;
		if (isOnlyImplicitlyRequiredForFeatureInheritance(eClass)) return;
		if (!createCREATES) return;

		
		HashMap<EReference, List<EClass>> optionalParents = eClassInfoManagement.getAllOptionalParentContext(eClass);
		
		// Create Modules for every parent in which the EClass may exist.
		for(Entry<EReference,List<EClass>> pcEntry: optionalParents.entrySet()) {			
			List<EClass> contexts = pcEntry.getValue();
			EReference eRef = pcEntry.getKey();
			
			for(EClass context: contexts) {
				
				if (!isAllowed(context,false)) continue;
				
				// Create file name and Module				
				Module module = henshinFactory.createModule();
				String outputFileName = "";
				
				if(!eClassInfoManagement.hasMultipleOccurences(context, optionalParents)) {

					LogUtil.log(LogEvent.NOTICE, "Generating CREATE : " + eClass.getName() +"In"+context.getName());
					outputFileName = outputFolderPath + CREATE_prefix + eClass.getName() +"In" + context.getName()+ EXECUTE_suffix;					
					module.setDescription("Creates one "+eClass.getName()+" in " + context.getName());
					module.setName(CREATE_prefix + eClass.getName()+"In" + context.getName());
				
				}else{
					
					LogUtil.log(LogEvent.NOTICE, "Generating CREATE : " + eClass.getName() +"In"+context.getName()+"WithRef"+Common.firstLetterToUpperCase(eRef.getName()));
					outputFileName = outputFolderPath + CREATE_prefix + eClass.getName() +"In" + context.getName()+"WithRef"+Common.firstLetterToUpperCase(eRef.getName())+EXECUTE_suffix;					
					module.setDescription("Creates one "+eClass.getName()+" in " + context.getName() + " with reference "+Common.firstLetterToUpperCase(eRef.getName()));
					module.setName(CREATE_prefix + eClass.getName()+"In" + context.getName()+"WithRef"+Common.firstLetterToUpperCase(eRef.getName()));
					
				}
				
				// Add imports for meta model
				module.getImports().addAll(ePackages);
								
				// create rule
				Rule rule = createSimpleCreateRule(context, eClass, module, eRef,1);
				Node newNode = HenshinRuleAnalysisUtilEx.getRHSMinusLHSNodes(rule).get(0);

				// create mandatories if any
				if(eClassInfo.hasMandatories()) {

					createMandatoryChildren(rule, eClassInfo, newNode);			
					createMandatoryNeighbours(rule, eClassInfo, newNode);

				}
				
				// Create stereotype version
				for(EClass stereotype : eClassInfo.getStereotypes()) {
					if (!isAllowed(stereotype,true)) continue;
					EClassInfo stereotypeInfo = eClassInfoManagement.getEClassInfo(stereotype);
					
					Module moduleWithStereotype = EcoreUtil.copy(module);
					moduleWithStereotype.setName(moduleWithStereotype.getName().replaceFirst(eClass.getName(), stereotype.getName()));
					moduleWithStereotype.setDescription(moduleWithStereotype.getDescription().replaceFirst("Creates one "+eClass.getName(), "Creates one stereotype:"+stereotype.getName()));
					String stereoOutputFileName = outputFileName.replaceFirst(eClass.getName(), stereotype.getName());
					
					Rule ruleWithStereotype = HenshinRuleAnalysisUtilEx.getRulesUnderModule(moduleWithStereotype).get(0);
					ruleWithStereotype.setName(ruleWithStereotype.getName().replaceFirst(eClass.getName(), stereotype.getName()));
					ruleWithStereotype.setDescription(ruleWithStereotype.getDescription().replaceFirst("Creates one "+eClass.getName(), "Creates one stereotype:"+stereotype.getName()));
					//new stereotype node
					Node newStereotypeNode = HenshinRuleAnalysisUtilEx.createCreateNode(ruleWithStereotype.getRhs(), "", stereotype);
					//get metaclass node and create edge "base_<metaclass>"
					Node metaClassNode = null;
					for(Node node: HenshinRuleAnalysisUtilEx.getRHSMinusLHSNodes(ruleWithStereotype)) {
						if(node.getName().equals("New")) {
							metaClassNode = node;
							break;
						}
					}
					EReference baseERef = (EReference) stereotype.getEStructuralFeature("base_"+eClass.getName());
					HenshinRuleAnalysisUtilEx.createCreateEdge(newStereotypeNode, metaClassNode, baseERef);
					
					
					// create mandatories if any
					if(stereotypeInfo.hasMandatories()) {

						createMandatoryChildren(ruleWithStereotype, stereotypeInfo, newStereotypeNode);			
						createMandatoryNeighbours(ruleWithStereotype, stereotypeInfo, newStereotypeNode);

					}
					// finalize stereotype versions (TODO abstract-/subtype replacement missing yet)
					finalizeStereotypeVersion(moduleWithStereotype, stereoOutputFileName, context, eClass, eRef, OperationType.CREATE);
				}
				
				// create variants (abstract replaces of <<create>> nodes and sub type variants)
				ArrayList<ModuleFilenamePair> variantList = new ArrayList<ModuleFilenamePair>();
				ArrayList<ModuleFilenamePair> variantConcretes = null;
				boolean origContainsAbstrCreateChild = false;
				
				if(!disableVariants) {
					ModuleFilenamePair originalPair = new ModuleFilenamePair(outputFileName, module);
					variantList.add(originalPair);
					
					// create variants of abstract children replacements
					variantConcretes = replaceCreatableAbstractChildrenWithConcretes(variantList);
					if(variantConcretes!=null) {
						variantList.addAll(variantConcretes);
					}
					
					// if by now there is more than one entry in the variantList
					// we must assume that the original module uses at least one abstract <<create>> child
					// and therefore must be removed from the variantList
					if(variantList.size()>1) {
						origContainsAbstrCreateChild = true;
					}
					
					// create variants of subtype children replacements
					// if none are found then at least the abstract replacement variants are kept
					variantList = replaceCreateableChildrenWithSubTypes(variantList);
					
					// remove original pair (else mainUnits will be overwritten)
					// if it uses some abstract <<create>> children and may not stay
					if(origContainsAbstrCreateChild) {
						while(variantList.contains(originalPair)) {
								variantList.remove(originalPair);
						}
					}
				}
				
				if(variantList.isEmpty()) {
					// create initialChecks if any
					if(createINITIALS) {
						createInitialChecksForMultiplicities(module.getName(),context,eClass,eRef,OperationType.CREATE);
					}

					LogUtil.log(LogEvent.NOTICE, "Generating CREATE : " + module.getName());
					
					// create mainUnit
					mainUnitCreation(module, eClass, OperationType.CREATE);
					
					// serialize
					serialize(module, outputFileName);
					
					// if wished: create inverse
					if(createDELETES) {
						// inverse and string replaces
						Module inverseModule = createInverse(module);
						LogUtil.log(LogEvent.NOTICE, "Generating DELETE : " + inverseModule.getName());			
						Common.replaceNewsWithToBeDeleted(inverseModule);
						
						// remove old mainUnit and re-create mainUnit & serialize
				//TODO Test the following: all non-rule-units must be deleted from module.	
						removeAllNonRuleUnits(inverseModule);	
				//		inverseModule.getTransformationUnits().clear();
						mainUnitCreation(inverseModule, eClass, OperationType.DELETE);			
						serialize(inverseModule, outputFileName.replace(CREATE_prefix, DELETE_prefix));
						
						// create initialChecks if any
						if(createINITIALS) {
						createInitialChecksForMultiplicities(inverseModule.getName(),context,eClass,eRef,OperationType.DELETE);
						}
					}

					
				}else{
					for(ModuleFilenamePair pair: variantList) {
						
						Module module4variant = pair.getModule();
						String variantOutputFileName = pair.getOutputFileName();
						
						// create initialChecks if any
						if(createINITIALS) {
							createInitialChecksForMultiplicities(module4variant.getName(),context,eClass,eRef,OperationType.CREATE);
						}
						
						// create mainUnit & serialize
						mainUnitCreation(module4variant, eClass, OperationType.CREATE);
						serialize(module4variant, variantOutputFileName);
						
						// if wished: create inverse
						if(createDELETES) {
							// inverse and string replaces
							Module inverseModule = createInverse(module4variant);
							LogUtil.log(LogEvent.NOTICE, "Generating DELETE : " + inverseModule.getName());			
							Common.replaceNewsWithToBeDeleted(inverseModule);
							
							// remove old mainUnit and re-create mainUnit & serialize
					//TODO Test the following: all non-rule-units must be deleted from module
							removeAllNonRuleUnits(inverseModule);
					//		inverseModule.getTransformationUnits().clear();
							mainUnitCreation(inverseModule, eClass, OperationType.DELETE);			
							serialize(inverseModule, variantOutputFileName.replace(CREATE_prefix, DELETE_prefix));
							
							// create initialChecks if any
							if(createINITIALS) {
								createInitialChecksForMultiplicities(inverseModule.getName(),context,eClass,eRef,OperationType.DELETE);
							}
						}
					}					
				}
			}


		}
	}

	@Override
	public void generate_Update_Module(EClass eClass) {
		
		if (!(isAllowed(eClass,true) || isOnlyImplicitlyRequiredForFeatureInheritance(eClass)))  return;
		
		
		HashMap<Module,String> moduleMap = new HashMap<Module,String>();

		if(createSETS) {
			
			// EAttributes which shall be considered
			List<EAttribute> easToConsider = null;
			if(reduceToSuperType_SETUNSET) {
				easToConsider = eClass.getEAttributes();
			}else{
				easToConsider = eClass.getEAllAttributes();
			}
						
			for(EAttribute ea: easToConsider) {
				// don't consider derived, not changeable, unsettable and transient references
				if(!ea.isDerived() && !ea.isTransient() && ea.isChangeable()) {
					
					// SET for EAttributes ***************************************************************************/
					LogUtil.log(LogEvent.NOTICE, "Generating SET : " + eClass.getName() + " attribute "+ ea.getName());
					
					String name = SET_prefix + eClass.getName() +"_"+Common.firstLetterToUpperCase(ea.getName());
					
					String outputFileName =  outputFolderPath + name+ EXECUTE_suffix;
					Module SET_Module = henshinFactory.createModule();
					
					SET_Module.setName(name);
					SET_Module.setDescription("Sets "+eClass.getName()+" "+Common.firstLetterToUpperCase(ea.getName()));
					
					// Add imports for meta model
					SET_Module.getImports().addAll(ePackages);
	
					// create rule
					Rule rule = henshinFactory.createRule();
					rule.setActivated(true);
					rule.setName("set"+eClass.getName()+Common.firstLetterToUpperCase(ea.getName()));
					rule.setDescription("Sets the EAttribute "+ea.getName());
					SET_Module.getUnits().add(rule);
	
					// create preserved node for eClass
					NodePair selectedNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, "Selected", eClass);
					Node rhsNode = selectedNodePair.getRhsNode();
	
					// create attribute
					HenshinRuleAnalysisUtilEx.createCreateAttribute(rhsNode, ea, Common.firstLetterToUpperCase(ea.getName()));
	
					// create mainUnits & put TS in map for later serializing
					mainUnitCreation(SET_Module, eClass, OperationType.SET);
					moduleMap.put(SET_Module, outputFileName);	
					
					// create UNSET if wished
					if(createUNSETS && ea.isUnsettable()) {
						// UNSET for EAttributes *************************************************************************/
						LogUtil.log(LogEvent.NOTICE, "Generating UNSET : " + eClass.getName() + " attribute "+ ea.getName());

						// create UNSET from copy of SET and set DefaultValue for the <<create>> parameter
						Module UNSET_Module = EcoreUtil.copy(SET_Module);
						String outputFileNameUNSET = outputFileName.replace(SET_prefix, UNSET_prefix);
						Node unsetRHSNode = HenshinRuleAnalysisUtilEx.getRulesUnderModule(UNSET_Module).get(0).getRhs().getNodes().get(0);

						// get the attribute's default value and set it
						Object defaultValue = ea.getDefaultValue();
						String strDefaultValue = null;
						if(defaultValue!=null) {
							strDefaultValue = defaultValue.toString();
						}else{
							// No need for UNSET, since there is no DefaultValue to which we could reset
							continue;
						}
						
						Attribute changedAttribute = HenshinRuleAnalysisUtilEx.getAttributeByType(unsetRHSNode.getAttributes(), ea);
						unsetRHSNode.getAttributes().remove(changedAttribute);
						changedAttribute.setValue(strDefaultValue);
						unsetRHSNode.getAttributes().add(changedAttribute);

						// rename everything from SET to UNSET
						UNSET_Module.setName(UNSET_Module.getName().replace(SET_prefix, UNSET_prefix));
						UNSET_Module.setDescription(UNSET_Module.getDescription().replace("Sets", "Unsets"));
						Rule unsetRule = HenshinRuleAnalysisUtilEx.getRulesUnderModule(UNSET_Module).get(0);
						unsetRule.setName(unsetRule.getName().replace("set", "unset"));
						unsetRule.setDescription(unsetRule.getDescription().replace("Sets", "Sets"));
						
						// create mainUnits & put Module in map for later serializing
				//TODO Test the following: all non-rule-units must be deleted from module
						removeAllNonRuleUnits(UNSET_Module);
				//		UNSET_Module.getTransformationUnits().clear(); //remove copied mainUnit form SET_TS
						HenshinRuleAnalysisUtilEx.getRulesUnderModule(UNSET_Module).get(0).getParameters().clear(); //remove parameters that came from inverse
						mainUnitCreation(UNSET_Module, eClass, OperationType.UNSET);
						moduleMap.put(UNSET_Module, outputFileNameUNSET);
					}			
				}
			}
	
		}

		// EReferences and their EOpposites, if any		
		for(EReference eRef: eClass.getEAllReferences()) {

			// don't consider derived, not changeable, unsettable and transient references
			if(!eRef.isDerived() && eRef.isChangeable() && !eRef.isTransient()) {

				// eRef == no containment reference  *************************************************************/
				if(!eRef.isContainment()) {
					EReference eOpposite = eRef.getEOpposite();

					// and skip eRefs where it's EOpposite is a containment
					if((eOpposite!=null && !eOpposite.isContainment()) || eOpposite==null) {

						EClass targetType = (EClass)eRef.getEType();

						if (!isAllowed(targetType,false))  continue;

						// create trafo(s) to modify the reference
						moduleMap.putAll(create_Trafo_toModifyReference(eRef, eClass, targetType));
					}
				}
			}
		}

		// serialize
		for(Entry<Module,String> entry: moduleMap.entrySet()) {
			serialize(entry.getKey(), entry.getValue());
		}
	}


	private void removeAllNonRuleUnits(Module module) {
		Iterator<Unit> itUnit = module.getUnits().iterator();
		while(itUnit.hasNext()) {
			Unit unit = itUnit.next();
			if(!(unit instanceof Rule)) {
				itUnit.remove(); //removes the current unit (not the iterator)
			}
		}		
	}



	@Override
	public void generate_MOVE_Module(EClass eClass) {
		
		if (!isAllowed(eClass,true) || createMOVES==false)  return;
		
		HashMap<Module,String> moduleMap = new HashMap<Module,String>();
		
		// get all possible contexts and the according references
		EClassInfo eClassInfo = eClassInfoManagement.getEClassInfo(eClass);
		HashMap<EReference,List<EClass>> combinedContextMap = eClassInfo.getMandatoryParentContext();
		combinedContextMap.putAll(eClassInfo.getOptionalParentContext());
				
		for(EReference eRef: combinedContextMap.keySet()) {

			assert(eRef.isContainment()) : "eRef is no containment but should be";
			
			// don't consider containment references where multiplicity is fixed
			// in such cases a SWAP (complex) operation is necessary
			if(!(eRef.getLowerBound()==eRef.getUpperBound())) {
			
				// don't consider derived, not changeable, unsettable and transient references
				if(!eRef.isDerived() && eRef.isChangeable() && !eRef.isUnsettable() && !eRef.isTransient()) {
					
					EClass parent = (EClass) eRef.eContainer();
					
					if (!isAllowed(parent,false))  continue;				
					
					moduleMap.putAll(create_Trafo_toModifyReference(eRef, eClass, parent));				
				}
			}
		}
		
		// serialize
		for(Entry<Module,String> entry: moduleMap.entrySet()) {	
			serialize(entry.getKey(), entry.getValue());
		}
	}

	public void serialize(Module module, String outputFileName) {
		
		// assertions / checks
		checkModuleFileNameEquality(module, outputFileName);		
		checkMainUnitIsUnique(module);
			
		ResourceSet resourceSet = new ResourceSetImpl();
		URI fileUri = URI.createFileURI(outputFileName);
		Resource resource = resourceSet.createResource(fileUri);
		resource.getContents().add(module);

		// create option map for saving
		Map<String,Boolean> options = new HashMap<String, Boolean>();
		options.put (XMIResource.OPTION_SCHEMA_LOCATION, true);

		// convert every local ePackage URL into ePackage nsURI
		for(EPackage ePackage : ePackages){
			Resource eResource = ePackage.eResource();
			URI uri = URI.createURI(ePackage.getNsURI());
			ePackage.getNsPrefix();
			if(!URIConverter.URI_MAP.containsValue(uri)){
				URIConverter.URI_MAP.put(eResource.getURI(), uri);
			}
			eResource.setURI(URIConverter.INSTANCE.normalize(uri));    
		}

		try {
			resource.save(options);
			
		} catch (IOException e) {

			e.printStackTrace();
		}
	}


	private void checkModuleFileNameEquality(Module module,
			String outputFileName) {
		
		//name equality assertion
		String name = outputFileName.replace(EXECUTE_suffix, "");
		name = name.replace(INITIALCHECK_suffix, "");
		String[] splitName = null;
		String separator = System.getProperty("file.separator");
		
		if(separator.equals("\\")) { //if Windows, prepend backslash to escape
			splitName = name.split("\\"+ System.getProperty("file.separator"));
		}else{
			splitName = name.split(System.getProperty("file.separator"));
		}
		name = splitName[splitName.length-1];	
		assert(module.getName().equals(name)) : "Output file name and Module name are not equal.";
		
	}



	private void checkMainUnitIsUnique(Module module) {
			
		//Exactly one mainUnit assertion
		int mainUnitCount = 0;
		for (Unit unit : module.getUnits()) {
			if(!(unit instanceof Rule)) { // if it's a unit (seq, priority, etc.)
				if (unit.getName().equals(INamingConventions.MAINUNIT)){
					mainUnitCount++;
				}
				for (Unit subUnit : unit.getSubUnits(true)) {
					if(!(subUnit instanceof Rule)) { // if it's a unit (seq, priority, etc.)
						if (subUnit.getName().equals(INamingConventions.MAINUNIT)){
							mainUnitCount++;
						}
						
					}
				}
			}
		}
		assert(mainUnitCount == 1) : "Multiple or no main units in Module " + module.getName()+". Should be exactly one";		
	}



	/** Some private methods **********************************************************/
	
	private void mainUnitCreation(Module module, EClass eClass, OperationType tsType) {
		
	//TODO Test the following: all non-rule-units must be deleted from module.	
		removeAllNonRuleUnits(module);
	//	module.getTransformationUnits().clear();	
		
		/** Unit creation **/ 
		
		PriorityUnit prioUnit = henshinFactory.createPriorityUnit();
		prioUnit.setActivated(true);
		prioUnit.setName("mainUnit");
		
		String description = "";
		if(tsType==OperationType.CREATE) {
			description = "Creates one "+eClass.getName()+ " depending on available contexts in model instance";
						
			// Create the required "new"-Parameter if not already contained in the unit
			Parameter newEClassParam = henshinFactory.createParameter("New");
			if(!prioUnit.getParameters().contains(newEClassParam)) {
				prioUnit.getParameters().add(newEClassParam);
			}
			prioUnit.setDescription(description);
			
		}
		else if(tsType==OperationType.DELETE) {
			description = "Deletes one "+eClass.getName()+ " depending on available contexts in model instance";
			

			//add only ChildX/ExistingX Parameters - everything else is not
			//necessary for <<delete>>
			List<Parameter> unnecessaryParameters = new ArrayList<Parameter>();

			for(Rule r: HenshinRuleAnalysisUtilEx.getRulesUnderModule(module)) {
				for(Parameter p: r.getParameters()) {
					
					if(p.getName().startsWith("Child")
							|| p.getName().startsWith("Existing")) {
						
						boolean alreadyContained = false;
						for(Parameter pInInverseUnit: prioUnit.getParameters()) {
							if(pInInverseUnit.equals(p.getName())) {
								alreadyContained = true;
								break;
							}
						}
						
						if(!alreadyContained) {
							Parameter newEClassParam = henshinFactory.createParameter(p.getName());
							prioUnit.getParameters().add(newEClassParam);
						}												
					}else{
						unnecessaryParameters.add(p);
					}
				}
			}			
			
			//remove unnecessary parameters
			HenshinRuleAnalysisUtilEx.getRulesUnderModule(module).get(0).getParameters().removeAll(unnecessaryParameters);
			
			// Create the required "toBeDeleted"-Parameter under the unit if there is a context to delete EClass from
			// else selectedEObject-Parameter will directly map to this
			if(HenshinRuleAnalysisUtilEx.getNodeByName(HenshinRuleAnalysisUtilEx.getRulesUnderModule(module).get(0),"Selected",true)!=null) {
				Parameter newEClassParam = henshinFactory.createParameter("toBeDeleted");
				if(!prioUnit.getParameters().contains(newEClassParam)) {
					prioUnit.getParameters().add(newEClassParam);
				}
			}
			prioUnit.setDescription(description);
		}
		
		
		// Create the mandatory "selectedEObject"-Parameter
		Parameter selectedEObject = henshinFactory.createParameter("selectedEObject");
		prioUnit.getParameters().add(selectedEObject);

		
		
		/** Parameter and Mapping creation **/
		for(Rule rule: HenshinRuleAnalysisUtilEx.getRulesUnderModule(module)) {
			
			
			 //for <<create>> we only need RHS
			for(Node nInRHS : rule.getRhs().getNodes()) {
				String name = nInRHS.getName();
				// Add Parameter for Nodes
				if(name!=null && !name.equals("")) {
					Parameter pForRule = henshinFactory.createParameter(name);
					if(HenshinRuleAnalysisUtilEx.getParameterByName(rule, name)==null) {
						pForRule.setUnit(rule);
						rule.getParameters().add(pForRule);
						if(!pForRule.getName().equals("Selected") && HenshinRuleAnalysisUtilEx.getParameterByName(prioUnit, pForRule.getName())==null) {
							Parameter pForUnit = henshinFactory.createParameter(name);
							prioUnit.getParameters().add(pForUnit);
						}
					}
				}
				
				// Add Parameter for Attributes
				for(Attribute a: nInRHS.getAttributes()) {
					Object defaultValue = a.getType().getDefaultValue();
					String defaultValueName = null;
					if(defaultValue!=null) {
						defaultValueName = defaultValue.toString();
					}
					if(a.getValue()!="null" && ((defaultValueName!=null && !a.getValue().equals(defaultValueName)) || defaultValueName==null)) {
						Parameter pForRule = henshinFactory.createParameter(a.getValue());
						Parameter pForUnit = henshinFactory.createParameter(a.getValue());
						if(HenshinRuleAnalysisUtilEx.getParameterByName(rule, pForRule.getName())==null) {
							// ..to rule
							rule.getParameters().add(pForRule);
							pForRule.setUnit(rule);
							// ..to unit
							if(HenshinRuleAnalysisUtilEx.getParameterByName(prioUnit, pForUnit.getName())==null) {
								prioUnit.getParameters().add(pForUnit);
								pForUnit.setUnit(prioUnit);
							}
						}
					}
				}
			}
		
			
			 //for <<delete>> we also need LHS
			for(Node nInLHS : rule.getLhs().getNodes()) {
				String name = nInLHS.getName();
				// Add Parameter for Nodes (if there is a name and we have a context)
				// otherwise no Parameter will be created in Unit and the selectedEObject will map to this
				if(name!=null && !name.equals("")) {
					Parameter p = henshinFactory.createParameter(name);
					if(HenshinRuleAnalysisUtilEx.getParameterByName(rule, name)==null) {
						p.setUnit(rule);
						rule.getParameters().add(p);
						if(!p.getName().equals("Selected")
								&& HenshinRuleAnalysisUtilEx.getParameterByName(prioUnit, p.getName())==null
								&& HenshinRuleAnalysisUtilEx.getParameterByName(rule, "Selected")!=null) {
							prioUnit.getParameters().add(p);
						}
					}
				}

				
				// Add Parameter for Attributes
				for(Attribute a: nInLHS.getAttributes()) {
					if(a.getValue()!="null") {
						Parameter pForRule = henshinFactory.createParameter(a.getValue());
						Parameter pForUnit = henshinFactory.createParameter(a.getValue()); 
						if(HenshinRuleAnalysisUtilEx.getParameterByName(rule, pForRule.getName())==null) {
							// ..to rule
							rule.getParameters().add(pForRule);
							pForRule.setUnit(rule);
							// ..to unit
							if(HenshinRuleAnalysisUtilEx.getParameterByName(prioUnit, pForUnit.getName())==null) {
								prioUnit.getParameters().add(pForUnit);
								pForUnit.setUnit(prioUnit);
							}
						}
					}
				}
			}
			
			
			// Create Mappings
			for(Parameter p :rule.getParameters()) {
				// == selectedEObject
				assert(p.getName()!=null): rule.getName();
				if(p.getName().equals("Selected")) {
					ParameterMapping selEObjectMapping = henshinFactory.createParameterMapping();
					selEObjectMapping.setSource(selectedEObject);
					selEObjectMapping.setTarget(p);			
					prioUnit.getParameterMappings().add(selEObjectMapping);
				}
				// == selected element is the toBeDeleted (in case there is no context to delete from)
				else if(HenshinRuleAnalysisUtilEx.getParameterByName(rule, "Selected")==null && p.getName().equals("toBeDeleted")) {
					ParameterMapping selEObjectMapping = henshinFactory.createParameterMapping();
					selEObjectMapping.setSource(selectedEObject);
					selEObjectMapping.setTarget(p);			
					prioUnit.getParameterMappings().add(selEObjectMapping);
				}
				// == new / out-parameter
				else if(p.getName().matches("New[0-9]*")) {
					ParameterMapping pm = henshinFactory.createParameterMapping();
					pm.setSource(p);
					pm.setTarget(HenshinRuleAnalysisUtilEx.getParameterByName(prioUnit, p.getName()));
					if(!prioUnit.getParameterMappings().contains(pm)) {
						prioUnit.getParameterMappings().add(pm);
					}
				}else if(p.getName().matches("NewTarget[0-9]*")|| p.getName().matches("NewSource[0-9]*")) {
					ParameterMapping pm = henshinFactory.createParameterMapping();
					pm.setTarget(p);
					pm.setSource(HenshinRuleAnalysisUtilEx.getParameterByName(prioUnit, p.getName()));
					if(!prioUnit.getParameterMappings().contains(pm)) {
						prioUnit.getParameterMappings().add(pm);
					}
					
				// == every other in-parameter
				}else{
					ParameterMapping pm = henshinFactory.createParameterMapping();
					pm.setSource(HenshinRuleAnalysisUtilEx.getParameterByName(prioUnit, p.getName()));
					pm.setTarget(p);
					if(!prioUnit.getParameterMappings().contains(pm)) {
						prioUnit.getParameterMappings().add(pm);
					}		
				}
			}
			
			
			// Add rule to unit
			prioUnit.getSubUnits().add(rule);	
						
		}
		
		// Add unit to module
		module.getUnits().add(prioUnit);
	}
	
	

	/**
	 * This recursive method creates mandatory children for a given EClass.
	 * It will create mandatory children and mandatory neighbours of the child
	 * if necessary.
	 * @param rule
	 * @param eClass
	 * @param eClassNode
	 */
	private void createMandatoryChildren(Rule rule, EClassInfo eClassInfo, Node eClassNode) {
			
		for(Entry<EReference,List<EClass>> childEntry: eClassInfo.getMandatoryChildren().entrySet()) {
			List<EClass> children = childEntry.getValue();
			EReference eRef = childEntry.getKey();
			
			for(EClass child: children) {

				if (!isAllowed(child,false))  continue;
				
				for(int i=0; i<eRef.getLowerBound();i++) {

					Node newChildNode = null;
					String name = getFreeNodeName("Child", rule);
					// create node for mandatory child
					newChildNode = HenshinRuleAnalysisUtilEx.createCreateNode(rule.getRhs(), name, child);				
					// create edge for mandatory child
					HenshinRuleAnalysisUtilEx.createCreateEdge(eClassNode, newChildNode, eRef);
					// Add necessary attributes to the new eClass node
					createAttributes(child, newChildNode, rule);					
					// recursively check for child's mandatories and create them
					if(eClassInfoManagement.getEClassInfo(child).hasMandatories()) {
						createMandatoryChildren(rule, eClassInfoManagement.getEClassInfo(child), newChildNode);
						createMandatoryNeighbours(rule, eClassInfoManagement.getEClassInfo(child), newChildNode);
					}

				}
			}
		}
	}		

	/**
	 * This recursive method creates mandatory neighbours for a given EClass.
	 * It will create mandatory children and mandatory neighbours of the neighbour
	 * if necessary.
	 * @param rule
	 * @param eClass
	 * @param eClassNode
	 */
	private void createMandatoryNeighbours(Rule rule, EClassInfo eClassInfo, Node eClassNode) {

		for(Entry<EReference,List<EClass>> neighbourEntry: eClassInfo.getMandatoryNeighbours().entrySet()) {
			EReference eRef = neighbourEntry.getKey();
			EReference eOpposite = eRef.getEOpposite();
			List<EClass> neighbours = neighbourEntry.getValue();
			
			for(EClass neighbour: neighbours) {

				if (!isAllowed(neighbour,false))  continue;
				
				// check if neighbours have already been created to its maximum lowerBound and skip if so
				// else allow creation of neigbour
				boolean alreadyCreatedMaxNeighbourNode = false;
				int nodesWithSameERef = 0;
				for(Node n:rule.getRhs().getNodes()) {
					if(n.getType().equals(neighbour)) {
						for(Edge e:n.getIncoming()) {
							if(e.getType().getName().equals(eRef.getName()) && (eRef.getLowerBound()>nodesWithSameERef)) {
								nodesWithSameERef++;
								if((eRef.getLowerBound()<=nodesWithSameERef)) {
									alreadyCreatedMaxNeighbourNode = true;
									break;
								}
								break;
							}			
						}
						if(alreadyCreatedMaxNeighbourNode){
							break;
						}
						
					}
				}
				

				for(int i=0; i<eRef.getLowerBound();i++) {
					// create node for mandatory neighbour
					// but only if it wasn't created to its lowerBound maximum before.
					// This ensures we can have a neighbour circle support.
					if(!alreadyCreatedMaxNeighbourNode) {

						Node newNeighbourNode = null;

						// create node for mandatory neighbour
						String existingName = getFreeNodeName("Existing",rule);
						NodePair preservedNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, existingName, neighbour);
						newNeighbourNode = preservedNodePair.getRhsNode();		
						// create edge for mandatory neighbour
						HenshinRuleAnalysisUtilEx.createPreservedEdge(rule, eClassNode, newNeighbourNode, eRef);						
						// create edge for eOpposite, if any
						if(eOpposite!=null) {
							HenshinRuleAnalysisUtilEx.createPreservedEdge(rule,newNeighbourNode, eClassNode, eOpposite);
						}
						// recursively check neighbour's mandatories and create them
						createMandatoryChildren(rule, eClassInfoManagement.getEClassInfo(neighbour), newNeighbourNode);
						createMandatoryNeighbours(rule, eClassInfoManagement.getEClassInfo(neighbour), newNeighbourNode);

					}
				}
			}
		}
	}
	
	
	private void createInitialChecksForMultiplicities(String moduleName, EClass parentOrNeighbor, EClass eClass, EReference eRef, OperationType operationType) {
		
		// Return on CHANGE, since there are no initials needed
		if(operationType==OperationType.CHANGE) { return; }
		
		boolean initialCreated = false;
		
		// Create file name and Module
		String outputFileName = outputFolderPath + moduleName + INITIALCHECK_suffix;		
		Module module = henshinFactory.createModule();
		module.setName(moduleName);

		// Add imports for meta model
		for(EPackage epackage: ePackages) {
			module.getImports().add(epackage);
		}
		
		// x..* : minimum
		if(eRef.getLowerBound()!=0 && eRef.getUpperBound()==-1 && (operationType==OperationType.DELETE || operationType==OperationType.REMOVE || operationType==OperationType.MOVE)) {
			
			LogUtil.log(LogEvent.NOTICE, "Generating INITIAL: " + moduleName);
			
			// create rule
			Rule rule = HenshinRuleAnalysisUtilEx.createRule("doNotFallBelowLowerBound", "doNotFallBelowLowerBound", true, module);
			
			switch(operationType) {
				case DELETE: case REMOVE:
				{
					// create Node(-Pair) for <<preserved>> parentOrNeighbour
					NodePair parentOrNeighbourNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, "Selected", parentOrNeighbor);
					Node parentOrNeighbourNode = parentOrNeighbourNodePair.getRhsNode();
					
					for(int i=0; i<eRef.getLowerBound(); i++) {
						// create <<preserved>> node, that must exist at the minimum
						NodePair np = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, "", eClass);
						HenshinRuleAnalysisUtilEx.createPreservedEdge(rule, parentOrNeighbourNodePair, np, eRef);	
					}
					
					// create <<forbid>> nodes: these node MAY NOT ONLY exist when doing a delete/remove from this parentOrNeighborNode
					Node n = HenshinRuleAnalysisUtilEx.createForbidNode(rule, eClass);
					HenshinRuleAnalysisUtilEx.createForbidEdge(parentOrNeighbourNode, n, eRef, rule);	
					
					// finally add rule
					module.getUnits().add(rule);
					initialCreated = true;
				};break;
				case MOVE:
				{
					// create Node(-Pair) for <<preserved>> parentOrNeighbour
					NodePair parentOrNeighbourNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, "OldSource", parentOrNeighbor);
					Node parentOrNeighbourNode = parentOrNeighbourNodePair.getRhsNode();
					
					for(int i=0; i<eRef.getLowerBound(); i++) {
						// create <<preserved>> node, that must exist at the minimum
						NodePair np = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, "", eClass);
						HenshinRuleAnalysisUtilEx.createPreservedEdge(rule, parentOrNeighbourNodePair, np, eRef);	
					}
					
					// create <<forbid>> nodes: these node MAY NOT ONLY exist when doing a delete/remove from this parentOrNeighborNode
					Node n = HenshinRuleAnalysisUtilEx.createForbidNode(rule, eClass);
					HenshinRuleAnalysisUtilEx.createForbidEdge(parentOrNeighbourNode, n, eRef, rule);	
					
					// finally add rule
					module.getUnits().add(rule);
					initialCreated = true;
				};break;					
			}
		}
		// 0..y : maximum
		else if(eRef.getLowerBound()==0 && eRef.getUpperBound()!=-1 && (operationType==OperationType.CREATE || operationType==OperationType.ADD || operationType==OperationType.MOVE)) {
			
			LogUtil.log(LogEvent.NOTICE, "Generating INITIAL: " + moduleName);
			
			// create rule
			Rule rule = HenshinRuleAnalysisUtilEx.createRule("doNotExceedUpperBound", "doNotExceedUpperBound", true, module);
			
			switch(operationType) {
				case CREATE: case ADD:
				{
					// create NodePair for <<preserved>> parentOrNeighbour
					NodePair parentOrNeighbourNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, "Selected", parentOrNeighbor);
					
					// create <<preserved>> nodes: these nodes MAY NOT ALREADY exist when doing a create/add to this parentOrNeighborNode
					for(int i=0; i<eRef.getUpperBound(); i++) {
						NodePair np = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, "", eClass);
						HenshinRuleAnalysisUtilEx.createPreservedEdge(rule, parentOrNeighbourNodePair, np, eRef);	
					}
					// finally add rule
					module.getUnits().add(rule);
					initialCreated = true;
				};break;
				case MOVE:
				{
					// create Node(-Pair) for <<preserved>> parentOrNeighbour
					NodePair parentOrNeighbourNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, "NewSource", parentOrNeighbor);
					
					// create <<forbid>> nodes: these nodes MAY NOT ALREADY exist when doing a move to this parentOrNeighborNode
					for(int i=0; i<eRef.getUpperBound(); i++) {
						NodePair np = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, "", eClass);
						HenshinRuleAnalysisUtilEx.createPreservedEdge(rule, parentOrNeighbourNodePair, np, eRef);	
					}
					// finally add rule
					module.getUnits().add(rule);
					initialCreated = true;
				};break;
			}
		}
		// x..y : minimum and maximum
		else if(eRef.getLowerBound()!=0 && eRef.getUpperBound()!=-1) {
			
			LogUtil.log(LogEvent.NOTICE, "Generating INITIAL: " + moduleName);
			
			// create rule
			Rule rule = HenshinRuleAnalysisUtilEx.createRule("doNotIgnoreBounds", "doNotIgnoreBounds", true, module);

			switch(operationType) {
				case DELETE: case REMOVE:
				{
					// create Node(-Pair) for <<preserved>> parentOrNeighbour
					NodePair parentOrNeighbourNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, "Selected", parentOrNeighbor);
					Node parentOrNeighbourNode = parentOrNeighbourNodePair.getRhsNode();
	
					for(int i=0; i<eRef.getLowerBound(); i++) {
						// create <<preserved>> node, that must exist at the minimum
						NodePair np = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, "", eClass);
						HenshinRuleAnalysisUtilEx.createPreservedEdge(rule, parentOrNeighbourNodePair, np, eRef);	
					}
					
					// create <<forbid>> nodes: these node MAY NOT ONLY exist when doing a delete/remove from this parentOrNeighborNode
					Node n = HenshinRuleAnalysisUtilEx.createForbidNode(rule, eClass);
					HenshinRuleAnalysisUtilEx.createForbidEdge(parentOrNeighbourNode, n, eRef, rule);
					
					// finally add rule
					module.getUnits().add(rule);
					initialCreated = true;
				};break;
				case CREATE: case ADD:
				{
					// create NodePair for <<preserved>> parentOrNeighbour
					NodePair parentOrNeighbourNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, "Selected", parentOrNeighbor);
					
					// create <<preserved>> nodes: these nodes MAY NOT ALREADY exist when doing a create/add to this parentOrNeighborNode
					for(int i=0; i<eRef.getUpperBound(); i++) {
						NodePair np = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, "", eClass);
						HenshinRuleAnalysisUtilEx.createPreservedEdge(rule, parentOrNeighbourNodePair, np, eRef);	
					}
					// finally add rule
					module.getUnits().add(rule);
					initialCreated = true;
				};break;
				case MOVE:
				{
					// create precondition for lowerBound --------------------------------------------------------------------------/
					// create Node(-Pair) for <<preserved>> parentOrNeighbour for OldSource
					NodePair parentOrNeighbourNodePair_OLD = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, "OldSource", parentOrNeighbor);
	
					for(int i=0; i<eRef.getLowerBound(); i++) {
						// create <<preserved>> node, that must exist at the minimum
						NodePair np = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, "", eClass);
						HenshinRuleAnalysisUtilEx.createPreservedEdge(rule, parentOrNeighbourNodePair_OLD, np, eRef);	
					}
					
					// create <<forbid>> nodes: these node MAY NOT ONLY exist when doing a delete/remove from this parentOrNeighborNode
					Node n = HenshinRuleAnalysisUtilEx.createForbidNode(rule, eClass);
					HenshinRuleAnalysisUtilEx.createForbidEdge(parentOrNeighbourNodePair_OLD.getRhsNode(), n, eRef, rule);	
					
					// create precondition for upperBound --------------------------------------------------------------------------/
					// create Node(-Pair) for <<preserved>> parentOrNeighbour for NewSource
					NodePair parentOrNeighbourNodePair_NEW = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, "NewSource", parentOrNeighbor);
					
					// create <<forbid>> nodes: these nodes MAY NOT ALREADY exist when doing a move to this parentOrNeighborNode
					for(int i=0; i<eRef.getUpperBound(); i++) {
						NodePair np = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, "", eClass);
						HenshinRuleAnalysisUtilEx.createPreservedEdge(rule, parentOrNeighbourNodePair_NEW, np, eRef);	
					}
			
					// finally add rule
					module.getUnits().add(rule);
					initialCreated = true;
				};break;
			}
		}

		// now create mainUnit and serialize if initialCreated == true
		if(initialCreated) {
			mainUnitCreation(module, eClass, null); //null -> initial
			serialize(module, outputFileName);
		}else{
			return;
		}
	}
	
	
	private Rule createSimpleCreateRule(EClass context, EClass eClass, Module module, EReference eRef, Integer number) {
		
		String contextName = "";
		if(context!=null) {
			contextName = Common.firstLetterToUpperCase(context.getName());
		}else{
			contextName = "Model";			
		}
				
		
		// Add new rule to Module
		Rule simpleCreateRule = HenshinRuleAnalysisUtilEx.createRule(
				"create"+eClass.getName()+"In"+ contextName,
				"creates one "+eClass.getName()+" in the context: "+ contextName,
				true,
				module);

		
		// create <<preserve>> nodes for context, if any
		String selectedName = getFreeNodeName("Selected",simpleCreateRule);
		Graph rhs = null;
		if(context!=null) {	
			NodePair nodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(
					simpleCreateRule,
					selectedName,
					context);
			rhs = nodePair.getRhsNode().getGraph();
		}
		else{
			rhs = simpleCreateRule.getRhs();
		}
		
		
		for(int i=0; i<number;i++) {
			// Add new eClass to RHS
			String newName = getFreeNodeName("New",simpleCreateRule);
			Node newNode = HenshinRuleAnalysisUtilEx.createCreateNode(rhs, newName, eClass);	
	
			// Add necessary attributes to the new eClass node
			createAttributes(eClass, newNode, simpleCreateRule);
	
			// Add edge between context and new eClass, if any
			if(context!=null && eRef!=null) {
				Node contextNode = null;
				for(Node n: rhs.getNodes()) {
					String nName = n.getName();
					if(nName!=null && nName.equals(selectedName)) {
						contextNode = n;
					}
				}
				HenshinRuleAnalysisUtilEx.createCreateEdge(contextNode, newNode, eRef);
			}
		}
		
		return simpleCreateRule;
	}
	
	private Module createInverse(Module module) {
		
		String name 		= "";
		String description	= "";
		Module inverse = null;
		
		if(module.getName().startsWith(ADD_prefix)) {
			name = module.getName().replaceFirst(ADD_prefix, REMOVE_prefix);
			description = module.getDescription().replaceFirst("Adds to","Removes");
			inverse =  HenshinRuleAnalysisUtilEx.createInverse(name,description,module);
			Rule firstRule = HenshinRuleAnalysisUtilEx.getRulesUnderModule(inverse).get(0);
			firstRule.setName(firstRule.getName().replaceFirst("addTo", "removeFrom"));
			firstRule.setDescription(firstRule.getDescription().replaceFirst("Adds to", "Removes from"));
			HenshinRuleAnalysisUtilEx.getNodeByName(firstRule,"NewTarget",true).setName("OldTarget");  //rename Node in LHS
			HenshinRuleAnalysisUtilEx.getNodeByName(firstRule,"NewTarget",false).setName("OldTarget"); //rename Node in RHS
		}
		else if(module.getName().startsWith(SET_prefix)) {
			name = module.getName().replaceFirst(SET_prefix, UNSET_prefix);
			description = module.getDescription().replaceFirst("Sets","Unsets");
			inverse =  HenshinRuleAnalysisUtilEx.createInverse(name,description,module);
			Rule firstRule = HenshinRuleAnalysisUtilEx.getRulesUnderModule(inverse).get(0);
			firstRule.setName(firstRule.getName().replaceFirst("set", "unset"));
			firstRule.setDescription(firstRule.getDescription().replaceFirst("Set", "Unset"));
			HenshinRuleAnalysisUtilEx.getNodeByName(firstRule,"NewTarget",true).setName("OldTarget");  //rename Node in LHS
			HenshinRuleAnalysisUtilEx.getNodeByName(firstRule,"NewTarget",false).setName("OldTarget"); //rename Node in RHS
		}
		else if(module.getName().startsWith(CREATE_prefix)) {
			name = module.getName().replaceFirst(CREATE_prefix, DELETE_prefix);
			description = module.getDescription().replaceFirst("Creates","Deletes");
			inverse =  HenshinRuleAnalysisUtilEx.createInverse(name,description,module);
			Rule firstRule = HenshinRuleAnalysisUtilEx.getRulesUnderModule(inverse).get(0);
			firstRule.setName(firstRule.getName().replaceFirst("create", "delete"));
			firstRule.setDescription(firstRule.getDescription().replaceFirst("create", "delete"));
			//TODO speedup DELETEs: remove parameters + node names for all children and existings
			
		}
		return inverse;
	}

	
	private String getFreeNodeName(String originalName, Rule rule) {

		originalName = Common.firstLetterToUpperCase(originalName);

		List<Node> allNodes = HenshinRuleAnalysisUtilEx.getLHSIntersectRHSNodes(rule);
		allNodes.addAll(HenshinRuleAnalysisUtilEx.getLHSMinusRHSNodes(rule));
		allNodes.addAll(HenshinRuleAnalysisUtilEx.getRHSMinusLHSNodes(rule));
		
		int count = 0;
		for(Node node: allNodes) {
			String nNode = node.getName();
			if(nNode!=null && node.getName().startsWith(originalName)) {
				count++;
			}		
		}
		
		if(count!=0) {
			return originalName+String.valueOf(count);
		}else{
			return originalName;
		}
	}

	private String getFreeAttributeName(String originalName, Rule rule) {

		originalName = Common.firstLetterToUpperCase(originalName);

		List<Attribute> allCreateAttributes = HenshinRuleAnalysisUtilEx.getCreationAttributes(rule);		
		int count = 0;
		for(Attribute attrib: allCreateAttributes) {
			
			if(attrib.getValue().startsWith(originalName)) {
				count++;
			}		
		}
		
		if(count!=0) {
			return originalName+String.valueOf(count);
		}else{
			return originalName;
		}
	}
	
	private void createAttributes(EClass forEClass, Node inEClassNode, Rule rule) {
		
		// Add necessary attributes to the new eClass node
		for(EAttribute ea: forEClass.getEAllAttributes()) {
			//we don't want: derived, transient or unchangeable EAttributes
			if(!ea.isDerived() && !ea.isTransient() && ea.isChangeable()) {
				String eaName = getFreeAttributeName(ea.getName(),rule);
				//only allow required and identifier EAttributes and
				//only in case of createNotRequiredAndNotIDAttributes==true
				//allow also the not required and not-identifier-EAttributes.
				if( (!createNotRequiredAndNotIDAttributes &&  ea.isRequired() &&  ea.isID()) ||
					( createNotRequiredAndNotIDAttributes && !ea.isRequired() && !ea.isID())) {
					HenshinRuleAnalysisUtilEx.createCreateAttribute(
							inEClassNode, ea,Common.firstLetterToUpperCase(getFreeAttributeName(eaName, rule))
							);
				}
			}
		}
		
	}
	
	private ArrayList<ModuleFilenamePair> replaceCreatableAbstractChildrenWithConcretes(ArrayList<ModuleFilenamePair> list) {
	
		ArrayList<ModuleFilenamePair> newList = new ArrayList<ModuleFilenamePair>();
		
		for(ModuleFilenamePair pair : list) {
			
			Module origModule = pair.getModule();
			String origOutputFileName = pair.getOutputFileName();
						
			String sep = System.getProperty("file.separator");
			if(sep.equals("\\")) sep="\\"+sep;
			String[] fileNamePath = origOutputFileName.split(sep);
			String fileName = fileNamePath[fileNamePath.length-1].replace(EXECUTE_suffix, "");
			
			assert(fileName.equals(origModule.getName())): "Output filename and module file name are not equal";
			
			List<Node> originalAbstractNodes = HenshinRuleAnalysisUtilEx.getChildNodesWithinAContainmentRelation(
					origModule, HenshinRuleAnalysisUtilEx.NodeKindSelection.CREATE,true);
			
			if(!originalAbstractNodes.isEmpty()) {
				EClass typeOfReplacable = originalAbstractNodes.get(0).getType();


				ArrayList<EClass> replacements = eClassInfoManagement.getAllConcreteEClassesForAbstract(typeOfReplacable);
				
				for(EClass replacement: replacements) {
					
					if (!isAllowed(replacement,false))  continue;
					
					System.out.println("Replacing: "+replacement.getName()+" for " + typeOfReplacable.getName() +" in originally "+ origModule.getName());					
					
					// create copy
					Module copy = EcoreUtil.copy(origModule);
					String outputFileName = origOutputFileName;
					
					// adjust outputFileName
					long id = System.nanoTime();				
					if(outputFileName.matches(".*(_Variant\\d*\\w*\\.\\w*)$")) {
						outputFileName = outputFileName.replaceAll("(\\d*"+EXECUTE_suffix+")$", id+EXECUTE_suffix);
						copy.setDescription(copy.getDescription().replaceAll("(Variant\\d*)$", "Variant"+String.valueOf(id)));
						copy.setName(copy.getName().replaceAll("(Variant\\d*)$", "Variant"+String.valueOf(id)));						
					}else{
						outputFileName = outputFileName.replace(EXECUTE_suffix, "_Variant"+id+EXECUTE_suffix);
						copy.setDescription(copy.getDescription()+" Variant"+id);
						copy.setName(copy.getName()	+"_Variant"+id);						
					}			
							
					// get nodes of the copy
					List<Node> copyNodes = HenshinRuleAnalysisUtilEx.getChildNodesWithinAContainmentRelation(
							copy, HenshinRuleAnalysisUtilEx.NodeKindSelection.CREATE,true);
					
					// search for the 1st node with same type as typeOfReplacable and replace
					for(Node copyNode :copyNodes) {
						if(copyNode.getType().equals(typeOfReplacable)) {
							copyNode.setType(replacement);
							
							// create mandatories for replacement, if any
							createMandatoryChildren((Rule)copyNode.getGraph().eContainer(),
														eClassInfoManagement.getEClassInfo(replacement),
														copyNode);
							createMandatoryNeighbours((Rule)copyNode.getGraph().eContainer(),
														eClassInfoManagement.getEClassInfo(replacement),
														copyNode);
							
							break; // since we only want 1 replacement per new module
						}
					}
									
					// check if modified copy still contains abstract nodes within containment relation
					if(!HenshinRuleAnalysisUtilEx.getChildNodesWithinAContainmentRelation(
							copy, HenshinRuleAnalysisUtilEx.NodeKindSelection.CREATE,true).isEmpty()) {
								// abstracts in containment relation found --> incomplete ts
								ArrayList<ModuleFilenamePair> propagatedList = new ArrayList<ModuleFilenamePair>();
								ArrayList<ModuleFilenamePair> furtherReplaces = null;
								propagatedList.add(new ModuleFilenamePair(outputFileName, copy));
								furtherReplaces =replaceCreatableAbstractChildrenWithConcretes(propagatedList); 
								if(furtherReplaces!=null) {
									newList.addAll(furtherReplaces);								
								}
					}else{
						// no abstracts after replacement and creation of mandatories --> complete module
						newList.add(new ModuleFilenamePair(outputFileName,copy));
					}
				}
			}
			// no replaceables in original --> complete module
			else{
				//newList.add(pair);
				return null;
			}
		}	
		
		// no abstract node with containment relation in module found (anymore)
		return newList;
	}
	
	
	private HashMap<Module,String> create_Trafo_toModifyReference(EReference eRef, EClass eClass, EClass target) {
		
		HashMap<Module,String> map = new HashMap<Module,String>();
		
		int lower = eRef.getLowerBound();
		int upper = eRef.getUpperBound();
		
		// eRef == no containment reference  *************************************************************/	
		if(!eRef.isContainment()) {

			//	(0..1)	->SET/UNSET ******************************************************************************************/
			if(lower==0 && upper==1 && createSETS) {
				
				// only continue, if ref is inherited and no reduction to super type is wished
				// or ref is not inherited
				if((isInheritedReference(eRef, eClass) && !reduceToSuperType_SETUNSET) || !isInheritedReference(eRef, eClass)) {

					// SET *******************************************************************************************************/
					String name = SET_prefix + eClass.getName() + "_Ref_" + eRef.getName()+ "_tgt_"+target.getName(); 
					LogUtil.log(LogEvent.NOTICE, "Generating SET : " + name);

					// set file name
					String outputFileName = outputFolderPath + name+ EXECUTE_suffix;
					String outputFileNameUnset = outputFileName.replace(SET_prefix, UNSET_prefix);

					Module SET_Module = henshinFactory.createModule();
					SET_Module.setName(name);

					SET_Module.setDescription("Sets "+eClass.getName()+"'s reference "+eRef.getName()+" the target "+target.getName());

					// add imports
					SET_Module.getImports().addAll(ePackages);

					// create rule
					create_Rule_toSetReference(SET_Module, eRef, eClass, target);

					if(createUNSETS) {
						// UNSET *****************************************************************************************************/
						Module UNSET_Module = createInverse(SET_Module);

						// create mainUnit for UNSET and put in map
						mainUnitCreation(UNSET_Module, eClass, OperationType.UNSET);
						map.put(UNSET_Module, outputFileNameUnset);
					}
					// create mainUnit for SET and put in map
					mainUnitCreation(SET_Module, eClass, OperationType.SET);
					map.put(SET_Module, outputFileName);
				}
			}
			//	(..*) or (x..y)	->ADD/REMOVE ********************************************************************************/
			else if((upper==-1 || upper-lower>0) && createADDS) {

				// only continue, if ref is inherited and no reduction to super type is wished
				// or ref is not inherited
				if((isInheritedReference(eRef, eClass) && !reduceToSuperType_ADDREMOVE) || !isInheritedReference(eRef, eClass)) {

					String name = ADD_prefix + eClass.getName() + "_Ref_" + eRef.getName()+ "_tgt_"+target.getName(); 
					LogUtil.log(LogEvent.NOTICE, "Generating ADD : " + name);

					// set file name
					String outputFileName 		= outputFolderPath + name+ EXECUTE_suffix;
					String outputFileNameRemove = outputFileName.replace(ADD_prefix, REMOVE_prefix);

					Module ADD_Module = henshinFactory.createModule();
					ADD_Module.setName(name);

					ADD_Module.setDescription("Adds to "+eClass.getName() +"'s reference "+ eRef.getName()
							+ " the target "+ target.getName());

					// add imports
					ADD_Module.getImports().addAll(ePackages);

					// create rule
					create_Rule_toSetReference(ADD_Module, eRef, eClass, target);

					if(createREMOVES) {
						// REMOVE **************************************************************************************************/				
						Module REMOVE_Module = createInverse(ADD_Module);
						LogUtil.log(LogEvent.NOTICE, "Generating REMOVE : " + REMOVE_Module.getName());

						// create mainUnits and put in map
						mainUnitCreation(REMOVE_Module, eClass, OperationType.REMOVE);
						map.put(REMOVE_Module, outputFileNameRemove);

						// create initialChecks, if any
						if(createINITIALS) {
							createInitialChecksForMultiplicities(REMOVE_Module.getName(), eClass, target, eRef, OperationType.REMOVE);
						}
					}
					// create mainUnits and put in map
					mainUnitCreation(ADD_Module, eClass, OperationType.ADD);
					map.put(ADD_Module, outputFileName);
					// create initialChecks, if any
					if(createINITIALS) {
						createInitialChecksForMultiplicities(ADD_Module.getName(), eClass, target, eRef, OperationType.ADD);
					}
				}
			}
			//	(x..x)	-> CHANGE *******************************************************************************************/
			else if(upper==lower && createCHANGES) {

				// only continue, if ref is inherited and no reduction to super type is wished
				// or ref is not inherited
				if((isInheritedReference(eRef, eClass) && !reduceToSuperType_CHANGE) || !isInheritedReference(eRef, eClass)) {

					String name = CHANGE_prefix + eClass.getName() + "_Ref_" + eRef.getName()+ "_tgt_"+target.getName(); 
					LogUtil.log(LogEvent.NOTICE, "Generating CHANGE : " + name);

					// CHANGE file name
					String outputFileName = outputFolderPath + name+ EXECUTE_suffix;

					Module CHANGE_Module = henshinFactory.createModule();
					CHANGE_Module.setName(name);

					CHANGE_Module.setDescription("CHANGEs "+eClass.getName() +"'s reference "+ eRef.getName() + " the target "+ target.getName());

					// add imports
					CHANGE_Module.getImports().addAll(ePackages);

					// create rule
					create_Rule_toSetReference(CHANGE_Module, eRef, eClass, target);

					// create mainUnit and put in map
					mainUnitCreation(CHANGE_Module, eClass, OperationType.CHANGE);
					map.put(CHANGE_Module, outputFileName);
				}
			}
		}
		// eRef == is containment reference  *************************************************************/	
		else{
			if(createMOVES) {
				String name = MOVE_prefix + eClass.getName() + "_Ref_" + eRef.getName()+ "_To_"+target.getName(); 
				LogUtil.log(LogEvent.NOTICE, "Generating Move : " + name);
	
				// MOVE file name
				String outputFileName = outputFolderPath + name+ EXECUTE_suffix;
	
				Module MOVE_Module = henshinFactory.createModule();
				MOVE_Module.setName(name);
	
				MOVE_Module.setDescription("MOVEs "+eClass.getName() +" with reference "+ eRef.getName() + " to "+ target.getName());
	
				// add imports
				MOVE_Module.getImports().addAll(ePackages);
				
				// create rule
				create_Rule_toSetReference(MOVE_Module, eRef, eClass, target);
	
				// create mainUnit and put in map
				mainUnitCreation(MOVE_Module, eClass, OperationType.MOVE);
				map.put(MOVE_Module, outputFileName);
				
				// create initialChecks, if any
				if(createINITIALS) {
					createInitialChecksForMultiplicities(MOVE_Module.getName(), target, eClass, eRef, OperationType.MOVE);
				}
			}
		}
		
		return map;
	}
	
	
	private void create_Rule_toSetReference(Module module, EReference eRef, EClass eClass, EClass target) {
		
		if(module.getName().startsWith(ADD_prefix)) {
			
			// ADD ***************************************************************************************************/
			Rule rule = henshinFactory.createRule();
			rule.setActivated(true);
			rule.setName("addTo"+eClass.getName() + "Ref" +Common.firstLetterToUpperCase(eRef.getName())+"To"+target.getName());
			rule.setDescription("Adds to "+eClass.getName() +"'s reference "+ eRef.getName() +" the target "+ target.getName());
			module.getUnits().add(rule);

			// create preserved node for eClass
			NodePair selectedNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, "Selected", eClass);
			Node rhsNode = selectedNodePair.getRhsNode();

			NodePair newNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, "NewTarget", target);

			// create <<create>> edge for new target for EReference and it's EOpposite, if any
			HenshinRuleAnalysisUtilEx.createCreateEdge(rhsNode, newNodePair.getRhsNode(), eRef);
			
		}
		else if(module.getName().startsWith(SET_prefix)) {
			
			// SET ***************************************************************************************************/
			Rule rule = henshinFactory.createRule();
			rule.setActivated(true);
			rule.setName("set"+eClass.getName() + "Ref" +Common.firstLetterToUpperCase(eRef.getName())+"To"+target.getName());
			rule.setDescription("Set"+eClass.getName() + "Ref" +eRef.getName() +"To"+target.getName());
			module.getUnits().add(rule);

			// create preserved node for eClass
			NodePair selectedNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, "Selected", eClass);
			Node rhsNode = selectedNodePair.getRhsNode();

			NodePair newNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, "NewTarget", target);

			// create <<create>> edge for new target for EReference and it's EOpposite, if any
			HenshinRuleAnalysisUtilEx.createCreateEdge(rhsNode, newNodePair.getRhsNode(), eRef);
			
		}
		else if(module.getName().startsWith(CHANGE_prefix)) {
			// CHANGE ***************************************************************************************************/
			Rule rule = henshinFactory.createRule();
			rule.setActivated(true);
			rule.setName("change"+eClass.getName() + "Ref" +Common.firstLetterToUpperCase(eRef.getName())+"To"+target.getName());
			rule.setDescription("Change the EReference "+eRef.getName());
			module.getUnits().add(rule);

			// create preserved node for eClass
			NodePair selectedNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, "Selected", eClass);
			
			NodePair oldNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, "OldTarget", target);		
			NodePair newNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, "NewTarget", target);

			// create <<delete>> edge to old target for EReference and it's EOpposite, if any
			HenshinRuleAnalysisUtilEx.createDeleteEdge(selectedNodePair.getLhsNode(), oldNodePair.getLhsNode(), eRef, rule);
			// create <<create>> edge for new target for EReference and it's EOpposite, if any
			HenshinRuleAnalysisUtilEx.createCreateEdge(selectedNodePair.getRhsNode(), newNodePair.getRhsNode(), eRef);
		}
		else if(module.getName().startsWith(MOVE_prefix)) {
			// MOVE ***************************************************************************************************/
			Rule rule = henshinFactory.createRule();
			rule.setActivated(true);
			rule.setName("move"+eClass.getName() + "Ref" +Common.firstLetterToUpperCase(eRef.getName())+"To"+target.getName());
			rule.setDescription("Moves one "+eClass.getName()+" with reference "+eRef.getName()+" to "+target.getName());
			module.getUnits().add(rule);

			// create preserved node for eClass
			NodePair selectedNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, "Selected", eClass);

			NodePair oldNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, "OldSource", target);		
			NodePair newNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, "NewSource", target);

			// create <<delete>> edge to old target for EReference and it's EOpposite, if any
			HenshinRuleAnalysisUtilEx.createDeleteEdge(oldNodePair.getLhsNode(), selectedNodePair.getLhsNode(), eRef, rule);
			// create <<create>> edge for new target for EReference and it's EOpposite, if any
			HenshinRuleAnalysisUtilEx.createCreateEdge(newNodePair.getRhsNode(), selectedNodePair.getRhsNode(), eRef);
		}
		
	}
	
	private ArrayList<ModuleFilenamePair> replaceCreateableChildrenWithSubTypes(ArrayList<ModuleFilenamePair> list) {
		
		
		ArrayList<ModuleFilenamePair> newList = new ArrayList<ModuleFilenamePair>();
		
		for(ModuleFilenamePair pair : list) {
			
			Module origModule = pair.getModule();
			String origOutputFileName = pair.getOutputFileName();
			
			String sep = System.getProperty("file.separator");
			if(sep.equals("\\")) sep="\\"+sep;
			String[] fileNamePath = origOutputFileName.split(sep);
			String fileName = fileNamePath[fileNamePath.length-1].replace(EXECUTE_suffix, "");
			
			assert(fileName.equals(origModule.getName())): "Output filename and module file name are not equal";
			
			List<Node> originalNodes = HenshinRuleAnalysisUtilEx.getChildNodesWithinAContainmentRelation(
					origModule, HenshinRuleAnalysisUtilEx.NodeKindSelection.CREATE,false);
			
			if(!originalNodes.isEmpty()) {
				// Next if-statement is important! We don't want to create variants for the
				// main child node X when we have a module "CREATE_X_inSomeContext"
				// The sub type replacement shall only be done for dangling children
				// not the main child in focus! The main child in focus which shall
				// be created is re-recognized via the node name "New".
				// All sub types of the main child in focus will receive their own
				// CREATE-module when visited.
				for(Node origN: originalNodes) {
					String nOrigN = origN.getName();
					if(nOrigN!=null && nOrigN.equals("New")) {
						continue;
					}
					EClass typeOfReplacable = origN.getType();

					List<EClass> replacements = eClassInfoManagement.getEClassInfo(typeOfReplacable).getSubTypes();

					for(EClass replacement: replacements) {

						if (!isAllowed(replacement,false))  continue;

						// create copy
						Module copy = EcoreUtil.copy(origModule);	
						String outputFileName = origOutputFileName;

						// adjust outputFileName
						long id = System.nanoTime();				
						if(outputFileName.matches(".*(_Variant\\d*\\w*\\.\\w*)$")) {
							outputFileName = outputFileName.replaceAll("(\\d*"+EXECUTE_suffix+")$", id+EXECUTE_suffix);
							copy.setDescription(copy.getDescription().replaceAll("(Variant\\d*)$", "Variant"+String.valueOf(id)));
							copy.setName(copy.getName().replaceAll("(Variant\\d*)$", "Variant"+String.valueOf(id)));							
						}else{
							outputFileName = outputFileName.replace(EXECUTE_suffix, "_Variant"+id+EXECUTE_suffix);
							copy.setDescription(copy.getDescription()+" Variant"+id);
							copy.setName(copy.getName()	+"_Variant"+id);						
						}

						// get nodes of the copy
						List<Node> copyNodes = HenshinRuleAnalysisUtilEx.getChildNodesWithinAContainmentRelation(
								copy, HenshinRuleAnalysisUtilEx.NodeKindSelection.CREATE,false);

						// search for the 1st node with same type as typeOfReplacable and replace
						for(Node copyNode :copyNodes) {
							if(copyNode.getType().equals(typeOfReplacable)) {
								
								String nCopyNode = copyNode.getName();

								// replace the name of typeOfReplacable by the name of the replacement in filename
								// if current node is really the "New" Node					
								if(nCopyNode!=null && copyNode.getName().equals("New")) {

									//make sure that only the actual file name is edited and not preceding directories
									fileNamePath = outputFileName.split(sep);
									fileName = fileNamePath[fileNamePath.length-1];
									fileName = fileName.replaceFirst(typeOfReplacable.getName(), replacement.getName());
									outputFileName = "";
									for(int i=0; i<fileNamePath.length-1; i++) {
										outputFileName += fileNamePath[i] + System.getProperty("file.separator");//no escape required
									}
									outputFileName += fileName;

									//remove Variant suffix
									outputFileName = outputFileName.replaceAll("(_Variant\\d*\\w*\\.\\w*)$", EXECUTE_suffix);

									// change module and rule details
									String newCopyName = copy.getName().replaceFirst(typeOfReplacable.getName(), replacement.getName());
									newCopyName = newCopyName.replaceAll("(_Variant\\d*)$", "");
									copy.setName(newCopyName);
									String newCopyDesc = copy.getDescription().replaceFirst(typeOfReplacable.getName(), replacement.getName());
									newCopyDesc = newCopyDesc.replaceAll("(\\sVariant\\d*)$", "");
									copy.setDescription(newCopyDesc);

									Rule ruleOfNode = (Rule) copyNode.getGraph().eContainer();
									ruleOfNode.setName(ruleOfNode.getName().replaceFirst(typeOfReplacable.getName(), replacement.getName()));
									ruleOfNode.setDescription(ruleOfNode.getDescription().replaceFirst(typeOfReplacable.getName(), replacement.getName()));
								}


								// replace type
								copyNode.setType(replacement);

								// create mandatories for replacement, if any
								createMandatoryChildren((Rule)copyNode.getGraph().eContainer(),
										eClassInfoManagement.getEClassInfo(replacement),
										copyNode);
								createMandatoryNeighbours((Rule)copyNode.getGraph().eContainer(),
										eClassInfoManagement.getEClassInfo(replacement),
										copyNode);

								break; // since we only want 1 replacement per new module
							}
						}

						// Now examine copy: are there now some abstract <<create>> children? if so, replace!
						if(!HenshinRuleAnalysisUtilEx.getChildNodesWithinAContainmentRelation(
								copy, HenshinRuleAnalysisUtilEx.NodeKindSelection.CREATE,true).isEmpty()) {
							ArrayList<ModuleFilenamePair> propagatedList = new ArrayList<ModuleFilenamePair>();
							ArrayList<ModuleFilenamePair> furtherReplaces = null;
							propagatedList.add(new ModuleFilenamePair(outputFileName, copy));
							furtherReplaces = replaceCreatableAbstractChildrenWithConcretes(propagatedList);

							if(furtherReplaces!=null) {
								newList.addAll(furtherReplaces);			
							}
						}else{
							// none found after replacement and creation of mandatories --> complete module
							newList.add(new ModuleFilenamePair(outputFileName, copy));
						}
						// also put the original module, since typeOfReplacable is also allowed beside it's subTypes
						// (that's the difference to the abstract replacement)
						if(!newList.contains(pair)) {
							newList.add(pair);
						}
					}
				}
			//--------------------------------------------------------------------------------------------------
			}
			// no replaceables in original --> complete module
			else{
				newList.add(pair);
				return null;
			}
		}	
		
		// none in ts found (anymore)
		return newList;
	}
	
	/**
	 * This fills the actual blackList with real EClasses.
	 * If preventInconsistencyThroughSkipping is set to TRUE, then
	 * it will additionally be checked if other model elements have mandatory
	 * dependencies to the skippable elements. If so, they will be skipped, too.
	 * Recursively.
	 */
	private static void unfoldBlackList() {
		
		for(String eClassName: blackList) {
			for(EPackage ePackage: ePackages) {				
				EClass skip = (EClass) ePackage.getEClassifier(eClassName);
				if(skip!=null) {
					unfoldedBlackList.add(skip);
				}
			}
		}
		
		if(preventInconsistencyThroughSkipping) {
			unfoldedBlackList = findMoreSkips(unfoldedBlackList);
		}	
	}
	
	
	/**
	 * This fills the actual whiteList with real EClasses.
	 * It will additionally be checked recursively if EClasses in the WhiteList require
	 * further EClasses to prevent model inconsistency.
	 */
	private static void unfoldWhiteList() {
		
		for(String eClassName: whiteList) {
			for(EPackage ePackage: ePackages) {				
				EClass eClass = (EClass) ePackage.getEClassifier(eClassName);
				if(eClass!=null) {
					unfoldedWhiteList.add(eClass);
				}
			}
		}		
		findMoreRequiredClassifier(unfoldedWhiteList);
	}
	
	
	/**
	 * This method extends the whiteList by classifiers (meta classes) that are extended by stereotypes
	 * when using the the profile mechanismn. Meta classes are necessary because profile model instances
	 * contain not only objects for stereotypes but also for their meta classes.<br/><br/>
	 * This method also fills the implicitRequirementList with EClasses.
	 * An EClass is implicitly required if it is supertype for other EClasses on the
	 * whitelist (or EClasses not on the blacklist) because it contains EAttributes and
	 * ERferences which are inherited and relevant for the sub types.
	 * @param oldList
	 */
	private static void findMoreRequiredClassifier(List<EClass> oldList) {
		
		ArrayList<EClass> currentList = new ArrayList<EClass>(oldList);
		
		// find implicit requirement by stereotyping / meta class extension
		if(profileApplicationInUse) {

			for(EClass req: oldList) {
				for(EClass metaClass:eClassInfoManagement.getEClassInfo(req).getExtendedMetaClasses()){
					if(!currentList.contains(metaClass)) {
						currentList.add(metaClass);
					}
				}

			}
		}
		unfoldedWhiteList = currentList;

		// find implicit requirements of supertypes
		for(EClass req: currentList) {

			for(EAttribute ea: req.getEAllAttributes()) {
				
				//if EAttribute is derived from SuperType
				if(!ea.eContainer().equals(req)) {					
					EClass superType = (EClass) ea.eContainer();
					//if supertype is not explicitly set on blacklist or already on whitelist
					if(!unfoldedBlackList.contains(superType) & !unfoldedWhiteList.contains(superType)) {
						//add superType to implicit requirement list.
						if(!implicitRequirements.get(REQ_IS).contains(superType)) {
							implicitRequirements.get(REQ_IS).add(superType);
						}
					}
				}				
			}			
		}
	}



	private static ArrayList<EClass> findMoreSkips(List<EClass> oldList) {
		
		ArrayList<EClass> extendedSkipList = new ArrayList<EClass>();
		extendedSkipList.addAll(oldList);
		
		boolean newEntries = false;
		
		for(EClass skip : oldList) {
			EClassInfo skipInfo = eClassInfoManagement.getEClassInfo(skip);
			for(List<EClass> mpcList :skipInfo.getMandatoryParentContext().values()) {
				for(EClass mpc: mpcList) {
					// only add skip if not already in list and if its not required on white or rootlist
					if(!oldList.contains(mpc) && !whiteList.contains(mpc.getName()) && !mpc.equals(root)) {
						extendedSkipList.add(mpc);
						newEntries = true;
					}
				}
			}
			for(List<EClass> mpcList :skipInfo.getMandatoryNeighbourContext().values()) {
				for(EClass mnc: mpcList) {
					// only add skip if not already in list and if its not required on white or rootlist
					if(!oldList.contains(mnc) && !whiteList.contains(mnc.getName()) && !mnc.equals(root)) {
						extendedSkipList.add(mnc);
						newEntries = true;
					}
				}
			}
		}
		
		if(newEntries) {
			extendedSkipList = findMoreSkips(extendedSkipList);
		}
		return extendedSkipList;
		
	}
	
	/**
	 * Checks whether an eClass is part of the blackList or on whiteList or required in other ways.
	 * The parameter asPivot should be TRUE, if the main focus of
	 * the generatable transformation lies on that eClass (meaning the eClass is
	 * not just a mandatory or optional dangling model element).
	 * @param eClass
	 * @param asPivot
	 * @return
	 */
	private static boolean isAllowed(EClass eClass, Boolean asPivot) {
		
		EClassInfo eClassInfo = eClassInfoManagement.getEClassInfo(eClass);
		
		boolean blackListed	= unfoldedBlackList.contains(eClass);
		boolean whiteListed	= unfoldedWhiteList.contains(eClass);
		boolean assumeAllOnWhitelist = unfoldedWhiteList.isEmpty();
		boolean requiredForFeatureInheritance = implicitRequirements.get(REQ_IS).contains(eClass);

		
		
		//in case use of EClass is in its own context ---------------------------------------------/
		if(asPivot) {			

			if(whiteListed
					|| requiredForFeatureInheritance
					|| (blackListed==false && assumeAllOnWhitelist)) {
				return true;
			}else{
				return false;
			}

			
		}//in case use of EClass is inside another context (as neighbour or child or as parent) --/
		else{
			
			if(preventInconsistencyThroughSkipping){				
				boolean requiredForContexts = false;
				boolean requiredForChild = false;
				
				//check if one of the mandatory contexts of this EClass is white listed or implicitly
				//required. If so this EClass is necessary too.					
				for(EClass mandatoryContext: eClassInfo.getMandatoryContexts()) {			

					if( unfoldedWhiteList.contains(mandatoryContext)
							|| implicitRequirements.get(REQ_IS).contains(mandatoryContext)
							|| (unfoldedBlackList.contains(mandatoryContext)==false && assumeAllOnWhitelist)) {
						requiredForContexts =  true;
						break;
					}		
				}
				
				//check if current eClass is the parent of some white listed EClass and therefore necessary
				for(EClass whiteListedEClass: unfoldedWhiteList) {
					for(Entry<EReference,List<EClass>> entry: eClassInfoManagement.getAllOptionalParentContext(whiteListedEClass).entrySet()) {
						if(entry.getValue().contains(eClass)) {
							requiredForChild = true;
							break;
						}
					}
					if(requiredForChild) break;
				}
				
				if(whiteListed
						|| requiredForFeatureInheritance
						|| requiredForChild
						|| requiredForContexts
						|| (blackListed==false && assumeAllOnWhitelist)) {
					return true;
				}
				else{
					return false;
				}
			}else{ //preventInconsistencyThroughSkipping==false
				if(blackListed) { //hard cut
					return false;
				}
			}
			
		}		
		return true;
	}

	
	/**
	 * Checks whether an EClass is only implicitly required because it inherits its features
	 * to sub types which are white listed. Implicitly required EClasses do not need CREATE/DELETES.
	 * Only the SET/MOVE/ADD/CHANGE transformations.
	 * @param eClass
	 * @return
	 */
	private static boolean isOnlyImplicitlyRequiredForFeatureInheritance(EClass eClass) {
		if(implicitRequirements.get(REQ_IS).contains(eClass) && !unfoldedWhiteList.contains(eClass)) {
			return true;
		}
		return false;
	}
	

	
	/**
	 * Checks whether an eClass is the user specified root element
	 * @param eClass
	 * @return
	 */	
	private static boolean isRoot(EClass eClass) {
		return root==eClass;
	}
	
	/**
	 * Checks if a given EReference is inherited
	 * @param the EReference
	 * @param concerningEClass is the class to check on
	 * @return true if inherited
	 */
	private static boolean isInheritedReference(EReference eRef, EClass concerningEClass) {
		
		return !concerningEClass.getEReferences().contains(eRef);
	}
	
	
	private static void loadConfiguration(String config) {
		
		String pattern = Pattern.quote(System.getProperty("file.separator"));
		String[] splittedConfigPath = config.split(pattern);
		
		Document doc = XMLParser.parseStream(IOUtil.getInputStream(splittedConfigPath[splittedConfigPath.length-1]));
		
		Element docElem = doc.getDocumentElement();
		org.w3c.dom.Node currentNode = null;
		NodeList currentChildNodes = null;
		
		// retrieve and set global parameters	
		currentNode = doc.getElementsByTagName("preventInconsistency").item(0);
		preventInconsistencyThroughSkipping = Boolean.valueOf(Common.getAttributeValue("value", currentNode));
		currentNode = doc.getElementsByTagName("initialChecks").item(0);		
		createINITIALS = Boolean.valueOf(Common.getAttributeValue("allow", currentNode));
		currentNode = doc.getElementsByTagName("disableVariants").item(0);		
		disableVariants = Boolean.valueOf(Common.getAttributeValue("value", currentNode));
		currentNode = doc.getElementsByTagName("modelUsesProfileMechanism").item(0);		
		profileApplicationInUse = Boolean.valueOf(Common.getAttributeValue("value", currentNode));
		currentNode = doc.getElementsByTagName("reduceToSuperType").item(0);
		reduceToSuperType_SETUNSET = Boolean.valueOf(Common.getAttributeValue("SET_UNSET", currentNode));
		reduceToSuperType_ADDREMOVE = Boolean.valueOf(Common.getAttributeValue("ADD_REMOVE", currentNode));
		reduceToSuperType_CHANGE = Boolean.valueOf(Common.getAttributeValue("CHANGE", currentNode));
		currentNode = doc.getElementsByTagName("Creates").item(0);
		createCREATES = Boolean.valueOf(Common.getAttributeValue("allow", currentNode));
		currentNode = doc.getElementsByTagName("Deletes").item(0);
		createDELETES = Boolean.valueOf(Common.getAttributeValue("allow", currentNode));
		currentNode = doc.getElementsByTagName("Moves").item(0);
		createMOVES = Boolean.valueOf(Common.getAttributeValue("allow", currentNode));
		currentNode = doc.getElementsByTagName("Changes").item(0);
		createCHANGES = Boolean.valueOf(Common.getAttributeValue("allow", currentNode));
		currentNode = doc.getElementsByTagName("Adds").item(0);
		createADDS = Boolean.valueOf(Common.getAttributeValue("allow", currentNode));
		currentNode = doc.getElementsByTagName("Removes").item(0);
		createREMOVES = Boolean.valueOf(Common.getAttributeValue("allow", currentNode));
		currentNode = doc.getElementsByTagName("Sets").item(0);
		createSETS = Boolean.valueOf(Common.getAttributeValue("allow", currentNode));
		currentNode = doc.getElementsByTagName("Unsets").item(0);
		createCHANGES = Boolean.valueOf(Common.getAttributeValue("allow", currentNode));
		
		
		// retrieve and fill root, whiteList and blackList entries		
		currentNode = docElem.getElementsByTagName("Root").item(0);
		rootName = String.valueOf(Common.getAttributeValue("name", currentNode));
		rootEClassCanBeNested = Boolean.valueOf(Common.getAttributeValue("nested", currentNode));
		if(!rootName.equals("")) {
			//resolve root
			for(EPackage ePackage: ePackages) {				
				root = (EClass) ePackage.getEClassifier(rootName);
			}	
		}
		
		// blackList
		currentNode = docElem.getElementsByTagName("BlackList").item(0);
		currentChildNodes = currentNode.getChildNodes();
		for(int i=0; i<currentChildNodes.getLength(); i++) {
			if(currentChildNodes.item(i).getNodeName().equals("EClass")) {
				blackList.add(Common.getAttributeValue("name", currentChildNodes.item(i)));
			}
		}
		// whiteList
		currentNode = docElem.getElementsByTagName("WhiteList").item(0);
		currentChildNodes = currentNode.getChildNodes();
		for(int i=0; i<currentChildNodes.getLength(); i++) {
			if(currentChildNodes.item(i).getNodeName().equals("EClass")) {
				whiteList.add(Common.getAttributeValue("name", currentChildNodes.item(i)));
			}
		}		
	}
	
	private void finalizeStereotypeVersion(Module module, String outputFileName, EClass context, EClass currentEClass, EReference eRef, OperationType tstype) {
		
		// create initialChecks if any
		if(createINITIALS) {
			createInitialChecksForMultiplicities(module.getName(),context,currentEClass,eRef,OperationType.CREATE);
		}

		LogUtil.log(LogEvent.NOTICE, "Generating CREATE : " + module.getName());
		
		// create mainUnit
		mainUnitCreation(module, currentEClass, OperationType.CREATE);
		
		// serialize
		serialize(module, outputFileName);
		
		// if wished: create inverse
		if(createDELETES) {
			// inverse and string replaces
			Module inverseModule = createInverse(module);
			LogUtil.log(LogEvent.NOTICE, "Generating DELETE : " + inverseModule.getName());			
			Common.replaceNewsWithToBeDeleted(inverseModule);
			
			
			//TODO Test the following: mainUnit is deleted from module
			// remove old mainUnit and re-create mainUnit & serialize
			Iterator<Unit> itUnit = inverseModule.getUnits().iterator();
			while(itUnit.hasNext()){
				Unit unit = itUnit.next();
				if(!(unit instanceof Rule)) {
					if(unit.getName().equals("mainUnit")) {
						itUnit.remove(); //will remove current unit (not the iterator)
					}
				}
			}
			mainUnitCreation(inverseModule, currentEClass, OperationType.DELETE);			
			serialize(inverseModule, outputFileName.replace(CREATE_prefix, DELETE_prefix));
			
			// create initialChecks if any
			if(createINITIALS) {
				createInitialChecksForMultiplicities(inverseModule.getName(),context,currentEClass,eRef,OperationType.DELETE);
			}
		}
	}	
}
