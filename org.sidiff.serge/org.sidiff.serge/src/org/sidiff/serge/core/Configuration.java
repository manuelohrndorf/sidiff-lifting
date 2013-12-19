package org.sidiff.serge.core;

import java.util.Stack;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.sidiff.common.emf.extensions.impl.EClassifierInfoManagement;

@SuppressWarnings("unused")
public class Configuration {

	private boolean createCREATES;
	private boolean createDELETES;
	private boolean createMOVES;
	private boolean createADDS;
	private boolean createREMOVES;
	private boolean createSETS;

	private boolean createUNSETS;
	private boolean createCHANGES;
	
	private boolean multiplicityPreconditionsIntegrated;
	private boolean multiplicityPreconditionsSeparately;
	private boolean createNotRequiredAndNotIDAttributes;
	private boolean preventInconsistencyThroughSkipping;
	private boolean reduceToSuperType_SETUNSET;
	private boolean reduceToSuperType_ADDREMOVE;
	private boolean reduceToSuperType_CHANGE;
	private boolean reduceToSuperType_MOVE;
	private boolean reduceToSuperType_CREATEDELETE;

	private boolean literalSwitching_CHANGE;
	private boolean referenceSwitching_MOVE;	
	
	private EPackage metaModel;
	
	private String rootName;
	private static EClassifier root;
	private static Boolean rootEClassCanBeNested;
	
	private Boolean profileApplicationInUse;
	private Boolean disableVariants;
	
	private String outputFolderPath				  = null;
	private String baseModelRuleFolderPath		  = null;
	
	private static Stack<EPackage> ePackagesStack = null;	
	private static EClassifierInfoManagement ECM  = null;
	
	private static Configuration instance = null;
	
	public static enum OperationType { CREATE,DELETE,SET,UNSET,ADD,REMOVE,CHANGE,MOVE; }
	

	
	public static Configuration getInstance() {
		if(instance==null) {
			instance = new Configuration();
		}
		return instance;
	}	
	
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
	
	/***** public GETTER  ******************************************************************************/
	
	public boolean getPreventInconsistencyThroughSkipping() {
		return preventInconsistencyThroughSkipping;
	}

	public Boolean getProfileApplicationInUse() {
		return profileApplicationInUse;
	}
	
	public Stack<EPackage> getEPackagesStack() {
		return ePackagesStack;
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
