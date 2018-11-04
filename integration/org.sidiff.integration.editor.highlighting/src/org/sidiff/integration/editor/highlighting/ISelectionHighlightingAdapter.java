package org.sidiff.integration.editor.highlighting;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

/**
 * A highlighting adapters that convert a selection to highlightable model elements.
 * 
 * @author Manuel Ohrndorf, Robert M�ller
 */
public interface ISelectionHighlightingAdapter {

	public static final String EXTENSION_POINT_ID = "org.sidiff.integration.editor.highlighting.adapter.selection";
	public static final String ATTRIBUTE_CLASS = "class";

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
	@SuppressWarnings("unchecked")
	public static Stream<StyledObject> getDefaultStyle(Iterator<? extends EObject> modelElements) {
		Iterable<EObject> modelElementsIterable =  () -> ((Iterator<EObject>) modelElements);
		return StreamSupport.stream(modelElementsIterable.spliterator(), false).map(StyledObject::new);
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
	public static List<?> getAllElement(ISelection selection) {
		if (selection instanceof IStructuredSelection && !selection.isEmpty()) {
			return ((IStructuredSelection)selection).toList();
		}
		return null;
	}
}
