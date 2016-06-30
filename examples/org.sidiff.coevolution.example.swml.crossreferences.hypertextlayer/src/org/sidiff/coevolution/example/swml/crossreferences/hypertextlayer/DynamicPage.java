/**
 */
package org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer;

import org.sidiff.coevolution.example.swml.crossreferences.datalayer.Entity;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Dynamic Page</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.DynamicPage#getShows <em>Shows</em>}</li>
 * </ul>
 *
 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.HypertextlayerPackage#getDynamicPage()
 * @model abstract="true"
 * @generated
 */
public interface DynamicPage extends Page {
	/**
	 * Returns the value of the '<em><b>Shows</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Shows</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Shows</em>' reference.
	 * @see #setShows(Entity)
	 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.HypertextlayerPackage#getDynamicPage_Shows()
	 * @model
	 * @generated
	 */
	Entity getShows();

	/**
	 * Sets the value of the '{@link org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.DynamicPage#getShows <em>Shows</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Shows</em>' reference.
	 * @see #getShows()
	 * @generated
	 */
	void setShows(Entity value);

} // DynamicPage
