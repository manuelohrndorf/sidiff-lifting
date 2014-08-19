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

public class OutputParameterSection extends AbstractPropertySection implements IValueChangedListener {

	private OperationInvocationWrapper operationInvocationWrapper;
	private TableViewer outputArgumentsViewer;
	private ArgumentValueLabelProvider argumentValueLabelProvider;
	private ArgumentValueEditingSupport editingSupport;
	private Composite parent;

//	private ModifyListener listener = new ModifyListener() {
//	    @Override
//        public void modifyText(ModifyEvent arg0) {  
//        }
//    };
	
	@Override
	public void setInput(IWorkbenchPart part, ISelection selection) {
		super.setInput(part, selection);
		Assert.isTrue(selection instanceof IStructuredSelection);
		Object input = ((IStructuredSelection) selection).getFirstElement();
		Assert.isTrue(input instanceof OperationInvocationWrapper);
		this.operationInvocationWrapper = (OperationInvocationWrapper) input;
		this.argumentValueLabelProvider.init(operationInvocationWrapper);
		this.editingSupport.setOperationInvocationWrapper(operationInvocationWrapper);
		this.outputArgumentsViewer.setInput(operationInvocationWrapper.getOperationInvocation().getOutParameterBindings());
		
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
		FormData data;
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		data.top = new FormAttachment(0, ITabbedPropertyConstants.VSPACE);

		this.outputArgumentsViewer = new TableViewer(composite, SWT.FILL);
		this.outputArgumentsViewer.setContentProvider(new ArrayContentProvider());
		this.outputArgumentsViewer.getTable().setHeaderVisible(true);
		this.outputArgumentsViewer.getTable().setLinesVisible(true);
		this.outputArgumentsViewer.getTable().setLayoutData(data);
		this.argumentValueLabelProvider = new ArgumentValueLabelProvider();
		this.editingSupport = new ArgumentValueEditingSupport(outputArgumentsViewer);
		this.editingSupport.setListener(this);
		createColumns();
		
	}
	
	// This will create the columns for the table
	private void createColumns() {
		String[] titles = { "Name", "Value"};
		int[] bounds = { 150, 300};

		// the status
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider(){
			@Override
			public String getText(Object element) {
				ParameterBinding obj = (ParameterBinding) element;
				return obj.getFormalName();
			}
		});
		
		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(argumentValueLabelProvider);

	}

	private TableViewerColumn createTableViewerColumn(String title, int bound, final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(outputArgumentsViewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(false);
		
		// EditingSupport
		if(colNumber == 1)
			viewerColumn.setEditingSupport(editingSupport);
		
		return viewerColumn;
	}


	@Override
	public void valueChanged() {
		this.refresh();
	}
	
	public void showQualifiedArgumentName(boolean b){
		this.argumentValueLabelProvider.setShowQualifiedArgumentName(b);
	}
	
	public void showReliability(boolean b){
		this.argumentValueLabelProvider.setShowReliablities(b);
	}

}
