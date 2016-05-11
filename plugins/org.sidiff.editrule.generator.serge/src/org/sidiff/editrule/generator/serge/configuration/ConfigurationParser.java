package org.sidiff.editrule.generator.serge.configuration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;

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
import org.sidiff.common.emf.EMFUtil;
import org.sidiff.common.emf.exceptions.EAttributeNotFoundException;
import org.sidiff.common.emf.exceptions.EClassifierUnresolvableException;
import org.sidiff.common.emf.exceptions.EPackageNotFoundException;
import org.sidiff.common.emf.modelstorage.ModelStorage;
import org.sidiff.common.io.IOUtil;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.common.xml.XMLParser;
import org.sidiff.editrule.generator.serge.configuration.Configuration.OperationTypeGroup;
import org.sidiff.editrule.generator.serge.exceptions.NoEncapsulatedTypeInformationException;
import org.sidiff.editrule.generator.serge.exceptions.SERGeConfigParserException;
import org.sidiff.editrule.generator.serge.filter.ClassifierInclusionConfiguration;
import org.sidiff.editrule.generator.serge.filter.ClassifierInclusionConfiguration.InclusionType;
import org.sidiff.editrule.generator.serge.filter.ElementFilter;
import org.sidiff.editrule.generator.serge.metamodelanalysis.EClassifierInfoManagement;
import org.sidiff.editrule.generator.serge.metamodelanalysis.Mask;
import org.sidiff.editrule.generator.types.OperationType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ConfigurationParser {

	private static Configuration c = Configuration.getInstance();
	private static String metaModelForDefaultConfig = null;
	private static EClassifierInfoManagement ECM = EClassifierInfoManagement.getInstance();
	private static ElementFilter filter = ElementFilter.getInstance();
	private static ClassifierInclusionConfiguration CIC = ClassifierInclusionConfiguration.getInstance();

	public void setupDefaultConfig(String metaModelNsURI, String pathToDefaultConfigTemplate) throws Exception {
		ConfigurationParser.metaModelForDefaultConfig = metaModelNsURI;
		parse(pathToDefaultConfigTemplate);
	}

	public void parse(String pathToConfig) throws SERGeConfigParserException, EPackageNotFoundException,
			EClassifierUnresolvableException, NoEncapsulatedTypeInformationException, EAttributeNotFoundException,
			ParserConfigurationException, IOException {
		
		// create XML Document Object from file path
		Document doc = XMLParser.parseStream(IOUtil.getInputStream(pathToConfig));	
		Element docElem = doc.getDocumentElement();
		
		c.EPACKAGESSTACK = new Stack<EPackage>();
		org.w3c.dom.Node currentNode = doc.getElementsByTagName("Config").item(0);
		
		// start parsing
		parseMetaModel(getChildNodesByName(currentNode, "MetaModel").get(0));
		parseTransformations(getChildNodesByName(currentNode, "Transformations").get(0));
		parseConsistencyLevel(getChildNodesByName(currentNode, "ConsistencyLevel").get(0));
		
		// analyze meta model
		LogUtil.log(LogEvent.NOTICE, "Analyzing Meta-Model...");
		gatherAllRequiredEPackages(c.METAMODEL);
		
		// continue parsing / resolving meta model specific parts
		parseCompletenessLevel(getChildNodesByName(currentNode, "CompletnessLevel").get(0));
		parseMaskedClassifiers(docElem.getElementsByTagName("MaskedClassifiers").item(0));
	}


	private void parseMaskedClassifiers(org.w3c.dom.Node node)
			throws EClassifierUnresolvableException, NoEncapsulatedTypeInformationException {
		
		if (node == null)
			return;
		NodeList currentChildNodes = node.getChildNodes();
		for (int i = 0; i < currentChildNodes.getLength(); i++) {
			if (currentChildNodes.item(i).getNodeName().equals("Mask")) {
				Node maskNode = currentChildNodes.item(i);
				String maskName = getAttributeValue("name", maskNode);
				String eClassName = getAttributeValue("eClassifier", maskNode);
				String eAttributeName = getAttributeValue("eAttribute", maskNode);
				String eAttributeValue = getAttributeValue("eAttributeValue", maskNode);

				EClassifier maskContainer = resolveStringAsEClassifier(eClassName, c.EPACKAGESSTACK);
				EAttribute eAttribute = (EAttribute) ((EClass) maskContainer).getEStructuralFeature(eAttributeName);
				EClassifier valueContainer = eAttribute.getEType();
				EEnumLiteral valueLiteral = null;

				if (valueContainer instanceof EEnum) {
					valueLiteral = ((EEnum) valueContainer).getEEnumLiteral(eAttributeValue);
				} else {
					throw new NoEncapsulatedTypeInformationException();
				}
				// add mask to EClassInfo of maskContainer
				Mask mask = new Mask(maskName, maskContainer, eAttribute, valueLiteral);
				ECM.getEClassifierInfo(maskContainer).addMask(mask);

			}
		}
	}

	private void parseCompletenessLevel(org.w3c.dom.Node node)
			throws SERGeConfigParserException, EClassifierUnresolvableException {
		
		parseAbstractReplacements(getChildNodesByName(node, "AbstractReplacements").get(0));
		parseSupertypeReplacements(getChildNodesByName(node, "SupertypeReplacements").get(0));
		parseModuleInternalOptions(getChildNodesByName(node, "ModuleInternalOptions").get(0));
		parseModuleSetOptions(getChildNodesByName(node, "ModuleSetOptions").get(0));
		
	}

	private void parseModuleSetOptions(org.w3c.dom.Node node) 
			throws SERGeConfigParserException, EClassifierUnresolvableException {
		
		parseModuleGenerationSettings(getChildNodesByName(node, "ModuleGenerationSettings").get(0));
		
	}

	private void parseModuleGenerationSettings(org.w3c.dom.Node node)
			throws SERGeConfigParserException, EClassifierUnresolvableException {
		
		/*
		 * Default inclusion type
		 */
		InclusionType defaultIt = parseInclusionType(getAttributeValue(node, "defaultGenerationCase"));
		CIC.setDefaultFocusInclusionType(defaultIt);
		/*
		 * Explicitly defined types
		 */
		for (org.w3c.dom.Node n : getChildNodesByName(node, "GenerateModulesFor")) {
			String type = getAttributeValue(n, "type");
			String it = getAttributeValue(n, "case");
			CIC.setFocusInclusionType(resolveStringAsEClassifier(type, c.EPACKAGESSTACK), parseInclusionType(it));
		}

	}

	private void parseAbstractReplacements(org.w3c.dom.Node node) throws SERGeConfigParserException {
		
		for (org.w3c.dom.Node n : getChildNodesByName(node, "AbstractReplacement")) {
			for (OperationTypeGroup group : OperationTypeGroup.values()) {
				if (getAttributeValue(n, "ruleType").equals(group.name())
						&& getAttributeValue(n, "reduceDanglings", (Boolean) null)) {
					c.reduceAbstractDanglings.add(group);
				}
			}
		}
		
	}

	private void parseSupertypeReplacements(org.w3c.dom.Node node) throws SERGeConfigParserException {
		
		for (org.w3c.dom.Node n : getChildNodesByName(node, "SupertypeReplacement")) {
			for (OperationTypeGroup group : OperationTypeGroup.values()) {
				if (getAttributeValue(n, "ruleType").equals(group.name())) {
					if (getAttributeValue(n, "reduceDanglings", (Boolean) null))
						c.reduceSupertypeDanglings.add(group);
					if (getAttributeValue(n, "reduceContext", (Boolean) null))
						c.reduceSupertypeContext.add(group);
				}
			}
		}
		
	}

	private void parseModuleInternalOptions(org.w3c.dom.Node node)
			throws SERGeConfigParserException, EClassifierUnresolvableException {
		
		c.CREATE_NOT_REQUIRED_AND_NOT_ID_ATTRIBUTES = getAttributeValue(node, "CreateOptionalAttributes", "enable",
				null);
		parseDanglingNodeSettings(getChildNodesByName(node, "DanglingNodeSettings").get(0));
		
	}

	private void parseDanglingNodeSettings(org.w3c.dom.Node node)
			throws SERGeConfigParserException, EClassifierUnresolvableException {
		
		InclusionType defaultIt = parseInclusionType(getAttributeValue(node, "defaultTypeInclusion"));
		CIC.setDefaultDanglingInclusionType(defaultIt);
		/*
		 * Explicitly defined types
		 */
		for (org.w3c.dom.Node n : getChildNodesByName(node, "DanglingNodeSetting")) {
			String type = getAttributeValue(n, "type");
			String it = getAttributeValue(n, "include");
			CIC.setDanglingInclusionType(resolveStringAsEClassifier(type, c.EPACKAGESSTACK), parseInclusionType(it));
		}
		
	}

	private InclusionType parseInclusionType(String str) throws SERGeConfigParserException {
		if ("never".equals(str)) {
			return InclusionType.NEVER;
		} else if ("ifRequired".equals(str)) {
			return InclusionType.IF_REQUIRED;
		} else if ("always".equals(str)) {
			return InclusionType.ALWAYS;
		} else {
			throw new SERGeConfigParserException("Unkown inclusion type");
		}
	}

	private void parseMetaModel(org.w3c.dom.Node node)
			throws SERGeConfigParserException, EPackageNotFoundException, EClassifierUnresolvableException {
		
		// retrieve meta model info from xml
		String nsUri = null;
		if (metaModelForDefaultConfig == null) {
			org.w3c.dom.Node mainModelNode = getChildNodesByName(node, "MainModel").get(0);
			if (mainModelNode != null) {
				nsUri = getAttributeValue(mainModelNode, "nsUri");
				c.MAINMODEL_IS_PROFILE = getAttributeValue(mainModelNode, "isProfile", false);
			}
		} else {
			nsUri = metaModelForDefaultConfig;
		}

		// resolve meta model
		EPackage metaModel = EPackage.Registry.INSTANCE.getEPackage(nsUri);
		c.METAMODEL = metaModel;
		c.EPACKAGESSTACK.add(metaModel);
		try {
			// add contained sub packages
			c.EPACKAGESSTACK.addAll(EMFUtil.getAllSubEPackages(metaModel));
		} catch (EPackageNotFoundException e) {
			e.printStackTrace();
		}

		// retrieve all other required meta models from config  (if any) and resolve them
		List<org.w3c.dom.Node> requiredModelNodes = getChildNodesByName(node, "RequiredModel");
		for (Node requiredNode : requiredModelNodes) {
			String uri = getAttributeValue(requiredNode, "nsUri");
			EPackage reqModel = EPackage.Registry.INSTANCE.getEPackage(uri);
			if (reqModel == null) {
				throw new EPackageNotFoundException(uri);
			}
			if (!c.EPACKAGESSTACK.contains(reqModel)) {
				c.EPACKAGESSTACK.add(reqModel);
			}
		}

		// Root
		List<org.w3c.dom.Node> rootElementNodes = getChildNodesByName(node, "RootModelElement");
		c.ROOT = null; //default if no values entered
		c.ROOTECLASSCANBENESTED = false; //default if no values entered
		if (!rootElementNodes.isEmpty()) {
			org.w3c.dom.Node rootElementNode = rootElementNodes.get(0);
			String rootName = getAttributeValue(rootElementNode, "name");
			if(rootName!="") {
				EClassifier root = resolveStringAsEClassifier(rootName, c.EPACKAGESSTACK);
				c.ROOT = root;
				c.ROOTECLASSCANBENESTED = getAttributeValue(rootElementNode, "canBeNested", (Boolean) null);
			}
		}

	}

	private void parseConsistencyLevel(org.w3c.dom.Node node) throws SERGeConfigParserException {
		
		c.CREATE_MANDATORY_CHILDREN = getAttributeValue(node, "CreateMandatoryChildren", "enable", null);
		c.CREATE_MANDATORY_NEIGHBOURS = getAttributeValue(node, "CreateMandatoryNeighbours", "enable", null);
		c.MULTIPLICITY_PRECONDITIONS = getAttributeValue(node, "CreateMultiplicityPreconditions", "enable", null);
		
	}

	private void parseTransformations(org.w3c.dom.Node node) throws SERGeConfigParserException {
		
		boolean create;
		for (OperationType rt : OperationType.values()) {
			create = getAttributeValue(node, rt.name(), "enable", false);
			if (create)
				c.create.add(rt);
		}
		c.USE_SIMPLE_NAMES_ATTACHDETACH = getAttributeValue(node, OperationType.ATTACH.name(), "useSimpleNames", false);
		c.REFERENCESWITCHING_MOVE = getAttributeValue(node, OperationType.MOVE.name(), "enableReferenceSwitching",false);
		create = getAttributeValue(node, OperationType.MOVE.name(), "allowReferenceCombinations", false);
		if (create)
			c.create.add(OperationType.MOVE_REFERENCE_COMBINATION);
		c.REFERENCESWITCHING_MOVE_UP = getAttributeValue(node, OperationType.MOVE_UP.name(), "allowReferenceSwitching",
				false);
		c.REFERENCESWITCHING_MOVE_DOWN = getAttributeValue(node, OperationType.MOVE_DOWN.name(),
				"allowReferenceSwitching", false);
		c.LITERALSWITCHING_CHANGE = getAttributeValue(node, OperationType.CHANGE_LITERAL.name(),
				"allowLiteralSwitching", false);
	}
	

	private String getAttributeValue(org.w3c.dom.Node node, String attributeName) throws SERGeConfigParserException {
		
		String value = getAttributeValue(attributeName, node);
		if (value != null) {
			return value;
		} else {
			throw new SERGeConfigParserException("Required attribute \"" + attributeName + "\" not found in element \""
					+ node.getNodeName() + "\".");
		}
		
	}

	private boolean getAttributeValue(org.w3c.dom.Node node, String attributeName, Boolean defaultValue)
			throws SERGeConfigParserException {
		
		String value = getAttributeValue(attributeName, node);
		if (value != null) {
			return "true".equals(value);
		} else {
			if (defaultValue == null)
				throw new SERGeConfigParserException("Required attribute \"" + attributeName
						+ "\" not found in element \"" + node.getNodeName() + "\".");
			return defaultValue;
		}
		
	}

	@SuppressWarnings("unused")
	private String getAttributeValue(org.w3c.dom.Node node, String childElementName, String attributeName)
			throws SERGeConfigParserException {
		
		List<org.w3c.dom.Node> children = getChildNodesByName(node, childElementName);
		if (children.size() > 1)
			throw new SERGeConfigParserException();
		if (children.size() == 0) {
			throw new SERGeConfigParserException("Required node \"" + childElementName + "\" not found.");
		}
		return getAttributeValue(children.get(0), attributeName);
	}

	private boolean getAttributeValue(org.w3c.dom.Node node, String childElementName, String attributeName,
			Boolean defaultValue) throws SERGeConfigParserException {
		
		List<org.w3c.dom.Node> children = getChildNodesByName(node, childElementName);
		if (children.size() > 1)
			throw new SERGeConfigParserException();
		if (children.size() == 0) {
			if (defaultValue == null)
				throw new SERGeConfigParserException("Required node \"" + childElementName + "\" not found.");
			return defaultValue;
		}
		return getAttributeValue(children.get(0), attributeName, defaultValue);
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

	private List<org.w3c.dom.Node> getChildNodesByName(org.w3c.dom.Node node, String name) {
		
		List<org.w3c.dom.Node> nodes = new ArrayList<Node>();
		for (int i = 0; i < node.getChildNodes().getLength(); i++) {
			org.w3c.dom.Node n = node.getChildNodes().item(i);
			if (name.equals(n.getNodeName()))
				nodes.add(n);
		}
		return nodes;
	}

	/**
	 * Some meta models have references to other meta models.
	 * Currently these other required meta models have to be specified on the
	 * XML file also. This method reads in the required meta models and resolves them.
	 * 
	 * @param metaModel
	 * @throws EPackageNotFoundException 
	 */
	private static void gatherAllRequiredEPackages(EPackage metaModel) throws EPackageNotFoundException {

		// add all sub EPackages to list
		List<EPackage> subPackages = new ArrayList<EPackage>();
		for (EPackage ep : c.EPACKAGESSTACK) {
			for (EPackage sub : ep.getESubpackages()) {
				if (!subPackages.contains(sub) && !c.EPACKAGESSTACK.contains(sub)) {
					subPackages.add(sub);
				}
			}
		}
		c.EPACKAGESSTACK.addAll(subPackages);

		// try to find unresolved proxies and try to resolve them
		Map<EObject, Collection<Setting>> map = EcoreUtil.UnresolvedProxyCrossReferencer.find(metaModel);
		for (Entry<EObject, Collection<Setting>> entry : map.entrySet()) {
			for (Setting setting : entry.getValue()) {
				EClassifier targetedClassifier = setting.getEStructuralFeature().getEType();
				if (targetedClassifier.eIsProxy()) {

					// resolve eProxyURI of referenced meta model
					String absolutPathOfMetaModel = metaModel.eResource().getURI().toString();
					String completePath = convertEProxyURIToFilePath(targetedClassifier.toString(),
							absolutPathOfMetaModel);

					File ecoreFile = new File(completePath);				
					if(!ecoreFile.exists()) {
						throw new EPackageNotFoundException();
					}

					// load the referenced ecore file into the EPackage
					// Registry.
					String modelKey = ModelStorage.getInstance().loadEMF(ecoreFile.getPath());
					Resource referencedEcoreRes = ModelStorage.getInstance().getModel(modelKey);

					EObject eObject = referencedEcoreRes.getContents().get(0);
					if (eObject instanceof EPackage) {
						EPackage p = (EPackage) eObject;
						if (!EPackage.Registry.INSTANCE.containsKey(p.getNsURI())) {
							EPackage.Registry.INSTANCE.put(p.getNsURI(), p);
							if (!c.EPACKAGESSTACK.contains(p)) {
								c.EPACKAGESSTACK.add(p);
							}
						}
					}
				} else {
					// meta model resolvable. Add relevant packages for visitor
					// pattern
					if (!c.EPACKAGESSTACK.contains(targetedClassifier.getEPackage())) {
						c.EPACKAGESSTACK.add(targetedClassifier.getEPackage());
					}
				}

			}
		}
	}

	private static Set<EClassifier> getAllEClassifiers(Stack<EPackage> ePackagesStack) {
		
		Set<EClassifier> allEClassifiers = new HashSet<EClassifier>();
		for (EPackage ePackage : ePackagesStack) {
			allEClassifiers.addAll(ePackage.getEClassifiers());
		}
		return allEClassifiers;
		
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

		// TODO replace workspace_loc (in EProxyURIToFilePath converter)
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
