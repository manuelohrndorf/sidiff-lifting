package org.sidiff.serge.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.emf.henshin.model.PriorityUnit;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.UnaryFormula;
import org.eclipse.emf.henshin.model.Unit;
import org.sidiff.common.emf.extensions.impl.EClassifierInfo;
import org.sidiff.common.emf.extensions.impl.EClassifierInfo.ConstraintType;
import org.sidiff.common.emf.extensions.impl.Mask;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.FormulaCombineOperator;
import org.sidiff.common.henshin.INamingConventions;
import org.sidiff.common.henshin.NodePair;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.serge.core.Common;
import org.sidiff.serge.core.ModuleFilenamePair;
import org.sidiff.serge.exceptions.ConstraintException;
import org.sidiff.serge.exceptions.EPackageNotFoundException;

public class HenshinModuleGenerator extends AbstractGenerator {
	
	/** Henshin access ****************************************************************************/
	
	private static HenshinFactory henshinFactory = HenshinFactory.eINSTANCE;


	@Override
	public void generate_CREATE_And_DELETE_Modules(EClassifier eClassifier) throws ConstraintException {
		
		// Get the EClassInfo for eClass and return if no transformations are allowed
		EClassifierInfo eClassInfo = ecm.getEClassifierInfo(eClassifier);
		
		// return on the following conditions
		if (!eClassInfo.selfMayHaveTransformations()) return;
		if ((isRoot(eClassifier) && !rootEClassCanBeNested)) return;
		if (!isAllowed(eClassifier,true,reduceToSuperType_CREATEDELETE)) return;
		if (profileApplicationInUse && eClassInfo.isExtendedMetaClass() && !isRoot(eClassifier)) return;
		if (isRoot(eClassifier) && !rootCanBeNested(eClassifier)) return;
		if (!createCREATES) return;

		if(!profileApplicationInUse || (profileApplicationInUse && !eClassInfo.isStereotype())) {

			/** Create Modules for every parent in which the EClass may exist. ************************************************************/
			HashMap<EReference, List<EClassifier>> optionalParents = ecm.getAllOptionalParentContext(eClassifier, reduceToSuperType_CREATEDELETE);
			for(Entry<EReference,List<EClassifier>> pcEntry: optionalParents.entrySet()) {			
				List<EClassifier> contexts = pcEntry.getValue();
				EReference eRef = pcEntry.getKey();

				for(EClassifier context: contexts) {

					if (!isAllowed(context,false,reduceToSuperType_CREATEDELETE)) continue;

					// Create file name and Module				
					Module module = henshinFactory.createModule();
					String name = CREATE_prefix + eClassifier.getName()+IN + context.getName()+"_("+eRef.getName()+")";
					LogUtil.log(LogEvent.NOTICE, "Generating " + name);
					String outputFileName = outputFolderPath + name + EXECUTE_suffix;					
					module.setDescription("Creates one "+eClassifier.getName()+" in " + context.getName());
					module.setName(name);


					// Add imports for meta model
					module.getImports().addAll(ePackagesStack);

					// create rule
					Rule rule = createBasicRule(module, eRef, eClassifier, context, null, null);
					Node newNode = HenshinRuleAnalysisUtilEx.getRHSMinusLHSNodes(rule).get(0);

					// create mandatories if any
					if(eClassInfo.hasMandatories()) {

						createMandatoryChildren(rule, eClassInfo, newNode);			
						createMandatoryNeighbours(rule, eClassInfo, newNode);

					}
										
					createElementsForConstraints_CREATE(module);

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
						
						// create multiplicity preconditions if any
						if(multiplicityPreconditionsIntegrated) {
							createIntegratedPreconditionsForMultiplicities(rule, OperationType.CREATE);
						}
						if(multiplicityPreconditionsSeparately) {
							createInitialChecksForMultiplicities(module.getName(),context,eClassifier,eRef,null, null, OperationType.CREATE);
						}
						
						LogUtil.log(LogEvent.NOTICE, "Generating CREATE : " + module.getName());

						// create mainUnit
						mainUnitCreation(module, eClassifier, OperationType.CREATE);

						// serialize
						serialize(module, outputFileName);

						// if wished: create inverse
						if(createDELETES) {
							// inverse and string replaces
							Module inverseModule = createInverse(module);
							LogUtil.log(LogEvent.NOTICE, "Generating DELETE : " + inverseModule.getName());			
							replaceNewsWithToBeDeleted(inverseModule);

							// remove old mainUnit and re-create mainUnit
							removeAllNonRuleUnits(inverseModule);	
							mainUnitCreation(inverseModule, eClassifier, OperationType.DELETE);			

							// create multiplicity preconditions if any
							if(multiplicityPreconditionsIntegrated) {
								Rule inverseRule = HenshinRuleAnalysisUtilEx.getRulesUnderModule(inverseModule).get(0);
								createIntegratedPreconditionsForMultiplicities(inverseRule, OperationType.DELETE);
							}
							if(multiplicityPreconditionsSeparately) {
								createInitialChecksForMultiplicities(inverseModule.getName(),context,eClassifier,eRef,null, null, OperationType.DELETE);								
							}
							
							// serialize
							serialize(inverseModule, outputFileName.replace(CREATE_prefix, DELETE_prefix));
						}


					}else{
						for(ModuleFilenamePair pair: variantList) {

							Module module4variant = pair.getModule();
							String variantOutputFileName = pair.getOutputFileName();

							// create multiplicity preconditions if any
							if(multiplicityPreconditionsIntegrated) {
								Rule rule4variant = HenshinRuleAnalysisUtilEx.getRulesUnderModule(module4variant).get(0);
								createIntegratedPreconditionsForMultiplicities(rule4variant, OperationType.CREATE);
							}
							if(multiplicityPreconditionsSeparately) {
								createInitialChecksForMultiplicities(module4variant.getName(),context,eClassifier,eRef,null, null, OperationType.CREATE);
							}

							// create mainUnit & serialize
							mainUnitCreation(module4variant, eClassifier, OperationType.CREATE);
							serialize(module4variant, variantOutputFileName);

							// if wished: create inverse
							if(createDELETES) {
								// inverse and string replaces
								Module inverseModule = createInverse(module4variant);
								LogUtil.log(LogEvent.NOTICE, "Generating DELETE : " + inverseModule.getName());			
								replaceNewsWithToBeDeleted(inverseModule);

								// remove old mainUnit and re-create mainUnit
								removeAllNonRuleUnits(inverseModule);
								mainUnitCreation(inverseModule, eClassifier, OperationType.DELETE);			

								// create multiplicity preconditions if any
								if(multiplicityPreconditionsIntegrated) {
									Rule inverseRule = HenshinRuleAnalysisUtilEx.getRulesUnderModule(inverseModule).get(0);
									createIntegratedPreconditionsForMultiplicities(inverseRule, OperationType.DELETE);
								}
								if(multiplicityPreconditionsSeparately) {
									createInitialChecksForMultiplicities(inverseModule.getName(),context,eClassifier,eRef,null, null, OperationType.DELETE);			
								}
								// serialize
								serialize(inverseModule, variantOutputFileName.replace(CREATE_prefix, DELETE_prefix));
							}
						}					
					}
				}
			}
			/** In case of Stereotype, there are no contexts! Just create Rule with <<create>> Node for Stereotype ****************************/
		}else{

			// Create file name and Module				
			Module module = henshinFactory.createModule();
			String outputFileName = "";

			LogUtil.log(LogEvent.NOTICE, "Generating CREATE : " + eClassifier.getName());
			outputFileName = outputFolderPath + CREATE_prefix + eClassifier.getName()+ EXECUTE_suffix;					
			module.setDescription("Creates one "+eClassifier.getName());
			module.setName(CREATE_prefix + eClassifier.getName());
			
			// Add imports for meta model
			module.getImports().addAll(ePackagesStack);

			// create rule
			Rule rule = createBasicRule(module, null, eClassifier, null, null, null);
			Node newNode = HenshinRuleAnalysisUtilEx.getRHSMinusLHSNodes(rule).get(0);

			// create mandatories if any
			if(eClassInfo.hasMandatories()) {

				createMandatoryChildren(rule, eClassInfo, newNode);			
				createMandatoryNeighbours(rule, eClassInfo, newNode);

			}

			// create mainUnit
			mainUnitCreation(module, eClassifier, OperationType.CREATE);

			// serialize
			serialize(module, outputFileName);
			
			//TODO inverses, variants, initials
		}
			
	}

	@Override
	public void generate_Update_Module(EClassifier eClassifier) throws ConstraintException {
		
		EClassifierInfo eClassInfo = ecm.getEClassifierInfo(eClassifier);
		
		if (!(isAllowed(eClassifier,true,false) && !isImplicitlyRequiredForFeatureInheritance(eClassifier)))  return;
		if (profileApplicationInUse && eClassInfo.isExtendedMetaClass() && !isRoot(eClassifier)) return;
		
		HashMap<Module,String> moduleMap = new HashMap<Module,String>();
		
		EClass eClass = (EClass) eClassifier;

		if(createSETS) {

			// EAttributes which shall be considered
			List<EAttribute> easToConsider = new ArrayList<EAttribute>();
			if(reduceToSuperType_SETUNSET) {
				//all own eattributes
				List<EAttribute> ownEAttributes = eClass.getEAttributes();
				if(ownEAttributes!=null) {
					easToConsider.addAll(ownEAttributes);
				}
				
				//also include all inherited EAttributes, for which SERGEe Constraints are defined
				List<EAttribute> easOfConstraintsToConsider = ecm.getAllInheritedEAttributesInvolvedInConstraints(eClassifier);
				if(easOfConstraintsToConsider!=null) {
					easToConsider.addAll(easOfConstraintsToConsider);
				}
				
			}else{
				//all inherited eattributes
				easToConsider = eClass.getEAllAttributes();
			}

			for(EAttribute ea: easToConsider) {
				// don't consider derived, not changeable, unsettable and transient references
				if(!ea.isDerived() && !ea.isTransient() && ea.isChangeable()) {
					
					int lowerBound = ea.getLowerBound();
					int upperBound = ea.getUpperBound();
					
					/********** un-supported yet: isMany *************************************************************************************/
					if((lowerBound == 0) && (upperBound == -1)) {
						//TODO multivalued eattribs
						System.out.println("----------------ea:"+ea.getName()
								+" of "+ea.eContainer().eClass().getName()
								+ "isMany: ["+lowerBound+","+upperBound+"]--------------");
					}
					else if ((lowerBound == 1) && (upperBound == -1)) {
						//TODO multivalued eattribs
						System.out.println("----------------ea:"+ea.getName()
								+" of "+ea.eContainer().eClass().getName()
								+ "isMany: ["+lowerBound+","+upperBound+"]--------------");
					}
					
					/**********SET / UNSET *************************************************************************************/
					else if( ((lowerBound == 0) && (upperBound == 1)) || ((lowerBound == 1) && (upperBound == 1))){
						
						// SET for EAttributes ***************************************************************************/
						LogUtil.log(LogEvent.NOTICE, "Generating SET : " + eClassifier.getName() + " attribute "+ ea.getName());

						// create SET_Module
						Module SET_Module = henshinFactory.createModule();

						// Add imports for meta model
						SET_Module.getImports().addAll(ePackagesStack);

						// create rule
						Rule rule = henshinFactory.createRule();
						rule.setActivated(true);
						rule.setName("set"+eClassifier.getName()+Common.toCamelCase(ea.getName()));
						rule.setDescription("Sets the EAttribute "+ea.getName());
						SET_Module.getUnits().add(rule);

						// create preserved node for eClass
						NodePair selectedNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, SEL, (EClass) eClassifier);
						Node rhsNode = selectedNodePair.getRhsNode();

						// create attribute
						HenshinRuleAnalysisUtilEx.createCreateAttribute(rhsNode, ea, Common.toCamelCase(ea.getName()));


						/**
						 * 
						 * TODO Michaela:
						 * Überprüfen, ob dies überhaupt sinnvoll sein kann.
						 * Nun für SysML deaktiviert, da baseType zum SET-Zeitpunkt bereits angehängt ist.
										
						// if profiledModel then link mandatory neighbours (expecially the meta class)
						if(profileApplicationInUse) {
							createMandatoryNeighbours(rule, eClassInfo, rhsNode);
						}
						*/ 

						// If selected eClass is constrained locally (e.g. it's name to be set must be unique under a local context),
						// create parent nodes which are required by NACs/PACs later.
						// If there is more than one possible parent, module must be copied.
						if(eClassInfo.isConstrainedToLocalNameUniqueness() && ((EAttribute)eClassInfo.getConstraintsAndFlags().get(ConstraintType.NAME_UNIQUENESS_LOCAL).get(1)==ea)) {
							moduleMap = createNameUniquenessLocalConstraint_SET(SET_Module, rule, eClassifier, ea, moduleMap);

						}
						else if(eClassInfo.isOnlyConstrainedToGlobalNameUniqueness() && ((EAttribute)eClassInfo.getConstraintsAndFlags().get(ConstraintType.NAME_UNIQUENESS_GLOBAL).get(1)==ea)) {
							moduleMap = createNameUniquenessGlobalConstraint_SET(SET_Module, rule, eClassifier, ea, moduleMap);
						}
						else{ //not constrained locally
							
							// set outputFilename, Module name and description
							String name = SET_prefix + eClassifier.getName() +"_"+Common.toCamelCase(ea.getName());
							String outputFileName =  outputFolderPath + name+ EXECUTE_suffix;
							SET_Module.setName(name);
							SET_Module.setDescription("Sets "+eClassifier.getName()+" "+Common.toCamelCase(ea.getName()));
							
							// create mainUnits & put TS in map for later serializing
							mainUnitCreation(SET_Module, eClassifier, OperationType.SET);
							moduleMap.put(SET_Module, outputFileName);
							
							// create UNSET if wished
							if(createUNSETS && ea.isUnsettable()) {
								moduleMap = createUNSET(SET_Module, eClassifier, ea, outputFileName, moduleMap);
							}
						}
					}
					
					
					/********** CHANGE EAttribute value combinations if value type is EEnum  *****************************************************/
					
					if((lowerBound == 1) && (upperBound == 1) && (ea.getEType() instanceof EEnum) && literalSwitching_CHANGE) {
						
						EEnum eenum = (EEnum) ea.getEType();
												
						// build up combinations
						Map<EEnumLiteral,List<EEnumLiteral>> combinations = new HashMap<EEnumLiteral,List<EEnumLiteral>>();

						for(EEnumLiteral literal :eenum.getELiterals()) {
							combinations.put(literal, new ArrayList<EEnumLiteral>(eenum.getELiterals()));
						}//here combination with self is still included.						
						
						// build CHANGE modules for every combination:
						
						for(Entry<EEnumLiteral,List<EEnumLiteral>> entry: combinations.entrySet()) {									
							EEnumLiteral leftSidelLiteral = entry.getKey();	
							
							//to remove combination with self, remove self from right value
							entry.getValue().remove(leftSidelLiteral);
							
							// now create module for every literal combination
							for(EEnumLiteral rightSideLiteral: entry.getValue()) {

								// CHANGE for EAttribute with EEnumLiteral as EType ****************************************************************/
								LogUtil.log(LogEvent.NOTICE, "Generating CHANGE : "
										+ eClassifier.getName() + ea.getName()+ "From"
										+ leftSidelLiteral.getName()
										+ "To "+rightSideLiteral.getName());

								// create CHANGE_Module
								Module CHANGE_Module = henshinFactory.createModule();

								// Add imports for meta model
								CHANGE_Module.getImports().addAll(ePackagesStack);

								// create rule
								Rule rule = henshinFactory.createRule();
								rule.setActivated(true);
								rule.setName("change"+eClassifier.getName()
										+Common.toCamelCase(ea.getName())+FROM
										+Common.toCamelCase(leftSidelLiteral.getName())
										+TO+rightSideLiteral.getName());
								rule.setDescription("Changes the attribute value of "
										+ea.getName() +FROM
										+leftSidelLiteral.getName()
										+TO+rightSideLiteral.getName());
								CHANGE_Module.getUnits().add(rule);

								NodePair containerNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, SEL, ea.eContainer().eClass());

								HenshinRuleAnalysisUtilEx.createPreservedAttribute(containerNodePair, ea, "\""+leftSidelLiteral.getName()+"\"", false);
								containerNodePair.getRhsNode().getAttribute(ea).setValue("\""+rightSideLiteral.getName()+"\"");

								// set outputFilename, Module name and description
								String name = CHANGE_prefix + eClassifier.getName() 
										+"_"+ea.getName()
										+FROM+leftSidelLiteral.getName()
										+TO+rightSideLiteral.getName();
								String outputFileName =  outputFolderPath + name+ EXECUTE_suffix;
								CHANGE_Module.setName(name);
								CHANGE_Module.setDescription(rule.getDescription());
								
								// create mainUnits & put TS in map for later serializing
								mainUnitCreation(CHANGE_Module, eClassifier, OperationType.CHANGE);
								moduleMap.put(CHANGE_Module, outputFileName);
								
							}
							
						}

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

						if (!isAllowed(targetType,false,false))  continue;

						// create module(s) to modify the reference
						moduleMap.putAll(createModuleToModifyReference(eRef, eClassifier, targetType));
						
					}
				}
			}
		}

		// serialize
		for(Entry<Module,String> entry: moduleMap.entrySet()) {
			serialize(entry.getKey(), entry.getValue());
		}
	}



	private HashMap<Module,String> createNameUniquenessLocalConstraint_SET(Module SET_Module, Rule rule, EClassifier eClassifier, EAttribute ea, HashMap<Module,String> moduleMap ) {
		
		Module origSET_Module = EcoreUtil.copy(SET_Module); //needed otherwise SET_Module will be modified later
		HashMap<EReference,List<EClassifier>> map = ecm.getAllParentContexts(eClassifier, reduceToSuperType_SETUNSET);
		Integer contextCounter = 0;
		
		for(Entry<EReference, List<EClassifier>> entry: map.entrySet()) {
			EReference eRef = entry.getKey();
			for(EClassifier context: entry.getValue()) {
				
				//check if parent is allowed at all
				if(!isAllowed(context, false, reduceToSuperType_SETUNSET)) continue;
				
				// if its not the first or the only context, a new Module must be created for each context
				String nameExtensionForConstraint = "";
				if(contextCounter>0) { 
					// copy SET_Module
					SET_Module = EcoreUtil.copy(origSET_Module);
					rule = HenshinRuleAnalysisUtilEx.getRulesUnderModule(SET_Module).get(0);
					nameExtensionForConstraint = "In"+Common.toCamelCase(context.getName());
				}
				// create context node and create preserved Edge in RHS & LHS
				NodePair contextNP = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, "", (EClass) context);
				NodePair selectedNP = HenshinRuleAnalysisUtilEx.getNodePair(rule, (EClass) eClassifier, SEL);
				HenshinRuleAnalysisUtilEx.createPreservedEdge(rule, contextNP, selectedNP, eRef);

				// create NACs or PACs to cover individual constraints
				createElementsForConstraints_SET(SET_Module, OperationType.SET, ConstraintType.NAME_UNIQUENESS_LOCAL);
				
				// set outputFilename, Module name and description
				String name = SET_prefix + eClassifier.getName() + nameExtensionForConstraint +"_"+Common.toCamelCase(ea.getName());
				String outputFileName =  outputFolderPath + name+ EXECUTE_suffix;
				SET_Module.setName(name);
				SET_Module.setDescription("Sets "+eClassifier.getName()+" "+Common.toCamelCase(ea.getName()));							
				
				// create mainUnits & put TS in map for later serializing
				mainUnitCreation(SET_Module, eClassifier, OperationType.SET);
				moduleMap.put(SET_Module, outputFileName);
				
				// create UNSET if wished
				if(createUNSETS && ea.isUnsettable()) {
					moduleMap = createUNSET(SET_Module, eClassifier, ea, outputFileName, moduleMap);
				}

				// increase counter, so for next iteration the SET_Module must be copied
				contextCounter++;
			}
		}
		return moduleMap;		
	}

	private HashMap<Module,String> createNameUniquenessGlobalConstraint_SET(Module SET_Module, Rule rule, EClassifier eClassifier, EAttribute ea, HashMap<Module,String> moduleMap ) {

		// create NACs or PACs to cover individual constraints
		createElementsForConstraints_SET(SET_Module, OperationType.SET, ConstraintType.NAME_UNIQUENESS_GLOBAL);

		// set outputFilename, Module name and description
		String name = SET_prefix + eClassifier.getName() +"_"+Common.toCamelCase(ea.getName());
		String outputFileName =  outputFolderPath + name+ EXECUTE_suffix;
		SET_Module.setName(name);
		SET_Module.setDescription("Sets "+eClassifier.getName()+" "+Common.toCamelCase(ea.getName()));							

		// create mainUnits & put TS in map for later serializing
		mainUnitCreation(SET_Module, eClassifier, OperationType.SET);
		moduleMap.put(SET_Module, outputFileName);

		// create UNSET if wished
		if(createUNSETS && ea.isUnsettable()) {
			moduleMap = createUNSET(SET_Module, eClassifier, ea, outputFileName, moduleMap);
		}

		return moduleMap;		
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
	public void generate_MOVE_Module(EClassifier eClassifier) throws ConstraintException {
		
		EClassifierInfo eci = ecm.getEClassifierInfo(eClassifier);
		
		if (!isAllowed(eClassifier,true,reduceToSuperType_MOVE) || createMOVES==false)  return;
		if (profileApplicationInUse && eci.isExtendedMetaClass() && !isRoot(eClassifier)) return;
		if (isRoot(eClassifier) && !rootCanBeNested(eClassifier)) return;
		
		HashMap<Module,String> moduleMap = new HashMap<Module,String>();
		
		// get all possible contexts (mandatory & optional) and the according references
		HashMap<EReference,List<EClassifier>> allParents = ecm.getAllParentContexts(eClassifier, reduceToSuperType_MOVE);
		HashMap<EReference, List<EClass>> allAllowedParents = new HashMap<EReference, List<EClass>>();
		for(EReference eRef: allParents.keySet()) {

			assert(eRef.isContainment()) : "eRef is no containment but should be";
			
			// don't consider containment references where multiplicity is fixed
			// in such cases a SWAP (complex) operation is necessary
			if(!(eRef.getLowerBound()==eRef.getUpperBound())) {
			
				// don't consider derived, not changeable, unsettable and transient references
				if(!eRef.isDerived() && eRef.isChangeable() && !eRef.isUnsettable() && !eRef.isTransient()) {
					
					EClass parent = (EClass) eRef.eContainer();
					
					// if parent is allowed, put it in allAllowedParent-List
					if (isAllowed(parent,false,reduceToSuperType_MOVE)) {
						if(allAllowedParents.get(eRef)==null) {
							List<EClass> newParentList = new ArrayList<EClass>();
							newParentList.add(parent);
							allAllowedParents.put(eRef, newParentList);
						}else{
							allAllowedParents.get(eRef).add(parent);
						}
					}				
				}
			}
		}
		moduleMap.putAll(create_MOVE_Combinations(eClassifier, allAllowedParents));
				
		// serialize
		for(Entry<Module,String> entry: moduleMap.entrySet()) {	
			serialize(entry.getKey(), entry.getValue());
		}
	}
	
	

	private HashMap<Module,String> create_MOVE_Combinations(EClassifier eClassifier, Map<EReference,List<EClass>> contextsMaps) {
		HashMap<Module,String> moduleMap = new HashMap<Module,String>();
		
		// all EReferences
		ArrayList<EReference> allReferences = new ArrayList<EReference>();
		allReferences.addAll(contextsMaps.keySet());
		
		for(Entry<EReference,List<EClass>> entry: contextsMaps.entrySet()) {
			
			EReference eRefA = entry.getKey();
			List<EClass> contexts_eRefA = entry.getValue();
			
			//internal-eRef combinations
			for(EClass contextA_eRef: contexts_eRefA) {
				for(EClass contextB_eRefA: contexts_eRefA) {
					//move eClass from contextA to contextB (along same eRefA)
					moduleMap.putAll(create_single_MOVE(eClassifier, eRefA, contextA_eRef, eRefA, contextB_eRefA));
				}
			}
			
			//inter-eRef combinations (switching of EReferences when moving)
			if(referenceSwitching_MOVE) {
				for(EClass contextA_eRefA: contexts_eRefA) {
					
					// get all other EReferences
					ArrayList<EReference> allOtherEReferences = new ArrayList<EReference>();
					allOtherEReferences.addAll(contextsMaps.keySet());
					allOtherEReferences.remove(contextA_eRefA);
					
					for(EReference eRefB: allOtherEReferences) {
						for(EClass contextB_eRefB: contextsMaps.get(eRefB)) {
							//move eClass to contextA to contextB (switching from eRefA to eRefB)
							moduleMap.putAll(create_single_MOVE(eClassifier, eRefA, contextA_eRefA, eRefB, contextB_eRefB));
						}
					}
					
				}
			}
			
		}	
		return moduleMap;
	}
	

	public void serialize(Module module, String outputFileName) {
		
		// assertions / checks
		checkModuleFileNameEquality(module, outputFileName);		
		checkMainUnitIsUnique(module);
		
		// kick out unnecessary sub package imports when super package import available
		// and set main meta-model packages as first import
		organizeImports(module);
		
		// create resource out of module and outputFileName
		ResourceSet resourceSet = new ResourceSetImpl();
		URI fileUri = URI.createFileURI(outputFileName);
		Resource resource = resourceSet.createResource(fileUri);
		resource.getContents().add(module);

		// create option map for saving
		Map<String,Boolean> options = new HashMap<String, Boolean>();
		options.put (XMIResource.OPTION_SCHEMA_LOCATION, true);

		try {
			resource.save(options);
			
		} catch (IOException e) {

			e.printStackTrace();
		}
	}


	/**
	 * This method removes unnecessary imports of EPackages that are
	 * sub packages of imported super packages. Additionally, this method
	 * removes all EPackage imports whose model elements have not been used.
	 * @param module
	 */
	private void organizeImports(Module module) {

		// find out which (sub) packages have actually been used (via node type usage)
		List<EPackage> actuallyUsedEPackages = new ArrayList<EPackage>();
		for(Rule rule: HenshinRuleAnalysisUtilEx.getRulesUnderModule(module)) {
			List<Node> allNodesInRule = new ArrayList<Node>();
			allNodesInRule.addAll(rule.getRhs().getNodes());
			allNodesInRule.addAll(rule.getLhs().getNodes());
			for(Node node: allNodesInRule) {
				EPackage usedEPackage = node.getType().getEPackage();
				if(!actuallyUsedEPackages.contains(usedEPackage)) {
					actuallyUsedEPackages.add(usedEPackage);
				}
			}
		}
		
		// get EPackage of main meta-model
		EPackage mainMetaModel = ePackagesStack.firstElement();
		// get sub EPackages of main meta-model
		List<EPackage> subsOfMain = new ArrayList<EPackage>();
		try {
			subsOfMain.addAll(Common.getAllSubEPackages(mainMetaModel));
		} catch (EPackageNotFoundException e) {
			e.printStackTrace();
		}
	
		// remove the following EPackages:
		// a) unused EPackages which are not the EPackage of the meta-model
		// b) sub EPackages of the meta-model
		Iterator<EPackage> itImports = module.getImports().iterator();
		while(itImports.hasNext()) {
			
			EPackage currentEPackage = itImports.next();				
			// if currentEPackage is not the meta-model itself....
			if(!mainMetaModel.equals(currentEPackage)) {
				// ..but a sub package of the meta-model			
				// ..or actually not used: remove it.					
				boolean actuallyUsed = actuallyUsedEPackages.contains(currentEPackage);
				if( subsOfMain.contains(currentEPackage) || !actuallyUsed) {
					itImports.remove();
				}				
			}		
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
	
	private void mainUnitCreation(Module module, EClassifier eClassifier, OperationType tsType) {
		
		removeAllNonRuleUnits(module);
		List<Rule> rulesUnderModule = HenshinRuleAnalysisUtilEx.getRulesUnderModule(module);
		
		/** Unit creation *************************************************/	
		PriorityUnit prioUnit = henshinFactory.createPriorityUnit();
		prioUnit.setActivated(true);
		prioUnit.setName("mainUnit");		
				
		/** Parameter and Mapping creation ********************************/
		
		// In case of DELETE module, remove unnecessary parameters
		if(tsType==OperationType.DELETE) {
			removeUnnecessaryParametersForDELETE(module, prioUnit);
		}
		
		// Create the mandatory "selectedEObject"-Parameter with type information
		Parameter selectedEObjectParameter = henshinFactory.createParameter(SELEO);
		Node selectedEObjectNode = HenshinRuleAnalysisUtilEx.getNodeByName(rulesUnderModule.get(0), SEL, true);
		selectedEObjectParameter.setType(selectedEObjectNode.getType());
		prioUnit.getParameters().add(selectedEObjectParameter);
			
		for(Rule rule: rulesUnderModule) {
					
			//we only need to consider RHS (it covers <<preserved>> and <<create>> Nodes/Attributes)
			//Since <<delete>> Node Parameters are renamed <<create>> Node Parameters and therefore
			//we don't have to check LHS here. Also because <<delete>> Attributes never appear
			//(even not in UNSETs because they only revert Attribute values to Default, if any).
			for(Node nInRHS : rule.getRhs().getNodes()) {
				String nodeName = nInRHS.getName();
				EClass nodeType = nInRHS.getType();
				/** Add Parameter for RHS Nodes ********************************/
				if(nodeName!=null && !nodeName.equals("")) {
					// ..to rule
					Parameter pForRule = henshinFactory.createParameter(nodeName);
					pForRule.setType(nodeType);
					if(HenshinRuleAnalysisUtilEx.getParameterByName(rule, nodeName)==null) {
						pForRule.setUnit(rule);
						rule.getParameters().add(pForRule);
						// ..to unit
						if(!pForRule.getName().equals(SEL)
								&& HenshinRuleAnalysisUtilEx.getParameterByName(prioUnit, pForRule.getName())==null) {
							Parameter pForUnit = henshinFactory.createParameter(nodeName);
							pForUnit.setType(nodeType);
							prioUnit.getParameters().add(pForUnit);
						}
					}
				}
				
				/** Add Parameter for RHS Attributes ***************************/
				for(Attribute a: nInRHS.getAttributes()) {
					Object defaultValue = a.getType().getDefaultValue();
					String defaultValueName = null;
					if(defaultValue!=null) {
						defaultValueName = defaultValue.toString();
					}
					
					//Don't create Parameter if:
					// attribute is in quotation marks "..." (like specific literal values, e.g. "initial").
					// Else create Parameter.
					if((a.getValue()!="null" 
							&& !a.getValue().startsWith("\"") 
							&& ((defaultValueName!=null && !a.getValue().equals(defaultValueName))
							|| defaultValueName==null))) {
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
				if(p.getName().equals(SEL)) {
					ParameterMapping selEObjectMapping = henshinFactory.createParameterMapping();
					selEObjectMapping.setSource(selectedEObjectParameter);
					selEObjectMapping.setTarget(p);			
					prioUnit.getParameterMappings().add(selEObjectMapping);
				}
				// == selected element is the toBeDeleted (in case there is no context to delete from)
				else if(HenshinRuleAnalysisUtilEx.getParameterByName(rule, SEL)==null && p.getName().equals(DEL)) {
					ParameterMapping selEObjectMapping = henshinFactory.createParameterMapping();
					selEObjectMapping.setSource(selectedEObjectParameter);
					selEObjectMapping.setTarget(p);			
					prioUnit.getParameterMappings().add(selEObjectMapping);
				}
				// == new / out-parameter
				else if(p.getName().matches(NEW+"[0-9]*") || (rule.getName().startsWith("create") && p.getName().matches(CHILD+"[0-9]*"))) {
					ParameterMapping pm = henshinFactory.createParameterMapping();
					pm.setSource(p);
					pm.setTarget(HenshinRuleAnalysisUtilEx.getParameterByName(prioUnit, p.getName()));
					if(!prioUnit.getParameterMappings().contains(pm)) {
						prioUnit.getParameterMappings().add(pm);
					}
				}
				else if(p.getName().matches(NEW+"[0-9]*") || (rule.getName().startsWith("delete") && p.getName().matches(CHILD+"[0-9]*"))) {
					ParameterMapping pm = henshinFactory.createParameterMapping();
					pm.setSource(HenshinRuleAnalysisUtilEx.getParameterByName(prioUnit, p.getName()));
					pm.setTarget(p);
					if(!prioUnit.getParameterMappings().contains(pm)) {
						prioUnit.getParameterMappings().add(pm);
					}
				}
				
				else if(p.getName().matches(NEWTGT+"[0-9]*")|| p.getName().matches(NEWSRC+"[0-9]*")) {
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
	private void createMandatoryChildren(Rule rule, EClassifierInfo eClassInfo, Node eClassNode) {
			
		for(Entry<EReference,List<EClassifier>> childEntry: eClassInfo.getMandatoryChildren().entrySet()) {
			List<EClassifier> children = childEntry.getValue();
			EReference eRef = childEntry.getKey();
			
			for(EClassifier child: children) {

				if (!isAllowed(child,false,false))  continue;
				
				for(int i=0; i<eRef.getLowerBound();i++) {

					Node newChildNode = null;
					String name = getFreeNodeName(CHILD, rule);
					// create node for mandatory child
					newChildNode = HenshinRuleAnalysisUtilEx.createCreateNode(rule.getRhs(), name, (EClass) child);				
					// create edge for mandatory child
					HenshinRuleAnalysisUtilEx.createCreateEdge(eClassNode, newChildNode, eRef);
					// Add necessary attributes to the new eClass node
					createAttributes((EClass) child, newChildNode, rule);					
					// recursively check for child's mandatories and create them
					if(ecm.getEClassifierInfo(child).hasMandatories()) {
						createMandatoryChildren(rule, ecm.getEClassifierInfo(child), newChildNode);
						createMandatoryNeighbours(rule, ecm.getEClassifierInfo(child), newChildNode);
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
	private void createMandatoryNeighbours(Rule rule, EClassifierInfo eClassInfo, Node eClassNode) {

		for(Entry<EReference,List<EClassifier>> neighbourEntry: eClassInfo.getMandatoryNeighbours().entrySet()) {
			EReference eRef = neighbourEntry.getKey();
			EReference eOpposite = eRef.getEOpposite();
			List<EClassifier> neighbours = neighbourEntry.getValue();
			
			for(EClassifier neighbour: neighbours) {

				if (!isAllowed(neighbour,false,false))  continue;
				
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
						String existingName = getFreeNodeName(EX,rule);
						NodePair preservedNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, existingName, (EClass) neighbour);
						newNeighbourNode = preservedNodePair.getRhsNode();		
						// create edge for mandatory neighbour
						HenshinRuleAnalysisUtilEx.createPreservedEdge(rule, eClassNode, newNeighbourNode, eRef);						
						// create edge for eOpposite, if any
						if(eOpposite!=null) {
							HenshinRuleAnalysisUtilEx.createPreservedEdge(rule,newNeighbourNode, eClassNode, eOpposite);
						}
						// recursively check neighbour's mandatories and create them
						createMandatoryChildren(rule, ecm.getEClassifierInfo(neighbour), newNeighbourNode);
						createMandatoryNeighbours(rule, ecm.getEClassifierInfo(neighbour), newNeighbourNode);

					}
				}
			}
		}
	}
	
	
	private void createInitialChecksForMultiplicities(String moduleName, EClassifier parentOrNeighbourA, EClassifier eClassifier, EReference eRefA, EClassifier parentOrNeighbourB, EReference eRefB, OperationType operationType) {
		
		// Return on CHANGE, since there are no initials needed
		if(operationType==OperationType.CHANGE) { return; }
		
		boolean initialCreated = false;
		
		// Create file name and Module
		String outputFileName = outputFolderPath + moduleName + INITIALCHECK_suffix;		
		Module module = henshinFactory.createModule();
		module.setName(moduleName);

		// Add imports for meta model
		for(EPackage epackage: ePackagesStack) {
			module.getImports().add(epackage);
		}
		
		// x..* : minimum
		if(eRefA.getLowerBound()!=0 && eRefA.getUpperBound()==-1 && (operationType==OperationType.DELETE || operationType==OperationType.REMOVE || operationType==OperationType.MOVE)) {
			
			LogUtil.log(LogEvent.NOTICE, "Generating INITIAL: " + moduleName);
			
			// create rule
			Rule rule = HenshinRuleAnalysisUtilEx.createRule("doNotFallBelowLowerBound", "doNotFallBelowLowerBound", true, module);
			
			switch(operationType) {
				case DELETE: case REMOVE:
				{
					// create Node(-Pair) for <<preserved>> parentOrNeighbour
					NodePair parentOrNeighbourNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, SEL, (EClass) parentOrNeighbourA);
					Node parentOrNeighbourNode = parentOrNeighbourNodePair.getRhsNode();
					
					for(int i=0; i<eRefA.getLowerBound(); i++) {
						// create <<preserved>> node, that must exist at the minimum
						NodePair np = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, "", (EClass) eClassifier);
						HenshinRuleAnalysisUtilEx.createPreservedEdge(rule, parentOrNeighbourNodePair, np, eRefA);	
					}
					
					// create <<forbid>> nodes: these node MAY NOT ONLY exist when doing a delete/remove from this parentOrNeighborNode
					Node n = HenshinRuleAnalysisUtilEx.createForbidNode(rule, (EClass) eClassifier);
					HenshinRuleAnalysisUtilEx.createForbidEdge(parentOrNeighbourNode, n, eRefA, rule);	
					
					// finally add rule
					module.getUnits().add(rule);
					initialCreated = true;
				};break;
				case MOVE:
				{
					// create Node(-Pair) for <<preserved>> parentOrNeighbour
					NodePair parentOrNeighbourNodePairA = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, OLDSRC, (EClass) parentOrNeighbourA);
					Node parentOrNeighbourNode = parentOrNeighbourNodePairA.getRhsNode();
					
					for(int i=0; i<eRefA.getLowerBound(); i++) {
						// create <<preserved>> node, that must exist at the minimum
						NodePair np = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, "", (EClass) eClassifier);
						HenshinRuleAnalysisUtilEx.createPreservedEdge(rule, parentOrNeighbourNodePairA, np, eRefA);	
					}
					
					// create <<forbid>> nodes: these node MAY NOT ONLY exist when doing a delete/remove from this parentOrNeighborNode
					Node n = HenshinRuleAnalysisUtilEx.createForbidNode(rule, (EClass) eClassifier);
					HenshinRuleAnalysisUtilEx.createForbidEdge(parentOrNeighbourNode, n, eRefA, rule);	
					
					// finally add rule
					module.getUnits().add(rule);
					initialCreated = true;
				};break;
			default:
				break;					
			}
		}
		// 0..y : maximum
		else if(eRefA.getLowerBound()==0 && eRefA.getUpperBound()!=-1 && (operationType==OperationType.CREATE || operationType==OperationType.ADD || operationType==OperationType.MOVE)) {
			
			LogUtil.log(LogEvent.NOTICE, "Generating INITIAL: " + moduleName);
			
			// create rule
			Rule rule = HenshinRuleAnalysisUtilEx.createRule("doNotExceedUpperBound", "doNotExceedUpperBound", true, module);
			
			switch(operationType) {
				case CREATE: case ADD:
				{
					// create NodePair for <<preserved>> parentOrNeighbour
					NodePair parentOrNeighbourNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, SEL, (EClass) parentOrNeighbourA);
					
					// create <<preserved>> nodes: these nodes MAY NOT ALREADY exist when doing a create/add to this parentOrNeighborNode
					for(int i=0; i<eRefA.getUpperBound(); i++) {
						NodePair np = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, "", (EClass) eClassifier);
						HenshinRuleAnalysisUtilEx.createPreservedEdge(rule, parentOrNeighbourNodePair, np, eRefA);	
					}
					// finally add rule
					module.getUnits().add(rule);
					initialCreated = true;
				};break;
				case MOVE:
				{
					// create Node(-Pair) for <<preserved>> parentOrNeighbour
					NodePair parentOrNeighbourNodePairB = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, NEWSRC, (EClass) parentOrNeighbourB);
					
					// create <<forbid>> nodes: these nodes MAY NOT ALREADY exist when doing a move to this parentOrNeighborNodeB
					for(int i=0; i<eRefB.getUpperBound(); i++) {
						NodePair np = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, "", (EClass) eClassifier);
						HenshinRuleAnalysisUtilEx.createPreservedEdge(rule, parentOrNeighbourNodePairB, np, eRefB);	
					}
					// finally add rule
					module.getUnits().add(rule);
					initialCreated = true;
				};break;
			default:
				break;
			}
		}
		// x..y : minimum and maximum
		else if(eRefA.getLowerBound()!=0 && eRefA.getUpperBound()!=-1) {
			
			LogUtil.log(LogEvent.NOTICE, "Generating INITIAL: " + moduleName);
			
			// create rule
			Rule rule = HenshinRuleAnalysisUtilEx.createRule("doNotIgnoreBounds", "doNotIgnoreBounds", true, module);

			switch(operationType) {
				case DELETE: case REMOVE:
				{
					// create Node(-Pair) for <<preserved>> parentOrNeighbour
					NodePair parentOrNeighbourNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, SEL, (EClass) parentOrNeighbourA);
					Node parentOrNeighbourNode = parentOrNeighbourNodePair.getRhsNode();
	
					for(int i=0; i<eRefA.getLowerBound(); i++) {
						// create <<preserved>> node, that must exist at the minimum
						NodePair np = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, "", (EClass) eClassifier);
						HenshinRuleAnalysisUtilEx.createPreservedEdge(rule, parentOrNeighbourNodePair, np, eRefA);	
					}
					
					// create <<forbid>> nodes: these node MAY NOT ONLY exist when doing a delete/remove from this parentOrNeighborNode
					Node n = HenshinRuleAnalysisUtilEx.createForbidNode(rule, (EClass) eClassifier);
					HenshinRuleAnalysisUtilEx.createForbidEdge(parentOrNeighbourNode, n, eRefA, rule);
					
					// finally add rule
					module.getUnits().add(rule);
					initialCreated = true;
				};break;
				case CREATE: case ADD:
				{
					// create NodePair for <<preserved>> parentOrNeighbour
					NodePair parentOrNeighbourNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, SEL, (EClass) parentOrNeighbourA);
					
					// create <<preserved>> nodes: these nodes MAY NOT ALREADY exist when doing a create/add to this parentOrNeighborNode
					for(int i=0; i<eRefA.getUpperBound(); i++) {
						NodePair np = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, "", (EClass) eClassifier);
						HenshinRuleAnalysisUtilEx.createPreservedEdge(rule, parentOrNeighbourNodePair, np, eRefA);	
					}
					// finally add rule
					module.getUnits().add(rule);
					initialCreated = true;
				};break;
				case MOVE:
				{
					// create precondition for lowerBound --------------------------------------------------------------------------/
					// create Node(-Pair) for <<preserved>> parentOrNeighbour for OldSource
					NodePair parentOrNeighbourNodePair_OLD = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, OLDSRC, (EClass) parentOrNeighbourA);
	
					for(int i=0; i<eRefA.getLowerBound(); i++) {
						// create <<preserved>> node, that must exist at the minimum
						NodePair np = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, "", (EClass) eClassifier);
						HenshinRuleAnalysisUtilEx.createPreservedEdge(rule, parentOrNeighbourNodePair_OLD, np, eRefA);	
					}
					
					// create <<forbid>> nodes: these node MAY NOT ONLY exist when doing a delete/remove from this parentOrNeighborNode
					Node n = HenshinRuleAnalysisUtilEx.createForbidNode(rule, (EClass) eClassifier);
					HenshinRuleAnalysisUtilEx.createForbidEdge(parentOrNeighbourNodePair_OLD.getRhsNode(), n, eRefA, rule);	
					
					// create precondition for upperBound --------------------------------------------------------------------------/
					// create Node(-Pair) for <<preserved>> parentOrNeighbour for NewSource
					NodePair parentOrNeighbourNodePair_NEW = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, NEWSRC, (EClass) parentOrNeighbourA);
					
					// create <<forbid>> nodes: these nodes MAY NOT ALREADY exist when doing a move to this parentOrNeighborNode
					for(int i=0; i<eRefA.getUpperBound(); i++) {
						NodePair np = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, "", (EClass) eClassifier);
						HenshinRuleAnalysisUtilEx.createPreservedEdge(rule, parentOrNeighbourNodePair_NEW, np, eRefA);	
					}
			
					// finally add rule
					module.getUnits().add(rule);
					initialCreated = true;
				};break;
			default:
				break;
			}
		}

		// now create mainUnit and serialize if initialCreated == true
		if(initialCreated) {
			mainUnitCreation(module, eClassifier, null); //null -> initial
			serialize(module, outputFileName);
		}else{
			return;
		}
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
			HenshinRuleAnalysisUtilEx.getNodeByName(firstRule,NEWTGT,true).setName(OLDTGT);  //rename Node in LHS
			HenshinRuleAnalysisUtilEx.getNodeByName(firstRule,NEWTGT,false).setName(OLDTGT); //rename Node in RHS
		}
		else if(module.getName().startsWith(SET_prefix)) {
			name = module.getName().replaceFirst(SET_prefix, UNSET_prefix);
			description = module.getDescription().replaceFirst("Sets","Unsets");
			inverse =  HenshinRuleAnalysisUtilEx.createInverse(name,description,module);
			Rule firstRule = HenshinRuleAnalysisUtilEx.getRulesUnderModule(inverse).get(0);
			firstRule.setName(firstRule.getName().replaceFirst("set", "unset"));
			firstRule.setDescription(firstRule.getDescription().replaceFirst("Set", "Unset"));
			HenshinRuleAnalysisUtilEx.getNodeByName(firstRule, NEWTGT,true).setName(OLDTGT);  //rename Node in LHS
			HenshinRuleAnalysisUtilEx.getNodeByName(firstRule,NEWTGT,false).setName(OLDTGT); //rename Node in RHS
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

	
	private HashMap<Module,String> createUNSET(Module SET_Module, EClassifier eClassifier, EAttribute ea, String outputFileName, HashMap<Module,String> moduleMap) {
		
		// UNSET for EAttributes *************************************************************************/
		LogUtil.log(LogEvent.NOTICE, "Generating UNSET : " + eClassifier.getName() + " attribute "+ ea.getName());

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
			return moduleMap;
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
		removeAllNonRuleUnits(UNSET_Module);
		HenshinRuleAnalysisUtilEx.getRulesUnderModule(UNSET_Module).get(0).getParameters().clear(); //remove parameters that came from inverse
		mainUnitCreation(UNSET_Module, eClassifier, OperationType.UNSET);
		moduleMap.put(UNSET_Module, outputFileNameUNSET);

		return moduleMap;

	}
	
	
	
	private String getFreeNodeName(String originalName, Rule rule) {

		originalName = Common.toCamelCase(originalName);

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

		originalName = Common.toCamelCase(originalName);

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

				if( createNotRequiredAndNotIDAttributes ||
					!createNotRequiredAndNotIDAttributes && (ea.isRequired() || ea.isID())){
					HenshinRuleAnalysisUtilEx.createCreateAttribute(
							inEClassNode, ea,Common.toCamelCase(getFreeAttributeName(eaName, rule))
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


				ArrayList<EClassifier> replacements = ecm.getAllConcreteEClassifiersForAbstract(typeOfReplacable);
				
				for(EClassifier replacement: replacements) {
					
					if (!isAllowed(replacement,false,false))  continue;
					
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
							copyNode.setType((EClass) replacement);
							
							// create mandatories for replacement, if any
							createMandatoryChildren((Rule)copyNode.getGraph().eContainer(),
														ecm.getEClassifierInfo(replacement),
														copyNode);
							createMandatoryNeighbours((Rule)copyNode.getGraph().eContainer(),
														ecm.getEClassifierInfo(replacement),
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
	
	
	private HashMap<Module,String> createModuleToModifyReference(EReference eRef, EClassifier eClassifier, EClass target) throws ConstraintException {
		
		HashMap<Module,String> map = new HashMap<Module,String>();
		
		int lower = eRef.getLowerBound();
		int upper = eRef.getUpperBound();
		
		// eRef == no containment reference  *************************************************************/	
		if(!eRef.isContainment()) {

			//	(0..1)	->SET/UNSET ******************************************************************************************/
			if(lower==0 && upper==1 && createSETS) {
				
				// only continue, if ref is inherited and no reduction to super type is wished
				// or ref is not inherited
				if((isInheritedReference(eRef, eClassifier) && !reduceToSuperType_SETUNSET) || !isInheritedReference(eRef, eClassifier)) {

					// SET *******************************************************************************************************/
					String name = SET_prefix + eClassifier.getName() + "_(" + eRef.getName()+ ")"+TGT+target.getName(); 
					LogUtil.log(LogEvent.NOTICE, "Generating SET : " + name);

					// set file name
					String outputFileName = outputFolderPath + name+ EXECUTE_suffix;
					String outputFileNameUnset = outputFileName.replace(SET_prefix, UNSET_prefix);

					Module SET_Module = henshinFactory.createModule();
					SET_Module.setName(name);

					SET_Module.setDescription("Sets "+eClassifier.getName()+"'s reference "+eRef.getName()+" the target "+target.getName());

					// add imports
					SET_Module.getImports().addAll(ePackagesStack);

					// create rule
					createBasicRule(SET_Module, eRef, eClassifier, target, null, null);

					if(createUNSETS) {
						// UNSET *****************************************************************************************************/
						Module UNSET_Module = createInverse(SET_Module);

						// create mainUnit for UNSET and put in map
						mainUnitCreation(UNSET_Module, eClassifier, OperationType.UNSET);
						map.put(UNSET_Module, outputFileNameUnset);
					}
					// create mainUnit for SET and put in map
					mainUnitCreation(SET_Module, eClassifier, OperationType.SET);
					map.put(SET_Module, outputFileName);
				}
			}
			//	(..*) or (x..y)	->ADD/REMOVE ********************************************************************************/
			else if((upper==-1 || upper-lower>0) && createADDS) {

				// only continue, if ref is inherited and no reduction to super type is wished
				// or ref is not inherited
				if((isInheritedReference(eRef, eClassifier) && !reduceToSuperType_ADDREMOVE) || !isInheritedReference(eRef, eClassifier)) {

					String name = ADD_prefix + eClassifier.getName() + "_(" + eRef.getName()+")"+TGT+target.getName(); 
					LogUtil.log(LogEvent.NOTICE, "Generating ADD : " + name);

					// set file name
					String outputFileName 		= outputFolderPath + name+ EXECUTE_suffix;
					String outputFileNameRemove = outputFileName.replace(ADD_prefix, REMOVE_prefix);

					Module ADD_Module = henshinFactory.createModule();
					ADD_Module.setName(name);

					ADD_Module.setDescription("Adds to "+eClassifier.getName() +"'s reference "+ eRef.getName()
							+ " the target "+ target.getName());

					// add imports
					ADD_Module.getImports().addAll(ePackagesStack);

					// create rule
					createBasicRule(ADD_Module, eRef, eClassifier, target, null, null);

					if(createREMOVES) {
						// REMOVE **************************************************************************************************/				
						Module REMOVE_Module = createInverse(ADD_Module);
						LogUtil.log(LogEvent.NOTICE, "Generating REMOVE : " + REMOVE_Module.getName());

						// create mainUnits and put in map
						mainUnitCreation(REMOVE_Module, eClassifier, OperationType.REMOVE);
						map.put(REMOVE_Module, outputFileNameRemove);

						// create multiplicity preconditions, if any
						if(multiplicityPreconditionsIntegrated) {
							Rule rule = HenshinRuleAnalysisUtilEx.getRulesUnderModule(REMOVE_Module).get(0);
							createIntegratedPreconditionsForMultiplicities(rule, OperationType.REMOVE);
						}
						if(multiplicityPreconditionsSeparately) {
							createInitialChecksForMultiplicities(REMOVE_Module.getName(), eClassifier, target, eRef, null, null, OperationType.REMOVE);							
						}
					}
					// create mainUnits
					mainUnitCreation(ADD_Module, eClassifier, OperationType.ADD);
					
					// create multiplicity preconditions, if any
					if(multiplicityPreconditionsIntegrated) {
						Rule rule = HenshinRuleAnalysisUtilEx.getRulesUnderModule(ADD_Module).get(0);
						createIntegratedPreconditionsForMultiplicities(rule, OperationType.ADD);
					}
					if(multiplicityPreconditionsSeparately) {
						createInitialChecksForMultiplicities(ADD_Module.getName(), eClassifier, target, eRef, null, null, OperationType.ADD);						
					}
					
					// put module in map for later serialization
					map.put(ADD_Module, outputFileName);
				}
			}
			//	(x..x)	-> CHANGE *******************************************************************************************/
			else if(upper==lower && createCHANGES) {

				// only continue, if ref is inherited and no reduction to super type is wished
				// or ref is not inherited
				if((isInheritedReference(eRef, eClassifier) && !reduceToSuperType_CHANGE) || !isInheritedReference(eRef, eClassifier)) {	
					
					String name = CHANGE_prefix + eClassifier.getName() + "_(" + eRef.getName()+ ")" + TGT+target.getName(); 
					LogUtil.log(LogEvent.NOTICE, "Generating CHANGE : " + name);

					// CHANGE file name
					String outputFileName = outputFolderPath + name+ EXECUTE_suffix;

					Module CHANGE_Module = henshinFactory.createModule();
					CHANGE_Module.setName(name);

					CHANGE_Module.setDescription("CHANGEs "+eClassifier.getName() +"'s reference "+ eRef.getName() + " the target "+ target.getName());

					// add imports
					CHANGE_Module.getImports().addAll(ePackagesStack);

					// create rule
					createBasicRule(CHANGE_Module, eRef, eClassifier, target, null, null);

					// create mainUnit and put in map
					mainUnitCreation(CHANGE_Module, eClassifier, OperationType.CHANGE);
					map.put(CHANGE_Module, outputFileName);
				}
			}
		}
		
		return map;
	}
	
	private HashMap<Module,String> create_single_MOVE(EClassifier eClassifier, EReference eRefA, EClass contextA, EReference eRefB, EClass contextB) {
		
		HashMap<Module,String> map = new HashMap<Module,String>();
		
		String name = MOVE_prefix + eClassifier.getName() + FROM + contextA.getName()+"_("+eRefA.getName()+")"+ TO+contextB.getName()+"_("+eRefB.getName()+")"; 
		LogUtil.log(LogEvent.NOTICE, "Generating Move : " + name);

		// MOVE file name
		String outputFileName = outputFolderPath + name+ EXECUTE_suffix;

		Module MOVE_Module = henshinFactory.createModule();
		MOVE_Module.setName(name);

		MOVE_Module.setDescription("MOVEs "+eClassifier.getName() + " from " + contextA.getName()+"(Reference:"+eRefA.getName()+")"+ " to "+contextB.getName()+"(Reference:"+eRefB.getName());

		// add imports
		MOVE_Module.getImports().addAll(ePackagesStack);
		
		// create rule
		createBasicRule(MOVE_Module, eRefA, eClassifier, contextA, eRefB, contextB);

		// create all elements necessary for constraints
		createElementsForConstraints_MOVE(MOVE_Module);
		
		// create mainUnit and put in map
		mainUnitCreation(MOVE_Module, eClassifier, OperationType.MOVE);
		map.put(MOVE_Module, outputFileName);
		
		// create multiplicity preconditions, if any
		if(multiplicityPreconditionsIntegrated) {
			Rule rule = HenshinRuleAnalysisUtilEx.getRulesUnderModule(MOVE_Module).get(0);
			createIntegratedPreconditionsForMultiplicities(rule, OperationType.MOVE);
		}
		if(multiplicityPreconditionsSeparately) {
			createInitialChecksForMultiplicities(MOVE_Module.getName(), contextA, eClassifier, eRefA, contextB, eRefB, OperationType.MOVE);	
		}
		
		// if EClass has Masks, also create MOVES for them
		for(Mask mask: ecm.getEClassifierInfo(eClassifier).getMasks()) {				
			map.putAll(createMOVE_UsingMask(eRefA, mask, contextA, eRefB, contextB));			
		}
		
		return map;
		
	}

	private HashMap<Module,String> createMOVE_UsingMask(EReference eRefA, Mask mask, EClass contextA, EReference eRefB, EClass contextB) {
		
		HashMap<Module,String> map = new HashMap<Module,String>();
		
		String name = MOVE_prefix + mask.getName() + FROM+contextA.getName()+"_(" + eRefA.getName()+ ")"+ TO+contextB.getName()+"_("+eRefB.getName()+")"; 
		LogUtil.log(LogEvent.NOTICE, "Generating Move : " + name);

		// MOVE file name
		String outputFileName = outputFolderPath + name+ EXECUTE_suffix;

		Module MOVE_Module = henshinFactory.createModule();
		MOVE_Module.setName(name);

		MOVE_Module.setDescription("Moves "+ mask.getName() + " from "+contextA.getName()+"(Reference:" + eRefA.getName()+ ") to "+contextB.getName()+("(Reference:"+eRefB.getName()+")"));

		// add imports
		MOVE_Module.getImports().addAll(ePackagesStack);
		
		// create rule
		createBasicRule(MOVE_Module, eRefA, mask.getOriginalEClassifier(), contextA, eRefB, contextB);
		
		// create Attribute, containing the masked type
		Rule rule = HenshinRuleAnalysisUtilEx.getRulesUnderModule(MOVE_Module).get(0);
		NodePair np = HenshinRuleAnalysisUtilEx.getNodePair(rule, (EClass)mask.getOriginalEClassifier(), SEL);
		HenshinRuleAnalysisUtilEx.createPreservedAttribute(np, mask.getEAttributeContainingFakeType(), "\""+mask.getTypeExtension().getName()+"\"", false);

		// create all elements necessary for constraints
		createElementsForConstraints_MOVE(MOVE_Module);
		
		// create mainUnit and put in map
		mainUnitCreation(MOVE_Module, mask.getOriginalEClassifier(), OperationType.MOVE);
		map.put(MOVE_Module, outputFileName);
		
		// create multiplicity preconditions, if any
		if(multiplicityPreconditionsIntegrated) {
			createIntegratedPreconditionsForMultiplicities(rule, OperationType.MOVE);
		}
		if(multiplicityPreconditionsSeparately) {
			createInitialChecksForMultiplicities(MOVE_Module.getName(), contextA, mask.getOriginalEClassifier(), eRefA, contextB, eRefB, OperationType.MOVE);	
			//TODO separate initial check for masked eclass
		}
		
		return map;
	}
	
	private void createIntegratedPreconditionsForMultiplicities(Rule rule, OperationType opType) {
		
		
		if(opType==OperationType.MOVE) {
		
			/*** Find relevant elements in rule ************************************************************/
			Node selectedNodeLHS = HenshinRuleAnalysisUtilEx.getNodeByName(rule, SEL, true);
			Node selectedNodeRHS = HenshinRuleAnalysisUtilEx.getNodeByName(rule, SEL, false);
			Node oldContextNodeLHS = null;
			Node newContextNodeLHS = null;
			
			EReference eRefOfOldSource = null;
			EReference eRefOfNewSource = null;
			
			// get EReference from old context to selected node
			for(Edge inEdge: selectedNodeLHS.getIncoming()) {
				if(inEdge.getType().isContainment() && HenshinRuleAnalysisUtilEx.isDeletionEdge(inEdge)) {
					eRefOfOldSource = inEdge.getType();
					oldContextNodeLHS = inEdge.getSource();
					
				}
			}
			// get EReference from new context to selected node
			for(Edge inEdge: selectedNodeRHS.getIncoming()) {
				if(inEdge.getType().isContainment() && HenshinRuleAnalysisUtilEx.isCreationEdge(inEdge)) {
					eRefOfNewSource = inEdge.getType();
					Node newContextNodeRHS = inEdge.getSource();
					newContextNodeLHS = rule.getAllMappings().getOrigin(newContextNodeRHS);
				}
			}			


			/*** Differentiate multiplicity cases **********************************************************/
			
			// Concerning <<delete>> Edge: Ensure minimum must be contained if lowerBound is greater zero [x..]
			if(eRefOfOldSource.getLowerBound()!=0) {
				
				createLowerBoundConstrainedElements(rule, oldContextNodeLHS, selectedNodeLHS, eRefOfOldSource);
			}
			// Concerning <<create>> Edge: Ensure maximum must not be surpassed if upperBound is not infinite [..y]
			if(eRefOfNewSource.getUpperBound()!=-1) {
				
				createUpperBoundConstrainedElements(rule, newContextNodeLHS, selectedNodeLHS, eRefOfNewSource);
				
			}
			
		
			
		}
		else if(opType==OperationType.CREATE) {		
			
			/*** Find relevant elements in rule ************************************************************/
			Node selectedNodeRHS = HenshinRuleAnalysisUtilEx.getNodeByName(rule, SEL, false);
			Node newNodeLHS = null;
			
			EReference eRefOfContext = null;
			
			// get EReference from new context to new node
			for(Edge outEdge: selectedNodeRHS.getOutgoing()) {
				if(outEdge.getType().isContainment() && HenshinRuleAnalysisUtilEx.isCreationEdge(outEdge)) {
					eRefOfContext = outEdge.getType();
					newNodeLHS = outEdge.getTarget();
				}
			}		

			/*** Differentiate multiplicity cases **********************************************************/
			
			// Concerning <<create>> Edge: Ensure maximum must not be surpassed if upperBound is not infinite [..y]
			if(eRefOfContext.getUpperBound()!=-1) {
				
				createUpperBoundConstrainedElements(rule, selectedNodeRHS, newNodeLHS, eRefOfContext);
				
			}
			
		}
		else if(opType==OperationType.ADD) {
				
			/*** Find relevant elements in rule ************************************************************/
			
			for(Edge creationEdge: HenshinRuleAnalysisUtilEx.getRHSMinusLHSEdges(rule)) {
			
				Node sourceNodeRHS = creationEdge.getSource();				
				EReference eRef = creationEdge.getType();
				Node targetNodeRHS = creationEdge.getTarget();			
	
				/*** Differentiate multiplicity cases **********************************************************/
				
				// Concerning <<create>> Edge: Ensure maximum is not already contained if upperBound is greater zero [..y]
				if(eRef.getUpperBound()!=-1) {
					
					Node sourceNodeLHS = rule.getMappings().getOrigin(sourceNodeRHS);
					Node targetNodeLHS = rule.getMappings().getOrigin(targetNodeRHS);
					createUpperBoundConstrainedElements(rule, sourceNodeLHS, targetNodeLHS, eRef);
				}	
			
			}
			
		}
		/******* INVERSES *********************************************************************************************/
		else if(opType==OperationType.DELETE) {		
			
			/*** Find relevant elements in rule ************************************************************/
			
			for(Node deletionNode: HenshinRuleAnalysisUtilEx.getLHSMinusRHSNodes(rule)) {
			
				Node oldContextNodeLHS = null;				
				EReference eRefOfOldSource = null;
				
				// get EReference from old context to selected node
				for(Edge inEdge: deletionNode.getIncoming()) {
					if(inEdge.getType().isContainment()) {
						eRefOfOldSource = inEdge.getType();
						oldContextNodeLHS = inEdge.getSource();					
					}
				}
	
				/*** Differentiate multiplicity cases **********************************************************/
				
				// Concerning <<delete>> Edge: Ensure minimum must be contained if lowerBound is greater zero [x..]
				if(eRefOfOldSource.getLowerBound()!=0) {
					
					createLowerBoundConstrainedElements(rule, oldContextNodeLHS, deletionNode, eRefOfOldSource);
				}	
			
			}

		}
		else if(opType==OperationType.REMOVE) {
			
			/*** Find relevant elements in rule ************************************************************/
			
			for(Edge deletionEdge: HenshinRuleAnalysisUtilEx.getLHSMinusRHSEdges(rule)) {
			
				Node oldSourceNodeLHS = deletionEdge.getSource();				
				EReference eRef = deletionEdge.getType();
				Node oldTargetNodeLHS = deletionEdge.getTarget();			
	
				/*** Differentiate multiplicity cases **********************************************************/
				
				// Concerning <<delete>> Edge: Ensure minimum must be contained if lowerBound is greater zero [x..]
				if(eRef.getLowerBound()!=0) {
					
					createLowerBoundConstrainedElements(rule, oldSourceNodeLHS, oldTargetNodeLHS, eRef);
				}	
			
			}
		}
		//CHANGE does not need any multiplicity constraints.
	}

	private void createLowerBoundConstrainedElements(Rule rule, Node oldContextNodeLHS, Node targetNodeLHS, EReference eRefOfOldSource) {
		
		//TODO recursively for all contained <<delete>> nodes
		
		Integer numberOfRequiredNodes = eRefOfOldSource.getLowerBound();		
		NodePair contextNodePair = new NodePair(oldContextNodeLHS, rule.getMappings().getImage(oldContextNodeLHS, rule.getRhs()));
		
		if(numberOfRequiredNodes!=0) {
		
			NestedCondition nestedCondition = henshinFactory.createNestedCondition();
			Graph conclusion = henshinFactory.createGraph("sufficientElementsMustExist");
			
			// copy contextNode from base graph in conclusion			
			Node copiedContextNode = EcoreUtil.copy(oldContextNodeLHS);
			conclusion.getNodes().add(copiedContextNode);
			
			// copy targetNode from base graph in conclusion
			Node copiedTargetNode = EcoreUtil.copy(targetNodeLHS);
			conclusion.getNodes().add(copiedTargetNode);
			
			
			//There must be at least numberOfRequiredNodes+1 Nodes for a <<delete>> to be executed
			for(int i=1; i<=numberOfRequiredNodes+1; i++) {
				
				Node requiredNode = henshinFactory.createNode();
				requiredNode.setType(targetNodeLHS.getType());

				Edge requiredEdge = henshinFactory.createEdge(copiedContextNode, requiredNode, eRefOfOldSource);
				conclusion.getNodes().add(requiredNode);
				conclusion.getEdges().add(requiredEdge);
				
			}
			
			nestedCondition.setConclusion(conclusion);		
			
			// add mapping in nestedCondition between
			// base graph contextNode and copied contextNode
			// and the same for targetNodes inside the conclusion
			Mapping contextNodeMapping = henshinFactory.createMapping(oldContextNodeLHS, copiedContextNode);
			nestedCondition.getMappings().add(contextNodeMapping);
			Mapping targetNodeMapping = henshinFactory.createMapping(targetNodeLHS, copiedTargetNode);
			nestedCondition.getMappings().add(targetNodeMapping);
			
			
			// put Formulas together
			HenshinRuleAnalysisUtilEx.addFormula(nestedCondition, rule.getLhs(), FormulaCombineOperator.AND);
		}
		
	}
	
	private void createUpperBoundConstrainedElements(Rule rule, Node newContextNodeLHS, Node targetNodeLHS, EReference eRefOfNewSource) {
		
		//TODO recursively for all contained <<create>> nodes
		
		Integer numberOfMaximumNodes = eRefOfNewSource.getUpperBound();	
	
		if(numberOfMaximumNodes!=-1) {

			UnaryFormula newNOTFormular = henshinFactory.createNot();

			//create NestedCondition with conclusion-graph that will contain the forbidNodes and forbidEdges
			NestedCondition nestedCondition = henshinFactory.createNestedCondition();
			Graph conclusion = henshinFactory.createGraph("doNotExceedUpperBound");			

			// copy contextNode from base graph in conclusion			
			Node copiedContextNode = EcoreUtil.copy(newContextNodeLHS);
			conclusion.getNodes().add(copiedContextNode);
			
			// copy targetNode from base graph in conclusion
			Node copiedTargetNode = EcoreUtil.copy(targetNodeLHS);
			conclusion.getNodes().add(copiedTargetNode);
						
			// create forbid nodes and edges
			for(int i=1; i<=numberOfMaximumNodes; i++) {

				//create forbid node
				Node forbidNode = henshinFactory.createNode();
				forbidNode.setType(targetNodeLHS.getType());
				forbidNode.setGraph(conclusion);
				conclusion.getNodes().add(forbidNode);

				//create forbid edge
				Edge forbidEdge = henshinFactory.createEdge(copiedContextNode, forbidNode, eRefOfNewSource);
				forbidEdge.setGraph(conclusion);			
				conclusion.getEdges().add(forbidEdge);

			}
			
			// add conclusion to nestedCondition
			nestedCondition.setConclusion(conclusion);
			
			// add mapping in nestedCondition between
			// base graph contextNode and copied contextNode
			// and the same for targetNodes inside the conclusion
			Mapping contextNodeMapping = henshinFactory.createMapping(newContextNodeLHS, copiedContextNode);
			nestedCondition.getMappings().add(contextNodeMapping);
			Mapping targetNodeMapping = henshinFactory.createMapping(targetNodeLHS, copiedTargetNode);
			nestedCondition.getMappings().add(targetNodeMapping);
			
			// add nestedCondition to NOT-Formula
			newNOTFormular.setChild(nestedCondition);
			
			// put Formulas together
			HenshinRuleAnalysisUtilEx.addFormula(newNOTFormular, rule.getLhs(), FormulaCombineOperator.AND);			
			
		}			
	}

	private Rule createBasicRule(Module module, EReference eRefA, EClassifier eClassifier, EClassifier targetA, EReference eRefB, EClassifier targetB) {

		Rule rule = null;

		if(module.getName().startsWith(ADD_prefix)) {

			// ADD ***************************************************************************************************/
			rule = henshinFactory.createRule();
			rule.setActivated(true);
			rule.setName("addTo"+eClassifier.getName() + "_" +eRefA.getName() + "_"+targetA.getName());
			rule.setDescription("Adds to "+eClassifier.getName() +"'s reference "+ eRefA.getName() +" the target "+ targetA.getName());
			module.getUnits().add(rule);

			// create preserved node for eClass
			NodePair selectedNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, SEL, (EClass) eClassifier);
			Node rhsNode = selectedNodePair.getRhsNode();

			NodePair newNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, NEWTGT, (EClass) targetA);

			// create <<create>> edge for new target for EReference and it's EOpposite, if any
			HenshinRuleAnalysisUtilEx.createCreateEdge(rhsNode, newNodePair.getRhsNode(), eRefA);

		}
		else if(module.getName().startsWith(SET_prefix)) {

			// SET ***************************************************************************************************/
			rule = henshinFactory.createRule();
			rule.setActivated(true);
			rule.setName("set"+eClassifier.getName() + "_" +eRefA.getName() +TO+targetA.getName());
			rule.setDescription("Set"+eClassifier.getName() + "Ref" +eRefA.getName() +"To"+targetA.getName());
			module.getUnits().add(rule);

			// create preserved node for eClass
			NodePair selectedNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, SEL, (EClass) eClassifier);
			Node rhsNode = selectedNodePair.getRhsNode();

			NodePair newNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, NEWTGT, (EClass) targetA);

			// create <<create>> edge for new target for EReference and it's EOpposite, if any
			HenshinRuleAnalysisUtilEx.createCreateEdge(rhsNode, newNodePair.getRhsNode(), eRefA);

		}
		else if(module.getName().startsWith(CHANGE_prefix)) {
			// CHANGE ***************************************************************************************************/
			rule = henshinFactory.createRule();
			rule.setActivated(true);
			rule.setName("change"+eClassifier.getName() + "_" +eRefA.getName() + TO +targetA.getName());
			rule.setDescription("Change the EReference "+eRefA.getName());
			module.getUnits().add(rule);

			// create preserved node for eClass
			NodePair selectedNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, SEL, (EClass) eClassifier);

			NodePair oldNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, OLDTGT, (EClass) targetA);		
			NodePair newNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, NEWTGT, (EClass) targetA);

			// create <<delete>> edge to old target for EReference and it's EOpposite, if any
			HenshinRuleAnalysisUtilEx.createDeleteEdge(selectedNodePair.getLhsNode(), oldNodePair.getLhsNode(), eRefA, rule);
			// create <<create>> edge for new target for EReference and it's EOpposite, if any
			HenshinRuleAnalysisUtilEx.createCreateEdge(selectedNodePair.getRhsNode(), newNodePair.getRhsNode(), eRefA);
		}
		else if(module.getName().startsWith(MOVE_prefix)) {
			// MOVE ***************************************************************************************************/
			rule = henshinFactory.createRule();
			rule.setActivated(true);
			rule.setName("move"+eClassifier.getName() + FROM +targetA.getName() + "_"+eRefA.getName()+ TO +targetB.getName()+"_"+targetB.getName()+"");
			rule.setDescription("Moves "+eClassifier.getName() + " from " +targetA.getName() + "(Reference:"+eRefA.getName()+") to"+targetB.getName()+"(Reference:"+targetB.getName()+")");
			module.getUnits().add(rule);

			// create preserved node for eClass
			NodePair selectedNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, SEL, (EClass) eClassifier);

			NodePair oldNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, OLDSRC, (EClass) targetA);		
			NodePair newNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, NEWSRC, (EClass) targetB);

			// create <<delete>> edge to old target for EReference and it's EOpposite, if any
			HenshinRuleAnalysisUtilEx.createDeleteEdge(oldNodePair.getLhsNode(), selectedNodePair.getLhsNode(), eRefA, rule);
			// create <<create>> edge for new target for EReference and it's EOpposite, if any
			HenshinRuleAnalysisUtilEx.createCreateEdge(newNodePair.getRhsNode(), selectedNodePair.getRhsNode(), eRefB);
		}
		else if(module.getName().startsWith(CREATE_prefix)) {

			// CamelCasing of target-context name
			String contextName = "";
			if(targetA!=null) {
				contextName = Common.toCamelCase(targetA.getName());
			}else{
				contextName = "Model";			
			}				

			// Add new rule to Module
			rule = HenshinRuleAnalysisUtilEx.createRule(
					"create"+eClassifier.getName()+IN+ contextName,
					"creates one "+eClassifier.getName()+" in the context: "+ contextName,
					true,
					module);


			// create <<preserve>> nodes for context, if any
			String selectedName = getFreeNodeName(SEL,rule);
			Graph rhs = null;
			if(targetA!=null) {	
				NodePair nodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(
						rule,
						selectedName,
						(EClass) targetA);
				rhs = nodePair.getRhsNode().getGraph();
			}
			else{
				rhs = rule.getRhs();
			}			

			// Add new eClass to RHS
			String newName = getFreeNodeName(NEW,rule);
			Node newNode = HenshinRuleAnalysisUtilEx.createCreateNode(rhs, newName, (EClass) eClassifier);	

			// Add necessary attributes to the new eClass node
			createAttributes((EClass) eClassifier, newNode, rule);

			// Add edge between target-context and new eClass, if any
			if(targetA!=null && eRefA!=null) {
				Node contextNode = null;
				for(Node n: rhs.getNodes()) {
					String nName = n.getName();
					if(nName!=null && nName.equals(selectedName)) {
						contextNode = n;
					}
				}
				HenshinRuleAnalysisUtilEx.createCreateEdge(contextNode, newNode, eRefA);
			}

		}
		return rule;
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
				// be created is re-recognized via the node name NEW.
				// All sub types of the main child in focus will receive their own
				// CREATE-module when visited.
				for(Node origN: originalNodes) {
					String nOrigN = origN.getName();
					if(nOrigN!=null && nOrigN.equals(NEW)) {
						continue;
					}
					EClass typeOfReplacable = origN.getType();
					EClassifierInfo typeofReplacableInfo = ecm.getEClassifierInfo(typeOfReplacable);

					Set<EClassifierInfo> replacements = ecm.getAllSubTypes(typeofReplacableInfo);

					for(EClassifierInfo replacementInfo: replacements) {
						EClassifier replacement = replacementInfo.getTheEClassifier();
						
						if (!isAllowed(replacement,false,false))  continue;

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
								// if current node is really the NEW Node					
								if(nCopyNode!=null && copyNode.getName().equals(NEW)) {

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
								copyNode.setType((EClass) replacement);

								// create mandatories for replacement, if any
								createMandatoryChildren((Rule)copyNode.getGraph().eContainer(),
										ecm.getEClassifierInfo(replacement),
										copyNode);
								createMandatoryNeighbours((Rule)copyNode.getGraph().eContainer(),
										ecm.getEClassifierInfo(replacement),
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
	 * Creates NACs or PACs consisting of Nodes and Edges that are required to cover individual
	 * constraints, defined on the SERGe configuration.
	 * @param node
	 * @param module
	 * @param opType
	 */
	private void createElementsForConstraints_SET(Module module, OperationType opType, ConstraintType cType) {

		Rule rule = HenshinRuleAnalysisUtilEx.getRulesUnderModule(module).get(0);

		List<Attribute> createAttributes = new ArrayList<Attribute>();			
		// add all <<create>> Attributes under <<preserved> Nodes
		createAttributes.addAll(HenshinRuleAnalysisUtilEx.getRHSChangedAttributes(rule));

		for(Attribute createAttribute: createAttributes) {
			Node nodeOfAttribute = createAttribute.getNode();
			EClassifierInfo eInfo = ecm.getEClassifierInfo(nodeOfAttribute.getType());

			if(cType == ConstraintType.NAME_UNIQUENESS_LOCAL) {
				embedLocalNameUniqueness(eInfo, nodeOfAttribute, rule);
			}
			else if(cType == ConstraintType.NAME_UNIQUENESS_GLOBAL) {
				embedGlobalNameUniqueness(eInfo, nodeOfAttribute, rule);
			}
		}
	}
	

	private void createElementsForConstraints_CREATE(Module module) throws ConstraintException {

		Rule rule = HenshinRuleAnalysisUtilEx.getRulesUnderModule(module).get(0);
		
		// regard every <<create>> node	
		//TODO check constraints other than name uniqueness here, if available
		
		// regard every <<create>> attribute (either under a <<create>> or <<preserved>> node)		
		List<Attribute> createAttributes = new ArrayList<Attribute>();			
		createAttributes.addAll(HenshinRuleAnalysisUtilEx.getCreationAttributes(rule));
		createAttributes.addAll(HenshinRuleAnalysisUtilEx.getRHSChangedAttributes(rule));

		for(Attribute createAttribute: createAttributes) {
			Node nodeOfAttribute = createAttribute.getNode();
			EClassifierInfo eInfo = ecm.getEClassifierInfo(nodeOfAttribute.getType());
			
			Boolean constrainedByLNU = eInfo.isConstrainedToLocalNameUniqueness();
			Boolean constrainedByGNU = eInfo.isConstrainedToGlobalNameUniqueness();
			
			if(constrainedByLNU && constrainedByGNU) {
				EAttribute LNUea =(EAttribute)eInfo.getConstraintsAndFlags().get(ConstraintType.NAME_UNIQUENESS_LOCAL).get(1);
				EAttribute GNUea =(EAttribute)eInfo.getConstraintsAndFlags().get(ConstraintType.NAME_UNIQUENESS_GLOBAL).get(1);
				
				if( LNUea == GNUea) {
					//if current EAttribute is part of a constraint
					if(createAttribute.getType() == LNUea) {
						//find out which constraint has priority
						Boolean isSetToOverride_CLNU = (Boolean)eInfo.getConstraintsAndFlags().get(ConstraintType.NAME_UNIQUENESS_LOCAL).get(2);
						Boolean isSetToOverride_CGNU = (Boolean)eInfo.getConstraintsAndFlags().get(ConstraintType.NAME_UNIQUENESS_GLOBAL).get(2);
						
						if(isSetToOverride_CGNU && isSetToOverride_CLNU) {
							throw new ConstraintException("The constrained Attribute '"+createAttribute.getType()+"' is both locally and globally constraint and both are set to override.");
						}
						else if(isSetToOverride_CGNU) {
							embedGlobalNameUniqueness(eInfo, nodeOfAttribute, rule);
						}
						else if(isSetToOverride_CLNU) {
							embedLocalNameUniqueness(eInfo, nodeOfAttribute, rule);
						}
					}
				}else{
					throw new ConstraintException("The constrained Attributes for global and local Name Uniqueness are not equal");
				}
			}
			else if(!constrainedByLNU && constrainedByGNU) {
				EAttribute GNUea =(EAttribute)eInfo.getConstraintsAndFlags().get(ConstraintType.NAME_UNIQUENESS_GLOBAL).get(1);
				if(createAttribute.getType()==GNUea) {
					embedGlobalNameUniqueness(eInfo, nodeOfAttribute, rule);
				}
			}
			else if(constrainedByLNU && !constrainedByGNU) {
				EAttribute LNUea =(EAttribute)eInfo.getConstraintsAndFlags().get(ConstraintType.NAME_UNIQUENESS_LOCAL).get(1);
				if(createAttribute.getType()==LNUea) {
					embedLocalNameUniqueness(eInfo, nodeOfAttribute, rule);
				}
			}
		}
	}
	
	
	private void createElementsForConstraints_MOVE(Module MOVE_Module) {

		// The selected Node is the node which will be moved. It's new context
		// must be identified via the <<create>> edge.
		// We need to create a new node of the same type and same context as the selected node.
		// In both the selected and the new node we need to create the Attributes which must be unique
		// but only locally. Global uniqueness needs only to be covered in CREATEs and SETs.
		
		Rule rule = HenshinRuleAnalysisUtilEx.getRulesUnderModule(MOVE_Module).get(0);		
		Node selectedNode = HenshinRuleAnalysisUtilEx.getNodeByName(rule, SEL,true);
		NodePair np = HenshinRuleAnalysisUtilEx.getNodePair(rule, selectedNode.getType(), SEL);
		
		EClassifierInfo eInfo = ecm.getEClassifierInfo(selectedNode.getType());
		
		if(eInfo.isConstrainedToLocalNameUniqueness()) {
		
			EAttribute eAttribute = (EAttribute) eInfo.getConstraintsAndFlags().get(ConstraintType.NAME_UNIQUENESS_LOCAL).get(1); 
			HenshinRuleAnalysisUtilEx.createPreservedAttribute(np, eAttribute, "Name", false);
			embedLocalNameUniqueness(eInfo, selectedNode, rule);
		}
		
	}
	
	private void embedLocalNameUniqueness(EClassifierInfo eInfo, Node attributeContainingNode, Rule rule){

		EAttribute eAttribute = (EAttribute) eInfo.getConstraintsAndFlags().get(ConstraintType.NAME_UNIQUENESS_LOCAL).get(1);//TODO find type safe solution in future
		EClass nodeType = attributeContainingNode.getType();

		// add forbid node
		Attribute nodeAttribute= attributeContainingNode.getAttribute(eAttribute);
		Node forbidNode = HenshinRuleAnalysisUtilEx.createForbidNode(rule, nodeType);
		forbidNode.getGraph().setName("elementWithSameNameMustNotExistLocally");
		String nameOfEAParameter = nodeAttribute.getValue();
		henshinFactory.createAttribute(forbidNode, eAttribute, nameOfEAParameter );

		// add edge to subordinate forbid node under context
		EReference eRef = null;
		Node contextNode = null;
		for(Edge inEdge: attributeContainingNode.getIncoming()) {
			if(inEdge.getType().isContainment() &&
				(HenshinRuleAnalysisUtilEx.isCreationEdge(inEdge) || (HenshinRuleAnalysisUtilEx.isPreservedEdge(inEdge)))) {
				eRef = inEdge.getType();
				contextNode = inEdge.getSource(); //==contextNode either in RHS or LHS
				
				//if Node lies in RHS, find the LHS equivalent, since NAC is always in LHS.
				if(contextNode.getGraph().isRhs()) {
					contextNode = rule.getMappings().getOrigin(contextNode);
				}
				
				// draw edge from context to forbid node
				HenshinRuleAnalysisUtilEx.createForbidEdge(contextNode, forbidNode, eRef, rule);
			}
		}
		// if contextNode still null, then the edge pointing to the context is in the other Graph (RHS)
		if(contextNode==null) {
			attributeContainingNode = rule.getMappings().getImage(attributeContainingNode, rule.getRhs());
			for(Edge inEdge: attributeContainingNode.getIncoming()) {
				if(inEdge.getType().isContainment() &&
					(HenshinRuleAnalysisUtilEx.isCreationEdge(inEdge) || (HenshinRuleAnalysisUtilEx.isPreservedEdge(inEdge)))) {
					eRef = inEdge.getType();
					contextNode = inEdge.getSource(); //==contextNode either in RHS or LHS
					
					//if Node lies in RHS, find the LHS equivalent, since NAC is always in LHS.
					if(contextNode.getGraph().isRhs()) {
						contextNode = rule.getMappings().getOrigin(contextNode);
					}
					
					// draw edge from context to forbid node
					HenshinRuleAnalysisUtilEx.createForbidEdge(contextNode, forbidNode, eRef, rule);
				}
			}
		}
	}

	
	
	private void embedGlobalNameUniqueness(EClassifierInfo eInfo, Node attributeContainingNode, Rule rule){

		EAttribute eAttribute = (EAttribute) eInfo.getConstraintsAndFlags().get(ConstraintType.NAME_UNIQUENESS_GLOBAL).get(1);//TODO find type safe solution in future
		Node forbidNode = null;
		EClass nodeType = attributeContainingNode.getType();

		// add forbid node
		Attribute nodeAttribute= attributeContainingNode.getAttribute(eAttribute);
		forbidNode = HenshinRuleAnalysisUtilEx.createForbidNode(rule, nodeType);
		forbidNode.getGraph().setName("elementWithSameNameMustNotExistGlobally");
		String nameOfEAParameter = nodeAttribute.getValue();
		henshinFactory.createAttribute(forbidNode, eAttribute, nameOfEAParameter );

	}

	private void replaceNewsWithToBeDeleted(Module module) {
		
		for(Rule r: HenshinRuleAnalysisUtilEx.getRulesUnderModule(module)) {
			for(Node n: r.getLhs().getNodes()) {
				String nN = n.getName();
				if(nN!=null && nN.equals(NEW)) {
					n.setName(DEL);
				}
			}
			for(Node n: r.getRhs().getNodes()) {
				if(n.getName().equals(NEW)) {
					n.setName(DEL);
				}
			}
			for(Parameter p: r.getParameters()) {
				String pN = p.getName();
				if(pN!=null && pN.equals(NEW)) {
					p.setName(DEL);	
				}
			}
		}
		
	}
	
	private void removeUnnecessaryParametersForDELETE(Module module, Unit mainUnit) {
		//retain only ChildX/ExistingX/ToBeDeleted Parameters
		List<Parameter> unnecessaryParameters = new ArrayList<Parameter>();

		for(Rule r: HenshinRuleAnalysisUtilEx.getRulesUnderModule(module)) {
			for(Parameter p: r.getParameters()) {
				
				if(p.getName().startsWith(CHILD) || p.getName().startsWith(EX) || p.getName().startsWith(DEL)) {
					
					boolean alreadyContained = false;
					for(Parameter pInInverseUnit: mainUnit.getParameters()) {
						if(pInInverseUnit.equals(p.getName())) {
							alreadyContained = true;
							break;
						}
					}
					
					if(!alreadyContained) {
						Parameter newEClassParam = henshinFactory.createParameter(p.getName());
						newEClassParam.setType(p.getType());
						mainUnit.getParameters().add(newEClassParam);
					}												
				}else{
					unnecessaryParameters.add(p);
				}
			}
		}			
		//remove unnecessary parameters
		HenshinRuleAnalysisUtilEx.getRulesUnderModule(module).get(0).getParameters().removeAll(unnecessaryParameters);
	}

}
