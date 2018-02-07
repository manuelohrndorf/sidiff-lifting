/**
 */
package org.sidiff.slicer.structural.configuration;

import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.sidiff.slicer.ISlicingConfiguration;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Slicing Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.slicer.structural.configuration.SlicingConfiguration#getName <em>Name</em>}</li>
 *   <li>{@link org.sidiff.slicer.structural.configuration.SlicingConfiguration#getDescription <em>Description</em>}</li>
 *   <li>{@link org.sidiff.slicer.structural.configuration.SlicingConfiguration#getDocumentTypes <em>Document Types</em>}</li>
 *   <li>{@link org.sidiff.slicer.structural.configuration.SlicingConfiguration#getImports <em>Imports</em>}</li>
 *   <li>{@link org.sidiff.slicer.structural.configuration.SlicingConfiguration#getSlicingMode <em>Slicing Mode</em>}</li>
 *   <li>{@link org.sidiff.slicer.structural.configuration.SlicingConfiguration#getSlicedEClasses <em>Sliced EClasses</em>}</li>
 *   <li>{@link org.sidiff.slicer.structural.configuration.SlicingConfiguration#getOppositeSlicedEClassType <em>Opposite Sliced EClass Type</em>}</li>
 *   <li>{@link org.sidiff.slicer.structural.configuration.SlicingConfiguration#isCheckMultiplicity <em>Check Multiplicity</em>}</li>
 *   <li>{@link org.sidiff.slicer.structural.configuration.SlicingConfiguration#getConstraintInterpreterID <em>Constraint Interpreter ID</em>}</li>
 *   <li>{@link org.sidiff.slicer.structural.configuration.SlicingConfiguration#getConstraintInterpreter <em>Constraint Interpreter</em>}</li>
 * </ul>
 *
 * @see org.sidiff.slicer.structural.configuration.ConfigurationPackage#getSlicingConfiguration()
 * @model
 * @generated NOT
 */
public interface SlicingConfiguration extends EObject, ISlicingConfiguration {
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
	 * @see org.sidiff.slicer.structural.configuration.ConfigurationPackage#getSlicingConfiguration_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.sidiff.slicer.structural.configuration.SlicingConfiguration#getName <em>Name</em>}' attribute.
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
	 * @see org.sidiff.slicer.structural.configuration.ConfigurationPackage#getSlicingConfiguration_Description()
	 * @model
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link org.sidiff.slicer.structural.configuration.SlicingConfiguration#getDescription <em>Description</em>}' attribute.
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
	 * @see org.sidiff.slicer.structural.configuration.ConfigurationPackage#getSlicingConfiguration_DocumentTypes()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
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
	 * @see org.sidiff.slicer.structural.configuration.ConfigurationPackage#getSlicingConfiguration_Imports()
	 * @model
	 * @generated
	 */
	EList<EPackage> getImports();

	/**
	 * Returns the value of the '<em><b>Slicing Mode</b></em>' attribute.
	 * The default value is <code>"FORWARD"</code>.
	 * The literals are from the enumeration {@link org.sidiff.slicer.structural.configuration.SlicingMode}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Slicing Mode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Slicing Mode</em>' attribute.
	 * @see org.sidiff.slicer.structural.configuration.SlicingMode
	 * @see #setSlicingMode(SlicingMode)
	 * @see org.sidiff.slicer.structural.configuration.ConfigurationPackage#getSlicingConfiguration_SlicingMode()
	 * @model default="FORWARD" required="true"
	 * @generated
	 */
	SlicingMode getSlicingMode();

	/**
	 * Sets the value of the '{@link org.sidiff.slicer.structural.configuration.SlicingConfiguration#getSlicingMode <em>Slicing Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Slicing Mode</em>' attribute.
	 * @see org.sidiff.slicer.structural.configuration.SlicingMode
	 * @see #getSlicingMode()
	 * @generated
	 */
	void setSlicingMode(SlicingMode value);

	/**
	 * Returns the value of the '<em><b>Sliced EClasses</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.slicer.structural.configuration.SlicedEClass}.
	 * It is bidirectional and its opposite is '{@link org.sidiff.slicer.structural.configuration.SlicedEClass#getSlicingConfiguration <em>Slicing Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sliced EClasses</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sliced EClasses</em>' containment reference list.
	 * @see org.sidiff.slicer.structural.configuration.ConfigurationPackage#getSlicingConfiguration_SlicedEClasses()
	 * @see org.sidiff.slicer.structural.configuration.SlicedEClass#getSlicingConfiguration
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
	 * @see org.sidiff.slicer.structural.configuration.ConfigurationPackage#getSlicingConfiguration_OppositeSlicedEClassType()
	 * @model transient="true" changeable="false" derived="true"
	 * @generated
	 */
	Map<EClass, SlicedEClass> getOppositeSlicedEClassType();

	/**
	 * Returns the value of the '<em><b>Check Multiplicity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Check Multiplicity</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Check Multiplicity</em>' attribute.
	 * @see #setCheckMultiplicity(boolean)
	 * @see org.sidiff.slicer.structural.configuration.ConfigurationPackage#getSlicingConfiguration_CheckMultiplicity()
	 * @model required="true"
	 * @generated
	 */
	boolean isCheckMultiplicity();

	/**
	 * Sets the value of the '{@link org.sidiff.slicer.structural.configuration.SlicingConfiguration#isCheckMultiplicity <em>Check Multiplicity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Check Multiplicity</em>' attribute.
	 * @see #isCheckMultiplicity()
	 * @generated
	 */
	void setCheckMultiplicity(boolean value);

	/**
	 * Returns the value of the '<em><b>Constraint Interpreter ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Constraint Interpreter ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constraint Interpreter ID</em>' attribute.
	 * @see #setConstraintInterpreterID(String)
	 * @see org.sidiff.slicer.structural.configuration.ConfigurationPackage#getSlicingConfiguration_ConstraintInterpreterID()
	 * @model
	 * @generated
	 */
	String getConstraintInterpreterID();

	/**
	 * Sets the value of the '{@link org.sidiff.slicer.structural.configuration.SlicingConfiguration#getConstraintInterpreterID <em>Constraint Interpreter ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Constraint Interpreter ID</em>' attribute.
	 * @see #getConstraintInterpreterID()
	 * @generated
	 */
	void setConstraintInterpreterID(String value);

	/**
	 * Returns the value of the '<em><b>Constraint Interpreter</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Constraint Interpreter</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constraint Interpreter</em>' attribute.
	 * @see org.sidiff.slicer.structural.configuration.ConfigurationPackage#getSlicingConfiguration_ConstraintInterpreter()
	 * @model dataType="org.sidiff.slicer.structural.configuration.IConstraintInterpreter" transient="true" changeable="false" derived="true"
	 * @generated
	 */
	IConstraintInterpreter getConstraintInterpreter();

} // SlicingConfiguration
