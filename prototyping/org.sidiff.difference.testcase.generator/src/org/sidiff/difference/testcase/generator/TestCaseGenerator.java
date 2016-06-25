package org.sidiff.difference.testcase.generator;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.MappingList;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.difference.testcase.util.MetaModelAccess;

/**
 * 
 * @author cpietsch
 *
 */
public class TestCaseGenerator {
	
	/**
	 * The {@link Rule}
	 */
	private Rule rule;

	/**
	 * 
	 */
	private Map<String, EFactory> eFactories;
	
	/**
	 * 
	 */
	private Map<Node, EObject> node2eObject = new HashMap<Node, EObject>();
	
	/**
	 * 
	 */
	private List<EObject> containments;
	
	/**
	 * 
	 * @param rule
	 */
	public void init(Rule rule){
		this.rule = rule;
		this.eFactories = new HashMap<String, EFactory>(); 
		this.resolveEFactories(rule);
	}

	/**
	 * Generates the origin model of the test scenario.
	 * 
	 * @param pacs
	 *            consider positive application conditions
	 * @param nacs
	 *            consider negative application conditions
	 * @param multi
	 *            consider multi rules
	 * @return a {@link Collection} containing all generated model elements of
	 *         the origin model
	 */
	public Collection<EObject> generateOriginModel(boolean pacs, boolean nacs, boolean multi) {
		node2eObject = new HashMap<Node, EObject>();
		containments = new LinkedList<EObject>();
	
		generateKernelRule(pacs, nacs, multi, rule, "lhs");
			
		generateContainer(node2eObject);

		node2eObject.values().removeAll(containments);

		return node2eObject.values();
	}
	
	/**
	 * Generates the modified model of the test scenario.
	 * 
	 * @param pacs
	 *            consider positive application conditions
	 * @param nacs
	 *            consider negative application conditions
	 * @param multi
	 *            consider multi rules
	 * @return a {@link Collection} containing all generated model elements of
	 *         the modified model
	 */
	public Collection<EObject> generateModifiedModel(boolean pacs, boolean nacs, boolean multi) {
		node2eObject = new HashMap<Node, EObject>();
		containments = new LinkedList<EObject>();
	
		generateKernelRule(pacs, nacs, multi, rule, "rhs");
		
		generateContainer(node2eObject);
		
		node2eObject.values().removeAll(containments);
		
		return node2eObject.values();
	}

	/**
	 * 
	 * @param pacs
	 * @param nacs
	 * @param multi
	 * @param rule
	 * @param side
	 */
	private void generateKernelRule(boolean pacs, boolean nacs, boolean multi, Rule rule, String side){
		Graph graph = (Graph)rule.eGet(rule.eClass().getEStructuralFeature(side));
		generateNodes(graph.getNodes());
		generateEdges(graph.getEdges());

		if (pacs) {
			generateACs(graph.getPACs());
		}
		if (nacs) {
			generateACs(graph.getNACs());
		}
		
		if(multi){
			generateMultiRules(pacs, nacs, multi, rule.getMultiRules(), side);
		}
	}
	
	/**
	 * 
	 * @param pacs
	 * @param nacs
	 * @param multi
	 * @param rules
	 * @param side
	 */
	private void generateMultiRules(boolean pacs, boolean nacs, boolean multi, List<Rule> rules, String side) {

		for (Rule rule : rules) {
			List<Node> nestedNodes = new LinkedList<Node>();
			List<Edge> nestedEdges = new LinkedList<Edge>();
			
			MappingList mappingList = rule.getMultiMappings();
			Graph graph = (Graph)rule.eGet(rule.eClass().getEStructuralFeature(side));
			for (Node node : graph.getNodes()) {
				if (mappingList.getOrigin(node) == null) {
					nestedNodes.add(node);
				} else if(node2eObject.get(mappingList.getOrigin(node)) != null){
					node2eObject.put(node, node2eObject.get(mappingList.getOrigin(node)));
					generateAttributes(node);
				}
			}
			generateNodes(nestedNodes);

			for (Edge edge : graph.getEdges()) {
				if (mappingList.getOrigin(edge) == null) {
					nestedEdges.add(edge);
				}
			}

			generateEdges(nestedEdges);
			
			if(pacs){
				generateACs(graph.getPACs());
			}
			if(nacs){
				generateACs(graph.getNACs());
			}
			
			if(multi){
				generateMultiRules(pacs, nacs, multi, rule.getMultiRules(), side);
			}
		}
	}
	
	/**
	 * 
	 * @param nodes
	 */
	private void generateNodes(List<Node> nodes) {

		for (Node node : nodes) {
			EClass eClass = MetaModelAccess.getFirstConcreteSubClass(node.getType());
			EFactory eFactory = eFactories.get(eClass.getEPackage().getNsURI());
			EObject eObject = eFactory.create(eClass);
			if (eObject.eClass().getEStructuralFeature("name") != null) {
				String name = node.getName() != null && !node.getName().isEmpty() ? node.getName()
						: eObject.eClass().getName().replace(eObject.eClass().getName().substring(0, 1),
								eObject.eClass().getName().substring(0, 1).toLowerCase());
				eObject.eSet(eObject.eClass().getEStructuralFeature("name"), name);
			}
			node2eObject.put(node, eObject);
			generateAttributes(node);
		}
	}

	/**
	 * 
	 * @param node
	 */
	private void generateAttributes(Node node) {

		for (Attribute attribute : node.getAttributes()) {
			EAttribute eAttribute = attribute.getType();
			if(eAttribute.isChangeable()){
				EObject eObject = node2eObject.get(node);
				if (attribute.getValue() != null) {
					Class<?> clazz = eAttribute.getEType().getInstanceClass();
					try {
						eObject.eSet(eAttribute, clazz.cast(attribute.getValue()));
					} catch (ClassCastException e) {
						System.out.println(e);
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param edges
	 */
	@SuppressWarnings("unchecked")
	private void generateEdges(List<Edge> edges) {

		for (Edge edge : edges) {
			EReference eReference = edge.getType();
			if (eReference.isChangeable()) {
				EObject eObject_src = node2eObject.get(edge.getSource());
				EObject eObject_tgt = node2eObject.get(edge.getTarget());

				if(eObject_src != null && eObject_tgt != null){
					if (eReference.isMany()) {
						List<Object> eObjects = new LinkedList<Object>();
						eObjects.add(eObject_tgt);
						eObjects.addAll((Collection<? extends Object>) eObject_src.eGet(eReference));
						eObject_src.eSet(eReference, eObjects);
					} else {
						eObject_src.eSet(eReference, eObject_tgt);
					}
					if (eReference.isContainment()) {
						containments.add(node2eObject.get(edge.getTarget()));
					}
				}

			}
		}
	}
	
	/**
	 * 
	 * @param acs
	 */
	private void generateACs(List<NestedCondition> acs) {

		for (NestedCondition condition : acs) {
			List<Node> nestedNodes = new LinkedList<Node>();
			List<Edge> nestedEdges = new LinkedList<Edge>();

			MappingList mappingList = condition.getMappings();
			for (Node node : condition.getConclusion().getNodes()) {
				if (mappingList.getOrigin(node) == null) {
					nestedNodes.add(node);
				} else {
					node2eObject.put(node, node2eObject.get(mappingList.getOrigin(node)));
					generateAttributes(node);
				}
			}
			generateNodes(nestedNodes);

			for (Edge edge : condition.getConclusion().getEdges()) {
				if (mappingList.getOrigin(edge) == null) {
					nestedEdges.add(edge);
				}
			}

			generateEdges(nestedEdges);
		}
	}
	
	/**
	 * 
	 * @param node2eObjects
	 */
	private void generateContainer(Map<Node, EObject> node2eObjects) {
		for (EObject eObject : node2eObjects.values()) {
			if(eObject.eContainer() == null){
				//TODO find common container element
			}
		}
	}

	/**
	 * 
	 * @param module
	 * @return
	 */
	private void resolveEFactories(Rule rule) {
		Module module = (Module) rule.eContainer();
		for(EPackage ePackage : module.getImports()){
			eFactories.put(ePackage.getNsURI(), ePackage.getEFactoryInstance());
		}
	}
	
	public boolean hasPACs(boolean checkMulti){
		
		if(hasPACs(rule, checkMulti)){
			return true;
		}
		
		return false;
	}
	
	private boolean hasPACs(Rule rule, boolean checkMulti){
		boolean hasPACs = false;
		if(checkMulti){
			for(Rule nestedRule : rule.getMultiRules()){
				hasPACs = hasPACs(nestedRule, checkMulti);
			}
		}
		return  hasPACs || rule.getLhs().getPACs().size() > 0;
	}
	
	public boolean hasNACs(boolean checkMulti){

		if(hasNACs(rule, checkMulti)){
			return true;
		}
		
		return false;
	}
	
	private boolean hasNACs(Rule rule, boolean checkMulti){
		boolean hasNACs = false;
		if(checkMulti){
			for(Rule nestedRule : rule.getMultiRules()){
				hasNACs = hasNACs(nestedRule, checkMulti);
			}
		}
		return  hasNACs || rule.getLhs().getNACs().size() > 0;
	}
	
	public boolean hasMultiRules(){
		return rule.getMultiRules().size()>0;
	}
}
