package org.sidiff.difference.symmetric.compareview.internal;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.emf.common.ui.viewer.IViewerProvider;
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
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.symmetric.AddObject;
import org.sidiff.difference.symmetric.AddReference;
import org.sidiff.difference.symmetric.AttributeValueChange;
import org.sidiff.difference.symmetric.Change;
import org.sidiff.difference.symmetric.Correspondence;
import org.sidiff.difference.symmetric.RemoveObject;
import org.sidiff.difference.symmetric.RemoveReference;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.silift.common.HighlightableElement;

@SuppressWarnings("restriction")
public class DifferenceSelectionController implements ISelectionListener, INullSelectionListener {

	private static final String COMPARE_VIEWER_EXTENSION = "org.sidiff.difference.compareViewer";

	private static DifferenceSelectionController instance = null;

	private List<IDecorator> decorators = null;
	private Map<EObject, IDecoratorTarget> decoratorTargets = null;
	private List<EObject> selected = null;
	private List<EObject> treeDecorations = null;
	private Set<TreeViewer> treeViewersWithDecorations = null;

	private Map<String, Collection<SupportedEditor>> compareViewers = null;

	private boolean highlightEnabled = false;
	private ISelection lastSelection = null;
	private IWorkbenchPart lastPart = null;

	private DifferenceSelectionController() {
		selected = new ArrayList<EObject>();
		decoratorTargets = new HashMap<EObject, IDecoratorTarget>();
		decorators = new ArrayList<IDecorator>();
		treeDecorations = new ArrayList<EObject>();
		treeViewersWithDecorations = new HashSet<TreeViewer>();

		compareViewers = new HashMap<String, Collection<SupportedEditor>>();
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] config = registry.getConfigurationElementsFor(COMPARE_VIEWER_EXTENSION);

		for (IConfigurationElement configElement : config) {
			String compareViewerID = configElement.getAttribute("editorId");

			List<SupportedEditor> supportedEditors = new ArrayList<DifferenceSelectionController.SupportedEditor>();
			for (IConfigurationElement childElement : configElement.getChildren()) {
				SupportedEditor supportedEditor = new SupportedEditor();

				supportedEditor.treeId = childElement.getAttribute("treeEditorId");
				supportedEditor.diagramId = childElement.getAttribute("diagramEditorId");

				supportedEditors.add(supportedEditor);
			}

			compareViewers.put(compareViewerID, supportedEditors);
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
			doHighlight();
		}
	}

	public void registerDecorator(IDecorator decorator, IDecoratorTarget decoratorTarget) {
		View view = (View) decoratorTarget.getAdapter(View.class);

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
		if (compareViewers.containsKey(part.getSite().getId())) {
			lastPart = part;
			lastSelection = selection;

			selected.clear();
			if (selection instanceof IStructuredSelection && !selection.isEmpty()) {
				IStructuredSelection _selection = (IStructuredSelection) selection;
				if (_selection.getFirstElement() instanceof SemanticChangeSet) {
					SemanticChangeSet semanticChangeSet = (SemanticChangeSet) _selection.getFirstElement();

					for (Change change : semanticChangeSet.getChanges()) {
						handleChange(change);
					}
				} else if (_selection.getFirstElement() instanceof Change) {
					handleChange((Change) _selection.getFirstElement());
				} else if (_selection.getFirstElement() instanceof Correspondence) {
					Correspondence corr = (Correspondence) _selection.getFirstElement();
					selected.add(corr.getObjA());
					selected.add(corr.getObjB());
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
		}
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

			for (SupportedEditor supportedEditor : compareViewers.get(lastPart.getSite().getId())) {
				treeEditors.add(supportedEditor.treeId);
			}

			for (EObject selectedObject : selected) {
				DecoratedTuple decoratedTuple = findObjectInEditor(selectedObject, treeEditors);
				int index = 0;
				for (IEditorPart editor : decoratedTuple.editors) {
					bringEditorToTop(editor);
					if (editor instanceof IViewerProvider) {
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
		} else {
			List<String> diagramEditors = new ArrayList<String>();

			for (SupportedEditor supportedEditor : compareViewers.get(lastPart.getSite().getId())) {
				if (supportedEditor.diagramId != null) {
					diagramEditors.add(supportedEditor.diagramId);
				}
			}
			for (EObject decoratedView : decoratedViews) {
				DecoratedTuple tuple = findObjectInEditor(decoratedView, diagramEditors);
				for (IEditorPart editor : tuple.editors) {
					bringEditorToTop(editor);
				}
			}
		}
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

						boolean found = false;
						for (Resource resource : resources) {
							EObject result = resource.getEObject(selectedObjectFragment);

							if (result != null) {
								found = true;
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
		if (change instanceof AddObject) {
			selected.add(((AddObject) change).getObj());
		} else if (change instanceof RemoveObject) {
			selected.add(((RemoveObject) change).getObj());
		} else if (change instanceof AttributeValueChange) {
			selected.add(((AttributeValueChange) change).getObjA());
			selected.add(((AttributeValueChange) change).getObjB());
		} else if (change instanceof AddReference) {
			selected.add(((AddReference) change).getSrc());
			selected.add(((AddReference) change).getTgt());
		} else if (change instanceof RemoveReference) {
			selected.add(((RemoveReference) change).getSrc());
			selected.add(((RemoveReference) change).getTgt());
		}
	}

	private static class SupportedEditor {

		public String treeId = null;
		public String diagramId = null;

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
