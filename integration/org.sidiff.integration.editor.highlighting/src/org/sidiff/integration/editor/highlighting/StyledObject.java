package org.sidiff.integration.editor.highlighting;

import java.util.Objects;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Color;

/**
 * An object and its highlighting style.
 * @author Robert Müller
 */
public class StyledObject {
	private EObject eObject;
	private Color color;
	private boolean focus;

	/**
	 * Convenience constructor for <code>StyledObject(eObject, ColorConstants.cyan, true)</code>.
	 * @param eObject the EObject
	 */
	public StyledObject(EObject eObject) {
		this(eObject, ColorConstants.cyan, true);
	}

	/**
	 * Creates a new styled object. The color must be disposed by the caller.
	 * @param eObject the EObject
	 * @param color the SWT color, see also {@link ColorConstants}
	 * @param focus whether to focus the object
	 */
	public StyledObject(EObject eObject, Color color) {
		this(eObject, color, true);
	}

	/**
	 * Creates a new styled object. The color must be disposed by the caller.
	 * @param eObject the EObject
	 * @param color the SWT color, see also {@link ColorConstants}
	 * @param focus whether to focus the object
	 */
	public StyledObject(EObject eObject, Color color, boolean focus) {
		this.eObject = Objects.requireNonNull(eObject);
		this.color = Objects.requireNonNull(color);
		this.focus = focus;
	}
	
	public EObject getEObject() {
		return eObject;
	}
	
	public void setEObject(EObject eObject) {
		this.eObject = eObject;
	}

	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public boolean isFocus() {
		return focus;
	}
	
	public void setFocus(boolean focus) {
		this.focus = focus;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this)
			return true;
		if(!(obj instanceof StyledObject))
			return false;
		return Objects.equals(this.getEObject(), ((StyledObject)obj).getEObject());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(eObject);
	}
}