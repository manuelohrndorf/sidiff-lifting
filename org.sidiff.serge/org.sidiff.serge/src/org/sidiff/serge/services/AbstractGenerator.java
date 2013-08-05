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
import org.sidiff.serge.exceptions.ConstraintException;
import org.sidiff.serge.util.EClassInfo;
import org.sidiff.serge.util.EClassInfoManagement;

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
	
	protected String outputFolderPath  = null;
	protected String baseModelRuleFolderPath  = null;
	protected static Stack<EPackage> ePackagesStack = null;
	
	protected static EClassInfoManagement ecm = null;
	
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

	protected boolean literalSwitching_CHANGE;
	protected boolean referenceSwitching_MOVE;	
	
	protected EPackage metaModel;
	
	protected static ArrayList<EClass> blackList;	
	protected static ArrayList<EClass> whiteList;
	
	protected static HashMap<ImplicitRequirementType,ArrayList<EClass>> implicitRequirements;
	
	protected String rootName;
	protected static EClass root;
	protected Boolean rootEClassCanBeNested;
	
	protected Boolean profileApplicationInUse;
	protected Boolean disableVariants;
	

	@Override
	public void eClassifier(EClassifier eClassifier, String fullyQualifiedPath) {
		
		if(eClassifier instanceof EClass) {		
			EClass eClass = (EClass) eClassifier;
			LogUtil.log(LogEvent.NOTICE, "***** " + eClass.getName() + " ***********************************************");
			
			// DELETE ---------------------------------------------
			if(!eClass.isAbstract()) {
				try {
					generate_CREATE_And_DELETE_Modules(eClass);
					generate_Update_Module(eClass);
					generate_MOVE_Module(eClass);
				} catch (ConstraintException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public void finish() {
		LogUtil.log(LogEvent.NOTICE, "finished");
		
	}

	/***** abstracts  *****************************************************************************/
	
	public abstract void generate_CREATE_And_DELETE_Modules(EClass eClass) throws ConstraintException;
	
	public abstract void generate_Update_Module(EClass eClass) throws ConstraintException;
	
	public abstract void generate_MOVE_Module(EClass eClass) throws ConstraintException;
	
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

	public void setBlackList(ArrayList<EClass> bList) {
		blackList = bList;
	}

	public void setWhiteList(ArrayList<EClass> wList) {
		whiteList = wList;
	}

	public void setRoot(EClass rootEClass) {
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
	
	public EClassInfoManagement initEClassInfoManagement(Boolean enableStereotypeMapping) {
		implicitRequirements = new HashMap<AbstractGenerator.ImplicitRequirementType, ArrayList<EClass>>();
		implicitRequirements.put(ImplicitRequirementType.INHERITING_SUPERTYPES, new ArrayList<EClass>());
		implicitRequirements.put(ImplicitRequirementType.EXTENDED_METACLASSES, new ArrayList<EClass>());
		ecm = EClassInfoManagement.getInstance(enableStereotypeMapping);			
		ecm.mapConcreteEClassesToAbstractSuperTypes(ePackagesStack);
		ecm.gatherAllEClassInfos(ePackagesStack);
		ecm.linkSubTypesToSuperTypes(ePackagesStack);
		return ecm;
	}
	
	/***** public GETTER  ******************************************************************************/
	
	public boolean getPreventInconsistencyThroughSkipping() {
		return preventInconsistencyThroughSkipping;
	}

	public ArrayList<EClass> getBlackList() {
		return blackList;
	}

	public ArrayList<EClass> getWhiteList() {
		return whiteList;
	}

	public Boolean getProfileApplicationInUse() {
		return profileApplicationInUse;
	}

	public ArrayList<EClass> getImplicitRequirements(ImplicitRequirementType type) {
		return implicitRequirements.get(type);
	}
	

	/***** protected, convenience Methods  ************************************************************/

	/**
	 * Checks whether an eClass is part of the blackList or on whiteList or required in other ways.
	 * The parameter asPivot should be TRUE, if the main focus of
	 * the generatable transformation lies on that eClass (meaning the eClass is
	 * not just a mandatory or optional dangling model element).
	 * @param eClass
	 * @param asPivot
	 * @return
	 */
	protected static boolean isAllowed(EClass eClass, Boolean asPivot) {
		
		EClassInfo eClassInfo = ecm.getEClassInfo(eClass);
		
		boolean blackListed	= blackList.contains(eClass);
		boolean whiteListed	= whiteList.contains(eClass);
		boolean assumeAllOnWhitelist = whiteList.isEmpty();
		boolean requiredForFeatureInheritance = implicitRequirements.get(ImplicitRequirementType.INHERITING_SUPERTYPES).contains(eClass);

		
		
		//in case use of EClass is in its own context ---------------------------------------------/
		if(asPivot) {			

			if(whiteListed
					|| requiredForFeatureInheritance
					|| (blackListed==false && assumeAllOnWhitelist)) {
				return true;
			}else{
				return false;
			}

			
		}//in case use of EClass is inside another context (as neighbour or child or as parent) --/
		else{
			
			if(preventInconsistencyThroughSkipping){				
				boolean requiredForContexts = false;
				boolean requiredForChild = false;
				
				//check if one of the mandatory contexts of this EClass is white listed or implicitly
				//required. If so this EClass is necessary too.					
				for(EClass mandatoryContext: eClassInfo.getMandatoryContexts()) {			

					if( whiteList.contains(mandatoryContext)
							|| implicitRequirements.get(ImplicitRequirementType.INHERITING_SUPERTYPES).contains(mandatoryContext)
							|| (blackList.contains(mandatoryContext)==false && assumeAllOnWhitelist)) {
						requiredForContexts =  true;
						break;
					}		
				}
				
				//check if current eClass is the parent of some white listed EClass and therefore necessary
				for(EClass whiteListedEClass: whiteList) {
					for(Entry<EReference,List<EClass>> entry: ecm.getAllOptionalParentContext(whiteListedEClass).entrySet()) {
						if(entry.getValue().contains(eClass)) {
							requiredForChild = true;
							break;
						}
					}
					if(requiredForChild) break;
				}
				
				if(whiteListed
						|| requiredForFeatureInheritance
						|| requiredForChild
						|| requiredForContexts
						|| (blackListed==false && assumeAllOnWhitelist)) {
					return true;
				}
				else{
					return false;
				}
			}else{ //preventInconsistencyThroughSkipping==false
				if(blackListed) { //hard cut
					return false;
				}
			}
			
		}		
		return true;
	}

	
	/**
	 * Checks whether an EClass is only implicitly required because it inherits its features
	 * to sub types which are white listed. Implicitly required EClasses do not need CREATE/DELETES.
	 * Only the SET/MOVE/ADD/CHANGE transformations.
	 * @param eClass
	 * @return
	 */
	protected static boolean isOnlyImplicitlyRequiredForFeatureInheritance(EClass eClass) {
		if(implicitRequirements.get(ImplicitRequirementType.INHERITING_SUPERTYPES).contains(eClass) && !whiteList.contains(eClass)) {
			return true;
		}
		return false;
	}
	

	
	/**
	 * Checks whether an eClass is the user specified root element
	 * @param eClass
	 * @return
	 */	
	protected static boolean isRoot(EClass eClass) {
		return root==eClass;
	}
	
	/**
	 * Checks if a given EReference is inherited
	 * @param the EReference
	 * @param concerningEClass is the class to check on
	 * @return true if inherited
	 */
	protected static boolean isInheritedReference(EReference eRef, EClass concerningEClass) {
		
		return !concerningEClass.getEReferences().contains(eRef);
	}
	
	
}
