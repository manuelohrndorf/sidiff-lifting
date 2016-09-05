/**
 */
package org.sidiff.slicing.configuration;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.ocl.ecore.OCL;

import org.eclipse.ocl.helper.OCLHelper;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>OCL Constraint Interpreter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.slicing.configuration.OCLConstraintInterpreter#getOcl <em>Ocl</em>}</li>
 *   <li>{@link org.sidiff.slicing.configuration.OCLConstraintInterpreter#getOclHelper <em>Ocl Helper</em>}</li>
 * </ul>
 *
 * @see org.sidiff.slicing.configuration.ConfigurationPackage#getOCLConstraintInterpreter()
 * @model
 * @generated
 */
public interface OCLConstraintInterpreter extends IConstraintInterpreter {
	/**
	 * Returns the value of the '<em><b>Ocl</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ocl</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ocl</em>' attribute.
	 * @see org.sidiff.slicing.configuration.ConfigurationPackage#getOCLConstraintInterpreter_Ocl()
	 * @model dataType="org.sidiff.slicing.configuration.EOCL" required="true" transient="true" changeable="false"
	 * @generated
	 */
	OCL getOcl();

	/**
	 * Returns the value of the '<em><b>Ocl Helper</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ocl Helper</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ocl Helper</em>' attribute.
	 * @see org.sidiff.slicing.configuration.ConfigurationPackage#getOCLConstraintInterpreter_OclHelper()
	 * @model dataType="org.sidiff.slicing.configuration.EOCLHelper" required="true" transient="true" changeable="false"
	 * @generated NOT
	 */
	OCLHelper<EClassifier, EOperation, EStructuralFeature, org.eclipse.ocl.ecore.Constraint> getOclHelper();

} // OCLConstraintInterpreter
