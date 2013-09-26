package org.sidiff.serge.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Stack;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Module;
import org.sidiff.common.emf.ecore.EClassVisitor;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.serge.core.EClassifierInfo;
import org.sidiff.serge.core.EClassifierInfoManagement;
import org.sidiff.serge.core.MetaModelSlicer;
import org.sidiff.serge.exceptions.ConstraintException;

public abstract class AbstractGenerator implements EClassVisitor{

	/** General settings **************************************************************************/
	
	protected final String CREATE_prefix 			= "CREATE_";
	protected final String DELETE_prefix			= "DELETE_";
	protected final String SET_prefix 				= "SET_";
	protected final String UNSET_prefix 			= "UNSET_";
	protected final String ADD_prefix 				= "ADD_";
	protected final String REMOVE_prefix 			= "REMOVE_";
	protected final String CHANGE_prefix 			= "CHANGE_";
	protected final String MOVE_prefix 				= "MOVE_";
	protected final String EXECUTE_suffix			= "_execute.henshin";
	protected final String INITIALCHECK_suffix		= "_initialcheck.henshin";
	protected final String TGT						= "_TGT_";
	protected final String FROM						= "_FROM_";
	protected final String TO						= "_TO_";
	protected final String IN						= "_IN_";
	
	protected final String SEL						= "Selected";
	protected final String SELEO					= "selectedEObject";
	protected final String CHILD					= "Child";
	protected final String EX						= "Existing";
	protected final String NEW						= "New";
	protected final String DEL						= "ToBeDeleted";
	protected final String NEWTGT					= "NewTarget";
	protected final String NEWSRC					= "NewSource";
	protected final String OLDTGT					= "OldTarget";
	protected final String OLDSRC					= "OldSource";
	
	protected String outputFolderPath 				= null;
	protected String baseModelRuleFolderPath  		= null;
	
	protected static Stack<EPackage> ePackagesStack = null;	
	protected static EClassifierInfoManagement ecm 		= null;
	
	public static enum ImplicitRequirementType {INHERITING_SUPERTYPES, EXTENDED_METACLASSES; }
	public static enum ConstraintType {NAME_UNIQUENESS_LOCAL, NAME_UNIQUENESS_GLOBAL};
	protected static enum OperationType { CREATE,DELETE,SET,UNSET,ADD,REMOVE,CHANGE,MOVE; }

	
	/** Configuration *****************************************************************************/

	protected boolean createCREATES;
	protected boolean createDELETES;// CREATES are required
	protected boolean createMOVES;
	protected boolean createADDS;
	protected boolean createREMOVES;// ADDS are required
	protected boolean createSETS;
	protected boolean createUNSETS; // SETS are required
	protected boolean createCHANGES;
	
	protected boolean multiplicityPreconditionsIntegrated;
	protected boolean multiplicityPreconditionsSeparately;
	protected boolean createNotRequiredAndNotIDAttributes;
	protected static boolean preventInconsistencyThroughSkipping;
	protected boolean reduceToSuperType_SETUNSET;
	protected boolean reduceToSuperType_ADDREMOVE;
	protected boolean reduceToSuperType_CHANGE;
	protected boolean reduceToSuperType_MOVE;
	protected boolean reduceToSuperType_CREATEDELETE;

	protected boolean literalSwitching_CHANGE;
	protected boolean referenceSwitching_MOVE;	
	
	protected EPackage metaModel;
	
	protected static ArrayList<EClassifier> blackList;	
	protected static ArrayList<EClassifier> whiteList;
	
	protected static HashMap<ImplicitRequirementType,ArrayList<EClassifier>> implicitRequirements;
	
	protected String rootName;
	protected static EClassifier root;
	protected static Boolean rootEClassCanBeNested;
	
	protected Boolean profileApplicationInUse;
	protected Boolean disableVariants;
	

	@Override
	public void eClassifier(EClassifier eClassifier, String fullyQualifiedPath) {


		LogUtil.log(LogEvent.NOTICE, "***** " + eClassifier.getName() + " ***********************************************");

		if(eClassifier instanceof EClass) {		
			EClass eClass = (EClass) eClassifier;
			LogUtil.log(LogEvent.NOTICE, "***** " + eClass.getName() + " ***********************************************");

			try {
				if(!eClass.isAbstract()) {
					generate_CREATE_And_DELETE_Modules(eClass);
				}
				generate_Update_Module(eClass);
				generate_MOVE_Module(eClass);
			} catch (ConstraintException e) {
				e.printStackTrace();
			}

		}

	}

	@Override
	public void finish() {
		LogUtil.log(LogEvent.NOTICE, "finished");
		
	}

	/***** abstracts  *****************************************************************************/
	
	public abstract void generate_CREATE_And_DELETE_Modules(EClassifier eClassifier) throws ConstraintException;
	
	public abstract void generate_Update_Module(EClassifier eClassifier) throws ConstraintException;
	
	public abstract void generate_MOVE_Module(EClassifier eClassifier) throws ConstraintException;
	
	public abstract void serialize(Module module, String outputFileName);

	/***** public SETTER *****************************************************************************/
	
	public void setOutputFolderPath(String outputFolderPath) {
		this.outputFolderPath = outputFolderPath;
	}
	
	public void setCreateCREATES(boolean createCREATES) {
		this.createCREATES = createCREATES;
	}

	public void setCreateDELETES(boolean createDELETES) {
		this.createDELETES = createDELETES;
	}

	public void setCreateMOVES(boolean createMOVES) {
		this.createMOVES = createMOVES;
	}

	public void setCreateADDS(boolean createADDS) {
		this.createADDS = createADDS;
	}

	public void setCreateREMOVES(boolean createREMOVES) {
		this.createREMOVES = createREMOVES;
	}

	public void setCreateSETS(boolean createSETS) {
		this.createSETS = createSETS;
	}

	public void setCreateUNSETS(boolean createUNSETS) {
		this.createUNSETS = createUNSETS;
	}
	
	public void setCreateCHANGES(boolean createCHANGES) {
		this.createCHANGES = createCHANGES;
	}
	
	public void setLiteralSwitching_CHANGE(boolean literalSwitching_CHANGE) {
		this.literalSwitching_CHANGE = literalSwitching_CHANGE;
	}

	public void setReferenceSwitching_MOVE(boolean referenceSwitching_MOVE) {
		this.referenceSwitching_MOVE = referenceSwitching_MOVE;
	}

	public void setMultiplicityPreconditions(boolean integrated, boolean separately) {
		this.multiplicityPreconditionsIntegrated = integrated;
		this.multiplicityPreconditionsSeparately = separately;
	}

	public void setCreateNotRequiredAndNotIDAttributes(
			boolean createNotRequiredAndNotIDAttributes) {
		this.createNotRequiredAndNotIDAttributes = createNotRequiredAndNotIDAttributes;
	}

	public void setPreventInconsistencyThroughSkipping(
			boolean preventInconsistency) {
		preventInconsistencyThroughSkipping = preventInconsistency;
	}

	public void setReduceToSuperType_SETUNSET(
			boolean reduceToSuperType_SETUNSET) {
		this.reduceToSuperType_SETUNSET = reduceToSuperType_SETUNSET;
	}

	public void setReduceToSuperType_ADDREMOVE(
			boolean reduceToSuperType_ADDREMOVE) {
		this.reduceToSuperType_ADDREMOVE = reduceToSuperType_ADDREMOVE;
	}	

	public void setReduceToSuperType_CHANGE(boolean reduceToSuperType_CHANGE) {
		this.reduceToSuperType_CHANGE = reduceToSuperType_CHANGE;
	}

	public void setReduceToSuperType_MOVE(boolean reduceToSuperType_MOVE) {
		this.reduceToSuperType_MOVE = reduceToSuperType_MOVE;
	}
	
	public void setReduceToSuperType_CREATEDELETE(boolean reduceToSuperType_CREATEDELETE) {
		this.reduceToSuperType_CREATEDELETE = reduceToSuperType_CREATEDELETE;
	}
	
	public void setBlackList(ArrayList<EClassifier> bList) {
		blackList = bList;
	}

	public void setWhiteList(ArrayList<EClassifier> wList) {
		whiteList = wList;
	}

	public void setRoot(EClassifier rootEClass) {
		root = rootEClass;
	}

	public void setRootEClassCanBeNested(Boolean rootEClassCanBeNested) {
		this.rootEClassCanBeNested = rootEClassCanBeNested;
	}

	public void setProfileApplicationInUse(Boolean profileApplicationInUse) {
		this.profileApplicationInUse = profileApplicationInUse;
	}

	public void setDisableVariants(Boolean disableVariants) {
		this.disableVariants = disableVariants;
	}

	public void setMetaModel(EPackage ePackage) {
		this.metaModel=ePackage;
		
	}
	
	public void setEPackages(Stack<EPackage> stackWithAllEPackages) {
		ePackagesStack = stackWithAllEPackages;
		
	}
	
	public void setBaseModelRuleFolderPath(String baseModelRuleFolderPath) {
		this.baseModelRuleFolderPath = baseModelRuleFolderPath;
	}
	
	public EClassifierInfoManagement initEClassInfoManagement(Boolean enableStereotypeMapping) {
		implicitRequirements = new HashMap<AbstractGenerator.ImplicitRequirementType, ArrayList<EClassifier>>();
		implicitRequirements.put(ImplicitRequirementType.INHERITING_SUPERTYPES, new ArrayList<EClassifier>());
		implicitRequirements.put(ImplicitRequirementType.EXTENDED_METACLASSES, new ArrayList<EClassifier>());
		ecm = EClassifierInfoManagement.getInstance(enableStereotypeMapping, ePackagesStack);
		return ecm;
	}
	
	
	public void sliceMetaModel() {

		/****** Meta Model Slicer TESTING **********************************************************************************/
		MetaModelSlicer mms = new MetaModelSlicer();
		String newNS_URI = new String(metaModel.getNsURI());
		newNS_URI = newNS_URI.concat("_sliced");
		mms.slice(metaModel, ePackagesStack, whiteList, blackList,newNS_URI);
	}
	
	
	
	/***** public GETTER  ******************************************************************************/
	
	public boolean getPreventInconsistencyThroughSkipping() {
		return preventInconsistencyThroughSkipping;
	}

	public ArrayList<EClassifier> getBlackList() {
		return blackList;
	}

	public ArrayList<EClassifier> getWhiteList() {
		return whiteList;
	}

	public Boolean getProfileApplicationInUse() {
		return profileApplicationInUse;
	}

	public ArrayList<EClassifier> getImplicitRequirements(ImplicitRequirementType type) {
		return implicitRequirements.get(type);
	}
	

	/***** protected, convenience Methods  ************************************************************/

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
		
		EClassifierInfo eClassifierInfo = ecm.getEClassifierInfo(eClassifier);
		
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
			if(!preventInconsistencyThroughSkipping){
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
					for(Entry<EReference,List<EClassifier>> entry: ecm.getAllParentContext(whiteListedEClass, false).entrySet()) {
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
						|| requiredByNeighbours
						|| requiredByParents
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
	 * Checks whether an eClassifier is the user specified root element
	 * @param eClassifier
	 * @return
	 */	
	protected static boolean isRoot(EClassifier eClassifier) {
		return root==eClassifier;
	}
	
	/**
	 * Checks whether a configured root element is configured to be nested
	 * (Example: The children of the root StateMachine can contain further sub-StateMachines)
	 * @param eClassifier
	 * @return
	 */
	protected static boolean rootCanBeNested(EClassifier eClassifier) {
		return rootEClassCanBeNested;
	}
	
	/**
	 * Checks if a given EReference is inherited
	 * @param the EReference
	 * @param concerningEClassifier is the class to check on
	 * @return true if inherited
	 */
	protected static boolean isInheritedReference(EReference eRef, EClassifier concerningEClassifier) {
		
		if(concerningEClassifier instanceof EClass) {
			EClass eClass = (EClass) concerningEClassifier;
			return !eClass.getEReferences().contains(eRef);
		}
		
		return true;
	}
	
	
}
