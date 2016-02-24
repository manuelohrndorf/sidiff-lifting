/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.javaast.model;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>JStructural Code Fragment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.sidiff.javaast.model.JStructuralCodeFragment#getFragmentType <em>Fragment Type</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.JStructuralCodeFragment#getCodeBlocks <em>Code Blocks</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.sidiff.javaast.model.JavaModelPackage#getJStructuralCodeFragment()
 * @model
 * @generated
 */
public interface JStructuralCodeFragment extends JCodeFragment
{
  /**
	 * Returns the value of the '<em><b>Fragment Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.sidiff.javaast.model.StructuralFragmentType}.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Fragment Type</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Fragment Type</em>' attribute.
	 * @see org.sidiff.javaast.model.StructuralFragmentType
	 * @see #setFragmentType(StructuralFragmentType)
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJStructuralCodeFragment_FragmentType()
	 * @model required="true" ordered="false"
	 * @generated
	 */
  StructuralFragmentType getFragmentType();

  /**
	 * Sets the value of the '{@link org.sidiff.javaast.model.JStructuralCodeFragment#getFragmentType <em>Fragment Type</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fragment Type</em>' attribute.
	 * @see org.sidiff.javaast.model.StructuralFragmentType
	 * @see #getFragmentType()
	 * @generated
	 */
  void setFragmentType(StructuralFragmentType value);

  /**
	 * Returns the value of the '<em><b>Code Blocks</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.javaast.model.JCodeBlock}.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Code Blocks</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Code Blocks</em>' containment reference list.
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJStructuralCodeFragment_CodeBlocks()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
  EList<JCodeBlock> getCodeBlocks();

} // JStructuralCodeFragment
