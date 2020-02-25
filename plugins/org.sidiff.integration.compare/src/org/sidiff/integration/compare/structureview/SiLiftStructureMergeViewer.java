package org.sidiff.integration.compare.structureview;

import org.eclipse.compare.CompareUI;
import org.eclipse.compare.CompareViewerPane;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;
import org.sidiff.integration.compare.SiLiftCompareConfiguration;
import org.sidiff.integration.compare.selection.SiLiftCompareSelectionController;
import org.sidiff.integration.compare.structureview.actions.AbstractAction;
import org.sidiff.integration.compare.structureview.actions.ApplyOperationAction;
import org.sidiff.integration.compare.structureview.actions.ApplyPatchOnLeftAction;
import org.sidiff.integration.compare.structureview.actions.IgnoreOperationAction;
import org.sidiff.integration.compare.structureview.actions.OperationInvocationViewerFilterAction;
import org.sidiff.integration.compare.structureview.actions.RevertOperationAction;
import org.sidiff.integration.compare.structureview.actions.ShowDiagramAction;
import org.sidiff.integration.compare.structureview.actions.ShowPropertiesViewAction;
import org.sidiff.integration.compare.structureview.actions.SwitchDisplayModeAction;
import org.sidiff.integration.compare.structureview.actions.UnignoreOperationAction;
import org.sidiff.patching.operation.OperationInvocationStatus;
import org.sidiff.patching.operation.OperationInvocationWrapper;

// TODO: Reuse further Actions from OperationExplorerView? Validation Mode, Highlighting, etc
/**
 * Used to show {@link AsymmetricDifference}s and {@link SymmetricDifference}s
 * as structured view for ecore files.
 *
 * @author Adrian Bingener
 * @author rmueller
 */
public class SiLiftStructureMergeViewer extends TreeViewer {

	private Action applyOperationAction;
	private Action revertOperationAction;
	private OperationInvocationViewerFilterAction filterAction;
	
	private IOpenListener openListener = event -> {
		if(event.getSelection() instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection)event.getSelection();
			if(selection.getFirstElement() instanceof OperationInvocationWrapper) {
				OperationInvocationWrapper wrapper = (OperationInvocationWrapper)selection.getFirstElement();
				if(wrapper.getStatus() == OperationInvocationStatus.PASSED) {
					revertOperationAction.run();
				} else if(wrapper.getStatus() != OperationInvocationStatus.IGNORED) {
					applyOperationAction.run();
				}
			}
		}
	};

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
		config.getContainer().getWorkbenchPart().getSite().setSelectionProvider(SiLiftCompareSelectionController.getInstance());
		getControl().setData(CompareUI.COMPARE_VIEWER_TITLE, "SiLift Model Differences");

		setContentProvider(new SiLiftStructureMergeViewerContentProvider(config));
		setLabelProvider(new SiLiftStructureMergeViewerLabelProvider(config.getAdapterFactory(), this));
		addSelectionChangedListener(SiLiftCompareSelectionController.getInstance());

		initToolbarActions(CompareViewerPane.getToolBarManager(parent), config);
		initContextMenu(config);

		addOpenListener(openListener);
	}

	protected void initToolbarActions(ToolBarManager toolbarManager, SiLiftCompareConfiguration config) {
		filterAction = new OperationInvocationViewerFilterAction();
		toolbarManager.add(filterAction);
		filterAction.addViewer(this);
		toolbarManager.add(new ApplyPatchOnLeftAction(config));
		toolbarManager.add(new ShowDiagramAction(config));
		toolbarManager.add(new CommandContributionItem(
				new CommandContributionItemParameter(PlatformUI.getWorkbench(), null,
						"org.sidiff.integration.editor.highlighting.commands.Toggle",
						CommandContributionItem.STYLE_CHECK)));
		toolbarManager.add(new SwitchDisplayModeAction(this, config));
		toolbarManager.update(true);
	}

	protected void initContextMenu(SiLiftCompareConfiguration config) {
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

		applyOperationAction = new ApplyOperationAction(this, config);
		menuMgr.add(applyOperationAction);
		revertOperationAction = new RevertOperationAction(this, config);
		menuMgr.add(revertOperationAction);
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
		applyOperationAction = null;
		revertOperationAction = null;
		if(filterAction != null) {
			filterAction.removeViewer(this);
			filterAction = null;
		}
		removeOpenListener(openListener);
		super.handleDispose(event);
	}

	@Override
	public void addOpenListener(IOpenListener listener) {
		// We only allow our own open listener, because the one
		// the framework provides breaks the content viewers.
		if(listener == openListener) {
			super.addOpenListener(listener);
		}
	}
}
