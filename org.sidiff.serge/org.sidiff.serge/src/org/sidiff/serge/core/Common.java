package org.sidiff.serge.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.Map.Entry;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.emf.henshin.model.PriorityUnit;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.sidiff.common.emf.extensions.impl.EClassifierInfo;
import org.sidiff.common.emf.extensions.impl.EClassifierInfoManagement;
import org.sidiff.common.henshin.HenshinModuleAnalysis;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;
import org.sidiff.common.henshin.INamingConventions;
import org.sidiff.common.henshin.NodePair;
import org.sidiff.serge.core.Configuration.OperationType;
import org.sidiff.serge.exceptions.EClassifierUnresolvableException;
import org.sidiff.serge.exceptions.EPackageNotFoundException;
import org.sidiff.serge.exceptions.OperationTypeNotImplementedException;
import org.w3c.dom.NamedNodeMap;

public class Common {

	/**
	 * Converts the first letter of a string to upper case
	 * @param s
	 * @return
	 */
	public static String toCamelCase(String s) {
		if(Character.isLetter(s.charAt(0))) {
			char c = s.charAt(0);			
			return s.replaceFirst(String.valueOf(c), String.valueOf(c).toUpperCase());
		}else{
			return s;
		}
	}


	public static String getAttributeValue(String attribName, org.w3c.dom.Node node) {
			
		NamedNodeMap attribs = node.getAttributes(); 
		for(int i=0; i<attribs.getLength(); i++) {
			if(attribs.item(i).getNodeName().equals(attribName)) {
				return attribs.item(i).getNodeValue();
			}
		}

		return null;
	}
	
	/**
	 * This method converts an unresolved EObject / EProxyURI into a correct file path.
	 * e.g. "org.something.de@1234 (eProxyURI: platform:/plugin/org.something.de/model/my.ecore#//someclassifier"
	 * into "C:\myworkspace\org.something.de\model\my.ecore".
	 * The workspace location needs to be given in as an argument (retrievable via run configuration variable).
	 * @param eProxyURIString
	 * @param workspace_loc
	 * @return
	 */
	public static String convertEProxyURIToFilePath(String eProxyURIString, String workspace_loc) {
		
		//convert eProxyURI into correct plugin-path inside workspace
		String proxyUri = eProxyURIString;
		proxyUri = proxyUri.replaceFirst("[\\w\\.@\\s\\(:]*[plugin/]+/", ""); //org...(eProxyURI: platform:/plugin/)
		proxyUri = proxyUri.replaceFirst("#[\\/]{2}[\\w\\d]*[\\)]$", ""); 	  // #//classifier)						
		String FILE_SEPERATOR = System.getProperty("file.separator");
		if(!FILE_SEPERATOR.equals("/")) {
			proxyUri = proxyUri.replace("/", "\\");
		}
		
		return workspace_loc + FILE_SEPERATOR + proxyUri;
	}
	
	/**
	 * This method recursively finds all sub EPackages of an EPackage.
	 * @param ePackage
	 * @return
	 * @throws EPackageNotFoundException 
	 */
	public static List<EPackage> getAllSubEPackages(EPackage ePackage) throws EPackageNotFoundException {
		
		if(ePackage == null) {
			throw new EPackageNotFoundException();
		}
		
		ArrayList<EPackage> list = new ArrayList<EPackage>();
		
		for(EPackage sub: ePackage.getESubpackages()) {
			if(!list.contains(sub)) {
				// add current sub package
				list.add(sub);
				// recursively add sub of sub packages...
				List<EPackage> subsOfSub = getAllSubEPackages(sub);
				subsOfSub.removeAll(list);
				list.addAll(subsOfSub);
			}
		}		
		
		return list;
	}
	
	public static EClassifier resolveStringAsEClassifier(String eClassifierName, Stack<EPackage> ePackagesStack) throws EClassifierUnresolvableException{
		EClassifier resolvedEClassifier = null;
		
		for(EPackage ePackage: ePackagesStack) {				
			resolvedEClassifier = ePackage.getEClassifier(eClassifierName);
			if(resolvedEClassifier!=null) {
				return resolvedEClassifier;
			}
		}
		throw new EClassifierUnresolvableException(eClassifierName);
	}
	
	/**
	 * This recursive method creates mandatory children for a given EClassifier.
	 * It will create mandatory children and mandatory neighbours of the child
	 * if necessary.
	 * @param rule
	 * 		  the container rule
	 * @param eClassifierInfo
	 * 		  The EClassifierInfo of the connectable Node
	 * @param eClassifierNode
	 * 		  The Node to which mandatory neighbours need to be connected
	 * @param opType
	 * 		  needs to now the OperationType
	 * @param reduceToSuperType
	 * 		  needs to know if reducedToSuperType is wished for the given OperationType
	 * @throws OperationTypeNotImplementedException 
	 */
	public static void createMandatoryChildren(Rule rule, EClassifierInfo eClassifierInfo, Node eClassifierNode, OperationType opType, boolean reduceToSuperType) throws OperationTypeNotImplementedException {
			
		for(Entry<EReference,List<EClassifier>> childEntry: eClassifierInfo.getMandatoryChildren().entrySet()) {
			List<EClassifier> children = childEntry.getValue();
			EReference eRef = childEntry.getKey();
			
			for(EClassifier child: children) {

				if (!ElementFilter.getInstance().isAllowedAsDangling(child, opType, reduceToSuperType))  continue;
				
				for(int i=0; i<eRef.getLowerBound();i++) {

					Node newChildNode = null;
					String name = getFreeNodeName(GlobalConstants.CHILD, rule);
					// create node for mandatory child
					newChildNode = HenshinRuleAnalysisUtilEx.createCreateNode(rule.getRhs(), name, (EClass) child);				
					// create edge for mandatory child
					HenshinRuleAnalysisUtilEx.createCreateEdge(eClassifierNode, newChildNode, eRef);
					// Add necessary attributes to the new eClass node
					createAttributes((EClass) child, newChildNode, rule);					
					// recursively check for child's mandatories and create them
					if(EClassifierInfoManagement.getInstance().getEClassifierInfo(child).hasMandatories()) {
						createMandatoryChildren(rule, EClassifierInfoManagement.getInstance().getEClassifierInfo(child), newChildNode, opType, reduceToSuperType);
						createMandatoryNeighbours(rule, EClassifierInfoManagement.getInstance().getEClassifierInfo(child), newChildNode, opType, reduceToSuperType);
					}

				}
			}
		}
	}		

	/**
	 * This recursive method creates mandatory neighbours for a given EClass.
	 * It will create mandatory children and mandatory neighbours of the neighbour
	 * if necessary.
	 * @param rule
	 * 		  the container rule
	 * @param eClassifierInfo
	 * 		  The EClassifierInfo of the connectable Node
	 * @param eClassifierNode
	 * 		  The Node to which mandatory neighbours need to be connected
	 * @param opType
	 * 		  needs to now the OperationType
	 * @param reduceToSuperType
	 * 		  needs to know if reducedToSuperType is wished for the given OperationType
	 * @throws OperationTypeNotImplementedException 
	 */
	public static void createMandatoryNeighbours(Rule rule, EClassifierInfo eClassifierInfo, Node eClassifierNode, OperationType opType, boolean reduceToSuperType) throws OperationTypeNotImplementedException {

		for(Entry<EReference,List<EClassifier>> neighbourEntry: eClassifierInfo.getMandatoryNeighbours().entrySet()) {
			EReference eRef = neighbourEntry.getKey();
			EReference eOpposite = eRef.getEOpposite();
			List<EClassifier> neighbours = neighbourEntry.getValue();
			
			for(EClassifier neighbour: neighbours) {

				if (!ElementFilter.getInstance().isAllowedAsDangling(neighbour, opType, reduceToSuperType))  continue;
				
				// check if neighbours have already been created to its maximum lowerBound and skip if so
				// else allow creation of neigbour
				boolean alreadyCreatedMaxNeighbourNode = false;
				int nodesWithSameERef = 0;
				for(Node n:rule.getRhs().getNodes()) {
					if(n.getType().equals(neighbour)) {
						for(Edge e:n.getIncoming()) {
							if(e.getType().getName().equals(eRef.getName()) && (eRef.getLowerBound()>nodesWithSameERef)) {
								nodesWithSameERef++;
								if((eRef.getLowerBound()<=nodesWithSameERef)) {
									alreadyCreatedMaxNeighbourNode = true;
									break;
								}
								break;
							}			
						}
						if(alreadyCreatedMaxNeighbourNode){
							break;
						}
						
					}
				}
				

				for(int i=0; i<eRef.getLowerBound();i++) {
					// create node for mandatory neighbour
					// but only if it wasn't created to its lowerBound maximum before.
					// This ensures we can have a neighbour circle support.
					if(!alreadyCreatedMaxNeighbourNode) {

						Node newNeighbourNode = null;

						// create node for mandatory neighbour
						String existingName = getFreeNodeName(GlobalConstants.EX,rule);
						NodePair preservedNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, existingName, (EClass) neighbour);
						newNeighbourNode = preservedNodePair.getRhsNode();		
						// create edge for mandatory neighbour
						HenshinRuleAnalysisUtilEx.createPreservedEdge(rule, eClassifierNode, newNeighbourNode, eRef);						
						// create edge for eOpposite, if any
						if(eOpposite!=null) {
							HenshinRuleAnalysisUtilEx.createPreservedEdge(rule,newNeighbourNode, eClassifierNode, eOpposite);
						}
						// recursively check neighbour's mandatories and create them
						createMandatoryChildren(rule, EClassifierInfoManagement.getInstance().getEClassifierInfo(neighbour), newNeighbourNode, opType, reduceToSuperType);
						createMandatoryNeighbours(rule, EClassifierInfoManagement.getInstance().getEClassifierInfo(neighbour), newNeighbourNode, opType, reduceToSuperType);

					}
				}
			}
		}
	}
	
	private static void createAttributes(EClass forEClass, Node inEClassNode, Rule rule) {
		
		// Add necessary attributes to the new eClass node
		for(EAttribute ea: forEClass.getEAllAttributes()) {
			//we don't want: derived, transient or unchangeable EAttributes
			if(!ea.isDerived() && !ea.isTransient() && ea.isChangeable()) {
				String eaName = getFreeAttributeName(ea.getName(),rule);

				boolean createNotRequiredAndNotIDAttributes = Configuration.getInstance().CREATENOTREQUIREDANDNOTIDATTRIBUTES;
				if( createNotRequiredAndNotIDAttributes ||
					!createNotRequiredAndNotIDAttributes && (ea.isRequired() || ea.isID())){
					HenshinRuleAnalysisUtilEx.createCreateAttribute(
							inEClassNode, ea,Common.toCamelCase(getFreeAttributeName(eaName, rule))
							);
				}
			}
		}
		
	}
	
	/**
	 * Method that finds the next unused name for a Node.
	 * Example: If a rule already contains a Node for an EClassifier 'Operation' e.g. "Operation1",
	 * it will deliver "Operation2" in case there is no "Operation2" and so on.
	 * @param originalName
	 * @param rule
	 * @return free node name
	 */
	private static String getFreeNodeName(String originalName, Rule rule) {

		originalName = Common.toCamelCase(originalName);

		List<Node> allNodes = HenshinRuleAnalysisUtilEx.getLHSIntersectRHSNodes(rule);
		allNodes.addAll(HenshinRuleAnalysisUtilEx.getLHSMinusRHSNodes(rule));
		allNodes.addAll(HenshinRuleAnalysisUtilEx.getRHSMinusLHSNodes(rule));
		
		int count = 0;
		for(Node node: allNodes) {
			String nNode = node.getName();
			if(nNode!=null && node.getName().startsWith(originalName)) {
				count++;
			}		
		}
		
		if(count!=0) {
			return originalName+String.valueOf(count);
		}else{
			return originalName;
		}
	}

	/**
	 * Method that finds the next unused name for an attribute variable.
	 * Example: If a rule already contains some attribute variable for an EAttribute 'name' e.g. "Name1",
	 * it will deliver "Name2" in case there is no "Name2" and so on.
	 * 
	 * @param originalName
	 * @param rule
	 * @return free attribute variable name
	 */
	private static String getFreeAttributeName(String originalName, Rule rule) {

		originalName = Common.toCamelCase(originalName);

		List<Attribute> allCreateAttributes = HenshinRuleAnalysisUtilEx.getCreationAttributes(rule);		
		int count = 0;
		for(Attribute attrib: allCreateAttributes) {
			
			if(attrib.getValue().startsWith(originalName)) {
				count++;
			}		
		}
		
		if(count!=0) {
			return originalName+String.valueOf(count);
		}else{
			return originalName;
		}
	}
	
	/**
	 * Creates a rule inside a module containing with Nodes and Edges for the EClassifiers and EReferences given as arguments.
	 * How the Nodes and Edges are created depends on the OperationType. The result is a basic rule that does not consider
	 * consistency criteria yet (like mandatory children or neighbours).
	 * 
	 * @param module
	 * 			the module that should contain the basic rule
	 * @param eRefA
	 * 			reference from eClassifier to targetA, if any
	 * @param eRefB
	 * 			reference from eClassifier to targetB, if any
	 * @param eClassifier
	 * 			eClassifier which will be the 'selectedEObject', and might have references to given targets.
	 * @param targetA
	 * 			a targeted classifier, if any
	 * @param targetB
	 * 			a targeted classifier, if any
	 * @param opType
	 * 			the operationType
	 * @throws OperationTypeNotImplementedException 
	 * 			in case switch-case concerning opType is missing
	 */
	public static Rule createBasicRule(Module module, EReference eRefA, EClassifier eClassifier, EClassifier targetA, EReference eRefB, EClassifier targetB, OperationType opType) throws OperationTypeNotImplementedException {

		Rule rule = null;
		NodePair selectedNodePair = null;
		NodePair newNodePair = null;
		NodePair oldNodePair = null;
		Node rhsNode = null;
		
		switch(opType) {

			case ADD:
	
				// ADD ***************************************************************************************************/
				rule = HenshinFactory.eINSTANCE.createRule();
				rule.setActivated(true);
				rule.setName("addTo"+eClassifier.getName() + "_" +eRefA.getName() + "_"+targetA.getName());
				rule.setDescription("Adds to "+eClassifier.getName() +"'s reference "+ eRefA.getName() +" the target "+ targetA.getName());
				module.getUnits().add(rule);
	
				// create preserved node for eClass
				selectedNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, GlobalConstants.SEL, (EClass) eClassifier);
				rhsNode = selectedNodePair.getRhsNode();
	
				newNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, GlobalConstants.NEWTGT, (EClass) targetA);
	
				// create <<create>> edge for new target for EReference and it's EOpposite, if any
				HenshinRuleAnalysisUtilEx.createCreateEdge(rhsNode, newNodePair.getRhsNode(), eRefA);
	
				break;
				
			case SET_REFERENCE:
	
				// SET ***************************************************************************************************/
				rule = HenshinFactory.eINSTANCE.createRule();
				rule.setActivated(true);
				rule.setName("set"+eClassifier.getName() + "_" +eRefA.getName() +GlobalConstants.TO+targetA.getName());
				rule.setDescription("Set"+eClassifier.getName() + "Ref" +eRefA.getName() +"To"+targetA.getName());
				module.getUnits().add(rule);
	
				// create preserved node for eClass
				selectedNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, GlobalConstants.SEL, (EClass) eClassifier);
				rhsNode = selectedNodePair.getRhsNode();
	
				newNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, GlobalConstants.NEWTGT, (EClass) targetA);
	
				// create <<create>> edge for new target for EReference and it's EOpposite, if any
				HenshinRuleAnalysisUtilEx.createCreateEdge(rhsNode, newNodePair.getRhsNode(), eRefA);
				
				break;
				
			case CHANGE:
				
				// CHANGE ***************************************************************************************************/
				rule = HenshinFactory.eINSTANCE.createRule();
				rule.setActivated(true);
				rule.setName("change"+eClassifier.getName() + "_" +eRefA.getName() + GlobalConstants.TO +targetA.getName());
				rule.setDescription("Change the EReference "+eRefA.getName());
				module.getUnits().add(rule);
	
				// create preserved node for eClass
				selectedNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, GlobalConstants.SEL, (EClass) eClassifier);
	
				oldNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, GlobalConstants.OLDTGT, (EClass) targetA);		
				newNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, GlobalConstants.NEWTGT, (EClass) targetA);
	
				// create <<delete>> edge to old target for EReference and it's EOpposite, if any
				HenshinRuleAnalysisUtilEx.createDeleteEdge(selectedNodePair.getLhsNode(), oldNodePair.getLhsNode(), eRefA, rule);
				// create <<create>> edge for new target for EReference and it's EOpposite, if any
				HenshinRuleAnalysisUtilEx.createCreateEdge(selectedNodePair.getRhsNode(), newNodePair.getRhsNode(), eRefA);
				
				break;
				
			case MOVE:
				
				// MOVE ***************************************************************************************************/
				rule = HenshinFactory.eINSTANCE.createRule();
				rule.setActivated(true);
				rule.setName("move"+eClassifier.getName() + GlobalConstants.FROM +targetA.getName() + "_"+eRefA.getName()+ GlobalConstants.TO +targetB.getName()+"_"+targetB.getName()+"");
				rule.setDescription("Moves "+eClassifier.getName() + " from " +targetA.getName() + "(Reference:"+eRefA.getName()+") to"+targetB.getName()+"(Reference:"+targetB.getName()+")");
				module.getUnits().add(rule);
	
				// create preserved node for eClass
				selectedNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, GlobalConstants.SEL, (EClass) eClassifier);
	
				oldNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, GlobalConstants.OLDSRC, (EClass) targetA);		
				newNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, GlobalConstants.NEWSRC, (EClass) targetB);
	
				// create <<delete>> edge to old target for EReference and it's EOpposite, if any
				HenshinRuleAnalysisUtilEx.createDeleteEdge(oldNodePair.getLhsNode(), selectedNodePair.getLhsNode(), eRefA, rule);
				// create <<create>> edge for new target for EReference and it's EOpposite, if any
				HenshinRuleAnalysisUtilEx.createCreateEdge(newNodePair.getRhsNode(), selectedNodePair.getRhsNode(), eRefB);
			
				break;
				
			case CREATE:
	
				// CamelCasing of target-context name
				String contextName = "";
				if(targetA!=null) {
					contextName = Common.toCamelCase(targetA.getName());
				}else{
					contextName = "Model";			
				}				
	
				// Add new rule to Module
				rule = HenshinRuleAnalysisUtilEx.createRule(
						"create"+eClassifier.getName()+GlobalConstants.IN+ contextName,
						"creates one "+eClassifier.getName()+" in the context: "+ contextName,
						true,
						module);
	
	
				// create <<preserve>> nodes for context, if any
				String selectedName = getFreeNodeName(GlobalConstants.SEL,rule);
				Graph rhs = null;
				if(targetA!=null) {	
					NodePair nodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(
							rule,
							selectedName,
							(EClass) targetA);
					rhs = nodePair.getRhsNode().getGraph();
				}
				else{
					rhs = rule.getRhs();
				}			
	
				// Add new eClass to RHS
				String newName = getFreeNodeName(GlobalConstants.NEW,rule);
				Node newNode = HenshinRuleAnalysisUtilEx.createCreateNode(rhs, newName, (EClass) eClassifier);	
	
				// Add necessary attributes to the new eClass node
				createAttributes((EClass) eClassifier, newNode, rule);
	
				// Add edge between target-context and new eClass, if any
				if(targetA!=null && eRefA!=null) {
					Node contextNode = null;
					for(Node n: rhs.getNodes()) {
						String nName = n.getName();
						if(nName!=null && nName.equals(selectedName)) {
							contextNode = n;
						}
					}
					HenshinRuleAnalysisUtilEx.createCreateEdge(contextNode, newNode, eRefA);
				}
	
				break;
			
			default:
				throw new OperationTypeNotImplementedException(opType.toString());
		}
		return rule;
	}
	
	
	/**
	 * The Method that creates the "mainUnit", its parameters and parameter mappings to rule parameters or vice versa
	 * (depending on the parameter nature (in or out)). Internally it also removes all unnecessary units and unnecessary existing parameters.
	 * @param module
	 * @param eClassifier
	 * @param tsType
	 */
	public static void mainUnitCreation(Module module, EClassifier eClassifier, OperationType tsType) {
		
		removeAllNonRuleUnits(module);
		List<Rule> rulesUnderModule = HenshinModuleAnalysis.getAllRules(module);
		
		/** Unit creation *************************************************/	
		PriorityUnit prioUnit = HenshinFactory.eINSTANCE.createPriorityUnit();
		prioUnit.setActivated(true);
		prioUnit.setName(INamingConventions.MAIN_UNIT);		
				
		/** Parameter and Mapping creation ********************************/
		
		// In case of DELETE module, remove unnecessary parameters
		if(tsType==OperationType.DELETE) {
			removeUnnecessaryParametersForDELETE(module, prioUnit);
		}
		
		// Create the mandatory "selectedEObject"-Parameter with type information
		Parameter selectedEObjectParameter = HenshinFactory.eINSTANCE.createParameter(GlobalConstants.SELEO);
		Node selectedEObjectNode = HenshinRuleAnalysisUtilEx.getNodeByName(rulesUnderModule.get(0), GlobalConstants.SEL, true);
		selectedEObjectParameter.setType(selectedEObjectNode.getType());
		prioUnit.getParameters().add(selectedEObjectParameter);
			
		for(Rule rule: rulesUnderModule) {
					
			//we only need to consider RHS (it covers <<preserved>> and <<create>> Nodes/Attributes)
			//Since <<delete>> Node Parameters are renamed <<create>> Node Parameters and therefore
			//we don't have to check LHS here. Also because <<delete>> Attributes never appear
			//(even not in UNSETs because they only revert Attribute values to Default, if any).
			for(Node nInRHS : rule.getRhs().getNodes()) {
				String nodeName = nInRHS.getName();
				EClass nodeType = nInRHS.getType();
				/** Add Parameter for RHS Nodes ********************************/
				if(nodeName!=null && !nodeName.equals("")) {
					// ..to rule
					Parameter pForRule = HenshinFactory.eINSTANCE.createParameter(nodeName);
					pForRule.setType(nodeType);
					if(HenshinRuleAnalysisUtilEx.getParameterByName(rule, nodeName)==null) {
						pForRule.setUnit(rule);
						rule.getParameters().add(pForRule);
						// ..to unit
						if(!pForRule.getName().equals(GlobalConstants.SEL)
								&& HenshinRuleAnalysisUtilEx.getParameterByName(prioUnit, pForRule.getName())==null) {
							Parameter pForUnit = HenshinFactory.eINSTANCE.createParameter(nodeName);
							pForUnit.setType(nodeType);
							prioUnit.getParameters().add(pForUnit);
						}
					}
				}
				
				/** Add Parameter for RHS Attributes ***************************/
				for(Attribute a: nInRHS.getAttributes()) {
					EClassifier attributeType = a.getType().getEType();
					Object defaultValue = a.getType().getDefaultValue();
					String defaultValueName = null;
					if(defaultValue!=null) {
						defaultValueName = defaultValue.toString();
					}
					
					//Don't create Parameter if:
					// attribute is in quotation marks "..." (like specific literal values, e.g. "initial").
					// Else create Parameter.
					if((a.getValue()!="null" 
							&& !a.getValue().startsWith("\"") 
							&& ((defaultValueName!=null && !a.getValue().equals(defaultValueName))
							|| defaultValueName==null))) {
						Parameter pForRule = HenshinFactory.eINSTANCE.createParameter(a.getValue());
						pForRule.setType(attributeType);
						Parameter pForUnit = HenshinFactory.eINSTANCE.createParameter(a.getValue());
						pForUnit.setType(attributeType);
						if(HenshinRuleAnalysisUtilEx.getParameterByName(rule, pForRule.getName())==null) {
							// ..to rule
							rule.getParameters().add(pForRule);
							pForRule.setUnit(rule);
							// ..to unit
							if(HenshinRuleAnalysisUtilEx.getParameterByName(prioUnit, pForUnit.getName())==null) {
								prioUnit.getParameters().add(pForUnit);
								pForUnit.setUnit(prioUnit);
							}
						}
					}
				}
			}			
			
			// Create Mappings
			for(Parameter p :rule.getParameters()) {
				// == selectedEObject
				assert(p.getName()!=null): rule.getName();
				if(p.getName().equals(GlobalConstants.SEL)) {
					ParameterMapping selEObjectMapping = HenshinFactory.eINSTANCE.createParameterMapping();
					selEObjectMapping.setSource(selectedEObjectParameter);
					selEObjectMapping.setTarget(p);			
					prioUnit.getParameterMappings().add(selEObjectMapping);
				}
				// == selected element is the toBeDeleted (in case there is no context to delete from)
				else if(HenshinRuleAnalysisUtilEx.getParameterByName(rule, GlobalConstants.SEL)==null && p.getName().equals(GlobalConstants.DEL)) {
					ParameterMapping selEObjectMapping = HenshinFactory.eINSTANCE.createParameterMapping();
					selEObjectMapping.setSource(selectedEObjectParameter);
					selEObjectMapping.setTarget(p);			
					prioUnit.getParameterMappings().add(selEObjectMapping);
				}
				// == new / out-parameter
				else if(p.getName().matches(GlobalConstants.NEW+"[0-9]*") || (rule.getName().startsWith("create") && p.getName().matches(GlobalConstants.CHILD+"[0-9]*"))) {
					ParameterMapping pm = HenshinFactory.eINSTANCE.createParameterMapping();
					pm.setSource(p);
					pm.setTarget(HenshinRuleAnalysisUtilEx.getParameterByName(prioUnit, p.getName()));
					if(!prioUnit.getParameterMappings().contains(pm)) {
						prioUnit.getParameterMappings().add(pm);
					}
				}
				else if(p.getName().matches(GlobalConstants.NEW+"[0-9]*") || (rule.getName().startsWith("delete") && p.getName().matches(GlobalConstants.CHILD+"[0-9]*"))) {
					ParameterMapping pm = HenshinFactory.eINSTANCE.createParameterMapping();
					pm.setSource(HenshinRuleAnalysisUtilEx.getParameterByName(prioUnit, p.getName()));
					pm.setTarget(p);
					if(!prioUnit.getParameterMappings().contains(pm)) {
						prioUnit.getParameterMappings().add(pm);
					}
				}
				
				else if(p.getName().matches(GlobalConstants.NEWTGT+"[0-9]*")|| p.getName().matches(GlobalConstants.NEWSRC+"[0-9]*")) {
					ParameterMapping pm = HenshinFactory.eINSTANCE.createParameterMapping();
					pm.setTarget(p);
					pm.setSource(HenshinRuleAnalysisUtilEx.getParameterByName(prioUnit, p.getName()));
					if(!prioUnit.getParameterMappings().contains(pm)) {
						prioUnit.getParameterMappings().add(pm);
					}
					
				// == every other in-parameter
				}else{
					ParameterMapping pm = HenshinFactory.eINSTANCE.createParameterMapping();
					pm.setSource(HenshinRuleAnalysisUtilEx.getParameterByName(prioUnit, p.getName()));
					pm.setTarget(p);
					if(!prioUnit.getParameterMappings().contains(pm)) {
						prioUnit.getParameterMappings().add(pm);
					}		
				}
			}					
			// Add rule to unit
			prioUnit.getSubUnits().add(rule);						
		}		
		// Add unit to module
		module.getUnits().add(prioUnit);
	}
	
	
	/**
	 * Method that removes all units (not rules) inside a module.
	 * @param module
	 */
	private static void removeAllNonRuleUnits(Module module) {
		Iterator<Unit> itUnit = module.getUnits().iterator();
		while(itUnit.hasNext()) {
			Unit unit = itUnit.next();
			if(!(unit instanceof Rule)) {
				itUnit.remove(); //removes the current unit (not the iterator)
			}
		}		
	}
	
	/**
	 * Method that removes parameters which are not needed for an inverse.
	 * E.g. a DELETE-Module does not need EAttributes which were necessary for the
	 * creation of an EClassifier inside a CREATE-Module.
	 * @param module
	 * @param mainUnit
	 */
	private static void removeUnnecessaryParametersForDELETE(Module module, Unit mainUnit) {
		//retain only ChildX/ExistingX/ToBeDeleted Parameters
		List<Parameter> unnecessaryParameters = new ArrayList<Parameter>();

		for(Rule r: HenshinModuleAnalysis.getAllRules(module)) {
			for(Parameter p: r.getParameters()) {
				
				if(p.getName().startsWith(GlobalConstants.CHILD) || p.getName().startsWith(GlobalConstants.EX) || p.getName().startsWith(GlobalConstants.DEL)) {
					
					boolean alreadyContained = false;
					for(Parameter pInInverseUnit: mainUnit.getParameters()) {
						if(pInInverseUnit.equals(p.getName())) {
							alreadyContained = true;
							break;
						}
					}
					
					if(!alreadyContained) {
						Parameter newEClassParam = HenshinFactory.eINSTANCE.createParameter(p.getName());
						newEClassParam.setType(p.getType());
						mainUnit.getParameters().add(newEClassParam);
					}												
				}else{
					unnecessaryParameters.add(p);
				}
			}
		}			
		//remove unnecessary parameters
		HenshinModuleAnalysis.getAllRules(module).get(0).getParameters().removeAll(unnecessaryParameters);
	}
}
