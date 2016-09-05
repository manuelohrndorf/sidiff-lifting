/**
 */
package org.sidiff.slicing.configuration.impl;

import java.lang.reflect.InvocationTargetException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.Query;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.ecore.OCL;
import org.eclipse.ocl.helper.OCLHelper;
import org.sidiff.slicing.configuration.ConfigurationPackage;
import org.sidiff.slicing.configuration.Constraint;
import org.sidiff.slicing.configuration.OCLConstraintInterpreter;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>OCL Constraint Interpreter</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.slicing.configuration.impl.OCLConstraintInterpreterImpl#getKey <em>Key</em>}</li>
 *   <li>{@link org.sidiff.slicing.configuration.impl.OCLConstraintInterpreterImpl#getOcl <em>Ocl</em>}</li>
 *   <li>{@link org.sidiff.slicing.configuration.impl.OCLConstraintInterpreterImpl#getOclHelper <em>Ocl Helper</em>}</li>
 * </ul>
 *
 * @generated
 */
public class OCLConstraintInterpreterImpl extends MinimalEObjectImpl.Container implements OCLConstraintInterpreter {
	/**
	 * The default value of the '{@link #getKey() <em>Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKey()
	 * @generated NOT
	 * @ordered
	 */
	protected static final String KEY_EDEFAULT = OCLConstraintInterpreter.class.getSimpleName();

	/**
	 * The cached value of the '{@link #getKey() <em>Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKey()
	 * @generated
	 * @ordered
	 */
	protected String key = KEY_EDEFAULT;

	/**
	 * The default value of the '{@link #getOcl() <em>Ocl</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOcl()
	 * @generated
	 * @ordered
	 */
	protected static final OCL OCL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOcl() <em>Ocl</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOcl()
	 * @generated
	 * @ordered
	 */
	protected OCL ocl = OCL_EDEFAULT;

	/**
	 * The default value of the '{@link #getOclHelper() <em>Ocl Helper</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOclHelper()
	 * @generated NOT
	 * @ordered
	 */
	protected static final OCLHelper<EClassifier, EOperation, EStructuralFeature, org.eclipse.ocl.ecore.Constraint> OCL_HELPER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOclHelper() <em>Ocl Helper</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOclHelper()
	 * @generated NOT
	 * @ordered
	 */
	protected OCLHelper<EClassifier, EOperation, EStructuralFeature, org.eclipse.ocl.ecore.Constraint> oclHelper = OCL_HELPER_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected OCLConstraintInterpreterImpl() {
		super();
		this.ocl = OCL.newInstance(EcoreEnvironmentFactory.INSTANCE);
		this.oclHelper = ocl.createOCLHelper();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ConfigurationPackage.Literals.OCL_CONSTRAINT_INTERPRETER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getKey() {
		return key;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OCL getOcl() {
		return ocl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public OCLHelper<EClassifier, EOperation, EStructuralFeature, org.eclipse.ocl.ecore.Constraint> getOclHelper() {
		return oclHelper;
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @throws ParserException 
	 * @generated NOT
	 */
	public boolean evaluate(Constraint constraint, EObject context) throws ParserException {
		this.oclHelper.setContext(context.eClass());
		org.eclipse.ocl.ecore.Constraint oclConstraint = oclHelper.createInvariant(constraint.getExpression());
		Query<EClassifier, EClass, EObject> query = ocl.createQuery(oclConstraint);
		return query.check(context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @throws ParserException 
	 * @generated NOT
	 */
	public boolean evaluate(EList<Constraint> constraints, EObject context) throws ParserException {

		for(Constraint constraint: constraints){
			if(!evaluate(constraint, context)){
				return false;
			}
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ConfigurationPackage.OCL_CONSTRAINT_INTERPRETER__KEY:
				return getKey();
			case ConfigurationPackage.OCL_CONSTRAINT_INTERPRETER__OCL:
				return getOcl();
			case ConfigurationPackage.OCL_CONSTRAINT_INTERPRETER__OCL_HELPER:
				return getOclHelper();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ConfigurationPackage.OCL_CONSTRAINT_INTERPRETER__KEY:
				return KEY_EDEFAULT == null ? key != null : !KEY_EDEFAULT.equals(key);
			case ConfigurationPackage.OCL_CONSTRAINT_INTERPRETER__OCL:
				return OCL_EDEFAULT == null ? ocl != null : !OCL_EDEFAULT.equals(ocl);
			case ConfigurationPackage.OCL_CONSTRAINT_INTERPRETER__OCL_HELPER:
				return OCL_HELPER_EDEFAULT == null ? oclHelper != null : !OCL_HELPER_EDEFAULT.equals(oclHelper);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case ConfigurationPackage.OCL_CONSTRAINT_INTERPRETER___EVALUATE__CONSTRAINT_EOBJECT:
			try {
				return evaluate((Constraint)arguments.get(0), (EObject)arguments.get(1));
			} catch (ParserException e) {
				throw new InvocationTargetException(e);
			}
		}
		return super.eInvoke(operationID, arguments);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (key: ");
		result.append(key);
		result.append(", ocl: ");
		result.append(ocl);
		result.append(", oclHelper: ");
		result.append(oclHelper);
		result.append(')');
		return result.toString();
	}

} //OCLConstraintInterpreterImpl
