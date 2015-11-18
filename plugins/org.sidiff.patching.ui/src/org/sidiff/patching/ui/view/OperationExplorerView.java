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
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.sidiff.difference.symmetric.compareview.internal.DifferenceSelectionController;
import org.sidiff.patching.PatchEngine;
import org.sidiff.patching.operation.OperationInvocationStatus;
import org.sidiff.patching.operation.OperationInvocationWrapper;
import org.sidiff.patching.report.IPatchReportListener;
import org.sidiff.patching.settings.PatchingSettings.ValidationMode;
import org.sidiff.patching.ui.Activator;
import org.sidiff.patching.ui.adapter.IModelChangeListener;
import org.sidiff.patching.ui.view.ArgumentValueEditingSupport.IValueChangedListener;
import org.sidiff.patching.ui.view.filter.OperationInvocationFilter;

public class OperationExplorerView extends ViewPart implements IModelChangeListener, IValueChangedListener,
		IPatchReportListener, ITabbedPropertySheetPageContributor, IPartListener {

	public static final String ID = "org.sidiff.patching.ui.view.OperationExplorerView";

	private final ImageDescriptor apply = Activator.getImageDescriptor("apply.gif");
	private final ImageDescriptor revert = Activator.getImageDescriptor("revert.gif");
	private final ImageDescriptor ignore = Activator.getImageDescriptor("ignored.gif");
	private final ImageDescriptor unignore = Activator.getImageDescriptor("unignore.gif");
	private final ImageDescriptor properties = Activator.getImageDescriptor("properties.gif");
	private final ImageDescriptor toggle = PlatformUI.getWorkbench().getSharedImages()
			.getImageDescriptor(ISharedImages.IMG_ELCL_SYNCED);

	private PatchEngine engine;

	private TreeViewer patchViewer;
	private OperationLabelProvider operationLabelProvider;

	// ----------- Execution -------------------
	private Action applyPatchAction;

	// Collapse
	private Action collapseAllAction;
	// Expand
	private Action expandAllAction;

	// ----------- Toggle Highlight -------------
	private Action toggleHighlightAction;

	// ----------- Filter -------------------
	private Action filterOperationsAction;
	private OperationInvocationFilter executedOperationsFilter;

	// ----------- Validation -------------------
	private DropDownAction validateMenu;
	private Action iterativeValidationAction;
	private Action finalValidationAction;
	private Action noValidationAction;

	// FIXME cpietsch 26.02.2014: notify properties view
	private void updatePropertyViewViaSelectionListener(TreeViewer viewer) {
		ISelection selection = viewer.getSelection();
		viewer.setSelection(null);
		viewer.setSelection(selection);
	}

	@Override
	public void createPartControl(Composite parent) {

		// TreeViewer
		patchViewer = new TreeViewer(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL) {
			public void setSelection(ISelection selection, boolean reveal) {
				/**
				 * <p>
				 * If the new selection differs from the current selection the
				 * hook <code>updateSelection</code> is called.
				 * </p>
				 * <p>
				 * If <code>setSelection</code> is called from within
				 * <code>preserveSelection</code>, the call to
				 * <code>updateSelection</code> is delayed until the end of
				 * <code>preserveSelection</code>.
				 * </p>
				 * <p>
				 * Subclasses do not typically override this method, but
				 * implement <code>setSelectionToWidget</code> instead.
				 * </p>
				 */
				Control control = getControl();
				if (control == null || control.isDisposed()) {
					return;
				}

				setSelectionToWidget(selection, reveal);
				ISelection sel = getSelection();
				updateSelection(sel);
				firePostSelectionChanged(new SelectionChangedEvent(this, sel));

			}
		};
		patchViewer.setContentProvider(new PatchContentProvider());
		patchViewer.setAutoExpandLevel(0);

		operationLabelProvider = new OperationLabelProvider();
		patchViewer.setLabelProvider(operationLabelProvider);

		// Register part listener (for editor)
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPartService().addPartListener(this);

		patchViewer.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent event) {
				TreeViewer viewer = (TreeViewer) event.getViewer();
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				Object selectedNode = selection.getFirstElement();
				if (selectedNode instanceof OperationInvocationWrapper) {
					OperationInvocationWrapper operationWrapper = (OperationInvocationWrapper) selectedNode;
					if (operationWrapper.getStatus() == OperationInvocationStatus.PASSED
							&& operationWrapper.getStatus() != OperationInvocationStatus.IGNORED) {
						engine.revert(operationWrapper.getOperationInvocation());
					} else if (operationWrapper.getStatus() != OperationInvocationStatus.IGNORED) {
						engine.apply(operationWrapper.getOperationInvocation(), true);
					} else if (operationWrapper.getStatus() == OperationInvocationStatus.IGNORED) {
						engine.unignore(operationWrapper.getOperationInvocation());
					}
					updatePropertyViewViaSelectionListener(viewer);
					patchViewer.refresh();
				}
			};
		});

		// ContextMenu
		MenuManager menuMgr = new MenuManager();
		Menu menu = menuMgr.createContextMenu(patchViewer.getControl());
		menuMgr.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				// IWorkbench wb = PlatformUI.getWorkbench();
				// IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
				if (patchViewer.getSelection().isEmpty()) {
					return;
				}

				if (patchViewer.getSelection() instanceof IStructuredSelection) {
					IStructuredSelection selection = (IStructuredSelection) patchViewer.getSelection();
					Object selectedNode = selection.getFirstElement();
					if (selectedNode instanceof OperationInvocationWrapper) {
						final OperationInvocationWrapper operationWrapper = (OperationInvocationWrapper) selectedNode;
						if (operationWrapper.getStatus() != OperationInvocationStatus.PASSED
								&& operationWrapper.getStatus() != OperationInvocationStatus.IGNORED) {
							manager.add(new Action("Apply operation", apply) {

								@Override
								public void run() {
									engine.apply(operationWrapper.getOperationInvocation(), true);
									updatePropertyViewViaSelectionListener(patchViewer);
									patchViewer.refresh();
								};
							});
							manager.add(new Action("Ignore operation", ignore) {

								@Override
								public void run() {
									engine.ignore(operationWrapper.getOperationInvocation());
									updatePropertyViewViaSelectionListener(patchViewer);
									patchViewer.refresh();
								}
							});
						} else if (operationWrapper.getStatus() == OperationInvocationStatus.PASSED
								&& operationWrapper.getStatus() != OperationInvocationStatus.IGNORED) {
							manager.add(new Action("Revert operation", revert) {

								@Override
								public void run() {
									engine.revert(operationWrapper.getOperationInvocation());
									updatePropertyViewViaSelectionListener(patchViewer);
								};
							});
						} else if (operationWrapper.getStatus() == OperationInvocationStatus.IGNORED) {
							manager.add(new Action("Unignore operation", unignore) {

								@Override
								public void run() {
									engine.unignore(operationWrapper.getOperationInvocation());
									updatePropertyViewViaSelectionListener(patchViewer);
									patchViewer.refresh();
								}
							});
						}

						manager.add(new Action("Show Properties View", properties) {

							@Override
							public void run() {
								try {
									PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
											.showView(IPageLayout.ID_PROP_SHEET);
								} catch (PartInitException e) {
									e.printStackTrace();
								}
							}
						});
					}
				}
			}
		});
		menuMgr.setRemoveAllWhenShown(true);
		patchViewer.getControl().setMenu(menu);

		getSite().setSelectionProvider(patchViewer);

		createActions(patchViewer);
		createMenus();
		createToolbar();

		// Clear initially
		this.clearView();
	}

	public void setPatchEngine(PatchEngine patchEngine) {

		this.engine = patchEngine;

		this.initView();

		this.patchViewer.setInput(engine.getOperationManager());

		filterOperationsAction.setEnabled(true);

		ValidationMode tmpMode = this.engine.getValidationMode();

		iterativeValidationAction.setEnabled(true);
		finalValidationAction.setEnabled(true);
		noValidationAction.setEnabled(true);

		this.engine.setValidationMode(tmpMode);

		if (this.engine.getValidationMode() == ValidationMode.MODEL_VALIDATION)
			finalValidationAction.setChecked(true);
		else if (this.engine.getValidationMode() == ValidationMode.ITERATIVE_VALIDATION)
			iterativeValidationAction.setChecked(true);
		else
			noValidationAction.setChecked(true);

	}

	/**
	 * 
	 */
	private void createActions(TreeViewer viewer) {

		final TreeViewer treeViewer = viewer;
		// ----------- Filter Operations ----
		this.executedOperationsFilter = new OperationInvocationFilter();
		this.filterOperationsAction = new Action("Hide Successful Operations", IAction.AS_CHECK_BOX) {
			@Override
			public void run() {
				updateFilter(filterOperationsAction);
			}
		};
		this.filterOperationsAction.setToolTipText("Hide all succesfully executed or ignored operations");
		this.filterOperationsAction.setImageDescriptor(Activator.getImageDescriptor("filter_applied.gif"));

		// Init filter enabled
		this.filterOperationsAction.setChecked(true);
		updateFilter(this.filterOperationsAction);

		// ----------- Validation ------------------
		validateMenu = new DropDownAction("Validate");
		this.validateMenu.setImageDescriptor(Activator.getImageDescriptor("validation_16x16.gif"));
		this.iterativeValidationAction = new Action("Iterative Validation", IAction.AS_RADIO_BUTTON) {
			@Override
			public void run() {
				engine.setValidationMode(ValidationMode.ITERATIVE_VALIDATION);
			}

		};
		this.iterativeValidationAction.setToolTipText("The model will be validated after each operation invocation.");

		this.finalValidationAction = new Action("Final Validation", IAction.AS_RADIO_BUTTON) {
			@Override
			public void run() {
				engine.setValidationMode(ValidationMode.MODEL_VALIDATION);
			}
		};
		this.finalValidationAction
				.setToolTipText("The model will be validatetd after applying the selected operation invocations.");

		this.noValidationAction = new Action("No Validation", IAction.AS_RADIO_BUTTON) {
			@Override
			public void run() {
				engine.setValidationMode(ValidationMode.NO_VALIDATION);
			}
		};
		this.noValidationAction.setToolTipText("The model won't be validated.");

		iterativeValidationAction.setEnabled(false);
		finalValidationAction.setEnabled(false);
		noValidationAction.setEnabled(false);

		validateMenu.add(noValidationAction);
		validateMenu.add(finalValidationAction);
		validateMenu.add(iterativeValidationAction);

		// ----------- Execution ------------------
		this.applyPatchAction = new Action("Apply all non conflicting and unignored changes", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				engine.applyPatch(false);
				updatePropertyViewViaSelectionListener(patchViewer);
			}
		};
		this.applyPatchAction.setToolTipText("Apply all non conflicting and unignored changes");
		this.applyPatchAction.setImageDescriptor(Activator.getImageDescriptor("patch_exc_16x16.gif"));

		// ----------- Collapse All ------------------
		this.collapseAllAction = new Action("Collapse all", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				treeViewer.collapseAll();

			}
		};
		this.collapseAllAction.setToolTipText("Collapse all");
		this.collapseAllAction.setImageDescriptor(Activator.getImageDescriptor("collapseall.png"));

		// ----------- Expand All ------------------
		this.expandAllAction = new Action("Expand all", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				treeViewer.expandAll();
				;
			}
		};
		this.expandAllAction.setToolTipText("Expand all");
		this.expandAllAction.setImageDescriptor(Activator.getImageDescriptor("expandall.gif"));

		// ----------- Toggle Highlight ------------------
		this.toggleHighlightAction = new Action("Highlighting of Changes", IAction.AS_CHECK_BOX) {
			@Override
			public void run() {
				System.out.println("TOGGLE");
				DifferenceSelectionController.getInstance().toggleHighlight();
			}
		};
		this.toggleHighlightAction.setToolTipText("Highlighting of Changes");
		this.toggleHighlightAction.setImageDescriptor(toggle);
	}

	private void updateFilter(Action action) {
		if (action.equals(filterOperationsAction)) {
			if (action.isChecked()) {
				patchViewer.addFilter(executedOperationsFilter);
			} else {
				patchViewer.removeFilter(executedOperationsFilter);
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

	}

	private void createToolbar() {
		IToolBarManager toolbarManager = getViewSite().getActionBars().getToolBarManager();
		toolbarManager.add(applyPatchAction);
		toolbarManager.add(filterOperationsAction);
		toolbarManager.add(collapseAllAction);
		toolbarManager.add(expandAllAction);
		toolbarManager.add(toggleHighlightAction);
		toolbarManager.add(validateMenu);
	}

	@Override
	public void dispose() {
		super.dispose();

		if (engine != null && engine.getPatchReportManager() != null) {
			engine.getPatchReportManager().removePatchReportListener(this);
		}

	}

	@Override
	public void setFocus() {
		patchViewer.getControl().setFocus();
	}

	@Override
	public void valueChanged() {

		patchViewer.refresh();

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

	@Override
	public void pushReport(int i) {

	}

	@Override
	public String getContributorId() {
		return getSite().getId();
	}

	@Override
	public Object getAdapter(Class adapter) {
		if (adapter == IPropertySheetPage.class)
			return new TabbedPropertySheetPage(this);
		return super.getAdapter(adapter);
	}

	@Override
	public void partActivated(IWorkbenchPart part) {

	}

	@Override
	public void partBroughtToTop(IWorkbenchPart part) {

	}

	@Override
	public void partClosed(IWorkbenchPart part) {
		if (part instanceof EditorPart) {
			// Check if at least one editor is still open
			if (PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences().length == 0) {
				this.clearView();
			}
		}
	}

	@Override
	public void partDeactivated(IWorkbenchPart part) {

	}

	@Override
	public void partOpened(IWorkbenchPart part) {

	}

	private void clearView() {
		this.patchViewer.getTree().clearAll(true);
		this.patchViewer.getTree().setVisible(false);
		this.applyPatchAction.setEnabled(false);
		this.collapseAllAction.setEnabled(false);
		this.expandAllAction.setEnabled(false);
		this.toggleHighlightAction.setEnabled(false);
		this.filterOperationsAction.setEnabled(false);
		this.validateMenu.setEnabled(false);

		if (engine != null && engine.getPatchReportManager() != null) {
			engine.getPatchReportManager().removePatchReportListener(this);
		}

	}

	private void initView() {

		if (engine != null && engine.getPatchReportManager() != null) {
			engine.getPatchReportManager().addPatchReportListener(this);
		}

		this.patchViewer.getTree().setVisible(true);
		this.applyPatchAction.setEnabled(true);
		this.collapseAllAction.setEnabled(true);
		this.expandAllAction.setEnabled(true);
		this.toggleHighlightAction.setEnabled(true);
		this.filterOperationsAction.setEnabled(true);
		this.validateMenu.setEnabled(true);
	}

}
