package org.sidiff.slicer.rulebased.ui.views;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.edit.domain.EditingDomain;
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
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.sidiff.common.emf.EMFUtil;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.emf.exceptions.NoCorrespondencesException;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.emf.modelstorage.UUIDResource;
import org.sidiff.conflicts.modifieddetector.util.ModifiedDetectorUtil;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.integration.editor.access.IntegrationEditorAccess;
import org.sidiff.integration.editor.extension.IEditorIntegration;
import org.sidiff.integration.preferences.settingsadapter.SettingsAdapterUtil;
import org.sidiff.patching.ExecutionMode;
import org.sidiff.patching.PatchEngine;
import org.sidiff.patching.PatchMode;
import org.sidiff.patching.api.settings.PatchingSettings;
import org.sidiff.patching.operation.OperationInvocationWrapper;
import org.sidiff.patching.transformation.ITransformationEngine;
import org.sidiff.patching.transformation.TransformationEngineUtil;
import org.sidiff.patching.ui.adapter.ModelAdapter;
import org.sidiff.patching.ui.adapter.ModelChangeHandler;
import org.sidiff.patching.ui.handler.DialogPatchInterruptHandler;
import org.sidiff.patching.ui.view.OperationExplorerView;
import org.sidiff.patching.ui.view.ReportView;
import org.sidiff.patching.validation.ValidationMode;
import org.sidiff.slicer.rulebased.RuleBasedSlicer;
import org.sidiff.slicer.rulebased.configuration.RuleBasedSlicingConfiguration;
import org.sidiff.slicer.rulebased.exceptions.ExtendedSlicingCriteriaIntersectionException;
import org.sidiff.slicer.rulebased.exceptions.NotInitializedException;
import org.sidiff.slicer.rulebased.exceptions.UncoveredChangesException;
import org.sidiff.slicer.rulebased.slice.ExecutableModelSlice;
import org.sidiff.slicer.rulebased.slice.arguments.ModelSliceBasedArgumentManager;
import org.sidiff.slicer.rulebased.ui.RuleBasedSlicerUI;
import org.sidiff.slicer.rulebased.ui.provider.SlicingCriteriaLabelProvider;

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
	 * The remote complete @link UUIDResource}
	 */
	private UUIDResource remoteResourceComplete;
	
	/**
	 * The remote empty {@link UUIDResource}
	 */
	private UUIDResource remoteResourceEmpty;
	
	/**
	 * The local sliced {@link UUIDResource}
	 */
	private UUIDResource localSlicedResource;
	
	/**
	 * The local modified sliced {@link UUIDResource}
	 */
	private UUIDResource localModifiedSlicedResource;
	
	/**
	 * The {@link AsymmetricDifference} for propagating the slice to the {@link #localSlicedResource}
	 */
	private AsymmetricDifference asymDiff; 
	
	/**
	 * The {@link RuleBasedSlicer}
	 */
	private RuleBasedSlicer slicer;
	
	/**
	 * The slicing criteria
	 */
	private Set<EObject> slicingCriteria;
	
	/**
	 * The {@link RuleBasedSlicingConfiguration}
	 */
	private RuleBasedSlicingConfiguration config;
	
	/**
	 * The {@link PatchEngine} merging {@link #remoteSlicedResource} into {@link #localSlicedResource}
	 */
	private PatchEngine mergeEngine;
	
	/**
	 * The {@link PatchingSettings} used for merging
	 */
	private PatchingSettings mergeSettings;
	
	/**
	 * The {@link ComposedAdapterFactory}
	 */
	private ComposedAdapterFactory adapterFactory;
	
	/**
	 * An {@link EContentAdapter} for listing modifications of the model slice
	 */
	private EContentAdapter eContentAdapter;
	

	// ---------- UI Elements ----------
	
	/**
	 * 
	 */
	private CheckboxTreeViewer checkboxTreeViewer;
	
	/**
	 * 
	 */
	private SlicingCriteriaLabelProvider labelProvider;
	
	/**
	 * 
	 */
	private Action selectSubTreeAction;
	
	/**
	 * 
	 */
	private Action synchAction;
	
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
		this.config = new RuleBasedSlicingConfiguration();
		this.config.setLiftingSettings(new LiftingSettings());
		this.adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
	}

	/**
	 * loads the origin resource and shows the content in the {@link #checkboxTreeViewer}
	 * @param input
	 * 			the {@link IFile} representing the origin resource
	 * @throws UnsupportedFeatureLevelException 
	 * @throws InvalidSettingsException 
	 */
	public void init(IFile input) {

		ResourceSet remoteRSS = new ResourceSetImpl();
		
		this.remoteResourceComplete = new UUIDResource(EMFStorage.pathToUri(input.getLocation().toOSString()), remoteRSS);
		
		this.remoteResourceEmpty = UUIDResource.createUUIDResource(EMFStorage.pathToUri(input.getLocation().toOSString().replace(remoteResourceComplete.getURI().lastSegment(), "empty_" + remoteResourceComplete.getURI().lastSegment())), remoteRSS);

		this.localSlicedResource = UUIDResource.createUUIDResource(EMFStorage.pathToUri(EMFStorage.uriToPath(remoteResourceComplete.getURI()).replace("remote", "local").replace(remoteResourceComplete.getURI().lastSegment(), "sliced" + remoteResourceComplete.getURI().lastSegment())));
		
		Map<EObject, EObject> copies_empty = EMFUtil.copyAll(new HashSet<EObject>(remoteResourceComplete.getContents()));
		
		this.remoteResourceEmpty.getContents().addAll(copies_empty.values());
		
		for(EObject origin : copies_empty.keySet()){
			String id = EMFUtil.getXmiId(origin);
			EMFUtil.setXmiId(copies_empty.get(origin), id);
		}
		
		Map<EObject, EObject> copies = EMFUtil.copyAll(new HashSet<EObject>(remoteResourceComplete.getContents()));
		
		this.localSlicedResource.getContents().addAll(copies.values());
		
		for(EObject origin : copies.keySet()){
			String id = EMFUtil.getXmiId(origin);
			EMFUtil.setXmiId(copies.get(origin), id);
		}
		
		LiftingSettings settings = new LiftingSettings();
		SettingsAdapterUtil.adaptSettingsGlobal(settings, EMFModelAccess.getDocumentTypes(
				remoteResourceComplete, Scope.RESOURCE_SET), Collections.emptySet());
		this.config.setLiftingSettings(settings);
		
		this.mergeSettings = new PatchingSettings(config.getLiftingSettings().getScope(), false,
				config.getLiftingSettings().getMatcher(),
				config.getLiftingSettings().getCandidatesService(),
				config.getLiftingSettings().getCorrespondencesService(),
				config.getLiftingSettings().getTechBuilder(), null,
				new ModelSliceBasedArgumentManager(),
				new DialogPatchInterruptHandler(),
				TransformationEngineUtil.getFirstTransformationEngine(ITransformationEngine.DEFAULT_DOCUMENT_TYPE),
				ModifiedDetectorUtil.getGenericModifiedDetector(), ExecutionMode.INTERACTIVE,
				PatchMode.MERGING, 100, ValidationMode.NO_VALIDATION);
		
		this.checkboxTreeViewer.setInput(this.remoteResourceComplete.getResourceSet());
		this.checkboxTreeViewer.refresh();
		try {
			this.config.setEmtpyResource(this.remoteResourceEmpty);
			this.config.setEmtpyResource(this.remoteResourceComplete);
			this.slicer.init(this.config);
			this.remoteResourceEmpty.save(null);
		} catch (UncoveredChangesException | InvalidModelException | NoCorrespondencesException | IOException e) {
			MessageDialog.openError(checkboxTreeViewer.getControl().getShell(), "Error", e.getMessage());
			e.printStackTrace();
		}
		
		this.checkboxTreeViewer.setGrayChecked(this.remoteResourceComplete, true);
		for(EObject eObject : EMFUtil.copyAll(new HashSet<EObject>(remoteResourceComplete.getContents())).keySet()){
			checkboxTreeViewer.setGrayChecked(eObject, true);
		}
		
		this.synchAction.setEnabled(true);
	}
	
//	private void initEContentAdapter(Resource resource){
//		this.eContentAdapter = new EContentAdapter(){
//
//			@Override
//			public void notifyChanged(Notification notification) {
//				// TODO Auto-generated method stub
//				super.notifyChanged(notification);
//				System.out.println(notification);
//			}
//			
//		};
//		resource.eAdapters().add(this.eContentAdapter);
//	}
	
	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		this.checkboxTreeViewer = new CheckboxTreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		this.checkboxTreeViewer.setContentProvider(new AdapterFactoryContentProvider(this.adapterFactory));
		this.labelProvider = new SlicingCriteriaLabelProvider(new AdapterFactoryLabelProvider(this.adapterFactory));
		this.checkboxTreeViewer.setLabelProvider(this.labelProvider);
		this.checkboxTreeViewer.addCheckStateListener(this);

		// Create the help context id for the viewer's control
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this.checkboxTreeViewer.getControl(), "org.sidiff.slicer.rulebased.ui.viewer");
		getSite().setSelectionProvider(this.checkboxTreeViewer);
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
		
//		this.treeViewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
//		this.treeViewer.setContentProvider(new AdapterFactoryContentProvider(this.adapterFactory));
//		this.treeViewer.setLabelProvider(new AdapterFactoryLabelProvider(this.adapterFactory));
	}

	private void makeActions() {
		//begin selectSubTreeAction
		selectSubTreeAction = new Action() {
			public void run(){
				for(TreeItem treeItem : checkboxTreeViewer.getTree().getSelection()){
					checkboxTreeViewer.setSubtreeChecked(treeItem.getData(), true);
				}
			}
		};
		selectSubTreeAction.setText("Select Subtree");
		selectSubTreeAction.setImageDescriptor(RuleBasedSlicerUI.getImageDescriptor(RuleBasedSlicerUI.IMG_SELECT));
		//end selectSubTreeAction
		
		//begin sliceAction		
		synchAction = new Action() {
			public void run() {
				
				final AtomicReference<OperationExplorerView> operationExplorerViewReference = new AtomicReference<OperationExplorerView>();
				final AtomicReference<ReportView> reportViewReference = new AtomicReference<ReportView>();
				final AtomicReference<Resource> resourceResult = new AtomicReference<Resource>();
				
				Display.getDefault().syncExec(new Runnable() {

					@Override
					public void run() {
						try {
							
							if(localModifiedSlicedResource == null){
								localModifiedSlicedResource = UUIDResource.createUUIDResource(EMFStorage.pathToUri(EMFStorage.uriToPath(remoteResourceComplete.getURI()).replace("remote", "local")));
								Map<EObject, EObject> copies = EMFUtil.copyAll(localSlicedResource.getContents());
								for(EObject eObject : localSlicedResource.getContents()){
									localModifiedSlicedResource.getContents().add(copies.get(eObject));
								}
								for(EObject o : copies.keySet()){
									String id = EMFUtil.getXmiId(o);
									EMFUtil.setXmiId(copies.get(o), id);
								}
								localModifiedSlicedResource.save(null);
								
							}
							
							updateSlicingCriteria();
							ExecutableModelSlice slicingScript = (ExecutableModelSlice) slicer.slice(slicingCriteria);
							slicingScript.serialize(EMFStorage.uriToPath(remoteResourceComplete.getURI()).replace(remoteResourceComplete.getURI().lastSegment(), ""), false);
							for (EObject eObject : slicer.getExtendedAddSlicingCriteria()) {
								checkboxTreeViewer.setChecked(eObject, true);								
							}
							labelProvider.setAddElements(slicer.getExtendedAddSlicingCriteria());
							
							for (EObject eObject : slicer.getExtendedRemSlicingCriteria()) {
								checkboxTreeViewer.setChecked(eObject, false);								
							}
							labelProvider.setRemElements(slicer.getExtendedRemSlicingCriteria());
							checkboxTreeViewer.refresh();
					
							IEditorIntegration domainEditor = IntegrationEditorAccess.getInstance().getIntegrationEditorForModel(localModifiedSlicedResource);
							IEditorPart editorPart = domainEditor.openModelInDefaultEditor(localModifiedSlicedResource.getURI());
							EditingDomain editingDomain = domainEditor.getEditingDomain(editorPart);
							resourceResult.set(domainEditor.getResource(editorPart));
							
							if(asymDiff != null){
								
								mergeEngine = new PatchEngine(asymDiff, resourceResult.get(), mergeSettings);
								mergeEngine.setPatchedEditingDomain(editingDomain);
								// Init detector if available
								if (mergeSettings.getModifiedDetector() != null) {
									mergeSettings.getModifiedDetector().init(localSlicedResource, localModifiedSlicedResource, mergeSettings.getMatcher(), Scope.RESOURCE);
								}

								// Opening and setting operation explorer view
								OperationExplorerView operationExplorerView = (OperationExplorerView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(OperationExplorerView.ID);
								operationExplorerView.setPatchEngine(mergeEngine);
								operationExplorerViewReference.set(operationExplorerView);
								// Opening and setting report view
								ReportView reportView = (ReportView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(ReportView.ID);
									reportView.setPatchReportManager(mergeEngine.getPatchReportManager());
									reportViewReference.set(reportView);
							}			
					
						
						} catch (IOException | NotInitializedException e) {
							MessageDialog.openError(checkboxTreeViewer.getControl().getShell(), "Error", e.getMessage());
							e.printStackTrace();
						} catch (PartInitException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ExtendedSlicingCriteriaIntersectionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				
				if(mergeEngine != null){
					boolean conflicting = false;
					for(OperationInvocationWrapper wrapper : mergeEngine.getOperationManager().getOrderedOperationWrappers()){
						if(wrapper.hasUnresolvedInArguments()){
							conflicting = true;
							break;
						}
					}
					
					if(conflicting){
						OperationExplorerView operationExplorerView = operationExplorerViewReference.get();
						ModelAdapter adapter = new ModelAdapter(
								resourceResult.get());
						try {
							adapter.addListener(new ModelChangeHandler(mergeSettings.getArgumentManager()));
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						adapter.addListener(operationExplorerView);
					}else{
						mergeEngine.applyPatch(true);
					}
				}
			}
		};
		synchAction.setText("checkout/synchronize model");
		synchAction.setToolTipText("checkout/synchronize model");
		synchAction.setEnabled(false);
		synchAction.setImageDescriptor(RuleBasedSlicerUI.getImageDescriptor(RuleBasedSlicerUI.IMG_SYNCH));
		//end sliceAction
		
		//begin expandAllAction
		expandAllAction = new Action() {
			public void run() {
				checkboxTreeViewer.expandAll();
			}
		};
		expandAllAction.setToolTipText("expand all");
		expandAllAction.setImageDescriptor(RuleBasedSlicerUI.getImageDescriptor(RuleBasedSlicerUI.IMG_EXPANDALL));
		//end expandAllAction
		
		// begin collapseAllAction
		collapseAllAction = new Action() {
			public void run() {
				checkboxTreeViewer.collapseAll();
			}
		};
		collapseAllAction.setToolTipText("collapse all");
		collapseAllAction.setImageDescriptor(RuleBasedSlicerUI.getImageDescriptor(RuleBasedSlicerUI.IMG_COLLAPSEALL));
		// end collapseAllAction
				
		//begin doubleClickAction
		doubleClickAction = new Action() {
			public void run() {
				ISelection selection = checkboxTreeViewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				System.out.println(checkboxTreeViewer.getChecked(obj));
				if(checkboxTreeViewer.getGrayed(obj)){
					checkboxTreeViewer.setChecked(obj, false);
				}else if(checkboxTreeViewer.getChecked(obj)){
					checkboxTreeViewer.setGrayed(obj, true);
				}else {
					checkboxTreeViewer.setChecked(obj, true);
				}
			}
		};
		//end doubleClickAction
		
		checkboxTreeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
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
		Menu menu = menuMgr.createContextMenu(checkboxTreeViewer.getControl());
		checkboxTreeViewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, checkboxTreeViewer);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(selectSubTreeAction);
	}
	
	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(synchAction);
		manager.add(new Separator());
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(synchAction);
		manager.add(new Separator());
		manager.add(expandAllAction);
		manager.add(collapseAllAction);
	}

	private void hookDoubleClickAction() {
		checkboxTreeViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}
	
	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		checkboxTreeViewer.getControl().setFocus();
	}
	
	private void updateSlicingCriteria(){
		slicingCriteria.clear();
		for (Iterator<EObject> iterator = remoteResourceComplete.getAllContents(); iterator.hasNext();) {
			EObject eObject = iterator.next();
			if(this.checkboxTreeViewer.getChecked(eObject)){
				slicingCriteria.add(eObject);
			}
			
		}
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
}
