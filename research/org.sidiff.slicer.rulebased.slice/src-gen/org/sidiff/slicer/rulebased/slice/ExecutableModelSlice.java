/**
 */
package org.sidiff.slicer.rulebased.slice;

import org.sidiff.difference.asymmetric.AsymmetricDifference;

import org.sidiff.slicer.slice.ModelSlice;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Executable Model Slice</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.slicer.rulebased.slice.ExecutableModelSlice#getAsymmetricDifference <em>Asymmetric Difference</em>}</li>
 * </ul>
 *
 * @see org.sidiff.slicer.rulebased.slice.RuleBasedSlicePackage#getExecutableModelSlice()
 * @model
 * @generated
 */
public interface ExecutableModelSlice extends ModelSlice {
	/**
	 * Returns the value of the '<em><b>Asymmetric Difference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Asymmetric Difference</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Asymmetric Difference</em>' reference.
	 * @see #setAsymmetricDifference(AsymmetricDifference)
	 * @see org.sidiff.slicer.rulebased.slice.RuleBasedSlicePackage#getExecutableModelSlice_AsymmetricDifference()
	 * @model required="true"
	 * @generated
	 */
	AsymmetricDifference getAsymmetricDifference();

	/**
	 * Sets the value of the '{@link org.sidiff.slicer.rulebased.slice.ExecutableModelSlice#getAsymmetricDifference <em>Asymmetric Difference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Asymmetric Difference</em>' reference.
	 * @see #getAsymmetricDifference()
	 * @generated
	 */
	void setAsymmetricDifference(AsymmetricDifference value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model pathRequired="true" pathOrdered="false" zipRequired="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='String MODELSLICE_NAME = \"ExecutableSlice.slice\";  String SYMMETRICDIFF_NAME = \"ExecutableSlice.\" + AsymmetricDiffFacade.SYMMETRIC_DIFF_EXT;  String ASYMMETRICDIFF_NAME = \"ExecutableSlice.\" + AsymmetricDiffFacade.ASYMMETRIC_DIFF_EXT;  String FOLDERNAME = \"RuleBasedSlice\";  String SEPARATOR = File.separator;  for(SlicedElement slicedElement : this.getSlicedElements()) { slicedElement.setObject(null); }  if (!path.endsWith(SEPARATOR)) { path += SEPARATOR; }  path += FOLDERNAME;  SymmetricDifference symmetricDifference = this.getAsymmetricDifference().getSymmetricDifference(); symmetricDifference.getMatching().setUriA(MODELSLICE_NAME); symmetricDifference.getMatching().setUriB(MODELSLICE_NAME); symmetricDifference.setUriModelA(MODELSLICE_NAME); symmetricDifference.setUriModelB(MODELSLICE_NAME);  AsymmetricDifference asymmetricDifference = this.getAsymmetricDifference(); asymmetricDifference.setUriOriginModel(MODELSLICE_NAME); asymmetricDifference.setUriChangedModel(MODELSLICE_NAME);  String resSymDiffSavePath = path + SEPARATOR + SYMMETRICDIFF_NAME;  EMFStorage.eSaveAs(EMFStorage.pathToUri(resSymDiffSavePath), symmetricDifference, true);  String resAsymDiffSavePath = path + SEPARATOR + ASYMMETRICDIFF_NAME;  EMFStorage.eSaveAs(EMFStorage.pathToUri(resAsymDiffSavePath), asymmetricDifference, true);  String resModelSliceSavePath = path + SEPARATOR + MODELSLICE_NAME;  EMFStorage.eSaveAs(EMFStorage.pathToUri(resModelSliceSavePath), this, true);  if(zip) { ZipUtil.zip(path, path, AsymmetricDiffFacade.PATCH_EXTENSION); FileOperations.removeFolder(path); }'"
	 * @generated
	 */
	void serialize(String path, boolean zip);

} // ExecutableModelSlice
