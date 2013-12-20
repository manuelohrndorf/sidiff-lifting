package org.sidiff.serge.core;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Map.Entry;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sidiff.common.emf.extensions.impl.EClassifierInfo;
import org.sidiff.common.emf.extensions.impl.EClassifierInfoManagement;
import org.sidiff.common.emf.extensions.impl.Mask;
import org.sidiff.common.emf.extensions.impl.EClassifierInfo.ConstraintType;
import org.sidiff.common.emf.modelstorage.ModelStorage;
import org.sidiff.common.io.IOUtil;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.common.xml.XMLParser;
import org.sidiff.serge.exceptions.EAttributeNotFoundException;
import org.sidiff.serge.exceptions.EClassifierUnresolvableException;
import org.sidiff.serge.exceptions.EPackageNotFoundException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ConfigurationParser {
	
	private static Configuration c 							= Configuration.getInstance();
	private static EClassifierInfoManagement ECM 			= EClassifierInfoManagement.getInstance();
	private static ElementFilter filter 					= ElementFilter.getInstance();
	
	private static Stack<EPackage> calculatedEPackagesStack	= new Stack<EPackage>();
	private static List<String> stringWhiteList 			= new ArrayList<String>();
	private static List<String> stringBlackList 			= new ArrayList<String>();
	private static String rootName							= null;

	public void parse (String pathToConfig) throws Exception {
			
		//TODO other optypes
		//TODO workspace_loc
		String workspace_loc = null;
		
		
		Document doc = XMLParser.parseStream(IOUtil.getInputStream(pathToConfig));
		
		Element docElem = doc.getDocumentElement();
		org.w3c.dom.Node currentNode = null;
		NodeList currentChildNodes = null;
		
		// retrieve and set general settings	
		currentNode = doc.getElementsByTagName("preventInconsistency").item(0);
		c.PREVENTINCONSISTENCYTHROUGHSKIPPING =Boolean.valueOf(Common.getAttributeValue("value", currentNode));
		currentNode = doc.getElementsByTagName("multiplicityPreconditions").item(0);		
		c.MULTIPLICITYPRECONDITIONSINTEGRATED=Boolean.valueOf(Common.getAttributeValue("integrated", currentNode));
		c.MULTIPLICITYPRECONDITIONSSEPARATELY=Boolean.valueOf(Common.getAttributeValue("separately", currentNode));
		currentNode = doc.getElementsByTagName("disableVariants").item(0);		
		c.DISABLEVARIANTS=Boolean.valueOf(Common.getAttributeValue("value", currentNode));
		currentNode = doc.getElementsByTagName("createAllAttributes").item(0);		
		c.CREATENOTREQUIREDANDNOTIDATTRIBUTES=Boolean.valueOf(Common.getAttributeValue("value", currentNode));
		currentNode = doc.getElementsByTagName("modelUsesProfileMechanism").item(0);		
		c.PROFILEAPPLICATIONINUSE=Boolean.valueOf(Common.getAttributeValue("value", currentNode));
		currentNode = doc.getElementsByTagName("outputFolder").item(0);		
		c.OUTPUTFOLDERPATH=String.valueOf(Common.getAttributeValue("absolutePath", currentNode));
		currentNode = doc.getElementsByTagName("reduceToSuperType").item(0);
		c.REDUCETOSUPERTYPE_SETUNSET=Boolean.valueOf(Common.getAttributeValue("SET_UNSET", currentNode));
		c.REDUCETOSUPERTYPE_ADDREMOVE=Boolean.valueOf(Common.getAttributeValue("ADD_REMOVE", currentNode));
		c.REDUCETOSUPERTYPE_CHANGE=Boolean.valueOf(Common.getAttributeValue("CHANGE", currentNode));
		c.REDUCETOSUPERTYPE_MOVE=Boolean.valueOf(Common.getAttributeValue("MOVE", currentNode));
		c.REDUCETOSUPERTYPE_CREATEDELETE=Boolean.valueOf(Common.getAttributeValue("CREATE_DELETE", currentNode));
		
	
		// retrieve and set operation types
		currentNode = doc.getElementsByTagName("Creates").item(0);
		c.CREATE_CREATES=Boolean.valueOf(Common.getAttributeValue("allow", currentNode));
		currentNode = doc.getElementsByTagName("Deletes").item(0);
		c.CREATE_DELETES=Boolean.valueOf(Common.getAttributeValue("allow", currentNode));
		currentNode = doc.getElementsByTagName("Moves").item(0);
		c.CREATE_MOVES=Boolean.valueOf(Common.getAttributeValue("allow", currentNode));
		c.REFERENCESWITCHING_MOVE=Boolean.valueOf(Common.getAttributeValue("allowReferenceSwitching", currentNode));
		currentNode = doc.getElementsByTagName("Changes").item(0);
		c.CREATE_CHANGES=Boolean.valueOf(Common.getAttributeValue("allow", currentNode));
		c.LITERALSWITCHING_CHANGE=Boolean.valueOf(Common.getAttributeValue("allowLiteralSwitching", currentNode));	
		currentNode = doc.getElementsByTagName("Adds").item(0);
		c.CREATE_ADDS=Boolean.valueOf(Common.getAttributeValue("allow", currentNode));
		currentNode = doc.getElementsByTagName("Removes").item(0);
		c.CREATE_REMOVES=Boolean.valueOf(Common.getAttributeValue("allow", currentNode));
		currentNode = doc.getElementsByTagName("Sets").item(0);
		c.CREATE_SET_ATTRIBUTES=Boolean.valueOf(Common.getAttributeValue("allow", currentNode));
		currentNode = doc.getElementsByTagName("Unsets").item(0);
		c.CREATE_UNSET_ATTRIBUTES=Boolean.valueOf(Common.getAttributeValue("allow", currentNode));
		
		
		// read ProfiledModel Settings if available
		if(c.PROFILEAPPLICATIONINUSE) {
			currentNode = doc.getElementsByTagName("BaseModelRules").item(0);
			c.BASEMODELRULEFOLDERPATH=String.valueOf(Common.getAttributeValue("path", currentNode));
		}
		
		/**** Read BlackList & WhiteList Elements as Strings ***************************************************/
		
		// read blacklisted names of EClasses
		currentNode = docElem.getElementsByTagName("BlackList").item(0);
		if (currentNode != null){
			// indeed, we have blacklisted EClasses
			currentChildNodes = currentNode.getChildNodes();
			for(int i=0; i<currentChildNodes.getLength(); i++) {
				if(currentChildNodes.item(i).getNodeName().equals("EClassifier")) {
					stringBlackList.add(Common.getAttributeValue("name", currentChildNodes.item(i)));
				}
			}
		}
		
		// read whiteList names of EClasses
		currentNode = docElem.getElementsByTagName("WhiteList").item(0);
		currentChildNodes = currentNode.getChildNodes();
		for(int i=0; i<currentChildNodes.getLength(); i++) {
			if(currentChildNodes.item(i).getNodeName().equals("EClassifier")) {
				stringWhiteList.add(Common.getAttributeValue("name", currentChildNodes.item(i)));
			}
		}
		
		/**** Resolve ePackages ********************************************************************************/
		
		// retrieve and set meta-model	
		currentNode = doc.getElementsByTagName("MainModel").item(0);
		String nsUri = String.valueOf(Common.getAttributeValue("nsUri", currentNode));
		EPackage metaModel = EPackage.Registry.INSTANCE.getEPackage(nsUri);
		c.METAMODEL=metaModel;
		calculatedEPackagesStack.add(metaModel);
		try {
			calculatedEPackagesStack.addAll(Common.getAllSubEPackages(metaModel));
		} catch (EPackageNotFoundException e) {
			e.printStackTrace();
		}
		
		
		// retrieve all other required meta models
		NodeList requiredModelNodes = doc.getElementsByTagName("RequiredModel");
		for(int i=0; i<=requiredModelNodes.getLength()-1; i++) {
			Node requiredNode = requiredModelNodes.item(i);
			String uri = String.valueOf(Common.getAttributeValue("nsUri", requiredNode));
			EPackage reqModel = EPackage.Registry.INSTANCE.getEPackage(uri);
			
			if(reqModel==null) {
				throw new EPackageNotFoundException(uri);
			}
			
			if(!calculatedEPackagesStack.contains(reqModel)) {
				calculatedEPackagesStack.add(reqModel);
			}
		}
		
		/**** Read Root ********************************************************************************************/
		
		// retrieve and root and nested attribute	
		currentNode = docElem.getElementsByTagName("Root").item(0);
		rootName = String.valueOf(Common.getAttributeValue("name", currentNode));
		if(!rootName.equals("")) {
			//resolve root
			EClassifier root = Common.resolveStringAsEClassifier(rootName, calculatedEPackagesStack);
			c.ROOT=root;
		}
		c.ROOTECLASSCANBENESTED=Boolean.valueOf(Common.getAttributeValue("nested", currentNode));
		
		/**** Meta-Model Analysis / Post-Processing Phase **********************************************************/
	
		LogUtil.log(LogEvent.NOTICE, "Analyzing Meta-Model...");
		
		// retrieve all other required EPackages
		gatherAllRequiredEPackages(metaModel, workspace_loc);
	
		// forward all necessary EPackage to Generator
		c.EPACKAGESSTACK=calculatedEPackagesStack;
		
		
		/**** Resolve BlackList & WhiteList Strings as EClasses ***************************************************/	
		
		// unfold lists (convert Strings to EClasses and find additional requirements)
		unfoldBlackList();
		unfoldWhiteList();
		
		/**** Handling of Masked Classifiers **********************************************************************/
				
		// retrieve masks
		currentNode = docElem.getElementsByTagName("MaskedClassifiers").item(0);
		if (currentNode != null){
			// indeed, we have masked classifiers
			currentChildNodes = currentNode.getChildNodes();
			for(int i=0; i<currentChildNodes.getLength(); i++) {
				if(currentChildNodes.item(i).getNodeName().equals("Mask")) {
					Node maskNode = currentChildNodes.item(i);
					String maskName = Common.getAttributeValue("name", maskNode);
					String eClassName = Common.getAttributeValue("eClassifier", maskNode);
					String eAttributeName = Common.getAttributeValue("eAttribute", maskNode);
					String eAttributeValue = Common.getAttributeValue("eAttributeValue", maskNode);
				
					EClassifier maskContainer = Common.resolveStringAsEClassifier(eClassName, calculatedEPackagesStack);
					EAttribute eAttribute = (EAttribute) ((EClass) maskContainer).getEStructuralFeature(eAttributeName);
					EClassifier valueContainer = eAttribute.getEType();
					EEnumLiteral valueLiteral = null;
					
					if(valueContainer instanceof EEnum) {					
						valueLiteral = ((EEnum) valueContainer).getEEnumLiteral(eAttributeValue);
					}else{
						throw new Exception("Masked Classifier contains type information that is not represented by EEnum(Literals)");
					}
					// add mask to EClassInfo of maskContainer
					Mask mask = new Mask(maskName, maskContainer, eAttribute, valueLiteral);
					ECM.getEClassifierInfo(maskContainer).addMask(mask);
					
				}
			}
		}
		
		/**** Constraints *****************************************************************************************/
		
		// retrieve Constraints & forward them to eClassInfoManagement
		NodeList c_nameUniqueness = doc.getElementsByTagName("NameUniqueness");
		for(int i=0; i<=c_nameUniqueness.getLength()-1; i++) {
			Node c = c_nameUniqueness.item(i);
			String scope = String.valueOf(Common.getAttributeValue("scope", c));
			String eClass = String.valueOf(Common.getAttributeValue("eClassifier", c));
			String eAttributeName = String.valueOf(Common.getAttributeValue("eAttributeName", c));
			Boolean eAttributeIsInherited = Boolean.valueOf(Common.getAttributeValue("eAttributeIsInherited", c));
			Boolean overrideInheritedConstraints = Boolean.valueOf(Common.getAttributeValue("overrideInheritedConstraintsIfAny", c));
			Boolean applyOnSubTypes = Boolean.valueOf(Common.getAttributeValue("applyOnSubTypes", c));
			EClass constrainedEClass = null;
			EAttribute constrainedEAttribute = null;
			
			if(!eClass.equals("")) {
				//resolve eClass
				for(EPackage ePackage: calculatedEPackagesStack) {
					constrainedEClass = (EClass) ePackage.getEClassifier(eClass);
					if(constrainedEClass!=null) {
						//if it's an inherited EAttribute
						if(eAttributeIsInherited) {
							//check all super types for the EAttribute in question
							for(EClass superType: constrainedEClass.getEAllSuperTypes()) {
								for(EAttribute ea: superType.getEAttributes()) {
									if(ea.getName().equals(eAttributeName)) {
										constrainedEAttribute = ea;
										break;
									}
								}
								if(constrainedEAttribute != null) {
									break;
								}
							}							
						}
						//else it's an EAttribute residing under the constrained class
						else{						
							for(EAttribute ea: constrainedEClass.getEAttributes()) {
								if(ea.getName().equals(eAttributeName)) {
									constrainedEAttribute = ea;
									break;
								}
							}
						}
						if(constrainedEAttribute==null) {
							throw new EAttributeNotFoundException(eAttributeName, constrainedEClass);
						}
						break;
					}
				}
				if(constrainedEClass==null) {
					throw new EClassifierUnresolvableException(eClass);
				}
			}
			List<Object> flags = new ArrayList<Object>();
			flags.add(applyOnSubTypes);
			flags.add(constrainedEAttribute);
			flags.add(overrideInheritedConstraints);
			flags.add(eAttributeIsInherited);
			if(scope.equals("global")) {
				ECM.addConstraint(ConstraintType.NAME_UNIQUENESS_GLOBAL, constrainedEClass, flags);
			}else {
				ECM.addConstraint(ConstraintType.NAME_UNIQUENESS_LOCAL, constrainedEClass, flags);
			}
		}
	}
	
	/*** TESTING Meta Model Slicer ******************************************************************************/
//	generator.sliceMetaModel();
	
	private static void gatherAllRequiredEPackages(EPackage metaModel, String workspace_loc) {

		// add all sub EPackages to epackages
		List<EPackage> subPackages = new ArrayList<EPackage>();
		for(EPackage ep: calculatedEPackagesStack) {
			for(EPackage sub: ep.getESubpackages()) {
				if(!subPackages.contains(sub) && !calculatedEPackagesStack.contains(sub)) {
					subPackages.add(sub);
				}
			}
		}
		calculatedEPackagesStack.addAll(subPackages);
		
		// try to find unresolved proxies and try to resolve them
		Map<EObject, Collection<Setting>> map = EcoreUtil.UnresolvedProxyCrossReferencer.find(metaModel);
		for(Entry<EObject, Collection<Setting>> entry : map.entrySet()) {
			for(Setting setting: entry.getValue()) {
				EClassifier targetedClassifier = setting.getEStructuralFeature().getEType();
				if(targetedClassifier.eIsProxy()) {


					// resolve eProxyURI of referenced meta model
					assert(workspace_loc!=null): "Add workspace_log variable into your run configuration at the bottom";

					String completePath = Common.convertEProxyURIToFilePath(targetedClassifier.toString(), workspace_loc);

					File ecoreFile = new File(completePath);

					assert(ecoreFile!=null): "The Metamodel uses other ecore files that are not inside your workspace or enabled in the run configuration";

					// load the referenced ecore file into the EPackage Registry.
					String modelKey = ModelStorage.getInstance().loadEMF(ecoreFile.getPath());					
					Resource referencedEcoreRes = ModelStorage.getInstance().getModel(modelKey);	

					EObject eObject = referencedEcoreRes.getContents().get(0);
					if (eObject instanceof EPackage) {
						EPackage p = (EPackage)eObject;
						if(!EPackage.Registry.INSTANCE.containsKey(p.getNsURI())) {
							EPackage.Registry.INSTANCE.put(p.getNsURI(), p);
							if(!calculatedEPackagesStack.contains(p)) {
								calculatedEPackagesStack.add(p);
							}
						}
					}
				}
				else{
					// meta model resolvable. Add relevant packages for visitor pattern
					if(!calculatedEPackagesStack.contains(targetedClassifier.getEPackage())) {
						calculatedEPackagesStack.add(targetedClassifier.getEPackage());
					}
				}


			}
		}
	}

	
	/**
	 * This fills the actual blackList with real EClassifiers.
	 * If preventInconsistencyThroughSkipping is set to TRUE, then
	 * it will additionally be checked if other model elements have mandatory
	 * dependencies to the skippable elements. If so, they will be skipped, too.
	 * Recursively.
	 * @throws EClassifierUnresolvableException 
	 */
	private static void unfoldBlackList() throws EClassifierUnresolvableException {
		filter.setBlackList(new ArrayList<EClassifier>());
		
		for(String eClassName: stringBlackList) {
			EClassifier skip = Common.resolveStringAsEClassifier(eClassName, calculatedEPackagesStack);
			filter.getBlackList().add(skip);
		}
		
		if(c.PREVENTINCONSISTENCYTHROUGHSKIPPING) {
			filter.setBlackList(findMoreSkips(filter.getBlackList()));
		}	
	}
	
	
	/**
	 * This fills the actual whiteList with real EClassifiers.
	 * It will additionally be checked recursively if EClassifiers in the WhiteList require
	 * further EClassesifiers to prevent model inconsistency.
	 * @throws EClassifierUnresolvableException 
	 */
	private static void unfoldWhiteList() throws EClassifierUnresolvableException {
		filter.setWhiteList(new ArrayList<EClassifier>());
		
		for(String eClassName: stringWhiteList) {
			EClassifier eClassifier = Common.resolveStringAsEClassifier(eClassName, calculatedEPackagesStack);
			filter.getWhiteList().add(eClassifier);
		}		
		findMoreRequiredClassifier(filter.getWhiteList());
	}
	
	
	/**
	 * This method extends the whiteList by classifiers (meta classes) that are extended by stereotypes
	 * when using the the profile mechanismn. Meta classes are necessary because profile model instances
	 * contain not only objects for stereotypes but also for their meta classes.<br/><br/>
	 * This method also fills the implicitRequirementList with EClasses.
	 * An EClass is implicitly required if it is supertype for other EClasses on the
	 * whitelist (or EClasses not on the blacklist) because it contains EAttributes and
	 * ERferences which are inherited and relevant for the sub types.
	 * @param oldList
	 */
	private static void findMoreRequiredClassifier(List<EClassifier> oldList) {
		
		ArrayList<EClassifier> currentList = new ArrayList<EClassifier>(oldList);
		
		// find implicit requirement by stereotyping / meta class extension
		if(c.PROFILEAPPLICATIONINUSE) {

			for(EClassifier req: oldList) {
				for(EClassifier metaClass:ECM.getEClassifierInfo(req).getExtendedMetaClasses()){
					if(!filter.getImplicitRequirements(ElementFilter.ImplicitRequirementType.EXTENDED_METACLASSES).contains(metaClass)) {
						filter.getImplicitRequirements(ElementFilter.ImplicitRequirementType.EXTENDED_METACLASSES).add(metaClass);
					}
				}

			}
		}
		filter.setWhiteList(currentList);

		// find implicit requirements of supertypes
		for(EClassifier req: currentList) {
			
			if(req instanceof EClass) {
				EClass reqEClass = (EClass) req;
				
				for(EAttribute ea: reqEClass.getEAllAttributes()) {
					
					//if EAttribute is derived from SuperType
					if(!ea.eContainer().equals(req)) {					
						EClass superType = (EClass) ea.eContainer();
						//if supertype is not explicitly set on blacklist or already on whitelist
						if(!filter.getBlackList().contains(superType) & !filter.getWhiteList().contains(superType)) {
							//add superType to implicit requirement list.
							if(!filter.getImplicitRequirements(ElementFilter.ImplicitRequirementType.INHERITING_SUPERTYPES).contains(superType)) {
								filter.getImplicitRequirements(ElementFilter.ImplicitRequirementType.INHERITING_SUPERTYPES).add(superType);
							}
						}
					}				
				}	
			}
		}
	}



	private static ArrayList<EClassifier> findMoreSkips(List<EClassifier> oldList) {
		
		ArrayList<EClassifier> extendedSkipList = new ArrayList<EClassifier>();
		extendedSkipList.addAll(oldList);
		
		boolean newEntries = false;
		
		for(EClassifier skip : oldList) {
			EClassifierInfo skipInfo = ECM.getEClassifierInfo(skip);
			for(List<EClassifier> mpcList :skipInfo.getMandatoryParentContext().values()) {
				for(EClassifier mpc: mpcList) {
					// only add skip if not already in list and if its not required on white or rootlist
					if(!oldList.contains(mpc) && !stringWhiteList.contains(mpc.getName()) && !mpc.equals(rootName)) {
						extendedSkipList.add(mpc);
						newEntries = true;
					}
				}
			}
			for(List<EClassifier> mpcList :skipInfo.getMandatoryNeighbourContext().values()) {
				for(EClassifier mnc: mpcList) {
					// only add skip if not already in list and if its not required on white or rootlist
					if(!oldList.contains(mnc) && !stringWhiteList.contains(mnc.getName()) && !mnc.equals(rootName)) {
						extendedSkipList.add(mnc);
						newEntries = true;
					}
				}
			}
		}
		
		if(newEntries) {
			extendedSkipList = findMoreSkips(extendedSkipList);
		}
		return extendedSkipList;
		
	}
	
	
}
