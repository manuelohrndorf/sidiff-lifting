package org.sidiff.serge.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Stack;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.sidiff.common.emf.extensions.impl.EClassifierInfo;
import org.sidiff.common.emf.extensions.impl.EClassifierInfoManagement;
import org.sidiff.common.emf.metamodelslicer.impl.MetaModelSlicer;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.serge.exceptions.OperationTypeNotImplementedException;
import org.sidiff.serge.generators.actions.CreateGenerator;
import org.sidiff.serge.services.AbstractGenerator_old.ImplicitRequirementType;

public class ElementFilter {

	private static ArrayList<EClassifier> blackList;	
	private static ArrayList<EClassifier> whiteList;
	
	private static Configuration c;
	private static EClassifierInfoManagement ECM;
	private static ElementFilter instance = null;
	
	private static HashMap<ImplicitRequirementType,ArrayList<EClassifier>> implicitRequirements;
	
	public static enum ImplicitRequirementType {INHERITING_SUPERTYPES, EXTENDED_METACLASSES;}
	
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
		
		implicitRequirements = new HashMap<ImplicitRequirementType, ArrayList<EClassifier>>();
		implicitRequirements.put(ImplicitRequirementType.INHERITING_SUPERTYPES, new ArrayList<EClassifier>());
		implicitRequirements.put(ImplicitRequirementType.EXTENDED_METACLASSES, new ArrayList<EClassifier>());
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

	public ArrayList<EClassifier> getImplicitRequirements(ImplicitRequirementType type) {
		return implicitRequirements.get(type);
	}
	
	
	public Boolean isAllowedAsModuleBasis(EClassifier eClassifier, Configuration.OperationType opType) throws OperationTypeNotImplementedException {
		
		EClassifierInfo eInf = ECM.getEClassifierInfo(eClassifier);

		boolean blackListed	= blackList.contains(eClassifier);
		boolean whiteListed	= whiteList.contains(eClassifier);
		boolean assumeAllOnWhitelist = whiteList.isEmpty();
		boolean providesFeaturesForSubtypes = implicitRequirements.get(ImplicitRequirementType.INHERITING_SUPERTYPES).contains(eClassifier);
		boolean requiredByNeighbours = false;
		boolean requiredByParents = false;	
		boolean requiredByChildren = false;
		
		switch(opType) {
		
			case CREATE:
				// deny eClassifier usage on the following conditions:
				if (
					(!c.CREATE_CREATES)
					|| (!(eClassifier instanceof EClass))
					|| (eClassifier instanceof EClass && ((EClass)eClassifier).isAbstract())
					|| (!eInf.selfMayHaveTransformations())
					|| (c.isAnUnnestableRoot(eClassifier))
					|| (c.PROFILEAPPLICATIONINUSE && eInf.isExtendedMetaClass() && !c.isRoot(eClassifier))
					|| (!whiteListed && !providesFeaturesForSubtypes )
					|| (assumeAllOnWhitelist && blackListed && !providesFeaturesForSubtypes)
					)
				return false;
				
				break;
			case DELETE:
				if (!c.CREATE_DELETES) return false;
				break;
			case ADD:				
				if (
					(!c.CREATE_ADDS)
					|| (c.PROFILEAPPLICATIONINUSE && eInf.isExtendedMetaClass() && !c.isRoot(eClassifier))
				)
				return false;
//				if (!(isAllowed(eClassifier,true,false) && !isImplicitlyRequiredForFeatureInheritance(eClassifier)))  return false;				
				break;
			case REMOVE:
				if (!c.CREATE_REMOVES) return false;
				break;
			case CHANGE_REFERENCE:
				if (
					(!c.CREATE_CHANGE_REFERENCES)
					|| (c.PROFILEAPPLICATIONINUSE && eInf.isExtendedMetaClass() && !c.isRoot(eClassifier))
					)
				return false;	
//				if (!(isAllowed(eClassifier,true,false) && !isImplicitlyRequiredForFeatureInheritance(eClassifier)))  return false;
				break;
			case MOVE:
				if (
					(!c.CREATE_MOVES)
					|| (c.isAnUnnestableRoot(eClassifier))
					|| (c.PROFILEAPPLICATIONINUSE && eInf.isExtendedMetaClass() && !c.isRoot(eClassifier))
				)
				return false;
//				if (!isAllowed(eClassifier,true,reduceToSuperType_MOVE) || createMOVES==false)  return;
				break;
			case MOVE_COMBINATION:
				if (
					(!c.CREATE_MOVE_COMBINATIONS)
					|| (c.isAnUnnestableRoot(eClassifier))
					|| (c.PROFILEAPPLICATIONINUSE && eInf.isExtendedMetaClass() && !c.isRoot(eClassifier))
			)
				return false;
	//			if (!isAllowed(eClassifier,true,reduceToSuperType_MOVE) || createMOVES==false)  return;
				break;
			case MOVE_UP:
				if (
					(!c.CREATE_MOVE_UPS)
					|| (c.isAnUnnestableRoot(eClassifier))
					|| (c.PROFILEAPPLICATIONINUSE && eInf.isExtendedMetaClass() && !c.isRoot(eClassifier))
				)
				return false;
//				if (!isAllowed(eClassifier,true,reduceToSuperType_MOVE) || createMOVES==false)  return;
				break;
			case MOVE_DOWN:
				if (
					(!c.CREATE_MOVE_DOWNS)
					|| (c.isAnUnnestableRoot(eClassifier))
					|| (c.PROFILEAPPLICATIONINUSE && eInf.isExtendedMetaClass() && !c.isRoot(eClassifier))
				)
				return false;
//				if (!isAllowed(eClassifier,true,reduceToSuperType_MOVE) || createMOVES==false)  return;
				break;
			case SET_ATTRIBUTE:
				if (
					(!c.CREATE_SET_ATTRIBUTES)
					|| (c.PROFILEAPPLICATIONINUSE && eInf.isExtendedMetaClass() && !c.isRoot(eClassifier))
				)		
				return false;
//				if (!(isAllowed(eClassifier,true,false) && !isImplicitlyRequiredForFeatureInheritance(eClassifier)))  return false;		
				break;
			case SET_REFERENCE:
				if (
					(!c.CREATE_SET_REFERENCES)
					|| (c.PROFILEAPPLICATIONINUSE && eInf.isExtendedMetaClass() && !c.isRoot(eClassifier))
					)
					return false;
//				if (!(isAllowed(eClassifier,true,false) && !isImplicitlyRequiredForFeatureInheritance(eClassifier)))  return false;
				
				break;
			case UNSET_ATTRIBUTE:
				if (
					(!c.CREATE_UNSET_ATTRIBUTES)
					|| (c.PROFILEAPPLICATIONINUSE && eInf.isExtendedMetaClass() && !c.isRoot(eClassifier))
					)
				return false;
//				if (!(isAllowed(eClassifier,true,false) && !isImplicitlyRequiredForFeatureInheritance(eClassifier)))  return false;
				break;
			case UNSET_REFERENCE:
				if (
					(!c.CREATE_UNSET_REFERENCES)
					|| (c.PROFILEAPPLICATIONINUSE && eInf.isExtendedMetaClass() && !c.isRoot(eClassifier))
					)
				return false;
//				if (!(isAllowed(eClassifier,true,false) && !isImplicitlyRequiredForFeatureInheritance(eClassifier)))  return false;
				break;
			default:
				throw new OperationTypeNotImplementedException(opType.toString());
		}
		
		
		
		
		return true;
	}
	
	public Boolean isAllowedAsDangling(EClassifier eClassifier, Configuration.OperationType opType, Boolean preferSupertypes) throws OperationTypeNotImplementedException {
		
		EClassifierInfo eInf = ECM.getEClassifierInfo(eClassifier);

		boolean blackListed	= blackList.contains(eClassifier);
		boolean whiteListed	= whiteList.contains(eClassifier);
		boolean assumeAllOnWhitelist = whiteList.isEmpty();
		boolean providesFeaturesForSubtypes = implicitRequirements.get(ImplicitRequirementType.INHERITING_SUPERTYPES).contains(eClassifier);
		boolean requiredByNeighbours = isRequiredByWhitelistedIncomingNeighbors(eClassifier, preferSupertypes);
		boolean requiredByParents = isRequiredByWhitelistedIncomingParent(eClassifier, preferSupertypes);	
		boolean requiredByChildren = isRequiredByWhitelistedChildren(eClassifier);
		boolean hardCutOff = blackListed && !c.PREVENTINCONSISTENCYTHROUGHSKIPPING;
		
		switch(opType) {
		
			case CREATE:
//				if (!isAllowed(context,false,reduceToSuperType_CREATEDELETE)) continue;
				break;
			case DELETE:
				//..
				break;
			case ADD:
				//..
				break;
			case REMOVE:
				//..
				break;
			case MOVE:
//				if (isAllowed(parent,false,reduceToSuperType_MOVE)) continue;
				//..
				break;
			case MOVE_COMBINATION:
//				if (isAllowed(parent,false,reduceToSuperType_MOVE)) continue;
				//..
				break;
			case MOVE_UP:
//				if (isAllowed(parent,false,reduceToSuperType_MOVE)) continue;
				//..
				break;
			case MOVE_DOWN:
//				if (isAllowed(parent,false,reduceToSuperType_MOVE)) continue;
				//..
				break;
			case SET_ATTRIBUTE:
				//..
				break;
			case SET_REFERENCE:
				//..
				break;
			case UNSET_ATTRIBUTE:
				//..
				break;
			case UNSET_REFERENCE:
				//..
				break;
			default:
				throw new OperationTypeNotImplementedException(opType.toString());
		}
		
		
		
		return true;
	}
	
	
	
	public Boolean eAttributeIsAllowed(EAttribute eAttribute) {
		
		//TODO
		
		return true;
	}
	
	public Boolean eReferenceIsAllowed(EReference eREference)  {
		
		//TODO
		
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
	 * 5. EClass is implicitly required by not white listed but recursively required EClasses (only if asPivot==false) //TODO<br />
	 * 6. EClass is required by incoming neighbour references of white listed EClasses<br />
	 * 7. EClass is required by incoming neighbour references of recursively required EClasses //TODO<br />
	 * 8. EClass is required by incoming parent references of white listed EClasses<br />
	 * 9. EClass is required by incoming parent references of recursively required EClasses<br />
 	 * 10. EClass is required by incoming child references of white listed EClasses<br />
 	 * 11. EClass is required by incoming child references of recursively required EClasses //TODO<br />
 	 * 12. more? //TODO
	 * 
	 * @param eClassifier
	 * @param asPivot
	 * @param preferSupertypes
	 * @return
	 */
	protected static boolean isAllowed(EClassifier eClassifier, Boolean asPivot, Boolean preferSupertypes) {
		
		EClassifierInfo eClassifierInfo = ECM.getEClassifierInfo(eClassifier);
		
		boolean blackListed	= blackList.contains(eClassifier);
		boolean whiteListed	= whiteList.contains(eClassifier);
		boolean assumeAllOnWhitelist = whiteList.isEmpty();
		boolean providesFeaturesForSubtypes = implicitRequirements.get(ImplicitRequirementType.INHERITING_SUPERTYPES).contains(eClassifier);
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
	 * Checks whether an EClassifier is implicitly required because it inherits its features
	 * to sub types which are white listed. Implicitly required EClassifiers are not required in CREATE/DELETES.
	 * Only in SET/ADD/CHANGE transformations.
	 * @param eClassifier
	 * @return
	 */
	protected static boolean isImplicitlyRequiredForFeatureInheritance(EClassifier eClassifier) {
		if(implicitRequirements.get(ImplicitRequirementType.INHERITING_SUPERTYPES).contains(eClassifier)) {
			return true;
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
