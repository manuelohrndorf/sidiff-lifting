package org.sidiff.difference.rulebase.ui.editor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.command.CommandStackListener;
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
import org.eclipse.emf.edit.ui.view.ExtendedPropertySheetPage;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.resource.ImageDescriptor;
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
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
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
import org.eclipse.ui.views.contentoutline.ContentOutline;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheet;
import org.eclipse.ui.views.properties.PropertySheetPage;
import org.sidiff.difference.rulebase.RuleBaseItem;
import org.sidiff.difference.rulebase.provider.RulebaseItemProviderAdapterFactory;
import org.sidiff.difference.rulebase.ui.Activator;
import org.sidiff.difference.rulebase.ui.editor.columns.ColumnACCount;
import org.sidiff.difference.rulebase.ui.editor.columns.ColumnActive;
import org.sidiff.difference.rulebase.ui.editor.columns.ColumnDependenyCount;
import org.sidiff.difference.rulebase.ui.editor.columns.ColumnDescription;
import org.sidiff.difference.rulebase.ui.editor.columns.ColumnEditType;
import org.sidiff.difference.rulebase.ui.editor.columns.ColumnParameterCount;
import org.sidiff.difference.rulebase.ui.editor.columns.ColumnPriority;
import org.sidiff.difference.rulebase.ui.editor.columns.ColumnRecognitionType;
import org.sidiff.difference.rulebase.ui.editor.columns.ColumnRefinementLevel;
import org.sidiff.difference.rulebase.ui.editor.columns.ColumnRulebaseItem;
import org.sidiff.difference.rulebase.wrapper.RuleBaseItemWrapper;
import org.sidiff.difference.rulebase.wrapper.RuleBaseWrapper;
import org.silift.common.util.emf.EMFStorage;

public class RulebaseEditor

extends EditorPart implements IEditingDomainProvider, ISelectionProvider, IMenuListener, IViewerProvider {

	public static final String ID = "org.sidiff.difference.rulebase.ui.rulebaseEditor";

	private Composite parent = null;
	
	private Viewer currentViewer = null;
	private ListViewer docTypViewer = null;
	private TableViewer ruleViewer = null;
	private TableColumnLayout tableColumnLayout;

	protected PropertySheetPage propertySheetPage;

	protected IContentOutlinePage contentOutlinePage;
	protected IStatusLineManager contentOutlineStatusLineManager;
	protected TreeViewer contentOutlineViewer;

	private ISelectionChangedListener selectionChangedListener = null;
	private Collection<ISelectionChangedListener> selectionChangedListeners = null;
	private ISelection editorSelection = StructuredSelection.EMPTY;

	protected AdapterFactoryEditingDomain editingDomain;
	protected ComposedAdapterFactory adapterFactory;

	private boolean sortedAscending = true;
	private boolean dirty = false;

	/**
	 * Rule base facade
	 */
	private RuleBaseWrapper rbManager;

	/**
	 * Rulebase item facade shortcut.
	 */
	class RBIW extends RuleBaseItemWrapper {
	}

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
	 * 
	 * @generated
	 */
	protected void handleActivate() {
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
		this.parent = parent;

		/*
		 * parent composite of all viewers
		 */
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());
		
		/*
		 * location for doctyp etc.
		 */
		Composite generalComposite = new Composite(composite, SWT.NONE);
		GridLayout glGeneralComposite = new GridLayout(2,false);
		glGeneralComposite.horizontalSpacing = 20;
		generalComposite.setLayout(glGeneralComposite);
		
		Label generalPropertiesLabel = new Label(generalComposite, SWT.NONE);
		generalPropertiesLabel.setFont(new Font(generalComposite.getDisplay(), "Arial", 12, SWT.BOLD));
		generalPropertiesLabel.setText("General Properties");
		GridData generalPropertiesData = new GridData();
		generalPropertiesData.horizontalSpan = 2;
		generalPropertiesLabel.setLayoutData(generalPropertiesData);
		
		Font labelFont = new Font(generalComposite.getDisplay(), "Arial", 10, SWT.BOLD);
		Label docTypLabel = new Label(generalComposite, SWT.NONE);
		docTypLabel.setFont(labelFont);
		docTypLabel.setText("Document Types:");
		
		docTypViewer = new ListViewer(generalComposite);
		docTypViewer.setContentProvider(ArrayContentProvider.getInstance());
		docTypViewer.setInput(rbManager.getRuleBase().getDocumentTypes());
		
		Label characteristicDocumentTypeLabel = new Label(generalComposite, SWT.NONE);
		characteristicDocumentTypeLabel.setFont(labelFont);
		characteristicDocumentTypeLabel.setText("Characteristic Document Type:");
		Label characteristicDocumentType = new Label(generalComposite, SWT.NONE);
		String docType = rbManager.getCharacteristicDocumentType();
		
		if (docType != null) {
			characteristicDocumentType.setText(docType);			
		} else {
			characteristicDocumentType.setText("N/A");
		}
		
		/*
		 * JFace Table Viewer
		 */
		Composite tableComposite = new Composite(composite, SWT.NONE);
		tableColumnLayout = new TableColumnLayout();
		tableComposite.setLayout(tableColumnLayout);

		GridData gdTableComposite = new GridData();
		gdTableComposite.horizontalAlignment = GridData.FILL;
		gdTableComposite.verticalAlignment = GridData.FILL;
		gdTableComposite.grabExcessHorizontalSpace = true;
		gdTableComposite.grabExcessVerticalSpace = true;

		tableComposite.setLayoutData(gdTableComposite);

		int style = SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER;
		ruleViewer = new TableViewer(tableComposite, style);
		
		
		ruleViewer.setComparator(new ViewerComparator(){

			@Override
			public int compare(Viewer viewer, Object e1, Object e2) {
				if((e1 instanceof RuleBaseItem) && (e2 instanceof RuleBaseItem)){
					RuleBaseItem item1 = (RuleBaseItem) e1;
					RuleBaseItem item2 = (RuleBaseItem) e2;
					
					int direction = ((TableViewer) viewer).getTable().getSortDirection();
					
					if(direction == SWT.UP){
						return RBIW.getName(item1).compareTo(RBIW.getName(item2));
					} else {
						return RBIW.getName(item1).compareTo(RBIW.getName(item2)) * -1;
					}
				}
				return super.compare(viewer, e1, e2);
			}
			
			
		});
		// SWT Table

		final Table table = ruleViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		GridData gdTable = new GridData();
		gdTable.verticalAlignment = GridData.FILL;
		gdTable.horizontalAlignment = GridData.FILL;
		gdTable.grabExcessVerticalSpace = true;
		gdTable.grabExcessHorizontalSpace = true;

		table.setLayoutData(gdTable);

		// ArrayContentProvider -> Table input is a java collection
		ruleViewer.setContentProvider(ArrayContentProvider.getInstance());

		/*
		 * Columns
		 */

		new ColumnActive(this);		
		new ColumnRulebaseItem(this);
		new ColumnDescription(this);
		new ColumnEditType(this);
		new ColumnRecognitionType(this);
		new ColumnPriority(this);
		new ColumnRefinementLevel(this);
		new ColumnACCount(this);
		new ColumnParameterCount(this);
		new ColumnDependenyCount(this);

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
				for (TableItem tableItem : table.getSelection()) {
					RuleBaseItem item = (RuleBaseItem) tableItem.getData();
					rbManager.removeItem(item, true);
				}
				
				table.remove(table.getSelectionIndices());
			}
		});

		MenuItem itemFormat = new MenuItem(menu, SWT.PUSH);
		itemFormat.setText("Format Rule Name(s)");

		itemFormat.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				for (TableItem tableItem : table.getSelection()) {
					RuleBaseItem item = (RuleBaseItem) tableItem.getData();
					RuleBaseItemWrapper.setName(item, RuleBaseItemWrapper.formatName(item));
					ruleViewer.update(item, null);
				}
			}
		});

		/*
		 * Set table input
		 */

		ruleViewer.setInput(rbManager.getItems());

		/*
		 * Register notification adapters on rulebase
		 */

		rbManager.startObserverNotification();
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
				parent.getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
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
					}
				});
			}
		});

		// Create the editing domain with a special command stack.
		//
		editingDomain = new AdapterFactoryEditingDomain(adapterFactory, commandStack, new HashMap<Resource, Boolean>());
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class key) {
		if (key.equals(IContentOutlinePage.class)) {
			return showOutlineView() ? getContentOutlinePage() : null;
		} else if (key.equals(IPropertySheetPage.class)) {
			return getPropertySheetPage();
		} else if (key.equals(IGotoMarker.class)) {
			return this;
		} else {
			return super.getAdapter(key);
		}
	}

	protected boolean showOutlineView() {
		return true;
	}

	public IContentOutlinePage getContentOutlinePage() {
		if (contentOutlinePage == null) {
			// The content outline is just a tree.
			//
			class RulebaseContentOutlinePage extends ContentOutlinePage {
				@Override
				public void createControl(Composite parent) {
					super.createControl(parent);
					contentOutlineViewer = getTreeViewer();
					contentOutlineViewer.addSelectionChangedListener(this);

					// Set up the tree viewer.
					//
					contentOutlineViewer.setContentProvider(new AdapterFactoryContentProvider(adapterFactory));
					contentOutlineViewer.setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory));
					contentOutlineViewer.setInput(editingDomain.getResourceSet());

					// Make sure our popups work.
					//
					createContextMenuFor(contentOutlineViewer);

					if (!editingDomain.getResourceSet().getResources().isEmpty()) {
						// Select the root object in the view.
						//
						contentOutlineViewer.setSelection(new StructuredSelection(editingDomain.getResourceSet().getResources().get(0)), true);
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
			Iterator<?> selectedElements = ((IStructuredSelection) selection).iterator();
			if (selectedElements.hasNext()) {
				// Get the first selected element.
				//
				Object selectedElement = selectedElements.next();

				// If it's the selection viewer, then we want it to select the
				// same selection as this selection.
				//
				ArrayList<Object> selectionList = new ArrayList<Object>();
				selectionList.add(selectedElement);
				while (selectedElements.hasNext()) {
					selectionList.add(selectedElements.next());
				}

				// Set the selection to the widget.
				//
				ruleViewer.setSelection(new StructuredSelection(selectionList));
			}
		}
	}

	public IPropertySheetPage getPropertySheetPage() {
		if (propertySheetPage == null) {
			propertySheetPage = new ExtendedPropertySheetPage(editingDomain) {
				@Override
				public void setSelectionToViewer(List<?> selection) {
					setSelectionToViewer(selection);
					setFocus();
				}

				@Override
				public void setActionBars(IActionBars actionBars) {
					super.setActionBars(actionBars);
				}
			};
			propertySheetPage.setPropertySourceProvider(new AdapterFactoryContentProvider(adapterFactory));
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

		int dndOperations = DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_LINK;
		Transfer[] transfers = new Transfer[] { LocalTransfer.getInstance() };
		viewer.addDragSupport(dndOperations, transfers, new ViewerDragAdapter(viewer));
		viewer.addDropSupport(dndOperations, transfers, new EditingDomainViewerDropAdapter(editingDomain, viewer));
	}

	public void setSelectionToViewer(Collection<?> collection) {
		final Collection<?> theSelection = collection;
		// Make sure it's okay.
		//
		if (theSelection != null && !theSelection.isEmpty()) {
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					// Try to select the items in the current content viewer of
					// the editor.
					//
					if (ruleViewer != null) {
						ruleViewer.setSelection(new StructuredSelection(theSelection.toArray()), true);
					}
				}
			};
			getSite().getShell().getDisplay().asyncExec(runnable);
		}
	}

	public void setStatusLineManager(ISelection selection) {
		IStatusLineManager statusLineManager = ruleViewer != null && currentViewer == contentOutlineViewer ? contentOutlineStatusLineManager : getActionBars().getStatusLineManager();

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

		super.dispose();
	}

	@Override
	public void setFocus() {
		parent.setFocus();
	}

	@Override
	public void doSave(IProgressMonitor monitor) {

		dirty = false;
		firePropertyChange(IEditorPart.PROP_DIRTY);

		try {
			((IFileEditorInput) getEditorInput()).getFile().getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doSaveAs() {
		// Nothing to do...
	}

	@Override
	public boolean isDirty() {
		return dirty;
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInputWithNotify(input);
		setPartName(input.getName());

		site.setSelectionProvider(this);
		site.getPage().addPartListener(partListener);

		selectionChangedListeners = new ArrayList<ISelectionChangedListener>();

		// Get workspace URI:
		URI resourceURI = EditUIUtil.getURI(getEditorInput());
		
		// Rulebase wrapper (No Recognition-Rule generation -> Folder = null):
		rbManager = new RuleBaseWrapper(EMFStorage.uriToFileUri(resourceURI));
		
		rbManager.addObserver(new Observer() {

			@Override
			public void update(Observable o, Object arg) {
				dirty = true;
				firePropertyChange(PROP_DIRTY);
			}
		});
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
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

	public RuleBaseWrapper getRulebaseWrapper() {
		return rbManager;
	}

	public ImageDescriptor getImageDescriptor(String name) {
		return ImageDescriptor.createFromURL(FileLocator.find(Platform.getBundle(Activator.PLUGIN_ID), new Path(String.format("icons/%s", name)), null));
	}

	public void update() {
		ruleViewer.refresh();
	}
	
	/**
	 * Returns the currently selected RuleBaseItems
	 * 
	 * @return
	 */
	public Collection<RuleBaseItem> getSelectedRuleBaseItems(){
		Collection<RuleBaseItem> res = new LinkedList<RuleBaseItem>();
		for (TableItem tableItem : ruleViewer.getTable().getSelection()) {
			RuleBaseItem item = (RuleBaseItem) tableItem.getData();
			res.add(item);		
		}
		
		return Collections.unmodifiableCollection(res);
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
}
