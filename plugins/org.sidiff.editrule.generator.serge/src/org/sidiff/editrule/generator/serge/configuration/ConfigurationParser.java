package org.sidiff.editrule.generator.serge.configuration;

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
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EcoreFactoryImpl;
import org.eclipse.emf.ecore.impl.EcorePackageImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreAdapterFactory;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sidiff.common.emf.EMFUtil;
import org.sidiff.common.emf.exceptions.EAttributeNotFoundException;
import org.sidiff.common.emf.exceptions.EClassifierUnresolvableException;
import org.sidiff.common.emf.exceptions.EPackageNotFoundException;
import org.sidiff.common.emf.extensions.impl.EClassifierInfo;
import org.sidiff.common.emf.extensions.impl.EClassifierInfo.ConstraintType;
import org.sidiff.common.emf.extensions.impl.EClassifierInfoManagement;
import org.sidiff.common.emf.extensions.impl.Mask;
import org.sidiff.common.emf.modelstorage.ModelStorage;
import org.sidiff.common.io.IOUtil;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.common.xml.XMLParser;
import org.sidiff.editrule.generator.serge.filter.ElementFilter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ConfigurationParser {
	
	private static Configuration c 							= Configuration.getInstance();
	private static String metaModelForDefaultConfig			= null;
	private static EClassifierInfoManagement ECM 			= EClassifierInfoManagement.getInstance();
	private static ElementFilter filter 					= ElementFilter.getInstance();
	
	private static Stack<EPackage> calculatedEPackagesStack	= new Stack<EPackage>();
	private static List<String> stringWhiteList 			= new ArrayList<String>();
	private static List<String> stringBlackList 			= new ArrayList<String>();
	private static String rootName							= null;
	private static Document doc								= null;

	public void setupDefaultConfig(String metaModelNsURI, String pathToDefaultConfigTemplate) throws Exception {
		
		this.metaModelForDefaultConfig = metaModelNsURI;
		parse(pathToDefaultConfigTemplate);		
		
	}

	public void parse (String pathToConfig) throws Exception {
				
		Document doc = XMLParser.parseStream(IOUtil.getInputStream(pathToConfig));	
		Element docElem = doc.getDocumentElement();
		org.w3c.dom.Node currentNode = null;
		NodeList currentChildNodes = null;
		
		// retrieve and set general settings	
		currentNode = doc.getElementsByTagName("preventInconsistency").item(0);
		c.PREVENT_INCONSISTENCY_THROUGHSKIPPING =Boolean.valueOf(getAttributeValue("value", currentNode));
		currentNode = doc.getElementsByTagName("multiplicityPreconditions").item(0);		
		c.MULTIPLICITY_PRECONDITIONS_INTEGRATED=Boolean.valueOf(getAttributeValue("integrated", currentNode));
		c.MULTIPLICITY_PRECONDITIONS_SEPARATELY=Boolean.valueOf(getAttributeValue("separately", currentNode));
		currentNode = doc.getElementsByTagName("disableVariantsWithSupertypeReplacement").item(0);		
		c.DISABLE_VARIANTS_BY_SUPERTYPE_REPLACEMENT=Boolean.valueOf(getAttributeValue("value", currentNode));
		currentNode = doc.getElementsByTagName("createAllAttributes").item(0);		
		c.CREATE_NOT_REQUIRED_AND_NOT_ID_ATTRIBUTES=Boolean.valueOf(getAttributeValue("value", currentNode));
		currentNode = doc.getElementsByTagName("modelUsesProfileMechanism").item(0);		
		c.PROFILE_APPLICATION_IN_USE=Boolean.valueOf(getAttributeValue("value", currentNode));
		currentNode = doc.getElementsByTagName("outputFolder").item(0);	
		
		// reduce to supertype settings
		currentNode = doc.getElementsByTagName("reduceToSuperType").item(0);		
		c.REDUCETOSUPERTYPE_ADDREMOVE=Boolean.valueOf(getAttributeValue("ADD_REMOVE", currentNode));
		c.REDUCETOSUPERTYPE_CHANGE_LITERALS=Boolean.valueOf(getAttributeValue("CHANGE_LITERAL", currentNode));
		c.REDUCETOSUPERTYPE_CHANGE_REFERENCE=Boolean.valueOf(getAttributeValue("CHANGE_REFERENCE", currentNode));
		c.REDUCETOSUPERTYPE_CREATEDELETE=Boolean.valueOf(getAttributeValue("CREATE_DELETE", currentNode));
		c.REDUCETOSUPERTYPE_MOVE=Boolean.valueOf(getAttributeValue("MOVE", currentNode));
		c.REDUCETOSUPERTYPE_MOVE_DOWN=Boolean.valueOf(getAttributeValue("MOVE_DOWN", currentNode));
		c.REDUCETOSUPERTYPE_MOVE_UP=Boolean.valueOf(getAttributeValue("MOVE_UP", currentNode));
		c.REDUCETOSUPERTYPE_SETUNSET_ATTRIBUTES=Boolean.valueOf(getAttributeValue("SET_UNSET_ATTRIBUTE", currentNode));
		c.REDUCETOSUPERTYPE_SETUNSET_REFERENCES=Boolean.valueOf(getAttributeValue("SET_UNSET_REFERENCE", currentNode));
		
	
		// enable/disable transformation types and its settings
		currentNode = doc.getElementsByTagName("Creates").item(0);
		c.CREATE_CREATES=Boolean.valueOf(getAttributeValue("allow", currentNode));
		
		currentNode = doc.getElementsByTagName("Deletes").item(0);
		c.CREATE_DELETES=Boolean.valueOf(getAttributeValue("allow", currentNode));
		
		currentNode = doc.getElementsByTagName("Moves").item(0);
		c.CREATE_MOVES=Boolean.valueOf(getAttributeValue("allow", currentNode));
		c.REFERENCESWITCHING_MOVE=Boolean.valueOf(getAttributeValue("allowReferenceSwitching", currentNode));
		c.CREATE_MOVE_REFERENCE_COMBINATIONS = Boolean.valueOf(getAttributeValue("allowReferenceCombinations", currentNode));
		
		currentNode = doc.getElementsByTagName("MoveUps").item(0);
		c.CREATE_MOVE_UPS=Boolean.valueOf(getAttributeValue("allow", currentNode));
		c.REFERENCESWITCHING_MOVE=Boolean.valueOf(getAttributeValue("allowReferenceSwitching", currentNode));

		currentNode = doc.getElementsByTagName("MoveDowns").item(0);
		c.CREATE_MOVE_DOWNS=Boolean.valueOf(getAttributeValue("allow", currentNode));
		c.REFERENCESWITCHING_MOVE=Boolean.valueOf(getAttributeValue("allowReferenceSwitching", currentNode));
		
		currentNode = doc.getElementsByTagName("ChangeLiterals").item(0);
		c.CREATE_CHANGE_LITERALS=Boolean.valueOf(getAttributeValue("allow", currentNode));
		c.LITERALSWITCHING_CHANGE=Boolean.valueOf(getAttributeValue("allowLiteralSwitching", currentNode));
		
		currentNode = doc.getElementsByTagName("ChangeReferences").item(0);
		c.CREATE_CHANGE_REFERENCES=Boolean.valueOf(getAttributeValue("allow", currentNode));
		
		currentNode = doc.getElementsByTagName("Adds").item(0);
		c.CREATE_ADDS=Boolean.valueOf(getAttributeValue("allow", currentNode));
		
		currentNode = doc.getElementsByTagName("Removes").item(0);
		c.CREATE_REMOVES=Boolean.valueOf(getAttributeValue("allow", currentNode));
		
		currentNode = doc.getElementsByTagName("SetAttributes").item(0);
		c.CREATE_SET_ATTRIBUTES=Boolean.valueOf(getAttributeValue("allow", currentNode));
		
		currentNode = doc.getElementsByTagName("SetReferences").item(0);
		c.CREATE_SET_REFERENCES=Boolean.valueOf(getAttributeValue("allow", currentNode));
		
		currentNode = doc.getElementsByTagName("UnsetAttributes").item(0);
		c.CREATE_UNSET_ATTRIBUTES=Boolean.valueOf(getAttributeValue("allow", currentNode));
		
		currentNode = doc.getElementsByTagName("UnsetReferences").item(0);
		c.CREATE_UNSET_REFERENCES=Boolean.valueOf(getAttributeValue("allow", currentNode));
		
		
		/**** Read BlackList & WhiteList Elements as Strings ***************************************************/
		
		// read blacklisted names of EClasses
		currentNode = docElem.getElementsByTagName("BlackList").item(0);
		if (currentNode != null){
			// indeed, we have blacklisted EClasses
			currentChildNodes = currentNode.getChildNodes();
			for(int i=0; i<currentChildNodes.getLength(); i++) {
				if(currentChildNodes.item(i).getNodeName().equals("EClassifier")) {
					stringBlackList.add(getAttributeValue("name", currentChildNodes.item(i)));
				}
			}
		}
		
		// read whiteList names of EClasses
		currentNode = docElem.getElementsByTagName("WhiteList").item(0);
		currentChildNodes = currentNode.getChildNodes();
		for(int i=0; i<currentChildNodes.getLength(); i++) {
			if(currentChildNodes.item(i).getNodeName().equals("EClassifier")) {
				stringWhiteList.add(getAttributeValue("name", currentChildNodes.item(i)));
			}
		}
		
		/**** Resolve ePackages ********************************************************************************/
			
		// Case refined config.
		String nsUri = "";	
		if(metaModelForDefaultConfig==null) {
			currentNode = doc.getElementsByTagName("MainModel").item(0);
			nsUri = String.valueOf(getAttributeValue("nsUri", currentNode));
		}
		// Case default config.
		else{
			nsUri = metaModelForDefaultConfig;
		}

		EPackage metaModel = EPackage.Registry.INSTANCE.getEPackage(nsUri);
		c.METAMODEL=metaModel;
		calculatedEPackagesStack.add(metaModel);
		try {
			// add contained sub packages
			calculatedEPackagesStack.addAll(EMFUtil.getAllSubEPackages(metaModel));
		} catch (EPackageNotFoundException e) {
			e.printStackTrace();
		}


		// retrieve all other required meta models
		NodeList requiredModelNodes = doc.getElementsByTagName("RequiredModel");
		for(int i=0; i<=requiredModelNodes.getLength()-1; i++) {
			Node requiredNode = requiredModelNodes.item(i);
			String uri = String.valueOf(getAttributeValue("nsUri", requiredNode));
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
		rootName = String.valueOf(getAttributeValue("name", currentNode));
		if(!rootName.equals("")) {
			//resolve root
			EClassifier root = resolveStringAsEClassifier(rootName, calculatedEPackagesStack);
			c.ROOT=root;
		}
		c.ROOTECLASSCANBENESTED=Boolean.valueOf(getAttributeValue("nested", currentNode));
		
		/**** Meta-Model Analysis / Post-Processing Phase **********************************************************/
	
		LogUtil.log(LogEvent.NOTICE, "Analyzing Meta-Model...");
		
		// retrieve all other required EPackages
		gatherAllRequiredEPackages(c.METAMODEL);
	
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
					String maskName = getAttributeValue("name", maskNode);
					String eClassName = getAttributeValue("eClassifier", maskNode);
					String eAttributeName = getAttributeValue("eAttribute", maskNode);
					String eAttributeValue = getAttributeValue("eAttributeValue", maskNode);
				
					EClassifier maskContainer = resolveStringAsEClassifier(eClassName, calculatedEPackagesStack);
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
			String scope = String.valueOf(getAttributeValue("scope", c));
			String eClass = String.valueOf(getAttributeValue("eClassifier", c));
			String eAttributeName = String.valueOf(getAttributeValue("eAttributeName", c));
			Boolean eAttributeIsInherited = Boolean.valueOf(getAttributeValue("eAttributeIsInherited", c));
			Boolean overrideInheritedConstraints = Boolean.valueOf(getAttributeValue("overrideInheritedConstraintsIfAny", c));
			Boolean applyOnSubTypes = Boolean.valueOf(getAttributeValue("applyOnSubTypes", c));
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
	
	private static void gatherAllRequiredEPackages(EPackage metaModel) {

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
					String absolutPathOfMetaModel = metaModel.eResource().getURI().toString();
					String completePath = convertEProxyURIToFilePath(targetedClassifier.toString(), absolutPathOfMetaModel);

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
			EClassifier skip = resolveStringAsEClassifier(eClassName, calculatedEPackagesStack);
			filter.getBlackList().add(skip);
		}
		
		if(c.PREVENT_INCONSISTENCY_THROUGHSKIPPING) {
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
			EClassifier eClassifier = resolveStringAsEClassifier(eClassName, calculatedEPackagesStack);
			filter.getWhiteList().add(eClassifier);
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
	
	private String getAttributeValue(String attribName, org.w3c.dom.Node node) {

		NamedNodeMap attribs = node.getAttributes();
		for (int i = 0; i < attribs.getLength(); i++) {
			if (attribs.item(i).getNodeName().equals(attribName)) {
				return attribs.item(i).getNodeValue();
			}
		}

		return null;
	}
	
	private static EClassifier resolveStringAsEClassifier(String eClassifierName, Stack<EPackage> ePackagesStack)
			throws EClassifierUnresolvableException {
		EClassifier resolvedEClassifier = null;

		for (EPackage ePackage : ePackagesStack) {
			resolvedEClassifier = ePackage.getEClassifier(eClassifierName);
			if (resolvedEClassifier != null) {
				return resolvedEClassifier;
			}
		}
		throw new EClassifierUnresolvableException(eClassifierName);
	}
	
	/**
	 * This method converts an unresolved EObject / EProxyURI into a correct
	 * file path. e.g.
	 * "org.something.de@1234 (eProxyURI: platform:/plugin/org.something.de/model/my.ecore#//someclassifier"
	 * into "C:\myworkspace\org.something.de\model\my.ecore". The workspace
	 * location needs to be given in as an argument (retrievable via run
	 * configuration variable).
	 * 
	 * @param eProxyURIString
	 * @param workspace_loc
	 * @return
	 */
	private static String convertEProxyURIToFilePath(String eProxyURIString, String containingMetaModelPath) {

		//TODO replace workspace_loc (in EProxyURIToFilePath converter)
		// find out absolut path from containingMetaModelPath...
		String formerWorkspacke_loc = "";
		
		// convert eProxyURI into correct plugin-path inside workspace
		String proxyUri = eProxyURIString;
		proxyUri = proxyUri.replaceFirst("[\\w\\.@\\s\\(:]*[plugin/]+/", ""); // org...(eProxyURI:
																				// platform:/plugin/)
		proxyUri = proxyUri.replaceFirst("#[\\/]{2}[\\w\\d]*[\\)]$", ""); // #//classifier)
		String FILE_SEPERATOR = System.getProperty("file.separator");
		if (!FILE_SEPERATOR.equals("/")) {
			proxyUri = proxyUri.replace("/", "\\");
		}

		return formerWorkspacke_loc + FILE_SEPERATOR + proxyUri;
	}
	
}
