/**
 */
package org.silift.difference.symboliclink.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.silift.difference.symboliclink.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SymboliclinkFactoryImpl extends EFactoryImpl implements SymboliclinkFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SymboliclinkFactory init() {
		try {
			SymboliclinkFactory theSymboliclinkFactory = (SymboliclinkFactory)EPackage.Registry.INSTANCE.getEFactory(SymboliclinkPackage.eNS_URI);
			if (theSymboliclinkFactory != null) {
				return theSymboliclinkFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new SymboliclinkFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SymboliclinkFactoryImpl() {
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
			case SymboliclinkPackage.SYMBOLIC_LINKS: return createSymbolicLinks();
			case SymboliclinkPackage.SYMBOLIC_LINK_REFERENCE: return createSymbolicLinkReference();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SymbolicLinks createSymbolicLinks() {
		SymbolicLinksImpl symbolicLinks = new SymbolicLinksImpl();
		return symbolicLinks;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SymbolicLinkReference createSymbolicLinkReference() {
		SymbolicLinkReferenceImpl symbolicLinkReference = new SymbolicLinkReferenceImpl();
		return symbolicLinkReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SymboliclinkPackage getSymboliclinkPackage() {
		return (SymboliclinkPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static SymboliclinkPackage getPackage() {
		return SymboliclinkPackage.eINSTANCE;
	}

} //SymboliclinkFactoryImpl
