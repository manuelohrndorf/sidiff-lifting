package org.sidiff.editrule.generator.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EReference;
import org.sidiff.common.emf.extensions.impl.EClassifierInfo;
import org.sidiff.common.emf.extensions.impl.EClassifierInfoManagement;
import org.sidiff.common.emf.extensions.impl.Mask;
//import org.sidiff.common.emf.metamodelslicer.impl.MetaModelSlicer;
import org.sidiff.editrule.generator.configuration.Configuration;
import org.sidiff.editrule.generator.configuration.Configuration.OperationType;
import org.sidiff.editrule.generator.exceptions.OperationTypeNotImplementedException;

/**
 * This class is responsible for filtering out EClassifiers  in different
 * scenarios. It also contains the blacklist and whitelist.
 * 
 * @author mrindt
 *
 */
public class ElementFilter {

	private static ArrayList<EClassifier> blackList;	
	private static ArrayList<EClassifier> whiteList;
	
	private static Configuration c;
	private static EClassifierInfoManagement ECM;
	private static ElementFilter instance = null;
	
	public static ElementFilter getInstance() {
		if (instance==null) {
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
	
	/** Public SETTER **************************************************************************/
	
	public void setBlackList(ArrayList<EClassifier> bList) {
		blackList = bList;
	}

	public void setWhiteList(ArrayList<EClassifier> wList) {
		whiteList = wList;
	}
	
	/** Public GETTER **************************************************************************/
	
	public ArrayList<EClassifier> getBlackList() {
		return blackList;
	}

	public ArrayList<EClassifier> getWhiteList() {
		return whiteList;
	}
	
	/**
	 * This method delivers false if the given eClassifier should not recieve a transformation module
	 * in which the eClassifier is considered as <b>focal point</b> (or module basis). For example the eClassifier "State" is
	 * considered in focus for the generation of a "CREATE_State_In_Region" transformation module.
	 * There can be various cases in which an eClassifier should be avoided as a focal point when running certain module generations.
	 * @param eClassifier
	 * @param opType
	 * @return false or true
	 * @throws OperationTypeNotImplementedException
	 */
	public Boolean isAllowedAsModuleBasis(EClassifier eClassifier, Configuration.OperationType opType) throws OperationTypeNotImplementedException {
		
		EClassifierInfo eInf = ECM.getEClassifierInfo(eClassifier);

		boolean blackListed	= blackList.contains(eClassifier);
		boolean whiteListed	= whiteList.contains(eClassifier);
		boolean assumeAllOnWhitelist = whiteList.isEmpty();
		boolean requiredBySubtypes = isRequiredByWhitelistedSubtypes(eClassifier);
		boolean requiredByStereotypes = isRequiredByWhitelistedStereotype(eClassifier);
		
		switch(opType) {
		
			case CREATE:
				if (
					(!(eClassifier instanceof EClass))
					|| (eClassifier instanceof EClass && ((EClass)eClassifier).isAbstract())
					|| (!eInf.selfMayHaveTransformations())
					|| (c.isAnUnnestableRoot(eClassifier))
					|| (requiredByStereotypes && !c.isRoot(eClassifier))
					|| (!whiteListed && !assumeAllOnWhitelist /*&& !requiredBySubtypes*/)
					|| (blackListed && !requiredBySubtypes)
					)
				return false;				
				break;
				
			case ADD:				
				if (
						( (!whiteListed && !assumeAllOnWhitelist && !requiredBySubtypes) && (!requiredByStereotypes)&& (!c.isRoot(eClassifier)))
						|| ((blackListed && !requiredBySubtypes) &&(!requiredByStereotypes) && (!c.isRoot(eClassifier))) 
						) {
					return false;
				}	
				break;
				
			case CHANGE_REFERENCE:
				if (
						( (!whiteListed && !assumeAllOnWhitelist && !requiredBySubtypes) && (!requiredByStereotypes))
						|| ((blackListed && !requiredBySubtypes) &&(!requiredByStereotypes)) 
						) {
					return false;
				}	
				break;
				
			case CHANGE_LITERAL:
				if (
						( (!whiteListed && !assumeAllOnWhitelist && !requiredBySubtypes) && (!requiredByStereotypes))
						|| ((blackListed && !requiredBySubtypes) &&(!requiredByStereotypes)) 
						) {
					return false;
				}	
				break;
				
			case MOVE:
				if (
						(!(eClassifier instanceof EClass))
						|| (eClassifier instanceof EClass && ((EClass)eClassifier).isAbstract())
						|| (!eInf.selfMayHaveTransformations())
						|| (c.isAnUnnestableRoot(eClassifier))
						|| (requiredByStereotypes && !c.isRoot(eClassifier))
						|| (!whiteListed && !assumeAllOnWhitelist /*&& !requiredBySubtypes*/)
						|| (blackListed && !requiredBySubtypes)
						)
					return false;
//				if ((c.isAnUnnestableRoot(eClassifier))
//					|| (!requiredByStereotypes && !c.isRoot(eClassifier))) {
//					return false;
//				}
				break;
				
			case MOVE_REFERENCE_COMBINATION:
					if (
						(!(eClassifier instanceof EClass))
						|| (eClassifier instanceof EClass && ((EClass)eClassifier).isAbstract())
						|| (!eInf.selfMayHaveTransformations())
						|| (c.isAnUnnestableRoot(eClassifier))
						|| (requiredByStereotypes && !c.isRoot(eClassifier))
						|| (!whiteListed && !assumeAllOnWhitelist /*&& !requiredBySubtypes*/)
						|| (blackListed && !requiredBySubtypes)
					)
					return false;
//				if ((c.isAnUnnestableRoot(eClassifier))
//					|| (!requiredByStereotypes && !c.isRoot(eClassifier))) {
//					return false;
//				}
				break;
				
			case MOVE_UP:
				if (
						(!(eClassifier instanceof EClass))
						|| (eClassifier instanceof EClass && ((EClass)eClassifier).isAbstract())
						|| (!eInf.selfMayHaveTransformations())
						|| (c.isAnUnnestableRoot(eClassifier))
						|| (requiredByStereotypes && !c.isRoot(eClassifier))
						|| (!whiteListed && !assumeAllOnWhitelist /*&& !requiredBySubtypes*/)
						|| (blackListed && !requiredBySubtypes)
					)
					return false;
//				if ((c.isAnUnnestableRoot(eClassifier))
//					|| (!requiredByStereotypes && !c.isRoot(eClassifier))) {
//					return false;
//				}
				break;
				
			case MOVE_DOWN:
				if (
						(!(eClassifier instanceof EClass))
						|| (eClassifier instanceof EClass && ((EClass)eClassifier).isAbstract())
						|| (!eInf.selfMayHaveTransformations())
						|| (c.isAnUnnestableRoot(eClassifier))
						|| (requiredByStereotypes && !c.isRoot(eClassifier))
						|| (!whiteListed && !assumeAllOnWhitelist  /*&& !requiredBySubtypes*/)
						|| (blackListed && !requiredBySubtypes)
					)
					return false;
//				if ((c.isAnUnnestableRoot(eClassifier))
//					|| (!requiredByStereotypes && !c.isRoot(eClassifier))) {
//					return false;
//				}
				break;
				
			case SET_ATTRIBUTE:
				if (
						( (!whiteListed && !assumeAllOnWhitelist /*&& !requiredBySubtypes*/) && (!requiredByStereotypes))
						|| ((blackListed && !requiredBySubtypes) &&(!requiredByStereotypes)) 
						) {
					return false;
				}			
				break;
				
			case SET_REFERENCE:
				if (
						( (!whiteListed && !assumeAllOnWhitelist /*&& !requiredBySubtypes*/) && (!requiredByStereotypes))
						|| ((blackListed && !requiredBySubtypes) &&(!requiredByStereotypes)) 
						) {
					return false;
				}	
				break;
			
			// ----- inverses (don't need to be denied/allowed explicitly) ---------------------------/
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
				throw new OperationTypeNotImplementedException(opType.toString());
		}
		
		
		
		
		return true;
	}
	
	/**
	 *  This method delivers false if the given eClassifier should not be mentioned inside a transformation module
	 * with focus on another eClassifier. The eClassifier here is NOT considered as focal point; i.e. it is merely
	 * considered as a <b>dangling</b> element.  For example the eClassifier "Region" is
	 * considered as a dangling element in the generation of a "CREATE_State_In_Region" transformation module.
	 * There can be various cases in which an eClassifier should be avoided as dangling when running certain module generations.
	 * @param eClassifier
	 * @param opType
	 * @param preferSupertypes
	 * @return false or true
	 * @throws OperationTypeNotImplementedException
	 */
	public Boolean isAllowedAsDangling(EClassifier eClassifier, Configuration.OperationType opType, Boolean preferSupertypes) throws OperationTypeNotImplementedException {

		boolean blackListed	= blackList.contains(eClassifier);
		boolean whiteListed	= whiteList.contains(eClassifier);
		boolean assumeAllOnWhitelist = whiteList.isEmpty();
		boolean requiredBySubtypes = isRequiredByWhitelistedSubtypes(eClassifier);
		boolean requiredByNeighbours = isRequiredByWhitelistedIncomingNeighbors(eClassifier, preferSupertypes);
		boolean requiredByParents = isRequiredByWhitelistedIncomingParent(eClassifier, preferSupertypes);	
		boolean requiredByChildren = isRequiredByWhitelistedChildren(eClassifier);
		
		// The two scenarios of hard cut-offs:
		// 1. blacklisted and no inconsistency prevention enabled
		// 2. whitelist is not empty, however the blacklist is. Addionally  no inconsistency prevention is enabled.
		boolean hardCutOff = ((blackListed && !c.PREVENT_INCONSISTENCY_THROUGHSKIPPING)
											  || (blackList.isEmpty() && !assumeAllOnWhitelist && !c.PREVENT_INCONSISTENCY_THROUGHSKIPPING));
		
		switch(opType) {
		
			case CREATE:
				
				if( hardCutOff
						|| (!hardCutOff && (!whiteListed && !assumeAllOnWhitelist) && !(requiredByChildren || requiredBySubtypes))) {
					return false;
				}
				break;
				
			case ADD:
				if( hardCutOff
						|| (!hardCutOff && (!whiteListed && !assumeAllOnWhitelist) && !requiredByChildren)) {
					return false;
				}
				break;

			case MOVE:
				if( hardCutOff
						|| (!hardCutOff && (!whiteListed && !assumeAllOnWhitelist) && !requiredByChildren)) {
					return false;
				}
				break;
				
			case MOVE_REFERENCE_COMBINATION:
				if( hardCutOff
						|| (!hardCutOff && (!whiteListed && !assumeAllOnWhitelist) && !requiredByChildren)) {
					return false;
				}
				break;
				
			case MOVE_UP:
				if( hardCutOff
						|| (!hardCutOff && (!whiteListed && !assumeAllOnWhitelist) && !requiredByChildren)) {
					return false;
				}
				break;
				
			case MOVE_DOWN:
				if( hardCutOff
						|| (!hardCutOff && (!whiteListed && !assumeAllOnWhitelist) && !requiredByChildren)) {
					return false;
				}
				break;
				
			case CHANGE_REFERENCE:
				if( hardCutOff
						|| (!hardCutOff && (!whiteListed && !assumeAllOnWhitelist) && !requiredByChildren)) {
					return false;
				}
				break;
				
			case SET_ATTRIBUTE:
				if( hardCutOff
						|| (!hardCutOff && (!whiteListed && !assumeAllOnWhitelist) && !requiredByChildren)) {
					return false;
				}
				break;
				
			case SET_REFERENCE:
				if( hardCutOff
						|| (!hardCutOff && (!whiteListed && !assumeAllOnWhitelist) && !requiredByChildren)) {
					return false;
				}
				break;
				
				
			// ----- inverses (don't need to be denied/allowed explicitly) ---------------------------/
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
				throw new OperationTypeNotImplementedException(opType.toString());
		}

		return true;
	}
	
	
	/***** Convenience Methods  ************************************************************/
	
	/**
	 * Checks if eClassifier is required by white listed incoming neighbour contexts.
	 * @param eClassifier
	 * @param preferSupertypes
	 * @return
	 */
	private Boolean isRequiredByWhitelistedIncomingNeighbors(EClassifier eClassifier, Boolean preferSupertypes) {
		
		boolean requiredByNeighbours = false;
		
		EClassifierInfo eInf = ECM.getEClassifierInfo(eClassifier);		
		for(Entry<EReference, List<EClassifier>> entry: eInf.getMandatoryNeighbourContext().entrySet()) {

			// find out if reference is pointing to eClass or to a super type of the eClass
			EReference eRef = entry.getKey();
			boolean eClassIsDirectTarget = eRef.getEType().equals(eClassifier);		
			
			for(EClassifier mnc: entry.getValue()) {										
				
				if(eClassIsDirectTarget && whiteList.contains(mnc)) {
					requiredByNeighbours = true;
					break;
				}
				else if(!eClassIsDirectTarget && whiteList.contains(mnc) && !preferSupertypes) {
					requiredByNeighbours = true;
					break;
				}					
			}		
		}
		return requiredByNeighbours;		
	}
	
	/**
	 * Checks if EClassifier is required by white listed incoming parent contexts
	 * @param eClassifier
	 * @param preferSupertypes
	 * @return
	 */
	private Boolean isRequiredByWhitelistedIncomingParent(EClassifier eClassifier, Boolean preferSupertypes) {
		
		boolean requiredByParents = false;
		
		EClassifierInfo eInf = ECM.getEClassifierInfo(eClassifier);
		for(Entry<EReference, List<EClassifier>> entry: eInf.getMandatoryParentContext().entrySet()) {

			// find out if reference is pointing to eClass or to a super type of the eClass
			EReference eRef = entry.getKey();
			boolean eClassIsDirectTarget = eRef.getEType().equals(eClassifier);		
			
			for(EClassifier mpc: entry.getValue()) {										
				
				if(eClassIsDirectTarget && whiteList.contains(mpc)) {
					requiredByParents = true;
					break;
				}
				else if(!eClassIsDirectTarget && whiteList.contains(mpc) && !preferSupertypes) {
					requiredByParents = true;
					break;
				}					
			}		
		}
				
		return requiredByParents;
	}
	
	
	/**
	 * Checks if EClassifier is required by white listed children.
	 * @param eClassifier
	 * @return
	 */
	private Boolean isRequiredByWhitelistedChildren(EClassifier eClassifier) {
		
		boolean requiredByChildren = false;
		
		for(EClassifier whiteListedEClass: whiteList) {
			for(Entry<EReference,List<EClassifier>> entry: ECM.getAllParentContexts(whiteListedEClass, false).entrySet()) {
				EReference eRefChildToParent = entry.getKey().getEOpposite();
				if(eRefChildToParent!=null) {
					int lb = eRefChildToParent.getLowerBound();
					int ub = eRefChildToParent.getUpperBound();
					boolean notFixedAndRequired = (ub-lb>0) && lb>0;

					if(notFixedAndRequired) {					

						List<EClassifier> parentContexts = entry.getValue();
						if(parentContexts.contains(eClassifier)) {
							requiredByChildren = true;
							break;
						}						
					}				
				}
			}
		}
		
		return requiredByChildren;
	}
	
	/**
	 * Checks if an EClassifier is required because it provides features for its sub types
	 * which are white listed.
	 * @param eClassifier
	 * @return
	 */
	private static Boolean isRequiredByWhitelistedSubtypes(EClassifier eClassifier) {

		boolean isRequired = false;
		EClassifierInfo eInfo = ECM.getEClassifierInfo(eClassifier);
		Set<EClassifierInfo> subTypes = ECM.getAllSubTypes(eInfo);
		
		for(EClassifierInfo subTypeInfo: subTypes) {
			EClassifier subtype = subTypeInfo.getTheEClassifier();
			if(whiteList.contains(subtype)) {
				isRequired = true;
				break;
			}
		}
		
		
		return isRequired;
	}
	
	/**
	 * Checks if an EClassifier is a profile meta-class of any white listed stereotype. 
	 * @param eClassifier
	 * @return
	 */
	private static Boolean isRequiredByWhitelistedStereotype(EClassifier eClassifier) {
		
		if(c.PROFILE_APPLICATION_IN_USE) {			
			Set<EClassifier> stereotypes = ECM.getAllStereotypes(eClassifier);
			for(EClassifier stereotype: stereotypes) {
				if(whiteList.contains(stereotype)) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	/**
	 * This method delivers a list with all names of EClassifiers contained in the given list.
	 * @param eClassifierList
	 * @return
	 * 		names of EClassifiers contained in the given list.
	 */
	protected static List<String> extractAllEClassifierNames(ArrayList<EClassifier> eClassifierList) {
		List<String> list = new ArrayList<String>();
		
		for(EClassifier ec: eClassifierList) {
			list.add(ec.getName());
		}
		return list;
	}
	
	/**
	 * This method delivers all allowed parent context regarding a childEClassifer.
	 * @param childEClassifier
	 * @param reduceToSupertype
	 * @param operationType
	 * @return
	 * @throws OperationTypeNotImplementedException
	 */
	public HashMap<EReference, List<EClass>>getAllAllowedParentContexts(EClassifier childEClassifier,
			Boolean reduceToSupertype, OperationType operationType) 
			throws OperationTypeNotImplementedException {
		
		HashMap<EReference, List<EClass>> allAllowedParents = new HashMap<EReference, List<EClass>>();
		
		// get possible eClassifier Masks for additional move generation of masked classifiers.
		List<Mask> eClassifierMasks = new ArrayList<Mask>();
		eClassifierMasks.addAll(ECM.getEClassifierInfo(childEClassifier).getMasks());

		// get all possible contexts (mandatory & optional) and the  according references
		HashMap<EReference, List<EClassifier>> allParents = ECM.getAllParentContexts(childEClassifier,
				reduceToSupertype);

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
					if (isAllowedAsDangling(parent, operationType, reduceToSupertype)) {
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
	
	
	
//	/****** Meta Model Slicer TESTING **********************************************************************************/
//	
//	public void sliceMetaModel() {
//		
//		Stack<EPackage> ePackagesStack = c.EPACKAGESSTACK;
//		EPackage metaModel = ePackagesStack.firstElement();
//		
//		MetaModelSlicer mms = new MetaModelSlicer();
//		String newNS_URI = new String(metaModel.getNsURI());
//		newNS_URI = newNS_URI.concat("_sliced");
//		List<EPackage> ePackages = new ArrayList<EPackage>(ePackagesStack);
//		List<String> whiteListAsStrings = extractAllEClassifierNames(whiteList);
//		mms.slice(metaModel, ePackages, whiteListAsStrings, blackList,newNS_URI, newNS_URI);
//	}
}
