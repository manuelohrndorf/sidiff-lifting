package org.sidiff.difference.symmetric.compareview.internal;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.emf.common.ui.viewer.IViewerProvider;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecorator;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorTarget;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.INullSelectionListener;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.EditorSite;
import org.sidiff.common.emf.access.EMFMetaAccess;
import org.sidiff.common.emf.access.HighlightableElement;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.symmetric.AddObject;
import org.sidiff.difference.symmetric.AddReference;
import org.sidiff.difference.symmetric.AttributeValueChange;
import org.sidiff.difference.symmetric.Change;
import org.sidiff.difference.symmetric.RemoveObject;
import org.sidiff.difference.symmetric.RemoveReference;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.compareview.ChangeType;
import org.sidiff.difference.symmetric.compareview.XtextMarker;
import org.sidiff.integration.editor.access.IntegrationEditorAccess;
import org.sidiff.integration.editor.extension.IEditorIntegration;
import org.sidiff.matching.model.Correspondence;
import org.sidiff.matching.model.Matching;

@SuppressWarnings("restriction")
public class DifferenceSelectionController implements ISelectionListener, INullSelectionListener {

	//private static final String COMPARE_VIEWER_EXTENSION = "org.sidiff.difference.compareViewer";
	private static final String XTEXT_MARKER_EXTENSION = "org.sidiff.difference.symmetric.compareview.xtextmarker";

	private static DifferenceSelectionController instance = null;

	private List<IDecorator> decorators = null;
	private Map<EObject, IDecoratorTarget> decoratorTargets = null;
	private List<EObject> selected = null;
	private List<EObject> addedElements = null;
	private List<EObject> deletedElements = null;
	private List<EObject> changedElements = null;
	private List<EObject> correspondentElements = null;
	private List<EObject> contextElements = null;
	private List<EObject> treeDecorations = null;
	private Set<TreeViewer> treeViewersWithDecorations = null;
	private Map<EObject, URI> eObjecToResourceURI = new HashMap<EObject, URI>();
	
	private Map<EObject, EObject> correspondencesAB = new HashMap<EObject, EObject>();
	private Map<EObject, EObject> correspondencesBA = new HashMap<EObject, EObject>();
	
	private XtextMarker xtextmarker = null;

	private boolean highlightReferenceChanges = false;

	private boolean highlightEnabled = false;
	private ISelection lastSelection = null;
	private IWorkbenchPart lastPart = null;

	private DifferenceSelectionController() {
		selected = new ArrayList<EObject>();
		addedElements = new ArrayList<EObject>();
		deletedElements = new ArrayList<EObject>();
		changedElements = new ArrayList<EObject>();
		correspondentElements = new ArrayList<EObject>();
		contextElements = new ArrayList<EObject>();
		decoratorTargets = new HashMap<EObject, IDecoratorTarget>();
		decorators = new ArrayList<IDecorator>();
		treeDecorations = new ArrayList<EObject>();
		treeViewersWithDecorations = new HashSet<TreeViewer>();
		
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] config = registry.getConfigurationElementsFor(XTEXT_MARKER_EXTENSION);
		for (IConfigurationElement configElement : config) {
			try {
				xtextmarker = (XtextMarker) configElement.createExecutableExtension("xtextmarker");
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}

	public static DifferenceSelectionController getInstance() {
		if (instance == null) {
			instance = new DifferenceSelectionController();
		}
		return instance;
	}

	public List<EObject> getSelected() {
		return selected;
	}

	public void toggleHighlight() {
		highlightEnabled = highlightEnabled ? false : true;

		if (highlightEnabled) {
			selectionChanged(lastPart, lastSelection);
		} else {
			selected.clear();
			addedElements.clear();
			deletedElements.clear();
			changedElements.clear();
			doHighlight();
		}
	}

	public void registerDecorator(IDecorator decorator, IDecoratorTarget decoratorTarget) {
		View view = (View) decoratorTarget.getAdapter(View.class);

		if(view.getElement() != null){
			if (decoratorTargets.containsKey(view.getElement())) {
				View parent = view;
				boolean topMost = false;
				if (!(parent instanceof Diagram)) {
					while (!((parent = (View) parent.eContainer()) instanceof Diagram)) {
						if (parent == decoratorTargets.get(view.getElement()).getAdapter(View.class)) {
							topMost = true;
						}
					}
					if (!topMost) {
						decoratorTargets.put(view.getElement(), decoratorTarget);
					}
				}
			} else {
				decoratorTargets.put(view.getElement(), decoratorTarget);
			}
		}
		decorators.add(decorator);
	}

	public IDecoratorTarget getPrefferedDecoratorTarget(EObject element) {
		return decoratorTargets.get(element);
	}

	public void unregisterDecorator(IDecorator decorator) {
		decorators.remove(decorator);
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		//if (compareViewers.containsKey(part.getSite().getId())) {
			lastPart = part;
			lastSelection = selection;

			selected.clear();
			addedElements.clear();
			deletedElements.clear();
			changedElements.clear();
			correspondentElements.clear();
			contextElements.clear();
			eObjecToResourceURI.clear();
			if (selection instanceof IStructuredSelection && !selection.isEmpty()) {
				IStructuredSelection _selection = (IStructuredSelection) selection;
				if (_selection.getFirstElement() instanceof SemanticChangeSet) {
					SemanticChangeSet semanticChangeSet = (SemanticChangeSet) _selection.getFirstElement();
					if(correspondencesAB.isEmpty() && correspondencesBA.isEmpty()){
						for(Correspondence correspondence : ((SymmetricDifference)semanticChangeSet.eContainer()).getMatching().getCorrespondences()){
							correspondencesAB.put(correspondence.getMatchedA(), correspondence.getMatchedB());
							correspondencesBA.put(correspondence.getMatchedB(), correspondence.getMatchedA());
						}
					}
					for (Change change : semanticChangeSet.getChanges()) {
						highlightReferenceChanges = false;
						if(semanticChangeSet.getChanges().size()==1 && (semanticChangeSet.getChanges().get(0) instanceof AddReference || semanticChangeSet.getChanges().get(0) instanceof RemoveReference)){
							highlightReferenceChanges = true;
						}
						handleChange(change);
					}
				} else if (_selection.getFirstElement() instanceof Change) {
					handleChange((Change) _selection.getFirstElement());
				} else if (_selection.getFirstElement() instanceof Correspondence) {
					Correspondence corr = (Correspondence) _selection.getFirstElement();
					
					EObject eObjectA = corr.getMatchedA();
					EObject eObjectB = corr.getMatchedB();
					
					selected.add(eObjectA);
					selected.add(eObjectB);
					
					correspondentElements.add(eObjectA);
					correspondentElements.add(eObjectB);
					
					eObjecToResourceURI.put(eObjectA, ((Matching) corr.eContainer()).getEResourceA().getURI());
					eObjecToResourceURI.put(eObjectB, ((Matching) corr.eContainer()).getEResourceB().getURI());
				} else if (_selection.getFirstElement() instanceof OperationInvocation) {
					OperationInvocation operationInvocation = (OperationInvocation) _selection.getFirstElement();
					SemanticChangeSet semanticChangeSet = operationInvocation.getChangeSet();

					for (Change change : semanticChangeSet.getChanges()) {
						handleChange(change);
					}
				} else if (_selection.getFirstElement() instanceof ObjectParameterBinding) {
					ObjectParameterBinding objectParameter = (ObjectParameterBinding) _selection.getFirstElement();

					if (objectParameter.getActualA() != null) {
						selected.add(objectParameter.getActualA());
					}

					if (objectParameter.getActualB() != null) {
						selected.add(objectParameter.getActualB());
					}
				} else if (_selection.getFirstElement() instanceof EObject) {
					EObject eObject = (EObject) _selection.getFirstElement();
					selected.add(eObject);
				} else if (_selection.getFirstElement() instanceof HighlightableElement) {
					HighlightableElement highlightableElement = (HighlightableElement) _selection.getFirstElement();
					selected.addAll(highlightableElement.getElements());
				}
			}

			if (highlightEnabled) {
				doHighlight();
			}
		//} //if (compareViewers.containsKey(part.getSite().getId()))
	}

	private void doHighlight() {
		treeDecorations.clear();
		for (TreeViewer treeViewer : treeViewersWithDecorations) {
			try {
				treeViewer.refresh();
			} catch (SWTException e) {
				// do nothing as widget is disposed and will be cleared soon
			}
		}
		treeViewersWithDecorations.clear();
		
		if (xtextmarker != null) {
			xtextmarker.clear();
		}

		List<EObject> decoratedViews = new ArrayList<EObject>();
		for (int i = 0; i < decorators.size(); i++) {
			IDecorator decorator = decorators.get(i);
			if (decorator instanceof DifferenceSelectionDecorator) {
				View decoratedViewOrNull = ((DifferenceSelectionDecorator) decorator).decorate();
				if (decoratedViewOrNull != null && !(decoratedViewOrNull instanceof Diagram)) {
					decoratedViews.add(decoratedViewOrNull);
				}
			} else {
				decorator.refresh();
			}
		}

		if (decoratedViews.size() == 0) {
			List<String> treeEditors = new ArrayList<String>();

			//Get "best" default Editor for current model
			if(!eObjecToResourceURI.values().isEmpty()){
				IEditorIntegration editor = IntegrationEditorAccess.getInstance().
						getIntegrationEditorForModelOrDiagramFile(eObjecToResourceURI.values().iterator().next());

				if(editor != null && editor.isDefaultEditorPresent()){
					treeEditors.add(editor.getDefaultEditorID());

				}			
			}
			markElements(contextElements, treeEditors, ChangeType.CONTEXT, "org.sidiff.compare.marker.context");
			markElements(addedElements, treeEditors, ChangeType.ADD, "org.sidiff.compare.marker.add");
			markElements(deletedElements, treeEditors, ChangeType.DELETE, "org.sidiff.compare.marker.delete");
			markElements(changedElements, treeEditors, ChangeType.CHANGE, "org.sidiff.compare.marker.change");
			markElements(correspondentElements, treeEditors, ChangeType.CORRESPONDENCE, "org.sidiff.compare.marker.correspondence");
			
			
		} else {
			List<String> diagramEditors = new ArrayList<String>();

			//Get "best" diagram Editor for current model	
			if(!eObjecToResourceURI.values().isEmpty()){
				IEditorIntegration editor = IntegrationEditorAccess.getInstance().
						getIntegrationEditorForModelOrDiagramFile(eObjecToResourceURI.values().iterator().next());
				if(editor != null && editor.isDiagramEditorPresent()){
					diagramEditors.add(editor.getDefaultEditorID());

				}		
			}

			for (EObject decoratedView : decoratedViews) {
				DecoratedTuple tuple = findObjectInEditor(decoratedView, diagramEditors);
				for (IEditorPart ed : tuple.editors) {
					bringEditorToTop(ed);
				}
			}
		}
	}
	
	private void markElements(List<EObject> elements, List<String> treeEditors, ChangeType type, String markerID){
		for (EObject selectedObject : elements) {
			DecoratedTuple decoratedTuple = null;
			if (xtextmarker != null && xtextmarker.isXtextObject(selectedObject)) {
				decoratedTuple = findTextObjectInEditor(selectedObject);
			} else {
				decoratedTuple = findObjectInEditor(selectedObject, treeEditors);
			}
			int index = 0;
			for (IEditorPart editor : decoratedTuple.editors) {
				bringEditorToTop(editor);
				if (xtextmarker != null && xtextmarker.isXtextObject(selectedObject)) {
					xtextmarker.mark(selectedObject, editor, type, markerID);
				} else if (editor instanceof IViewerProvider) {
					Viewer viewer = ((IViewerProvider) editor).getViewer();

					if (viewer instanceof TreeViewer) {
						TreeViewer treeViewer = (TreeViewer) viewer;
						treeViewer.collapseAll();
						treeViewer.reveal(decoratedTuple.eObjects.get(index));
						IBaseLabelProvider labelProvider = treeViewer.getLabelProvider();
						if (!(labelProvider instanceof WrapperLabelProvider)) {
							WrapperLabelProvider wrapper = new WrapperLabelProvider();

							wrapper.labelProvider = (ILabelProvider) labelProvider;
							wrapper.treeDecorations = treeDecorations;

							treeViewer.setLabelProvider(wrapper);
						}

						treeDecorations.add(decoratedTuple.eObjects.get(index));
						treeViewersWithDecorations.add(treeViewer);
						treeViewer.update(decoratedTuple.eObjects.get(index), null);
					}
				}
				index++;
			}
		}
	}
	
	private DecoratedTuple findTextObjectInEditor(EObject eObject) {
		DecoratedTuple decoratedTuple = new DecoratedTuple();
		for (IEditorReference editorReference : PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getEditorReferences()) {
			if (xtextmarker != null) {
				URI resourceURI = eObjecToResourceURI.get(eObject);
				Set<EObject> results = xtextmarker.findXtextObjectInEditor(eObject, editorReference.getEditor(true), resourceURI);
				
				if (!results.isEmpty()) {
					decoratedTuple.editors.add(editorReference.getEditor(true));
					for (EObject result : results)  {
						decoratedTuple.eObjects.add(result);
					}
				}
			}
		}
		
		return decoratedTuple;
	}

	private DecoratedTuple findObjectInEditor(EObject eObject, List<String> filter) {
		DecoratedTuple decoratedTuple = new DecoratedTuple();
		for (IEditorReference editorReference : PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getEditorReferences()) {
			if (filter.contains(editorReference.getId())) {
				IEditorPart editor = editorReference.getEditor(false);
				try {
					if (editor != null
							&& (editor instanceof IEditingDomainProvider || editor instanceof IDiagramWorkbenchPart || editor
									.getClass().getMethod("getEditingDomain", (Class<?>[]) null) != null)) {
						Collection<Resource> resources = null;
						if (editor instanceof IEditingDomainProvider) {
							EditingDomain editingDomain = ((IEditingDomainProvider) editor).getEditingDomain();
							resources = editingDomain.getResourceSet().getResources();
						} else if (editor instanceof IDiagramWorkbenchPart) {
							resources = ((IDiagramWorkbenchPart) editor).getDiagram().eResource().getResourceSet()
									.getResources();
						} else {
							EditingDomain domain = (EditingDomain) editor.getClass()
									.getMethod("getEditingDomain", (Class<?>[]) null).invoke(editor);
							resources = domain.getResourceSet().getResources();
						}
						String selectedObjectFragment = EcoreUtil.getURI(eObject).fragment();

						for (Resource resource : resources) {
							EObject result = resource.getEObject(selectedObjectFragment);

							if (result != null) {
								decoratedTuple.eObjects.add(result);
								decoratedTuple.editors.add(editor);
							}
						}

						List<EStructuralFeature> allBaseReferences = EMFMetaAccess.getEStructuralFeaturesByRegEx(
								eObject.eClass(), "^(base)_\\w+", true);

						// FIXME for more than 1 stereotype
						if (allBaseReferences.size() == 1) {
							EStructuralFeature feature = allBaseReferences.get(0);
							EObject profiledObject = (EObject) eObject.eGet(feature);
							EObject resultProfiledObject = null;

							if (profiledObject != null) {
								for (Resource resource : resources) {
									selectedObjectFragment = EcoreUtil.getURI(profiledObject).fragment();
									resultProfiledObject = resource.getEObject(selectedObjectFragment);

									if (resultProfiledObject != null) {
										decoratedTuple = new DecoratedTuple();
										decoratedTuple.eObjects.add(resultProfiledObject);
										decoratedTuple.editors.add(editor);
									}
								}
							}
						}
					}
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return decoratedTuple;
	}

	private void bringEditorToTop(IEditorPart editor) {
		MPart mPart = ((EditorSite) editor.getSite()).getModel();
		((MElementContainer<MUIElement>) mPart.getParent()).setSelectedElement(mPart);
		// EModelService modelService =
		// mPart.getContext().get(EModelService.class);
		// modelService.bringToTop(mPart);
	}

	private void handleChange(Change change) {
		SymmetricDifference diff = (SymmetricDifference) change.eContainer();
		if (change instanceof AddObject) {
			EObject eObjectB = ((AddObject) change).getObj();
			selected.add(eObjectB);
			addedElements.add(eObjectB);
			eObjecToResourceURI.put(eObjectB, diff.getModelB().getURI());
		
			if (xtextmarker != null && xtextmarker.isXtextObject(eObjectB)){
				EObject[] eObjects = xtextmarker.getContextElement(eObjectB, correspondencesBA);
				contextElements.add(eObjects[0]);
				eObjecToResourceURI.put(eObjects[0], diff.getModelB().getURI());
				contextElements.add(eObjects[1]);	
				eObjecToResourceURI.put(eObjects[1], diff.getModelA().getURI());
			}
		} else if (change instanceof RemoveObject) {
			EObject eObjectA = ((RemoveObject) change).getObj();
			selected.add(eObjectA);
			deletedElements.add(eObjectA);
			eObjecToResourceURI.put(eObjectA, diff.getModelA().getURI());
			
			if (xtextmarker != null && xtextmarker.isXtextObject(eObjectA)){
				EObject[] eObjects = xtextmarker.getContextElement(eObjectA, correspondencesAB);
				contextElements.add(eObjects[0]);
				eObjecToResourceURI.put(eObjects[0], diff.getModelA().getURI());
				contextElements.add(eObjects[1]);	
				eObjecToResourceURI.put(eObjects[1], diff.getModelB().getURI());
			}
		} else if (change instanceof AttributeValueChange) {
			EObject eObjectA = ((AttributeValueChange) change).getObjA();
			EObject eObjectB = ((AttributeValueChange) change).getObjB();
			selected.add(eObjectA);
			changedElements.add(eObjectA);
			selected.add(eObjectB);
			changedElements.add(eObjectB);
			eObjecToResourceURI.put(eObjectA, diff.getModelA().getURI());
			eObjecToResourceURI.put(eObjectB, diff.getModelB().getURI());
		} else if (change instanceof AddReference) {
			EObject eObjectA = ((AddReference) change).getSrc();
			EObject eObjectB = ((AddReference) change).getTgt();
			selected.add(eObjectA);
			selected.add(eObjectB);
			eObjecToResourceURI.put(eObjectA, diff.getModelB().getURI());
			eObjecToResourceURI.put(eObjectB, diff.getModelB().getURI());
			
			if (xtextmarker != null && (xtextmarker.isXtextObject(eObjectA) || xtextmarker.isXtextObject(eObjectB)) && highlightReferenceChanges) {
				// In case of Xtext models, do not include Reference Changes into the highlighted change context 
				// since this usually leads to too many underlined lines of text in textual model representations.
				EObject[] eObjects = xtextmarker.getContextElement(eObjectB, correspondencesBA);
				changedElements.add(eObjectA);
				contextElements.add(eObjects[0]);
				eObjecToResourceURI.put(eObjects[0], diff.getModelB().getURI());
				contextElements.add(eObjects[1]);	
				eObjecToResourceURI.put(eObjects[1], diff.getModelA().getURI());
			}
		} else if (change instanceof RemoveReference) {
			EObject eObjectA = ((RemoveReference) change).getSrc();
			EObject eObjectB = ((RemoveReference) change).getTgt();
			selected.add(eObjectA);
			selected.add(eObjectB);
			eObjecToResourceURI.put(eObjectA, diff.getModelA().getURI());
			eObjecToResourceURI.put(eObjectB, diff.getModelA().getURI());
			if (xtextmarker != null && (xtextmarker.isXtextObject(eObjectA) || xtextmarker.isXtextObject(eObjectB)) && highlightReferenceChanges) {
				// In case of Xtext models, do not include Reference Changes into the highlighted change context 
				// since this usually leads to too many underlined lines of text in textual model representations.
				EObject[] eObjects = xtextmarker.getContextElement(eObjectA, correspondencesAB);
				changedElements.add(eObjectA);
				eObjecToResourceURI.put(eObjectA, diff.getModelA().getURI());
				eObjecToResourceURI.put(eObjectB, diff.getModelA().getURI());
				contextElements.add(eObjects[0]);
				eObjecToResourceURI.put(eObjects[0], diff.getModelA().getURI());
				contextElements.add(eObjects[1]);	
				eObjecToResourceURI.put(eObjects[1], diff.getModelB().getURI());
			}
		}
	}


	private static class DecoratedTuple {

		public List<IEditorPart> editors = new ArrayList<IEditorPart>();
		public List<EObject> eObjects = new ArrayList<EObject>();

	}

	private static class WrapperLabelProvider implements ILabelProvider, ITableLabelProvider, IColorProvider {

		public ILabelProvider labelProvider = null;
		public ITableLabelProvider tableProvider = null;
		public List<EObject> treeDecorations = null;

		@Override
		public Color getForeground(Object element) {
			if (treeDecorations.contains(element)) {
				return new Color(null, 255, 0, 0);
			}
			return null;
		}

		@Override
		public Color getBackground(Object element) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void addListener(ILabelProviderListener listener) {
			labelProvider.addListener(listener);
		}

		@Override
		public void dispose() {
			labelProvider.dispose();
		}

		@Override
		public boolean isLabelProperty(Object element, String property) {
			return labelProvider.isLabelProperty(element, property);
		}

		@Override
		public void removeListener(ILabelProviderListener listener) {
			labelProvider.removeListener(listener);
		}

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return tableProvider.getColumnImage(element, columnIndex);
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			return tableProvider.getColumnText(element, columnIndex);
		}

		@Override
		public Image getImage(Object element) {
			return labelProvider.getImage(element);
		}

		@Override
		public String getText(Object element) {
			return labelProvider.getText(element);
		}
	}
}
