package org.sidiff.remote.application.ui.connector.views;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.sidiff.remote.application.connector.ConnectorFacade;
import org.sidiff.remote.application.connector.exception.ConnectionException;
import org.sidiff.remote.application.connector.exception.InvalidSessionException;
import org.sidiff.remote.application.connector.exception.ModelNotVersionedException;
import org.sidiff.remote.application.connector.exception.RemoteApplicationException;
import org.sidiff.remote.application.connector.settings.CheckoutSettings;
import org.sidiff.remote.application.connector.settings.RepositorySettings;
import org.sidiff.remote.application.ui.connector.ConnectorUIPlugin;
import org.sidiff.remote.application.ui.connector.model.AdaptableTreeModel;
import org.sidiff.remote.application.ui.connector.model.AdaptableTreeNode;
import org.sidiff.remote.application.ui.connector.model.ModelUtil;
import org.sidiff.remote.application.ui.connector.providers.TreeModelContentProvider;
import org.sidiff.remote.application.ui.connector.providers.TreeModelLabelProvider;
import org.sidiff.remote.application.ui.connector.wizards.AddRepositoryLocationWizard;
import org.sidiff.remote.application.ui.connector.wizards.CheckoutSubModelWizard;
import org.sidiff.remote.common.ProxyObject;
import org.sidiff.remote.common.settings.IRemotePreferencesSupplier;
import org.sidiff.remote.common.util.ProxyUtil;


/**
 * 
 * @author cpietsch
 * 
 */
public class SiDiffModelRepositoryView extends ViewPart implements ISelectionChangedListener, ICheckStateListener, ISelectionListener {

	public static final ImageDescriptor IMG_ADD_REPOSITORY = ConnectorUIPlugin.getImageDescriptor("full/obj16/add_repo.png");
	public static final ImageDescriptor IMG_SYNC_SELECTION = ConnectorUIPlugin.getImageDescriptor("full/obj16/synced.gif");
	public static final ImageDescriptor IMG_BROWSE_FILES = ConnectorUIPlugin.getImageDescriptor("full/obj16/refresh_remote.gif");
	public static final ImageDescriptor IMG_BROWSE_MODEL = ConnectorUIPlugin.getImageDescriptor("full/obj16/refresh.gif");
	public static final ImageDescriptor IMG_CHECKOUT = ConnectorUIPlugin.getImageDescriptor("full/obj16/checkout.gif");
	public static final ImageDescriptor IMG_UPDATE = ConnectorUIPlugin.getImageDescriptor("full/obj16/update.gif");
	public static final ImageDescriptor IMG_SYNCH = ConnectorUIPlugin.getImageDescriptor("full/obj16/synch.gif");
	public static final ImageDescriptor IMG_EXPANDALL = ConnectorUIPlugin.getImageDescriptor("full/obj16/expandall.gif");
	public static final ImageDescriptor IMG_COLLAPSEALL = ConnectorUIPlugin.getImageDescriptor("full/obj16/collapseall.gif");
	public static final ImageDescriptor IMG_SELECT_SUBTREE = ConnectorUIPlugin.getImageDescriptor("full/obj16/select_subtree.gif");
	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "org.sidiff.remote.application.ui.connector.views.SiDiffModelRepositoryView";
	
	@Inject IWorkbench workbench;
	
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
	 * Action for adding a repository
	 */
	private Action addRepository_action;
	
	/**
	 * Action for en-/disabling selection listening
	 */
	private Action syncSelection_action;
	
	/**
	 * Action for browsing the remote model files
	 */
	private Action refresh_action;
	
	/**
	 * 
	 */
	private Action selectSubTreeAction;
	
	/**
	 * 
	 */
	private Action expandAllAction;
	
	/**
	 * 
	 */
	private Action collapseAllAction;
	
	/**
	 * 
	 */
	private Action checkoutAction;
	
	/**
	 * 
	 */
	private Action updateAction;
	
	/**
	 * 
	 */
	private Action doubleClickAction;
	
	/**
	 * 
	 */
	private AdaptableTreeNode treeViewer_selection;
	
	/**
	 * 
	 */
	private String selected_model_path;

	@Override
	public void createPartControl(Composite parent) {
		
		// initialize composite using RowLayout
		this.composite = parent;
		GridLayout gl_composite = new GridLayout();
		this.composite.setLayout(gl_composite);
		
		GridData gd_viewer = new GridData(GridData.FILL_BOTH);
		
		// initialize providers
		this.treeModelContentProvider = new TreeModelContentProvider();
		this.treeModelLabelProvider = new TreeModelLabelProvider(SiDiffModelRepositoryView.adapterFactory);
		
		// initialize treeViewer
		this.treeViewer = new TreeViewer(composite, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		this.treeViewer.getControl().setLayoutData(gd_viewer);
		this.treeViewer.setContentProvider(this.treeModelContentProvider);
		this.treeViewer.setLabelProvider(this.treeModelLabelProvider);
		this.treeViewer.addSelectionChangedListener(this);
		
		// initialize checkboxTreeViewer
		this.checkboxTreeViewer = new CheckboxTreeViewer(composite, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		this.checkboxTreeViewer.getControl().setLayoutData(gd_viewer);
		this.checkboxTreeViewer.setContentProvider(this.treeModelContentProvider);
		this.checkboxTreeViewer.setLabelProvider(this.treeModelLabelProvider);
		this.checkboxTreeViewer.addCheckStateListener(this);
		this.checkboxTreeViewer.addSelectionChangedListener(this);
		
		// Create the help context id for the viewer's control
		workbench.getHelpSystem().setHelp(treeViewer.getControl(), "org.sidiff.remote.application.ui.connector.viewer");
		
		// add selection provider and listener
		getSite().setSelectionProvider(treeViewer);
		getSite().setSelectionProvider(checkboxTreeViewer);
		getSite().getPage().addSelectionListener(this);
		
		// make actions and menu contributions
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}

	private void hookContextMenu() {
		MenuManager menuMgrTreeViewer = new MenuManager("#PopupMenu");
		menuMgrTreeViewer.setRemoveAllWhenShown(true);
		menuMgrTreeViewer.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				SiDiffModelRepositoryView.this.fillContextMenuTreeViewer(manager);
			}
		});
		Menu menuTreeViewer = menuMgrTreeViewer.createContextMenu(treeViewer.getControl());
		treeViewer.getControl().setMenu(menuTreeViewer);
		getSite().registerContextMenu(menuMgrTreeViewer, treeViewer);
		MenuManager menuMgrCheckboxTreeViewer = new MenuManager("#PopupMenu");
		menuMgrCheckboxTreeViewer.setRemoveAllWhenShown(true);
		menuMgrCheckboxTreeViewer.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				SiDiffModelRepositoryView.this.fillContextMenuCheckboxTreeViewer(manager);
			}
		});
		Menu menuCheckboxTreeViewer = menuMgrCheckboxTreeViewer.createContextMenu(checkboxTreeViewer.getControl());
		checkboxTreeViewer.getControl().setMenu(menuCheckboxTreeViewer);
		getSite().registerContextMenu(menuMgrCheckboxTreeViewer, checkboxTreeViewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {

	}

	private void fillContextMenuTreeViewer(IMenuManager manager) {
		manager.add(new Separator());

		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillContextMenuCheckboxTreeViewer(IMenuManager manager) {
		manager.add(this.selectSubTreeAction);
		manager.add(new Separator());

		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(this.addRepository_action);
		manager.add(this.syncSelection_action);
		manager.add(this.expandAllAction);
		manager.add(this.collapseAllAction);
		manager.add(this.refresh_action);
		manager.add(this.checkoutAction);
		manager.add(this.updateAction);
		manager.add(new Separator());
	}

	private void makeActions() {
		
		this.addRepository_action = new Action("Add Repository", Action.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				RepositorySettings settings = new RepositorySettings();
				
				AddRepositoryLocationWizard addRepositoryLocationWizard = new AddRepositoryLocationWizard(settings);
				WizardDialog wizardDialog = new WizardDialog(PlatformUI
						.getWorkbench().getActiveWorkbenchWindow().getShell(),
						addRepositoryLocationWizard);
				wizardDialog.setBlockOnOpen(true);
				wizardDialog.open();
				if(addRepositoryLocationWizard.getException() != null) {
					Exception e = addRepositoryLocationWizard.getException();
					MessageDialog.openError(composite.getShell(), e.getClass().getSimpleName(), e.getMessage());
				}
			}
		};
		this.addRepository_action.setToolTipText("Adds a repository");
		this.addRepository_action.setImageDescriptor(IMG_ADD_REPOSITORY);
		
		this.syncSelection_action = new Action("Syncronize Selection", Action.AS_CHECK_BOX) {
			@Override
			public void run() {
				doListen(syncSelection_action.isChecked());
			}
		};
		this.syncSelection_action.setToolTipText("Syncronize view with selection");
		this.syncSelection_action.setImageDescriptor(IMG_SYNC_SELECTION);
		
		this.refresh_action = new Action() {
			public void run() {
				try {
					AdaptableTreeModel model = new AdaptableTreeModel();
					List<ProxyObject> proxyObjects = ConnectorFacade.browseRemoteApplicationContent("",null);
					List<AdaptableTreeNode> treeNodes = ModelUtil.transform(proxyObjects);
					model.getRoot().addAllChildren(treeNodes);
					treeViewer.setInput(model);
					checkboxTreeViewer.setInput(null);
				} catch (ConnectionException | RemoteApplicationException e) {
					MessageDialog.openError(composite.getShell(), e.getClass().getSimpleName(), e.getMessage());
				}
			}
		};
		this.refresh_action.setText("Browse Repository");
		this.refresh_action.setToolTipText("Browse remote model repository");
		this.refresh_action.setImageDescriptor(IMG_BROWSE_FILES);
		
		
		this.selectSubTreeAction = new Action() {
			public void run(){
				for(TreeItem treeItem : checkboxTreeViewer.getTree().getSelection()){
					checkboxTreeViewer.setSubtreeChecked(treeItem.getData(), true);
				}
			}
		};
		this.selectSubTreeAction.setText("Select Subtree");
		this.selectSubTreeAction.setImageDescriptor(IMG_SELECT_SUBTREE);
		
		this.expandAllAction = new Action() {
			public void run() {
				treeViewer.expandAll();
				checkboxTreeViewer.expandAll();
			}
		};
		this.expandAllAction.setToolTipText("expand all");
		this.expandAllAction.setImageDescriptor(IMG_EXPANDALL);


		this.collapseAllAction = new Action() {
			public void run() {
				treeViewer.collapseAll();
				checkboxTreeViewer.collapseAll();
			}
		};
		this.collapseAllAction.setToolTipText("collapse all");
		this.collapseAllAction.setImageDescriptor(IMG_COLLAPSEALL);
	
		this.checkoutAction = new Action() {
			
			@Override
			public void run() {
				CheckoutSettings settings = new CheckoutSettings();
				
				WizardDialog wizardDialog = new WizardDialog(PlatformUI
						.getWorkbench().getActiveWorkbenchWindow().getShell(),
						new CheckoutSubModelWizard(settings));
				wizardDialog.setBlockOnOpen(true);
				int return_code = wizardDialog.open();
				if(return_code == WizardDialog.OK) {
					String remote_model_path = treeViewer_selection.getId();
					String target_model_path = settings.getTargetPath().toOSString() + File.separator + treeViewer_selection.getLabel();
					Set<String> elementIds = getSelectedElementIDs();
					try {
						File file = ConnectorFacade.checkoutSubModel(remote_model_path, target_model_path, elementIds, IRemotePreferencesSupplier.getDefaultRemotePreferences());
						IResource resource = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(new Path(file.getAbsolutePath()));
						resource.getParent().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
					} catch (ConnectionException | CoreException | InvalidSessionException | RemoteApplicationException e) {
						MessageDialog.openError(composite.getShell(), e.getClass().getSimpleName(), e.getMessage());
					}
				}
			}
		};
		this.checkoutAction.setText("Check out (Sub-) Model");
		this.checkoutAction.setToolTipText("Check out new (sub-) model");
		this.checkoutAction.setImageDescriptor(IMG_CHECKOUT);

		this.updateAction = new Action() {
			@Override
			public void run() {
				try {
					Set<String> elementIds = getSelectedElementIDs();
					if(!elementIds.isEmpty()) {
						String remote_model_path = treeViewer_selection.getId();
						File file = ConnectorFacade.updateSubModel(remote_model_path, selected_model_path, elementIds, IRemotePreferencesSupplier.getDefaultRemotePreferences());
						IResource resource = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(file.getAbsolutePath().replace(ResourcesPlugin.getWorkspace().getRoot().getLocation().toOSString() + File.separator, "")));
						resource.getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
					}
				} catch (ConnectionException | CoreException | InvalidSessionException e) {
					MessageDialog.openError(composite.getShell(), e.getClass().getSimpleName(), e.getMessage());
				}
				super.run();
			}
		};
		this.updateAction.setText("Update (Sub-) Model");
		this.updateAction.setToolTipText("Update local (sub-) model");
		this.updateAction.setImageDescriptor(IMG_UPDATE);
		
		this.doubleClickAction = new Action() {
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
		Object element = event.getElement();
		if(this.checkboxTreeViewer.getGrayed(element)){
			this.checkboxTreeViewer.setGrayChecked(element, true);
		}else if(event.getChecked()){
			 ITreeContentProvider provider = (ITreeContentProvider)this.checkboxTreeViewer.getContentProvider();
			 Object parent = provider.getParent(element);
			 while(parent != null){
				 this.checkboxTreeViewer.setChecked(parent, true);
				 parent = provider.getParent(parent);
			 }
		}else if(!event.getChecked()){
			this.checkboxTreeViewer.setSubtreeChecked(element, false);
		}
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if(selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			if(structuredSelection.size() == 1 && structuredSelection.getFirstElement() instanceof IFile) {
				IFile file = (IFile) structuredSelection.getFirstElement();

				try {
					String local_model_path = file.getLocation().toOSString();
					updateRequestedModelElements(local_model_path);
					selected_model_path = local_model_path;
				} catch (ConnectionException | InvalidSessionException | RemoteApplicationException e) {
					MessageDialog.openError(composite.getShell(), e.getClass().getSimpleName(), e.getMessage());
				}catch ( ModelNotVersionedException e) {
					
				}
				
			}
		}
	}
	
	private Set<String> getSelectedElementIDs(){
		Set<String> elementIds = new HashSet<String>();
		for(Object element : checkboxTreeViewer.getCheckedElements()) {
			AdaptableTreeNode treeNode = (AdaptableTreeNode) element;
			elementIds.add(treeNode.getId());
		}
		return elementIds;
	}
	
	private void doListen(boolean b) {
		if(b) {
			getSite().getPage().addSelectionListener(this);
		}else {
			getSite().getPage().removeSelectionListener(this);
		}
	}
	
	private void updateRequestedModelElements(String localModelPath) throws ConnectionException, InvalidSessionException, RemoteApplicationException, ModelNotVersionedException {
		
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		String local_relative_model_path = localModelPath.replace(workspace.getRoot().getLocation().toOSString() + File.separator, "");
		String remoteModelPath = ConnectorFacade.getSession().getRemoteModelPath(local_relative_model_path);
		
		List<ProxyObject> proxyObjectsFiles = ConnectorFacade.getRequestedModelFile(remoteModelPath);
		
		AdaptableTreeModel treeModelFiles = null;
		if(treeViewer.getInput() == null) {
			treeModelFiles = new AdaptableTreeModel();
			treeViewer.setInput(treeModelFiles);
		}else {
			treeModelFiles = (AdaptableTreeModel) treeViewer.getInput();
		}
		
		List<ProxyObject> proxyFiles = new ArrayList<ProxyObject>();

		for(ProxyObject proxyObject : proxyObjectsFiles) {
			proxyFiles.addAll(ProxyUtil.sort(proxyObject));
		}
	
		List<AdaptableTreeNode> visibleModelFileNodes = new ArrayList<AdaptableTreeNode>();
		
		AdaptableTreeNode selectedFileNode = null;
		
		for(ProxyObject proxyObject : proxyFiles) {
			
			AdaptableTreeNode currentFileNode = null;
			if(treeModelFiles.getTreeNode(proxyObject.getId()) == null) {
				AdaptableTreeNode parent = null;
				if(proxyObject.getParent() == null) {
					parent = treeModelFiles.getRoot();
				}else {
					parent = treeModelFiles.getTreeNode(proxyObject.getParent().getId());
				}
				currentFileNode = new AdaptableTreeNode(proxyObject.getLabel(), proxyObject.getId(), proxyObject.getType(), !proxyObject.isContainer(), parent);
			}else {
				currentFileNode = treeModelFiles.getTreeNode(proxyObject.getId());
			}
			visibleModelFileNodes.add(currentFileNode);
			if(currentFileNode.getId().equals(remoteModelPath)) {
				selectedFileNode = currentFileNode;
			}
		}
		
		treeViewer.setExpandedElements(visibleModelFileNodes.toArray());
		treeViewer.setSelection(new StructuredSelection(selectedFileNode), true);
		
		List<ProxyObject> proxyObjectsElement = ConnectorFacade.getRequestedModelElements(remoteModelPath, localModelPath);
		
		AdaptableTreeModel treeModelElements = null;
		if(checkboxTreeViewer.getInput() == null) {
			treeModelElements = new AdaptableTreeModel();
			checkboxTreeViewer.setInput(treeModelElements);
		}else {
			treeModelElements = (AdaptableTreeModel) checkboxTreeViewer.getInput();
		}
		
		List<ProxyObject> proxyElements = new ArrayList<ProxyObject>();

		for(ProxyObject proxyObject : proxyObjectsElement) {
			proxyElements.addAll(ProxyUtil.sort(proxyObject));
		}
		
		List<AdaptableTreeNode> visibleModelElementNodes = new ArrayList<AdaptableTreeNode>();
		
		for(ProxyObject proxyObject : proxyElements) {
			
			AdaptableTreeNode currentElementNode = null;
			if(treeModelElements.getTreeNode(proxyObject.getId()) == null) {
				AdaptableTreeNode parent = null;
				if(proxyObject.getParent() == null) {
					parent = treeModelElements.getRoot();
				}else {
					parent = treeModelElements.getTreeNode(proxyObject.getParent().getId());
				}
				currentElementNode = new AdaptableTreeNode(proxyObject.getLabel(), proxyObject.getId(), proxyObject.getType(), !proxyObject.isContainer(), parent, proxyObject.isSelected());
			}else {
				currentElementNode = treeModelElements.getTreeNode(proxyObject.getId());
				currentElementNode.setSelected(proxyObject.isSelected());
			}
			if(currentElementNode.isSelected()) {
				visibleModelElementNodes.add(currentElementNode);
			}
		}
		checkboxTreeViewer.setExpandedElements(visibleModelElementNodes.toArray());
		checkboxTreeViewer.setCheckedElements(visibleModelElementNodes.toArray());		
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		ISelection selection = event.getSelection();
		if(selection instanceof StructuredSelection) {
			StructuredSelection structuredSelection = (StructuredSelection) selection;
			if(structuredSelection.size() == 1 ) {
				AdaptableTreeNode treeNode = (AdaptableTreeNode) structuredSelection.getFirstElement();
				
				if(event.getSource().equals(this.treeViewer) && (this.treeViewer_selection == null || !this.treeViewer_selection.equals(treeNode))) {
					this.treeViewer_selection = treeNode;
					if(!(treeNode.isLeaf())) {
						if(treeNode.getChildren().isEmpty()) {
							try {
								List<ProxyObject> proxyObjects = ConnectorFacade.browseRemoteApplicationContent(treeNode.getId(), null);
								List<AdaptableTreeNode> children = ModelUtil.transform(proxyObjects);
								treeNode.addAllChildren(children);
								this.treeViewer.refresh();
							} catch (ConnectionException | RemoteApplicationException e) {
								MessageDialog.openError(composite.getShell(), e.getClass().getSimpleName(), e.getMessage());
								e.printStackTrace();
							}
						}
						this.checkboxTreeViewer.setInput(null);
					}else {
						AdaptableTreeModel treeModel = new AdaptableTreeModel();
						
						try {
							List<ProxyObject> proxyObjects = ConnectorFacade.browseRemoteApplicationContent(treeNode.getId(), null);
							List<AdaptableTreeNode> children = ModelUtil.transform(proxyObjects);
							treeModel.getRoot().addAllChildren(children);
							this.checkboxTreeViewer.setInput(treeModel);
						} catch (ConnectionException | RemoteApplicationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				}else if(event.getSource().equals(this.checkboxTreeViewer) && treeNode.getChildren().isEmpty()){
		
					try {
						String session_path = ((AdaptableTreeNode) this.treeViewer.getStructuredSelection()
								.getFirstElement()).getId();
						List<ProxyObject> proxyObjects = ConnectorFacade.browseRemoteApplicationContent(session_path,
								treeNode.getId());
						List<AdaptableTreeNode> children = ModelUtil.transform(proxyObjects);
						treeNode.addAllChildren(children);
						this.checkboxTreeViewer.refresh();

					} catch (ConnectionException | RemoteApplicationException e) {
						MessageDialog.openError(composite.getShell(), e.getClass().getSimpleName(), e.getMessage());
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	@Override
	public void dispose() {
		getSite().getPage().removeSelectionListener(this);
		super.dispose();
	}
}
