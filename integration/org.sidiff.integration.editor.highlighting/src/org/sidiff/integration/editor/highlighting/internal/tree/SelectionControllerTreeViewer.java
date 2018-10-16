package org.sidiff.integration.editor.highlighting.internal.tree;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.common.ui.viewer.IViewerProvider;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.sidiff.integration.editor.highlighting.StyledObject;
import org.sidiff.integration.editor.highlighting.internal.SelectionController;

public class SelectionControllerTreeViewer {

	private static SelectionControllerTreeViewer instance;

	private Map<TreeViewer,Selection> treeViewerSelections = new HashMap<>();
	private Collection<TreeViewer> managedTreeViewers = new HashSet<>();

	public static SelectionControllerTreeViewer getInstance() {
		if (instance == null) {
			instance = new SelectionControllerTreeViewer();
		}
		return instance;
	}

	public void refresh() {
		Display.getDefault().asyncExec(
				() -> highlightTreeEditors(
						SelectionController.getInstance().isEnabled()
							? SelectionController.getInstance().getSelected()
							: Collections.emptyList()));
	}

	public void addTreeViewer(TreeViewer treeViewer) {
		managedTreeViewers.add(treeViewer);
	}

	public void removeTreeViewer(TreeViewer treeViewer) {
		removeLabelProvider(treeViewer);
		managedTreeViewers.remove(treeViewer);
	}

	private void highlightTreeEditors(Collection<StyledObject> selected) {
		Set<TreeViewer> removedTreeViewers = treeViewerSelections.values().stream()
				.map(selection -> selection.treeViewer).collect(Collectors.toSet());

		// Set new selection:
		treeViewerSelections = calculateTreeEditorSelection(selected).peek(editor -> {
			IBaseLabelProvider labelProvider = editor.treeViewer.getLabelProvider();

			// Wrap the original label provider with one that highlights the selected elements:
			if(labelProvider instanceof HighlightingLabelProvider) {
				HighlightingLabelProvider wrapper = (HighlightingLabelProvider)labelProvider;
				wrapper.setTreeDecorations(editor.selection);
			} else {
				HighlightingLabelProvider wrapper = new HighlightingLabelProvider(labelProvider, editor.selection);
				editor.treeViewer.setLabelProvider(wrapper);
			}
			removedTreeViewers.remove(editor.treeViewer);

			// Show the elements in the tree:
			for (StyledObject selectedObject : editor.selection) {
				editor.treeViewer.reveal(selectedObject.getEObject());
			}
			editor.treeViewer.refresh();
		}).collect(Collectors.toMap(s -> s.treeViewer, s -> s));

		// Clear old selection:
		for (TreeViewer treeViewer : removedTreeViewers) {
			removeLabelProvider(treeViewer);
		}
	}

	private void removeLabelProvider(TreeViewer treeViewer) {
		try {
			IBaseLabelProvider labelProvider = treeViewer.getLabelProvider();
			if (labelProvider instanceof HighlightingLabelProvider) {
				treeViewer.setLabelProvider(((HighlightingLabelProvider) labelProvider).getBaseLabelProvider());
			}
		} catch (Exception e) {
			// do nothing as widget is disposed and will be cleared soon
		}
	}

	private Stream<Selection> calculateTreeEditorSelection(Collection<StyledObject> selected) {
		// Search all opened editors in the workbench:
		return Stream.concat(managedTreeViewers.stream(), findActiveTreeViewers())
			.map(treeViewer -> {
				Collection<Resource> editorResources = findResourcesForTreeViewer(treeViewer);
				List<StyledObject> editorSelected = selected.stream()
					.filter(styled -> styled.getEObject().eResource() != null && !editorResources.contains(styled.getEObject().eResource()))
					.flatMap(styled -> findEObjectInResources(editorResources, styled))
					.collect(Collectors.toList());
				return Selection.create(treeViewer, editorSelected);
			})
			.filter(Optional::isPresent)
			.map(Optional::get);
	}

	private Stream<StyledObject> findEObjectInResources(Collection<Resource> editorResources, StyledObject styledObject) {
		String fragment = EcoreUtil.getURI(styledObject.getEObject()).fragment();
		if(fragment == null || fragment.isEmpty()) {
			return Stream.empty();
		}

		return editorResources.stream().map(resource -> { 
			try {
				EObject resolved = resource.getEObject(fragment);
				if(resolved != null) {
					styledObject.setEObject(resolved);
					return styledObject;
				}
				return null;
			} catch(IllegalArgumentException e) {
				// TODO: this is not good practice, but how else to know whether getEObject works?
				return null;
			}
		}).filter(Objects::nonNull);
	}

	private Stream<TreeViewer> findActiveTreeViewers() {
		final IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if(window == null) {
			return Stream.empty();
		}
		return Arrays.stream(window.getActivePage().getEditorReferences())
				.map(ref -> ref.getEditor(false))
				.filter(IViewerProvider.class::isInstance)
				.filter(editor -> ((IViewerProvider)editor).getViewer() instanceof TreeViewer)
				.map(editor -> (TreeViewer)((IViewerProvider)editor).getViewer());
	}

	private Collection<Resource> findResourcesForTreeViewer(TreeViewer treeViewer) {
		if(treeViewer.getInput() instanceof ResourceSet) {
			return ((ResourceSet)treeViewer.getInput()).getResources();
		} else if(treeViewer.getInput() instanceof Resource) {
			return Collections.singleton((Resource)treeViewer.getInput());
		} else if(treeViewer.getInput() instanceof EObject) {
			Resource resource = ((EObject)treeViewer.getInput()).eResource();
			if(resource != null) {
				return Collections.singleton(resource);
			}
		}
		return Collections.emptySet();
	}

	private static class Selection {
		final TreeViewer treeViewer;
		final Set<StyledObject> selection;

		Selection(TreeViewer treeViewer, Collection<StyledObject> selection) {
			this.treeViewer = treeViewer;
			this.selection = Collections.unmodifiableSet(new HashSet<>(selection));
		}

		static Optional<Selection> create(TreeViewer treeViewer, Collection<StyledObject> selection) {
			if(selection.isEmpty()) {
				return Optional.empty();
			}
			return Optional.of(new Selection(treeViewer, selection));
		}
	}
}
