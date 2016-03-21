package org.sidiff.editrule.generator.serge.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
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
import org.sidiff.editrule.generator.serge.filter.ClassifierInclusionConfiguration.InclusionType;
import org.sidiff.editrule.generator.types.OperationType;

/**
 * This class is responsible for filtering out EClassifiers in different
 * scenarios.
 * 
 * @author mrindt
 *
 */
public class ElementFilter {

	/**
	 * The configuration
	 */
	private Configuration config;
	
	/**
	 * The EClassifierManagement
	 */
	private EClassifierInfoManagement ECM;
	
	/**
	 * The ClassifierInclusionConfiguration
	 */
	private ClassifierInclusionConfiguration CIC;
	
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
		config = Configuration.getInstance();
		ECM = EClassifierInfoManagement.getInstance();
		CIC = ClassifierInclusionConfiguration.getInstance();
	}

	/**
	 * Checks if an EClassifier is a profile meta-class of any as focus allowed
	 * (direct or required) stereotype.
	 * 
	 * @param eClassifier
	 * @return
	 */
	private boolean hasRequiredOrAllowedStereotype_Focus(EClassifier eClassifier) {
		if (config.MAINMODEL_IS_PROFILE) {
			Set<EClassifier> stereotypes = ECM.getAllStereotypes(eClassifier);
			for (EClassifier stereotype : stereotypes) {
				if (InclusionType.ALWAYS.equals(CIC.getFocusInclusionType(stereotype))
						|| (InclusionType.IF_REQUIRED.equals(CIC.getFocusInclusionType(stereotype))
								&& CIC.getRequiredDanglings().contains(stereotype))) {
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
		boolean includeBySettings = (InclusionType.ALWAYS.equals(CIC.getFocusInclusionType(eClassifier))
				|| (InclusionType.IF_REQUIRED.equals(CIC.getFocusInclusionType(eClassifier))
						&& (requiredByStereotypes || CIC.getRequiredDanglings().contains(eClassifier))));

		switch (opType) {

		case CREATE:
			if ((!(eClassifier instanceof EClass))
					|| (eClassifier instanceof EClass && ((EClass) eClassifier).isAbstract()) || config.MAINMODEL_IS_PROFILE
					|| (!eInf.selfMayHaveTransformations()) || (config.isAnUnnestableRoot(eClassifier))
					|| (requiredByStereotypes && !config.isRoot(eClassifier)) || !includeBySettings
					|| isElementOfRequiredMetamodels)
				return false;
			break;

		case ATTACH:
			if ((!(eClassifier instanceof EClass))
					|| (eClassifier instanceof EClass && ((EClass) eClassifier).isAbstract()) || !config.MAINMODEL_IS_PROFILE
					|| !includeBySettings || isElementOfRequiredMetamodels)
				return false;
			break;

		case ADD:
			
			if ((!includeBySettings && !config.isRoot(eClassifier)) || isElementOfRequiredMetamodels) {
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
					|| (!eInf.selfMayHaveTransformations()) || (config.isAnUnnestableRoot(eClassifier))
					|| (requiredByStereotypes && !config.isRoot(eClassifier)) || !includeBySettings
					|| isElementOfRequiredMetamodels)
				return false;
			break;

		case MOVE_REFERENCE_COMBINATION:
			if ((!(eClassifier instanceof EClass))
					|| (eClassifier instanceof EClass && ((EClass) eClassifier).isAbstract())
					|| (!eInf.selfMayHaveTransformations()) || (config.isAnUnnestableRoot(eClassifier))
					|| (requiredByStereotypes && !config.isRoot(eClassifier)) || !includeBySettings
					|| isElementOfRequiredMetamodels)
				return false;
			break;

		case MOVE_UP:
			if ((!(eClassifier instanceof EClass))
					|| (eClassifier instanceof EClass && ((EClass) eClassifier).isAbstract())
					|| (!eInf.selfMayHaveTransformations()) || (config.isAnUnnestableRoot(eClassifier))
					|| (requiredByStereotypes && !config.isRoot(eClassifier)) || !includeBySettings
					|| isElementOfRequiredMetamodels)
				return false;
			break;

		case MOVE_DOWN:
			if ((!(eClassifier instanceof EClass))
					|| (eClassifier instanceof EClass && ((EClass) eClassifier).isAbstract())
					|| (!eInf.selfMayHaveTransformations()) || (config.isAnUnnestableRoot(eClassifier))
					|| (requiredByStereotypes && !config.isRoot(eClassifier)) || includeBySettings
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

		boolean required = CIC.getRequiredDanglings().contains(eClassifier);
		boolean is_NEVER = InclusionType.NEVER.equals(CIC.getDanglingInclusionType(eClassifier));
		boolean is_If_REQUIRED = InclusionType.IF_REQUIRED.equals(CIC.getDanglingInclusionType(eClassifier));
		
		switch (opType) {

		case CREATE:

			if (is_NEVER || (is_If_REQUIRED && !required)) {
				return false;
			}
			break;

		case ATTACH:

			if (is_NEVER || (is_If_REQUIRED && !required)) {
				return false;
			}
			break;

		case ADD:
			if (is_NEVER || (is_If_REQUIRED && !required)) {
				return false;
			}
			break;

		case MOVE:
			if (is_NEVER || (is_If_REQUIRED && !required)) {
				return false;
			}
			break;

		case MOVE_REFERENCE_COMBINATION:
			if (is_NEVER || (is_If_REQUIRED && !required)) {
				return false;
			}
			break;

		case MOVE_UP:
			if (is_NEVER || (is_If_REQUIRED && !required)) {
				return false;
			}
			break;

		case MOVE_DOWN:
			if (is_NEVER || (is_If_REQUIRED && !required)) {
				return false;
			}
			break;

		case CHANGE_REFERENCE:
			if (is_NEVER || (is_If_REQUIRED && !required)) {
				return false;
			}
			break;

		case SET_ATTRIBUTE:
			if (is_NEVER || (is_If_REQUIRED && !required)) {
				return false;
			}
			break;

		case SET_REFERENCE:
			if (is_NEVER || (is_If_REQUIRED && !required)) {
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

	
	
	public boolean isAllowedAsParentContext(EClassifier eClassifier) {

		boolean required = CIC.getRequiredDanglingParentContexts().contains(eClassifier);
		InclusionType userDefinedBehavior = CIC.getDanglingInclusionType(eClassifier);
		InclusionType userDefinedDefaultInclusionBehavior = CIC.getDefaultDanglingInclusionType();
		
		// user defined behavior
		if(userDefinedBehavior!=null) {
			if(userDefinedBehavior==InclusionType.ALWAYS) {
				return true;
			}
			else if(userDefinedBehavior==InclusionType.IF_REQUIRED) {
				return required;
			}
			else if(userDefinedBehavior==InclusionType.NEVER) {
				return false;
			}
		}
		// user undefined behavior
		else{
			if(userDefinedDefaultInclusionBehavior==InclusionType.ALWAYS) {
				return true;
			}
			else if(userDefinedDefaultInclusionBehavior==InclusionType.IF_REQUIRED) {
				return required;	
			}
			else if(userDefinedDefaultInclusionBehavior==InclusionType.NEVER) {
				return false;
			}
		}
		
		return false;
		
	}
	
	
	/*****
	 * Convenience Methods
	 ************************************************************/

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

		HashMap<EReference, List<EClassifier>> allFilteredParents = new HashMap<EReference,List<EClassifier>>();
		
		// remove all the parents which have to be excluded by user configuration
		for(Entry<EReference,List<EClassifier>> entry: allParents.entrySet()) {
			List<EClassifier> parentList = new ArrayList<EClassifier>(entry.getValue());
			
			for(EClassifier parent: entry.getValue()) {
				if(!isAllowedAsParentContext(parent)) {
					parentList.remove(parent);
				}
			}			
			if(!parentList.isEmpty()) {
				allFilteredParents.put(entry.getKey(), parentList);
			}
		}
		
		
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
