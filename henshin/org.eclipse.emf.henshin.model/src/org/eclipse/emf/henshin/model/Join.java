/**
 */
package org.eclipse.emf.henshin.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Join</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.Join#getRule <em>Rule</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.Join#getJoinFrom <em>Join From</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.Join#getJoinInto <em>Join Into</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.henshin.model.HenshinPackage#getJoin()
 * @model
 * @generated
 */
public interface Join extends EObject {

	/**
	 * Returns the value of the '<em><b>Rule</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.model.Rule#getJoins <em>Joins</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule</em>' container reference.
	 * @see #setRule(Rule)
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getJoin_Rule()
	 * @see org.eclipse.emf.henshin.model.Rule#getJoins
	 * @model opposite="joins" required="true" transient="false"
	 * @generated
	 */
	Rule getRule();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.model.Join#getRule <em>Rule</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule</em>' container reference.
	 * @see #getRule()
	 * @generated
	 */
	void setRule(Rule value);

	/**
	 * Returns the value of the '<em><b>Join From</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.Node}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.model.Node#getSrcForJoin <em>Src For Join</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Join From</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Join From</em>' reference list.
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getJoin_JoinFrom()
	 * @see org.eclipse.emf.henshin.model.Node#getSrcForJoin
	 * @model opposite="srcForJoin" lower="2"
	 * @generated
	 */
	EList<Node> getJoinFrom();

	/**
	 * Returns the value of the '<em><b>Join Into</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.Node}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.model.Node#getTgtForJoin <em>Tgt For Join</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Join Into</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Join Into</em>' reference list.
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getJoin_JoinInto()
	 * @see org.eclipse.emf.henshin.model.Node#getTgtForJoin
	 * @model opposite="tgtForJoin" required="true"
	 * @generated
	 */
	EList<Node> getJoinInto();
} // Join
