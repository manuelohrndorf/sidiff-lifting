package org.sidiff.patching.ui.view;

import java.util.Objects;

import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.Notifier;
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
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.sidiff.common.ui.util.Exceptions;
import org.sidiff.common.ui.util.UIUtil;
import org.sidiff.integration.editor.highlighting.EditorHighlighting;
import org.sidiff.patching.PatchEngine;
import org.sidiff.patching.operation.OperationInvocationStatus;
import org.sidiff.patching.operation.OperationInvocationWrapper;
import org.sidiff.patching.report.IPatchReportListener;
import org.sidiff.patching.ui.adapter.IModelChangeListener;
import org.sidiff.patching.ui.adapter.ModelAdapter;
import org.sidiff.patching.ui.adapter.ModelChangeHandler;
import org.sidiff.patching.ui.internal.PatchingUiPlugin;
import org.sidiff.patching.ui.view.ArgumentValueEditingSupport.IValueChangedListener;
import org.sidiff.patching.ui.view.filter.OperationInvocationFilter;
import org.sidiff.patching.validation.ValidationMode;

public class OperationExplorerView extends ViewPart implements IModelChangeListener, IValueChangedListener,
		IPatchReportListener, ITabbedPropertySheetPageContributor, IPartListener {

	public static final String ID = "org.sidiff.patching.ui.view.OperationExplorerView";

	private static final ImageDescriptor apply = PatchingUiPlugin.getImageDescriptor("apply.gif");
	private static final ImageDescriptor revert = PatchingUiPlugin.getImageDescriptor("revert.gif");
	private static final ImageDescriptor ignore = PatchingUiPlugin.getImageDescriptor("ignored.gif");
	private static final ImageDescriptor unignore = PatchingUiPlugin.getImageDescriptor("unignore.gif");
	private static final ImageDescriptor properties = PatchingUiPlugin.getImageDescriptor("properties.gif");

	private PatchEngine engine;
	private IEditorPart editor;
	private ModelAdapter modelAdapter;

	private TreeViewer patchViewer;
	private OperationLabelProvider operationLabelProvider;

	// ----------- Execution -------------------
	private Action applyPatchAction;

	// Collapse
	private Action collapseAllAction;
	// Expand
	private Action expandAllAction;

	// ----------- Filter -------------------
	private Action filterOperationsAction;
	private OperationInvocationFilter executedOperationsFilter;

	// ----------- Validation -------------------
	private DropDownAction validateMenu;
	private Action iterativeValidationAction;
	private Action finalValidationAction;
	private Action noValidationAction;

	private void updatePropertyViewViaSelectionListener(TreeViewer viewer) {
		ISelection selection = viewer.getSelection();
		viewer.setSelection(null);
		viewer.setSelection(selection);
	}

	@Override
	public void createPartControl(Composite parent) {

		// TreeViewer
		patchViewer = new TreeViewer(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL);
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
					refresh();
				}
			};
		});

		// ContextMenu
		MenuManager menuMgr = new MenuManager();
		Menu menu = menuMgr.createContextMenu(patchViewer.getControl());
		menuMgr.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
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
									refresh();
								};
							});
							manager.add(new Action("Ignore operation", ignore) {
								@Override
								public void run() {
									engine.ignore(operationWrapper.getOperationInvocation());
									updatePropertyViewViaSelectionListener(patchViewer);
									refresh();
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
									refresh();
								}
							});
						}
						manager.add(new Action("Show Properties View", properties) {
							@Override
							public void run() {
								Exceptions.show(() -> {
									UIUtil.showView(IViewPart.class, IPageLayout.ID_PROP_SHEET);
									return Status.OK_STATUS;
								});
							}
						});
					}
				}
			}
		});
		menuMgr.setRemoveAllWhenShown(true);
		patchViewer.getControl().setMenu(menu);

		getSite().setSelectionProvider(patchViewer);
		try {
			patchViewer.addSelectionChangedListener(EditorHighlighting.getInstance().getSelectionChangedListener());
		} catch(NoClassDefFoundError e) {
			// The editor highlighting plugin is optional.
			// This exception might be thrown when it is unavailable.
		}

		createActions();
		createToolbar();
		updateActionEnabledStates();
	}

	public void setPatchEngine(PatchEngine patchEngine) {
		Objects.requireNonNull(patchEngine, "patchEngine must not be null");
		clearPatchEngine();
		this.engine = patchEngine;
		this.engine.getPatchReportManager().addPatchReportListener(this);
		this.patchViewer.setInput(engine.getOperationManager());
		initModelAdapter();

		if (this.engine.getValidationMode() == ValidationMode.MODEL_VALIDATION) {
			finalValidationAction.setChecked(true);
		} else if (this.engine.getValidationMode() == ValidationMode.ITERATIVE_VALIDATION) {
			iterativeValidationAction.setChecked(true);
		} else {
			noValidationAction.setChecked(true);
		}

		updateActionEnabledStates();
	}
	
	protected void clearPatchEngine() {
		disposeModelAdapter();
		if (engine != null && engine.getPatchReportManager() != null) {
			engine.getPatchReportManager().removePatchReportListener(this);
			engine = null;
		}
		if(patchViewer != null && !patchViewer.getTree().isDisposed()) {
			patchViewer.setInput(null);				
		}
	}

	public void setEditor(IEditorPart editor) {
		this.editor = editor;
		updateActionEnabledStates();
	}
	
	protected void clearEditor() {
		editor = null;
	}

	/**
	 * 
	 */
	private void createActions() {

		// ----------- Filter Operations ----
		this.executedOperationsFilter = new OperationInvocationFilter();
		this.filterOperationsAction = new Action("Hide Successful Operations", IAction.AS_CHECK_BOX) {
			@Override
			public void run() {
				updateFilter(filterOperationsAction.isChecked());
			}
		};
		this.filterOperationsAction.setToolTipText("Hide all succesfully executed or ignored operations");
		this.filterOperationsAction.setImageDescriptor(PatchingUiPlugin.getImageDescriptor("filter_applied.gif"));

		// Init filter enabled
		this.filterOperationsAction.setChecked(true);
		updateFilter(true);

		// ----------- Validation ------------------
		validateMenu = new DropDownAction("Validate");
		this.validateMenu.setImageDescriptor(PatchingUiPlugin.getImageDescriptor("validation_16x16.gif"));
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
		this.finalValidationAction.setToolTipText("The model will be validated after applying the selected operation invocations.");

		this.noValidationAction = new Action("No Validation", IAction.AS_RADIO_BUTTON) {
			@Override
			public void run() {
				engine.setValidationMode(ValidationMode.NO_VALIDATION);
			}
		};
		this.noValidationAction.setToolTipText("The model won't be validated.");

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
		this.applyPatchAction.setImageDescriptor(PatchingUiPlugin.getImageDescriptor("patch_exc_16x16.gif"));
		this.applyPatchAction.setEnabled(false);

		// ----------- Collapse All ------------------
		this.collapseAllAction = new Action("Collapse all", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				patchViewer.collapseAll();
			}
		};
		this.collapseAllAction.setToolTipText("Collapse all");
		this.collapseAllAction.setImageDescriptor(PatchingUiPlugin.getImageDescriptor("collapseall.png"));

		// ----------- Expand All ------------------
		this.expandAllAction = new Action("Expand all", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				patchViewer.expandAll();
			}
		};
		this.expandAllAction.setToolTipText("Expand all");
		this.expandAllAction.setImageDescriptor(PatchingUiPlugin.getImageDescriptor("expandall.gif"));
		
	}

	private void updateFilter(boolean filterExecutedOperations) {
		if (filterExecutedOperations) {
			patchViewer.addFilter(executedOperationsFilter);
		} else {
			patchViewer.removeFilter(executedOperationsFilter);
		}
	}

	private void createToolbar() {
		IToolBarManager toolbarManager = getViewSite().getActionBars().getToolBarManager();
		toolbarManager.add(applyPatchAction);
		toolbarManager.add(filterOperationsAction);
		toolbarManager.add(collapseAllAction);
		toolbarManager.add(expandAllAction);
		toolbarManager.add(new CommandContributionItem(
				new CommandContributionItemParameter(PlatformUI.getWorkbench(), null,
						"org.sidiff.integration.editor.highlighting.commands.Toggle",
						CommandContributionItem.STYLE_CHECK)));
		toolbarManager.add(validateMenu);
	}

	@Override
	public void dispose() {
		super.dispose();
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPartService().removePartListener(this);
		clearPatchEngine();
		clearEditor();
	}
	
	@Override
	public void setFocus() {
		patchViewer.getControl().setFocus();
	}

	@Override
	public void valueChanged() {
		refresh();
	}

	@Override
	public void objectAdded(EObject eObject) {
		refresh();
	}

	@Override
	public void objectRemoved(EObject eObject) {
		refresh();
	}

	@Override
	public void referenceAdded(EReference referenceType, EObject src, EObject tgt) {
		refresh();
	}

	@Override
	public void referenceRemoved(EReference referenceType, EObject src, EObject tgt) {
		refresh();
	}

	@Override
	public void attributeValueSet(EAttribute attribute, EObject object, Object value) {
		refresh();
	}

	@Override
	public void reportChanged() {
		refresh();
	}

	public void refresh() {
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
	public <T> T getAdapter(Class<T> adapter) {
		if (adapter == IPropertySheetPage.class)
			return adapter.cast(new TabbedPropertySheetPage(this));
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
		if (part == editor) {
			clearPatchEngine();
			clearEditor();
			updateActionEnabledStates();
		}
	}

	@Override
	public void partDeactivated(IWorkbenchPart part) {
	}

	@Override
	public void partOpened(IWorkbenchPart part) {
	}

	protected void updateActionEnabledStates() {
		collapseAllAction.setEnabled(patchViewer != null && patchViewer.getInput() != null);
		expandAllAction.setEnabled(patchViewer != null && patchViewer.getInput() != null);
		filterOperationsAction.setEnabled(engine != null);
		applyPatchAction.setEnabled(engine != null);
		validateMenu.setEnabled(engine != null);
	}
	
	protected void initModelAdapter() {
		disposeModelAdapter();
		modelAdapter = new ModelAdapter();
		modelAdapter.addListener(new ModelChangeHandler(engine.getArgumentManager()));
		modelAdapter.addListener(this);
	}
	
	protected void disposeModelAdapter() {
		if(modelAdapter != null) {
			Notifier target = modelAdapter.getTarget();
			if(target != null) {
				target.eAdapters().remove(modelAdapter);
			}
			modelAdapter = null;
		}
	}
}
