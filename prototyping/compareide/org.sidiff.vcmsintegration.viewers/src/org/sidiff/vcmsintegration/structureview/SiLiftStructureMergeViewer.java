package org.sidiff.vcmsintegration.structureview;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareUI;
import org.eclipse.compare.CompareViewerPane;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.vcmsintegration.ResourceChangeListener;
import org.sidiff.vcmsintegration.contentprovider.DisplayMode;
import org.sidiff.vcmsintegration.contentprovider.SiLiftStructuredViewerContentProvider;
import org.sidiff.vcmsintegration.remote.CompareResource;
import org.sidiff.vcmsintegration.structureview.actions.ApplyOnLeftAction;
import org.sidiff.vcmsintegration.structureview.actions.ApplyOnRightAction;
import org.sidiff.vcmsintegration.structureview.actions.IActionHandler;
import org.sidiff.vcmsintegration.structureview.actions.SaveAction;
import org.sidiff.vcmsintegration.structureview.actions.ShowDiagramAction;
import org.sidiff.vcmsintegration.structureview.actions.SwitchDisplayModeAction;
import org.sidiff.vcmsintegration.structureview.actions.SwitchDisplayModeAction.DisplayModeCallback;
import org.sidiff.vcmsintegration.structureview.actions.ToggleDirectionAction;

/**
 * Used to show {@link AsymmetricDifference}s and {@link SymmetricDifference}s
 * as structured view for ecore files.
 *
 * @author Adrian Bingener, Robert Müller
 */
public class SiLiftStructureMergeViewer extends TreeViewer implements DisplayModeCallback,
	ISelectionChangedListener, IActionHandler {

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
		AdapterFactory adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		contentProvider = new SiLiftStructuredViewerContentProvider(adapterFactory, displayMode, compareConfiguration);
		setContentProvider(contentProvider);
		setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory));

		// Register selection listeners on tree view
		addSelectionChangedListener(this);
		compareConfiguration.getContainer().getWorkbenchPart().getSite().setSelectionProvider(this);

		// Register actions on toolbar
		final ToolBarManager toolbarManager = CompareViewerPane.getToolBarManager(parent);
		saveAction = new SaveAction(contentProvider);
		applyOnRightAction = new ApplyOnRightAction(this);
		applyOnLeftAction = new ApplyOnLeftAction(this);
		switchDirectionAction = new ToggleDirectionAction(this, contentProvider);
		showDiagramAction = new ShowDiagramAction(contentProvider);
		toolbarManager.add(applyOnRightAction);
		toolbarManager.add(switchDirectionAction);
		toolbarManager.add(applyOnLeftAction);
		toolbarManager.add(saveAction);
		toolbarManager.add(showDiagramAction);
		toolbarManager.add(new SwitchDisplayModeAction(this));
		toolbarManager.update(true);
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
		if (selection instanceof IStructuredSelection) {
			Object firstElement = ((IStructuredSelection) selection).getFirstElement();
			if (firstElement instanceof OperationInvocation) {
				// Checks if the selected item is a operation invocation
				selectedOperationInvocation = (OperationInvocation) firstElement;
			} else {
				// If the selection is no operation invocation, it is set to
				// null to make sure that updateToolbarItemStates() enables/disables
				// the correct UI elements
				selectedOperationInvocation = null;
			}
		} else {
			selectedOperationInvocation = null;
		}
		updateToolbarItemStates();
	}

	// TODO: encapsulate this in the individual actions
	@Override
	public void handle(EventObject event) {
		// TODO: left and right are actually not handled differently here?
		if (event.getSource().equals(applyOnLeftAction) || event.getSource().equals(applyOnRightAction)) {
			// apply OperationInvocation
			IRunnableWithProgress runnable = new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) throws InvocationTargetException {
					monitor.beginTask("Applying Operation", IProgressMonitor.UNKNOWN);
					try {
						contentProvider.applyOperationInvocation(selectedOperationInvocation);
					} catch (Exception e) {
						throw new InvocationTargetException(e);
					} finally {
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

			// fire ResourceChangedEvent
			notifyResourceChangeListener(contentProvider.getPatchedResource());

			// refresh GUI
			refresh();
		}
	}

	/**
	 * Adds the given listener to the internal list of listeners that will be
	 * notified when this viewer changes the resource.
	 * 
	 * @param changeListener The listener that is being notified
	 */
	public void addResourceChangeListener(ResourceChangeListener changeListener) {
		Assert.isNotNull(changeListener);
		resourceChangeListeners.add(changeListener);
	}

	/**
	 * Remove the given listener from the internal list of listeners that will
	 * be notified when this viewer changes the resource.
	 * 
	 * @param changeListener The listener that is removed
	 */
	public void removeResourceChangeListener(ResourceChangeListener changeListener) {
		Assert.isNotNull(changeListener);
		resourceChangeListeners.remove(changeListener);
	}

	/**
	 * Notifies all registered listeners that the given resource has changed.
	 * 
	 * @param compareResource The resource that was changed.
	 */
	private void notifyResourceChangeListener(CompareResource compareResource) {
		for (ResourceChangeListener changeListener : resourceChangeListeners) {
			changeListener.onResourceChanged(compareResource);
		}
	}

	public void updateToolbarItemStates() {
		final boolean modeAsymmetric = displayMode == DisplayMode.ASYMMETRIC_DIFFERENCE;
		applyOnRightAction.setEnabled(modeAsymmetric && selectedOperationInvocation != null && contentProvider.getRight().isEditable());
		applyOnLeftAction.setEnabled(modeAsymmetric && selectedOperationInvocation != null && contentProvider.getLeft().isEditable());
		switchDirectionAction.updateEnabledState();
		saveAction.updateEnabledState();
	}
}
