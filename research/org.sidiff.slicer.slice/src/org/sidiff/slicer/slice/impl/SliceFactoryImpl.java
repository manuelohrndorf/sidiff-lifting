/**
 */
package org.sidiff.slicer.slice.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.sidiff.slicer.slice.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SliceFactoryImpl extends EFactoryImpl implements SliceFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SliceFactory init() {
		try {
			SliceFactory theSliceFactory = (SliceFactory) EPackage.Registry.INSTANCE.getEFactory(SlicePackage.eNS_URI);
			if (theSliceFactory != null) {
				return theSliceFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new SliceFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SliceFactoryImpl() {
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
		case SlicePackage.MODEL_SLICE:
			return createModelSlice();
		case SlicePackage.SLICED_ELEMENT:
			return createSlicedElement();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelSlice createModelSlice() {
		ModelSliceImpl modelSlice = new ModelSliceImpl();
		return modelSlice;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SlicedElement createSlicedElement() {
		SlicedElementImpl slicedElement = new SlicedElementImpl();
		return slicedElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SlicePackage getSlicePackage() {
		return (SlicePackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static SlicePackage getPackage() {
		return SlicePackage.eINSTANCE;
	}

} //SliceFactoryImpl
