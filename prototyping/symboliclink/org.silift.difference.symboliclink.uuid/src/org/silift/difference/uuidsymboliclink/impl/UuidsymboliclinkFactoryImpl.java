/**
 */
package org.silift.difference.uuidsymboliclink.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.silift.difference.uuidsymboliclink.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class UuidsymboliclinkFactoryImpl extends EFactoryImpl implements UuidsymboliclinkFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static UuidsymboliclinkFactory init() {
		try {
			UuidsymboliclinkFactory theUuidsymboliclinkFactory = (UuidsymboliclinkFactory)EPackage.Registry.INSTANCE.getEFactory(UuidsymboliclinkPackage.eNS_URI);
			if (theUuidsymboliclinkFactory != null) {
				return theUuidsymboliclinkFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new UuidsymboliclinkFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UuidsymboliclinkFactoryImpl() {
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
			case UuidsymboliclinkPackage.UUID_SYMBOLIC_LINK: return createUUIDSymbolicLink();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UUIDSymbolicLink createUUIDSymbolicLink() {
		UUIDSymbolicLinkImpl uuidSymbolicLink = new UUIDSymbolicLinkImpl();
		return uuidSymbolicLink;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UuidsymboliclinkPackage getUuidsymboliclinkPackage() {
		return (UuidsymboliclinkPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static UuidsymboliclinkPackage getPackage() {
		return UuidsymboliclinkPackage.eINSTANCE;
	}

} //UuidsymboliclinkFactoryImpl
