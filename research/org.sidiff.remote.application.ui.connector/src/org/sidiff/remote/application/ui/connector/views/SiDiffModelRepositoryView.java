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
import org.eclipse.swt.layout.FillLayout;
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
import org.sidiff.remote.application.connector.exception.RemoteApplicationException;
import org.sidiff.remote.application.connector.settings.CheckoutSettings;
import org.sidiff.remote.application.connector.settings.RepositorySettings;
import org.sidiff.remote.application.ui.connector.ConnectorUIPlugin;
import org.sidiff.remote.application.ui.connector.providers.TreeModelContentProvider;
import org.sidiff.remote.application.ui.connector.providers.TreeModelLabelProvider;
import org.sidiff.remote.application.ui.connector.wizards.AddRepositoryLocationWizard;
import org.sidiff.remote.application.ui.connector.wizards.CheckoutSubModelWizard;
import org.sidiff.remote.common.exceptions.InvalidSessionException;
import org.sidiff.remote.common.tree.TreeLeaf;
import org.sidiff.remote.common.tree.TreeModel;
import org.sidiff.remote.common.tree.TreeNode;


/**
 * 
 * @author cpietsch
 * 
 */
public class SiDiffModelRepositoryView extends ViewPart implements ICheckStateListener, ISelectionListener {

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
	 * 
	 */
	private TreeLeaf treeViewer_selection;
	
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
	private Action browseFiles_action;
	
	/**
	 * Action for browsing a specific remote model file
	 */
	private Action browseModel_action;
	
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
	
	private String selected_model_path;

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
				if(selection.size() == 1 && !selection.getFirstElement().equals(treeViewer_selection))
					if(selection.getFirstElement() instanceof TreeLeaf) {
						browseModel_action.setEnabled(true);
						treeViewer_selection = (TreeLeaf) selection.getFirstElement();
					}else {
						browseModel_action.setEnabled(false);
					}
				else {
					checkboxTreeViewer.setInput(null);
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
		manager.add(this.browseModel_action);
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
		manager.add(this.browseFiles_action);
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
		
		this.browseFiles_action = new Action() {
			public void run() {
				try {
					TreeModel models = ConnectorFacade.browseModelFiles(null);
					treeViewer.setInput(models);
					checkboxTreeViewer.setInput(null);
				} catch (ConnectionException | InvalidSessionException | RemoteApplicationException e) {
					MessageDialog.openError(composite.getShell(), e.getClass().getSimpleName(), e.getMessage());
				}
			}
		};
		this.browseFiles_action.setText("Browse Repository");
		this.browseFiles_action.setToolTipText("Browse remote model repository");
		this.browseFiles_action.setImageDescriptor(IMG_BROWSE_FILES);
		
		this.browseModel_action = new Action() {
			
			public void run() {
				IStructuredSelection selection = treeViewer.getStructuredSelection();
				
				Object object = selection.getFirstElement();
				if (object instanceof TreeLeaf) {
					try {
						String remote_model_path = ((TreeLeaf)object).getId();
						TreeModel model = ConnectorFacade.browseModelElements(remote_model_path);
						checkboxTreeViewer.setInput(model);
					} catch (ConnectionException | InvalidSessionException e) {
						MessageDialog.openError(composite.getShell(), e.getClass().getSimpleName(), e.getMessage());
					}
				} else {
					MessageDialog.openError(composite.getShell(), "No Model File", "Please select a model file");
				}
			}
		};
		this.browseModel_action.setText("Browse Model");
		this.browseModel_action.setToolTipText("Browse remote model file");
		this.browseModel_action.setImageDescriptor(IMG_BROWSE_MODEL);
		this.browseModel_action.setEnabled(false);
		
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
					String target_model_path = settings.getTargetPath().toOSString() + File.separator + treeViewer_selection.getLabel();
					String remote_model_path = treeViewer_selection.getId();
					Set<String> elementIds = getSelectedElementIDs();
					try {
						File file = ConnectorFacade.checkoutSubModel(remote_model_path, target_model_path, elementIds);
						IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(file.getPath()));
						resource.getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
					} catch (ConnectionException | CoreException | InvalidSessionException e) {
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
					File file = ConnectorFacade.updateSubModel(selected_model_path, elementIds);
					IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(file.getPath()));
					resource.getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
					
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
				}
				
			}
		}
	}
	
	private Set<String> getSelectedElementIDs(){
		Set<String> elementIds = new HashSet<String>();
		for(Object element : checkboxTreeViewer.getCheckedElements()) {
			TreeNode treeNode = (TreeNode) element;
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
	
	private void updateRequestedModelElements(String localModelPath) throws ConnectionException, InvalidSessionException, RemoteApplicationException {
		
		TreeModel files = ConnectorFacade.browseModelFiles(localModelPath);
		
		treeViewer.setInput(files);
	
		TreeNode selectedModelFile = files.getSelectedElements()[0];
		List<TreeNode> visibleModelFilesNodes = new ArrayList<TreeNode>();
		TreeNode parent = selectedModelFile.getParent();
		while(!parent.equals(files.getRoot())){
			visibleModelFilesNodes.add(0, parent);
			parent = parent.getParent();
		}
		treeViewer.setExpandedElements(visibleModelFilesNodes.toArray());
		treeViewer.setSelection(new StructuredSelection(selectedModelFile), true);
		
		TreeModel model = ConnectorFacade.getRequestedModelElements(localModelPath);
		checkboxTreeViewer.setInput(model);
		
		TreeNode[] selectedModelElements = model.getSelectedElements();
		List<TreeNode> visibleModelElements = new ArrayList<TreeNode>();
		
		for(TreeNode selectedModelElement : selectedModelElements) {
			TreeNode parentElement = selectedModelElement.getParent();
			while(!parentElement.equals(model.getRoot())){
				visibleModelElements.add(0, parentElement);
				parentElement = parentElement.getParent();
			}
		}
		checkboxTreeViewer.setExpandedElements(visibleModelElements.toArray());
		checkboxTreeViewer.setCheckedElements(selectedModelElements);		
	}
}
