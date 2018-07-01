package org.sidiff.vcmsintegration.contentview;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.symmetric.AddObject;
import org.sidiff.difference.symmetric.AttributeValueChange;
import org.sidiff.difference.symmetric.Change;
import org.sidiff.difference.symmetric.RemoveObject;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.patching.operation.OperationInvocationWrapper;

/**
 * 
 * @author Jonas Schmeck, Robert Müller
 *
 */
class SiLiftContentMergeViewerLabelProvider extends AdapterFactoryLabelProvider.ColorProvider implements ISelectionChangedListener {

	private final Color COLOR_ATTRIBUTE_VALUE_CHANGED = new Color(Display.getCurrent(), 255, 0, 127);
	private final Color COLOR_REMOVE_OBJECT = new Color(Display.getCurrent(), 190, 10, 10);
	private final Color COLOR_ADD_OBJECT = new Color(Display.getCurrent(), 10, 190, 10);

	private final TreeViewer treeViewer;
	private final Map<Object, Color> objectColors;
	private final ISelectionProvider selectionProvider;
	private final Set<Object> changedObjects;

	public SiLiftContentMergeViewerLabelProvider(AdapterFactory adapterFactory, TreeViewer viewer, ISelectionProvider selectionProvider) {
		super(adapterFactory, viewer);
		this.treeViewer = viewer;
		this.objectColors = new HashMap<>();
		this.changedObjects = new HashSet<>();

		this.selectionProvider = selectionProvider;
		this.selectionProvider.addSelectionChangedListener(this);
	}

	/**
	 * Is triggered whenever a new selection is made in the associated
	 * {@link org.sidiff.vcmsintegration.structureview.SiLiftStructureMergeViewer};
	 * Calls methods responsible for highlighting items concerning the
	 * selected difference.
	 * 
	 * @param event The triggered event containing the selection
	 * 
	 */
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		if(!(event.getSelection() instanceof IStructuredSelection)) return;
		Object element = ((IStructuredSelection)event.getSelection()).getFirstElement();

		// clear old colors
		changedObjects.addAll(objectColors.keySet());
		objectColors.clear();

		if (element instanceof Change) {
			highlightChange((Change)element);
		} else if (element instanceof SemanticChangeSet) {
			highlightChangeSet((SemanticChangeSet)element);
		} else if (element instanceof OperationInvocation) {
			highlightOperationInvocation(element);
		} else if (element instanceof OperationInvocationWrapper) {
			highlightOperationInvocation(((OperationInvocationWrapper)element).getOperationInvocation());
		}

		// refresh all changed objects
		treeViewer.update(changedObjects.toArray(), null);
		changedObjects.clear();
	}

	private void highlightOperationInvocation(Object element) {
		OperationInvocation inv = (OperationInvocation)element;
		highlightChangeSet(inv.getChangeSet());
		List<OperationInvocation> pre = inv.getAllPredecessors();
		if (pre != null) {
			for (OperationInvocation opInv : pre) {
				highlightChangeSet(opInv.getChangeSet());
			}
		}
	}

	/**
	 * This method highlights all changes in the given ChangeSet
	 * @param cs The ChangeSet
	 */
	private void highlightChangeSet(SemanticChangeSet cs) {
		for (Change c : cs.getChanges()) {
			highlightChange(c);
		}
	}

	private void highlightChange(Change change) {
		if (change instanceof AttributeValueChange) {
			applyHighlight(((AttributeValueChange)change).getObjA(), COLOR_ATTRIBUTE_VALUE_CHANGED);
			applyHighlight(((AttributeValueChange)change).getObjB(), COLOR_ATTRIBUTE_VALUE_CHANGED);
		}
		else if (change instanceof RemoveObject) {
			applyHighlight(((RemoveObject)change).getObj(), COLOR_REMOVE_OBJECT);
		}
		else if (change instanceof AddObject) {
			applyHighlight(((AddObject)change).getObj(), COLOR_ADD_OBJECT);
		}
		// else
		// either instanceof 'RemoveReference' or 'AddReference'
		// we don't want to highlight these
	}

	private void applyHighlight(EObject eObject, Color color) {
		Resource input = (Resource)treeViewer.getInput();
		if(input == null) 
			return;
		EObject resolvedObject = input.getEObject(EcoreUtil.getURI(eObject).fragment());
		if(resolvedObject == null)
			return;

		objectColors.put(resolvedObject, color);
		treeViewer.expandToLevel(resolvedObject, 0);
		changedObjects.add(resolvedObject);
	}

	@Override
	public Color getForeground(Object object) {
		Color color = objectColors.get(object);
		if(color != null) {
			return color;
		}
		return super.getForeground(object);
	}

	@Override
	public void dispose() {
		objectColors.clear();
		COLOR_ADD_OBJECT.dispose();
		COLOR_REMOVE_OBJECT.dispose();
		COLOR_ATTRIBUTE_VALUE_CHANGED.dispose();
		selectionProvider.removeSelectionChangedListener(this);
		super.dispose();
	}
}