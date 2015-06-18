/**
 */
package org.eclipse.emf.henshin.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Split</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.Split#getRule <em>Rule</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.Split#getSplitFrom <em>Split From</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.Split#getSplitInto <em>Split Into</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.henshin.model.HenshinPackage#getSplit()
 * @model
 * @generated
 */
public interface Split extends EObject {

	/**
	 * Returns the value of the '<em><b>Rule</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.model.Rule#getSplits <em>Splits</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule</em>' container reference.
	 * @see #setRule(Rule)
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getSplit_Rule()
	 * @see org.eclipse.emf.henshin.model.Rule#getSplits
	 * @model opposite="splits" required="true" transient="false"
	 * @generated
	 */
	Rule getRule();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.model.Split#getRule <em>Rule</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule</em>' container reference.
	 * @see #getRule()
	 * @generated
	 */
	void setRule(Rule value);

	/**
	 * Returns the value of the '<em><b>Split From</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.Node}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.model.Node#getSrcOfSplit <em>Src Of Split</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Split From</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Split From</em>' reference list.
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getSplit_SplitFrom()
	 * @see org.eclipse.emf.henshin.model.Node#getSrcOfSplit
	 * @model opposite="srcOfSplit" required="true"
	 * @generated
	 */
	EList<Node> getSplitFrom();

	/**
	 * Returns the value of the '<em><b>Split Into</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.Node}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.model.Node#getTgtOfSplit <em>Tgt Of Split</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Split Into</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Split Into</em>' reference list.
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getSplit_SplitInto()
	 * @see org.eclipse.emf.henshin.model.Node#getTgtOfSplit
	 * @model opposite="tgtOfSplit" lower="2"
	 * @generated
	 */
	EList<Node> getSplitInto();
} // Split
