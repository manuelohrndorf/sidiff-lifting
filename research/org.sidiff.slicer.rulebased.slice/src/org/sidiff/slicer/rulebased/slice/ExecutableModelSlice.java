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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "(c), Christopher Pietsch, Software Engineering Group, University of Siegen 2017 all rights reserved";

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
	 * @model required="true" pathRequired="true" zipRequired="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='String absolute_path = path; String modelslice_name = \"ExecutableSlice.slice\"; String symmetricdiff_name = \"ExecutableSlice.\" + AsymmetricDiffFacade.SYMMETRIC_DIFF_EXT; String asymmetricdiff_name = \"ExecutableSlice.\" + AsymmetricDiffFacade.ASYMMETRIC_DIFF_EXT; String foldername = \"RuleBasedSlice\"; String separator = File.separator; for (SlicedElement slicedElement : this.getSlicedElements()) { slicedElement.setObject(null); } if (!absolute_path.endsWith(separator)) { absolute_path += separator; } absolute_path += foldername; SymmetricDifference symmetricDifference = this.getAsymmetricDifference().getSymmetricDifference(); symmetricDifference.getMatching().setUriA(modelslice_name); symmetricDifference.getMatching().setUriB(modelslice_name); symmetricDifference.setUriModelA(modelslice_name); symmetricDifference.setUriModelB(modelslice_name); AsymmetricDifference asymmetricDifference = this.getAsymmetricDifference(); asymmetricDifference.setUriOriginModel(modelslice_name); asymmetricDifference.setUriChangedModel(modelslice_name); ResourceSet resourceSet = new ResourceSetImpl(); String resSymDiffSavePath = absolute_path + separator + symmetricdiff_name; resourceSet.createResource(EMFStorage.pathToUri(resSymDiffSavePath)).getContents().add(symmetricDifference); String resAsymDiffSavePath = absolute_path + separator + asymmetricdiff_name; resourceSet.createResource(EMFStorage.pathToUri(resAsymDiffSavePath)).getContents().add(asymmetricDifference); String resModelSliceSavePath = absolute_path + separator + modelslice_name; resourceSet.createResource(EMFStorage.pathToUri(resModelSliceSavePath)).getContents().add(this); for (Resource resource : resourceSet.getResources()) { try { resource.save(null); } catch (IOException e) { e.printStackTrace(); } } if (zip) { ZipUtil.zip(absolute_path, absolute_path, AsymmetricDiffFacade.PATCH_EXTENSION); FileOperations.removeFolder(absolute_path); }  return zip? absolute_path + \".\" + AsymmetricDiffFacade.PATCH_EXTENSION: absolute_path;'"
	 * @generated
	 */
	String serialize(String path, boolean zip);

} // ExecutableModelSlice
