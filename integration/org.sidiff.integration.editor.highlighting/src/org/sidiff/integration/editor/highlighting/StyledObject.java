package org.sidiff.integration.editor.highlighting;

import java.util.Objects;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Color;

/**
 * <p>An {@link EObject} and its highlighting style.</p>
 * <p>Per default, the object will be highlighted in cyan,
 * and it will receive focus.</p>
 * @author Robert Müller
 */
public class StyledObject {

	private EObject eObject;
	private Color color = ColorConstants.cyan;
	private boolean focus = true;

	/**
	 * <p>Creates a new styled object wrapping the given EObject.</p>
	 * <p>Per default, the object will be highlighted in cyan,
	 * and it will receive focus.</p>
	 * @param eObject the EObject
	 */
	public StyledObject(EObject eObject) {
		setEObject(eObject);
	}

	/**
	 * Returns the target object of this styled object. 
	 * @return the EObject
	 */
	public EObject getEObject() {
		return eObject;
	}

	/**
	 * Sets the target object of this styled object.
	 * @param eObject the new EObject
	 * @return this styled object for method chaining
	 */
	public StyledObject setEObject(EObject eObject) {
		this.eObject = Objects.requireNonNull(eObject, "the object must not be null");
		return this;
	}

	/**
	 * Returns this styled object's color.
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Sets the color of this styled object.
	 * It is the callers responsibility to dispose the color.
	 * @param color the new color, see also {@link ColorConstants}
	 * @return this styled object for method chaining
	 */
	public StyledObject setColor(Color color) {
		this.color = Objects.requireNonNull(color, "the color must not be null");
		return this;
	}

	/**
	 * Returns whether this objects should receive focus.
	 * @return <code>true</code> to focus, else <code>false</code>
	 */
	public boolean isFocus() {
		return focus;
	}

	/**
	 * Sets whether this object should receive focus.
	 * @param focus <code>true</code> to focus, <code>false</code> to only highlight
	 * @return this styled object for method chaining
	 */
	public StyledObject setFocus(boolean focus) {
		this.focus = focus;
		return this;
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