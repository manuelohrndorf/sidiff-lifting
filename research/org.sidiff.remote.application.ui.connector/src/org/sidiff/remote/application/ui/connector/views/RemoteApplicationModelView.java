package org.sidiff.remote.application.ui.connector.views;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.edit.domain.EditingDomain;
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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.emf.modelstorage.UUIDResource;
import org.sidiff.common.file.ZipUtil;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.integration.editor.access.IntegrationEditorAccess;
import org.sidiff.integration.editor.extension.IEditorIntegration;
import org.sidiff.integration.preferences.settingsadapter.SettingsAdapterUtil;
import org.sidiff.patching.ExecutionMode;
import org.sidiff.patching.PatchEngine;
import org.sidiff.patching.api.settings.PatchingSettings;
import org.sidiff.patching.operation.OperationInvocationWrapper;
import org.sidiff.patching.ui.adapter.ModelAdapter;
import org.sidiff.patching.ui.adapter.ModelChangeHandler;
import org.sidiff.patching.ui.view.OperationExplorerView;
import org.sidiff.patching.ui.view.ReportView;
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
import org.sidiff.slicer.rulebased.slice.ExecutableModelSlice;
import org.sidiff.slicer.rulebased.slice.arguments.ModelSliceBasedArgumentManager;

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
	private String selected_remote_model_path;
	
	/**
	 * 
	 */
	private String selected_local_model_path;
	
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
						selected_local_model_path = file.getLocation().toOSString();
						updateRequestedModelElements(selected_local_model_path);
					} catch (ConnectionException | InvalidProjectInfoException e) {
						MessageDialog.openError(composite.getShell(), e.getClass().getSimpleName(), e.getMessage());
					}catch ( ModelNotVersionedException e) {
						selected_local_model_path = null;
					}
				}else if(part.getSite().getId().equals(RemoteApplicationFileView.ID) && structuredSelection.getFirstElement() instanceof AdaptableTreeNode) {
					AdaptableTreeNode treeNode = (AdaptableTreeNode) structuredSelection.getFirstElement();
					if(treeNode.isLeaf()) {
						if(!treeNode.getId().equals(selected_remote_model_path)) {
							selected_local_model_path = null;
							AdaptableTreeModel treeModel = new AdaptableTreeModel();
							selected_remote_model_path = treeNode.getId();
							selecetd_model_name = treeNode.getLabel();
							try {
								List<ProxyObject> proxyObjects = ConnectorFacade
										.browseRemoteApplicationContent(treeNode.getId(), null, false);
								List<AdaptableTreeNode> children = ModelUtil.transform(proxyObjects);
								treeModel.getRoot().addAllChildren(children);
								this.treeViewer.setInput(treeModel);
							} catch (ConnectionException | RemoteApplicationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}else {
						selected_local_model_path = null;
						selected_remote_model_path = null;
						selecetd_model_name = null;
						this.treeViewer.setInput(null);
					}
				}
			}
			validateActions();
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
				if (event.getSource().equals(this.treeViewer) && treeNode.getChildren().isEmpty() && selected_remote_model_path != null) {
					try {
						List<ProxyObject> proxyObjects = ConnectorFacade.browseRemoteApplicationContent(selected_remote_model_path,
								treeNode.getId(), false);
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
					String target_model_path = settings.getTargetPath().toOSString() + File.separator
							+ selecetd_model_name;
					Set<String> elementIds = getSelectedElementIDs();
					try {
						File file = ConnectorFacade.checkoutSubModel(selected_remote_model_path, target_model_path, elementIds,
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
				TreeItem selectedTreeItem = treeViewer.getTree().getSelection()[0];
				AdaptableTreeNode selectedTreeNode = (AdaptableTreeNode) selectedTreeItem.getData();
				selectedTreeNode.setSelected(true);
				String elementId = ((AdaptableTreeNode)selectedTreeItem.getData()).getId();
				
				
				AdaptableTreeModel treeModelElements = (AdaptableTreeModel) treeViewer.getInput();
				
				List<ProxyObject> proxyObjectsElement;
				try {
					proxyObjectsElement = ConnectorFacade.browseRemoteApplicationContent(selected_remote_model_path, elementId, true);
				} catch (ConnectionException | RemoteApplicationException e) {
					MessageDialog.openError(composite.getShell(), e.getClass().getSimpleName(), e.getMessage());
					return;
				}
				List<ProxyObject> proxyElements = new ArrayList<ProxyObject>();

				for(ProxyObject proxyObject : proxyObjectsElement) {
					proxyElements.addAll(ProxyUtil.sort(proxyObject));
				}
				
				List<AdaptableTreeNode> visibleModelElementNodes = new ArrayList<AdaptableTreeNode>();
				visibleModelElementNodes.add(selectedTreeNode);
				
				for(ProxyObject proxyObject : proxyElements) {
					
					AdaptableTreeNode currentElementNode = null;
					if(treeModelElements.getTreeNode(proxyObject.getId()) == null) {
						AdaptableTreeNode parent = null;
						if(proxyObject.getParent() == null) {
							parent = treeModelElements.getRoot();
						}else {
							parent = treeModelElements.getTreeNode(proxyObject.getParent().getId());
						}
						currentElementNode = ModelUtil.transform(proxyObject);
						currentElementNode.setParent(parent);
					}else {
						currentElementNode = treeModelElements.getTreeNode(proxyObject.getId());
					}
					currentElementNode.setSelected(true);
					if(currentElementNode.isSelected()) {
						visibleModelElementNodes.add(currentElementNode);
					}
				}
				treeViewer.setExpandedElements(visibleModelElementNodes.toArray());
				treeViewer.setCheckedElements(visibleModelElementNodes.toArray());		
			
				
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
						File file = ConnectorFacade.updateSubModel(selected_local_model_path, elementIds,
								IRemotePreferencesSupplier.getDefaultRemotePreferences());
						IResource resource = ResourcesPlugin.getWorkspace().getRoot()
								.getFile(new Path(file.getAbsolutePath()
										.replace(ResourcesPlugin.getWorkspace().getRoot().getLocation().toOSString()
												+ File.separator, "")));
						resource.getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
						ResourceSet resourceSet = new ResourceSetImpl();
						UUIDResource localModelResource = new UUIDResource(EMFStorage.pathToUri(selected_local_model_path), resourceSet);
						merge(localModelResource, file);
					}
				} catch (ConnectionException | CoreException | InvalidProjectInfoException
						| ModelNotVersionedException | RemoteApplicationException e) {
					MessageDialog.openError(composite.getShell(), e.getClass().getSimpleName(), e.getMessage());
				}
				super.run();
			}
		};
		this.update_action.setToolTipText("Update local (Sub-) Model");
		validateActions();
	}

	// ########## Make Menu Contributions ##########
	@Override
	protected void fillContextMenuTreeViewer(IMenuManager manager) {
		manager.add(this.selectSubTree_action);
		
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

		System.out.println("Proxy:" + proxyObjectsElement.size());
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
				currentElementNode = ModelUtil.transform(proxyObject);
				currentElementNode.setParent(parent);
			}else {
				currentElementNode = treeModelElements.getTreeNode(proxyObject.getId());
				currentElementNode.setSelected(proxyObject.isSelected());
			}
			if(currentElementNode.isSelected()) {
				visibleModelElementNodes.add(currentElementNode);
			}
		}
		treeViewer.setExpandedElements(visibleModelElementNodes.toArray());
		System.out.println("tree items: " + treeModelElements.getSize());
		treeViewer.setCheckedElements(visibleModelElementNodes.toArray());		
	}

	private void merge(UUIDResource localModelResource, File patchFile) {

		final AtomicReference<OperationExplorerView> operationExplorerViewReference = new AtomicReference<OperationExplorerView>();
		final AtomicReference<ReportView> reportViewReference = new AtomicReference<ReportView>();
		final AtomicReference<Resource> targetResource = new AtomicReference<Resource>();
		final AtomicReference<PatchEngine> patchEngineReference = new AtomicReference<PatchEngine>();
		final AtomicReference<PatchingSettings> patchingSettingsReference = new AtomicReference<PatchingSettings>();
		
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {

				IEditorIntegration domainEditor = IntegrationEditorAccess.getInstance()
						.getIntegrationEditorForModel(localModelResource);
				IEditorPart editorPart = domainEditor.openModelInDefaultEditor(localModelResource.getURI());
				EditingDomain editingDomain = domainEditor.getEditingDomain(editorPart);
				targetResource.set(domainEditor.getResource(editorPart));

				String ARCHIVE_URI_PREFIX = "archive:file:///";
				String ARCHIVE_SEPERATOR = "!/";
				String patchPath = patchFile.getAbsolutePath();

				URI uri_patch = null;
				for (String entry : ZipUtil.getEntries(patchPath)) {
					if (entry.endsWith("slice")) {
						uri_patch = URI.createURI(ARCHIVE_URI_PREFIX + patchPath + ARCHIVE_SEPERATOR + entry);
					}
				}

				ResourceSet resourceSet = new ResourceSetImpl();
				Resource executableModelSliceResource = resourceSet.getResource(uri_patch, true);
				ExecutableModelSlice executableModelSlice = executableModelSliceResource.getContents().stream()
						.filter(c -> c instanceof ExecutableModelSlice).map(c -> (ExecutableModelSlice) c).findFirst()
						.get();
				AsymmetricDifference asymmetricDifference = executableModelSlice.getAsymmetricDifference();

				PatchingSettings patchingSettings = new PatchingSettings();
				SettingsAdapterUtil.adaptSettingsGlobal(patchingSettings,
						EMFModelAccess.getDocumentTypes(localModelResource, Scope.RESOURCE),
						Collections.<Enum<?>>emptySet());
				patchingSettings.setArgumentManager(new ModelSliceBasedArgumentManager());
				patchingSettings.setExecutionMode(ExecutionMode.INTERACTIVE);
				patchingSettingsReference.set(patchingSettings);
				
				if (asymmetricDifference != null) {
					PatchEngine patchEngine = new PatchEngine(asymmetricDifference, targetResource.get(),
							patchingSettings);
					patchEngine.setPatchedEditingDomain(editingDomain);
					// Init detector if available
					try {
						if (patchingSettings.getModifiedDetector() != null) {
							patchingSettings.getModifiedDetector().init(asymmetricDifference.getChangedModel(),
									targetResource.get(), patchingSettings.getMatcher(), Scope.RESOURCE);
						}
					} catch (FileNotFoundException e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					
					patchEngineReference.set(patchEngine);

					// Opening and setting operation explorer view

					try {
						OperationExplorerView operationExplorerView = (OperationExplorerView) PlatformUI.getWorkbench()
								.getActiveWorkbenchWindow().getActivePage().showView(OperationExplorerView.ID);
						operationExplorerView.setPatchEngine(patchEngine);
						operationExplorerViewReference.set(operationExplorerView);
					} catch (PartInitException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// Opening and setting report view
					try {
						ReportView reportView = (ReportView) PlatformUI.getWorkbench().getActiveWorkbenchWindow()
								.getActivePage().showView(ReportView.ID);
						reportView.setPatchReportManager(patchEngine.getPatchReportManager());
						reportViewReference.set(reportView);
					} catch (PartInitException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
		if(patchEngineReference.get() != null){
			boolean conflicting = false;
			for(OperationInvocationWrapper wrapper : patchEngineReference.get().getOperationManager().getOrderedOperationWrappers()){
				if(wrapper.hasUnresolvedInArguments()){
					conflicting = true;
					break;
				}
			}
			
			if(conflicting){
				OperationExplorerView operationExplorerView = operationExplorerViewReference.get();
				ModelAdapter adapter = new ModelAdapter(
						targetResource.get());
				try {
					adapter.addListener(new ModelChangeHandler(patchingSettingsReference.get().getArgumentManager()));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				adapter.addListener(operationExplorerView);
			}else{
				patchEngineReference.get().applyPatch(true);
			}
		}
	}
	
	private void validateActions() {
		
		if(selected_local_model_path == null) {
			update_action.setEnabled(false);
			update_action.setImageDescriptor(ConnectorUIPlugin.IMG_DLCL16_UPDATE_ACTION);
		}else {
			update_action.setImageDescriptor(ConnectorUIPlugin.IMG_ELCL16_UPDATE_ACTION);
			update_action.setEnabled(true);
		}
		
		if(selected_remote_model_path == null) {
			checkout_action.setEnabled(false);
			checkout_action.setImageDescriptor(ConnectorUIPlugin.IMG_DLCL16_CHECKOUT_ACTION);
		}else {
			checkout_action.setEnabled(true);
			checkout_action.setImageDescriptor(ConnectorUIPlugin.IMG_ELCL16_CHECKOUT_ACTION);
		}
	}
}
