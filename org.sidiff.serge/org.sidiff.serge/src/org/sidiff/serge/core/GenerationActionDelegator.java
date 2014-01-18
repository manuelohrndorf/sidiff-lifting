package org.sidiff.serge.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Module;
import org.sidiff.common.emf.extensions.impl.EClassifierInfo;
import org.sidiff.common.emf.extensions.impl.EClassifierInfoManagement;
import org.sidiff.common.emf.extensions.impl.EcoreHelper;
import org.sidiff.common.emf.extensions.impl.Mask;
import org.sidiff.serge.core.Configuration.OperationType;
import org.sidiff.serge.exceptions.ModuleForInverseCreationRequiredException;
import org.sidiff.serge.exceptions.OperationTypeNotImplementedException;
import org.sidiff.serge.generators.actions.AddGenerator;
import org.sidiff.serge.generators.actions.ChangeLiteralGenerator;
import org.sidiff.serge.generators.actions.ChangeReferenceGenerator;
import org.sidiff.serge.generators.actions.CreateGenerator;
import org.sidiff.serge.generators.actions.DeleteGenerator;
import org.sidiff.serge.generators.actions.MoveDownGenerator;
import org.sidiff.serge.generators.actions.MoveGenerator;
import org.sidiff.serge.generators.actions.MoveMaskedElementGenerator;
import org.sidiff.serge.generators.actions.MoveReferenceCombinationGenerator;
import org.sidiff.serge.generators.actions.MoveUpGenerator;
import org.sidiff.serge.generators.actions.RemoveGenerator;
import org.sidiff.serge.generators.actions.SetAttributeGenerator;
import org.sidiff.serge.generators.actions.SetReferenceGenerator;
import org.sidiff.serge.generators.actions.UnsetAttributeGenerator;
import org.sidiff.serge.generators.actions.UnsetReferenceGenerator;
import org.sidiff.serge.generators.actions.VariantGenerator;
import org.silift.common.util.access.EMFMetaAccessEx;

public class GenerationActionDelegator {

	private static GenerationActionDelegator GAD = null;
	private static Configuration 			 c	 = Configuration.getInstance();
	private static EClassifierInfoManagement ECM = EClassifierInfoManagement.getInstance();
	private static ElementFilter			 FILTER = ElementFilter.getInstance();
	
	/**
	 * Singleton
	 * @return {@link GenerationActionDelegator}
	 */
	public static GenerationActionDelegator getInstance() {
		if(GAD==null) {
			GAD = new GenerationActionDelegator();
		}
		return GAD;
	}
	
	/**
	 * General CREATE-generation method, that finds all relevant
	 * contexts and references that represent different CREATE-Modules for this eClassifier.
	 * For each setup the generation process will be delegated to {@link CreateGenerator}
	 * 
	 * @param eClassifier
	 * @return Set of disparate create modules for the given classifier.
	 * @throws OperationTypeNotImplementedException 
	 */
	public Set<Module> generate_CREATE(EClassifier eClassifier) throws OperationTypeNotImplementedException {
		
		Set<Module> modules = new HashSet<Module>();	
	
		if(c.CREATE_CREATES) {
		
			if(FILTER.isAllowedAsModuleBasis(eClassifier, OperationType.CREATE)) {
			
				EClassifierInfo eInf = ECM.getEClassifierInfo(eClassifier);
				
				/** In case of no Stereotype, create CREATE normally ******************************************************************************/
				if(!c.PROFILEAPPLICATIONINUSE || (c.PROFILEAPPLICATIONINUSE && !eInf.isStereotype())) {
					
					/** Create Modules for every parent in which the EClass may exist. ************************************************************/
					HashMap<EReference, List<EClassifier>> optionalParents = ECM.getAllOptionalParentContext(eClassifier, c.REDUCETOSUPERTYPE_CREATEDELETE);
					for(Entry<EReference,List<EClassifier>> pcEntry: optionalParents.entrySet()) {			
						List<EClassifier> contexts = pcEntry.getValue();
						EReference eRef = pcEntry.getKey();
		
						for(EClassifier context: contexts) {
							
							if(FILTER.isAllowedAsDangling(context, OperationType.CREATE, c.REDUCETOSUPERTYPE_CREATEDELETE)) {
												
								CreateGenerator generator = new CreateGenerator(eRef, context, eInf);
								Module resultModule = generator.generate();
								
								modules.add(resultModule);
							
							}
						}
					}
				}
				/** In case of Stereotype, there are no contexts! Just create Rule with <<create>> Node for Stereotype ****************************/
				else{
					//TODO ProfileModelIntegration
				}
				
			
			}
		}
		return modules;
	}
	
	/**
	 * General DELETE-generation method that delegates each given CREATE-module of the input set
	 * to the inverse creation method {@link DeleteGenerator}.
	 * 
	 * @param set of create modules
	 * @return 
	 * @throws ModuleForInverseCreationRequiredException 
	 * @throws OperationTypeNotImplementedException 
	 */
	public Set<Module> generate_DELETE(Set<Module> createModulesSet) throws ModuleForInverseCreationRequiredException, OperationTypeNotImplementedException {

		Set<Module> modules	= new HashSet<Module>();
		
		if(c.CREATE_DELETES) {
			
			if(!c.CREATE_CREATES) throw new ModuleForInverseCreationRequiredException(OperationType.DELETE);
			
			for(Module createModule: createModulesSet) {
				
				DeleteGenerator generator = new DeleteGenerator(createModule);
				Module resultModule = generator.generate();
				
				modules.add(resultModule);
				
			}
		}
		
		return modules;
		
	}
	
	/**
	 * General MOVE-generation method, that finds all relevant
	 * contexts and references that represent different MOVE-Modules for this eClassifier.
	 * The generated MOVE-Modules will represent moves of EClassifiers between context EClassifiers over one
	 * specific containment EReference type.
	 * For each setup the generation process will be delegated to {@link MoveGenerator}
	 * 
	 * @param eClassifier
	 * @return 
	 * @throws OperationTypeNotImplementedException 
	 */
	public Set<Module> generate_MOVE(EClassifier eClassifier) throws OperationTypeNotImplementedException {
		
		Set<Module> modules	= new HashSet<Module>();

		if(c.CREATE_MOVES && FILTER.isAllowedAsModuleBasis(eClassifier, OperationType.MOVE)) {

			// get possible eClassifier Masks for additional move generation of masked classifiers.
			List<Mask> eClassifierMasks = new ArrayList<Mask>();
			eClassifierMasks.addAll(ECM.getEClassifierInfo(eClassifier).getMasks());
			
			// get all possible contexts (mandatory & optional) and the according references
			HashMap<EReference,List<EClassifier>> allParents = ECM.getAllParentContexts(eClassifier, c.REDUCETOSUPERTYPE_MOVE);
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
						if (FILTER.isAllowedAsDangling(parent, OperationType.MOVE, c.REDUCETOSUPERTYPE_MOVE)) {
							if(allAllowedParents.get(eRef)==null) {
								List<EClass> newParentList = new ArrayList<EClass>();
								newParentList.add(parent);
								allAllowedParents.put(eRef, newParentList);
							}else{
								allAllowedParents.get(eRef).add(parent);
							}
						}

						// all EReferences
						ArrayList<EReference> allReferences = new ArrayList<EReference>();
						allReferences.addAll(allAllowedParents.keySet());

						for(Entry<EReference,List<EClass>> entry: allAllowedParents.entrySet()) {

							EReference eRefA = entry.getKey();
							List<EClass> contexts_eRefA = entry.getValue();

							//internal-eRef combinations along same EReference
							for(EClass contextA_eRef: contexts_eRefA) {
								for(EClass contextB_eRefA: contexts_eRefA) {							

									// generate normal moves
									MoveGenerator generator = new MoveGenerator(eClassifier, eRefA, contextA_eRef, contextB_eRefA);
									modules.add(generator.generate());
									
									// also generate all moves for masked eClassifiers, if any
									for(Mask mask: eClassifierMasks) {
										MoveMaskedElementGenerator generatorForMasks = new MoveMaskedElementGenerator(mask, eClassifier, eRefA, contextA_eRef, contextB_eRefA, eRef, null);
										modules.add(generatorForMasks.generate());
									}

								}
							}					
						}
					}
				}
			}
		}
	
		return modules;
		
	}
	
	/**
	 * General MOVE-UP-generation method, that finds all relevant
	 * contexts and references that represent different MOVE-UP-Modules for this eClassifier.
	 * For each setup the generation process will be delegated to {@link MoveUpGenerator}
	 * 
	 * @param eClassifier
	 * @return 
	 */
	public Set<Module> generate_MOVE_UP(EClassifier eClassifier) {
		
		Set<Module> modules	= new HashSet<Module>();
		
		if(c.CREATE_MOVE_UPS) {
			
			// TODO implement MoveUp
			
		}
		
		return modules;
		
	}
	
	/**
	 * General MOVE-DOWN-generation method, that finds all relevant
	 * contexts and references that represent different MOVE-DOWN-Modules for this eClassifier.
	 * For each setup the generation process will be delegated to {@link MoveDownGenerator}
	 * 
	 * @param eClassifier
	 * @return 
	 */
	public Set<Module> generate_MOVE_DOWN(EClassifier eClassifier) {
		
		Set<Module> modules	= new HashSet<Module>();
		
		if(c.CREATE_MOVE_DOWNS) {
			
			// TODO implement MoveDown
			
		}
		
		return modules;
		
	}
	
	/**
	 * General MOVE-Combination-generation method, that finds all relevant
	 * contexts and references that represent different MOVE-Combination-Modules for this eClassifier.
	 * For each setup the generation process will be delegated to {@link MoveReferenceCombinationGenerator}
	 * 
	 * @param eClassifier
	 * @return 
	 * @throws OperationTypeNotImplementedException 
	 */
	public Set<Module> generate_MOVE_REFERENCE_COMBINATION(EClassifier eClassifier) throws OperationTypeNotImplementedException {
		
		Set<Module> modules	= new HashSet<Module>();

		if(c.CREATE_MOVE_REFERENCE_COMBINATIONS && FILTER.isAllowedAsModuleBasis(eClassifier, OperationType.MOVE_REFERENCE_COMBINATION)) {

			// get possible eClassifier Masks for additional move generation of masked classifiers.
			List<Mask> eClassifierMasks = new ArrayList<Mask>();
			eClassifierMasks.addAll(ECM.getEClassifierInfo(eClassifier).getMasks());
			
			// get all possible contexts (mandatory & optional) and the according references
			HashMap<EReference,List<EClassifier>> allParents = ECM.getAllParentContexts(eClassifier, c.CREATE_MOVE_REFERENCE_COMBINATIONS);
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
						if (FILTER.isAllowedAsDangling(parent, OperationType.MOVE_REFERENCE_COMBINATION, c.REDUCETOSUPERTYPE_MOVE_REFERENCE_COMBINATION)) {
							if(allAllowedParents.get(eRef)==null) {
								List<EClass> newParentList = new ArrayList<EClass>();
								newParentList.add(parent);
								allAllowedParents.put(eRef, newParentList);
							}else{
								allAllowedParents.get(eRef).add(parent);
							}
						}

						// all EReferences
						ArrayList<EReference> allReferences = new ArrayList<EReference>();
						allReferences.addAll(allAllowedParents.keySet());

						for(Entry<EReference,List<EClass>> entry: allAllowedParents.entrySet()) {

							EReference eRefA = entry.getKey();
							List<EClass> contexts_eRefA = entry.getValue();

							//inter-eRef combinations (switching of EReferences when moving)
							for(EClass contextA_eRefA: contexts_eRefA) {

								// get all other EReferences
								ArrayList<EReference> allOtherEReferences = new ArrayList<EReference>();
								allOtherEReferences.addAll(allAllowedParents.keySet());
								allOtherEReferences.remove(contextA_eRefA);

								for(EReference eRefB: allOtherEReferences) {
									for(EClass contextB_eRefB: allAllowedParents.get(eRefB)) {
										
										// generate normal moves
										MoveReferenceCombinationGenerator generator = new MoveReferenceCombinationGenerator(eClassifier, eRefA, contextA_eRefA, contextB_eRefB, eRef, OperationType.MOVE_REFERENCE_COMBINATION);
										modules.add(generator.generate());

										// also generate all moves for masked eClassifiers, if any
										for(Mask mask: eClassifierMasks) {
											MoveMaskedElementGenerator generatorForMasks = new MoveMaskedElementGenerator(mask, eClassifier, eRefA, contextA_eRefA, contextB_eRefB, eRef,  OperationType.MOVE_REFERENCE_COMBINATION);
											modules.add(generatorForMasks.generate());
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		return modules;
		
	}
	
	/**
	 * General ADD-generation method, that finds all relevant
	 * contexts and references that represent different ADD-Modules for this eClassifier.
	 * For each setup the generation process will be delegated to {@link AddGenerator}
	 * 
	 * @param eClassifier
	 * @return Set of disparate add modules for the given eclass.
	 * @throws OperationTypeNotImplementedException 
	 */
	public Set<Module> generate_ADD(EClassifier eClassifier) throws OperationTypeNotImplementedException {
		
		Set<Module> modules	= new HashSet<Module>();
		
		if(c.CREATE_ADDS) {
			
			EClassifierInfo eClassInfo = ECM.getEClassifierInfo(eClassifier);
			
			if (!FILTER.isAllowedAsModuleBasis(eClassifier, OperationType.ADD)) return null;
	
			//TODO implicit requirements
			// if (!isImplicitlyRequiredForFeatureInheritance(eClassifier)))  return;
			if (c.PROFILEAPPLICATIONINUSE && eClassInfo.isExtendedMetaClass() && !c.isRoot(eClassifier)) return null;
			
			EClass eClass = (EClass) eClassifier;
							
			// EReferences and their EOpposites, if any		
			for(EReference eRef: eClass.getEAllReferences()) {
	
				// don't consider derived, not changeable, unsettable and transient references
				if(!eRef.isDerived() && eRef.isChangeable() && !eRef.isTransient()) {
	
					// eRef == no containment reference  *************************************************************/
					if(!eRef.isContainment()) {
						EReference eOpposite = eRef.getEOpposite();
	
						// and skip eRefs where it's EOpposite is a containment
						if((eOpposite!=null && !eOpposite.isContainment()) || eOpposite==null) {
	
							EClass contextType = (EClass)eRef.getEType();
	
							if (!FILTER.isAllowedAsDangling(contextType,OperationType.ADD,c.REDUCETOSUPERTYPE_ADDREMOVE))  continue;
	
							int lower = eRef.getLowerBound();
							int upper = eRef.getUpperBound();
							
							if(!eRef.isContainment() 
								&& (upper==-1 || upper-lower>0)
								&& (
									(EcoreHelper.isInheritedReference(eRef, eClassifier) && !c.REDUCETOSUPERTYPE_ADDREMOVE)
									|| !EcoreHelper.isInheritedReference(eRef, eClassifier)
								   )
							   ) {
	
								AddGenerator generator = new AddGenerator(eRef, contextType, eClassifier);
								Module resultModule = generator.generate();
								
								modules.add(resultModule);
	
							}
						}
					}
				}
			}
		}
		return modules;
	}

	/**
	 * General REMOVE-generation method that delegates each given REMOVE-module of the input set
	 * to the inverse creation method {@link RemoveGenerator}.
	 * 
	 * @param set of remove modules
	 * @return 
	 * @throws ModuleForInverseCreationRequiredException 
	 * @throws OperationTypeNotImplementedException 
	 */
	public Set<Module> generate_REMOVE(Set<Module> addModules) throws ModuleForInverseCreationRequiredException, OperationTypeNotImplementedException {
		
		Set<Module> modules	= new HashSet<Module>();
		
		if(c.CREATE_REMOVES) {
		
			if(!c.CREATE_ADDS) throw new ModuleForInverseCreationRequiredException(OperationType.REMOVE);
						
			for(Module addModule: addModules) {
				
				RemoveGenerator generator = new RemoveGenerator(addModule);
				Module resultModule = generator.generate();
				
				modules.add(resultModule);
				
			}
		}
		return modules;
		
	}

	/**
	 * General SET-ATTRIBUTE-generation method, that finds all relevant
	 * contexts and references that represent different SET-ATTRIBUTE for this eClassifier.
	 * For each setup the generation process will be delegated to {@link SetAttributeGenerator}
	 * 
	 * @param eClassifier
	 * @return Set of disparate set attribute modules for the given eClassifier.
	 * @throws OperationTypeNotImplementedException 
	 */
	public Set<Module> generate_SET_ATTRIBUTE(EClassifier eClassifier) throws OperationTypeNotImplementedException {
		
		Set<Module> modules	= new HashSet<Module>();
		
		if(c.CREATE_SET_ATTRIBUTES) {
			
			EClassifierInfo eClassInfo = ECM.getEClassifierInfo(eClassifier);
			
			if (!FILTER.isAllowedAsModuleBasis(eClassifier, OperationType.SET_ATTRIBUTE)){
				return null;
			}
	
			//TODO implicit requirements
			// if (!isImplicitlyRequiredForFeatureInheritance(eClassifier)))  return;
			if (c.PROFILEAPPLICATIONINUSE && eClassInfo.isExtendedMetaClass() && !c.isRoot(eClassifier)){
				return null;
			}
			
			EClass eClass = (EClass) eClassifier;		

			// EAttributes which shall be considered
			List<EAttribute> easToConsider = new ArrayList<EAttribute>();
			if(c.REDUCETOSUPERTYPE_SETUNSET_ATTRIBUTES) {
				//all own eattributes
				List<EAttribute> ownEAttributes = eClass.getEAttributes();
				if(ownEAttributes!=null) {
					easToConsider.addAll(ownEAttributes);
				}

				//also include all inherited EAttributes, for which SERGEe Constraints are defined
				List<EAttribute> easOfConstraintsToConsider = ECM.getAllInheritedEAttributesInvolvedInConstraints(eClassifier);
				if(easOfConstraintsToConsider!=null) {
					easToConsider.addAll(easOfConstraintsToConsider);
				}

			}else{
				//all inherited eattributes
				easToConsider = eClass.getEAllAttributes();
			}

			for(EAttribute ea: easToConsider) {
				// don't consider derived, not changeable, and transient attributes
				if(!EMFMetaAccessEx.isUnconsideredStructualFeature(ea)) {

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

					/**********SET / UNSET ATTRIBUTES *******************************************************************************/
					else if( ((lowerBound == 0) && (upperBound == 1)) || ((lowerBound == 1) && (upperBound == 1))){

						SetAttributeGenerator generator = new SetAttributeGenerator(eClassifier, ea);
						Module module = generator.generate();
						modules.add(module);
					}
				}
			}
		}
		
		return modules;
	}

	/**
	 * General UNSET-ATTRIBUTE-generation method that delegates each given UNSET-ATTRIBUTE-module of the input set
	 * to the inverse creation method {@link UnsetAttributeGenerator}.
	 * <br/> Whereby the inverse can be considered as the resetting to default values if any.
	 * 
	 * @param set of set attribute modules
	 * @return 
	 * @throws ModuleForInverseCreationRequiredException 
	 * @throws OperationTypeNotImplementedException 
	 */
	public Set<Module> generate_UNSET_ATTRIBUTE(Set<Module> setAttributeModules) throws ModuleForInverseCreationRequiredException, OperationTypeNotImplementedException {
		Set<Module> modules	= new HashSet<Module>();
		
		if(c.CREATE_UNSET_ATTRIBUTES) {
			if(!c.CREATE_SET_ATTRIBUTES){
				throw new ModuleForInverseCreationRequiredException(OperationType.UNSET_ATTRIBUTE);
			}
		
			for(Module setAttributeModule: setAttributeModules) {			
				UnsetAttributeGenerator generator = new UnsetAttributeGenerator(setAttributeModule);
				Module resultModule = generator.generate();
				modules.add(resultModule);
			}
		}
		
		return modules;
	}
	
	/**
	 * General SET-REFERENCE-generation method, that finds all relevant
	 * contexts and references that represent different SET-REFERENCE for this eClassifier.
	 * For each setup the generation process will be delegated to {@link SetReferenceGenerator}
	 * 
	 * @param eClassifier
	 * @return Set of disparate set reference modules for the given eClassifier.
	 * @throws OperationTypeNotImplementedException 
	 */
	public Set<Module> generate_SET_REFERENCE(EClassifier eClassifier) throws OperationTypeNotImplementedException {	
		Set<Module> modules	= new HashSet<Module>();
		
		if(c.CREATE_ADDS) {
			
			EClassifierInfo eClassInfo = ECM.getEClassifierInfo(eClassifier);
			
			if (!FILTER.isAllowedAsModuleBasis(eClassifier, OperationType.ADD)) return null;
	
			//TODO implicit requirements
			// if (!isImplicitlyRequiredForFeatureInheritance(eClassifier)))  return;
			if (c.PROFILEAPPLICATIONINUSE && eClassInfo.isExtendedMetaClass() && !c.isRoot(eClassifier)) return null;
			
			EClass eClass = (EClass) eClassifier;
							
			// EReferences and their EOpposites, if any		
			for(EReference eRef: eClass.getEAllReferences()) {
	
				// don't consider derived, not changeable, and transient references
				if(!EMFMetaAccessEx.isUnconsideredStructualFeature(eRef)) {
	
					// eRef == no containment reference  *************************************************************/
					if(!eRef.isContainment()) {
						EReference eOpposite = eRef.getEOpposite();
	
						// and skip eRefs where it's EOpposite is a containment
						if((eOpposite!=null && !eOpposite.isContainment()) || eOpposite==null) {
	
							EClassifier target = eRef.getEType();
	
							if (!FILTER.isAllowedAsDangling(target,OperationType.ADD,c.REDUCETOSUPERTYPE_ADDREMOVE))  continue;
	
							int lower = eRef.getLowerBound();
							int upper = eRef.getUpperBound();		
							
							// eRef == no containment reference  and (0..1) and
							// only continue, if ref is inherited and no reduction to super type is wished
							// or ref is not inherited
							if(!eRef.isContainment() && lower==0 && upper==1
								 && (
									  (EcoreHelper.isInheritedReference(eRef, eClassifier) && !c.REDUCETOSUPERTYPE_SETUNSET_REFERENCES)
									   || !EcoreHelper.isInheritedReference(eRef, eClassifier)
									)						
							   ) {

								SetReferenceGenerator generator = new SetReferenceGenerator(eRef, eClassifier, target);
								modules.add(generator.generate());
							}
						}
					}
				}
			}
		}
		return modules;
	}
	
	/**
	 * General UNSET-REFERENCE-generation method that delegates each given UNSET-REFERENCE-module of the input set
	 * to the inverse creation method {@link UnsetReferenceGenerator}.
	 * <br/> Whereby the inverse can be considered as the resetting to default values if any.
	 * 
	 * @param set of set reference modules
	 * @return 
	 * @throws OperationTypeNotImplementedException 
	 * @throws ModuleForInverseCreationRequiredException 
	 */
	public Set<Module> generate_UNSET_REFERENCE(Set<Module> setReferenceModules) throws OperationTypeNotImplementedException, ModuleForInverseCreationRequiredException {
		
		Set<Module> modules	= new HashSet<Module>();
		
		if(c.CREATE_UNSET_REFERENCES) {
				
			if(!c.CREATE_SET_REFERENCES) throw new ModuleForInverseCreationRequiredException(OperationType.UNSET_REFERENCE);
			
			for(Module setReferenceModule: setReferenceModules) {
				
				UnsetReferenceGenerator generator = new UnsetReferenceGenerator(setReferenceModule);
				Module resultModule = generator.generate();
				
				modules.add(resultModule);
				
			}
		}
		
		return modules;
		
	}
	
	/**
	 * General CHANGe-generation method, that finds all relevant
	 * EAttributes that require CHANGe-Modules for different EEnumLiterals
	 * For each setup the generation process will be delegated to {@link ChangeLiteralGenerator}
	 * 
	 * @param eClassifier
	 * @return 
	 * @throws OperationTypeNotImplementedException 
	 */
	public Set<Module> generate_CHANGE_Literals(EClassifier eClassifier) throws OperationTypeNotImplementedException {
		
		Set<Module> modules	= new HashSet<Module>();
		
		if(c.CREATE_CHANGE_LITERALS) {
			
			EClassifierInfo eClassInfo = ECM.getEClassifierInfo(eClassifier);
			
			if (!FILTER.isAllowedAsModuleBasis(eClassifier, OperationType.ADD)) return null;
	
			//TODO implicit requirements
			// if (!isImplicitlyRequiredForFeatureInheritance(eClassifier)))  return;
			if (c.PROFILEAPPLICATIONINUSE && eClassInfo.isExtendedMetaClass() && !c.isRoot(eClassifier)) return null;
			
			EClass eClass = (EClass) eClassifier;
			
			// EAttributes which shall be considered
			List<EAttribute> easToConsider = new ArrayList<EAttribute>();
			if(c.REDUCETOSUPERTYPE_CHANGE_LITERALS) {
				//all own eattributes
				List<EAttribute> ownEAttributes = eClass.getEAttributes();
				if(ownEAttributes!=null) {
					easToConsider.addAll(ownEAttributes);
				}
				
				//also include all inherited EAttributes, for which SERGEe Constraints are defined
				List<EAttribute> easOfConstraintsToConsider = ECM.getAllInheritedEAttributesInvolvedInConstraints(eClassifier);
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
										
					if((lowerBound == 1) && (upperBound == 1) && (ea.getEType() instanceof EEnum)) {
						
						EEnum eenum = (EEnum) ea.getEType();
												
						// build up combinations
						Map<EEnumLiteral,List<EEnumLiteral>> combinations = new HashMap<EEnumLiteral,List<EEnumLiteral>>();

						for(EEnumLiteral literal :eenum.getELiterals()) {
							combinations.put(literal, new ArrayList<EEnumLiteral>(eenum.getELiterals()));
						}//here combination with self is still included.						
						
						// build CHANGE modules for every combination:						
						for(Entry<EEnumLiteral,List<EEnumLiteral>> entry: combinations.entrySet()) {									
							EEnumLiteral oldLiteral = entry.getKey();
							
							//to remove combination with self, remove self from old values
							entry.getValue().remove(oldLiteral);
							
							// now create module for every literal combination
							for(EEnumLiteral newLiteral: entry.getValue()) {

								ChangeLiteralGenerator generator = new ChangeLiteralGenerator(ea, oldLiteral, eClassifier, oldLiteral, newLiteral);
								modules.add(generator.generate());
								
							}
							
						}

					}
					
				}
			}

		}
		return modules;
	}

	/**
	 * General CHANGe-generation method, that finds all relevant
	 * references that require CHANGe-Modules.
	 * For each setup the generation process will be delegated to {@link ChangeReferenceGenerator}
	 * 
	 * @param eClassifier
	 * @return 
	 * @throws OperationTypeNotImplementedException 
	 */
	public Set<Module> generate_CHANGE_Reference(EClassifier eClassifier) throws OperationTypeNotImplementedException {
		
		Set<Module> modules	= new HashSet<Module>();
		
		if(c.CREATE_CHANGE_REFERENCES) {
			
			EClassifierInfo eClassInfo = ECM.getEClassifierInfo(eClassifier);
			
			if (!FILTER.isAllowedAsModuleBasis(eClassifier, OperationType.CHANGE_REFERENCE)) return null;
	
			//TODO implicit requirements
			// if (!isImplicitlyRequiredForFeatureInheritance(eClassifier)))  return;
			if (c.PROFILEAPPLICATIONINUSE && eClassInfo.isExtendedMetaClass() && !c.isRoot(eClassifier)) return null;
			
			EClass eClass = (EClass) eClassifier;
							
			// EReferences and their EOpposites, if any		
			for(EReference eRef: eClass.getEAllReferences()) {
	
				// don't consider derived, not changeable, unsettable and transient references
				if(!eRef.isDerived() && eRef.isChangeable() && !eRef.isTransient()) {
	
					// eRef == no containment reference  *************************************************************/
					if(!eRef.isContainment()) {
						EReference eOpposite = eRef.getEOpposite();
	
						// and skip eRefs where it's EOpposite is a containment
						if((eOpposite!=null && !eOpposite.isContainment()) || eOpposite==null) {
	
							EClass contextType = (EClass)eRef.getEType();
	
							if (!FILTER.isAllowedAsDangling(contextType,OperationType.ADD,c.REDUCETOSUPERTYPE_ADDREMOVE))  continue;
	
							int lower = eRef.getLowerBound();
							int upper = eRef.getUpperBound();
							
							if( !eRef.isContainment() && (upper==lower)
								&& ( 
									( !EcoreHelper.isInheritedReference(eRef, eClassifier) 
									  || (EcoreHelper.isInheritedReference(eRef, eClassifier) && !c.REDUCETOSUPERTYPE_CHANGE_REFERENCE))
								   )
								){	
								ChangeReferenceGenerator generator = new ChangeReferenceGenerator(eRef, contextType, eClassifier);
								Module resultModule = generator.generate();
								
								modules.add(resultModule);
	
							}
						}
					}
				}
			}
		}
		return modules;
	}

	
	/**
	 * General variant-generation method, that finds all variants of each given
	 * module. A variant either replaces the given module or it is added as an additional
	 * module to the given set.</br></br>
	 * Variants will be created if < < create > > nodes are abstract and need a concrete replacement.
	 * Each possible replacement results in a new module variant. If more than one < < create > > node
	 * is contained in the original rule or in any of the new variants (that required adding further mandatory <<create>> nodes),
	 * the cross product of all variants will be created...</br></br>
	 * Furthermore, variants for the sub types will also be created if super type replacements of contained node types is wished.
	 * This can also result in cross products of different sub type combinations. There can also be further replaces if
	 * one of the sub type is an abstract EClass again, and so on.
	 * </br></br>
	 * Variants are necessary for the completeness and correctness of module generation.
	 * For each setup the generation process will be delegated to {@link VariantPostprocessor}
	 * 
	 * @param inputModules
	 * 			A Set of modules. For each module the replacables will be processed.
	 * @return
	 *  		Set of disparate modules representing the complete set after all necessary replacements.
	 * @throws OperationTypeNotImplementedException 
	 */
	public Set<Module> process_Replacables(Set<Module> inputModules, OperationType opType, boolean reduceToSuperType) throws OperationTypeNotImplementedException {
		
		Set<Module> modules = new HashSet<Module>();
		
		for(Module module: inputModules) {
			VariantGenerator generator = new VariantGenerator(module, opType, reduceToSuperType);
			modules.addAll(generator.generate());
		}
		
		return modules;
	}

}
