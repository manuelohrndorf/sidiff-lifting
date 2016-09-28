/**
 */
package org.sidiff.slicing.configuration;

import java.util.Map;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Slicing Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.slicing.configuration.SlicingConfiguration#getName <em>Name</em>}</li>
 *   <li>{@link org.sidiff.slicing.configuration.SlicingConfiguration#getDescription <em>Description</em>}</li>
 *   <li>{@link org.sidiff.slicing.configuration.SlicingConfiguration#getDocumentTypes <em>Document Types</em>}</li>
 *   <li>{@link org.sidiff.slicing.configuration.SlicingConfiguration#getImports <em>Imports</em>}</li>
 *   <li>{@link org.sidiff.slicing.configuration.SlicingConfiguration#getSlicingMode <em>Slicing Mode</em>}</li>
 *   <li>{@link org.sidiff.slicing.configuration.SlicingConfiguration#isSliceBoundaryContainments <em>Slice Boundary Containments</em>}</li>
 *   <li>{@link org.sidiff.slicing.configuration.SlicingConfiguration#getSlicedEClasses <em>Sliced EClasses</em>}</li>
 *   <li>{@link org.sidiff.slicing.configuration.SlicingConfiguration#getOppositeSlicedEClassType <em>Opposite Sliced EClass Type</em>}</li>
 *   <li>{@link org.sidiff.slicing.configuration.SlicingConfiguration#getConstraints <em>Constraints</em>}</li>
 *   <li>{@link org.sidiff.slicing.configuration.SlicingConfiguration#getConstraintinterpreter <em>Constraintinterpreter</em>}</li>
 * </ul>
 *
 * @see org.sidiff.slicing.configuration.ConfigurationPackage#getSlicingConfiguration()
 * @model
 * @generated
 */
public interface SlicingConfiguration extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.sidiff.slicing.configuration.ConfigurationPackage#getSlicingConfiguration_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.sidiff.slicing.configuration.SlicingConfiguration#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see org.sidiff.slicing.configuration.ConfigurationPackage#getSlicingConfiguration_Description()
	 * @model
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link org.sidiff.slicing.configuration.SlicingConfiguration#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Document Types</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Document Types</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Document Types</em>' attribute list.
	 * @see org.sidiff.slicing.configuration.ConfigurationPackage#getSlicingConfiguration_DocumentTypes()
	 * @model
	 * @generated
	 */
	EList<String> getDocumentTypes();

	/**
	 * Returns the value of the '<em><b>Imports</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EPackage}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Imports</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Imports</em>' reference list.
	 * @see org.sidiff.slicing.configuration.ConfigurationPackage#getSlicingConfiguration_Imports()
	 * @model transient="true" volatile="true" derived="true"
	 * @generated
	 */
	EList<EPackage> getImports();

	/**
	 * Returns the value of the '<em><b>Slicing Mode</b></em>' attribute.
	 * The default value is <code>"OPTIMISTIC"</code>.
	 * The literals are from the enumeration {@link org.sidiff.slicing.configuration.SlicingMode}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Slicing Mode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Slicing Mode</em>' attribute.
	 * @see org.sidiff.slicing.configuration.SlicingMode
	 * @see #setSlicingMode(SlicingMode)
	 * @see org.sidiff.slicing.configuration.ConfigurationPackage#getSlicingConfiguration_SlicingMode()
	 * @model default="OPTIMISTIC" required="true"
	 * @generated
	 */
	SlicingMode getSlicingMode();

	/**
	 * Sets the value of the '{@link org.sidiff.slicing.configuration.SlicingConfiguration#getSlicingMode <em>Slicing Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Slicing Mode</em>' attribute.
	 * @see org.sidiff.slicing.configuration.SlicingMode
	 * @see #getSlicingMode()
	 * @generated
	 */
	void setSlicingMode(SlicingMode value);

	/**
	 * Returns the value of the '<em><b>Slice Boundary Containments</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Slice Boundary Containments</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Slice Boundary Containments</em>' attribute.
	 * @see #setSliceBoundaryContainments(boolean)
	 * @see org.sidiff.slicing.configuration.ConfigurationPackage#getSlicingConfiguration_SliceBoundaryContainments()
	 * @model default="false" required="true"
	 * @generated
	 */
	boolean isSliceBoundaryContainments();

	/**
	 * Sets the value of the '{@link org.sidiff.slicing.configuration.SlicingConfiguration#isSliceBoundaryContainments <em>Slice Boundary Containments</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Slice Boundary Containments</em>' attribute.
	 * @see #isSliceBoundaryContainments()
	 * @generated
	 */
	void setSliceBoundaryContainments(boolean value);

	/**
	 * Returns the value of the '<em><b>Sliced EClasses</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.slicing.configuration.SlicedEClass}.
	 * It is bidirectional and its opposite is '{@link org.sidiff.slicing.configuration.SlicedEClass#getSlicingConfiguration <em>Slicing Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sliced EClasses</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sliced EClasses</em>' containment reference list.
	 * @see org.sidiff.slicing.configuration.ConfigurationPackage#getSlicingConfiguration_SlicedEClasses()
	 * @see org.sidiff.slicing.configuration.SlicedEClass#getSlicingConfiguration
	 * @model opposite="slicingConfiguration" containment="true"
	 * @generated
	 */
	EList<SlicedEClass> getSlicedEClasses();

	/**
	 * Returns the value of the '<em><b>Opposite Sliced EClass Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Opposite Sliced EClass Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Opposite Sliced EClass Type</em>' attribute.
	 * @see org.sidiff.slicing.configuration.ConfigurationPackage#getSlicingConfiguration_OppositeSlicedEClassType()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	Map<?, ?> getOppositeSlicedEClassType();

	/**
	 * Returns the value of the '<em><b>Constraints</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.slicing.configuration.Constraint}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Constraints</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constraints</em>' containment reference list.
	 * @see org.sidiff.slicing.configuration.ConfigurationPackage#getSlicingConfiguration_Constraints()
	 * @model containment="true"
	 * @generated
	 */
	EList<Constraint> getConstraints();

	/**
	 * Returns the value of the '<em><b>Constraintinterpreter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Constraintinterpreter</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constraintinterpreter</em>' containment reference.
	 * @see #setConstraintinterpreter(IConstraintInterpreter)
	 * @see org.sidiff.slicing.configuration.ConfigurationPackage#getSlicingConfiguration_Constraintinterpreter()
	 * @model containment="true"
	 * @generated
	 */
	IConstraintInterpreter getConstraintinterpreter();

	/**
	 * Sets the value of the '{@link org.sidiff.slicing.configuration.SlicingConfiguration#getConstraintinterpreter <em>Constraintinterpreter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Constraintinterpreter</em>' containment reference.
	 * @see #getConstraintinterpreter()
	 * @generated
	 */
	void setConstraintinterpreter(IConstraintInterpreter value);

} // SlicingConfiguration
