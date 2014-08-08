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
import org.sidiff.serge.configuration.Configuration;
import org.sidiff.serge.configuration.Configuration.OperationType;
import org.sidiff.serge.exceptions.ModuleForInverseCreationRequiredException;
import org.sidiff.serge.exceptions.OperationTypeNotImplementedException;
import org.sidiff.serge.filter.ElementFilter;
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
	private static Configuration c = Configuration.getInstance();
	private static EClassifierInfoManagement ECM = EClassifierInfoManagement.getInstance();
	private static ElementFilter FILTER = ElementFilter.getInstance();

	/**
	 * Singleton
	 * 
	 * @return {@link GenerationActionDelegator}
	 */
	public static GenerationActionDelegator getInstance() {
		if (GAD == null) {
			GAD = new GenerationActionDelegator();
		}
		return GAD;
	}

	/**
	 * General CREATE-generation method, that finds all relevant contexts and
	 * references that represent different CREATE-Modules for this eClassifier.
	 * For each setup the generation process will be delegated to
	 * {@link CreateGenerator}
	 * 
	 * @param eClassifier
	 * @return Set of disparate create modules for the given classifier.
	 * @throws OperationTypeNotImplementedException
	 */
	public Set<Module> generate_CREATE(EClassifier eClassifier) throws OperationTypeNotImplementedException {

		Set<Module> modules = new HashSet<Module>();

		if (c.CREATE_CREATES) {

			if (FILTER.isAllowedAsModuleBasis(eClassifier, OperationType.CREATE)) {

				EClassifierInfo eInf = ECM.getEClassifierInfo(eClassifier);

				/** In case of no Stereotype, create CREATE normally ******************************************************************************/
				if (!c.PROFILE_APPLICATION_IN_USE || (c.PROFILE_APPLICATION_IN_USE && !eInf.isStereotype())) {

					/**
					 * Create Modules for every parent in which the EClass may
					 * exist.
					 ************************************************************/
					HashMap<EReference, List<EClassifier>> optionalParents = ECM.getAllOptionalParentContext(
							eClassifier, c.REDUCETOSUPERTYPE_CREATEDELETE);
					for (Entry<EReference, List<EClassifier>> pcEntry : optionalParents.entrySet()) {
						List<EClassifier> contexts = pcEntry.getValue();
						EReference eRef = pcEntry.getKey();

						for (EClassifier context : contexts) {

							if (FILTER.isAllowedAsDangling(context, OperationType.CREATE,
									c.REDUCETOSUPERTYPE_CREATEDELETE)) {

								CreateGenerator generator = new CreateGenerator(eRef, context, eInf);
								Module resultModule = generator.generate();

								modules.add(resultModule);

							}
						}
					}
				}
				/**
				 * In case of Stereotype, there are no contexts! Just create
				 * Rule with <<create>> Node for Stereotype
				 ****************************/
				else {
					// TODO ProfileModelIntegration
				}

			}
		}
		return modules;
	}

	/**
	 * General DELETE-generation method that delegates each given CREATE-module
	 * of the input set to the inverse creation method {@link DeleteGenerator}.
	 * 
	 * @param set
	 *            of create modules
	 * @return
	 * @throws ModuleForInverseCreationRequiredException
	 * @throws OperationTypeNotImplementedException
	 */
	public Set<Module> generate_DELETE(Set<Module> createModulesSet) throws ModuleForInverseCreationRequiredException,
			OperationTypeNotImplementedException {

		Set<Module> modules = new HashSet<Module>();

		if (c.CREATE_DELETES) {

			if (!c.CREATE_CREATES)
				throw new ModuleForInverseCreationRequiredException(OperationType.DELETE);

			for (Module createModule : createModulesSet) {

				DeleteGenerator generator = new DeleteGenerator(createModule);
				Module resultModule = generator.generate();

				modules.add(resultModule);

			}
		}

		return modules;

	}

	/**
	 * General MOVE-generation method, that finds all relevant contexts and
	 * references that represent different MOVE-Modules for this eClassifier.
	 * The generated MOVE-Modules will represent moves of EClassifiers between
	 * context EClassifiers over one specific containment EReference type. For
	 * each setup the generation process will be delegated to
	 * {@link MoveGenerator}
	 * 
	 * @param eClassifier
	 * @return
	 * @throws OperationTypeNotImplementedException
	 */
	public Set<Module> generate_MOVE(EClassifier eClassifier) throws OperationTypeNotImplementedException {

		Set<Module> modules = new HashSet<Module>();

		if (c.CREATE_MOVES && FILTER.isAllowedAsModuleBasis(eClassifier, OperationType.MOVE)) {

			// get possible eClassifier Masks for additional move generation of masked classifiers.
			List<Mask> eClassifierMasks = new ArrayList<Mask>();
			eClassifierMasks.addAll(ECM.getEClassifierInfo(eClassifier).getMasks());

			// get all possible contexts (mandatory & optional) and the  according references
			HashMap<EReference, List<EClass>> allAllowedParents = FILTER.getAllAllowedParentContexts(
						eClassifier,	c.REDUCETOSUPERTYPE_MOVE, OperationType.MOVE);

			for (Entry<EReference, List<EClass>> entry : allAllowedParents.entrySet()) {

				EReference oldERefToOldParent = entry.getKey();
				List<EClass> contexts_eRefA = entry.getValue();

				// context combinations regarding unvarying eReference
				for (EClass oldParent : contexts_eRefA) {
					for (EClass newParent : contexts_eRefA) {

						// generate move (does not include masked eclassifiers)
						MoveGenerator generator = new MoveGenerator(
								eClassifier, oldERefToOldParent, oldParent, newParent);
						modules.add(generator.generate());

						// generate moves for masked eClassifiers, if any
						for (Mask mask : eClassifierMasks) {
							MoveMaskedElementGenerator generatorForMasks = new MoveMaskedElementGenerator(
									mask, oldERefToOldParent, oldParent, newParent, oldERefToOldParent, OperationType.MOVE);
							modules.add(generatorForMasks.generate());
						}
					}
				}
			}
		}

		return modules;

	}

	/**
	 * General MOVE-UP-generation method, that finds all relevant contexts and
	 * references that represent different MOVE-UP-Modules for this eClassifier.
	 * For each setup the generation process will be delegated to
	 * {@link MoveUpGenerator}
	 * 
	 * @param eClassifier
	 * @return
	 * @throws OperationTypeNotImplementedException 
	 */
	public Set<Module> generate_MOVE_UP(EClassifier eClassifier) throws OperationTypeNotImplementedException {

		Set<Module> modules = new HashSet<Module>();
		EClassifierInfo eInfo = ECM.getEClassifierInfo(eClassifier);
		
		if (c.CREATE_MOVE_UPS
			&& FILTER.isAllowedAsModuleBasis(eClassifier, OperationType.MOVE_UP)) {

			// get possible eClassifier Masks for additional move generation of masked classifiers.
			List<Mask> eClassifierMasks = new ArrayList<Mask>();
			eClassifierMasks.addAll(eInfo.getMasks());

			// get all possible contexts (mandatory & optional) and the according references
			HashMap<EReference, List<EClass>> allAllowedParents = FILTER.getAllAllowedParentContexts(
					eClassifier, c.REDUCETOSUPERTYPE_MOVE_UP, OperationType.MOVE_UP);
			
			// get all possible, recursivly contained mandatory & optional children inclusively their subtypes
			HashMap<EReference, List<EClassifier>>allDirectChildren =  eInfo.getAllDirectChildren(eClassifier);

			// find out where a child can also be a parent
			for(Entry<EReference, List<EClass>> parentRef2List: allAllowedParents.entrySet()) {
				List<EClass> parents = parentRef2List.getValue();				
				

				
				
			}
		}

		return modules;

	}

	/**
	 * General MOVE-DOWN-generation method, that finds all relevant contexts and
	 * references that represent different MOVE-DOWN-Modules for this
	 * eClassifier. For each setup the generation process will be delegated to
	 * {@link MoveDownGenerator}
	 * 
	 * @param eClassifier
	 * @return
	 * @throws OperationTypeNotImplementedException 
	 */
	public Set<Module> generate_MOVE_DOWN(EClassifier eClassifier) throws OperationTypeNotImplementedException {

		Set<Module> modules = new HashSet<Module>();

		if (c.CREATE_MOVE_DOWNS
					&& FILTER.isAllowedAsModuleBasis(eClassifier, OperationType.MOVE_DOWN)) {
			
			
		

		}
		
		return modules;

	}

	/**
	 * General MOVE-Combination-generation method, that finds all relevant
	 * contexts and references that represent different MOVE-Combination-Modules
	 * for this eClassifier. Here not one specific but all possible containment
	 * eReferences are considered. Thus, varying eReferences result in several
	 * move-modules that build up all combinations of old and new parent contexts types.
	 * For each setup the generation process will be
	 * delegated to {@link MoveReferenceCombinationGenerator}
	 * 
	 * @param eClassifier
	 * @return
	 * @throws OperationTypeNotImplementedException
	 */
	public Set<Module> generate_MOVE_REFERENCE_COMBINATION(EClassifier eClassifier)
			throws OperationTypeNotImplementedException {

		Set<Module> modules = new HashSet<Module>();

		if (c.CREATE_MOVE_REFERENCE_COMBINATIONS
				&& FILTER.isAllowedAsModuleBasis(eClassifier, OperationType.MOVE_REFERENCE_COMBINATION)) {

			// get possible eClassifier Masks for additional move generation of masked classifiers.
			List<Mask> eClassifierMasks = new ArrayList<Mask>();
			eClassifierMasks.addAll(ECM.getEClassifierInfo(eClassifier).getMasks());

			// get all possible contexts (mandatory & optional) and the according references
			HashMap<EReference, List<EClass>> allAllowedParents = FILTER.getAllAllowedParentContexts(
									eClassifier, c.REDUCETOSUPERTYPE_MOVE_REFERENCE_COMBINATION,
									OperationType.MOVE_REFERENCE_COMBINATION);
			

			for (Entry<EReference, List<EClass>> entry : allAllowedParents.entrySet()) {

				EReference oldERefToOldParent = entry.getKey();
				List<EClass> contexts_eRefA = entry.getValue();

				// context combinations regarding varying eReference
				for (EClass oldParent : contexts_eRefA) {

					// get all other EReferences
					ArrayList<EReference> allOtherEReferences = new ArrayList<EReference>();
					allOtherEReferences.addAll(allAllowedParents.keySet());
					allOtherEReferences.remove(oldParent);

					for (EReference newERefToNewParent : allOtherEReferences) {
						for (EClass newParent : allAllowedParents.get(newERefToNewParent)) {

							// generate move (does not include masked eclassifiers)
							MoveReferenceCombinationGenerator generator = new MoveReferenceCombinationGenerator(
									eClassifier, oldERefToOldParent, oldParent, newParent, newERefToNewParent);
							modules.add(generator.generate());

							// also generate all moves for masked eClassifiers, if any
							for (Mask mask : eClassifierMasks) {
								MoveMaskedElementGenerator generatorForMasks = new MoveMaskedElementGenerator(
										mask, oldERefToOldParent, oldParent, newParent, newERefToNewParent,
										OperationType.MOVE_REFERENCE_COMBINATION);
								modules.add(generatorForMasks.generate());
							}
						}
					}
				}
			}
		}

		return modules;

	}

	/**
	 * General ADD-generation method, that finds all relevant contexts and
	 * references that represent different ADD-Modules for this eClassifier. For
	 * each setup the generation process will be delegated to
	 * {@link AddGenerator}
	 * 
	 * @param eClassifier
	 * @return Set of disparate add modules for the given eclass.
	 * @throws OperationTypeNotImplementedException
	 */
	public Set<Module> generate_ADD(EClass contextClass) throws OperationTypeNotImplementedException {

		Set<Module> modules = new HashSet<Module>();

		if (c.CREATE_ADDS) {

			if (!FILTER.isAllowedAsModuleBasis(contextClass, OperationType.ADD)){
				return modules;
			}
							
			// EReferences and their EOpposites, if any
			for (EReference eRef : contextClass.getEAllReferences()) {

				// skip derived, not changeable and transient
				// references
				if (EMFMetaAccessEx.isUnconsideredStructualFeature(eRef)) {
					continue;
				}

				// skip containment references
				if (eRef.isContainment()) {
					continue;
				}

				// skip eRefs where it's EOpposite is a containment
				if (eRef.getEOpposite() != null && eRef.getEOpposite().isContainment()) {
					continue;
				}
				
				// not many
				if (!eRef.isMany()){ 
					continue;
				}	
				
				// fixed
				if (eRef.getLowerBound() == eRef.getUpperBound()){
					continue;
				}
				
				if (!FILTER.isAllowedAsDangling(eRef.getEReferenceType(), OperationType.ADD,
									c.REDUCETOSUPERTYPE_ADDREMOVE)){
					continue;
				}

				// Maybe skip when we reduce reference change to supertype
				if (c.REDUCETOSUPERTYPE_ADDREMOVE && EcoreHelper.isInheritedReference(eRef, contextClass)) {
					continue;
				}

				// Delegate to generator
				AddGenerator generator = new AddGenerator(eRef, contextClass);
				Module resultModule = generator.generate();
				modules.add(resultModule);
			}
		}
		return modules;
	}

	/**
	 * General REMOVE-generation method that delegates each given REMOVE-module
	 * of the input set to the inverse creation method {@link RemoveGenerator}.
	 * 
	 * @param set
	 *            of remove modules
	 * @return
	 * @throws ModuleForInverseCreationRequiredException
	 * @throws OperationTypeNotImplementedException
	 */
	public Set<Module> generate_REMOVE(Set<Module> addModules) throws ModuleForInverseCreationRequiredException,
			OperationTypeNotImplementedException {

		Set<Module> modules = new HashSet<Module>();

		if (c.CREATE_REMOVES) {
			
			if (!c.CREATE_ADDS)
				throw new ModuleForInverseCreationRequiredException(OperationType.REMOVE);

			for (Module addModule : addModules) {

				RemoveGenerator generator = new RemoveGenerator(addModule);
				Module resultModule = generator.generate();

				modules.add(resultModule);

			}
		}
		return modules;

	}

	/**
	 * General SET-ATTRIBUTE-generation method, that finds all relevant contexts
	 * and references that represent different SET-ATTRIBUTE for this
	 * eClassifier. For each setup the generation process will be delegated to
	 * {@link SetAttributeGenerator}
	 * 
	 * @param eClassifier
	 * @return Set of disparate set attribute modules for the given eClassifier.
	 * @throws OperationTypeNotImplementedException
	 */
	public Set<Module> generate_SET_ATTRIBUTE(EClassifier eClassifier) throws OperationTypeNotImplementedException {

		Set<Module> modules = new HashSet<Module>();

		if (c.CREATE_SET_ATTRIBUTES) {

			if (!FILTER.isAllowedAsModuleBasis(eClassifier, OperationType.SET_ATTRIBUTE)) {
				return modules;
			}

			EClass eClass = (EClass) eClassifier;

			// EAttributes which shall be considered
			List<EAttribute> easToConsider = new ArrayList<EAttribute>();
			if (c.REDUCETOSUPERTYPE_SETUNSET_ATTRIBUTES) {
				// all own eattributes
				List<EAttribute> ownEAttributes = eClass.getEAttributes();
				if (ownEAttributes != null) {
					easToConsider.addAll(ownEAttributes);
				}

				// also include all inherited EAttributes, for which SERGEe
				// Constraints are defined
				List<EAttribute> easOfConstraintsToConsider = ECM
						.getAllInheritedEAttributesInvolvedInConstraints(eClassifier);
				if (easOfConstraintsToConsider != null) {
					easToConsider.addAll(easOfConstraintsToConsider);
				}

			} else {
				// all inherited eattributes
				easToConsider = eClass.getEAllAttributes();
			}

			for (EAttribute ea : easToConsider) {
				// don't consider derived, not changeable, and transient
				// attributes
				if (!EMFMetaAccessEx.isUnconsideredStructualFeature(ea)) {

					int lowerBound = ea.getLowerBound();
					int upperBound = ea.getUpperBound();

					/********** un-supported yet: isMany *************************************************************************************/
					if ((lowerBound == 0) && (upperBound == -1)) {
						// TODO multivalued eattribs
						System.out.println("----------------ea:" + ea.getName() + " of "
								+ ea.eContainer().eClass().getName() + "isMany: [" + lowerBound + "," + upperBound
								+ "]--------------");
					} else if ((lowerBound == 1) && (upperBound == -1)) {
						// TODO multivalued eattribs
						System.out.println("----------------ea:" + ea.getName() + " of "
								+ ea.eContainer().eClass().getName() + "isMany: [" + lowerBound + "," + upperBound
								+ "]--------------");
					}

					/********** SET / UNSET ATTRIBUTES *******************************************************************************/
					else if (((lowerBound == 0) && (upperBound == 1)) || ((lowerBound == 1) && (upperBound == 1))) {

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
	 * General UNSET-ATTRIBUTE-generation method that delegates each given
	 * UNSET-ATTRIBUTE-module of the input set to the inverse creation method
	 * {@link UnsetAttributeGenerator}. <br/>
	 * Whereby the inverse can be considered as the resetting to default values
	 * if any.
	 * 
	 * @param set
	 *            of set attribute modules
	 * @return
	 * @throws ModuleForInverseCreationRequiredException
	 * @throws OperationTypeNotImplementedException
	 */
	public Set<Module> generate_UNSET_ATTRIBUTE(Set<Module> setAttributeModules)
			throws ModuleForInverseCreationRequiredException, OperationTypeNotImplementedException {
		Set<Module> modules = new HashSet<Module>();

		if (c.CREATE_UNSET_ATTRIBUTES) {
			if (!c.CREATE_SET_ATTRIBUTES) {
				throw new ModuleForInverseCreationRequiredException(OperationType.UNSET_ATTRIBUTE);
			}

			for (Module setAttributeModule : setAttributeModules) {
				UnsetAttributeGenerator generator = new UnsetAttributeGenerator(setAttributeModule);
				Module resultModule = generator.generate();
				modules.add(resultModule);
			}
		}

		return modules;
	}

	/**
	 * General SET-REFERENCE-generation method, that finds all relevant contexts
	 * and references that represent different SET-REFERENCE for this
	 * eClassifier. For each setup the generation process will be delegated to
	 * {@link SetReferenceGenerator}
	 * 
	 * @param eClassifier
	 * @return Set of disparate set reference modules for the given eClassifier.
	 * @throws OperationTypeNotImplementedException
	 */
	public Set<Module> generate_SET_REFERENCE(EClass eClassifier) throws OperationTypeNotImplementedException {
		Set<Module> modules = new HashSet<Module>();

		if (c.CREATE_SET_REFERENCES) {

			if (!FILTER.isAllowedAsModuleBasis(eClassifier, OperationType.ADD))
				return modules;

			EClass eClass = (EClass) eClassifier;

			// EReferences and their EOpposites, if any
			for (EReference eRef : eClass.getEAllReferences()) {

				// don't consider derived, not changeable, and transient
				// references
				if (!EMFMetaAccessEx.isUnconsideredStructualFeature(eRef)) {

					// eRef == no containment reference
					// *************************************************************/
					if (!eRef.isContainment()) {
						EReference eOpposite = eRef.getEOpposite();

						// and skip eRefs where it's EOpposite is a containment
						if ((eOpposite != null && !eOpposite.isContainment()) || eOpposite == null) {

							EClassifier target = eRef.getEType();

							if (!FILTER.isAllowedAsDangling(target, OperationType.ADD, c.REDUCETOSUPERTYPE_ADDREMOVE))
								continue;

							int lower = eRef.getLowerBound();
							int upper = eRef.getUpperBound();

							// eRef == no containment reference and (0..1)
							// continue only
							// if it has no EOpposites which has ub-lb>1 (this is already covered in ADD_Rules) 
							// and if ref is either inherited and no
							// reduction to super type is wished
							// or ref is not inherited
							if (!eRef.isContainment() && lower == 0	&& upper == 1
									&& !(eRef.getEOpposite()!=null && eRef.getEOpposite().getUpperBound()-eRef.getEOpposite().getLowerBound()>1)
									&& ((EcoreHelper.isInheritedReference(eRef, eClassifier) && !c.REDUCETOSUPERTYPE_SETUNSET_REFERENCES) || !EcoreHelper
											.isInheritedReference(eRef, eClassifier))) {

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
	 * General UNSET-REFERENCE-generation method that delegates each given
	 * UNSET-REFERENCE-module of the input set to the inverse creation method
	 * {@link UnsetReferenceGenerator}. <br/>
	 * Whereby the inverse can be considered as the resetting to default values
	 * if any.
	 * 
	 * @param set
	 *            of set reference modules
	 * @return
	 * @throws OperationTypeNotImplementedException
	 * @throws ModuleForInverseCreationRequiredException
	 */
	public Set<Module> generate_UNSET_REFERENCE(Set<Module> setReferenceModules)
			throws OperationTypeNotImplementedException, ModuleForInverseCreationRequiredException {

		Set<Module> modules = new HashSet<Module>();

		if (c.CREATE_UNSET_REFERENCES) {

			if (!c.CREATE_SET_REFERENCES)
				throw new ModuleForInverseCreationRequiredException(OperationType.UNSET_REFERENCE);

			for (Module setReferenceModule : setReferenceModules) {

				UnsetReferenceGenerator generator = new UnsetReferenceGenerator(setReferenceModule);
				Module resultModule = generator.generate();

				modules.add(resultModule);

			}
		}

		return modules;

	}

	/**
	 * General CHANGe-generation method, that finds all relevant EAttributes
	 * that require CHANGe-Modules for different EEnumLiterals For each setup
	 * the generation process will be delegated to
	 * {@link ChangeLiteralGenerator}
	 * 
	 * @param eClassifier
	 * @return
	 * @throws OperationTypeNotImplementedException
	 */
	public Set<Module> generate_CHANGE_Literals(EClassifier eClassifier) throws OperationTypeNotImplementedException {

		Set<Module> modules = new HashSet<Module>();

		if (c.CREATE_CHANGE_LITERALS) {

			if (!FILTER.isAllowedAsModuleBasis(eClassifier, OperationType.ADD))
				return modules;

			EClass eClass = (EClass) eClassifier;

			// EAttributes which shall be considered
			List<EAttribute> easToConsider = new ArrayList<EAttribute>();
			if (c.REDUCETOSUPERTYPE_CHANGE_LITERALS) {
				// all own eattributes
				List<EAttribute> ownEAttributes = eClass.getEAttributes();
				if (ownEAttributes != null) {
					easToConsider.addAll(ownEAttributes);
				}

				// also include all inherited EAttributes, for which SERGEe
				// Constraints are defined
				List<EAttribute> easOfConstraintsToConsider = ECM
						.getAllInheritedEAttributesInvolvedInConstraints(eClassifier);
				if (easOfConstraintsToConsider != null) {
					easToConsider.addAll(easOfConstraintsToConsider);
				}

			} else {
				// all inherited eattributes
				easToConsider = eClass.getEAllAttributes();
			}

			for (EAttribute ea : easToConsider) {
				// don't consider derived, not changeable, unsettable and
				// transient references
				if (!ea.isDerived() && !ea.isTransient() && ea.isChangeable()) {

					int lowerBound = ea.getLowerBound();
					int upperBound = ea.getUpperBound();

					if ((lowerBound == 1) && (upperBound == 1) && (ea.getEType() instanceof EEnum)) {

						EEnum eenum = (EEnum) ea.getEType();

						// build up combinations
						Map<EEnumLiteral, List<EEnumLiteral>> combinations = new HashMap<EEnumLiteral, List<EEnumLiteral>>();

						for (EEnumLiteral literal : eenum.getELiterals()) {
							combinations.put(literal, new ArrayList<EEnumLiteral>(eenum.getELiterals()));
						}// here combination with self is still included.

						// build CHANGE modules for every combination:
						for (Entry<EEnumLiteral, List<EEnumLiteral>> entry : combinations.entrySet()) {
							EEnumLiteral oldLiteral = entry.getKey();

							// to remove combination with self, remove self from
							// old values
							entry.getValue().remove(oldLiteral);

							// now create module for every literal combination
							for (EEnumLiteral newLiteral : entry.getValue()) {

								ChangeLiteralGenerator generator = new ChangeLiteralGenerator(ea, oldLiteral,
										eClassifier, oldLiteral, newLiteral);
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
	 * General CHANGE-generation method, that finds all relevant references that
	 * require CHANGE-Modules. For each setup the generation process will be
	 * delegated to {@link ChangeReferenceGenerator}
	 * 
	 * @param eClassifier
	 * @return
	 * @throws OperationTypeNotImplementedException
	 */
	public Set<Module> generate_CHANGE_Reference(EClass contextClass) throws OperationTypeNotImplementedException {

		Set<Module> modules = new HashSet<Module>();

		if (c.CREATE_CHANGE_REFERENCES) {

			if (!FILTER.isAllowedAsModuleBasis(contextClass, OperationType.CHANGE_REFERENCE)) {
				return modules;
			}

			// EReferences and their EOpposites, if any
			for (EReference eRef : contextClass.getEAllReferences()) {

				// skip derived, not changeable and transient
				// references
				if (EMFMetaAccessEx.isUnconsideredStructualFeature(eRef)) {
					continue;
				}

				// skip containment references
				if (eRef.isContainment()) {
					continue;
				}

				// skip eRefs where it's EOpposite is a containment
				if (eRef.getEOpposite() != null && eRef.getEOpposite().isContainment()) {
					continue;
				}

				// Change operation only when lowerBound=upperBound
				if (eRef.getLowerBound() != eRef.getUpperBound()) {
					continue;
				}

				// Filter, depends on configuration
				if (!FILTER.isAllowedAsDangling(eRef.getEReferenceType(), OperationType.CHANGE_REFERENCE,
						c.REDUCETOSUPERTYPE_CHANGE_REFERENCE)) {
					continue;
				}

				// Maybe skip when we reduce reference change to supertype
				if (c.REDUCETOSUPERTYPE_CHANGE_REFERENCE && EcoreHelper.isInheritedReference(eRef, contextClass)) {
					continue;
				}

				// Delegate to generator
				ChangeReferenceGenerator generator = new ChangeReferenceGenerator(eRef, contextClass);
				Module resultModule = generator.generate();
				modules.add(resultModule);
			}
		}
		
		return modules;
	}

	/**
	 * General variant-generation method, that finds all variants of each given
	 * module. A variant either replaces the given module or it is added as an
	 * additional module to the given set.</br></br> Variants will be created if
	 * < < create > > nodes are abstract and need a concrete replacement. Each
	 * possible replacement results in a new module variant. If more than one <
	 * < create > > node is contained in the original rule or in any of the new
	 * variants (that required adding further mandatory <<create>> nodes), the
	 * cross product of all variants will be created...</br></br> Furthermore,
	 * variants for the sub types will also be created if super type
	 * replacements of contained node types is wished. This can also result in
	 * cross products of different sub type combinations. There can also be
	 * further replaces if one of the sub type is an abstract EClass again, and
	 * so on. </br></br> Variants are necessary for the completeness and
	 * correctness of module generation. For each setup the generation process
	 * will be delegated to {@link VariantPostprocessor}
	 * @param inputModules
	 *            A Set of modules. For each module the replacables will be
	 *            processed.
	 * @return Set of disparate modules representing the complete set after all
	 *         necessary replacements.
	 * @throws OperationTypeNotImplementedException
	 * @throws ModuleViolatesConsistencyException 
	 */
	public Set<Module> process_Replacables(Set<Module> inputModules, OperationType opType, boolean reduceToSuperType)
			throws OperationTypeNotImplementedException {

		Set<Module> modules = new HashSet<Module>();
		if(!c.DISABLE_VARIANTS_BY_SUPERTYPE_REPLACEMENT) {
			
			for (Module module : inputModules) {
				VariantGenerator generator = new VariantGenerator(module, opType, reduceToSuperType);
				modules.addAll(generator.generate());
			}
		}
		return modules;

	}
	

}
