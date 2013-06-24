package org.sidiff.serge.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Module;
import org.sidiff.common.emf.ecore.EClassVisitor;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
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
	protected static List<EPackage> ePackages = null;
	protected static Map<ConstraintType, Map<EClass,Boolean>> constraints = null;
	
	protected static EClassInfoManagement eClassInfoManagement = null;
	
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
	
	protected boolean createINITIALS;
	protected boolean createNotRequiredAndNotIDAttributes;
	protected static boolean preventInconsistencyThroughSkipping;
	protected boolean reduceToSuperType_SETUNSET;
	protected boolean reduceToSuperType_ADDREMOVE;
	protected boolean reduceToSuperType_CHANGE;
	
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
			if(!eClass.isAbstract()) {
				generate_CREATE_And_DELETE_Modules(eClass);
			}
			generate_Update_Module(eClass);
			generate_MOVE_Module(eClass);
		}

	}

	@Override
	public void finish() {
		LogUtil.log(LogEvent.NOTICE, "finished");
		
	}

	/***** abstracts *****************************************************************************/
	
	public abstract void generate_CREATE_And_DELETE_Modules(EClass eClass);
	
	public abstract void generate_Update_Module(EClass eClass);
	
	public abstract void generate_MOVE_Module(EClass eClass);
	
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

	public void setCreateINITIALS(boolean createINITIALS) {
		this.createINITIALS = createINITIALS;
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
	
	public void setEPackages(List<EPackage> allEPackages) {
		ePackages = allEPackages;
		
	}
	
	public void setBaseModelRuleFolderPath(String baseModelRuleFolderPath) {
		this.baseModelRuleFolderPath = baseModelRuleFolderPath;
	}

	public void addConstraint(ConstraintType ctype, EClass eClass, Boolean flag) {
		if(constraints==null) {
			constraints = new HashMap<ConstraintType,Map<EClass,Boolean>>();		
		}
		if(constraints.get(ctype)==null) {
			Map<EClass, Boolean> classifierFlagMap = new HashMap<EClass,Boolean>();
			classifierFlagMap.put(eClass, flag);
			constraints.put(ctype, classifierFlagMap);
		}else{
			constraints.get(ctype).put(eClass,flag);
		}
	}
	
	public EClassInfoManagement initEClassInfoManagement(Boolean enableStereotypeMapping) {
		implicitRequirements = new HashMap<AbstractGenerator.ImplicitRequirementType, ArrayList<EClass>>();
		implicitRequirements.put(ImplicitRequirementType.INHERITING_SUPERTYPES, new ArrayList<EClass>());
		implicitRequirements.put(ImplicitRequirementType.EXTENDED_METACLASSES, new ArrayList<EClass>());
		eClassInfoManagement = EClassInfoManagement.getInstance(enableStereotypeMapping);			
		eClassInfoManagement.mapConcreteEClassesToAbstractSuperTypes(ePackages);
		eClassInfoManagement.gatherAllEClassInfos(ePackages);
		eClassInfoManagement.linkSubTypesToSuperTypes(ePackages);
		return eClassInfoManagement;
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
	
}
