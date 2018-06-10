package org.sidiff.vcmsintegration.structureview;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareUI;
import org.eclipse.compare.CompareViewerPane;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.menus.IMenuService;
import org.eclipse.ui.services.IServiceLocator;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.emf.exceptions.NoCorrespondencesException;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.vcmsintegration.ResourceChangeListener;
import org.sidiff.vcmsintegration.ViewerRegistry;
import org.sidiff.vcmsintegration.contentprovider.CompareResource;
import org.sidiff.vcmsintegration.contentprovider.DisplayMode;
import org.sidiff.vcmsintegration.contentprovider.SiLiftStructuredViewerContentProvider;
import org.sidiff.vcmsintegration.structureview.SwitchDisplayModeAction.DisplayModeCallback;
import org.sidiff.vcmsintegration.util.Util;

/**
 * Used to show {@link AsymmetricDifference}s and {@link SymmetricDifference}s
 * as structured view for ecore files.
 *
 * @author Adrian Bingener
 */
public class SiLiftStructureMergeViewer extends TreeViewer implements DisplayModeCallback, ISelectionChangedListener, ITabbedPropertySheetPageContributor, IActionHandler {

	/**
	 * Provides the elements for this {@link TreeViewer} by the two given input
	 * resources that are being compared.
	 */
	private SiLiftStructuredViewerContentProvider contentProvider;
	/**
	 * The display mode that is currently selected in the {@link ToolBar}.
	 */
	private DisplayMode displayMode = DisplayMode.getDefault();
	/**
	 * The id for the toolbar that is used in this {@link TreeViewer}.
	 */
	private static final String TOOLBAR_ID = "toolbar:org.sidiff.vcmsintegration.structureview.silift.toolbar";
	/**
	 * The operation invocation that is selected in the {@link TreeViewer}
	 */
	private OperationInvocation selectedOperationInvocation;
	/**
	 * The configuration that controls various UI aspects of compare/merge
	 * viewers like title labels and images.
	 */
	private CompareConfiguration compareConfiguration;
	/**
	 * The action button in the toolbar, that applies the selected operation
	 * invocation on the right model.
	 */
	private ApplyOnRightAction applyOnRightAction;
	/**
	 * The action button in the toolbar, that applies the selected operation
	 * invocation on the left model.
	 */
	private ApplyOnLeftAction applyOnLeftAction;
	/**
	 * The action button in the toolbar, that switches the
	 * {@link DifferenceDirection} in the direction in the content provider.
	 */
	private ToggleDirectionAction switchDirectionAction;

	/**
	 * The action button in the toolbar, that tries to open diagram files
	 * corresponding with die {@link CompareResource} delivered by the
	 * {@link SiLiftStructuredViewerContentProvider}
	 */
	private ShowDiagramAction showDiagramAction;
	/**
	 * The action button in the toolbar, thaT tries to save the patched model 
	 * to disk
	 */
	private SaveAction saveAction;
	/**
	 * A list of all listeners that will be notified if this tree view changes
	 * the resource that are loaded.
	 */
	protected List<ResourceChangeListener> resourceChangeListeners;

	/**
	 * Creates a new {@link SiLiftStructureMergeViewer} with the given compare
	 * configuration.
	 *
	 * @param parent The parent composite that holds this view
	 * @param compareConfiguration Configuration to be used when comparing the
	 *            inputs
	 */
	public SiLiftStructureMergeViewer(Composite parent, CompareConfiguration compareConfiguration) {
		super(parent);

		this.compareConfiguration = compareConfiguration;
		this.resourceChangeListeners = new ArrayList<ResourceChangeListener>();
		getControl().setData(CompareUI.COMPARE_VIEWER_TITLE, "SiLift Viewer");

		// Create custom content provider that takes two ecore files and shows
		// lifted differences in this TreeView
		contentProvider = new SiLiftStructuredViewerContentProvider(displayMode, compareConfiguration);
		setContentProvider(contentProvider);
		setLabelProvider(Util.getAdapterFactoryLabelProvider());

		// Initialize toolbar
		final ToolBarManager toolbarManager = CompareViewerPane.getToolBarManager(parent);
		if (PlatformUI.isWorkbenchRunning()) {
			IServiceLocator workbench = PlatformUI.getWorkbench();
			final IMenuService menuService = (IMenuService) workbench.getService(IMenuService.class);
			if (menuService != null) {
				menuService.populateContributionManager(toolbarManager, TOOLBAR_ID);
				ToolBar toolbar = toolbarManager.getControl();
				if (toolbar != null) {
					toolbar.addDisposeListener(new DisposeListener() {
						public void widgetDisposed(DisposeEvent e) {
							menuService.releaseContributions(toolbarManager);
						}
					});
				} else {
					LogUtil.log(LogEvent.ERROR, "Failed to locate the toolbar for this viewer. Used toolbar id: " + TOOLBAR_ID);
				}
			} else {
				LogUtil.log(LogEvent.ERROR, "Failed to get IMenuService");
			}
		}

		// Register selection listeners on tree view
		addSelectionChangedListener(this);
		compareConfiguration.getContainer().getWorkbenchPart().getSite().setSelectionProvider(this);

		// Register actions on toolbar
		saveAction = new SaveAction(this);
		applyOnRightAction = new ApplyOnRightAction(this);
		applyOnLeftAction = new ApplyOnLeftAction(this);
		switchDirectionAction = new ToggleDirectionAction(this);
		showDiagramAction = new ShowDiagramAction(contentProvider);
		toolbarManager.add(applyOnRightAction);
		toolbarManager.add(switchDirectionAction);
		toolbarManager.add(applyOnLeftAction);
		toolbarManager.add(saveAction);
		toolbarManager.add(showDiagramAction);
		toolbarManager.add(new SwitchDisplayModeAction(DisplayMode.values(), this));

		saveAction.setEnabled(false);
		switchDirectionAction.setEnabled(true);
		applyOnRightAction.setEnabled(false);
		applyOnLeftAction.setEnabled(false);
		toolbarManager.update(true);
	}

	/**
	 * To be called when an item in the {@link TreeViewer} was selected. This
	 * method will enable the buttons in the toolbar according to the selected
	 * {@link CompareConfiguration}.
	 */
	protected void onSelectionChange(SelectionChangedEvent event) {
		// Copying elements to the left side is enabled, if the left is editable
		// and a operation invocation is selected
		// mergeToRightInvocationAction.setEnabled(selectedOperationInvocation
		// != null && compareConfiguration.isLeftEditable());
		updateToolbarItemStates();
	}

	@Override
	public void onRefresh() {
		contentProvider.refreshAsync(displayMode);
		updateToolbarItemStates();
		refresh();
	}
	
	
	/**
	 * Refreshs the Content Provider and notifies all registered listeners
	 * to refresh as well.
	 * Used if a model is modified in the Editor Integration while comparing it.
	 * @author Daniel Roedder
	 */
	public void onRefreshNotify() {
		onRefresh();
		notifyResourceChangeListener(contentProvider.getLeft());
		notifyResourceChangeListener(contentProvider.getRight());
		if (contentProvider.getAncestor() != null) {
			notifyResourceChangeListener(contentProvider.getAncestor());
		}
		
	}

	@Override
	public void onDisplayModeChanged(DisplayMode displayMode) {
		this.displayMode = displayMode;
		contentProvider.setDisplayMode(displayMode);
		updateToolbarItemStates();
		refresh();
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		ISelection selection = event.getSelection();
		if (selection instanceof TreeSelection) {
			TreeSelection ts = (TreeSelection) selection;
			Object firstElement = ts.getFirstElement();
			if (firstElement instanceof OperationInvocation) {
				// Checks if the selected item is a operation invocation
				selectedOperationInvocation = (OperationInvocation) firstElement;
			} else {
				// If the selection is no operation invocation, it is set to
				// null to make sure that onSelectionChange() enables(disables
				// the correct UI elements
				selectedOperationInvocation = null;
			}
		} else {
			selectedOperationInvocation = null;
			LogUtil.log(LogEvent.ERROR, "Cannot convert selection in TreeViewer to instance of TreeSelection");
		}
		onSelectionChange(event);
	}

	@Override
	protected void fireSelectionChanged(final SelectionChangedEvent event) {
		ViewerRegistry.getInstance().getContentMergeViewer().selectionChanged(event);
		ISelection selection = event.getSelection();
		if (selection instanceof TreeSelection) {
			TreeSelection ts = (TreeSelection) selection;
			Object firstElement = ts.getFirstElement();
			if (firstElement instanceof OperationInvocation) {
				// Checks if the selected item is a operation invocation
				selectedOperationInvocation = (OperationInvocation) firstElement;
			} else {
				// If the selection is no operation invocation, it is set to
				// null to make sure that onSelectionChange() enables(disables
				// the correct UI elements
				selectedOperationInvocation = null;
			}
		} else {
			selectedOperationInvocation = null;
			LogUtil.log(LogEvent.ERROR, "Cannot convert selection in TreeViewer to instance of TreeSelection");
		}
		updateToolbarItemStates();
	}

	@Override
	public void handle(EventObject event) {
		if (event.getSource().equals(applyOnLeftAction) || event.getSource().equals(applyOnRightAction)) {
			// apply OperationInvocation
			IRunnableWithProgress runnable = new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) {

					// Actual refresh method that blocks
					monitor.beginTask("Apply Operation", IProgressMonitor.UNKNOWN);
					contentProvider.applyOperationInvocation(selectedOperationInvocation);
					monitor.done();
				}
			};

			IWorkbench wb = PlatformUI.getWorkbench();
			IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
			Shell shell = win != null ? win.getShell() : null;
			try {
				ProgressMonitorDialog dialog = new ProgressMonitorDialog(shell);
				dialog.run(true, false, runnable);
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// fire ResourceChangedEvent
			notifyResourceChangeListener(contentProvider.getPatchedResource());

			// refresh GUI
			refresh();
		} else if (event.getSource().equals(switchDirectionAction)) {
			IRunnableWithProgress runnable = new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) {

					// Actual refresh method that blocks
					monitor.beginTask("Calculating model difference", IProgressMonitor.UNKNOWN);
					try {
						contentProvider.switchDifferenceDirection();
					} catch (InvalidModelException e) {
						e.printStackTrace();
					} catch (NoCorrespondencesException e) {
						e.printStackTrace();
					} finally {
						updateToolbarItemStates();
						monitor.done();
					}
				}
			};

			IWorkbench wb = PlatformUI.getWorkbench();
			IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
			Shell shell = win != null ? win.getShell() : null;
			try {
				ProgressMonitorDialog dialog = new ProgressMonitorDialog(shell);
				dialog.run(true, false, runnable);
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			refresh();
		} else if (event.getSource().equals(saveAction)) {
			// save model to local file
			try {
				contentProvider.getPatchEngine().getPatchedResource().save(null);
				MessageDialog.open(MessageDialog.INFORMATION, null, "Save succeeded", "Model has been saved", SWT.NONE);
				saveAction.setEnabled(false);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Adds the given listener to the internal list of listeners that will be
	 * notified when this viewer changes the resource.
	 * 
	 * @param changeListener The listener that is being notified
	 */
	public void addResourceChangeListener(ResourceChangeListener changeListener) {
		resourceChangeListeners.add(changeListener);
	}

	/**
	 * Remove the given listener from the internal list of listeners that will
	 * be notified when this viewer changes the resource.
	 * 
	 * @param changeListener The listener that is removed
	 */
	public void removeResourceChangeListener(ResourceChangeListener changeListener) {
		resourceChangeListeners.remove(changeListener);
	}

	/**
	 * Notifies all registered listeners that the given resource has changed.
	 * 
	 * @param compareResource The resource that was changed.
	 */
	private void notifyResourceChangeListener(CompareResource compareResource) {
		for (ResourceChangeListener changeListener : resourceChangeListeners) {
			if (changeListener != null) {
				changeListener.onResourceChanged(compareResource);
			} else {
				LogUtil.log(LogEvent.WARNING, "Cannot notify a null reference change listener");
			}
		}
	}

	private void updateToolbarItemStates() {
		final boolean modeAsymmetric = displayMode.equals(DisplayMode.ASYMMETRIC_DIFFERENCE);
		applyOnRightAction.setEnabled(modeAsymmetric && selectedOperationInvocation != null && contentProvider.getRight().isEditable());
		applyOnLeftAction.setEnabled(modeAsymmetric && selectedOperationInvocation != null && contentProvider.getLeft().isEditable());
		switchDirectionAction.setEnabled(modeAsymmetric);
		saveAction.setEnabled(modeAsymmetric && contentProvider.getPatchEngine() != null
				&& contentProvider.getLeft() != null && contentProvider.getLeft().isEditable());
	}

	@Override
	protected void handleOpen(SelectionEvent event) {
		super.handleOpen(event);
	}

	protected void fireOpen(final OpenEvent event) {
	}

	@Override
	public String getContributorId() {
		return "org.sidiff.vcmsintegration.structureview.silift";
	}

	@Override
	public void setSelection(ISelection selection, boolean reveal) {
		/**
		 * <p>
		 * If the new selection differs from the current selection the hook
		 * <code>updateSelection</code> is called.
		 * </p>
		 * <p>
		 * If <code>setSelection</code> is called from within
		 * <code>preserveSelection</code>, the call to
		 * <code>updateSelection</code> is delayed until the end of
		 * <code>preserveSelection</code>.
		 * </p>
		 * <p>
		 * Subclasses do not typically override this method, but implement
		 * <code>setSelectionToWidget</code> instead.
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

}
