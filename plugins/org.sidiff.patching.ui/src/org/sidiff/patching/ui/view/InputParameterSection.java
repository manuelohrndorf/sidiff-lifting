package org.sidiff.patching.ui.view;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.sidiff.difference.asymmetric.ParameterBinding;
import org.sidiff.patching.operation.OperationInvocationWrapper;
import org.sidiff.patching.ui.view.ArgumentValueEditingSupport.IValueChangedListener;
import org.sidiff.patching.ui.view.filter.UnchangedArgumentsFilter;

public class InputParameterSection extends AbstractPropertySection implements IValueChangedListener {

	private OperationInvocationWrapper operationInvocationWrapper;
	private TableViewer inputArgumentsViewer;
	private ArgumentValueLabelProvider argumentValueLabelProvider;
	private ArgumentValueEditingSupport editingSupport;
	private UnchangedArgumentsFilter argumentsFilter;
	private Composite parent;

	@Override
	public void setInput(IWorkbenchPart part, ISelection selection) {
		super.setInput(part, selection);
		Assert.isTrue(selection instanceof IStructuredSelection);
		Object input = ((IStructuredSelection) selection).getFirstElement();
		Assert.isTrue(input instanceof OperationInvocationWrapper);
		this.operationInvocationWrapper = (OperationInvocationWrapper) input;
		this.argumentValueLabelProvider.init(operationInvocationWrapper);
		this.editingSupport.setOperationInvocationWrapper(operationInvocationWrapper);
		this.inputArgumentsViewer.setInput(operationInvocationWrapper.getOperationInvocation().getInParameterBindings());
	}
	
	
	@Override
	public void refresh() {
		super.refresh();
		this.parent.pack();
    }
	
	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage aTabbedPropertySheetPage) {
		this.parent = parent;
		super.createControls(parent, aTabbedPropertySheetPage);
		Composite composite = getWidgetFactory().createFlatFormComposite(parent);

		FormData data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		data.top = new FormAttachment(0, ITabbedPropertyConstants.VSPACE);

		this.inputArgumentsViewer = new TableViewer(composite, SWT.FILL | SWT.FULL_SELECTION);
		this.inputArgumentsViewer.setContentProvider(new ArrayContentProvider());
		this.inputArgumentsViewer.getTable().setHeaderVisible(true);
		this.inputArgumentsViewer.getTable().setLinesVisible(true);
		this.inputArgumentsViewer.getTable().setLayoutData(data);

		this.argumentsFilter = new UnchangedArgumentsFilter();
		this.inputArgumentsViewer.addFilter(this.argumentsFilter);
		
		this.argumentValueLabelProvider = new ArgumentValueLabelProvider();
		this.editingSupport = new ArgumentValueEditingSupport(inputArgumentsViewer);
		this.editingSupport.setListener(this);
		createColumns();
		
	}

	// This will create the columns for the table
	private void createColumns() {
		TableViewerColumn nameColumn = createTableViewerColumn("Name", 150);
		nameColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				ParameterBinding obj = (ParameterBinding) element;
				return obj.getFormalName();
			}
		});

		TableViewerColumn valueColumn = createTableViewerColumn("Value", 300);
		valueColumn.setLabelProvider(argumentValueLabelProvider);
		valueColumn.setEditingSupport(editingSupport);
	}

	private TableViewerColumn createTableViewerColumn(String title, int bound) {
		TableViewerColumn viewerColumn = new TableViewerColumn(inputArgumentsViewer, SWT.NONE);
		TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(false);
		return viewerColumn;
	}


	@Override
	public void valueChanged() {
		this.refresh();
		
	}
	
	public void showQualifiedArgumentName(boolean b){
		this.argumentValueLabelProvider.setShowQualifiedArgumentName(b);
		this.editingSupport.setShowQualifiedArgumentName(b);
	}
	
	public void showReliability(boolean b){
		this.argumentValueLabelProvider.setShowReliablities(b);
		this.editingSupport.setShowReliablities(b);
	}
	
	public void showUnchangedArguments(boolean b){
		if(!b){
			this.inputArgumentsViewer.addFilter(this.argumentsFilter);			
		}
		else{
			this.inputArgumentsViewer.removeFilter(this.argumentsFilter);
		}
	}

}
