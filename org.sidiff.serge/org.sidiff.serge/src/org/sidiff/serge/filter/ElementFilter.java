package org.sidiff.serge.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.sidiff.common.emf.extensions.impl.EClassifierInfo;
import org.sidiff.common.emf.extensions.impl.EClassifierInfoManagement;
import org.sidiff.common.emf.metamodelslicer.impl.MetaModelSlicer;
import org.sidiff.serge.configuration.Configuration;
import org.sidiff.serge.exceptions.OperationTypeNotImplementedException;

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
					|| (!whiteListed && !assumeAllOnWhitelist && !requiredBySubtypes )
					|| (assumeAllOnWhitelist && blackListed && !requiredBySubtypes)
					)
				return false;				
				break;
				
			case ADD:				
				if (
						( (!whiteListed && !assumeAllOnWhitelist && !requiredBySubtypes ) && (!requiredByStereotypes)&& (!c.isRoot(eClassifier)))
						|| ((assumeAllOnWhitelist && blackListed && !requiredBySubtypes) &&(!requiredByStereotypes) && (!c.isRoot(eClassifier))) 
						) {
					return false;
				}	
				break;
				
			case CHANGE_REFERENCE:
				if (
						( (!whiteListed && !assumeAllOnWhitelist && !requiredBySubtypes ) && (!requiredByStereotypes))
						|| ((assumeAllOnWhitelist && blackListed && !requiredBySubtypes) &&(!requiredByStereotypes)) 
						) {
					return false;
				}	
				break;
				
			case CHANGE_LITERAL:
				if (
						( (!whiteListed && !assumeAllOnWhitelist && !requiredBySubtypes ) && (!requiredByStereotypes))
						|| ((assumeAllOnWhitelist && blackListed && !requiredBySubtypes) &&(!requiredByStereotypes)) 
						) {
					return false;
				}	
				break;
				
			case MOVE:
				if ((c.isAnUnnestableRoot(eClassifier))
					|| (!requiredByStereotypes && !c.isRoot(eClassifier))) {
					return false;
				}
				break;
				
			case MOVE_REFERENCE_COMBINATION:
				if ((c.isAnUnnestableRoot(eClassifier))
						|| (!requiredByStereotypes && !c.isRoot(eClassifier))) {
						return false;
					}
				break;
				
			case MOVE_UP:
				if ((c.isAnUnnestableRoot(eClassifier))
						|| (!requiredByStereotypes && !c.isRoot(eClassifier))) {
						return false;
					}
				break;
				
			case MOVE_DOWN:
				if ((c.isAnUnnestableRoot(eClassifier))
						|| (!requiredByStereotypes && !c.isRoot(eClassifier))) {
						return false;
					}
				break;
				
			case SET_ATTRIBUTE:
				if (
						( (!whiteListed && !assumeAllOnWhitelist && !requiredBySubtypes ) && (!requiredByStereotypes))
						|| ((assumeAllOnWhitelist && blackListed && !requiredBySubtypes) &&(!requiredByStereotypes)) 
						) {
					return false;
				}			
				break;
				
			case SET_REFERENCE:
				if (
						( (!whiteListed && !assumeAllOnWhitelist && !requiredBySubtypes ) && (!requiredByStereotypes))
						|| ((assumeAllOnWhitelist && blackListed && !requiredBySubtypes) &&(!requiredByStereotypes)) 
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
		
		EClassifierInfo eInf = ECM.getEClassifierInfo(eClassifier);

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
		boolean hardCutOff = ((blackListed && !c.PREVENTINCONSISTENCYTHROUGHSKIPPING)
											  || (blackList.isEmpty() && !assumeAllOnWhitelist && !c.PREVENTINCONSISTENCYTHROUGHSKIPPING));
		
		switch(opType) {
		
			case CREATE:
				
				if( hardCutOff
						|| (!hardCutOff && !whiteListed && !requiredByChildren)) {
					return false;
				}
				break;
				
			case ADD:
				if( hardCutOff
						|| (!hardCutOff && !whiteListed && !requiredByChildren)) {
					return false;
				}
				break;

			case MOVE:
				if( hardCutOff
						|| (!hardCutOff && !whiteListed && !requiredByChildren)) {
					return false;
				}
				break;
				
			case MOVE_REFERENCE_COMBINATION:
				if( hardCutOff
						|| (!hardCutOff && !whiteListed && !requiredByChildren)) {
					return false;
				}
				break;
				
			case MOVE_UP:
				if( hardCutOff
						|| (!hardCutOff && !whiteListed && !requiredByChildren)) {
					return false;
				}
				break;
				
			case MOVE_DOWN:
				if( hardCutOff
						|| (!hardCutOff && !whiteListed && !requiredByChildren)) {
					return false;
				}
				break;
				
			case CHANGE_REFERENCE:
				if( hardCutOff
						|| (!hardCutOff && !whiteListed && !requiredByChildren)) {
					return false;
				}
				break;
				
			case SET_ATTRIBUTE:
				if( hardCutOff
						|| (!hardCutOff && !whiteListed && !requiredByChildren)) {
					return false;
				}
				break;
				
			case SET_REFERENCE:
				if( hardCutOff
						|| (!hardCutOff && !whiteListed && !requiredByChildren)) {
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
			for(Entry<EReference,List<EClassifier>> entry: ECM.getAllParentContext(whiteListedEClass, false).entrySet()) {
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
		
		if(c.PROFILEAPPLICATIONINUSE) {
			EClassifierInfo eInfo = ECM.getEClassifierInfo(eClassifier);
			
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
	 * Checks whether an EClass is allowed to be used by configuration details:<br /> <br />
	 * Set the parameter asPivot to true, if the focus of the transformation lies on the
	 * given EClass (e.g. CREATE_Operation..., or SET_Operation_Name, MOVE_OperationInClass; Here the focus lies on Operation).
	 * Set it to false, if the focus does not lie on the given EClass (e.g. CREATE_ParameterInOperation).
	 * <br /><br />
	 * Set preferSuperTypes to true, if EClasses that are sub types of white listed super types shall be denied. 
	 * <br /><br />
	 * An EClass can also be denied if the global variable preventInconsistency is false and EClass is blackListed (hard cutoff)<br/>
	 * An EClass can be allowed if preventInconsistency is true:<br />
	 * 1. nothing is on the white list and nothing is on the blacklist<br />
	 * 2. nothing is on the white list and EClasses other than this are blacklisted<br />
	 * 3. white list is not empty and current EClass is not blacklisted<br />
	 * 4. EClass is implicitly required by white listed EClasses (only if asPivot==false)<br />
	 * 5. EClass is implicitly required by not white listed but recursively required EClasses (only if asPivot==false)<br />
	 * 6. EClass is required by incoming neighbour references of white listed EClasses<br />
	 * 7. EClass is required by incoming neighbour references of recursively required EClasses<br />
	 * 8. EClass is required by incoming parent references of white listed EClasses<br />
	 * 9. EClass is required by incoming parent references of recursively required EClasses<br />
 	 * 10. EClass is required by incoming child references of white listed EClasses<br />
 	 * 11. EClass is required by incoming child references of recursively required EClasses<br />
 	 * 12. more?
	 * 
	 * @param eClassifier
	 * @param asPivot
	 * @param preferSupertypes
	 * @return
	 */
	@Deprecated
	protected static boolean isAllowed(EClassifier eClassifier, Boolean asPivot, Boolean preferSupertypes) {
		
		EClassifierInfo eClassifierInfo = ECM.getEClassifierInfo(eClassifier);
		
		boolean blackListed	= blackList.contains(eClassifier);
		boolean whiteListed	= whiteList.contains(eClassifier);
		boolean assumeAllOnWhitelist = whiteList.isEmpty();
		boolean providesFeaturesForSubtypes = isRequiredByWhitelistedSubtypes(eClassifier);
		boolean requiredByNeighbours = false;
		boolean requiredByParents = false;	
		boolean requiredByChildren = false;

		
		
		//in case use of EClass is in its own context ---------------------------------------------/
		if(asPivot) {
			if(whiteListed
					|| providesFeaturesForSubtypes
					|| (blackListed==false && assumeAllOnWhitelist)) {
				return true;
			}else{
				return false;
			}		
		}
		
		//in case use of EClass is inside another context (as neighbour or child or as parent) --/
		else{
			
			// hard cutoff classifiers
			if(!c.PREVENTINCONSISTENCYTHROUGHSKIPPING){
				if(blackListed) { //hard cut
					return false;
				}

			// soft cutoff classifiers
			}else {
				
				/*** check if eClass is required by white listed incoming neighbour contexts *************************/
				
				for(Entry<EReference, List<EClassifier>> entry: eClassifierInfo.getMandatoryNeighbourContext().entrySet()) {

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

				/*** check if EClassifier is required by white listed incoming parent contexts ****************************/
				
				for(Entry<EReference, List<EClassifier>> entry: eClassifierInfo.getMandatoryParentContext().entrySet()) {

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
				
				/*** check if EClassifier is required by white listed children ****************************************************/
				
				for(EClassifier whiteListedEClass: whiteList) {
					for(Entry<EReference,List<EClassifier>> entry: ECM.getAllParentContext(whiteListedEClass, false).entrySet()) {
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
				
				/** decide ****************************************************************************************************/	
				
				if(	whiteListed
						|| requiredByChildren
						|| (requiredByNeighbours && asPivot)
						|| (requiredByParents && asPivot)
						|| (blackListed==false && assumeAllOnWhitelist)) {
					return true;
				}
				else{
					return false;
				}
			}			
		}		
		return true;
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
	
	/****** Meta Model Slicer TESTING **********************************************************************************/
	
	public void sliceMetaModel() {
		
		Stack<EPackage> ePackagesStack = c.EPACKAGESSTACK;
		EPackage metaModel = ePackagesStack.firstElement(); //TODO first or last?
		
		MetaModelSlicer mms = new MetaModelSlicer();
		String newNS_URI = new String(metaModel.getNsURI());
		newNS_URI = newNS_URI.concat("_sliced");
		List<EPackage> ePackages = new ArrayList<EPackage>(ePackagesStack);
		List<String> whiteListAsStrings = extractAllEClassifierNames(whiteList);
		mms.slice(metaModel, ePackages, whiteListAsStrings, blackList,newNS_URI, newNS_URI);
	}
}
