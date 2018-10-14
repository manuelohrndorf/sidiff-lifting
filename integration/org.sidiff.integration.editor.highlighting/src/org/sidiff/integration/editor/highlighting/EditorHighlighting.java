package org.sidiff.integration.editor.highlighting;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.ui.INullSelectionListener;
import org.eclipse.ui.ISelectionListener;
import org.sidiff.integration.editor.highlighting.internal.SelectionController;
import org.sidiff.integration.editor.highlighting.internal.tree.SelectionControllerTreeViewer;

/**
 * Singleton ({@link #getInstance()}) which registers the highlighting adapters
 * ({@link ISelectionHighlightingAdapter}) that convert a selection to model
 * elements. The highlighting is triggered by a selection listener
 * ({@link #getSelectionListener()}, {@link #getSelectionChangedListener()},
 * {@link #getNullSelectionListener()})
 * 
 * @author Manuel Ohrndorf, Robert Müller
 */
public class EditorHighlighting {

	private static EditorHighlighting instance;

	/**
	 * @return The singleton of the highlighting registry.
	 */
	public static EditorHighlighting getInstance() {
		if (instance == null) {
			instance = new EditorHighlighting();
		}
		return instance;
	}


	private List<ISelectionHighlightingAdapter> adapters;

	private EditorHighlighting() {
		this.adapters = new LinkedList<>();
		initSelectionAdapters();
	}

	private void initSelectionAdapters() {
		for(IConfigurationElement element :
			Platform.getExtensionRegistry().getConfigurationElementsFor(ISelectionHighlightingAdapter.EXTENSION_POINT_ID)) {
			try {
				registerAdapter((ISelectionHighlightingAdapter)
						element.createExecutableExtension(ISelectionHighlightingAdapter.ATTRIBUTE_CLASS));	
			} catch(CoreException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param adapter
	 *            The highlighting adapters that convert a selection to model
	 *            elements.
	 */
	public void registerAdapter(ISelectionHighlightingAdapter adapter) {
		adapters.add(adapter);
	}

	/**
	 * @param adapter
	 *            The highlighting adapters that convert a selection to model
	 *            elements.
	 */
	public void deregisterAdapter(ISelectionHighlightingAdapter adapter) {
		adapters.remove(adapter);
	}

	/**
	 * @return A listener that can be registered to a viewer. All selections
	 *         will be converted to model elements that will be highlighted.
	 */
	public ISelectionListener getSelectionListener() {
		return SelectionController.getInstance();
	}

	/**
	 * @return A listener that can be registered to a viewer. All selections
	 *         will be converted to model elements that will be highlighted.
	 */
	public ISelectionChangedListener getSelectionChangedListener() {
		return SelectionController.getInstance();
	}

	/**
	 * @return A listener that can be registered to a viewer. All selections
	 *         will be converted to model elements that will be highlighted.
	 */
	public INullSelectionListener getNullSelectionListener() {
		return SelectionController.getInstance();
	}

	/**
	 * @return The current selection.
	 */
	public Collection<StyledObject> getSelection() {
		return SelectionController.getInstance().getSelected();
	}

	/**
	 * Overwrites the actual selection (listener).
	 * 
	 * @param selected
	 *            A new selection.
	 * 
	 * @see #getSelectionListener()
	 * @see #getSelectionChangedListener()
	 * @see #getNullSelectionListener()
	 */
	public void setSelection(Collection<StyledObject> selected) {
		SelectionController.getInstance().setSelection(selected);
	}

	/**
	 * @param selection
	 *            The selection that will be appended.
	 */
	public void appendSelection(Collection<StyledObject> selection) {
		SelectionController.getInstance().appendSelection(selection);
	}

	/**
	 * @param selection
	 *            A UI selection.
	 * @return The selected model elements that will be highlighted.
	 */
	public Collection<StyledObject> getElements(ISelection selection) {
		Set<StyledObject> elements = new HashSet<>();

		if (selection instanceof IStructuredSelection && !selection.isEmpty()) {
			for (Object selectedElement : ((IStructuredSelection)selection).toList()) {
				if (selectedElement instanceof EObject) {
					elements.add(new StyledObject((EObject)selectedElement, ColorConstants.red));
				}
			}
		}

		// Check registered adapter:
		for (ISelectionHighlightingAdapter adapter : adapters) {
			adapter.getElements(selection).forEach(elements::add);
		}

		return elements;
	}

	/**
	 * Returns whether the highlighting is enabled.
	 * @return <code>true</code> if highlighting is enabled, <code>false</code> otherwise
	 */
	public boolean isEnabled() {
		return SelectionController.getInstance().isEnabled();
	}

	/**
	 * Enables/disables highlighting globally.
	 * @param enabled <code>true</code> to enabled, <code>false</code> to disable
	 */
	public void setEnabled(boolean enabled) {
		SelectionController.getInstance().setEnabled(enabled);
	}

	/**
	 * Adds a manually managed TreeViewer to receive highlighting.
	 * Only necessary for some editors that do not directly expose the TreeViewer.
	 * The TreeViewer will automatically be unregistered, when its underlying
	 * control is disposed. If, however it has no control when it is added,
	 * it must be manually removed.
	 * @param treeViewer the tree viewer
	 */
	public void addHighlightingReceiver(TreeViewer treeViewer) {
		SelectionControllerTreeViewer.getInstance().addTreeViewer(treeViewer);
		if(treeViewer.getControl() != null) {
			treeViewer.getControl().addDisposeListener(new DisposeListener() {
				@Override
				public void widgetDisposed(DisposeEvent e) {
					removeHighlightingReceiver(treeViewer);
				}
			});
		}
	}

	/**
	 * Removes a manually managed TreeViewer, removing the highlighting.
	 * Only necessary for some editors that do not directly expose the TreeViewer.
	 * @param treeViewer the tree viewer
	 */
	public void removeHighlightingReceiver(TreeViewer treeViewer) {
		SelectionControllerTreeViewer.getInstance().removeTreeViewer(treeViewer);
	}
}
