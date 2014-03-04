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
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.ParameterBinding;
import org.sidiff.difference.asymmetric.ValueParameterBinding;
import org.sidiff.patching.arguments.IArgumentManager;
import org.sidiff.patching.operation.OperationInvocationStatus;
import org.sidiff.patching.operation.OperationInvocationWrapper;
import org.sidiff.patching.operation.OperationManager;

public class ArgumentValueEditingSupport extends EditingSupport {
	private List<CellObject> itemObjects;
	private List<String> itemStrings;
	private IArgumentManager argumentManager;
	private OperationInvocationWrapper operationInvocationWrapper;
	private IValueChangedListener listener;

	public ArgumentValueEditingSupport(ColumnViewer viewer) {
		super(viewer);
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		if(element instanceof ObjectParameterBinding){
			ObjectParameterBinding binding = (ObjectParameterBinding) element;

			this.itemObjects = new ArrayList<CellObject>();

			Map<Resource, Collection<EObject>> potentialArgs = argumentManager.getPotentialArguments(binding);
			//Collection<Object> args = new ArrayList<Object>();
			int resourceCategory = 0;
			for (Resource r : potentialArgs.keySet()) {
				if(!potentialArgs.get(r).isEmpty()){
					ArrayList<EObject> resource = (ArrayList<EObject>) potentialArgs.get(r);
					CellObject cellObject = new CellObject(resourceCategory, " - " + resource.get(0).eResource().getURI().toString() + " - ");
					itemObjects.add(cellObject);
					resourceCategory++;
					for(Object object : potentialArgs.get(r)){
						float reliability = argumentManager.getReliability(binding, (EObject)object);
						cellObject = new CellObject(resourceCategory, reliability, object);
						itemObjects.add(cellObject);
					}
					resourceCategory++;
				}
			}
			Collections.sort(itemObjects);
			String[] items = getItemsStringArray();
			return new ComboBoxCellEditor(((TableViewer) getViewer()).getTable(), items, SWT.READ_ONLY);
		}
		else if (element instanceof ValueParameterBinding){
			ValueParameterBinding binding = (ValueParameterBinding) element;
			if((binding.getFormalParameter().getType().getInstanceClass() == Boolean.class)
					|| (binding.getFormalParameter().getType().getInstanceClass() == boolean.class)){

				this.itemStrings = new ArrayList<String>();
				this.itemStrings.add("true");
				this.itemStrings.add("false");
				String[] items = new String[]{"true","false"};	
				
				return new ComboBoxCellEditor(((TableViewer) getViewer()).getTable(), items, SWT.READ_ONLY);
			}
			else{
				return new TextCellEditor(((TableViewer) getViewer()).getTable());
			}
		}
		return null;
	}

	private String[] getItemsStringArray() {
		String[] items = new String[itemObjects.size()];
		for (int i = 0; i < items.length; i++) {
			items[i] = itemObjects.get(i).toString();
		}
		return items;
	}

	@Override
	protected void initializeCellEditorValue(CellEditor cellEditor, ViewerCell cell) {
		if (cellEditor instanceof ComboBoxCellEditor)
			cellEditor.setValue(1);
	}
	
	@Override
	protected boolean canEdit(Object element) {
		//Only editable if not applied beforehand
		if (element instanceof ParameterBinding){
			ParameterBinding parBinding = (ParameterBinding) element;
			if (operationInvocationWrapper.getStatus() == OperationInvocationStatus.PASSED) {
				return false;
			}
			if (element instanceof ObjectParameterBinding) {
				ObjectParameterBinding binding = (ObjectParameterBinding) element;
				return !binding.isMappingTarget();
			}
			else if (element instanceof ValueParameterBinding){
				return true;
			}
		}
		return false;
	}

	@Override
	protected Object getValue(Object element) {
	
		if(element instanceof ObjectParameterBinding)
			return itemObjects.indexOf(element);
		else if(element instanceof ValueParameterBinding){
			ValueParameterBinding binding = (ValueParameterBinding) element;
			if((binding.getFormalParameter().getType().getInstanceClass() == Boolean.class)
					|| (binding.getFormalParameter().getType().getInstanceClass() == boolean.class)){
				return itemStrings.indexOf(binding.getActual());
			}
			return binding.getActual();
		}
		return null;
	}

	@Override
	protected void setValue(Object element, Object value) {
		if (element instanceof ObjectParameterBinding) {
			int index = ((Integer) value).intValue();
			ObjectParameterBinding binding = (ObjectParameterBinding) element;
			if (index == -1) {
				argumentManager.resetArgumentResolution(binding);
			} else {
				Object object = itemObjects.get(index).getObject();
				if(object instanceof EObject){
					EObject elementB = (EObject)object;
					argumentManager.addArgumentResolution(binding, elementB);
				}
			}
		}
		else if(element instanceof ValueParameterBinding){
			ValueParameterBinding binding = (ValueParameterBinding) element;
			if((binding.getFormalParameter().getType().getInstanceClass() == Boolean.class)
					|| (binding.getFormalParameter().getType().getInstanceClass() == boolean.class)){
				int index = ((Integer) value).intValue();
				if (index != -1) {
					binding.setActual(itemStrings.get(index));
					argumentManager.setArgument(binding, itemStrings.get(index));
				}
			}
			else{
				if(!((String)value).startsWith(" - ")){
					binding.setActual((String) value);
					argumentManager.setArgument(binding, value);
				}
			}
		}
		this.listener.valueChanged();
		getViewer().refresh();
	}

	public void setArgumentManager(IArgumentManager manager) {
		this.argumentManager = manager;
	}
	
	public void setOperationInvocationWrapper(OperationInvocationWrapper operationInvocationWrapper) {
		this.operationInvocationWrapper = operationInvocationWrapper;
		this.argumentManager = operationInvocationWrapper.getArgumentManager();
	}
	
	public void setListener(IValueChangedListener listener) {
		this.listener = listener;
	}
	
	public interface IValueChangedListener {
		public void valueChanged();
	}
	
	private class CellObject implements Comparable<CellObject> {
		
		private Object object;
		private int resourceCategory;
		private String name;
		private float reliability;
		
		public CellObject(int resourceCategory, Object object){
			this.resourceCategory = resourceCategory;
			this.reliability = 0.f;
			this.object = object;
			this.name = object.toString();
		}
		public CellObject(int resourceCategory, float reliability, Object object) {
			super();
			this.resourceCategory = resourceCategory;
			this.reliability = reliability;
			this.object = object;
			this.name = Util.getName((EObject)object);
		}

		public Object getObject() {
			return object;
		}

		@Override
		public int compareTo(CellObject cellObject) {
			int compare = Integer.compare(resourceCategory, cellObject.resourceCategory);
			if(compare != 0){
				return compare;
			}else{
				compare = Float.compare(cellObject.reliability, reliability);
				if (compare != 0) {
					return compare;
				} else {
					return name.compareTo(cellObject.name);
				}
			}
		}
		
		@Override
		public String toString() {
			return (object instanceof EObject)? name + " (" + reliability + ")" : name;
		}
	}
	
}
