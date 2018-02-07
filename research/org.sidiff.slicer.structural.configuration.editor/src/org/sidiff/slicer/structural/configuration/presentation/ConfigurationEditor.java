/**
 */
package org.sidiff.slicer.structural.configuration.presentation;


import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.ui.MarkerHelper;
import org.eclipse.emf.common.ui.ViewerPane;
import org.eclipse.emf.common.ui.editor.ProblemEditorPart;
import org.eclipse.emf.common.ui.viewer.IViewerProvider;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.action.EditingDomainActionBarContributor;
import org.eclipse.emf.edit.ui.action.ValidateAction;
import org.eclipse.emf.edit.ui.celleditor.AdapterFactoryTreeEditor;
import org.eclipse.emf.edit.ui.dnd.EditingDomainViewerDropAdapter;
import org.eclipse.emf.edit.ui.dnd.LocalTransfer;
import org.eclipse.emf.edit.ui.dnd.ViewerDragAdapter;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.edit.ui.provider.DiagnosticDecorator;
import org.eclipse.emf.edit.ui.provider.UnwrappingSelectionProvider;
import org.eclipse.emf.edit.ui.util.EditUIUtil;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.DecoratingStyledCellLabelProvider;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.IToolTipProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.ToolTip;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.services.IDisposable;
import org.eclipse.ui.views.contentoutline.ContentOutline;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheet;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.sidiff.common.emf.EMFUtil;
import org.sidiff.slicer.structural.configuration.ConfigurationPackage;
import org.sidiff.slicer.structural.configuration.SlicedEClass;
import org.sidiff.slicer.structural.configuration.SlicingConfiguration;
import org.sidiff.slicer.structural.configuration.adapters.ImportedModelsEcoreItemProviderAdapterFactory;
import org.sidiff.slicer.structural.configuration.logic.ISlicingLogic;
import org.sidiff.slicer.structural.configuration.logic.ISlicingLogic.CheckboxState;
import org.sidiff.slicer.structural.configuration.logic.SlicingLogic;
import org.sidiff.slicer.structural.configuration.provider.ConfigurationItemProviderAdapterFactory;
import org.sidiff.slicer.structural.configuration.views.AlternativeElementsView;


/**
 * This is the main editor for Configuration models.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated NOT
 */
public class ConfigurationEditor
	extends MultiPageEditorPart
	implements IEditingDomainProvider, ISelectionProvider, IMenuListener, IViewerProvider, IGotoMarker, IConfigurationEditor {
	
	/**
	 * The ID of the editor as specified by the extension.
	 */
	public static final String ID = "org.sidiff.slicer.structural.configuration.presentation.ConfigurationEditorID"; //$NON-NLS-1$
	
	/**
	 * This keeps track of the editing domain that is used to track all changes to the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AdapterFactoryEditingDomain editingDomain;

	/**
	 * This is the one adapter factory used for providing views of the configuration.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected ComposedAdapterFactory adapterFactoryConfiguration;
	
	/**
	 * This is the one adapter factory used for providing views of the imported models.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected ComposedAdapterFactory adapterFactoryImports;

	/**
	 * This is the content outline page.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected ConfigurationEditorContentOutlinePage contentOutlinePage;

	/**
	 * This is the property sheet page.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected List<IPropertySheetPage> propertySheetPages = new ArrayList<IPropertySheetPage>();

	/**
	 * This is the viewer that shows the slicing configuration model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected TreeViewer configurationViewer;
	
	/**
	 * Map of viewers, that show the underlying model of the slicing configuration
	 * in a tree view with checkboxes, and their corresponding {@link SlicingLogicDelegate}s
	 */
	protected Map<CheckboxTreeViewer,SlicingLogicDelegate> modelViewersToDelegatesMap;

	/**
	 * The slicing configuration
	 */
	protected SlicingConfiguration slicingConfig;
	
	/**
	 * Flag for executing commands programmatically without changing the selection.
	 * Set to true to disable the command stack listener for the next command.
	 * The flag is automatically set to false once the command stack listener has been called.
	 */
	protected boolean commandStackListenerGuard = false;

	/**
	 * This keeps track of the active viewer pane, in the book.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ViewerPane currentViewerPane;

	/**
	 * This keeps track of the active content viewer, which may be either one of the viewers in the pages or the content outline viewer.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Viewer currentViewer;

	/**
	 * This listens to which ever viewer is active.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ISelectionChangedListener selectionChangedListener;

	/**
	 * This keeps track of all the {@link org.eclipse.jface.viewers.ISelectionChangedListener}s that are listening to this editor.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Collection<ISelectionChangedListener> selectionChangedListeners = new ArrayList<ISelectionChangedListener>();

	/**
	 * This keeps track of the selection of the editor as a whole.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ISelection editorSelection = StructuredSelection.EMPTY;

	/**
	 * The MarkerHelper is responsible for creating workspace resource markers presented
	 * in Eclipse's Problems View.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected MarkerHelper markerHelper = new ValidateAction.EclipseResourcesUtil();

	/**
	 * This listens for when the outline becomes active
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected IPartListener partListener = new IPartListener()
	{
		public void partActivated(IWorkbenchPart p)
		{
			if(p instanceof ContentOutline)
			{
				if(((ContentOutline)p).getCurrentPage() == contentOutlinePage)
				{
					getActionBarContributor().setActiveEditor(ConfigurationEditor.this);
					setCurrentViewer(contentOutlinePage.getViewer());
				}
			}
			else if(p instanceof PropertySheet)
			{
				if(propertySheetPages.contains(((PropertySheet)p).getCurrentPage()))
				{
					getActionBarContributor().setActiveEditor(ConfigurationEditor.this);
					handleActivate();
				}
			}
			else if(p == ConfigurationEditor.this)
			{
				handleActivate();
			}
		}

		public void partBroughtToTop(IWorkbenchPart p)
		{
			// Ignore.
		}

		public void partClosed(IWorkbenchPart p)
		{
			// Ignore.
		}

		public void partDeactivated(IWorkbenchPart p)
		{
			// Ignore.
		}

		public void partOpened(IWorkbenchPart p)
		{
			// Ignore.
		}
	};

	/**
	 * Resources that have been removed since last activation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Collection<Resource> removedResources = new ArrayList<Resource>();

	/**
	 * Resources that have been changed since last activation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Collection<Resource> changedResources = new ArrayList<Resource>();

	/**
	 * Resources that have been saved.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Collection<Resource> savedResources = new ArrayList<Resource>();

	/**
	 * Map to store the diagnostic associated with a resource.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Map<Resource, Diagnostic> resourceToDiagnosticMap = new LinkedHashMap<Resource, Diagnostic>();
	
	/**
	 * Controls whether the problem indication should be updated.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected boolean updateProblemIndication = true;

	/**
	 * This listens for workspace changes.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected IResourceChangeListener resourceChangeListener = new IResourceChangeListener()
	{
		public void resourceChanged(IResourceChangeEvent event)
		{
			IResourceDelta delta = event.getDelta();
			try
			{
				class ResourceDeltaVisitor implements IResourceDeltaVisitor
				{
					protected Collection<Resource> changedResources = new ArrayList<Resource>();
					protected Collection<Resource> removedResources = new ArrayList<Resource>();

					// these are to track movement / renaming of a configuration
					protected IPath movedFrom = null;
					protected IPath movedTo = null;

					public boolean visit(IResourceDelta delta)
					{
						if(delta.getResource().getType() == IResource.FILE)
						{
							if((delta.getFlags() & IResourceDelta.MOVED_FROM) > 0)
							{
								movedFrom = delta.getMovedFromPath();
							}
							else if((delta.getFlags() & IResourceDelta.MOVED_TO) > 0)
							{
								movedTo = delta.getMovedToPath();
							}
							else if(delta.getKind() == IResourceDelta.REMOVED || 
									(delta.getKind() == IResourceDelta.CHANGED && delta.getFlags() != IResourceDelta.MARKERS))
							{
								URI uri = URI.createPlatformResourceURI(delta.getFullPath().toString(), true);
								Resource resource = editingDomain.getResourceSet().getResource(uri, false);
								if(resource != null)
								{
									if(delta.getKind() == IResourceDelta.REMOVED)
									{
										removedResources.add(resource);
									}
									else if(!savedResources.remove(resource))
									{
										changedResources.add(resource);
									}
								}
							}

							return false;
						}

						return true;
					}
				}

				final ResourceDeltaVisitor visitor = new ResourceDeltaVisitor();
				delta.accept(visitor);

				if(!visitor.removedResources.isEmpty())
				{
					getSite().getShell().getDisplay().asyncExec(new Runnable()
					{
						public void run()
						{
							removedResources.addAll(visitor.removedResources);
							if(!isDirty())
							{
								getSite().getPage().closeEditor(ConfigurationEditor.this, false);
							}
						}
					});
				}

				if(!visitor.changedResources.isEmpty())
				{
					getSite().getShell().getDisplay().asyncExec(new Runnable()
					{
						public void run()
						{
							changedResources.addAll(visitor.changedResources);
							if(getSite().getPage().getActiveEditor() == ConfigurationEditor.this)
							{
								handleActivate();
							}
						}
					});
				}

				// handle rename / movement of the file that is open
				if(visitor.movedFrom != null && visitor.movedTo != null)
				{
					getSite().getShell().getDisplay().asyncExec(new Runnable()
					{
						public void run()
						{
							// make sure that the file being moved is the input of this editor
							IFile fileMovedFrom = ResourcesPlugin.getWorkspace().getRoot().getFile(visitor.movedFrom);
							IFile fileMovedTo = ResourcesPlugin.getWorkspace().getRoot().getFile(visitor.movedTo);
							if(fileMovedFrom != null && fileMovedTo != null && getEditorInput().equals(new FileEditorInput(fileMovedFrom)))
							{
								(editingDomain.getResourceSet().getResources().get(0)).setURI(
										URI.createPlatformResourceURI(fileMovedTo.getFullPath().toString(), true));
								IEditorInput input = new FileEditorInput(fileMovedTo);
								setInputWithNotify(input);
								setPartName(input.getName());
							}
						}
					});
				}
			}
			catch(CoreException exception)
			{
				ConfigurationEditorPlugin.INSTANCE.log(exception);
			}
		}
	};

	/**
	 * Listener for changed preferences in the preference store of the editor plugin.
	 * Refreshes the current viewer if it exists and the source of the event is
	 * the preference store of this plugin.
	 */
	protected IPropertyChangeListener propertyChangeListener = new IPropertyChangeListener()
	{
		@Override
		public void propertyChange(PropertyChangeEvent event)
		{
			if(event.getSource() == ConfigurationEditorPlugin.getPlugin().getPreferenceStore())
			{
				if(currentViewer != null)
				{
					currentViewer.refresh();
				}
			}
		}
	};

	/**
	 * Handles activation of the editor or it's associated views.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected void handleActivate() {
		// Recompute the read only state.
		//
		if(editingDomain.getResourceToReadOnlyMap() != null)
		{
			editingDomain.getResourceToReadOnlyMap().clear();

			// mark all imported models as read only
			if(slicingConfig != null)
			{
				for(EPackage importedPackage : slicingConfig.getImports())
				{
					editingDomain.getResourceToReadOnlyMap().put(importedPackage.eResource(), true);
				}
			}

			// Refresh any actions that may become enabled or disabled.
			//
			setSelection(getSelection());
		}

		if (!removedResources.isEmpty()) {
			if (handleDirtyConflict()) {
				getSite().getPage().closeEditor(ConfigurationEditor.this, false);
			}
			else {
				removedResources.clear();
				changedResources.clear();
				savedResources.clear();
			}
		}
		else if (!changedResources.isEmpty()) {
			changedResources.removeAll(savedResources);
			handleChangedResources();
			changedResources.clear();
			savedResources.clear();
		}
	}

	/**
	 * Handles what to do with changed resources on activation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void handleChangedResources() {
		if (!changedResources.isEmpty() && (!isDirty() || handleDirtyConflict())) {
			if (isDirty()) {
				changedResources.addAll(editingDomain.getResourceSet().getResources());
			}
			editingDomain.getCommandStack().flush();

			updateProblemIndication = false;
			for (Resource resource : changedResources) {
				if (resource.isLoaded()) {
					resource.unload();
					try {
						resource.load(Collections.EMPTY_MAP);
					}
					catch (IOException exception) {
						if (!resourceToDiagnosticMap.containsKey(resource)) {
							resourceToDiagnosticMap.put(resource, analyzeResourceProblems(resource, exception));
						}
					}
				}
			}

			if (AdapterFactoryEditingDomain.isStale(editorSelection)) {
				setSelection(StructuredSelection.EMPTY);
			}

			updateProblemIndication = true;
			updateProblemIndication(true);
		}
	}

	/**
	 * Updates the problems indication with the information described in the specified diagnostic.
	 * @param activate sets page active or not
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected void updateProblemIndication(boolean activate)
	{
		if(updateProblemIndication)
		{
			// create diagnostic to contain the individual ones
			BasicDiagnostic diagnostic = new BasicDiagnostic(Diagnostic.OK, ConfigurationEditorPlugin.ID,
					0, null, new Object[] { editingDomain.getResourceSet() });
			for(Diagnostic childDiagnostic : resourceToDiagnosticMap.values())
			{
				if(childDiagnostic.getSeverity() != Diagnostic.OK)
				{
					diagnostic.add(childDiagnostic);
				}
			}

			// add ecore validation diagnostics
			if(slicingConfig != null)
			{
				diagnostic.addAll(Diagnostician.INSTANCE.validate(slicingConfig));
			}

			// remove all problem editor parts that are not on the last page
			for(int i = 0; i < getPageCount() - 1; i++) // does not check the last entry!
			{
				if(getEditor(i) instanceof ProblemEditorPart)
				{
					removePage(i);
					i--; // the page is removed, hence the current index has to be checked again
				}
			}

			int lastEditorPage = getPageCount() - 1;
			if(lastEditorPage >= 0 && getEditor(lastEditorPage) instanceof ProblemEditorPart)
			{
				// update existing problem editor part
				((ProblemEditorPart)getEditor(lastEditorPage)).setDiagnostic(diagnostic);
			}
			else if(diagnostic.getSeverity() != Diagnostic.OK)
			{
				// create problem editor part if it does not exist and the diagnostic is not OK
				ProblemEditorPart problemEditorPart = new ProblemEditorPart();
				problemEditorPart.setDiagnostic(diagnostic);
				problemEditorPart.setMarkerHelper(markerHelper);
				try
				{
					addPage(++lastEditorPage, problemEditorPart, getEditorInput());
					setPageText(lastEditorPage, problemEditorPart.getPartName());
				}
				catch(PartInitException exception)
				{
					ConfigurationEditorPlugin.INSTANCE.log(exception);
				}
			}
			
			// activate the page if requested
			if(diagnostic.getSeverity() != Diagnostic.OK && activate && diagnostic.getSeverity() != Diagnostic.WARNING)
			{
				setActivePage(lastEditorPage);
			}

			// remove existing markers and create new ones
			if(markerHelper.hasMarkers(editingDomain.getResourceSet()))
			{
				markerHelper.deleteMarkers(editingDomain.getResourceSet());
				if(diagnostic.getSeverity() != Diagnostic.OK)
				{
					try
					{
						markerHelper.createMarkers(diagnostic);
					}
					catch(CoreException exception)
					{
						ConfigurationEditorPlugin.INSTANCE.log(exception);
					}
				}
			}
		}
	}
	
	/**
	 * Shows a dialog that asks if conflicting changes should be discarded.
	 * <!-- begin-user-doc -->
	 * @return whether the changes should be discarded
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected boolean handleDirtyConflict() {
		return MessageDialog.openQuestion(getSite().getShell(),
				ConfigurationEditorPlugin.getSubstitutedString("_UI_FileConflict_label"), //$NON-NLS-1$
				ConfigurationEditorPlugin.getSubstitutedString("_WARN_FileConflict")); //$NON-NLS-1$
	}

	/**
	 * This creates a model editor.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConfigurationEditor() {
		super();
		initializeEditingDomain();
	}

	/**
	 * This sets up the editing domain for the model editor.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected void initializeEditingDomain() {
		// Create an adapter factory that yields item providers for the configuration model.
		//
		adapterFactoryConfiguration = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		adapterFactoryConfiguration.addAdapterFactory(new ResourceItemProviderAdapterFactory());
		adapterFactoryConfiguration.addAdapterFactory(new ConfigurationItemProviderAdapterFactory());
		adapterFactoryConfiguration.addAdapterFactory(new EcoreItemProviderAdapterFactory());
		adapterFactoryConfiguration.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());

		// Create an adapter factory that yields item providers for the imported models.
		//
		adapterFactoryImports = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		adapterFactoryImports.addAdapterFactory(new ResourceItemProviderAdapterFactory());
		adapterFactoryImports.addAdapterFactory(new ConfigurationItemProviderAdapterFactory());
		adapterFactoryImports.addAdapterFactory(new ImportedModelsEcoreItemProviderAdapterFactory());
		adapterFactoryImports.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
		
		// Create the command stack that will notify this editor as commands are executed.
		//
		BasicCommandStack commandStack = new BasicCommandStack();

		// Add a listener to set the most recent command's affected objects to be the selection of the viewer with focus.
		//
		commandStack.addCommandStackListener(new CommandStackListener()
		{
			public void commandStackChanged(final EventObject event)
			{
				getContainer().getDisplay().asyncExec(new Runnable()
				{
					public void run()
					{
						firePropertyChange(IEditorPart.PROP_DIRTY);

						// check if listener is disabled
						if(commandStackListenerGuard)
						{
							commandStackListenerGuard = false;
							return;
						}
						
						// Try to select the affected objects.
						//
						Command mostRecentCommand = ((CommandStack)event.getSource()).getMostRecentCommand();
						if(mostRecentCommand != null)
						{
							setSelectionToViewer(mostRecentCommand.getAffectedObjects());

							// update the current viewer, the changes might have changed the checked states
							currentViewer.refresh();
							refreshAlternativeElementsView();
						}

						// update property sheet pages
						for(Iterator<IPropertySheetPage> i = propertySheetPages.iterator(); i.hasNext();)
						{
							IPropertySheetPage propertySheetPage = i.next();
							if(propertySheetPage.getControl().isDisposed())
							{
								i.remove();
							}
							else
							{
								if(propertySheetPage instanceof TabbedPropertySheetPage) {
								// make sure that the tab exists
								if(((TabbedPropertySheetPage)propertySheetPage).getCurrentTab() != null)
									((TabbedPropertySheetPage)propertySheetPage).refresh();
								}
							}
						}

						// check if the command modified the list of imported models
						// modifications of the imports are wrapped in a CompoundCommand
						if(mostRecentCommand instanceof CompoundCommand)
						{
							// go over all commands that the CompoundCommand consists of
							for(Command cmd : ((CompoundCommand)mostRecentCommand).getCommandList())
							{
								if(cmd instanceof AddCommand)
								{
									// check if the Imports were modified
									if(((AddCommand)cmd).getFeature() == ConfigurationPackage.Literals.SLICING_CONFIGURATION__IMPORTS)
									{
										// reload models, recreate pages
										recreateModelPages();
										
										// one recreation is enough
										break;
									}
								}
								else if(cmd instanceof RemoveCommand)
								{
									// check if the Imports were modified
									if(((RemoveCommand)cmd).getFeature() == ConfigurationPackage.Literals.SLICING_CONFIGURATION__IMPORTS)
									{
										// reload models, recreate pages
										recreateModelPages();
										
										// one recreation is enough
										break;
									}
								}
								
								// MoveCommand might also be occur, but it only changes the order of the imports and will be ignored
							}
						}
					}
				});
			}
		});

		// Create the editing domain with a special command stack.
		//
		editingDomain = new AdapterFactoryEditingDomain(adapterFactoryConfiguration, commandStack, new HashMap<Resource, Boolean>());
	}

	/**
	 * This is here for the listener to be able to call it.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void firePropertyChange(int action) {
		super.firePropertyChange(action);
	}

	/**
	 * This sets the selection into whichever viewer is active.
	 * <!-- begin-user-doc -->
	 * @param collection the collection of elements to select
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSelectionToViewer(Collection<?> collection) {
		final Collection<?> theSelection = collection;
		// Make sure it's okay.
		//
		if (theSelection != null && !theSelection.isEmpty()) {
			Runnable runnable =
				new Runnable() {
					public void run() {
						// Try to select the items in the current content viewer of the editor.
						//
						if (currentViewer != null) {
							currentViewer.setSelection(new StructuredSelection(theSelection.toArray()), true);
						}
					}
				};
			getSite().getShell().getDisplay().asyncExec(runnable);
		}
	}

	/**
	 * This returns the editing domain as required by the {@link IEditingDomainProvider} interface.
	 * This is important for implementing the static methods of {@link AdapterFactoryEditingDomain}
	 * and for supporting {@link org.eclipse.emf.edit.ui.action.CommandAction}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EditingDomain getEditingDomain() {
		return editingDomain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param viewerPane the new viewer pane
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setCurrentViewerPane(ViewerPane viewerPane)
	{
		if(currentViewerPane != viewerPane)
		{
			if(currentViewerPane != null)
			{
				currentViewerPane.showFocus(false);
			}
			currentViewerPane = viewerPane;
		}
		setCurrentViewer(currentViewerPane == null ? null : currentViewerPane.getViewer());
	}

	/**
	 * This makes sure that one content viewer, either for the current page or the outline view, if it has focus,
	 * is the current one.
	 * <!-- begin-user-doc -->
	 * @param viewer the new viewer
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setCurrentViewer(Viewer viewer) {
		// If it is changing...
		//
		if (currentViewer != viewer) {
			if (selectionChangedListener == null) {
				// Create the listener on demand.
				//
				selectionChangedListener =
					new ISelectionChangedListener() {
						// This just notifies those things that are affected by the section.
						//
						public void selectionChanged(SelectionChangedEvent selectionChangedEvent) {
							setSelection(selectionChangedEvent.getSelection());
						}
					};
			}

			// Stop listening to the old one.
			//
			if (currentViewer != null) {
				currentViewer.removeSelectionChangedListener(selectionChangedListener);
			}

			// Start listening to the new one.
			//
			if (viewer != null) {
				viewer.addSelectionChangedListener(selectionChangedListener);
			}

			// Remember it.
			//
			currentViewer = viewer;

			// Set the editors selection based on the current viewer's selection.
			//
			setSelection(currentViewer == null ? StructuredSelection.EMPTY : currentViewer.getSelection());
			
			// refresh viewer (check states)
			if(currentViewer != null)
			{
				currentViewer.refresh();
			}
		}
	}

	/**
	 * This returns the viewer as required by the {@link IViewerProvider} interface.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Viewer getViewer() {
		return currentViewer;
	}

	/**
	 * This creates a context menu for the viewer and adds a listener as well registering the menu for extension.
	 * <!-- begin-user-doc -->
	 * @param viewer the viewer to create the context menu for
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createContextMenuFor(StructuredViewer viewer) {
		MenuManager contextMenu = new MenuManager("#PopUp"); //$NON-NLS-1$
		contextMenu.add(new Separator("additions")); //$NON-NLS-1$
		contextMenu.setRemoveAllWhenShown(true);
		contextMenu.addMenuListener(this);
		Menu menu= contextMenu.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(contextMenu, new UnwrappingSelectionProvider(viewer));

		int dndOperations = DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_LINK;
		Transfer[] transfers = new Transfer[] { LocalTransfer.getInstance(), LocalSelectionTransfer.getTransfer(), FileTransfer.getInstance() };
		viewer.addDragSupport(dndOperations, transfers, new ViewerDragAdapter(viewer));
		viewer.addDropSupport(dndOperations, transfers, new EditingDomainViewerDropAdapter(editingDomain, viewer));
	}

	/**
	 * This is the method called to load a resource into the editing domain's resource set based on the editor's input.
	 * <!-- begin-user-doc -->
	 * @param progressMonitor a ProgressMonitor to report the progress of this method
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void createModel(IProgressMonitor progressMonitor)
	{
		// create SubMonitor for progress in current function
		SubMonitor sub = SubMonitor.convert(progressMonitor, 2);
		
		// load model
		URI resourceURI = EditUIUtil.getURI(getEditorInput(), editingDomain.getResourceSet().getURIConverter());
		Exception exception = null;
		Resource resource = null;
		try
		{
			// Load the resource through the editing domain.
			//
			resource = editingDomain.getResourceSet().getResource(resourceURI, true);
		}
		catch (Exception e)
		{
			exception = e;
			resource = editingDomain.getResourceSet().getResource(resourceURI, false);
		}
		sub.worked(1);

		// handle errors
		Diagnostic diagnostic = analyzeResourceProblems(resource, exception);
		if (diagnostic.getSeverity() != Diagnostic.OK)
		{
			resourceToDiagnosticMap.put(resource,  diagnostic);
		}
		else
		{
			// load the imported models
			EObject root = resource.getContents().get(0);
			if(root instanceof SlicingConfiguration)
			{
				slicingConfig = (SlicingConfiguration)root;
				loadImportedResources(sub.newChild(1));
			}
			else
			{
				// the root element is not of type SlicingConfiguration
				slicingConfig = null;
				
				// create diagnostic for invalid root element
				BasicDiagnostic diag = new BasicDiagnostic(
						Diagnostic.ERROR,
						ConfigurationEditorPlugin.ID,
						0,
						ConfigurationEditorPlugin.INSTANCE.getString("_UI_ConfigEditor_ShouldBeSlicing"), //$NON-NLS-1$
						new Object[] { root });

				resourceToDiagnosticMap.put(resource, diag);
				sub.worked(1);
			}
		}
	}

	/**
	 * Load the imported resources. Clears all imported resources that have been loaded previously.
	 * @param progressMonitor a ProgressMonitor to report the progress of this method
	 */
	protected void loadImportedResources(IProgressMonitor progressMonitor)
	{
		// check if slicing configuration is set
		if(slicingConfig == null)
			return;
		
		// create SubMonitor for progress in current function
		SubMonitor sub = SubMonitor.convert(progressMonitor, slicingConfig.getImports().size());
		
		// load all imported models
		for(EPackage importPackage : slicingConfig.getImports())
		{
			if(progressMonitor.isCanceled())
			{
				progressMonitor.done();
				return;
			}
			
			// resolve proxy package
			if(importPackage.eIsProxy())
			{
				// resolve the package using the package registry
				EObject resolvedPackage = EcoreUtil.resolve(importPackage, (ResourceSet)null); // null to resolve globally

				// check if resolved object is EPackage and not a proxy anymore
				if(resolvedPackage instanceof EPackage && !resolvedPackage.eIsProxy())
				{
					// proxy was resolved successfully
					importPackage = (EPackage)resolvedPackage;
				}
				else
				{
					sub.worked(1);
					continue;
				}
			}
			
			// create model URI
			URI modelURI = URI.createURI(importPackage.getNsURI());
			Resource importResource;
			Exception importException = null;
			
			try
			{
				// Load the resource through the editing domain.
				//
				importResource = editingDomain.getResourceSet().getResource(modelURI, true);
			}
			catch (Exception e)
			{
				importException = e;
				importResource = editingDomain.getResourceSet().getResource(modelURI, false);
			}
			
			// handle errors
			Diagnostic diagnostic = analyzeResourceProblems(importResource, importException);
			if(diagnostic.getSeverity() != Diagnostic.OK)
			{
				resourceToDiagnosticMap.put(importResource, diagnostic);
			}

			sub.worked(1);
		}
		
		// check selection and imports after resolving models
		analyzeImportedResources();
	}

	/**
	 * Check if for each element in the selection is the model imported and if for each imported model is a element selected. Also check if model not found.
	 * Adds a diagnostic to the {@link #resourceToDiagnosticMap} for the main resource using the {@link #addToMainDiagnostic(Diagnostic)} 
	 * method and create error and warning marker.
	 */
	public void analyzeImportedResources() {
		
		if(slicingConfig != null){	
			
			Resource resource = slicingConfig.eResource();
			
			//remove diagnostics
			Diagnostic diagContainer = resourceToDiagnosticMap.get(slicingConfig.eResource());
			Assert.isTrue(diagContainer == null || diagContainer instanceof BasicDiagnostic,
					"diagnostic for main resource is not a BasicDiagnostic"); //$NON-NLS-1$

			if(diagContainer != null){
				resourceToDiagnosticMap.remove(resource, diagContainer);
				
			}
			
			//check if selection and imports both empty
			if(slicingConfig.getSlicedEClasses().isEmpty() && slicingConfig.getImports().isEmpty()){
				BasicDiagnostic basicDiagnostic =	
						new BasicDiagnostic
							(Diagnostic.WARNING,
							 ConfigurationEditorPlugin.ID,
							 0,
							 ConfigurationEditorPlugin.INSTANCE.getString("_UI_ConfigEditor_SelectionImport_Empty"), //$NON-NLS-1$
							 new Object[] { slicingConfig });
				
				addToMainDiagnostic(basicDiagnostic);
			}
			else{
				// check if for each element in the selection is model imported
				for(SlicedEClass sC : slicingConfig.getSlicedEClasses()){
					boolean found = false;
					boolean proxyOrNull = true;
					
					if(!sC.eIsProxy() && sC.getType() != null && !sC.getType().eIsProxy() && !sC.getType().getEPackage().eIsProxy()){						
						proxyOrNull = false;
						for(EPackage Ip : slicingConfig.getImports()){
							if(Ip != null && !Ip.eIsProxy()){
								if(sC.getType().getEPackage().eResource() == Ip.eResource()){
									found = true;
									break;
								}
							}
						}
						if((!found && !proxyOrNull) || (!found && slicingConfig.getImports().isEmpty())){
							BasicDiagnostic basicDiagnostic =	
									new BasicDiagnostic
										(Diagnostic.ERROR,
										 ConfigurationEditorPlugin.ID,
										 0,
										 ConfigurationEditorPlugin.getSubstitutedString("_UI_ConfigEditor_ImportedModelMissing", //$NON-NLS-1$
												 sC.getType().getEPackage().getName(), sC.getType().getName()), 
										 new Object[] { sC });
							
							addToMainDiagnostic(basicDiagnostic);
						}
					}
				}
				
				// check if for each imported model is a element selected
				for(EPackage Ip : slicingConfig.getImports()){
					boolean found = false;
					boolean proxyOrNull = true;
					if(Ip != null && !Ip.eIsProxy()){
						proxyOrNull = false;
					for(SlicedEClass sC : slicingConfig.getSlicedEClasses()){
						if(!sC.eIsProxy() && sC.getType() != null && !sC.getType().eIsProxy() && !sC.getType().getEPackage().eIsProxy()){
							if(sC.getType().getEPackage().eResource() == Ip.eResource()){
								found = true;
								break;
							}
						}
					}
					if((!found && !proxyOrNull) || (!found && slicingConfig.getSlicedEClasses().isEmpty())){
						BasicDiagnostic basicDiagnostic =	
								new BasicDiagnostic
									(Diagnostic.WARNING,
									 ConfigurationEditorPlugin.ID,
									 0,
									 ConfigurationEditorPlugin.INSTANCE.getString("_UI_ConfigEditor_NoElementSelected", new Object [] {Ip.getName()}), //$NON-NLS-1$
									 new Object[] { slicingConfig });
						
						addToMainDiagnostic(basicDiagnostic);
					}
					}else{
						
						//if package is proxy it wasn't resolved in loadImportedResources
						if(Ip.eIsProxy()){
						// create diagnostic for missing imported package
						BasicDiagnostic diag = new BasicDiagnostic(
								Diagnostic.ERROR,
								ConfigurationEditorPlugin.ID,
								0,
								ConfigurationEditorPlugin.INSTANCE.getString("_UI_ConfigEditor_ImportedPackageNotFound", new Object [] {EMFUtil.getEObjectID(Ip)}), //$NON-NLS-1$
								new Object[] { slicingConfig });

						addToMainDiagnostic(diag);
						}
					}
				}
			}
		}
	}
	
	/**
	 * Returns a diagnostic describing the errors and warnings listed in the resource
	 * and the specified exception (if any).
	 * <!-- begin-user-doc -->
	 * @param resource the resource
	 * @param exception the exception, <code>null</code> if none
	 * @return a diagnostic
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Diagnostic analyzeResourceProblems(Resource resource, Exception exception) {
		boolean hasErrors = !resource.getErrors().isEmpty();
		if (hasErrors || !resource.getWarnings().isEmpty()) {
			BasicDiagnostic basicDiagnostic =
				new BasicDiagnostic
					(hasErrors ? Diagnostic.ERROR : Diagnostic.WARNING,
					 ConfigurationEditorPlugin.ID,
					 0,
					 ConfigurationEditorPlugin.getSubstitutedString("_UI_CreateModelError_message", resource.getURI()), //$NON-NLS-1$
					 new Object [] { exception == null ? (Object)resource : exception });
			basicDiagnostic.merge(EcoreUtil.computeDiagnostic(resource, true));
			return basicDiagnostic;
		}
		else if (exception != null) {
			return
				new BasicDiagnostic
					(Diagnostic.ERROR,
					 ConfigurationEditorPlugin.ID,
					 0,
					 ConfigurationEditorPlugin.getSubstitutedString("_UI_CreateModelError_message", resource.getURI()), //$NON-NLS-1$
					 new Object[] { exception });
		}
		else {
			return Diagnostic.OK_INSTANCE;
		}
	}
	
	/**
	 * Adds a diagnostic to the {@link #resourceToDiagnosticMap} for the main resource.
	 * The diagnostic is wrapped in another diagnostic to that multiple diagnostics
	 * can be attached to the main resource.<br>
	 * This also creates a marker using the {@link #markerHelper}.
	 * @param diag the diagnostic to add, must not be <code>null</code>
	 */
	protected void addToMainDiagnostic(Diagnostic diag)
	{
		// check if slicing configuration is set
		if(slicingConfig == null)
			return;

		Diagnostic diagContainer = resourceToDiagnosticMap.get(slicingConfig.eResource());
		Assert.isTrue(diagContainer == null || diagContainer instanceof BasicDiagnostic,
				"diagnostic for main resource is not a BasicDiagnostic"); //$NON-NLS-1$

		// create new diagnostic container if non exists
		if(diagContainer == null)
		{
			diagContainer = new BasicDiagnostic(
					Diagnostic.ERROR,
					ConfigurationEditorPlugin.ID,
					0,
					ConfigurationEditorPlugin.INSTANCE.getString("_UI_ConfigEditor_ProblemsInConfig"), //$NON-NLS-1$
					null);
			
			resourceToDiagnosticMap.put(slicingConfig.eResource(), diagContainer);
		}

		// add the diagnostic to the container and update the severity
		((BasicDiagnostic)diagContainer).add(diag);
		((BasicDiagnostic)diagContainer).recomputeSeverity();

		// create a marker
		try
		{
			markerHelper.createMarkers(diag);
		}
		catch(CoreException exception)
		{
			ConfigurationEditorPlugin.INSTANCE.log(exception);
		}
	}

	/**
	 * This is the method used by the framework to install your own controls.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public void createPages()
	{
		WorkspaceModifyOperation operation = new WorkspaceModifyOperation()
		{
			@Override
			public void execute(IProgressMonitor monitor)
			{
				// create SubMonitor for creating subtasks
				SubMonitor sub = SubMonitor.convert(monitor, 2);
				
				// Creates the model from the editor input
				//
				sub.subTask(ConfigurationEditorPlugin.INSTANCE.getString("_UI_ConfigEditor_LoadingModels")); //$NON-NLS-1$
				createModel(sub.newChild(1));

				// Only creates the other pages if there is something that can be edited
				//
				if(!getEditingDomain().getResourceSet().getResources().isEmpty())
				{
					// Create a page for the selection tree view.
					//
					getSite().getShell().getDisplay().syncExec(new Runnable()
					{
						public void run()
						{
							ViewerPane viewerPane = new ViewerPane(getSite().getPage(), ConfigurationEditor.this)
							{
								@Override
								public Viewer createViewer(Composite composite)
								{
									Tree tree = new Tree(composite, SWT.MULTI);
									TreeViewer newTreeViewer = new TreeViewer(tree);
									return newTreeViewer;
								}
	
								@Override
								public void requestActivation()
								{
									super.requestActivation();
									setCurrentViewerPane(this);
								}
							};
							viewerPane.createControl(getContainer());
	
							configurationViewer = (TreeViewer)viewerPane.getViewer();
							configurationViewer.setContentProvider(new AdapterFactoryContentProvider(adapterFactoryConfiguration));
							configurationViewer.setLabelProvider(new DecoratingLabelProvider(
									new AdapterFactoryLabelProvider(adapterFactoryConfiguration),
									new DiagnosticDecorator(editingDomain, configurationViewer))); // problem markers as decoration
							Resource configResource = editingDomain.getResourceSet().getResources().get(0);
							configurationViewer.setInput(configResource);
							configurationViewer.setSelection(new StructuredSelection(configResource), true);
	
							if(slicingConfig != null)
							{
								// fully expand the slicing configuration
								configurationViewer.expandToLevel(slicingConfig, TreeViewer.ALL_LEVELS);
	
								// set title
								viewerPane.setTitle(slicingConfig);
							}
	
							new AdapterFactoryTreeEditor(configurationViewer.getTree(), adapterFactoryConfiguration);
	
							createContextMenuFor(configurationViewer);
							int pageIndex = addPage(viewerPane.getControl());
							setPageText(pageIndex, ConfigurationEditorPlugin.getSubstitutedString("_UI_ConfigurationPage_label")); //$NON-NLS-1$
						}
					});

					// Create a page with a checkbox tree viewer for each imported model
					// If no models are imported, no viewers are created
					//
					sub.subTask(ConfigurationEditorPlugin.INSTANCE.getString("_UI_ConfigEditor_CreatingModelViewers")); //$NON-NLS-1$
					createModelViewers(sub.newChild(1));

					getSite().getShell().getDisplay().syncExec(new Runnable()
					{
						public void run()
						{
							setActivePage(0);
						}
					});
				}

				getSite().getShell().getDisplay().syncExec(new Runnable()
				{
					public void run()
					{
						updateProblemIndication(true);
					}
				});
			}
		};

		// start operation and show progress dialog
		try
		{
			PlatformUI.getWorkbench().getProgressService().run(true, true, operation);
		}
		catch(InvocationTargetException | InterruptedException e)
		{
			ConfigurationEditorPlugin.INSTANCE.log(e);
		}
		
		try 
		{
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(AlternativeElementsView.ID, null, IWorkbenchPage.VIEW_VISIBLE);
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(IPageLayout.ID_PROP_SHEET, null, IWorkbenchPage.VIEW_VISIBLE);
		} 
		catch (PartInitException e) 
		{	
			ConfigurationEditorPlugin.INSTANCE.log(e);		
		}	
	}

	/**
	 * Creates a CheckboxTreeViewer for every imported model and adds them as pages to the editor.
	 * @param progressMonitor a ProgressMonitor to report the progress of this method
	 */
	protected void createModelViewers(IProgressMonitor progressMonitor)
	{
		// using a LinkedHashMap for predictable key ordering
		modelViewersToDelegatesMap = new LinkedHashMap<CheckboxTreeViewer, ConfigurationEditor.SlicingLogicDelegate>();

		if(slicingConfig == null)
			return;

		// create SubMonitor for progress in current function
		int workRemaining = slicingConfig.getImports().size();
		SubMonitor sub = SubMonitor.convert(progressMonitor, workRemaining);

		// create a viewer for every imported model
		for(final EPackage importedPackage : slicingConfig.getImports())
		{
			if(progressMonitor.isCanceled())
			{
				progressMonitor.done();
				return;
			}

			final Resource modelResource = importedPackage.eResource();

			// create no tab if model was not loaded
			if(modelResource == null)
			{
				// decrement the remaining work instead of incrementing
				// the completed work for a smoother progress bar
				workRemaining--;
				sub.setWorkRemaining(workRemaining);
				continue;
			}

			getSite().getShell().getDisplay().syncExec(new Runnable()
			{
				public void run()
				{
					ViewerPane viewerPane = new ImportedModelViewerPane(getSite().getPage(), ConfigurationEditor.this)
					{
						@Override
						public void requestActivation()
						{
							super.requestActivation();
							setCurrentViewerPane(this);
						}
					};

					viewerPane.createControl(getContainer());
					CheckboxTreeViewer modelViewer = (CheckboxTreeViewer)viewerPane.getViewer();
					modelViewer.setContentProvider(new AdapterFactoryContentProvider(adapterFactoryImports));

					// create a delegate that implements a font and color provider, a check state provider,
					// a label decorator, and a check state listener for the model viewer and delegates
					// the functions to the slicing logic
					final SlicingLogicDelegate slicingLogicDelegate = new SlicingLogicDelegate(adapterFactoryImports, modelViewer, modelResource);
					modelViewer.setLabelProvider(new DecoratingStyledCellLabelProvider(slicingLogicDelegate, slicingLogicDelegate, null));
					modelViewer.setCheckStateProvider(slicingLogicDelegate);
					modelViewer.addCheckStateListener(slicingLogicDelegate);
					ColumnViewerToolTipSupport.enableFor(modelViewer, ToolTip.RECREATE);

					// set the model
					modelViewer.setInput(modelResource);
					modelViewer.setSelection(new StructuredSelection(modelResource));
					viewerPane.setTitle(importedPackage.getName() + " - " + importedPackage.getNsURI(), //$NON-NLS-1$
							slicingLogicDelegate.getImage(importedPackage));

					new AdapterFactoryTreeEditor(modelViewer.getTree(), adapterFactoryImports);

					// expand the EPackage and all checked elements
					modelViewer.expandToLevel(2);
					modelViewer.setExpandedElements(modelViewer.getCheckedElements());

					createContextMenuFor(modelViewer);
					int pageIndex = addPage(viewerPane.getControl());
					setPageText(pageIndex, ConfigurationEditorPlugin.getSubstitutedString("_UI_ModelPage_label", //$NON-NLS-1$
							importedPackage.getName())); // model name as page text

					// add model viewer to the list
					modelViewersToDelegatesMap.put(modelViewer, slicingLogicDelegate);
				}
			});

			sub.worked(1);
		}
	}
	
	/**
	 * Recreates all model pages.
	 * Removes all pages except the first one. Reloads the imported resources and
	 * creates a model viewer for each one.
	 */
	protected void recreateModelPages()
	{
		// remove all pages except for first one
		while(getPageCount() > 1)
			removePage(1);
		
		// reload the imported resources and recreate the model viewers in an operation
		WorkspaceModifyOperation operation = new WorkspaceModifyOperation()
		{
			@Override
			public void execute(IProgressMonitor monitor)
			{
				// create SubMonitor for creating subtasks
				SubMonitor sub = SubMonitor.convert(monitor, 2);

				// reload the imported resources
				sub.subTask(ConfigurationEditorPlugin.INSTANCE.getString("_UI_ConfigEditor_LoadingModels")); //$NON-NLS-1$
				loadImportedResources(sub.newChild(1));
				
				if(monitor.isCanceled())
				{
					monitor.done();
					return;
				}
				
				// recreate the model viewers
				sub.subTask(ConfigurationEditorPlugin.INSTANCE.getString("_UI_ConfigEditor_CreatingModelViewers")); //$NON-NLS-1$
				createModelViewers(sub.newChild(1));
				
				// update problem indication
				getSite().getShell().getDisplay().syncExec(new Runnable()
				{
					public void run()
					{
						analyzeImportedResources();
						updateProblemIndication(true);
					}
				});
			}
		};

		// start operation and show progress dialog
		try
		{
			PlatformUI.getWorkbench().getProgressService().run(true, true, operation);
		}
		catch(InvocationTargetException | InterruptedException e)
		{
			ConfigurationEditorPlugin.INSTANCE.log(e);
		}
	}

	/**
	 * This is used to track the active viewer.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	protected void pageChange(int pageIndex)
	{
		super.pageChange(pageIndex);

		// focus the page; handleActivation is only called when the page is focused
		// without the focus, the outline does not work correctly
		if(getSelectedPage() instanceof Control)
		{
			((Control)getSelectedPage()).setFocus();
		}
		
		// update the model that is displayed in the outline view
		if(contentOutlinePage != null)
		{
			contentOutlinePage.updateInputAndSelection();
		}
	}

	/**
	 * This is how the framework determines which interfaces we implement.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object getAdapter(Class key)
	{
		if(key.equals(IContentOutlinePage.class))
		{
			return showOutlineView() ? getContentOutlinePage() : null;
		}
		else if(key.equals(IPropertySheetPage.class))
		{
			return getPropertySheetPage();
		}
		else if(key.equals(IGotoMarker.class))
		{
			return this;
		}
		
		return super.getAdapter(key);
	}

	/**
	 * This accesses a cached version of the content outliner.
	 * <!-- begin-user-doc -->
	 * @return the content outline page
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public IContentOutlinePage getContentOutlinePage()
	{
		if(contentOutlinePage == null)
		{
			contentOutlinePage = new ConfigurationEditorContentOutlinePage();

			// Listen to selection so that we can handle it is a special way.
			//
			contentOutlinePage.addSelectionChangedListener(new ISelectionChangedListener()
			{
				// This ensures that we handle selections correctly.
				//
				public void selectionChanged(SelectionChangedEvent event)
				{
					handleContentOutlineSelection(event.getSelection());
				}
			});
		}

		return contentOutlinePage;
	}

	/**
	 * This accesses a cached version of the property sheet.
	 * <!-- begin-user-doc -->
	 * @return the property sheet page
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public IPropertySheetPage getPropertySheetPage() {
		// create tabbed property sheet page
		IPropertySheetPage propertySheetPage = new TabbedPropertySheetPage(new ITabbedPropertySheetPageContributor()
		{
			@Override
			public String getContributorId()
			{
				return getSite().getId();
			}
		});

		propertySheetPages.add(propertySheetPage);
		return propertySheetPage;
	}

	/**
	 * This deals with how we want selection in the outline to affect the other views.
	 * <!-- begin-user-doc -->
	 * @param selection the selection in the outline view
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void handleContentOutlineSelection(ISelection selection)
	{
		if(currentViewerPane != null && !selection.isEmpty() && selection instanceof IStructuredSelection)
		{
			// propagate the outline selection to the current viewer pane
			currentViewerPane.getViewer().setSelection(selection, true);

			// update all property sheet pages to properly show the constraint tab
			for(IPropertySheetPage propertySheetPage : propertySheetPages)
			{
				if(!propertySheetPage.getControl().isDisposed())
				{
					propertySheetPage.selectionChanged(ConfigurationEditor.this, selection);
				}
			}
		}
	}

	/**
	 * This is for implementing {@link IEditorPart} and simply tests the command stack.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDirty() {
		return ((BasicCommandStack)editingDomain.getCommandStack()).isSaveNeeded();
	}

	/**
	 * This is for implementing {@link IEditorPart} and simply saves the model file.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public void doSave(IProgressMonitor progressMonitor)
	{
		// Save only resources that have actually changed.
		//
		final Map<Object, Object> saveOptions = new HashMap<Object, Object>();
		saveOptions.put(Resource.OPTION_SAVE_ONLY_IF_CHANGED, Resource.OPTION_SAVE_ONLY_IF_CHANGED_MEMORY_BUFFER);
		saveOptions.put(Resource.OPTION_LINE_DELIMITER, Resource.OPTION_LINE_DELIMITER_UNSPECIFIED);

		// Do the work within an operation because this is a long running activity that modifies the workbench.
		//
		WorkspaceModifyOperation operation = new WorkspaceModifyOperation()
		{
			// This is the method that gets invoked when the operation runs.
			//
			@Override
			public void execute(IProgressMonitor monitor)
			{
				// Save the resource to the file system.
				// Only the main resource is saved.
				Resource resource = editingDomain.getResourceSet().getResources().get(0);
				try
				{
					long timeStamp = resource.getTimeStamp();
					resource.save(saveOptions);
					if(resource.getTimeStamp() != timeStamp)
					{
						savedResources.add(resource);
					}
				}
				catch(Exception exception)
				{
					resourceToDiagnosticMap.put(resource, analyzeResourceProblems(resource, exception));
				}
			}
		};

		updateProblemIndication = false;
		try
		{
			// This runs the options, and shows progress.
			//
			new ProgressMonitorDialog(getSite().getShell()).run(true, false, operation);

			// Refresh the necessary state.
			//
			((BasicCommandStack)editingDomain.getCommandStack()).saveIsDone();
			firePropertyChange(IEditorPart.PROP_DIRTY);
		}
		catch(Exception exception)
		{
			// Something went wrong that shouldn't.
			//
			ConfigurationEditorPlugin.INSTANCE.log(exception);
		}

		//check selection and imports
		analyzeImportedResources();

		updateProblemIndication = true;
		updateProblemIndication(true);
	}

	/**
	 * This returns whether something has been persisted to the URI of the specified resource.
	 * The implementation uses the URI converter from the editor's resource set to try to open an input stream.
	 * <!-- begin-user-doc -->
	 * @param resource the resource
	 * @return whether the resource is persisted
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected boolean isPersisted(Resource resource) {
		boolean result = false;
		try {
			InputStream stream = editingDomain.getResourceSet().getURIConverter().createInputStream(resource.getURI());
			if (stream != null) {
				result = true;
				stream.close();
			}
		}
		catch (IOException e) {
			// Ignore
		}
		return result;
	}

	/**
	 * This always returns true because it is not currently supported.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}

	/**
	 * Shows the Save as-dialog and changes the editor's input.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void doSaveAs() {
		SaveAsDialog saveAsDialog = new SaveAsDialog(getSite().getShell());
		saveAsDialog.open();
		IPath path = saveAsDialog.getResult();
		if (path != null) {
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
			if (file != null) {
				doSaveAs(URI.createPlatformResourceURI(file.getFullPath().toString(), true), new FileEditorInput(file));
			}
		}
	}

	/**
	 * Saves the resource to the location specified by the uri and sets the editor input to the specified input.
	 * <!-- begin-user-doc -->
	 * @param uri the uri
	 * @param editorInput the new editor input
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void doSaveAs(URI uri, IEditorInput editorInput) {
		(editingDomain.getResourceSet().getResources().get(0)).setURI(uri);
		setInputWithNotify(editorInput);
		setPartName(editorInput.getName());
		IProgressMonitor progressMonitor =
			getActionBars().getStatusLineManager() != null ?
				getActionBars().getStatusLineManager().getProgressMonitor() :
				new NullProgressMonitor();
		doSave(progressMonitor);
	}

	/**
	 * Activates the configuration tab and selects the target objects of the marker.
	 * <!-- begin-user-doc -->
	 * @param marker the marker
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void gotoMarker(IMarker marker)
	{
		List<?> targetObjects = markerHelper.getTargetObjects(editingDomain, marker);
		if(!targetObjects.isEmpty())
		{
			// activate configuration tab
			setActivePage(0);
			
			// select the objects
			setSelectionToViewer(targetObjects);
		}
	}

	/**
	 * This is called during startup.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void init(IEditorSite site, IEditorInput editorInput) {
		setSite(site);
		setInputWithNotify(editorInput);
		setPartName(editorInput.getName());
		site.setSelectionProvider(this);
		site.getPage().addPartListener(partListener);
		ResourcesPlugin.getWorkspace().addResourceChangeListener(resourceChangeListener, IResourceChangeEvent.POST_CHANGE);
		ConfigurationEditorPlugin.getPlugin().getPreferenceStore().addPropertyChangeListener(propertyChangeListener);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFocus() {
		if (currentViewerPane != null) {
			currentViewerPane.setFocus();
		}
		else {
			getControl(getActivePage()).setFocus();
		}
	}

	/**
	 * This implements {@link org.eclipse.jface.viewers.ISelectionProvider}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		selectionChangedListeners.add(listener);
	}

	/**
	 * This implements {@link org.eclipse.jface.viewers.ISelectionProvider}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		selectionChangedListeners.remove(listener);
	}

	/**
	 * This implements {@link org.eclipse.jface.viewers.ISelectionProvider} to return this editor's overall selection.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ISelection getSelection() {
		return editorSelection;
	}

	/**
	 * This implements {@link org.eclipse.jface.viewers.ISelectionProvider} to set this editor's overall selection.
	 * Calling this result will notify the listeners.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setSelection(ISelection selection)
	{
		editorSelection = selection;

		// notify listeners
		for(ISelectionChangedListener listener : selectionChangedListeners)
		{
			listener.selectionChanged(new SelectionChangedEvent(this, selection));
		}

		setStatusLineManager(selection);
	}

	/**
	 * Updates the status line manager's message according to the selection.
	 * <!-- begin-user-doc -->
	 * @param selection the selection
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setStatusLineManager(ISelection selection)
	{
		IStatusLineManager statusLineManager = 
				currentViewer != null && contentOutlinePage != null && currentViewer == contentOutlinePage.getViewer() ?
				contentOutlinePage.getStatusLineManeger() : getActionBars().getStatusLineManager();

		if(statusLineManager != null)
		{
			if(selection instanceof IStructuredSelection)
			{
				Collection<?> collection = ((IStructuredSelection)selection).toList();
				switch(collection.size())
				{
					case 0:
					{
						statusLineManager.setMessage(ConfigurationEditorPlugin.getSubstitutedString("_UI_NoObjectSelected")); //$NON-NLS-1$
						break;
					}
					case 1:
					{
						String text = new AdapterFactoryItemDelegator(adapterFactoryConfiguration).getText(collection.iterator().next());
						statusLineManager.setMessage(ConfigurationEditorPlugin.getSubstitutedString("_UI_SingleObjectSelected", text)); //$NON-NLS-1$
						break;
					}
					default:
					{
						statusLineManager.setMessage(ConfigurationEditorPlugin.getSubstitutedString("_UI_MultiObjectSelected", //$NON-NLS-1$
								Integer.toString(collection.size())));
						break;
					}
				}
			}
			else
			{
				statusLineManager.setMessage(""); //$NON-NLS-1$
			}
		}
	}

	/**
	 * This implements {@link org.eclipse.jface.action.IMenuListener} to help fill the context menus with contributions from the Edit menu.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void menuAboutToShow(IMenuManager menuManager) {
		((IMenuListener)getEditorSite().getActionBarContributor()).menuAboutToShow(menuManager);
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return the action bar contributor of the editor site
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EditingDomainActionBarContributor getActionBarContributor() {
		return (EditingDomainActionBarContributor)getEditorSite().getActionBarContributor();
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return the action bars of the current action bar contributor
	 * @see #getActionBarContributor() getActionBarContributor()
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IActionBars getActionBars() {
		return getActionBarContributor().getActionBars();
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return the adapter factory used for the configuration model
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AdapterFactory getAdapterFactory() {
		return adapterFactoryConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public void dispose() {
		updateProblemIndication = false;

		ResourcesPlugin.getWorkspace().removeResourceChangeListener(resourceChangeListener);
		ConfigurationEditorPlugin.getPlugin().getPreferenceStore().removePropertyChangeListener(propertyChangeListener);

		getSite().getPage().removePartListener(partListener);

		adapterFactoryConfiguration.dispose();
		adapterFactoryImports.dispose();

		if (getActionBarContributor().getActiveEditor() == this) {
			getActionBarContributor().setActiveEditor(null);
		}

		for (IPropertySheetPage propertySheetPage : propertySheetPages) {
			propertySheetPage.dispose();
		}

		if (contentOutlinePage != null) {
			contentOutlinePage.dispose();
		}

		super.dispose();
	}

	/**
	 * Returns whether the outline view should be presented to the user.
	 * <!-- begin-user-doc -->
	 * @return always true
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected boolean showOutlineView() {
		return true;
	}
	
	/**
	 * Returns whether the configuration tab is active.
	 * @return true if the configuration tab is active, false otherwise
	 */
	public boolean isConfigurationTabActive()
	{
		return currentViewerPane != null && currentViewerPane.getViewer() == configurationViewer;
	}
	
	/**
	 * Returns whether the problems tab is active.
	 * @return true if the problems tab is active, false otherwise.
	 */
	public boolean isProblemsTabActive()
	{
		return getEditor(getActivePage()) instanceof ProblemEditorPart;
	}

	/**
	 * Returns the {@link SlicingLogicDelegate} of the current viewer pane,
	 * or <code>null</code> if no delegate exists for the current viewer pane.
	 * @return slicing logic delegate for current viewer pane, or <code>null</code> if none
	 */
	protected SlicingLogicDelegate getCurrentSlicingLogicDelegate()
	{
		if(currentViewerPane == null)
			return null;
		return modelViewersToDelegatesMap.get(currentViewerPane.getViewer());
	}

	@Override
	public SlicingConfiguration getConfig()
	{
		return slicingConfig;
	}
	
	@Override
	public void executeCommand(Command cmd)
	{
		// prevent listener from being called for next command
		commandStackListenerGuard = true;
		
		// add command to stack and execute it
		editingDomain.getCommandStack().execute(cmd);
	}

	/**
	 * Returns the alternative elements for the given selected object. The function
	 * delegates the call to the {@link SlicingLogicDelegate} of the current viewer.
	 * @param selectedObject the currently selected object
	 * @return set of all objects that are alternatives for the selected one
	 */
	public Set<EObject> getAlternativeElements(EObject selectedObject)
	{
		final SlicingLogicDelegate slicingLogicDelegate = getCurrentSlicingLogicDelegate();
		return slicingLogicDelegate != null ? slicingLogicDelegate.getAlternativeElements(selectedObject) : Collections.emptySet();
	}

	/**
	 * Returns whether the given object, which is one of the elements return by
	 * {@link #getAlternativeElements(EObject)}, is checked. The function
	 * delegates the call to the {@link SlicingLogicDelegate} of the current viewer.
	 * @param object the object
	 * @return <code>true</code> if the object is checked, <code>false</code> otherwise
	 */
	public boolean isAlternativeChecked(EObject object)
	{
		final SlicingLogicDelegate slicingLogicDelegate = getCurrentSlicingLogicDelegate();
		return slicingLogicDelegate != null && slicingLogicDelegate.isChecked(object);
	}

	/**
	 * Delegates the given {@link CheckStateChangedEvent}, that originates from the
	 * alternative elements view, to the {@link SlicingLogicDelegate}
	 * of the current viewer.
	 * @param event the event
	 */
	public void onAlternativeChecked(CheckStateChangedEvent event)
	{
		final SlicingLogicDelegate slicingLogicDelegate = getCurrentSlicingLogicDelegate();
		if(slicingLogicDelegate != null)
		{
			slicingLogicDelegate.checkStateChanged(event);
		}
	}

	/**
	 * Refreshes the {@link AlternativeElementsView} if it exists.
	 */
	protected void refreshAlternativeElementsView()
	{
		AlternativeElementsView altElementsView = (AlternativeElementsView)getSite().getPage().findView(AlternativeElementsView.ID);
		if(altElementsView != null)
		{
			altElementsView.refresh();
		}
	}

	@Override
	public void showAlternativeElements(EObject object)
	{
		try
		{
			// the view is only made visible but not given focus,
			// because the view will not update if the editor does not have focus
			AlternativeElementsView altElementsView = (AlternativeElementsView)getSite().getPage()
					.showView(AlternativeElementsView.ID, null, IWorkbenchPage.VIEW_VISIBLE);
			if(altElementsView != null)
			{
				altElementsView.setSelectedObject(this, object); // select element in the alternative elements view
				setSelectionToViewer(Collections.singleton(object)); // select element in the editor
			}
		}
		catch(Exception e)
		{
			ConfigurationEditorPlugin.INSTANCE.log(e);
		}
	}

	/**
	 * This class implements
	 * <ul>
	 * <li>{@link AdapterFactoryLabelProvider.StyledLabelProvider}</li>
	 * <li>{@link ICheckStateProvider}</li>
	 * <li>{@link ICheckStateListener}</li>
	 * <li>{@link ILabelDecorator}</li>
	 * <li>{@link IToolTipProvider}</li>
	 * </ul>
	 * and delegates the implemented functions to the slicing logic.<br>
	 * The class also implements {@link IDisposable} to dispose all images that were created by the delegate.<br>
	 * Each delegate creates its own slicing logic.
	 * @author rmueller
	 *
	 */
	protected class SlicingLogicDelegate extends AdapterFactoryLabelProvider.StyledLabelProvider
										implements ICheckStateProvider, ICheckStateListener, ILabelDecorator, IDisposable, IToolTipProvider
	{		
		/**
		 * The slicing logic
		 */
		protected ISlicingLogic slicingLogic;
		
		/**
		 * The model viewer
		 */
		protected CheckboxTreeViewer viewer;

		/**
		 * Collection of images that need to be disposed when the viewer that uses this SlicingLogicDelegate is disposed
		 */
		protected Collection<Image> imagesToDispose;

		/**
		 * Flag for {@link #dispose()} to only dispose the object once
		 */
		private boolean disposed;

		/**
		 * Create a new SlicingLogicDelegate. The delegate creates a new {@link SlicingLogic}.
		 * @param adapterFactory adapter factory for model elements
		 * @param viewer model viewer
		 * @param modelResource resource of the model
		 */
		public SlicingLogicDelegate(AdapterFactory adapterFactory, CheckboxTreeViewer viewer, Resource modelResource)
		{
			super(adapterFactory, viewer);
			Assert.isNotNull(adapterFactory);
			Assert.isNotNull(viewer);
			Assert.isNotNull(modelResource);
			this.viewer = viewer;
			this.imagesToDispose = new LinkedList<Image>();
			this.disposed = false;
			
			// create slicing logic
			this.slicingLogic = new SlicingLogic(ConfigurationEditor.this, modelResource);
		}

		@Override
		public StyledString getStyledText(Object object)
		{
			StyledString original = super.getStyledText(object);
			StyledString styled = slicingLogic.getDecoratedText(original, object);
			return styled != null ? styled : original;
		}

		@Override
		public Color getForeground(Object object)
		{
			return slicingLogic.getForegroundColor(object);
		}

		@Override
		public Color getBackground(Object object)
		{
			return slicingLogic.getBackgroundColor(object);
		}

		@Override
		public Font getFont(Object object)
		{
			int fontStyle = slicingLogic.getFontStyle(object);

			// create new font if style is not normal
			if(fontStyle != SWT.NORMAL)
			{
				// copy the font descriptor of the default font
				FontDescriptor fontDescriptor = FontDescriptor.createFrom(viewer.getControl().getFont());

				// set style and create font from font descriptor
				return fontDescriptor.setStyle(fontStyle).createFont(Display.getCurrent());
			}

			// default font
			return super.getFont(object);
		}

		@Override
		public void checkStateChanged(CheckStateChangedEvent event)
		{
			Object elem = event.getElement();
			if(elem instanceof EObject)
			{
				Set<EObject> changedElements = slicingLogic.checkStateChanged((EObject)elem, event.getChecked());

				// update layout
				if(changedElements == null)
				{
					viewer.refresh();
				}
				else if(!changedElements.isEmpty())
				{
					viewer.update(changedElements.toArray(), null);
				}

				//check selection and imports
				analyzeImportedResources();

				// update the tabs in the tabbed property view
				if(getSelection() instanceof IStructuredSelection)
				{
					IStructuredSelection selection = (IStructuredSelection)getSelection();
					Object element = selection.getFirstElement();

					// only update the tabs if the selected element's checked state changed
					if(element != null && element.equals(elem))
					{
						// update all property sheet pages
						for(IPropertySheetPage propertySheetPage : propertySheetPages)
						{
							if(!propertySheetPage.getControl().isDisposed())
							{
								// selectionChanged does nothing if the new selection equals the old one
								// therefore the selection is changed to an empty selection first
								// before being changed back to the original one
								propertySheetPage.selectionChanged(ConfigurationEditor.this, StructuredSelection.EMPTY);
								propertySheetPage.selectionChanged(ConfigurationEditor.this, selection);
							}
						}
					}
				}

				refreshAlternativeElementsView();
			}

			updateProblemIndication(false);
		}

		@Override
		public boolean isGrayed(Object element)
		{
			return slicingLogic.getCheckboxState(element) == CheckboxState.GRAYED;
		}

		@Override
		public boolean isChecked(Object element)
		{
			return slicingLogic.getCheckboxState(element) != CheckboxState.NOT_CHECKED;
		}

		@Override
		public Image decorateImage(Image image, Object element)
		{
			// get descriptor of decorated image from slicing logic
			final ImageDescriptor desc = slicingLogic.getDecoratedImage(image, element);
			if(desc == null)
				return null;

			// create image from image descriptor and add it to the image list
			// so it can be disposed when the viewer is disposed
			final Image i = desc.createImage();
			imagesToDispose.add(i);
			return i;
		}

		@Override
		public String decorateText(String text, Object element)
		{
			// no text decoration; text is decorated with getStyledText
			return null;
		}

		public Set<EObject> getAlternativeElements(EObject element)
		{		
			final Set<EObject> elements = slicingLogic.getAlternativeElements(element);
			return elements == null ? Collections.emptySet() : elements;
		}

		/**
		 * Disposes of this delegate. Disposes all images that were created by {@link #decorateImage(Image, Object)}.
		 * Disposes the slicing logic. Calls through to super.dispose() to dispose of the {@link AdapterFactoryLabelProvider}.
		 */
		@Override
		public void dispose()
		{
			// check if already disposed
			if(disposed)
				return;
			disposed = true;
			
			// dispose all images that were created by this delegate
			for(Image i : imagesToDispose)
			{
				i.dispose();
			}
			imagesToDispose.clear();
			
			// dispose the slicing logic
			slicingLogic.dispose();
			
			// call through
			super.dispose();
		}

		@Override
		public String getToolTipText(Object element)
		{
			return slicingLogic.getToolTipText(element);
		}
	}
	
	/**
	 * The configuration editor's outline page, which is basically a tree. The outline also
	 * shows the elements that are hidden in the model viewers.
	 * @author rmueller
	 *
	 */
	protected class ConfigurationEditorContentOutlinePage extends ContentOutlinePage
	{
		/**
		 * The content outline page's status line manager
		 */
		protected IStatusLineManager statusLineManager;
		
		/**
		 * This is the content outline page's viewer.
		 */
		protected TreeViewer viewer;
		
		@Override
		public void createControl(Composite parent)
		{
			super.createControl(parent);
			viewer = getTreeViewer();
			viewer.addSelectionChangedListener(this);

			// Set up the tree viewer.
			//
			viewer.setContentProvider(new AdapterFactoryContentProvider(adapterFactoryConfiguration));
			viewer.setLabelProvider(new AdapterFactoryLabelProvider(adapterFactoryConfiguration));
			updateInputAndSelection();

			// Make sure our popups work.
			//
			createContextMenuFor(viewer);
		}

		/**
		 * Sets the content outline viewer's input and selection to those of the current viewer pane.
		 */
		public void updateInputAndSelection()
		{
			if(isProblemsTabActive())
			{
				viewer.setInput(null);
				viewer.setSelection(StructuredSelection.EMPTY);
			}
			else if(currentViewerPane != null)
			{
				viewer.setInput(currentViewerPane.getViewer().getInput());
				viewer.setSelection(currentViewerPane.getViewer().getSelection(), true);
			}
			else
			{
				// this is only the initial input when currentViewerPane is not yet set
				if(!editingDomain.getResourceSet().getResources().isEmpty())
				{
					Resource configResource = editingDomain.getResourceSet().getResources().get(0);
					viewer.setInput(configResource);
					viewer.setSelection(new StructuredSelection(configResource), true);
				}
			}
		}

		@Override
		public void makeContributions(IMenuManager menuManager, IToolBarManager toolBarManager, IStatusLineManager statusLineManager)
		{
			super.makeContributions(menuManager, toolBarManager, statusLineManager);
			this.statusLineManager = statusLineManager;
		}

		@Override
		public void setActionBars(IActionBars actionBars)
		{
			super.setActionBars(actionBars);
			getActionBarContributor().shareGlobalActions(this, actionBars);
		}
		
		public IStatusLineManager getStatusLineManeger()
		{
			return statusLineManager;
		}
		
		public TreeViewer getViewer()
		{
			return viewer;
		}
	}
}
