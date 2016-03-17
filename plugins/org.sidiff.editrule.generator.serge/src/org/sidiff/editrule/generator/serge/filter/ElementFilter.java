package org.sidiff.editrule.generator.serge.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.sidiff.common.emf.extensions.impl.EClassifierInfo;
import org.sidiff.common.emf.extensions.impl.EClassifierInfoManagement;
import org.sidiff.common.emf.extensions.impl.Mask;
import org.sidiff.editrule.generator.exceptions.OperationTypeNotImplementedException;
//import org.sidiff.common.emf.metamodelslicer.impl.MetaModelSlicer;
import org.sidiff.editrule.generator.serge.configuration.Configuration;
import org.sidiff.editrule.generator.types.OperationType;

/**
 * This class is responsible for filtering out EClassifiers in different
 * scenarios.
 * 
 * @author mrindt
 *
 */
public class ElementFilter {

	public static enum InclusionType {
		NEVER, IF_REQUIRED, ALWAYS;
	}

	/**
	 * A map that stores the configured inclusion behavior for each classifier
	 * in case it is a dangling element
	 */
	private final Map<EClassifier, InclusionType> danglingInclusionTypes = new HashMap<>();
	
	/**
	 * A variable that stores the configured default inclusion behavior for dangling elements
	 */
	private InclusionType defaultDanglingInclusionType;
	
	/**
	 * A map that stores the configured inclusion behavior for each classifier
	 * in case it is a focal element
	 */
	private final Map<EClassifier, InclusionType> focusInclusionTypes = new HashMap<>();
	
	/**
	 * A variable that stores the configured default inclusion behavior for focal elements
	 */
	private InclusionType defaultFocusInclusionType;
	
	/**
	 * A set that holds all recursively required classifiers. A classifier is seen as required
	 * if it is needed as mandatory element in the construction for modules of other classifiers
	 * (inheritance hierarchy is included in the calculation) 
	 * 
	 */
	private final Set<EClassifier> requiredTypes = new HashSet<>();


	/**
	 * The configuration
	 */
	private Configuration c;
	
	/**
	 * The EClassifierManagement
	 */
	private EClassifierInfoManagement ECM;
	
	/**
	 * The ElementFilter
	 */
	private static ElementFilter instance = null;

	/**
	 * Singleton
	 * @return ElementFilter
	 */
	public static ElementFilter getInstance() {
		if (instance == null) {
			instance = new ElementFilter();
		}
		return instance;
	}

	/**
	 * Constructor
	 */
	private ElementFilter() {
		c = Configuration.getInstance();
		ECM = EClassifierInfoManagement.getInstance();
	}

	/**
	 * Method that configures the inclusion type behavior for a classifier
	 * in case it is considered a dangling element
	 * 
	 * @param type
	 * @param it
	 */
	public void setDanglingInclusionType(EClassifier type, InclusionType it) {
		if (it != null) {
			danglingInclusionTypes.put(type, it);
		} else {
			danglingInclusionTypes.remove(type);
		}
	}

	/**
	 * Method that configures the inclusion type behavior for a classifier
	 * in case it is considered a focal element
	 * @param type
	 * @param it
	 */
	public void setFocusInclusionType(EClassifier type, InclusionType it) {
		if (it != null) {
			focusInclusionTypes.put(type, it);
		} else {
			focusInclusionTypes.remove(type);
		}
	}

	/**
	 * Method that gets the inclusion type behavior for each classifier
	 * in case it is considered a dangling element
	 * @return
	 */
	public Map<EClassifier, InclusionType> getDanglingInclusionTypes() {
		return danglingInclusionTypes;
	}

	/**
	 * Method that gets the inclusion type behavior for each classifier
	 * in case it is considered a focal element
	 * @return
	 */
	public Map<EClassifier, InclusionType> getFocusInclusionTypes() {
		return focusInclusionTypes;
	}

	/**
	 * Method that gets the inclusion type behavior for a classifier
	 * in case it is considered a focus element
	 * @param type
	 * @return
	 */
	public InclusionType getFocusInclusuionType(EClassifier type) {
		InclusionType it = focusInclusionTypes.get(type);
		if (it == null)
			return getDefaultFocusInclusionType();
		return it;
	}

	/**
	 * Method that gets the inclusion type behavior for a classifier
	 * in case it is considered a dangling element
	 * @param type
	 * @return
	 */
	public InclusionType getDanglingInclusuionType(EClassifier type) {
		InclusionType it = danglingInclusionTypes.get(type);
		if (it == null)
			return getDefaultDanglingInclusionType();
		return it;
	}

	
	/**
	 * Method that gets the default inclusion type behavior for danglings 
	 * @return InclusionType
	 */
	public InclusionType getDefaultDanglingInclusionType() {
		return defaultDanglingInclusionType;
	}

	/**
	 * Method that sets the default inclusion type behavior for danglings 
	 */
	public void setDefaultDanglingInclusionType(InclusionType defaultDanglingInclusionType) {
		this.defaultDanglingInclusionType = defaultDanglingInclusionType;
	}

	/**
	 * Method that gets the default inclusion type behavior for focal elements 
	 * @return InclusionType
	 */
	public InclusionType getDefaultFocusInclusionType() {
		return defaultFocusInclusionType;
	}

	/**
	 * Method that sets the default inclusion type behavior for focal elements 
	 */
	public void setDefaultFocusInclusionType(InclusionType defaultFocusInclusionType) {
		this.defaultFocusInclusionType = defaultFocusInclusionType;
	}

	/**
	 * Adds types that are indirectly required by eClassifier to the
	 * ElementFilter's required types list.
	 * 
	 * If a directly required type is abstract, all abstract and nearest
	 * non-abstract subtypes of the type and types required by them are added to
	 * the list
	 * 
	 * This method collects all explicitly allowed (InclusionType.ALWAYS) types
	 * (dangling and focus) and uses them as the starting point to search for
	 * required types.
	 * 
	 * The required list contains types with all InclusionTypes, including
	 * ALWAYS and NEVER.
	 * 
	 * The meta model has to be analyzed before calling this method.
	 *
	 */
	public void collectRequiredTypes() {
		requiredTypes.clear();
		Set<EClassifier> baseClassifiers = new HashSet<>();
		// Collect all ALWAYS included dangling types
		for (EClassifier eC : danglingInclusionTypes.keySet()) {
			if (InclusionType.ALWAYS.equals(getFocusInclusuionType(eC)))
				baseClassifiers.add(eC);
		}
		// Collect all ALWAYS included focus types
		for (EClassifier eC : focusInclusionTypes.keySet()) {
			if (InclusionType.ALWAYS.equals(getFocusInclusuionType(eC)))
				baseClassifiers.add(eC);
		}
		// Find required types
		Set<EClassifier> needChecking = new HashSet<>();
		for (EClassifier classifier : baseClassifiers) {
			needChecking.addAll(collectRequiredTypes(classifier, requiredTypes));
		}
		// Find additionally required types
		collectAdditionalRequiredTypes(requiredTypes, needChecking);
	}

	/**
	 * Handles types that could not be checked intermediately.
	 * @param required Already required types (results will be added to this set)
	 * @param check Types to check if they are required
	 */
	private void collectAdditionalRequiredTypes(Set<EClassifier> required, Set<EClassifier> check) {
		//TODO [LM@28.11.15] Test this method
		Set<EClassifier> lastRequired;
		Set<EClassifier> lastCheck;
		do {
			lastRequired = new HashSet<>(required);
			lastCheck = new HashSet<>(check);
			for (EClassifier classifier : check) {
				if (requiredTypes.contains(classifier)) {
					check.addAll(collectRequiredTypes(classifier, required));
				}
			}
		} while (!(lastCheck.equals(check) && lastRequired.equals(required)));
	}

	/**
	 * {@link #collectRequiredTypes()}
	 * 
	 * @param eClassifier
	 *            Classifier which will be the starting point for searching
	 * @param result
	 *            Already found required types. Results will be added to this
	 *            set.
	 * @return List of EClassifiers that need to be checked later because it can
	 *         not be determined now, if they are required. This is mainly
	 *         because of the used configuration. These types are handled by
	 *         {@link #collectAdditionalRequiredTypes(Set, Set)} later.
	 */
	private Set<EClassifier> collectRequiredTypes(EClassifier eClassifier, Set<EClassifier> result) {
		// TODO Do this based on while/blacklistes types
		// TODO (Not here) Vererbung
		// Set which holds all types that need to be checked later
		Set<EClassifier> needChecking = new HashSet<>();
		EClassifierInfo mcInfo = ECM.getEClassifierInfo(eClassifier);
		Set<EClassifier> allMmandatoryChildren = new HashSet<>();
		for (List<EClassifier> ecL : mcInfo.getMandatoryChildren().values()) {
			allMmandatoryChildren.addAll(ecL);
		}
		for (EClassifier mc : allMmandatoryChildren) {
			if (!result.contains(mc)) {
				// Add required types of the new required type recursively
				result.add(mc); // Must be done first to prevent infinite loops
				needChecking.addAll(collectRequiredTypes(mc, result));
			}
		}
		Set<EClassifier> allMmandatoryNeighbours = new HashSet<>();
		for (List<EClassifier> ecL : mcInfo.getMandatoryNeighbours().values()) {
			allMmandatoryNeighbours.addAll(ecL);
		}
		for (EClassifier mc : allMmandatoryNeighbours) {
			if (!result.contains(mc)) {
				// Add required types
				result.add(mc); // Must be done first to prevent infinite loops
				/*
				 * If the user has specified in the configuration that modules
				 * should be created for required types, this includes also
				 * mandatory neighbours. THis is handled here. If we already
				 * know taht the type is required we can handle it right here.
				 * If not this has to be done later. In this case the type is
				 * saved in the needsCHecking set.
				 */
				if (InclusionType.IF_REQUIRED.equals(getFocusInclusuionType(mc))) {
					if (requiredTypes.contains(mc)) {
						needChecking.addAll(collectRequiredTypes(mc, result));
					} else {
						needChecking.add(mc);
					}
				}
			}
		}
		// Supertypes are also needed and may have required types
		if (eClassifier instanceof EClass) {
			//TODO !!!!!!!
//			for (EClassifier supertype : ((EClass) eClassifier).getESuperTypes()) {
//				if (!result.contains(supertype)) {
//					//result.add(supertype);
//					needChecking.addAll(collectRequiredTypes(supertype, result));
//				}
//			}
		}
		// If the child is abstract its subtypes are also required
		// (recursively)
		if ((eClassifier instanceof EClass) && ((EClass) eClassifier).isAbstract()) {
			for (EClassifier subtype : ECM.getSubTypes(eClassifier)) {
				if (!result.contains(subtype)) {
					result.add(subtype);
					needChecking.addAll(collectRequiredTypes(subtype, result));
				}
			}
		}
		return needChecking;
	}

	/**
	 * Checks if an EClassifier is a profile meta-class of any as focus allowed
	 * (direct or required) stereotype.
	 * 
	 * @param eClassifier
	 * @return
	 */
	private boolean hasRequiredOrAllowedStereotype_Focus(EClassifier eClassifier) {
		if (c.MAINMODEL_IS_PROFILE) {
			Set<EClassifier> stereotypes = ECM.getAllStereotypes(eClassifier);
			for (EClassifier stereotype : stereotypes) {
				if (InclusionType.ALWAYS.equals(getFocusInclusuionType(stereotype))
						|| (InclusionType.IF_REQUIRED.equals(getFocusInclusuionType(stereotype))
								&& requiredTypes.contains(stereotype))) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * This method delivers false if the given eClassifier should not recieve a
	 * transformation module in which the eClassifier is considered as <b>focal
	 * point</b> (or module basis). For example the eClassifier "State" is
	 * considered in focus for the generation of a "CREATE_State_In_Region"
	 * transformation module. There can be various cases in which an eClassifier
	 * should be avoided as a focal point when running certain module
	 * generations.
	 * 
	 * @param eClassifier
	 * @param opType
	 * @return false or true
	 * @throws OperationTypeNotImplementedException
	 */
	public boolean isAllowedAsFocused(EClassifier eClassifier, OperationType opType)
			throws OperationTypeNotImplementedException {

		EClassifierInfo eInf = ECM.getEClassifierInfo(eClassifier);
		boolean requiredByStereotypes = hasRequiredOrAllowedStereotype_Focus(eClassifier);
		boolean isElementOfRequiredMetamodels = isElementOfRequiredMetamodels(eClassifier);
		// TODO [LM@22.11.2015] Check this considering stereotypes
		boolean includeBySettings = (InclusionType.ALWAYS.equals(getFocusInclusionType(eClassifier))
				|| (InclusionType.IF_REQUIRED.equals(getFocusInclusionType(eClassifier))
						&& (requiredByStereotypes || requiredTypes.contains(eClassifier))));

		switch (opType) {

		case CREATE:
			if ((!(eClassifier instanceof EClass))
					|| (eClassifier instanceof EClass && ((EClass) eClassifier).isAbstract()) || c.MAINMODEL_IS_PROFILE
					|| (!eInf.selfMayHaveTransformations()) || (c.isAnUnnestableRoot(eClassifier))
					|| (requiredByStereotypes && !c.isRoot(eClassifier)) || !includeBySettings
					|| isElementOfRequiredMetamodels)
				return false;
			break;

		case ATTACH:
			if ((!(eClassifier instanceof EClass))
					|| (eClassifier instanceof EClass && ((EClass) eClassifier).isAbstract()) || !c.MAINMODEL_IS_PROFILE
					|| !includeBySettings || isElementOfRequiredMetamodels)
				return false;
			break;

		case ADD:

			if ((!includeBySettings && !c.isRoot(eClassifier)) || isElementOfRequiredMetamodels) {
				return false;
			}
			break;

		case CHANGE_REFERENCE:
			if (!includeBySettings || isElementOfRequiredMetamodels) {
				return false;
			}
			break;

		case CHANGE_LITERAL:
			if (!includeBySettings || isElementOfRequiredMetamodels) {
				return false;
			}
			break;

		case MOVE:
			if ((!(eClassifier instanceof EClass))
					|| (eClassifier instanceof EClass && ((EClass) eClassifier).isAbstract())
					|| (!eInf.selfMayHaveTransformations()) || (c.isAnUnnestableRoot(eClassifier))
					|| (requiredByStereotypes && !c.isRoot(eClassifier)) || !includeBySettings
					|| isElementOfRequiredMetamodels)
				return false;
			break;

		case MOVE_REFERENCE_COMBINATION:
			if ((!(eClassifier instanceof EClass))
					|| (eClassifier instanceof EClass && ((EClass) eClassifier).isAbstract())
					|| (!eInf.selfMayHaveTransformations()) || (c.isAnUnnestableRoot(eClassifier))
					|| (requiredByStereotypes && !c.isRoot(eClassifier)) || !includeBySettings
					|| isElementOfRequiredMetamodels)
				return false;
			break;

		case MOVE_UP:
			if ((!(eClassifier instanceof EClass))
					|| (eClassifier instanceof EClass && ((EClass) eClassifier).isAbstract())
					|| (!eInf.selfMayHaveTransformations()) || (c.isAnUnnestableRoot(eClassifier))
					|| (requiredByStereotypes && !c.isRoot(eClassifier)) || !includeBySettings
					|| isElementOfRequiredMetamodels)
				return false;
			break;

		case MOVE_DOWN:
			if ((!(eClassifier instanceof EClass))
					|| (eClassifier instanceof EClass && ((EClass) eClassifier).isAbstract())
					|| (!eInf.selfMayHaveTransformations()) || (c.isAnUnnestableRoot(eClassifier))
					|| (requiredByStereotypes && !c.isRoot(eClassifier)) || includeBySettings
					|| isElementOfRequiredMetamodels)
				return false;
			break;

		case SET_ATTRIBUTE:
			if (!includeBySettings || isElementOfRequiredMetamodels) {
				return false;
			}
			break;

		case SET_REFERENCE:
			if (!includeBySettings || isElementOfRequiredMetamodels) {
				return false;
			}
			break;

		// ----- inverses (don't need to be denied/allowed explicitly)
		// ---------------------------/
		case DETACH:
			return true;

		case UNSET_ATTRIBUTE:
			return true;

		case UNSET_REFERENCE:
			return true;

		case DELETE:
			return true;

		case REMOVE:
			return true;

		// -----unsupported ---------------------------/
		default:
			throw new OperationTypeNotImplementedException(opType);
		}

		return true;
	}

	public InclusionType getFocusInclusionType(EClassifier type) {
		InclusionType ic = focusInclusionTypes.get(type);
		if (ic == null) {
			return getDefaultFocusInclusionType();
		} else {
			return ic;
		}
	}

	public InclusionType getDanglingInclusionType(EClassifier type) {
		InclusionType ic = danglingInclusionTypes.get(type);
		if (ic == null) {
			return getDefaultDanglingInclusionType();
		} else {
			return ic;
		}
	}

	/**
	 * This method delivers false if the given eClassifier should not be
	 * mentioned inside a transformation module with focus on another
	 * eClassifier. The eClassifier here is NOT considered as focal point; i.e.
	 * it is merely considered as a <b>dangling</b> element. For example the
	 * eClassifier "Region" is considered as a dangling element in the
	 * generation of a "CREATE_State_In_Region" transformation module. There can
	 * be various cases in which an eClassifier should be avoided as dangling
	 * when running certain module generations.
	 * 
	 * @param eClassifier
	 * @param opType
	 * @return false or true
	 * @throws OperationTypeNotImplementedException
	 */
	public boolean isAllowedAsDangling(EClassifier eClassifier, OperationType opType)
			throws OperationTypeNotImplementedException {

		boolean required = requiredTypes.contains(eClassifier);
		boolean forbiddenAsDangling = InclusionType.NEVER.equals(getDanglingInclusionType(eClassifier));
		boolean configuredToBeAllowedAsDangling_on_IF_Required = InclusionType.IF_REQUIRED.equals(getDanglingInclusionType(eClassifier));
		
		switch (opType) {

		case CREATE:

			if (forbiddenAsDangling || (configuredToBeAllowedAsDangling_on_IF_Required && !required)) {
				return false;
			}
			break;

		case ATTACH:

			if (forbiddenAsDangling || (configuredToBeAllowedAsDangling_on_IF_Required && !required)) {
				return false;
			}
			break;

		case ADD:
			if (forbiddenAsDangling || (configuredToBeAllowedAsDangling_on_IF_Required && !required)) {
				return false;
			}
			break;

		case MOVE:
			if (forbiddenAsDangling || (configuredToBeAllowedAsDangling_on_IF_Required && !required)) {
				return false;
			}
			break;

		case MOVE_REFERENCE_COMBINATION:
			if (forbiddenAsDangling || (configuredToBeAllowedAsDangling_on_IF_Required && !required)) {
				return false;
			}
			break;

		case MOVE_UP:
			if (forbiddenAsDangling || (configuredToBeAllowedAsDangling_on_IF_Required && !required)) {
				return false;
			}
			break;

		case MOVE_DOWN:
			if (forbiddenAsDangling || (configuredToBeAllowedAsDangling_on_IF_Required && !required)) {
				return false;
			}
			break;

		case CHANGE_REFERENCE:
			if (forbiddenAsDangling || (configuredToBeAllowedAsDangling_on_IF_Required && !required)) {
				return false;
			}
			break;

		case SET_ATTRIBUTE:
			if (forbiddenAsDangling || (configuredToBeAllowedAsDangling_on_IF_Required && !required)) {
				return false;
			}
			break;

		case SET_REFERENCE:
			if (forbiddenAsDangling || (configuredToBeAllowedAsDangling_on_IF_Required && !required)) {
				return false;
			}
			break;

		// ----- inverses (don't need to be denied/allowed explicitly)
		// ---------------------------/
		case DETACH:
			return true;

		case UNSET_ATTRIBUTE:
			return true;

		case UNSET_REFERENCE:
			return true;

		case DELETE:
			return true;

		case REMOVE:
			return true;

		// -----unsupported ---------------------------/
		default:
			throw new OperationTypeNotImplementedException(opType);
		}

		return true;
	}

	/*****
	 * Convenience Methods
	 ************************************************************/

	/**
	 * This method delivers a list with all names of EClassifiers contained in
	 * the given list.
	 * 
	 * @param eClassifierList
	 * @return names of EClassifiers contained in the given list.
	 */
	protected static List<String> extractAllEClassifierNames(ArrayList<EClassifier> eClassifierList) {
		List<String> list = new ArrayList<String>();

		for (EClassifier ec : eClassifierList) {
			list.add(ec.getName());
		}
		return list;
	}

	/**
	 * This method delivers all allowed parent context regarding a
	 * childEClassifer.
	 * 
	 * @param childEClassifier
	 * @param reduceToSupertypeContext
	 * @param operationType
	 * @return
	 * @throws OperationTypeNotImplementedException
	 */
	public HashMap<EReference, List<EClass>> getAllAllowedParentContexts(EClassifier childEClassifier,
			boolean reduceToSupertypeContext, OperationType operationType) throws OperationTypeNotImplementedException {

		HashMap<EReference, List<EClass>> allAllowedParents = new HashMap<EReference, List<EClass>>();

		// get possible eClassifier Masks for additional move generation of
		// masked classifiers.
		List<Mask> eClassifierMasks = new ArrayList<Mask>();
		eClassifierMasks.addAll(ECM.getEClassifierInfo(childEClassifier).getMasks());

		// get all possible contexts (mandatory & optional) and the according
		// references
		HashMap<EReference, List<EClassifier>> allParents = ECM.getAllParentContexts(childEClassifier,
				reduceToSupertypeContext);

		for (EReference eRef : allParents.keySet()) {

			assert (eRef.isContainment()) : "eRef is no containment but should be";

			// don't consider containment references where multiplicity is
			// fixed. In such cases a SWAP (complex) operation is necessary
			if (!(eRef.getLowerBound() == eRef.getUpperBound())) {

				// don't consider derived, not changeable, unsettable and
				// transient references
				if (!eRef.isDerived() && eRef.isChangeable() && !eRef.isUnsettable() && !eRef.isTransient()) {

					EClass parent = (EClass) eRef.eContainer();

					// if parent is allowed, put it in allAllowedParent-List
					if (isAllowedAsDangling(parent, operationType)) {
						if (allAllowedParents.get(eRef) == null) {
							List<EClass> newParentList = new ArrayList<EClass>();
							newParentList.add(parent);
							allAllowedParents.put(eRef, newParentList);
						} else {
							allAllowedParents.get(eRef).add(parent);
						}
					}

					// all EReferences
					ArrayList<EReference> allReferences = new ArrayList<EReference>();
					allReferences.addAll(allAllowedParents.keySet());
				}
			}
		}
		return allAllowedParents;
	}

	/**
	 * Returns true if the given classifier is an element of the required meta
	 * models (as opposed to an element of the main meta-model)
	 * 
	 * @param eClassifier
	 * @return
	 */
	private boolean isElementOfRequiredMetamodels(EClassifier eClassifier) {

		// access to config
		Configuration config = Configuration.getInstance();

		// get only the required meta models
		List<EPackage> requiredEPackages = new ArrayList<EPackage>();
		requiredEPackages.addAll(config.EPACKAGESSTACK);
		requiredEPackages.remove(config.METAMODEL);
		for (EPackage p : config.METAMODEL.getESubpackages()) {
			// TODO Recursive?
			requiredEPackages.remove(p);
		}

		// is eClassifier contained in one of the required meta models?
		for (EPackage requiredEPackage : requiredEPackages) {
			if (requiredEPackage.getEClassifiers().contains(eClassifier)) {
				return true;
			}
		}

		return false;

	}

}
