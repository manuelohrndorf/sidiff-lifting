package org.sidiff.remote.application.ui.connector.views;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.sidiff.remote.application.connector.ConnectorFacade;
import org.sidiff.remote.application.connector.exception.ConnectionException;
import org.sidiff.remote.application.connector.exception.InvalidProjectInfoException;
import org.sidiff.remote.application.connector.exception.ModelNotVersionedException;
import org.sidiff.remote.application.connector.exception.RemoteApplicationException;
import org.sidiff.remote.application.connector.settings.CheckoutSettings;
import org.sidiff.remote.application.ui.connector.ConnectorUIPlugin;
import org.sidiff.remote.application.ui.connector.model.AdaptableTreeModel;
import org.sidiff.remote.application.ui.connector.model.AdaptableTreeNode;
import org.sidiff.remote.application.ui.connector.model.ModelUtil;
import org.sidiff.remote.application.ui.connector.wizards.CheckoutSubModelWizard;
import org.sidiff.remote.common.ProxyObject;
import org.sidiff.remote.common.settings.IRemotePreferencesSupplier;
import org.sidiff.remote.common.util.ProxyUtil;

public class RemoteApplicationModelView extends AbstractRemoteApplicationView<CheckboxTreeViewer> implements ICheckStateListener {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "org.sidiff.remote.application.ui.connector.views.RemoteApplicationModelView";
	
	// ########## Actions ##########
	/**
	 * 
	 */
	private Action checkout_action;

	/**
	 * 
	 */
	private Action selectSubTree_action;

	/**
	 * 
	 */
	private Action update_action;

	// ########## Current Selection ##########
	/**
	 * 
	 */
	private String selected_model_path;
	
	/**
	 * 
	 */
	private String selecetd_model_name;
	
	// ########## AbstractRemoteApplicationView ##########
	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		getSite().getPage().addSelectionListener(RemoteApplicationFileView.ID, this);
	}
	
	
	@Override
	protected CheckboxTreeViewer createViewer(Composite parent) {
		CheckboxTreeViewer treeViewer = new CheckboxTreeViewer(composite, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		treeViewer.addCheckStateListener(this);
		return treeViewer;
	}
	
	// ########## ICheckStateListener ##########
	@Override
	public void checkStateChanged(CheckStateChangedEvent event) {
		Object element = event.getElement();
		if(this.treeViewer.getGrayed(element)){
			this.treeViewer.setGrayChecked(element, true);
		}else if(event.getChecked()){
			 ITreeContentProvider provider = (ITreeContentProvider)this.treeViewer.getContentProvider();
			 Object parent = provider.getParent(element);
			 while(parent != null){
				 this.treeViewer.setChecked(parent, true);
				 parent = provider.getParent(parent);
			 }
		}else if(!event.getChecked()){
			this.treeViewer.setSubtreeChecked(element, false);
		}
	}
	
	// ########## ISelectionChangedListener ############
	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if(selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			if(structuredSelection.size() == 1) {
				if(part.getSite().getId().equals(IPageLayout.ID_PROJECT_EXPLORER) && structuredSelection.getFirstElement() instanceof IFile) {
			
					IFile file = (IFile) structuredSelection.getFirstElement();
	
					try {
						String local_model_path = file.getLocation().toOSString();
						updateRequestedModelElements(local_model_path);
					} catch (ConnectionException | InvalidProjectInfoException e) {
						MessageDialog.openError(composite.getShell(), e.getClass().getSimpleName(), e.getMessage());
					}catch ( ModelNotVersionedException e) {
						
					}
				}else if(part.getSite().getId().equals(RemoteApplicationFileView.ID) && structuredSelection.getFirstElement() instanceof AdaptableTreeNode) {
					AdaptableTreeNode treeNode = (AdaptableTreeNode) structuredSelection.getFirstElement();
					if(treeNode.isLeaf()) {
						if(!treeNode.getId().equals(selected_model_path)) {
							AdaptableTreeModel treeModel = new AdaptableTreeModel();
							selected_model_path = treeNode.getId();
							selecetd_model_name = treeNode.getLabel();
							try {
								List<ProxyObject> proxyObjects = ConnectorFacade
										.browseRemoteApplicationContent(treeNode.getId(), null);
								List<AdaptableTreeNode> children = ModelUtil.transform(proxyObjects);
								treeModel.getRoot().addAllChildren(children);
								this.treeViewer.setInput(treeModel);
							} catch (ConnectionException | RemoteApplicationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}else {
						selected_model_path = null;
						selecetd_model_name = null;
						this.treeViewer.setInput(null);
					}
				}
			}
		}
	}

	// ############ ISelectionListener ##########
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		ISelection selection = event.getSelection();
		if(selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			if(structuredSelection.size() == 1) {
				AdaptableTreeNode treeNode = (AdaptableTreeNode) structuredSelection.getFirstElement();
				if (event.getSource().equals(this.treeViewer) && treeNode.getChildren().isEmpty()) {
					try {
						List<ProxyObject> proxyObjects = ConnectorFacade.browseRemoteApplicationContent(selected_model_path,
								treeNode.getId());
						List<AdaptableTreeNode> children = ModelUtil.transform(proxyObjects);
						treeNode.addAllChildren(children);
						this.treeViewer.refresh();

					} catch (ConnectionException | RemoteApplicationException e) {
						MessageDialog.openError(composite.getShell(), e.getClass().getSimpleName(), e.getMessage());
						e.printStackTrace();
					}
				}
			}
		}

	}

	// ########## Make Actions ##########
	@Override
	protected void makeActions() {
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
					String remote_model_path = selected_model_path;
					String target_model_path = settings.getTargetPath().toOSString() + File.separator
							+ selecetd_model_name;
					Set<String> elementIds = getSelectedElementIDs();
					try {
						File file = ConnectorFacade.checkoutSubModel(remote_model_path, target_model_path, elementIds,
								IRemotePreferencesSupplier.getDefaultRemotePreferences());
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

		this.selectSubTree_action = new Action("Select Subtree", Action.AS_PUSH_BUTTON) {
			public void run() {
				for (TreeItem treeItem : treeViewer.getTree().getSelection()) {
					treeViewer.setSubtreeChecked(treeItem.getData(), true);
				}
			}
		};
		this.selectSubTree_action.setToolTipText("Select Subtree");
		this.selectSubTree_action.setImageDescriptor(ConnectorUIPlugin.IMG_ELCL16_SELECT_SUBTREE_ACTION);
		
		this.update_action = new Action("Update (Sub-) Model", Action.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				try {
					Set<String> elementIds = getSelectedElementIDs();
					if (!elementIds.isEmpty()) {
						File file = ConnectorFacade.updateSubModel(selected_model_path, elementIds,
								IRemotePreferencesSupplier.getDefaultRemotePreferences());
						IResource resource = ResourcesPlugin.getWorkspace().getRoot()
								.getFile(new Path(file.getAbsolutePath()
										.replace(ResourcesPlugin.getWorkspace().getRoot().getLocation().toOSString()
												+ File.separator, "")));
						resource.getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
					}
				} catch (ConnectionException | CoreException | InvalidProjectInfoException
						| ModelNotVersionedException e) {
					MessageDialog.openError(composite.getShell(), e.getClass().getSimpleName(), e.getMessage());
				}
				super.run();
			}
		};
		this.update_action.setToolTipText("Update local (Sub-) Model");
		this.update_action.setImageDescriptor(ConnectorUIPlugin.IMG_ELCL16_UPDATE_ACTION);
	}

	// ########## Make Menu Contributions ##########
	@Override
	protected void fillContextMenuTreeViewer(IMenuManager manager) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void fillLocalPullDown(IMenuManager manager) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void fillLocalToolBar(IToolBarManager manager) {
		super.fillLocalToolBar(manager);
		manager.add(this.checkout_action);
		manager.add(this.update_action);
	}
	
	// ########## Help Methods ##########

	
	private Set<String> getSelectedElementIDs(){
		Set<String> elementIds = new HashSet<String>();
		for(Object element : treeViewer.getCheckedElements()) {
			AdaptableTreeNode treeNode = (AdaptableTreeNode) element;
			elementIds.add(treeNode.getId());
		}
		return elementIds;
	}
	
	private void updateRequestedModelElements(String localModelPath) throws ConnectionException, InvalidProjectInfoException, ModelNotVersionedException {
	
		List<ProxyObject> proxyObjectsElement = ConnectorFacade.getRequestedModelElements(localModelPath);
		
		AdaptableTreeModel treeModelElements = null;
		if(treeViewer.getInput() == null) {
			treeModelElements = new AdaptableTreeModel();
			treeViewer.setInput(treeModelElements);
		}else {
			treeModelElements = (AdaptableTreeModel) treeViewer.getInput();
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
		treeViewer.setExpandedElements(visibleModelElementNodes.toArray());
		treeViewer.setCheckedElements(visibleModelElementNodes.toArray());		
		treeViewer.refresh();
	}

}
