package org.sidiff.integration.structureview;

import org.eclipse.compare.CompareUI;
import org.eclipse.compare.CompareViewerPane;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchPart;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.integration.SiLiftCompareConfiguration;
import org.sidiff.integration.editor.highlighting.EditorHighlighting;
import org.sidiff.integration.properties.PropertySheetPageHelper;
import org.sidiff.integration.structureview.actions.AbstractAction;
import org.sidiff.integration.structureview.actions.ApplyOperationAction;
import org.sidiff.integration.structureview.actions.ApplyPatchOnLeftAction;
import org.sidiff.integration.structureview.actions.IgnoreOperationAction;
import org.sidiff.integration.structureview.actions.RevertOperationAction;
import org.sidiff.integration.structureview.actions.ShowDiagramAction;
import org.sidiff.integration.structureview.actions.ShowPropertiesViewAction;
import org.sidiff.integration.structureview.actions.SwitchDisplayModeAction;
import org.sidiff.integration.structureview.actions.UnignoreOperationAction;

// TODO: Double click on item in the structure viewer clears input of content viewer
// TODO: reuse OperationExplorerView: Double Click listener, other actions, validation mode action
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
	private SiLiftCompareConfiguration config;

	private ISelectionChangedListener selectionListener;

	/**
	 * Creates a new {@link SiLiftStructureMergeViewer} with the given compare
	 * configuration.
	 *
	 * @param parent The parent composite that holds this view
	 * @param config Configuration to be used when comparing the
	 *            inputs
	 */
	public SiLiftStructureMergeViewer(Composite parent, SiLiftCompareConfiguration config) {
		super(parent);

		this.config = config;
		getControl().setData(CompareUI.COMPARE_VIEWER_TITLE, "SiLift Viewer");

		// Create custom content provider that takes two ecore files and shows
		// lifted differences in this TreeView
		contentProvider = new SiLiftStructureMergeViewerContentProvider(config);
		setContentProvider(contentProvider);
		setLabelProvider(new SiLiftStructureMergeViewerLabelProvider(config.getAdapterFactory(), this));

		// Register selection listeners on tree view
		final IWorkbenchPart part = config.getContainer().getWorkbenchPart();
		part.getSite().setSelectionProvider(this);

		// Register actions on toolbar and context menu
		initToolbarActions(CompareViewerPane.getToolBarManager(parent));
		initContextMenu();

		selectionListener = new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				PropertySheetPageHelper.notifiySelectionChanged(part, event.getSelection());
			}
		};
		addSelectionChangedListener(selectionListener);
		addSelectionChangedListener(EditorHighlighting.getInstance().getSelectionChangedListener());
	}

	protected void initToolbarActions(ToolBarManager toolbarManager) {
		toolbarManager.add(new ApplyPatchOnLeftAction(config));
		toolbarManager.add(new ShowDiagramAction(config));
		toolbarManager.add(new SwitchDisplayModeAction(this, config));
		toolbarManager.update(true);
	}

	protected void initContextMenu() {
		MenuManager menuMgr = new MenuManager();
		Menu menu = menuMgr.createContextMenu(getControl());
		menu.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				for(IContributionItem item : menuMgr.getItems()) {
					if(item instanceof ActionContributionItem) {
						IAction action = ((ActionContributionItem)item).getAction();
						if(action instanceof AbstractAction) {
							((AbstractAction)action).dispose();
						}
					}
				}
			}
		});

		menuMgr.add(new ApplyOperationAction(this, config));
		menuMgr.add(new RevertOperationAction(this, config));
		menuMgr.add(new IgnoreOperationAction(this, config));
		menuMgr.add(new UnignoreOperationAction(this, config));
		menuMgr.add(new ShowPropertiesViewAction());

		menuMgr.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				for(IContributionItem item : manager.getItems()) {
					if(item instanceof ActionContributionItem) {
						IAction action = ((ActionContributionItem)item).getAction();
						if(action instanceof AbstractAction) {
							item.setVisible(((AbstractAction)action).isVisible());
						}
					}
				}
				manager.update(true);
			}
		});

		getControl().setMenu(menu);
	}

	@Override
	protected void handleDispose(DisposeEvent event) {
		if(config.getContainer().getWorkbenchPart().getSite().getSelectionProvider() == this) {
			config.getContainer().getWorkbenchPart().getSite().setSelectionProvider(null);
		}
		if(selectionListener != null) {
			removeSelectionChangedListener(selectionListener);
			selectionListener = null;
		}
		super.handleDispose(event);
	}
}
