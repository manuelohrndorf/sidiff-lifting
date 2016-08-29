/**
 */
package simpleWebModel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Data Layer</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link simpleWebModel.DataLayer#getEntities <em>Entities</em>}</li>
 * </ul>
 *
 * @see simpleWebModel.SimpleWebModelPackage#getDataLayer()
 * @model
 * @generated
 */
public interface DataLayer extends EObject {
	/**
	 * Returns the value of the '<em><b>Entities</b></em>' containment reference list.
	 * The list contents are of type {@link simpleWebModel.Entity}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entities</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entities</em>' containment reference list.
	 * @see simpleWebModel.SimpleWebModelPackage#getDataLayer_Entities()
	 * @model containment="true"
	 * @generated
	 */
	EList<Entity> getEntities();

} // DataLayer
