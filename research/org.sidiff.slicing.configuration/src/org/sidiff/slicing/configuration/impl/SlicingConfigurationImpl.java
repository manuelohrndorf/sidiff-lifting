/**
 */
package org.sidiff.slicing.configuration.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.sidiff.slicing.configuration.ConfigurationPackage;
import org.sidiff.slicing.configuration.Constraint;
import org.sidiff.slicing.configuration.IConstraintInterpreter;
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
 *   <li>{@link org.sidiff.slicing.configuration.impl.SlicingConfigurationImpl#getDocumentTypes <em>Document Types</em>}</li>
 *   <li>{@link org.sidiff.slicing.configuration.impl.SlicingConfigurationImpl#getImports <em>Imports</em>}</li>
 *   <li>{@link org.sidiff.slicing.configuration.impl.SlicingConfigurationImpl#getSlicingMode <em>Slicing Mode</em>}</li>
 *   <li>{@link org.sidiff.slicing.configuration.impl.SlicingConfigurationImpl#isSliceBoundaryContainments <em>Slice Boundary Containments</em>}</li>
 *   <li>{@link org.sidiff.slicing.configuration.impl.SlicingConfigurationImpl#getSlicedEClasses <em>Sliced EClasses</em>}</li>
 *   <li>{@link org.sidiff.slicing.configuration.impl.SlicingConfigurationImpl#getOppositeSlicedEClassType <em>Opposite Sliced EClass Type</em>}</li>
 *   <li>{@link org.sidiff.slicing.configuration.impl.SlicingConfigurationImpl#getConstraints <em>Constraints</em>}</li>
 *   <li>{@link org.sidiff.slicing.configuration.impl.SlicingConfigurationImpl#getConstraintinterpreter <em>Constraintinterpreter</em>}</li>
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
	 * The cached value of the '{@link #getDocumentTypes() <em>Document Types</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDocumentTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<String> documentTypes;

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
	 * The default value of the '{@link #isSliceBoundaryContainments() <em>Slice Boundary Containments</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSliceBoundaryContainments()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SLICE_BOUNDARY_CONTAINMENTS_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isSliceBoundaryContainments() <em>Slice Boundary Containments</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSliceBoundaryContainments()
	 * @generated
	 * @ordered
	 */
	protected boolean sliceBoundaryContainments = SLICE_BOUNDARY_CONTAINMENTS_EDEFAULT;

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
	 * The cached value of the '{@link #getConstraints() <em>Constraints</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConstraints()
	 * @generated
	 * @ordered
	 */
	protected EList<Constraint> constraints;

	/**
	 * The cached value of the '{@link #getConstraintinterpreter() <em>Constraintinterpreter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConstraintinterpreter()
	 * @generated
	 * @ordered
	 */
	protected IConstraintInterpreter constraintinterpreter;

	/**
	 * @generated NOT
	 */
	protected Map<EClass, SlicedEClass> oppositeSlicedEClassType = new HashMap<EClass, SlicedEClass>();

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
	public EList<String> getDocumentTypes() {
		if (documentTypes == null) {
			documentTypes = new EDataTypeUniqueEList<String>(String.class, this, ConfigurationPackage.SLICING_CONFIGURATION__DOCUMENT_TYPES);
		}
		return documentTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<EPackage> getImports() {
		EList<EPackage> imports = new EObjectResolvingEList<EPackage>(EPackage.class, this, ConfigurationPackage.SLICING_CONFIGURATION__IMPORTS);
		
		for(String nsURI : documentTypes){
			EPackage ePackage = EPackage.Registry.INSTANCE.getEPackage(nsURI);
			if(ePackage != null){
				imports.add(ePackage);
				this.eResource().getResourceSet().getPackageRegistry().put(nsURI, ePackage);
			}
		}
		
		return imports;
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
	public boolean isSliceBoundaryContainments() {
		return sliceBoundaryContainments;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSliceBoundaryContainments(boolean newSliceBoundaryContainments) {
		boolean oldSliceBoundaryContainments = sliceBoundaryContainments;
		sliceBoundaryContainments = newSliceBoundaryContainments;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.SLICING_CONFIGURATION__SLICE_BOUNDARY_CONTAINMENTS, oldSliceBoundaryContainments, sliceBoundaryContainments));
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
	 * @generated NOT
	 */
	public Map<EClass, SlicedEClass> getOppositeSlicedEClassType() {
		oppositeSlicedEClassType.clear();
		for (SlicedEClass slicedEClass : slicedEClasses) {
			if (slicedEClass.getType() != null) {
				oppositeSlicedEClassType.put(slicedEClass.getType(), slicedEClass);
			}
		}
		
		return oppositeSlicedEClassType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Constraint> getConstraints() {
		if (constraints == null) {
			constraints = new EObjectContainmentEList<Constraint>(Constraint.class, this, ConfigurationPackage.SLICING_CONFIGURATION__CONSTRAINTS);
		}
		return constraints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IConstraintInterpreter getConstraintinterpreter() {
		return constraintinterpreter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConstraintinterpreter(IConstraintInterpreter newConstraintinterpreter, NotificationChain msgs) {
		IConstraintInterpreter oldConstraintinterpreter = constraintinterpreter;
		constraintinterpreter = newConstraintinterpreter;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ConfigurationPackage.SLICING_CONFIGURATION__CONSTRAINTINTERPRETER, oldConstraintinterpreter, newConstraintinterpreter);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConstraintinterpreter(IConstraintInterpreter newConstraintinterpreter) {
		if (newConstraintinterpreter != constraintinterpreter) {
			NotificationChain msgs = null;
			if (constraintinterpreter != null)
				msgs = ((InternalEObject)constraintinterpreter).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ConfigurationPackage.SLICING_CONFIGURATION__CONSTRAINTINTERPRETER, null, msgs);
			if (newConstraintinterpreter != null)
				msgs = ((InternalEObject)newConstraintinterpreter).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ConfigurationPackage.SLICING_CONFIGURATION__CONSTRAINTINTERPRETER, null, msgs);
			msgs = basicSetConstraintinterpreter(newConstraintinterpreter, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.SLICING_CONFIGURATION__CONSTRAINTINTERPRETER, newConstraintinterpreter, newConstraintinterpreter));
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
			case ConfigurationPackage.SLICING_CONFIGURATION__CONSTRAINTS:
				return ((InternalEList<?>)getConstraints()).basicRemove(otherEnd, msgs);
			case ConfigurationPackage.SLICING_CONFIGURATION__CONSTRAINTINTERPRETER:
				return basicSetConstraintinterpreter(null, msgs);
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
			case ConfigurationPackage.SLICING_CONFIGURATION__DOCUMENT_TYPES:
				return getDocumentTypes();
			case ConfigurationPackage.SLICING_CONFIGURATION__IMPORTS:
				return getImports();
			case ConfigurationPackage.SLICING_CONFIGURATION__SLICING_MODE:
				return getSlicingMode();
			case ConfigurationPackage.SLICING_CONFIGURATION__SLICE_BOUNDARY_CONTAINMENTS:
				return isSliceBoundaryContainments();
			case ConfigurationPackage.SLICING_CONFIGURATION__SLICED_ECLASSES:
				return getSlicedEClasses();
			case ConfigurationPackage.SLICING_CONFIGURATION__OPPOSITE_SLICED_ECLASS_TYPE:
				return getOppositeSlicedEClassType();
			case ConfigurationPackage.SLICING_CONFIGURATION__CONSTRAINTS:
				return getConstraints();
			case ConfigurationPackage.SLICING_CONFIGURATION__CONSTRAINTINTERPRETER:
				return getConstraintinterpreter();
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
			case ConfigurationPackage.SLICING_CONFIGURATION__DOCUMENT_TYPES:
				getDocumentTypes().clear();
				getDocumentTypes().addAll((Collection<? extends String>)newValue);
				return;
			case ConfigurationPackage.SLICING_CONFIGURATION__IMPORTS:
				getImports().clear();
				getImports().addAll((Collection<? extends EPackage>)newValue);
				return;
			case ConfigurationPackage.SLICING_CONFIGURATION__SLICING_MODE:
				setSlicingMode((SlicingMode)newValue);
				return;
			case ConfigurationPackage.SLICING_CONFIGURATION__SLICE_BOUNDARY_CONTAINMENTS:
				setSliceBoundaryContainments((Boolean)newValue);
				return;
			case ConfigurationPackage.SLICING_CONFIGURATION__SLICED_ECLASSES:
				getSlicedEClasses().clear();
				getSlicedEClasses().addAll((Collection<? extends SlicedEClass>)newValue);
				return;
			case ConfigurationPackage.SLICING_CONFIGURATION__CONSTRAINTS:
				getConstraints().clear();
				getConstraints().addAll((Collection<? extends Constraint>)newValue);
				return;
			case ConfigurationPackage.SLICING_CONFIGURATION__CONSTRAINTINTERPRETER:
				setConstraintinterpreter((IConstraintInterpreter)newValue);
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
			case ConfigurationPackage.SLICING_CONFIGURATION__DOCUMENT_TYPES:
				getDocumentTypes().clear();
				return;
			case ConfigurationPackage.SLICING_CONFIGURATION__IMPORTS:
				getImports().clear();
				return;
			case ConfigurationPackage.SLICING_CONFIGURATION__SLICING_MODE:
				setSlicingMode(SLICING_MODE_EDEFAULT);
				return;
			case ConfigurationPackage.SLICING_CONFIGURATION__SLICE_BOUNDARY_CONTAINMENTS:
				setSliceBoundaryContainments(SLICE_BOUNDARY_CONTAINMENTS_EDEFAULT);
				return;
			case ConfigurationPackage.SLICING_CONFIGURATION__SLICED_ECLASSES:
				getSlicedEClasses().clear();
				return;
			case ConfigurationPackage.SLICING_CONFIGURATION__CONSTRAINTS:
				getConstraints().clear();
				return;
			case ConfigurationPackage.SLICING_CONFIGURATION__CONSTRAINTINTERPRETER:
				setConstraintinterpreter((IConstraintInterpreter)null);
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
			case ConfigurationPackage.SLICING_CONFIGURATION__DOCUMENT_TYPES:
				return documentTypes != null && !documentTypes.isEmpty();
			case ConfigurationPackage.SLICING_CONFIGURATION__IMPORTS:
				return !getImports().isEmpty();
			case ConfigurationPackage.SLICING_CONFIGURATION__SLICING_MODE:
				return slicingMode != SLICING_MODE_EDEFAULT;
			case ConfigurationPackage.SLICING_CONFIGURATION__SLICE_BOUNDARY_CONTAINMENTS:
				return sliceBoundaryContainments != SLICE_BOUNDARY_CONTAINMENTS_EDEFAULT;
			case ConfigurationPackage.SLICING_CONFIGURATION__SLICED_ECLASSES:
				return slicedEClasses != null && !slicedEClasses.isEmpty();
			case ConfigurationPackage.SLICING_CONFIGURATION__OPPOSITE_SLICED_ECLASS_TYPE:
				return getOppositeSlicedEClassType() != null;
			case ConfigurationPackage.SLICING_CONFIGURATION__CONSTRAINTS:
				return constraints != null && !constraints.isEmpty();
			case ConfigurationPackage.SLICING_CONFIGURATION__CONSTRAINTINTERPRETER:
				return constraintinterpreter != null;
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
		result.append(", documentTypes: ");
		result.append(documentTypes);
		result.append(", slicingMode: ");
		result.append(slicingMode);
		result.append(", sliceBoundaryContainments: ");
		result.append(sliceBoundaryContainments);
		result.append(')');
		return result.toString();
	}

} //SlicingConfigurationImpl
