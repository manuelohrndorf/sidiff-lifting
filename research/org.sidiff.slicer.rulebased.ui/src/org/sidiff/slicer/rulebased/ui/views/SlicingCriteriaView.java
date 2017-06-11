package org.sidiff.slicer.rulebased.ui.views;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
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
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.integration.editor.access.IntegrationEditorAccess;
import org.sidiff.integration.editor.extension.IEditorIntegration;
import org.sidiff.slicer.rulebased.RuleBasedSlicer;
import org.sidiff.slicer.rulebased.configuration.SlicingConfiguration;
import org.sidiff.slicer.rulebased.exceptions.NotInitializedException;
import org.sidiff.slicer.rulebased.exceptions.UncoveredChangesException;
import org.sidiff.slicer.rulebased.ui.RuleBasedSlicerUI;

/**
 * 
 * @author cpietsch
 *
 */
public class SlicingCriteriaView extends ViewPart implements ICheckStateListener {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "org.sidiff.slicer.rulebased.ui.views.SlicingCriteriaView";
	
	/**
	 * The {@link RuleBasedSlicer}
	 */
	private RuleBasedSlicer slicer;
	
	/**
	 * The slicing criteria
	 */
	private Set<EObject> slicingCriteria;
	
	/**
	 * The {@link SlicingConfiguration}
	 */
	private SlicingConfiguration config;
	
	/**
	 * The {@link ComposedAdapterFactory}
	 */
	private ComposedAdapterFactory adapterFactory;

	// ---------- UI Elements ----------
	
	/**
	 * 
	 */
	private CheckboxTreeViewer viewer;
	
	/**
	 * 
	 */
	private Action selectSubTreeAction;
	
	/**
	 * 
	 */
	private Action deselectSubTreeAction;
	
	/**
	 * 
	 */
	private Action sliceAction;
	
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
	private Action doubleClickAction;
	 
	
	/**
	 * The constructor.
	 */
	public SlicingCriteriaView() {
		this.slicer = new RuleBasedSlicer();
		this.slicingCriteria = new HashSet<EObject>();
		this.config = new SlicingConfiguration(new LiftingSettings());
		this.adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		
	}

	/**
	 * loads the origin resource and shows the content in the {@link #viewer}
	 * @param input
	 * 			the {@link IFile} representing the origin resource
	 */
	public void init(IFile input){
		Resource originResource = EMFStorage.eLoad(EMFStorage.pathToUri(input.getLocation().toOSString())).eResource();
		Resource targetResource = new ResourceSetImpl().createResource(EMFStorage.pathToUri(input.getLocation().toOSString().replace(originResource.getURI().lastSegment(), "sliced_" + originResource.getURI().lastSegment())));
		try {
			targetResource.save(null);
		} catch (IOException e) {
			MessageDialog.openError(viewer.getControl().getShell(), "Error", "Couldn't save the target resource at " + targetResource.getURI());
		}
		config.setLiftingSettings(new LiftingSettings(EMFModelAccess.getDocumentTypes(originResource, Scope.RESOURCE)));
		
		viewer.setInput(originResource.getResourceSet());
		viewer.refresh();
		
		IEditorIntegration domainEditor = IntegrationEditorAccess.getInstance()
				.getIntegrationEditorForModel(targetResource);
		IEditorPart editorPart = domainEditor.openModelInDefaultEditor(targetResource.getURI());
		
		slicer.init(config, originResource, domainEditor.getEditingDomain(editorPart).getResourceSet().getResource(targetResource.getURI(), false));
		slicer.setEditingDomain(domainEditor.getEditingDomain(editorPart));
		
		sliceAction.setEnabled(true);
		sliceAction.setImageDescriptor(RuleBasedSlicerUI.getImageDescriptor(RuleBasedSlicerUI.IMG_RUN_ENABLED));
	}
	
	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		viewer = new CheckboxTreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new AdapterFactoryContentProvider(adapterFactory));
		viewer.setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory));
		viewer.addCheckStateListener(this);
		
		
		// Create the help context id for the viewer's control
		PlatformUI.getWorkbench().getHelpSystem().setHelp(viewer.getControl(), "org.sidiff.slicer.rulebased.ui.viewer");
		getSite().setSelectionProvider(viewer);
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}

	private void makeActions() {
		//begin selectSubTreeAction
		selectSubTreeAction = new Action() {
			public void run(){
				for(TreeItem treeItem : viewer.getTree().getSelection()){
					viewer.setSubtreeChecked(treeItem.getData(), true);
				}
			}
		};
		selectSubTreeAction.setText("Select Subtree");
		selectSubTreeAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_ADD));
		//end selectSubTreeAction
		
		//begin deselectSubTreeAction
		deselectSubTreeAction = new Action() {
			public void run(){
				for(TreeItem treeItem : viewer.getTree().getSelection()){
					viewer.setSubtreeChecked(treeItem.getData(), false);
				}
			}
		};
		deselectSubTreeAction.setText("Select Subtree");
		deselectSubTreeAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_ETOOL_DELETE));
		//end deselectSubTreeAction
		
		//begin sliceAction		
		sliceAction = new Action() {
			public void run() {
				try {
					slicer.slice(slicingCriteria);
				} catch (UncoveredChangesException | NotInitializedException e) {
					MessageDialog.openError(viewer.getControl().getShell(), "Error", e.getMessage());
				}
			}
			
		};
		sliceAction.setText("Apply Slice");
		sliceAction.setToolTipText("slice model");
		sliceAction.setEnabled(false);
		sliceAction.setImageDescriptor(RuleBasedSlicerUI.getImageDescriptor(RuleBasedSlicerUI.IMG_RUN_DISABLED));
		//end sliceAction
		
		//begin expandAllAction
		expandAllAction = new Action() {
			public void run() {
				viewer.expandAll();
			}
		};
		expandAllAction.setToolTipText("expand all");
		expandAllAction.setImageDescriptor(RuleBasedSlicerUI.getImageDescriptor(RuleBasedSlicerUI.IMG_EXPANDALL));
		//end expandAllAction
		
		// begin collapseAllAction
		collapseAllAction = new Action() {
			public void run() {
				viewer.collapseAll();
			}
		};
		collapseAllAction.setToolTipText("collapse all");
		collapseAllAction.setImageDescriptor(RuleBasedSlicerUI.getImageDescriptor(RuleBasedSlicerUI.IMG_COLLAPSEALL));
		// end collapseAllAction
				
		//begin doubleClickAction
		doubleClickAction = new Action() {
			public void run() {
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				showMessage("Double-click detected on "+obj.toString());
			}
		};
		//end doubleClickAction
		
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
            public void selectionChanged(SelectionChangedEvent event) {
                    
            }
		});
	}
	
	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(selectSubTreeAction);
		manager.add(deselectSubTreeAction);
	}
	
	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(sliceAction);
		manager.add(new Separator());
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(sliceAction);
		manager.add(new Separator());
		manager.add(expandAllAction);
		manager.add(collapseAllAction);
	}

	

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}
	
	
	private void showMessage(String message) {
		MessageDialog.openInformation(
			viewer.getControl().getShell(),
			"Slicing Criteria view",
			message);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}


	@Override
	public void checkStateChanged(CheckStateChangedEvent event) {
		if(event.getChecked()){	
			Object element = event.getElement();
			if(element instanceof EObject){
				slicingCriteria.add((EObject)element);
			}
		}
	}
}
