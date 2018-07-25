package de.unisiegen.informatik.pi.henshin.ui.interpreter.views;


import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.ui.dialogs.WorkspaceResourceDialog;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.provider.HenshinItemProviderAdapterFactory;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.eclipse.sirius.diagram.ui.tools.api.editor.DDiagramEditor;
import org.eclipse.sirius.ui.business.api.dialect.DialectUIManager;
import org.eclipse.sirius.viewpoint.DRepresentation;
import org.eclipse.sirius.viewpoint.DRepresentationDescriptor;
import org.eclipse.sirius.viewpoint.DView;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.ViewPart;

import de.unisiegen.informatik.pi.henshin.interpreter.Argument;
import de.unisiegen.informatik.pi.henshin.interpreter.HenshinInterpreter;
import de.unisiegen.informatik.pi.henshin.interpreter.exceptions.UnresolvedArgumentException;
import de.unisiegen.informatik.pi.henshin.ui.interpreter.HenshinInterpreterUIPlugin;


/**
 * 
 * @author cpietsch
 * 
 */
public class HenshinInterpreterView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "de.unisiegen.informatik.pi.henshin.ui.interpreter.views.HenshinInterpreterView";

	private static final ImageDescriptor HENSHIN_APPLY_IMG = HenshinInterpreterUIPlugin.getImageDescriptor("icons/henshin-apply.gif");
	
	private static final ImageDescriptor HENSHIN_MODEL_IMG = HenshinInterpreterUIPlugin.getImageDescriptor("icons/HenshinModelFile.gif");
	
	@Inject IWorkbench workbench;
	
	private Composite composite;
	
	// Viewer
	private TableViewer rule_viewer;
	
	private TableViewer	parameter_viewer;
	
	// Actions
	private Action loadModelResource_action;
	
	private Action loadHenshinModule_action;
	
	private Action applyRule_action;
	
	// Interpreter
	private HenshinInterpreter interpreter;
	
	private Map<EClass,List<EObject>> argumentCandidates;
	
	private EditingDomain editingDomain;
	
	private Resource resource;

	/**
	 * The {@link ComposedAdapterFactory} used to determine the default content and
	 * label provider
	 */
	private ComposedAdapterFactory adapterFactory;
	
	private AdapterFactoryLabelProvider adapterFactoryLabelProvider;
	
	public HenshinInterpreterView() {
		this.interpreter = new HenshinInterpreter();
		// Create an adapter factory that yields item providers.
		//
		this.adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		this.adapterFactory.addAdapterFactory(new HenshinItemProviderAdapterFactory());
		this.adapterFactoryLabelProvider = new AdapterFactoryLabelProvider(adapterFactory);
	}


	@Override
	public void createPartControl(Composite parent) {
		
		this.composite = parent;
		this.composite.setLayout(new FillLayout(SWT.VERTICAL));
		
		
		this.rule_viewer = new TableViewer(composite, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		this.rule_viewer.setContentProvider(ArrayContentProvider.getInstance());
		this.rule_viewer.setLabelProvider(this.adapterFactoryLabelProvider);
		this.rule_viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if (!event.getSelection().isEmpty() && event.getSelection() instanceof IStructuredSelection) {
					IStructuredSelection selection = (IStructuredSelection) event.getSelection();
					if (selection.size() == 1 && selection.getFirstElement() instanceof Unit) {
						Unit unit = (Unit) selection.getFirstElement();
						List<Argument> arguments = new ArrayList<Argument>();
						for(Argument argument: interpreter.getArgumentManager().getArguments()) {
							if(unit.getParameters().contains(argument.getParameter())) {
								arguments.add(argument);
							}
						}
						parameter_viewer.setInput(arguments);
					}
				}
				
			}
		});
		
		this.parameter_viewer = new TableViewer(composite, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
		this.parameter_viewer.setContentProvider(ArrayContentProvider.getInstance());
		this.parameter_viewer.getTable().setHeaderVisible(true);
		
		TableViewerColumn column_parameter = new TableViewerColumn(this.parameter_viewer, SWT.NONE);
		column_parameter.getColumn().setText("Parameter");
		column_parameter.getColumn().setWidth(150);
		column_parameter.getColumn().setResizable(true);
		column_parameter.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Argument argument = (Argument) element;
				return adapterFactoryLabelProvider.getText(argument.getParameter());
			}
		});
		
		TableViewerColumn unset = new TableViewerColumn(this.parameter_viewer, SWT.NONE);
		unset.getColumn().setText("Unset");
		unset.getColumn().setWidth(50);

		// add a labelProvider (display true, false or nothing)
		unset.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(Object element) {
				Argument argument = (Argument)element;
				return String.valueOf(argument.isUnset());
			}
		});

		// add editingSupport
		unset.setEditingSupport(new EditingSupport(this.parameter_viewer) {

			@Override
			protected void setValue(Object element, Object value) {
				Argument argument = (Argument) element;
				argument.setUnset((boolean)value);
				parameter_viewer.refresh();
			}

			@Override
			protected Object getValue(Object element) {
				Argument argument = (Argument) element;
				return argument.isUnset();
			}

			@Override
			protected CellEditor getCellEditor(Object element) {
				return new CheckboxCellEditor(((TableViewer) getViewer()).getTable());
				
			}

			@Override
			protected boolean canEdit(Object element) {
				return true;
			}
		});
		
		TableViewerColumn column_value = new TableViewerColumn(this.parameter_viewer, SWT.NONE);
		column_value.getColumn().setText("Value");
		column_value.getColumn().setWidth(350);
		column_value.getColumn().setResizable(true);
		column_value.setLabelProvider(new ColumnLabelProvider() {
			
			
			@Override
			public String getText(Object element) {
				Argument argument = (Argument) element;
				if(argument.getParameter().getType() instanceof EObject) {
					return adapterFactoryLabelProvider.getText(argument.getValue());
				}else {
					return argument.getValue().toString();
				}
			}
		});
		column_value.setEditingSupport(new EditingSupport(this.parameter_viewer) {
			
			
			@Override
			protected void setValue(Object element, Object value) {
				Argument argument = (Argument) element;
				if(value instanceof String) {
					argument.setValue(value);
				}else {
					int index = ((Integer) value).intValue();
					if (index > -1)
						argument.setValue(argumentCandidates.get(argument.getParameter().getType()).get(((Integer) value).intValue()));
				}
				
				parameter_viewer.refresh();
				
			}
			
			@Override
			protected Object getValue(Object element) {
				Argument argument = (Argument) element;
				if(argument.getParameter().getType() instanceof EDataType) {
					return argument.getValue();
				}else {
					return argumentCandidates.get(argument.getParameter().getType()).indexOf(argument.getValue());
				}
			}
			
			@Override
			protected CellEditor getCellEditor(Object element) {
				Argument argument = (Argument) element;
				if(argument.getParameter().getType() instanceof EDataType) {
					return new TextCellEditor(((TableViewer) getViewer()).getTable());
				}else {
					List<String> items = new ArrayList<String>();
					for(EObject eObject : argumentCandidates.get(argument.getParameter().getType())) {
						items.add(adapterFactoryLabelProvider.getText(eObject));
					}
					return new ComboBoxCellEditor(((TableViewer) getViewer()).getTable(), items.toArray(new String[] {}), SWT.READ_ONLY);
				}
			}
			
			@Override
			protected boolean canEdit(Object element) {
				return true;
			}
		});
		
		this.parameter_viewer.getTable().addListener(SWT.Resize, new Listener()
        {
            public void handleEvent(Event event)
            {
                column_value.getColumn().setWidth(parameter_viewer.getTable().getClientArea().width);
            }
        });
		
		
		getSite().setSelectionProvider(this.rule_viewer);
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				HenshinInterpreterView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(this.rule_viewer.getControl());
		this.rule_viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, this.rule_viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		
		manager.add(new Separator());
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(applyRule_action);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(loadModelResource_action);
		manager.add(loadHenshinModule_action);
	}

	private void makeActions() {
		this.loadModelResource_action = new Action() {
			public void run() {
				IFile[] files = WorkspaceResourceDialog.openFileSelection(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
						"Model Selection", "Select a model file", false, null,null);
				if(files.length > 0){
					
					IFile file = files[0];
					
					try {
					IEditorPart editorPart = openDiagram(file);
					
					if(editorPart != null) {
						if (editorPart instanceof DDiagramEditor) {
							DDiagramEditor editor = (DDiagramEditor) editorPart;
							editingDomain = editor.getEditingDomain();
							Iterator<Resource> iter = editor.getSession()
									.getSemanticResources().iterator();
							if (iter.hasNext()) {
								// FIXME: Determine the right resource if there are multiple
								resource = iter.next();
							}
						}else if(editorPart instanceof IEditingDomainProvider) {
							IEditingDomainProvider iEditingDomainProvider = (IEditingDomainProvider) editorPart;
							editingDomain = iEditingDomainProvider.getEditingDomain();
							// FIXME: Determine the right resource if there are multiple
							resource = editingDomain.getResourceSet().getResources().get(0);
						}
					}
					} catch (PartInitException e) {
						showMessage("Couldn't open the respective editor for the model!");
					}
							
					if(resource == null) {
						ResourceSet resourceSet = new ResourceSetImpl();
						resource = resourceSet.getResource(URI.createFileURI(file.getLocation().toOSString()), true);
					}
					
					argumentCandidates = new HashMap<EClass, List<EObject>>();
					updateArgumentCandidates();
					
					interpreter.setModelResource(resource);
				}
			}
		};
		this.loadModelResource_action.setText("load model");
		this.loadModelResource_action.setToolTipText("load a model");
		this.loadModelResource_action.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
			getImageDescriptor(ISharedImages.IMG_OBJ_FILE));
		
		this.loadHenshinModule_action = new Action() {
			public void run() {
				IFile[] files = WorkspaceResourceDialog.openFileSelection(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
						"Henshin Module Selection", "Select a Henshin Transformation", false, null,null);
				if(files.length > 0){
					
					IFile file = files[0];
					ResourceSet resourceSet = new ResourceSetImpl();
					Resource resource = resourceSet.getResource(URI.createFileURI(file.getLocation().toOSString()), true);
					
					Module module = (Module) resource.getContents().get(0);
					
					interpreter.setHenshinModule(module);
					
					rule_viewer.setInput(interpreter.getHenshinModule().getUnits().toArray());
				}
			}
		};
		this.loadHenshinModule_action.setText("load henshin units");
		this.loadHenshinModule_action.setToolTipText("load henshin units");
		this.loadHenshinModule_action.setImageDescriptor(HENSHIN_MODEL_IMG);
		
		applyRule_action = new Action() {
			
			public void run() {
				IStructuredSelection selection = rule_viewer.getStructuredSelection();
				if(selection.size() == 1) {
					Object obj = selection.getFirstElement();
					if(obj instanceof Unit) {
						Unit unit = (Unit) obj;
						ApplyCommand command = new ApplyCommand(interpreter, unit);
						if(editingDomain != null) {
							editingDomain.getCommandStack().execute(command);
						}else {
							command.execute();
						}
						updateArgumentCandidates();
						if(!command.isSuccess()) {
							MessageDialog.openError(rule_viewer.getControl().getShell(), "Error", unit.getName() + " couldn't be applied!");
						}
					}
				}
			};
		};
		applyRule_action.setText("apply");
		applyRule_action.setToolTipText("apply selected rule");
		applyRule_action.setImageDescriptor(HENSHIN_APPLY_IMG);
		
	}
	
	private class ApplyCommand extends AbstractCommand {

		private HenshinInterpreter interpreter;
		
		private Unit unit;
		
		private boolean success;
		
		 public ApplyCommand(HenshinInterpreter interpreter, Unit unit) {
			this.interpreter = interpreter;
			this.unit = unit;
			this.success = false;
		}
		@Override
		public boolean canExecute() {
			return true;
		}
		@Override
		public void execute() {
			try {
				this.success = interpreter.applyUnit(unit);
				if(!this.success) {
					MessageDialog.openError(composite.getShell(), "Application Error", unit.getName() + " could not be applied!");
				}
			} catch (UnresolvedArgumentException e) {
				MessageDialog.openError(composite.getShell(), "Unresolved Argument Error", e.getMessage());
			}
		}

		@Override
		public void redo() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void undo() {
			interpreter.undoLast();
		}
		
		public boolean isSuccess() {
			return this.success;
		}
		
	}

	private void hookDoubleClickAction() {
		this.rule_viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				System.out.print(event);
			}
		});
	}
	
	private void showMessage(String message) {
		MessageDialog.openInformation(
			this.rule_viewer.getControl().getShell(),
			"Henshin Interpreter",
			message);
	}

	@Override
	public void setFocus() {
		this.rule_viewer.getControl().setFocus();
	}

	public IEditorPart openDiagram(IFile modelFile) throws PartInitException {
				
		String diagramPath = modelFile.getLocation().toOSString().replace(modelFile.getName(), modelFile.getName().replace(modelFile.getFileExtension(), "") + "aird");
		URI diagramUri = URI.createFileURI(diagramPath);
		
		File diagram_file = new File(diagramPath);
		
		if(diagram_file.exists()) {
			// Start Sirius Session
			Session session = SessionManager.INSTANCE.getSession(diagramUri,
					new NullProgressMonitor());
			if (session != null) {
				if (!session.isOpen()) {
					session.open(new NullProgressMonitor());
				}
				
				for (Session otherSession : SessionManager.INSTANCE.getSessions()) {
					otherSession.getOwnedViews();
				}
				// Get representation
				Collection<DView> views = session.getOwnedViews();
				EList<DRepresentation> representations = new BasicEList<DRepresentation>();
				for (DView view : views) {
					for(DRepresentationDescriptor descriptor : view.getOwnedRepresentationDescriptors()){
						representations.add(descriptor.getRepresentation());
					}
				}
				
				// select the first representation
				DRepresentation representation = null;
				if (!representations.isEmpty())
					representation = representations.get(0);
				if (representation != null) {
					return DialectUIManager.INSTANCE.openEditor(session,
							representation, new NullProgressMonitor());
				} 
			}
		}
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		
		IEditorInput input = new FileEditorInput(modelFile);
		
		return page.openEditor(input, "org.eclipse.emf.ecore.presentation.EcoreEditorID");
		
	}
	
	@SuppressWarnings("unchecked")
	private void updateArgumentCandidates() {
		argumentCandidates.clear();
		for (Iterator<EObject> iterator = resource.getAllContents(); iterator.hasNext();) {
			EObject eObject = iterator.next();
			addArgumentCandidates(eObject);
			for(EReference eReference : eObject.eClass().getEAllReferences()) {
				if(!eReference.isDerived()) {
					Object target = eObject.eGet(eReference);
					if(target != null) {
						if(eReference.isMany()) {
							if(target instanceof List) {
								List<EObject> eObjects = (List<EObject>)target; 
								for(EObject tgt : eObjects) {
									if(tgt.eResource() != null && !tgt.eResource().equals(eObject.eResource())) {
										addArgumentCandidates(tgt);
									}
									
								}
							}
						}else {
							EObject tgt = (EObject) target;
							if(tgt.eResource() != null && !tgt.eResource().equals(eObject.eResource())) {
								addArgumentCandidates(tgt);
							}
						}
					}
				}
			}
			
		}
	}
	
	private void addArgumentCandidates(EObject eObject) {
		if(argumentCandidates.get(eObject.eClass()) == null) {
			argumentCandidates.put(eObject.eClass(), new ArrayList<EObject>());
		}
		if(!argumentCandidates.get(eObject.eClass()).contains(eObject)) {
			argumentCandidates.get(eObject.eClass()).add(eObject);
		}
		for(EClass eClass : eObject.eClass().getESuperTypes()) {
			if(argumentCandidates.get(eClass) == null) {
				argumentCandidates.put(eClass, new ArrayList<EObject>());
			}
			if(!argumentCandidates.get(eClass).contains(eObject)) {
				argumentCandidates.get(eClass).add(eObject);
			}
		}
	}
}
