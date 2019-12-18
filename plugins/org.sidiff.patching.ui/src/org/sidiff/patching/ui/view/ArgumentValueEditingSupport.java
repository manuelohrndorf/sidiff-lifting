package org.sidiff.patching.ui.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.ui.provider.PropertyDescriptor.EDataTypeCellEditor;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.sidiff.common.emf.ecore.NameUtil;
import org.sidiff.common.ui.util.Exceptions;
import org.sidiff.common.ui.util.UIUtil;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.ParameterBinding;
import org.sidiff.difference.asymmetric.ValueParameterBinding;
import org.sidiff.patching.arguments.IArgumentManager;
import org.sidiff.patching.arguments.ObjectArgumentWrapper;
import org.sidiff.patching.operation.OperationInvocationStatus;
import org.sidiff.patching.operation.OperationInvocationWrapper;

public class ArgumentValueEditingSupport extends EditingSupport {

	private List<CellObject> itemObjects;
	private IArgumentManager argumentManager;
	private OperationInvocationWrapper operationInvocationWrapper;
	private List<IValueChangedListener> listeners;

	private boolean showReliabilities = false;
	private boolean showQualifiedArgumentNames = false;

	public ArgumentValueEditingSupport(ColumnViewer viewer) {
		super(viewer);
		listeners = new ArrayList<>();

		Exceptions.log(() -> {
			OperationExplorerView operationExplorerView = UIUtil.showView(OperationExplorerView.class, OperationExplorerView.ID);
			listeners.add(operationExplorerView);
			return Status.OK_STATUS;
		});
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		if(element instanceof ObjectParameterBinding){
			ObjectParameterBinding binding = (ObjectParameterBinding) element;

			this.itemObjects = new ArrayList<>();

			Map<Resource, Collection<EObject>> potentialArgs = argumentManager.getPotentialArguments(binding);
			int resourceCategory = 0;
			for(Map.Entry<Resource, Collection<EObject>> args : potentialArgs.entrySet()) {
				if(args.getValue().isEmpty()) {
					continue;
				}

				CellObject cellObject = new CellObject(resourceCategory, " - " + args.getKey().getURI().toString() + " - ");
				itemObjects.add(cellObject);
				resourceCategory++;
				for(Object object : args.getValue()) {
					float reliability = argumentManager.getReliability(binding, (EObject)object);
					cellObject = new CellObject(resourceCategory, reliability, object);
					itemObjects.add(cellObject);
				}
				resourceCategory++;
			}
			Collections.sort(itemObjects);
			String[] items = getItemsStringArray(itemObjects);
			return new ComboBoxCellEditor(((TableViewer) getViewer()).getTable(), items, SWT.READ_ONLY);
		} else if (element instanceof ValueParameterBinding) {
			ValueParameterBinding binding = (ValueParameterBinding) element;
			return new EDataTypeCellEditor(
					(EDataType)binding.getFormalParameter().getType(),
					((TableViewer)getViewer()).getTable());
		}
		return null;
	}

	private static String[] getItemsStringArray(List<CellObject> itemObjects) {
		String[] items = new String[itemObjects.size()];
		for (int i = 0; i < items.length; i++) {
			items[i] = itemObjects.get(i).toString();
		}
		return items;
	}

	@Override
	protected boolean canEdit(Object element) {
		//Only editable if not applied beforehand
		if (element instanceof ParameterBinding){			
			if (operationInvocationWrapper.getStatus() == OperationInvocationStatus.PASSED) {
				return false;
			}
			if (element instanceof ObjectParameterBinding) {
//				ObjectParameterBinding binding = (ObjectParameterBinding) element;
//				return !binding.isMappingTarget();
				return true;
			}
			else if (element instanceof ValueParameterBinding){
				return true;
			}
		}
		return false;
	}

	@Override
	protected Object getValue(Object element) {
		if(element instanceof ObjectParameterBinding) {
			ObjectArgumentWrapper wrapper = (ObjectArgumentWrapper)argumentManager.getArgument((ObjectParameterBinding)element);
			return IntStream.range(0, itemObjects.size())
					.filter(i -> itemObjects.get(i).getObject() == wrapper.getTargetObject())
					.findFirst().orElse(1); // select first object as default if none selected
		} else if(element instanceof ValueParameterBinding) {
			ValueParameterBinding binding = (ValueParameterBinding) element;
			return EcoreUtil.createFromString((EDataType)binding.getFormalParameter().getType(), binding.getActual());
		}
		return null;
	}

	@Override
	protected void setValue(Object element, Object value) {
		if(element instanceof ObjectParameterBinding) {
			int index = ((Integer) value).intValue(); // value of ComboBoxCellEditor is selection index
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
		} else if(element instanceof ValueParameterBinding) {
			ValueParameterBinding binding = (ValueParameterBinding) element;
			String stringValue = EcoreUtil.convertToString((EDataType)binding.getFormalParameter().getType(), value);
			binding.setActual(stringValue);
			argumentManager.setArgument(binding, stringValue);
		}
		for(IValueChangedListener listener : listeners){
			listener.valueChanged();
		}
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
		listeners.add(listener);
	}
	
	public interface IValueChangedListener {
		public void valueChanged();
	}
	
	private class CellObject implements Comparable<CellObject> {
		
		private Object object;
		private int resourceCategory;
		private String name;
		private float reliability;
		
		public CellObject(int resourceCategory, Object object) {
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
			this.name = NameUtil.getName((EObject)object);
		}

		public Object getObject() {
			return object;
		}

		@Override
		public int compareTo(CellObject cellObject) {
			int compare = Integer.compare(resourceCategory, cellObject.resourceCategory);
			if(compare != 0){
				return compare;
			}
			compare = Float.compare(cellObject.reliability, reliability);
			if (compare != 0) {
				return compare;
			}
			return name.compareTo(cellObject.name);
		}
		
		@Override
		public String toString() {
			if(object instanceof EObject){
				EObject eObject = (EObject)object;
				return (showQualifiedArgumentNames? NameUtil.getQualifiedArgumentName(eObject):name)
						+ (showReliabilities?" (" + reliability + ")":"");
			}
			return name;
		}
	}
	
	public void setShowReliablities(boolean b) {
		showReliabilities = b;
	}
	
	public boolean isShowQualifiedArgumentName() {
		return showQualifiedArgumentNames;
	}

	public void setShowQualifiedArgumentName(boolean showQualifiedArgumentName) {
		this.showQualifiedArgumentNames = showQualifiedArgumentName;
	}
}
