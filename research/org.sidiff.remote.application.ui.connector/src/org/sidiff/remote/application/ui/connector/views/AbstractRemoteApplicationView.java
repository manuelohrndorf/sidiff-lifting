package org.sidiff.remote.application.ui.connector.views;

import javax.inject.Inject;

import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.part.ViewPart;
import org.sidiff.remote.application.ui.connector.ConnectorUIPlugin;
import org.sidiff.remote.application.ui.connector.model.AdaptableTreeNode;
import org.sidiff.remote.application.ui.connector.providers.TreeModelContentProvider;
import org.sidiff.remote.application.ui.connector.providers.TreeModelLabelProvider;

public abstract class AbstractRemoteApplicationView<T extends TreeViewer> extends ViewPart implements ISelectionChangedListener, ISelectionListener {
	
	/**
	 * {@link ComposedAdapterFactory} used for determining a respective label provider for model elements
	 */
	private static ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);

	/**
	 * The {@link IWorkbench}
	 */
	@Inject IWorkbench workbench;
	
	// ########## Content and Label Providers ##########
	/**
	 * The {@link TreeModelContentProvider} for {@link #treeViewer} and {@link #checkboxTreeViewer}
	 */
	private TreeModelContentProvider treeModelContentProvider;
	
	/**
	 * The {@link TreeModelLabelProvider} for {@link #treeViewer} and {@link #checkboxTreeViewer}
	 */
	private TreeModelLabelProvider treeModelLabelProvider;
	
	// ########## UI Elements ##########
	/**
	 * The {@link Composite} parent
	 */
	protected Composite composite;
	
	/**
	 * The {@link TreeViewer} listing remote model files;
	 */
	protected T treeViewer;
	
	// ########## Actions ##########

	/**
	 * 
	 */
	private Action collapseAll_action;

	/**
	 * 
	 */
	private Action doubleClick_action;

	/**
	 * 
	 */
	private Action expandAll_action;

	/**
	 * Action for en-/disabling selection listening
	 */
	private Action syncViews_action;
	
	// ########## Current Selection ##########
	/**
	 * 
	 */
	protected AdaptableTreeNode treeViewer_selection;
	
	// ########## ViewPart ##########
	@Override
	public void createPartControl(Composite parent) {
		GridLayout gl_composite = new GridLayout();
		GridData gd_viewer = new GridData(GridData.FILL_BOTH);
		
		// initialize composite using RowLayout
		this.composite = parent;
		this.composite.setLayout(gl_composite);
		
		// initialize providers
		this.treeModelContentProvider = new TreeModelContentProvider();
		this.treeModelLabelProvider = new TreeModelLabelProvider(adapterFactory);
		
		// initialize treeViewer
		this.treeViewer = createViewer(this.composite);
		this.treeViewer.getControl().setLayoutData(gd_viewer);
		this.treeViewer.setContentProvider(this.treeModelContentProvider);
		this.treeViewer.setLabelProvider(this.treeModelLabelProvider);
		
		
		// Create the help context id for the viewer's control
		workbench.getHelpSystem().setHelp(this.treeViewer.getControl(), "org.sidiff.remote.application.ui.connector.viewer");
		
		// add selection provider and listener
		getSite().setSelectionProvider(this.treeViewer);
		this.treeViewer.addSelectionChangedListener(this);
		
		
		// make actions
		this.collapseAll_action = new Action("Collapse All", Action.AS_PUSH_BUTTON) {
			public void run() {
				treeViewer.collapseAll();
			}
		};
		this.collapseAll_action.setToolTipText("Collapse All");
		this.collapseAll_action.setImageDescriptor(ConnectorUIPlugin.IMG_ELCL16_COLLAPSEALL_ACTION);
		
		// FIXME recursively get all child elements from remote application server
		this.expandAll_action = new Action("Expand All", Action.AS_PUSH_BUTTON) {
			public void run() {
				treeViewer.expandAll();
			}
		};
		this.expandAll_action.setToolTipText("Expand All");
		this.expandAll_action.setImageDescriptor(ConnectorUIPlugin.IMG_ELCL16_EXPANDALL_ACTION);

		this.syncViews_action = new Action("Link Project Explorer", Action.AS_CHECK_BOX) {
			@Override
			public void run() {
				doListen(syncViews_action.isChecked());
			}
		};
		this.syncViews_action.setToolTipText("Link with Project Explorer");
		this.syncViews_action.setImageDescriptor(ConnectorUIPlugin.IMG_ELCL16_SYNCED_ACTION);
		
		makeActions();
		// make menu contributions
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}

	@Override
	public void setFocus() {
		treeViewer.getControl().setFocus();
	}

	
	// ########## ISelectionChangedListener ##########
	// ############ ISelectionListener ##########
	
	// ########## AbstractRemoteApplicationView ##########
	
	protected abstract T createViewer(Composite parent);
	
	protected abstract void makeActions();
	
	private void hookContextMenu() {
		MenuManager menuMgrTreeViewer = new MenuManager("#PopupMenu");
		menuMgrTreeViewer.setRemoveAllWhenShown(true);
		menuMgrTreeViewer.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				AbstractRemoteApplicationView.this.fillContextMenuTreeViewer(manager);
			}
		});
		Menu menuTreeViewer = menuMgrTreeViewer.createContextMenu(treeViewer.getControl());
		treeViewer.getControl().setMenu(menuTreeViewer);
		getSite().registerContextMenu(menuMgrTreeViewer, treeViewer);
	}

	private void hookDoubleClickAction() {
		treeViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClick_action.run();
			}
		});
	}
	
	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}
	
	protected abstract void fillContextMenuTreeViewer(IMenuManager manager);
	
	protected abstract void fillLocalPullDown(IMenuManager manager);
	
	protected void fillLocalToolBar(IToolBarManager manager) {
		manager.add(this.syncViews_action);
		manager.add(this.expandAll_action);
		manager.add(this.collapseAll_action);
	}
	
	// ########## Help Methods ##########
	private void doListen(boolean b) {
		if(b) {
			getSite().getPage().addSelectionListener(IPageLayout.ID_PROJECT_EXPLORER, this);
		}else {
			getSite().getPage().removeSelectionListener(IPageLayout.ID_PROJECT_EXPLORER, this);
		}
	}
}
