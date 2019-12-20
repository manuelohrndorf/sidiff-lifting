/**
 */
package org.sidiff.slicer.rulebased.slice.impl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.file.FileOperations;
import org.sidiff.common.file.ZipUtil;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.api.AsymmetricDiffFacade;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.slicer.rulebased.slice.ExecutableModelSlice;
import org.sidiff.slicer.rulebased.slice.RuleBasedSlicePackage;
import org.sidiff.slicer.slice.SlicedElement;
import org.sidiff.slicer.slice.impl.ModelSliceImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Executable Model Slice</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.slicer.rulebased.slice.impl.ExecutableModelSliceImpl#getAsymmetricDifference <em>Asymmetric Difference</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ExecutableModelSliceImpl extends ModelSliceImpl implements ExecutableModelSlice {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "(c), Christopher Pietsch, Software Engineering Group, University of Siegen 2017 all rights reserved";
	/**
	 * The cached value of the '{@link #getAsymmetricDifference() <em>Asymmetric Difference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAsymmetricDifference()
	 * @generated
	 * @ordered
	 */
	protected AsymmetricDifference asymmetricDifference;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExecutableModelSliceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RuleBasedSlicePackage.Literals.EXECUTABLE_MODEL_SLICE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AsymmetricDifference getAsymmetricDifference() {
		if (asymmetricDifference != null && asymmetricDifference.eIsProxy()) {
			InternalEObject oldAsymmetricDifference = (InternalEObject)asymmetricDifference;
			asymmetricDifference = (AsymmetricDifference)eResolveProxy(oldAsymmetricDifference);
			if (asymmetricDifference != oldAsymmetricDifference) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RuleBasedSlicePackage.EXECUTABLE_MODEL_SLICE__ASYMMETRIC_DIFFERENCE, oldAsymmetricDifference, asymmetricDifference));
			}
		}
		return asymmetricDifference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AsymmetricDifference basicGetAsymmetricDifference() {
		return asymmetricDifference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAsymmetricDifference(AsymmetricDifference newAsymmetricDifference) {
		AsymmetricDifference oldAsymmetricDifference = asymmetricDifference;
		asymmetricDifference = newAsymmetricDifference;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RuleBasedSlicePackage.EXECUTABLE_MODEL_SLICE__ASYMMETRIC_DIFFERENCE, oldAsymmetricDifference, asymmetricDifference));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public String serialize(final String path, final boolean zip) {
		String absolute_path = path;
		String modelslice_name = "ExecutableSlice.slice";
		String symmetricdiff_name = "ExecutableSlice." + AsymmetricDiffFacade.SYMMETRIC_DIFF_EXT;
		String asymmetricdiff_name = "ExecutableSlice." + AsymmetricDiffFacade.ASYMMETRIC_DIFF_EXT;
		String foldername = "RuleBasedSlice";
		String separator = File.separator;
		for (SlicedElement slicedElement : this.getSlicedElements()) {
			slicedElement.setObject(null);
		}
		if (!absolute_path.endsWith(separator)) {
			absolute_path += separator;
		}
		absolute_path += foldername;
		SymmetricDifference symmetricDifference = this.getAsymmetricDifference().getSymmetricDifference();
		symmetricDifference.getMatching().setUriA(modelslice_name);
		symmetricDifference.getMatching().setUriB(modelslice_name);
		symmetricDifference.setUriModelA(modelslice_name);
		symmetricDifference.setUriModelB(modelslice_name);
		AsymmetricDifference asymmetricDifference = this.getAsymmetricDifference();
		asymmetricDifference.setUriOriginModel(modelslice_name);
		asymmetricDifference.setUriChangedModel(modelslice_name);
		ResourceSet resourceSet = new ResourceSetImpl();
		String resSymDiffSavePath = absolute_path + separator + symmetricdiff_name;
		resourceSet.createResource(EMFStorage.pathToUri(resSymDiffSavePath)).getContents().add(symmetricDifference);
		String resAsymDiffSavePath = absolute_path + separator + asymmetricdiff_name;
		resourceSet.createResource(EMFStorage.pathToUri(resAsymDiffSavePath)).getContents().add(asymmetricDifference);
		String resModelSliceSavePath = absolute_path + separator + modelslice_name;
		resourceSet.createResource(EMFStorage.pathToUri(resModelSliceSavePath)).getContents().add(this);
		for (Resource resource : resourceSet.getResources()) {
			try {
				resource.save(null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (zip) {
			try {
				ZipUtil.zip(Paths.get(absolute_path), Paths.get(absolute_path + "." + AsymmetricDiffFacade.PATCH_EXTENSION));
				FileOperations.removeFolder(Paths.get(absolute_path));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return zip ? (absolute_path + "." + AsymmetricDiffFacade.PATCH_EXTENSION) : absolute_path;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RuleBasedSlicePackage.EXECUTABLE_MODEL_SLICE__ASYMMETRIC_DIFFERENCE:
				if (resolve) return getAsymmetricDifference();
				return basicGetAsymmetricDifference();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case RuleBasedSlicePackage.EXECUTABLE_MODEL_SLICE__ASYMMETRIC_DIFFERENCE:
				setAsymmetricDifference((AsymmetricDifference)newValue);
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
			case RuleBasedSlicePackage.EXECUTABLE_MODEL_SLICE__ASYMMETRIC_DIFFERENCE:
				setAsymmetricDifference((AsymmetricDifference)null);
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
			case RuleBasedSlicePackage.EXECUTABLE_MODEL_SLICE__ASYMMETRIC_DIFFERENCE:
				return asymmetricDifference != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case RuleBasedSlicePackage.EXECUTABLE_MODEL_SLICE___SERIALIZE__STRING_BOOLEAN:
				return serialize((String)arguments.get(0), (Boolean)arguments.get(1));
		}
		return super.eInvoke(operationID, arguments);
	}

} //ExecutableModelSliceImpl
