package org.sidiff.remote.application.ui.connector.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.sidiff.remote.application.connector.ConnectorFacade;
import org.sidiff.remote.application.connector.exception.ConnectionException;
import org.sidiff.remote.application.connector.exception.InvalidProjectInfoException;
import org.sidiff.remote.application.connector.exception.ModelNotVersionedException;
import org.sidiff.remote.application.connector.exception.RemoteApplicationException;
import org.sidiff.remote.application.connector.settings.RepositorySettings;
import org.sidiff.remote.application.ui.connector.ConnectorUIPlugin;
import org.sidiff.remote.application.ui.connector.model.AdaptableTreeModel;
import org.sidiff.remote.application.ui.connector.model.AdaptableTreeNode;
import org.sidiff.remote.application.ui.connector.model.ModelUtil;
import org.sidiff.remote.application.ui.connector.wizards.AddRepositoryLocationWizard;
import org.sidiff.remote.common.ProxyObject;
import org.sidiff.remote.common.util.ProxyUtil;

public class RemoteApplicationFileView extends AbstractRemoteApplicationView<TreeViewer> {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "org.sidiff.remote.application.ui.connector.views.RemoteApplicationFileView";
	
	// ########## Actions ##########
	/**
	 * Action for adding a new repository location
	 */
	private Action newLocation_action;
	
	/**
	 * Action for refreshing the remote model view
	 */
	private Action refreshRemote_action;
	
	// ########## AbstractRemoteApplicationView ##########
	@Override
	protected TreeViewer createViewer(Composite parent) {
		TreeViewer treeViewer = new TreeViewer(composite, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		return treeViewer;
	}

	// ########## ISelectionChangedListener ############
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		ISelection selection = event.getSelection();
		if (selection instanceof StructuredSelection) {
			StructuredSelection structuredSelection = (StructuredSelection) selection;
			if (structuredSelection.size() == 1) {
				AdaptableTreeNode treeNode = (AdaptableTreeNode) structuredSelection.getFirstElement();

				if (event.getSource().equals(this.treeViewer)) {
					this.treeViewer_selection = treeNode;
					if (!(treeNode.isLeaf())) {
						if (treeNode.getChildren().isEmpty()) {
							try {
								List<ProxyObject> proxyObjects = ConnectorFacade
										.browseRemoteApplicationContent(treeNode.getId(), null);
								List<AdaptableTreeNode> children = ModelUtil.transform(proxyObjects);
								treeNode.addAllChildren(children);
								this.treeViewer.refresh();
							} catch (ConnectionException | RemoteApplicationException e) {
								MessageDialog.openError(composite.getShell(), e.getClass().getSimpleName(),
										e.getMessage());
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}

	// ############ ISelectionListener ##########
	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			if (structuredSelection.size() == 1 && structuredSelection.getFirstElement() instanceof IFile) {
				IFile file = (IFile) structuredSelection.getFirstElement();

				try {
					String local_model_path = file.getLocation().toOSString();
					updateRequestedModelElements(local_model_path);
				} catch (ConnectionException | InvalidProjectInfoException | RemoteApplicationException e) {
					MessageDialog.openError(composite.getShell(), e.getClass().getSimpleName(), e.getMessage());
				} catch (ModelNotVersionedException e) {

				}

			}
		}
	}

	// ########## Make Actions ##########
	@Override
	protected void makeActions() {
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
		
		this.refreshRemote_action = new Action("Remote Refresh", Action.AS_PUSH_BUTTON) {
			public void run() {
				try {
					AdaptableTreeModel model = new AdaptableTreeModel();
					List<ProxyObject> proxyObjects = ConnectorFacade.browseRemoteApplicationContent("", null);
					List<AdaptableTreeNode> treeNodes = ModelUtil.transform(proxyObjects);
					model.getRoot().addAllChildren(treeNodes);
					treeViewer.setInput(model);
				} catch (ConnectionException | RemoteApplicationException e) {
					MessageDialog.openError(composite.getShell(), e.getClass().getSimpleName(), e.getMessage());
				}
			}
		};
		this.refreshRemote_action.setToolTipText("Refresh Remote Model Browser");
		this.refreshRemote_action.setImageDescriptor(ConnectorUIPlugin.IMG_ELCL16_REFRESH_REMOTE_ACTION);
		
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
		manager.add(this.newLocation_action);
		manager.add(this.refreshRemote_action);
	}

	// ########## Help Methods ##########
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
		treeViewer.refresh();
		treeViewer.setExpandedElements(visibleModelFileNodes.toArray());
		treeViewer.setSelection(new StructuredSelection(selectedFileNode), true);
				
	}
}
