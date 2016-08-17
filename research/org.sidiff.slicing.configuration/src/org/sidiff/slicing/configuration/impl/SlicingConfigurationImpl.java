/**
 */
package org.sidiff.slicing.configuration.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.sidiff.slicing.configuration.ConfigurationPackage;
import org.sidiff.slicing.configuration.SlicedEClass;
import org.sidiff.slicing.configuration.SlicingConfiguration;
import org.sidiff.slicing.configuration.SlicingMode;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Slicing Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.slicing.configuration.impl.SlicingConfigurationImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.sidiff.slicing.configuration.impl.SlicingConfigurationImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link org.sidiff.slicing.configuration.impl.SlicingConfigurationImpl#getDocumentType <em>Document Type</em>}</li>
 *   <li>{@link org.sidiff.slicing.configuration.impl.SlicingConfigurationImpl#getImports <em>Imports</em>}</li>
 *   <li>{@link org.sidiff.slicing.configuration.impl.SlicingConfigurationImpl#getSlicingMode <em>Slicing Mode</em>}</li>
 *   <li>{@link org.sidiff.slicing.configuration.impl.SlicingConfigurationImpl#getSlicedEClasses <em>Sliced EClasses</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SlicingConfigurationImpl extends MinimalEObjectImpl.Container implements SlicingConfiguration {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected String description = DESCRIPTION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDocumentType() <em>Document Type</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDocumentType()
	 * @generated
	 * @ordered
	 */
	protected EList<String> documentType;

	/**
	 * The default value of the '{@link #getSlicingMode() <em>Slicing Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSlicingMode()
	 * @generated
	 * @ordered
	 */
	protected static final SlicingMode SLICING_MODE_EDEFAULT = SlicingMode.OPTIMISTIC;

	/**
	 * The cached value of the '{@link #getSlicingMode() <em>Slicing Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSlicingMode()
	 * @generated
	 * @ordered
	 */
	protected SlicingMode slicingMode = SLICING_MODE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSlicedEClasses() <em>Sliced EClasses</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSlicedEClasses()
	 * @generated
	 * @ordered
	 */
	protected EList<SlicedEClass> slicedEClasses;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SlicingConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ConfigurationPackage.Literals.SLICING_CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.SLICING_CONFIGURATION__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.SLICING_CONFIGURATION__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getDocumentType() {
		if (documentType == null) {
			documentType = new EDataTypeUniqueEList<String>(String.class, this, ConfigurationPackage.SLICING_CONFIGURATION__DOCUMENT_TYPE);
		}
		return documentType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EPackage> getImports() {
		// TODO: implement this method to return the 'Imports' reference list
		// Ensure that you remove @generated or mark it @generated NOT
		// The list is expected to implement org.eclipse.emf.ecore.util.InternalEList and org.eclipse.emf.ecore.EStructuralFeature.Setting
		// so it's likely that an appropriate subclass of org.eclipse.emf.ecore.util.EcoreEList should be used.
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SlicingMode getSlicingMode() {
		return slicingMode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSlicingMode(SlicingMode newSlicingMode) {
		SlicingMode oldSlicingMode = slicingMode;
		slicingMode = newSlicingMode == null ? SLICING_MODE_EDEFAULT : newSlicingMode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.SLICING_CONFIGURATION__SLICING_MODE, oldSlicingMode, slicingMode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SlicedEClass> getSlicedEClasses() {
		if (slicedEClasses == null) {
			slicedEClasses = new EObjectContainmentWithInverseEList<SlicedEClass>(SlicedEClass.class, this, ConfigurationPackage.SLICING_CONFIGURATION__SLICED_ECLASSES, ConfigurationPackage.SLICED_ECLASS__SLICING_CONFIGURATION);
		}
		return slicedEClasses;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ConfigurationPackage.SLICING_CONFIGURATION__SLICED_ECLASSES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getSlicedEClasses()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ConfigurationPackage.SLICING_CONFIGURATION__SLICED_ECLASSES:
				return ((InternalEList<?>)getSlicedEClasses()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ConfigurationPackage.SLICING_CONFIGURATION__NAME:
				return getName();
			case ConfigurationPackage.SLICING_CONFIGURATION__DESCRIPTION:
				return getDescription();
			case ConfigurationPackage.SLICING_CONFIGURATION__DOCUMENT_TYPE:
				return getDocumentType();
			case ConfigurationPackage.SLICING_CONFIGURATION__IMPORTS:
				return getImports();
			case ConfigurationPackage.SLICING_CONFIGURATION__SLICING_MODE:
				return getSlicingMode();
			case ConfigurationPackage.SLICING_CONFIGURATION__SLICED_ECLASSES:
				return getSlicedEClasses();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ConfigurationPackage.SLICING_CONFIGURATION__NAME:
				setName((String)newValue);
				return;
			case ConfigurationPackage.SLICING_CONFIGURATION__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case ConfigurationPackage.SLICING_CONFIGURATION__DOCUMENT_TYPE:
				getDocumentType().clear();
				getDocumentType().addAll((Collection<? extends String>)newValue);
				return;
			case ConfigurationPackage.SLICING_CONFIGURATION__IMPORTS:
				getImports().clear();
				getImports().addAll((Collection<? extends EPackage>)newValue);
				return;
			case ConfigurationPackage.SLICING_CONFIGURATION__SLICING_MODE:
				setSlicingMode((SlicingMode)newValue);
				return;
			case ConfigurationPackage.SLICING_CONFIGURATION__SLICED_ECLASSES:
				getSlicedEClasses().clear();
				getSlicedEClasses().addAll((Collection<? extends SlicedEClass>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ConfigurationPackage.SLICING_CONFIGURATION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ConfigurationPackage.SLICING_CONFIGURATION__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case ConfigurationPackage.SLICING_CONFIGURATION__DOCUMENT_TYPE:
				getDocumentType().clear();
				return;
			case ConfigurationPackage.SLICING_CONFIGURATION__IMPORTS:
				getImports().clear();
				return;
			case ConfigurationPackage.SLICING_CONFIGURATION__SLICING_MODE:
				setSlicingMode(SLICING_MODE_EDEFAULT);
				return;
			case ConfigurationPackage.SLICING_CONFIGURATION__SLICED_ECLASSES:
				getSlicedEClasses().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ConfigurationPackage.SLICING_CONFIGURATION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ConfigurationPackage.SLICING_CONFIGURATION__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case ConfigurationPackage.SLICING_CONFIGURATION__DOCUMENT_TYPE:
				return documentType != null && !documentType.isEmpty();
			case ConfigurationPackage.SLICING_CONFIGURATION__IMPORTS:
				return !getImports().isEmpty();
			case ConfigurationPackage.SLICING_CONFIGURATION__SLICING_MODE:
				return slicingMode != SLICING_MODE_EDEFAULT;
			case ConfigurationPackage.SLICING_CONFIGURATION__SLICED_ECLASSES:
				return slicedEClasses != null && !slicedEClasses.isEmpty();
		}
		return super.eIsSet(featureID);
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
		result.append(" (name: ");
		result.append(name);
		result.append(", description: ");
		result.append(description);
		result.append(", documentType: ");
		result.append(documentType);
		result.append(", slicingMode: ");
		result.append(slicingMode);
		result.append(')');
		return result.toString();
	}

} //SlicingConfigurationImpl
