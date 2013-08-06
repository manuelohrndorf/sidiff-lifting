package org.sidiff.patching.ui.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.window.ToolTip;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.ViewPart;
import org.sidiff.common.util.StringUtil;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.patching.PatchEngine;
import org.sidiff.patching.PatchEngine.PatchResult;
import org.sidiff.patching.exceptions.PatchNotExecuteableException;
import org.sidiff.patching.report.PatchReport;
import org.sidiff.patching.ui.Activator;
import org.sidiff.patching.ui.adapter.ModelAdapter.IModelChangeListener;
import org.sidiff.patching.ui.view.CheckBoxMouseListener.ICheckBoxListener;
import org.sidiff.patching.ui.view.ValueEditingSupport.IValueChangedListener;
import org.sidiff.patching.ui.view.filter.NullValueParameterFilter;
import org.sidiff.patching.ui.view.filter.ValueParameterFilter;
import org.sidiff.patching.util.PatchUtil;

public class PatchView extends ViewPart implements ICheckBoxListener, IModelChangeListener, IValueChangedListener {
	public static final String ID = "org.sidiff.patching.ui.view.PatchView";
	private Logger logger = Logger.getLogger(PatchView.class.getName());

	private PatchEngine engine;

	private TreeViewer patchViewer;

	private ValueEditingSupport editingSupport;
	private ValueLabelProvider valueLabelProvider;

	private NullValueParameterFilter nullValueParameterFilter;
	private Action nullValueParameterFilterAction;
	private ValueParameterFilter valueParameterFilter;
	private Action valueParameterFilterAction;
	
	private SelectionHandler selectionHandler;

	private Action saveAction;
	private Action validateAction;

	@Override
	public void createPartControl(Composite parent) {
		FillLayout layout = new FillLayout(SWT.VERTICAL);
		parent.setLayout(layout);

		// TreeViewer
		patchViewer = new TreeViewer(parent, SWT.BORDER);
		patchViewer.setContentProvider(new PatchContentProvider());
		ColumnViewerToolTipSupport.enableFor(patchViewer, ToolTip.RECREATE);

		Tree tree = patchViewer.getTree();
		tree.setLinesVisible(true);
		tree.setHeaderVisible(true);

		// CheckboxListener
		tree.addMouseListener(new CheckBoxMouseListener(this));

		// Patch column
		TreeViewerColumn differenceColumn = new TreeViewerColumn(patchViewer, SWT.NONE);
		differenceColumn.getColumn().setWidth(200);
		differenceColumn.getColumn().setResizable(true);
		// PatchLabelProvider
		differenceColumn.setLabelProvider(new PatchLabelProvider());

		// Value column
		TreeViewerColumn valueColumn = new TreeViewerColumn(patchViewer, SWT.NONE);
		valueColumn.getColumn().setWidth(200);
		valueColumn.getColumn().setResizable(true);
		// ValueLabelProvider
		valueLabelProvider = new ValueLabelProvider();
		valueColumn.setLabelProvider(valueLabelProvider);
		editingSupport = new ValueEditingSupport(patchViewer);
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


	private void createActions() {
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
		
		this.saveAction = new Action("Save") {
			@Override
			public void run() {
				try {
					IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
					FileDialog dialog = new FileDialog(window.getShell(), SWT.SAVE);
					String filename = dialog.open();
					if (filename != null) {
						PatchResult result = engine.applyPatch();

						ReportView reportView = (ReportView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(ReportView.ID);
						if (reportView != null)
							reportView.setEntries(result.getReport().getEntries());

						File file = new File(filename);
						Resource resource = result.getPatchedResource();
						resource.save(Collections.EMPTY_MAP);
						// Open Editor for patched model
						IWorkbenchPage page = window.getActivePage();
						IFileStore fileStore = EFS.getLocalFileSystem().getStore(file.toURI());
						IDE.openEditorOnFileStore(page, fileStore);
					}

				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (PatchNotExecuteableException e1) {
					IStatus error = new Status(IStatus.ERROR, Activator.PLUGIN_ID, SWT.OK, e1.getLocalizedMessage(), null);
					ErrorDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Error", null, error);
				} catch (PartInitException e1) {
					e1.printStackTrace();
				}
			}
		};
		this.saveAction.setToolTipText("Execute and save");
		this.saveAction.setImageDescriptor(Activator.getImageDescriptor("save_edit.gif"));
		this.saveAction.setEnabled(false);

		this.validateAction = new Action("Check Patch") {
			@Override
			public void run() {
				PatchReport report = engine.createPatchReport();
				ReportView reportView = (ReportView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(ReportView.ID);
				if (reportView != null) { 
					reportView.setEntries(report.getEntries());
				}
				((PatchLabelProvider) patchViewer.getLabelProvider(0)).setReport(report);
				patchViewer.refresh();
			}
		};
		this.validateAction.setToolTipText("Check Patch for valid preconditions");
		this.validateAction.setImageDescriptor(Activator.getImageDescriptor("check_preconditions.gif"));
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
	}

	private void createToolbar() {
		IToolBarManager toolbarManager = getViewSite().getActionBars().getToolBarManager();
		toolbarManager.add(saveAction);
		// toolbarManager.add(preCheckAction);
	}

	public void setPatchEngine(PatchEngine patchEngine) {
		this.engine = patchEngine;
		this.valueLabelProvider.setCorrespondence(engine.getCorrespondence());
		this.editingSupport.setCorrespondence(engine.getCorrespondence());
		this.patchViewer.setInput(engine.getDifference());
		this.patchViewer.expandAll();
		this.patchViewer.getTree().getColumns()[1].pack();
		saveAction.setEnabled(true);
		validateAction.run();
	}

	@Override
	public void itemChecked(OperationInvocation invocation, boolean checked) {
		logger.log(Level.FINE, StringUtil.resolve(invocation) + " is " + (checked ? "checked" : "unchecked"));
		PatchUtil.ensureDependency(invocation, checked);
		patchViewer.update(engine.getDifference().getOperationInvocations().toArray(), null);
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
}
