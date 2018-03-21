package org.sidiff.remote.application.ui.connector.views;

import java.io.IOException;

import javax.inject.Inject;

import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.part.ViewPart;
import org.sidiff.remote.application.connector.ConnectionHandler;
import org.sidiff.remote.application.ui.connector.Activator;
import org.sidiff.remote.application.ui.connector.providers.TreeModelContentProvider;
import org.sidiff.remote.application.ui.connector.providers.TreeModelLabelProvider;
import org.sidiff.remote.common.Command;
import org.sidiff.remote.common.tree.TreeLeaf;


/**
 * 
 * @author cpietsch
 * 
 */
public class SiDiffModelRepositoryView extends ViewPart implements ICheckStateListener {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "org.sidiff.remote.application.ui.connector.views.SiDiffModelRepositoryView";
	
	@Inject IWorkbench workbench;
	
	private static ConnectionHandler connectionHandler = ConnectionHandler.getInstance();
	
	/**
	 * {@link ComposedAdapterFactory} used for determining a respective label provider for model elements
	 */
	private static ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);

	/**
	 * The {@link TreeModelContentProvider} for {@link #treeViewer} and {@link #checkboxTreeViewer}
	 */
	private TreeModelContentProvider treeModelContentProvider;
	
	/**
	 * The {@link TreeModelLabelProvider} for {@link #treeViewer} and {@link #checkboxTreeViewer}
	 */
	private TreeModelLabelProvider treeModelLabelProvider;
	
	/**
	 * The {@link Composite} parent
	 */
	private Composite composite;
	
	/**
	 * The {@link TreeViewer} listing remote model files;
	 */
	private TreeViewer treeViewer;
	
	/**
	 * The {@link CheckboxTreeViewer} for selecting elements of a remote model
	 */
	private CheckboxTreeViewer checkboxTreeViewer; 
	
	/**
	 * Action for browsing the remote model files
	 */
	private Action browseFiles_action;
	
	/**
	 * Action for browsing a specific remote model file
	 */
	private Action browseModel_action;
	
	/**
	 * 
	 */
	private Action doubleClickAction;
	 

	@Override
	public void createPartControl(Composite parent) {
		
		// initialize composite using FillLayout
		this.composite = parent;
		this.composite.setLayout(new FillLayout(SWT.VERTICAL));
		
		// initialize providers
		this.treeModelContentProvider = new TreeModelContentProvider();
		this.treeModelLabelProvider = new TreeModelLabelProvider(SiDiffModelRepositoryView.adapterFactory);
		
		// initialize treeViewer
		this.treeViewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);	
		this.treeViewer.setContentProvider(this.treeModelContentProvider);
		this.treeViewer.setLabelProvider(this.treeModelLabelProvider);
		this.treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = event.getStructuredSelection();
				if(selection.size() == 1 && selection.getFirstElement() instanceof TreeLeaf) {
					browseModel_action.setEnabled(true);
				}else {
					browseModel_action.setEnabled(false);
				}
				
			}
		});
		
		// initialize checkboxTreeViewer
		this.checkboxTreeViewer = new CheckboxTreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		this.checkboxTreeViewer.setContentProvider(this.treeModelContentProvider);
		this.checkboxTreeViewer.setLabelProvider(this.treeModelLabelProvider);
		this.checkboxTreeViewer.addCheckStateListener(this);

		
		// Create the help context id for the viewer's control
		workbench.getHelpSystem().setHelp(treeViewer.getControl(), "org.sidiff.remote.application.ui.connector.viewer");
		getSite().setSelectionProvider(treeViewer);
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				SiDiffModelRepositoryView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(treeViewer.getControl());
		treeViewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, treeViewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {

	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(browseModel_action);
		manager.add(new Separator());

		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(this.browseFiles_action);
		manager.add(new Separator());
	}

	private void makeActions() {
		this.browseFiles_action = new Action() {
			public void run() {
				try {
					Object object = connectionHandler.handleCommand(Command.BROWSE_MODEL_FILES, null);
					treeViewer.setInput(object);
					checkboxTreeViewer.getTree().clearAll(true);
				} catch (ClassNotFoundException | IOException e) {
					MessageDialog.openError(composite.getShell(), e.getClass().getSimpleName(), e.getMessage());
				}
			}
		};
		this.browseFiles_action.setText("Browse Repository");
		this.browseFiles_action.setToolTipText("Browse remote model repository");
		this.browseFiles_action.setImageDescriptor(Activator.getImageDescriptor("refresh_remote.gif"));
		
		this.browseModel_action = new Action() {
			
			public void run() {
				IStructuredSelection selection = treeViewer.getStructuredSelection();
				
				Object obj = selection.getFirstElement();
				if (obj instanceof TreeLeaf) {
					try {
						Object object = connectionHandler.handleCommand(Command.BROWSE_MODEL, ((TreeLeaf) obj).getId());
						checkboxTreeViewer.setInput(object);
					} catch (ClassNotFoundException | IOException e) {
						MessageDialog.openError(composite.getShell(), e.getClass().getSimpleName(), e.getMessage());
					}
				} else {
					MessageDialog.openError(composite.getShell(), "No Model File", "Please select a model file");
				}
			}
		};
		this.browseModel_action.setText("Browse Model");
		this.browseModel_action.setToolTipText("Browse remote model file");
		this.browseModel_action.setEnabled(false);
		
		doubleClickAction = new Action() {
			public void run() {
				//TODO
			}
		};
	}

	private void hookDoubleClickAction() {
		treeViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}

	@Override
	public void setFocus() {
		treeViewer.getControl().setFocus();
	}

	@Override
	public void checkStateChanged(CheckStateChangedEvent event) {
		// TODO Auto-generated method stub
		
	}
}
