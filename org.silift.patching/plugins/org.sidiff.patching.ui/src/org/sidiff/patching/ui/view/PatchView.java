package org.sidiff.patching.ui.view;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionException;
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
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.ViewPart;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.patching.PatchEngine;
import org.sidiff.patching.report.IPatchReportListener;
import org.sidiff.patching.ui.adapter.IModelChangeListener;
import org.sidiff.patching.ui.view.ArgumentValueEditingSupport.IValueChangedListener;
import org.sidiff.patching.ui.view.CheckBoxMouseListener.ICheckBoxListener;
import org.sidiff.patching.ui.view.filter.NullValueParameterFilter;
import org.sidiff.patching.ui.view.filter.OutParameterFilter;
import org.sidiff.patching.ui.view.filter.ValueParameterFilter;
import org.sidiff.patching.validation.ValidationMode;


public class PatchView extends ViewPart implements ICheckBoxListener, IModelChangeListener, IValueChangedListener, IPatchReportListener {
	
	public static final String ID = "org.sidiff.patching.ui.view.PatchView";
	
	private PatchEngine engine;

	private TreeViewer patchViewer;
	private OperationLabelProvider operationLabelProvider;

	private ArgumentValueEditingSupport editingSupport;
	private ArgumentValueLabelProvider valueLabelProvider;

	//----------- Filter ----------------------
	private NullValueParameterFilter nullValueParameterFilter;
	private Action nullValueParameterFilterAction;
	private ValueParameterFilter valueParameterFilter;
	private Action valueParameterFilterAction;
	private OutParameterFilter outParameterFilter;
	private Action outParameterFilterAction;
	
	//----------- Validation -------------------
	private Action iterativeValidationAction;
	private Action finalValidationAction;
	private Action noValidationAction;
	private Action manualValidationAction;
	
	//----------- Execution -------------------
	private Action applyPatchAction;
	
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

		// Operation column
		TreeViewerColumn operationColumn = new TreeViewerColumn(patchViewer, SWT.NONE);
		operationColumn.getColumn().setWidth(200);
		operationColumn.getColumn().setText("Operation");
		operationColumn.getColumn().setResizable(true);
		// PatchLabelProvider
		operationLabelProvider = new OperationLabelProvider();
		operationColumn.setLabelProvider(operationLabelProvider);

		// Argument column
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

		createActions();
		createMenus();
		createToolbar();
		this.updateCommands();

	}

	public void setPatchEngine(PatchEngine patchEngine) {
		this.engine = patchEngine;
		this.valueLabelProvider.init(engine.getArgumentManager(), engine.getOperationManager());
		this.operationLabelProvider.init(engine.getOperationManager());
		this.editingSupport.setArgumentManager(engine.getArgumentManager());
		this.patchViewer.setInput(engine.getAsymmetricDifference());
		this.patchViewer.expandAll();
		this.patchViewer.getTree().getColumns()[1].pack();
		
		iterativeValidationAction.setEnabled(true);
		finalValidationAction.setEnabled(true);
		noValidationAction.setEnabled(true);		
		
		if(this.engine.getValidationMode() == ValidationMode.FINAL)
			finalValidationAction.setChecked(true);
		else
			if(this.engine.getValidationMode() == ValidationMode.ITERATIVE)
				iterativeValidationAction.setChecked(true);
			else
				noValidationAction.setChecked(true);
		
		if(!this.engine.getReliabilitiesComputed()){
			reliabilitiesButton.setVisible(false);
		}
		
		engine.getPatchReportManager().addPatchReportListener(this);
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
		
		this.outParameterFilter = new OutParameterFilter();
		this.outParameterFilterAction = new Action("Hide Out-Parameter"){
			@Override
			public void run(){
				updatefilter(outParameterFilterAction);
			}
		};
		
		patchViewer.addFilter(outParameterFilter);
		outParameterFilterAction.setChecked(true);
		
		//----------- Validation ------------------
		this.iterativeValidationAction = new Action("Iterative Validation", IAction.AS_RADIO_BUTTON) {
			@Override
			public void run() {
				engine.setValidationMode(ValidationMode.ITERATIVE);
			}
			
		};
		this.iterativeValidationAction.setToolTipText("The model will be validated after each operation invocation.");
		this.iterativeValidationAction.setChecked(false);
		
		this.finalValidationAction = new Action("Final Validation", IAction.AS_RADIO_BUTTON){
			@Override
			public void run() {
				engine.setValidationMode(ValidationMode.FINAL);
			}
		};
		this.finalValidationAction.setToolTipText("The model will be validatetd after applying the selected operation invocations.");
		
		this.noValidationAction = new Action("No Validation", IAction.AS_RADIO_BUTTON){
			@Override
			public void run() {
				engine.setValidationMode(ValidationMode.NO);
			}
		};
		this.noValidationAction.setToolTipText("The model won't be validated.");
		
		this.manualValidationAction = new Action("Manual Validation", IAction.AS_PUSH_BUTTON){
			@Override
			public void run() {
				engine.setValidationMode(ValidationMode.MANUAL);
				
				//TODO(TK): start validation
				System.out.println("TODO: implement me");
			}
		};
		this.manualValidationAction.setToolTipText("Validate the model.");
		
		this.manualValidationAction.setEnabled(true);
		iterativeValidationAction.setEnabled(false);
		finalValidationAction.setEnabled(false);
		noValidationAction.setEnabled(false);
		
		//----------- Execution ------------------
		this.applyPatchAction = new Action("Apply Patch", IAction.AS_PUSH_BUTTON){
			@Override
			public void run() {				
				engine.applyPatch();
			}
		};
		this.applyPatchAction.setToolTipText("Apply Patch");
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
		} else if(action == outParameterFilterAction){
			if(action.isChecked()){
				patchViewer.addFilter(outParameterFilter);
			}else{
				patchViewer.removeFilter(outParameterFilter);
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
		filterSubmenu.add(outParameterFilterAction);
		
		IMenuManager validateModeSubmenu = new MenuManager("Validation");
		rootMenuManager.add(validateModeSubmenu);
		validateModeSubmenu.add(iterativeValidationAction);
		validateModeSubmenu.add(finalValidationAction);
		validateModeSubmenu.add(noValidationAction);
		validateModeSubmenu.add(new Separator());
		validateModeSubmenu.add(manualValidationAction);
		
		IMenuManager execMenu = new MenuManager("Application");
		rootMenuManager.add(execMenu);
		execMenu.add(applyPatchAction);
	}

	private void createToolbar() {
		IToolBarManager toolbarManager = getViewSite().getActionBars().getToolBarManager();
		
		// toolbarManager.add(preCheckAction);
	}

	private void updateCommands(){
		ICommandService commandService = (ICommandService)this.getSite().getService(ICommandService.class);
		
		// QualifiedArgumentName
		Command command = commandService.getCommand("org.sidiff.patching.ui.commandQualifiedArgumentName");
		try {
			System.out.println(!HandlerUtil.toggleCommandState(command));
			this.valueLabelProvider.setShowQualifiedArgumentName(!HandlerUtil.toggleCommandState(command));
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}
	
	@Override
	public void itemChecked(OperationInvocation op, boolean checked) {
		op.setApply(checked);
		
		if (checked){
			// Include also operations which "op" depends on
			for (OperationInvocation pre : op.getAllPredecessors()) {
				pre.setApply(true);
			}
		} else {
			// Revert also operations which depend on "op"
			for (OperationInvocation succ : op.getAllSuccessors()) {
				succ.setApply(false);
			}
		}
		
		patchViewer.refresh();
		engine.applyPatch();
	}

	@Override
	public Tree getTree() {
		return patchViewer.getTree();
	}

	@Override
	public void dispose() {		
		super.dispose();
		
		engine.getPatchReportManager().removePatchReportListener(this);
	}
	
	@Override
	public void setFocus() {

	}

	@Override
	public void valueChanged() {
		
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

	@Override
	public void reportChanged() {
		patchViewer.refresh();
	}
	
	public void toggleQualifiedArgumentNames(boolean b){
		valueLabelProvider.setShowQualifiedArgumentName(b);
		patchViewer.refresh();
	}
}
