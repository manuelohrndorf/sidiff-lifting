package org.sidiff.editrule.generator.serge.core;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.eclipse.emf.henshin.model.Annotation;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.sidiff.common.emf.metamodel.analysis.EClassifierInfo;
import org.sidiff.common.emf.metamodel.analysis.EClassifierInfoManagement;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;

/**
 * IMPORTANT: This class is tested for CREATE-Rules only!
 */
public class ReplacementTableHeader implements Iterable<Node> {

	private Stack<Node> tableHeader;

	private EClassifierInfoManagement ECM;

	public ReplacementTableHeader(Module module) {

		ECM = EClassifierInfoManagement.getInstance();
		tableHeader = new Stack<Node>();
		collectReplacableNodes(module);

	}

	private void collectReplacableNodes(Module module) {

		// gather all child nodes within a containment relation
		List<Node> childNodes = HenshinRuleAnalysisUtilEx.getChildNodesWithinAContainmentRelation(module,
				HenshinRuleAnalysisUtilEx.NodeKindSelection.CREATE, false);

		/*
		 * Exclude the focus node from variant creation We don't want variants
		 * of the focus node, since this is covered by separate rules. It is
		 * also a precondition for ReplacementTable#fillRows
		 */
		Node focusNode = null;
		for (Node n : childNodes) {
			for (Annotation a : n.getAnnotations()) {
				if ("SERGE_".equals(a.getKey()) && "FOCUS".equals(a.getValue())) {
					focusNode = n;
					break;
				}
			}
		}

		// only collect nodes that are abstract or super type (i.e. replacable)
		Iterator<Node> childNodeIterator = childNodes.iterator();
		while (childNodeIterator.hasNext()) {
			Node childNode = childNodeIterator.next();
			EClassifierInfo childEInfo = ECM.getEClassifierInfo(childNode.getType());
			if (childNode != focusNode && (childNode.getType().isAbstract()
					|| !ECM.getAllSubTypes(childEInfo).isEmpty())) {
				tableHeader.push(childNode);
			}
		}
	}

	@Override
	public Iterator<Node> iterator() {
		return tableHeader.iterator();
	}

	public Node firstElement() {
		return tableHeader.firstElement();
	}

	public Integer size() {
		return tableHeader.size();
	}

	public Node get(int index) {
		return tableHeader.get(index);
	}

	public int getIndexOf(Node node) {
		return tableHeader.indexOf(node);
	}

	/**
	 * Inserts the node at the given index. If the position is already occupied,
	 * the node will be appended. (use set(..) if overwriting is needed)
	 * 
	 * @param node
	 * @param index
	 */
	public void insertElementAt(Node node, int index) {
		tableHeader.insertElementAt(node, index);
	}

	/**
	 * Overwrites the node at the given index.
	 * 
	 * @param node
	 * @param index
	 */
	public void set(Node node, int index) {
		tableHeader.set(index, node);
	}

	public void ensureCapacity(int minCapacity) {
		tableHeader.ensureCapacity(minCapacity);
	}

	public boolean isEmpty() {
		return tableHeader.isEmpty();
	}
}
