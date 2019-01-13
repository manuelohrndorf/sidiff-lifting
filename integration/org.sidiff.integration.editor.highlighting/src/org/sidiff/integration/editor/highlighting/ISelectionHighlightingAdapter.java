package org.sidiff.integration.editor.highlighting;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.sidiff.common.collections.CollectionUtil;
import org.sidiff.common.extension.ExtensionManager;
import org.sidiff.common.extension.IExtension;

/**
 * A highlighting adapters that convert a selection to highlightable model elements.
 * 
 * @author Manuel Ohrndorf
 * @author Robert Müller
 */
public interface ISelectionHighlightingAdapter extends IExtension {

	Description<ISelectionHighlightingAdapter> DESCRIPTION = Description.of(ISelectionHighlightingAdapter.class,
			"org.sidiff.integration.editor.highlighting.adapter.selection", "selectionHighlightingAdapter", "class");

	ExtensionManager<ISelectionHighlightingAdapter> MANAGER = new ExtensionManager<>(DESCRIPTION);


	/**
	 * Returns a stream of styled objects.
	 * @param selection The actual selection to highlight.
	 * @return A stream of {@link StyledObject}s, or {@link Stream#empty()}.
	 */
	Stream<StyledObject> getElements(ISelection selection);


	/**
	 * Convenient function to convert a model element iterator into styled object.  
	 * 
	 * @param modelElements
	 *            Iterates over all model elements to be highlighted.
	 * @return The model elements with default highlighting.
	 */
	public static Stream<StyledObject> getDefaultStyle(Iterator<? extends EObject> modelElements) {
		return CollectionUtil.asStream(modelElements).map(StyledObject::new);
	}

	/**
	 * Convenience function to convert a selection.
	 * 
	 * @param selection A (structured) selection.
	 * @return The first selected element.
	 */
	public static Object getFirstElement(ISelection selection) {
		if (selection instanceof IStructuredSelection && !selection.isEmpty()) {
			return ((IStructuredSelection) selection).getFirstElement();
		}
		return null;
	}
	
	/**
	 * Convenience function to convert a selection.
	 * 
	 * @param selection A (structured) selection.
	 * @return The selected elements.
	 */
	public static List<?> getAllElements(ISelection selection) {
		if (selection instanceof IStructuredSelection && !selection.isEmpty()) {
			return ((IStructuredSelection)selection).toList();
		}
		return Collections.emptyList();
	}
}
