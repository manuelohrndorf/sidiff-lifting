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
 * A representation of the model object '<em><b>JCode Block</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.javaast.model.JCodeBlock#getCodeFragments <em>Code Fragments</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.JCodeBlock#getSubBlocks <em>Sub Blocks</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.JCodeBlock#getDefinedVariables <em>Defined Variables</em>}</li>
 * </ul>
 *
 * @see org.sidiff.javaast.model.JavaModelPackage#getJCodeBlock()
 * @model
 * @generated
 */
public interface JCodeBlock extends JAdressableFragment, JIdentifiableElement
{
  /**
	 * Returns the value of the '<em><b>Code Fragments</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.javaast.model.JCodeFragment}.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Code Fragments</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Code Fragments</em>' containment reference list.
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJCodeBlock_CodeFragments()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
  EList<JCodeFragment> getCodeFragments();

  /**
	 * Returns the value of the '<em><b>Sub Blocks</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.javaast.model.JCodeBlock}.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Sub Blocks</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Sub Blocks</em>' containment reference list.
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJCodeBlock_SubBlocks()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
  EList<JCodeBlock> getSubBlocks();

  /**
	 * Returns the value of the '<em><b>Defined Variables</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.javaast.model.JLocalVariable}.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Defined Variables</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Defined Variables</em>' containment reference list.
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJCodeBlock_DefinedVariables()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
  EList<JLocalVariable> getDefinedVariables();

} // JCodeBlock
