package org.sidiff.integration.editor.highlighting.adapter.difference;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.sidiff.difference.symmetric.AddObject;
import org.sidiff.difference.symmetric.AddReference;
import org.sidiff.difference.symmetric.AttributeValueChange;
import org.sidiff.difference.symmetric.Change;
import org.sidiff.difference.symmetric.RemoveObject;
import org.sidiff.difference.symmetric.RemoveReference;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.integration.editor.highlighting.ISelectionHighlightingAdapter;
import org.sidiff.integration.editor.highlighting.StyledObject;
import org.sidiff.matching.model.Correspondence;
import org.sidiff.matching.model.Matching;

/**
 * 
 * @author Robert Müller, cpietsch
 */
public class DifferenceSelectionHighlightingAdapter implements ISelectionHighlightingAdapter {

	protected static final Color CHANGE = new Color(Display.getCurrent(), 0, 128, 255);
	protected static final Color REMOVE = new Color(Display.getCurrent(), 204, 0, 0);
	protected static final Color ADD = new Color(Display.getCurrent(), 0, 204, 00);

	@Override
	public Stream<StyledObject> getElements(ISelection selection) {
		Set<StyledObject> result = new HashSet<>();

		Object selectedObject = ISelectionHighlightingAdapter.getFirstElement(selection);

		if (selectedObject instanceof SemanticChangeSet) {
			collectSemanticChangeSetElements((SemanticChangeSet) selectedObject, result);
		} else if (selectedObject instanceof Change) {
			collectChangeElements((Change) selectedObject, result);
		}
		if (selectedObject instanceof Matching) {
			collectMatchingElements((Matching) selectedObject, result);
		} else if (selectedObject instanceof Correspondence) {
			collectCorrespondenceElements((Correspondence) selectedObject, result);
		}

		return result.stream();
	}

	protected void collectSemanticChangeSetElements(SemanticChangeSet changeSet, Set<StyledObject> result) {
		for (Change change : changeSet.getChanges()) {
			collectChangeElements(change, result);
		}
	}

	protected void collectChangeElements(Change change, Set<StyledObject> result) {
		SymmetricDifference symmetric = (SymmetricDifference) change.eContainer();

		if (change instanceof AddObject) {
			highlight(((AddObject) change).getObj(), ADD, result);
		} else if (change instanceof RemoveObject) {
			highlight(((RemoveObject) change).getObj(), REMOVE, result);
		} else if (change instanceof AddReference) {
			AddReference addRef = (AddReference) change;
			highlight(addRef.getSrc(), CHANGE, result);
			highlight(addRef.getTgt(), CHANGE, result);
			highlight(symmetric.getCorrespondingObjectInA(addRef.getSrc()), CHANGE, result);
			highlight(symmetric.getCorrespondingObjectInA(addRef.getTgt()), CHANGE, result);
		} else if (change instanceof RemoveReference) {
			RemoveReference remRef = (RemoveReference) change;
			highlight(remRef.getSrc(), CHANGE, result);
			highlight(remRef.getTgt(), CHANGE, result);
			highlight(symmetric.getCorrespondingObjectInB(remRef.getSrc()), CHANGE, result);
			highlight(symmetric.getCorrespondingObjectInB(remRef.getTgt()), CHANGE, result);
		} else if (change instanceof AttributeValueChange) {
			AttributeValueChange attrChange = (AttributeValueChange) change;
			highlight(attrChange.getObjA(), CHANGE, result);
			highlight(attrChange.getObjB(), CHANGE, result);
		}
	}

	protected void collectMatchingElements(Matching matching, Set<StyledObject> result) {
		for (Correspondence correspondence : matching.getCorrespondences()) {
			collectCorrespondenceElements(correspondence, result);
		}
	}

	protected void collectCorrespondenceElements(Correspondence correspondence, Set<StyledObject> result) {
		highlight(correspondence.getMatchedA(), CHANGE, result);
		highlight(correspondence.getMatchedB(), CHANGE, result);
	}

	protected void highlight(EObject eObject, Color color, Set<StyledObject> result) {
		if (eObject != null) {
			result.add(new StyledObject(eObject).setColor(color));
		}
	}

	/**
	 * Internal method to dispose the shared system resources when the bundle is
	 * stopped.
	 */
	public static void internalDispose() {
		CHANGE.dispose();
		REMOVE.dispose();
		ADD.dispose();
	}
}
