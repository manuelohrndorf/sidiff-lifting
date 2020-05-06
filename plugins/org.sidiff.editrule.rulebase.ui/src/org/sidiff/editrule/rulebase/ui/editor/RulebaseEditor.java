package org.sidiff.editrule.rulebase.ui.editor;

import java.util.Collection;
import java.util.EventObject;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.ui.viewer.IViewerProvider;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.action.EditingDomainActionBarContributor;
import org.eclipse.emf.edit.ui.dnd.EditingDomainViewerDropAdapter;
import org.eclipse.emf.edit.ui.dnd.LocalTransfer;
import org.eclipse.emf.edit.ui.dnd.ViewerDragAdapter;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.edit.ui.provider.UnwrappingSelectionProvider;
import org.eclipse.emf.edit.ui.util.EditUIUtil;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.ui.views.contentoutline.ContentOutline;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheet;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.editrule.rulebase.RuleBase;
import org.sidiff.editrule.rulebase.RuleBaseItem;
import org.sidiff.editrule.rulebase.builder.EditRuleBaseBuilder;
import org.sidiff.editrule.rulebase.builder.EditRuleBaseWrapper;
import org.sidiff.editrule.rulebase.provider.RulebaseItemProviderAdapterFactory;
import org.sidiff.editrule.rulebase.ui.editor.columns.RuleBaseColumnLibrary;
import org.sidiff.editrule.rulebase.ui.internal.EditruleRulebaseUiPlugin;
import org.sidiff.editrule.rulebase.util.EditRuleItemUtil;

public class RulebaseEditor extends EditorPart implements IEditingDomainProvider, ISelectionProvider, IMenuListener, IViewerProvider {

	/**
	 * The ID of the RulebaseEditor,
	 * and the contributor ID of the associated tabbed properties view.
	 */
	public static final String ID = "org.sidiff.editrule.rulebase.ui.rulebaseEditor";

	private Viewer currentViewer;
	private ListViewer docTypViewer;
	private TableViewer ruleViewer;
	private TableColumnLayout tableColumnLayout;

	protected TabbedPropertySheetPage propertySheetPage;

	protected RulebaseContentOutlinePage contentOutlinePage;
	protected IStatusLineManager contentOutlineStatusLineManager;
	protected TreeViewer contentOutlineViewer;

	private ISelectionChangedListener selectionChangedListener = null;
	private final ListenerList<ISelectionChangedListener> selectionChangedListeners = new ListenerList<>();
	private ISelection editorSelection = StructuredSelection.EMPTY;

	protected AdapterFactoryEditingDomain editingDomain;
	protected ComposedAdapterFactory adapterFactory;

	private boolean sortedAscending = true;
	private boolean dirty = false;
	
	private IResourceChangeListener changeListener;

	/**
	 * Rule base facade
	 */
	private EditRuleBaseWrapper rbManager;

	protected IPartListener partListener = new IPartListener() {
		@Override
		public void partActivated(IWorkbenchPart p) {
			if (p instanceof ContentOutline) {
				if (((ContentOutline) p).getCurrentPage() == contentOutlinePage) {
					getActionBarContributor().setActiveEditor(RulebaseEditor.this);
					setCurrentViewer(contentOutlineViewer);
				}
			} else if (p instanceof PropertySheet) {
				if (((PropertySheet) p).getCurrentPage() == propertySheetPage) {
					getActionBarContributor().setActiveEditor(RulebaseEditor.this);
					handleActivate();
				}
			} else if (p == RulebaseEditor.this) {
				handleActivate();
			}
		}

		@Override
		public void partBroughtToTop(IWorkbenchPart p) {
			// Ignore.
		}

		@Override
		public void partClosed(IWorkbenchPart p) {
			// Ignore.
		}

		@Override
		public void partDeactivated(IWorkbenchPart p) {
			// Ignore.
		}

		@Override
		public void partOpened(IWorkbenchPart p) {
			// Ignore.
		}
	};

	public RulebaseEditor() {
		super();
		initializeEditingDomain();
	}

	/**
	 * Handles activation of the editor or it's associated views.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected void handleActivate() {
		setCurrentViewer(ruleViewer);

		// Recompute the read only state.
		//
		if (editingDomain.getResourceToReadOnlyMap() != null) {
			editingDomain.getResourceToReadOnlyMap().clear();

			// Refresh any actions that may become enabled or disabled.
			//
			setSelection(getSelection());
		}
	}

	@Override
	public void createPartControl(Composite parent) {
		/*
		 * Parent composite of all viewers:
		 */

		final Color COLOR_WHITE = Display.getDefault().getSystemColor(SWT.COLOR_WHITE);

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setBackground(COLOR_WHITE);
		composite.setLayout(new GridLayout());
		
		/*
		 * Document type:
		 */

		Composite generalComposite = new Composite(composite, SWT.NONE);
		generalComposite.setBackground(COLOR_WHITE);
		generalComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		GridLayout glGeneralComposite = new GridLayout(2,false);
		glGeneralComposite.horizontalSpacing = 20;
		generalComposite.setLayout(glGeneralComposite);

		Label generalPropertiesLabel = new Label(generalComposite, SWT.NONE);
		generalPropertiesLabel.setBackground(COLOR_WHITE);
		generalPropertiesLabel.setFont(new Font(generalComposite.getDisplay(), "Arial", 12, SWT.BOLD));
		generalPropertiesLabel.setText("General Properties");
		GridData generalPropertiesData = new GridData();
		generalPropertiesData.horizontalSpan = 2;
		generalPropertiesLabel.setLayoutData(generalPropertiesData);

		Font labelFont = new Font(generalComposite.getDisplay(), "Arial", 10, SWT.BOLD);
		Label docTypLabel = new Label(generalComposite, SWT.NONE);
		docTypLabel.setBackground(COLOR_WHITE);
		docTypLabel.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		docTypLabel.setFont(labelFont);
		docTypLabel.setText("Document Types:");

		docTypViewer = new ListViewer(generalComposite);
		org.eclipse.swt.widgets.List list = docTypViewer.getList();
		GridData gd_list = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_list.heightHint = 25;
		list.setLayoutData(gd_list);
		docTypViewer.setContentProvider(ArrayContentProvider.getInstance());
		docTypViewer.setInput(rbManager.getRuleBase().getDocumentTypes());

		/*
		 * JFace Table Viewer
		 */

		Composite tableComposite = new Composite(composite, SWT.NONE);
		tableColumnLayout = new TableColumnLayout();
		tableComposite.setLayout(tableColumnLayout);
		tableComposite.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));

		ruleViewer = new TableViewer(tableComposite, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		ruleViewer.setComparator(new ViewerComparator() {
			@Override
			public int compare(Viewer viewer, Object e1, Object e2) {
				if((e1 instanceof RuleBaseItem) && (e2 instanceof RuleBaseItem)){
					RuleBaseItem item1 = (RuleBaseItem) e1;
					RuleBaseItem item2 = (RuleBaseItem) e2;
					int directionMult = ((TableViewer)viewer).getTable().getSortDirection() == SWT.UP ? 1 : -1;
					return EditRuleItemUtil.getName(item1).compareTo(EditRuleItemUtil.getName(item2)) * directionMult;
				}
				return super.compare(viewer, e1, e2);
			}
		});
		ruleViewer.setContentProvider(new AdapterFactoryContentProvider(adapterFactory) {
			@Override
			public Object[] getElements(Object object) {
				if(object instanceof RuleBase) {
					return Stream.of(super.getElements(object))
						.filter(RuleBaseItem.class::isInstance)
						.toArray();
				}
				return new Object[0];
			}
		});

		// SWT Table
		final Table table = ruleViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));

		/*
		 * Columns
		 */

		RuleBaseColumnLibrary.createAllColumns(this);

		/*
		 * Table context menu
		 */

		Menu menu = new Menu(parent);
		table.setMenu(menu);

		MenuItem itemDelete = new MenuItem(menu, SWT.PUSH);
		itemDelete.setText("Delete Rule(s)");
		itemDelete.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				getSelectedRuleBaseItems().forEach(rbManager::removeItem);
				table.remove(table.getSelectionIndices());
				setDirty();
			}
		});

		MenuItem itemFormat = new MenuItem(menu, SWT.PUSH);
		itemFormat.setText("Format Rule Name(s)");
		itemFormat.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				for(RuleBaseItem item : getSelectedRuleBaseItems()) {
					EditRuleItemUtil.setName(item, EditRuleItemUtil.formatName(item));
					ruleViewer.update(item, null);
					setDirty();					
				}
			}
		});

		/*
		 * Set table input
		 */

		ruleViewer.setInput(rbManager.getRuleBase());
		setCurrentViewer(ruleViewer);
	}

	protected void initializeEditingDomain() {
		// Create an adapter factory that yields item providers.
		//
		adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);

		adapterFactory.addAdapterFactory(new ResourceItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new RulebaseItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new EcoreItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());

		// Create the command stack that will notify this editor as commands are executed.
		//
		BasicCommandStack commandStack = new BasicCommandStack();

		// Add a listener to set the most recent command's affected objects
		// to be the selection of the viewer with focus.
		//
		commandStack.addCommandStackListener(new CommandStackListener() {
			@Override
			public void commandStackChanged(final EventObject event) {
				Display.getDefault().asyncExec(() -> {
					if (!dirty) {
						dirty = true;
						firePropertyChange(IEditorPart.PROP_DIRTY);
					}

					// Try to select the affected objects.
					//
					Command mostRecentCommand = ((CommandStack) event.getSource()).getMostRecentCommand();
					if (mostRecentCommand != null) {
						setSelectionToViewer(mostRecentCommand.getAffectedObjects());
					}
					if (propertySheetPage != null && !propertySheetPage.getControl().isDisposed()) {
						propertySheetPage.refresh();
					}
					update();
				});
			}
		});

		// Create the editing domain with a special command stack.
		//
		editingDomain = new AdapterFactoryEditingDomain(adapterFactory, commandStack, new HashMap<Resource, Boolean>());
	}

	@Override
	public <T> T getAdapter(Class<T> key) {
		if (key.equals(IContentOutlinePage.class)) {
			return showOutlineView() ? key.cast(getContentOutlinePage()) : null;
		} else if (key.equals(IPropertySheetPage.class)) {
			return key.cast(getPropertySheetPage());
		} else if (key.equals(IGotoMarker.class)) {
			return key.cast(this);
		} else {
			return super.getAdapter(key);
		}
	}

	protected boolean showOutlineView() {
		return true;
	}

	public IContentOutlinePage getContentOutlinePage() {
		if (contentOutlinePage == null) {
			contentOutlinePage = new RulebaseContentOutlinePage();

			// Listen to selection so that we can handle it is a special way.
			//
			contentOutlinePage.addSelectionChangedListener(new ISelectionChangedListener() {
				// This ensures that we handle selections correctly.
				//
				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					handleContentOutlineSelection(event.getSelection());
				}
			});
		}
		return contentOutlinePage;
	}

	public void handleContentOutlineSelection(ISelection selection) {
		if (ruleViewer != null && !selection.isEmpty() && selection instanceof IStructuredSelection) {
			// propagate the outline selection to the current viewer pane
			ruleViewer.setSelection(selection, true);

			// update all property sheet pages to properly show the constraint tab
			if (propertySheetPage != null && !propertySheetPage.getControl().isDisposed()) {
				propertySheetPage.selectionChanged(this, selection);
			}
		}
	}

	public IPropertySheetPage getPropertySheetPage() {
		if (propertySheetPage == null) {
			propertySheetPage = new TabbedPropertySheetPage(getSite()::getId);
		}
		return propertySheetPage;
	}

	protected void createContextMenuFor(StructuredViewer viewer) {
		MenuManager contextMenu = new MenuManager("#PopUp");
		contextMenu.add(new Separator("additions"));
		contextMenu.setRemoveAllWhenShown(true);
		contextMenu.addMenuListener(this);
		Menu menu = contextMenu.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(contextMenu, new UnwrappingSelectionProvider(viewer));

		// drag & drop support
		int dndOperations = DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_LINK;
		Transfer[] transfers = new Transfer[] { LocalTransfer.getInstance() };
		viewer.addDragSupport(dndOperations, transfers, new ViewerDragAdapter(viewer));
		viewer.addDropSupport(dndOperations, transfers, new EditingDomainViewerDropAdapter(editingDomain, viewer));
	}

	public void setSelectionToViewer(Collection<?> collection) {
		// Make sure it's okay.
		//
		if (collection != null && !collection.isEmpty()) {
			getSite().getShell().getDisplay().asyncExec(() -> {
				// Try to select the items in the current content viewer of
				// the editor.
				//
				if (ruleViewer != null) {
					ruleViewer.setSelection(new StructuredSelection(collection.toArray()), true);
				}
			});
		}
	}

	public void setStatusLineManager(ISelection selection) {
		IStatusLineManager statusLineManager = ruleViewer != null && currentViewer == contentOutlineViewer
				? contentOutlineStatusLineManager : getActionBars().getStatusLineManager();

		if (statusLineManager != null) {
			if (selection instanceof IStructuredSelection) {
				Collection<?> collection = ((IStructuredSelection) selection).toList();
				switch (collection.size()) {
				case 0: {
					statusLineManager.setMessage("No Object selected");
					break;
				}
				case 1: {
					String text = new AdapterFactoryItemDelegator(adapterFactory).getText(collection.iterator().next());
					statusLineManager.setMessage("Selected Object: " + text);
					break;
				}
				default: {
					statusLineManager.setMessage("Selected Objects: " + Integer.toString(collection.size()));
					break;
				}
				}
			} else {
				statusLineManager.setMessage("");
			}
		}
	}

	public void setCurrentViewer(Viewer viewer) {
		// If it is changing...
		//
		if (currentViewer != viewer) {
			if (selectionChangedListener == null) {
				// Create the listener on demand.
				//
				selectionChangedListener = new ISelectionChangedListener() {
					// This just notifies those things that are affected by the section.
					//
					@Override
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
		}
	}

	@Override
	public void dispose() {
		getSite().getPage().removePartListener(partListener);

		adapterFactory.dispose();

		if (getActionBarContributor().getActiveEditor() == this) {
			getActionBarContributor().setActiveEditor(null);
		}

		if (propertySheetPage != null) {
			propertySheetPage.dispose();
		}

		if (contentOutlinePage != null) {
			contentOutlinePage.dispose();
		}
		
		if (changeListener != null) {
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			workspace.removeResourceChangeListener(changeListener);
		}

		super.dispose();
	}

	@Override
	public void setFocus() {
		if(ruleViewer != null) {
			ruleViewer.getTable().setFocus();			
		}
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		rbManager.saveRuleBase();

		setClean();

		try {
			((IFileEditorInput) getEditorInput()).getFile().getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		} catch (CoreException e) {
			StatusManager.getManager().handle(e, EditruleRulebaseUiPlugin.PLUGIN_ID);
		}
	}

	@Override
	public void doSaveAs() {
		// Not allowed
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public boolean isDirty() {
		return dirty;
	}

	public void setDirty() {
		this.dirty = true;
		firePropertyChange(PROP_DIRTY);
	}

	public void setClean() {
		dirty = false;
		firePropertyChange(IEditorPart.PROP_DIRTY);
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInputWithNotify(input);
		setPartName(input.getName());

		site.setSelectionProvider(this);
		site.getPage().addPartListener(partListener);

		// Get workspace URI:
		final URI resourceURI = EditUIUtil.getURI(getEditorInput());
		IProject project = EMFStorage.toIFile(resourceURI).getProject();
		
		// Rulebase wrapper:
		rbManager = new EditRuleBaseWrapper(EditRuleBaseBuilder.createResourceSet(), project, resourceURI, false);

		// Update editor:
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		
		changeListener = new IResourceChangeListener() {
			@Override
			public void resourceChanged(IResourceChangeEvent event) {
				int needsUpdate = needsUpdate(event, resourceURI);
				
				// Reload if rulebase file has changed:
				if (needsUpdate == IResourceDelta.ADDED || needsUpdate == IResourceDelta.CHANGED) {
					Display.getDefault().asyncExec(() -> {
						rbManager = new EditRuleBaseWrapper(EditRuleBaseBuilder.createResourceSet(), project, resourceURI, false);
						ruleViewer.setInput(rbManager.getItems());
						update();
					});	
				} 
				
				// Clear view:
				else if (needsUpdate == IResourceDelta.REMOVED) {
					Display.getDefault().asyncExec(() -> {
						ruleViewer.setInput(null);
						update();
					});
				}
			}
		};
		workspace.addResourceChangeListener(changeListener);
	}

	private static int needsUpdate(IResourceChangeEvent event, URI resourceURI) {
		// Workspace -> Plugin..
		for (IResourceDelta resourceDelta : event.getDelta().getAffectedChildren()) {
			
			// Plugin -> Files/Folders...
			for (IResourceDelta subResourceDelta : resourceDelta.getAffectedChildren()) {
				
				// Rulebase file?
				if (resourceURI.toString().contains(subResourceDelta.getFullPath().toString())) {
					return subResourceDelta.getKind();
				}
			}
		}
		return IResourceDelta.NO_CHANGE;
	}

	@Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		selectionChangedListeners.add(listener);
	}

	@Override
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		selectionChangedListeners.remove(listener);
	}

	@Override
	public ISelection getSelection() {
		return editorSelection;
	}

	@Override
	public void setSelection(ISelection selection) {
		editorSelection = selection;
		for (ISelectionChangedListener listener : selectionChangedListeners) {
			listener.selectionChanged(new SelectionChangedEvent(this, selection));
		}
		setStatusLineManager(selection);
		if (propertySheetPage != null && !propertySheetPage.getControl().isDisposed()) {
			propertySheetPage.selectionChanged(this, selection);
		}
	}

	@Override
	public void menuAboutToShow(IMenuManager manager) {
		((IMenuListener) getEditorSite().getActionBarContributor()).menuAboutToShow(manager);
	}

	public EditingDomainActionBarContributor getActionBarContributor() {
		return (EditingDomainActionBarContributor) getEditorSite().getActionBarContributor();
	}

	public IActionBars getActionBars() {
		return getActionBarContributor().getActionBars();
	}

	@Override
	public Viewer getViewer() {
		return ruleViewer;
	}

	@Override
	public EditingDomain getEditingDomain() {
		return editingDomain;
	}

	public EditRuleBaseWrapper getRulebaseWrapper() {
		return rbManager;
	}

	public void update() {
		ruleViewer.refresh();
	}

	/**
	 * Returns the currently selected RuleBaseItems
	 * 
	 * @return
	 */
	public Collection<RuleBaseItem> getSelectedRuleBaseItems() {
		return Stream.of(ruleViewer.getStructuredSelection().toArray())
				.filter(RuleBaseItem.class::isInstance)
				.map(RuleBaseItem.class::cast)
				.collect(Collectors.toList());
	}

	public TableViewer getRuleViewer() {
		return ruleViewer;
	}

	public TableColumnLayout getTableColumnLayout() {
		return tableColumnLayout;
	}

	public boolean isSortedAscending() {
		return sortedAscending;
	}

	public void invertSortedAscending() {
		this.sortedAscending = !sortedAscending;
	}

	public AdapterFactory getAdapterFactory() {
		return adapterFactory;
	}

	protected class RulebaseContentOutlinePage extends ContentOutlinePage {
		@Override
		public void createControl(Composite parent) {
			super.createControl(parent);
			contentOutlineViewer = getTreeViewer();
			contentOutlineViewer.addSelectionChangedListener(this);

			// Set up the tree viewer.
			//
			contentOutlineViewer.setContentProvider(new AdapterFactoryContentProvider(adapterFactory));
			contentOutlineViewer.setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory));
			updateInputAndSelection();

			// Make sure our popups work.
			//
			createContextMenuFor(contentOutlineViewer);
		}

		/**
		 * Sets the content outline viewer's input and selection to those of the current viewer pane.
		 */
		public void updateInputAndSelection() {
			if (ruleViewer != null) {
				contentOutlineViewer.setInput(ruleViewer.getInput());
				contentOutlineViewer.setSelection(ruleViewer.getSelection(), true);
			} else {
				// this is only the initial input when currentViewerPane is not yet set
				if (!editingDomain.getResourceSet().getResources().isEmpty()) {
					Resource configResource = editingDomain.getResourceSet().getResources().get(0);
					contentOutlineViewer.setInput(configResource);
					contentOutlineViewer.setSelection(new StructuredSelection(configResource), true);
				}
			}
		}

		@Override
		public void makeContributions(IMenuManager menuManager, IToolBarManager toolBarManager, IStatusLineManager statusLineManager) {
			super.makeContributions(menuManager, toolBarManager, statusLineManager);
			contentOutlineStatusLineManager = statusLineManager;
		}

		@Override
		public void setActionBars(IActionBars actionBars) {
			super.setActionBars(actionBars);
			getActionBarContributor().shareGlobalActions(this, actionBars);
		}
	}
}
