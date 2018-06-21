/**
 */
package org.sidiff.slicer.structural.configuration.impl;

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
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.sidiff.slicer.structural.configuration.ConfigurationPackage;
import org.sidiff.slicer.structural.configuration.IConstraintInterpreter;
import org.sidiff.slicer.structural.configuration.SlicedEClass;
import org.sidiff.slicer.structural.configuration.SlicingConfiguration;
import org.sidiff.slicer.structural.configuration.SlicingMode;
import org.sidiff.slicer.structural.configuration.util.ConfigurationUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Slicing Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.slicer.structural.configuration.impl.SlicingConfigurationImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.sidiff.slicer.structural.configuration.impl.SlicingConfigurationImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link org.sidiff.slicer.structural.configuration.impl.SlicingConfigurationImpl#getDocumentTypes <em>Document Types</em>}</li>
 *   <li>{@link org.sidiff.slicer.structural.configuration.impl.SlicingConfigurationImpl#getImports <em>Imports</em>}</li>
 *   <li>{@link org.sidiff.slicer.structural.configuration.impl.SlicingConfigurationImpl#getSlicingMode <em>Slicing Mode</em>}</li>
 *   <li>{@link org.sidiff.slicer.structural.configuration.impl.SlicingConfigurationImpl#getSlicedEClasses <em>Sliced EClasses</em>}</li>
 *   <li>{@link org.sidiff.slicer.structural.configuration.impl.SlicingConfigurationImpl#getOppositeSlicedEClassType <em>Opposite Sliced EClass Type</em>}</li>
 *   <li>{@link org.sidiff.slicer.structural.configuration.impl.SlicingConfigurationImpl#isCheckMultiplicity <em>Check Multiplicity</em>}</li>
 *   <li>{@link org.sidiff.slicer.structural.configuration.impl.SlicingConfigurationImpl#getConstraintInterpreterID <em>Constraint Interpreter ID</em>}</li>
 *   <li>{@link org.sidiff.slicer.structural.configuration.impl.SlicingConfigurationImpl#getConstraintInterpreter <em>Constraint Interpreter</em>}</li>
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
	 * The cached value of the '{@link #getImports() <em>Imports</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImports()
	 * @generated
	 * @ordered
	 */
	protected EList<EPackage> imports;

	/**
	 * The default value of the '{@link #getSlicingMode() <em>Slicing Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSlicingMode()
	 * @generated
	 * @ordered
	 */
	protected static final SlicingMode SLICING_MODE_EDEFAULT = SlicingMode.FORWARD;

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
	 * The cached value of the '{@link #getOppositeSlicedEClassType() <em>Opposite Sliced EClass Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOppositeSlicedEClassType()
	 * @generated
	 * @ordered
	 */
	protected Map<EClass, SlicedEClass> oppositeSlicedEClassType;
	
	/**
	 * The default value of the '{@link #isCheckMultiplicity() <em>Check Multiplicity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCheckMultiplicity()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CHECK_MULTIPLICITY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isCheckMultiplicity() <em>Check Multiplicity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCheckMultiplicity()
	 * @generated
	 * @ordered
	 */
	protected boolean checkMultiplicity = CHECK_MULTIPLICITY_EDEFAULT;

	/**
	 * The default value of the '{@link #getConstraintInterpreterID() <em>Constraint Interpreter ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConstraintInterpreterID()
	 * @generated
	 * @ordered
	 */
	protected static final String CONSTRAINT_INTERPRETER_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getConstraintInterpreterID() <em>Constraint Interpreter ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConstraintInterpreterID()
	 * @generated
	 * @ordered
	 */
	protected String constraintInterpreterID = CONSTRAINT_INTERPRETER_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getConstraintInterpreter() <em>Constraint Interpreter</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConstraintInterpreter()
	 * @generated
	 * @ordered
	 */
	protected static final IConstraintInterpreter CONSTRAINT_INTERPRETER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getConstraintInterpreter() <em>Constraint Interpreter</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConstraintInterpreter()
	 * @generated
	 * @ordered
	 */
	protected IConstraintInterpreter constraintInterpreter = CONSTRAINT_INTERPRETER_EDEFAULT;

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
	 * @generated NOT
	 */
	public EList<String> getDocumentTypes() {
		EList<String> documentTypes = new EDataTypeUniqueEList<String>(String.class, this, ConfigurationPackage.SLICING_CONFIGURATION__DOCUMENT_TYPES);
		for(EPackage ePackage : getImports()){
			documentTypes.add(ePackage.getNsURI());
		}
		return documentTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EPackage> getImports() {
		if (imports == null) {
			imports = new EObjectResolvingEList<EPackage>(EPackage.class, this, ConfigurationPackage.SLICING_CONFIGURATION__IMPORTS);
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
		oppositeSlicedEClassType = new HashMap<EClass, SlicedEClass>();
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
	public boolean isCheckMultiplicity() {
		return checkMultiplicity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCheckMultiplicity(boolean newCheckMultiplicity) {
		boolean oldCheckMultiplicity = checkMultiplicity;
		checkMultiplicity = newCheckMultiplicity;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.SLICING_CONFIGURATION__CHECK_MULTIPLICITY, oldCheckMultiplicity, checkMultiplicity));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getConstraintInterpreterID() {
		return constraintInterpreterID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setConstraintInterpreterID(String newConstraintInterpreterID) {
		String oldConstraintInterpreterID = constraintInterpreterID;
		constraintInterpreterID = newConstraintInterpreterID;
		constraintInterpreter = ConfigurationUtil.getConstraintInterpreterByID(constraintInterpreterID);
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.SLICING_CONFIGURATION__CONSTRAINT_INTERPRETER_ID, oldConstraintInterpreterID, constraintInterpreterID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public IConstraintInterpreter getConstraintInterpreter() {
		if(constraintInterpreter == null){
			constraintInterpreter = ConfigurationUtil.getConstraintInterpreterByID(constraintInterpreterID);
		}
		return constraintInterpreter;
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
			case ConfigurationPackage.SLICING_CONFIGURATION__DOCUMENT_TYPES:
				return getDocumentTypes();
			case ConfigurationPackage.SLICING_CONFIGURATION__IMPORTS:
				return getImports();
			case ConfigurationPackage.SLICING_CONFIGURATION__SLICING_MODE:
				return getSlicingMode();
			case ConfigurationPackage.SLICING_CONFIGURATION__SLICED_ECLASSES:
				return getSlicedEClasses();
			case ConfigurationPackage.SLICING_CONFIGURATION__OPPOSITE_SLICED_ECLASS_TYPE:
				return getOppositeSlicedEClassType();
			case ConfigurationPackage.SLICING_CONFIGURATION__CHECK_MULTIPLICITY:
				return isCheckMultiplicity();
			case ConfigurationPackage.SLICING_CONFIGURATION__CONSTRAINT_INTERPRETER_ID:
				return getConstraintInterpreterID();
			case ConfigurationPackage.SLICING_CONFIGURATION__CONSTRAINT_INTERPRETER:
				return getConstraintInterpreter();
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
			case ConfigurationPackage.SLICING_CONFIGURATION__CHECK_MULTIPLICITY:
				setCheckMultiplicity((Boolean)newValue);
				return;
			case ConfigurationPackage.SLICING_CONFIGURATION__CONSTRAINT_INTERPRETER_ID:
				setConstraintInterpreterID((String)newValue);
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
			case ConfigurationPackage.SLICING_CONFIGURATION__IMPORTS:
				getImports().clear();
				return;
			case ConfigurationPackage.SLICING_CONFIGURATION__SLICING_MODE:
				setSlicingMode(SLICING_MODE_EDEFAULT);
				return;
			case ConfigurationPackage.SLICING_CONFIGURATION__SLICED_ECLASSES:
				getSlicedEClasses().clear();
				return;
			case ConfigurationPackage.SLICING_CONFIGURATION__CHECK_MULTIPLICITY:
				setCheckMultiplicity(CHECK_MULTIPLICITY_EDEFAULT);
				return;
			case ConfigurationPackage.SLICING_CONFIGURATION__CONSTRAINT_INTERPRETER_ID:
				setConstraintInterpreterID(CONSTRAINT_INTERPRETER_ID_EDEFAULT);
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
				return !getDocumentTypes().isEmpty();
			case ConfigurationPackage.SLICING_CONFIGURATION__IMPORTS:
				return imports != null && !imports.isEmpty();
			case ConfigurationPackage.SLICING_CONFIGURATION__SLICING_MODE:
				return slicingMode != SLICING_MODE_EDEFAULT;
			case ConfigurationPackage.SLICING_CONFIGURATION__SLICED_ECLASSES:
				return slicedEClasses != null && !slicedEClasses.isEmpty();
			case ConfigurationPackage.SLICING_CONFIGURATION__OPPOSITE_SLICED_ECLASS_TYPE:
				return oppositeSlicedEClassType != null;
			case ConfigurationPackage.SLICING_CONFIGURATION__CHECK_MULTIPLICITY:
				return checkMultiplicity != CHECK_MULTIPLICITY_EDEFAULT;
			case ConfigurationPackage.SLICING_CONFIGURATION__CONSTRAINT_INTERPRETER_ID:
				return CONSTRAINT_INTERPRETER_ID_EDEFAULT == null ? constraintInterpreterID != null : !CONSTRAINT_INTERPRETER_ID_EDEFAULT.equals(constraintInterpreterID);
			case ConfigurationPackage.SLICING_CONFIGURATION__CONSTRAINT_INTERPRETER:
				return CONSTRAINT_INTERPRETER_EDEFAULT == null ? constraintInterpreter != null : !CONSTRAINT_INTERPRETER_EDEFAULT.equals(constraintInterpreter);
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
		result.append(", slicingMode: ");
		result.append(slicingMode);
		result.append(", oppositeSlicedEClassType: ");
		result.append(oppositeSlicedEClassType);
		result.append(", checkMultiplicity: ");
		result.append(checkMultiplicity);
		result.append(", constraintInterpreterID: ");
		result.append(constraintInterpreterID);
		result.append(", constraintInterpreter: ");
		result.append(constraintInterpreter);
		result.append(')');
		return result.toString();
	}

} //SlicingConfigurationImpl
