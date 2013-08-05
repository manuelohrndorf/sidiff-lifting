package org.sidiff.serge.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

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
import org.sidiff.common.emf.ecore.ECoreTraversal;
import org.sidiff.common.emf.modelstorage.ModelStorage;
import org.sidiff.common.io.IOUtil;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.common.xml.XMLParser;
import org.sidiff.serge.SergeService;
import org.sidiff.serge.exceptions.EAttributeNotFoundException;
import org.sidiff.serge.exceptions.EClassUnresolvableException;
import org.sidiff.serge.exceptions.EPackageNotFoundException;
import org.sidiff.serge.services.AbstractGenerator;
import org.sidiff.serge.services.AbstractGenerator.ConstraintType;
import org.sidiff.serge.services.AbstractGenerator.ImplicitRequirementType;
import org.sidiff.serge.services.HenshinTransformationGenerator;
import org.sidiff.serge.util.Common;
import org.sidiff.serge.util.EClassInfo;
import org.sidiff.serge.util.EClassInfoManagement;
import org.sidiff.serge.util.Mask;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SergeServiceImpl implements SergeService{

	private static AbstractGenerator generator  				= null;
	private static Stack<EPackage> ePackagesStack					= new Stack<EPackage>();
	private static EClassInfoManagement eClassInfoManagement 	= null;

	private static Boolean enableStereotypeMapping				= false;
	private static List<String> stringWhiteList 				= new ArrayList<String>();
	private static List<String> stringBlackList 				= new ArrayList<String>();
	private static String rootName								= null;
	
	public SergeServiceImpl() {
		
	}
	
	@Override
	public void init(Class<?> service, String pathToConfig, String workspace_loc, String pathToOutputFolder) throws Exception {
				
		generator = new HenshinTransformationGenerator();
		generator.setOutputFolderPath(pathToOutputFolder);
		
		/**************************************************************************************************************/		
		LogUtil.log(LogEvent.NOTICE, "Interpreting Configuration File...");
		
		Document doc = XMLParser.parseStream(IOUtil.getInputStream(pathToConfig));
		
		Element docElem = doc.getDocumentElement();
		org.w3c.dom.Node currentNode = null;
		NodeList currentChildNodes = null;
		
		// retrieve and set general settings	
		currentNode = doc.getElementsByTagName("preventInconsistency").item(0);
		generator.setPreventInconsistencyThroughSkipping(Boolean.valueOf(Common.getAttributeValue("value", currentNode)));
		currentNode = doc.getElementsByTagName("multiplicityPreconditions").item(0);		
		generator.setMultiplicityPreconditions(Boolean.valueOf(Common.getAttributeValue("integrated", currentNode)), Boolean.valueOf(Common.getAttributeValue("separately", currentNode)));
		currentNode = doc.getElementsByTagName("disableVariants").item(0);		
		generator.setDisableVariants(Boolean.valueOf(Common.getAttributeValue("value", currentNode)));
		currentNode = doc.getElementsByTagName("modelUsesProfileMechanism").item(0);		
		enableStereotypeMapping = Boolean.valueOf(Common.getAttributeValue("value", currentNode));
		generator.setProfileApplicationInUse(enableStereotypeMapping);
		currentNode = doc.getElementsByTagName("reduceToSuperType").item(0);
		generator.setReduceToSuperType_SETUNSET(Boolean.valueOf(Common.getAttributeValue("SET_UNSET", currentNode)));
		generator.setReduceToSuperType_ADDREMOVE(Boolean.valueOf(Common.getAttributeValue("ADD_REMOVE", currentNode)));
		generator.setReduceToSuperType_CHANGE(Boolean.valueOf(Common.getAttributeValue("CHANGE", currentNode)));

		// retrieve and set operation types
		currentNode = doc.getElementsByTagName("Creates").item(0);
		generator.setCreateCREATES(Boolean.valueOf(Common.getAttributeValue("allow", currentNode)));
		currentNode = doc.getElementsByTagName("Deletes").item(0);
		generator.setCreateDELETES(Boolean.valueOf(Common.getAttributeValue("allow", currentNode)));
		currentNode = doc.getElementsByTagName("Moves").item(0);
		generator.setCreateMOVES(Boolean.valueOf(Common.getAttributeValue("allow", currentNode)));
		generator.setReferenceSwitching_MOVE(Boolean.valueOf(Common.getAttributeValue("allowReferenceSwitching", currentNode)));
		currentNode = doc.getElementsByTagName("Changes").item(0);
		generator.setCreateCHANGES(Boolean.valueOf(Common.getAttributeValue("allow", currentNode)));
		generator.setLiteralSwitching_CHANGE(Boolean.valueOf(Common.getAttributeValue("allowLiteralSwitching", currentNode)));	
		currentNode = doc.getElementsByTagName("Adds").item(0);
		generator.setCreateADDS(Boolean.valueOf(Common.getAttributeValue("allow", currentNode)));
		currentNode = doc.getElementsByTagName("Removes").item(0);
		generator.setCreateREMOVES(Boolean.valueOf(Common.getAttributeValue("allow", currentNode)));
		currentNode = doc.getElementsByTagName("Sets").item(0);
		generator.setCreateSETS(Boolean.valueOf(Common.getAttributeValue("allow", currentNode)));
		currentNode = doc.getElementsByTagName("Unsets").item(0);
		generator.setCreateUNSETS(Boolean.valueOf(Common.getAttributeValue("allow", currentNode)));
		
		
		// read ProfiledModel Settings if available
		if(enableStereotypeMapping) {
			currentNode = doc.getElementsByTagName("BaseModelRules").item(0);
			generator.setBaseModelRuleFolderPath(String.valueOf(Common.getAttributeValue("path", currentNode)));
		}
		
		/**** Read BlackList & WhiteList Elements as Strings ***************************************************/
		
		// read blacklisted names of EClasses
		currentNode = docElem.getElementsByTagName("BlackList").item(0);
		currentChildNodes = currentNode.getChildNodes();
		for(int i=0; i<currentChildNodes.getLength(); i++) {
			if(currentChildNodes.item(i).getNodeName().equals("EClass")) {
				stringBlackList.add(Common.getAttributeValue("name", currentChildNodes.item(i)));
			}
		}
		
		// read whiteList names of EClasses
		currentNode = docElem.getElementsByTagName("WhiteList").item(0);
		currentChildNodes = currentNode.getChildNodes();
		for(int i=0; i<currentChildNodes.getLength(); i++) {
			if(currentChildNodes.item(i).getNodeName().equals("EClass")) {
				stringWhiteList.add(Common.getAttributeValue("name", currentChildNodes.item(i)));
			}
		}
		
		/**** Resolve ePackages ********************************************************************************/
		
		// retrieve and set meta-model	
		currentNode = doc.getElementsByTagName("MainModel").item(0);
		String nsUri = String.valueOf(Common.getAttributeValue("nsUri", currentNode));
		EPackage metaModel = EPackage.Registry.INSTANCE.getEPackage(nsUri);
		generator.setMetaModel(metaModel);
		ePackagesStack.add(metaModel);
		try {
			ePackagesStack.addAll(Common.getAllSubEPackages(metaModel));
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
				throw new EPackageNotFoundException();
			}
			
			if(!ePackagesStack.contains(reqModel)) {
				ePackagesStack.add(reqModel);
			}
		}
		
		/**** Read Root ********************************************************************************************/
		
		// retrieve and root and nested attribute	
		currentNode = docElem.getElementsByTagName("Root").item(0);
		rootName = String.valueOf(Common.getAttributeValue("name", currentNode));
		if(!rootName.equals("")) {
			//resolve root
			EClass root = Common.resolveStringAsEClass(rootName, ePackagesStack);
			generator.setRoot(root);
		}
		generator.setRootEClassCanBeNested(Boolean.valueOf(Common.getAttributeValue("nested", currentNode)));
		
		/**** Meta-Model Analysis / Post-Processing Phase **********************************************************/

		LogUtil.log(LogEvent.NOTICE, "Analyzing Meta-Model...");
		
		// retrieve all other required EPackages
		gatherAllRequiredEPackages(metaModel, workspace_loc);

		// forward all necessary EPackage to Generator
		generator.setEPackages(ePackagesStack);
		
		// initalize EClassInfoManagement
		eClassInfoManagement = generator.initEClassInfoManagement(enableStereotypeMapping);
		
		
		/**** Resolve BlackList & WhiteList Strings as EClasses ***************************************************/	
		
		// unfold lists (convert Strings to EClasses and find additional requirements)
		unfoldBlackList();
		unfoldWhiteList();
		
		/**** Handling of Masked Classifiers **********************************************************************/
				
		// retrieve masks
		currentNode = docElem.getElementsByTagName("MaskedClassifiers").item(0);
		currentChildNodes = currentNode.getChildNodes();
		for(int i=0; i<currentChildNodes.getLength(); i++) {
			if(currentChildNodes.item(i).getNodeName().equals("Mask")) {
				Node maskNode = currentChildNodes.item(i);
				String maskName = Common.getAttributeValue("name", maskNode);
				String eClassName = Common.getAttributeValue("eClass", maskNode);
				String eAttributeName = Common.getAttributeValue("eAttribute", maskNode);
				String eAttributeValue = Common.getAttributeValue("eAttributeValue", maskNode);
			
				EClass maskContainer = Common.resolveStringAsEClass(eClassName, ePackagesStack);
				EAttribute eAttribute = (EAttribute) maskContainer.getEStructuralFeature(eAttributeName);
				EClassifier valueContainer = eAttribute.getEType();
				EEnumLiteral valueLiteral = null;
				
				if(valueContainer instanceof EEnum) {					
					valueLiteral = ((EEnum) valueContainer).getEEnumLiteral(eAttributeValue);
				}else{
					throw new Exception("Masked Classifier contains type information that is not represented by EEnum(Literals)");
				}
				// add mask to EClassInfo of maskContainer
				Mask mask = new Mask(maskName, maskContainer, eAttribute, valueLiteral);
				eClassInfoManagement.getEClassInfo(maskContainer).addMask(mask);
				
			}
		}
		
		
		
		/**** Constraints *****************************************************************************************/
		
		// retrieve Constraints & forward them to eClassInfoManagement
		NodeList c_nameUniqueness = doc.getElementsByTagName("NameUniqueness");
		for(int i=0; i<=c_nameUniqueness.getLength()-1; i++) {
			Node c = c_nameUniqueness.item(i);
			String scope = String.valueOf(Common.getAttributeValue("scope", c));
			String eClass = String.valueOf(Common.getAttributeValue("eClass", c));
			String eAttributeName = String.valueOf(Common.getAttributeValue("eAttributeName", c));
			Boolean eAttributeIsInherited = Boolean.valueOf(Common.getAttributeValue("eAttributeIsInherited", c));
			Boolean overrideInheritedConstraints = Boolean.valueOf(Common.getAttributeValue("overrideInheritedConstraintsIfAny", c));
			Boolean applyOnSubTypes = Boolean.valueOf(Common.getAttributeValue("applyOnSubTypes", c));
			EClass constrainedEClass = null;
			EAttribute constrainedEAttribute = null;
			
			if(!eClass.equals("")) {
				//resolve eClass
				for(EPackage ePackage: ePackagesStack) {
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
					throw new EClassUnresolvableException(eClass);
				}
			}
			List<Object> flags = new ArrayList<Object>();
			flags.add(applyOnSubTypes);
			flags.add(constrainedEAttribute);
			flags.add(overrideInheritedConstraints);
			flags.add(eAttributeIsInherited);
			if(scope.equals("global")) {
				eClassInfoManagement.addConstraint(ConstraintType.NAME_UNIQUENESS_GLOBAL, constrainedEClass, flags);
			}else {
				eClassInfoManagement.addConstraint(ConstraintType.NAME_UNIQUENESS_LOCAL, constrainedEClass, flags);
			}
		}
		

	}	
	
	
	@Override
	public void generate(Class<?> service) throws EPackageNotFoundException {

		assert(service!=null) : "Service not found";
				
		if(!ePackagesStack.isEmpty()){			
			ECoreTraversal.traverse(generator, ePackagesStack.toArray(new EPackage[ePackagesStack.size()]));				
		}else{
			throw new EPackageNotFoundException();
		}
		
	}
	
	private static void gatherAllRequiredEPackages(EPackage metaModel, String workspace_loc) {

		// add all sub EPackages to epackages
		List<EPackage> subPackages = new ArrayList<EPackage>();
		for(EPackage ep: ePackagesStack) {
			for(EPackage sub: ep.getESubpackages()) {
				if(!subPackages.contains(sub) && !ePackagesStack.contains(sub)) {
					subPackages.add(sub);
				}
			}
		}
		ePackagesStack.addAll(subPackages);
		
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
							if(!ePackagesStack.contains(p)) {
								ePackagesStack.add(p);
							}
						}
					}
				}
				else{
					// meta model resolvable. Add relevant packages for visitor pattern
					if(!ePackagesStack.contains(targetedClassifier.getEPackage())) {
						ePackagesStack.add(targetedClassifier.getEPackage());
					}
				}


			}
		}
	}

	
	/**
	 * This fills the actual blackList with real EClasses.
	 * If preventInconsistencyThroughSkipping is set to TRUE, then
	 * it will additionally be checked if other model elements have mandatory
	 * dependencies to the skippable elements. If so, they will be skipped, too.
	 * Recursively.
	 * @throws EClassUnresolvableException 
	 */
	private static void unfoldBlackList() throws EClassUnresolvableException {
		generator.setBlackList(new ArrayList<EClass>());
		
		for(String eClassName: stringBlackList) {
			EClass skip = Common.resolveStringAsEClass(eClassName, ePackagesStack);
			generator.getBlackList().add(skip);
		}
		
		if(generator.getPreventInconsistencyThroughSkipping()) {
			generator.setBlackList(findMoreSkips(generator.getBlackList()));
		}	
	}
	
	
	/**
	 * This fills the actual whiteList with real EClasses.
	 * It will additionally be checked recursively if EClasses in the WhiteList require
	 * further EClasses to prevent model inconsistency.
	 * @throws EClassUnresolvableException 
	 */
	private static void unfoldWhiteList() throws EClassUnresolvableException {
		generator.setWhiteList(new ArrayList<EClass>());
		
		for(String eClassName: stringWhiteList) {
			EClass eClass = Common.resolveStringAsEClass(eClassName, ePackagesStack);
			generator.getWhiteList().add(eClass);
		}		
		findMoreRequiredClassifier(generator.getWhiteList());
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
	private static void findMoreRequiredClassifier(List<EClass> oldList) {
		
		ArrayList<EClass> currentList = new ArrayList<EClass>(oldList);
		
		// find implicit requirement by stereotyping / meta class extension
		if(generator.getProfileApplicationInUse()) {

			for(EClass req: oldList) {
				for(EClass metaClass:eClassInfoManagement.getEClassInfo(req).getExtendedMetaClasses()){
					if(!generator.getImplicitRequirements(ImplicitRequirementType.EXTENDED_METACLASSES).contains(metaClass)) {
						generator.getImplicitRequirements(ImplicitRequirementType.EXTENDED_METACLASSES).add(metaClass);
					}
				}

			}
		}
		generator.setWhiteList(currentList);

		// find implicit requirements of supertypes
		for(EClass req: currentList) {

			for(EAttribute ea: req.getEAllAttributes()) {
				
				//if EAttribute is derived from SuperType
				if(!ea.eContainer().equals(req)) {					
					EClass superType = (EClass) ea.eContainer();
					//if supertype is not explicitly set on blacklist or already on whitelist
					if(!generator.getBlackList().contains(superType) & !generator.getWhiteList().contains(superType)) {
						//add superType to implicit requirement list.
						if(!generator.getImplicitRequirements(ImplicitRequirementType.INHERITING_SUPERTYPES).contains(superType)) {
							generator.getImplicitRequirements(ImplicitRequirementType.INHERITING_SUPERTYPES).add(superType);
						}
					}
				}				
			}			
		}
	}



	private static ArrayList<EClass> findMoreSkips(List<EClass> oldList) {
		
		ArrayList<EClass> extendedSkipList = new ArrayList<EClass>();
		extendedSkipList.addAll(oldList);
		
		boolean newEntries = false;
		
		for(EClass skip : oldList) {
			EClassInfo skipInfo = eClassInfoManagement.getEClassInfo(skip);
			for(List<EClass> mpcList :skipInfo.getMandatoryParentContext().values()) {
				for(EClass mpc: mpcList) {
					// only add skip if not already in list and if its not required on white or rootlist
					if(!oldList.contains(mpc) && !stringWhiteList.contains(mpc.getName()) && !mpc.equals(rootName)) {
						extendedSkipList.add(mpc);
						newEntries = true;
					}
				}
			}
			for(List<EClass> mpcList :skipInfo.getMandatoryNeighbourContext().values()) {
				for(EClass mnc: mpcList) {
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
