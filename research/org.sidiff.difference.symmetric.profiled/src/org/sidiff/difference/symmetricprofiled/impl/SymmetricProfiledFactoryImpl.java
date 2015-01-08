/**
 */
package org.sidiff.difference.symmetricprofiled.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.sidiff.difference.symmetricprofiled.AppliedStereotype;
import org.sidiff.difference.symmetricprofiled.ProfiledSCS;
import org.sidiff.difference.symmetricprofiled.ProfiledSD;
import org.sidiff.difference.symmetricprofiled.SymmetricProfiledFactory;
import org.sidiff.difference.symmetricprofiled.SymmetricProfiledPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SymmetricProfiledFactoryImpl extends EFactoryImpl implements SymmetricProfiledFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SymmetricProfiledFactory init() {
		try {
			SymmetricProfiledFactory theSymmetricProfiledFactory = (SymmetricProfiledFactory)EPackage.Registry.INSTANCE.getEFactory(SymmetricProfiledPackage.eNS_URI);
			if (theSymmetricProfiledFactory != null) {
				return theSymmetricProfiledFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new SymmetricProfiledFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SymmetricProfiledFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case SymmetricProfiledPackage.PROFILED_SD: return createProfiledSD();
			case SymmetricProfiledPackage.PROFILED_SCS: return createProfiledSCS();
			case SymmetricProfiledPackage.APPLIED_STEREOTYPE: return createAppliedStereotype();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProfiledSD createProfiledSD() {
		ProfiledSDImpl profiledSD = new ProfiledSDImpl();
		return profiledSD;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProfiledSCS createProfiledSCS() {
		ProfiledSCSImpl profiledSCS = new ProfiledSCSImpl();
		return profiledSCS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AppliedStereotype createAppliedStereotype() {
		AppliedStereotypeImpl appliedStereotype = new AppliedStereotypeImpl();
		return appliedStereotype;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SymmetricProfiledPackage getSymmetricProfiledPackage() {
		return (SymmetricProfiledPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static SymmetricProfiledPackage getPackage() {
		return SymmetricProfiledPackage.eINSTANCE;
	}

} //SymmetricProfiledFactoryImpl
