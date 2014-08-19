package org.sidiff.difference.lifting.recognitionengine.ruleapplication;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sidiff.difference.symmetric.Change;
import org.sidiff.difference.symmetric.RemoveObject;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.difference.symmetric.SymmetricDifference;

/**
 * Can be used in the lifting pipeline after post-processing to aggregate deleted
 * subtrees into one semantic change set (background: (up to now, atomic edit
 * rules only delete "empty" objects, i.e. the corresponding recognition rules
 * will never detect deleted subtrees except the ones with mandatory children .
 * 
 * <br>
 * Please note that the aggregated semantic change sets, however, will not have
 * a corresponding edit rule. Thus, the DeletedSubtreeAggregator shall not be
 * used when dependencies between change sets must be computed or the difference
 * is intended to serve as patch.
 * 
 * 
 * 
 * @author kehrer
 */
public class DeletedSubtreeAggregator {

	private SymmetricDifference difference;

	public void aggregate(SymmetricDifference d) {
		this.difference = d;

		// Copy change sets for iteration
		List<SemanticChangeSet> changeSetsCopy = new ArrayList<SemanticChangeSet>();
		changeSetsCopy.addAll(difference.getChangeSets());

		// iterate ...
		for (SemanticChangeSet cs : changeSetsCopy) {
			aggregate(cs);
		}
	}

	private void aggregate(SemanticChangeSet cs) {
		List<EObject> delObjs = getDeletedObjects(cs);

		if (delObjs.size() == 0) {
			// no delObjs -> nothing to do
		} else if (delObjs.size() == 1) {
			// one deleted object
			aggregate(cs, delObjs.get(0));
		} else {
			// multiple deleted objects
			List<EObject> subtreeRoots = EcoreUtil.filterDescendants(delObjs);
			if (subtreeRoots.size() == 1) {
				// del Objs form a single subtree -> get root of subtree and
				// aggregate
				aggregate(cs, subtreeRoots.get(0));
			} else {
				assert (false) : "cannot aggregate deleted subtree, because it is not clear which parent change set to aggregate (This should never happen)";								
			}
		}
	}

	private void aggregate(SemanticChangeSet cs, EObject obj) {
		if (obj.eContainer() == null) {
			// no parent -> nothing to aggregate
			return;
		} else {
			SemanticChangeSet parentDelete_cs = getDelete_CS(obj.eContainer());
			if (parentDelete_cs == null) {
				// parent not deleted -> nothing to aggregate
				return;
			} else {
				merge(parentDelete_cs, cs);
			}
		}
	}

	private void merge(SemanticChangeSet parent, SemanticChangeSet child) {
		// mark parent cs as merged
		parent.setName(parent.getName() + "_recursive");

		// merge child changes
		parent.getChanges().addAll(child.getChanges());

		// finally delete child cs from changeSets and add it to unusedChangeSets
		difference.getChangeSets().remove(child);
	}

	private SemanticChangeSet getDelete_CS(EObject obj) {
		for (SemanticChangeSet cs : difference.getChangeSets()) {
			if (getDeletedObjects(cs).contains(obj)) {
				return cs;
			}
		}

		return null;
	}

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
