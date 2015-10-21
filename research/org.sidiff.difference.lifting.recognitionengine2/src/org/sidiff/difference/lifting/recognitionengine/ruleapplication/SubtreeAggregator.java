package org.sidiff.difference.lifting.recognitionengine.ruleapplication;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sidiff.difference.symmetric.AddObject;
import org.sidiff.difference.symmetric.AddReference;
import org.sidiff.difference.symmetric.AttributeValueChange;
import org.sidiff.difference.symmetric.Change;
import org.sidiff.difference.symmetric.RemoveObject;
import org.sidiff.difference.symmetric.RemoveReference;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.difference.symmetric.SymmetricDifference;

/**
 * Can be used in the lifting pipeline after post-processing to aggregate
 * created/deleted subtrees into one semantic change set (background: (up to
 * now, atomic edit rules only create/delete "empty" objects, i.e. the
 * corresponding recognition rules will never detect created/deleted subtrees
 * except the ones with mandatory children .
 * 
 * <br>
 * Please note that the aggregated semantic change sets, however, will not have
 * a corresponding edit rule. Thus, the SubtreeAggregator shall not be used when
 * dependencies between change sets must be computed or the difference is
 * intended to serve as edit script.
 * 
 * @author kehrer
 */
public class SubtreeAggregator {

	/**
	 * Constant that identifies CREATE operations
	 */
	public static final int CREATE = 1;

	/**
	 * Constant that identifies DELETE operations
	 */
	public static final int DELETE = 2;

	/**
	 * The lifted difference on which we want to perform the aggregation.
	 */
	private SymmetricDifference difference;

	/**
	 * Operation types for which we want to perform subtree aggregation
	 */
	private int[] operationTypes = null;

	public void aggregate(SymmetricDifference d, int... operationTypes) {
		// Basic assertions
		assert (d != null);
		for (int i = 0; i < operationTypes.length; i++) {
			assert (operationTypes[i] == CREATE || operationTypes[i] == DELETE);
		}

		// Init
		this.difference = d;
		this.operationTypes = operationTypes;

		// Copy change sets for iteration
		List<SemanticChangeSet> changeSetsCopy = new ArrayList<SemanticChangeSet>();
		changeSetsCopy.addAll(difference.getChangeSets());

		// iterate ...
		for (SemanticChangeSet cs : changeSetsCopy) {
			aggregate(cs);
		}
	}

	private void aggregate(SemanticChangeSet cs) {
		for (int i = 0; i < operationTypes.length; i++) {
			EObject subtreeRoot = representsSubtreeOperation(cs, operationTypes[i]);
			if (subtreeRoot != null) {
				// do aggregate
				aggregate(cs, subtreeRoot, operationTypes[i]);
				return;
			}
		}
	}

	private void aggregate(SemanticChangeSet cs, EObject subtreeRoot, int operationType) {
		if (subtreeRoot.eContainer() == null) {
			// no parent -> nothing to aggregate
			return;
		} else {
			SemanticChangeSet parentCS = findChangeSet(subtreeRoot.eContainer(), operationType);
			if (parentCS == null) {
				// parent not created/deleted -> nothing to aggregate
				return;
			} else {
				merge(parentCS, cs);
			}
		}
	}

	/**
	 * Utility method that checks whether the given change set cs represents the
	 * creation/deletion of a subtree. We have two conditions for that:
	 * <nl>
	 * <li>(1) cs contains only creation/deletion actions</li>
	 * <li>(2) the objects which are created/deleted form indeed a (sub-)tree</li>
	 * </nl>
	 * 
	 * If its is a subtree creation/deletion, we return the root of this
	 * subtree. Otherwise we return null.
	 * 
	 * @param cs
	 * @param operationType
	 * @return
	 */
	private EObject representsSubtreeOperation(SemanticChangeSet cs, int operationType) {
		assert (operationType == CREATE || operationType == DELETE);

		// Condition (1)
		if (operationType == CREATE) {
			for (Change c : cs.getChanges()) {
				if ((c instanceof RemoveObject) || (c instanceof RemoveReference)
						|| (c instanceof AttributeValueChange)) {
					return null;
				}
			}
		}
		if (operationType == DELETE) {
			for (Change c : cs.getChanges()) {
				if ((c instanceof AddObject) || (c instanceof AddReference) || (c instanceof AttributeValueChange)) {
					return null;
				}
			}
		}

		// Condition (2)
		List<EObject> objs = null;
		if (operationType == CREATE) {
			objs = getCreatedObjects(cs);
		}
		if (operationType == DELETE) {
			objs = getDeletedObjects(cs);
		}

		List<EObject> subtreeRoots = EcoreUtil.filterDescendants(objs);
		if (subtreeRoots.size() != 1) {
			return null;
		}

		return subtreeRoots.get(0);
	}

	/**
	 * Utility method that merges two change sets. Child will be merged into
	 * parent.
	 * 
	 * @param parent
	 * @param child
	 */
	private void merge(SemanticChangeSet parent, SemanticChangeSet child) {
		// mark parent cs as merged
		parent.setName(parent.getName() + "_recursive");

		// merge child changes
		parent.getChanges().addAll(child.getChanges());

		// finally delete child cs from changeSets
		difference.getChangeSets().remove(child);

		// Finally we add child to unusedChangeSets
		difference.getUnusedChangeSets().add(child);

	}

	/**
	 * Utility method that finds the change set which creates/deletes the
	 * EObject obj, depending on the value of parameter operationType.<br/>
	 * If obj is not created/deleted at all, then null is returned.
	 * 
	 * @param obj
	 * @return
	 */
	private SemanticChangeSet findChangeSet(EObject obj, int operationType) {
		assert (operationType == CREATE || operationType == DELETE);

		for (SemanticChangeSet cs : difference.getChangeSets()) {
			if ((operationType == CREATE) && getCreatedObjects(cs).contains(obj)) {
				return cs;
			}
			if ((operationType == DELETE) && getDeletedObjects(cs).contains(obj)) {
				return cs;
			}
		}

		return null;
	}

	/**
	 * Utility method that return the objects that are created within change set
	 * cs.
	 * 
	 * @param cs
	 * @return
	 */
	private List<EObject> getCreatedObjects(SemanticChangeSet cs) {
		List<EObject> res = new LinkedList<EObject>();
		for (Change c : cs.getChanges()) {
			if (c instanceof AddObject) {
				res.add(((AddObject) c).getObj());
			}
		}

		return res;
	}

	/**
	 * Utility method that return the objects that are deleted within change set
	 * cs.
	 * 
	 * @param cs
	 * @return
	 */
	private List<EObject> getDeletedObjects(SemanticChangeSet cs) {
		List<EObject> res = new LinkedList<EObject>();
		for (Change c : cs.getChanges()) {
			if (c instanceof RemoveObject) {
				res.add(((RemoveObject) c).getObj());
			}
		}

		return res;
	}
}
