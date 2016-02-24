package org.sidiff.difference.model.util;

import differencemodel.AddObject;
import differencemodel.AddReference;
import differencemodel.AttributeValueChange;
import differencemodel.Change;
import differencemodel.Difference;
import differencemodel.RemoveObject;
import differencemodel.RemoveReference;

/**
 * This class contains some analyzed difference informations.
 */
public class DifferenceAnalysis {

	/**
	 * The corresponding difference.
	 */
	private Difference difference;

	/**
	 * Count of all AddObject changes in the difference.
	 */
	private int addObjectCount = 0;

	/**
	 * Count of all RemoveObject changes in the difference.
	 */
	private int removeObjectCount = 0;

	/**
	 * Count of all AddReference changes in the difference.
	 */
	private int addReferenceCount = 0;

	/**
	 * Count of all RemoveReference changes in the difference.
	 */
	private int removeReferenceCount = 0;

	/**
	 * Count of all AttributeValueChange changes in the difference.
	 */
	private int attributeValueChangeCount = 0;

	/**
	 * Count of all Correspondences in the difference.
	 */
	private int correspondenceCount = 0;

	/**
	 * Initialize difference analysis.
	 * 
	 * @param difference
	 *            the difference to analyze.
	 */
	public DifferenceAnalysis(Difference difference) {
		super();
		this.difference = difference;

		// Count Changes and correspondences
		countChanges();
		correspondenceCount = difference.getCorrespondences().size();
	}

	/**
	 * Count all changes in the difference.
	 */
	private void countChanges() {

		// Count the atomic changes
		for (Change change : difference.getChanges()) {
			if (change instanceof AddObject) {
				addObjectCount++;
			}
			else if (change instanceof RemoveObject) {
				removeObjectCount++;
			}
			else if (change instanceof AddReference) {
				addReferenceCount++;
			}
			else if (change instanceof RemoveReference) {
				removeReferenceCount++;
			}
			else if (change instanceof AttributeValueChange) {
				attributeValueChangeCount++;
			}
		}
	}


	/**
	 * @return the corresponding difference.
	 */
	public Difference getDifference() {
		return difference;
	}

	/**
	 * @return the count of all AddObject changes in the difference.
	 */
	public int getAddObjectCount() {
		return addObjectCount;
	}

	/**
	 * @return the count of all RemoveObject changes in the difference.
	 */
	public int getRemoveObjectCount() {
		return removeObjectCount;
	}

	/**
	 * @return the count of all AddReference changes in the difference.
	 */
	public int getAddReferenceCount() {
		return addReferenceCount;
	}

	/**
	 * @return the count of all AddReference changes in the difference.
	 */
	public int getRemoveReferenceCount() {
		return removeReferenceCount;
	}

	/**
	 * @return the count of all AddReference changes in the difference.
	 */
	public int getAttributeValueChangeCount() {
		return attributeValueChangeCount;
	}

	/**
	 * @return the count of all Correspondences in the difference.
	 */
	public int getCorrespondenceCount() {
		return correspondenceCount;
	}
}
