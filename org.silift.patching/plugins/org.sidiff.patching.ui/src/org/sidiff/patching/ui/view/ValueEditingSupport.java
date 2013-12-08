package org.sidiff.patching.ui.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.patching.IPatchCorrespondence;

public class ValueEditingSupport extends EditingSupport {
	private List<CellObject> itemObjects;
	private IPatchCorrespondence correspondence;
	private IValueChangedListener listener;

	public ValueEditingSupport(ColumnViewer viewer) {
		super(viewer);
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		ObjectParameterBinding substitution = (ObjectParameterBinding) element;

		this.itemObjects = new ArrayList<CellObject>();
		EObject currentObject = substitution.getActualA();

		
		Map<Resource, Collection<EObject>> potentialArgs = correspondence.getPotentialArguments(currentObject);
		Collection<EObject> args = new ArrayList<EObject>();
		for (Resource r : potentialArgs.keySet()) {
			args.addAll(potentialArgs.get(r));			
		}
		//TODO: Categorize potential args by their resource in the UI.
		for (EObject eObject : args) {
			float reliability = correspondence.getReliability(currentObject, eObject);
			CellObject cellObject = new CellObject(reliability, eObject);
			itemObjects.add(cellObject);
		}
		
		Collections.sort(itemObjects);
		String[] items = getItemsStringArray();
		return new ComboBoxCellEditor(((TreeViewer) getViewer()).getTree(), items, SWT.READ_ONLY);
	}

	private String[] getItemsStringArray() {
		String[] items = new String[itemObjects.size()];
		for (int i = 0; i < items.length; i++) {
			items[i] = itemObjects.get(i).toString();
		}
		return items;
	}

	@Override
	protected boolean canEdit(Object element) {
		if (element instanceof ObjectParameterBinding) {
			ObjectParameterBinding objectParameterBinding = (ObjectParameterBinding) element;
			EObject eObject = objectParameterBinding.getActualA();
			if (eObject != null) {
				return eObject.eResource() == correspondence.getOriginModel();
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	protected Object getValue(Object element) {
		return itemObjects.indexOf(element);
	}

	@Override
	protected void setValue(Object element, Object value) {
		if (element instanceof ObjectParameterBinding) {
			int index = ((Integer) value).intValue();
			ObjectParameterBinding substitution = (ObjectParameterBinding) element;
			if (index == -1) {
				correspondence.removeCorrespondence(substitution.getActualA());
			} else {
				EObject elementB = itemObjects.get(index).getEObject();
				correspondence.addCorrespondence(substitution.getActualA(), elementB);
			}
		}
		getViewer().refresh();
		this.listener.valueChanged();
	}

	public void setCorrespondence(IPatchCorrespondence correspondence) {
		this.correspondence = correspondence;
	}
	
	public void setListener(IValueChangedListener listener) {
		this.listener = listener;
	}
	
	public interface IValueChangedListener {
		public void valueChanged();
	}
	
	private class CellObject implements Comparable<CellObject> {
		private float reliability;
		private EObject eObject;

		public CellObject(float reliability, EObject eObject) {
			super();
			this.reliability = reliability;
			this.eObject = eObject;
		}

		public EObject getEObject() {
			return eObject;
		}

		@Override
		public int compareTo(CellObject cellObject) {
			int compare = Float.compare(cellObject.reliability, reliability);
			if (compare != 0) {
				return compare;
			} else {
				return Util.getName(eObject).compareTo(Util.getName(cellObject.getEObject()));
			}
		}
		
		@Override
		public String toString() {
			return Util.getName(eObject) + " (" + reliability + ")";
		}
	}

}
