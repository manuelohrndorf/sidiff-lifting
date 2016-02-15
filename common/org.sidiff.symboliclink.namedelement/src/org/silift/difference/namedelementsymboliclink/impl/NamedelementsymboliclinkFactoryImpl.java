/**
 */
package org.silift.difference.namedelementsymboliclink.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.silift.difference.namedelementsymboliclink.NamedElementSymbolicLinkObject;
import org.silift.difference.namedelementsymboliclink.NamedelementsymboliclinkFactory;
import org.silift.difference.namedelementsymboliclink.NamedelementsymboliclinkPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class NamedelementsymboliclinkFactoryImpl extends EFactoryImpl implements NamedelementsymboliclinkFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static NamedelementsymboliclinkFactory init() {
		try {
			NamedelementsymboliclinkFactory theNamedelementsymboliclinkFactory = (NamedelementsymboliclinkFactory)EPackage.Registry.INSTANCE.getEFactory(NamedelementsymboliclinkPackage.eNS_URI);
			if (theNamedelementsymboliclinkFactory != null) {
				return theNamedelementsymboliclinkFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new NamedelementsymboliclinkFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NamedelementsymboliclinkFactoryImpl() {
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
			case NamedelementsymboliclinkPackage.NAMED_ELEMENT_SYMBOLIC_LINK_OBJECT: return createNamedElementSymbolicLinkObject();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NamedElementSymbolicLinkObject createNamedElementSymbolicLinkObject() {
		NamedElementSymbolicLinkObjectImpl namedElementSymbolicLinkObject = new NamedElementSymbolicLinkObjectImpl();
		return namedElementSymbolicLinkObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NamedelementsymboliclinkPackage getNamedelementsymboliclinkPackage() {
		return (NamedelementsymboliclinkPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static NamedelementsymboliclinkPackage getPackage() {
		return NamedelementsymboliclinkPackage.eINSTANCE;
	}

} //NamedelementsymboliclinkFactoryImpl
