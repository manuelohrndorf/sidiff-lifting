package org.sidiff.vcmsintegration.structureview;

import org.eclipse.compare.CompareUI;
import org.eclipse.compare.CompareViewerPane;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.widgets.Composite;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.vcmsintegration.SiLiftCompareConfiguration;
import org.sidiff.vcmsintegration.structureview.actions.ApplyOnLeftAction;
import org.sidiff.vcmsintegration.structureview.actions.ShowDiagramAction;
import org.sidiff.vcmsintegration.structureview.actions.SwitchDisplayModeAction;

// TODO: reuse OperationExplorerView
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

	private ApplyOnLeftAction applyOnLeftAction;

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
		applyOnLeftAction = new ApplyOnLeftAction(compareConfiguration);
		addSelectionChangedListener(applyOnLeftAction);
		toolbarManager.add(applyOnLeftAction);
		toolbarManager.add(new ShowDiagramAction(contentProvider));
		toolbarManager.add(new SwitchDisplayModeAction(this, compareConfiguration));
		toolbarManager.update(true);
	}

	@Override
	protected void handleDispose(DisposeEvent event) {
		if(compareConfiguration.getContainer().getWorkbenchPart().getSite().getSelectionProvider() == this) {
			compareConfiguration.getContainer().getWorkbenchPart().getSite().setSelectionProvider(null);
		}
		removeSelectionChangedListener(applyOnLeftAction);
		super.handleDispose(event);
	}
}
