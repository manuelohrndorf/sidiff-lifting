package org.sidiff.vcmsintegration.structureview;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.compare.CompareUI;
import org.eclipse.compare.CompareViewerPane;
import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.widgets.Composite;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.vcmsintegration.ResourceChangeListener;
import org.sidiff.vcmsintegration.SiLiftCompareConfiguration;
import org.sidiff.vcmsintegration.remote.CompareResource;
import org.sidiff.vcmsintegration.structureview.actions.ApplyOnLeftAction;
import org.sidiff.vcmsintegration.structureview.actions.SaveAction;
import org.sidiff.vcmsintegration.structureview.actions.ShowDiagramAction;
import org.sidiff.vcmsintegration.structureview.actions.SwitchDisplayModeAction;
import org.sidiff.vcmsintegration.structureview.actions.ToggleDirectionAction;

/**
 * Used to show {@link AsymmetricDifference}s and {@link SymmetricDifference}s
 * as structured view for ecore files.
 *
 * @author Adrian Bingener, Robert Müller
 */
public class SiLiftStructureMergeViewer extends TreeViewer {

	/**
	 * Provides the elements for this {@link TreeViewer} by the two given input
	 * resources that are being compared.
	 */
	private SiLiftStructureMergeViewerContentProvider contentProvider;

	/**
	 * The configuration that controls various UI aspects of compare/merge
	 * viewers like title labels and images.
	 */
	private SiLiftCompareConfiguration compareConfiguration;

	/**
	 * A list of all listeners that will be notified if this tree view changes
	 * the resource that are loaded.
	 */
	private List<ResourceChangeListener> resourceChangeListeners;

	/**
	 * Creates a new {@link SiLiftStructureMergeViewer} with the given compare
	 * configuration.
	 *
	 * @param parent The parent composite that holds this view
	 * @param compareConfiguration Configuration to be used when comparing the
	 *            inputs
	 */
	public SiLiftStructureMergeViewer(Composite parent, SiLiftCompareConfiguration compareConfiguration) {
		super(parent);

		this.compareConfiguration = compareConfiguration;
		this.resourceChangeListeners = new ArrayList<ResourceChangeListener>();
		getControl().setData(CompareUI.COMPARE_VIEWER_TITLE, "SiLift Viewer");

		// Create custom content provider that takes two ecore files and shows
		// lifted differences in this TreeView
		AdapterFactory adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		contentProvider = new SiLiftStructureMergeViewerContentProvider(adapterFactory, compareConfiguration);
		setContentProvider(contentProvider);
		setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory));

		// Register selection listeners on tree view
		compareConfiguration.getContainer().getWorkbenchPart().getSite().setSelectionProvider(this);

		// Register actions on toolbar
		initToolbarActions(CompareViewerPane.getToolBarManager(parent));
	}

	protected void initToolbarActions(ToolBarManager toolbarManager) {
		toolbarManager.add(new SaveAction(contentProvider));
		ApplyOnLeftAction applyOnLeftAction = new ApplyOnLeftAction(this, contentProvider, compareConfiguration);
		addSelectionChangedListener(applyOnLeftAction);
		toolbarManager.add(applyOnLeftAction);
		toolbarManager.add(new ToggleDirectionAction(this, contentProvider, compareConfiguration));
		toolbarManager.add(new ShowDiagramAction(contentProvider));
		toolbarManager.add(new SwitchDisplayModeAction(this, contentProvider, compareConfiguration));
		toolbarManager.update(true);
	}

	/**
	 * Refreshs the Content Provider and notifies all registered listeners
	 * to refresh as well.
	 * Used if a model is modified in the Editor Integration while comparing it.
	 * @author Daniel Roedder
	 */
	public void onRefreshNotify() {
		notifyResourceChangeListener(contentProvider.getLeft());
		notifyResourceChangeListener(contentProvider.getRight());
		notifyResourceChangeListener(contentProvider.getAncestor());
		refresh();
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
	public void notifyResourceChangeListener(CompareResource compareResource) {
		for (ResourceChangeListener changeListener : resourceChangeListeners) {
			changeListener.onResourceChanged(compareResource);
		}
	}

	@Override
	protected void handleDispose(DisposeEvent event) {
		if(compareConfiguration.getContainer().getWorkbenchPart().getSite().getSelectionProvider() == this) {
			compareConfiguration.getContainer().getWorkbenchPart().getSite().setSelectionProvider(null);
		}
		super.handleDispose(event);
	}
}
