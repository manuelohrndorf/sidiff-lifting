package org.sidiff.slicer.structural.configuration.logic;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

/**
 * Interface of the slicing logic. Functions will be called by the configuration editor on user interactions and to format the layout.
 * @author rmueller
 *
 */
public interface ISlicingLogic
{
	/**
	 * Enumeration of all checkbox states.
	 * @author rmueller
	 *
	 */
	public enum CheckboxState
	{
		/**
		 * The checkbox is empty
		 */
		NOT_CHECKED,
		
		/**
		 * The checkbox contains a tick mark
		 */
		CHECKED,
		
		/**
		 * The checkbox contains a square
		 */
		GRAYED
	}

	/**
	 * Called when the checked-state changes for an element.
	 * @param element the element whose checked-state changed
	 * @param checked the new checked-state
	 * @return all elements that changed due to the changed checked state, may be empty, may be <code>null</code> to update all items
	 */
	Set<EObject> checkStateChanged(EObject element, boolean checked);

	/**
	 * This function should return the checkbox state of the given element.
	 * The return value should be one of these:
	 * <ul>
	 * <li>{@link CheckboxState#NOT_CHECKED}</li>
	 * <li>{@link CheckboxState#CHECKED}</li>
	 * <li>{@link CheckboxState#GRAYED}</li>
	 * </ul>
	 * @param element the element
	 * @return checkbox state of the element
	 */
	CheckboxState getCheckboxState(Object element);

	/**
	 * This function should return the foreground color of the specified object.
	 * The default color is <code>null</code>.
	 * @param object the object
	 * @return foreground color of the object
	 */
	Color getForegroundColor(Object object);

	/**
	 * This function should return the background color of the specified object.
	 * The default color is <code>null</code>.
	 * @param object the object
	 * @return background color of the object
	 */
	Color getBackgroundColor(Object object);

	/**
	 * This function should return the font style of the specified object.<br>
	 * The return value must be one of the following:
	 * <ul>
	 * <li>{@link SWT#NORMAL}</li>
	 * <li>{@link SWT#BOLD}</li>
	 * <li>{@link SWT#ITALIC}</li>
	 * <li>{@link SWT#BOLD} | {@link SWT#ITALIC}</li>
	 * </ul>
	 * The default style is {@link SWT#NORMAL}.
	 * @param object the object
	 * @return font style of the object, must be one of the values above
	 */
	int getFontStyle(Object object);

	/**
	 * This function should return an image descriptor based on the given image with
	 * additional decoration for the given object. The function may return <code>null</code>
	 * to use the base image without decoration.
	 * @param baseImage base image of the element
	 * @param object the element
	 * @return decorated image descriptor, <code>null</code> to show no decoration
	 */
	ImageDescriptor getDecoratedImage(Image baseImage, Object object);

	/**
	 * This function should return the given text decorated with additional information
	 * for the given object. the function may return <code>null</code> to show the
	 * original text.
	 * @param originalText the original text
	 * @param object the element
	 * @return the text with additional decoration
	 */
	StyledString getDecoratedText(StyledString originalText, Object object);

	/**
	 * This function should return a tooltip for the given object. The tooltip
	 * will be shown when the cursor is hovering over an element.
	 * The function may return <code>null</code> to show no tooltip.
	 * @param object the element
	 * @return tooltip text for the given element, <code>null</code> if none
	 */
	String getToolTipText(Object object);

	/**
	 * This function should return all elements that can be included in the slicing
	 * configuration alternative to the given object. The result should <u>not</u>
	 * contain the object itself. The function may return <code>null</code> if no
	 * alternatives are available.
	 * @param object the element
	 * @return set of alternatives, <code>null</code> if non are available
	 */
	Set<EObject> getAlternativeElements(EObject object);

	/**
	 * Called when the slicing logic is no longer used and should free all
	 * created resources.
	 */
	void dispose();
}
