package org.sidiff.patching.ui.view;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.window.ToolTip;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.patching.PatchEngine;
import org.sidiff.patching.PatchEngine.ValidationMode;
import org.sidiff.patching.report.PatchReport;
import org.sidiff.patching.ui.Activator;
import org.sidiff.patching.ui.adapter.ModelAdapter.IModelChangeListener;
import org.sidiff.patching.ui.view.CheckBoxMouseListener.ICheckBoxListener;
import org.sidiff.patching.ui.view.ArgumentValueEditingSupport.IValueChangedListener;
import org.sidiff.patching.ui.view.filter.NullValueParameterFilter;
import org.sidiff.patching.ui.view.filter.ValueParameterFilter;
import org.sidiff.patching.util.PatchUtil;


public class PatchView extends ViewPart implements ICheckBoxListener, IModelChangeListener, IValueChangedListener {
	
	public static final String ID = "org.sidiff.patching.ui.view.PatchView";
	
	private PatchEngine engine;

	private TreeViewer patchViewer;

	private ArgumentValueEditingSupport editingSupport;
	private ArgumentValueLabelProvider valueLabelProvider;

	private PatchLabelProvider patchLabelProvider;
	
	//----------- Filter ----------------------
	private NullValueParameterFilter nullValueParameterFilter;
	private Action nullValueParameterFilterAction;
	private ValueParameterFilter valueParameterFilter;
	private Action valueParameterFilterAction;
	
	//----------- Validation -------------------
	private Action iterativeValidation;
	private Action finalValidation;
	private Action noValidation;
	private Action manualValidation;
	
	private SelectionHandler selectionHandler;


	private Action validateAction;
	
	private Button reliabilitiesButton;

	@Override
	public void createPartControl(Composite parent) {
		
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout glComposite = new GridLayout();
		composite.setLayout(glComposite);
		
		Composite editComposite = new Composite(composite, SWT.NONE);
		GridLayout glEditComposite = new GridLayout(4,false);
		editComposite.setLayout(glEditComposite);
		
		reliabilitiesButton = new Button(editComposite, SWT.CHECK);
		reliabilitiesButton.setText("Show Reliabilities");
		reliabilitiesButton.setSelection(false);
		reliabilitiesButton.addSelectionListener(new SelectionAdapter() {
		    @Override
		    public void widgetSelected(SelectionEvent e) {
		        // Handle the selection event
		        valueLabelProvider.setShowReliablities(reliabilitiesButton.getSelection());
		        patchViewer.refresh();
		    }
		}); 

		// TreeViewer
		patchViewer = new TreeViewer(composite, SWT.BORDER);
		patchViewer.setContentProvider(new PatchContentProvider());
		ColumnViewerToolTipSupport.enableFor(patchViewer, ToolTip.RECREATE);

		Tree tree = patchViewer.getTree();
		tree.setLinesVisible(true);
		tree.setHeaderVisible(true);
		tree.setLayoutData(new GridData(GridData.FILL_BOTH));

		// CheckboxListener
		tree.addMouseListener(new CheckBoxMouseListener(this));

		// Patch column
		TreeViewerColumn differenceColumn = new TreeViewerColumn(patchViewer, SWT.NONE);
		differenceColumn.getColumn().setWidth(200);
		differenceColumn.getColumn().setText("Operation");
		differenceColumn.getColumn().setResizable(true);
		// PatchLabelProvider
		patchLabelProvider = new PatchLabelProvider();
		differenceColumn.setLabelProvider(patchLabelProvider);

		// Value column
		TreeViewerColumn valueColumn = new TreeViewerColumn(patchViewer, SWT.NONE);
		valueColumn.getColumn().setWidth(200);
		valueColumn.getColumn().setText("Argument");
		valueColumn.getColumn().setResizable(true);
		// ValueLabelProvider
		valueLabelProvider = new ArgumentValueLabelProvider();
		valueColumn.setLabelProvider(valueLabelProvider);
		editingSupport = new ArgumentValueEditingSupport(patchViewer);
		editingSupport.setListener(this);
		// EditingSupport
		valueColumn.setEditingSupport(editingSupport);

		// Selectionlistener
		selectionHandler = new SelectionHandler(patchViewer);
		patchViewer.addSelectionChangedListener(selectionHandler);

		getSite().setSelectionProvider(selectionHandler);

		createActions();
		createMenus();
		createToolbar();

	}

	
	/**
	 * 
	 */
	private void createActions() {
		
		//----------- Filter ----------------------
		this.nullValueParameterFilter = new NullValueParameterFilter();
		this.nullValueParameterFilterAction = new Action("Hide NullValueParameter in Patch") {
			@Override
			public void run() {
				updatefilter(nullValueParameterFilterAction);
			}
		};
		
		patchViewer.addFilter(nullValueParameterFilter);
		nullValueParameterFilterAction.setChecked(true);

		this.valueParameterFilter = new ValueParameterFilter();
		this.valueParameterFilterAction = new Action("Hide ValueParameter in Patch") {
			@Override
			public void run() {
				updatefilter(valueParameterFilterAction);
			}
		};
		
		patchViewer.addFilter(valueParameterFilter);
		valueParameterFilterAction.setChecked(true);
		
		//----------- Validation ------------------
		this.iterativeValidation = new Action("Iterative Validation", IAction.AS_RADIO_BUTTON) {
			@Override
			public void run() {
				engine.setValidationMode(ValidationMode.ITERATIVE);
				manualValidation.setEnabled(false);
				updateViewer();
			}
			
		};
		this.iterativeValidation.setToolTipText("The model will be validated after each operation invocation.");
		this.iterativeValidation.setChecked(true);
		
		this.finalValidation = new Action("Final Validation", IAction.AS_RADIO_BUTTON){
			@Override
			public void run() {
				engine.setValidationMode(ValidationMode.FINAL);
				manualValidation.setEnabled(false);
				updateViewer();
			}
		};
		this.finalValidation.setToolTipText("The model will be validatetd after applying the selected operation invocations.");
		
		this.noValidation = new Action("No Validation", IAction.AS_RADIO_BUTTON){
			@Override
			public void run() {
				engine.setValidationMode(ValidationMode.NO);
				manualValidation.setEnabled(true);
				updateViewer();
			}
		};
		this.noValidation.setToolTipText("The model won't be validated.");
		
		this.manualValidation = new Action("Manual Validation", IAction.AS_PUSH_BUTTON){
			@Override
			public void run() {
				engine.setValidationMode(ValidationMode.MANUAL);
				updateViewer();
				engine.setValidationMode(ValidationMode.NO);
			}
		};
		this.manualValidation.setToolTipText("Validate the model.");
		this.manualValidation.setEnabled(false);
		
		
		iterativeValidation.setEnabled(false);
		finalValidation.setEnabled(false);
		noValidation.setEnabled(false);
		
		
		//----------- Check -----------------------
		//TODO to revise (cpietsch)
		this.validateAction = new Action("Check Patch") {
			@Override
			public void run() {
				PatchReport report = engine.updatePatchReport();
				ReportView reportView = (ReportView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(ReportView.ID);
				if (reportView != null) { 
					reportView.setEntries(report.getEntries());
				}
				patchViewer.refresh();
			}
		};
		this.validateAction.setToolTipText("Check Patch for valid preconditions");
		this.validateAction.setImageDescriptor(Activator.getImageDescriptor("check_preconditions.gif"));
		
	}

	
	private void updateViewer(){
		PatchReport report = engine.updatePatchReport();
		ReportView reportView = (ReportView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(ReportView.ID);
		reportView.setEntries(report.getEntries());
		patchViewer.refresh();
	}
	
	
	private void updatefilter(Action action) {
		if (action == nullValueParameterFilterAction) {
			if (action.isChecked()) {
				patchViewer.addFilter(nullValueParameterFilter);
			} else {
				patchViewer.removeFilter(nullValueParameterFilter);
			}
		} else if (action == valueParameterFilterAction) {
			if (action.isChecked()) {
				patchViewer.addFilter(valueParameterFilter);
			} else {
				patchViewer.removeFilter(valueParameterFilter);
			}
		}
	}

	private void createMenus() {
		IMenuManager rootMenuManager = getViewSite().getActionBars().getMenuManager();
		rootMenuManager.setRemoveAllWhenShown(true);
		rootMenuManager.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager mgr) {
				fillMenu(mgr);
			}
		});
		fillMenu(rootMenuManager);
	}

	private void fillMenu(IMenuManager rootMenuManager) {
		IMenuManager filterSubmenu = new MenuManager("Filters");
		rootMenuManager.add(filterSubmenu);
		filterSubmenu.add(nullValueParameterFilterAction);
		filterSubmenu.add(valueParameterFilterAction);
		IMenuManager validateModeSubmenu = new MenuManager("Validation");
		rootMenuManager.add(validateModeSubmenu);
		validateModeSubmenu.add(iterativeValidation);
		validateModeSubmenu.add(finalValidation);
		validateModeSubmenu.add(noValidation);
		validateModeSubmenu.add(new Separator());
		validateModeSubmenu.add(manualValidation);
	}

	private void createToolbar() {
		IToolBarManager toolbarManager = getViewSite().getActionBars().getToolBarManager();
		
		// toolbarManager.add(preCheckAction);
	}

	public void setPatchEngine(PatchEngine patchEngine) {
		this.engine = patchEngine;
		this.valueLabelProvider.init(engine.getArgumentManager(), engine.getPatchReport());
		this.patchLabelProvider.init(engine.getPatchReport());
		this.editingSupport.setCorrespondence(engine.getArgumentManager());
		this.patchViewer.setInput(engine.getAsymmetricDifference());
		this.patchViewer.expandAll();
		this.patchViewer.getTree().getColumns()[1].pack();
		validateAction.run();
		
		iterativeValidation.setEnabled(true);
		finalValidation.setEnabled(true);
		noValidation.setEnabled(true);		
	}

	@Override
	public void itemChecked(OperationInvocation invocation, boolean checked) {
		PatchUtil.ensureDependency(invocation, checked);
		patchViewer.update(engine.getAsymmetricDifference().getOperationInvocations().toArray(), null);
		validateAction.run();
	}

	@Override
	public Tree getTree() {
		return patchViewer.getTree();
	}

	@Override
	public void setFocus() {

	}

	@Override
	public void valueChanged() {
		validateAction.run();
	}

	@Override
	public void objectAdded(EObject eObject) {
		patchViewer.refresh();
	}

	@Override
	public void objectRemoved(EObject eObject) {
		patchViewer.refresh();
	}
	
	@Override
	public void referenceAdded(EReference referenceType, EObject src, EObject tgt) {
		patchViewer.refresh();
	}
	
	@Override
	public void referenceRemoved(EReference referenceType, EObject src, EObject tgt) {
		patchViewer.refresh();		
	}
	
	@Override
	public void attributeValueSet(EAttribute attribute, EObject object, Object value) {
		patchViewer.refresh();		
	}
}
