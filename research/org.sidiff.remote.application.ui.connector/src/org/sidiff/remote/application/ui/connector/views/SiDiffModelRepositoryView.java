package org.sidiff.remote.application.ui.connector.views;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
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
import org.sidiff.remote.application.connector.exception.InvalidProjectInfoException;
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

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "org.sidiff.remote.application.ui.connector.views.SiDiffModelRepositoryView";
	
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
	private Composite composite;
	
	/**
	 * The {@link TreeViewer} listing remote model files;
	 */
	private TreeViewer treeViewer;
	
	/**
	 * The {@link CheckboxTreeViewer} for selecting elements of a remote model
	 */
	private CheckboxTreeViewer checkboxTreeViewer; 
	
	// ########## Actions ##########
	/**
	 * 
	 */
	private Action checkout_action;
	
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
	 * Action for adding a new repository location
	 */
	private Action newLocation_action;
	
	/**
	 * 
	 */
	private Action selectSubTree_action;
	
	/**
	 * Action for en-/disabling selection listening
	 */
	private Action syncViews_action;
	
	/**
	 * Action for refreshing the remote model view
	 */
	private Action refreshRemote_action;
	
	/**
	 * 
	 */
	private Action update_action;
	
	// ########## Current Selection ##########
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
		
		GridLayout gl_composite = new GridLayout();
		GridData gd_viewer = new GridData(GridData.FILL_BOTH);
		
		// initialize composite using RowLayout
		this.composite = parent;
		this.composite.setLayout(gl_composite);
		
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

	@Override
	public void dispose() {
		getSite().getPage().removeSelectionListener(this);
		super.dispose();
	}

	@Override
	public void setFocus() {
		treeViewer.getControl().setFocus();
	}
	
	// ########## ICheckStateListener ##########
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
	
	// ########## ISelectionChangedListener ############
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		ISelection selection = event.getSelection();
		if (selection instanceof StructuredSelection) {
			StructuredSelection structuredSelection = (StructuredSelection) selection;
			if (structuredSelection.size() == 1) {
				AdaptableTreeNode treeNode = (AdaptableTreeNode) structuredSelection.getFirstElement();

				if (event.getSource().equals(this.treeViewer)
						&& (this.treeViewer_selection == null || !this.treeViewer_selection.equals(treeNode))) {
					this.treeViewer_selection = treeNode;
					if (!(treeNode.isLeaf())) {
						if (treeNode.getChildren().isEmpty()) {
							try {
								List<ProxyObject> proxyObjects = ConnectorFacade
										.browseRemoteApplicationContent(treeNode.getId(), null, false);
								List<AdaptableTreeNode> children = ModelUtil.transform(proxyObjects);
								treeNode.addAllChildren(children);
								this.treeViewer.refresh();
							} catch (ConnectionException | RemoteApplicationException e) {
								MessageDialog.openError(composite.getShell(), e.getClass().getSimpleName(),
										e.getMessage());
								e.printStackTrace();
							}
						}
						this.checkboxTreeViewer.setInput(null);
					} else {
						AdaptableTreeModel treeModel = new AdaptableTreeModel();

						try {
							List<ProxyObject> proxyObjects = ConnectorFacade
									.browseRemoteApplicationContent(treeNode.getId(), null, false);
							List<AdaptableTreeNode> children = ModelUtil.transform(proxyObjects);
							treeModel.getRoot().addAllChildren(children);
							this.checkboxTreeViewer.setInput(treeModel);
						} catch (ConnectionException | RemoteApplicationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				} else if (event.getSource().equals(this.checkboxTreeViewer) && treeNode.getChildren().isEmpty()) {

					try {
						String session_path = ((AdaptableTreeNode) this.treeViewer.getStructuredSelection()
								.getFirstElement()).getId();
						List<ProxyObject> proxyObjects = ConnectorFacade.browseRemoteApplicationContent(session_path,
								treeNode.getId(), false);
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

	// ############ ISelectionListener ##########
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
				} catch (ConnectionException | InvalidProjectInfoException | RemoteApplicationException e) {
					MessageDialog.openError(composite.getShell(), e.getClass().getSimpleName(), e.getMessage());
				}catch ( ModelNotVersionedException e) {
					
				}
				
			}
		}
	}
	
	// ########## Make Actions ##########
	private void makeActions() {

		this.newLocation_action = new Action("New Repository Location", Action.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				RepositorySettings settings = new RepositorySettings();

				AddRepositoryLocationWizard addRepositoryLocationWizard = new AddRepositoryLocationWizard(settings);
				WizardDialog wizardDialog = new WizardDialog(
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), addRepositoryLocationWizard);
				wizardDialog.setBlockOnOpen(true);
				wizardDialog.open();
				if (addRepositoryLocationWizard.getException() != null) {
					Exception e = addRepositoryLocationWizard.getException();
					MessageDialog.openError(composite.getShell(), e.getClass().getSimpleName(), e.getMessage());
				}
			}
		};
		this.newLocation_action.setToolTipText("New Remote Application Repository Location");
		this.newLocation_action.setImageDescriptor(ConnectorUIPlugin.IMG_ELCL16_NEW_LOCATION_ACTION);

		this.syncViews_action = new Action("Link Project Explorer", Action.AS_CHECK_BOX) {
			@Override
			public void run() {
				doListen(syncViews_action.isChecked());
			}
		};
		this.syncViews_action.setToolTipText("Link with Project Explorer");
		this.syncViews_action.setImageDescriptor(ConnectorUIPlugin.IMG_ELCL16_SYNCED_ACTION);

		this.refreshRemote_action = new Action("Remote Refresh", Action.AS_PUSH_BUTTON) {
			public void run() {
				try {
					AdaptableTreeModel model = new AdaptableTreeModel();
					List<ProxyObject> proxyObjects = ConnectorFacade.browseRemoteApplicationContent("", null, false);
					List<AdaptableTreeNode> treeNodes = ModelUtil.transform(proxyObjects);
					model.getRoot().addAllChildren(treeNodes);
					treeViewer.setInput(model);
					checkboxTreeViewer.setInput(null);
				} catch (ConnectionException | RemoteApplicationException e) {
					MessageDialog.openError(composite.getShell(), e.getClass().getSimpleName(), e.getMessage());
				}
			}
		};
		this.refreshRemote_action.setToolTipText("Refresh Remote Model Browser");
		this.refreshRemote_action.setImageDescriptor(ConnectorUIPlugin.IMG_ELCL16_REFRESH_REMOTE_ACTION);

		this.selectSubTree_action = new Action("Select Subtree", Action.AS_PUSH_BUTTON) {
			public void run() {
				for (TreeItem treeItem : checkboxTreeViewer.getTree().getSelection()) {
					checkboxTreeViewer.setSubtreeChecked(treeItem.getData(), true);
				}
			}
		};
		this.selectSubTree_action.setToolTipText("Select Subtree");
		this.selectSubTree_action.setImageDescriptor(ConnectorUIPlugin.IMG_ELCL16_SELECT_SUBTREE_ACTION);

		// FIXME recursively get all child elements from remote application server
		this.expandAll_action = new Action("Expand All", Action.AS_PUSH_BUTTON) {
			public void run() {
				treeViewer.expandAll();
				checkboxTreeViewer.expandAll();
			}
		};
		this.expandAll_action.setToolTipText("Expand All");
		this.expandAll_action.setImageDescriptor(ConnectorUIPlugin.IMG_ELCL16_EXPANDALL_ACTION);

		this.collapseAll_action = new Action("Collapse All", Action.AS_PUSH_BUTTON) {
			public void run() {
				treeViewer.collapseAll();
				checkboxTreeViewer.collapseAll();
			}
		};
		this.collapseAll_action.setToolTipText("Collapse All");
		this.collapseAll_action.setImageDescriptor(ConnectorUIPlugin.IMG_ELCL16_COLLAPSEALL_ACTION);

		this.checkout_action = new Action("Check Out Sub-Model", Action.AS_PUSH_BUTTON) {

			@Override
			public void run() {
				CheckoutSettings settings = new CheckoutSettings();

				WizardDialog wizardDialog = new WizardDialog(
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
						new CheckoutSubModelWizard(settings));
				wizardDialog.setBlockOnOpen(true);
				int return_code = wizardDialog.open();
				if (return_code == WizardDialog.OK) {
					String remote_model_path = treeViewer_selection.getId();
					String target_model_path = settings.getTargetPath().toOSString() + File.separator
							+ treeViewer_selection.getLabel();
					Set<String> elementIds = getSelectedElementIDs();
					try {
						File file = ConnectorFacade.checkoutSubModel(remote_model_path, target_model_path, elementIds,
								IRemotePreferencesSupplier.getUiRemotePreferences());
						IResource resource = ResourcesPlugin.getWorkspace().getRoot()
								.getFileForLocation(new Path(file.getAbsolutePath()));
						resource.getParent().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
					} catch (ConnectionException | CoreException | InvalidProjectInfoException
							| RemoteApplicationException e) {
						MessageDialog.openError(composite.getShell(), e.getClass().getSimpleName(), e.getMessage());
					}
				}
			}
		};
		this.checkout_action.setToolTipText("Check out new (sub-) model");
		this.checkout_action.setImageDescriptor(ConnectorUIPlugin.IMG_ELCL16_CHECKOUT_ACTION);

		this.update_action = new Action("Update (Sub-) Model", Action.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				try {
					Set<String> elementIds = getSelectedElementIDs();
					if (!elementIds.isEmpty()) {
						File file = ConnectorFacade.updateSubModel(selected_model_path, elementIds,
								IRemotePreferencesSupplier.getUiRemotePreferences());
						IResource resource = ResourcesPlugin.getWorkspace().getRoot()
								.getFile(new Path(file.getAbsolutePath()
										.replace(ResourcesPlugin.getWorkspace().getRoot().getLocation().toOSString()
												+ File.separator, "")));
						resource.getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
					}
				} catch (ConnectionException | CoreException | InvalidProjectInfoException
						| ModelNotVersionedException | RemoteApplicationException e) {
					MessageDialog.openError(composite.getShell(), e.getClass().getSimpleName(), e.getMessage());
				}
				super.run();
			}
		};
		this.update_action.setToolTipText("Update local (Sub-) Model");
		this.update_action.setImageDescriptor(ConnectorUIPlugin.IMG_ELCL16_UPDATE_ACTION);

		this.doubleClick_action = new Action() {
			public void run() {
				// TODO
			}
		};
	}
		
	// ########## Make Menu Contributions ##########
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

	private void fillContextMenuTreeViewer(IMenuManager manager) {
		manager.add(new Separator());

		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillContextMenuCheckboxTreeViewer(IMenuManager manager) {
		manager.add(this.selectSubTree_action);
		manager.add(new Separator());

		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalPullDown(IMenuManager manager) {

	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(this.newLocation_action);
		manager.add(this.syncViews_action);
		manager.add(this.expandAll_action);
		manager.add(this.collapseAll_action);
		manager.add(this.refreshRemote_action);
		manager.add(this.checkout_action);
		manager.add(this.update_action);
		manager.add(new Separator());
	}
	
	// ########## Help Methods ##########
	private void doListen(boolean b) {
		if(b) {
			getSite().getPage().addSelectionListener(this);
		}else {
			getSite().getPage().removeSelectionListener(this);
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
	
	private void updateRequestedModelElements(String localModelPath) throws ConnectionException, InvalidProjectInfoException, RemoteApplicationException, ModelNotVersionedException {
		
		List<ProxyObject> proxyObjectsFiles = ConnectorFacade.getRequestedModelFile(localModelPath);
		
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
				currentFileNode = new AdaptableTreeNode(proxyObject.getLabel(), proxyObject.getId(), proxyObject.getType(), !proxyObject.isContainer(), parent, proxyObject.isSelected());
			}else {
				currentFileNode = treeModelFiles.getTreeNode(proxyObject.getId());
				currentFileNode.setSelected(proxyObject.isSelected());
			}
			visibleModelFileNodes.add(currentFileNode);
			if(currentFileNode.isSelected()) {
				selectedFileNode = currentFileNode;
			}
		}
		
		treeViewer.setExpandedElements(visibleModelFileNodes.toArray());
		treeViewer.setSelection(new StructuredSelection(selectedFileNode), true);
		
		List<ProxyObject> proxyObjectsElement = ConnectorFacade.getRequestedModelElements(localModelPath);
		
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
}
