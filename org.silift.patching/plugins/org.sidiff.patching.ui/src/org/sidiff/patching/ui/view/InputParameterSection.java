package org.sidiff.patching.ui.view;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.ParameterBinding;
import org.sidiff.difference.asymmetric.ValueParameterBinding;
import org.sidiff.patching.arguments.ObjectArgumentWrapper;
import org.sidiff.patching.operation.OperationInvocationStatus;
import org.sidiff.patching.operation.OperationInvocationWrapper;
import org.sidiff.patching.ui.view.ArgumentValueEditingSupport.IValueChangedListener;

public class InputParameterSection extends AbstractPropertySection implements IValueChangedListener {

	private OperationInvocationWrapper operationInvocationWrapper;
	private TableViewer inputArgumentsViewer;
	private ArgumentValueLabelProvider argumentValueLabelProvider;
	private ArgumentValueEditingSupport editingSupport;

	private ModifyListener listener = new ModifyListener() {
	    @Override
        public void modifyText(ModifyEvent arg0) {  
        }
    };
	
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
//        inputArgumentsText.removeModifyListener(listener);
//        OperationInvocationWrapperPropertySource properties = (OperationInvocationWrapperPropertySource)Platform.getAdapterManager().getAdapter(operationInvocationWrapper, IPropertySource.class);
//        inputArgumentsText.setText(properties.getPropertyValue("status").toString());
//        inputArgumentsText.addModifyListener(listener);
  
    }
	
	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage);
		Composite composite = getWidgetFactory().createFlatFormComposite(parent);
		FormData data;
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		data.top = new FormAttachment(0, ITabbedPropertyConstants.VSPACE);

		this.inputArgumentsViewer = new TableViewer(composite, SWT.FILL);
		this.inputArgumentsViewer.setContentProvider(new ArrayContentProvider());
		this.inputArgumentsViewer.getTable().setHeaderVisible(true);
		this.inputArgumentsViewer.getTable().setLinesVisible(true);
		this.inputArgumentsViewer.getTable().setLayoutData(data);
		this.argumentValueLabelProvider = new ArgumentValueLabelProvider();
		this.editingSupport = new ArgumentValueEditingSupport(inputArgumentsViewer);
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
		final TableViewerColumn viewerColumn = new TableViewerColumn(inputArgumentsViewer, SWT.NONE);
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

}
