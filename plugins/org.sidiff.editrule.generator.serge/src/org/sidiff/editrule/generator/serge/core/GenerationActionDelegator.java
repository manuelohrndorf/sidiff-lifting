package org.sidiff.editrule.generator.serge.core;

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
import org.sidiff.common.emf.access.EMFMetaAccess;
import org.sidiff.common.emf.metamodel.analysis.ContainmentCycle;
import org.sidiff.common.emf.metamodel.analysis.ContainmentCyclePathStep;
import org.sidiff.common.emf.metamodel.analysis.EClassifierInfo;
import org.sidiff.common.emf.metamodel.analysis.EClassifierInfoManagement;
import org.sidiff.common.emf.metamodel.analysis.EReferenceInfo;
import org.sidiff.common.emf.metamodel.analysis.Mask;
import org.sidiff.editrule.generator.exceptions.OperationTypeNotImplementedException;
import org.sidiff.editrule.generator.serge.configuration.Configuration;
import org.sidiff.editrule.generator.serge.configuration.Configuration.OperationTypeGroup;
import org.sidiff.editrule.generator.serge.exceptions.ModuleForInverseCreationRequiredException;
import org.sidiff.editrule.generator.serge.filter.ClassifierInclusionConfiguration;
import org.sidiff.editrule.generator.serge.filter.ElementFilter;
import org.sidiff.editrule.generator.serge.generators.actions.AddGenerator;
import org.sidiff.editrule.generator.serge.generators.actions.AttachGenerator;
import org.sidiff.editrule.generator.serge.generators.actions.ChangeLiteralGenerator;
import org.sidiff.editrule.generator.serge.generators.actions.ChangeReferenceGenerator;
import org.sidiff.editrule.generator.serge.generators.actions.ConsolidatedVariantGenerator;
import org.sidiff.editrule.generator.serge.generators.actions.CreateGenerator;
import org.sidiff.editrule.generator.serge.generators.actions.DeleteGenerator;
import org.sidiff.editrule.generator.serge.generators.actions.DetachGenerator;
import org.sidiff.editrule.generator.serge.generators.actions.MoveDownGenerator;
import org.sidiff.editrule.generator.serge.generators.actions.MoveGenerator;
import org.sidiff.editrule.generator.serge.generators.actions.MoveMaskedElementGenerator;
import org.sidiff.editrule.generator.serge.generators.actions.MoveReferenceCombinationGenerator;
import org.sidiff.editrule.generator.serge.generators.actions.MoveUpGenerator;
import org.sidiff.editrule.generator.serge.generators.actions.RemoveGenerator;
import org.sidiff.editrule.generator.serge.generators.actions.SetAttributeGenerator;
import org.sidiff.editrule.generator.serge.generators.actions.SetReferenceGenerator;
import org.sidiff.editrule.generator.serge.generators.actions.SolitaryVariantGenerator;
import org.sidiff.editrule.generator.serge.generators.actions.UnsetAttributeGenerator;
import org.sidiff.editrule.generator.serge.generators.actions.UnsetReferenceGenerator;
import org.sidiff.editrule.generator.types.OperationType;

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

		Set<Module> modules = new HashSet<>();

		if (c.isRuleCreationEnabled(OperationType.CREATE)) {

			if (FILTER.isAllowedAsFocused(eClassifier, OperationType.CREATE)) {

				EClassifierInfo eInf = ECM.getEClassifierInfo(eClassifier);

				HashMap<EReference, List<EClassifier>> optionalParents = ECM.getAllOptionalParentContext(eClassifier,
						c.preferSuperTypesOnContexts(OperationTypeGroup.CREATE_DELETE));

				// In case there are no parents (i.e., incoming eContainment
				// references)
				// and this eClassifier is no EEnum, we may create CPEOs
				// to create the eClassifier instance without setting up a
				// connected context
				if (optionalParents.isEmpty() && !(eClassifier instanceof EEnum)) {

					CreateGenerator generator = new CreateGenerator(null, null, eInf);
					Module resultModule = generator.generate();

					modules.add(resultModule);

				}
				// Otherwise (if there are contexts),
				// Just create a node for the eClassifier and refrain from
				// setting up a context node to connect
				else {

					for (Entry<EReference, List<EClassifier>> pcEntry : optionalParents.entrySet()) {
						List<EClassifier> contexts = pcEntry.getValue();
						EReference eRef = pcEntry.getKey();

						for (EClassifier context : contexts) {

							if (FILTER.isAllowedAsParentContext(context)) {

								CreateGenerator generator = new CreateGenerator(eRef, context, eInf);
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
	 * General DELETE-generation method that delegates each given CREATE-module
	 * of the input set to the inverse creation method {@link DeleteGenerator}.
	 * 
	 * @param set
	 *            of create modules
	 * @return
	 * @throws ModuleForInverseCreationRequiredException
	 * @throws OperationTypeNotImplementedException
	 */
	public Set<Module> generate_DELETE(Set<Module> createModulesSet)
			throws ModuleForInverseCreationRequiredException, OperationTypeNotImplementedException {

		Set<Module> modules = new HashSet<>();

		if (c.isRuleCreationEnabled(OperationType.DELETE)) {

			if (!c.isRuleCreationEnabled(OperationType.CREATE))
				throw new ModuleForInverseCreationRequiredException(OperationType.DELETE);

			for (Module createModule : createModulesSet) {

				DeleteGenerator generator = new DeleteGenerator(createModule);
				Module resultModule = generator.generate();

				modules.add(resultModule);

			}
		}

		return modules;

	}

	public Set<Module> generate_ATTACH(EClassifier eClassifier) throws OperationTypeNotImplementedException {
		Set<Module> modules = new HashSet<>();
		if (c.isRuleCreationEnabled(OperationType.ATTACH)) {
			if (FILTER.isAllowedAsFocused(eClassifier, OperationType.ATTACH)) {
				EClassifierInfo eInf = ECM.getEClassifierInfo(eClassifier);
				if (!eInf.isStereotype())
					return modules;
				AttachGenerator generator = new AttachGenerator(eClassifier);
				modules.addAll(generator.generate());
			}
		}
		return modules;
	}

	public Set<Module> generate_DETACH(Set<Module> attachModuleSet)
			throws OperationTypeNotImplementedException, ModuleForInverseCreationRequiredException {
		Set<Module> modules = new HashSet<>();
		if (c.isRuleCreationEnabled(OperationType.DETACH)) {
			if (!c.isRuleCreationEnabled(OperationType.ATTACH))
				throw new ModuleForInverseCreationRequiredException(OperationType.DETACH);
			for (Module attachModule : attachModuleSet) {
				DetachGenerator detachGenerator = new DetachGenerator(attachModule);
				modules.add(detachGenerator.generate());
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

		Set<Module> modules = new HashSet<>();

		if (c.isRuleCreationEnabled(OperationType.MOVE) && FILTER.isAllowedAsFocused(eClassifier, OperationType.MOVE)) {

			// get possible eClassifier Masks for additional move generation of
			// masked classifiers.
			List<Mask> eClassifierMasks = new ArrayList<>();
			eClassifierMasks.addAll(ECM.getEClassifierInfo(eClassifier).getMasks());

			// get all possible contexts (mandatory & optional) and the
			// according references
			HashMap<EReference, List<EClass>> allAllowedParents = FILTER.getAllAllowedParentContexts(eClassifier,
					c.preferSuperTypesOnContexts(OperationTypeGroup.MOVE), OperationType.MOVE);

			for (Entry<EReference, List<EClass>> entry : allAllowedParents.entrySet()) {

				EReference oldERefToOldParent = entry.getKey();
				List<EClass> contexts_eRefA = entry.getValue();

				// context combinations regarding unvarying eReference
				for (EClass oldParent : contexts_eRefA) {
					for (EClass newParent : contexts_eRefA) {

						// generate move (does not include masked eclassifiers)
						MoveGenerator generator = new MoveGenerator(eClassifier, oldERefToOldParent, oldParent,
								newParent);
						modules.add(generator.generate());

						// generate moves for masked eClassifiers, if any
						for (Mask mask : eClassifierMasks) {
							MoveMaskedElementGenerator generatorForMasks = new MoveMaskedElementGenerator(mask,
									oldERefToOldParent, oldParent, newParent, oldERefToOldParent, OperationType.MOVE);
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

		Set<Module> modules = new HashSet<>();
		EClassifierInfo eInfo = ECM.getEClassifierInfo(eClassifier);

		if (c.isRuleCreationEnabled(OperationType.MOVE_UP)
				&& FILTER.isAllowedAsFocused(eClassifier, OperationType.MOVE_UP)) {

			Set<ContainmentCycle> ccSet = eInfo.getContainmentCycles();
			for (ContainmentCycle cc : ccSet) {
				// *** on outer circle **********
				if (!cc.isInnerCircle()) {

					ContainmentCyclePathStep step = cc.getBackwardPointingStep();
					EReference eRef = step.getTargetingReference();
					EClassifier parent = (EClassifier) eRef.eContainer();

					if (c.isRuleCreationEnabled(OperationType.MOVE_UP)
							&& FILTER.isAllowedAsParentContext(parent)) {

						// TODO check every entry in path is allowed as
						// dangling?

						MoveUpGenerator generator = new MoveUpGenerator(eClassifier, cc);
						modules.add(generator.generate());

					}
				}
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

		Set<Module> modules = new HashSet<>();
		EClassifierInfo eInfo = ECM.getEClassifierInfo(eClassifier);

		if (c.isRuleCreationEnabled(OperationType.MOVE_DOWN)
				&& FILTER.isAllowedAsFocused(eClassifier, OperationType.MOVE_DOWN)) {

			Set<ContainmentCycle> ccSet = eInfo.getContainmentCycles();
			for (ContainmentCycle cc : ccSet) {
				// *** on outer circle **********
				if (!cc.isInnerCircle()) {

					ContainmentCyclePathStep step = cc.getBackwardPointingStep();
					EReference eRef = step.getTargetingReference();
					EClassifier parent = (EClassifier) eRef.eContainer();

					if (c.isRuleCreationEnabled(OperationType.MOVE_DOWN)
							&& FILTER.isAllowedAsParentContext(parent)) {

						// TODO check every entry in path is allowed as
						// dangling?

						MoveDownGenerator generator = new MoveDownGenerator(eClassifier, cc);
						modules.add(generator.generate());

					}
				}
			}

		}

		return modules;

	}

	/**
	 * General MOVE-Combination-generation method, that finds all relevant
	 * contexts and references that represent different MOVE-Combination-Modules
	 * for this eClassifier. Here not one specific but all possible containment
	 * eReferences are considered. Thus, varying eReferences result in several
	 * move-modules that build up all combinations of old and new parent
	 * contexts types. For each setup the generation process will be delegated
	 * to {@link MoveReferenceCombinationGenerator}
	 * 
	 * @param eClassifier
	 * @return
	 * @throws OperationTypeNotImplementedException
	 */
	public Set<Module> generate_MOVE_REFERENCE_COMBINATION(EClassifier eClassifier)
			throws OperationTypeNotImplementedException {

		Set<Module> modules = new HashSet<>();

		if (c.isRuleCreationEnabled(OperationType.MOVE_REFERENCE_COMBINATION)
				&& FILTER.isAllowedAsFocused(eClassifier, OperationType.MOVE_REFERENCE_COMBINATION)) {

			// get possible eClassifier Masks for additional move generation of
			// masked classifiers.
			List<Mask> eClassifierMasks = new ArrayList<>();
			eClassifierMasks.addAll(ECM.getEClassifierInfo(eClassifier).getMasks());

			// get all possible contexts (mandatory & optional) and the
			// according references
			HashMap<EReference, List<EClass>> allAllowedParents = FILTER.getAllAllowedParentContexts(eClassifier,
					c.preferSuperTypesOnContexts(OperationTypeGroup.MOVE_REFERENCE_COMBINATION),
					OperationType.MOVE_REFERENCE_COMBINATION);

			for (Entry<EReference, List<EClass>> entry : allAllowedParents.entrySet()) {

				EReference oldERefToOldParent = entry.getKey();
				List<EClass> contexts_eRefA = entry.getValue();

				// context combinations regarding varying eReference
				for (EClass oldParent : contexts_eRefA) {

					// get all other EReferences
					for (EReference newERefToNewParent : allAllowedParents.keySet()) {
						for (EClass newParent : allAllowedParents.get(newERefToNewParent)) {

							// generate move (does not include masked
							// eclassifiers)
							MoveReferenceCombinationGenerator generator = new MoveReferenceCombinationGenerator(
									eClassifier, oldERefToOldParent, oldParent, newParent, newERefToNewParent);
							modules.add(generator.generate());

							// also generate all moves for masked eClassifiers,
							// if any
							for (Mask mask : eClassifierMasks) {
								MoveMaskedElementGenerator generatorForMasks = new MoveMaskedElementGenerator(mask,
										oldERefToOldParent, oldParent, newParent, newERefToNewParent,
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
	 * references that represent different ADD-Modules for this context. Each
	 * target class type, which will be selected for an addition, is retrieved
	 * from the given eReferences target type. Note: we don't need super type
	 * replacements for target class. The reason is that the target class will
	 * be connected as a preserved node and have a parameter "existing" by which
	 * can be controlled which exact sub type of the target class should be
	 * used. For each setup the generation process will be delegated to
	 * {@link AddGenerator}
	 * The contextClass here represents the source-EClass from which the
	 * new reference will point to an additional target EClass.
	 * 
	 * @param eClassifier
	 * @return Set of disparate add modules for the given eclass.
	 * @throws OperationTypeNotImplementedException
	 */
	public Set<Module> generate_ADD(EClass contextClass) throws OperationTypeNotImplementedException {

		Set<Module> modules = new HashSet<>();

		if (c.isRuleCreationEnabled(OperationType.ADD)) {

			if (!FILTER.isAllowedAsFocused(contextClass, OperationType.ADD)) {
				return modules;
			}

			// EReferences and their EOpposites, if any
			for (EReference eRef : contextClass.getEAllReferences()) {

				// skip derived, not changeable and transient
				// references
				if (EMFMetaAccess.isUnconsideredStructualFeature(eRef)) {
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
				if (!eRef.isMany()) {
					continue;
				}

				// fixed
				if (eRef.getLowerBound() == eRef.getUpperBound()) {
					continue;
				}

				if (!FILTER.isAllowedAsDangling(eRef.getEReferenceType(), OperationType.ADD)) {
					continue;
				}

				// Maybe skip when we reduce reference change to supertype
				if (c.preferSuperTypesOnDanglings(OperationTypeGroup.ADD_REMOVE)
						&& EReferenceInfo.isInheritedReference(eRef, contextClass)) {
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
	public Set<Module> generate_REMOVE(Set<Module> addModules)
			throws ModuleForInverseCreationRequiredException, OperationTypeNotImplementedException {

		Set<Module> modules = new HashSet<>();

		if (c.isRuleCreationEnabled(OperationType.REMOVE)) {

			if (!c.isRuleCreationEnabled(OperationType.ADD))
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

		Set<Module> modules = new HashSet<>();

		if (c.isRuleCreationEnabled(OperationType.SET_ATTRIBUTE)) {

			if (!FILTER.isAllowedAsFocused(eClassifier, OperationType.SET_ATTRIBUTE)) {
				return modules;
			}

			EClass eClass = (EClass) eClassifier;

			// EAttributes which shall be considered
			List<EAttribute> easToConsider = new ArrayList<>();
			ClassifierInclusionConfiguration CIC = ClassifierInclusionConfiguration.getInstance();
			
			
			// find which inherited attributes should also be considered
			for(EClassifier superType: eClass.getEAllSuperTypes()) {
				if (!FILTER.shouldInheritedFeaturesOnlyBeGeneratedForItsContainingSupertype(superType, OperationTypeGroup.SET_UNSET_ATTRIBUTE)) {
					for(EAttribute ea: ((EClass)superType).getEAttributes()) {
						EClassifier container = (EClassifier) ea.eContainer();
						if(!ea.eContainer().equals(eClass)
								&& CIC.getFocusInclusionType(container)==null) {
							easToConsider.add(ea);
						}
					}
					
				}
			}
			
			// all own eattributes
			easToConsider.addAll(eClass.getEAttributes());			
		
			// Now process all gathered attributes
			for (EAttribute ea : easToConsider) {
				// don't consider derived, not changeable, and transient
				// attributes
				if (!EMFMetaAccess.isUnconsideredStructualFeature(ea)) {

					int lowerBound = ea.getLowerBound();
					int upperBound = ea.getUpperBound();

					/**********
					 * un-supported yet: isMany
					 *************************************************************************************/
					if ((lowerBound == 0) && (upperBound == -1)) {
						// TODO multivalued eattribs
						System.out.println(
								"----------------ea:" + ea.getName() + " of " + ea.eContainer().eClass().getName()
										+ "isMany: [" + lowerBound + "," + upperBound + "]--------------");
					} else if ((lowerBound == 1) && (upperBound == -1)) {
						// TODO multivalued eattribs
						System.out.println(
								"----------------ea:" + ea.getName() + " of " + ea.eContainer().eClass().getName()
										+ "isMany: [" + lowerBound + "," + upperBound + "]--------------");
					}

					/**********
					 * SET / UNSET ATTRIBUTES
					 *******************************************************************************/
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
		Set<Module> modules = new HashSet<>();

		if (c.isRuleCreationEnabled(OperationType.UNSET_ATTRIBUTE)) {
			if (!c.isRuleCreationEnabled(OperationType.SET_ATTRIBUTE)) {
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
		Set<Module> modules = new HashSet<>();

		if (c.isRuleCreationEnabled(OperationType.SET_REFERENCE)) {

			if (!FILTER.isAllowedAsFocused(eClassifier, OperationType.ADD))
				return modules;

			EClass eClass = eClassifier;
			List<EReference> eRefsToConsider = new ArrayList<>();
			
			// Findout which EReferences should be considered
			ClassifierInclusionConfiguration CIC = ClassifierInclusionConfiguration.getInstance();
			
			// find which inherited eReferences should also be considered
			for(EClassifier superType: eClass.getEAllSuperTypes()) {
				if (!FILTER.shouldInheritedFeaturesOnlyBeGeneratedForItsContainingSupertype(superType, OperationTypeGroup.SET_UNSET_ATTRIBUTE)) {
					for(EReference eRef: ((EClass)superType).getEReferences()) {
						EClassifier container = (EClassifier) eRef.eContainer();
						if(!eRef.eContainer().equals(eClass)
								&& CIC.getFocusInclusionType(container)==null) {
							eRefsToConsider.add(eRef);
						}
					}
					
				}
			}
			
			// all own eattributes
			eRefsToConsider.addAll(eClass.getEReferences());			
		
			// Now process all gathered References and their EOpposites
			for (EReference eRef : eRefsToConsider) {

				// don't consider derived, not changeable, and transient
				// references
				if (!EMFMetaAccess.isUnconsideredStructualFeature(eRef)) {

					// eRef == no containment reference
					// *************************************************************/
					if (!eRef.isContainment()) {
						EReference eOpposite = eRef.getEOpposite();

						// and skip eRefs where it's EOpposite is a containment
						if ((eOpposite != null && !eOpposite.isContainment()) || eOpposite == null) {

							EClassifier target = eRef.getEType();

							if (!FILTER.isAllowedAsDangling(target, OperationType.SET_REFERENCE))
								continue;

							int lower = eRef.getLowerBound();
							int upper = eRef.getUpperBound();

							// eRef == no containment reference and (0..1)
							// continue only
							// if it has no EOpposites which has ub-lb>1 (this
							// is already covered in ADD_Rules)
							// and if ref is either inherited and no
							// reduction to super type is wished
							// or ref is not inherited
							if (!eRef.isContainment() && lower == 0 && upper == 1
									&& !(eRef.getEOpposite() != null && eRef.getEOpposite().getUpperBound()
											- eRef.getEOpposite().getLowerBound() > 1)
									&& ((EReferenceInfo.isInheritedReference(eRef, eClassifier)
											&& !c.preferSuperTypesOnDanglings(OperationTypeGroup.SET_UNSET_REFERENCE))
											|| !EReferenceInfo.isInheritedReference(eRef, eClassifier))) {

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

		Set<Module> modules = new HashSet<>();

		if (c.isRuleCreationEnabled(OperationType.UNSET_REFERENCE)) {

			if (!c.isRuleCreationEnabled(OperationType.SET_REFERENCE))
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

		Set<Module> modules = new HashSet<>();

		if (c.isRuleCreationEnabled(OperationType.CHANGE_LITERAL)) {

			if (!FILTER.isAllowedAsFocused(eClassifier, OperationType.ADD))
				return modules;

			EClass eClass = (EClass) eClassifier;

			// EAttributes which shall be considered
			List<EAttribute> easToConsider = new ArrayList<>();
			ClassifierInclusionConfiguration CIC = ClassifierInclusionConfiguration.getInstance();
			if (c.preferSuperTypesOnContexts(OperationTypeGroup.CHANGE_LITERAL)) {
				// if reduceToContext is enabled but the super type itself was not
				// added by the user on the config to generate modules for
				// then there will be lots of rules missing. Thus, the following
				// workaround for missing super type config declarations adds only the
				// own and inherited eattributes of missing super types to this list:
				for(EAttribute ea: eClass.getEAllAttributes()) {
					EClassifier container = (EClassifier) ea.eContainer();
					if(!ea.eContainer().equals(eClass)
							&& CIC.getFocusInclusionType(container)==null) {
						easToConsider.add(ea);
					}
				}
				// all own eattributes
				easToConsider.addAll(eClass.getEAttributes());
			} else {
				// all own and inherited eattributes
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
						Map<EEnumLiteral, List<EEnumLiteral>> combinations = new HashMap<>();

						for (EEnumLiteral literal : eenum.getELiterals()) {
							combinations.put(literal, new ArrayList<>(eenum.getELiterals()));
						} // here combination with self is still included.

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

		Set<Module> modules = new HashSet<>();

		if (c.isRuleCreationEnabled(OperationType.CHANGE_REFERENCE)) {

			if (!FILTER.isAllowedAsFocused(contextClass, OperationType.CHANGE_REFERENCE)) {
				return modules;
			}
			
			List<EReference> eRefsToConsider = new ArrayList<>();
			
			// Findout which EReferences should be considered
			ClassifierInclusionConfiguration CIC = ClassifierInclusionConfiguration.getInstance();
			if (c.preferSuperTypesOnContexts(OperationTypeGroup.CHANGE_REFERENCE)) {
				// if reduceToContext is enabled but the super type itself was not
				// added by the user on the config to generate modules for
				// then there will be lots of rules missing. Thus, the following
				// workaround for missing super type config declarations adds only the
				// own and inherited EReferences of missing super types to this list:
				for(EReference eRef: contextClass.getEAllReferences()) {
					EClassifier container = (EClassifier) eRef.eContainer();
					if(!eRef.eContainer().equals(contextClass)
							&& CIC.getFocusInclusionType(container)==null) {
						eRefsToConsider.add(eRef);
					}
				}
				// all own EReferences
				eRefsToConsider.addAll(contextClass.getEReferences());
			} else {
				// all own and inherited EReferences
				eRefsToConsider = contextClass.getEAllReferences();
			}

			// EReferences and their EOpposites, if any
			for (EReference eRef : eRefsToConsider) {

				// skip derived, not changeable and transient
				// references
				if (EMFMetaAccess.isUnconsideredStructualFeature(eRef)) {
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
				if (!FILTER.isAllowedAsDangling(eRef.getEReferenceType(), OperationType.CHANGE_REFERENCE)) {
					continue;
				}

				// Maybe skip when we reduce reference change to supertype
				if (c.preferSuperTypesOnDanglings(OperationTypeGroup.CHANGE_REFERENCE)
						&& EReferenceInfo.isInheritedReference(eRef, contextClass)) {
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
	 * additional module to the given set.</br>
	 * </br>
	 * Variants will be created if < < create > > nodes are abstract and need a
	 * concrete replacement. Each possible replacement results in a new module
	 * variant. If more than one < < create > > node is contained in the
	 * original rule or in any of the new variants (that required adding further
	 * mandatory <<create>> nodes), the cross product of all variants will be
	 * created...</br>
	 * </br>
	 * Furthermore, variants for the sub types will also be created if super
	 * type replacements of contained node types is wished. This can also result
	 * in cross products of different sub type combinations. There can also be
	 * further replaces if one of the sub type is an abstract EClass again, and
	 * so on. </br>
	 * </br>
	 * Variants are necessary for the completeness and correctness of module
	 * generation. For each setup the generation process will be delegated to
	 * {@link VariantPostprocessor}
	 * </br>
	 * Variants are either generated into solitary modules (solitary variant generator)
	 * or they are consolidated into the correlating, general module (consolidated variant generator)
	 * 
	 * @param inputModules
	 *            A Set of modules. For each module the replacables will be
	 *            processed.
	 * @return Set of disparate modules representing the complete set after all
	 *         necessary replacements.
	 * @throws OperationTypeNotImplementedException
	 * @throws ModuleViolatesConsistencyException
	 */
	public Set<Module> process_Replacables(Set<Module> inputModules, OperationType opType,
			OperationTypeGroup opTypeGroup) throws OperationTypeNotImplementedException {

		//TODO update method describtion recarding consolidated variant generation
		
		Set<Module> modules = new HashSet<>();
		for (Module module : inputModules) {
			
			if(c.enable_consolidatedvariants) {
			
				ConsolidatedVariantGenerator generator = new ConsolidatedVariantGenerator(module, opType, opTypeGroup);
				modules.addAll(generator.generate());
			
			}else {
				
				SolitaryVariantGenerator generator = new SolitaryVariantGenerator(module, opType, opTypeGroup);
				modules.addAll(generator.generate());
			}
		}
		return modules;

	}

}
